/*
 * Created on 29 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.tools;

import java.io.File;



/**
 * @author dupadmin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CopyDataOnFileServer implements Runnable
{
	private String [] listFiles = null;
	private String addrMac = "";
	
	public CopyDataOnFileServer(String [] files, String addrMac)
	{
		this.addrMac = addrMac;
		if (files.length > 1)
		{
			listFiles = new String [files.length];
			listFiles = files;
		}
	}

	public void run()
	{
		if (listFiles == null)
			return;
		listFiles[1] = listFiles[1].substring(1, listFiles[1].length() - 1);
		
		for (int i = 1; i < listFiles.length; i++)
		{
			Copy cp = new Copy();
			cp.setFile(new File(listFiles[i]));
			cp.destFile = new File("\\\\10.247.8.8\\wanduxStorage\\" + this.addrMac + listFiles[i]);
			
			System.out.println("* listFiles[i]" + listFiles[i]);
			System.out.println("* \\\\10.247.8.8\\wanduxStorage\\" + this.addrMac + listFiles[i]);
			
//			System.out.println("cmd /c \"xcopy /Y /E " + listFiles[1] + " " + "\\\\10.247.8.8\\wanduxStorage\\dup" + listFiles[1] + "\"");
//			try {
//					Process ls_proc = Runtime.getRuntime().exec("cmd /c \"xcopy /Y /E " + listFiles[1] + " " + "\\\\10.247.8.8\\wanduxStorage\\dup" + listFiles[1] + "\"");
//			} catch (IOException e1) {
//				System.err.println(e1);
//				System.exit(1);
//			}
		}
	}

}
