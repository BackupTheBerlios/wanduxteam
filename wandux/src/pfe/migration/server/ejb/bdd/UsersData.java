package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseUsersData;

/**
 * This is the object class that relates to the users_data table.
 * Any customizations belong here.
 */
public class UsersData extends BaseUsersData {

/*[CONSTRUCTOR MARKER BEGIN]*/
	public UsersData () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public UsersData (java.lang.String _key) {
		super(_key);
	}

	/**
	 * Constructor for required fields
	 */
	public UsersData (
		java.lang.String _key,
		java.lang.String _type,
		java.lang.String _login) {

		super (
			_key,
			_type,
			_login);
	}

/*[CONSTRUCTOR MARKER END]*/
}