package RMIClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import RMIServer.UserServerIF;

public class UserClientDriver {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException
	{
		String userServerURL = "rmi://localhost/RMIUserServer";
		UserServerIF userServer = (UserServerIF) Naming.lookup(userServerURL);
		new Thread(new UserClient(args[0], userServer)).start();
	}
}
