/**
 * 
 */
package alg.leetcode;

import java.util.Arrays;

/**
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m +
 * n))。 你可以假设 nums1 和 nums2 不会同时为空。
 * <a href="https://www.cnblogs.com/zichi/p/leetcode-4-median-of-two-sorted-arrays.html">解答</a>
 * @date 2018年12月27日
 */
public class MedianOfTwoSequence {
	
	private static int[] EMPTY_ARRAY = new int[0];
	
	
//	public static void main(String[] args) {
//		int[] n1 = {};
//		int[] n2 = {};
//		MedianOfTwoSequence mts = new MedianOfTwoSequence();
//		System.out.println(mts.findMedianSortedArrays(n1, n2));
//	}

	public double findMedianSortedArrays(int[] nums1, int[] nums2) {		
		return solution2(nums1, nums2);
	}
	
	/**
	 * 	排序后直接取中位数.
	 *   时间复杂度主要在排序：O(nlogn)
	 *   空间复杂度：O(n + m)
	 */
	private double solution1(int[] num1, int[] num2) {
		num1 = checkNull(num1);
		num2 = checkNull(num2);
		int totalLen = num1.length + num2.length;
		int[] integrated = new int[totalLen];
		System.arraycopy(num1, 0, integrated, 0, num1.length);
		System.arraycopy(num2, 0, integrated, num1.length, num2.length);
		Arrays.sort(integrated);
		double res = integrated[totalLen >> 1];
		if((totalLen & 0x01) == 0 && (totalLen >> 1 - 1) >= 0) {
			res = (res + integrated[totalLen >> 1 - 1] ) / 2;
		}
		return res;
	}
	
	/**
	 * 有序数组的合并只需要O(n + m)，只需要按顺序找到中位数即可
	 */
	private double solution2(int[] num1, int[] num2) {
		num1 = checkNull(num1);
		num2 = checkNull(num2);
		int totalLen = num1.length + num2.length;
		int index = totalLen >> 1;
		int i = 0, j = 0, k = 0;
		double res = 0.0;
		double prev = 0.0;
		while(i < num1.length && j < num2.length) {
			int current = (num1[i] <= num2[j]) ? num1[i ++] : num2[j ++];
			if(k == index) {
				res = current;
				break;
			}		
			++ k;
			prev = current;
		}
		
		while(i < num1.length) {			
			if(k == index) {
				res = num1[i];
				break;
			}
			prev = num1[i];
			++ i;
			++ k;
		}

		while(j < num2.length) {			
			if(k == index) {
				res = num2[j];
				break;
			}
			prev = num2[j];
			++ j;
			++ k;
		}
		
		if((totalLen & 0x01) == 0 && index - 1 >= 0) {
			res = (res + prev) / 2;
		}
		return res;
	}
	
	/**
	 * 
	 */
	private double solution3(int[] num1, int[] num2) {
		num1 = checkNull(num1);
		num2 = checkNull(num2);
		int totalN = num1.length + num2.length;
		int k = totalN >> 1;
		if((totalN & 0x01) == 1)
			return findKth(num1, 0, num1.length, num2, 0, num2.length, k + 1);
		else 
			return (findKth(num1, 0, num1.length, num2, 0, num2.length, k) 
					+ findKth(num1, 0, num1.length, num2, 0, num2.length, k + 1)) / 2;
	}
	
	

	private double findKth(int[] a, int offset1, int m, int[] b, int offset2, int n, int k) {
		if(m > n)
			return findKth(b, offset2, n, a, offset1, m, k);
		if(m == 0)
			return b[offset2 + k -1];
		if(k == 1)
			return Math.min(a[offset1], b[offset2]);
		
		int ka = Math.min(k >> 1, m);
		int kb = k - ka;
		
		if(a[ka - 1] > b[kb - 1]) {
			return findKth(a, offset1, m, b, kb, n - kb, k - kb);
		} else if(a[ka - 1] < b[kb - 1]) {
			return findKth(a, ka, m - ka, b, offset2, n, k - ka);
		} else {
			return a[ka - 1];
		}	
	}

	private int[] checkNull(int[] a) {
		return (a == null) ? EMPTY_ARRAY : a;
	}
	

	
}
