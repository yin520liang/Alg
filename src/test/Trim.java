/**
 * 
 */
package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @Title Trim
 * @Description
 */
public class Trim {

	private static final String regEx_quot = "&[a-z|0-9|#]{2,};"; // html转义字符

	public static void main(String[] args) {
		// String text =
		// "I love&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;"
		// +
		// "&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;&#161;";

		// Pattern p_w = Pattern.compile(regEx_quot, Pattern.CASE_INSENSITIVE);
		// Matcher m_w = p_w.matcher(text);
		// text = m_w.replaceAll(""); // 过滤w标签
		// System.out.println(text);

		String fp = "";
		StringBuilder builder = new StringBuilder();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); // 创建一个DocumentBuilderFactory的对象

		try {
			DocumentBuilder db = dbf.newDocumentBuilder(); // 创建DocumentBuilder对象
			InputSource source = new InputSource(new BufferedReader(new InputStreamReader(new FileInputStream(fp), "utf-8")));
			Document document = db.parse(source); 
			NodeList commentList = document.getElementsByTagName("comment"); // 获取所有comment节点的集合

			for (int i = 0; i < commentList.getLength(); i++) {// 遍历每一个comment节点
				Node comment = commentList.item(i);
				String text = comment.getFirstChild().getTextContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
