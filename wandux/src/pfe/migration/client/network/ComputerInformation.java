/*
 * Created on 15 mars 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.client.network;

import java.io.Serializable;

import pfe.migration.client.pre.system.FileSystemModel;
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
//	 private InformationUser infou = null;
//	 private NetworkDhcpConfig infon = null;
//	 private InformationPrograms infop = null;
//	 private InformationProgram = null; // en attendant
//	 private int infnbprog = 0;

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	private GlobalConf gconf = null;
//	private UsersData udata = null;
//	private ParamIe ieconf = null;
	
	private NetworkConfig netconf [] = null;
	private FileSystemModel fsm [] = null;
	
	private String hostname = ""; 
	
	public ComputerInformation ()
	{
		super();
		this.gconf = new GlobalConf();
	}

	public String getHostname()
	{
		return hostname; //this.local_ip;
	}
	
	public void setInfoNetwork (NetworkConfig infon [])
	{
		this.netconf = infon;
	}

	public NetworkConfig [] getInfoNetwork()
	{
		return this.netconf;
	}
	
	public void setFileSystemModel(FileSystemModel [] mfsm)
	{
		this.fsm = mfsm; // verifier si 
	}
	
	public FileSystemModel [] getFileSystemModel()
	{
		return this.fsm;
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
