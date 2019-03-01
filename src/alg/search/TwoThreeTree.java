package alg.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 2-3树伪实现，每个节点都已经预先开辟好可能用到的最大空间，空间利用率低
 * 	两种类型的节点以及之间的转换使23树实现复杂且需付出的代价大，实际应用较少
 * 
 * @author 
 *
 */
public class TwoThreeTree<K extends Comparable<K>, V> implements SymbolTable<K, V> {

	final static int TWO_NODE = 2;
	final static int THREE_NODE = 3;

	private TTNode<K, V> root;

	private int size = 0; // 存储的键值对的数量，不是节点数

	@Override
	public void put(K key, V val) {
		if (root == null) {
			root = new TTNode<K, V>(TWO_NODE, null, key, val);
			++ size;
			return;
		}
		put(root, key, val);
	}

	public void deleteMin() {
		if(root == null)
			return;
		deleteMin(root);
	}
	
	
	private void deleteMin(TTNode<K, V> x) {
		if(x.ps[0] == null) { // x即为要删除的元素所在节点
			if(x == root && x.num == TWO_NODE) {
				root = null;
				return;
			}
			removeElementAt(x, 0);
			if(x.parent != null)
				siftUp(x.parent.parent, x.parent);
		}else {
			if(x.ps[0].num == TWO_NODE) {
				if(x.ps[1].num > TWO_NODE) { // borrow from brother
					addElementAt(x.ps[0], 1, x.keys[0], x.vals[0]);
					x.ps[0].ps[2] = x.ps[1].ps[0];
					x.ps[1].ps[0].parent = x.ps[0];
					x.setKvAt(0, x.ps[1].keys[0], x.ps[1].vals[0]);
					removeElementAt(x.ps[1], 0);
					
				} else if(x.num == TWO_NODE){ // merge
					TTNode<K, V> left = x.ps[0];
					TTNode<K, V> right = x.ps[1];
					addElementAt(x, 0, left.keys[0], left.vals[0]);
					addElementAt(x, 2, right.keys[0], right.vals[0]);
					x.ps[0] = left.ps[0];
					x.ps[1] = left.ps[1];
					x.ps[2] = right.ps[0];
					x.ps[3] = right.ps[1];
					if(left.ps[0] != null)
						left.ps[0].parent = left.ps[1].parent = x;
					if(right.ps[0] != null)
						right.ps[0].parent = right.ps[1].parent = x;
					
				} else { // move down
					addElementAt(x.ps[1], 0, x.ps[0].keys[0], x.ps[0].vals[0]);
					addElementAt(x.ps[1], 1, x.keys[0], x.vals[0]);
					x.ps[0].parent = null; 
					removeElementAt(x, 0);
				}
				deleteMin(x);
			}else {
				deleteMin(x.ps[0]);
			}
		}
		
	}

	private void removeElementAt(TTNode<K, V> x, int i) {
		for(int j = i; j < 2; ++j) {
			x.keys[j] = x.keys[j + 1];
			x.vals[j] = x.vals[j + 1];
		}
		for(int j = i; j < 3; ++j) {
			x.ps[j] = x.ps[j + 1];
			x.ps[j] = x.ps[j + 1];
		}
		x.num --;
		// reset
		reset(x);
	}

	private void put(TTNode<K, V> p, K key, V val) {
		TTNode<K, V> parent = p.parent;
		// find the node to insert
		while (p != null) {
			if (p.type() == TWO_NODE) {
				int cmp = p.keys[0].compareTo(key);
				if (cmp == 0) { // update
					p.vals[0] = val;
					return;
				}
				parent = p;
				p = (cmp > 0) ? p.ps[0] : p.ps[1];
			} else if (p.type() == THREE_NODE) {
				int cmp1 = p.keys[0].compareTo(key);
				int cmp2 = p.keys[1].compareTo(key);
				if (cmp1 == 0) { // update
					p.vals[0] = val;
					return;
				} else if (cmp2 == 0) { // update
					p.vals[1] = val;
					return;
				} else {
					parent = p;
					p = (cmp1 > 0) ? p.ps[0] : (cmp2 < 0) ? p.ps[2] : p.ps[1];
				}
			}
		}
		// perform insert at p
		p = parent;
		if (p.type() == TWO_NODE) {
			int cmp = key.compareTo(p.keys[0]);
			if (cmp < 0) { // key, keys[0]
				addElementAt(p, 0, key, val);
			} else {
				addElementAt(p, 1, key, val);
			}
		} else if (p.type() == THREE_NODE) {
			int cmp1 = key.compareTo(p.keys[0]);
			int cmp2 = key.compareTo(p.keys[1]);
			if (cmp1 < 0) {
				addElementAt(p, 0, key, val);
			} else if (cmp2 > 0) {
				addElementAt(p, 2, key, val);
			} else {
				addElementAt(p, 1, key, val);
			}
		}
		++ size;
		siftUp(p.parent, p);

	}

	private void siftUp(TTNode<K, V> p, TTNode<K, V> child) {
		if (child.num <= THREE_NODE)
			return;
		// cope with null parent, which mean child is the root
		if (p == null) {
			// new root
			TTNode<K, V> newRoot = new TTNode<>(TWO_NODE, null, child.keys[1], child.vals[1]);
			// left child
			TTNode<K, V> rightChild = newTwoNodeAt(child, 2, newRoot);
			TTNode<K, V> leftChild = newTwoNodeAt(child, 0, newRoot);
			newRoot.ps[0] = leftChild;
			newRoot.ps[1] = rightChild;
			// change root
			root = newRoot;
			child.parent = null; // help gc

		} else {
			TTNode<K, V> rightChild = newTwoNodeAt(child, 2, p);
			TTNode<K, V> leftChild = newTwoNodeAt(child, 0, p);
			if (child == p.ps[0]) {// left child
				addElementAt(p, 0, child.keys[1], child.vals[1]);
				p.ps[0] = leftChild;
				p.ps[1] = rightChild;
			} else if (child == p.ps[1]) { // mid child
				addElementAt(p, 1, child.keys[1], child.vals[1]);
				p.ps[1] = leftChild;
				p.ps[2] = rightChild;
			} else { // right child
				addElementAt(p, 2, child.keys[1], child.vals[1]);
				p.ps[2] = leftChild;
				p.ps[3] = rightChild;
			}
			child.parent = null;
			siftUp(p.parent, p);
		}

	}

	/**
	 * 将值插入节点的i位,
	 * 
	 * @param p
	 * @param i
	 * @param key
	 * @param val
	 */
	private void addElementAt(TTNode<K, V> p, int i, K key, V val) {
		for (int j = 2; j > i; --j) {
			p.keys[j] = p.keys[j - 1];
			p.vals[j] = p.vals[j - 1];
		}
		p.keys[i] = key;
		p.vals[i] = val;
		if (i == 0) {
			p.ps[3] = p.ps[2];
			p.ps[2] = p.ps[1];
			p.ps[1] = p.ps[0];
			p.ps[0] = null;
		} else if (i == 1) {
			p.ps[3] = p.ps[2];
			p.ps[2] = p.ps[1];
		}
		p.num++;
		// reset
		reset(p);
	}
	
	
	private void reset(TTNode<K, V> x) {
		if(x.num == TWO_NODE) {
			x.keys[1] = x.keys[2] = null;
			x.vals[1] = x.vals[2] = null;
			x.ps[2] = x.ps[3] = null;
		} else if(x.num == THREE_NODE){
			x.keys[2] = null;
			x.vals[2] = null;
			x.ps[3] = null;
		}
	}

	/**
	 * 从现有节点复制单个元素创建新的2节点
	 * 
	 * @param proto
	 * @param i
	 * @param parent
	 * @return
	 */
	private TTNode<K, V> newTwoNodeAt(TTNode<K, V> proto, int i, TTNode<K, V> parent) {
		TTNode<K, V> node = new TTNode<>(TWO_NODE, parent);
		node.setKvAt(0, proto.keys[i], proto.vals[i]);
		node.ps[0] = proto.ps[i];
		node.ps[1] = proto.ps[i + 1];
		if(node.ps[0] != null) {
			node.ps[0].parent = node;
		}
		if(node.ps[1] != null) {
			node.ps[1].parent = node;
		}
		return node;
	}

	@Override
	public void delete(K key) {
		// 实在写不动了，太麻烦了
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public V get(K key) {
		return get(root, key);
	}

	private V get(TTNode<K, V> subroot, K key) {
		if (subroot == null)
			return null;
		if (subroot.type() == TWO_NODE) {
			int cmp = subroot.keys[0].compareTo(key);
			return (cmp == 0) ? subroot.vals[0] : (cmp > 0) ? get(subroot.ps[0], key) : get(subroot.ps[1], key);
		} else {
			int cmp1 = subroot.keys[0].compareTo(key);
			int cmp2 = subroot.keys[1].compareTo(key);
			if (cmp1 == 0)
				return subroot.vals[0];
			else if (cmp2 == 0)
				return subroot.vals[1];
			else if (cmp1 > 0)
				return get(subroot.ps[0], key);
			else if (cmp2 < 0)
				return get(subroot.ps[2], key);
			else
				return get(subroot.ps[1], key);
		}
	}

	/**
	 * 中序遍历
	 */
	@Override
	public Iterable<K> keys() {
		List<K> list = new ArrayList<> (size);
		midOrderTranverse(root, list);
		return list;
	}

	private void midOrderTranverse(TTNode<K, V> subroot, List<K> list) {
		if(subroot == null)
			return;
		if(subroot.type() == TWO_NODE) {
			midOrderTranverse(subroot.ps[0], list);
			list.add(subroot.keys[0]);
			midOrderTranverse(subroot.ps[1], list);
		} else {
			midOrderTranverse(subroot.ps[0], list);
			list.add(subroot.keys[0]);
			midOrderTranverse(subroot.ps[1], list);
			list.add(subroot.keys[1]);
			midOrderTranverse(subroot.ps[2], list);
		}		
	}

	public static void main(String[] args) {
		TwoThreeTree<Integer, String> ttt = new TwoThreeTree<>();
		ttt.put(1, "hello");
		ttt.put(2, "world");
		ttt.put(3, "see");
		ttt.put(4, "blue");
		ttt.put(5, "bye");
		ttt.put(6, "goods");
		ttt.put(7, "ant");
		ttt.put(8, "bee");
		ttt.put(9, "flower");
		
		for(int a : ttt.keys()) {
			System.out.print(a);
		}
		System.out.println();
		ttt.deleteMin();
		ttt.deleteMin();
		ttt.deleteMin();
		ttt.deleteMin();
		ttt.deleteMin();
		for(int a : ttt.keys()) {
			System.out.print(a);
		}
		System.out.println();
	}

}

/**
 * 偷懒的节点实现，直接使用一个类型表示2节点和3节点，具体类型通过变量num区分，取值为常量：TWO/THREE
 * @author yang
 *
 * @param <E>
 * @param <T>
 */
class TTNode<E, T> {
	E[] keys;
	T[] vals;
	TTNode<E, T>[] ps;
	TTNode<E, T> parent;
	int num;

	TTNode(int n, TTNode<E, T> p) {
		assert n == 2 || n == 3;
		num = n;
		keys = (E[]) new Comparable[3];
		vals = (T[]) new Object[3];
		ps = new TTNode[4];
		parent = p;
	}

	TTNode(int n, TTNode<E, T> p, E key, T val) {
		this(n, p);
		keys[0] = key;
		vals[0] = val;
	}

	void setKvAt(int i, E k, T v) {
		keys[i] = k;
		vals[i] = v;
	}

	TTNode(int n) {
		this(n, null);
	}

	int type() {
		return num;
	}
}
