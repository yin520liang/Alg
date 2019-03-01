package reactive;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.codec.Charsets;
import org.springframework.util.StreamUtils;

import com.alibaba.fastjson.JSONObject;

import feign.Feign;
import feign.Response;
import feign.jackson.JacksonDecoder;
import feign.slf4j.Slf4jLogger;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import spring.cloud.AccessTokenApi;
import spring.cloud.AccessTokenApi.Token;

public class RxJavaTest {

	public static void main(String[] args) throws InterruptedException {
		RxJavaTest rx = new RxJavaTest( );
//		rx.deferredDependent();
		rx.simple();
//		rx.httpRequest();
		
	}
	
	private void simple() {
		Observable.range(1,  10) // 创建一个ObervableRange
			.map(x -> {
				System.out.println("In Map:" + Thread.currentThread().getName());
				return x * x;
			})  // 以代理模式转换成ObservableMap
			.subscribe(t -> {
				System.out.println("In Observer:" + Thread.currentThread().getName());
				System.out.println(t);
			}); // 触发实际的代码执行，此时执行的是ObservableMap.subscribe
		// 所有Observable的订阅方法使用模板方法，实际业务代码在subscribeActual()中
		// ObservableMap封装上游的Observable，并注册一个MapObserver以装饰器模式封装原Observer（此例中为输出函数）
		// 即最后执行subscribe时，执行的实际上是ObservableRange.subscribeActual(new MapObserver(observer))
	}
	
	private void deferredDependent() {
		AtomicInteger count = new AtomicInteger(0);
		Observable.range(1,  10)
					.doOnNext(i -> count.incrementAndGet())
					.subscribe(System.out::println);
	}
	
	private void httpRequest() throws InterruptedException {
		ConfigReader cfg = ConfigReader.load();
		AccessTokenApi tokenClient = Feign.builder()
				  .logger(new Slf4jLogger())
				  .mapAndDecode((response, type) -> unwrapResponse(response, type), new JacksonDecoder())
				  .target(AccessTokenApi.class, cfg.getRootUrl());
		
		System.out.println("In Main: " + Thread.currentThread().getName());
		Observable.fromCallable(() -> {
				Token token = tokenClient.token(cfg.getGrantType(), 
												cfg.getClientId(), 
												cfg.getClientSecret());
				System.out.println("In Callable: " + Thread.currentThread().getName());
				return token.getAccess_token();
			})     // 返回ObservableFromCallable
			.subscribeOn(Schedulers.single()) // 返回ObservableSubscribeOn
			.observeOn(Schedulers.io())
			.subscribe(t -> {
				System.out.println("In Observer: " + Thread.currentThread().getName());
				System.out.println(t);
			}, Throwable::printStackTrace);
		// 调用subscribe时调用的是ObservableSubscribeOn.subscribeActual，使用SubscribeOnObserver封装observer，
		// 同时新建一个runnable提交到指定的线程池执行
		// runnable的内容为调用上游（即ObservableFromCallable）的subscribe(new SubscribeOnObserver(observer))
		// 即若只使用subscribeOn，则会改变所有操作执行所在的线程
		TimeUnit.SECONDS.sleep(3);
	}

	private static Response unwrapResponse(Response response, Type type) {
		JSONObject json;
		try(InputStream is = response.body().asInputStream()) {
			json = JSONObject.parseObject(StreamUtils.copyToString(is, Charsets.UTF_8));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
		int code = json.getIntValue("code");
		String msg = json.getString("msg");
		if (code != 0) {
			throw new RuntimeException(msg);
		}
		return response.toBuilder().body(json.getJSONObject("data").toJSONString(), Charsets.UTF_8).build();
	}
}
