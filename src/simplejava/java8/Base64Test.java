/**
 * 
 */
package simplejava.java8;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年9月15日下午2:14:32
 */
public class Base64Test {
	public static void main(String[] args) {
		final String text = "http://www.baidu.com?key=汽车 网";

		final String encoded = Base64.getUrlEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
		System.out.println(encoded); // QmFzZTY0IGZpbmFsbHkgaW4gSmF2YSA4IQ==

		final String decoded = new String(Base64.getUrlDecoder().decode(encoded), StandardCharsets.UTF_8);
		System.out.println(decoded); // Base64 finally in Java 8!
	}
}
