package pfe.migration.server.ejb.bdd.base;

import java.io.Serializable;


/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an object that contains data related to the NETWORK_CONFIG table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="NETWORK_CONFIG"
 */
public abstract class BaseNetworkConfig  implements Serializable {

	public static String PROP_NETWORK_DNS_SERVER = "NetworkDnsServer";
	public static String PROP_NETWORK_IP_ADDRESS = "NetworkIpAddress";
	public static String PROP_NETWORK_KEY = "NetworkKey";
	public static String PROP_NETWORK_INTERFACE = "NetworkInterface";
	public static String PROP_NETWORK_SUBNETMASK = "NetworkSubnetmask";
	public static String PROP_NETWORK_STATUS = "NetworkStatus";
	public static String PROP_NETWORK_DNS_SERVER2 = "NetworkDnsServer2";
	public static String PROP_NETWORK_GATEWAY = "NetworkGateway";
	public static String PROP_ID = "Id";
	public static String PROP_NETWORK_DHCP_ENABLED = "NetworkDhcpEnabled";


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer _id;

	// fields
	private java.lang.String _networkIpAddress;
	private java.lang.String _networkSubnetmask;
	private java.lang.String _networkInterface;
	private java.lang.String _networkGateway;
	private java.lang.String _networkDnsServer2;
	private java.lang.Byte _networkStatus;
	private java.lang.String _networkDnsServer;
	private java.lang.Byte _networkDhcpEnabled;
	private java.lang.Integer _networkKey;


	// constructors
	public BaseNetworkConfig () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseNetworkConfig (java.lang.Integer _id) {
		this.setId(_id);
		initialize();
	}

	protected void initialize () {}



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="vm"
     *  column="NETWORK_ID"
     */
	public java.lang.Integer getId () {
		return _id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param _id the new ID
	 */
	public void setId (java.lang.Integer _id) {
		this._id = _id;
		this.hashCode = Integer.MIN_VALUE;
	}


	/**
	 * Return the value associated with the column: NETWORK_IP_ADDRESS
	 */
	public java.lang.String getNetworkIpAddress () {
		return _networkIpAddress;
	}

	/**
	 * Set the value related to the column: NETWORK_IP_ADDRESS
	 * @param _networkIpAddress the NETWORK_IP_ADDRESS value
	 */
	public void setNetworkIpAddress (java.lang.String _networkIpAddress) {
		this._networkIpAddress = _networkIpAddress;
	}


	/**
	 * Return the value associated with the column: NETWORK_SUBNETMASK
	 */
	public java.lang.String getNetworkSubnetmask () {
		return _networkSubnetmask;
	}

	/**
	 * Set the value related to the column: NETWORK_SUBNETMASK
	 * @param _networkSubnetmask the NETWORK_SUBNETMASK value
	 */
	public void setNetworkSubnetmask (java.lang.String _networkSubnetmask) {
		this._networkSubnetmask = _networkSubnetmask;
	}


	/**
	 * Return the value associated with the column: NETWORK_INTERFACE
	 */
	public java.lang.String getNetworkInterface () {
		return _networkInterface;
	}

	/**
	 * Set the value related to the column: NETWORK_INTERFACE
	 * @param _networkInterface the NETWORK_INTERFACE value
	 */
	public void setNetworkInterface (java.lang.String _networkInterface) {
		this._networkInterface = _networkInterface;
	}


	/**
	 * Return the value associated with the column: NETWORK_GATEWAY
	 */
	public java.lang.String getNetworkGateway () {
		return _networkGateway;
	}

	/**
	 * Set the value related to the column: NETWORK_GATEWAY
	 * @param _networkGateway the NETWORK_GATEWAY value
	 */
	public void setNetworkGateway (java.lang.String _networkGateway) {
		this._networkGateway = _networkGateway;
	}


	/**
	 * Return the value associated with the column: NETWORK_DNS_SERVER2
	 */
	public java.lang.String getNetworkDnsServer2 () {
		return _networkDnsServer2;
	}

	/**
	 * Set the value related to the column: NETWORK_DNS_SERVER2
	 * @param _networkDnsServer2 the NETWORK_DNS_SERVER2 value
	 */
	public void setNetworkDnsServer2 (java.lang.String _networkDnsServer2) {
		this._networkDnsServer2 = _networkDnsServer2;
	}


	/**
	 * Return the value associated with the column: NETWORK_STATUS
	 */
	public java.lang.Byte getNetworkStatus () {
		return _networkStatus;
	}

	/**
	 * Set the value related to the column: NETWORK_STATUS
	 * @param _networkStatus the NETWORK_STATUS value
	 */
	public void setNetworkStatus (java.lang.Byte _networkStatus) {
		this._networkStatus = _networkStatus;
	}


	/**
	 * Return the value associated with the column: NETWORK_DNS_SERVER
	 */
	public java.lang.String getNetworkDnsServer () {
		return _networkDnsServer;
	}

	/**
	 * Set the value related to the column: NETWORK_DNS_SERVER
	 * @param _networkDnsServer the NETWORK_DNS_SERVER value
	 */
	public void setNetworkDnsServer (java.lang.String _networkDnsServer) {
		this._networkDnsServer = _networkDnsServer;
	}


	/**
	 * Return the value associated with the column: NETWORK_DHCP_ENABLED
	 */
	public java.lang.Byte getNetworkDhcpEnabled () {
		return _networkDhcpEnabled;
	}

	/**
	 * Set the value related to the column: NETWORK_DHCP_ENABLED
	 * @param _networkDhcpEnabled the NETWORK_DHCP_ENABLED value
	 */
	public void setNetworkDhcpEnabled (java.lang.Byte _networkDhcpEnabled) {
		this._networkDhcpEnabled = _networkDhcpEnabled;
	}


	/**
	 * Return the value associated with the column: NETWORK_KEY
	 */
	public java.lang.Integer getNetworkKey () {
		return _networkKey;
	}

	/**
	 * Set the value related to the column: NETWORK_KEY
	 * @param _networkKey the NETWORK_KEY value
	 */
	public void setNetworkKey (java.lang.Integer _networkKey) {
		this._networkKey = _networkKey;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof pfe.migration.server.ejb.bdd.base.BaseNetworkConfig)) return false;
		else {
			pfe.migration.server.ejb.bdd.base.BaseNetworkConfig mObj = (pfe.migration.server.ejb.bdd.base.BaseNetworkConfig) obj;
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