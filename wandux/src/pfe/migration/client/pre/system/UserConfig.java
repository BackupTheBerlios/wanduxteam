/*
 * Created on 22 déc. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.system;

import pfe.migration.client.pre.service.Win32Cim;
import com.jacob.com.Variant;

/**
 * @author CornFlaks
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UserConfig {

	private Win32Cim _wcim = null;

	public UserConfig(Win32Cim wcim) {
		this._wcim = wcim;
	}

	public Variant[] listUsers() {
		this._wcim.Request("SELECT Name FROM Win32_UserAccount");
		return this._wcim.GetResult();
	}

	public Variant[] getUserKbLayout() {
		this._wcim.Request("SELECT Layout FROM Win32_Keyboard");
		return this._wcim.GetResult();
	}

	public Variant[] getUserProxyServ() {
		this._wcim.Request("SELECT ProxyServer FROM Win32_Proxy");
		return this._wcim.GetResult();
	}

	public Variant[] getHostname() {
		this._wcim
				.Request("SELECT DNSHostName FROM Win32_NetworkAdapterConfiguration");
		return this._wcim.GetResult();
	}

	public Variant[] getDomainName() {
		this._wcim
				.Request("SELECT DNSDomain FROM Win32_NetworkAdapterConfiguration");
		return this._wcim.GetResult();
	}

	public Variant[] getUserTimezone() {
		this._wcim.Request("SELECT StandardName FROM Win32_TimeZone");
		return this._wcim.GetResult();
	}

}
