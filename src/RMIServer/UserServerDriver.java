package RMIServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class UserServerDriver {
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		Naming.rebind("RMIUserServer", new UserServer());
	}

}
