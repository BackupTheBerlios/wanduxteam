package pfe.migration.server.ejb.bdd.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the users table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="users"
 */
public abstract class BaseUsers  implements Serializable {

	public static String PROP_TYPE = "Type";
	public static String PROP_PASSWORD = "Password";
	public static String PROP_LOGIN = "Login";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String _login;

	// fields
	private java.lang.String _password;
	private java.lang.String _type;


	// constructors
	public BaseUsers () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseUsers (java.lang.String _login) {
		this.setLogin(_login);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseUsers (
		java.lang.String _login,
		java.lang.String _password,
		java.lang.String _type) {

		this.setLogin(_login);
		this.setPassword(_password);
		this.setType(_type);
		initialize();
	}

	protected void initialize () {}



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="vm"
     *  column="login"
     */
	public java.lang.String getLogin () {
		return _login;
	}

	/**
	 * Set the unique identifier of this class
	 * @param _login the new ID
	 */
	public void setLogin (java.lang.String _login) {
		this._login = _login;
		this.hashCode = Integer.MIN_VALUE;
	}


	/**
	 * Return the value associated with the column: password
	 */
	public java.lang.String getPassword () {
		return _password;
	}

	/**
	 * Set the value related to the column: password
	 * @param _password the password value
	 */
	public void setPassword (java.lang.String _password) {
		this._password = _password;
	}


	/**
	 * Return the value associated with the column: type
	 */
	public java.lang.String getType () {
		return _type;
	}

	/**
	 * Set the value related to the column: type
	 * @param _type the type value
	 */
	public void setType (java.lang.String _type) {
		this._type = _type;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof pfe.migration.server.ejb.bdd.base.BaseUsers)) return false;
		else {
			pfe.migration.server.ejb.bdd.base.BaseUsers mObj = (pfe.migration.server.ejb.bdd.base.BaseUsers) obj;
			if (null == this.getLogin() || null == mObj.getLogin()) return false;
			else return (this.getLogin().equals(mObj.getLogin()));
		}
	}


	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getLogin()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getLogin().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}

}