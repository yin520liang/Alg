package alg.search;

import java.util.ArrayList;
import java.util.List;

/**
 * AVL树：任意一个节点的左右两个子树高度差不超过1.
 * 平衡操作：左旋右旋
 * 
 * @author yang
 *
 * @param <K>
 * @param <V>
 */
public class AVLTree<K extends Comparable<K>, V> extends AbstractBST<K, V> {

	private AVLNode root;
	
	@Override
	public void put(K key, V val) {
		root = put(root, key, val);
	}
	/**
	 * 以和BST相同的方式插入节点，设置叶节点高度为1（为了区分0），区别在于向上进行树高的更新；更新完成后可能会破坏AVL树的性质，需要通过旋转进行调整
	 * @param x
	 * @param key
	 * @param val
	 * @return
	 */
	private AVLNode put(AVLNode x, K key, V val) {
		if(x == null) {
			return new AVLNode(key, val, 1);
		} else {
			int cmp = key.compareTo(x.key);
			if(cmp == 0) {
				x.val = val;
			} else if(cmp > 0) {
				x.right = put(x.right, key, val);
			} else {
				x.left = put(x.left, key, val);
			}
			x.height = Math.max(height(x.left), height(x.right)) + 1;
			x = balance(x); // 调整
			return x;
		}
	}
	
	private int height(AVLNode x) {
		return (x == null) ? 0 : x.height;
	}

	private AVLNode balance(AVLNode x) {
		int lh = height(x.left);
		int rh = height(x.right);
		if(lh > rh + 1) { // rotate right
			x = rotateRight(x);
		} else if(rh > lh + 1) { // rotate left
			x = rotateLeft(x);
		}
		return x;
	}


	private AVLNode rotateRight(AVLNode x) {
		AVLNode newX = x.left;
		x.left = newX.right;
		newX.right = x;
		x.height = Math.max(height(x.left), height(x.right)) + 1;
		newX.height = Math.max(height(newX.left), height(newX.right)) + 1;
		return newX;
	}

	private AVLNode rotateLeft(AVLNode x) {
		AVLNode newX = x.right;
		x.right = newX.left;
		newX.left = x;
		x.height = Math.max(height(x.left), height(x.right)) + 1;
		newX.height = Math.max(height(newX.left), height(newX.right)) + 1;
		return newX;
	}

	@Override
	public void delete(K key) {
		root = delete(root, key);
	}
	
	
	protected AVLNode delete(AVLNode x, K key) {
		if(x == null) return null;
		int cmp = key.compareTo(x.key);
		if(cmp < 0) {
			x.left = delete(x.left, key);
		} else if(cmp > 0) {
			x.right = delete(x.right, key);	
		} else {
			if(x.left == null) return x.right;
			if(x.right == null) return x.left;
			AVLNode tmp = min(x.right);
			x.right = deleteMin(x.right);
			x.key = tmp.key;
			x.val = tmp.val;
		}
		x.height = Math.max(height(x.left), height(x.right)) + 1;
		x = balance(x); // 调整
		return x;
	}
	
	
	protected AVLNode min(AVLNode x) {
		return (x.left == null) ? x : min(x.left);
	}

	protected AVLNode deleteMin(AVLNode x) {
		if(x.left == null) return x.right;
		x.left = deleteMin(x.left);
		x.height = Math.max(height(x.left), height(x.right)) + 1;
		x = balance(x); // 调整
		return x;
	}

	
	@Override
	public V get(K key) {
		// 同一般的BST
		throw new UnsupportedOperationException();
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
	
	private void midOrderTranverse(AVLNode x, List<K> list) {
		if(x == null)
			return;
		midOrderTranverse(x.left, list);
		list.add(x.key);
		midOrderTranverse(x.right, list);	
	}

	
	
	public static void main(String[] args) {
		AVLTree<Integer, String> avl = new AVLTree<>();
		avl.put(1, "hello");
		avl.put(2, "world");
		avl.put(3, "see");
		avl.put(4, "blue");
		avl.put(5, "bye");
		avl.put(6, "goods");
		avl.put(7, "ant");
		avl.put(8, "bee");
		avl.put(9, "flower");
		
		outputTree(avl);
		
		avl.delete(3);
		avl.delete(4);
		avl.delete(8);
		outputTree(avl);
	}
	
	
	private static void outputTree(AVLTree<Integer, String> t) {
		for(int a : t.keys()) {
			System.out.print(a);
		}
		System.out.println();
	}
	
	class AVLNode {
		K key;
		V val;
		AVLNode left;
		AVLNode right;
		int height;
		
		AVLNode(K k, V v) {
			this(k, v, 0);
		}
		
		AVLNode(K k, V v, int h) {
			key = k;
			val = v;
			height = h;
		}
	}


}
