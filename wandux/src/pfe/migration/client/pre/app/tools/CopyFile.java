package pfe.migration.client.pre.app.tools;

/*
 * Created on 5 juil. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author Corn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CopyFile {

	public static void copyFile(String src, String dest) throws Exception {

		String[] dir;
		dir = dest.split("\\\\");
		
		String path = "";
		
		for(int i = 0; i < dir.length - 1; i++)
		{
			path += dir[i] + "\\";
			File fi = new File(path);
			fi.mkdir();
		}


		File in = new File(src);
		File out = new File(dest);
		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);
		
		byte[] buf = new byte[1024];
		int i = 0;
		while ((i = fis.read(buf)) != -1) {
			fos.write(buf, 0, i);
		}
		
		fis.close();
		fos.close();
	}
	
	public static void copyArbo(String src, String dest)
	{
		int i;
		
		File source = new File(src);
		if (source.exists())
		{
			String tab[] = source.list();
			for (i = 0; i < tab.length; i++)
			{
				File tmp = new File(src + "\\" + tab[i]);
				if (tmp.isFile())
				{
					try {
						copyFile(src + "\\" + tab[i], dest + "\\" + tab[i]);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					copyArbo(src + "\\" + tab[i], dest + "\\" + tab[i]);
				}
			}	
		}
	}
}
