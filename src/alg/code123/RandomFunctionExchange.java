/**
 * 
 */
package alg.code123;

import java.util.Random;

/**
 * 	题目：给你一个能生成1到5随机数的函数，用它写一个函数生成1到7的随机数。 (即：使用函数rand5()来实现函数rand7())。
 * <a href = "http://www.code123.cc/959.html" >题目</a>
 * @title RandomFunctionExchange
 */
public class RandomFunctionExchange {
	
	private Random rand = new Random(43);
	
	public static void main(String[] args) {
		RandomFunctionExchange rfe = new RandomFunctionExchange();
		for(int i = 0; i < 20; ++i) {
			System.out.println(rfe.rand7());
		}
	}
	
	public int rand7() {
		int a = rand25(); // 若AB本身范围超过了CD则不需要这一步转换
		while(a > 21) { // 此处21实为a/7*7，即不超过25的7的最大倍数
			a = rand25();
		}
		return a % 7 + 1;
	}
	
	private int rand25() {
		return 5 * (rand5() - 1) + 5;
	}
	
	/**
	 * 	此处用random类模拟随机返回1~5的函数
	 */
	private int rand5() {
		return rand.nextInt(5) + 1;
	}

	/**
	 * 	扩展：给你一个随机生成a到b的函数， 用它去实现一个随机生成c到d的函数
	 * 	前者设为randAB，后者设为randCD
	 * 	randAB - A + 1即可转换为从1到B-A + 1的随机数，从而使用上述方法
	 */
}
