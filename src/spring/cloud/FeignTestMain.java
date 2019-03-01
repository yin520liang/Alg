/**
 * 
 */
package spring.cloud;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.Charsets;
import org.springframework.util.StreamUtils;

import com.alibaba.fastjson.JSONObject;

import feign.Feign;
import feign.Response;
import feign.jackson.JacksonDecoder;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import reactive.ConfigReader;
import spring.cloud.AccessTokenApi.Token;

/**
 * Feign使用测试，申请access_token
 * 
 * @title FeignTestMain
 */
@Slf4j 
public class FeignTestMain {

	public static void main(String[] args) {
		ConfigReader cfg = ConfigReader.load();
		AccessTokenApi tokenClient = Feign.builder()
										  .logger(new Slf4jLogger())
										  .mapAndDecode((response, type) -> unwrapResponse(response, type), new JacksonDecoder())
										  .target(AccessTokenApi.class, cfg.getRootUrl());
		// 2
		Map<String, String> params = new HashMap<> (4);
		params.put("client_id", cfg.getClientId());
		params.put("client_secret", cfg.getClientSecret());
		params.put("grant_type", cfg.getGrantType());
		Token token = tokenClient.token(params);

		log.info("Applied token: {}", token);
	}



	private static Response unwrapResponse(Response response, Type type) {
		JSONObject json;
		try(InputStream is = response.body().asInputStream()) {
			json = JSONObject.parseObject(StreamUtils.copyToString(is, Charsets.UTF_8));
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
//		log.info("Response: {}", json.toJSONString());
		int code = json.getIntValue("code");
		String msg = json.getString("msg");
		if (code != 0) {
			throw new RuntimeException(msg);
		}
		return response.toBuilder().body(json.getJSONObject("data").toJSONString(), Charsets.UTF_8).build();
	}

}


