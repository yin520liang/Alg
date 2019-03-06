/**
 * 
 */
package alg.string.pattern;

/**
 * @title
 * @description Boyer-Moore字符串查找算法
 *
 */
public class BoyerMoore {

	public static void main(String[] args) {
		String p = "abc";
		int mask = createMask(p.toCharArray());
		for (char c = 'a'; c <= 'z'; c += 1) {
			int tmp = 1 << (c & 0x1f);
			if((mask & tmp) == tmp) {
				System.out.println(c + " in " + p);
			}
		}
	}

	static int createMask(char[] pch) {
		int pmask = 0;
		for (char c : pch) {
			pmask |= 1 << (c & 0x1f);
		}
		return pmask;
	}

}
