/**
 * 
 */
package spring.mvc;

/**
 * @Title AccountDao
 * @Description 
 */
public interface AccountDao {
	
	String addAccount();
	
	String addAccount(long id);
	
	void updateAccount();
	
	void deleteAccount();
	
	void findAccount();

}
