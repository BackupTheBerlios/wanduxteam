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

import pfe.migration.client.network.ComputerInformation;

/**
 * @author Corn
 * 
 * Classe XmlAdllParseTool qui contient les outils de parsing
 */
public class XmlAdllParseTool {

	public void parse(ComputerInformation ci) { // Object[] info

		/*
		 * section_change_txt(XmlAdllParse.racine, "Méthode d'authentification",
		 * "", "10.0.0.1", "Utiliser un serveur NIS pour l'authentification",
		 * "Ip du serveur");
		 * 
		 * section_change_txt(XmlAdllParse.racine, "Méthode d'authentification",
		 * "", "10.0.0.2", "Utiliser un serveur LDAP pour l'authentification",
		 * "Ip du serveur");
		 */

		XmlAdllParseUser pu = new XmlAdllParseUser();
		XmlAdllParseSelTxt pst = new XmlAdllParseSelTxt();
		XmlAdllParseSel ps = new XmlAdllParseSel();
		XmlAdllParseTxt pt = new XmlAdllParseTxt();
		
		ps.section_change_select(XmlAdllParse.racine, "Creation des partitions",
				"Primary Master", "no", "Peripherique d'installation");

		ps.section_change_select(XmlAdllParse.racine, "Creation des partitions",
				"Secondary Master", "yes", "Peripherique d'installation");
		
		pt.option_change_txt(XmlAdllParse.racine, "",
				ci.gconf.getGlobalHostname(), "Nom de la machine");
		
		ps.option_change_select(XmlAdllParse.racine, "French",
				"no", "Type de clavier");
		
		ps.option_change_select(XmlAdllParse.racine, ci.udata.getUserKbLayout(),
				"yes", "Type de clavier");
		
		ps.option_change_select(XmlAdllParse.racine, "Europe/Paris",
				"no", "Choix du fuseau horaire");
		
		ps.option_change_select(XmlAdllParse.racine, ci.udata.getUserTimezone(),
				"yes", "Choix du fuseau horaire");
		
		pu.ChangeUser(ci);
		
		//pu.AddUser(XmlAdllParse.racine, ci.udata);
		
		/*
		 * section_change_select_txt(XmlAdllParse.racine, "Méthode
		 * d'authentification", "Oui", "no", "0", "Utiliser les shadow
		 * password");
		 */

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
