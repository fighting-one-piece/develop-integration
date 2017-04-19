package org.cisiondata.modules.titan;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

import com.thinkaurelius.titan.core.TitanGraph;

public class QQRelationGraph {
	
	public void buildSchema(TitanGraph graph) {
		TitanUtils.getInstance().buildMixedIndexForVertexProperty("qqnode", "qqNum", String.class);
		TitanUtils.getInstance().buildMixedIndexForVertexProperty("qqnode", "nickname", String.class);
		TitanUtils.getInstance().buildMixedIndexForVertexProperty("qqnode", "age", Integer.class);
		TitanUtils.getInstance().buildMixedIndexForVertexProperty("qqnode", "gender", Integer.class);
		TitanUtils.getInstance().buildMixedIndexForVertexProperty("qqqunnode", "qunNum", String.class);
		TitanUtils.getInstance().buildMixedIndexForVertexProperty("qqqunnode", "name", String.class);
		TitanUtils.getInstance().buildMixedIndexForVertexProperty("qqqunnode", "personNum", Integer.class);
		TitanUtils.getInstance().buildMixedIndexForVertexProperty("qqqunnode", "createDate", Date.class);
	    
		TitanUtils.getInstance().buildEdgeLabel("included");
		TitanUtils.getInstance().buildEdgeLabel("including");
	}
	
	public void loadNode(TitanGraph graph) {
	    Vertex vertex01 = graph.addVertex("qq");
	    vertex01.property("qqNum", "412345678");
	    vertex01.property("nickname", "张三01");
	    vertex01.property("age", 22);
	    vertex01.property("gender", 1);
	    System.out.println(vertex01.id());
	  
	    Vertex vertex02 = graph.addVertex("qq");
	    vertex02.property("qqNum", "422345678");
	    vertex02.property("nickname", "张三02");
	    vertex02.property("age", 24);
	    vertex02.property("gender", 0);
	    System.out.println(vertex01.id());
	    
	    Vertex vertex03 = graph.addVertex("qq");
	    vertex03.property("qqNum", "432345678");
	    vertex03.property("nickname", "张三03");
	    vertex03.property("age", 26);
	    vertex03.property("gender", 0);
	    System.out.println(vertex01.id());
	    
	    Vertex vertex101 = graph.addVertex("qqqun");
	    vertex101.property("qunNum", "345678");
	    vertex101.property("name", "技术交流01");
	    vertex101.property("personNum", 24);
		vertex101.property("createDate", new Date());
		System.out.println(vertex01.id());
	    
		vertex101.addEdge("including", vertex01);
		vertex101.addEdge("including", vertex02);
		vertex101.addEdge("including", vertex03);
		
		vertex01.addEdge("included", vertex101);
		vertex02.addEdge("included", vertex101);
		vertex03.addEdge("included", vertex101);
		
	    graph.tx().commit();
	}
	
	public void queryNode(TitanGraph graph) {
	    try {
	        GraphTraversal<Vertex, Vertex> gt = graph.traversal().V().has("qqNum", "422345678");
	        while (gt.hasNext()) {
				Vertex vertex = gt.next();
				System.out.println("vertex label: " + vertex.label());
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
	        System.out.println("qqnum query finish!");
	        List<Vertex> verties = graph.traversal().V().toList();
	        for (Vertex vertex : verties) {
	        	System.out.println("vertex label: " + vertex.label());
				Iterator<VertexProperty<Object>> vertexProperties = vertex.properties();
				while (vertexProperties.hasNext()) {
					VertexProperty<Object> vp = vertexProperties.next();
					System.out.println(vp.key() + ":" + vp.value());
				}
				System.out.println("All$$$$$$All");
	        }
	        Vertex vertex1 = graph.traversal().V().has("qunNum", "345678").next();
	        System.out.println("qun name: " +vertex1.value("name").toString());
	        System.out.println("qun date: " + vertex1.value("createDate").toString());
	    } catch (NoSuchElementException e) {
	        e.printStackTrace();
	    } finally {
	        graph.tx().close();
	    }
	}
	
	public static void main(String[] args) {
		TitanGraph graph = TitanUtils.getInstance().getGraph();
		QQRelationGraph qqRelationGraph = new QQRelationGraph();
//		qqRelationGraph.buildSchema(graph);
		qqRelationGraph.loadNode(graph);
		qqRelationGraph.queryNode(graph);
		graph.close();
	}
	
}
