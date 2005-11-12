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

	public FsXml(String name, BufferedWriter out) {
		super(name);
		_out = out;
	}

	public void printDir(int depth) throws IOException {
		for (int i = 0; i < depth; i++)
			_out.write("  ");
		_out.write("<item text=\"" + getName() + "\" id=\"" + getPath() + "\\"
				+ "\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\">\n");
	}

	public void printFile(int depth) throws IOException {
		for (int i = 0; i < depth; i++)
			_out.write("  ");
		_out.write("<item text=\"" + getName() + "\" id=\"" + getPath()
				+ "\" im0=\"leaf.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\" />\n");
	}

	public void listAll() throws IOException {
		listAll(1);
	}

	private void listAll(int depth) throws IOException {
		if (isDirectory()) {
			printDir(depth);
			String[] entries;
			if ((entries = list()) != null) {
				for (int i = 0; i < entries.length; i++) {
					FsXml child = new FsXml(getPath() + separatorChar
							+ entries[i], _out);
					child.listAll(depth + 1);
				}
				for (int i = 0; i < depth; i++)
					_out.write("  ");
				_out.write("</item>\n");
			}
		} else
			printFile(depth);
	}
}

