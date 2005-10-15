/*
 * Created on 3 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import com.ice.jni.registry.NoSuchKeyException;
import com.ice.jni.registry.RegistryException;
import com.ice.jni.registry.RegistryKey;

import pfe.migration.client.network.ClientEjb;
import pfe.migration.client.pre.system.KeyVal;
import pfe.migration.server.ejb.bdd.LangInfo;

/**
 * @author cb6
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LanguageSettings {

	public static String GetDefaultKBLayout(ClientEjb ce) {
		RegistryKey aKey = null;
		KeyVal kvkb = new KeyVal();
		String subkeyval = null;
		String mslangid = null;

		try {
			aKey = com.ice.jni.registry.Registry.HKEY_CURRENT_USER
					.openSubKey("Keyboard Layout\\Preload");

			Enumeration enumeration = aKey.valueElements();
			ArrayList locallanglist = new ArrayList();

			while (enumeration.hasMoreElements()) {
				subkeyval = (String) enumeration.nextElement();
				mslangid = aKey.getStringValue(subkeyval);
				// Retreival of local MS language Hex codes
				if (mslangid != null)
					locallanglist.add(mslangid.toUpperCase());
			}

			// Comparison between local and database lang entries
			if (locallanglist != null) {
				Iterator li = locallanglist.iterator();
				List l = ce.getLangInformation();
				Iterator i = l.iterator();
				LangInfo linfo = null;

				while (li.hasNext()) {
					String curlang = li.next().toString();
					for (i = l.iterator() ; i.hasNext(); ) {
						linfo = (LangInfo) i.next();
						String mshexa = "00000" + linfo.getLangMsLocalIdHexa();
						if (mshexa.equals(curlang)) {
							return(linfo.getLangLanguage());
						}
					}
				}
			}
		} catch (NoSuchKeyException e) {
			e.printStackTrace();
		} catch (RegistryException e) {
			e.printStackTrace();
		}
		return null;
	}
}