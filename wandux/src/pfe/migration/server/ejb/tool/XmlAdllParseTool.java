/*
 * Created on 11 juin 2005
 * 
 * Proprièté privé @CornFlaks
 * Pour plus d'info galiss_c@epitech.net
 */
package pfe.migration.server.ejb.tool;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

/**
 * @author Corn
 * 
 *	Classe XmlAdllParseTool qui contient les outils de parsing
 */
public class XmlAdllParseTool {

	public void parse() {

		// TODO penser à switcher le "yes" en "no" et inversement 
		// de même le text "1" en "0" et inversement pour les choix 
		// simple de type "yes" ou "no"

		//change_select_txt(XmlAdllParse.racine, "Utiliser l'encryption md5",
		//		"Oui", "yes", "1");

		//change_select(XmlAdllParse.racine, "Utiliser l'encryption md5", 
		//		"Oui", "yes");

		// TODO Bug change tous les text des child Ip du serveur 
		// dans "Utiliser un serveur NIS pour l'authentification"
		// et dans "Utiliser un serveur LDAP pour l'authentification"

		//change_text(XmlAdllParse.racine, "Ip du serveur", "", "10.0.0.1");

		//change_section(XmlAdllParse.racine, "netboot", "port", "23");
	}

	// TODO pensez à rajouter les try catch ou condition NullPointer

	public void change_select_txt(Element element, String optionlbl,
			String choicelbl, String selected, String text) {

		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "option"
					&& optionlbl.equals(courant.getAttributeValue("label")))
				choice_select_text(courant, choicelbl, selected, text);
			else
				change_select_txt(courant, optionlbl, choicelbl, selected, text);
		}
	}

	private void choice_select_text(Element element, String choicelbl,
			String selected, String text) {
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "choice"
					&& choicelbl.equals(courant.getAttributeValue("label"))) {
				courant.setAttribute("selected", selected);
				courant.setText(text);
			}
		}
	}

	public void change_select(Element element, String optionlbl,
			String choicelbl, String selected) {
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "option"
					&& optionlbl.equals(courant.getAttributeValue("label")))
				choice_select(courant, choicelbl, selected);
			else
				change_select(courant, optionlbl, choicelbl, selected);
		}
	}

	private void choice_select(Element element, String choicelbl,
			String selected) {
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "choice"
					&& choicelbl.equals(courant.getAttributeValue("label")))
				courant.setAttribute("selected", selected);
		}
	}

	public void change_text(Element element, String optionlbl,
			String choicelbl, String text) {
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "option"
					&& optionlbl.equals(courant.getAttributeValue("label")))
				choice_text(courant, choicelbl, text);
			else
				change_text(courant, optionlbl, choicelbl, text);
		}
	}

	private void choice_text(Element element, String choicelbl, String text) {
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "choice"
					&& choicelbl.equals(courant.getAttributeValue("label")))
				courant.setText(text);
		}
	}

	public void change_section(Element element, String sectionlbl,
			String childlbl, String text) {

		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (sectionlbl.equals(courant.getName()))
				choice_section(courant, childlbl, text);
			else
				change_section(courant, sectionlbl, childlbl, text);
		}
	}

	private void choice_section(Element element, String childlbl, String text) {
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (childlbl.equals(courant.getName()))
				courant.setText(text);
		}
	}
}
