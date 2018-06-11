/**
 * 
 */
package alg;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @title
 * @description 计算11^N结果中有多少个‘1’
 * @author lvzhaoyang
 * @date 2017年10月12日下午1:39:06
 */
public class Multiple11 {

	public static void main(String[] args) {
		Multiple11 m11 = new Multiple11();
		
//		long s1 = System.currentTimeMillis();
//		m11.solution(998);
//		System.out.println(String.format("1 cost %d miliseconds.", System.currentTimeMillis() - s1));
//		
//		long s2 = System.currentTimeMillis();
//		m11.solution2(999);
//		System.out.println(String.format("2 cost %d miliseconds.", System.currentTimeMillis() - s2));
		System.out.println(m11.solution2(1000));
	}

	/**
	 * 模拟计算过程，最后统计1的个数
	 * 
	 */
	public int solution(int n) {
		if (n < 0)
			throw new RuntimeException("n can not be negative!");
		// 直接返回11的0次和1次方结果
		if (n == 0)
			return 1;
		if (n == 1)
			return 2;

		List<Integer> bits = new ArrayList<>(10000);
		// init: 11^1=11
		bits.add(1);
		bits.add(1);

		int carry; // 进位
		int left, right;

		for (int i = 1; i < n; ++i) {
			int size = bits.size();
			carry = 0;
			right = bits.get(size - 1);
			for (int j = size - 2; j >= 0; --j) {
				left = bits.get(j);
				int res = left + right + carry;
				if (res >= 10) {
					carry = 1;
					res = res - 10;
				} else {
					carry = 0;
				}
				bits.set(j, res);
				right = left;
			}
			int head = right + carry;
			if (head >= 10) {
				bits.add(0, head - 10);
				bits.add(0, 1);
			} else {
				bits.add(0, head);
			}
			// output(bits);
		}

		int count = 0;
		for (int bit : bits) {
			if (bit == 1) {
				count += 1;
			}
		}
		return count;
	}

	
	public int solution2(int N) {
		// write your code in Java SE 8
		if (N < 5) {
			return 2;
		}
		BigInteger a = new BigInteger("11");
		BigInteger constNum = new BigInteger("11");
		for (int i = 1; i < N; ++i) {
			a = a.multiply(constNum);
		}

		int cnt = 0;
		char[] charArr = a.toString().toCharArray();;
		for (int j = 0; j < charArr.length; j++) {
			if (charArr[j] == '1') {
				cnt++;
			}
		}
		return cnt;
	}


	private <T> void output(List<T> list) {
		for (T t : list) {
			System.out.print(t + ",");
		}
		System.out.println();
	}
}
