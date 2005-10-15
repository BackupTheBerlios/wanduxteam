package pfe.migration.server.ejb.bdd;

import pfe.migration.server.ejb.bdd.base.BaseUsersData;

/**
 * This is the object class that relates to the USERS_DATA table.
 * Any customizations belong here.
 */
public class UsersData extends BaseUsersData {

/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public UsersData () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public UsersData (java.lang.Integer _id) {
		super(_id);
	}

/*[CONSTRUCTOR MARKER END]*/
}