/*
 * Created on 29 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.tools;

import java.io.File;

import pfe.migration.client.pre.app.apparence.steps.ProgressBarStep;



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
	private ProgressBarStep pbs = null;
	
	public CopyDataOnFileServer(String [] files, String addrMac, ProgressBarStep pbs)
	{
		this.pbs = pbs;
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
		
		for (int i = 1; i < listFiles.length; i++)
		{
			Copy cp = new Copy();
			cp.setFile(new File(listFiles[i]));
			cp.destFile = new File("\\\\10.247.0.248\\wanduxStorage\\" + this.addrMac + listFiles[i]);
			
			this.pbs.refreshBar(i , listFiles[i]);
//			System.out.println("cmd /c \"xcopy /Y /E " + listFiles[1] + " " + "\\\\10.247.8.8\\wanduxStorage\\dup" + listFiles[1] + "\"");
//			try {
//					Process ls_proc = Runtime.getRuntime().exec("cmd /c \"xcopy /Y /E " + listFiles[1] + " " + "\\\\10.247.8.8\\wanduxStorage\\dup" + listFiles[1] + "\"");
//			} catch (IOException e1) {
//				System.err.println(e1);
//				System.exit(1);
//			}
		}
		this.pbs.refreshBar(listFiles.length , "");
	}

}
