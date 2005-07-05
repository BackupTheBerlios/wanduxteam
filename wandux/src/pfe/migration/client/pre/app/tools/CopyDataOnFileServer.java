/*
 * Created on 29 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.tools;

import java.io.File;

import pfe.migration.client.network.ComputerInformation;
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
	//private ComputerInformation ci = null;
	
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

//	public CopyDataOnFileServer(ComputerInformation ci , String [] files, String addrMac, ProgressBarStep pbs)
//	{
//		//this.ci = ci;
//		this.pbs = pbs;
//		this.addrMac = addrMac;
//		if (files.length > 1)
//		{
//			listFiles = new String [files.length];
//			listFiles = files;
//		}
//	}

	public void run()
	{
		if (listFiles == null)
			return;

		this.pbs.refreshBar(0 , "Transfert : Favoris et Carnet d'adresse");
		
		for (int i = 1; i < listFiles.length; i++)
		{
			File f = new File(listFiles[i]);
			
			String disk = "disk" + listFiles[i].substring(0,1);
			String path = listFiles[i].substring(3,listFiles[i].length());
			
			if (f.isDirectory() == true)
			{
				DirCopy.CopyRec(listFiles[i] , "\\\\10.247.0.248\\wanduxStorage\\" + this.addrMac + "\\"+ disk + "\\" + path);
			}
			else
			{
				FileCopy.CopyRec(listFiles[i] , "\\\\10.247.0.248\\wanduxStorage\\" + this.addrMac + "\\"+ disk + "\\" + path);
			}
			this.pbs.refreshBar(i , listFiles[i]);
		}
		this.pbs.refreshBar(listFiles.length , "");
	}

}
