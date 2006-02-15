package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseGlobalConf;

/**
 * This is the object class that relates to the GLOBAL_CONF table.
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
	public GlobalConf (java.lang.Integer _id) {
		super(_id);
	}

/*[CONSTRUCTOR MARKER END]*/
}