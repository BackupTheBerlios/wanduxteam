/*
 * Created on 3 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app;

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
public class ProgramsLister {
	public static void ParseExtensions()
	{
		KeyVal kvpl = new KeyVal();
		RegistryKey aKey = null;
		String currentkey = ".init";

		System.out.println("__programs conf");
		try {
			aKey = com.ice.jni.registry.Registry.HKEY_CLASSES_ROOT.openSubKey("");
			for (int i = 1; i < aKey.getNumberSubkeys(); i++)
			{
				if (!currentkey.substring(0, 1).equals("."))
				{
					System.out.println("\nextension count: "+ i);
					break;
				}
					currentkey = kvpl.getNextKey(aKey, i);
					System.out.print(currentkey + ", ");
			}
			//System.out.println(aKey.getNumberSubkeys());
		} catch (NoSuchKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
