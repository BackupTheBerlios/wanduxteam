/*
 * Created on 1 juil. 2003
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.server.ejb.tool;

//import java.util.Iterator;
//import java.util.List;

//import org.jdom.Attribute;
//import org.jdom.Element;

import pfe.migration.client.network.ComputerInformation;
//import pfe.migration.server.ejb.bdd.UsersData;

/**
 * @author Corn
 * 
 */

public class XmlAdllParseUser {

	// Instancier ChangeUser avant AddUser

//	public void ChangeUser(ComputerInformation ci) { //Object[] info
//
//		XmlAdllParseSelTxt pst = new XmlAdllParseSelTxt();
//		
//		pst.section_change_select_txt(XmlAdllParse.racine, "Ajout des utilisateurs",
//				"", "yes", ci.udata.getUserLogin(), "Login de l'utilisateur");
//
//		pst.section_change_select_txt(XmlAdllParse.racine, "Ajout des utilisateurs",
//				"", "yes", ci.udata.getUserLogin(), "Nom de l'utilisateur");
//
//		pst.section_change_select_txt(XmlAdllParse.racine, "Ajout des utilisateurs",
//				"", "yes", "wandux", "Mot de passe de l'utilisateur");
//
//		pst.section_change_select_txt(XmlAdllParse.racine, "Ajout des utilisateurs",
//				"", "yes", "/bin/bash", "Shell de l'utilisateur");
//
//		pst.section_change_select_txt(XmlAdllParse.racine, "Ajout des utilisateurs",
//				"", "yes", "/home/" + ci.udata.getUserLogin(), "Repertoire de l'utilisateur");
//
//		pst.section_change_select_txt(XmlAdllParse.racine, "Ajout des utilisateurs",
//				"", "yes", "", "Icone de l'utilisateur");
//
//	}

	/*public void AddUser(Element element, UsersData udata) {

		// Element Option

		List list = element.getChildren();
		Iterator i = list.iterator();

		String section = new String("section");
		String adduser = new String("Ajout des utilisateurs");

		String option = new String("option");
		String lblnull = new String("");

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (section.equals(courant.getName())
					&& adduser.equals(courant.getAttributeValue("label"))) {

				List list_2 = courant.getChildren();
				Iterator j = list_2.iterator();

				while (j.hasNext()) {
					Element cur = (Element) j.next();
					if (option.equals(cur.getName())
							&& lblnull.equals(cur.getAttributeValue("label"))) {

						Element optionfirst = new Element("option");
						cur.addContent(optionfirst);

						Attribute name = new Attribute("name", "");
						optionfirst.setAttribute(name);

						Attribute label = new Attribute("label", "");
						optionfirst.setAttribute(label);

						Attribute separator = new Attribute("separator", ", ");
						optionfirst.setAttribute(separator);

						Attribute terminator = new Attribute("terminator", "}");
						optionfirst.setAttribute(terminator);

						Attribute multi = new Attribute("multi", "yes");
						optionfirst.setAttribute(multi);

						// Element Option

						Element optionsecond = new Element("option");
						optionfirst.addContent(optionsecond);

						label = new Attribute("label", "Login de l'utilisateur");
						optionsecond.setAttribute(label);

						name = new Attribute("name", "{'name' =>'");
						optionsecond.setAttribute(name);

						terminator = new Attribute("terminator", "'");
						optionsecond.setAttribute(terminator);

						// Element Choice

						Element choicefirst = new Element("choice");
						optionsecond.addContent(choicefirst);

						label = new Attribute("label", "");
						choicefirst.setAttribute(label);
						choicefirst.setText(udata.getUserLogin());

						// Element Option

						Element optionthird = new Element("option");
						optionfirst.addContent(optionthird);

						label = new Attribute("label", "Nom de l'utilisateur");
						optionthird.setAttribute(label);

						name = new Attribute("name", "'realname' => '");
						optionthird.setAttribute(name);

						terminator = new Attribute("terminator", "'");
						optionthird.setAttribute(terminator);

						// Element Choice

						Element choicesecond = new Element("choice");
						optionthird.addContent(choicesecond);

						label = new Attribute("label", "");
						choicesecond.setAttribute(label);
						choicesecond.setText(udata.getUserLogin());

						// Element Option

						Element optionfourth = new Element("option");
						optionfirst.addContent(optionfourth);

						label = new Attribute("label",
								"Mot de passe de l'utilisateur");
						optionfourth.setAttribute(label);

						name = new Attribute("name", "'password' => '");
						optionfourth.setAttribute(name);

						terminator = new Attribute("terminator", "'");
						optionfourth.setAttribute(terminator);

						// Element Choice

						Element choicethird = new Element("choice");
						optionfourth.addContent(choicethird);

						label = new Attribute("label", "");
						choicethird.setAttribute(label);
						choicethird.setText("wandux");

						// Element Option

						Element optionfifth = new Element("option");
						optionfirst.addContent(optionfifth);

						label = new Attribute("label", "Shell de l'utilisateur");
						optionfifth.setAttribute(label);

						name = new Attribute("name", "'shell' => '");
						optionfifth.setAttribute(name);

						terminator = new Attribute("terminator", "'");
						optionfifth.setAttribute(terminator);

						// Element Choice

						Element choicefourth = new Element("choice");
						optionfifth.addContent(choicefourth);

						label = new Attribute("label", "");
						choicefourth.setAttribute(label);
						choicefourth.setText("/bin/bash");

						// Element Option

						Element optionsix = new Element("option");
						optionfirst.addContent(optionsix);

						label = new Attribute("label",
								"Repertoire de l'utilisateur");
						optionsix.setAttribute(label);

						name = new Attribute("name", "'home' => '");
						optionsix.setAttribute(name);

						terminator = new Attribute("terminator", "'");
						optionsix.setAttribute(terminator);

						// Element Choice

						Element choicefifth = new Element("choice");
						optionsix.addContent(choicefifth);

						label = new Attribute("label", "");
						choicefifth.setAttribute(label);
						choicefifth.setText("/home/" + udata.getUserLogin());

						// Element Option

						Element optionseven = new Element("option");
						optionfirst.addContent(optionseven);

						label = new Attribute("label", "Icone de l'utilisateur");
						optionseven.setAttribute(label);

						name = new Attribute("name", "'icon' => '");
						optionseven.setAttribute(name);

						terminator = new Attribute("terminator", "'");
						optionseven.setAttribute(terminator);

						// Element Choice

						Element choicesix = new Element("choice");
						optionseven.addContent(choicesix);

						label = new Attribute("label", "");
						choicesix.setAttribute(label);
						choicesix.setText("");
					}
				}
			}
		}
	}*/
}
