package edu.brandeis.cs12b.pa8;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Vertices {
	public Edges edges = new Edges();
	public Set<String> neighbors = new HashSet<String>();
	public String name;
	private Vertices previous;
	public int distance;
	
	public Vertices(String name){
		this.name = name;
		this.previous=null;
		this.distance=0;
	}
	
	public void addDirectConnection(String vertex, Integer weight) {
		this.neighbors.add(vertex);
		this.edges.addEdges(vertex, weight);
	}
	
	public Integer getEdges(String vertex) {
		return edges.getEdges(vertex);
	}
	
	public boolean isDirectlyConnected(String vertex) {
		if(neighbors.contains(vertex)) {
			return true;
		}
		return false;
	}
	
	public void setPrevious(Vertices Prev) {
		this.previous=Prev;
	}
	
	public Vertices getPrev() {
		return this.previous;
	}

	

}