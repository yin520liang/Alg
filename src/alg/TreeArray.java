/**
 * 
 */
package alg;

import java.util.Arrays;

/**
 * @title
 * @description 树状数组
 */
public class TreeArray {

	private int size;
	private int[] rawData;
	private int[] array;

	public void create(int[] a) {
		size = a.length;
		if (size > 0) {
			// init array
			rawData = new int[size + 1];
			array = new int[size + 1];
			System.arraycopy(a, 0, rawData, 1, size);
			Arrays.fill(array, 0);

			for (int i = 1; i <= size; ++i) {
				int index = i;
				while (_checkRange(index)) {
					array[index] += rawData[i];
					int lowbit = index & (-index);
					index += lowbit;
				}
			}
		}
	}

	public int getSum(int start, int end) {
		if (_checkRange(start, end)) {
			int sum1 = getSum(start - 1);
			int sum2 = getSum(end);
			return (sum2 - sum1);
		}
		return 0;
	}


	public int getSum(int i) {
		int sum = 0;
		while (_checkRange(i)) {
			sum += array[i];
			int lowbit = i & (-i);
			i -= lowbit;
		}
		return sum;
	}

	private boolean _checkRange(int start, int end) {
		if (start < 1 || end > size || start > end)
			return false;
		return true;
	}

	private boolean _checkRange(int i) {
		if (i > 0 && i <= size)
			return true;
		return false;
	}

	public static void main(String[] args) {
		int[] a = { 3, 5, -1, 9, 2, 4, -8, 7 };
		TreeArray ta = new TreeArray();
		ta.create(a);
		System.out.println(ta.getSum(3));
		System.out.println(ta.getSum(2, 5));
	}
}
