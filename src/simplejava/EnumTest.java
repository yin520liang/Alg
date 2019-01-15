/**
 * 
 */
package simplejava;

import java.util.Arrays;

/**
 * @Title EnumTest
 * @Description
 */
public class EnumTest {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
//		Class<DataSourceKey> cl = DataSourceKey.class;
//		if (cl.isEnum()) {
//			DataSourceKey[] keys = cl.getEnumConstants();
//			System.out.println(Arrays.toString(keys));
//		}
//
//		Singleton s = Singleton.INSTANCE;
//		System.out.println(s);
		long ord = 150;
		long mask = 0x3F;
		System.out.println(1L << (ord & mask));
		System.out.println(1L << ord);
	}

	enum DataSourceKey {
		MASTER, SLAVE
	}

	enum Singleton {
		INSTANCE;
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
