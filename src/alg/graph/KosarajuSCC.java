package alg.graph;

/**
 * 求有向图强联通分支的算法Kosaraju.
 * <ul>
 * 过程：
 * <li>求图G的反向图G'，对G'进行后逆序遍历(DepthFirstOrder)
 * <li>根据逆后序遍历的顺序对G进行DFS，每次递归调用访问的到的节点都在一个强联通分支里
 * 
 * <p>
 * Kosaraju算法正确性证明：<br>
 * 令G'表示G的反向图 <br>
 * 即证明在G上dfs(G,s)所到达的顶点构成一个强联通分支 <br>
 * 即证明:1) dfs(G,s)所到达的顶点和s都是强联通的；2)所有和s强联通的顶点都会在dfs(G,s)中访问到 <br>
 * 
 * 证明2)： <br>
 * 反证，假设存在和s强联通的一个点v没有在dfs(G,s)中访问到，根据dfs的性质(所有s可达的点都能在dfs中遍历到)，只有一种可能，即v在调用dfs(G,s)之前已经被标记访问；
 * <br>
 * 而又因为s和v是强联通的，即v可达s，在v被访问的调用中s也会被标记访问，dfs(G,s)就不会被调用，和题设矛盾； <br>
 * 证明1)： <br>
 * 设v为dfs(G,s)中访问到的一个点，即s可达v，则G'中v可达s； <br>
 * 现只需证明G中v可达s，等价于证明G'中s可达v； <br>
 * v在dfs(G,s)调用中被访问意味着在逆后序顺序中v的位置比s靠后（也可说v有更大的逆后序编号），即在G'执行dfs过程时dfs(G,v)先于dfs(G,s)结束，现考虑G'的执行过程，有两种可能：
 * <br>
 * 1. dfs(G,v)在dfs(G,s)开始调用前结束，由于G'中v可达s，故不可能； <br>
 * 2. dfs(G,v)在dfs(G,s)开始调用后开始，并在dfs(G,s)调用结束前结束，说明dfs(G,s)访问到v，即s可达v <br>
 * 即证明G中v可达s，s和v强联通
 * 
 * @author yang
 *
 */
public class KosarajuSCC {
	private boolean[] marked;
	private int[] id; // 标识节点属于哪个连通分量
	private int count; // 连通分量个数

	public KosarajuSCC(DiGraph g) {
		marked = new boolean[g.verticeNum()];
		id = new int[g.verticeNum()];
		DepthFirstOrder order = new DepthFirstOrder(g);
		for (int i : order.reversePost()) {
			if (!marked[i]) {
				dfs(i, g);
				count++;
			}
		}
	}

	private void dfs(int s, DiGraph g) {
		marked[s] = true;
		id[s] = count;
		for (int v : g.adj(s)) {
			if (!marked[v])
				dfs(v, g);
		}
	}

	public boolean stronglyConnected(int u, int v) {
		return id[v] == id[u];
	}

	public int count() {
		return count;
	}

	public int id(int v) {
		return id[v];
	}

}
