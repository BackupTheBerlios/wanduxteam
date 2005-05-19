package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseGlobalConf;

/**
 * This is the object class that relates to the global_conf table.
 * Any customizations belong here.
 */
public class GlobalConf extends BaseGlobalConf {

/*[CONSTRUCTOR MARKER BEGIN]*/
	public GlobalConf () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public GlobalConf (java.lang.Integer _key) {
		super(_key);
	}

	/**
	 * Constructor for required fields
	 */
	public GlobalConf (
		java.lang.Integer _key,
		java.lang.String _hostname) {

		super (
			_key,
			_hostname);
	}

/*[CONSTRUCTOR MARKER END]*/
}