/*
 * Created on 4 nov. 2005
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
public class FsXml extends File {

	private static final long serialVersionUID = 1L;

	private BufferedWriter _out;

	public boolean rootflg = true;

	public FsXml(String name, BufferedWriter out) {
		super(name);
		_out = out;
	}

	public void printDir(int depth) throws IOException {
		for (int i = 0; i < depth; i++)
			_out.write("  ");
		_out
				.write("<item text=\""
						+ getName()
						+ "\" id=\""
						+ getPath().replaceAll("&", "&amp;")
						+ separatorChar
						+ "\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\">\n");
	}

	public void printFile(int depth) throws IOException {
		for (int i = 0; i < depth; i++)
			_out.write("  ");
		_out
				.write("<item text=\""
						+ getName()
						+ "\" id=\""
						+ getPath().replaceAll("&", "&amp;")
						+ "\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" />\n");
	}

	public void listAll() throws IOException {
		listAll(1);
	}

	private void listAll(int depth) throws IOException {
		if (isDirectory()) {
			if (!this.rootflg)
				printDir(depth);
			String[] entries;
			if ((entries = list()) != null) {
				for (int i = 0; i < entries.length; i++) {
					FsXml child = new FsXml(getPath().replaceAll("&", "&amp;")
							+ separatorChar
							+ entries[i].replaceAll("&", "&amp;"), _out);
					child.rootflg = false;
					child.listAll(depth + 1);
				}
			}
			if (!this.rootflg) {
				for (int i = 0; i < depth; i++)
					_out.write("  ");
				_out.write("</item>\n");
			}
		} else {
			if (!rootflg)
				printFile(depth);
		}
	}
}