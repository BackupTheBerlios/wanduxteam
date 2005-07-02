/*
 * Created on 4 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.system;

import com.ice.jni.registry.NoSuchKeyException;
import com.ice.jni.registry.RegStringValue;
import com.ice.jni.registry.RegistryException;
import com.ice.jni.registry.RegistryKey;

/**
 * @author lahous
 * 
 * This class is to provide hight level access to the registry
 * 
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RegistryAccess {
	
	public String GetRegHKCUValue(String SubKey, String Key)
	{
		String Value = null;
		RegistryKey aKey = null;
		RegStringValue regValue = null;     
			try {
				 aKey = com.ice.jni.registry.Registry.HKEY_CURRENT_USER.openSubKey(SubKey);
				 regValue = (RegStringValue)aKey.getValue(Key);
				 Value = regValue.getData();
				  
			} catch (NoSuchKeyException e) { System.err.println("pas de cle"); //e.printStackTrace();
			} catch (RegistryException e) { System.err.println("RegistryException"); // e.printStackTrace();
			}
		return Value;
	}
	
	public String GetRegHKCLValue(String SubKey, String Key)
	{
		String Value = null;
		RegistryKey aKey = null;
		RegStringValue regValue = null;     
			try {
				 aKey = com.ice.jni.registry.Registry.HKEY_CLASSES_ROOT.openSubKey(SubKey);
				 regValue = (RegStringValue)aKey.getValue(Key);
				 Value = regValue.getData();
				  
			} catch (NoSuchKeyException e) {  e.printStackTrace();
			} catch (RegistryException e) { e.printStackTrace();
			}
		return Value;
	}

	public String GetRegHKCCValue(String SubKey, String Key)
	{
		String Value = null;
		RegistryKey aKey = null;
		RegStringValue regValue = null;     
			try {
				 aKey = com.ice.jni.registry.Registry.HKEY_CURRENT_CONFIG.openSubKey(SubKey);
				 regValue = (RegStringValue)aKey.getValue(Key);
				 Value = regValue.getData();
				  
			} catch (NoSuchKeyException e) {  e.printStackTrace();
			} catch (RegistryException e) { e.printStackTrace();
			}
		return Value;
	}
	
	public String GetRegHKDDValue(String SubKey, String Key)
	{
		String Value = null;
		RegistryKey aKey = null;
		RegStringValue regValue = null;     
			try {
				 aKey = com.ice.jni.registry.Registry.HKEY_DYN_DATA.openSubKey(SubKey);
				 regValue = (RegStringValue)aKey.getValue(Key);
				 Value = regValue.getData();
				  
			} catch (NoSuchKeyException e) {  e.printStackTrace();
			} catch (RegistryException e) { e.printStackTrace();
			}
		return Value;
	}
	
	public String GetRegHKLMValue(String SubKey, String Key)
	{
		String Value = null;
		RegistryKey aKey = null;
		RegStringValue regValue = null;     
			try {
				 aKey = com.ice.jni.registry.Registry.HKEY_LOCAL_MACHINE.openSubKey(SubKey);
				 regValue = (RegStringValue)aKey.getValue(Key);
				 Value = regValue.getData();
				  
			} catch (NoSuchKeyException e) {  e.printStackTrace();
			} catch (RegistryException e) { e.printStackTrace();
			}
		return Value;
	}
	
	public String GetRegHKUSERSValue(String SubKey, String Key)
	{
		String Value = null;
		RegistryKey aKey = null;
		RegStringValue regValue = null;     
			try {
				 aKey = com.ice.jni.registry.Registry.HKEY_USERS.openSubKey(SubKey);
				 regValue = (RegStringValue)aKey.getValue(Key);
				 Value = regValue.getData();
				  
			} catch (NoSuchKeyException e) {  e.printStackTrace();
			} catch (RegistryException e) { e.printStackTrace();
			}
		return Value;
	}
	
	
	public String GetRegHKPDValue(String SubKey, String Key)
	{
		String Value = null;
		RegistryKey aKey = null;
		RegStringValue regValue = null;     
			try {
				 aKey = com.ice.jni.registry.Registry.HKEY_PERFORMANCE_DATA.openSubKey(SubKey);
				 regValue = (RegStringValue)aKey.getValue(Key);
				 Value = regValue.getData();
				  
			} catch (NoSuchKeyException e) {  e.printStackTrace();
			} catch (RegistryException e) { e.printStackTrace();
			}
		return Value;
	}
}
