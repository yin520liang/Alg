/**
 * 
 */
package simplejava.java8;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @title
 * @description
 */
public class ParallelArrays {
	
	public static void main(String[] args) {
		long[] arrayOfLong = new long[200000];

		Arrays.parallelSetAll(arrayOfLong, 
				index -> ThreadLocalRandom.current().nextInt(1000000));
		Arrays.stream(arrayOfLong).limit(10).forEach(i -> System.out.print(i + " "));
		System.out.println();
		
		System.out.println("available processors: " + Runtime.getRuntime().availableProcessors());
		
		long s1 = System.currentTimeMillis();
		Arrays.sort(arrayOfLong);
		long e1 = System.currentTimeMillis();
		System.out.println("sort serial: " + (e1 - s1) + " ms." );
		
		long s2 = System.currentTimeMillis();
		Arrays.parallelSort(arrayOfLong);
		long e2 = System.currentTimeMillis();
		System.out.println("sort parallel: " + (e2 - s2) + " ms." );
	}
	
}