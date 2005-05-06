/*
 * Created on 26 f�vr. 2005
 */
package pfe.migration.server.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import pfe.migration.client.network.ComputerInformation;
import pfe.migration.server.monitor.CiList;
import pfe.migration.server.monitor.ClientMonitor;
import pfe.migration.server.monitor.ClientMonitorListener;

/**
 * @ejb.bean name="ServerEjb"
 *           display-name="Name for ServerEjb"
 *           description="Description for ServerEjb"
 *           jndi-name="ejb/ServerEjb"
 *           type="Stateless"
 *           view-type="remote"
 */
public class WanduxEjbBean implements SessionBean {

//	List computerList = new ArrayList(); // ip des machines
	private ClientMonitor cml = null;
	
	// -- ejb ------------------------------------------------------------------------------------ //
	public WanduxEjbBean()
	{
		super();
	}

	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException { }

	public void ejbRemove() throws EJBException, RemoteException { }

	public void ejbActivate() throws EJBException, RemoteException { }

	public void ejbPassivate() throws EJBException, RemoteException { }

	public void ejbCreate() { }
	
	// -- remote methode ------------------------------------------------------------------------- //
	public String sayMe()
	{
		return "koukouk";
	}
	
	public void putListName(String [] ok)
	{
		System.out.println(ok);
	}
	
	public void putComputerInformation(final ComputerInformation ci) // String ip, 
	{
//		migrationInProgress.add(ip);

		final ComputerInformation ref = null;

		
		new Thread()
		{
		  public void run()
		  {
		  	
			ref.setInfoNetwork(ci.getInfoNetwork());
		  	String ip = ref.getIp();
			cml.CINewIp(ip);
		  	// TODO signaler au module web chaque etape

		  	// step1
		  	ref.setInfoUser(ci.getInfoUser());
		  	changeCmlStep(ip, 1, 0);
			// step2
		  	ref.setInfoPrograms(ci.getInfoPrograms());
		  	changeCmlStep(ip, 2, 0);
			// step3
		  	changeCmlStep(ip, 3, 0);
//			computerList.add(ci);
		  }
		}.start();

	}
	
	public void getClientMonitor(ClientMonitorListener cml)
	{
		this.cml = (ClientMonitor) cml;
	}

	public void changeCmlStep (String ip, int step, int percent)
	{
		this.cml.CIProgress(ip,step,percent);
	}

	
	
	//	public ComputerInformation getComputerInformation()
//	{
//		return ci;
//	}

}