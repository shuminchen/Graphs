package edu.brandeis.cs12b.pa8;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Edges {
	public Map<String, Integer> edges = new HashMap<String, Integer>();

	public void addEdges(String otherVertex, Integer weight) {
		this.edges.put(otherVertex, weight);
	}
	
	public Integer getEdges(String otherVertex) {
		if (edges.containsKey(otherVertex)) {
			return edges.get(otherVertex);
		}
		else {
			return null;
		}
	}
	
	
	
}