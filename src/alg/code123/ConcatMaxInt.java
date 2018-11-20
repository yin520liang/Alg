/**
 * 
 */
package alg.code123;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 给定一组整数，拼接成最大的整数
 */
public class ConcatMaxInt {

	public static void main(String[] args) {
		String[] ints = new String[] { "73", "7", "4", "21" };
		Arrays.sort(ints, new Comparator<String> () {

			@Override
			public int compare(String s1, String s2) {
				int n = s1.length() + s2.length();
				String a = s1 + s2; // 737
				String b = s2 + s1; // 773
				int i = 0;
				while(i < n && a.charAt(i) == b.charAt(i))
					++ i;			
				if(i < n) {
					int aa = a.charAt(i) - '0';
					int bb = b.charAt(i) - '0';
					return bb - aa;
				}
				return 0;
			}
			
		});
		
		StringBuilder b = new StringBuilder();
		for(String i : ints)
			b.append(i);
		
		System.out.println(b.toString());
	}

}
