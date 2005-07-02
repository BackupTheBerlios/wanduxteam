/*
 * Created on 1 juil. 2003
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.server.ejb.tool;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

/**
 * @author Corn
 *
 */

public class XmlAdllParseSelTxt {

	public void section_change_select_txt(Element element, String sectionlbl, String choicelbl, 
			String selected, String Text, String optionlbl_1, String optionlbl_2, 
			String optionlbl_3, String optionlbl_4)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "section"
				&& sectionlbl.equals(courant.getAttributeValue("label")))
			{
				option_change_select_txt(courant, choicelbl, selected, 
					Text, optionlbl_1, optionlbl_2, optionlbl_3, optionlbl_4);
			}	
			else
				section_change_select_txt(courant, sectionlbl, choicelbl, selected, 
						Text, optionlbl_1, optionlbl_2, optionlbl_3, optionlbl_4);
		}
	}
	
	public void section_change_select_txt(Element element, String sectionlbl, String choicelbl, 
			String selected, String Text, String optionlbl_1, String optionlbl_2, 
			String optionlbl_3)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "section"
				&& sectionlbl.equals(courant.getAttributeValue("label")))
			{
				option_change_select_txt(courant, choicelbl, selected, 
					Text, optionlbl_1, optionlbl_2, optionlbl_3);
			}	
			else
				section_change_select_txt(courant, sectionlbl, choicelbl, selected, 
						Text, optionlbl_1, optionlbl_2, optionlbl_3);
		}
	}
	
	public void section_change_select_txt(Element element, String sectionlbl, String choicelbl, 
			String selected, String Text, String optionlbl_1, String optionlbl_2)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "section"
				&& sectionlbl.equals(courant.getAttributeValue("label")))
			{
				option_change_select_txt(courant, choicelbl, selected, 
					Text, optionlbl_1, optionlbl_2);
			}	
			else
				section_change_select_txt(courant, sectionlbl, choicelbl, selected, 
						Text, optionlbl_1, optionlbl_2);
		}
	}
	
	public void section_change_select_txt(Element element, String sectionlbl, String choicelbl, 
			String selected, String Text, String optionlbl_1)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "section"
				&& sectionlbl.equals(courant.getAttributeValue("label")))
			{
				option_change_select_txt(courant, choicelbl, selected, 
					Text, optionlbl_1);
			}	
			else
				section_change_select_txt(courant, sectionlbl, choicelbl, selected, 
						Text, optionlbl_1);
		}
	}
	
	public void option_change_select_txt(Element element, String choicelbl, String selected, 
			String Text, String optionlbl_1, String optionlbl_2, String optionlbl_3,
			String optionlbl_4)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "option"
				&& optionlbl_1.equals(courant.getAttributeValue("label")))
			{
				option_change_select_txt(courant, choicelbl, selected, 
					Text, optionlbl_2, optionlbl_3, optionlbl_4);
			}	
			else
				option_change_select_txt(courant, choicelbl, selected, 
						Text, optionlbl_1, optionlbl_2, optionlbl_3, optionlbl_4);
		}
	}
	
	public void option_change_select_txt(Element element, String choicelbl, String selected, 
			String Text, String optionlbl_1, String optionlbl_2, String optionlbl_3)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "option"
				&& optionlbl_1.equals(courant.getAttributeValue("label")))
			{
				option_change_select_txt(courant, choicelbl, selected, 
					Text, optionlbl_2, optionlbl_3);
			}	
			else
				option_change_select_txt(courant, choicelbl, selected, 
						Text, optionlbl_1, optionlbl_2, optionlbl_3);
		}
	}
	
	public void option_change_select_txt(Element element, String choicelbl, String selected, 
			String Text, String optionlbl_1, String optionlbl_2)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "option"
				&& optionlbl_1.equals(courant.getAttributeValue("label")))
			{
				option_change_select_txt(courant, choicelbl, selected, 
					Text, optionlbl_2);
			}	
			else
				option_change_select_txt(courant, choicelbl, selected, 
						Text, optionlbl_1, optionlbl_2);
		}
	}
	
	public void option_change_select_txt(Element element, String choicelbl, String selected, 
			String Text, String optionlbl_1)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "option"
				&& optionlbl_1.equals(courant.getAttributeValue("label")))
			{
				choice_change_select_txt(courant, choicelbl, selected, 
					Text);
			}	
			else
				option_change_select_txt(courant, choicelbl, selected, 
						Text, optionlbl_1);
		}
	}
	
	private void choice_change_select_txt(Element element, String choicelbl,
			String selected, String text) {
		List list = element.getChildren();
		Iterator i = list.iterator();
		Object[] obj = list.toArray();
		
		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "choice"
					&& choicelbl.equals(courant.getAttributeValue("label"))) {
				if (choicelbl.equals("Oui") && selected.equals("yes")) {
					courant.setAttribute("selected", selected);
					courant.setText(text);
					courant = (Element) i.next();
					courant.removeAttribute("selected");
					courant.setText("0");
				} else if (choicelbl.equals("Oui") && selected.equals("no")) {
					courant.removeAttribute("selected");
					courant.setText(text);
					courant = (Element) i.next();
					courant.setAttribute("selected", "yes");
					courant.setText("1");
					
				} else if (choicelbl.equals("Non") && selected.equals("yes")) {
					courant.setAttribute("selected", selected);
					courant.setText(text);
					courant = (Element)obj[0];
					courant.removeAttribute("selected");
					courant.setText("0");

				} else if (choicelbl.equals("Non") && selected.equals("no")) {
					courant.removeAttribute("selected");
					courant.setText(text);
					courant = (Element)obj[0];
					courant.setAttribute("selected", "yes");
					courant.setText("1");
					
				} else {
					courant.setAttribute("selected", selected);
					courant.setText(text);
				}
			}
		}
	}	
}
