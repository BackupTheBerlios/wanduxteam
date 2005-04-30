/*
 * Created on 9 mars 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.network;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;

import pfe.migration.server.ejb.WanduxEjb;
import pfe.migration.server.ejb.WanduxEjbHome;

/**
 * @author dup
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ClientEjb {

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
	}

	public void EjbClose()
	{
		try {
			this.bean.remove();
		} catch (RemoteException e) { e.printStackTrace();
		} catch (RemoveException e) { e.printStackTrace();
		}
	}

	/**
	 * methotes d'exemple
	 * @return
	 */
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
	
	public WanduxEjb getBean()
	{
		return this.bean;
	}
}
