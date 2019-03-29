package simplejava.bytecode;

public class CustomClassLoader extends ClassLoader {
	
	/**
	 * 显示调用类加载方法
	 * @param b
	 * @param off
	 * @param len
	 * @return
	 */
	 protected final Class<?> customDefineClass(byte[] b, int off, int len) {
		 return super.defineClass(null, b, off, len);
	 }

}
