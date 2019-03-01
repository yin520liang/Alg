package alg.graph.sp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *  加权有向图
 *
 */
public class EdgeWeightedDigraph {	
	private int vertexNum;
	private int edgeNum;
	private List<DirectedEdge>[] edges;
	
	public EdgeWeightedDigraph(int v) {
		vertexNum = v;
		edges = new List[v];
		for(int i = 0; i < v; ++ i)
			edges[i] = new LinkedList<>();
	}
	
	public EdgeWeightedDigraph(InputStream in) {
		try(Scanner scanner = new Scanner(in)) {			
			vertexNum = scanner.nextInt();
			edgeNum = scanner.nextInt();
			edges = new List[vertexNum];
			for(int i = 0; i < vertexNum; ++ i)
				edges[i] = new LinkedList<>();
			for(int i = 0; i < edgeNum; ++ i) {
				int from = scanner.nextInt();
				int to = scanner.nextInt();
				double w = scanner.nextDouble();
				addEdge(new DirectedEdge(from, to, w));
			}
		}
	}
	
	public int vertexNum() {
		return vertexNum;
	}
	
	public int edgeNum() {
		return edgeNum;
	}
	
	public void setEdgeNum(int edgeNum) {
		this.edgeNum = edgeNum;
	}
	
	public void addEdge(DirectedEdge e) {
		edges[e.from()].add(e);
	}
	
	public Iterable<DirectedEdge> adj(int v) {
		return edges[v];
	}
	
	public Iterable<DirectedEdge> edges() {
		List<DirectedEdge> all = new ArrayList<>(edgeNum);
		for(int i = 0; i < vertexNum; ++ i)
			all.addAll(edges[i]);
		return all;
	}

}

class DirectedEdge implements Comparable<DirectedEdge>, Cloneable {
	int u; // u -> v
	int v;
	double w;
	
	DirectedEdge(int from, int to, double w) {
		this.v = to;
		this.u = from;
		this.w = w;
	}
	
	public int from() { return u; }
	
	public int to() { return v; }
	
	public double weight() { return w; }

	@Override
	public int compareTo(DirectedEdge o) {
		return (w > o.w) ? 1 : (w < o.w) ? -1 : 0;
	}
	
	public String toString() {
		return String.format("<%d, %d> - %.2f", u, v, w);
	}
	
	public DirectedEdge clone() {
		return new DirectedEdge(this.u, this.v, this.w);
	}
	
}