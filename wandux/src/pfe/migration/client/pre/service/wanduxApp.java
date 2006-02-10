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

import com.jacob.com.JacobException;
import com.jacob.com.Variant;

public class wanduxApp {
	private String applicationServerIp = "";

	protected String storageServerIp = "";

	protected ComputerInformation ci = null;

	private ClientEjb ce = null;

	// wandux wmi bridge, permet de gerer l'execution des requettes wmi
	WanduxWmiBridge wwb = null;

	private static String rootCIMV2 = "root\\CIMV2";

	String rootCIMV2ApplicationsMicrosoftIE = "root\\CIMV2\\Applications\\MicrosoftIE";

	// prevu pour contenir la requette wmi
	String rq = null;

	// prevu pour contenir l'element a recuperer dans la quette wmi
	String wzName = null;

	// resultat de la requette renvoye par la dll
	String[] rqRSLT = null;

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
		// iniatalise la connexiion wmi
		WanduxWmiInfoManager();
		// TODO recuperer l ip du serveur d appli depuis un fichier comme prevu
		// ....
		// this.applicationServerIp = "127.0.0.1";

		// TODO recuperer l ip du serveur d appli depuis un fichier comme prevu
		// ....
		this.ci = new ComputerInformation();

		WanduxWmiInfoManager();
		fillNetworkInCI();
		fillHostname();
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

			File f = new File("\\\\" + this.storageServerIp + "\\wanduxStorage\\" + this.ci.getHostname() + "\\");
			if (f.exists() == false)
				f.mkdir();
			f = new File("\\\\" + this.storageServerIp + "\\wanduxStorage\\" + this.ci.getHostname() + "\\" + disk);
			if (f.exists() == false)
				f.mkdir();
			
			System.out.println("src : " + s);
			System.out.println("dest : " + "\\\\" + this.storageServerIp + "\\wanduxStorage\\" + this.ci.getHostname() + "\\" + disk);
			// System.out.println("\\\\" + this.storageServerIp +
			// "\\wanduxStorage\\" + this.ci.getHostname() + "\\" + disk);
			//cp.CopyNode(s, "\\\\" + this.storageServerIp + "\\wanduxStorage\\" + this.ci.getHostname() + "\\" + disk, true);
			cp.GenericCopyNode(s, "\\\\" + this.storageServerIp + "\\wanduxStorage\\" + this.ci.getHostname() + "\\" + disk);
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

	public void WanduxWmiInfoManager() {
		wwb = new WanduxWmiBridge(rootCIMV2);
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
//		 for (int i = 0; i < roots.length; i++)
//		 {
//			 System.out.println("\n ==================== scan data disk: " +
//			 roots[i].toString() + " ====================\n");
//			 DefaultMutableTreeNode node = getSubDirs(roots[i]); // new
////			 DefaultMutableTreeNode(roots[i].getAbsoluteFile().toString());
//			 root.add(node);
//		 }

		// //////// tmppour lestests //
		System.out.println("\n ==================== scan data disk: "
				+ roots[0].toString() + " ====================\n");
		// DefaultMutableTreeNode node = getSubDirs(roots[0]); // new
		// DefaultMutableTreeNode(roots[i].getAbsoluteFile().toString());
		DefaultMutableTreeNode node = getSubDirs(new File("C:/Documents and Settings/All Users/Application Data"));
		root.add(node);
		// //////// ---------------- //

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
				System.out.println(list[j]);
				if (list[j].isDirectory()) {
					file = getSubDirs(list[j]);
					racine.add(file);
				}
				else
					racine.add(new DefaultMutableTreeNode(list[j], false));
			}
		}
		return racine;
	}

	public void DeskTop() throws IOException {
		Win32Cim wcim = new Win32Cim("SELECT Wallpaper FROM Win32_Desktop");
		String DeskPath = wcim.result[3].getString();

		new FileCopy(DeskPath, "\\\\" + this.storageServerIp
				+ "\\wanduxStorage\\" + this.ci.getHostname()
				+ "\\userconfig\\desktop\\Wallpaper.bmp");
	}

	private void fillNetworkInCI() {
		NetConfig netconfig = new NetConfig(wwb);

		Variant[] listNetworkInterfacesCaption = netconfig
				.listNetworkInterfaces();
		int i = 0;
		int allocationSize = 0;
		while (i < listNetworkInterfacesCaption.length
				&& listNetworkInterfacesCaption[i] != null) {
			System.out.println("listNetworkInterfacesCaption:"
					+ listNetworkInterfacesCaption[i]);
			if (netconfig
					.GetStatus(listNetworkInterfacesCaption[i].getString())
					.equals(new Byte("1")))
				allocationSize++;
			// / listNetworkInterfacesCaption[i];
			i++;
		}
		NetworkConfig[] nc = new NetworkConfig[allocationSize];
		allocationSize = 0;
		i = 0;
		try {
			while (i < listNetworkInterfacesCaption.length
					&& listNetworkInterfacesCaption[i] != null) {
				if (netconfig.GetStatus(
						listNetworkInterfacesCaption[i].getString()).equals(
						new Byte("0"))) {
					i++;
					continue;
				}
				System.out
						.println("\n ==================== index de l'interface: "
								+ listNetworkInterfacesCaption[i].getString()
								+ " ====================\n");
				NetworkConfig ncs = new NetworkConfig();
				// Caption
				ncs
						.setNetworkInterface(netconfig
								.GetCaption(listNetworkInterfacesCaption[i]
										.getString()));
				// status
				ncs
						.setNetworkStatus(netconfig
								.GetStatus(listNetworkInterfacesCaption[i]
										.getString()));
				// Mac
				ncs.setNetworkMacAdress(netconfig
						.GetMac(listNetworkInterfacesCaption[i].getString()));
				// ip
				ncs.setNetworkIpAddress(netconfig
						.GetIpadress(listNetworkInterfacesCaption[i]
								.getString()));
				// Subnetmask
				ncs
						.setNetworkSubnetmask(netconfig
								.GetNetmask(listNetworkInterfacesCaption[i]
										.getString()));
				// Gate
				ncs.setNetworkGateway(netconfig
						.GetGate(listNetworkInterfacesCaption[i].getString()));
				// dns
				String[] dnsServerListe = netconfig
						.GetDnsServer(listNetworkInterfacesCaption[i]
								.getString());
				ncs.setNetworkDnsServer(dnsServerListe[0]);
				ncs.setNetworkDnsServer2(dnsServerListe[1]);
				// DHCPEnable
				ncs.setNetworkDhcpEnabled(netconfig
						.GetDHCPEnable(listNetworkInterfacesCaption[i]
								.getString()));
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
	private void fillusersData() {
		UserConfig usersConfig = new UserConfig(wwb);

		Variant[] listNetworkInterfacesCaption = usersConfig.listUsers();
		int i = 0;
		System.out.println("\n================ users data =================");
		// System.out.println(listNetworkInterfacesCaption.length);
		try {
			while (i < listNetworkInterfacesCaption.length) {
				System.out.println(listNetworkInterfacesCaption[i].getString());
				i++;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

		UsersData[] udTab = new UsersData[i + 1];
		i = 0;
		try {
			while (i < listNetworkInterfacesCaption.length
					&& listNetworkInterfacesCaption[i] != null) {
				UsersData ud = new UsersData();
				// System.out.println("\n ==================== index de
				// l'interface: " + listNetworkInterfacesCaption[i].getString()
				// + " ====================\n");
				String user = listNetworkInterfacesCaption[i].getString();
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
		String rq = "SELECT Caption FROM Win32_LogicalDisk WHERE DriveType = 3";
		String wzName = "Caption"; // element a recuperer depuis la requette
		try {
			rqRSLT = wwb.exec_rq(rq, wzName);
			if (rqRSLT[0].equals("1")) // erreur detected
			{
				System.err.println(rqRSLT[1]);
				return null;
			}
		} catch (JacobException je) {
			je.printStackTrace();
		}
		return rqRSLT;
	}

	private void fillHostname() {
		Variant[] rqRSLT = null;
		String rq = "SELECT Caption FROM Win32_ComputerSystem";
		String wzName = "Caption"; // element a recuperer depuis la requette

		try {
			rqRSLT = wwb.exec_rq(rq, wzName);
			ci.gconf.setGlobalHostname(rqRSLT[0].getString());
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}
		Variant var = rqRSLT[0];
		ci.gconf.setGlobalHostname(var.getString());
	}

	/**
	 * TODO on utilise le fichier wanduxServerIp.xml contenu dans
	 * utils\wanduxServerIp\ pour recuperer l'ip du server sur lequel le client
	 * doit se connecter.
	 */
	private void getIp() {
		XmlRetrieve ri = new XmlRetrieve(
				"utils\\wanduxServerIp\\wanduxServerIp.xml");
		// this.applicationServerIp = ri.IpServer();
		this.applicationServerIp = "127.0.0.1";
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
