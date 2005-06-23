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
import pfe.migration.server.ejb.adll.ExecAdll;
import pfe.migration.server.ejb.bdd.HibernateUtil;
import pfe.migration.server.ejb.tool.XmlAdllParse;
import pfe.migration.server.monitor.CiList;

/**
 * @ejb.bean name="ServerEjb"
 *           description="Description for ServerEjb"
 *           jndi-name="ejb/ServerEjb"
 *           type="Stateless"
 *           view-type="remote"
 */
public class WanduxEjbBean implements SessionBean
{
	public static CiList cil;
	
//	String AdllXmlFileName = "";
	
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
	
//	public void putListName(String [] ok)
//	{
//		System.out.println(ok);
//	}
	
	// -- client normal et taches internes ------------------------------------------------------- //
	public void createAdllXmlFile(ComputerInformation ci)
	{
		XmlAdllParse xml = new XmlAdllParse();
//		System.out.println("ip pouet " + ci.netconf.getNetworkIpAddress());
//		System.out.println("ip pouet " + ci.getIp());
		ExecAdll ea = new ExecAdll(ci.getIp()); // TODO mettre l adresse mac quand ce sera bon
	}
	
	public void putComputerInformation(ComputerInformation ci)
	{
		String ip = ci.getIp();

		useCiList();
		cil.add(ip);

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
	}
	

	public ComputerInformation getComputerInformation()
	{
		return null;
	}

	// -- monitoring ----------------------------------------------------------------------------- //
	private void useCiList ()
	{
		if (WanduxEjbBean.cil == null)
			WanduxEjbBean.cil = new CiList();
	}
	
	public CiList getCiList ()
	{
		if (WanduxEjbBean.cil == null)
		{
			System.out.println("WanduxEjbBean.cil = null");
			WanduxEjbBean.cil = new CiList();
		}
		else
			System.out.println("WanduxEjbBean.cil = not null");
		return WanduxEjbBean.cil;
	}
}