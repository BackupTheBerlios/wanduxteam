/*
 * Created on 4 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.system;

import com.ice.jni.registry.NoSuchKeyException;
import com.ice.jni.registry.NoSuchValueException;
import com.ice.jni.registry.RegistryException;
import com.ice.jni.registry.RegistryKey;

/**
 * @author joe star
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class KeyVal {

	public String getKeyVal(String key) {
		RegistryKey aKey = null;
		String ret = "";

		try {
			aKey = com.ice.jni.registry.Registry.HKEY_CLASSES_ROOT
					.openSubKey(key);
		} catch (NoSuchKeyException e) { e.printStackTrace();
		} catch (RegistryException e) { e.printStackTrace();
		}

		try {
			aKey = com.ice.jni.registry.Registry.HKEY_CLASSES_ROOT
					.openSubKey(aKey.getDefaultValue()
							+ "\\shell\\open\\command");
			ret = aKey.getDefaultValue();
			return (ret);
		} catch (NoSuchKeyException e1) { e1.printStackTrace();
		} catch (NoSuchValueException e1) { e1.printStackTrace();
		} catch (RegistryException e1) { e1.printStackTrace();
		}
		return (ret);
	}
}
