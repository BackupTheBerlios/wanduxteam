package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseUsers;

/**
 * This is the object class that relates to the users table.
 * Any customizations belong here.
 */
public class Users extends BaseUsers {

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Users () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Users (java.lang.String _login) {
		super(_login);
	}

	/**
	 * Constructor for required fields
	 */
	public Users (
		java.lang.String _login,
		java.lang.String _password,
		java.lang.String _type) {

		super (
			_login,
			_password,
			_type);
	}

/*[CONSTRUCTOR MARKER END]*/
}