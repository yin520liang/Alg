package alg.graph.sp;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
/**
 * 按路径最短的顺序松弛节点.
 * 分析：额外空间V， 复杂度ElgV.
 * 问题：只能处理环形权值非负的图，若图无环有更快的算法 - AcyclicSP
 * @author yang
 *
 */
public class DijkstraSP implements SP {
	protected int s;	
	protected double[] distTo;
	protected DirectedEdge[] edgeTo;	
	private PriorityQueue<Index> pq;
	
	private static final Comparator<Index> comparator = 
			(i1, i2) -> (i1.dis > i2.dis) ? 1 : (i1.dis < i2.dis) ? -1 : 0;

	protected DijkstraSP(EdgeWeightedDigraph g, int s) {
		this.s = s;
		distTo = new double[g.vertexNum()];
		for(int i = 0; i < g.vertexNum(); ++ i) {
			distTo[i] = Double.MAX_VALUE;
		}
		distTo[s] = 0.0;
		edgeTo = new DirectedEdge[g.vertexNum()];
		
		pq = new PriorityQueue<>(comparator);
		//
		pq.offer(new Index(s, 0.0));
		while(! pq.isEmpty()) {
			relax(g, pq.poll().v);
		}
	}

	private void relax(EdgeWeightedDigraph g, int v) {
		for(DirectedEdge e : g.adj(v)) {
			int w = e.to();
			if(distTo[w] > distTo[v] + e.w) {
				distTo[w] = distTo[v] + e.w;
				edgeTo[w] = e;
				Index index = new Index(w, distTo[w]);
				if(pq.contains(index)) {
					pq.remove(index);
				}
				pq.offer(index);
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
	 * 用于dijkstra算法的索引对，顶点和当前距离
	 *
	 */
	class Index {
		int v;
		double dis;
		Index(int v, double dis) {
			this.v = v;
			this.dis = dis;
		}
		
		public boolean equals(Object o) {
			if(o instanceof Index) {
				Index i = (Index) o;
				return this.v == i.v;
			}
			return false;
		}
	}
	
}
