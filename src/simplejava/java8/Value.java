/**
 * 
 */
package simplejava.java8;

/**
 * @title
 * @description
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
