package pfe.migration.client.pre.service;

//import pfe.migration.server.ejb.tool.FileSystemXml;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import pfe.migration.client.network.ClientEjb;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.client.pre.system.FSNodeCopy;
import pfe.migration.client.pre.system.NetConfig;
import pfe.migration.client.pre.system.ProgramsLister;
import pfe.migration.client.pre.system.UserConfig;
import pfe.migration.server.ejb.bdd.NetworkConfig;
import pfe.migration.server.ejb.bdd.UsersData;
import pfe.migration.server.ejb.tool.XmlRetrieve;

import com.jacob.com.Variant;

public class wanduxApp {
	private String applicationServerIp = "";

	protected String storageServerIp = "";

	protected ComputerInformation ci = null;

	private ClientEjb ce = null;

	private Win32Cim wcim = new Win32Cim();

	// prevu pour contenir la requette wmi
	String rq = null;

	// resultat de la requette renvoye par la dll
	Variant[] rqRSLT = null;

	// private WorkQueue wq = null;

	static InputStreamReader converter = new InputStreamReader(System.in);

	static BufferedReader in = new BufferedReader(converter);

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.print("Please enter the IP of the application server: ");
			new wanduxApp(getString());

		} else
			new wanduxApp(args[0]);
	}

	public static String getString() {
		try {
			return in.readLine();
		} catch (Exception e) {
			System.out.println("getString() exception, returning empty string");
			return "";
		}
	}

	public wanduxApp(String arg) {
		storageServerIp = applicationServerIp = arg;
		this.ci = new ComputerInformation();

		fillNetworkInCI();
		fillHostname();
		fillusersData();
		GetFileTreeModel();

		if (makeConnection() == true)
			System.out.println("connection etablie ...");
		if (this.ce.IsConnected() == false)
			return;

		ProgramMatcher();

		try { // Send Machine CI to server
			this.ce.getBean().putCi(this.ci);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		System.out.println("information recupere et envoyer");
		System.out.println("list de fichiers envoyer");

		while (true) // mecanisme a change dans le futur
		{
			try {
				this.ci = this.ce.getBean().getCi(this.ci.getHostname());
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}

			try {
				if (this.ci.migrable == 0
						&& this.ce.getBean().getFileList(this.ci.getHostname()) == null) {
					System.out.println("Waiting for migrating informations");
					Thread.sleep(15000);
				} else {
					parseAndCopieFiles(this.ce.getBean().getFileList(
							this.ci.getHostname()));
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		this.closeConnection();
	}

	private void parseAndCopieFiles(List l) {
		FSNodeCopy cp = new FSNodeCopy();

		System.out.println(l);

		if (l == null) {
			System.err.println("error while saving files ...");
			return;
		}
		Iterator i = l.iterator();

		while (i.hasNext()) {
			String s = (String) i.next();
			String disk = "disk" + s.substring(0, 1);

			File f = new File("\\\\" + this.storageServerIp
					+ "\\wanduxStorage\\" + this.ci.getHostname() + "\\");
			if (f.exists() == false)
				f.mkdir();
			f = new File("\\\\" + this.storageServerIp + "\\wanduxStorage\\"
					+ this.ci.getHostname() + "\\" + disk);
			if (f.exists() == false)
				f.mkdir();

			System.out.println("src : " + s);
			System.out
					.println("dest : " + "\\\\" + this.storageServerIp
							+ "\\wanduxStorage\\" + this.ci.getHostname()
							+ "\\" + disk);
			// System.out.println("\\\\" + this.storageServerIp +
			// "\\wanduxStorage\\" + this.ci.getHostname() + "\\" + disk);
			// cp.CopyNode(s, "\\\\" + this.storageServerIp +
			// "\\wanduxStorage\\" + this.ci.getHostname() + "\\" + disk, true);
			cp
					.GenericCopyNode(s, "\\\\" + this.storageServerIp
							+ "\\wanduxStorage\\" + this.ci.getHostname()
							+ "\\" + disk);
		}
	}

	/**
	 * Matches existence of pre-defined programs
	 */
	private void ProgramMatcher() {
		ProgramsLister pl = new ProgramsLister();
		String progs[] = new String[10];
		String CommonName[] = new String[10];

		progs[0] = "iexplore"; /* ie */
		progs[1] = "msimn"; /* outlook */
		progs[2] = "windword"; /* winword */
		CommonName[0] = "Internet Explorer";
		CommonName[1] = "Outlook Express";
		CommonName[2] = "MS Office";

		progs = pl.Programexists(pl.ParseExtensions(), progs, 3);
		ArrayList proglist = new ArrayList();
		for (int k = 0; k < 3; k++) {
			if (progs[k] != null)
				proglist.add(CommonName[k]);
			else
				proglist.add(null);
		}
		ci.windowsProgram = proglist;
	}

	/**
	 * @author Dupix
	 * @see GetFileTreeModel get the file system model from the machine
	 */
	public void GetFileTreeModel() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(ci.gconf
				.getGlobalHostname());

		Variant[] disk = GetPartitionName();

		// File roots[] = File.listRoots();

		File roots[] = new File[disk.length];

		int j = 0;
		for (int i = 0; i < disk.length; i++) {
			if (disk[i] == null)
				break;
			roots[j++] = new File(disk[i].getString());
		}

		// TODO liste tout les disque lorsque cette partie sera fini de teste
		// for (int i = 0; i < roots.length; i++)
		// {
		// System.out.println("\n ==================== scan data disk: " +
		// roots[i].toString() + " ====================\n");
		// DefaultMutableTreeNode node = getSubDirs(roots[i]); // new
		// DefaultMutableTreeNode(roots[i].getAbsoluteFile().toString());
		// root.add(node);
		// }

		// //////// tmppour lestests //
		System.out.println("\n ==================== scan data disk: "
				+ roots[1].toString() + " ====================\n");
		// DefaultMutableTreeNode node = getSubDirs(roots[0]); // new
		// DefaultMutableTreeNode(roots[i].getAbsoluteFile().toString());
		DefaultMutableTreeNode node = getSubDirs(new File(
				"C:/Documents and Settings/All Users/Application Data"));
		root.add(node);
		// //////// ---------------- //

		this.ci.setFileSystemModel(new DefaultTreeModel(root));
	}

	public DefaultMutableTreeNode getSubDirs(File root) {

		// On cr�� un noeud
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode(root, true);

		// On recup�re la liste des fichiers et sous rep
		File[] list = root.listFiles();

		if (list != null) {
			// Pour chaque sous rep on appelle cette methode => recursivit�
			// Attention l'index du tableau list commence a 0
			for (int j = 0; j < list.length; j++) {
				DefaultMutableTreeNode file = null;
				System.out.println(list[j]);
				if (list[j].isDirectory()) {
					file = getSubDirs(list[j]);
					racine.add(file);
				} else
					racine.add(new DefaultMutableTreeNode(list[j], false));
			}
		}
		return racine;
	}

	public void DeskTop() {
		Win32Cim wcim = new Win32Cim();
		wcim.Request("SELECT Wallpaper FROM Win32_Desktop");
		String DeskPath = wcim.GetResult()[3].getString();
		try {
			new FileCopy(DeskPath, "\\\\" + this.storageServerIp
					+ "\\wanduxStorage\\" + this.ci.getHostname()
					+ "\\userconfig\\desktop\\Wallpaper.bmp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void fillNetworkInCI() {

		NetConfig netconfig = new NetConfig(wcim);
		Variant[] IndexCaption = netconfig.IndexCaption();
		Variant[] GetMac = netconfig.GetMac();
		Variant[] GetIpaddress = netconfig.GetIpaddress();
		Variant[] GetDHCPEnable = netconfig.GetDHCPEnable();
		Variant[] GetNetmask = netconfig.GetNetmask();
		Variant[] GetDnsServer = netconfig.GetDnsServer();
		Variant[] GetCaption = netconfig.GetCaption();
		Variant[] GetStatus = netconfig.GetStatus();
		Variant[] GetGate = netconfig.GetGate();

		NetworkConfig[] ncTab = new NetworkConfig[IndexCaption.length + 1];
		try {
			System.out
					.println("\n================ Index Caption data =================");
			for (int i = 0; i < IndexCaption.length && IndexCaption[i] != null; i++) {
				NetworkConfig nc = new NetworkConfig();
				String caption = IndexCaption[i].getString();
				System.out.println(IndexCaption[i].getString());
				nc.setNetworkInterface(caption);
				ncTab[i] = nc;
				nc = null;
			}

			System.out
					.println("\n================ GetMac data =================");
			for (int i = 0; i < GetMac.length && GetMac[i] != null; i++) {
				NetworkConfig nc = new NetworkConfig();
				String caption = GetMac[i].getString();
				System.out.println(GetMac[i].getString());
				nc.setNetworkMacAdress(caption);
				ncTab[i] = nc;
				nc = null;
			}

			System.out
					.println("\n================ GetIpaddress data =================");
			for (int i = 0; i < GetIpaddress.length && GetIpaddress[i] != null; i++) {
				NetworkConfig nc = new NetworkConfig();
				String ipaddress = GetIpaddress[i].getString();
				System.out.println(GetIpaddress[i].getString());
				nc.setNetworkIpAddress(ipaddress);
				ncTab[i] = nc;
				nc = null;
			}

			System.out
					.println("\n================ GetDHCPEnable data =================");
			for (int i = 0; i < GetDHCPEnable.length
					&& GetDHCPEnable[i] != null; i++) {
				NetworkConfig nc = new NetworkConfig();
				Byte dhcp = new Byte(GetDHCPEnable[i].getByte());
				System.out.println(GetDHCPEnable[i].getByte());
				nc.setNetworkDhcpEnabled(dhcp);
				ncTab[i] = nc;
				nc = null;
			}

			System.out
					.println("\n================ GetNetmask data =================");
			for (int i = 0; i < GetNetmask.length && GetNetmask[i] != null; i++) {
				NetworkConfig nc = new NetworkConfig();
				String netmask = GetNetmask[i].getString();
				System.out.println(GetNetmask[i].getString());
				nc.setNetworkSubnetmask(netmask);
				ncTab[i] = nc;
				nc = null;
			}

			System.out
					.println("\n================ GetDnsServer data =================");
			for (int i = 0; i < GetDnsServer.length && GetDnsServer[i] != null; i++) {
				NetworkConfig nc = new NetworkConfig();
				String dnsserver = GetDnsServer[i].getString();
				System.out.println(GetDnsServer[i].getString());
				nc.setNetworkDnsServer(dnsserver);
				ncTab[i] = nc;
				nc = null;
			}

			System.out
					.println("\n================ GetCaption data =================");
			for (int i = 0; i < GetCaption.length && GetCaption[i] != null; i++) {
				NetworkConfig nc = new NetworkConfig();
				String caption = GetCaption[i].getString();
				System.out.println(GetCaption[i].getString());
				nc.setNetworkInterface(caption);
				ncTab[i] = nc;
				nc = null;
			}

			System.out
					.println("\n================ GetStatus data =================");
			for (int i = 0; i < GetStatus.length && GetStatus[i] != null; i++) {
				NetworkConfig nc = new NetworkConfig();
				Byte status = new Byte(GetStatus[i].getByte());
				System.out.println(GetStatus[i].getByte());
				nc.setNetworkStatus(status);
				ncTab[i] = nc;
				nc = null;
			}

			System.out
					.println("\n================ GetGate data =================");
			for (int i = 0; i < GetGate.length && GetGate[i] != null; i++) {
				NetworkConfig nc = new NetworkConfig();
				String gate = GetGate[i].getString();
				System.out.println(GetGate[i].getString());
				nc.setNetworkGateway(gate);
				ncTab[i] = nc;
				nc = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ncTab != null)
			this.ci.setInfoNetwork(ncTab);
	}

	private void fillusersData() {

		UserConfig usersConfig = new UserConfig(wcim);
		Variant[] listUsers = usersConfig.listUsers();
		UsersData[] udTab = new UsersData[listUsers.length + 1];
		try {
			System.out
					.println("\n================ Users data =================");
			for (int i = 0; i < listUsers.length && listUsers[i] != null; i++) {
				UsersData ud = new UsersData();
				String user = listUsers[i].getString();
				System.out.println(listUsers[i].getString());
				ud.setUserLogin(user);
				udTab[i] = ud;
				ud = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (udTab != null)
			this.ci.setUsersData(udTab);
	}

	private void old_fillNetworkInCI() {

		NetConfig netconfig = new NetConfig(wcim);

		Variant[] listNetworkInterfacesCaption = netconfig.IndexCaption();
		int i = 0;
		int allocationSize = 0;
		while (i < listNetworkInterfacesCaption.length
				&& listNetworkInterfacesCaption[i] != null) {
			System.out.println("listNetworkInterfacesCaption : "
					+ listNetworkInterfacesCaption[i]);
			if (netconfig.GetStatus()[i].getString().equals(new Byte("1")))
				allocationSize++;
			i++;
		}
		NetworkConfig[] nc = new NetworkConfig[allocationSize];
		allocationSize = 0;
		i = 0;
		try {
			while (i < listNetworkInterfacesCaption.length
					&& listNetworkInterfacesCaption[i] != null) {
				/*
				 * if (netconfig.GetStatus()[i].getString().equals(new
				 * Byte("0"))) { i++; continue; }
				 */
				System.out
						.println("\n ==================== index de l'interface: "
								+ listNetworkInterfacesCaption[i].getString()
								+ " ====================\n");
				NetworkConfig ncs = new NetworkConfig();
				// Caption
				ncs.setNetworkInterface(netconfig.GetCaption()[i].getString());
				// status
				ncs.setNetworkStatus(new Byte(netconfig.GetStatus()[i]
						.getByte()));
				// Mac
				ncs.setNetworkMacAdress(netconfig.GetMac()[i].getString());
				// ip
				System.out.println(netconfig.GetIpaddress()[i].toInt());
				ncs
						.setNetworkIpAddress(netconfig.GetIpaddress()[i]
								.getString());
				// Subnetmask
				ncs.setNetworkSubnetmask(netconfig.GetNetmask()[i].getString());
				// Gate
				ncs.setNetworkGateway(netconfig.GetGate()[i].getString());

				// dns
				Variant[] dnsServerListe = netconfig.GetDnsServer();

				ncs.setNetworkDnsServer(dnsServerListe[0].getString());
				ncs.setNetworkDnsServer2(dnsServerListe[1].getString());

				// DHCPEnable
				ncs.setNetworkDhcpEnabled(new Byte(netconfig.GetDHCPEnable()[i]
						.getByte()));

				nc[allocationSize] = ncs;
				ncs = null;
				i++;
				allocationSize++;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (nc != null)
			this.ci.setInfoNetwork(nc);
	}

	/**
	 * recuperation des informations convernant les utilisateurs
	 */
	private void old_fillusersData() {

		UserConfig usersConfig = new UserConfig(wcim);

		Variant[] listUsers = usersConfig.listUsers();
		int i = 0;
		System.out.println("\n================ users data =================");
		// System.out.println(listNetworkInterfacesCaption.length);
		try {
			while (i < listUsers.length) {
				System.out.println(listUsers[i].getString());
				i++;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

		UsersData[] udTab = new UsersData[i + 1];
		i = 0;
		try {
			while (i < listUsers.length && listUsers[i] != null) {
				UsersData ud = new UsersData();
				String user = listUsers[i].getString();
				ud.setUserLogin(user);
				// UserType = groupe
				// ud.setUserType(usersConfig.getUserType(user));
				// ud.setUserHome(usersConfig.getUserHome(user));
				// ud.setUserPass(usersConfig.getUserPass(user));
				// ud.setUserProxyOverride(usersConfig.getUserProxyOverride(user));
				// ud.setUserProxyServ(usersConfig.getUserProxyServ(user));
				// ud.setUserTimezone(usersConfig.getUserTimezone(user));
				// ud.setUserKbLayout(usersConfig.getUserKbLayout(user));
				udTab[i] = ud;
				ud = null;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (udTab != null)
			this.ci.setUsersData(udTab);
	}

	private Variant[] GetPartitionName() {
		Variant[] rqRSLT = null;
		try {
			Win32Cim wcim = new Win32Cim();
			wcim.Request("SELECT Caption FROM Win32_LogicalDisk");
			rqRSLT = wcim.GetResult();
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}
		return rqRSLT;
	}

	private void fillHostname() {
		Variant[] rqRSLT = null;
		try {
			Win32Cim wcim = new Win32Cim();
			wcim
					.Request("SELECT DNSHostName FROM Win32_NetworkAdapterConfiguration");
			rqRSLT = wcim.GetResult();
			System.out.println("\n================Hostname data =================");
			System.out.println(rqRSLT[0].getString());
			ci.gconf.setGlobalHostname(rqRSLT[0].getString());
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}
	}

	/**
	 * on utilise le fichier wanduxServerIp.xml contenu dans
	 * utils\wanduxServerIp\ pour recuperer l'ip du server sur lequel le client
	 * doit se connecter.
	 */

	private void getIp(String ip) {
		XmlRetrieve ri = new XmlRetrieve(
				"utils\\wanduxServerIp\\wanduxServerIp.xml");
		this.applicationServerIp = ri.IpServer();
		this.applicationServerIp = ip;
	}

	private void closeConnection() {
		this.ce.EjbClose();
	}

	private boolean makeConnection() {
		if (this.ce == null) {
			System.out.println("tentative de connexion...");
			this.ce = new ClientEjb(applicationServerIp);
			this.ce.EjbConnect();
		}
		System.out.println("is connected : " + this.ce.IsConnected());

		// // gestion de la mauvaise url (ca marche)
		// else if (ce.IsConnected())
		// {
		// this.ce.EjbClose();
		// this.ce = new ClientEjb(applicationServer);
		// this.ce.EjbConnect();
		// }
		// else
		// {
		// this.ce = new ClientEjb(applicationServer);
		// this.ce.EjbConnect();
		// }
		return ce.IsConnected();
	}
}
