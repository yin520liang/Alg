package alg.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 	开放寻址法实现的哈希表.
 * 	线性探查、二次探查、随机探查.
 * 问题：满负荷可能导致无限循环，必须在适当的时候扩容；删除操作很复杂，不能直接置空，需要将后续数据依次前提
 * @author 
 *
 */
public class ProbingHashST<K, V> implements SymbolTable<K,V> {	
	private static final float HIGH_THRESH = 0.5f;
	private static final float LOW_THRESH = 0.125f;
	private Item<K,V>[] array;
	private int capacity;
	private int size;

	public ProbingHashST(int initCapacity) {
		capacity = initCapacity;
		array = new Item[capacity];
	}
	
	public ProbingHashST() {
		this(16);
	}

	@Override
	public void put(K key, V val) {
		if(size > capacity * HIGH_THRESH)
			resize(2 * size);
		if(putInArray(array, key, val));
			size ++;
	}

	private boolean putInArray(Item<K, V>[] a, K key, V val) {
		int bucket = hash(key) % a.length;
		Item<K, V> p = a[bucket];
		for(;p != null; bucket = (bucket + 1) % a.length, p = a[bucket]) {
			if(p.key.equals(key)) {
				p.val = val;
				return false;
			}
		}
		a[bucket] = new Item<>(key, val);
		return true;
	}

	private void resize(int newCapacity) {
		Item<K, V>[] newArray = new Item[newCapacity];
		for(Item<K, V> oldItem : array) {
			if(oldItem != null) {
				putInArray(newArray, oldItem.key, oldItem.val);
			}
		}
		capacity = newCapacity;
		array = newArray;
	}

	/**
	 * 删除时直接将元素置空会导致查找断层，需要将后续元素提前
	 */
	@Override
	public void delete(K key) {
		int bucket = hash(key) % capacity;
		int i = bucket;
		for(;array[i] != null && !array[i].key.equals(key); i = (i + 1) % capacity);
		// found
		if(array[i] != null)  { 
			int j = i + 1;
			while(array[j] != null) {
				if(hash(array[j].key) % capacity == bucket) {
					array[i].key = array[j].key;
					array[i].val = array[j].val;
					i = j;
				}
				j = (j + 1) % capacity;
			}
			array[i] = null;
			-- size;
		}
		if(size > 0 && (size < capacity * LOW_THRESH))
			resize(capacity / 2);
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
		int bucket = hash(key) % capacity;
		Item<K, V> p = array[bucket];
		// 若此时数组已满容易陷入无限循环，因此需要在put时扩容
		for(; p!= null && !p.key.equals(key); bucket = (bucket + 1) % capacity, p = array[bucket]);
		return (p == null) ? null : p.val;
	}
	
	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff);
	}

	@Override
	public Iterable<K> keys() {
		List<K> list = new ArrayList<> (size);
		for(Item<K,V> item : array) {
			if(item != null)
				list.add(item.key);
		}
		return list;
	}

	
	public static void main(String[] args) {
		ProbingHashST<Integer, String> ph = new ProbingHashST<>(7);
		ph.put(1, "hello");
		ph.put(15, "world");
		ph.put(22, "see");
		ph.put(4, "blue");
		ph.put(5, "bye");
		ph.put(6, "goods");
		ph.put(20, "ant");
		ph.put(8, "bee");
		ph.put(9, "flower");
		
		ph.delete(2); // do nothing
		ph.delete(1);
		ph.delete(15);

		System.out.println(ph.get(22));
	}
}




