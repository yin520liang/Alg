/**
 * 
 */
package simplejava.invokedynamic;

/**
 * @title
 * @description 
 */
public class SubSample extends Sample{
	
	public void subSampleMethod() {
		super.sampleMethodInInterface(); // invokespecial
		normalMethod(); // invokevirtual
	}

}
