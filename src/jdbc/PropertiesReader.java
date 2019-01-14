/**
 * 
 */
package jdbc;

import java.io.IOException;
import java.util.Properties;

/**
 * @title PropertiesReader
 * @author lvzhaoyang
 * @date 2019年1月14日
 */
public class PropertiesReader {
	
	public static Properties load(String path) {
		Properties props = new Properties();
		try {
			props.load(PropertiesReader.class.getResourceAsStream(path));
		} catch (IOException e) {
			
		}
		return props;
	}

}
