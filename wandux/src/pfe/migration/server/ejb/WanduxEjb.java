package pfe.migration.server.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import pfe.migration.client.network.ComputerInformation;

public interface WanduxEjb extends EJBObject
{
	public void putComputerInformation(ComputerInformation ci) throws RemoteException;
	public ComputerInformation getComputerInformation() throws RemoteException;
	
	public String sayMe() throws RemoteException;
	public void putListName(String [] ok) throws RemoteException;
}
