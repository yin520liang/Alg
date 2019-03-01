package alg.search;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

/**
 *  哈希表：链表法解决冲突. <br>
 *  理论上没有容量限制，但为了高效查找应当在适当的时候（如根据负载因子）扩容
 * @author 
 *
 */
public class HashST<K,V> implements SymbolTable<K,V> {
	private static final float HIGH_THRESH = 0.75f;
	private static final float LOW_THRESH = 0.25f;
	private Item<K,V>[] array;
	private int capacity;
	private int size;
	
	public HashST() {
		this(16);
	}
	
	public HashST(int initCapacity) {
		assert initCapacity > 0;
		capacity = initCapacity;
		array = new Item[capacity];
	}

	@Override
	public void put(K key, V val) {
		assert key != null && val != null;
		// 扩容
		if(size / capacity > HIGH_THRESH) 
			resize(size * 2); 
		if(putInArray(array, key, val))
			++ size;
	}
	
	private boolean putInArray(Item<K, V>[] a, K key, V val) {
		int bucket = hash(key) % a.length;
		Item<K, V> p = a[bucket];
		while(p != null && !p.key.equals(key)) p = p.next;
		if(p != null) {
			p.val = val;
			return false;
		} else {
			a[bucket] = new Item<>(key, val, a[bucket]);
			return true;
		}
	}

	private void resize(int newCapacity) {
		Item<K,V>[] newArray = new Item[newCapacity];
		for(Item<K, V> item : array) {
			Item<K, V> p = item;
			while(p != null) {
				putInArray(newArray, p.key, p.val);
				p = p.next;
			}
		}
		array = newArray;
		capacity = newCapacity;
	}

	@Override
	public void delete(K key) {
		int bucket = hash(key) % capacity;
		Item<K, V> p = array[bucket];
		Item<K, V> prev = null;
		while(p != null && !p.key.equals(key)) {
			prev = p; 
			p = p.next;
		}
		if(p != null) { // found
			if(prev == null) {
				array[bucket] = p.next;
			} else {
				prev.next = p.next;
			}
			-- size;
		}
		if(size / capacity < LOW_THRESH)
			resize(capacity / 2);
	}
	
	private int hash(K key) {
		return key.hashCode() & 0x7fffffff;
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
		Item<K, V> p = array[hash(key) % capacity];
		while(p != null && !p.key.equals(key)) p = p.next;
		return (p == null) ? null : p.val;
	}

	
	@Override
	public Iterable<K> keys() {
		List<K> list = new ArrayList<>(size);
		for(int i = 0; i < capacity; ++ i) {
			Item<K, V> p = array[i];
			while(p != null) {
				list.add(p.key);
				p = p.next;
			}
		}
		return list;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String content = FileUtils.readFileToString(
							ResourceUtils.getFile("resources/wiki-horspool.txt"));
		HashST<String, Integer> hst = new HashST<>(197);
		Random rand = new Random();
		for(String token : content.split("\\W+")) {
			hst.put(token, rand.nextInt(100));
		}
		
		System.out.println(hst.get("The"));
		System.out.println(hst.get("a"));
	}
}



class Item<E, T> {
	E key;
	T val;
	Item<E, T> next;
	
	Item(E k, T v) {
		key = k;
		val = v;
	}
	
	Item(E k, T v, Item<E, T> n) {
		key = k;
		val = v;
		next = n;
	}
}


