/**
 * 
 */
package alg.sort;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 	快速排序的三种实现方法和非递归实现.
 * 	改进点：pivot的选取更具随机性，如在第一个、最后一个和中间元素中取中位数
 * 不稳定
 * @title QuickSort
 */
public class QuickSort {
	
	
	public static void main(String[] args) {
//		int[] a = {4, 1, 7, 6, 9, 2, 8, 0, 3, 5};
//		int[] a = {1};
//		int[] a = {-1, 7};
//		int[] a = {8, 2};
		int[] a = {4, 1, 1, 6, 9, 2, 1, 0, 3, 5};
		QuickSort qs = new QuickSort();
		
		qs.sort(a, 0, a.length - 1);
		System.out.println(Arrays.toString(a));
		
		qs.sortWithStack(a, 0, a.length - 1);
		System.out.println(Arrays.toString(a));
	}
	
	public void sort(int[] a, int left, int right) {
		if(left >= right)
			return;
		int position = partition3(a, left, right);
		sort(a, left, position - 1);
		sort(a, position + 1, right);
	}

	/**
	 * 	1.前后指针法，同时适合链表结构
	 */
	private int partition1(int[] a, int left, int right) {
		int tail = left - 1;
		int pivot = a[right];
		for(int i = left; i < right; ++i) {
			if(a[i] < pivot && ++tail != i) {
				swap(a, i, tail);
			}
		}
		swap(a, ++tail, right);
		return tail;
	}
	
	/**
	 * 2. 左右指针法
	 */
	private int partition2(int[] a, int left, int right) {
		int pivot = a[right];
		int i = left, j = right;
		while(i < j) {
			while(i < j && a[i] <= pivot)
				++i;
			while(i < j && a[j] >= pivot)
				--j;
			if(i < j) {
				swap(a, i, j);
			}
		}
		swap(a, j, right);
		return j;
	}
	
	
	/**
	 * 3. 挖坑法
	 */
	private int partition3(int[] a, int left, int right) {
		int pivot = a[right];
		while(left < right) {
			while(left < right && a[left] <= pivot)
				++left;
			a[right] = a[left];
			while(left < right && a[right] >= pivot)
				--right;
			a[left] = a[right];
		}
		a[left] = pivot;
		return left;
	}

	
	/**
	 * 	非递归版本：借助栈存储后续需要处理的范围参数
	 */
	public void sortWithStack(int[] a, int low, int high) {
		if(low >= high)
			return;
		
		LinkedList<Range> stack = new LinkedList<>();
		stack.push(new Range(low, high));
		while(!stack.isEmpty()) {
			Range range = stack.pop();
			int position = partition2(a, range.left, range.right);
			if(range.left < position - 1) {
				stack.push(new Range(range.left, position - 1));
			}
			if(position + 1 < range.right) {
				stack.push(new Range(position + 1, range.right));
			}
		}
	}

	private int pivotPick(int[] a, int left, int right) {
		return 0;
	}
	
	private void swap(int[] a, int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	
	class Range {
		int left;
		int right;		
		Range(int l, int r) {
			left = l;
			right =r;
		}		
	}
	
}
