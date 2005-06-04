/*
 * Created on 4 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.system;

import pfe.migration.client.pre.system.RegistryAccess;
/**
 * @author lahous
 *
 * This class is to obtain all the information related to Internet Explorer parameters
 * Proxy, http version passwords, cookies, etc.
 * 
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IeParam {
	String InternetSettingSubKey = "Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings";
	String ProxyServerKey = "ProxyServer";
	String ProxyEnable = "ProxyEnable";
	String ProxyOverride = "ProxyOverride";
	String AutoConfigURL = "AutoConfigURL";
	String ProxyServer = null;
	
	public String getProxyServer(){
	RegistryAccess ra = new pfe.migration.client.pre.system.RegistryAccess();
	ProxyServer = ra.GetRegHKCUValue(InternetSettingSubKey, ProxyServerKey);
	return ProxyServer;
	}
	public String getProxyOverride(){
		RegistryAccess ra = new pfe.migration.client.pre.system.RegistryAccess();
		ProxyServer = ra.GetRegHKCUValue(InternetSettingSubKey, ProxyOverride);
		return ProxyServer;
		}
	
}
