package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseNetworkConfig;

/**
 * This is the object class that relates to the NETWORK_CONFIG table.
 * Any customizations belong here.
 */
public class NetworkConfig extends BaseNetworkConfig {

/*[CONSTRUCTOR MARKER BEGIN]*/
	public NetworkConfig () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public NetworkConfig (java.lang.Integer _id) {
		super(_id);
	}

/*[CONSTRUCTOR MARKER END]*/
}