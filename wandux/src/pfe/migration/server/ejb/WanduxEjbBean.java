/*
 * Created on 26 févr. 2005
 */
package pfe.migration.server.ejb;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.swing.tree.DefaultTreeModel;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.client.pre.service.WanduxAppSvr;
import pfe.migration.server.ejb.bdd.HibernateUtil;
import pfe.migration.server.ejb.bdd.Linuxcomponents;
import pfe.migration.server.ejb.bdd.Windowscomponents;
import pfe.migration.server.ejb.tool.CopyBookmark;

/**
 * @ejb.bean name="ServerEjb"
 *           description="Description for ServerEjb"
 *           jndi-name="ejb/ServerEjb"
 *           type="Stateless"
 *           view-type="remote"
 */
public class WanduxEjbBean implements SessionBean
{

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	public static CiList cil = new CiList();

//	String AdllXmlFileName = "";
	
	private Map servers = new HashMap();
	
	// -- ejb ------------------------------------------------------------------------------------ //
	public WanduxEjbBean()
	{
		super();
	}

	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException { }

	public void ejbRemove() throws EJBException, RemoteException
	{
		System.out.println("connection removed!!");
	}

	public void ejbActivate() throws EJBException, RemoteException { }

	public void ejbPassivate() throws EJBException, RemoteException { }

	public void ejbCreate() { }
	
	// -- remote methode ------------------------------------------------------------------------- //
	public String sayMe()
	{
		return "koukouk";
	}
	
	public ArrayList getLinuxEquivalents(String WinSoft)
	{
		Session session;
		Byte subcategory=null;
		ArrayList lequivs = new ArrayList();
		
		try {
			session = HibernateUtil.currentSession();

	        List l = session.createSQLQuery(
	        	    "SELECT {wc.*} FROM WINDOWSCOMPONENTS {wc} where name=\""+WinSoft+"\"",
	        	    "wc",
	        	    Windowscomponents.class
	        	).list();	        
	        Iterator i = l.iterator();
			while (i.hasNext())
			{
				Windowscomponents item = (Windowscomponents)i.next();
				subcategory=item.getSubcategory();
			}

			//System.out.println(subcategory);

	        List equivs = session.createSQLQuery(
	        	    "SELECT {lc.*} FROM LINUXCOMPONENTS {lc} where subcategory="+subcategory, 
	        	    "lc",
	        	    Linuxcomponents.class
	        	).list();
	        i = equivs.iterator();
			while (i.hasNext())
			{
				Linuxcomponents lxc = (Linuxcomponents)i.next();
				//System.out.println(lxc.getId());
//				System.out.println(lxc.getName());
//				System.out.println(lxc.getUrl());
				lequivs.add(lxc.getName());
				lequivs.add(lxc.getUrl());
			}
	        HibernateUtil.closeSession();
	        return lequivs;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lequivs;
	}
	
	// -- taches internes ------------------------------------------------------- //
	public void createAdllXmlFile(ComputerInformation ci)
	{
//		XmlAdllParse xml = new XmlAdllParse(ci.getMac(), ci);
//		ExecAdll ea = new ExecAdll(ci.getMac());
	}
	
	public void createXmlBookmark(ComputerInformation ci)
	{
		//CopyBookmark cb = 
		new CopyBookmark(ci);
	}
	
	// -- client normal ------------------------------------------------------- //
	public void putHostname(String hostname)
	{ // TODO savoir si c est utile ou si ce st juste pour les tests ... voir si c est a enlever ...
		cil.add(hostname);
	}
	
	public List getIps()
	{
		return cil.getListHostname();
	}
	
	public void putCi(ComputerInformation ci)
	{
		cil.fill(ci);
	}

	public ComputerInformation getCi(String Hostname)
	{
		return (ComputerInformation)cil.get(Hostname);
	}

	public void putReference(String hostname, WanduxAppSvr was)
	{
		this.servers.put(hostname, was);	
	}

	public WanduxAppSvr getReference(String hostname)
	{
		return (WanduxAppSvr) this.servers.get(hostname);	
	}
	
	public void putCiDataList(String hostname, DefaultTreeModel dtm)
	{
		System.out.println(dtm.toString());
		ComputerInformation ci = cil.get(hostname);
		ci.setFileSystemModel(dtm);
		cil.fill(ci);
		try {
			this.getReference(hostname).setSelectedFileList(dtm);
//			this.getReference(hostname).setSelectedFileList(dtm);
		} catch (RemoteException e) { e.printStackTrace(); }
	}

//	public void putComputerInformation(ComputerInformation ci)
//	{ // TODO vire cette merde
//		String ip = ci.getIp();
//
////		useCiList();
////		cil.add(ip);
//		
//		Transaction transaction;
//		Session session;
//		try {
//			session = HibernateUtil.currentSession();
//			transaction = session.beginTransaction();
//			session.save(ci.gconf);
//			session.save(ci.netconf);
//			session.save(ci.udata);
//			session.save(ci.ieconf);
//			transaction.commit();
//			HibernateUtil.closeSession();
//		} catch (HibernateException e) {
//			e.printStackTrace();
//		}
//		
////		exemple d'utilisation des threads // TODO a enleve prochainement
////		WorkQueue wt = new WorkQueue(10);
////		wt.execute(new SendingData(ci));
//
//		// c est pas pour recuppere le nom du fichier
//		createAdllXmlFile (ci); // a enleve pour que ca puisse etre gere depuis le monitoring
//		createXmlBookmark (ci);
//	}

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
	
	
// TODO vire cette merde ... y en a qui se crac quand meme
//	public ComputerInformation getComputerInformation(String macaddr) throws RemoteException, HibernateException
//	{
//		List l = null;
//		Iterator i = null;
//		Integer user_id = null;
//		Session session;
//		ComputerInformation ci = new ComputerInformation();
//		
//		session = HibernateUtil.currentSession();
//
//		ci.gconf = new GlobalConf();
//		l = session.find(" from GlobalConf ");
//		i = l.iterator();
//		while (i.hasNext())
//		{
//			GlobalConf gconf = (GlobalConf)i.next();
//			if (gconf != null)
//				if (gconf.getGlobalKey() != null)
//					if (gconf.getGlobalKey().equals(new Integer(macaddr.hashCode())))
//			{
//				ci.gconf.setGlobalDomainName(gconf.getGlobalDomainName());
//				ci.gconf.setGlobalHostname(gconf.getGlobalHostname());
//				ci.gconf.setGlobalKey(gconf.getGlobalKey());
//				break;
//			}
//		}
//		
//		
//		ci.netconf = new NetworkConfig();
//		l = session.find(" from NetworkConfig ");
//		i = l.iterator();
//		while (i.hasNext())
//		{
//			NetworkConfig netconf = (NetworkConfig)i.next();
//			if (netconf != null)
//				if (netconf.getNetworkKey() != null)
//					if (netconf.getNetworkKey().equals(new Integer(macaddr.hashCode())))
//			{
//				ci.netconf.setId(netconf.getId());
//				ci.netconf.setNetworkDhcpEnabled(netconf.getNetworkDhcpEnabled());
//				ci.netconf.setNetworkDnsServer(netconf.getNetworkDnsServer());
//				ci.netconf.setNetworkDnsServer2(netconf.getNetworkDnsServer2());
//				ci.netconf.setNetworkGateway(netconf.getNetworkGateway());
//				ci.netconf.setNetworkInterface(netconf.getNetworkInterface());
//				ci.netconf.setNetworkIpAddress(netconf.getNetworkIpAddress());
//				ci.netconf.setNetworkKey(netconf.getNetworkKey());
//				ci.netconf.setNetworkMacAdress(netconf.getNetworkMacAdress());
//				ci.netconf.setNetworkStatus(netconf.getNetworkStatus());
//				ci.netconf.setNetworkSubnetmask(netconf.getNetworkSubnetmask());
//				break;
//			}
//		}
//
//		
//		ci.udata = new UsersData();
//		l = session.find(" from UsersData ");
//		i = l.iterator();
//		while (i.hasNext())
//		{
//			UsersData udata = (UsersData)i.next();
//			if (udata != null)
//				if (udata.getUserKey() != null)
//					if (udata.getUserKey().equals(new Integer(macaddr.hashCode())))
//			{
//				user_id = new Integer(udata.getUserLogin().hashCode());
//				ci.udata.setUserLogin(udata.getUserLogin());
//				ci.udata.setUserBgimg(udata.getUserBgimg());
//				ci.udata.setUserHome(udata.getUserHome());
//				ci.udata.setUserKey(udata.getUserKey());
//				ci.udata.setUserPass(udata.getUserPass());
//				ci.udata.setUserProxyOverride(udata.getUserProxyOverride());
//				ci.udata.setUserProxyServ(udata.getUserProxyServ());
//				ci.udata.setUserType(udata.getUserType());
//				break;
//			}
//		}
//		
//
//		ci.ieconf = new ParamIe();
//		l = session.find(" from ParamIe ");
//		i = l.iterator();
//		while (i.hasNext())
//		{
//			ParamIe ieconf = (ParamIe)i.next();
//			if (ieconf != null)
//				if (ieconf.getIeParamUserId() != null)
//					if (ieconf.getIeParamUserId().equals(user_id))
//			{
//				ci.ieconf.setId(ieconf.getId());
//				ci.ieconf.setIeParamSaveDirectory(ieconf.getIeParamSaveDirectory());
//				ci.ieconf.setIeParamUserId(ieconf.getIeParamUserId());
//				ci.ieconf.setIeProxyAutoConfigUrl(ieconf.getIeProxyAutoConfigUrl());
//				ci.ieconf.setIeProxyOverride(ieconf.getIeProxyOverride());
//				ci.ieconf.setIeProxyServer(ieconf.getIeProxyServer());
//				break;
//			}
//		}
//		
//		HibernateUtil.closeSession();
//		return ci;
//	}

	// -- monitoring ----------------------------------------------------------------------------- //
//	private void useCiList ()
//	{
//		if (WanduxEjbBean.cil == null)
//			WanduxEjbBean.cil = new CiList();
//	}
//	
//	public CiList getCiList ()
//	{
//		if (WanduxEjbBean.cil == null)
//		{
//			System.out.println("WanduxEjbBean.cil = null");
//			WanduxEjbBean.cil = new CiList();
//		}
//		else
//			System.out.println("WanduxEjbBean.cil = not null");
//		return WanduxEjbBean.cil;
//	}
}