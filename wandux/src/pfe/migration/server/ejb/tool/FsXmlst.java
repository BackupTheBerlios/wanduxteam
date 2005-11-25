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
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FsXmlst {

	public FsXmlst() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					"utils/FileSystem/filesystem.xml"));
			out
					.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>"
							+ "\n<tree id=\"0\">\n");

			File[] roots = File.listRoots();
			for (int i = 1; i < roots.length; i++) {
				out
						.write("  "
								+ "<item text=\""
								+ roots[i].toString().replaceAll("&", "&amp;")
								+ "\" id=\""
								+ roots[i].toString().replaceAll("&", "&amp;")
								+ "\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\">\n");
				FsXml child = new FsXml(roots[i].toString(), out);
				child.listAll();
				out.write("  </item>\n");
			}
			out.write("</tree>");
			out.close();
			System.out.println("filesystem to xml terminer");
		} catch (IOException e) {
		}
	}
}