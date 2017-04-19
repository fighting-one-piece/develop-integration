package org.cisiondata.modules.titan;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanFactory.Builder;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.schema.Mapping;
import com.thinkaurelius.titan.core.schema.Parameter;
import com.thinkaurelius.titan.core.schema.TitanManagement;

public class TitanUtils {
	
	private Logger LOG = LoggerFactory.getLogger(TitanUtils.class);

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
        builder.set("root.graph.set-vertex-id", "true");
        this.graph = builder.open();
        /**
        this.graph.configuration().setProperty(GraphDatabaseConfiguration.ALLOW_SETTING_VERTEX_ID.toString(), true);
		**/
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
	
	public PropertyKey buildPropertyKey(TitanManagement mgmt, String propertyKeyName, Class<?> propertyType) {
		return mgmt.containsPropertyKey(propertyKeyName) ? mgmt.getPropertyKey(propertyKeyName) :
			mgmt.makePropertyKey(propertyKeyName).dataType(propertyType).make();
	}
	
	public void buildEdgeLabel(String edgeLabel) {
		TitanManagement mgmt = graph.openManagement();
		try {
			if (!mgmt.containsEdgeLabel(edgeLabel)) {
				mgmt.makeEdgeLabel(edgeLabel).make();
			}
			mgmt.commit();
	    } catch (Exception e) {
	    	LOG.error(e.getMessage(), e);
	        mgmt.rollback();
	    } 
	}
	
	public void buildMixedIndexForVertexProperty(String indexName, String propertyKeyName, Class<?> propertyType) {
		TitanManagement mgmt = graph.openManagement();
		try {
	        PropertyKey propertyKey = buildPropertyKey(mgmt, propertyKeyName, propertyType);
	        if (mgmt.containsGraphIndex(indexName)) {
	        	mgmt.addIndexKey(mgmt.getGraphIndex(indexName), propertyKey);
	        } else {
	        	mgmt.buildIndex(indexName, Vertex.class).unique().addKey(propertyKey, 
	        			Parameter.of("mapping", Mapping.STRING)).buildMixedIndex("search");
//	        	mgmt.buildIndex(indexName, Vertex.class).addKey(propertyKey, Mapping.TEXTSTRING.asParameter());
	        }
	        mgmt.commit();
	    } catch (Exception e) {
	    	LOG.error(e.getMessage(), e);
	        mgmt.rollback();
	    } 
	}
	
}
