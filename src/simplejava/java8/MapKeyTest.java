/**
 * 
 */
package simplejava.java8;

import java.util.HashMap;
import java.util.Map;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年11月27日上午10:26:02
 *
 */
public class MapKeyTest {

	/**
	 * @author lvzhaoyang
	 * @date 2017年11月27日上午10:26:02
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Map<MyObj, Integer> map = new HashMap<>();
		
		MyObj key = new MyObj();
		key.setX(1);
		
		map.put(key, 1);
		System.out.println(map.get(key));
		
		key.setX(2);
		System.out.println(map.get(key));
	}

}


/**
 * key class
 */
class MyObj {
	int x;
	
	int getX() {
		return x;
	}
	
	void setX(int x) {
		this.x = x;
	}
	
	public int hashCode() {
		return x;
	}
}
