/*
 * Created on 5 nov. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pfe.migration.client.pre.system;

import com.jacob.com.Variant;
import pfe.migration.client.pre.service.Win32Cim;

/**
 * @author CornFlaks
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class NetConfig {
	private Win32Cim _wcim = null;

	public NetConfig(Win32Cim wcim) {
		this._wcim = wcim;
	}

	public Variant[] GetMac() {
		this._wcim
				.Request("SELECT MACAddress FROM Win32_NetworkAdapterConfiguration");
		return this._wcim.GetResult();
	}

	public Variant[][] GetIpaddress() {
		this._wcim
				.Request("SELECT IPAddress FROM Win32_NetworkAdapterConfiguration");
		return this._wcim.GetResultTab();
	}

	public Variant[] GetDHCPEnable() {
		this._wcim
				.Request("SELECT DHCPEnabled FROM Win32_NetworkAdapterConfiguration");
		return this._wcim.GetResult();
	}

	public Variant[][] GetNetmask() {
		this._wcim
				.Request("SELECT IPSubnet FROM Win32_NetworkAdapterConfiguration");
		return this._wcim.GetResultTab();
	}

	public Variant[][] GetDnsServer() {
		this._wcim
				.Request("SELECT DNSServerSearchOrder FROM Win32_NetworkAdapterConfiguration");
		return this._wcim.GetResultTab();
	}

	public Variant[][] GetGate() {
		this._wcim
				.Request("SELECT DefaultIPGateway FROM Win32_NetworkAdapterConfiguration");
		return this._wcim.GetResultTab();
	}

	public Variant[] GetCaption() {
		this._wcim
				.Request("SELECT Caption FROM Win32_NetworkAdapterConfiguration");
		return this._wcim.GetResult();
	}

	public Variant[] GetStatus() {
		this._wcim
				.Request("SELECT IPEnabled FROM Win32_NetworkAdapterConfiguration");
		return this._wcim.GetResult();
	}

	public Variant[] IndexCaption() {
		this._wcim
				.Request("SELECT Index FROM Win32_NetworkAdapterConfiguration");
		return this._wcim.GetResult();
	}

}