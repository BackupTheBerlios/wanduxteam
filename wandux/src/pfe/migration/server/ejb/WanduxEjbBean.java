/*
 * Created on 26 févr. 2005
 */
package pfe.migration.server.ejb;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import pfe.migration.client.network.ComputerInformation;

/**
 * @ejb.bean name="ServerEjb"
 *           display-name="Name for ServerEjb"
 *           description="Description for ServerEjb"
 *           jndi-name="ejb/ServerEjb"
 *           type="Stateless"
 *           view-type="remote"
 */
public class WanduxEjbBean implements SessionBean {

	ComputerInformation ci = null;
	List migrationInProgress = new ArrayList(); // ip des machines 
	
	// -- ejb ------------------------------------------------------------------------------------ //
	public WanduxEjbBean()
	{
		super();
	}

	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException { }

	public void ejbRemove() throws EJBException, RemoteException { }

	public void ejbActivate() throws EJBException, RemoteException { }

	public void ejbPassivate() throws EJBException, RemoteException { }

	public void ejbCreate() { }
	
	// -- remote methode ------------------------------------------------------------------------- //
	public String sayMe()
	{
		return "koukouk";
	}
	
	public void putListName(String [] ok)
	{
		System.out.println(ok);
	}
	
	public void putComputerInformation(ComputerInformation ci) // String ip, 
	{
//		migrationInProgress.add(ip);
//		new Thread()
//		{
//		  public void run()
//		  {
//			this.ci = ci;
//		  }
//		}.start();
		this.ci = ci;

	}
	
	public ComputerInformation getComputerInformation()
	{
		return ci;
	}
}