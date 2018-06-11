/**
 * 
 */
package alg;

/**
 * @title
 * @description 线段树
 * @author lvzhaoyang
 * @date 2017年8月4日上午11:26:36
 */
public class LineTree {

	private LineTreeNode root;
	private int size;
	private int[] rawData;

	public LineTree() {
		size = 0;
	}

	public void create(int[] data) {
		size = data.length;
		if (size > 0) {
			rawData = new int[size];
			System.arraycopy(data, 0, rawData, 0, size);

			root = _create(0, size - 1);
		}
	}

	private LineTreeNode _create(int start, int end) {
		if (start > end)
			return null;
		if (start == end) {
			LineTreeNode node = new LineTreeNode(rawData[start]);
			node.startIndex = node.endIndex = start;
			return node;
		}

		int maxIndex = _max(start, end);
		LineTreeNode subroot = new LineTreeNode(rawData[maxIndex]);
		subroot.startIndex = start;
		subroot.endIndex = end;

		int mid = (start + end) / 2;
		subroot.lchild = _create(start, mid);
		subroot.rchild = _create(mid + 1, end);
		return subroot;
	}

	private int _max(int start, int end) {
		int index = start;
		for (int i = start + 1; i <= end; ++i) {
			if (rawData[i] > rawData[index]) {
				index = i;
			}
		}
		return index;
	}

	public int maxBetween(int start, int end) {
		if (_checkRange(start, end)) {
			return _maxBetween(start, end, root);
		}
		return -1;
	}

	private int _maxBetween(int start, int end, LineTreeNode subroot) {
		if (subroot == null)
			return -1;
		_pushDelay(subroot);
		int mid = (subroot.startIndex + subroot.endIndex) / 2;
		if (start == subroot.startIndex && end == subroot.endIndex) {
			return subroot.x;
		} else if (end <= mid) {
			return _maxBetween(start, end, subroot.lchild);
		} else if (start > mid) {
			return _maxBetween(start, end, subroot.rchild);
		} else {
			return Math.max(_maxBetween(start, mid, subroot.lchild), _maxBetween(mid + 1, end, subroot.rchild));
		}

	}

	private void _pushDelay(LineTreeNode subroot) {
		if (subroot.delay != 0) {
			subroot.x += subroot.delay;
			if (subroot.lchild != null)
				subroot.lchild.delay += subroot.delay;
			if (subroot.rchild != null)
				subroot.rchild.delay += subroot.delay;
			subroot.clearDelay();
		}

	}

	public void increase(int start, int end, int val) {
		if (_checkRange(start, end)) {
			_updateDelay(start, end, root, val);
		}
	}

	private int _updateDelay(int start, int end, LineTreeNode subroot, int val) {
		if (subroot == null)
			return -1;
		_pushDelay(subroot);
		int mid = (subroot.startIndex + subroot.endIndex) / 2;
		int max = subroot.x;
		if (start == subroot.startIndex && end == subroot.endIndex) {
			subroot.delay += val;
		} else if (end <= mid) {
			max = _updateDelay(start, end, subroot.lchild, val);
		} else if (start > mid) {
			max = _updateDelay(start, end, subroot.rchild, val);
		} else {
			max = Math.max(_updateDelay(start, mid, subroot.lchild, val),
					_updateDelay(mid + 1, end, subroot.rchild, val));
		}
		if (max > subroot.x) {
			subroot.x = max;
		}
		return (subroot.x + subroot.delay);

	}

	private boolean _checkRange(int start, int end) {
		if (start < 0 || end >= size || start > end)
			return false;
		return true;
	}

	/**
	 * 
	 * @title
	 * @description
	 * @author lvzhaoyang
	 * @date 2017年8月4日下午2:43:53
	 */
	private class LineTreeNode {
		int x;
		int startIndex;
		int endIndex;
		int delay = 0;
		LineTreeNode lchild;
		LineTreeNode rchild;

		LineTreeNode(int x) {
			this.x = x;
		}

		void clearDelay() {
			delay = 0;
		}

	}

	public static void main(String[] args) {
		int[] a = { 3, 2, 6, 5, 1, 9 };
		LineTree tree = new LineTree();
		tree.create(a);
		//
		System.out.println(tree.maxBetween(0, 4));
		tree.increase(0, 2, 2);
		tree.increase(0, 1, 4);
		System.out.println(tree.maxBetween(0, 2));
	}
}
