/*
 * Created on 15 mars 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.network;

import java.io.Serializable;

/**
 * @author dup
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ComputerInformation implements Serializable {

	private String ip = "";

	// ce dont tu n as pas besoin peut reste a null ...
	 private InformationUser infou = null;
	 private InformationNetwork infon = null;	
	 private InformationPrograms infop = null;
	
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
	
	public void setInfoUser (InformationUser infou)
	{
		this.infou = infou;
	}

	public InformationUser getInfoUser()
	{
		return this.infou;
	}
	
	public void setInfoNetwork (InformationNetwork infon)
	{
		this.infon = infon;
	}

	public InformationNetwork getInfoNetwork()
	{
		return this.infon;
	}
	
	public void setInfoPrograms (InformationPrograms infop)
	{
		this.infop = infop;
	}

	public InformationPrograms getInfoPrograms()
	{
		return this.infop;
	}
}
