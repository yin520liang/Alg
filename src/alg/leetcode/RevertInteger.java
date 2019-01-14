/**
 * 
 */
package alg.leetcode;

/**
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。 
 * 示例 3: 输入: 120 输出: 21
 * 
 * 重点在于溢出检查
 * @title RevertInteger
 * @author lvzhaoyang
 * @date 2018年12月27日
 */
public class RevertInteger {
	
	public static void main(String[] args) {
		RevertInteger ri = new RevertInteger();
		System.out.println(ri.reverse(-123));
		System.out.println(ri.reverse(120));
		System.out.println(ri.reverse(123));
	}

	public int reverse(int x) {
		int y = 0;
		int next;
		while(x != 0) {
			next = x / 10;
			int bit = x - next * 10;
			if(y > Integer.MAX_VALUE / 10 || (y == Integer.MAX_VALUE / 10 && bit > 7))
				return 0;
			if(y < Integer.MIN_VALUE/10 || (y == Integer.MIN_VALUE && bit < -8))
				return  0;
			y = y * 10 + bit;
			x = next;
		}
		return y;
	}
}
