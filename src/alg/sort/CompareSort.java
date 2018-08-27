/**
 * 
 */
package alg.sort;

import java.util.Arrays;

/**
 * 基本比较排序
 * <p>
 * <a href="http://www.cnblogs.com/eniac12/p/5329396.html">排序算法总结</a>
 * 
 * @title CompareSort
 */
public class CompareSort {

	public static void main(String[] args) {
		int[] a = { 4, 1, -1, 6, 9, 2, 1, 0, 3, 5, 5, 18, 20, -19 };
		CompareSort cs = new CompareSort();
		cs.shellSort(a);
		System.out.println(Arrays.toString(a));
	}

	/**
	 * 冒泡排序，复杂度固定为n^2，稳定
	 */
	public void bubbleSort(int[] a) {
		if (a == null || a.length <= 1)
			return;
		int n = a.length;
		for (int i = 1; i < n; ++i) { // round
			for (int j = 0; j < n - i; ++j) {
				if (a[j] > a[j + 1])
					swap(a, j, j + 1);
			}
		}
	}

	/**
	 * 冒泡排序另一个版本：鸡尾酒排序
	 */
	public void cocktailSort(int a[]) {
		int n = a.length;
		int left = 0; // 初始化边界
		int right = n - 1;
		while (left < right) {
			for (int i = left; i < right; i++) { // 前半轮,将最大元素放到后面
				if (a[i] > a[i + 1]) {
					swap(a, i, i + 1);
				}
			}
			right--;
			for (int i = right; i > left; i--) { // 后半轮,将最小元素放到前面
				if (a[i - 1] > a[i]) {
					swap(a, i - 1, i);
				}
			}
			left++;
		}
	}

	/**
	 * 选择排序，选择最值的过程开销是固定的，不存在最好的最坏情况，在任何情况下复杂度都是n^2 不稳定，发生在最小元素交换时
	 */
	public void selectSort(int[] a) {
		if (a == null || a.length <= 1)
			return;
		int n = a.length;
		for (int i = 0; i < n - 1; ++i) {
			int min = i;
			for (int j = i + 1; j < n; ++j) {
				if (a[j] < a[min]) {
					min = j;
				}
			}
			if (min != i)
				swap(a, min, i);
		}
	}

	/**
	 * 插入排序，最优(n)和最坏情况(n^2)分别发生在数据已有序和逆序情况下，稳定
	 */
	public void insertSort(int[] a) {
		if (a == null || a.length <= 1)
			return;
		int n = a.length;
		for (int i = 1; i < n; ++i) {
			int tmp = a[i];
			int j = i - 1;
			for (; j >= 0 && a[j] > tmp; --j) {
				a[j + 1] = a[j];
			}
			a[j + 1] = tmp;
		}
	}

	/**
	 * 希尔排序，插入排序的改进，重点在于间隔h的选取
	 */
	public void shellSort(int[] a) {
		if (a == null || a.length <= 1)
			return;

		for (int h = getH(a); h >= 1; h /= 2) {
			for (int s = 0; s < h; ++s) {
				innerInsertSort(a, s, h);
			}
		}
	}

	/**
	 * 希尔排序内部的插入排序
	 */
	private void innerInsertSort(int[] a, int start, int h) {
		int n = a.length;
		for (int i = start + h; i < n; i += h) {
			int tmp = a[i]; // to be insert
			int j = i - h; // prev element
			for (; j >= start && a[j] > tmp; j -= h) {
				a[j + h] = a[j];
			}
			a[j + h] = tmp;
		}
	}

	private int getH(int[] a) {
		return 4;
	}

	private void swap(int[] a, int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

}
