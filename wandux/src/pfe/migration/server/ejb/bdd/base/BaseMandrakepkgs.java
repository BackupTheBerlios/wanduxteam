package pfe.migration.server.ejb.bdd.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the MANDRAKEPKGS table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="MANDRAKEPKGS"
 */
public abstract class BaseMandrakepkgs  implements Serializable {

	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_NAME = "Name";
	public static String PROP_ID = "Id";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Long _id;

	// fields
	private java.lang.String _name;
	private java.lang.String _description;


	// constructors
	public BaseMandrakepkgs () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMandrakepkgs (java.lang.Long _id) {
		this.setId(_id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMandrakepkgs (
		java.lang.Long _id,
		java.lang.String _name,
		java.lang.String _description) {

		this.setId(_id);
		this.setName(_name);
		this.setDescription(_description);
		initialize();
	}

	protected void initialize () {}



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="vm"
     *  column="ID"
     */
	public java.lang.Long getId () {
		return _id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param _id the new ID
	 */
	public void setId (java.lang.Long _id) {
		this._id = _id;
		this.hashCode = Integer.MIN_VALUE;
	}


	/**
	 * Return the value associated with the column: NAME
	 */
	public java.lang.String getName () {
		return _name;
	}

	/**
	 * Set the value related to the column: NAME
	 * @param _name the NAME value
	 */
	public void setName (java.lang.String _name) {
		this._name = _name;
	}


	/**
	 * Return the value associated with the column: DESCRIPTION
	 */
	public java.lang.String getDescription () {
		return _description;
	}

	/**
	 * Set the value related to the column: DESCRIPTION
	 * @param _description the DESCRIPTION value
	 */
	public void setDescription (java.lang.String _description) {
		this._description = _description;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof pfe.migration.server.ejb.bdd.base.BaseMandrakepkgs)) return false;
		else {
			pfe.migration.server.ejb.bdd.base.BaseMandrakepkgs mObj = (pfe.migration.server.ejb.bdd.base.BaseMandrakepkgs) obj;
			if (null == this.getId() || null == mObj.getId()) return false;
			else return (this.getId().equals(mObj.getId()));
		}
	}


	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}

}