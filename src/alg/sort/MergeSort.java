/**
 * 
 */
package alg.sort;

import java.util.Arrays;

/**
 * 	归并排序.
 * 	改进点：当元素数在一定范围内时，避免递归，直接插入排序
 * 	稳定
 * @title MergeSort
 */
public class MergeSort {
	
	
	public static void main(String[] args) {
//		int[] a = {1};
//		int[] a = {-1, 7};
//		int[] a = {8, 2};
		int[] a = {4, 1, 1, 6, 9, 2, 1, 0, 3, 5, -19, -1, 0, 5};
		MergeSort ms = new MergeSort();
		
		ms.bottomUpSort(a);
		System.out.println(Arrays.toString(a));
	}
	
	
	public void sort(int[] a, int left, int right) {
		if(left >= right)
			return;
		int mid = (left + right) / 2;
		sort(a, left, mid);
		sort(a, mid + 1, right);
		merge(a, left, mid, right);
	}
	
	// 自顶而下
	private void merge(int[] a, int left, int mid, int right) {
		int[] tmp = new int[right - left + 1];
		int p = left, q = mid + 1;
		int k = 0;
		while(p <= mid && q <= right) {
			tmp[k++] = (a[p] <= a[q]) ? a[p++] : a[q++];
		}
		while(p <= mid) {
			tmp[k++] = a[p++];
		}
		while(q <= right) {
			tmp[k++] = a[q++];
		}
		System.arraycopy(tmp, 0, a, left, right - left + 1);
	}
	
	// 自底而上，不使用递归
	private void bottomUpSort(int[] a) {
		int n = a.length;
		for(int size = 1; size < n; size *= 2) {
			for(int i = 0; i + size < n; i += size + size) {
				merge(a, i, i + size - 1, Math.min(i + size + size - 1, n - 1));
			}
		}
	}
	
	
}
