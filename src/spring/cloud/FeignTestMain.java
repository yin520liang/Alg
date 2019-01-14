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
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.util.StreamUtils;

import com.alibaba.fastjson.JSONObject;

import feign.Feign;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import feign.Response;
import feign.jackson.JacksonDecoder;
import feign.slf4j.Slf4jLogger;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import spring.cloud.AccessTokenApi.Token;
import spring.cloud.AccessTokenApi.TokenApplyParamDto;

/**
 * Feign使用测试，申请access_token
 * 
 * @title FeignTestMain
 */
@Slf4j 
public class FeignTestMain {

	public static void main(String[] args) {
		AccessTokenApi tokenClient = Feign.builder()
										  .logger(new Slf4jLogger())
										  .mapAndDecode((response, type) -> unwrapResponse(response, type), new JacksonDecoder())
										  .target(AccessTokenApi.class, "https://api.cn.miaozhen.com");
		// 1
//		Token token = tokenClient.token("mz-babel", "e4a4b54a-da89-4c34-a6b4-612e312b685b");
		// 2
		Map<String, String> params = new HashMap<> (4);
		params.put("client_id", "mz-babel");
		params.put("client_secret", "e4a4b54a-da89-4c34-a6b4-612e312b685b");
		params.put("grant_type", "client_credentials");
		Token token = tokenClient.token(params);
		
//		TokenApplyParamDto dto = new TokenApplyParamDto("mz-babel", "e4a4b54a-da89-4c34-a6b4-612e312b685b");
//		Token token = tokenClient.token(dto);
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


interface AccessTokenApi {
	// 1. use @Param to format GET url
	@RequestLine("GET /passport/token?grant_type=client_credentials&client_id={clientId}&client_secret={secret}")
	Token token(@Param("clientId") String clientId, @Param("secret") String secret);
	// 2. use Map
	@RequestLine("GET /passport/token")
	Token token(@QueryMap Map<String, String> params);
	// 3. use 
	@RequestLine("GET /passport/token")
	Token token(@QueryMap TokenApplyParamDto param);
	
	@Data
	class Token {
		String access_token;
		String token_type;
		int expires_in;
		String scope;

		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
	}
	
	@Data
	class TokenApplyParamDto {
		final String grant_type = "client_credentials";
		String client_id;
		String client_secret;
		
		public TokenApplyParamDto(String id, String secret) {
			client_id = id;
			client_secret = secret;
		}
		
	}

}

