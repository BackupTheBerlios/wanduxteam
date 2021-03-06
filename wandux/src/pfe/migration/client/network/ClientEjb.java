package pfe.migration.client.network;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Properties;

import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;

import pfe.migration.server.ejb.WanduxEjb;
import pfe.migration.server.ejb.WanduxEjbHome;

/**
 * @author dup
 *
 * Created on 9 mars 2005
 */
public class ClientEjb
{
	private static String SERVER_EJB_NAME = "";
	protected WanduxEjb bean = null;
	
	public boolean isConnected = false;
	
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
	    
		System.out.println("connection en cours ...");
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
	
	public String getApplicationServer()
	{
		return ClientEjb.SERVER_EJB_NAME;
	}
	
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
}
