package pfe.migration.server.ejb;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;


public interface WanduxEjbHome extends EJBHome
{
	public WanduxEjb create() throws CreateException, RemoteException;
}