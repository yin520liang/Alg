package alg.graph;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 邻接表实现的图
 * @author yang
 *
 */
public class AdjTableGraph implements Graph {
	protected List<Integer>[] table;
	protected int vertexN;
	protected int edgeN;
	
	@SuppressWarnings("unchecked")
	public AdjTableGraph(int n) {
		vertexN = n;
		table = new List[n];
		for(int i = 0; i < n; ++ i)
			table[i] = new LinkedList<>( );
	}
	
	public AdjTableGraph(InputStream is, GraphInputType inputType) {
		try(Scanner scanner = new Scanner(is)) {			
			if(inputType == GraphInputType.EDGE_SET) {
				buildFromEdgeSet(scanner);
			} else if (inputType == GraphInputType.EDGE_ADJ){
				buildFromEdgeAdj(scanner);
			}
		}
	}
	
	

	@SuppressWarnings("unchecked")
	protected void buildFromEdgeAdj(Scanner scanner) {
		vertexN = scanner.nextInt();
		edgeN = scanner.nextInt();
		table = new List[vertexN];
		for(int i = 0; i < vertexN; ++ i)
			table[i] = new LinkedList<>( );
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			String[] tokens = line.split("[:\\s]+");
			int s = Integer.parseInt(tokens[0].trim());
			for(int i = 1; i < tokens.length; ++ i) {
				int v = Integer.parseInt(tokens[i].trim());
				addEdge(s, v);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected void buildFromEdgeSet(Scanner scanner) {
		vertexN = scanner.nextInt();
		edgeN = scanner.nextInt();
		table = new List[vertexN];
		for(int i = 0; i < vertexN; ++ i)
			table[i] = new LinkedList<>( );
		while(scanner.hasNext()) {
			int u = scanner.nextInt();
			int v = scanner.nextInt();
			addEdge(u, v);
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
		table[v].add(w);
		table[w].add(v);
		++ edgeN;
	}

	@Override
	public Iterable<Integer> adj(int v) {
		return table[v];
	}

}
