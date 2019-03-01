package alg.string;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Trie树. <p>
 * 除了基本的get、put方法，还提供key的遍历、最长前缀、通配符匹配等
 * @author yang
 *
 * @param <V>
 */
public class TrieST<V> {
	
	static final int R = 256;
	private Node<V> root;	
	
	public void put(String key, V value) {
		root = put(root, key, value, 0);
	}

	private Node<V> put(Node<V> node, String key, V value, int d) {
		if(node == null) node = new Node<>();
		if(d == key.length()) node.v = value;
		else {
			char c = key.charAt(d);
			node.next[c] = put(node.next[c], key, value, d + 1);
		}
		return node;
	}
	
	
	public V get(String key) {
		Node<V> x = get(root, key, 0);
		return (x == null) ? null : x.v;
	}

		
	private Node<V> get(Node<V> node, String key, int d) {
		if(node == null) return null;
		if(d == key.length()) return node;
		int c = key.charAt(d);
		return get(node.next[c], key, d + 1);
	}
	
	
	/**
	 * 前缀相关
	 */
	public Iterable<String> keysWithPrefix(String prefix) {
		Queue<String> q = new LinkedList<>( );
		collect(get(root, prefix, 0), new StringBuilder(prefix), q);
		return q;
	}
	
	
	public Iterable<String> keys() {
		return keysWithPrefix("");
	}
	
	public Iterable<String> patternMatch(String pattern) {
		Queue<String> q = new LinkedList<>( );
		patternMatch(root, pattern, "", q, 0);
		return q;
	}


	private void patternMatch(Node<V> node, String pattern, String key, Queue<String> q, int d) {
//		if(node == null) return;
//		if(d == pattern.length() && node.v != null) 
//			q.offer(key); 
//		char tc = pattern.charAt(d);
//		if(tc == '*') {
//			// 0
//			patternMatch(node, pattern, key, q, d + 1);
//			// 1 ~ n
//			
//		} else {
//			for(char c = 0; c < R; ++ c) {
//				if(c == tc || tc == '.')
//					patternMatch(node.next[c], pattern, key + c, q, d + 1);
//			}
//		}
//		
	}

	private void collect(Node<V> node, StringBuilder pb, Queue<String> q) {
		if(node == null) return;
		if(node.v != null) q.offer(pb.toString());
		for(char c = 0; c < R; ++ c) {
			pb.append(c);
			collect(node.next[c], pb, q);
			pb.deleteCharAt(pb.length() - 1);
		}	
	}

	public void delete(String key) {
		root = delete(root, key, 0);
	}


	private Node<V> delete(Node<V> node, String key, int d) {
		if(node == null) return null;
		// find target node and set value = null
		if(d == key.length()) node.v = null;
		else {
			char c = key.charAt(d);
			node.next[c] = delete(node.next[c], key, d + 1);
		}
		// if node should be deleted
		if(node.v != null) return node;
		for(char c = 0; c < R; ++ c) {
			if(node.next[c] != null) return node;
		}
		return null;
	}


	static class Node<V> {
		V v;
		Node<V> next[];
		
		Node() {
			next = new Node[R];
		}
	}
	
}
