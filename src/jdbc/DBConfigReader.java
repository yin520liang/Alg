/**
 * 
 */
package jdbc;

import java.io.IOException;
import java.util.Properties;

/**
 * @title DBConfigReader
 */
public class DBConfigReader {
	
	private final static String DEFAULT_CONFIG = "resources/config/db.config";
	
	private Properties props;

	
	public static DBConfigReader load() {
		return load(DEFAULT_CONFIG);
	}
	
	public static DBConfigReader load(String path) {
		DBConfigReader reader = new DBConfigReader();
		reader.props = new Properties();
		try {
			reader.props.load(DBConfigReader.class.getResourceAsStream(path));
		} catch (IOException e) {
			
		}
		return reader;
	}
	
	
	public String getUrl(String instance) {
		return props.getProperty(propertyName(instance, "url"));
	}
	
	public String getPassword(String instance) {
		return props.getProperty(propertyName(instance, "password"));
	}
	
	public String getDb(String instance) {
		return props.getProperty(propertyName(instance, "name"));
	}
	
	public String getUser(String instance) {
		return props.getProperty(propertyName(instance, "user"));
	}


	private String propertyName(String instance, String key) {
		return String.format("db.%s.%s", instance, key);
	}
	
}
