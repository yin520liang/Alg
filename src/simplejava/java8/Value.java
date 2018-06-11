/**
 * 
 */
package simplejava.java8;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年9月12日下午4:37:44
 */
public class Value<T> {
	public static <T> T defaultValue() {
		return null;
	}

	public T getOrDefault(T value, T defaultValue) {
		return (value != null) ? value : defaultValue;
	}

	public static void main(String[] args) {
		final Value<String> value = new Value<>();
		value.getOrDefault("22", Value.defaultValue());
	}
}
