package pfe.migration.server.ejb.bdd.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the SOFTTYPES table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="SOFTTYPES"
 */
public abstract class BaseSofttypes  implements Serializable {

	public static String PROP_TEXTMODE = "Textmode";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_GUIMODE = "Guimode";
	public static String PROP_NAME = "Name";
	public static String PROP_ID = "Id";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Byte _id;

	// fields
	private java.lang.String _name;
	private java.lang.Byte _textmode;
	private java.lang.String _description;
	private java.lang.Byte _guimode;


	// constructors
	public BaseSofttypes () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSofttypes (java.lang.Byte _id) {
		this.setId(_id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseSofttypes (
		java.lang.Byte _id,
		java.lang.String _name,
		java.lang.Byte _textmode,
		java.lang.String _description,
		java.lang.Byte _guimode) {

		this.setId(_id);
		this.setName(_name);
		this.setTextmode(_textmode);
		this.setDescription(_description);
		this.setGuimode(_guimode);
		initialize();
	}

	protected void initialize () {}



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="vm"
     *  column="ID"
     */
	public java.lang.Byte getId () {
		return _id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param _id the new ID
	 */
	public void setId (java.lang.Byte _id) {
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
	 * Return the value associated with the column: TEXTMODE
	 */
	public java.lang.Byte getTextmode () {
		return _textmode;
	}

	/**
	 * Set the value related to the column: TEXTMODE
	 * @param _textmode the TEXTMODE value
	 */
	public void setTextmode (java.lang.Byte _textmode) {
		this._textmode = _textmode;
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


	/**
	 * Return the value associated with the column: GUIMODE
	 */
	public java.lang.Byte getGuimode () {
		return _guimode;
	}

	/**
	 * Set the value related to the column: GUIMODE
	 * @param _guimode the GUIMODE value
	 */
	public void setGuimode (java.lang.Byte _guimode) {
		this._guimode = _guimode;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof pfe.migration.server.ejb.bdd.base.BaseSofttypes)) return false;
		else {
			pfe.migration.server.ejb.bdd.base.BaseSofttypes mObj = (pfe.migration.server.ejb.bdd.base.BaseSofttypes) obj;
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