/**
 * 
 */
package alg.leetcode;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 * 输入: "abcabcbb" <br>
 * 输出: 3 <br>
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。 <br>
 * 
 * @title MaxUniqueSubstring
 * @author lvzhaoyang
 * @date 2018年12月27日
 */
public class MaxUniqueSubstring {

	public static void main(String[] args) {
		MaxUniqueSubstring mus = new MaxUniqueSubstring();
		int l = mus.lengthOfLongestSubstring("bbbbbbb");
		System.out.println(l);
	}

	public int lengthOfLongestSubstring(String s) {
		if (s == null || s.length() < 1)
			return 0;
		int len = 1;
		char[] cs = s.toCharArray();
		StringBuilder substring = new StringBuilder(cs.length);
		substring.append(cs[0]);
		for (int i = 1; i < cs.length; ++i) {
			char current = cs[i];
			int j;
			for(j = 0; j < substring.length() && substring.charAt(j) != current; ++j );
			if(j < substring.length()) {
				substring = substring.delete(0, j + 1);
			}
			substring.append(current); // at this point, substring has the longest unique substring ending at i
			if (substring.length() > len) {
				len = substring.length();
			}
		}
		return len;
	}

}
