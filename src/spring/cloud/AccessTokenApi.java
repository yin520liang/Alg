package spring.cloud;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import lombok.Data;

public interface AccessTokenApi {
	// 1. use @Param to format GET url
	@RequestLine("GET /passport/token?grant_type={grantType}&client_id={clientId}&client_secret={secret}")
	Token token(@Param("grantType") String grantType, 
				@Param("clientId") String clientId, 
				@Param("secret") String secret);

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
		String grant_type;
		String client_id;
		String client_secret;

		public TokenApplyParamDto(String grantType, String id, String secret) {
			grant_type = grantType;
			client_id = id;
			client_secret = secret;
		}

	}

}
