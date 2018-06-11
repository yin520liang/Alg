/**
 * 
 */
package simplejava.invokedynamic;

/**
 * @title
 * @description 
 * @author lvzhaoyang
 * @date 2017年9月18日下午4:38:27
 */
public class SubSample extends Sample{
	
	public void subSampleMethod() {
		super.sampleMethodInInterface(); // invokespecial
		normalMethod(); // invokevirtual
	}

}
