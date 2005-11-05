/*
 * Created on 19 sept. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.server.ejb.tool;

import java.io.File;
import java.io.FileOutputStream;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @author Corn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class FileSystemXml {

	private File[] _roots;

	static Element racine = new Element("tree");

	static org.jdom.Document document = new Document(racine);

	public FileSystemXml() {
		_roots = File.listRoots();

		for (int i = 1; i < _roots.length; i++) {
			Element item = new Element("item");
			racine.addContent(item);
			Attribute id0 = new Attribute("id", "0");
			racine.setAttribute(id0);
			Attribute txt = new Attribute("text", _roots[i].toString());
			item.setAttribute(txt);
			Attribute id = new Attribute("id", _roots[i].toString());
			item.setAttribute(id);
			Attribute im0 = new Attribute("im0", "leaf.gif");
			item.setAttribute(im0);
			Attribute im1 = new Attribute("im1", "folderOpen.gif");
			item.setAttribute(im1);
			Attribute im2 = new Attribute("im2", "folderClosed.gif");
			item.setAttribute(im2);
			getSubDirs(_roots[i], item);
		}
	}

	private void getSubDirs(File root, Element item) {

		File[] list = root.listFiles();
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				if (list[i].isDirectory()) {
					Element directory = new Element("item");
					item.addContent(directory);
					Attribute txt = new Attribute("text", list[i].getName());
					directory.setAttribute(txt);
					Attribute id = new Attribute("id", list[i].getPath() + "\\");
					directory.setAttribute(id);
					Attribute im0 = new Attribute("im0", "leaf.gif");
					directory.setAttribute(im0);
					Attribute im1 = new Attribute("im1", "folderOpen.gif");
					directory.setAttribute(im1);
					Attribute im2 = new Attribute("im2", "folderClosed.gif");
					directory.setAttribute(im2);
					getSubDirs(list[i], directory);
				}
				if (list[i].isFile()) {
					Element file = new Element("item");
					item.addContent(file);
					Attribute txt = new Attribute("text", list[i].getName());
					file.setAttribute(txt);
					Attribute id = new Attribute("id", list[i].getPath());
					file.setAttribute(id);
					Attribute im0 = new Attribute("im0", "leaf.gif");
					file.setAttribute(im0);
					Attribute im1 = new Attribute("im1", "folderOpen.gif");
					file.setAttribute(im1);
					Attribute im2 = new Attribute("im2", "folderClosed.gif");
					file.setAttribute(im2);
				}
			}
		}
		writeXMLfile("FileSystemXml/FileSystem.xml");
	}

	static void writeXMLfile(String fichier) {
		try {
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(fichier));
		} catch (java.io.IOException e) {
		}
	}
}
