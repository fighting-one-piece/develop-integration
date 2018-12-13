package org.cisiondata.utils.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterFactory {

	private final Logger LOG = LoggerFactory.getLogger(JedisClusterFactory.class);

	private Set<HostAndPort> hostAndPorts = null;
	private Integer timeout = null;
	private Integer maxRedirections = null;
	private JedisCluster jedisCluster = null;
	private GenericObjectPoolConfig genericObjectPoolConfig = null;

	public JedisClusterFactory() {
		initJedisClusterParams();
		this.jedisCluster = new JedisCluster(hostAndPorts, timeout, maxRedirections, genericObjectPoolConfig);
	}
	
	private static class JedisClusterFactoryHolder {
		public static JedisClusterFactory INSTANCE = new JedisClusterFactory();
	}

	public static JedisClusterFactory getInstance() {
		return JedisClusterFactoryHolder.INSTANCE;
	}
	
	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}
	
	private void initJedisClusterParams() {
		InputStream in = null;
		try {
			Properties properties = new Properties();
			in = JedisClusterFactory.class.getClassLoader().getResourceAsStream("redis/env.local.properties");
			properties.load(in);
			this.hostAndPorts = parseHostAndPorts(properties.getProperty("spring.redis.cluster.address"));
			this.timeout = Integer.parseInt(properties.getProperty("spring.redis.cluster.timeout"));
			this.maxRedirections = Integer.parseInt(properties.getProperty("spring.redis.cluster.maxRedirections"));
			genericObjectPoolConfig = new GenericObjectPoolConfig();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try {
				if (null != in) in.close();
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
	
	private Set<HostAndPort> parseHostAndPorts(String address) {
		Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
		try {
			if (null == address || "".equals(address)) return hostAndPorts;
			String[] addresses = address.indexOf(",") == -1 ? new String[]{address} : address.split(",");
			for (int i = 0, len = addresses.length; i < len; i++) {
				String clusterAddress = addresses[i];
				String[] ipAndPort = clusterAddress.split(":");
				HostAndPort hostAndPort = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
				hostAndPorts.add(hostAndPort);
			}
		} catch (Exception e) {
			LOG.error("parse redis cluster config failure!", e);
		}
		return hostAndPorts;
	}
	
}
