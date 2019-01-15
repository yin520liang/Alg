/**
 * 
 */
package redis;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisShardInfo;

/**
 * @Title RedisPubSubMQTest
 * @Description 由专门的线程作为poller，订阅特定频道的消息，在收到消息后去特定队列（此处为list，若强调有序性可以用zset）取出消息，交给woker线程池中的某个线程处理
 */
public class RedisPubSubMQTest{

	
	private ExecutorService threadPool;
	
	private String key;
	
	private Jedis pollerJedis;
	
	public RedisPubSubMQTest(String key, JedisShardInfo info) {
		this.key = key;
		this.threadPool = Executors.newFixedThreadPool(5);
		pollerJedis = new Jedis(info);
	}

	
	
	public void run() {
		while(true) {
			List<String> data = pollerJedis.brpop(0, key);
			System.out.println("poll message: " + data);
			if(data.size() > 1) {
				threadPool.execute(new ConsumerTask(data.subList(1, data.size())));
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// props
		String host = "127.0.0.1";
		int port = 6379;
		String password = "stupid";
		String queue = "msqueue";
		
		JedisShardInfo info = new JedisShardInfo(host, port);
		info.setPassword(password);

		new RedisPubSubMQTest(queue, info).run();

	}

	class ConsumerTask implements Runnable {

		private List<String> data;

		ConsumerTask(List<String> data) {
			this.data = data;
		}

		public void run() {
			for(String msg : data ) {
				System.out.println(String.format("Consume message - %s", msg));
			}
		}
	}

}
