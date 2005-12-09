package pfe.migration.client.network;

import java.rmi.RemoteException;
//import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;

import net.sf.hibernate.HibernateException;


//import pfe.migration.client.pre.app.tools.DirCopy;
//import pfe.migration.client.pre.app.tools.FileCopy;

//import pfe.migration.client.pre.app.tools.DirCopy;
//import pfe.migration.client.pre.app.tools.FileCopy;


import pfe.migration.server.ejb.WanduxEjb;
import pfe.migration.server.ejb.WanduxEjbHome;
//import pfe.migration.server.ejb.bdd.LangInfo;
//import pfe.migration.server.monitor.ClientMonitor;

/**
 * @author dup
 *
 * Created on 9 mars 2005
 */
public class ClientEjb
{
	//private ClientMonitor cm = null;
	private static String SERVER_EJB_NAME = "";
	protected WanduxEjb bean = null;
	
	boolean isConnected = false;
	
	public ClientEjb (String ipEjb)
	{
		SERVER_EJB_NAME = ipEjb;
		System.out.println("ip du serveur d'application = " + SERVER_EJB_NAME);
	}
	
	public boolean IsConnected()
	{
		return this.isConnected;
	}

	public void EjbConnect ()
	{
	    Properties ppt = null;
	    Context ctx = null;
	    Object ref = null;
	    
	    WanduxEjbHome home = null;
	    
		System.out.println("connection en cours de connection");
	    try {
	    	ppt = new Properties();
	      ppt.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
	      ppt.put(Context.PROVIDER_URL, SERVER_EJB_NAME + ":1099");
	      ctx = new InitialContext(ppt);
	      ref = ctx.lookup("WanduxEjb");
	      home = (WanduxEjbHome)javax.rmi.PortableRemoteObject.narrow(ref, WanduxEjbHome.class);
	      bean = home.create();
	      this.isConnected = true;
	      System.out.println("connection effecue");
	  	}
	    catch (Exception e)
		{ // e.printStackTrace();
	    	this.isConnected = false;
	    	System.out.println("connection rate...");
	    }
	}

	public void EjbClose()
	{
		System.out.println("connection en cours de fermeture");
		try {
			this.bean.remove();
		} catch (RemoteException e) { e.printStackTrace();
		} catch (RemoveException e) { e.printStackTrace();
		}
		System.out.println("connection ferme");
	}
	
// -- client pre installation --

//	 useless    //
//	public void Transfert (ComputerInformation ci) 
//	{
//		System.out.println("transfer commence");
//		try {
//			bean.putComputerInformation(ci);
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		System.out.println("transfer termine");
//	}

//	 useless    //
//	public ComputerInformation getComputerInformation(String macaddr) throws RemoteException, HibernateException
//	{
//		try {
//			ComputerInformation cinfo = bean.getComputerInformation(macaddr);
//
//			return cinfo;
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}


	public WanduxEjb getBean()
	{
		return this.bean;
	}
	
	public List getLangInformation ()
	{
		try {
			return bean.getLangInformation();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	
// 	methode utilise pour lapplication de test client main
//	public String[] giveMsg () 
//	{
//		String [] st = new String[2];
//		try {
//			st[0] = bean.sayMe();
//		} catch (RemoteException e) { e.printStackTrace(); }
//		return st;
//	}
	
//	public void putListName(String [] str)
//	{
//		try {
//			bean.putListName(str);
//		} catch (RemoteException e) { e.printStackTrace(); }
//	}

// -- client monitoring --
//	public ClientMonitor makeTheClientMonitoring ()
//	{
//		ClientMonitor cm = null;
//		try {
//			cm = new ClientMonitor(bean.getCiList());
//		} catch (RemoteException e) { e.printStackTrace(); }
//		return cm;
//	}
	
}
