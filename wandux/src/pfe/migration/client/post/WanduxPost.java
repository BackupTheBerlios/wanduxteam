/*
 * Created on 15 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.post;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pfe.migration.client.network.ClientEjb;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.client.post.system.FSNodeCopy;

/**
 * @author dup
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WanduxPost
{
	private ComputerInformation ci = null;
	private String currentIP = "";
	private String storageServerIp = "";
	
	static InputStreamReader converter = new InputStreamReader(System.in);
	static BufferedReader in = new BufferedReader(converter);
	
	public static void main(String[] args)
	{
		if (args.length != 1)
			new WanduxPost(getString());
		else
			new WanduxPost(args[0]);
	}
	
	public static String getString() {
		try {
			return in.readLine();
		} catch (Exception e) {
			System.out.println("getString() exception, returning empty string");
			return "";
		}
	}
	
	public WanduxPost (String applicationServerIp)
	{
		this.storageServerIp = applicationServerIp;
		ClientEjb ce = new ClientEjb (this.storageServerIp);

		getCurrentIp();
		
		ce.EjbConnect();

		try {
			System.out.println(ce.getBean().sayMe());
			this.ci = ce.getBean().getCi(this.currentIP);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		init();
		usersGroupsCreation();
		confWebPrograms();
		getDataFromStorageServer();
		
		ce.EjbClose();
	}
	
	public void getCurrentIp()
	{
		this.currentIP = System.getenv("hostname");
	}
	
	public void init()
	{
		
	}
	
	public void usersGroupsCreation()
	{
		// reception des groups et utilisateur
	}

	public void confWebPrograms()
	{
		
	}
	
	public void getDataFromStorageServer()
	{
		FSNodeCopy fsnc = new FSNodeCopy();
		File fromServer = new File("\\\\" + this.storageServerIp + "\\wanduxStorage\\" + this.ci.getHostname());
		Pattern p = Pattern.compile(".*disk.[^aA].*");

		for (int i = 0; i< fromServer.length(); i++)
		{
			Matcher m = p.matcher(fromServer.list()[i]);
			if (m.find())
				fsnc.GenericCopyNode(fromServer.toString() + fromServer.list()[i], "/mnt/");
		}
	}
}
