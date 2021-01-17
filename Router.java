package edu.brandeis.cs12b.pa8;

import java.util.*;
import java.util.Map.Entry;
/**
 * The router class takes in a Graph object and then
 * allows the user to find routes between places represented by
 * the graph.
 * 
 */
public class Router {
	private Graph graph;

	/**
	 * Constructs a new Router object with the given graph
	 * @param graph the graph to route on
	 */
	public Router(Graph graph) {
		this.graph = graph;
	}

	/**
	 * Returns an ArrayList over the vertices along a valid path
	 * between vertex "start" and vertex "end" in the graph.
	 * 
	 * If no route exists, or the input is invalid return null.
	 * 
	 * @param start the start position
	 * @param end the desired end position
	 * @return the vertices along a path
	 */
	public ArrayList<String> getRoute(String start, String end) {
		if(!this.graph.vertices.containsKey(end) || !this.graph.vertices.containsKey(start)) {
			return null;
		}
		if(!this.graph.isConnected(start, end)) {
			return null;
		}
		ArrayList<String> route = new ArrayList<String>();
		ArrayList<String> hasBeen = new ArrayList<String>();
		ArrayList<String> search = new ArrayList<String>();
		search.add(start);
		while(!search.isEmpty()) {
			String temp=search.get(0);
			search.remove(0);
			hasBeen.add(temp);
			if(this.graph.vertices.get(temp).isDirectlyConnected(end)) {
				this.graph.vertices.get(end).setPrevious(this.graph.vertices.get(temp));
				break;
			}
			for (String v : this.graph.vertices.get(temp).neighbors) {
				if(!hasBeen.contains(v)) {
					this.graph.vertices.get(v).setPrevious(this.graph.vertices.get(temp));
					search.add(v);
				}
			}
		}
		Vertices temp1 = this.graph.vertices.get(end);
		while(temp1!=null) {
			route.add(temp1.name);
			temp1 = temp1.getPrev();
		}
		Collections.reverse(route);
		return route;
	}
				
	



	/**
	 * Same as the above method, but the route returned
	 * must be a the shortest as described in the assignment.
	 *  
	 * @param start the starting location
	 * @param end the end location
	 * @return the shortest path between start and end
	 */
	
	public Iterable<String> getShortestRoute(String start, String end) {
		if(!this.graph.vertices.containsKey(end) || !this.graph.vertices.containsKey(start)) {
			return null;
		}
		if(!this.graph.isConnected(start, end)) {
			return null;
		}
		ArrayList<String> route = new ArrayList<String>();
		ArrayList<String> hasBeen = new ArrayList<String>();
		myComparator comparator = new myComparator();
		PriorityQueue<Vertices> search = new PriorityQueue<Vertices>(1000,comparator); 
		Iterator<Entry<String, Vertices>> it = this.graph.vertices.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String,Vertices> entry = (Entry<String, Vertices>)it.next();
	        entry.getValue().distance=Integer.MAX_VALUE;
	        search.add(entry.getValue());
	    }
	    this.graph.vertices.get(start).distance=0;
		while(!search.isEmpty()) {
			Vertices temp=search.poll();
			hasBeen.add(temp.name);
			for (String v : temp.neighbors) {
				if(!hasBeen.contains(v)) {
					if(this.graph.vertices.get(v).distance>(temp.distance+this.graph.getEdgeWeight(temp.name, v))) {
						Vertices temp1 = this.graph.vertices.get(v);
						temp1.distance=temp.distance+this.graph.getEdgeWeight(temp.name, v);
						temp1.setPrevious(temp);
						search.remove(this.graph.vertices.get(v));
						search.add(temp1);
					}
				}
			}
		}
		Vertices temp1 = this.graph.vertices.get(end);
		while(temp1!=null) {
			route.add(temp1.name);
			temp1 = temp1.getPrev();
		}
		Collections.reverse(route);
		return route;
	}
	
}
