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

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.client.pre.applet.TreeApplet;
import pfe.migration.server.ejb.adll.ExecAdll;
import pfe.migration.server.ejb.bdd.GlobalConf;
import pfe.migration.server.ejb.bdd.HibernateUtil;
import pfe.migration.server.ejb.bdd.Linuxcomponents;
import pfe.migration.server.ejb.bdd.Mandrakepkgs;
import pfe.migration.server.ejb.bdd.NetworkConfig;
import pfe.migration.server.ejb.bdd.UsersData;
import pfe.migration.server.ejb.bdd.Windowscomponents;
import pfe.migration.server.ejb.tool.CopyBookmark;
import pfe.migration.server.ejb.tool.XmlAdllParse;

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
	
	/** 
	 * @value ListOfFiles contain the selected file witch will be saved on the storage server
	 * @param @arg1 hostname , @arg2 listOfFiles
	 * @author Dupix
	 */
	private static Map ListOfFiles = new HashMap(); 
	
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

	/**
	 * Small AI to determine best matching package, from Commercial name
	 * 
	 */
	public Integer GuessMostRelevantPackage(String LinSoft, String CurrentPackage)
	{
		Integer mark = new Integer(0);
		int im = 0;
		System.out.println("_Linsoft: "+LinSoft);
		System.out.println("_Current: "+CurrentPackage);


		if (LinSoft.toLowerCase().equals((CurrentPackage.toLowerCase()))) {
			im += 100;
		}
		else
		{
			if (CurrentPackage.toLowerCase().substring(0, LinSoft.length()).toLowerCase().equals(LinSoft)) {
				im += 20;
			}
			if (CurrentPackage.toLowerCase().substring(CurrentPackage.length() - LinSoft.length(),
													   CurrentPackage.length()).toLowerCase().equals(LinSoft)) {
				im += 20;
			}
			if (CurrentPackage.substring(0, LinSoft.length()).equals(LinSoft)) {
				im += 40;
			}
			if (CurrentPackage.substring(CurrentPackage.length() - LinSoft.length(),
													   CurrentPackage.length()).equals(LinSoft)) {
				im += 40;
			}
			/*
			 * retain package size to evaluate match
			 * (the smallest, the better)
			 */
			im += (20 - (CurrentPackage.length() - LinSoft.length()));
		}
		mark = new Integer(im); 
		return mark;
	}

	public String getLinuxPackageName(String LinSoft)
	{
		Session session = null;
		ArrayList lpkgs = new ArrayList();
		ArrayList lint = new ArrayList();

		try {
	        List l;
	        session = HibernateUtil.currentSession();

	        l = session.createSQLQuery(
				    "SELECT {wc.*} FROM MANDRAKEPKGS {wc} where name like CONVERT( _utf8 '%"
	        			+ LinSoft
	        			+ "%' USING latin1 )",
				    "wc",
				    Mandrakepkgs.class
				).list();
	        Iterator i = l.iterator();
			for (int j = 0; i.hasNext(); j++)
			{
				Mandrakepkgs mp = (Mandrakepkgs)i.next();
				lpkgs.add(mp.getName());
				//lsize.add(new Integer(mp.getName().length()));
				/*
				 * Function that matches best possible choice
				 */
				lint.add(GuessMostRelevantPackage(LinSoft, mp.getName()));
				// TODO CB6 pour le choix du meilleur package, si on a deux
				// packages ayant la meme note, choisir celui dont le nom est
				// le plus court
			}
			Iterator li = lint.iterator();
			for (int k = 0; li.hasNext(); k++)
			{
				System.out.println(k +" "+lpkgs.get(k)+" rate: " + li.next());
			}
			HibernateUtil.closeSession();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
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
		String xmlPath = "/wandux/adll/";
		new XmlAdllParse(xmlPath, ci.getHostname(), ci);
		new ExecAdll(xmlPath, ci.getHostname(), ci.getInfoNetwork()[0].getNetworkMacAdress());
	}
	
	public void createXmlBookmark(ComputerInformation ci)
	{
		//CopyBookmark cb = 
		new CopyBookmark(ci);
	}
	
	// -- client normal ------------------------------------------------------- //
	public void putHostname(String hostname)
	{
		cil.add(hostname);
	}
	
	public List getIps()
	{
		return cil.getListHostname();
	}
	
	public void putCi(ComputerInformation ci)
	{
		if (ci.migrable == 1)
			createAdllXmlFile(ci);
		cil.fill(ci);
		
		if (!ListOfFiles.containsKey(ci.getHostname()))
			ListOfFiles.put(ci.getHostname(), null);
	}
	
	public void putFileList(String hostname, List files)
	{
		ListOfFiles.put(hostname, files);
	}

	public List getFileList(String hostname)
	{
		return (List) ListOfFiles.get(hostname);
	}
	
	public void removeFileOfTheList(String hostname)
	{
		ListOfFiles.remove(hostname);
	}
	
	public ComputerInformation getCi(String Hostname)
	{
		return (ComputerInformation)cil.get(Hostname);
	}
	
	public ComputerInformation getCiDB(String Hostname)
	{
		ComputerInformation ci = new ComputerInformation();
		Transaction transaction;
		Session session;
		int i, count;
		GlobalConf gconf = null;
		UsersData udata = null;
		NetworkConfig netconf = null;
		List l;
		Iterator it;
		try {
			session = HibernateUtil.currentSession();
			transaction = session.beginTransaction();
			
			l = session.find(" from GlobalConf where GLOBAL_HOSTNAME like '" + Hostname + "'");
			it = l.iterator();
			while (it.hasNext())
			{
				gconf = (GlobalConf)it.next();
			}
			ci.gconf.setGlobalHostname(gconf.getGlobalHostname());
			ci.gconf.setGlobalDomainName(gconf.getGlobalDomainName());
			
			
			
			l = session.find(" from UsersData where USER_KEY like '" + gconf.getId() + "'");
			it = l.iterator();
			count = 0;
			while (it.hasNext())
			{
				it.next();
				count++;
			}
			ci.udata = new UsersData[count];
			it = l.iterator();
			i = 0;
			while (it.hasNext())
			{
				udata = (UsersData)it.next();
				ci.udata[i] = udata;
				i++;
			}
			
			
			
			l = session.find(" from NetworkConfig where NETWORK_KEY like '" + gconf.getId() + "'");
			it = l.iterator();
			count = 0;
			while (it.hasNext())
			{
				it.next();
				count++;
			}
			
			ci.netconf = new NetworkConfig[count];
			it = l.iterator();
			i = 0;
			while (it.hasNext())
			{
				netconf = (NetworkConfig)it.next();
				ci.netconf[i] = netconf;
				i++;
			}
			
			
			transaction.commit();
			HibernateUtil.closeSession();
		} catch (HibernateException e) { e.printStackTrace(); }
	
		return (ci);
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
	
	public void migrate(String hostname)
	{
		Transaction transaction;
		Session session;
		int i;
		GlobalConf gconf = null;
		ComputerInformation ci = this.getCi(hostname);
		
		try {
			session = HibernateUtil.currentSession();
			transaction = session.beginTransaction();
			session.save(ci.gconf);
			transaction.commit();

			List l = session.find(" from GlobalConf where GLOBAL_HOSTNAME like '" + ci.gconf.getGlobalHostname() + "'");
			Iterator it = l.iterator();
			while (it.hasNext())
			{
				gconf = (GlobalConf)it.next();
			}
			
			System.out.println("len = " + ci.netconf.length);

			for(i = 0; i < ci.netconf.length; i++)
			{
				if (ci.netconf[i] != null)
				{
					ci.netconf[i].setNetworkKey(gconf.getId());
					session.save(ci.netconf[i]);
				}
			}
			for(i = 0; i < ci.udata.length; i++)
			{
				if (ci.udata[i] != null)
				{
					ci.udata[i].setUserKey(gconf.getId());
					session.save(ci.udata[i]);
				}
			}
			transaction.commit();
			HibernateUtil.closeSession();
			this.putCi(ci); //TODO ce putCI est a virer le jour ou le client post ira chercher ses infos dans la BDD (pas le cas aujourdh'ui il fait un getCi "dans la ram" du serveur)
			//cil.remove(hostname); // a decommenter le jour ou le client post ira chercher ses infos dans la BDD (pas le cas aujourdh'ui il fait un getCi "dans la ram" du serveur)
		} catch (HibernateException e) { e.printStackTrace(); }
		
	}

}