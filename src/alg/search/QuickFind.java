package alg.search;

/**
 * find方法只需O(1)的时间，而union方法需要O(n); <br>
 * 给定一张连通图，最少需要调用N-1次union，整体复杂度接近O(n^2)
 *
 */
public class QuickFind extends UnionFind {

	public QuickFind(int n) {
		super(n);
	}

	@Override
	public int find(int p) {
		return id[p];
	}

	@Override
	public void union(int p, int q) {
		int pId = find(p);
		int qId = find(q);
		if (pId != qId) {
			for (int i = 0; i < id.length; ++i) {
				if (id[i] == pId) {
					id[i] = qId;
				}
			}
			--count;
		}
	}
}



