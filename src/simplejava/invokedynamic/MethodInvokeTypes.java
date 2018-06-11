/**
 * 
 */
package simplejava.invokedynamic;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年9月18日下午4:20:42
 */
public class MethodInvokeTypes {

	public void invoke() {
		SampleInterface sample = new Sample(); // invokespecial
		sample.sampleMethodInInterface(); // invokeinterface

		Sample newSample = new Sample(); // invokespecial
		newSample.normalMethod(); // invokevirtual
		newSample.sampleMethodInInterface(); // invokevirtual

		Sample.staticSampleMethod(); // invokestatic

		SubSample subSample = new SubSample();
		subSample.subSampleMethod();
	}

	public static void main(String[] args) {
		MethodInvokeTypes m = new MethodInvokeTypes();
		m.invoke();
	}

}
