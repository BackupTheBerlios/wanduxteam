/*
 * Created on 15 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.post;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.rmi.RemoteException;

import pfe.migration.client.network.ClientEjb;
import pfe.migration.client.network.ComputerInformation;

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
		getDataFromStorageServer();
		confPrograms();
		
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
		for (int i = 0; i < ci.udata.length; i++)
		{
			try {
				Process p = Runtime.getRuntime().exec("groupadd " + ci.udata[i].getUserType());
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				this.currentHostname = input.readLine();
				input.close();
			} catch (Exception err) {
				err.printStackTrace();
			}

			try {
				Process p = Runtime.getRuntime().exec("useradd -g "  + ci.udata[i].getUserType() + " " + ci.udata[i].getUserLogin());
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				this.currentHostname = input.readLine();
				input.close();
				p = Runtime.getRuntime().exec("echo " + ci.udata[i].getUserLogin() + ":wandux | chpasswd");
				input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				this.currentHostname = input.readLine();
				input.close();
			} catch (Exception err) {
				err.printStackTrace();
			}
		}

	public void confPrograms()
	{
		
	}
	
	private void createLocalStorage()
	{
		// Linux Folder /wandux creation
		// Linux folder /tmp/wanduxStorage creation
		File localstorage = new File("/tmp/wanduxStorage/");
		localstorage.mkdir();
		// Linux folder /tmp/wanduxStorage/<hostname> creation
		localstorage = new File("/tmp/wanduxStorage/" + this.currentHostname);
		localstorage.mkdir();
		
        FileOutputStream out; // declare a file output object
        PrintStream p; // declare a print stream object

        try
        {
                // Create a new file output stream
                // connected to "myfile.txt"
                out = new FileOutputStream("/tmp/wanduxcreds");

                // Connect print stream to the output stream
                p = new PrintStream( out );
                p.println ("username=wandux");
                p.println ("password=wandux");
                p.close();
        }
        catch (Exception e)
        {
                System.err.println ("Error writing to file");
        }
        
		try {
			Process smbmount = Runtime.getRuntime().exec("smbmount \\\\\\\\"
												+ this.currentHostname
												+ "\\\\wanduxStorage /tmp/wanduxStorage/" + this.currentHostname
												+ " -o credentials=/tmp/wanduxcreds");
			BufferedReader smblog = new BufferedReader(new InputStreamReader(smbmount.getInputStream()));
			this.currentHostname = smblog.readLine();
			smblog.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getDataFromStorageServer()
	{
		createLocalStorage();
		
		File fromServer = new File("/tmp/wanduxStorage/" + this.currentHostname);
		fromServer.mkdir();
		File f = new File("/wandux/");
		if (f.exists() ==  false)
			f.mkdir();

		try {
			Process p = Runtime.getRuntime().exec("cp -R /tmp/wanduxStorage/" + this.currentHostname + " /wandux/");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}
