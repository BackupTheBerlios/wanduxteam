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

		// PARTITIONS

		ps.section_change_select(XmlAdllParse.racine,
				"Creation des partitions", "Primary Master", "no",
				"Peripherique d'installation");

		ps.section_change_select(XmlAdllParse.racine,
				"Creation des partitions", "Secondary Master", "yes",
				"Peripherique d'installation");

		ps.option_change_select(XmlAdllParse.racine, "", "yes",
				"Taille en pourcentage de la partion");

		// CONFIG RESEAU

		ps.section_change_select(XmlAdllParse.racine,
				"Configuration du reseau", "Statique", "no",
				"Type de configuration reseau");

		ps.section_change_select(XmlAdllParse.racine,
				"Configuration du reseau", "Dhcp", "yes",
				"Type de configuration reseau");

		// CONFIG PARAM RESEAU

		/*
		 * TODO Rajouter dans la BDD getHostName et setHostName
		 * pst.option_change_select_txt(XmlAdllParse.racine, "", "yes",
		 * ci.udata[0].getHostName(), "Nom de la machine");
		 */

		/*
		 * TODO Rajouter dans la BDD getDomainName et setDomainName
		 * pst.option_change_select_txt(XmlAdllParse.racine, "", "yes",
		 * ci.udata[0].getDomainName(), "Domaine de la machine");
		 */

		// AUTOLOGIN
		pst.option_change_select_txt(XmlAdllParse.racine, "", "yes",
				ci.udata[0].getUserLogin(), "Login automatique au demarrage");

		// PROXY HTTP

		pst.option_change_select_txt(XmlAdllParse.racine, "", "yes",
				ci.udata[0].getUserProxyServ(), "Paramètrage du proxy http");

		// // PROXY FTP

		pst.option_change_select_txt(XmlAdllParse.racine, "", "yes",
				ci.udata[0].getUserProxyServ(), "Paramètrage du proxy ftp");

		// // TYPE DE CLAVIER

		ps.option_change_select(XmlAdllParse.racine, "French", "no",
				"Type de clavier");

		ps.option_change_select(XmlAdllParse.racine, "French", "yes",
				"Type de clavier");

		// // CHOIX DU FUSEAUX

		ps.option_change_select(XmlAdllParse.racine, "Europe/Paris", "no",
				"Choix du fuseau horaire");

		ps.option_change_select(XmlAdllParse.racine, "Europe/Paris", "yes",
				"Choix du fuseau horaire");

		// SUPER UTILISATEUR

		/*
		 * pst .option_change_select_txt(XmlAdllParse.racine, "", "yes", "root@" +
		 * ci.udata[0].getDomainName() + ".org", "Adresse E-Mail de
		 * l'utilisateur a prevenir en cas d'alerte de securite");
		 */

		ps.option_change_select(XmlAdllParse.racine, "", "yes",
				"Identifiant du super utilisateur");

		ps.option_change_select(XmlAdllParse.racine, "", "yes",
				"Identifiant du groupe du super utilisateur l'utilisateur");

		ps.option_change_select(XmlAdllParse.racine, "", "yes",
				"Nom du super utilisateur");

		ps.option_change_select(XmlAdllParse.racine, "", "yes",
				"Mot de passe du super utilisateur");

		ps.option_change_select(XmlAdllParse.racine, "", "yes",
				"Shell du super utilisateur");

		ps.option_change_select(XmlAdllParse.racine, "", "yes",
				"Repertoire du super utilisateur");

		ps.option_change_select(XmlAdllParse.racine, "", "yes",
				"Icone du super utilisateur");

		// UTILISATEUR

		// pu.ChangeUser(ci);
		// pu.AddUser(XmlAdllParse.racine, ci.udata);

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
