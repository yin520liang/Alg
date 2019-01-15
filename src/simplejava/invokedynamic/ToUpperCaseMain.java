/**
 * 
 */
package simplejava.invokedynamic;

import java.io.IOException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.nio.file.Files;
import java.nio.file.Paths;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

/**
 * @title
 * @description
 */
public class ToUpperCaseMain {
//	private static final MethodHandle BSM = new MethodHandle(MH_INVOKESTATIC,
//			ToUpperCase.class.getName().replace('.', '/'), "bootstrap",
//			MethodType.methodType(CallSite.class, Lookup.class, String.class, MethodType.class, String.class)
//					.toMethodDescriptorString());
//
//	public static void main(String[] args) throws IOException {
//		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
//		cw.visit(V1_7, ACC_PUBLIC | ACC_SUPER, "ToUpperCaseMain", null, "java/lang/Object", null);
//		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
//		mv.visitCode();
//		mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//		mv.visitInvokeDynamicInsn("toUpperCase", "()Ljava/lang/String;", BSM, "Hello");
//		mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
//		mv.visitInsn(RETURN);
//		mv.visitMaxs(0, 0);
//		mv.visitEnd();
//		cw.visitEnd();
//
//		Files.write(Paths.get("ToUpperCaseMain.class"), cw.toByteArray());
//	}
}
