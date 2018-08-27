/**
 * 
 */
package http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @title HttpTest
 * @author lvzhaoyang
 * @date 2018年8月20日
 */
public class HttpTest {

	/**
	 * @author lvzhaoyang
	 * @throws UnsupportedEncodingException 
	 * @date 2018年8月20日 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String url = "https://westore.aptamil.com.cn/m/passport-login-http%3a%2f%2fwestore.aptamil.com.cn%3fs%3dtznq007.html?utm_source=A713";
//		String encoded = URLEncoder.encode(url, "utf8");
//		System.out.println(encoded);
		
		String decoded = URLDecoder.decode(url, "utf8");
		System.out.println(decoded);

	}

}
