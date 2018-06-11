/**
 * 
 */
package simplejava.java8.concurrency;

import java.util.concurrent.locks.StampedLock;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年9月20日下午2:45:08
 */
public class BankAccountWithStampedLock {
	private final StampedLock lock = new StampedLock();
	private double balance;

	public void deposit(double amount) {
		long stamp = lock.writeLock();
		try {
			balance = balance + amount;
		} finally {
			lock.unlockWrite(stamp);
		}
	}

	public double getBalance() {
		long stamp = lock.readLock();
		try {
			return balance;
		} finally {
			lock.unlockRead(stamp);
		}
	}

}
