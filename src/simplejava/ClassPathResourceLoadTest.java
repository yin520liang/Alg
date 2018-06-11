/**
 * 
 */
package simplejava;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

/**
 * @Title ClassPathResourceLoadTest
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月9日
 */
public class ClassPathResourceLoadTest {

	/**
	 * @throws IOException 
	 * @Description 
	 * @Author lvzhaoyang
	 * @Date 2018年5月9日 
	 */
	public static void main(String[] args) throws IOException {
		ClassLoader cl = ClassPathResourceLoadTest.class.getClassLoader();
		String path = "simplejava/concurrent";
		Enumeration<URL> resourceUrls = cl.getResources(path);
		while(resourceUrls.hasMoreElements()) {
			URL url = resourceUrls.nextElement();
			File f = new File(getFilePath(url.getPath()));
			retrieveAllFiles(f);
		}
		
//		for (URL url : ((URLClassLoader) cl).getURLs()) {
//			System.out.println(url);
//		}
		
		System.out.println(File.pathSeparator);
		System.out.println(File.separator);
	}
	
	
	private static String getFilePath(String s) {
		if(s.startsWith("/")) {
			s = s.substring(1);
		}
		return s;
	}
	
	private static void retrieveAllFiles(File parent) {
		if(parent.isDirectory()) {
			File[] subFiles = parent.listFiles();
			for(File sf : subFiles) {
				retrieveAllFiles(sf);
			}
		}else {
			System.out.println(parent.getName());
		}
	}

}
