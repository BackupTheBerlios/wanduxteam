/*
 * Created on 17 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.app;

import pfe.migration.client.pre.system.KeyVal;

import com.ice.jni.registry.NoSuchKeyException;
import com.ice.jni.registry.RegistryException;
import com.ice.jni.registry.RegistryKey;

/**
 * @author cb6
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserConfig {
	public static void ProxyServer()
	{
		RegistryKey aKey = null;
		KeyVal kvuc = new KeyVal();
		String subkeyval = null;

		//aKey = com.ice.jni.registry.Registry.HKEY_CURRENT_USER.openSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings");
		subkeyval = kvuc.getKeyValCurrentUser("Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings", "ProxyServer");
		System.out.println(subkeyval);
	}
	public static void ProxyOverride()
	{
		RegistryKey aKey = null;
		KeyVal kvuc = new KeyVal();
		String subkeyval = null;

		//aKey = com.ice.jni.registry.Registry.HKEY_CURRENT_USER.openSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings");
		subkeyval = kvuc.getKeyValCurrentUser("Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings", "ProxyOverride");
		System.out.println(subkeyval);
	}
	public static void BGImage()
	{
		RegistryKey aKey = null;
		KeyVal kvuc = new KeyVal();
		String subkeyval = null;

		subkeyval = kvuc.getKeyValCurrentUser("Software\\Microsoft\\Internet Explorer\\Desktop\\General", "WallPaper");
		System.out.println(subkeyval);
	}

}
