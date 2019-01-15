/**
 * 
 */
package simplejava.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Collection;
import java.util.Map;

/**
 * @title IntrospectorTest
 */
public class IntrospectorTest {

	final static Object[] EMPTY_ARGS = new Object[0];

	public static void main(String[] args) {
//		Entity e = new Entity();
//		e.setA("a");
//		e.setB("b");
//		
//		Map m = new HashMap<>(2);
//		try {
//			extractAccessiblePropertiesToMap(m, e, Collections.emptyList());
//		} catch (IntrospectionException e1) {
//			e1.printStackTrace();
//		}
//		System.out.println(m);
		
		PropertyEditor editor = PropertyEditorManager.findEditor(String.class);
		System.out.println(editor);

	}

	public static void extractAccessiblePropertiesToMap(Map fillMe, Object bean, Collection ignoreProps)
			throws IntrospectionException {
		String propName = null;
		try {
			BeanInfo bi = Introspector.getBeanInfo(bean.getClass(), Object.class);
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();
			for (int i = 0, len = pds.length; i < len; ++i) {
				PropertyDescriptor pd = pds[i];
				propName = pd.getName();
				if (ignoreProps.contains(propName))
					continue;

				Method readMethod = pd.getReadMethod();
				Object propVal = readMethod.invoke(bean, EMPTY_ARGS);
				fillMe.put(propName, propVal);
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


class Entity {
	private String a;
	private String b;
	
	public String getA() {
		return a;
	}
	public String getB() {
		return b;
	}
	public void setA(String a) {
		this.a = a;
	}
	public void setB(String b) {
		this.b = b;
	}

	
}
