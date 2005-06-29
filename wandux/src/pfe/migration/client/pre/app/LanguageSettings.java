/*
 * Created on 3 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app;

import java.util.Enumeration;

import com.ice.jni.registry.NoSuchKeyException;
import com.ice.jni.registry.RegistryException;
import com.ice.jni.registry.RegistryKey;
import pfe.migration.client.pre.system.KeyVal;

/**
 * @author cb6
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LanguageSettings {

	public static void GetDefaultKBLayout()
	{
		RegistryKey aKey = null;
		KeyVal kvkb = new KeyVal();
		String subkeyval = null;
		String mslangid = null;
		
		try {
			aKey = com.ice.jni.registry.Registry.HKEY_CURRENT_USER.openSubKey(
					"Keyboard Layout\\Preload");

			Enumeration enum = aKey.valueElements();
			System.out.println("\n__langage conf");
			while (enum.hasMoreElements()) {
			    subkeyval = (String) enum.nextElement();
			    mslangid = aKey.getStringValue(subkeyval);
			    // recuperation du code de chaque entree dans la liste des langages.
			    System.out.println("MS hex code: " + mslangid);
			    if (mslangid.compareTo("0000040c") == 0)
			    {
			    	System.out.println("default langage is FR");
			    }
			}
		} catch (NoSuchKeyException e) {
			e.printStackTrace();
		} catch (RegistryException e) {
			e.printStackTrace();
		}
	}
}
