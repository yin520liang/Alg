/**
 * 
 */
package redis;

import java.util.Arrays;
import java.util.List;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

/**
 * 客户端sharding redis实现：ShardedJedis
 * @Title ShardedJedisTest
 */
public class ShardedJedisTest {


	public static void main(String[] args) {
		// shard info
		JedisShardInfo shard0 = new JedisShardInfo("127.0.0.1", 6379);
		shard0.setPassword("stupid");
		JedisShardInfo shard1 = new JedisShardInfo("127.0.0.1", 6380);
		shard1.setPassword("stupid");
		List<JedisShardInfo> infos = Arrays.asList(shard0, shard1);
		// jedis client
		try(ShardedJedis shardedJedis = new ShardedJedis(infos)) {
			shardedJedis.set("key1", "123");
			String val = shardedJedis.get("key1");
			System.out.println(val);
		}
		
	}

}
