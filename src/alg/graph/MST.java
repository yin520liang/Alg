package alg.graph;
/**
 * 最小生成树API
 * @author yang
 *
 */
public interface MST {

	double weight();
	
	Iterable<Edge> edges();
}
