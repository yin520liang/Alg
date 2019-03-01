package alg.string;

/**
 *  低位优先的字符串排序（基数排序）
 * @author yang
 *
 */
public class LSD {
	
	/**
	 * 	基数排序，从低位到高位
	 * @param strs
	 * @param W
	 */
	public void sort1(String[] strs, int W) {
		int n = strs.length;
		for(int w = W - 1; w >= 0; -- w) {
			// insertion sort
			for(int i = 1; i < n; ++ i) {
				String s = strs[i];
				char c = s.charAt(w);
				int j = i - 1;
				while(j >= 0 && strs[j].charAt(w) > c) {
					strs[j + 1] = strs[j];
					-- j;
				}
				strs[j + 1] = s;
			}
		}
	}
	
	/**
	 *   计数排序，需要确定字符范围
	 * @param strs
	 * @param W
	 */
	public void sort2(String[] strs, int W) {
		final int R = 256; // ascii字符
		final int n = strs.length;
		for(int w = W - 1; w >= 0; -- w) {
			int[] counts = new int[R + 1];
			for(String s : strs) 
				counts[s.charAt(w) + 1] ++;
			for(int j = 0; j < R; ++ j) 
				counts[j + 1] += counts[j];
			String[] newStrs = new String[n];
			for(String s : strs) {
				newStrs[counts[s.charAt(w)] ++] = s;				
			}
			System.arraycopy(newStrs, 0, strs, 0, n);
		}
		
	}

}
