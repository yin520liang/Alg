/**
 * 
 */
package spring.mvc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @Title SpringMain
 * @Description 
 */
public class SpringMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext=new
		          FileSystemXmlApplicationContext("spring-context1.xml");

		AccountDao account = (AccountDao) applicationContext.getBean(AccountDao.class);
		account.addAccount();

	}

}
