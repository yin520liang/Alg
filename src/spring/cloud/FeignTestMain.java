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
	
	private static String rooturl;
	
	private static String clientId;
	
	private static String clientSecret;
	
	private static String grantType;

	public static void main(String[] args) {
		initParameters();		
		AccessTokenApi tokenClient = Feign.builder()
										  .logger(new Slf4jLogger())
										  .mapAndDecode((response, type) -> unwrapResponse(response, type), new JacksonDecoder())
										  .target(AccessTokenApi.class, rooturl);
		// 2
		Map<String, String> params = new HashMap<> (4);
		params.put("client_id", clientId);
		params.put("client_secret", clientSecret);
		params.put("grant_type", grantType);
		Token token = tokenClient.token(params);

		log.info("Applied token: {}", token);
	}


	private static void initParameters() {
		ConfigReader cfg = ConfigReader.load();
		rooturl = cfg.getRootUrl();
		clientId = cfg.getClientId();
		clientSecret = cfg.getClientSecret();
		grantType = cfg.getGrantType();
		
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


