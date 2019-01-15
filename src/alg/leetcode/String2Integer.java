/**
 * 
 */
package alg.leetcode;

/**
 * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
 * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
 * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
 * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
 * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
 * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
 * 
 * @title String2Integer
 */
public class String2Integer {
	
	
	public static void main(String[] args) {
		String2Integer si = new String2Integer();
		System.out.println(si.myAtoi("-9128347233"));
	}
	
	
	

	public int myAtoi(String str) {
		int res = 0;
		boolean neg = false;
		if(str != null) {
			char[] cs = str.toCharArray();
			boolean start = false;
			int i = 0;
			while(i < cs.length && cs[i] == ' ') ++i;
			for(;i < cs.length; ++ i) {
				char c = cs[i];
				if(!start) {
					if(c == '-') {
						start = true;
						neg = true;
					} else if(c == '+') {
						start = true;
					} else if(isNumber(c)) {
						start = true;
						res = res * 10 + integer(c);
					} else {
						break;
					}
				} else if(isNumber(c)) {
					if(checkOverflow(neg, res, c))
						return neg ? Integer.MIN_VALUE : Integer.MAX_VALUE;
					res = res * 10 + integer(c);
				} else {
					break;
				}
				
			}
		}
		return neg ? -res : res;
	}
	
	private boolean checkOverflow(boolean neg, int rest, int add) {
		if(neg) {
			return ( -rest < Integer.MIN_VALUE / 10 || ( -rest == Integer.MIN_VALUE / 10 && add > 8));
		} else {
			return (rest > Integer.MAX_VALUE / 10 || (rest == Integer.MAX_VALUE / 10 && add > 7));
		}
	}

	private int integer(char c) {
		return c - '0';
	}

	private boolean isNumber(char c) {
		return c >= '0' && c <= '9';
	}

}
