/**
 * 
 */
package alg.code123;

import java.util.Random;

/**
 * <a href ="http://www.code123.cc/964.html" />Q20.2</a> <br>
 *	 写一个随机洗牌函数。要求洗出的52!种组合都是等概率的。 也就是你洗出的一种组合的概率是1/(52!)。假设已经给你一个完美的随机数发生器。
 */
public class RandomShuffle {

	// 此处假设java中的random函数是真随机
	public void shuffle(int[] cards) {
		Random rand = new Random(43);
		for(int i = 0; i < cards.length; ++i) {
			int j = rand.nextInt(cards.length - i) + i;
			swap(cards, i, j);
		}
	}


	private void swap(int[] cards, int i, int j) {
		int tmp = cards[i];
		cards[i] = cards[j];
		cards[j] = tmp;	
	}
	
}
