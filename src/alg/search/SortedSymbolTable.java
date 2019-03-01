package alg.search;

/**
 * 键有序的符号表API定义
 * 
 * @author 
 *
 * @param <K>
 * @param <V>
 */
public interface SortedSymbolTable<K extends Comparable<K>, V> extends SymbolTable<K, V> {
	K min();

	K max();

	/**
	 * 小于等于key的第一个键
	 * 
	 * @param key
	 * @return
	 */
	K floor(K key);

	/**
	 * 大于等于key的第一个键
	 * 
	 * @param key
	 * @return
	 */
	K ceiling(K key);
	/**
	 * 查找第i大的数，即排序序列位为i
	 * @param i
	 * @return
	 */
	K select(int i);

	void deleteMin();

	void deleteMax();

	int size(K low, K high);

	/**
	 * 如果表中存在该键，rank()应该返回该键的位置，也就是表中小于它的键的数量; 如果表中不存在该键，rank()还是应该返回表中小于它的键的数量. <br>
	 * @param key
	 * @return 返回值非负
	 */
	int rank(K key);

	Iterable<K> keys(K low, K high);
}
