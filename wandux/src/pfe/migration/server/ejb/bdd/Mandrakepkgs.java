package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseMandrakepkgs;

/**
 * This is the object class that relates to the MANDRAKEPKGS table.
 * Any customizations belong here.
 */
public class Mandrakepkgs extends BaseMandrakepkgs {

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Mandrakepkgs () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Mandrakepkgs (java.lang.Long _id) {
		super(_id);
	}

	/**
	 * Constructor for required fields
	 */
	public Mandrakepkgs (
		java.lang.Long _id,
		java.lang.String _name,
		java.lang.String _description) {

		super (
			_id,
			_name,
			_description);
	}

/*[CONSTRUCTOR MARKER END]*/
}