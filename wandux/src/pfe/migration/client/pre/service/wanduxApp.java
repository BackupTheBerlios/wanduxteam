package pfe.migration.client.pre.service;

//import pfe.migration.server.ejb.tool.FileSystemXml;
import java.rmi.RemoteException;

import pfe.migration.client.network.ClientEjb;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.client.pre.system.IpConfig;
import pfe.migration.server.ejb.bdd.NetworkConfig;
import pfe.migration.server.ejb.tool.FsXmlst;
import pfe.migration.server.ejb.tool.XmlRetrieve;

public class wanduxApp
{

	private String applicationServerIp = "";
	
	private ComputerInformation ci = new ComputerInformation();
	private ClientEjb ce = null;
	//private WorkQueue wq = null;

	public static void main(String[] args)
	{
		new wanduxApp();
	}
	
	public void WanduxWmiInfoManager()
	{
		WanduxWmiBridge wwb = new WanduxWmiBridge();
		// String rq = "SELECT * FROM Win32_OperatingSystem";
		//String rq = "SELECT * FROM Win32_NetworkAdapterConfiguration";
		// String wzName = "DHCPEnabled"; // element a recuperer depuis la requette
		
		//String rq = "SELECT * FROM Win32_UserAccount";
		//String wzName = "Name"; // element a recuperer depuis la requette
		
		//String rq = "SELECT * FROM Win32_LogicalDisk";
		//String wzName = "Caption";
		
		//String rq = "SELECT * FROM Win32_TimeZone";
		//String wzName = "DaylightName";
		
		String rootPath = "\\root\\CIMV2\\Applications\\MicrosoftIE";
		String rq = "SELECT * FROM MicrosoftIE_LanSettings";
		String wzName = "ProxyServer";
		String[] str;
		str = wwb.exec_rq(rootPath, rq, wzName);
		System.out.println("dans java :\n");
		int i= 0;
		while(str[i] != null)
		{
				System.out.println(str[i]);
				i++;
		}
	}
	
	public wanduxApp()
	{
		WanduxWmiInfoManager();
		//wq = new WorkQueue(10);
//		getIp();
//		
//		if (makeConnection() == true)
//			System.out.println("connection etablie ...");
//		if (this.ce.IsConnected() == false)
//			return ;
//		
//		WanduxWmiInfoManager();
//		fillNetworkInCI();
//		
//		try {
//			this.ce.getBean().putCi(this.ci);
//		} catch (RemoteException e) { e.printStackTrace(); }
//		System.out.println("information recupere et envoyer");
	}

	private void fillNetworkInCI()
	{
		IpConfig ipconf = new IpConfig();
		NetworkConfig nc = new NetworkConfig();
		nc.setNetworkDhcpEnabled(ipconf.GetDHCPEnable());
		nc.setNetworkGateway(ipconf.GetGate());
		nc.setNetworkIpAddress(ipconf.GetIp());
		nc.setNetworkMacAdress(ipconf.GetMac());
		nc.setNetworkSubnetmask(ipconf.GetNetmask());
		this.ci.setInfoNetwork(nc);
	}
		
	/**
	 * TODO on utilise le fichier wanduxServerIp.xml contenu dans utils\wanduxServerIp\
	 * pour recuperer l'ip du server sur lequel le client doit se connecter. 
	 */
	private void getIp()
	{
		XmlRetrieve ri = new XmlRetrieve("utils\\wanduxServerIp\\wanduxServerIp.xml");
		//this.applicationServerIp = ri.IpServer();
		this.applicationServerIp = "127.0.0.1";
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
