/**
 * 
 */
package spring.mvc;

/**
 * @Title AccountDao
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月3日
 */
public interface AccountDao {
	
	String addAccount();
	
	String addAccount(long id);
	
	void updateAccount();
	
	void deleteAccount();
	
	void findAccount();

}
