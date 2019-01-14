/**
 * 
 */
package alg.leetcode;

import java.util.Arrays;

/**
 * 给定一个字符串 (s) 和一个字符模式 (p)。实现支持 '.' 和 '*' 的正则表达式匹配。 '.' 匹配任意单个字符。 '*'
 * 匹配零个或多个前面的元素。 匹配应该覆盖整个字符串 (s) ，而不是部分字符串。 说明: s 可能为空，且只包含从 a-z 的小写字母。 p
 * 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。
 * 
 * @title RegexMatch
 */
public class RegexMatch {

	public static void main(String[] args) {
		RegexMatch rm = new RegexMatch( );
		System.out.println(rm.isMatch("aa", "a")); // F
		System.out.println(rm.isMatch("aa", "a*")); // T
		System.out.println(rm.isMatch("ab", ".*")); // T
		System.out.println(rm.isMatch("aab", "c*a*b")); // T
		System.out.println(rm.isMatch("mississippi", "mis*is*p*.")); // F
	}

	public boolean isMatch(String s, String p) {
		if (s == null || p == null)
			return false;
		return matchRecursive(s.toCharArray(), p.toCharArray(), 0, 0);
	}

	private boolean matchRecursive(char[] s, char[] p, int i, int j) {
		if (Arrays.equals(s, p))
			return true;
		if (p[j] == '.') {
			return matchRecursive(s, p, i + 1, j + 1);
		} else if (p[j] == '*') {
			for (int k = i; k < s.length; ++k) {
				if (matchRecursive(s, p, k, j + 1))
					return true;
			}
		} else if (s[i] == p[j]) {
			return matchRecursive(s, p, i + 1, j + 1);
		}
		return false;
	}

}
