package pfe.migration.client.pre.service;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Win32Cim {

	public static String query;

	public static String TargetIp;
	
	public Variant[] result = null;

	public static Dispatch querysubclasse;

	public static ActiveXComponent wmi;

	public Win32Cim(String query) {
		try {
			TargetIp = "127.0.0.1";

			try {
				String username = ""; // pas de login en local
				
				String password = ""; // pas de mdp en local

				// init activex wmi
				wmi = new ActiveXComponent("WbemScripting.SWbemLocator");

				// nombre de parametres
				Variant varParams[] = new Variant[4];

				Variant varRet;

				// les 4 parametres
				varParams[0] = new Variant(TargetIp);
				varParams[1] = new Variant("root\\cimv2");
				varParams[2] = new Variant(username);
				varParams[3] = new Variant(password);

				// connection au serveur
				varRet = wmi.invoke("ConnectServer", varParams);

				// recuperation du dispatch de l objet resultant
				querysubclasse = varRet.toDispatch();

			} catch (Exception e) {
				System.out.println("Exception: " + e);
				return;
			}

			// verification de la requete
			String tablename = null;
			StringTokenizer stoken = new StringTokenizer(query);
			for (int i = 0; i < 4; i++) {
				tablename = stoken.nextToken(" ");
			}
			String[] Tabproperties = null;
			// recuperation des propri�t�s
			stoken = new StringTokenizer(query);
			if (!(stoken.nextToken("*").equals(query)) && (tablename != null)) {
				// recuperation de la liste des propri�t�s
				CimProperties Prop = new CimProperties(querysubclasse);
				Tabproperties = Prop.GetProperties(tablename);
			} else {
				// local variable contenu dans la requete
				ArrayList arrayprop = new ArrayList();
				stoken = new StringTokenizer(query);
				stoken.nextToken(" ");
				String properties = stoken.nextToken(" ");
				stoken = new StringTokenizer(properties);
				// r�cuperation des prop dans la requete
				while (stoken.hasMoreTokens()) {
					arrayprop.add(stoken.nextToken(","));
				}
				Tabproperties = new String[arrayprop.size()];
				for (int i = 0; i < arrayprop.size(); i++) {
					Tabproperties[i] = (String)arrayprop.get(i);
				}
			}

			Variant[] queryresult = null;
			// recherche des donnees
			if ((Tabproperties != null) && (query != null)) {
				CimValues Val = new CimValues(Tabproperties, querysubclasse,
						query);
				queryresult = Val.GetValues();
			}
			this.result = queryresult;

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
}