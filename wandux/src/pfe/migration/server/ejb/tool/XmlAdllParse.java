/*
 * Created on 11 juin 2005
 *
 * Proprièté privé @CornFlaks
 * Pour plus d'info galiss_c@epitech.net
 */
package pfe.migration.server.ejb.tool;

import java.io.File;
import java.io.FileOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import pfe.migration.client.network.ComputerInformation;

/**
 * @author Corn
 * 
 * Classe XmlAdllParse qui contient les outils input output
 */

public class XmlAdllParse extends XmlAdllParseTool {

	public static Element racine;

	public static Document document;
	
	public XmlAdllParse(String path, String hostname, ComputerInformation ci) {

		String FilePath = path;

		String OriFilePath = FilePath.toString()
				+ "mandrake_10_1_FR.xml";

		String ChanFilePath = FilePath + hostname + ".xml";

		try {
			lire(OriFilePath.toString());
		} catch (Exception e) {
			System.out.println("Can't read Xml File");
		}
		
		try {
			parse(ci);
		} catch (Exception e) {
			System.out.println("Parse Error");
		}

		/*
		 * try { affiche(); } catch (Exception e) { System.out.println("OutPut
		 * Error"); }
		 */

		try {
			enregistre(ChanFilePath.toString());
		} catch (Exception e) {
			System.out.println("Can't save File");
		}

	}

	public static void affiche() {
		try {
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, System.out);
		} catch (java.io.IOException e) {
		}
	}

	public static void lire(String fichier) throws Exception {
		SAXBuilder sxb = new SAXBuilder();
		document = sxb.build(new File(fichier));
		racine = document.getRootElement();
	}

	public static void enregistre(String fichier) {
		try {
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(fichier));
		} catch (java.io.IOException e) {
		}
	}
}
