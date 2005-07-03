/*
 * Created on 30 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * @author cb6
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FileCopy {
	
	public static String replace(String orig, String from, String to)
	{
		int start = orig.indexOf(from);
		if (start == -1)
			return orig;
		int lf = from.length();
		char [] origChars = orig.toCharArray();
		StringBuffer buffer = new StringBuffer();
		int copyFrom = 0;

		while (start != -1)
		{
			buffer.append(origChars, copyFrom, start - copyFrom);
			buffer.append(to);
			copyFrom = start + lf;
			start = orig.indexOf(from, copyFrom);
		}
		buffer.append(origChars, copyFrom, origChars.length - copyFrom);
		return buffer.toString();
	}
	
	public static String getTmpDirName(String file)
	{
		Hashtable ht = new Hashtable();
		Boolean single = new Boolean(true);
		StringTokenizer st = new StringTokenizer(file, "\\", true);
		String TmpDir = "";

		for (int i = st.countTokens(); i > 1; i--)
		{
			TmpDir = TmpDir + st.nextToken();
			//System.out.println(st.nextToken());
		}
		//TmpDir = replace(TmpDir, "///", "/");
		TmpDir = TmpDir.substring(0, TmpDir.length() - 1);
		return TmpDir;
	}
	
	public static void BuildFileTree(String tree)
	{
		String TmpDir = "";
		File newPath = new File("");
		//System.out.println(tree);
		StringTokenizer st = new StringTokenizer(tree, "\\", true);
		for (int i = st.countTokens(); i > 0; i--)
		{
			TmpDir = TmpDir + st.nextToken();
			if (i % 2 != 0)
			{
				System.out.println(TmpDir);
				newPath = new File(TmpDir);
				newPath.mkdir();
			}
		}
	}
	
	public static void CopyRec(String old_path, String new_path) {
		//create file objects from method parameters
		File oldPath = new File(old_path);
		File newPath = new File(new_path + File.separator);

		if (oldPath.exists()) {
			if (!newPath.exists()) {
				newPath.mkdir();
			}
			File inputFile = oldPath;
			String dir = getTmpDirName(newPath + old_path.substring(2));
			BuildFileTree(dir);
			File mkpath = new File(dir);
			mkpath.mkdir();
			
			//System.out.println(newPath + old_path.substring(2));
				try {
					if (inputFile.isFile()) {
						File outputFile = new File(newPath + old_path.substring(2));
						//create streams for in and out files
						FileInputStream in = new FileInputStream(inputFile);
						FileOutputStream out = new FileOutputStream(outputFile);

						//read and write bytes from old file to new file
						byte[] b = new byte[1024];
						int readBytes;

						//while return code of read() isn't end of file (ie. -1)
						while ((readBytes = in.read(b)) != -1) {
							out.write(b, 0, readBytes);
						}
					}
				} catch (Exception e) { }
			}
	}
	
	public static void main(String args[])
	{
		CopyRec("C:\\wandux\\hehe.txt","C:\\test2");
	}
}
