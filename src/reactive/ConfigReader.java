/**
 * 
 */
package reactive;

import java.io.IOException;
import java.util.Properties;

/**
 * @title DBConfigReader
 */
public class ConfigReader {
	
	private final static String DEFAULT_CONFIG = "rx.config";
	
	private Properties props;

	
	public static ConfigReader load() {
		return load(DEFAULT_CONFIG);
	}
	
	public static ConfigReader load(String path) {
		ConfigReader reader = new ConfigReader();
		reader.props = new Properties();
		try {
			reader.props.load(ConfigReader.class.getResourceAsStream(path));
		} catch (IOException e) {
			
		}
		return reader;
	}
	
	
	public String getRootUrl() {
		return props.getProperty("token.root.url");
	}
	
	public String getClientId() {
		return props.getProperty("token.client.id");
	}
	
	public String getClientSecret() {
		return props.getProperty("token.client.secret");
	}
	
	public String getGrantType() {
		return props.getProperty("token.grant.type");
	}

}
