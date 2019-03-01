package alg.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *  递归实现的二叉查找树
 * @author 
 *
 */
public class BST<K extends Comparable<K>, V> implements SortedSymbolTable<K, V> {
	
	protected BSTNode root;

	@Override
	public void put(K key, V val) {
		assert key != null && val != null;
		root = put(root, key, val);		
	}

	protected BSTNode put(BSTNode subroot, K key, V val) {		
		if(subroot == null)
			return new BSTNode(key, val, 1);
		int cmp = key.compareTo(subroot.key);
		if(cmp < 0)
			subroot.left = put(subroot.left, key, val);
		else if(cmp > 0)
			subroot.right = put(subroot.right, key, val);
		else
			subroot.val = val;
		subroot.size = size(subroot.left) + size(subroot.right) + 1;
		return subroot;
	}

	@Override
	public void delete(K key) {
		if(root == null)
			return;
		root = delete(root, key);
		
	}

	/**
	 * 在指定的子树中删除特定键对应的节点.
	 * 若待删除的节点位于左右子树，分别删除返回；若为当前节点，分三种情况：
	 * <li>只有左子树，删除当前节点即直接返回右子树
	 * <li>只有右子树，删除当前节点即直接返回左子树
	 * <li>同时有左右子树，用后继节点替代，后继节点的计算可用右子树中的最小节点代替；
	 * 最后更新节点数
	 * 
	 * @param subroot
	 * @param key
	 * @return 执行删除操作后的当前节点
	 */
	protected BSTNode delete(BSTNode subroot, K key) {
		int cmp = key.compareTo(subroot.key);
		if(cmp < 0)
			subroot.left = delete(subroot.left, key);
		else if(cmp > 0)
			subroot.right = delete(subroot.right, key);
		else {
			if(subroot.left == null) return subroot.right;
			if(subroot.right == null) return subroot.left;
			BSTNode tmp = subroot;
			subroot = min(subroot.right);
			subroot.right = deleteMin(tmp.right);
			subroot.left = tmp.left;
		}
		subroot.size = size(subroot.left) + size(subroot.right) + 1;
		return subroot;
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
		return size(root);
	}
	
	protected int size(BSTNode subroot) {
		return (subroot == null) ? 0 : subroot.size;
	}

	@Override
	public V get(K key) {
		BSTNode node = get(root, key);
		return (node == null) ? null : node.val;
	}
	
	protected BSTNode get(BSTNode subroot, K key) {
		if(key == null || subroot == null)
			return null;
		int res = key.compareTo(subroot.key);
		if(res == 0)
			return subroot;
		else if(res < 0)
			return get(subroot.left, key);
		else		
			return get(subroot.right, key);
	}

	/**
	 * 根据中序遍历添加节点
	 */
	@Override
	public Iterable<K> keys() {
		List<K> list = new ArrayList<> (size(root));
		keys(list, root);
		return list;
	}
	
	protected void keys(List<K> list, BSTNode node) {
		if(node == null)
			return;
		keys(list, node.left);
		list.add(node.key);
		keys(list, node.right);
	}

	@Override
	public K min() {
		if(root == null)
			return null;
		BSTNode n = min(root);
		return (n == null) ? null : n.key;
	}

	protected BSTNode min(BSTNode subroot) {
		return (subroot.left == null) ? subroot : min(subroot.left);
	}
	
	@Override
	public K max() {
		if(root == null)
			return null;
		BSTNode n = max(root);
		return (n == null) ? null : n.key;
	}
	
	protected BSTNode max(BSTNode subroot) {
		return (subroot.right == null) ? subroot : max(subroot.right);
	}

	@Override
	public K floor(K key) {
		BSTNode node = floor(root, key);
		return (node == null) ? null : node.key;
	}
	
	protected BSTNode floor(BSTNode subroot, K key) {
		if(subroot == null)
			return null;
		int cmp = key.compareTo(subroot.key);
		if(cmp < 0) {
			return floor(subroot.left, key);
		} else if(cmp > 0) {
			BSTNode expected = floor(subroot.right, key);
			return (expected == null) ? subroot : expected;
		} else {
			return subroot;
		}
	}

	@Override
	public K ceiling(K key) {
		BSTNode node = ceiling(root, key);
		return (node == null) ? null : node.key;
	}

	protected BSTNode ceiling(BSTNode subroot, K key) {
		if(subroot == null)
			return null;
		int cmp = key.compareTo(subroot.key);
		if(cmp > 0) {
			return ceiling(subroot.right, key);
		} else if(cmp < 0) {
			BSTNode expected = ceiling(subroot.left, key);
			return (expected == null) ? subroot : expected;
		} else {
			return subroot;
		}
	}

	@Override
	public K select(int i) {
		assert i > 0;
		return select(root, i);
	}

	protected K select(BSTNode subroot, int i) {
		if(i > subroot.size)
			return null;
		int left = size(subroot.left);
		if(i <= left) {
			return select(subroot.left, i);
		} else if (i == left + 1){
			return subroot.key;
		} else {
			return select(subroot.right, i - left - 1);
		}
	}

	@Override
	public void deleteMin() {
		if(root == null)
			return;	
		root = deleteMin(root);
	}
	
	protected BSTNode deleteMin(BSTNode subroot) {
		if(subroot.left == null) return subroot.right;
		subroot.left = deleteMin(subroot.left);
		subroot.size = size(subroot.left) + size(subroot.right) + 1;
		return subroot;
	}

	@Override
	public void deleteMax() {
		if(root == null)
			return;
		root = deleteMax(root);
		
	}
	
	protected BSTNode deleteMax(BSTNode subroot) {
		if(subroot.right == null) return subroot.left;
		subroot.right = deleteMax(subroot.right);
		subroot.size = size(subroot.left) + size(subroot.right) + 1;
		return subroot;
	}

	@Override
	public int size(K low, K high) {
		if(low.compareTo(high) > 0) {
			K tmp = low;
			low = high;
			high = tmp;
		}
		return size(root, low, high);
	}

	protected int size(BSTNode subroot, K low, K high) {		
		if(subroot.key.compareTo(low) >= 0 && subroot.key.compareTo(high) <= 0) {
			return size(subroot.left, low, high) + 1 + size(subroot.right, low, high);
		}
		return 0;
	}

	@Override
	public int rank(K key) {
		return rank(root, key);
	}

	protected int rank(BSTNode subroot, K key) {
		int cmp = key.compareTo(subroot.key);
		if(cmp < 0)
			return rank(subroot.left, key);
		else if(cmp > 0)
			return size(subroot.left) + 1 + rank(subroot.right, key);
		else 
			return size(subroot.left) + 1;
	}

	@Override
	public Iterable<K> keys(K low, K high) {
		if(low.compareTo(high) > 0) {
			K tmp = low;
			low = high;
			high = tmp;
		}
		List<K> list = new LinkedList<> ( );
		keys(root, list, low, high);
		return list;
	}

	protected void keys(BSTNode node, List<K> list, K low, K high) {
		if(node == null)
			return;
		if(node.key.compareTo(low) >= 0 && node.key.compareTo(high) <= 0) {
			keys(node.left, list, low, high);
			list.add(node.key);
			keys(node.right, list, low, high);
		}
		
	}

	/**
	 * 二叉查找树节点
	 * @author 
	 *
	 */
	public class BSTNode {
		K key;
		V val;
		BSTNode left;
		BSTNode right;
		int size; // 节点数缓存
		
		BSTNode(K k, V v, int n) {
			key = k;
			val = v;
			size = n;
		}
	}
}
