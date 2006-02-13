package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseLinuxcomponents;

/**
 * This is the object class that relates to the LINUXCOMPONENTS table.
 * Any customizations belong here.
 */
public class Linuxcomponents extends BaseLinuxcomponents {

/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 4087357501509241188L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public Linuxcomponents () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Linuxcomponents (java.lang.Long _id) {
		super(_id);
	}

	/**
	 * Constructor for required fields
	 */
	public Linuxcomponents (
		java.lang.Long _id,
		java.lang.Short _type,
		java.lang.Byte _multiplatform,
		java.lang.Byte _proprietary,
		java.lang.Byte _rating,
		java.lang.Byte _subcategory,
		java.lang.String _name,
		java.lang.Byte _category,
		java.lang.Byte _stdprotocol,
		java.lang.Byte _winexists) {

		super (
			_id,
			_type,
			_multiplatform,
			_proprietary,
			_rating,
			_subcategory,
			_name,
			_category,
			_stdprotocol,
			_winexists);
	}

/*[CONSTRUCTOR MARKER END]*/
}