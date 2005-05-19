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

	/**
	 * Constructor for required fields
	 */
	public NetworkDhcpConfig (
		java.lang.Integer _dhcpKey,
		java.lang.String _dhcpDomain,
		java.lang.String _dhcpDefGateway,
		java.lang.String _dhcpServer,
		java.lang.String _dhcpSubnetmask,
		java.lang.String _dhcpPriDns,
		java.lang.String _dhcpAdress,
		java.lang.String _dhcpSecDns) {

		super (
			_dhcpKey,
			_dhcpDomain,
			_dhcpDefGateway,
			_dhcpServer,
			_dhcpSubnetmask,
			_dhcpPriDns,
			_dhcpAdress,
			_dhcpSecDns);
	}

/*[CONSTRUCTOR MARKER END]*/
}