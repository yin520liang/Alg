package alg.graph;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *  邻接矩阵实现的图
 * @author yang
 *
 */
public class AdjMatrixGraph implements Graph {
	
	private int[][] edge;
	private int vertexN;
	private int edgeN;
	
	public AdjMatrixGraph(int n) {
		this.vertexN = n;
		edge = new int[n][n];
	}
	
	public AdjMatrixGraph(InputStream is) {
		Scanner scanner = new Scanner(is);
		buildFromInput(scanner);
		scanner.close();
	}
	

	private void buildFromInput(Scanner scanner) {
		vertexN = scanner.nextInt();
		edgeN = scanner.nextInt();
		edge = new int[vertexN][vertexN];
		for(int i = 0; i < edgeN; ++ i) {
			int u = scanner.nextInt();
			int v = scanner.nextInt();
			edge[u][v] = edge[v][u] = 1;
		}
	}

	@Override
	public int verticeNum() {
		return vertexN;
	}

	@Override
	public int edgeNum() {
		return edgeN;
	}

	@Override
	public void addEdge(int v, int w) {
		edge[v][w] = edge[w][v] = 1;
		edgeN ++;
	}

	@Override
	public Iterable<Integer> adj(int v) {
		List<Integer> list = new ArrayList<> (vertexN);
		for(int u : edge[v]) {
			list.add(u);
		}
		return list;
	}

}
