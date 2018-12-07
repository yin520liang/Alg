/**
 * 
 */
package redis.impl;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @title EventHandler
 * @author lvzhaoyang
 * @date 2018年11月23日
 */
public interface EventHandler {
	
	void handle(EventDispatcher dispatche, SelectionKey key) throws IOException;

}
