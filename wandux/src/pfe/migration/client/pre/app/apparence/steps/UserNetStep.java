/*
 * Created on 23 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.apparence.steps;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;

import net.sf.hibernate.HibernateException;

import pfe.migration.client.network.ClientEjb;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.client.pre.app.LanguageSettings;
import pfe.migration.client.pre.app.NetSettings;
import pfe.migration.client.pre.app.UserConfig;
import pfe.migration.client.pre.system.IeParam;
import pfe.migration.client.pre.system.KeyVal;
import pfe.migration.server.ejb.bdd.GlobalConf;
import pfe.migration.server.ejb.bdd.NetworkConfig;
import pfe.migration.server.ejb.bdd.ParamIe;
import pfe.migration.server.ejb.bdd.UsersData;

/**
 * @author dupadmin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserNetStep extends JPanel
{
	private ComputerInformation ci = null;
	
	public UserNetStep(ClientEjb ce)
	{
//		JScrollPane scrollPane= new JScrollPane();
//		scrollPane.setPreferredSize(new Dimension(400,400));
//		scrollPane.setViewportView(printSystemInfos(systemInformation(ce)));
//		this.add(scrollPane);
		
		systemInformation(ce);
		this.add(printSystemInfos());
	}

	public void systemInformation(ClientEjb ce)
	{
		KeyVal kvusers = new KeyVal();
		this.ci = new ComputerInformation();
		String macaddr = NetSettings.FindMacAddr();
		this.ci.gconf = new GlobalConf();
		this.ci.gconf.setGlobalKey(new Integer(macaddr.hashCode()));
		this.ci.netconf = new NetworkConfig();
		this.ci.netconf.setNetworkKey(this.ci.gconf.getGlobalKey());
		this.ci.udata = new UsersData();
		this.ci.udata.setUserKey(this.ci.gconf.getGlobalKey());
		this.ci.ieconf = new ParamIe(new Integer("METTRE LE LOGIN ICI".hashCode()));
		
		//Hostname
		this.ci.gconf.setGlobalHostname(kvusers.getKeyValLocalMachine(
							"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters", "HostName"));

		//Dhcp Enabled
		String curinterface = kvusers
		.FindCurrentInterFace("SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces");

		KeyVal.FindLinkage();
		
		// Proxy
		IeParam ieparam = new IeParam();
		this.ci.ieconf.setIeProxyServer(ieparam.getProxyServer());
		this.ci.ieconf.setIeProxyOverride(ieparam.getProxyOverride());
		//this.ci.ieconf.setIeProxyAutoConfigUrl(ieparam.getAutoConfigURL());
		
		if (!curinterface.equals("dhcpdisabled"))
		{
			this.ci.netconf.setNetworkDhcpEnabled(new Byte("1"));
			//DhcpServer
			System.out.println("dhcp enabled");
			String enabledhcp = new String(kvusers.getKeyValLocalMachine(
					"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
							+ curinterface, "enabledhcp"));
			
			//DEPRECATED
//			ci.netconf.setDhcpServer(kvusers.getKeyValLocalMachine(
//								"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
//								+ curinterface, "DhcpServer"));
			
//			DEPRECATED
			//DHcp Ip Adress
			this.ci.netconf.setNetworkIpAddress(kvusers.getKeyValLocalMachine(
								"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
								+ curinterface, "dhcpIpaddress"));
			
//			DEPRECATED
			//DhcpSubNetMask
//			ci.netconf.setDhcpSubnetmask(kvusers.getKeyValLocalMachine(
//								"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
//								+ curinterface, "DhcpSubnetMask"));
		}
		else
		{
			this.ci.netconf.setNetworkDhcpEnabled(new Byte("0"));
			System.out.println("dhcp disabled");
			curinterface = KeyVal.FindLinkage();

			System.out.println(kvusers.getKeyValLocalMachine(
								"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
								+ curinterface, "DefaultGateway"));
			System.out.println(kvusers.getKeyValLocalMachine(
					"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
					+ curinterface, "Ipaddress"));
			System.out.println(kvusers.getKeyValLocalMachine(
					"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
					+ curinterface, "SubnetMask"));
			
//			System.out.println(kvusers.getKeyValLocalMachine(
//					"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
//					+ curinterface, "NameServer"));
			String nsreg = kvusers.getKeyValLocalMachine(
					"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces\\"
					+ curinterface, "NameServer");

			Hashtable ht = new Hashtable();
			StringTokenizer st = new StringTokenizer(nsreg, ",");
			Vector nstable = new Vector();
			String ns = null;
			int i;

			for (i = 0; i <= st.countTokens(); i++)
			{
				nstable.add(st.nextToken());
			}

			for (int j = 0; j < i; j++)
			{
				ns = (String)nstable.elementAt(j);
				System.out.println(ns);
			}
		}
		this.ci.udata.setUserKbLayout(LanguageSettings.GetDefaultKBLayout(ce));
		LanguageSettings lns = new LanguageSettings();
//		ProgramsLister proglist = new ProgramsLister();
//		ProgramsLister.ParseExtensions();

		//GROS ABUS : Login pas encore recupere
		this.ci.udata.setUserLogin(System.getProperty("user.name"));
		
		this.ci.udata.setUserHome(System.getProperty("user.home"));
		this.ci.udata.setUserTimezone(System.getProperty("user.timezone"));
		UserConfig uc = new UserConfig();
		this.ci.udata.setUserProxyServ(UserConfig.ProxyServer());
		this.ci.udata.setUserProxyOverride(UserConfig.ProxyOverride());
		
		
		String s = null;
		try {
			Process p = Runtime.getRuntime().exec(
					"CMD /c \"echo %systemroot%\"");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			s = stdInput.readLine();
//			if ((s = stdInput.readLine()) != null) {
//				System.out.println(s);
//			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		this.ci.udata.setUserBgimg(UserConfig.BGImage());
		//La MacAddr est desormais gettee dans les premieres lignes de cette fonction
		this.ci.netconf.setNetworkMacAdress(macaddr);
		
//		EXEMPLE DE CODE POUR GETTER ComputerInformation
//		(a surrounder avec un try/catch)
//		
//		ComputerInformation toto =  ce.getComputerInformation(macaddr);
//		toto.gconf.getGlobalHostname();
//		toto.netconf.getNetworkIpAddress();
//		toto.udata.getUserLogin();
	
		ce.Transfert(this.ci);
	}
	
	public static String replace(String orig, String from, String to)
	{
		int start = orig.indexOf(from);
		if (start == -1)
			return orig;
		int lf = from.length();
		char [] origChars = orig.toCharArray();
		StringBuffer buffer = new StringBuffer();
		int copyFrom = 0;

		while (start != -1)
		{
			buffer.append(origChars, copyFrom, start - copyFrom);
			buffer.append(to);
			copyFrom = start + lf;
			start = orig.indexOf(from, copyFrom);
		}
		buffer.append(origChars, copyFrom, origChars.length - copyFrom);
		return buffer.toString();
	}
	
	/**
	 * Graphical component : System informations
	 */
	public JList printSystemInfos()
	{
		List components  = new ArrayList();
		
		components.add("----------   Global configuration   -------------");
		components.add("");
		components.add("HostName: " + this.ci.gconf.getGlobalHostname());
		components.add("");
		components.add("----------   Network configuration   ------------");
		components.add("");
		if (this.ci.netconf.getNetworkDhcpEnabled().byteValue() == 1)
			components.add("Dhcpenabled: yes");
		else
			components.add("Dhcpenabled: no");
		components.add("");
//		components.add("DhcpServer: " + ci.netconf.getDhcpServer());
//		components.add("DhcpIpaddress: " + ci.netconf.getDhcpAdress());
//		components.add("DhcpSubnetMask: " + ci.netconf.getDhcpSubnetmask());
		components.add("");
		components.add("-----------   Users configuration   -------------");
		components.add("");
		return new JList (components.toArray());
	}

	public ComputerInformation getCurrentComputerInformation()
	{
		return this.ci;
	}
}
