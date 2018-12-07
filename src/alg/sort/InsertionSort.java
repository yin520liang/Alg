/**
 * 
 */
package alg.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 插入排序
 */
public class InsertionSort {

	
	public static void main(String[] args) {
		int[] a = randomArray(50000, 10000);
		InsertionSort s = new InsertionSort();

		long timestamp = System.currentTimeMillis();
		s.insertSort(a);
//		s.dualInsertSort(a);
		System.out.printf("Cost %f seconds\n", (System.currentTimeMillis() - timestamp) * 1.0 / 1000);
//		System.out.println(Arrays.toString(a));
	}
	
	
	public static int[] randomArray(int n, int round) {
		assert n > 0;
		int[] a = new int[n];
		Random rand = new Random(43);
		for(int i = 0; i < n; ++ i) {
			a[i] = rand.nextInt(round);
		}
		return a;
	}
	
	
	
	public void insertSort(int[] a) {
		int n = a.length;
		for(int i = 1; i < n; ++ i) {
			int tmp = a[i];
			int j = i - 1;
			while(j >= 0 && a[j] > tmp) {
				a[j + 1] = a[j];
				-- j;
			}
			a[j + 1] = tmp;
		}
	}
	
	/**
	 * 成对插入排序: 每次同时插入两个元素
	 */
	public void dualInsertSort(int[] a) {
		int n = a.length;
		if(n < 2)
			return;
					
		for(int i = 1; i + 1 < n; i += 2) {
			int a1 = a[i];
			int a2 = a[i + 1]; // bigger
			if(a1 > a2) {
				a1 = a2;
				a2 = a[i];
			}
			int j = i - 1;
			while(j >= 0 && a[j] > a2) {
				a[j + 2] = a[j];
				-- j;
			}
			a[j + 2] = a2;
			while(j >= 0 && a[j] > a1) {
				a[j + 1] = a[j];
				-- j;
			}
			a[j + 1] = a1;
		}
		// 处理因为n为偶数导致的最后一个元素尚未插入的情况
		int last = a[n - 1];
		int j = n - 2;
		while(j >= 0 && a[j] > last) {
			a[j + 1] = a[j];
			--j;
		}
		a[j + 1] = last;
	}
	
}
