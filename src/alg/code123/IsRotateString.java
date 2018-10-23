/**
 * 
 */
package alg.code123;

/**
 * <a href="http://www.code123.cc/751.html">题目：假设你有一个isSubstring函数，可以检测一个字符串是否是另一个字符串的子串。
 * 给出字符串s1和s2，只使用一次isSubstring就能判断s2是否是s1的旋转字符串，
 * 请写出代码。旋转字符串："waterbottle"是"erbottlewat"的旋转字符串。</a>
 * 
 * @title IsRotateString
 */
public class IsRotateString {
	
	
	public boolean isRotateString(String s1, String s2) {
		String all = s2 + s2;
		return all.contains(s1); // java中可以用contains判断子串
	}
	
	public boolean isRotateString2(String s1, String s2) {
		if(s1.length() != s2.length())
			return false;
		
		char target = s2.charAt(0);
		int i = 0;
		int n = s1.length();
		StringBuilder builder = new StringBuilder(s1);
		while(i ++ < n) {
			char cur = builder.charAt(0);
			if(cur == target && s2.equals(builder.toString()))
				return true;
			// rotate
			builder.deleteCharAt(0).append(cur);
		}
		return false;
	}

}
