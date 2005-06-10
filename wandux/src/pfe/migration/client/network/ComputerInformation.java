/*
 * Created on 15 mars 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.network;

import java.io.Serializable;

import pfe.migration.server.ejb.bdd.GlobalConf;
import pfe.migration.server.ejb.bdd.NetworkConfig;
import pfe.migration.server.ejb.bdd.ParamIe;
import pfe.migration.server.ejb.bdd.UsersData;



/**
 * @author dup
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ComputerInformation implements Serializable
{
	private String mac = "";

	private String ip = "";

//	 private InformationUser infou = null;
//	 private NetworkDhcpConfig infon = null;
//	 private InformationPrograms infop = null;
//	 private InformationProgram = null; // en attendant
//	 private int infnbprog = 0;

	public GlobalConf gconf = null;
	public UsersData udata = null;
	public NetworkConfig netconf = null;
	public ParamIe ieconf = null;
	
	public ComputerInformation (String ip)
	{
		super();
		this.mac = mac;
		this.ip = ip;
	}
	
	public ComputerInformation ()
	{
		super();
	}

	public void setMac(String mac)
	{
		this.mac = mac;
	}

	public String getMac()
	{
		return this.mac;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String getIp()
	{
		return this.ip;
	}
	
//	public void setInfoUser (InformationUser infou)
//	{
//		this.infou = infou;
//	}
//
//	public InformationUser getInfoUser()
//	{
//		return this.infou;
//	}
	
	public void setInfoNetwork (NetworkConfig infon)
	{
		this.netconf = infon;
	}

	public NetworkConfig getInfoNetwork()
	{
		return this.netconf;
	}
	
//	public void setInfoPrograms (InformationPrograms infop)
//	{
//		this.infop = infop;
//	}
//
//	public InformationPrograms getInfoPrograms()
//	{
//		return this.infop;
//	}

//	public void incProgressionProg(int lasttotal)
//	{
//		
//	}
}
