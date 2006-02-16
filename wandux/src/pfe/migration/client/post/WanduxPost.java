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
 * @author cb6
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * TODO Inbox Outbox fr/en
 * 		Favorites
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

		getCurrentHostname();
		
		ce.EjbConnect();

		try {
			this.ci = ce.getBean().getCi(this.currentHostname);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		init();
		usersGroupsCreation();
		getDataFromStorageServer();
		confPrograms();
		
		ce.EjbClose();
	}
	
	public void getCurrentHostname()
	{
		try {
			Process p = Runtime.getRuntime().exec("hostname");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			//this.currentHostname = input.readLine();
			this.currentHostname = "cb6-eb3940fa09f";
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	public void init()
	{
		int i;
		
		System.out.println("hostname :" + this.ci.gconf.getGlobalHostname());
		System.out.println(" ");
		for (i = 0; i < this.ci.udata.length; i++)
		{
			System.out.println("user :" + this.ci.udata[i].getUserLogin());
			System.out.println("type :" + this.ci.udata[i].getUserType());
			System.out.println(" ");
		}
	}
	
	public void usersGroupsCreation()
	{
//		try {
//			Process p2 = Runtime.getRuntime().exec("mkdir /tmp/wanduxtest"); /* groupname */
//			BufferedReader input2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
//			this.currentHostname = input2.readLine();
//		input2.close();
//		} catch (Exception err) {
//			err.printStackTrace();
//		}

        FileOutputStream out; // declare a file output object
        PrintStream ps = null; // declare a print stream object

        try
        {
                // Create a new file output stream
                // connected to "myfile.txt"
                out = new FileOutputStream("/tmp/pass.sh");
                // Connect print stream to the output stream
                ps = new PrintStream( out );
//                ps.println ("This is written to a file");
//                ps.close();
        }
        catch (Exception e)
        {
                System.err.println ("Error writing to file");
        }

		for (int i = 0; i < this.ci.udata.length; i++)
		{
			try {
				Process p = Runtime.getRuntime().exec("groupadd "
													+ ci.udata[i].getUserType()); /* groupname */
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				this.currentHostname = input.readLine();
				input.close();
			} catch (Exception err) {
				err.printStackTrace();
			}

			try {
				/* Affecting user to the group of the same name */
				Process p = Runtime.getRuntime().exec("useradd -m -g "
													+ ci.udata[i].getUserType() + " " /* groupname */
													+ ci.udata[i].getUserLogin());    /* user login */
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				this.currentHostname = input.readLine();
				input.close();
			} catch (Exception err) {
				err.printStackTrace();
			}

			ps.println("echo " + ci.udata[i].getUserLogin() + ":wandux | chpasswd");
		}
		ps.close();
		try {
			/* Setting password for the new created user */
			Process p = Runtime.getRuntime().exec("sh /tmp/pass.sh");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();
			p = Runtime.getRuntime().exec("rm -f /tmp/pass.sh");
			input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}


//	 mount /tmp/wanduxStorage
//	 tout est dans /samba
//	 

//	 installer la jvm et recuperer le wanduxPost sur le serveur
	private void impBookmarks()
	{
	    // apres avoir copie pyFavConv-0.1 dans le repertoire /root/wanduxinstall
	    // depuis le script de post install
	    for (int i = 0; i < ci.udata.length; i++)
		{
		    try {
		            FileOutputStream out = new FileOutputStream("/tmp/wanduxbookmarks");
		            PrintStream ps;
		            // Connect print stream to the output stream
		            ps = new PrintStream( out );
		            ps.println ("/root/wanduxinstall/pyFavConv-0.1/pyFavConv.py "
						      + "/wandux/conf/Documents\\ and\\ Settings/"
						      + ci.udata[i].getUserLogin()
						      + "/Favorites"
						      + " /tmp/bookmarks.xml");
		            ps.close();
		    	
			Process p = Runtime.getRuntime().exec("sh /tmp/wanduxbookmarks");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();

			p = Runtime.getRuntime().exec("mkdir /home/"
				      + ci.udata[i].getUserLogin()
				      + "/.kde/share/apps/konqueror/ -p");
			input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();

			p = Runtime.getRuntime().exec("mv /tmp/bookmarks.xml /home/"
						      + ci.udata[i].getUserLogin()
						      + "/.kde/share/apps/konqueror/");
			input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();

			p = Runtime.getRuntime().exec("chown -R "
										+ ci.udata[i].getUserLogin()
										+ " /home/"
										+ ci.udata[i].getUserLogin()
										+ "/.kde/share/apps/konqueror/");
			input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();
	
		    } catch (Exception err) {
			err.printStackTrace();
		    }
		}
	}

	private void impMailBoxes()
	{
	    // apres avoir copie libdbx_1.0.3 dans le repertoire /root/wanduxinstall
	    // depuis le script de post install
	    for (int i = 0; i < ci.udata.length; i++)
		{
		    try {
			Process p = Runtime.getRuntime().exec("mkdir /home/"
							      + ci.udata[i].getUserLogin()
							      +"/.evolution/mail/local -p");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();
			
			p = Runtime.getRuntime().exec("chown -R "
					+ ci.udata[i].getUserLogin()
					+ " /home/"
					+ ci.udata[i].getUserLogin()
					+ "/.evolution");
			input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();

            FileOutputStream out = new FileOutputStream("/tmp/wanduxmailboxes");
            PrintStream ps;
            // Connect print stream to the output stream
            ps = new PrintStream( out );
            ps.println ("/root/wanduxinstall/libdbx_1.0.3/readdbx < "
					  + "/wandux/conf/Documents\\ and\\ Settings/"
					  + ci.udata[i].getUserLogin()
					  + "/Local\\ Settings/Application\\ Data/Identities/"
					  + "`ls /wandux/conf/Documents\\ and\\ Settings/"
					  + ci.udata[i].getUserLogin()
					  + "/Local\\ Settings/Application\\ Data/Identities/`"
					  + "/Microsoft/Outlook\\ Express/"
					  + "Inbox.dbx 0&> /home/"
				      + ci.udata[i].getUserLogin()
				      + "/.evolution/mail/local/Inbox");
            ps.close();
			
			p = Runtime.getRuntime().exec("sh /tmp/wanduxmailboxes");
			input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();

			
            ps = new PrintStream( out );
            ps.println ("/root/wanduxinstall/libdbx_1.0.3/readdbx < "
					  + "/wandux/conf/Documents\\ and\\ Settings/"
					  + ci.udata[i].getUserLogin()
					  + "/Local\\ Settings/Application\\ Data/Identities/"
					  + "`ls /wandux/conf/Documents\\ and\\ Settings/"
					  + ci.udata[i].getUserLogin()
					  + "/Local\\ Settings/Application\\ Data/Identities/`"
					  + "/Microsoft/Outlook\\ Express/"
					  + "Outbox.dbx 0&> /home/"
				      + ci.udata[i].getUserLogin()
				      + "/.evolution/mail/local/Outbox");
            ps.close();
			
			
			p = Runtime.getRuntime().exec("sh /tmp/wanduxmailboxes");
			input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();

			p = Runtime.getRuntime().exec("chown -R "
					+ ci.udata[i].getUserLogin()
					+ " /home/"
					+ ci.udata[i].getUserLogin()
					+ "/.evolution");
			input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();

		    } catch (Exception err) {
			err.printStackTrace();
		    }
		}
	}

	private void impAddressBook()
	{
	    // apres avoir copie libwab-051010 dans le repertoire /root/wanduxinstall
	    // depuis le script de post install
	    for (int i = 0; i < ci.udata.length; i++)
		{
		    try {
	            FileOutputStream out = new FileOutputStream("/tmp/wanduxcontacts");
	            PrintStream ps;
	            // Connect print stream to the output stream
	            ps = new PrintStream( out );
	            ps.println ("/root/wanduxinstall/libwab-051010/wabread "
						  + "/wandux/conf/Documents\\ and\\ Settings/"
					      + ci.udata[i].getUserLogin()
					      + "/Application\\ Data/Microsoft/Address\\ Book/"
					      + ci.udata[i].getUserLogin()
					      + ".wab 0&> "
					      + "/wandux/"
					      + ci.udata[i].getUserLogin()
					      + ".ldif");
	            ps.close();
	            
			Process p = Runtime.getRuntime().exec("sh /tmp/wanduxcontacts");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();

			// mettre les ldiff dans un repertoire import afin de les importer a la
			// main depuis evolution
		    } catch (Exception err) {
			err.printStackTrace();
		    }
		}
	}

	public void confPrograms()
	{

	    impBookmarks();
	    impMailBoxes();
//	    impAddressBook();
	}

	
	private void createLocalStorage()
	{
		// Linux Folder /wandux creation
		// Linux folder /tmp/wanduxStorage creation
		File localstorage = new File("/tmp/wanduxStorage/");
		localstorage.mkdir();
		// Linux folder /tmp/wanduxStorage/<hostname> creation
		localstorage = new File("/tmp/wanduxStorage/" + this.ci.gconf.getGlobalHostname());
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
            // Create a new file output stream
            // connected to "myfile.txt"
            out = new FileOutputStream("/tmp/mountsmb.sh");

            // Connect print stream to the output stream
            p = new PrintStream( out );
            p.println ("smbmount \\\\\\\\"
					+ this.storageServerIp
					+ "\\\\wanduxStorage /tmp/wanduxStorage/"
					//+ this.ci.gconf.getGlobalHostname()
					+ " -o credentials=/tmp/wanduxcreds");
            p.close();
			Process smbmount = Runtime.getRuntime().exec("sh /tmp/mountsmb.sh");
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
            FileOutputStream out = new FileOutputStream("/tmp/wanduxcopy");
            PrintStream ps;
            // Connect print stream to the output stream
            ps = new PrintStream( out );
            ps.println ("cp -R /tmp/wanduxStorage/" + this.ci.gconf.getGlobalHostname() + "/* /wandux");
            ps.close();
			Process p = Runtime.getRuntime().exec("sh /tmp/wanduxcopy");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			this.currentHostname = input.readLine();
			input.close();
			
			// umount /tmp/wanduxStorage
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}
