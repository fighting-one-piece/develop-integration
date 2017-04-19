package org.cisiondata.utils.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstrUtils {
	
	protected static final Logger LOG = LoggerFactory.getLogger(AbstrUtils.class);
	
	protected static Configuration configuration = null;
	
	/** 初始化配置 **/
	static {
		System.setProperty("hadoop.home.dir", "F:/develop/hadoop/hadoop-2.7.2");
		System.setProperty("HADOOP_MAPRED_HOME", "F:/develop/hadoop/hadoop-2.7.2");
		configuration = new Configuration();
		/** 与hbase/conf/hbase-site.xml中hbase.master配置的值相同 */
		configuration.set("hbase.master", "192.168.0.115:60000");
		/** 与hbase/conf/hbase-site.xml中hbase.zookeeper.quorum配置的值相同 */
		configuration.set("hbase.zookeeper.quorum", "192.168.0.115");
		/** 与hbase/conf/hbase-site.xml中hbase.zookeeper.property.clientPort配置的值相同 */
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		//configuration = HBaseConfiguration.create(configuration);
	}

}
