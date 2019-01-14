/**
 * 
 */
package redis.sentinel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * @title SentinelJedisPool
 * @author lvzhaoyang
 * @date 2018年12月14日
 */
public class SentinelJedisPool {
	
	private String service; // 服务名称，一个服务使用一个SentinelJedisPool
	
	private JedisPool pool;
	
	private HostAndPort currentMaster;
	
	private List<HostAndPort> sentinels;
	private List<Jedis> sentinelClients;
	
	private Thread masterChangeListener; // 监听+switch-master事件
	
	private Thread sentinelUpdater; // 监听+sentinel事件
	
	
	public SentinelJedisPool(String service, List<String> sentinelAddress) {
		this.service = service;
		initSentinels(sentinelAddress);
		// start listeners
		masterChangeListener = new MasterChangeListener();
		masterChangeListener.start();
	}
	

	private void initSentinels(List<String> sentinelAddress) {
		sentinels = new CopyOnWriteArrayList< > ( );
		for(String addr : sentinelAddress) {
			sentinels.add(HostAndPort.parseString(addr));
		}
	}



	public JedisPool getPool() {
		return pool;
	}

	
	class MasterChangeListener extends Thread {
		AtomicBoolean running = new AtomicBoolean(false);
		
		public void run() {
			while(running.get()) {
				if(currentMaster == null) {
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
					}
					continue;
				}
				
				Jedis client = null;
				try {
					client = new Jedis(currentMaster.getHost(), currentMaster.getPort());
					client.subscribe(new JedisPubSub() {
						
					}, "+sentinel", "+switch-master");
				} finally {
					if(client != null)
						client.close();
				}
			}
		}
		
		public void close() {
			running.set(false);
		}
	}

	
}
