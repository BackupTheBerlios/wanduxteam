package pfe.migration.server.ejb.tool;

import java.util.ArrayList;

import com.jacob.com.Dispatch;
import com.jacob.com.EnumVariant;
import com.jacob.com.Variant;

public class CimProperties {

	public static Dispatch querysubclasse;

	public String tabproperties[];

	public static ArrayList resultats = new ArrayList();

	public CimProperties(Dispatch activx) {
		// initialisation
		querysubclasse = activx;
	}

	public String[] GetProperties(String classepath) {
		try {
			// recherche des propriétés
			Variant varclasse = Dispatch
					.call(querysubclasse, "Get", classepath);
			Dispatch classe = varclasse.toDispatch();
			Variant propertieslist = Dispatch.call(classe, "properties_");

			Dispatch liste = propertieslist.toDispatch();
			EnumVariant pliste = new EnumVariant(liste);

			Variant elementliste;
			while ((elementliste = pliste.Next()) != null) {
				Dispatch objenumsubclasselist = elementliste.toDispatch();
				Variant retelement = Dispatch
						.call(objenumsubclasselist, "name");
				resultats.add(retelement.toString());
			}
			tabproperties = new String[resultats.size()];
			for (int i = 0; i < resultats.size(); i++) {
				tabproperties[i] = (String) resultats.get(i);
			}
			return tabproperties;

		} catch (Exception e) {
			System.out.println("Exception: " + e);
			return null;
		}
	}
}
