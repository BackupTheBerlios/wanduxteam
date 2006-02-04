package pfe.migration.server.ejb.tool;

import java.util.StringTokenizer;

import com.jacob.com.Dispatch;
import com.jacob.com.EnumVariant;
import com.jacob.com.Variant;

public class CimValues {

	public static String TabProperties[];

	public static Dispatch ObjConnect;

	public static String querystring;

	public static int NB_PROPERTIES;

	public static String tablename;

	public static String elementname;

	public CimValues(String Tabprop[], Dispatch activx, String query) {

		// initialisation

		TabProperties = Tabprop;
		ObjConnect = activx;
		querystring = query;
		NB_PROPERTIES = Tabprop.length;

		// decoupage de la querystring
		StringTokenizer st = new StringTokenizer(query);
		for (int i = 0; i < 4; i++) {
			tablename = st.nextToken(" ");
		}

		st = new StringTokenizer(tablename);
		for (int i = 0; i < 2; i++) {
			elementname = st.nextToken("_");
		}
	}

	public String[] GetValues() {

		Object selectParam = new Variant(querystring);
		Variant retquery = Dispatch.call(ObjConnect, "ExecQuery", selectParam);
		Dispatch enumvalues = retquery.toDispatch();
		EnumVariant penumvalues = new EnumVariant(enumvalues);
		Variant ElementEnum;
		String[] resultat = new String[65535];

		int j = 0;
		while ((ElementEnum = penumvalues.Next()) != null) {
			for (int i = 0; i < NB_PROPERTIES; i++) {
				Dispatch obEnum = ElementEnum.toDispatch();
				Variant RetValue = Dispatch.call(obEnum, TabProperties[i]);
				if (!(RetValue.toString().equals("null"))) {
					if (RetValue.toString().equals("???")) {
						String test = TabProperties[i] + "(0)";
						try {
							RetValue = Dispatch.call(obEnum, test);
							resultat[j] = RetValue.toString();
						} catch (Exception e) {
						}
					} else {
						resultat[j] = RetValue.toString();
					}
					j++;
				}
			}
		}
		String[] res = new String[j - 1];
		for (int i = 0, k = 0; i < res.length; i++, k++)
			res[k] = resultat[i];
		return res;
	}
}
