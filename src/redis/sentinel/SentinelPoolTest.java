/**
 * 
 */
package redis.sentinel;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * @title SentinelPoolTest
 */
public class SentinelPoolTest {


	public static void main(String[] args) {
		Set<String> sentinels = new HashSet<> ( );
		sentinels.add("127.0.0.1:26464");
		sentinels.add("127.0.0.1:26565");
		JedisSentinelPool pool = new JedisSentinelPool("localmaster", sentinels, "stupid");
		try(Jedis jedis = pool.getResource()) {
			String msg = jedis.get("msg");
			System.out.println(msg);
		}
		pool.close();
		
	}

}
