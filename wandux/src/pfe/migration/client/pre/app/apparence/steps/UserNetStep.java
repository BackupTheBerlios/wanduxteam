/*
 * Created on 23 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.apparence.steps;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

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
	
	public UserNetStep(ClientEjb ce)
	{
		this.add(printSystemInfos(systemInformation(ce)));
	}

	public ComputerInformation systemInformation(ClientEjb ce)
	{
		KeyVal kvusers = new KeyVal();
		ComputerInformation ci = new ComputerInformation();
		ci.gconf = new GlobalConf();
		ci.netconf = new NetworkConfig(ci.gconf.getGlobalKey());
		ci.udata = new UsersData(ci.gconf.getGlobalKey());
		ci.ieconf = new ParamIe(/*Mettre ici la Clé unique d'un user*/);
		
		//Hostname
		ci.gconf.setGlobalHostname(kvusers.getKeyValLocalMachine(
							"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters", "HostName"));

		//Dhcp Enabled
		String curinterface = kvusers
		.FindCurrentInterFace("SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Parameters\\Interfaces");

		KeyVal.FindLinkage();
		
		// Proxy
		IeParam ieparam = new IeParam();
		ci.ieconf.setIeProxyServer(ieparam.getProxyServer());
		ci.ieconf.setIeProxyOverride(ieparam.getProxyOverride());
		//ci.ieconf.setIeProxyAutoConfigUrl(ieparam.getAutoConfigURL());
		
		if (!curinterface.equals("dhcpdisabled"))
		{
			ci.netconf.setNetworkDhcpEnabled(new Byte("1"));
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
			ci.netconf.setNetworkIpAddress(kvusers.getKeyValLocalMachine(
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
			ci.netconf.setNetworkDhcpEnabled(new Byte("0"));
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
		LanguageSettings.GetDefaultKBLayout();
		LanguageSettings lns = new LanguageSettings();
//		ProgramsLister proglist = new ProgramsLister();
//		ProgramsLister.ParseExtensions();
		String uhome = System.getProperty("user.home");
		System.out.println("User home:\t\t" + uhome);
		UserConfig uc = new UserConfig();
		String proxyserv = UserConfig.ProxyServer();
		System.out.println("Proxy serv:\t\t" + proxyserv);
		String proxyoverride = UserConfig.ProxyOverride();
		System.out.println("Proxy override:\t\t" + proxyoverride);
		String bgimg =  UserConfig.BGImage();
		System.out.println("Background image:\t" + bgimg);
		String macaddr = NetSettings.FindMacAddr();
		System.out.println("Mac address:\t\t" + macaddr);
		ce.Transfert(ci);
		return ci;
	}
	
	/**
	 * Graphical component : System informations
	 */
	public JList printSystemInfos(ComputerInformation ci)
	{
		List components  = new ArrayList();
		
		components.add("----------   Global configuration   -------------");
		components.add("");
		components.add("HostName: " + ci.gconf.getGlobalHostname());
		components.add("");
		components.add("----------   Network configuration   ------------");
		components.add("");
		if (ci.netconf.getNetworkDhcpEnabled().byteValue() == 1)
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

}
