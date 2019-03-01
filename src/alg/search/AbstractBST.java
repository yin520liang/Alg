package alg.search;

public abstract class AbstractBST<K extends Comparable<K>, V> implements SymbolTable<K, V> {
	protected int size = 0;
	
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
	
}
