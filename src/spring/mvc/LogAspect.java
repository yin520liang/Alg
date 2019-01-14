/**
 * 
 */
package spring.mvc;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @Title LogAspect
 * @Description
 */
@Aspect
public class LogAspect {
	
	@Pointcut("execution(* spring.AccountDao.addAccount(..))")
	private void myPointcut(){}

	/**
	 * 前置通知
	 */
	@Before(value = "myPointcut()", argNames = "userId")
	public void before(long userId) {
		System.out.println("前置通知...." + userId);
	}

	/**
	 * 后置通知 returnVal,切点方法执行后的返回值
	 */
	@AfterReturning(value = "myPointcut()", returning = "returnVal")
	public void AfterReturning(Object returnVal) {
		System.out.println("后置通知...." + returnVal);
	}

	/**
	 * 环绕通知
	 * 
	 * @param joinPoint
	 *            可用于执行切点的类
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "myPointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("环绕通知前....");
		Object obj = (Object) joinPoint.proceed();
		System.out.println("环绕通知后....");
		return obj;
	}

	/**
	 * 抛出通知
	 * 
	 * @param e
	 */
	@AfterThrowing(value = "myPointcut()", throwing = "e")
	public void afterThrowable(Throwable e) {
		System.out.println("出现异常:msg=" + e.getMessage());
	}

	/**
	 * 无论什么情况下都会执行的方法
	 */
	@After(value = "myPointcut()")
	public void after() {
		System.out.println("最终通知....");
	}
}
