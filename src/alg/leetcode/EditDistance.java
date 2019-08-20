package alg.leetcode;

import java.util.Arrays;

public class EditDistance {
	
	public static void main(String[] args) {
		
	}
	
	
	
	public int distance(String word1, String word2) {
		char[] cs1 = word1.toCharArray();
		char[] cs2 = word2.toCharArray();
		int n = cs1.length;
		int m = cs2.length;
		int[] lastLine = new int[m + 1];
		int[] curLine = new int[m + 1];
		int i, j;
		for(j = 0; j <= m; ++ j)
			lastLine[j] = j;
		for(i = 1; i <= n; ++ i) {
			curLine[0] = 0;
			for(j = 1; j <= m; ++ j) {
				curLine[j] = Math.min(lastLine[j], curLine[j - 1]) + 1;
				curLine[j] = Math.min(lastLine[j - 1] + 1, curLine[j]);
			}
			lastLine = curLine;
			Arrays.fill(curLine, 0);
		}		
		return curLine[m];
	}
	
	
	
	
	
}
