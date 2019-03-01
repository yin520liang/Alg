package alg.search;

import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<K extends Comparable<K>, V> implements SymbolTable<K, V> {
	
	private static final boolean RED = true;
	private static final boolean BLACK = true;
	
	private Node root;
	
	private int size;
	

	@Override
	public void put(K key, V val) {
		root = put(root, key, val);
		root.color = BLACK;		
	}

	private Node put(Node h, K key, V val) {
		if(h == null) 
			return new Node(key, val, RED);
		
		int cmp = key.compareTo(h.key);
		if(cmp == 0) {
			h.val = val;
		} else if(cmp > 0) {
			h.right = put(h.right, key, val);
		} else {
			h.left = put(h.left, key, val);
		}
		// adjust
		if(isRed(h.right) && !isRed(h.left)) 
			h = rotateLeft(h);
		// 此处h.left若为null则一定是黑色，故第一个判断就会返回false，根据短路原理不会再执行第二个判断，不会报错
		if(isRed(h.left) && isRed(h.left.left)) 
			h = rotateRight(h);
		if(isRed(h.left) && isRed(h.right)) 
			flipColor(h);
		return h;
	}
	

	@Override
	public void delete(K key) {
		if(root == null)
			return;
		if(!isRed(root.left) && !isRed(root.right))
			root.color = RED;
		root = delete(root, key);
		root.color = BLACK;
	}

	
	private Node delete(Node x, K key) {
		if(x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if(cmp < 0) {
			if(! isRed(x.left) && ! isRed(x.left.left)) {
				x = moveRedLeft(x);
			}
			x.left = delete(x.left, key);
		} else {
			if(isRed(x.left))
				x = rotateRight(x);
			if(cmp == 0 && x.right == null) 
					return null;
			if(!isRed(x.right) && !isRed(x.right.left))
				x = moveRedRight(x);
			if(cmp == 0) {
				Node minRight = min(x.right);
				x.key = minRight.key;
				x.val = minRight.val;
				x.right = deleteMin(x.right);
			} else {
				x.right = delete(x.right, key);
			}			
		}
		return siftUp4Delete(x);
	}

	private Node min(Node x) {
		if(x == null) 
			return null;
		Node p = x;
		while(p.left != null) p = p.left;
		return p;
	}

	public void deleteMax() {
		if(root == null)
			return;
		if(!isRed(root.left) && !isRed(root.right))
			root.color = RED;
		root = deleteMax(root);
		root.color = BLACK;
	}

	
	private Node deleteMax(Node x) {
		if(isRed(x.left)) 
			x = rotateRight(x);
		if(x.right == null) 
			return null;
		if(!isRed(x.right) && !isRed(x.right.left)) // 此时x.right仍为黑色意味着x.left也不会是红色，因此需要将父节点的红色下移
			x = moveRedRight(x);
		x.right = deleteMax(x.right);
		return siftUp4Delete(x);
	}

	private Node moveRedRight(Node x) {
		x.color = BLACK;
		x.left.color = x.right.color = RED;
		if(!isRed(x.left.left)) {
			x = rotateRight(x);
		}
		return x;
	}

	public void deleteMin() {
		if(root == null)
			return;
		if(!isRed(root.left) && !isRed(root.right))
			root.color = RED;
		root = deleteMin(root);
		root.color = BLACK;
	}

	private Node deleteMin(Node x) { // 保证x的颜色为红色
		if(x.left == null) {
			return null; // perform delete
		} else {
			if(! isRed(x.left) && ! isRed(x.left.left)) {
				x = moveRedLeft(x);
			}
			x.left = deleteMin(x.left);
			return siftUp4Delete(x);
		}
		
	}
	

	private Node moveRedLeft(Node x) {
		x.color = BLACK;
		x.left.color = x.right.color = RED;
		if(isRed(x.right.left))
			x.right = rotateRight(x.right);
		x = rotateLeft(x);
		return x;
	}

	private Node siftUp4Delete(Node h) {
		if(isRed(h.right))
			h = rotateLeft(h);
		if(isRed(h.left) && isRed(h.left.left)) 
			h = rotateRight(h);
		if(isRed(h.left) && isRed(h.right)) 
			flipColor(h);
		return h;
	}

	private Node rotateLeft(Node h) {
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		return x;
	}
	
	private Node rotateRight(Node h) {
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		h.color = RED;
		return x;
	}
	
	private void flipColor(Node x) {
		x.left.color = x.right.color = BLACK;
		x.color = RED;
	}
	
	
	/**
	 * whether x is a red node
	 * @param x
	 * @return
	 */
	private boolean isRed(Node x) {
		return (x == null) ? false: x.color == RED;
	}
	
	
	private class Node {
		K key;
		V val;
		boolean color;
		Node left, right;
		
		Node(K key, V val, boolean color) {
			this.key = key;
			this.val = val;
			this.color = color;
		}
	}


	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public V get(K key) {
		Node n = get(root, key);
		return (n == null) ? null : n.val;
	}

	private Node get(Node x, K key) {
		if(x == null) return null;
		Node p = x;
		int cmp = key.compareTo(p.key);
		return (cmp < 0) ? get(x.left, key) : (cmp > 0) ? get(x.right, key) : x;
	}

	@Override
	public Iterable<K> keys() {
		List<K> list = new ArrayList<> (size());
		midOrderTranversal(root, list);
		return list;
	}



	private void midOrderTranversal(Node x, List<K> list) {
		if(x == null) return;
		midOrderTranversal(x.left, list);
		list.add(x.key);
		midOrderTranversal(x.right, list);		
	}

	public static void main(String[] args) {
		RedBlackTree<Integer, String> rbt = new RedBlackTree<>();
		rbt.put(1, "hello");
		rbt.put(1, "bye");
	}
	
}
