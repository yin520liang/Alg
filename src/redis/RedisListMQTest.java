/**
 * 
 */
package redis;

import java.util.List;
import java.util.Random;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * @Title RedisListMQTest
 * @Description 使用redis的list结构做消息队列的demo
 *              <p>
 *              <a
 *              href="http://www.runoob.com/redis/redis-lists.html">list相关操作</a>
 * @Author lvzhaoyang
 * @Date 2018年5月30日
 */
public class RedisListMQTest {
	
	private static String host = "127.0.0.1";
	
	private static int port = 6379;
	
	private static String password = "stupid";
	

	public static void main(String[] args) throws InterruptedException {
		JedisShardInfo info = new JedisShardInfo(host, port);
		info.setPassword(password);
		
		int n = 4;
		Thread[] producers = new Thread[n];
		Thread[] consumers = new Thread[n];
		for(int i = 0; i < producers.length; ++i) {
			producers[i] = new Thread(new ProducerTask(String.valueOf(i), info));
//			consumers[i] = new Thread(new ConsumerTask(String.valueOf(i), info));
			producers[i].start();
//			consumers[i].start();
		}
		
	}

}


class ProducerTask implements Runnable {
	
	private Jedis jedis;
	
	private String name;
	
	private Random rand = new Random(43);
	
	ProducerTask(String name, JedisShardInfo info) {			
		this.jedis = new Jedis(info);
		this.name = name;
	}

	@Override
	public void run() {
		for(int i = 0; i < 10; ++i) {
			String val = name + "-" + rand.nextInt(100);
			jedis.lpush("messages", val);
			System.out.println("Producer-[" + name +"] produce " + val);
		}
		jedis.close();
	}
	
}

class ConsumerTask implements Runnable {
	
	private Jedis jedis;
	
	private String name;
	
	ConsumerTask(String name, JedisShardInfo info) {			
		this.jedis = new Jedis(info);
		this.name = name;
	}

	@Override
	public void run() {
		while(true) {
			List<String> items = jedis.brpop(3, "messages");
			if(items == null || items.size() < 1)
				break;
			System.out.println("Consumer-[" + name +"] consume " + items);
		}
		jedis.close();
	}
	
}
