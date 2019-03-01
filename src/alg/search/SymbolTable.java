package alg.search;

/**
 * 符号表通用API定义
 * 
 * @author 
 *
 * @param <K>
 * @param <V>
 */
public interface SymbolTable<K, V> {

	void put(K key, V val);

	void delete(K key);

	boolean contains(K key);

	boolean isEmpty();

	int size();

	V get(K key);

	Iterable<K> keys();

}
