/**
 * 
 */
package simplejava.jmx.client;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.management.AttributeChangeNotification;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import simplejava.jmx.ByeByeMBean;
import simplejava.jmx.HelloMBean;

/**
 * @title JMXClientMain
 */
public class JMXClientMain {


	public static void main(String[] args) {
		echo("Create an RMI connector client and connect it to the RMI connector server");
		// RMI connector client
		try(JMXConnector jmxc = JMXConnectorFactory.connect(new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/server"), null)) {
			// connect to remote MBean server
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
			// domain
			echo("Domains:");
			String[] domains = mbsc.getDomains();
			Arrays.sort(domains);
			for (String domain : domains) { 
			    echo("\tDomain = " + domain);
			}
			// mbean 
			echo("MBeanServer default domain = " + mbsc.getDefaultDomain());

			echo("MBean count = " +  mbsc.getMBeanCount());
			echo("Query MBeanServer MBeans:");
			Set<ObjectName> names = new TreeSet<ObjectName>(mbsc.queryNames(null, null));
			for (ObjectName name : names) {
			    echo("\tObjectName = " + name);
			}
			
//			callHello(mbsc);
			callBye(mbsc);
		} catch (Exception e) {
			e.printStackTrace();
		}  

	}
	
	private static void callBye(MBeanServerConnection mbsc) throws Exception {
		ObjectName mbeanName = new ObjectName("simplejava.jmx:type=ByeBye");
		// 'ByeByeMBean' interface
		ByeByeMBean mbeanProxy = JMX.newMBeanProxy(mbsc, mbeanName, ByeByeMBean.class, true);
		mbeanProxy.sayBye();
	}
	
	private static void callHello(MBeanServerConnection mbsc) throws Exception {
		ObjectName mbeanName = new ObjectName("simplejava.jmx:type=Hello");
		// 'HelloMBean' interface
		HelloMBean mbeanProxy = JMX.newMBeanProxy(mbsc, mbeanName, HelloMBean.class, true);

		echo("Add notification listener...");
//		mbsc.addNotificationListener(mbeanName, listener, null, null);

		echo("CacheSize = " + mbeanProxy.getCacheSize());

		mbeanProxy.setCacheSize(150);

		echo("Waiting for notification...");
		Thread.sleep(2000);
		echo("CacheSize = " + mbeanProxy.getCacheSize());
		echo("Invoke sayHello() in Hello MBean...");
		mbeanProxy.sayHello();

		echo("Invoke add(2, 3) in Hello MBean...");
		echo("add(2, 3) = " + mbeanProxy.add(2, 3));        
	}

	
	public static void echo(String s) {
		System.out.println(s);
	}
	
	
	static NotificationListener listener = (Notification notification, Object obj) -> {
		echo("Received notification:");
        echo("\tClassName: " + notification.getClass().getName());
        echo("\tSource: " + notification.getSource());
        echo("\tType: " + notification.getType());
        echo("\tMessage: " + notification.getMessage());
        if (notification instanceof AttributeChangeNotification) {
            AttributeChangeNotification acn = (AttributeChangeNotification) notification;
            echo("\tAttributeName: " + acn.getAttributeName());
            echo("\tAttributeType: " + acn.getAttributeType());
            echo("\tNewValue: " + acn.getNewValue());
            echo("\tOldValue: " + acn.getOldValue());
        }
	};
	
}
