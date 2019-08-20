package simplejava.bytecode;

import java.io.File;
import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
/**
 * Javassist使用示例
 * Javassist可以方便的生成字节码层面的代理
 * @title JavassistDemo
 */
public class JavassistDemo {

	public static void main(String[] args) {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass("simplejava.bytecode.Javassist$Programm");
		try {
			CtMethod method = CtNewMethod.make("public void code(){}", cc);
			method.insertBefore("System.out.println(\"I'm a Programmer, Just Coding.....\");");
			cc.addMethod(method);
			cc.writeFile(new File(".").getAbsolutePath() + "\\target\\classes\\");
		
		} catch (CannotCompileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
