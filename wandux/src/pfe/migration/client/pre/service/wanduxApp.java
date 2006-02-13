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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//import pfe.migration.server.ejb.tool.XmlRetrieve;

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
		Variant[][] GetIpaddress = netconfig.GetIpaddress();
		Variant[] GetDHCPEnable = netconfig.GetDHCPEnable();
		Variant[][] GetNetmask = netconfig.GetNetmask();
		Variant[][] GetDnsServer = netconfig.GetDnsServer();
		Variant[] GetCaption = netconfig.GetCaption();
		Variant[] GetStatus = netconfig.GetStatus();
		Variant[][] GetGate = netconfig.GetGate();

		try {
			NetworkConfig[] ncTab = new NetworkConfig[IndexCaption.length + 1];
			for (int i = 0; i < IndexCaption.length && IndexCaption[i] != null; i++) {

				if (GetStatus[i].getBoolean() == false)
					continue;

				System.out
						.println("\n================ Index Caption data =================");
				NetworkConfig nc = new NetworkConfig();
				String indexcaption = IndexCaption[i].getString();
				System.out.println(IndexCaption[i].getString());
				nc.setNetworkInterface(indexcaption);

				System.out
						.println("\n================ GetMac data =================");
				String Macadd = GetMac[i].getString();
				System.out.println(GetMac[i].getString());
				nc.setNetworkMacAdress(Macadd);
				System.out
						.println("\n================ GetIpaddress data =================");
				String ipaddress = GetIpaddress[i][0].getString();
				System.out.println(GetIpaddress[i][0].getString());
				nc.setNetworkIpAddress(ipaddress);
				System.out
						.println("\n================ GetDHCPEnable data =================");
				Boolean booldhcp = new Boolean(GetDHCPEnable[i].getBoolean());
				Byte dhcp = null;
				if (booldhcp.booleanValue() == true)
					dhcp = new Byte("1");
				else
					dhcp = new Byte("0");
				System.out.println(dhcp);
				nc.setNetworkDhcpEnabled(dhcp);
				System.out
						.println("\n================ GetNetmask data =================");
				String netmask = GetNetmask[i][0].getString();
				System.out.println(GetNetmask[i][0].getString());
				nc.setNetworkSubnetmask(netmask);
				System.out
						.println("\n================ GetDnsServer data =================");
				String dnsserver = GetDnsServer[i][0].getString();
				System.out.println(GetDnsServer[i][0].getString());
				nc.setNetworkDnsServer(dnsserver);
				System.out
						.println("\n================ GetCaption data =================");
				String caption = GetCaption[i].getString();
				System.out.println(GetCaption[i].getString());
				nc.setNetworkInterface(caption);
				System.out
						.println("\n================ GetStatus data =================");
				Boolean boolstatus = new Boolean(GetStatus[i].getBoolean());
				Byte status = null;
				if (boolstatus.booleanValue() == true)
					status = new Byte("1");
				else
					status = new Byte("0");

				System.out.println(status);
				nc.setNetworkStatus(status);
				System.out
						.println("\n================ GetGate data =================");
				String gate = GetGate[i][0].getString();
				System.out.println(GetGate[i][0].getString());
				nc.setNetworkGateway(gate);
				ncTab[i] = nc;
				nc = null;
			}
			if (ncTab != null)
				this.ci.setInfoNetwork(ncTab);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getGroup(String user, Variant[] listGroupName,
			Variant[] listUserWithGroup) {
		String group = "";

		Pattern p = Pattern.compile(user);
		for (int i = 0; i < listUserWithGroup.length; i += 2) {
			Matcher m = p.matcher(listUserWithGroup[i].toString());
			if (m.find()) {
				i++;
				group = listUserWithGroup[i].toString();
				break;
			}
		}
		for (int j = 0; j < listGroupName.length; j++) {
			Pattern p1 = Pattern.compile(listGroupName[j].toString());
			Matcher m1 = p1.matcher(group);
			if (m1.find())
				return listGroupName[j].toString();
		}
		return "users";
	}

	private void fillusersData() {

		UserConfig usersConfig = new UserConfig(wcim);
		Variant[] listUsers = usersConfig.listUsers();
		UsersData[] udTab = new UsersData[listUsers.length + 1];

		Variant[] listGroupName = usersConfig.listGroup();
		Variant[] listUserWithGroup = usersConfig.getUserGroup();
		try {
			System.out
					.println("\n================ Users data =================");
			for (int i = 0; i < listUsers.length && listUsers[i] != null; i++) {
				UsersData ud = new UsersData();
				String user = listUsers[i].getString();
				System.out.println(listUsers[i].getString());
				ud.setUserLogin(user);
				ud
						.setUserType(getGroup(user, listGroupName,
								listUserWithGroup));
				udTab[i] = ud;
				ud = null;
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
			System.out
					.println("\n================Hostname data =================");
			int i;
			for (i = 0; rqRSLT[i].isNull(); i++)
				;
			System.out.println(rqRSLT[i].getString());
			ci.gconf.setGlobalHostname(rqRSLT[i].getString());
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}
	}

	/**
	 * on utilise le fichier wanduxServerIp.xml contenu dans
	 * utils\wanduxServerIp\ pour recuperer l'ip du server sur lequel le client
	 * doit se connecter.
	 */

	/*
	 * private void getIp(String ip) { XmlRetrieve ri = new XmlRetrieve(
	 * "utils\\wanduxServerIp\\wanduxServerIp.xml"); this.applicationServerIp =
	 * ri.IpServer(); this.applicationServerIp = ip; }
	 */

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
