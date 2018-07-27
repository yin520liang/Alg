package simplejava.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import simplejava.rmi.Compute;
import simplejava.rmi.Task;

public class ComputeEngine implements Compute{

	@Override
	public <T> T executeTask(Task<T> t) throws RemoteException {
		return t.execute();
	}


    public static void main(String[] args) {
    	
//        if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new SecurityManager());
//        }
//        System.getSecurityManager().checkConnect("127.0.0.1", 1099);
        try {
            String name = "Compute";
            Compute engine = new ComputeEngine();
            Compute stub =
                (Compute) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("ComputeEngine bound");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
    }

}
