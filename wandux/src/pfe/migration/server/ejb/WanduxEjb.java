package pfe.migration.server.ejb;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;

import pfe.migration.client.network.ComputerInformation;
import pfe.migration.server.monitor.CiList;

public interface WanduxEjb extends EJBObject
{
	public void putComputerInformation(ComputerInformation ci) throws RemoteException;
	public ComputerInformation getComputerInformation(String macaddr) throws RemoteException;

	public List getLangInformation() throws RemoteException;
//	public void getClientMonitor(ClientMonitorListener cml) throws RemoteException;
//	public void changeCmlStep (String ip, int step, int percent) throws RemoteException;
	
	public String sayMe() throws RemoteException;
//	public void putListName(String [] ok) throws RemoteException; // je sia spas ce que c est !!

	public void createAdllXmlFile(ComputerInformation ci) throws RemoteException;
	
	public CiList getCiList () throws RemoteException; 
}
