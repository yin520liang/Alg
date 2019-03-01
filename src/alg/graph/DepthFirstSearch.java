package alg.graph;

import java.util.LinkedList;
import java.util.List;

/**
 *  深搜，可用于判断连通
 * @author yang
 *
 */
public class DepthFirstSearch {
	private boolean[] marked;
	private int[] edgeTo;
	
	public DepthFirstSearch(Graph g, int s) {
		marked = new boolean[g.verticeNum()];
		edgeTo = new int[g.verticeNum()];
		dfs(g, s);
	}
	
	public void dfs(Graph g, int s) {
		marked[s] = true;
		edgeTo[s] = -1;
		for(int v : g.adj(s)) {
			if(! marked[v]) {
				edgeTo[v] = s;
				dfs(g, v);
			}
		}	
	}	
	
	public boolean hasPathTo(int v) {
		return marked[v];
	}
	
	public Iterable<Integer> pathTo(int v) {
		List<Integer> path = new LinkedList<>( );
		for(; v != -1; v = edgeTo[v])
			path.add(0, v);
		return path;
	}

}
