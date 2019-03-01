package alg.graph.sp;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * bellman-ford算法. <p>
 * 前提：从起点s无法到达任何负权重环（即可以存在，但起点必须不可达，否则不存在最短路）<br>
 * 过程：重复V轮，每轮以任意顺序松弛所有边 <br>
 * 复杂度：O(V*E)
 * 
 * <p>考虑包含负权重环的任意有向图：
 * <li> 1. 当且仅当起点s到顶点v可达，且s到v的所有有向路径上都不存在负权重环时，s到v的最短路才存在
 * <li> 2. Bellman-Ford算法的正确性:
 * 
 * <p> 改进：每一轮没有必要松弛所有边，只有在上一轮中distTo发生变化的节点的出边才可能改变其他顶点的距离；可以用队列记录这样的节点
 * @author yang
 *
 */
public class BellmanFordSP implements SP {
	private int s;
	private double[] distTo;
	private DirectedEdge[] edgeTo;
	private Queue<Integer> changePoint;
	private boolean[] onQ;
	private int cost = 0;
	private Iterable<DirectedEdge> cycle;
	
	public BellmanFordSP(EdgeWeightedDigraph g, int s) {
		this.s = s;
		distTo = new double[g.vertexNum()];
		distTo[s] = 0.0;
		edgeTo = new DirectedEdge[g.vertexNum()];
		changePoint = new LinkedList<>( );
		changePoint.offer(s);
		onQ = new boolean[g.vertexNum()];
		onQ[s] = true;
		while(! changePoint.isEmpty() && ! hasNegativeCycle()) {
			int v = changePoint.poll();
			for(DirectedEdge e : g.adj(v))
				relax(g, e);
		}
	}
	
	private void relax(EdgeWeightedDigraph g, DirectedEdge e) {
		int w = e.to();
		int v = e.from();
		if(distTo[w] > distTo[v] + e.w) {
			distTo[w] = distTo[v] + e.w;
			edgeTo[w] = e;
			if(! onQ[w]) {
				onQ[w] = true;
				changePoint.offer(w);
			}
		}
		if(cost ++ % g.vertexNum() == 0) { // 每V轮判断一次负权重环
			findNegativeCycle();
		}
	}
	
	/**
	 * 若存在负权重环，其所有节点必然已经在edgeTo数组中，根据edgeTo数组构建图，之后调用有向图成环判断环
	 */
	private void findNegativeCycle() {
		// TODO Auto-generated method stub
		
	}

	public boolean hasNegativeCycle() {
		return cycle != null;
	}
	
	public Iterable<DirectedEdge> negativeCycle() {
		return cycle;
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
	
}
