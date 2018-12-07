/**
 * 
 */
package alg;

import java.util.Arrays;

/**
 * 	单源最短路径算法 <br>
 * 	思想：贪心
 */
public class Dijkstra {	
	private int n;
	private int[][] weight;
	
	public Dijkstra(int n) {
		assert n > 0;
		this.n = n;	
	}
	
	
	public void setWeight(int[][] weight) {
		this.weight = weight;
	}


	public int[] minDistance(int src) {
		assert weight != null && weight.length == n;
		boolean[] visited = new boolean[n];
		int[] dist = new int[n];
		initDistance(dist, src);
		// start
		visited[src] = true;
		// loop for n-1 round
		for(int r = 0; r < n - 1; ++ r) {
			int index = getMinIndex(visited, dist);
			if(index < 0)
				break;
			visited[index] = true;
			for(int j = 0; j < n; ++ j) {
				if(!visited[j] && weight[index][j] > 0) { // <index, v>
					int tmp = dist[index] + weight[index][j];
					if(tmp < dist[j]) {
						dist[j] = tmp;
					}
				}
			}
		}
		return dist;
	}


	private void initDistance(int[] dist, int src) {
		for(int i = 0; i < dist.length; ++ i) {
			if(weight[src][i] >= 0) {
				dist[i] = weight[src][i];
			} else {
				dist[i] = Integer.MAX_VALUE;		
			}
		}
	}
	

	private int getMinIndex(boolean[] visited, int[] dist) {
		int min = Integer.MAX_VALUE;
		int u = -1;
		for(int i = 0; i < n; ++i) {
			if(!visited[i] && dist[i] < min) {
				u = i;
				min = dist[i];
			}
		}
		return u;
	}

	
	public static void main(String[] args) {
		int n = 4;
		int[][] w = {
				{0, 2, 6, 4},
				{-1, 0, 3, -1},
				{7, -1, 0, 1},
				{5, -1, 12, 0}
		};
		Dijkstra dij = new Dijkstra(n);
		dij.setWeight(w);
		int[] dis = dij.minDistance(0);
		System.out.println(Arrays.toString(dis));
	}

}
