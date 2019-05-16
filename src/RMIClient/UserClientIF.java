package RMIClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserClientIF extends Remote{
	String getName() throws RemoteException;
	void handshake(String sender) throws RemoteException;
}
