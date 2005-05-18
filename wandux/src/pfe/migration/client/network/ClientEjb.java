package pfe.migration.client.network;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import pfe.migration.server.ejb.WanduxEjb;
import pfe.migration.server.ejb.WanduxEjbHome;
import pfe.migration.server.ejb.bdd.NetworkDhcpConfig;
import pfe.migration.server.monitor.ClientMonitor;

/**
 * @author dup
 *
 * Created on 9 mars 2005
 */
public class ClientEjb
{

	private ClientMonitor cm = null;
	
	private static String SERVER_EJB_NAME = "";
	
	protected WanduxEjb bean = null;
	
	public ClientEjb (String ipEjb)
	{
		SERVER_EJB_NAME = ipEjb;
		System.out.println("ip du serveur EJB = " + SERVER_EJB_NAME);
	}

	public void EjbConnect ()
	{
	    Properties ppt = null;
	    Context ctx = null;
	    Object ref = null;
	
	    WanduxEjbHome home = null;
	    
	    try {
	    	ppt = new Properties();
	      ppt.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
	      ppt.put(Context.PROVIDER_URL, SERVER_EJB_NAME + ":1099");
	      ctx = new InitialContext(ppt);
	      ref = ctx.lookup("WanduxEjb");
	      home = (WanduxEjbHome)javax.rmi.PortableRemoteObject.narrow(ref, WanduxEjbHome.class);
	      bean = home.create();
	  	}
	    catch (Exception e) { e.printStackTrace(); }
	    System.out.println("connection effecue");
	}

	public void EjbClose()
	{
		try {
			this.bean.remove();
		} catch (RemoteException e) { e.printStackTrace();
		} catch (RemoveException e) { e.printStackTrace();
		}
		System.out.println("connection ferme");
	}
	
	/**
	 * methotes d'exemple
	 * @return
	 */
	public void Transfert ()
	{
		System.out.println("transfer commence");
		Transaction transaction;
		Session session;
		ComputerInformation ci = new ComputerInformation();
		ci.ndhcp = new NetworkDhcpConfig();
		ci.ndhcp.setDhcpAdress("127.0.0.2");
		System.out.println("ci.ndhcp.getDhcpAdress()>" + ci.ndhcp.getDhcpAdress());
		try {
			bean.putComputerInformation(ci);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("transfer termine");
	}
	
	public String[] giveMsg ()
	{
		String [] st = new String[2];
		try {
			st[0] = bean.sayMe();
		} catch (RemoteException e) { e.printStackTrace(); }
		return st;
	}
	
	public void putListName(String [] str)
	{
		try {
			bean.putListName(str);
		} catch (RemoteException e) { e.printStackTrace(); }
	}

	public void setClientMonitor (boolean b)
	{
		if (b == true)
			cm = new ClientMonitor();
	}

	public ClientMonitor getClientMonitor()
	{
		return cm;
	}
}
