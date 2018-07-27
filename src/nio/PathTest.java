package nio;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Java NIO interface - Path (added in  Java 7)
 * @author MZCN501A0145
 *
 */
public class PathTest {

	public static void main(String[] args) {
		Path currentDir = Paths.get(".");
		System.out.println(currentDir.toAbsolutePath());
		System.out.println(currentDir.getFileSystem());
		
		Path parentDir = Paths.get("..");
		System.out.println(parentDir.toAbsolutePath());
		
		String originalPath ="d:\\data\\projects\\a-project\\..\\another-project";

		Path path1 = Paths.get(originalPath);
		System.out.println("path1 = " + path1);

		Path path2 = path1.normalize();
		System.out.println("path2 = " + path2);
	}

}
