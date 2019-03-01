package alg.search;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.util.ResourceUtils;

import alg.graph.AdjTableGraph;
import alg.graph.Graph;
import alg.graph.Graph.GraphInputType;

/**
 * 算法第一章1.5节union-find算法三种实现
 * <p>
 * 可用于计算无向图连通分量+判断成环等：成环判断只需要在union中特别注意pRoot=qRoot的情况，说明两个节点已经连通，还有一条直连边，即为有环.
 * <br>
 * 环的判断可用于kruskal算法
 *
 */
public abstract class UnionFind {
	protected int[] id;
	protected int count;

	UnionFind(int n) {
		count = n;
		id = new int[n];
		for (int i = 0; i < n; ++i)
			id[i] = i;
	}

	public int count() {
		return count;
	}

	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	public abstract int find(int p);

	public abstract void union(int p, int q);

	public static void main(String[] args) {
//		try (Scanner in = new Scanner(new FileInputStream(ResourceUtils.getFile("resources/union-find.txt")))) {
//			int n = in.nextInt();
//			int edges = in.nextInt();
//
//			UF uf = new WeightedQuickUnion(n);
//			for (int i = 0; i < edges; ++i) {
//				uf.union(in.nextInt(), in.nextInt());
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		try (FileInputStream fis = new FileInputStream(ResourceUtils.getFile("resources/graph-set.txt"))) {
			Graph g = new AdjTableGraph(fis, GraphInputType.EDGE_SET);
			// find circle
			boolean hasCircle = testCircleWithUF(g);
			System.out.println(hasCircle);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean testCircleWithUF(Graph g) {
		UnionFind uf = new WeightedQuickUnion(g.verticeNum());
		for(int u = 0; u < g.verticeNum(); ++ u) {
			for(int v : g.adj(u)) {
				if(uf.find(u) == uf.find(v)) {
					return true;
				} else {
					uf.union(v, u);
				}
			}
		}
		return false;
	}
}

