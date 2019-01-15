package reactive;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import org.apache.commons.codec.Charsets;
import org.springframework.util.StreamUtils;

import com.alibaba.fastjson.JSONObject;

import feign.Feign;
import feign.Response;
import feign.jackson.JacksonDecoder;
import feign.slf4j.Slf4jLogger;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import spring.cloud.AccessTokenApi;
import spring.cloud.AccessTokenApi.Token;

public class RxJavaTest {

	public static void main(String[] args) throws InterruptedException {
		ConfigReader cfg = ConfigReader.load();
		AccessTokenApi tokenClient = Feign.builder()
				  .logger(new Slf4jLogger())
				  .mapAndDecode((response, type) -> unwrapResponse(response, type), new JacksonDecoder())
				  .target(AccessTokenApi.class, "https://api.cn.miaozhen.com");
		
		Observable.fromCallable(() -> {
				Token token = tokenClient.token(cfg.getGrantType(), 
												cfg.getClientId(), 
												cfg.getClientSecret());
				return token.getAccess_token();
			})
//			.subscribeOn(Schedulers.io())
			.observeOn(Schedulers.single())
//			.subscribe(System.out::println, Throwable::printStackTrace);
			.blockingSubscribe(System.out::println, Throwable::printStackTrace);
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
