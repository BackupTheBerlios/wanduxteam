package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseWindowscomponents;

/**
 * This is the object class that relates to the WINDOWSCOMPONENTS table.
 * Any customizations belong here.
 */
public class Windowscomponents extends BaseWindowscomponents {

/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 4107915085598188038L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public Windowscomponents () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Windowscomponents (java.lang.String _id) {
		super(_id);
	}

	/**
	 * Constructor for required fields
	 */
	public Windowscomponents (
		java.lang.String _id,
		java.lang.String _type,
		java.lang.String _multiplatform,
		java.lang.String _managed,
		java.lang.String _proprietary,
		java.lang.String _rating,
		java.lang.String _lxexists,
		java.lang.Byte _subcategory,
		java.lang.String _name,
		java.lang.Byte _category,
		java.lang.String _stdprotocol) {

		super (
			_id,
			_type,
			_multiplatform,
			_managed,
			_proprietary,
			_rating,
			_lxexists,
			_subcategory,
			_name,
			_category,
			_stdprotocol);
	}

/*[CONSTRUCTOR MARKER END]*/
}