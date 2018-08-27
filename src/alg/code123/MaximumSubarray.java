/**
 * 
 */
package alg.code123;

/**
 *   题目：最大子数组和
 *   <a href="http://www.code123.cc/954.html">解释<a>
 * 
 * @Title MaximumSubarray
 */
public class MaximumSubarray {

	public static void main(String[] args) {
		int[] a = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
//		int m = solution1(a);
//		int m = solution2(a);
		int m = solution3(a);
		System.out.println(m);
	}

	/**
	 * O(n^2), nested for-loop
	 */
	private static int solution1(int[] a) {
		int n = a.length;
		if(n == 1)
			return a[0];
		int res = 0;
		int sum = 0;	
		for (int i = 0; i < n; ++i) {
			sum = a[i];
			for (int j = i + 1; j < n; ++j) {
				sum += a[j];
				if (sum > res) {
					res = sum;
				}
			}
		}
		return res;
	}

	/**
	 * divide and canquer, O(nlogn)
	 */
	private static int solution2(int[] a) {
		if(a.length == 1)
			return a[0];
		return divide(a, 0, a.length - 1);
	}

	private static int divide(int[] a, int left, int right) {
		if(left == right)
			return a[left];
		
		int mid = (left + right)/2;
		int lmax = divide(a, left, mid);
		int rmax = divide(a, mid+1, right);
		int mmax = getMidMax(a, mid);
		return Math.max(lmax, Math.max(mmax, rmax));
	}

	private static int getMidMax(int[] a, int mid) {
		int sum = 0;
		int i;
		int left = Integer.MIN_VALUE, right = Integer.MIN_VALUE;
		// left
		for (i = mid; i >= 0; --i) {
			sum += a[i];
			if (sum > left) {
				left = sum;
			}
		}
		// right
		sum = 0;
		for (i = mid + 1; i < a.length; ++i) {
			sum += a[i];
			if (sum > right) {
				right = sum;
			}
		}
		return left + right;
	}
	
	/**
	 * Dynamic programming.
	 * Explanation: 
	 * - A[i,j] = maximum value of subarray from i to j
	 * - sum[k] = 
	 */
	private static int solution3(int[] a) {
		if(a.length == 1)
			return a[0];
		
		int max = a[0]; // 当前到j的最大值
		int sum = a[0]; // 以j结尾的最大值
		for(int i = 1; i < a.length; ++i) {
			if(sum > 0) {
				sum += a[i];
			}else {
				sum = a[i];
			}
			if(sum > max) {
				max = sum;
			}
		}
		return max;
	}
	
}
