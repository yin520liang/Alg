package alg.string.pattern;
/**
 * <a href = "https://blog.csdn.net/unoboros/article/details/33106353" >自动机的解释</a>
 * <a href = "https://www.zhihu.com/question/21923021/answer/281346746">next数组</a>
 * @author yang
 *
 */
public class KMP {
	
	private int R = 256; // ascii charset
	
	public KMP(int r) {
		R = r;
	}
	
	public KMP() {
	}
	
	public int search(String txt, String ptn) {
		int n = txt.length();
		int m = ptn.length();
		if (m > n) return -1;
		// build dfa
		int[][] dfa = new int[R][m];
		for(int c = 0; c < R; ++ c) dfa[c][0] = 0;
		dfa[ptn.charAt(0)][0] = 1;
		
		for(int x = 0, j = 1; j < m; ++ j) {
			for(int c = 0; c < R; ++ c) 
				dfa[c][j] = dfa[c][x]; // unmatch transition
			dfa[ptn.charAt(j)][j] = j + 1; // match transition
			x = dfa[ptn.charAt(j)][x];
		}
		
		// search
		for(int i = 0, j = 0; i < n; ++ i) {
			j = dfa[txt.charAt(i)][j];
			if(j == m) return i - j + 1;
		}
		return -1;
	}
}
