/*
 * Created on 15 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.post;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
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
	private String currentHostname = "";
	private String storageServerIp = "";
	
	static InputStreamReader converter = new InputStreamReader(System.in);
	static BufferedReader in = new BufferedReader(converter);
	
	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.out.println("enter the Wandux Server IP : ");
			new WanduxPost(getString());
		}
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
			this.ci = ce.getBean().getCi(this.storageServerIp);
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
		try {
			Process p = Runtime.getRuntime().exec("hostname");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	public void init()
	{
		
	}
	
	public void usersGroupsCreation()
	{
		System.out.println("usersGroupsCreation"+ci);
		System.out.println("usersGroupsCreation"+ci.udata);
		System.out.println("usersGroupsCreation"+ci.udata.length);
		for (int i = 0; i < ci.udata.length; i++)
		{
			try {
				Process p = Runtime.getRuntime().exec("addgroup " + ci.udata[i].getUserType());
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				this.currentHostname = input.readLine();
				input.close();
			} catch (Exception err) {
				err.printStackTrace();
			}

			try {
				Process p = Runtime.getRuntime().exec("adduser  --group "  + ci.udata[i].getUserType() + " " + ci.udata[i].getUserLogin());
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				this.currentHostname = input.readLine();
				input.close();
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
// reception des groups et utilisateur
//		try {
//			Process p = Runtime.getRuntime().exec("hostname");
//			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
//			this.currentHostname = input.readLine();
//			input.close();
//		} catch (Exception err) {
//			err.printStackTrace();
//		}
	}

	public void confWebPrograms()
	{
		
	}
	
	public void getDataFromStorageServer()
	{
		File fromServer = new File("/home/samba/wanduxStorage/" + this.currentHostname);
		fromServer.mkdir();
		File f = new File("/wandux/");
		if (f.exists() ==  false)
			f.mkdir();

		try { // TODO a changer par l url samba !!
			Process p = Runtime.getRuntime().exec("cp -R /home/samba/wanduxStorage/" + this.currentHostname + " /wandux/");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}
