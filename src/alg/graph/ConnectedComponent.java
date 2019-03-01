package alg.graph;

import java.util.Arrays;

/**
 * 构造函数接收一个图对象作为参数，计算连通分量的数量
 * @author yang
 *
 */
public class ConnectedComponent {
	private int[] id; // 每个顶点所在的连通分量的编号（从0开始）
	private boolean[] marked;
	private int count;
	
	public ConnectedComponent(Graph g) {
		id = new int[g.verticeNum()];
		Arrays.fill(id, -1);
		marked = new boolean[g.verticeNum()];
		for(int i = 0; i < g.verticeNum(); ++ i) {
			if(! marked[i]) {
				dfs(g, i);
				++ count;
			}
		}
	}
	
	private void dfs(Graph g, int s) {
		id[s] = count;
		marked[s] = true;
		for(int v : g.adj(s)) {
			if(! marked[v])
				dfs(g, v);
		}		
	}

	public boolean connected(int v, int u) {
		return id[v] == id[u];
	}
	
	public int id(int v) {
		return id[v];
	}
	
	public int count() {
		return count;
	}

}
