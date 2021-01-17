package edu.brandeis.cs12b.pa8;

import java.util.HashMap;
import java.util.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {	
	public Map<String, Vertices> vertices = new HashMap<String,Vertices>();

	/**
	 * Construct a new graph object using the fdp-formatted graph
	 * provided as a string.
	 * 
	 * The format looks like this:
	 * 
	 *  graph g {
	 *  "a" -- "b" [ label = "10" ];
	 *  "b" -- "c" [ label = "20" ];
	 *  }
	 *  
	 *  To represent that vertex A is connected to vertex B with
	 *  a weight of 10, and B is connected to C with weight 20.
	 *
	 * @param fdp the graph to construct
	 */
	public Graph(String fdp) {
		String [] representation = fdp.split("\"");	
		for(int i=1;i<representation.length-1;i+=6) {
			String firstVertexName = representation[i];
			String secondVertexName = representation[i+2] ;
			Integer weight = Integer.parseInt( representation [i+4]);
			if(!this.vertices.containsKey(firstVertexName)) {
				Vertices firstVer = new Vertices(firstVertexName);
				this.vertices.put(firstVertexName,firstVer);
			}
			if(!this.vertices.containsKey(secondVertexName)) {
				Vertices secondVer = new Vertices(secondVertexName);
				this.vertices.put(secondVertexName,secondVer);
			}
			this.vertices.get(firstVertexName).addDirectConnection(secondVertexName, weight);		
			this.vertices.get(secondVertexName).addDirectConnection(firstVertexName, weight);	
		}
	}


	/**
	 * Get the weight of the edge between vertex a and vertex b.
	 * 
	 * @param a one vertex
	 * @param b another
	 * @return the edge weight or -1 if the edge doesn't exist in the graph.
	 */
	public int getEdgeWeight(String a, String b) {
		if(this.vertices.containsKey(a) && this.vertices.containsKey(b)) {
			return this.vertices.get(a).getEdges(b);
		}
		return -1;
	}

	/**
	 * Return a Set of Vertices that are directly connected to vertex v
	 * @param v the vertex
	 * @return A Set that contains all the vertices that are connected to v
	 */
	public Set<String> getNeighbors(String v) {
		return this.vertices.get(v).neighbors;
	}

	/**
	 * Gets the cost of the path (sum of the edge weights)
	 * represented by the passed ArrayList of vertices. 
	 * If the path is invalid, (jumps between non-connected vertices or contains a vertex that doesn't exist)
	 * return -1
	 * @param path a List of vertices
	 * @return the path length
	 */
	public int getPathCost(List<String> path) {
		int pathCost=0;
		for(int i=0; i<path.size()-1;i++) {
			String start = path.get(i);
			String end = path.get(i+1);
			if(this.vertices.get(start).getEdges(end)==null) {
				return -1;
			}
			pathCost += (this.vertices.get(start).getEdges(end));
		}
		return pathCost;		
	}
	
	/**
	 * Returns a Set of all vertices in the graph. 
	 * * @return the Set of vertices.
	 */
	public Set<String> getVertices() {
		return this.vertices.keySet();
	}
	
	/**
	 * Determines if the graph is a tree or not. 
	 * * @return true if the graph is a tree and false otherwise.
	 */
	public boolean isTree() {
		if(this.vertices.isEmpty()) {
			return false;
		}
		for(String vertexOne: this.vertices.keySet()) {
			for(String vertexTwo : this.vertices.keySet()) {
				if(!vertexOne.equals(vertexTwo)) {
					if(!isConnected(vertexOne,vertexTwo)) {
						return false;
					}
				}
			}
		}
		if(hasCircles()) {
			return false;
		}
		return true;
	}
	
	
	public boolean isConnected(String a, String b){
		ArrayList<String> hasBeen = new ArrayList<String>();
		ArrayList<String> search = new ArrayList<String>();
		search.add(a);
		while(!search.isEmpty()) {
			String temp = search.get(0);
			if(this.vertices.get(temp).isDirectlyConnected(b)) {
				return true;
			}
			search.remove(0);
			hasBeen.add(temp);
			for (String v : this.vertices.get(temp).neighbors) {
				if(!hasBeen.contains(v)) {
					search.add(v);
				}
			}
		}
		return false;
}

	
	
	public boolean hasCircles() {
		if(this.vertices.isEmpty()) {
			return false;
		}
		Map.Entry<String,Vertices> entry = vertices.entrySet().iterator().next();
		String a = entry.getKey();
		ArrayList<String> hasBeen = new ArrayList<String>();
		ArrayList<String> search = new ArrayList<String>();
		search.add(a);
		while(!search.isEmpty()) {
			String temp=search.get(0);
			search.remove(0);
			hasBeen.add(temp);
			for (String v : this.vertices.get(temp).neighbors) {
				if(!hasBeen.contains(v)) {
					if(search.contains(v)) {
						return true;
					} else {
						search.add(v);
					}
				}
			}
		}
		return false;
	}	
}
