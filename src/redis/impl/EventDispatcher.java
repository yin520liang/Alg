/**
 * 
 */
package redis.impl;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @title EventDispatcher
 * @author lvzhaoyang
 * @date 2018年11月23日
 */
public class EventDispatcher {
	
	private Selector selector;
	
	private final int delay = 100; // wait 100ms
	
	private Map<EventType, EventHandler> handlerMap;
	
	
	public EventDispatcher(Selector selector) {
		this.selector = selector;
		handlerMap = new HashMap<> ( );
	}

	
	public void handleEvents() throws IOException {
		if(selector.select(delay) > 0) {
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			while(it.hasNext()) {
				SelectionKey key = (SelectionKey) it.next();			
				if(key.isAcceptable()) {
					handlerMap.get(EventType.ACCEPT).handle(this, key);
				}
				if(key.isReadable()) {
					handlerMap.get(EventType.READ).handle(this, key);
				}
			}
		}
	}
	
	
	public void registerChannel(SelectableChannel ch, int ops, Object attach) throws IOException {
		if(ch == null)
			return;
		ch.configureBlocking(false);
		ch.register(selector, ops, attach);
	}
	
	public void registerChannel(SelectableChannel ch, int ops) throws IOException {
		registerChannel(ch, ops, null);
	}
	
	
	public void registerHandler(EventType type, EventHandler handler) {
		handlerMap.put(type, handler);
	}
	
	public void unregisterHandler(EventType type) {
		handlerMap.put(type, null);
	}
	
	
	public enum EventType {
		ACCEPT,
		READ
	}

}
