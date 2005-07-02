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
public class XmlAdllParseTxt {
	
	public void section_change_txt(Element element, String sectionlbl, String choicelbl, 
			String Text, String optionlbl_1, String optionlbl_2, 
			String optionlbl_3, String optionlbl_4)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "section"
				&& sectionlbl.equals(courant.getAttributeValue("label")))
			{
				option_change_txt(courant, choicelbl, 
					Text, optionlbl_1, optionlbl_2, optionlbl_3, optionlbl_4);
			}	
			else
				section_change_txt(courant, sectionlbl, choicelbl, 
						Text, optionlbl_1, optionlbl_2, optionlbl_3, optionlbl_4);
		}
	}
	
	public void section_change_txt(Element element, String sectionlbl, String choicelbl, 
			String Text, String optionlbl_1, String optionlbl_2, 
			String optionlbl_3)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "section"
				&& sectionlbl.equals(courant.getAttributeValue("label")))
			{
				option_change_txt(courant, choicelbl, 
					Text, optionlbl_1, optionlbl_2, optionlbl_3);
			}	
			else
				section_change_txt(courant, sectionlbl, choicelbl, 
						Text, optionlbl_1, optionlbl_2, optionlbl_3);
		}
	}
	
	public void section_change_txt(Element element, String sectionlbl, String choicelbl, 
			String Text, String optionlbl_1, String optionlbl_2)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "section"
				&& sectionlbl.equals(courant.getAttributeValue("label")))
			{
				option_change_txt(courant, choicelbl, 
					Text, optionlbl_1, optionlbl_2);
			}	
			else
				section_change_txt(courant, sectionlbl, choicelbl, 
						Text, optionlbl_1, optionlbl_2);
		}
	}
	
	public void section_change_txt(Element element, String sectionlbl, String choicelbl, 
			String Text, String optionlbl_1)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "section"
				&& sectionlbl.equals(courant.getAttributeValue("label")))
			{
				option_change_txt(courant, choicelbl, 
					Text, optionlbl_1);
			}	
			else
				section_change_txt(courant, sectionlbl, choicelbl, 
						Text, optionlbl_1);
		}
	}
	
	public void option_change_txt(Element element, String choicelbl, 
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
				option_change_txt(courant, choicelbl, 
					Text, optionlbl_2, optionlbl_3, optionlbl_4);
			}	
			else
				option_change_txt(courant, choicelbl, 
						Text, optionlbl_1, optionlbl_2, optionlbl_3, optionlbl_4);
		}
	}
	
	public void option_change_txt(Element element, String choicelbl, 
			String Text, String optionlbl_1, String optionlbl_2, String optionlbl_3)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "option"
				&& optionlbl_1.equals(courant.getAttributeValue("label")))
			{
				option_change_txt(courant, choicelbl,
					Text, optionlbl_2, optionlbl_3);
			}	
			else
				option_change_txt(courant, choicelbl,
						Text, optionlbl_1, optionlbl_2, optionlbl_3);
		}
	}
	
	public void option_change_txt(Element element, String choicelbl, 
			String Text, String optionlbl_1, String optionlbl_2)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "option"
				&& optionlbl_1.equals(courant.getAttributeValue("label")))
			{
				option_change_txt(courant, choicelbl, 
					Text, optionlbl_2);
			}	
			else
				option_change_txt(courant, choicelbl, 
						Text, optionlbl_1, optionlbl_2);
		}
	}
	
	public void option_change_txt(Element element, String choicelbl,
			String Text, String optionlbl_1)
	{
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "option"
				&& optionlbl_1.equals(courant.getAttributeValue("label")))
			{
				choice_change_txt(courant, choicelbl,
					Text);
			}	
			else
				option_change_txt(courant, choicelbl, 
						Text, optionlbl_1);
		}
	}
	
	private void choice_change_txt(Element element, String choicelbl,
			String text) {
		List list = element.getChildren();
		Iterator i = list.iterator();

		while (i.hasNext()) {
			Element courant = (Element) i.next();
			if (courant.getName() == "choice"
					&& choicelbl.equals(courant.getAttributeValue("label"))) {
					courant.setText(text);
				}
			}
		}
}	

