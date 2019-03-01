package alg.graph;

import java.io.InputStream;

/**
 * 邻接表实现的有向图
 * @author yang
 *
 */
public class AdjTableDiGraph extends AdjTableGraph implements DiGraph {
	
	public AdjTableDiGraph(int n) {
		super(n);
	}
	
	public AdjTableDiGraph(InputStream is, GraphInputType inputType) {
		super(is, inputType);
	}

	@Override
	public void addEdge(int v, int w) {
		table[v].add(w);
		++ edgeN;
	}

	@Override
	public DiGraph reverse() {
		DiGraph dg = new AdjTableDiGraph(vertexN);
		for(int v = 0; v < vertexN; ++ v) {
			for(int u : table[v]) {
				addEdge(u, v);
			}
		}
		return dg;
	}

}
