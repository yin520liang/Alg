/**
 * 
 */
package alg.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组和整数N，返回加和为N的两个整数在数组中的下标.
 * 假定：数组中没有重复元素
 * @title FindIndexTwoNumberSum
 * @author lvzhaoyang
 * @date 2018年12月26日
 */
public class FindIndexTwoNumberSum {

	public int[] twoSum(int[] nums, int target) {
		if(nums.length < 2)
			return null;
		// use map to store <number, index> pairs
		Map<Integer, Integer> map = new HashMap<> (nums.length);
		for(int i = 0 ; i < nums.length; ++i) {
			map.put(nums[i], i);
		}
		// loop for each element
		for(int i = 0; i < nums.length; ++ i) {
			Integer other = map.get(target - nums[i]);
			if(other != null && i != other) {
				return i < other ? new int[] {i, other} : new int[] {other, i};
			}
		}
		return null;
	}

}
