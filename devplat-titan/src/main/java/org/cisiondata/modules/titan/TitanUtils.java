package org.cisiondata.modules.titan;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanFactory.Builder;

public class TitanUtils {

	private TitanGraph graph = null;
	
	private TitanUtils() {
		loadTitanGraph();
	}
	
	private void loadTitanGraph() {
		Builder builder = TitanFactory.build();
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
        this.graph = builder.open();
	}
	
	private static class TitanUtilsHolder {
		public static TitanUtils INSTANCE = new TitanUtils();
	}
	
	public static TitanUtils getInstance() {
		return TitanUtilsHolder.INSTANCE;
	}
	
	public TitanGraph getGraph() {
		if (null == graph) loadTitanGraph();
		return graph;
	}
}
