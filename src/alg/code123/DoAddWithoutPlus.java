/**
 * 
 */
package alg.code123;

/**
 *	 写一个Add函数求两个数的和，不能使用+号或其它算术运算符。
 * 	思路：位操作
 * @title DoAddWithoutPlus
 */
public class DoAddWithoutPlus {
	
	public static void main(String[] args) {
		System.out.println(add1(3, -101));
		System.out.println(add1(18, 23));
		System.out.println(add1(-31, -9));
	}
	
	public static int add1(int a, int b) {
		if(b == 0)
			return a;
		int sum = a ^ b; // 忽略进位求加和
		int carry = (a & b) << 1; // 计算进位
		return add1(sum, carry);
	}
	
	/**
	 * 	非递归版
	 */
	public static int add2(int a, int b) {
		do {
			int sum = a ^ b;
			int carry = (a & b) << 1;
			a = sum;
			b = carry;
		} while(b != 0);
		return a;
	}

}
