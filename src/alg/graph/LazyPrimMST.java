package alg.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 最小生成树 - Prim算法. <p>
 * 所需的额外空间和边数量E成正比（取决于优先队列）；
 * 复杂度为ElgE（优先队列基于堆实现，最多有E个元素）
 *
 */
public class LazyPrimMST implements MST {
	private boolean[] marked;
	private PriorityQueue<Edge> minEdge;
	private List<Edge> mst;
	private double w;
	
	public LazyPrimMST(EdgeWeightedGraph g) {
		marked = new boolean[g.vertexNum()];
		mst = new LinkedList<>( );
		minEdge = new PriorityQueue<>( );
		
		visit(g, 0);
		while(! minEdge.isEmpty()) {
			Edge e = minEdge.poll();
			// 通过将其他边加入可能会导致当前边的两个端点都已在集合中
			if(marked[e.u] && marked[e.v]) continue;
			mst.add(e);
			if(!marked[e.u]) visit(g, e.u);
			if(!marked[e.v]) visit(g, e.v);
		}
	}


	private void visit(EdgeWeightedGraph g, int i) {
		marked[i] = true;
		for(Edge e : g.adj(i)) {
			if(!marked[e.other(i)])
				minEdge.offer(e);
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

