package pfe.migration.client.pre.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

public class FSNodeCopy {
	public void CopyNode(String src, String dst, boolean recurse)
	{
	}

	private void BuildFileTree(String tree, boolean isfile)
	{
		String TmpDir = "";
		File newPath = new File("");
		int until;

		StringTokenizer st = new StringTokenizer(tree, "\\", true);		
		if (isfile == true)
			until = 1;
		else
			until = 0;
		for (int i = st.countTokens(); i > until; i--)
		{
			TmpDir = TmpDir + st.nextToken();
			if (i % 2 != 0)
			{
				newPath = new File(TmpDir);
				newPath.mkdir();
			}
		}
	}

	/**
	 * Generic function to copy a filesytem node to a specific path,
	 * building the initial tree structure of the src path inside dst
	 * 
	 * @param src wether a file or a directory
	 * @param dst which is the destination directory where the src
	 * filesystem model will be rebuild
	 */
	public void GenericCopyNode(String src, String dst)
	{
		File srcpath = new File(src);
		
		if (srcpath.isDirectory() == true)
		{
			BuildFileTree(dst + src.substring(2), false);
		}
		else
		{
			BuildFileTree(dst + src.substring(2), true);
			File dstfile = new File(dst + src.substring(2));

			try {
				FileInputStream in = new FileInputStream(srcpath);
				FileOutputStream out = new FileOutputStream(dstfile);
				byte[] b = new byte[1024];
				int readBytes;

				while ((readBytes = in.read(b)) != -1) {
					out.write(b, 0, readBytes);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[])
	{
		FSNodeCopy fsnc = new FSNodeCopy();

		// Sample code
		fsnc.GenericCopyNode("C:\\filecopy\\test\\adll\\mandrake_10_1_FR.xml", "\\\\127.0.0.1\\share");
	}
}
