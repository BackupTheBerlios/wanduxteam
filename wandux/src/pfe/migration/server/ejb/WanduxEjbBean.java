/*
 * Created on 26 févr. 2005
 */
package pfe.migration.server.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * @ejb.bean name="ServerEjb"
 *           display-name="Name for ServerEjb"
 *           description="Description for ServerEjb"
 *           jndi-name="ejb/ServerEjb"
 *           type="Stateless"
 *           view-type="remote"
 */
public class WanduxEjbBean implements SessionBean {

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
	

}