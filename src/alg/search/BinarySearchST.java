package alg.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组实现的键有序符号表，可用于二分查找
 * 
 * @author yang
 *
 * @param <K> 键的类型，需要实现Comparable接口，用于比较是否相等
 * @param <V> 值的类型
 */
public class BinarySearchST<K extends Comparable<K>, V> implements SortedSymbolTable<K, V> {
	
	private K[] keys;
	private V[] values;
	private int size = 0; // size
	private int capacity = 0;
	
	public BinarySearchST(int initCapacity) {
		assert initCapacity > 0;
		capacity = initCapacity;
		keys = (K[]) new Comparable[capacity];
		values = (V[]) new Object[capacity];
	}

	@Override
	public void put(K key, V val) {
		int i = rank(key);
		if(i == capacity) {
			expand(capacity + 1);
		}
		if(i >= 0 && i < size && key.compareTo(keys[i]) == 0) {
			values[i] = val;
		} else {
			if(i < 0) i = 0;
			for(int j = size - 1; j > i; -- j) {
				keys[j] = keys[j - 1];
				values[j] = values[j - 1];
			}
			keys[i] = key;
			values[i] = val;
			size ++;
		}		
		
	}

	private void expand(int newCapacity) {
		K[] newKeys = (K[]) new Comparable[newCapacity];
		System.arraycopy(keys, 0, newKeys, 0, size);
		V[] newValues = (V[]) new Object[newCapacity];
		System.arraycopy(values, 0, newValues, 0, size);
		capacity = newCapacity;
	}

	@Override
	public void delete(K key) {
		int i = rank(key);
		if(i >= 0 && i < size && keys[i].compareTo(key) == 0) {
			for(int j = i; j < size - 1; ++ j) {
				keys[j] = keys[j + 1];
				values[j] = values[j + 1];
			}
			-- size;
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
		int i = rank(key);
		return (i >= 0 && i < size && keys[i].compareTo(key) == 0) ? values[i] : null;
	
	}

	@Override
	public Iterable<K> keys() {
		List<K> list = new ArrayList<> (size);
		for(K k : keys) {
			list.add(k);
		}
		return list;
	}

	@Override
	public K min() {
		if(size < 1)
			throw new IllegalStateException();
		return keys[0];
	}

	@Override
	public K max() {
		if(size < 1)
			throw new IllegalStateException();
		return keys[size - 1];
	}

	@Override
	public K floor(K key) {
		int i = rank(key);
		if(i >= 0 && i < size && keys[i].compareTo(key) == 0)
			return keys[i];
		if(i == 0) {
			return null;
		}
		return keys[i - 1];
	}

	@Override
	public K ceiling(K key) {
		int i = rank(key);
		if(i >= size)
			return null;
		return keys[i];
	}

	@Override
	public K select(int i) {
		if(size <= i)
			throw new IllegalStateException();
		return keys[i - 1];
	}

	@Override
	public void deleteMin() {
		if(size > 0) {
			delete(keys[0]);
		}		
	}

	@Override
	public void deleteMax() {
		if(size > 0) {
			delete(keys[size - 1]);
		}
	}

	@Override
	public int size(K low, K high) {
		if(low.compareTo(high) > 0) {
			K temp = low;
			low = high;
			high = temp;
		}
		return  rank(high) - rank(low);
	}

	
	@Override
	public int rank(K key) {
		if(size < 1) 
			throw new IllegalStateException();
		int low = 0, high = size - 1;
		while(low <= high) {
			int mid = (low + high) >> 1;
			int res = key.compareTo(keys[mid]);
			if(res == 0) {
				return mid;
			} else if(res < 0) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return low;
	}

	@Override
	public Iterable<K> keys(K low, K high) {
		if(low.compareTo(high) > 0) {
			K temp = low;
			low = high;
			high = temp;
		}
		int ra = rank(low);
		int rb = rank(high);
		List<K> list = new ArrayList<> (rb - ra + 1);
		if(ra < 0)
			ra = 0;
		for(int i = ra; i <= rb && i < size; ++ i) {
			list.add(keys[i]);
		}
		return list;
	}
	
	
}
