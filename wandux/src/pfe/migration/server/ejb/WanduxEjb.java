package pfe.migration.server.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface WanduxEjb extends EJBObject
{
	public String sayMe() throws RemoteException;
	public void putListName(String [] ok) throws RemoteException;
}
