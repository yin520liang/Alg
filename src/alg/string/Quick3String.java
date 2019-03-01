package alg.string;
/**
 * 	参考快排的三向排序
 * @author yang
 *
 */
public class Quick3String {
	
	public void sort(String[] strs) {
		int n = strs.length;
		sort(strs, 0, n - 1, 0);
	}

	
	private void sort(String[] strs, int low, int high, int w) {
		if(low >= high) return;
		int p1 = low + 1; // 小于pivot的元素下一个交换位
		int p2 = high; // 大于pivot的元素下一个交换位
		int pivotCh = charAt(strs[low], w);
		int i = p1; // 保证：i之前的元素小于等于pivot
		// p1之前的元素都小于pivot，[p1,i)之间的元素都等于pivot
		// 最后跳出循环时i和p2重合，因此[p1, p2]之间的是等于pivot的
		while(i <= p2) {
			int c = charAt(strs[i], w);
			if(c < pivotCh) swap(strs, p1 ++, i ++);
			else if(c > pivotCh) swap(strs, p2 --, i);
			else ++i;
		}
		sort(strs, low, p1 - 1, w);
		if(pivotCh >= 0)
			sort(strs, p1, p2, w + 1);
		sort(strs, p2 + 1, high, w);
	}
	
	private void swap(String[] s, int i, int j) {
		String tmp = s[i];
		s[i] = s[j];
		s[j] = tmp;
	}


	private int charAt(String s, int i) {
		return (i < s.length()) ? s.charAt(i) : -1;
	}

}
