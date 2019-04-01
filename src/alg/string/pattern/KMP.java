package alg.string.pattern;
/**
 * <a href = "https://blog.csdn.net/unoboros/article/details/33106353" >自动机的解释</a>
 * <a href = "https://www.zhihu.com/question/21923021/answer/281346746">next数组</a>
 * @author yang
 *
 */
public class KMP {	
	private int R = 256; // ascii charset	
	private String ptn;
	private int m;
	private int[] next; // next array
	private int[][] dfa; // dfa
	

	public KMP(String ptn) {
		this(256, ptn);
	}
	
	public KMP(int r, String ptn) {
		R = r;
		this.ptn = ptn;
		this.m = ptn.length();
		buildDFA();
		buildNext();
	}
	
	private void buildNext() {
		// abababca
		//  abababca
		next = new int[m];
		next[0] = -1;
		int i = 0, j = -1;
		while(i < m && j < m) {
			if(j == -1 || ptn.charAt(i) == ptn.charAt(j)) {
				++ i; 
				++ j;
				next[i] = j;
			} else {
				j = next[j];
			}
		}
		
	}

	private void buildDFA() {
		int[][] dfa = new int[R][m];
		for(int c = 0; c < R; ++ c) dfa[c][0] = 0;
		dfa[ptn.charAt(0)][0] = 1;
		for(int x = 0, j = 1; j < m; ++ j) {
			// unmatch transition
			for(int c = 0; c < R; ++ c) 
				dfa[c][j] = dfa[c][x]; 
			// match transition
			dfa[ptn.charAt(j)][j] = j + 1; 
			x = dfa[ptn.charAt(j)][x];
		}
	}

	
	public int search(String txt) {
		int n = txt.length();
		if (m > n) return -1;		
		// search
		for(int i = 0, j = 0; i < n; ++ i) {
			j = dfa[txt.charAt(i)][j];
			if(j == m) return i - j + 1;
		}
		return -1;
	}
	
	
	public int search2(String txt) {
		int n = txt.length();
		if (m > n) return -1;
		int i = 0, j = 0;
		while(i < n && j < m) {
			if(j < 0 || txt.charAt(i) == txt.charAt(j)) {
				++ i;
				++ j;
			} else {
				j = next[j];
			}
		}
		if(j == m) return i - j;
		return -1;
	}
}
