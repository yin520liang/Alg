package alg.code123;

import java.util.HashMap;

/**
 * 题目：有一个大文件中包含众多单词，现要求计算给定两个单词之间的最短距离（以中间隔了几个单词作为距离）.如 "My name is abc, what is
 * your name?"中，name和abc的最短距离为2。要求算法以O(1)的时间复杂度给出距离。
 * <p>
 * 思路：首先需要考虑几个问题：单词会重复出现；是否有顺序要求（即a和b的距离与b和a的距离不同）。实现时使用哈希表，一次建立后，每次查询复杂度都可以保证在O(1)。
 * 
 * @author 
 *
 */
public class WordDistance {
	private HashMap<String, Integer> dist;
	
	private void createMap(String[] words) {
		dist = new HashMap<>();
		for(String w : words) {
			
		}
	}
}
