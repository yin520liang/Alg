/**
 * 
 */
package alg.code123;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 题目：设计一个算法，找到数组中所有和为指定值的整数对
 * 
 * @title PairSum
 */
public class PairSum {

	private String msg = "find pair (%d, %d)";
	
	
	// O(n), 额外存储空间O(n)
	public void findWithHash(int[] a, int sum) {
		// 第一次遍历，将sum-x的值作为键存到hashmap里，可以处理重复值（将单独的int换为list）
		// 此处假不考虑重复值
		HashMap<Integer, Integer> map = new HashMap<>(a.length);
		for(int item : a) {
			map.put(sum - item, item);
		}
		for(int item : a) {
			if(map.containsKey(item)) {
				System.out.println(String.format(msg, item, 0, map.get(item), 0));
			}
		}
		// 若a中元素值范围有限，如[0,k]也可考虑使用bitmap，将bitmap[sum-x] =1，则map.containsKey(item)可换为bitmap[item] == 1
	}
	

	// O(nlogn)
	public void findAfterSort(int[] a, int sum) {
		if (a == null || a.length < 2)
			return;
		Arrays.sort(a);
		int low = 0;
		int high = a.length - 1;
		while (low < high) {
			if (a[low] + a[high] < sum) {
				++low;
			} else if (a[low] + a[high] > sum) {
				--high;
			} else {
				System.out.println(String.format(msg, a[low], a[high]));
				// add additional logic to cope with repeated values
				++low;
				--high;
			}
		}
	}

}
