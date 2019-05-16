package RMIServer;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface UserServerIF extends Remote{
	String match(String name, int timeout) throws RemoteException;
}
