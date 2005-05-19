package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseNetworkDhcpConfig;

/**
 * This is the object class that relates to the network_dhcp_config table.
 * Any customizations belong here.
 */
public class NetworkDhcpConfig extends BaseNetworkDhcpConfig {

/*[CONSTRUCTOR MARKER BEGIN]*/
	public NetworkDhcpConfig () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public NetworkDhcpConfig (java.lang.Integer _dhcpKey) {
		super(_dhcpKey);
	}
/*[CONSTRUCTOR MARKER END]*/
}