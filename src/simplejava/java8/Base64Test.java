/**
 * 
 */
package simplejava.java8;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @title
 * @description
 */
public class Base64Test {
	public static void main(String[] args) {
		final String text = "elastic=MZsecret@2018";

		final String encoded = Base64.getUrlEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
		System.out.println(encoded); // QmFzZTY0IGZpbmFsbHkgaW4gSmF2YSA4IQ==

		final String decoded = new String(Base64.getUrlDecoder().decode(encoded), StandardCharsets.UTF_8);
		System.out.println(decoded); // Base64 finally in Java 8!
	}
}
