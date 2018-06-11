/**
 * 
 */
package redis;

import java.net.URL;

/**
 * @Title GetResourceTest
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月29日
 */
public class GetResourceTest {

	/**
	 * @Description 
	 * @Author lvzhaoyang
	 * @Date 2018年5月29日 
	 */
	public static void main(String[] args) {
		String name = "wiki-horspool.txt";
		URL url = GetResourceTest.class.getClassLoader().getSystemResource(name);
		System.out.println(url);

	}

}
