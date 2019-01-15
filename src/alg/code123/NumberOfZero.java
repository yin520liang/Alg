/**
 * 
 */
package alg.code123;

/**
 * @title NumberOfZero
 * @date 2018年8月27日
 */
public class NumberOfZero {

	public int count(int n) {
		if(n < 0)
			return -1;
		int c = 0;
		while((n = n/5) > 0) {
			c += n;
		}
		return c;
	}
}
