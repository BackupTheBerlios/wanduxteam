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
		String program = null;

		System.out.println("\n__programs conf");
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
					program = kvpl.getCommandFromExtension(currentkey);
					System.out.println(program);
					// TODO CB6 store results under a hashtable without redundancy
					// propose to user to add a list of extension/program association into the database
					// if they are not already referenced > add this to log file and try to get it on
					// the net
					// Other example : ask user if he would like to
					// "save all files associated with program "RegSnap.exe""
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
