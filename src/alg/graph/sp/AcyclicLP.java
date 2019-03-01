package alg.graph.sp;

/**
 * 无环有向图SP算法可用于求最长路径：将边的权值取负，求出最短路
 * @author yang
 *
 */
public class AcyclicLP implements SP {
	private AcyclicSP sp;
	
	public AcyclicLP(EdgeWeightedDigraph g, int s) {
		// 复制图，将所有权值取负
		EdgeWeightedDigraph gg = new EdgeWeightedDigraph(g.vertexNum());
		gg.setEdgeNum(g.edgeNum());
		for(int i = 0; i < g.vertexNum(); ++ i) {
			for(DirectedEdge e : g.adj(i)) {
				DirectedEdge ee = e.clone();
				ee.w = - ee.w;
				g.addEdge(ee);
			}
		}
		// 求复制图的最短路
		sp = new AcyclicSP(gg, s);
	}

	@Override
	public double distTo(int v) {
		return - sp.distTo(v);
	}

	@Override
	public boolean hasPathTo(int v) {
		return sp.hasPathTo(v);
	}

	@Override
	public Iterable<DirectedEdge> pathTo(int v) {
		return sp.pathTo(v);
	}

}
