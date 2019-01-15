/**
 * 
 */
package alg.leetcode;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：

	L   C   I   R
	E T O E S I I G
	E   D   H   N
	
 * @title ZString
 */
public class ZString {
	
//	public static void main(String[] args) {
//		String s = "LEETCODEISHIRING";
//		ZString zs = new ZString();
//		System.out.println(StringUtils.equals("LCIRETOESIIGEDHN", zs.convert(s, 3)));
//		System.out.println(StringUtils.equals("LDREOEIIECIHNTSG", zs.convert(s, 4)));
//	}
	
	public String convert(String s, int numRows) {
		if(s == null || s.length() < 1 || numRows == 1)
			return s;

		char[] cs = s.toCharArray();
		StringBuilder res = new StringBuilder(s.length());
		for(int r = 0; r < numRows; ++ r) {
			int i = r;
			while(i < cs.length) {
				res.append(cs[i]);
				i += numRows + numRows - 2;
				int mid;
				if((r > 0) && (r < numRows - 1) && ((mid = i - r - r) < cs.length)) {
					res.append(cs[mid]);
				}
			}
		}
        return res.toString();
    }

}
