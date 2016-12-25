package org.cisiondata.utils.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cisiondata.utils.serde.SerializerUtils;
import org.cisiondata.utils.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Response;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.SafeEncoder;

@SuppressWarnings("deprecation")
public class RedisUtils {
	
    private final Logger LOG = LoggerFactory.getLogger(RedisUtils.class);
    
    public static final int CACHE_RETRY_TIMES = 3;
    
    private ShardedJedisPoolWrapper shardedJedisPoolWrapper;

    private ShardedJedisPool pool;

    private RedisUtils(){
    	this.shardedJedisPoolWrapper = (ShardedJedisPoolWrapper) SpringUtils.getBean("shardedJedisPool");
    	this.pool = this.shardedJedisPoolWrapper.getJedisPool();
    }
    
    private static class RedisUtilsHolder {
    	public static RedisUtils INSTANCE = new RedisUtils(); 
    }
    
    public static RedisUtils getInstance() {
    	return RedisUtilsHolder.INSTANCE;
    }
    
    public ShardedJedisPool getPool() {
        return pool;
    }

	public void transaction(String key, CacheOperationFactory factory) throws IOException {
        boolean isFailed = false;
        ShardedJedis jedis = pool.getResource();
        try {
            Jedis shard = jedis.getShard(key);
            shard.watch(SafeEncoder.encode(key));
            Transaction t = shard.multi();
            factory.setJedis(shard);
            factory.cache();
            t.exec();
        } catch (JedisException e) {
            isFailed = true;
            throw e;
        } finally {
            if (isFailed) {
                pool.returnBrokenResource(jedis);
            } else {
                pool.returnResource(jedis);
            }
        }
    }

    protected abstract class CacheOperationFactory {
        private Jedis jedis;

        public void setJedis(Jedis jedis) {
            this.jedis = jedis;
        }

        public Jedis getJedis() {
            return this.jedis;
        }

        public abstract void cache() throws JedisException, IOException;
    }

    public String set(final String key, final Object o) throws IOException {
        return new CountOperationFactory<String>(CACHE_RETRY_TIMES, key, false) {
            public String cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                return jedis.set(SafeEncoder.encode(key),
                        SerializerUtils.write(o));
            }
        }.operate();
    }

    public Long setnx(final String key, final Object o) throws IOException {
        return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                return jedis.setnx(SafeEncoder.encode(key),
                        SerializerUtils.write(o));
            }
        }.operate();
    }

    public String set(final String key, final Object o, final int expireTime) throws IOException {
        return new CountOperationFactory<String>(CACHE_RETRY_TIMES, key, false) {
            public String cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                return jedis.setex(SafeEncoder.encode(key), expireTime,
                        SerializerUtils.write(o));
            }
        }.operate();
    }

    public void delete(final String key) throws IOException {
        new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                return jedis.getShard(key).del(SafeEncoder.encode(key));
            }
        }.operate();
    }

    public <T> T get(final String key) throws IOException {
        try {
            return new CountOperationFactory<T>(CACHE_RETRY_TIMES, key, true) {
                @SuppressWarnings("unchecked")
				public T cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                    byte[] a = jedis.get(SafeEncoder.encode(key));
                    Object t = SerializerUtils.read(a);
                    return (T) t;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when get a object, key: " + key, e);
            return null;
        }
    }
    
  
    public <T> T getSet(final String key, final String value) throws IOException {
        try {
            return new CountOperationFactory<T>(CACHE_RETRY_TIMES, key, true) {
                @SuppressWarnings("unchecked")
				public T cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                    byte[] a = jedis.getSet(SafeEncoder.encode(key), SafeEncoder.encode(value));
                    Object t = SerializerUtils.read(a);
                    return (T) t;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when get a object, key: " + key, e);
            return null;
        }
    }

    public Long incrby(final String key, final long increment) throws IOException {
       return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                return jedis.incrBy(SafeEncoder.encode(key), increment);
            }
        }.operate();
    }
    
    public Long decrby(final String key, final long increment) throws IOException {
       return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                return jedis.decrBy(SafeEncoder.encode(key), increment);
            }
        }.operate();
    }
    
    public Double zScore(String key, final Object o) throws IOException {
        try {
            return new CountOperationFactory<Double>(CACHE_RETRY_TIMES, key, false) {
                public Double cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                    return jedis.zscore(SafeEncoder.encode(key),
                            SerializerUtils.write(o));
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling zScore, key: " + key, e);
            return null;
        }
    }

    public void listTrim(String key, final int start, final int end) throws IOException {
        new CountOperationFactory<Long>(1, key, false) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                jedis.ltrim(key, start, end);
                return 0L;
            }
        }.operate();
    }

    public <T> long listAppend(final String key, final T[] objs) throws IOException {
        return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                if (objs == null || objs.length == 0) {
                    return 0L;
                }
                byte[][] bytes = new byte[objs.length][];
                int lastIndex = bytes.length - 1;
                for (int i = lastIndex; i >= 0; i--) {
                    bytes[lastIndex - i] = SerializerUtils.write(objs[i]);
                }
                return jedis.lpush(SafeEncoder.encode(key), bytes);
            }
        }.operate();
    }

    public <T> long listAppend(final String key, final T obj) throws IOException {
        return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                if (obj == null) {
                    return 0L;
                }
                byte[] bytes = SerializerUtils.write(obj);
                return jedis.lpush(SafeEncoder.encode(key), bytes);
            }
        }.operate();
    }

    public <T> T rPop(final String key) throws IOException {
        return new CountOperationFactory<T>(CACHE_RETRY_TIMES, key, false) {
            @SuppressWarnings("unchecked")
			public T cacheOperate(ShardedJedis jedis) throws JedisException,
                    IOException {
                byte[] bytes = jedis.rpop(SafeEncoder.encode(key));
                if (bytes == null) {
                    return null;
                }
                return (T) SerializerUtils.read(bytes);
            }
        }.operate();
    }

    public <T> void listDel(final String key, final T[] objs) throws IOException {
        new CountOperationFactory<Void>(CACHE_RETRY_TIMES, key, false) {
            public Void cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                if (objs == null || objs.length == 0) {
                    return null;
                }
                byte[][] bytes = new byte[objs.length][];
                int lastIndex = bytes.length - 1;
                for (int i = lastIndex; i >= 0; i--) {
                    jedis.lrem(SafeEncoder.encode(key), 1,
                            SerializerUtils.write(objs[i]));
                }
                return null;
            }
        }.operate();
    }

    public <T> void listDel(final String key, final T obj) throws IOException {
        new CountOperationFactory<Void>(CACHE_RETRY_TIMES, key, false) {
            public Void cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                if (obj == null) {
                    return null;
                }
                jedis.lrem(SafeEncoder.encode(key), 1,
                        SerializerUtils.write(obj));
                return null;
            }
        }.operate();
    }


    public Long listLength(final String key) throws IOException {
        return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, true) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                try {
                    return jedis.llen(SafeEncoder.encode(key));
                } catch (Exception e) {
                    LOG.error("cache error when calling listlength, key=" + key, e);
                    return 0L;
                }
            }
        }.operate();
    }

    public <T> List<T> listRange(final String key, final int start, final int end) throws IOException {
        final int includeEnd = end - 1;
        if (start < 0 || includeEnd < start) {
            return Collections.emptyList();
        }
        final List<T> objs = new ArrayList<T>(includeEnd - start + 1);

        try {
            return new CountOperationFactory<List<T>>(CACHE_RETRY_TIMES, key, true) {
                @SuppressWarnings("unchecked")
				public List<T> cacheOperate(ShardedJedis jedis) throws IOException {
                    List<byte[]> values = jedis.lrange(SafeEncoder.encode(key), start, includeEnd);

                    for (byte[] v : values) {
                        try {
                            objs.add((T) SerializerUtils.read(v));
                        } catch (Exception e) {
                            throw new JedisException(e);
                        }
                    }
                    return objs;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling listRange, key: " + key, e);
            return objs;
        }
    }

    public long zCard(final String key) throws IOException {
        return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, true) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                return jedis.zcard(SafeEncoder.encode(key));
            }
        }.operate();
    }

    public long zCount(final String key, final double min, final double max) throws IOException {
        return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, true) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                return jedis.zcount(SafeEncoder.encode(key), min, max);
            }
        }.operate();
    }
    
    public long zAdd(final String key, final double score, final Object o, final boolean isSerialize) throws IOException {
        return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                return !isSerialize ? jedis.zadd(key, score, String.valueOf(o)) :
                	jedis.zadd(SafeEncoder.encode(key), score, SerializerUtils.write(o));
            }
        }.operate();
    }

	public long zAdd(String key, double score, Object o, long len, boolean isSerialize) throws IOException {
        long rt = zAdd(key, score, o, isSerialize);
        ShardedJedis jedis = null;
        boolean isFailed = false;
        try {
            jedis = pool.getResource();
            long total = jedis.zcard(SafeEncoder.encode(key));
            if (total > len) {
                jedis.zremrangeByRank(SafeEncoder.encode(key), 0, (int) (total - len - 1));
            }
        } catch (JedisException e) {
            LOG.error("exception occur when calling zcard, key: " + key, e);
            isFailed = true;
        } finally {
            if (jedis != null) {
                if (isFailed) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
            }
        }
        return rt;
    }
	
	public long zAdd(String key, double score, Object o, long len) throws IOException {
        long rt = zAdd(key, score, o, true);
        ShardedJedis jedis = null;
        boolean isFailed = false;
        try {
            jedis = pool.getResource();
            long total = jedis.zcard(SafeEncoder.encode(key));
            if (total > len) {
                jedis.zremrangeByRank(SafeEncoder.encode(key), 0, (int) (total - len - 1));
            }
        } catch (JedisException e) {
            LOG.error("exception occur when calling zcard, key: " + key, e);
            isFailed = true;
        } finally {
            if (jedis != null) {
                if (isFailed) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
            }
        }
        return rt;
    }

    public long zRemByValue(final String key, final Object value) throws IOException {
        return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                return jedis.zrem(SafeEncoder.encode(key),
                        SerializerUtils.write(value));
            }
        }.operate();
    }
    
    public long zRemRangeByRank(final String key, final int start, final int end) throws IOException {
        return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                return jedis.zremrangeByRank(SafeEncoder.encode(key), start,
                        end);
            }
        }.operate();
    }

    public long zRemRangeByScore(final String key, final double start, final double end) throws IOException {
        return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
            public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                return jedis.zremrangeByScore(SafeEncoder.encode(key), start, end);
            }
        }.operate();
    }

    public <T> Set<T> zRevRange(final String key, final int start, final int end) throws IOException {
        final Set<T> objs = new LinkedHashSet<T>();
        if (start < 0 || end < start) {
            return objs;
        }
        try {
            return new CountOperationFactory<Set<T>>(CACHE_RETRY_TIMES, key, true) {
                @SuppressWarnings("unchecked")
				public Set<T> cacheOperate(ShardedJedis jedis) throws IOException {
                    Set<byte[]> rt = jedis.zrevrange(SafeEncoder.encode(key), start, end);
                    for (byte[] it : rt) {
                        try {
                            objs.add((T) SerializerUtils.read(it));
                        } catch (Exception e) {
                            throw new JedisException(e);
                        }
                    }
                    return objs;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling zRevRange, key: " + key, e);
            return objs;
        }
    }

    public <T> Set<T> zRevRangeByOffset(final String key, final int start, final int end) throws IOException {
        final Set<T> objs = new LinkedHashSet<T>();
        if (start < 0 || end < start) {
            return objs;
        }

        try {
            return new CountOperationFactory<Set<T>>(CACHE_RETRY_TIMES, key, true) {
                @SuppressWarnings("unchecked")
				public Set<T> cacheOperate(ShardedJedis jedis) throws IOException {
                    Set<byte[]> rt = jedis.zrevrange(SafeEncoder.encode(key), start, end - 1);
                    for (byte[] it : rt) {
                        try {
                            objs.add((T) SerializerUtils.read(it));
                        } catch (Exception e) {
                            throw new JedisException(e);
                        }
                    }
                    return objs;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when zRangeByScore, key: " + key, e);
            return objs;
        }
    }

    public Set<Tuple> zRevWithScores(final String key, final int start, final int end) throws IOException {
        try {
            return new CountOperationFactory<Set<Tuple>>(CACHE_RETRY_TIMES, key, true) {
                public Set<Tuple> cacheOperate(ShardedJedis jedis) throws IOException {
                    return jedis.zrevrangeWithScores(SafeEncoder.encode(key), start, end);
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling zRangeByScore, key: " + key, e);
            return new LinkedHashSet<Tuple>();
        }
    }
    
    public Set<String> zRangeNoSerialize(final String key, final int start, final int end) throws IOException {
        try {
            return new CountOperationFactory<Set<String>>(CACHE_RETRY_TIMES, key, true) {
				public Set<String> cacheOperate(ShardedJedis jedis) throws IOException {
                    return jedis.zrange(key, start, end);
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling zRangeByScore, key: " + key, e);
            return new LinkedHashSet<String>();
        }
    }
    
    public <T> Set<T> zRange(final String key, final int start, final int end) throws IOException {
        final Set<T> rt = new LinkedHashSet<T>();
        try {
            return new CountOperationFactory<Set<T>>(CACHE_RETRY_TIMES, key, true) {
                @SuppressWarnings("unchecked")
				public Set<T> cacheOperate(ShardedJedis jedis) throws IOException {
                    Set<byte[]> ranges = jedis.zrange(SafeEncoder.encode(key), start, end);
                    for (byte[] range : ranges) {
                        try {
                            rt.add((T) SerializerUtils.read(range));
                        } catch (Exception e) {
                            throw new JedisException(e);
                        }
                    }
                    return rt;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling zRangeByScore, key: " + key, e);
            return rt;
        }
    }
    
    public Set<String> zRangeByScoreNoSerialize(final String key, final double min, final double max, 
    		final boolean rev) throws IOException {
        try {
            return new CountOperationFactory<Set<String>>(CACHE_RETRY_TIMES, key, true) {
				public Set<String> cacheOperate(ShardedJedis jedis) throws IOException {
                    Set<String> ranges;
                    if (!rev) {
                        ranges = jedis.zrangeByScore(key, min, max);
                    } else {
                        ranges = jedis.zrevrangeByScore(key, max, min);
                    }
                    return ranges;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling zRangeByScore, key: " + key, e);
            return new LinkedHashSet<String>();
        }
    }

    public <T> Set<T> zRangeByScore(final String key, final double min, final double max, final boolean rev)
            throws IOException {
        final Set<T> rt = new LinkedHashSet<T>();
        try {
            return new CountOperationFactory<Set<T>>(CACHE_RETRY_TIMES, key, true) {
                @SuppressWarnings("unchecked")
				public Set<T> cacheOperate(ShardedJedis jedis) throws IOException {
                    Set<byte[]> ranges;
                    if (!rev) {
                        ranges = jedis.zrangeByScore(SafeEncoder.encode(key), min, max);
                    } else {
                        ranges = jedis.zrevrangeByScore(SafeEncoder.encode(key), max, min);
                    }
                    for (byte[] range : ranges) {
                        try {
                            rt.add((T) SerializerUtils.read(range));
                        } catch (Exception e) {
                            throw new JedisException(e);
                        }
                    }
                    return rt;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling zRangeByScore, key: " + key, e);
            return rt;
        }
    }

    public <T> Set<T> zRangeByScore(final String key, final double min, final double max, final boolean rev,
            final int offset, final int length) throws IOException {
        final Set<T> rt = new LinkedHashSet<T>();
        try {
            return new CountOperationFactory<Set<T>>(CACHE_RETRY_TIMES, key, true) {
                @SuppressWarnings("unchecked")
				public Set<T> cacheOperate(ShardedJedis jedis) throws IOException {
                    Set<byte[]> ranges;
                    if (!rev) {
                        ranges = jedis.zrangeByScore(SafeEncoder.encode(key), min, max, offset, length);
                    } else {
                        ranges = jedis.zrevrangeByScore(SafeEncoder.encode(key), max, min, offset, length);
                        Set<Tuple> tuples = jedis.zrevrangeByScoreWithScores(SafeEncoder.encode(key), 
                        		min, max, offset, length);
                        for(Tuple tuple : tuples){
                        	LOG.info("{} - {}", SerializerUtils.read(tuple.getBinaryElement()), tuple.getScore());
            			}
                    }
                    for (byte[] range : ranges) {
                        try {
                            rt.add((T) SerializerUtils.read(range));
                        } catch (Exception e) {
                            throw new JedisException(e);
                        }
                    }
                    return rt;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling zRangeByScore, key: " + key, e);
            return rt;
        }
    }

    public Set<Tuple> zRangeByScoreWithScores(final String key, final double min, final double max, final boolean rev,
            final int offset, final int length) throws IOException {
        try {
            return new CountOperationFactory<Set<Tuple>>(CACHE_RETRY_TIMES, key, true) {
                public Set<Tuple> cacheOperate(ShardedJedis jedis) throws IOException {
                    if (!rev) {
                        return jedis.zrangeByScoreWithScores(SafeEncoder.encode(key), min, max, offset, length);
                    } else {
                        return jedis.zrevrangeByScoreWithScores(SafeEncoder.encode(key), max, min, offset,
                                length);
                    }
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling zRangeByScoreWithScores, key: " + key, e);
            return new LinkedHashSet<Tuple>();
        }
    }

    public void hmSet(final String key, final Map<String, Object> map) throws IOException {

        if(hExists(key)) {
            new CountOperationFactory<String>(CACHE_RETRY_TIMES, key, false) {
                public String cacheOperate(ShardedJedis jedis) throws IOException {
                    if (map == null || map.isEmpty()) {
                        return null;
                    }
                    Map<byte[], byte[]> binaryMap = new HashMap<byte[], byte[]>(map.size());
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        binaryMap.put(SerializerUtils.write(entry.getKey()),
                                SerializerUtils.write(entry.getValue()));
                    }
                    return jedis.hmset(SafeEncoder.encode(key), binaryMap);
                }
            }.operate();
        }
    }

    public void hmSetCreateKeyIfNotExists(final String key, final Map<String, Object> map) throws IOException {
            new CountOperationFactory<String>(CACHE_RETRY_TIMES, key, false) {
                public String cacheOperate(ShardedJedis jedis) throws IOException {
                    if (map == null || map.isEmpty()) {
                        return null;
                    }
                    Map<byte[], byte[]> binaryMap = new HashMap<byte[], byte[]>(map.size());
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        binaryMap.put(SerializerUtils.write(entry.getKey()),
                                SerializerUtils.write(entry.getValue()));
                    }
                    return jedis.hmset(SafeEncoder.encode(key), binaryMap);
                }
            }.operate();
    }

    public void hmSetMap(final String key, final Map<String, String> map) throws IOException {

            new CountOperationFactory<String>(CACHE_RETRY_TIMES, key, false) {
                public String cacheOperate(ShardedJedis jedis) throws IOException {
                    if (map == null || map.isEmpty()) {
                        return null;
                    }
                    Map<byte[], byte[]> binaryMap = new HashMap<byte[], byte[]>(map.size());
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        binaryMap.put(SerializerUtils.write(entry.getKey()),
                                SerializerUtils.write(entry.getValue()));
                    }
                    return jedis.hmset(SafeEncoder.encode(key), binaryMap);
                }
            }.operate();

    }

    public Map<String, String> hmGetMap(final String key) throws IOException {

        try {
            return new CountOperationFactory<Map<String, String>>(CACHE_RETRY_TIMES, key, true) {
                public Map<String, String> cacheOperate(ShardedJedis jedis) throws IOException {
                    Map<byte[], byte[]> all = jedis.hgetAll(SafeEncoder.encode(key));
                    Map<String, String> map = new HashMap<String, String>();
                    for (byte[] key : all.keySet()) {
                        String field = (String) SerializerUtils.read(key);
                        String value = String.valueOf(
                                SerializerUtils.read(all.get(key)));
                        map.put(field, value);
                    }

                    return map;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling hmGetMap, key: " + key, e);
            return null;
        }
    }

    public long hSet(final String key, final String field, final String value) throws IOException {
        if(hExists(key)){
            try {
                return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
                    public Long cacheOperate(ShardedJedis jedis) throws IOException {
                        return jedis.hset(SafeEncoder.encode(key),
                                SerializerUtils.write(field),
                                SerializerUtils.write(value));
                    }
                }.operate();
            } catch (JedisException e) {
                LOG.error("exception occur when calling hSet, key: " + key, e);
                return 0L;
            }
        }
        return 0L;
    }
    
    public long hSet(final String key, final String field, final Object object) throws IOException {
        if(hExists(key)){
            try {
                return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
                    public Long cacheOperate(ShardedJedis jedis) throws IOException {
                        return jedis.hset(SafeEncoder.encode(key),
                                SerializerUtils.write(field),
                                SerializerUtils.write(object));
                    }
                }.operate();
            } catch (JedisException e) {
                LOG.error("exception occur when calling hSet, key: " + key, e);
                return 0L;
            }
        }
        return 0L;
    }
    
    public long hSetCreateKeyIfNotExist(final String key, final String field, final Object object) throws IOException {
            try {
                return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
                    public Long cacheOperate(ShardedJedis jedis) throws IOException {
                        return jedis.hset(SafeEncoder.encode(key),
                                SerializerUtils.write(field),
                                SerializerUtils.write(object));
                    }
                }.operate();
            } catch (JedisException e) {
                LOG.error("exception occur when calling hSet, key: " + key, e);
                return 0L;
            }
    }
    
    public long hDel(final String key, final String field) throws IOException {
        if(hExists(key)){
            try {
                return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
                    public Long cacheOperate(ShardedJedis jedis) throws IOException {
                        return jedis.hdel(SafeEncoder.encode(key),
                                SerializerUtils.write(field));
                    }
                }.operate();
            } catch (JedisException e) {
                LOG.error("exception occur when calling hSet, key: " + key, e);
                return 0L;
            }
        }
        return 0L;
    }

    public long hSetCreateKeyIfNotExists(final String key, final String field, final String value) throws IOException {
        try {
            return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
                public Long cacheOperate(ShardedJedis jedis) throws IOException {
                    return jedis.hset(SafeEncoder.encode(key),
                            SerializerUtils.write(field),
                            SerializerUtils.write(value));
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when hExists a object, key: " + key, e);
            return 0L;
        }
    }

    public String hGet(final String key, final String field) throws IOException {
        try {
            return new CountOperationFactory<String>(CACHE_RETRY_TIMES, key, true) {
                public String cacheOperate(ShardedJedis jedis) throws IOException {
                    return (String) SerializerUtils
                            .read(jedis.hget(SafeEncoder.encode(key),
                                    SerializerUtils.write(field)));
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling hGet, key: " + key, e);
            return null;
        }
    }
    
    
    public Object hGetObject(final String key, final String field) throws IOException {
        try {
            return new CountOperationFactory<Object>(CACHE_RETRY_TIMES, key, true) {
                public Object cacheOperate(ShardedJedis jedis) throws IOException {
                    return  SerializerUtils
                            .read(jedis.hget(SafeEncoder.encode(key),
                                    SerializerUtils.write(field)));
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling hGet, key: " + key, e);
            return null;
        }
    }

    public boolean hExists(final String key) throws IOException {
        try {
            return new CountOperationFactory<Boolean>(CACHE_RETRY_TIMES, key, true) {
                public Boolean cacheOperate(ShardedJedis jedis) throws IOException {
                    long len = jedis.hlen(SafeEncoder.encode(key));
                    if (len > 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling hExists, key: " + key, e);
            return false;
        }
    }

    public long hLen(final String key) throws IOException {
        try {
            return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, true) {
                public Long cacheOperate(ShardedJedis jedis) throws IOException {
                    return jedis.hlen(SafeEncoder.encode(key));
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling hExists, key: " + key, e);
            return 0l;
        }
    }

    protected abstract class CountOperationFactory<T> {
        int count;
        boolean isGet;
        String key;
        String[] keys;

        public CountOperationFactory(int count, String key, boolean isGet) {
            this.count = count;
            this.key = key;
            this.isGet = isGet;
        }

        public CountOperationFactory(int count, String[] keys, boolean isGet) {
            this.count = count;
            this.keys = keys;
            this.isGet = isGet;
        }

        public T operate() throws JedisException, IOException {
            if (count <= 0) {
                throw new JedisException("cache operate count less than 1");
            }

            while (count > 0) {
                try {
                    return jedisTemplate();
                } catch (JedisException e) {
                    LOG.error("cache CountOperation exception, count: " + count, e);
                    count--;
                }
            }
            if (!isGet) {
              //  去掉操作3次失败后，删除缓存的操作.
              //  delete();
            }
            throw new JedisException("cache CountOperation exception count=" + count);
        }

		private T jedisTemplate() throws JedisException, IOException {
            ShardedJedis jedis = null;
            boolean isFailed = false;
            try {
                jedis = pool.getResource();
                return cacheOperate(jedis);
            } catch (JedisException e) {
                if(!(e.getCause() instanceof JedisDataException)) {
                    isFailed = true;
                    if(jedis != null && key != null) {
                        JedisShardInfo shardInfo = jedis.getShardInfo(key);
                        LOG.error("JedisException@{}:{}", shardInfo.getHost(), shardInfo.getPort());
                    }
                }
                throw e;
            } finally {
                if (jedis != null) {
                    if (isFailed) {
                        pool.returnBrokenResource(jedis);
                    } else {
                        pool.returnResource(jedis);
                    }
                }
            }
        }

        public abstract T cacheOperate(ShardedJedis jedis) throws JedisException, IOException;
    }

    public Long total(final String key) throws IOException {
        try {
            return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, true) {
                public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                    Long total = jedis.zcard(SafeEncoder.encode(key));
                    if (total == null) {
                        return 0L;
                    }
                    return total;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling total, key: " + key, e);
            return 0L;
        }
    }

    public Long zRank(final String key, final Object target) throws IOException {
        try {
            return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, true) {
                public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                    return jedis.zrank(SafeEncoder.encode(key),
                            SerializerUtils.write(target));
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling zRanking, key: " + key + ", value:" + target, e);
            return null;
        }
    }

    public Long zrevrank(final String key, final Object target) throws IOException {
        try {
            return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, true) {
                public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                    return jedis.zrevrank(SafeEncoder.encode(key),
                            SerializerUtils.write(target));
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling zrevrank, key: " + key + ", value:" + target.toString(), e);
            return null;
        }
    }

    public Long zrevrankNoExpire(final String key, final Object target) throws IOException {
        try {
            return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, true) {
                public Long cacheOperate(ShardedJedis jedis) throws JedisException, IOException {

                        byte[] keys = SafeEncoder.encode(key);
                        Transaction t = jedis.getShard(key).multi();
                    Response<Long> rankResponse = t.zrevrank(keys,
                            SerializerUtils.write(target));
                    Response<Boolean> existResponse = t.exists(keys);
                    t.exec();

                    if(!existResponse.get()) {
                        return -1L;
                    }else {
                        return rankResponse.get();
                    }

                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling zrevrank, key: " + key + ", value:" + target.toString(), e);
            return null;
        }
    }

    public boolean expire(String key, final int seconds) throws IOException {
        try {
            return new CountOperationFactory<Boolean>(CACHE_RETRY_TIMES, key, true) {
                public Boolean cacheOperate(ShardedJedis jedis) throws IOException {
                    Long expired = jedis.expire(key, seconds);
                    return expired != null;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling expire, key: " + key, e);
            return false;
        }

    }

    public boolean exists(String key) throws IOException {
        try {
            return new CountOperationFactory<Boolean>(2, key, true) {
                public Boolean cacheOperate(ShardedJedis jedis) throws IOException {
                    Long ttl = jedis.ttl(key);
                    if (ttl==-1) {//non-expire key or non-exists key
                        Boolean exists = jedis.exists(key);
                        return exists != null && exists.booleanValue();
                    }else if(ttl < 1){
                        try {
                            delete(key);
                        } catch (Exception e) {
                            LOG.error("delete key error! "+key,e);
                        }
                        return false;
                    }
                    return true;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling exists, key: " + key, e);
            return false;
        }

    }

    public void hmSetForPush(final String key, final Map<Long, Object> map) throws IOException {
        try {
            new CountOperationFactory<String>(CACHE_RETRY_TIMES, key, false) {
                public String cacheOperate(ShardedJedis jedis) throws IOException {
                    if (map == null || map.isEmpty()) {
                        return null;
                    }
                    Map<byte[], byte[]> binaryMap = new HashMap<byte[], byte[]>(map.size());
                    for (Map.Entry<Long, Object> entry : map.entrySet()) {
                        binaryMap.put(SerializerUtils.write(entry.getKey()),
                                SerializerUtils.write(entry.getValue()));
                    }
                    return jedis.hmset(SafeEncoder.encode(key), binaryMap);
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling hmSetForPush, key: " + key, e);
        }
    }

    public long hmDel(final String key, final long mapKey) throws IOException {
        if (key == null || key.trim().length() == 0) {
            throw new JedisException("no key is null or length of o");
        }
        try {
            return new CountOperationFactory<Long>(CACHE_RETRY_TIMES, key, false) {
                public Long cacheOperate(ShardedJedis jedis) throws IOException {
                    return jedis.hdel(SafeEncoder.encode(key),
                            SerializerUtils.write(mapKey));
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling hmDel, key: " + key, e);
            return -1L;
        }
    }

    public boolean setsIsMember(String key,final String member) throws IOException {
        try {
            return new CountOperationFactory<Boolean>(2, key, true) {
                public Boolean cacheOperate(ShardedJedis jedis) throws IOException {
                    Boolean exists = jedis.sismember(key, member);
                    return exists != null && exists.booleanValue();
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling setsIsMember, key: " + key, e);
            return false;
        }
    }
    
    public Long setsAddMember(String key,final String... member) throws IOException {
        try {
            return new CountOperationFactory<Long>(2, key, true) {
                public Long cacheOperate(ShardedJedis jedis) throws IOException {
                    Long added =  jedis.sadd(key, member);
                    return added;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling setsAddMember a object, key: " + key, e);
            return -1L;
        }
    }
    
    public Long setsDelMember(String key, final String... member) throws IOException {
        try {
            return new CountOperationFactory<Long>(2, key, true) {
                public Long cacheOperate(ShardedJedis jedis) throws IOException {
                    Long removed =  jedis.srem(key, member);
                    return removed;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling setsDelMember, key: " + key, e);
            return -1L;
        }
    }
    
    public Set<String> setsAllMember(String key) throws IOException {
        try {
            return new CountOperationFactory<Set<String>>(2, key, true) {
                public Set<String> cacheOperate(ShardedJedis jedis) throws IOException {
                    Set<String> members =  jedis.smembers(key);
                    return members;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling setsAllMember, key: " + key, e);
            return null;
        }
    }

    public long setsCountMember(String key) throws IOException {
        try {
            return new CountOperationFactory<Long>(1, key, true) {
                public Long cacheOperate(ShardedJedis jedis) throws IOException {
                    Long count =  jedis.scard(key);
                    return count;
                }
            }.operate();
        } catch (JedisException e) {
            LOG.error("exception occur when calling setsCountMember, key: " + key, e);
            return 0;
        }
    }
    
	public boolean lock(String key, int seconds) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getPool().getResource();
            
            Jedis jedis = shardedJedis.getShard(key);
            
            if(jedis.exists(key)) {
                return false;
            }
            jedis.watch(key);
            Transaction t = jedis.multi();
            Response<Long> response = t.setnx(key, "0");
            t.expire(key, seconds);
            List<Object> list = t.exec();

            return list != null && response.get() > 0;
        } catch (JedisException e) {
            LOG.error("lock : " + key, e);
        } finally {
            if (shardedJedis != null) {
                getPool().returnResource(shardedJedis);
            }
        }
        return false;
    }
    
	public boolean releaseLock(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = this.getPool().getResource();
            return jedis.del(key) > 0;
        } catch (JedisException e) {
            LOG.error("releaseLock key : " + key, e);
        } finally {
            if (jedis != null) {
                this.getPool().returnResource(jedis);
            }
        }
        return false;
    }

    public Double zIncrby(final String key, final long increment, final Object member) throws IOException {
       return new CountOperationFactory<Double>(CACHE_RETRY_TIMES, key, false) {
            public Double cacheOperate(ShardedJedis jedis) throws JedisException, IOException {
                return jedis.zincrby(SafeEncoder.encode(key), increment,
                        SerializerUtils.write(member));
            }
        }.operate();
    }

}
