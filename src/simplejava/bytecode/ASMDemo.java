package simplejava.bytecode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.objectweb.asm.Opcodes;

import aj.org.objectweb.asm.ClassWriter;
import aj.org.objectweb.asm.MethodVisitor;

public class ASMDemo {

	public static void main(String[] args) throws Exception {

		System.out.println();
		ClassWriter classWriter = new ClassWriter(0);
		// 通过visit方法确定类的头部信息
		classWriter.visit(Opcodes.V1_8, // java版本
						  Opcodes.ACC_PUBLIC, // 类修饰符
						  "ASM$Programm", // 类的全限定名
						  null, 
						  "java/lang/Object", 
						  null);

		// 创建构造函数
		MethodVisitor mv = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();

		// 定义code方法
		MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "code", "()V", null, null);
		methodVisitor.visitCode();
		methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		methodVisitor.visitLdcInsn("I'm a Programmer,Just Coding.....");
		methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
		methodVisitor.visitInsn(Opcodes.RETURN);
		methodVisitor.visitMaxs(2, 2);
		methodVisitor.visitEnd();
		classWriter.visitEnd();

		byte[] data = classWriter.toByteArray();
		// 写入文件便于查看
		FileOutputStream fout = new FileOutputStream(new File(".").getAbsolutePath() + 
				"//target//classes//simplejava//bytecode//ASM$Programm.class");
		fout.write(data);
		fout.close();
		
		// 加载
		CustomClassLoader classLoader = new CustomClassLoader();
		Class<?> clazz = classLoader.customDefineClass(data, 0, data.length);		
		Object p = clazz.newInstance();
		clazz.getMethod("code", (Class[]) null).invoke(p, (Object[]) null);
	}

}
