package alg.graph.sp;
/**
 * Bellman-Ford算法应用：套汇计算
 * 给定V种货币，每两种货币直接有兑换汇率（双向，即完全图），找到是否有一种套汇方案 <br>
 * 套汇：沿着x->y->z->x的环形路径，可用单位数量的x兑换到超过单位数量的x，产生利润(权值之积大于1) <br>
 * 汇率取自然对数后取负，即可转换为找负权重环的问题：求xyz最大 等价于求 -(lnx + lny + lnz)最小
 * @author yang
 *
 */
public class Arbitrage {
	private String[] currency;
	private Iterable<DirectedEdge> cycle;	
	public BellmanFordSP sp;
	
	
	public Arbitrage() {
		
	}

}
