package alg.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


/**
 * 即时的Prim算法，在优先队列中保存尽可能少的边：
 * <li>1. 即时删除失效边（当新的节点v加入时，以v为另一顶点的边）
 * <li>2. 只保留每个集合外顶点w到树的最短边
 * 需要使用索引优先队列
 *
 * 分析：额外空间和V成正比；复杂度为ElgV；当图为稀疏图时和延时Prim算法的差距甚微
 */
public class PrimMST implements MST {
	private double[] distTo;
	private Edge[] edgeTo;
	private boolean[] marked;
	private PriorityQueue<Index> pq;
	private static final Comparator<Index> comparator = 
			(i1, i2) -> (i1.w > i2.w) ? 1 : (i1.w < i2.w) ? -1 : 0;

	PrimMST(EdgeWeightedGraph g) {
		distTo = new double[g.vertexNum()];
		for (int i = 0; i < g.vertexNum(); ++i)
			distTo[i] = Double.MAX_VALUE;
		edgeTo = new Edge[g.vertexNum()];
		marked = new boolean[g.vertexNum()];
		pq = new PriorityQueue<>(comparator);

		visit(g, 0);
		while (!pq.isEmpty()) {
			visit(g, pq.poll().v);
		}
	}

	private void visit(EdgeWeightedGraph g, int u) {
		marked[u] = true;
		for (Edge e : g.adj(u)) {
			int w = e.other(u);
			if (marked[w])
				continue;
			if (e.w < distTo[w]) {
				distTo[w] = e.w;
				edgeTo[w] = e;
				Index index = new Index(w, distTo[w]);
				if (pq.contains(index)) // 比较Index中的端点是否一致
					pq.remove(index);
				pq.offer(index);
			}
		}
	}


	@Override
	public double weight() {
		double weight = 0.0;
		for(double w : distTo) {
			if(w < Double.MAX_VALUE) weight += w;
		}
		return weight;
	}

	@Override
	public Iterable<Edge> edges() {
		List<Edge> mst = new ArrayList<>(distTo.length - 1);
		for(Edge e : edgeTo) {
			if(e != null) mst.add(e);
		}
		return mst;
	}
	

	class Index {
		int v;
		double w;

		Index(int v, double w) {
			this.v = v;
			this.w = w;
		}

		public boolean equals(Object o) {
			if (o instanceof Index) {
				Index i = (Index) o;
				return this.v == i.v;
			}
			return false;
		}
	}
}
