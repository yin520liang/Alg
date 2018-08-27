/**
 * 
 */
package alg.code123;

/**
 * 题目：写一个函数返回两个数中的较大者，不能使用if-else及任何比较操作符
 * <a href="http://www.code123.cc/949.html" ></a>
 * @title GetMaxWithoutIf
 */
public class GetMaxWithoutIf {
	
	// 利用减法
	public int getMax(int a, int b) {
		int c = a - b;
		int k = (c >> 31) & 1;
		return a - k * c;
	}

}
