/**
 * 
 */
package alg.string.pattern;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @title Boyer-Moore-Horspool算法
 * @description 对Boyer-Moore算法进行简化
 *
 */
public class Horspool {

	private int[] skipTable = new int[256]; // 256 characters
	private int span = 10;

	// private int mask;

	public static void main(String[] args) {
		Horspool horspool = new Horspool();
		String s = null;
		try {
			s = FileUtils.readFileToString(new File("resources/wiki-horspool.txt"));
		} catch (IOException e) {
			System.out.println("read file faild.");
		}
		if (s == null)
			System.exit(1);

		String p = "significantly";
		long stime = System.currentTimeMillis();
		String res = horspool.highlight(s, p);
		System.out.println("time cost:" + (System.currentTimeMillis() - stime) + " ms");
		System.out.println("position:" + res);
	}

	String highlight(String s, String p) {
		int index = search(s, p);
		if (index >= 0) {
			int start = index - span;
			start = (start < 0) ? 0 : start;
			int end = index + p.length() + span;
			end = (end > s.length()) ? s.length() : end;
			return s.substring(start, index) + "<em>" + p + "</em>"
					+ s.substring(index + p.length(), end);
		}
		return null;
	}

	int search(String s, String p) {
		if (p.length() > s.length())
			return -1;
		if (p.length() <= 0)
			return 0;

		char[] sch = s.toCharArray();
		char[] pch = p.toCharArray();
		// preprocess
		buildSkipTable(pch);

		int skip = 0;
		while (sch.length - skip >= pch.length) {
			int j = pch.length - 1;
			while (j >= 0 && sch[skip + j] == pch[j])
				--j;
			if (j < 0)
				return skip;
			skip += skipTable[sch[skip + pch.length - 1]];
		}
		return -1;
	}

	void buildSkipTable(char[] pch) {
		int i;
		for (i = 0; i < 256; ++i) {
			skipTable[i] = pch.length;
		}
		for (i = 0; i < pch.length - 1; ++i) {
			skipTable[pch[i]] = pch.length - 1 - i;
		}
	}

}
