/**
 * 
 */
package redis.sentinel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisException;

/**
 * 连接到master节点，获取sentinel列表
 * 
 * @title SubSentinelTest
 */
public class SubSentinelTest {

	public static void main(String[] args) {
		HostAndPort hap = new HostAndPort("127.0.0.1", 6379);
		new SentinelUpdateListener(hap).start();
		
		List<HostAndPort> sentinels = new ArrayList<> ( );
		sentinels.add(new HostAndPort("127.0.0.1", 26464));
//		sentinels.add(new HostAndPort("127.0.0.1", 26565));
//		new SwitchMasterListener(sentinels).start();
	}

	public static class SentinelUpdateListener extends Thread {
		private AtomicBoolean running = new AtomicBoolean(false);
		private HostAndPort masterAddr;

		public SentinelUpdateListener(HostAndPort masterAddr) {
			this.masterAddr = masterAddr;
		}

		@Override
		public void run() {
			running.set(true);
			while (running.get()) {
				Jedis jedis = new Jedis(masterAddr.getHost(), masterAddr.getPort());
				jedis.auth("stupid");
				try {
					jedis.subscribe(new JedisPubSub() {

						public void onMessage(String channel, String message) {
							String[] info = message.split(",");
							String ip = info[0];
							String port = info[1];
							String runid = info[2];
							System.out.printf("Sentinel: [%s:%s] - %s\n", ip, port, runid);
						}
					}, "__sentinel__:hello");
				} catch(JedisException e) {
					// do nothing
				} finally {
					jedis.close();
				}
			}
		}
	}

	public static class SwitchMasterListener extends Thread {
		private AtomicBoolean running = new AtomicBoolean(false);
		private List<HostAndPort> sentinels;

		public SwitchMasterListener(List<HostAndPort> sentinels) {
			this.sentinels = sentinels;
		}

		@Override
		public void run() {
			running.set(true);
			while (running.get()) {
				for (HostAndPort hap : sentinels) {
					Jedis jedis = new Jedis(hap.getHost(), hap.getPort());
					try {
						jedis.subscribe(new JedisPubSub() {
							public void onMessage(String channel, String message) {
								String[] info = message.split(" ");
								String masterName = info[0];
								HostAndPort oldAddr = new HostAndPort(info[1], Integer.parseInt(info[2]));
								HostAndPort newAddr = new HostAndPort(info[3], Integer.parseInt(info[4]));
								System.out.printf("'%s' address change from [%s] to [%s]\n", masterName, oldAddr, newAddr);
							}
						}, "+switch-master");
					} catch (JedisException e) {
						
					} finally {
						jedis.close();
					}
				}
			}
		}

	}
}
