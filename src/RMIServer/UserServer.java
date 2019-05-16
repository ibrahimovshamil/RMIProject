package RMIServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import RMIClient.UserClientIF;

//import RMIClient.ChatClient;
//import RMIClient.ChatClientIF;

public class UserServer extends UnicastRemoteObject implements UserServerIF {

	private static final long serialVersionUID = 1L;
	private ConcurrentHashMap<String, Calendar> chatClients = new ConcurrentHashMap<String, Calendar>(); 

	protected UserServer() throws RemoteException {}

	@Override
	public synchronized String match(String name, int timeout) throws RemoteException {
		boolean flag = true;
		boolean notify = false;

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);


		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		calendar1.add(Calendar.SECOND, timeout);

		while(flag)
		{
			Iterator it1 = chatClients.entrySet().iterator();
			while (it1.hasNext()) {
				Map.Entry pair = (Map.Entry)it1.next();

				String str = (String) pair.getKey();
				try {
					UserClientIF obj = (UserClientIF) Naming.lookup(name);
				} catch (MalformedURLException | NotBoundException e) {
					e.printStackTrace();
				}

				flag = true;
				it1.remove();
				if(!notify)
					chatClients.put(name, calendar1);
				notifyAll();
				return str;
			}

			chatClients.put(name, calendar1);
			try {

				wait(timeout*1000);
				date = new Date();
				calendar.setTime(date);
				if(calendar1.after(calendar)) { // means im woken up
					flag = true;
					notify = true;
				}	
				else flag = false;
				//System.out.println("wokeup");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		chatClients.clear();
		return null;
	}

}
