package nio;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 
 * @author MZCN501A0145
 *
 */
public class FilesTest {

	public static void main(String[] args) throws IOException {
//		test1();
		testWalkFileTree();

	}

	static void test1() {
		Path path = Paths.get("data/subdir");
		try {
			Path newDir = Files.createDirectory(path);
		} catch (FileAlreadyExistsException e) {
			// the directory already exists.
		} catch (IOException e) {
			// something else went wrong
			e.printStackTrace();
		}
	}

	static void testWalkFileTree() throws IOException {
		Path path = Paths.get("E:\\BaiduNetdiskDownload\\");
		Files.walkFileTree(path, new FileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {
				System.out.println("pre visit dir:" + dir);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				System.out.println("visit file: " + file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc)
					throws IOException {
				System.out.println("visit file failed: " + file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc)
					throws IOException {
				System.out.println("post visit directory: " + dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

}
