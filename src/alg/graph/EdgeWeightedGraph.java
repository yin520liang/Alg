package alg.graph;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 加权无向图
 * @author yang
 *
 */
public class EdgeWeightedGraph {
	private int vertexNum;
	private int edgeNum;
	private List<Edge>[] edges;

	public EdgeWeightedGraph(int v) {
		vertexNum = v;
		edges = new List[v];
		for(int i = 0; i < v; ++ i)
			edges[i] = new LinkedList<Edge>();
	}
	
	public EdgeWeightedGraph(InputStream in) {
		try(Scanner scanner = new Scanner(in)) {			
			vertexNum = scanner.nextInt();
			edgeNum = scanner.nextInt();
			edges = new List[vertexNum];
			for(int i = 0; i < vertexNum; ++ i)
				edges[i] = new LinkedList<Edge>();
			for(int i = 0; i < edgeNum; ++ i) {
				int v = scanner.nextInt();
				int u = scanner.nextInt();
				double w = scanner.nextDouble();
				addEdge(new Edge(v, u, w));
			}
		}
	}
	
	public int vertexNum() {
		return vertexNum;
	}
	
	public int edgeNum() {
		return edgeNum;
	}
	
	public void addEdge(Edge e) {
		int v = e.either();
		edges[v].add(e);
		edges[e.other(v)].add(e);
	}
	
	public Iterable<Edge> adj(int v) {
		return edges[v];
	}
	
	public Iterable<Edge> edges() {
		List<Edge> all = new ArrayList<>(edgeNum);
		for(int i = 0; i < vertexNum; ++ i)
			all.addAll(edges[i]);
		return all;
	}

}

class Edge implements Comparable<Edge> {
	int v;
	int u;
	double w;
	
	Edge(int v, int u, double w) {
		this.v = v;
		this.u = u;
		this.w = w;
	}
	
	public int either() { return v; }
	
	public double weight() { return w; }
	
	public int other(int a) {
		if(a == v) return u;
		else if(a == u) return v;
		throw new RuntimeException("Not an endpoint of this edge.");
	}

	@Override
	public int compareTo(Edge o) {
		return (w > o.w) ? 1 : (w < o.w) ? -1 : 0;
	}
	
	public String toString() {
		return String.format("(%d, %d) - %.2f", v, u, w);
	}
	
}