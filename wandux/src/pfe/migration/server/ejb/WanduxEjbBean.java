/*
 * Created on 26 févr. 2005
 */
package pfe.migration.server.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.server.ejb.bdd.HibernateUtil;
import pfe.migration.server.monitor.ClientMonitorListener;

/**
 * @ejb.bean name="ServerEjb"
 *           display-name="Name for ServerEjb"
 *           description="Description for ServerEjb"
 *           jndi-name="ejb/ServerEjb"
 *           type="Stateless"
 *           view-type="remote"
 */
public class WanduxEjbBean implements SessionBean
{
//	List computerList = new ArrayList(); // ip des machines
//	private ClientMonitor cml = null;
	
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
	
	public void putComputerInformation(ComputerInformation ci) // String ip, // final  
	{
//		migrationInProgress.add(ip);
//		final ComputerInformation ref = null;
//		new Thread()
//		{
//		  public void run()
//		  {	  	
//			ref.setInfoNetwork(ci.getInfoNetwork());
//		  	String ip = ref.getIp();
		
	  	String ip = ci.getIp();
		//cml.CINewIp(ip);
	  	
	  	Transaction transaction;
		Session session;
		try {
			session = HibernateUtil.currentSession();
			transaction = session.beginTransaction();
			session.save(ci.ndhcp);
			transaction.commit();
			HibernateUtil.closeSession();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		System.out.println(ci.ndhcp.getDhcpAdress());
			
// TODO signaler au module web chaque etape
//		  	// step1
//		  	ref.setInfoUser(ci.getInfoUser());
//		  	changeCmlStep(ip, 1, 0);
//			// step2
//		  	ref.setInfoPrograms(ci.getInfoPrograms());
//		  	changeCmlStep(ip, 2, 0);
//			// step3
//		  	changeCmlStep(ip, 3, 0);
//			computerList.add(ci);
//		  }
//		}.start();
	}
	
	public void getClientMonitor(ClientMonitorListener cml)
	{
//		this.cml = (ClientMonitor) cml;
	}

//	public void changeCmlStep (String ip, int step, int percent)
//	{
//		this.cml.CIProgress(ip,step,percent);
//	}

//	public ComputerInformation getComputerInformation()
//	{
//		return ci;
//	}
	
	
	public ComputerInformation getComputerInformation()
	{
		return null;
	}


}