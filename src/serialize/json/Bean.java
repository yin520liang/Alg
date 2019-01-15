/**
 * 
 */
package serialize.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Title Bean
 * @Description
 */
public class Bean {

	public String key;
	public String title;
	public String[] values;
	public String defaultValue;

	public static Bean fromJsonString(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		Bean bean = new Bean();
		bean.key = jsonObject.optString("key");
		bean.title = jsonObject.optString("title");
		JSONArray jsonArray = jsonObject.optJSONArray("values");
		if (jsonArray != null && jsonArray.size() > 0) {
			int len = jsonArray.size();
			bean.values = new String[len];
			for (int i = 0; i < len; ++i) {
				bean.values[i] = jsonArray.getString(i);
			}
		}
		bean.defaultValue = jsonObject.optString("defaultValue");
		return bean;
	}

	public static String toJsonString(Bean bean) {
		if (bean == null) {
			return null;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", bean.key);
		jsonObject.put("title", bean.title);
		if (bean.values != null) {
			JSONArray array = new JSONArray();
			for (String str : bean.values) {
				array.add(str);
			}
			jsonObject.put("values", array);
		}
		jsonObject.put("defaultValue", bean.defaultValue);

		return jsonObject.toString();
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		String a = "{\"key\":\"123\", \"title\":\"asd\", \"values\":[\"a\", \"b\", \"c\", \"d\"], \"defaultValue\":\"a\"}";
		Bean bean = Bean.fromJsonString(a);
		ObjectMapper mapper = new ObjectMapper();
		
		long now = System.currentTimeMillis();
		for (int i = 0; i < 1000; ++i) {
			Bean.fromJsonString(a);
		}
		log("time", "jsonObject parse use time =" + (System.currentTimeMillis() - now));
		
		now = System.currentTimeMillis();
		for (int i = 0; i < 1000; ++i) {
			mapper.readValue(a, Bean.class);
		}
		log("time", "jackson parse use time =" + (System.currentTimeMillis() - now));
		
		now = System.currentTimeMillis();
		for (int i = 0; i < 1000; ++i) {
			Bean.toJsonString(bean);
		}
		log("time", "jsonObject tojson use time =" + (System.currentTimeMillis() - now));
	
		now = System.currentTimeMillis();
		for (int i = 0; i < 1000; ++i) {
			mapper.writeValueAsString(bean);
		}
		log("time", "jackson tojson use time =" + (System.currentTimeMillis() - now));
	}

	private static void log(String title, String msg) {
		System.out.println(title + ":: " + msg);
	}
}
