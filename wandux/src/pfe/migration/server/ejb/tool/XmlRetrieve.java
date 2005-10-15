package pfe.migration.server.ejb.tool;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class XmlRetrieve {

		private static Element racine;
		private static Document document;
		private static String _FilePath = "";
	
		public XmlRetrieve(String FilePath)
		{
			_FilePath = FilePath;
		}
		
		public String IpServer()
		{
			try {
				lire(_FilePath);
			} catch (Exception e) {
				System.out.println("Can't read Xml File");
			}
			
			List list = racine.getChildren();
			Iterator i = list.iterator();
			
			while (i.hasNext()) {
				Element courant = (Element) i.next();
				if (courant.getName() == "ip-server") {
					return courant.getText();
				}
			}
			return null;
		}
		
		public static void lire(String strfile) throws Exception {
			SAXBuilder sxb = new SAXBuilder();
			document = sxb.build(new File(strfile));
			racine = document.getRootElement();
		}
}
