/**
 * 
 */
package alg.sort;

import java.util.Arrays;

/**
 * 	堆排序.
 * 	适合数组也适合链表结构
 * 不稳定
 * @title HeapSort
 */
public class HeapSort {
	
	
	public static void main(String[] args) {
		int[] a = {1};
//		int[] a = {-1, 7};
//		int[] a = {8, 2};
//		int[] a = {4, 1, 1, 6, 9, 2, 1, 0, 3, 5};
		HeapSort hs = new HeapSort();
		
		hs.sort(a, 0, a.length);
		System.out.println(Arrays.toString(a));
	}
	
	public void sort(int[] a, int start, int size) {
		buildHeap(a, start, size);
		while(size > 1) {
			swap(a, 0, --size);
			heapify(a, 0, size);
		}
	}


	/**
	 * 	建堆
	 */
	private void buildHeap(int[] a, int start, int size) {
		// 左孩子 2i + 1,  右孩子 2i+2
		for(int i = size/2 - 1; i >= 0; --i) {
			heapify(a, i, size);
		}
	}


	private void heapify(int[] a, int k, int size) {
		int next;
		while((next = 2 * k + 1) < size) {
			// 如果有右孩子
			if(next + 1 < size && a[next + 1] > a[next])
				next = next + 1;
			if(a[next] > a[k]) {
				swap(a, next, k);
				k = next;
			} else {
				break;
			}
		}
		
	}


	private void swap(int[] a, int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
}
