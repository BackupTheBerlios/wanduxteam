package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseSoftsubcategory;

/**
 * This is the object class that relates to the SOFTSUBCATEGORY table.
 * Any customizations belong here.
 */
public class Softsubcategory extends BaseSoftsubcategory {

/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 7249816702519850779L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public Softsubcategory () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Softsubcategory (java.lang.Byte _id) {
		super(_id);
	}

	/**
	 * Constructor for required fields
	 */
	public Softsubcategory (
		java.lang.Byte _id,
		java.lang.String _name,
		java.lang.String _description,
		java.lang.Byte _category) {

		super (
			_id,
			_name,
			_description,
			_category);
	}

/*[CONSTRUCTOR MARKER END]*/
}