package simplejava.bytecode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

public class DynamicProxy {
	
	public static void main(String[] args) {
		Programm p = new Programm();
		
		InvocationHandler handler = new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("You are going to invoke " + method.getName() + " ...");
				Object res = method.invoke(p, args);
				System.out.println(method.getName() + " invocation is finished...");
				return res;
			}
			
		};
		
		CodeInterface ci = (CodeInterface) Proxy.newProxyInstance(
				DynamicProxy.class.getClassLoader(), new Class[] {CodeInterface.class}, handler);
		ci.code();
		
		
		String className = "JavaProxy$" + p.getClass().getSimpleName();
		try(FileOutputStream out = new FileOutputStream(new File(".").getAbsolutePath() + "\\target\\classes\\simplejava\\bytecode\\" + className)) {
			byte[] classData = ProxyGenerator.generateProxyClass(className, new Class[] {CodeInterface.class});
			out.write(classData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
