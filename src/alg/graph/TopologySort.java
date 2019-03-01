package alg.graph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 拓扑排序. <br>
 * 节点进行线性排序，保留节点间的偏序关系
 * @author yang
 *
 */
public class TopologySort {

	private boolean[] onStack;
	private boolean[] marked;
	private boolean circle;
	
	private Iterable<Integer> order;
	
	public TopologySort(DiGraph g) {
		onStack = new boolean[g.verticeNum()];
		marked = new boolean[g.verticeNum()];
		findCircle(g);
		if(! circle) {
			DepthFirstOrder dfo = new DepthFirstOrder(g);
			order = dfo.reversePost();
		}
	}
	
	/**
	 * 判断是否有环，有则无解
	 * @return
	 */
	private void findCircle(Graph g) {
		// 利用显式栈的dfs，通过判断当前待访问节点是否已在栈中		
		for(int s = 0; s < g.verticeNum(); ++ s)
			dfs(g, s);
	}

	private void dfs(Graph g, int s) {
		onStack[s] = true;
		marked[s] = true;
		for(int u : g.adj(s)) {
			if(! marked[u]) {
				dfs(g, u);
			} else if(onStack[u]) {
				circle = true;
				return;
			}
		}
		onStack[s] = false;
	}
	
	public boolean hasCircle() {
		return circle;
	}
	
	public Iterable<Integer> order() {
		return order;
	}

}


class DepthFirstOrder {
	private boolean[] marked;
	private Queue<Integer> pre; // 递归调用前加入
	private Queue<Integer> post; // 递归调用后加入
	private Stack<Integer> reversePost; // 递归调用后加入，用栈实现逆序
	
	DepthFirstOrder(DiGraph g) {		
		pre = new LinkedList<>();
		post = new LinkedList<>();
		reversePost = new Stack<>();		
		marked = new boolean[g.verticeNum()];
		for(int s = 0; s < g.verticeNum(); ++ s) {
			dfs(g, s);
		}
	}

	private void dfs(DiGraph g, int s) {
		pre.add(s);
		marked[s] = true;
		for(int v : g.adj(s)) {
			if(! marked[v]) {
				dfs(g, v);
			}
		}
		post.add(s);
		reversePost.push(s);
	}
	
	Iterable<Integer> pre() {
		return pre;
	}
	
	Iterable<Integer> post() {
		return post;
	}
	
	Iterable<Integer> reversePost() {
		return reversePost;
	}
	
}
