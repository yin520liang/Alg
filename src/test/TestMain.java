/**
 * 
 */
package test;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年12月21日上午9:31:50
 *
 */
public class TestMain {

	/**
	 * @author lvzhaoyang
	 * @date 2017年12月21日上午9:31:50
	 *
	 * @param args
	 * @throws Base64DecodingException 
	 */
	public static void main(String[] args) {
		
//		for(Type type : B.class.getGenericInterfaces()) {
//			System.out.println(type.getTypeName());
//			try { 
//				ParameterizedType ptype = (ParameterizedType) type;
//				Class<?> c = (Class<?>) ptype.getActualTypeArguments()[0];
//				System.out.println(c.getTypeName());
//			}catch(ClassCastException e) {
//				System.out.println("class cass failed.");
//			}
//		}
//		
//		for(Method m : B.class.getMethods()) {
//			System.out.println(m.getName());
//		}
//		String pwd = "mz@123456";
		System.out.println("defaultGroup".hashCode());
	}

	public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	static class Inner {
		
		public void test() {
			System.out.println(this);
		}
	}
}
