package alg.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 广搜
 * 
 * @author yang
 *
 */
public class BreadthFirstSearch {
	private boolean[] marked;
	private int[] edgeTo;
	private int s;

	public BreadthFirstSearch(Graph g, int s) {
		this.s = s;
		marked = new boolean[g.verticeNum()];
		edgeTo = new int[g.verticeNum()];
		bfs(g, s);
	}

	private void bfs(Graph g, int s) {
		Queue<Integer> queue = new LinkedList<>();
		marked[s] = true;
		queue.offer(s);
		edgeTo[s] = -1;
		while (!queue.isEmpty()) {
			int u = queue.poll();
			for (int v : g.adj(u)) {
				if (!marked[v]) {
					marked[v] = true;
					queue.offer(v);
					edgeTo[v] = u;
				}
			}
		}
	}

	public boolean hasPathTo(int v) {
		return marked[v];
	}

	public Iterable<Integer> pathTo(int v) {
		List<Integer> path = new LinkedList<>();
		for (; v != -1; v = edgeTo[v])
			path.add(0, v);
		return path;
	}
	
	public int distTo(int v) {
		int len = 0;
		for (; v != s; v = edgeTo[v]) ++ len;
		return len;
	}

}
