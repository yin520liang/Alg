package alg.leetcode;

public class MinSubstringCoverChars {
	
	private static final String NONE = "";
	
	public static void main(String[] args) {
		MinSubstringCoverChars mscc = new MinSubstringCoverChars();
		String s1 = "abcussss";
		String s2 = "as";
		System.out.println(mscc.substring(s1, s2));
	}
	
	
	public String substring(String s, String t) {
		if(s == null || s.length() == 0)
			return NONE;
		
		int tmask = mask(t, 0, t.length());
		int clen = bits(tmask);
		if(s.length() < clen)
			return NONE;
		
		int minSubLen = Integer.MAX_VALUE;
		int[] minSubRange = {-1, -1};
		int smask;
		for(int start = 0; start < s.length() - clen; ++ start) {
			int end = start + clen;
			smask = mask(s, start, end);
			while(!cover(smask, tmask) && end < s.length()) {
				smask = smask | (1 << charIndex(s.charAt(end)));
				end += 1;				
			}
			if(cover(smask, tmask)) {
				if(end - start < minSubLen) {
					minSubLen = end - start;
					minSubRange[0] = start;
					minSubRange[1] = end;
				}
			}
		}
		return (minSubRange[0] >= 0) ? s.substring(minSubRange[0], minSubRange[1]) : NONE;
	}
	
	
	private boolean cover(int mask1, int mask2) {
		return (mask1 & mask2) == mask2;
	}
	
	private int bits(int mask) {
		int count = 0;
		for(int i = 0; i < Integer.SIZE; ++ i) {
			count += (mask >> i) & 1;
		}
		return count;
	}
	
	private int mask(String s, int start, int end) {
		int m = 0;
		char[] cs = s.toCharArray();
		for(int i = start; i < end && i < cs.length; ++ i) {
			m = m | (1 << charIndex(cs[i]));
		}
		return m;
	}
	
	
	private int charIndex(char c) {
		return c - 'a';
	}

}
