package RMIClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

import RMIServer.UserServerIF;

public class UserClient extends UnicastRemoteObject implements UserClientIF, Runnable{
	private UserServerIF userServer;
	private String name = null;

	protected UserClient(String name, UserServerIF userServer) throws RemoteException {
		this.userServer = userServer;
		this.name = name;
		try {
			Naming.rebind(name, this);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void  handshake(String sender) throws RemoteException {
		System.out.println(sender + " sends a handshake to me" + "(" + this.name + ")");
		notifyAll();
	}

	@Override
	public synchronized void run() {
		Scanner scan = new Scanner(System.in);
		int message;
		while(true) {
			message = scan.nextInt();
			try {
				String answer = userServer.match(this.name, message);
				System.out.println(name + " has been matched with : " + answer);
				
				if(answer!=null && this.name.compareTo(answer) > 0) {
					try {
						System.out.println("I am " + "(" + this.name + ")" + " waiting for a handshake");
						wait();
						UserClientIF obj;
						try {
							obj = (UserClientIF) Naming.lookup(answer);
							obj.handshake(this.name);
						} catch (MalformedURLException | NotBoundException e) {
							e.printStackTrace();
						}
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				else if(answer!=null && this.name.compareTo(answer) <= 0 ) {
					try {
						UserClientIF obj = (UserClientIF) Naming.lookup(answer);
						obj.handshake(this.name);
						notifyAll();
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} catch (MalformedURLException | NotBoundException e) {
						e.printStackTrace();
					}
				}
					
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getName() {
		return this.name;
	}
}
