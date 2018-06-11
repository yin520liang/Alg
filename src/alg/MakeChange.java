/**
 * 
 */
package alg;

/**
 * @title
 * @description 找零钱
 *              <p>
 *              给定数组指定的零钱，计算对现金n有多少种找零方式
 * @author lvzhaoyang
 * @date 2017年9月20日下午3:44:07
 */
public class MakeChange {

	private int[] arr = { 1, 5, 10, 25 }; // 零钱

	public int change(int n) {
		if (n <= 1)
			return n;
		int[][] m = new int[n + 1][arr.length];
		// init
		int i, j;
		for (i = 1; i <= n; ++i)
			m[i][0] = 1;
		for (j = 0; j < arr.length; ++j)
			m[1][j] = 1;

		for (i = 2; i <= n; ++i) {
			int a = i; // money
			int last_res = 1; // last result
			for (j = 1; j < arr.length; ++j) {
				int coin = arr[j];
				if (coin > a) {
					m[a][j] = last_res;
				} else if (coin == a) {
					last_res = m[a][j] = m[a][j - 1] + 1;
				} else {
					last_res = m[a][j] = m[a - arr[j]][j] + m[a][j - 1];
				}
			}
		}
		return m[n][arr.length - 1];
	}

	public static void main(String[] args) {   
		MakeChange mc = new MakeChange();
//		System.out.println(String.format("ways of change %d:%d", 3, mc.change(3)));
//		System.out.println(String.format("ways of change %d:%d", 12, mc.change(12)));
//		System.out.println(String.format("ways of change %d:%d", 7, mc.change(7)));
//		System.out.println(String.format("ways of change %d:%d", 5, mc.change(5)));
		System.out.println(String.format("ways of change %d:%d", 25, mc.change(25)));
	}
}
