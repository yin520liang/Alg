package alg.string.pattern;
/**
 * 朴素/暴力字符串匹配算法
 * @author yang
 *
 */
public class BruteForce {
	
	
	/**
	 * 在文本中查找模式串第一次出现的位置
	 * @param txt 文本
	 * @param ptn 模式
	 * @return ptn在txt中第一次出现的下标（首字符的下标）
	 */
	public int search(String txt, String ptn) {
		int n = txt.length();
		int m = ptn.length();
		if(m > n) return -1;
		for(int i = 0; i <= n - m; ++ i) {
			int j = 0;
			while(j < m && txt.charAt(i + j) == ptn.charAt(j)) ++ j;
			if(j == m) return i;
		}
		return -1;	
	}
	
	// TODO: bug
	public int search2(String txt, String ptn) {
		int n = txt.length();
		int m = ptn.length();
		if(m > n) return -1;
		int i, j = 0;
		for(i = 0; i < n; ++ i) {
			while(j < m && txt.charAt(i) == ptn.charAt(j)) ++ j;
			if(j == m) return i - j + 1;
			else {
				i -= j;
				j = 0;
			}
		}
		return -1;	
	}

}
