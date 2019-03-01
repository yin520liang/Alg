package alg.graph.sp;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * 无环有向图SP算法
 * 分析： 复杂度 V+E，额外空间V
 * @author yang
 *
 */
public class AcyclicSP implements SP {
	private int s;
	private double[] distTo;
	private DirectedEdge[] edgeTo;
	
	public AcyclicSP(EdgeWeightedDigraph g, int s) {
		this.s = s;
		distTo = new double[g.vertexNum()];
		for(int i = 0; i < g.vertexNum(); ++ i) {
			distTo[i] = Double.MAX_VALUE;
		}
		distTo[s] = 0.0;
		edgeTo = new DirectedEdge[g.vertexNum()];
		
		// 拓扑排序
		TopologySort topology = new TopologySort(g);
		// 按拓扑排序的顺序访问节点
		for(int v : topology.order) {
			relax(g, v);
		}
		
	}
	
	private void relax(EdgeWeightedDigraph g, int v) {
		for(DirectedEdge e : g.adj(v)) {
			int w = e.to();
			if(distTo[w] > distTo[v] + e.w) {
				distTo[w] = distTo[v] + e.w;
				edgeTo[w] = e;
			}			
		}
		
	}

	@Override
	public double distTo(int v) {
		return distTo[v];
	}

	@Override
	public boolean hasPathTo(int v) {
		return distTo[v] < Double.MAX_VALUE;
	}

	@Override
	public Iterable<DirectedEdge> pathTo(int v) {
		List<DirectedEdge> path = new LinkedList<>( );
		while(v != s) {
			DirectedEdge e = edgeTo[v];
			path.add(0, e);
			v = e.from();
		}
		return path;
	}

	/**
	 * 简约实现，跳过环判断
	 * @author yang
	 *
	 */
	class TopologySort {
		Iterable<Integer> order; 
		
		TopologySort(EdgeWeightedDigraph g) {
			DepthFirstOrder dfo = new DepthFirstOrder(g);
			order = dfo.reversePost();
		}
		
		Iterable<Integer> order() {
			return order;
		}
	}


	class DepthFirstOrder {
		private boolean[] marked;
		private Queue<Integer> pre; // 递归调用前加入
		private Queue<Integer> post; // 递归调用后加入
		private Stack<Integer> reversePost; // 递归调用后加入，用栈实现逆序
		
		DepthFirstOrder(EdgeWeightedDigraph g) {		
			pre = new LinkedList<>();
			post = new LinkedList<>();
			reversePost = new Stack<>();		
			marked = new boolean[g.vertexNum()];
			for(int s = 0; s < g.vertexNum(); ++ s) {
				dfs(g, s);
			}
		}

		private void dfs(EdgeWeightedDigraph g, int s) {
			pre.add(s);
			marked[s] = true;
			for(DirectedEdge e : g.adj(s)) {
				if(! marked[e.to()]) {
					dfs(g, e.to());
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
}


