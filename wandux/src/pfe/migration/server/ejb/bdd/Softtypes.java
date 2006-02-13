package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseSofttypes;

/**
 * This is the object class that relates to the SOFTTYPES table.
 * Any customizations belong here.
 */
public class Softtypes extends BaseSofttypes {

/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -5551545262330533389L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public Softtypes () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Softtypes (java.lang.Byte _id) {
		super(_id);
	}

	/**
	 * Constructor for required fields
	 */
	public Softtypes (
		java.lang.Byte _id,
		java.lang.String _name,
		java.lang.Byte _textmode,
		java.lang.String _description,
		java.lang.Byte _guimode) {

		super (
			_id,
			_name,
			_textmode,
			_description,
			_guimode);
	}

/*[CONSTRUCTOR MARKER END]*/
}