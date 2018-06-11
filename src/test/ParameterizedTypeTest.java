/**
 * 
 */
package test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Title ParameterizedTypeTest
 * @Description 泛型示例
 * @Author lvzhaoyang
 * @Date 2018年1月10日
 */
public class ParameterizedTypeTest {

	/**
	 * @Description
	 * @Author lvzhaoyang
	 * @Date 2018年1月10日
	 */
	public static void main(String[] args) {
		FooChild fc = new FooChild();
		Type[] pts = getParameterizedTypes(fc);
		for(Type t : pts) {
			System.out.println(t.getTypeName());
		}
	}

	public static Type[] getParameterizedTypes(Object object) {
		Type superclassType = object.getClass().getGenericSuperclass();
		if (!ParameterizedType.class
				.isAssignableFrom(superclassType.getClass())) {
			return null;
		}
		return ((ParameterizedType) superclassType).getActualTypeArguments();
	}

}

/**
 * 
 * 对于直接创建类似Foo<Integer> foo这样的对象，只能通过强制传入class对象的方式知道泛型类型
 */
class Foo<T> {
	private Class<T> tClass;

	public Foo(Class<T> tClass) {
		this.tClass = tClass;
	}
	// content
}

class FooChild extends Foo<String> {

	public FooChild() {
		super(String.class);
	}

}