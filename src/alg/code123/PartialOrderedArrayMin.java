/**
 * 
 */
package alg.code123;

/**
 * 求部分有序数组的最值问题
 * 
 * @title PartialOrderedArrayMin
 * @author lvzhaoyang
 * @date 2019年5月7日
 */
public class PartialOrderedArrayMin {

	public static void main(String[] args) {
//		int[] a1 = { 4, 3, 2, 1, 5, 6 };
//		int[] a2 = { 4 };
//		int[] a3 = { 1, 5, 6 };
//		int[] a4 = { 4, 3, 2, 1 };
//
//		System.out.println(min(a1, 0, a1.length - 1));
//		System.out.println(min(a2, 0, a2.length - 1));
//		System.out.println(min(a3, 0, a3.length - 1));
//		System.out.println(min(a4, 0, a4.length - 1));
		
		
		int[] b1 = { 19, 20, 100, 888, -1, 3, 8, 10, 11, 15, 18 };
		int[] b2 = { 100, 19, 20};
		int[] b3 = { 15, 18, 19, 20, 100, 888, -1, 3, 8, 10, 11 };
		int[] b4 = { 8, 10, 11, 15, 18, 19, 20, -1, 3, 100, 888 };
		int[] b5 = { 3, -1, 8, 10, 11, 15, 18, 19, 20, 100, 888 };
		
		System.out.println(min2(b1, 0, b1.length - 1));
		System.out.println(min2(b2, 0, b2.length - 1));
		System.out.println(min2(b3, 0, b3.length - 1));
		System.out.println(min2(b4, 0, b4.length - 1));
		System.out.println(min2(b5, 0, b5.length - 1));
	}

	/**
	 * 对升序的排列的整数数组连续取前N个元素进行逆序排列，得到局部有序的数组，如：<br>
	 * 【1,2,3,4,5,6】->【4,3,2,1,5,6】 <br>
	 * 假设数组元素无重复，从这种局部有序的数组中找到最小值，要求时间复杂度小于O(n)，空间复杂度为O(1) <br>
	 */
	private static int min(int[] a, int l, int h) {
		while (h - l > 1) {
			int mid = (l + h) / 2;
			if (a[mid - 1] > a[mid]) {
				l = mid;
			} else {
				h = mid;
			}
		}
		return Math.min(a[l], a[h]);
	}
	
	/**
	 * 将单调递增数组中取出连续的一部分移动到数组头部，如数组[1,2,3,4,5,6] 取出3到6的部分移动到头部得到[3,4,5,1,2,6]
	 * 现要求给出算法求该数组的最小值，复杂度小于O(n)
	 * @author lvzhaoyang
	 * @date 2019年5月7日
	 */
	private static int min2(int[] a, int l, int h) {
		if(h - l <= 1) return Math.min(a[l], a[h]);
		int mid = (h + l) / 2;
		if(a[mid - 1] > a[mid]) return a[mid];
		if(a[mid] > a[mid + 1]) return a[mid + 1];
		if(a[l] > a[mid]) {
			return min2(a, l, mid);
		} else if (a[mid] > a[h]) {
			return min2(a, mid + 1, h);
		} else {
			return Math.min(min2(a, l, mid - 1), min2(a, mid + 1, h - 1));
		}
	}

}
