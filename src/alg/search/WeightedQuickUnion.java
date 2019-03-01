package alg.search;


/**
 * 通过记录树的大小使每次都将小树合并到大树中，尽可能降低树高，减少find复杂度，最优
 *
 */
public class WeightedQuickUnion extends QuickUnion {
	private int[] sz;

	public WeightedQuickUnion(int n) {
		super(n);
		sz = new int[n];
		for (int i = 0; i < n; ++i)
			sz[i] = 1;
	}

	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if (pRoot != qRoot) {
			if (sz[pRoot] <= sz[qRoot]) {
				id[pRoot] = qRoot;
				sz[qRoot] += sz[pRoot];
			} else {
				id[qRoot] = pRoot;
				sz[pRoot] += sz[qRoot];
			}
			--count;
		}
	}

}