package alg.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import alg.search.UnionFind;
import alg.search.WeightedQuickUnion;

/**
 * 最小生成树 - Kruskal算法.
 * 借助优先队列存储所有边，借助union-find算法判断是否成环
 *
 */
public class KruskalMST implements MST {
	private PriorityQueue<Edge> pq;
	private List<Edge> mst;
	private double w;
	private UnionFind uf;
	
	public KruskalMST(EdgeWeightedGraph g) {
		uf = new WeightedQuickUnion(g.vertexNum());
		mst = new ArrayList<>(g.vertexNum());
		pq = new PriorityQueue<>(g.edgeNum());
		for(Edge e : g.edges()) {
			pq.add(e);
		}
		// run for V-1 times
		int count = 0;
		while(count < g.vertexNum() - 1) {
			Edge e = pq.poll();
			if(!uf.connected(e.u, e.v)) {
				mst.add(e);
				w += e.w;
				uf.union(e.u, e.v);
				count ++;
			}
		}
	}

	@Override
	public double weight() {
		return w;
	}

	@Override
	public Iterable<Edge> edges() {
		return mst;
	}

}
