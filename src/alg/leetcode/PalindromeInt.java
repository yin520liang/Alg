/**
 * 
 */
package alg.leetcode;

/**
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * 示例 1:
 * 输入: 121
 * 输出: true
 * @title PalindromeInt
 * @author lvzhaoyang
 * @date 2018年12月28日
 */
public class PalindromeInt {
	
    public boolean isPalindrome(int x) {
        return solution1(x);
    }
	
    /**
     * 转换成字符串，判断回文串
     */
	public boolean solution1(int a) {
		String s = String.valueOf(a);
		return false;
	}
	
	public boolean solution2(int a) {
		if(a < 0 && ((a & 0x01) == 0 && a != 0))
			return false;
		int r = 0;
		while(a > r) {
			r = r * 10 + a % 10;
			a /= 10;
		}
		return (a == r) || ((a / 10) == r);
	}

}
