/**
 * 
 */
package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @title ListAddAllTest
 * @author lvzhaoyang
 * @date 2018年8月21日
 */
public class ListAddAllTest {

	/**
	 * @author lvzhaoyang
	 * @date 2018年8月21日 
	 */
	public static void main(String[] args) {
		List<String> a = new ArrayList<>(1);
		List<String> b = Arrays.asList("a", "b", "c");
		a.addAll(b);
		System.out.println(a);

	}

}
