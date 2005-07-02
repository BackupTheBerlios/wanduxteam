/*
 * Created on 26 févr. 2005
 */
package pfe.migration.server.ejb;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.server.ejb.adll.ExecAdll;
import pfe.migration.server.ejb.bdd.GlobalConf;
import pfe.migration.server.ejb.bdd.HibernateUtil;
import pfe.migration.server.ejb.bdd.NetworkConfig;
import pfe.migration.server.ejb.bdd.ParamIe;
import pfe.migration.server.ejb.bdd.UsersData;
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
	//	XmlAdllParse xml = new XmlAdllParse();
//		System.out.println("ip pouet " + ci.netconf.getNetworkIpAddress());
//		System.out.println("ip pouet " + ci.getIp());
	//	ExecAdll ea = new ExecAdll(ci.getIp()); // TODO mettre l adresse mac quand ce sera bon
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


	public List getLangInformation()
	{
		List l = null;
		Session session;
		 try {
			session = HibernateUtil.currentSession();
			l = session.find(" from LangInfo ");
			HibernateUtil.closeSession();
		} catch (HibernateException e) { e.printStackTrace(); }
		return l;
	}

	public ComputerInformation getComputerInformation(String macaddr) throws RemoteException, HibernateException
	{
		List l = null;
		Iterator i = null;
		Integer user_id = null;
		Session session;
		ComputerInformation ci = new ComputerInformation();
		
		session = HibernateUtil.currentSession();

		ci.gconf = new GlobalConf();
		l = session.find(" from GlobalConf ");
		i = l.iterator();
		while (i.hasNext())
		{
			GlobalConf gconf = (GlobalConf)i.next();
			if (gconf != null)
				if (gconf.getGlobalKey() != null)
					if (gconf.getGlobalKey().equals(new Integer(macaddr.hashCode())))
			{
				ci.gconf.setGlobalDomainName(gconf.getGlobalDomainName());
				ci.gconf.setGlobalHostname(gconf.getGlobalHostname());
				ci.gconf.setGlobalKey(gconf.getGlobalKey());
				break;
			}
		}
		
		
		ci.netconf = new NetworkConfig();
		l = session.find(" from NetworkConfig ");
		i = l.iterator();
		while (i.hasNext())
		{
			NetworkConfig netconf = (NetworkConfig)i.next();
			if (netconf != null)
				if (netconf.getNetworkKey() != null)
					if (netconf.getNetworkKey().equals(new Integer(macaddr.hashCode())))
			{
				ci.netconf.setId(netconf.getId());
				ci.netconf.setNetworkDhcpEnabled(netconf.getNetworkDhcpEnabled());
				ci.netconf.setNetworkDnsServer(netconf.getNetworkDnsServer());
				ci.netconf.setNetworkDnsServer2(netconf.getNetworkDnsServer2());
				ci.netconf.setNetworkGateway(netconf.getNetworkGateway());
				ci.netconf.setNetworkInterface(netconf.getNetworkInterface());
				ci.netconf.setNetworkIpAddress(netconf.getNetworkIpAddress());
				ci.netconf.setNetworkKey(netconf.getNetworkKey());
				ci.netconf.setNetworkMacAdress(netconf.getNetworkMacAdress());
				ci.netconf.setNetworkStatus(netconf.getNetworkStatus());
				ci.netconf.setNetworkSubnetmask(netconf.getNetworkSubnetmask());
				break;
			}
		}

		
		ci.udata = new UsersData();
		l = session.find(" from UsersData ");
		i = l.iterator();
		while (i.hasNext())
		{
			UsersData udata = (UsersData)i.next();
			if (udata != null)
				if (udata.getUserKey() != null)
					if (udata.getUserKey().equals(new Integer(macaddr.hashCode())))
			{
				user_id = new Integer(udata.getUserLogin().hashCode());
				ci.udata.setUserLogin(udata.getUserLogin());
				ci.udata.setUserBgimg(udata.getUserBgimg());
				ci.udata.setUserHome(udata.getUserHome());
				ci.udata.setUserKey(udata.getUserKey());
				ci.udata.setUserPass(udata.getUserPass());
				ci.udata.setUserProxyOverride(udata.getUserProxyOverride());
				ci.udata.setUserProxyServ(udata.getUserProxyServ());
				ci.udata.setUserType(udata.getUserType());
				break;
			}
		}
		

		ci.ieconf = new ParamIe();
		l = session.find(" from ParamIe ");
		i = l.iterator();
		while (i.hasNext())
		{
			ParamIe ieconf = (ParamIe)i.next();
			if (ieconf != null)
				if (ieconf.getIeParamUserId() != null)
					if (ieconf.getIeParamUserId().equals(user_id))
			{
				ci.ieconf.setId(ieconf.getId());
				ci.ieconf.setIeParamSaveDirectory(ieconf.getIeParamSaveDirectory());
				ci.ieconf.setIeParamUserId(ieconf.getIeParamUserId());
				ci.ieconf.setIeProxyAutoConfigUrl(ieconf.getIeProxyAutoConfigUrl());
				ci.ieconf.setIeProxyOverride(ieconf.getIeProxyOverride());
				ci.ieconf.setIeProxyServer(ieconf.getIeProxyServer());
				break;
			}
		}
		
		HibernateUtil.closeSession();
		return ci;
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