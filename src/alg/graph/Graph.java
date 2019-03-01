package alg.graph;

/**
 * 图操作API
 * 
 * @author yang
 *
 */
public interface Graph {
	int verticeNum();

	int edgeNum();

	void addEdge(int v, int w);

	Iterable<Integer> adj(int v);
	
	enum GraphInputType {
		EDGE_SET,
		EDGE_ADJ;
	}
}
