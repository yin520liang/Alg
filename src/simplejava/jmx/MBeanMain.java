/**
 * 
 */
package simplejava.jmx;

import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

/**
 * @title MBeanMain
 */
public class MBeanMain {
	
	private static int port = 9999;

	public static void main(String[] args) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		
		ObjectName name1 = new ObjectName("simplejava.jmx:type=Hello");
		Hello hello = new Hello();
		mbs.registerMBean(hello, name1);
		
		ObjectName name2 = new ObjectName("simplejava.jmx:type=ByeBye");
		ByeBye bye = new ByeBye();
		mbs.registerMBean(bye, name2);
		bye.addNotificationListener(Hello.listener, null, hello);


		LocateRegistry.createRegistry(port);
		JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + port + "/server");
		JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
		cs.start();
		System.out.println("........rmi started at [" + port + "]....."); 

	}

}
