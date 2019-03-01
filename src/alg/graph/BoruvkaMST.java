package alg.graph;

import java.util.ArrayList;
import java.util.List;

import alg.search.UnionFind;
import alg.search.WeightedQuickUnion;

/**
 * 最小生成树 - Boruvka算法. 〈br>
 * 初始时，将所有节点视为V个森林，每次将距离最小的两个森林合并.
 * 
 * 
 * @author yang
 *
 */
public class BoruvkaMST implements MST {
	private UnionFind uf; // 用于连通性检测
	private Edge[] closest; // 记录距离i所属子树的最近的邻居对应的边
	private List<Edge> mst;
	private double weight;

	public BoruvkaMST(EdgeWeightedGraph g) {
		uf = new WeightedQuickUnion(g.vertexNum());
		closest = new Edge[g.vertexNum()];
		mst = new ArrayList<>(g.vertexNum());
		build(g);
	}

	private void build(EdgeWeightedGraph g) {
		int count = 0;
		while (count < g.vertexNum() - 1) {
			for (Edge e : g.edges()) {
				int i = uf.find(e.u);
				int j = uf.find(e.v);
				if (i == j)
					continue;
				if (closest[i] == null || e.w < closest[i].w)
					closest[i] = e;
				if (closest[j] == null || e.w < closest[j].w)
					closest[j] = e;
			}
			for (int i = 0; i < g.vertexNum(); i++) {
				Edge e = closest[i];
				if (e != null) {
					int v = e.either(), w = e.other(v);
					if (!uf.connected(v, w)) {
						mst.add(e);
						weight += e.w;
						uf.union(v, w);
						++ count;
					}
				}
			}
		}
	}

	@Override
	public double weight() {
		return weight;
	}

	@Override
	public Iterable<Edge> edges() {
		return mst;
	}

}
