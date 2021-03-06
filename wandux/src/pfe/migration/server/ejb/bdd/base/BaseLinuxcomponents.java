package pfe.migration.server.ejb.bdd.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the LINUXCOMPONENTS table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="LINUXCOMPONENTS"
 */
public abstract class BaseLinuxcomponents  implements Serializable {

	public static String PROP_STDPROTOCOL = "Stdprotocol";
	public static String PROP_TYPE = "Type";
	public static String PROP_WINEXISTS = "Winexists";
	public static String PROP_PROPRIETARY = "Proprietary";
	public static String PROP_MULTIPLATFORM = "Multiplatform";
	public static String PROP_URL = "Url";
	public static String PROP_CATEGORY = "Category";
	public static String PROP_SUBCATEGORY = "Subcategory";
	public static String PROP_NAME = "Name";
	public static String PROP_ID = "Id";
	public static String PROP_RATING = "Rating";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Long _id;

	// fields
	private java.lang.Short _type;
	private java.lang.Byte _multiplatform;
	private java.lang.Byte _proprietary;
	private java.lang.Byte _rating;
	private java.lang.String _url;
	private java.lang.Byte _subcategory;
	private java.lang.String _name;
	private java.lang.Byte _category;
	private java.lang.Byte _stdprotocol;
	private java.lang.Byte _winexists;


	// constructors
	public BaseLinuxcomponents () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseLinuxcomponents (java.lang.Long _id) {
		this.setId(_id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseLinuxcomponents (
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

		this.setId(_id);
		this.setType(_type);
		this.setMultiplatform(_multiplatform);
		this.setProprietary(_proprietary);
		this.setRating(_rating);
		this.setSubcategory(_subcategory);
		this.setName(_name);
		this.setCategory(_category);
		this.setStdprotocol(_stdprotocol);
		this.setWinexists(_winexists);
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
	 * Return the value associated with the column: TYPE
	 */
	public java.lang.Short getType () {
		return _type;
	}

	/**
	 * Set the value related to the column: TYPE
	 * @param _type the TYPE value
	 */
	public void setType (java.lang.Short _type) {
		this._type = _type;
	}


	/**
	 * Return the value associated with the column: MULTIPLATFORM
	 */
	public java.lang.Byte getMultiplatform () {
		return _multiplatform;
	}

	/**
	 * Set the value related to the column: MULTIPLATFORM
	 * @param _multiplatform the MULTIPLATFORM value
	 */
	public void setMultiplatform (java.lang.Byte _multiplatform) {
		this._multiplatform = _multiplatform;
	}


	/**
	 * Return the value associated with the column: PROPRIETARY
	 */
	public java.lang.Byte getProprietary () {
		return _proprietary;
	}

	/**
	 * Set the value related to the column: PROPRIETARY
	 * @param _proprietary the PROPRIETARY value
	 */
	public void setProprietary (java.lang.Byte _proprietary) {
		this._proprietary = _proprietary;
	}


	/**
	 * Return the value associated with the column: RATING
	 */
	public java.lang.Byte getRating () {
		return _rating;
	}

	/**
	 * Set the value related to the column: RATING
	 * @param _rating the RATING value
	 */
	public void setRating (java.lang.Byte _rating) {
		this._rating = _rating;
	}


	/**
	 * Return the value associated with the column: URL
	 */
	public java.lang.String getUrl () {
		return _url;
	}

	/**
	 * Set the value related to the column: URL
	 * @param _url the URL value
	 */
	public void setUrl (java.lang.String _url) {
		this._url = _url;
	}


	/**
	 * Return the value associated with the column: SUBCATEGORY
	 */
	public java.lang.Byte getSubcategory () {
		return _subcategory;
	}

	/**
	 * Set the value related to the column: SUBCATEGORY
	 * @param _subcategory the SUBCATEGORY value
	 */
	public void setSubcategory (java.lang.Byte _subcategory) {
		this._subcategory = _subcategory;
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
	 * Return the value associated with the column: CATEGORY
	 */
	public java.lang.Byte getCategory () {
		return _category;
	}

	/**
	 * Set the value related to the column: CATEGORY
	 * @param _category the CATEGORY value
	 */
	public void setCategory (java.lang.Byte _category) {
		this._category = _category;
	}


	/**
	 * Return the value associated with the column: STDPROTOCOL
	 */
	public java.lang.Byte getStdprotocol () {
		return _stdprotocol;
	}

	/**
	 * Set the value related to the column: STDPROTOCOL
	 * @param _stdprotocol the STDPROTOCOL value
	 */
	public void setStdprotocol (java.lang.Byte _stdprotocol) {
		this._stdprotocol = _stdprotocol;
	}


	/**
	 * Return the value associated with the column: WINEXISTS
	 */
	public java.lang.Byte getWinexists () {
		return _winexists;
	}

	/**
	 * Set the value related to the column: WINEXISTS
	 * @param _winexists the WINEXISTS value
	 */
	public void setWinexists (java.lang.Byte _winexists) {
		this._winexists = _winexists;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof pfe.migration.server.ejb.bdd.base.BaseLinuxcomponents)) return false;
		else {
			pfe.migration.server.ejb.bdd.base.BaseLinuxcomponents mObj = (pfe.migration.server.ejb.bdd.base.BaseLinuxcomponents) obj;
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