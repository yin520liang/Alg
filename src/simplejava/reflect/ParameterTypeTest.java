/**
 * 
 */
package simplejava.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @title ParameterTypeTest
 * @author lvzhaoyang
 * @date 2018年8月6日
 */
public class ParameterTypeTest {

	private List<String> testList = new ArrayList<>(1);

	/**
	 * @author lvzhaoyang
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @date 2018年8月6日
	 */
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
//		Field[] fields = ParameterTypeTest.class.getDeclaredFields();
//		for(Field f : fields) {
//			if(List.class.isAssignableFrom(f.getType())) {
//				ParameterizedType pt = (ParameterizedType) f.getGenericType();
//				System.out.println(pt.getActualTypeArguments().length);
//				System.out.println(pt.getActualTypeArguments()[0]);
//
//			}
//		}
		
		System.out.println(getterMethodName("industry_1"));

	}
	
	
	private static String getterMethodName(String fieldName) {
		return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

}
