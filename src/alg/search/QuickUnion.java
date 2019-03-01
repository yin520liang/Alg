package alg.search;

/**
 * union方法只需O(1)，find方法可能需要O(n)
 *
 */
public class QuickUnion extends UnionFind {

	public QuickUnion(int n) {
		super(n);
	}

	@Override
	public int find(int p) {
		while (p != id[p])
			p = id[p];
		return p;
	}

	@Override
	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if (pRoot != qRoot) {
			id[pRoot] = qRoot;
			--count;
		}
	}
}
