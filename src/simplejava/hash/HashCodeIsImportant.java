/**
 * 
 */
package simplejava.hash;

import java.util.HashMap;

/**
 * @title HashCodeIsImportant
 */
public class HashCodeIsImportant {
	
	public static void main(String[] args) {
		Apple a1 = new Apple("green");
		Apple a2 = new Apple("red");

		// hashMap stores apple type and its quantity
		HashMap<Apple, Integer> m = new HashMap<Apple, Integer>(2);
		m.put(a1, 10);
		m.put(a2, 20);
		while(m.get(new Apple("green")) == null);
		System.out.println("find");
	}
}

class Apple {
	private String color;

	public Apple(String color) {
		this.color = color;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Apple))
			return false;
		if (obj == this)
			return true;
		return this.color.equals(((Apple) obj).color);
	}

	
}
