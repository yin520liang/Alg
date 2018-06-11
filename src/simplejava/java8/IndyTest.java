/**
 * 
 */
package simplejava.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年9月18日上午11:30:43
 */
public class IndyTest {
	public static void main(String[] args) {
		List<String> tbbt = new ArrayList<>(Arrays.asList("Howard", "Amy", "Leonard", "Sheldon"));
		List<String> res = tbbt.stream().filter((p) -> !p.contains("a")).collect(Collectors.toList());
		System.out.println(res);

	}
}
