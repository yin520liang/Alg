package alg.string.pattern;
/**
 * RabinKarp字符串匹配算法，借助哈希值 <br>
 *  以迭代的方式在线性时间计算所有M位字符串的哈希值
 * @author yang
 *
 */
public class RabinKarp {
	private int R = 256;
	private String ptn;
	private int m; // 模式串长度
	private long targetHash; // 目标哈希值	
	private int RM;	// R^(m-1)
	private int Q = 997;  // 取余素数
	
	public RabinKarp(String ptn) {
		this(256, ptn);
	}
	
	public RabinKarp(int r, String ptn) {
		R = r;
		this.ptn = ptn;
		m = ptn.length();
		targetHash = hash(ptn, 0, m - 1);
		RM = 1;
		for(int k = 1; k < m; ++ k) 
			RM = (RM * R) % Q;
	}

	private long hash(String s, int start, int end) {
		long h = 0;
		for(int i = start; i <= end; ++ i)
			h = (h * R + s.charAt(i)) % Q;
		return h;
	}
	
	
	public int search(String txt) {
		int n = txt.length();
		if(n < m) return -1;
		long txtHash = hash(txt, 0, m - 1);
		if(txtHash == targetHash && match(txt, 0)) 
			return 0;
		for(int i = m; i < n; ++ i) {
			txtHash = (txtHash + Q - txt.charAt(i - m) * RM % Q) % Q;
			txtHash = (txtHash * R + txt.charAt(i)) % Q;
			if(txtHash == targetHash && match(txt, i - m + 1)) {
				return i - m + 1;
			}
		}
		return -1;
	}
	
	private boolean match(String t, int offset) {
		return t.substring(offset, offset + m).equals(ptn);
	}

}
