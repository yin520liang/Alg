package alg.graph;

import java.util.Arrays;

/**
 * 最小环.
 * <p>借助Floyd算法
 * 
 * @author yang
 *
 */
public class MinCircle {
	private final int MAX_VAL = 10000;
	private int min = MAX_VAL;
	private int[][] d;

	public MinCircle(Graph g) {
		d = new int[g.verticeNum()][g.verticeNum()];
		initDistance(g, MAX_VAL);
		minDistance(g);
	}

	private void initDistance(Graph g, int maxDist) {
		for (int i = 0; i < g.verticeNum(); ++i) {
			Arrays.fill(d[i], maxDist);
			d[i][i] = 0;
		}
		for (int u = 0; u < g.verticeNum(); ++u) {
			for (int v : g.adj(u)) {
				d[u][v] = d[v][u] = 1;
			}
		}
	}

	/**
	 * 无向图的环至少包括三个节点， O(n^3)
	 * @param g
	 */
	protected void minDistance(Graph g) {
		int i, j, k;
		// 计算各对顶点之间的距离
		for (k = 0; k < g.verticeNum(); ++k) {
			for (i = 0; i < g.verticeNum(); i++) {
				for (j = 0; j < g.verticeNum(); j++) {
					int temp = sumUp(d[i][k], d[k][j]);
					if (temp < d[i][j])
						d[i][j] = temp;
				}
			}
		}
		// 找最小环
		for (k = 0; k < g.verticeNum(); ++k) {
			for (i = 0; i < k; ++i) {
				for (j = i + 1; j < k; ++j) {
					int temp = sumUp(d[i][j], d[i][k], d[k][j]);
					if (temp < min) {
						min = temp;
					}
				}
			}
		}

	}
	
	private int sumUp(int ... array) {
		for(int a : array) {
			if(a == MAX_VAL)
				return MAX_VAL;
		}
		int sum = 0;
		for(int a : array) {
			sum += a;
		}
		return sum;
	}

	public boolean hasCircle() {
		return min != MAX_VAL;
	}

	public int minCircle() {
		return min;
	}

}
