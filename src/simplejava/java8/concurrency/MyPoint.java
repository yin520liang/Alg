/**
 * 
 */
package simplejava.java8.concurrency;

import java.util.concurrent.locks.StampedLock;

/**
 * @title
 * @description
 */
public class MyPoint {
	private double x, y;
	private final StampedLock sl = new StampedLock();

	// method is modifying x and y, needs exclusive lock
	public void move(double deltaX, double deltaY) {
		long stamp = sl.writeLock();
		try {
			x += deltaX;
			y += deltaY;
		} finally {
			sl.unlockWrite(stamp);
		}
	}

	public void moveIfAt(double oldX, double oldY, double newX, double newY) {
		long stamp = sl.readLock(); // 获得一个读悲观锁
		try {
			while (x == oldX && y == oldY) { // 循环，检查当前状态是否符合
				long writeStamp = sl.tryConvertToWriteLock(stamp);// 将读锁转为写锁
				if (writeStamp != 0L) {// 这是确认转为写锁是否成功
					stamp = writeStamp; // 如果成功 替换票据
					x = newX;
					y = newY; // 进行状态改变
					break;
				} else { // 如果不能成功转换为写锁
					sl.unlockRead(stamp);// 我们显式释放读锁
					stamp = sl.writeLock(); // 显式直接进行写锁 然后再通过循环再试
				}
			}
		} finally {
			sl.unlock(stamp); // 释放读锁或写锁
		}
	}

	public double distanceFromOrigin() {
		long stamp = sl.tryOptimisticRead(); // 获得一个乐观读锁
		double currentX = x, currentY = y; // 将两个字段读入本地局部变量
		if (!sl.validate(stamp)) {// 检查发出乐观读锁后同时是否有其他写锁发生？
			stamp = sl.readLock();// 如果有，我们再次获得一个读悲观锁
			try {
				currentX = x;// 将两个字段读入本地局部变量
				currentY = y;
			} finally {
				sl.unlockRead(stamp); // 释放读锁
			}
		}
		return Math.sqrt(currentX * currentX + currentY * currentY);
	}
}
