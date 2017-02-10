package org.cisiondata.modules.titan;

import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.schema.Mapping;
import com.thinkaurelius.titan.core.schema.Parameter;
import com.thinkaurelius.titan.core.schema.TitanManagement;

public class QQRelationGraph {

	public PropertyKey buildPropertyKey(TitanManagement mgmt, String propertyKeyName, Class<?> propertyType) {
		return mgmt.containsPropertyKey(propertyKeyName) ? mgmt.getPropertyKey(propertyKeyName) :
			mgmt.makePropertyKey(propertyKeyName).dataType(propertyType).make();
	}
	
	public void buildMixedIndexForVertexProperty(TitanGraph graph, String indexName, String propertyKeyName, Class<?> propertyType) {
		TitanManagement mgmt = graph.openManagement();
		try {
	        PropertyKey propertyKey = buildPropertyKey(mgmt, propertyKeyName, propertyType);
	        if (mgmt.containsGraphIndex(indexName)) {
	        	mgmt.addIndexKey(mgmt.getGraphIndex(indexName), propertyKey);
	        } else {
	        	mgmt.buildIndex(indexName, Vertex.class).addKey(propertyKey, 
	        			Parameter.of("mapping", Mapping.STRING)).buildMixedIndex("search");
//	        	mgmt.buildIndex(indexName, Vertex.class).addKey(propertyKey, Mapping.TEXTSTRING.asParameter());
	        }
	        mgmt.commit();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        mgmt.rollback();
	    } 
	}
	
	public void buildEdgeLabel(TitanGraph graph, String edgeLabel) {
		TitanManagement mgmt = graph.openManagement();
		try {
			if (!mgmt.containsEdgeLabel(edgeLabel)) {
				mgmt.makeEdgeLabel(edgeLabel).make();
			}
			mgmt.commit();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        mgmt.rollback();
	    } 
	}
	
	public void buildSchema(TitanGraph graph) {
		buildMixedIndexForVertexProperty(graph, "qqnode", "qqNum", String.class);
	    buildMixedIndexForVertexProperty(graph, "qqnode", "nickname", String.class);
	    buildMixedIndexForVertexProperty(graph, "qqnode", "age", Integer.class);
	    buildMixedIndexForVertexProperty(graph, "qqnode", "gender", Integer.class);
	    buildMixedIndexForVertexProperty(graph, "qqqunnode", "qunNum", String.class);
	    buildMixedIndexForVertexProperty(graph, "qqqunnode", "name", String.class);
	    buildMixedIndexForVertexProperty(graph, "qqqunnode", "personNum", Integer.class);
	    buildMixedIndexForVertexProperty(graph, "qqqunnode", "createDate", Date.class);
	    
	    buildEdgeLabel(graph, "included");
	    buildEdgeLabel(graph, "including");
	}
	
	public void loadNode(TitanGraph graph) {
	    Vertex vertex01 = graph.addVertex("qq");
	    vertex01.property("qqNum", "412345678");
	    vertex01.property("nickname", "张三01");
	    vertex01.property("age", 22);
	    vertex01.property("gender", 1);
	  
	    Vertex vertex02 = graph.addVertex("qq");
	    vertex02.property("qqNum", "422345678");
	    vertex02.property("nickname", "张三02");
	    vertex02.property("age", 24);
	    vertex02.property("gender", 0);
	    
	    Vertex vertex03 = graph.addVertex("qqqun");
	    vertex03.property("qunNum", "345678");
	    vertex03.property("name", "技术交流01");
	    vertex03.property("personNum", 24);
		vertex03.property("createDate", new Date());
	    
		vertex03.addEdge("including", vertex01);
		vertex03.addEdge("including", vertex02);
		
		vertex01.addEdge("included", vertex03);
		vertex02.addEdge("included", vertex03);
		
	    graph.tx().commit();
	}
	
	public void queryNode(TitanGraph graph) {
	    try {
	        GraphTraversal<Vertex, Vertex> gt = graph.traversal().V().has("qqNum", 422345678);
	        while (gt.hasNext()) {
				Vertex vertex = gt.next();
				System.out.println(vertex.label());
				Iterator<VertexProperty<Object>> vertexProperties = vertex.properties();
				while (vertexProperties.hasNext()) {
					VertexProperty<Object> vp = vertexProperties.next();
					System.out.println(vp.key() + ":" + vp.value());
				}
				System.out.println("$$$$$$");
				Iterator<Edge> edgeIterator = vertex.edges(Direction.BOTH);
				while (edgeIterator.hasNext()) {
					Edge edge = edgeIterator.next();
					System.out.println(edge.label());
				}
				System.out.println("######");
	        }
	        System.out.println("%%%%%%");
	        Vertex vertex1 = graph.traversal().V().has("qunNum", "345678").next();
	        System.out.println(vertex1.value("name").toString());
	        System.out.println(vertex1.value("createDate").toString());
	    } catch (NoSuchElementException e) {
	        e.printStackTrace();
	    } finally {
	        graph.tx().close();
	    }
	}
	
	public static void main(String[] args) {
		TitanGraph graph = GraphUtils.getInstance().getGraph();
		QQRelationGraph qqRelationGraph = new QQRelationGraph();
//		qqRelationGraph.buildSchema(graph);
//		qqRelationGraph.loadNode(graph);
		qqRelationGraph.queryNode(graph);
		graph.close();
	}
	
}
