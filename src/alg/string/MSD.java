package alg.string;
/**
 *  高位优先的字符串排序
 * @author yang
 *
 */
public class MSD {
	final int R = 256;
	private String[] aa;
	
	public void sort(String[] strs) {
		int n = strs.length;
		aa = new String[n];
		sortLeft2Right(strs, 0, n - 1, 0);
	}

	private void sortLeft2Right(String[] a, int low, int high, int w) {
		if(low >= high) return;
		int[] counts = new int[R + 2]; // 原来预留的1位，再预留1位空字符（说明有些字符串已经到末尾）
		for(int i = low; i <= high; ++ i)
			counts[charAt(a[i], w) + 2] ++;
		for(int j = 0; j < R; ++ j)
			counts[j + 1] += counts[j];
		for(int i = low; i <= high; ++ i) 
			aa[counts[charAt(a[i], w) + 1] ++ ] = a[i];
		System.arraycopy(aa, 0, a, low, high - low + 1);
		for(int r = 0; r < R; ++ r) {
			sortLeft2Right(a, low + counts[r], low + counts[r + 1] - 1, w + 1);
		}
		
	}
	
	private int charAt(String s, int w) {
		return (w < s.length()) ? s.charAt(w) : -1;
	}

}
