/**
 * 
 */
package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
	 * @throws ParseException 
	 * @throws Base64DecodingException 
	 */
	public static void main(String[] args) throws ParseException {
		
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
//		char[] cs = "品牌篇".toCharArray();
//		System.out.println(Integer.toHexString((int) cs[0]));
//		System.out.println(Integer.toHexString((int) cs[1]));
//		Integer a1 = 3;
//		Integer a2 = 3;
//		Integer a3 = 300;
//		Integer a4 = 300;
//		System.out.println(a1 == a2);
//		System.out.println(a3 == a4);
//		Date d1 = new Date(1535990400000L);
//		System.out.println(d1.toString());
//		Date d2 = new Date(0L);
//		System.out.println(d2.toString());
		
		int a = 1;
		System.out.println(Number.class.isAssignableFrom(int.class));
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
