package org.cisiondata.modules.titan;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanFactory.Builder;
import com.thinkaurelius.titan.core.TitanGraph;

public class GraphUtils {
	
	private Builder builder = null;
	
	private GraphUtils() {
		loadConfiguration();
	}
	
	private void loadConfiguration() {
		builder = TitanFactory.build();
		builder.set("storage.backend", "hbase");
        builder.set("storage.hostname", "host-115");
        builder.set("storage.tablename", "titan");
        builder.set("index.search.backend", "elasticsearch");
        builder.set("index.search.hostname", "host-115");
        builder.set("index.search.port", "9300");
        builder.set("index.search.elasticsearch.interface", "TRANSPORT_CLIENT");
        builder.set("index.search.elasticsearch.cluster-name", "cisiondata");
        builder.set("index.search.elasticsearch.index-name", "titan");
//      builder.set("index.search.directory", "/tmp/titan" + File.separator + "es");  
        builder.set("index.search.elasticsearch.local-mode", false);  
        builder.set("index.search.elasticsearch.client-only", "true");
	}
	
	private static class GraphUtilsHolder {
		public static GraphUtils INSTANCE = new GraphUtils();
	}
	
	public static GraphUtils getInstance() {
		return GraphUtilsHolder.INSTANCE;
	}
	
	public TitanGraph getGraph() {
		return builder.open();
	}
	
}
