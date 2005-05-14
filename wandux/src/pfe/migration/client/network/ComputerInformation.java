/*
 * Created on 15 mars 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.network;

import java.io.Serializable;

import pfe.migration.server.ejb.bdd.NetworkDhcpConfig;
import pfe.migration.server.ejb.bdd.UsersData;

/**
 * @author dup
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ComputerInformation implements Serializable {

	private String ip = "";

	// ce dont tu n as pas besoin peut reste a null ...
//	 private InformationUser infou = null;
//	 private NetworkDhcpConfig infon = null;
//	 private InformationPrograms infop = null;
	public UsersData us_d = null;
	public NetworkDhcpConfig ndhcp = null;
 // private InformationProgram = null; // en attendant
//	 private int infnbprog = 0;
	

	
	public ComputerInformation (String ip)
	{
		super();
		this.ip = ip;
	}
	
	public ComputerInformation ()
	{
		super();
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
	
	public void setInfoNetwork (NetworkDhcpConfig infon)
	{
		this.ndhcp = infon;
	}

	public NetworkDhcpConfig getInfoNetwork()
	{
		return this.ndhcp;
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
