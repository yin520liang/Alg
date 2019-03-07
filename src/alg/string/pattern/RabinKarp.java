package alg.string.pattern;
/**
 * RabinKarp字符串匹配算法，借助哈希值 <br>
 * 以迭代的方式在线性时间计算所有M位字符串的哈希值
 * @author yang
 *
 */
public class RabinKarp {
	private int R = 256;
	private String ptn;
	private int m;
	private int targetHash;	
	private int RM;	
	private int Q = 997;
	
	public RabinKarp(String ptn) {
		this(256, ptn);
	}
	
	public RabinKarp(int r, String ptn) {
		R = r;
		this.ptn = ptn;
		m = ptn.length();
		targetHash = hash(ptn, 0, m - 1);
	}

	private int hash(String s, int start, int end) {
		int h = 0;
		for(int i = start; i <= end; ++ i)
			h = (h * R + s.charAt(i)) % Q;
		return h;
	}

}
