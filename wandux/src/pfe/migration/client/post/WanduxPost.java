/*
 * Created on 15 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.post;

import java.io.File;
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
		
		usersGroupsCreation();
		getDataFromStorageServer();
		
		ce.EjbClose();
	}
	
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("usage: java WanduxPost ip_serveur_ejb");
			System.exit (0);
		}
		new WanduxPost(args[0]);
	}
	
	public void getCurrentIp()
	{
		this.currentIP = System.getenv("hostname");
	}
	
	public void usersGroupsCreation()
	{
		// reception des groups et utilisateur
	}

	public void getDataFromStorageServer()
	{
		FSNodeCopy fsnc = new FSNodeCopy();
		
//		System.out.println("dup");
//		File io = new File("C:\\Documents and Settings");
//		System.out.println("File: " + io);
//		System.out.println("size: " + io.list().length);
//		
//		Pattern p = Pattern.compile(".*[dD][uU][pP].*");
//		
//		for (int i = 0; i < io.list().length; i++)
//		{
//			Matcher m = p.matcher(io.list()[i]);
//			System.out.println(">> " + io.list()[i] + " - " + m.find());
//		}
//		System.out.println("dup");

		File fromServer = new File("\\\\" + this.storageServerIp + "\\wanduxStorage\\" + this.ci.getHostname());
		Pattern p = Pattern.compile("disk.[^aA]");

		for (int i = 0; i< fromServer.length(); i++)
		{
			Matcher m = p.matcher(fromServer.list()[i]);
			if (m.find())
				fsnc.GenericCopyNode(fromServer.toString() + fromServer.list()[i], "/mnt/");
		}
	}
}
