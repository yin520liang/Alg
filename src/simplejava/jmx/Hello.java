/**
 * 
 */
package simplejava.jmx;

import javax.management.Notification;
import javax.management.NotificationListener;

/**
 * @title Hello
 */
public class Hello implements HelloMBean {
	
	public void sayHello() {
		System.out.println("hello, world");
	}

	public int add(int x, int y) {
		return x + y;
	}

	public String getName() {
		return this.name;
	}

	public int getCacheSize() {
		return this.cacheSize;
	}

	public synchronized void setCacheSize(int size) {
		this.cacheSize = size;
		System.out.println("Cache size now " + this.cacheSize);
	}

	private final String name = "Reginald";
	private int cacheSize = DEFAULT_CACHE_SIZE;
	private static final int DEFAULT_CACHE_SIZE = 200;
	
	
	static NotificationListener listener = (Notification notification, Object obj) -> {
		if(obj != null) {
			Hello hello = (Hello) obj;
			if("getName".equals(notification.getType())) {
				System.out.println(hello.getName());
			}
		}
	};
	
}