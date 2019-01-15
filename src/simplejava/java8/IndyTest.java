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
 */
public class IndyTest {
	public static void main(String[] args) {
		List<String> tbbt = new ArrayList<>(Arrays.asList("Howard", "Amy", "Leonard", "Sheldon"));
		List<String> res = tbbt.stream().filter((p) -> !p.contains("a")).collect(Collectors.toList());
		System.out.println(res);

	}
}
