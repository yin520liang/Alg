/**
 * 
 */
package simplejava.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Title IsNewThreadSafe
 * @Description new过程是否线程安全？
 */
public class IsNewThreadSafe {


	public static void main(String[] args) throws InterruptedException {
		Thread[] ths = new Thread[100];
		for(int i = 0; i != ths.length; ++i) {
			ths[i] = new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + 
						":" + A.getInstance().getData().get(9));
			});
			ths[i].start();
			ths[i].join();
		}

	}

}
class A {
	private static A a;
	
	private List<Double> data;
	
    private Random rand = new Random(43);

	private A() {
		// do something slow..
		data = new ArrayList<>(10);
		for(int i = 0; i < 10; ++i) {
			data.add(rand.nextDouble() * rand.nextDouble());
		}
	}

	public static A getInstance() {
		if (a == null) {
			synchronized (A.class) {
				if (a == null) {
					a = new A();
				}
			}
		}
		return a;
	}
	
	public List<Double> getData() {
		return data;
	}
}


/** * Created by zejian on 2017/6/11. * Blog : http://blog.csdn.net/javazejian [原文地址,请尊重原创] */ 
class DoubleCheckLock { 
	private static DoubleCheckLock instance; 
	
	private DoubleCheckLock(){} 
	
	public static DoubleCheckLock getInstance(){ 
		//第一次检测
		if (instance==null){ //同步 
			synchronized (DoubleCheckLock.class){
				if (instance == null){ //多线程环境下可能会出现问题的地方 
					instance = new DoubleCheckLock(); 
					} 
				} 
			} 
		return instance;  
	}

}
