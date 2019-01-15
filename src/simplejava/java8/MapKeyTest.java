/**
 * 
 */
package simplejava.java8;

import java.util.HashMap;
import java.util.Map;

/**
 * @title
 * @description
 *
 */
public class MapKeyTest {

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
