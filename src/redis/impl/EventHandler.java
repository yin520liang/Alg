/**
 * 
 */
package redis.impl;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @title EventHandler
 */
public interface EventHandler {
	
	void handle(EventDispatcher dispatche, SelectionKey key) throws IOException;

}
