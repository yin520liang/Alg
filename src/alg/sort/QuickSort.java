/**
 * 
 */
package alg.sort;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * 	快速排序的三种实现方法和非递归实现.
 * 	改进点：pivot的选取更具随机性，如在第一个、最后一个和中间元素中取中位数
 *	 不稳定排序
 * @title QuickSort
 */
public class QuickSort {
	
	private Random rand = new Random(43);
	
	
	public static void main(String[] args) {
		int[] a = randomArray(50, 100);
		QuickSort qs = new QuickSort();
//		qs.threeWaySort(a, 0, a.length - 1);
		qs.dualQuickSort(a, 0,  a.length - 1);
		System.out.println(Arrays.toString(a));
	}
	
	/**
	 * 	常规快排，三种方法：前后指针；左右指针；挖坑/填坑法
	 */
	public void sort(int[] a, int left, int right) {
		if(left >= right)
			return;
		int position = partition3(a, left, right);
		sort(a, left, position - 1);
		sort(a, position + 1, right);
	}

	/**
	 * 	1.前后指针法，同时适合链表结构 <br>
	 *  [left, tail] 存储的是比pivot小的元素集合
	 */
	private int partition1(int[] a, int left, int right) {
		int tail = left - 1;
		// pick a[right] as pivot
		int pivot = a[right];
		for(int i = left; i < right; ++i) {
			// ++ tail != i 是为了避免元素和自身做一次交换操作，也可以省略 
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
		// 自左向右找到第一个比pivot小的元素
		// 自右向左找到第一个比pivot小的元素
		// 交换
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

	
	
	private void swap(int[] a, int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	/**
	 *	 生成随机数组
	 */
	public static int[] randomArray(int n, int round) {
		assert n > 0;
		int[] a = new int[n];
		Random rand = new Random(43);
		for(int i = 0; i < n; ++ i) {
			a[i] = rand.nextInt(round);
		}
		return a;
	}
	
	/**
	 * 3-way sort: 将和pivot相同的值聚集在中间 <p>
	 * +--------+---------+  
	 * | <pivot | > pivot |  
	 * +------pivot-------+  
	 */
	public void threeWaySort(int[] a, int low, int high) {
		if(low >= high)
			return;
		// 1. pick a pivot random
		int pivotIndex = rand.nextInt(high - low + 1) + low;
		int pivot = a[pivotIndex];
		// move pivot to the lowest position
		swap(a, pivotIndex, low);
		int lt = low + 1;
		int gt = high;
		int i = lt;
		while(i <= gt) {
			if(a[i] < pivot) {
				swap(a, i++, lt++);
			} else if(a[i] > pivot) {
				swap(a, i, gt--);
			} else {
				++ i;
			}
		}
		swap(a, low, -- lt);
		sort(a, low, lt - 1);
		sort(a, gt + 1, high);		
	}
	
	
	/**
	 * DualQuickSort：选取两个pivot，将数组分成三部分分别排序 <p>
	 * java的实现核心思想相同，但更复杂，选取了五个pivot，下标相隔7（经验值），
	 * Collections.sort(List) - List.sort() - Arrays.sort() - DualPivotQuicksort.sort(),
	 * 	基本类型数组的排序使用DualPivotQuicksort.sort()， 
	 * 	其他使用归并排序（如带有比较器的、非基本类型数组）：TimSort，mergeSort，ComparableTimSort
	 */
	public void dualQuickSort(int[] a, int low, int high) {
		if(low >= high)
			return;
		// pick 2 pivots, let pivot1 <= pivot2
		int pivot1 = a[low];
		int pivot2 = a[high];
		if(pivot1 > pivot2) {
			swap(a, low, high);
			pivot1 = a[low];
			pivot2 = a[high];
		}
		// if pivot1 == pivot2, it degrades to 3-way sort		
		int lt = low + 1;
		int gt = high - 1;
		int i = lt;
		while(i <= gt) {
			if(a[i] < pivot1) {
				swap(a, i ++, lt ++);
			} else if (a[i] > pivot2) {
				swap(a, i, gt --);
			} else {
				++ i;
			}
		}
		swap(a, low, -- lt);
		swap(a, high, ++ gt);
		
		sort(a, low, lt - 1);
		sort(a, lt + 1, gt - 1);
		sort(a, gt + 1, high);
		
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
