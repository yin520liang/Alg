package simplejava.bytecode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassLoadDemo {

	public static void main(String[] args) {
		/*
		 * 加载指定的class文件
		 */
		File projectRootDir = new File(".");
		log.info("Current project root dir: {}", projectRootDir.getPath());
		// 动态生成的class文件的位置
		File classFile = new File(projectRootDir.getAbsolutePath() + 
				"//target//classes//simplejava//bytecode//Programm.class");
		
		try(InputStream fin = new FileInputStream(classFile)) {
			byte[] byteData = new byte[(int) classFile.length()];
			int len = read(fin, byteData);
			// 自定义类加载器
			CustomClassLoader cloader = new CustomClassLoader();
			Class<?> clazz = cloader.customDefineClass(byteData, 0, len);
			
			Object p = clazz.newInstance();
			clazz.getMethod("code", (Class[]) null).invoke(p, (Object[]) null);		
			
		} catch (Exception e) {
			e.printStackTrace();
		}  

	}
	
	
	private static int read(InputStream in, byte[] res) throws IOException {
		byte[] buf = new byte[1024];
		int count;
		int offset = 0;
		while((count = in.read(buf, 0, buf.length)) != -1 ) {
			System.arraycopy(buf, 0, res, offset, count);
			offset += count;
		}
		return offset;
	}

}
