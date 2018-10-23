/**
 * 
 */
package redis;

import java.util.Arrays;
import java.util.List;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;

/**
 * @Title PipelineTest
 * @Description redis批量操作示例
 * 使用场景
 * @Author lvzhaoyang
 * @Date 2018年4月9日
 */
public class PipelineTest {

	private static int count = 10000;

	public static void main(String[] args) {
		normal();
		pipeline();
	}

	private static void normal() {
		// shard info
		JedisShardInfo shard0 = new JedisShardInfo("127.0.0.1", 6379);
		shard0.setPassword("stupid");
		List<JedisShardInfo> infos = Arrays.asList(shard0);
		// jedis client
		long start;
		try (ShardedJedis shardedJedis = new ShardedJedis(infos)) {
			start = System.currentTimeMillis();
			for (int i = 0; i < count; ++i) {
				shardedJedis.set("key_" + i, "value_" + i);
			}
		}
		System.out.println("normal: " + (System.currentTimeMillis() - start)
				* 1.0 / 1000);

	}

	private static void pipeline() {
		// shard info
		JedisShardInfo shard0 = new JedisShardInfo("127.0.0.1", 6379);
		shard0.setPassword("stupid");
		List<JedisShardInfo> infos = Arrays.asList(shard0);
		// jedis client		
		long start;
		try (ShardedJedis shardedJedis = new ShardedJedis(infos)) {
			ShardedJedisPipeline pipelined = shardedJedis.pipelined();
			start = System.currentTimeMillis();
			for (int i = 0; i < count; ++i) {
				pipelined.set("key_" + i, "value_" + i);
			}
			pipelined.sync();
		}
		System.out.println("pipeline: " + (System.currentTimeMillis() - start) * 1.0 / 1000);
	}

}
