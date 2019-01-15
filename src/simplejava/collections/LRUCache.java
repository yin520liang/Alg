/**
 * 
 */
package simplejava.collections;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 继承LinkedHashMap实现的LRU缓存
 * @title LRUCache
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
	private int maxSize;
	
	public LRUCache(int maxSize) {
		super(maxSize, 0.75f, true);
		this.maxSize = maxSize;
	}
	
	/**
	 * 设置超过最大容量时移除最少访问的元素
	 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
	 */
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return size() > maxSize;
    }
    
    
    
    /**
     * test
     */
    public static void main(String[] args) {
    	LRUCache<String, String> cache = new LRUCache<> (3) ;
    	cache.put("msg1", "hello");
    	cache.put("msg2", "good morning");
    	cache.put("msg3", "hi");
    	
    	cache.get("msg2");
    	print(cache);
    	
    	cache.put("msg4", "bye");
    	print(cache);
    	
    }
    
    private static void print(HashMap<?, ?> map) {
    	for(Map.Entry<?, ?> entry : map.entrySet()) {
    		System.out.printf("%s=%s, ", entry.getKey(), entry.getValue());
    	}
    	System.out.println();
    }

}
