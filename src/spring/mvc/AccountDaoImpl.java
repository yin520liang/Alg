/**
 * 
 */
package spring.mvc;

import org.springframework.stereotype.Repository;

/**
 * @Title AccountDaoImpl
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月3日
 */
@Repository
public class AccountDaoImpl implements AccountDao{


	@Override
	public String addAccount() {
		System.out.println("add account...");
		return "123";
	}
	
	@Override
	public String addAccount(long id) {
		System.out.println("add account with id...");
		return "123";
	}


	@Override
	public void updateAccount() {
		System.out.println("update account...");		
	}


	@Override
	public void deleteAccount() {
		System.out.println("delete account...");		
	}


	@Override
	public void findAccount() {
		System.out.println("find account...");		
	}

}
