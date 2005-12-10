package pfe.migration.client.pre.service;

//import pfe.migration.server.ejb.tool.FileSystemXml;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pfe.migration.client.network.ClientEjb;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.client.pre.system.NetConfig;
import pfe.migration.client.pre.system.NetConfig;
import pfe.migration.client.pre.system.FileSystemModel;
import pfe.migration.client.pre.system.FileSystemModel;
import pfe.migration.client.pre.system.FileSystemModel;
import pfe.migration.server.ejb.bdd.NetworkConfig;
import pfe.migration.server.ejb.tool.XmlRetrieve;

public class wanduxApp
{

	private String applicationServerIp = "";
	
	private ComputerInformation ci= null;
	private ClientEjb ce = null;
//	 wandux wmi bridge, permet de gerer l'execution des requettes wmi
	WanduxWmiBridge wwb = null; 

	String rootCIMV2 = "root\\CIMV2";
	String rootCIMV2ApplicationsMicrosoftIE = "root\\CIMV2\\Applications\\MicrosoftIE";
	 // prevu pour contenir la requette wmi
	String rq = null;
	// prevu pour contenir l'element a recuperer dans la quette wmi
	String wzName = null;
	//	 resultat de la requette renvoye par la dll
	String [] rqRSLT = null; 
	
	//private WorkQueue wq = null;
	
	public static void main(String[] args)
	{
		new wanduxApp();
	}
	
	public void WanduxWmiInfoManager()
	{
		wwb = new WanduxWmiBridge();
		// String rq = "SELECT * FROM Win32_OperatingSystem";

		
		//String rq = "SELECT * FROM Win32_UserAccount";
		//String wzName = "Name"; // element a recuperer depuis la requette
		
		//String rq = "SELECT * FROM Win32_LogicalDisk";
		//String wzName = "Caption";
		
		//String rq = "SELECT * FROM Win32_TimeZone";
		//String wzName = "DaylightName";
		
//		String rootPath = "root\\CIMV2\\Applications\\MicrosoftIE";
//		String rq = "SELECT * FROM MicrosoftIE_LanSettings";
//		
//		String wzName = "ProxyServer";
//		String[] str;
//		str = wwb.exec_rq(rootPath, rq, wzName);
//		System.out.println("dans java :\n");
//		int i= 0;
//		while(str[i] != null)
//		{
//				System.out.println(str[i]);
//				i++;
//		}
	}
	
	public void GetFileTreeModel()
	{
		File [] disks = File.listRoots();
		FileSystemModel models [] = new FileSystemModel [disks.length-1]; // = new FileSystemModel()[allDisk.length];
		for (int i = 1; i < disks.length; i++)
			models[i-1] = new FileSystemModel(disks[i]);
		this.ci.setFileSystemModel(models);
	}
	
	public wanduxApp()
	{
		// TODO recuperer l ip du serveur d appli depuis un fichier comme prevu ....
		this.applicationServerIp = "127.0.0.1";
		ci = new ComputerInformation();
		//WanduxWmiInfoManager();
		//fillNetworkInCI();
		//fillHostname();
		ci.gconf.setGlobalHostname("127.0.0.1"); // 
		System.out.println(ci.gconf.getGlobalHostname());

//		NetworkConfig ntconfig[] = ci.getInfoNetwork();
//		int i = 0;
//		while(i < ntconfig.length)
//			System.out.println(ntconfig[i++]);
		//wq = new WorkQueue(10);
		
//		WanduxWmiInfoManager();
//		getIp();

//		WanduxWmiInfoManager();
//		fillNetworkInCI();

		//GetFileTreeModel();

		if (makeConnection() == true)
			System.out.println("connection etablie ...");
		if (this.ce.IsConnected() == false)
			return ;
		try {
			this.ce.getBean().putCi(this.ci);
		} catch (RemoteException e) { e.printStackTrace(); }
		System.out.println("information recupere et envoyer");
	}

	private void fillNetworkInCI()
	{
		NetConfig netconfig = new NetConfig(wwb);
		
		String[] listNetworkInterfacesCaption = netconfig.listNetworkInterfaces();
		int i = 0;
//		while(i<listNetworkInterfacesCaption.length && listNetworkInterfacesCaption[i] != null)
//		{
//			System.out.println(listNetworkInterfacesCaption[i]);
//			i++;
//		}
		NetworkConfig[] nc = new NetworkConfig[10];
		i = 0;
		try{
			 while(i < listNetworkInterfacesCaption.length && listNetworkInterfacesCaption[i] != null)
			{
				System.out.println("tour " + i);
				NetworkConfig ncs = new NetworkConfig();
				Byte  value = netconfig.GetDHCPEnable(listNetworkInterfacesCaption[i]);
				if(value != null)
					{
					System.out.println("pass dedans");
					ncs.setNetworkDhcpEnabled(value);
					}
				else // case d'erreur
					{
						i++;
						continue;
					}
	//			ncs.setNetworkGateway(netconfig.GetGate());
	//			ncs.setNetworkIpAddress(netconfig.GetIp());
	//			ncs.setNetworkMacAdress(netconfig.GetMac());
	//			ncs.setNetworkSubnetmask(netconfig.GetNetmask());
				nc[i] = ncs;
				ncs = null;
				i++;
			}
		}
		catch (Exception e)
		{
			System.err.println(e.getStackTrace());
		}
		if(nc != null)
		this.ci.setInfoNetwork(nc);	
	}
	
	void fillHostname()
	{
		String res = "";
		String[] rqRSLT = null;
		String rq  = "SELECT * FROM Win32_ComputerSystem";
		String wzName = "Caption"; // element a recuperer depuis la requette
			try
			{
				rqRSLT = wwb.exec_rq(rootCIMV2, rq, wzName);	
				if(rqRSLT[0].equals("1")) // erreur detected
				{
					System.err.println(rqRSLT[1]);
					return;
				}
				ci.gconf.setGlobalHostname(rqRSLT[0]);
			}
			catch(Exception e)
			{
				System.err.println(e.getStackTrace());
			}
			return;
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
