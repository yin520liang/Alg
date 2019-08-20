package alg.leetcode;

public class CanonicalPath {
	private static final String SEP = "/";
	
	
	public static void main(String[] args) {
		String s1 = "/.";
		String s2 = "";
		
		CanonicalPath cp = new CanonicalPath();		
		System.out.println(cp.getPath(s1));
	}
	
	public String getPath(String p) {
		if(p == null || p.length() == 0)
			return SEP;
		p = ridRedundantSlash(p);
		if(p.charAt(0) == '/')
			p = p.substring(1);		
		
		String[] token = p.split(SEP);
		String[] res = new String[token.length];
		int j = 0;
		for(int i = 0; i < token.length; ++ i) {
			if(".".equals(token[i])) {
				continue;
			}
			else if("..".equals(token[i])) {
				if(j > 0) -- j;
			}
			else {
				res[j ++ ] = token[i];
			}
		}
		String cpath = join(res, 0, j, SEP);
		return SEP + cpath;
	}

	private String join(String[] token, int start, int len, String sep) {
		int n = Math.min(token.length, start + len);
		if(token != null && n > 0 && start < n) {
			StringBuilder b = new StringBuilder();
			b.append(token[start]);	
			for(int i = start + 1; i < n; ++ i) {
				b.append(sep).append(token[i]);
			}
			return b.toString();
		}
		return "";
	}

	private String ridRedundantSlash(String p) {
		if(p.contains("//")) {
			p = p.replaceAll("//", SEP);
			return ridRedundantSlash(p);
		} else {
			return p;
		}
	}
}
