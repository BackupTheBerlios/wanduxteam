
package pfe.migration.server.ejb;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBObject;

import pfe.migration.client.network.ComputerInformation;
// import pfe.migration.server.monitor.CiList;

public interface WanduxEjb extends EJBObject
{
	public void putHostname(String ip) throws RemoteException;
	public List getIps() throws RemoteException;
	public void putCi(ComputerInformation ci) throws RemoteException;
	public ComputerInformation getCi(String Hostname) throws RemoteException;
	
	public void putFileList(String hostname, List files) throws RemoteException;
	public List getFileList(String hostname) throws RemoteException;
	public void removeFileOfTheList(String hostname) throws RemoteException;

	public List getLangInformation() throws RemoteException;
	
	public String sayMe() throws RemoteException;
//	public void putListName(String [] ok) throws RemoteException; // je sia spas ce que c est !!

	public void createAdllXmlFile(ComputerInformation ci) throws RemoteException;
	
//	public CiList getCiList () throws RemoteException;

	public ArrayList getLinuxEquivalents(String Winsoft) throws RemoteException;

	public Integer GuessMostRelevantPackage(String LinSoft, String CurrentPackage) throws RemoteException;
	public void migrate(String hostname) throws RemoteException;
}
