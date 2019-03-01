package alg.graph;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.util.ResourceUtils;

import alg.graph.Graph.GraphInputType;
import static java.lang.System.out;

public class GraphMain {

	public static void main(String[] args) {
		try (FileInputStream fis = new FileInputStream(ResourceUtils.getFile("resources/graph-adj-mst.txt"))) {
//			Graph g = new AdjTableGraph(fis, GraphInputType.EDGE_SET);
			// 1. BFS
//			testBFS(g);
			// 2. min circle
//			testMinCircle(g);
			EdgeWeightedGraph g = new EdgeWeightedGraph(fis);
			testKruskalMST(g);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Kruskal算法
	 * @param g
	 */
	private static void testKruskalMST(EdgeWeightedGraph g) {
		KruskalMST mst = new KruskalMST(g);
		for(Edge e : mst.edges()) {
			out.println(e);
		}
		out.println(mst.weight());
	}


	/**
	 * 计算最小环
	 * @param g
	 */
	private static void testMinCircle(Graph g) {
		MinCircle mc = new MinCircle(g);
		if(mc.hasCircle()) {
			out.printf("Min circle length is %d\n", mc.minCircle());
		} else {
			out.printf("Graph does not has circle", mc.minCircle());
		}
	}

	/**
	 * BFS
	 * @param g
	 */
	private static void testBFS(Graph g) {
		int s = 0;
		BreadthFirstSearch bfs = new BreadthFirstSearch(g, s);

		out.println("Paths from source " + s);
		for (int i = 0; i < g.verticeNum(); ++i) {
			out.printf("\t%d to %d: %s\n", s, i, bfs.pathTo(i).toString());
		}
	}
}
