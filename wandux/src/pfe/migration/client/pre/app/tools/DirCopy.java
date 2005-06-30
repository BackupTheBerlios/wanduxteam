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
/**
 * @author cb6
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DirCopy
{
	public static void CopyRec(String old_path, String new_path) {
		//create file objects from method parameters
		File oldPath = new File(old_path);
		File newPath = new File(new_path);

		if (oldPath.exists()) {
			//System.out.println("old path found");
			//new folder doesnt have to exist...
			if (!newPath.exists()) {
				newPath.mkdir();
			}
			//list files in old directory in array
			
			String[] oldFiles = oldPath.list();
			//get length of file array
			int numOldFiles = oldFiles.length;
			//for each file in the old directory
			for (int i = 0; i < numOldFiles; i++) {
				try {
					//create old and new files
					File inputFile = new File(oldPath + File.separator
							+ oldFiles[i]);
					File outputFile = new File(newPath + File.separator
							+ oldFiles[i]);

					if (inputFile.isFile()) {
//						System.out.println("Copying file: " + oldFiles[i]
//								+ " to: " + newPath + File.separator);

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
					//RECURSE through other directories
					else if (inputFile.isDirectory()) {
						outputFile.mkdir();
						String newFileLocation = outputFile.toString();
						CopyRec(oldPath + File.separator + oldFiles[i],
								newFileLocation);
					}
				} catch (Exception e) {
					//System.out.println("TODO");
					//do whatever here
				}
			}
		} else {
			//old path doesnt exist
			System.out.println("you gave an incorrect backup path");
		}
	}
//	public static void main(String args[])
//	{
//		CopyRec("C:\\test\\toto\\titi.txt","C:\\test\\tutu");
//	}
}