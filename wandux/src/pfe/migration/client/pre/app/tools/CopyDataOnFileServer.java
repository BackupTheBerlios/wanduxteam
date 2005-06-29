/*
 * Created on 29 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.tools;

import java.io.IOException;



/**
 * @author dupadmin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CopyDataOnFileServer implements Runnable
{
	String [] listFiles = null;
	
	public CopyDataOnFileServer(String [] files)
	{
		System.out.println("CopyDataOnFileServer");
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
			System.out.println("cmd /c \"xcopy /Y /E " + listFiles[1] + " " + "\\\\10.247.8.8\\wanduxStorage\\dup" + listFiles[1] + "\"");
			try {
					Process ls_proc = Runtime.getRuntime().exec("cmd /c \"xcopy /Y /E " + listFiles[1] + " " + "\\\\10.247.8.8\\wanduxStorage\\dup" + listFiles[1] + "\"");
			} catch (IOException e1) {
				System.err.println(e1);
				System.exit(1);
			}
		}
	}

}
