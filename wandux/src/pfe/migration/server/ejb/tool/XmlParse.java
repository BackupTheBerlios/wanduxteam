/*
 * Created on 3 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.server.ejb.tool;

/**
 * @author CornFlaks
 * 
 * TODO some manipulator functions
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.Filter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import pfe.migration.client.network.ComputerInformation;

public class XmlParse {

	public static Element racine;

	public static Document document;

	public XmlParse(ComputerInformation ci)
	{
		try {
			lire("c:\\wandux\\adll\\mandrake_10_1_FR.xml");
		
		} catch (Exception e) {
			System.out.println("fichier introuvable !!");
			return ;
		}

		//		afficheALL("netboot","address");
		//		afficheFiltre("port","21","port");

		//		supprElement("netboot","template");

		//		supprElement_racine("netboot");
				
			//	addElement("netboot","local","text");

		//		addElement("netboot","local");

		//		addElement_racine("local");

		//		addElement_racine("local", "text");

		//		changElement("netboot", "port", "local", "text");

		//		changElement("netboot", "port", "local");

		//		changElement_racine("port", "local", "text");

		//		changElement_racine("port", "local");
		
		//		supprAttribute("author","template");

		//		supprAttribute_racine("author");

		//		addAttribute("netboot","local","text");	

		//		addAttribute("netboot","local");

		//		addAttribute_racine("local");

		//		addAttribute_racine("local", "text");

		//		changAttribute("template", "author", "author", "cornflaks");

		//		changAttribute("template", "author", "local");

		//		changAttribute_racine("author", "local", "text");
		//		changAttribute_racine("author", "local");
		 // affiche(); // pour jboss
		 enregistre("c:\\wandux\\output\\test_changed.xml");
		  
		 // TODO enregistrer dans une base de donne lors d une mgrigration difere 
	}

	
	
	// Screen Output tools

	// Post XML files
	public static void affiche() {
		XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		try {
			//System.out.println(Format.getPrettyFormat());
			sortie.output(document, System.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Post XML files from Childs in Parent
	public static void afficheALL(String children, String parent) {
		List list = racine.getChildren(parent);

		Iterator i = list.iterator();
		while (i.hasNext()) {
			Element courant = (Element) i.next();
			System.out.println(courant.getChild(children).getText());
		}
	}

	// Post XML files from Childs in Root
	public static void afficheALL(String children) {
		List list = racine.getChildren();

		Iterator i = list.iterator();
		while (i.hasNext()) {
			Element courant = (Element) i.next();
			System.out.println(courant.getChild(children).getText());
		}
	}

	// Post Filter output with rules
	public static void 
Filtre(final String fparent,
			final String fparentvalue, String AttrChoice) {

		Filter filtre = new Filter() {

			public boolean matches(Object ob) {

				if (!(ob instanceof Element)) {
					return false;
				}

				Element element = (Element) ob;

				int verifIP = 0;

				if (element.getChild(fparent).getTextTrim()
						.equals(fparentvalue)) {
					verifIP = 1;
				}

				if (verifIP == 1) {
					return true;
				}
				return false;
			}
		};

		List resultat = racine.getContent(filtre);

		Iterator i = resultat.iterator();
		while (i.hasNext()) {
			Element courant = (Element) i.next();
			System.out.println(courant.getChildText(AttrChoice));
		}
	}

	// Elements Tools

	// Elements Suppression
	// TODO delete parent mark out with child and root mark out
	
	// Delete Elements from Parent mark out
	public static void supprElement(String element, String parent) {
		List list = racine.getChildren(parent);
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();

			if (courant.getChild(element) != null) {
				courant.removeChild(element);
			}
		}
	}

	// Delete Elements from Root
	public static void supprElement_racine(String element) {
		
		List list = racine.getChildren();
		Iterator i = list.iterator();
		
		while (i.hasNext()) {
			Element courant = (Element) i.next();

			if (courant.getChild(element) != null) {
				List l = courant.getChildren();
				Iterator j = l.iterator();
				while (j.hasNext()) {
					Element child = (Element) j.next();
					child.removeChild(child.getName());
				}
				courant.removeChild(element);
			}
		}
	}

	// Add Elements

	// Add Element from parent mark out with text
	public static void addElement(String parent, String addvalue, String text) {

		Element courant = racine.getChild(parent);
		Element other = new Element(addvalue);
		other.setText(text);
		courant.addContent(other);
	}

	// Add Element from parent mark out without text
	public static void addElement(String parent, String addvalue) {

		Element courant = racine.getChild(parent);
		Element other = new Element(addvalue);
		courant.addContent(other);
	}

	// Add Element from root with text
	public static void addElement_racine(String addvalue, String text) {

		Element other = new Element(addvalue);
		other.setText(text);
		racine.addContent(other);
	}

	// Add Element from root without text
	public static void addElement_racine(String addvalue) {

		Element other = new Element(addvalue);
		racine.addContent(other);
	}

	// Elements Change

	// Change Element from parent mark out with text
	public static void changElement(String parent, String element,
			String changevalue, String text) {
		List list = racine.getChildren(parent);
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();

			if (courant.getChild(element) != null) {
				courant.removeChild(element);
				Element other = new Element(changevalue);
				other.setText(text);
				courant.addContent(other);
			}
		}
	}

	// Change Element from parent mark out without text
	public static void changElement(String parent, String element,
			String changevalue) {
		List list = racine.getChildren(parent);
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();

			if (courant.getChild(element) != null) {
				courant.removeChild(element);
				Element other = new Element(changevalue);
				courant.addContent(other);
			}
		}
	}

	// Change Element from root with text
	public static void changElement_racine(String element, String changevalue,
			String text) {
		List list = racine.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();

			if (courant.getChild(element) != null) {
				courant.removeChild(element);
				Element other = new Element(changevalue);
				other.setText(text);
				courant.addContent(other);
			}
		}
	}

	// Change Element from root without text
	public static void changElement_racine(String element, String changevalue) {
		List list = racine.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();

			if (courant.getChild(element) != null) {
				courant.removeChild(element);
				Element other = new Element(changevalue);
				courant.addContent(other);
			}
		}
	}

	// Attributes Tools
	// TODO Debug Progress

	// Attributes Suppression

	// Delete Attributes from Parent mark out
	public static void supprAttribute(String attribute, String parent) {
		Element courant = racine.getChild(parent);
		List list = courant.getAttributes();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Attribute attr = (Attribute) i.next();

			if (courant.getAttribute(attribute) == attr) {
				courant.removeAttribute(attribute);
			}
		}
	}

	// Delete Attributes from Root
	public static void supprAttribute_racine(String attribute) {
		List list = racine.getAttributes();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Attribute attr = (Attribute) i.next();

			if (racine.getAttribute(attribute) == attr) {
				racine.removeAttribute(attribute);
			}
		}
	}

	// Add Attributes

	// Add Attributes from parent mark out with text
	public static void addAttribute(String parent, String attribute, String value) {

		Element courant = racine.getChild(parent);
		Attribute other = new Attribute(attribute, value);
		courant.setAttribute(other);
	}

	// Add Attributes from parent mark out without text
	public static void addAttribute(String parent, String attribute) {

		Element courant = racine.getChild(parent);
		Attribute other = new Attribute(attribute, "");
		courant.setAttribute(other);
	}

	// Add Attributes from root with text
	public static void addAttribute_racine(String attribute, String value) {

		Attribute other = new Attribute(attribute, value);
		racine.setAttribute(other);
	}

	// Add Attributes from root without text
	public static void addAttribute_racine(String attribute) {

		Attribute other = new Attribute(attribute, "");
		racine.setAttribute(other);
	}

	// Attributes Change

	// Change Attributes from parent mark out with text
	public static void changAttribute(String parent, String element,
			String attribute, String value) {
		List list = racine.getChildren(parent);
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();

			if (courant.getAttribute(element) != null) {
				courant.removeAttribute(element);
				Attribute other = new Attribute(attribute, value);
				courant.setAttribute(other);
			}
		}
	}

	// Change Element from parent mark out without text
	public static void changAttribute(String parent, String element,
			String attribute) {
		List list = racine.getChildren(parent);
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();

			if (courant.getAttribute(element) != null) {
				courant.removeAttribute(element);
				Attribute other = new Attribute(attribute, "");
				courant.setAttribute(other);
			}
		}
	}

	// Change Element from root with text
	public static void changAttribute_racine(String element, String attribute,
			String value) {
		List list = racine.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();

			if (courant.getAttribute(element) != null) {
				courant.removeAttribute(element);
				Attribute other = new Attribute(attribute, value);
				courant.setAttribute(other);
			}
		}
	}

	// Change Element from root without text
	public static void changAttribute_racine(String element, String attribute) {
		List list = racine.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();

			if (courant.getAttribute(element) != null) {
				courant.removeAttribute(element);
				Attribute other = new Attribute(attribute, "");
				courant.setAttribute(other);
			}
		}
	}
	
	// Text Tools
	// TODO when Attributes manipulators will be finished
	
	
	// Files Tools
	public static void lire(String fichier) throws Exception {
		SAXBuilder sxb = new SAXBuilder();
		document = sxb.build(new File(fichier));
		racine = document.getRootElement();
	}

	public static void enregistre(String fichier) {
		try 
		{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(fichier));
		} catch (java.io.IOException e) {
		}
	}

	// Parsing


}
