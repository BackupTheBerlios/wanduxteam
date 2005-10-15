package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseParamIe;

/**
 * This is the object class that relates to the PARAM_IE table.
 * Any customizations belong here.
 */
public class ParamIe extends BaseParamIe {

/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public ParamIe () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ParamIe (java.lang.Integer _id) {
		super(_id);
	}

/*[CONSTRUCTOR MARKER END]*/
}