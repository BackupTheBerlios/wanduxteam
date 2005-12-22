package pfe.migration.client.pre.service;

//import pfe.migration.server.ejb.tool.FileSystemXml;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.jacob.com.Variant;

import com.jacob.com.Variant;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


import com.jacob.com.Variant;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


import pfe.migration.client.network.ClientEjb;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.client.pre.app.ProgramsLister;
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
	
	private ComputerInformation ci = null;
	private ClientEjb ce = null;
//	 wandux wmi bridge, permet de gerer l'execution des requettes wmi
	WanduxWmiBridge wwb = null; 

	private static String rootCIMV2 = "root\\CIMV2";
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
	
	public wanduxApp()
	{
		// iniatalise la connexiion wmi
		WanduxWmiInfoManager();
		// TODO recuperer l ip du serveur d appli depuis un fichier comme prevu ....
		// this.applicationServerIp = "127.0.0.1";

		// TODO recuperer l ip du serveur d appli depuis un fichier comme prevu ....
		this.applicationServerIp = "127.0.0.1";
		this.ci = new ComputerInformation();
		WanduxWmiInfoManager();
		
		fillNetworkInCI();
		
		//WanduxWmiInfoManager();
		//fillNetworkInCI();

		fillHostname();
		
//		System.out.println(this.ci.gconf.getGlobalHostname());

//		NetworkConfig ntconfig[] = ci.getInfoNetwork();
//		int i = 0;
//		while(i < ntconfig.length)
//			System.out.println(ntconfig[i++]);
		//wq = new WorkQueue(10);
		
//		WanduxWmiInfoManager();
//		getIp();

//		WanduxWmiInfoManager();
//		fillNetworkInCI();

		// get the file system model from the machine
		GetFileTreeModel();


		if (makeConnection() == true)
			System.out.println("connection etablie ...");
		if (this.ce.IsConnected() == false)
			return ;

		ProgramMatcher();

		try {
			// Send Machine CI to server
			this.ce.getBean().putCi(this.ci);
		} catch (RemoteException e) { e.printStackTrace(); }
		System.out.println("information recupere et envoyer");

	}
	
	/**
	 * Matches existence of pre-defined programs
	 *
	 */
	private void ProgramMatcher()
	{
		ProgramsLister pl = new ProgramsLister();
		String progs[] = new String[10];				
		String CommonName[] = new String[10];				

		progs[0] = "iexplore";	/* ie	   */
		progs[1] = "msimn";		/* outlook */
		progs[2] = "windword";	/* winword */
		CommonName[0] = "Internet Explorer";
		CommonName[1] = "Outlook Express";
		CommonName[2] = "MS Office";

		progs = pl.Programexists(pl.ParseExtensions(), progs, 3);
		ArrayList proglist = new ArrayList();
		for (int k = 0; k < 3; k++)
		{
			if (progs[k] != null)
				proglist.add(CommonName[k]);
			else
				proglist.add(null);	
		}
		ci.windowsProgram = proglist;
	}


	public void WanduxWmiInfoManager()
	{
		wwb = new WanduxWmiBridge(rootCIMV2);
	}
	
	public void GetFileTreeModel()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(ci.gconf.getGlobalHostname());
		
		Variant [] disk =  GetPartitionName();
		
//		File roots[] = File.listRoots();

		File roots[] = new File [disk.length];

	    for (int i = 0; i < disk.length; i++)
	    {
	    	if (disk[i] == null)
	    		break ;
	    	roots[i] = new File(disk[i].getString());
	    }
	    
//	    for (int i = 0; i < roots.length; i++)
//	    {
//	        DefaultMutableTreeNode node = getSubDirs(roots[i]); // new DefaultMutableTreeNode(roots[i].getAbsoluteFile().toString());
//	        root.add(node);
//	    }

////////// tmppour lestests //
	    DefaultMutableTreeNode node = getSubDirs(roots[0]); // new DefaultMutableTreeNode(roots[i].getAbsoluteFile().toString());
        root.add(node);
////////// ---------------- //
        
	    this.ci.setFileSystemModel(new DefaultTreeModel(root)); 
	}
	
	public DefaultMutableTreeNode getSubDirs(File root) {
		
		// On créé un noeud
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode(root, true);
		
		// On recupère la liste des fichiers et sous rep
		File[] list = root.listFiles();
		
		if (list != null) {	
			// Pour chaque sous rep on appelle cette methode => recursivité
			// Attention l'index du tableau list commence a 0
			for (int j = 0; j < list.length; j++) {
				DefaultMutableTreeNode file = null;
				if (list[j].isDirectory()) {
					file = getSubDirs(list[j]);
					racine.add(file);
				}
			}
		}
		return racine;
	}
	
	private void fillNetworkInCI()
	{
		NetConfig netconfig = new NetConfig(wwb);
		
		Variant[] listNetworkInterfacesCaption = netconfig.listNetworkInterfaces();
		int i = 0;
		while(i<listNetworkInterfacesCaption.length && listNetworkInterfacesCaption[i] != null)
		{
			System.out.println(listNetworkInterfacesCaption[i]);
///			listNetworkInterfacesCaption[i];
			i++;
		}
		NetworkConfig[] nc = new NetworkConfig[i+1];
		i = 0;
		try{
			 while(i < listNetworkInterfacesCaption.length && listNetworkInterfacesCaption[i] != null)
			{
				System.out.println("\n ==================== index de l'interface: " + listNetworkInterfacesCaption[i].getString() + " ====================\n");
				NetworkConfig ncs = new NetworkConfig();
				
//				 Caption
				ncs.setNetworkInterface(netconfig.GetCaption(listNetworkInterfacesCaption[i].getString()));
				// status
				ncs.setNetworkStatus(netconfig.GetStatus(listNetworkInterfacesCaption[i].getString()));
//				 Mac
				ncs.setNetworkMacAdress(netconfig.GetMac(listNetworkInterfacesCaption[i].getString()));

				// ip
			    ncs.setNetworkIpAddress(netconfig.GetIpadress(listNetworkInterfacesCaption[i].getString()));
				// Subnetmask
				ncs.setNetworkSubnetmask(netconfig.GetNetmask(listNetworkInterfacesCaption[i].getString()));
				// Gate
				ncs.setNetworkGateway(netconfig.GetGate(listNetworkInterfacesCaption[i].getString()));
				// dns
				String[] dnsServerListe = netconfig.GetDnsServer(listNetworkInterfacesCaption[i].getString());
				 ncs.setNetworkDnsServer(dnsServerListe[0]);
				 ncs.setNetworkDnsServer2(dnsServerListe[1]);
//				 DHCPEnable
				ncs.setNetworkDhcpEnabled(netconfig.GetDHCPEnable(listNetworkInterfacesCaption[i].getString()));				
				nc[i] = ncs;
				ncs = null;
				i++;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if(nc != null)
		this.ci.setInfoNetwork(nc);	
	}
	
	private Variant [] GetPartitionName()
	{
		Variant[] rqRSLT = null;
		String rq  = "SELECT * FROM Win32_LogicalDisk WHERE DriveType = 3";
		String wzName = "Caption"; // element a recuperer depuis la requette
		try
		{
			rqRSLT = wwb.exec_rq(rq, wzName);	
			if(rqRSLT[0].equals("1")) // erreur detected
			{
				System.err.println(rqRSLT[1]);
				return null;
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getStackTrace());
		}
		return rqRSLT;
	}
	
	private void fillHostname()
	{
		String res = "";
		Variant[] rqRSLT = null;
		String rq  = "SELECT * FROM Win32_ComputerSystem";
		String wzName = "Caption"; // element a recuperer depuis la requette

		try
		{
			rqRSLT = wwb.exec_rq( rq, wzName);	
			ci.gconf.setGlobalHostname(rqRSLT[0].getString());
		}
		catch(Exception e)
		{
			System.err.println(e.getStackTrace());
		}
			Variant var = rqRSLT[0];
			ci.gconf.setGlobalHostname(var.getString());
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
