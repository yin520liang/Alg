/**
 * 
 */
package alg.string.pattern;

import java.util.Arrays;

/**
 * @title
 * @description Boyer-Moore字符串查找算法
 *
 */
public class BoyerMoore {
	private static final int R = 256;
	private int right[]; // 记录模式串中每个字符最后出现的下标
	private String ptn;
	private int m;
	
	public BoyerMoore(int r, String ptn) {
		assert ptn != null && ptn.length() > 0;
		this.ptn = ptn;
		this.m = ptn.length();
		right = new int[r];
		Arrays.fill(right, -1); // characters not in pattern
		for(int i = 0; i < m; ++ i) {
			right[ptn.charAt(i)] = i;   // characters in pattern, last index
		}
	}
	
	public BoyerMoore(String ptn) {
		this(R, ptn);
	}
	
	
	public int search(String txt) {
		if(txt == null || txt.length() < ptn.length()) 
			return -1;
		int n = txt.length();
		int skip = 1;
		int j;
		for(int i = 0; i <= n - m; i += skip) {
			j = m - 1;
			while(j >= 0 && txt.charAt(i + j) == ptn.charAt(j)) --j;
			if(j < 0) {
				return i;
			} else {
				skip = j - right[txt.charAt(i + j)];
				if(skip < 1) skip = 1;
			}
		}
		return -1;
	}

}
