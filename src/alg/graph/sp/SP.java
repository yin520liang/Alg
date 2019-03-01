package alg.graph.sp;

/**
 * 最短路API.
 * 最短路的最优性条件：distTo[]为从原点s到其他顶点的最短路径当且仅当，对图中任意一条边e=v->w，有distTo[w] <= distTo[v] + weight(e)
 *
 */
public interface SP {
	
	double distTo(int v);
	
	boolean hasPathTo(int v);
	
	Iterable<DirectedEdge> pathTo(int v);
	
	/**
	 * 验证方法
	 */
	default boolean check(EdgeWeightedDigraph g, double[] distTo) {
		for(DirectedEdge e : g.edges()) {
			int from = e.from();
			int to = e.to();
			if(distTo[to] > distTo[from] + e.w)
				return false;
		}
		return true;
	}
	
}
