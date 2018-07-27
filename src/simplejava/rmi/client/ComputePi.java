package simplejava.rmi.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import simplejava.rmi.Compute;

public class ComputePi {
	
	public static void main(String args[]) {
//		if (System.getSecurityManager() == null) {
//			System.setSecurityManager(new SecurityManager());
//		}
		try {
			String name = "Compute";
			Registry registry = LocateRegistry.getRegistry();
			Compute comp = (Compute) registry.lookup(name);
			Pi task = new Pi();
			double pi = comp.executeTask(task);
			System.out.println(pi);
			
		} catch (Exception e) {
			System.err.println("ComputePi exception:");
			e.printStackTrace();
		}
		
	}
	
}