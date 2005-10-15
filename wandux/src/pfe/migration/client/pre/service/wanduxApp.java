package pfe.migration.client.pre.service;

import pfe.migration.server.ejb.tool.FileSystemXml;
import pfe.migration.client.network.ClientEjb;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.server.ejb.tool.XmlRetrieve;

public class wanduxApp
{

	private String applicationServerIp = "";
	
	private ComputerInformation ci = null;
	private ClientEjb ce = null;
	//private WorkQueue wq = null;

	public static void main(String[] args)
	{
		new wanduxApp();
	}
	
	public wanduxApp()
	{
		//wq = new WorkQueue(10);
		getIp();
		if (makeConnection() == true)
			System.out.println("connection etablie ...");
		new FileSystemXml();
		// send xml file to server or use ci ?
	}

	/**
	 * TODO on utilise le fichier wanduxServerIp.xml contenu dans utils\wanduxServerIp\
	 * pour recuperer l'ip du server sur lequel le client doit se connecter. 
	 */
	private void getIp()
	{
		XmlRetrieve ri = new XmlRetrieve("utils\\wanduxServerIp\\wanduxServerIp.xml");
		this.applicationServerIp = ri.IpServer();
	}
	
  	private boolean makeConnection()
	{
  		if (this.ce == null)
  		{
  			this.ce = new ClientEjb(applicationServerIp);
			this.ce.EjbConnect();
  		}

//  	// gestion de la mauvaise url (ca marche)
//		else if (ce.IsConnected())
//		{
//			this.ce.EjbClose();
//			this.ce = new ClientEjb(applicationServer);
//			this.ce.EjbConnect();
//		}
//		else
//		{
//			this.ce = new ClientEjb(applicationServer);
//			this.ce.EjbConnect();
//		}
  		
  		return ce.IsConnected();
	}
}
