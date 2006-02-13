package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseSoftcategory;

/**
 * This is the object class that relates to the SOFTCATEGORY table.
 * Any customizations belong here.
 */
public class Softcategory extends BaseSoftcategory {

/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -234987576316559754L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public Softcategory () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Softcategory (java.lang.Byte _id) {
		super(_id);
	}

	/**
	 * Constructor for required fields
	 */
	public Softcategory (
		java.lang.Byte _id,
		java.lang.String _name,
		java.lang.String _description) {

		super (
			_id,
			_name,
			_description);
	}

/*[CONSTRUCTOR MARKER END]*/
}