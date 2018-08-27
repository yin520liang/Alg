package http;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MultiThreadTest {
	
	private static String prefix = "http://localhost:8080/babel-api/";
	
	private static String suffix = "?pageNum=1&pageSize=5";
	
	private static String[] urls = {
			"advertiser/list",
			"agency/list",
			"brand/list",
			"goods/list",
			"channel/list",
			"spotsType/list",
			"play/list"
	};
	
	private static Random rand = new Random(43);

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(20);
		for(int i = 0; i < 20; ++i) {
			Runnable task = new GetTask(i);
			executor.execute(task);
		}
		executor.shutdown();
	}
	
	static class GetTask implements Runnable {		

		private int id;
		
		GetTask(int i) {
			this.id = i;
		}
		
		@Override
		public void run() {
			String url = prefix + urls[rand.nextInt(urls.length)] + suffix;
			System.out.println("Thread-" + id + " visit " + url);
			System.out.println(HttpClientUtils.doGet(url, null));	
		}		
	}

}
