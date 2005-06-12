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
import pfe.migration.server.ejb.tool.XmlParse;
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
	
	
	String AdllXmlFileName = "";
	
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
	
	
	public void createAdllXmlFile(ComputerInformation ci)
	{
		XmlParse xmlFile = new XmlParse(ci);
		
		
	}
	
	public void putComputerInformation(ComputerInformation ci)
	{
		String ip = ci.getIp();

		Transaction transaction;
		Session session;
		try {
			session = HibernateUtil.currentSession();
			transaction = session.beginTransaction();
			session.save(ci.gconf);
			session.save(ci.netconf);
			session.save(ci.udata);
			session.save(ci.ieconf);
			transaction.commit();
			HibernateUtil.closeSession();
		} catch (HibernateException e) {
			e.printStackTrace();
		}

//		exemple d'utilisation des threads // TODO a enleve prochainement
//		WorkQueue wt = new WorkQueue(10);
//		wt.execute(new SendingData(ci));
		
		// c est pas pour recuppere le nom du fichier
		createAdllXmlFile (ci); // a enleve pour que ca puisse etre gere depuis le monitoring
//		System.out.println(ci.ndhcp.getDhcpAdress());
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