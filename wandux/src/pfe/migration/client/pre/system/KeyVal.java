package pfe.migration.client.pre.system;

import com.ice.jni.registry.NoSuchKeyException;
import com.ice.jni.registry.NoSuchValueException;
import com.ice.jni.registry.RegStringValue;
import com.ice.jni.registry.RegistryException;
import com.ice.jni.registry.RegistryKey;

/**
 * @author cb6
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
			if (aKey.getStringValue(field) != null)
			{
				return(aKey.getStringValue(field));
			}
			else
			{
				return "no such value";
			}
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

	/**
	 * Dhcp interface finding method
	 * @param key
	 * @return
	 */
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
					else
					{
						if (i == aKey.getNumberSubkeys() - 1)
							return ("dhcpdisabled");
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
	
	/**
	 * Method called if trying to find a DhcpEnabled interface failed
	 * @param key
	 * @return
	 */
	public String FindStaticInterFace(String key) {
		RegistryKey aKey = null;
		RegistryKey aifacekey = null;
		String testiface;
		String ip = null;
		String linkvalue = null;

		linkvalue = FindLinkage();
		try {
			RegStringValue regValue = null; 
			aKey = com.ice.jni.registry.Registry.HKEY_LOCAL_MACHINE.openSubKey(key);
			for (int i = 0; i < aKey.getNumberSubkeys(); i++)
			{
				testiface = getNextKey(aKey, i);
				aifacekey = com.ice.jni.registry.Registry.HKEY_LOCAL_MACHINE.openSubKey(key + "\\" + testiface);

				System.out.println("ti : " + testiface);

				if ( testiface.equals(linkvalue))
				{
					return(testiface);
				}
			}
			return("");
		} catch (NoSuchKeyException e) { e.printStackTrace();
		} catch (RegistryException e) { e.printStackTrace();
		}
		String ret = "";
		return "";
	}
	public static String FindLinkage()
	{
		RegistryKey aKey = null;
		String linkvalue = null;

		try {
			aKey = com.ice.jni.registry.Registry.HKEY_LOCAL_MACHINE.openSubKey(
					"SYSTEM\\CurrentControlSet\\Services\\Tcpip\\Linkage");
			linkvalue = aKey.getStringValue("Route");
			return(linkvalue.substring(1, linkvalue.length() - 1));
		} catch (NoSuchKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RegistryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.exit(0);
		return(null);
	}
}
