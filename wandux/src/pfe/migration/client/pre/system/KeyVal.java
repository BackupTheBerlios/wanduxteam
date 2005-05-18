package pfe.migration.client.pre.system;

import com.ice.jni.registry.NoSuchKeyException;
import com.ice.jni.registry.NoSuchValueException;
import com.ice.jni.registry.RegStringValue;
import com.ice.jni.registry.RegistryException;
import com.ice.jni.registry.RegistryKey;

/**
 * @author joe star
 *
 * Created on 4 mars 2005
 */
public class KeyVal {

	public String getKeyVal(String key) {
		RegistryKey aKey = null;
		String ret = "";

		try {
			aKey = com.ice.jni.registry.Registry.HKEY_CLASSES_ROOT
					.openSubKey(key);
		} catch (NoSuchKeyException e) { return null; // e.printStackTrace();
		} catch (RegistryException e) { return null; // e.printStackTrace();
		}

		try {
			aKey = com.ice.jni.registry.Registry.HKEY_CLASSES_ROOT
					.openSubKey(aKey.getDefaultValue()
							+ "\\shell\\open\\command");
			ret = aKey.getDefaultValue();
			return (ret);
		} catch (NoSuchKeyException e1) { ret = null; // e1.printStackTrace();
		} catch (NoSuchValueException e1) { ret = null; // e1.printStackTrace();
		} catch (RegistryException e1) { ret = null; // e1.printStackTrace();
		}
		return (ret);
	}

	public String getKeyValLocalMachine(String key, String field) {
		RegistryKey aKey = null;

		String ret = "";
			
		try {
			RegStringValue regValue = null; 
			aKey = com.ice.jni.registry.Registry.HKEY_LOCAL_MACHINE.openSubKey(key);
			return(aKey.getStringValue(field));

		} catch (NoSuchKeyException e) { e.printStackTrace();
		} catch (RegistryException e) { e.printStackTrace();
		}
		return (ret);
	}

	public String getNextKey(RegistryKey aKey, int SubkeyNum) {
		String ret = "";
			
		try {
			return(aKey.regEnumKey(SubkeyNum));

		} catch (NoSuchKeyException e) { return null;
		} catch (RegistryException e) { return null;
		}
	}

	public String FindCurrentInterFace(String key) {
		RegistryKey aKey = null;
		RegistryKey aifacekey = null;
		String testiface;
		String ip = null;

		try {
			RegStringValue regValue = null; 
			aKey = com.ice.jni.registry.Registry.HKEY_LOCAL_MACHINE.openSubKey(key);
			for (int i = 0; i < aKey.getNumberSubkeys(); i++)
			{
				testiface = getNextKey(aKey, i);
				aifacekey = com.ice.jni.registry.Registry.HKEY_LOCAL_MACHINE.openSubKey(key + "\\" + testiface);

				try {
					if (aifacekey.getStringValue("enableDHCP").length() == 1)
					{
						try {
							ip = aifacekey.getStringValue("dhcpIpAddress");
						} catch (NoSuchKeyException e) { continue;
						} catch (RegistryException e) { continue; }
						if (ip.compareTo("0.0.0.0") != 0)
						{
							return(testiface);
						}
					}
				} catch (NoSuchKeyException e) { e.printStackTrace(); }
				
			}
			return("");
		} catch (NoSuchKeyException e) { e.printStackTrace();
		} catch (RegistryException e) { e.printStackTrace();
		}
		String ret = "";
		return "";
	}

}
