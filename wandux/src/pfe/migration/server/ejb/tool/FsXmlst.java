/*
 * Created on 5 nov. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.server.ejb.tool;

import java.io.*;

/**
 * @author CornFlaks
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FsXmlst {

	public FsXmlst(){
	
	try {
		BufferedWriter out = new BufferedWriter(new FileWriter(
				"utils/FileSystemXml/newfilesystem.xml"));
		out
				.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<tree id=\"0\">\n");
		File[] roots = File.listRoots();
		for (int i = 1; i < roots.length; i++) {

			out
					.write("  "
							+ "<item text=\""
							+ roots[i]
							+ "\" id=\""
							+ roots[i]
							+ "\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\">\n");
			FsXml child = new FsXml(roots[i].toString(), out);
			child.listAll();
			out.write("  </item>\n");
		}
		out.write("</tree>");
		out.close();
		System.out.println("terminer");
	} catch (IOException e) {}
	}
}
