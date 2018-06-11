/**
 * 
 */
package simplejava.java8;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年11月17日下午12:35:24
 *
 */
public class ReverseString {

	public static void main(String[] args) {
		String s = "hello how are you";
		System.out.println(reverse(s));
		
	}

	private static String reverse(String s) {
		char[] ch = s.toCharArray();
		int start = 0;
		int end = ch.length - 1;

		while (start <= end) {
			start = reverse(ch, start, end);
		}
		return new String(ch);
	}

	private static int reverse(char[] ch, int start, int end) {
		if (ch[start] == ' ') {
			return start + 1;
		}

		if (ch[end] == ' ') {
			shift(ch, start, end);
			return start + 1;
		}

		int wordStart = end;
		// 找到第一个空格
		while (wordStart >= start && ch[wordStart] != ' ')
			--wordStart;

		if (ch[wordStart] != ' ')
			return end;

		wordStart += 1;
		int wordLen = end - wordStart + 1;
		// switch
		for (int i = 0; i < wordLen; ++i) {
			shift(ch, start + i, wordStart + i);
		}

		return start + wordLen;
	}

	// 移位
	private static void shift(char[] ch, int start, int end) {
		char c = ch[end];
		for (int i = end; i > start; --i) {
			ch[i] = ch[i - 1];
		}
		ch[start] = c;
	}
}
