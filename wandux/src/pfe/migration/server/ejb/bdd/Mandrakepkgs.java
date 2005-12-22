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
		java.lang.Byte _subcategory,
		java.lang.String _name,
		java.lang.String _description,
		java.lang.Byte _category) {

		super (
			_id,
			_subcategory,
			_name,
			_description,
			_category);
	}

/*[CONSTRUCTOR MARKER END]*/
}