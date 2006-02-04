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
import pfe.migration.client.network.ComputerInformation;
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
	
	/** 
	 * @value ListOfFiles contain the selected file witch will be saved on the storage server
	 * @param @arg1 hostname , @arg2 listOfFiles
	 * @author Dupix
	 */
	private Map ListOfFiles = new HashMap(); 
	
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
		this.ListOfFiles.put(ci.getHostname(), null);
	}
	
	public void putFileList(String hostname, List files)
	{
		this.ListOfFiles.put(hostname, files);
	}

	public List getFileList(String hostname)
	{
		// si c est null c est que la list n est aps encore remplit
		return (List) this.ListOfFiles.get(hostname);
	}
	
	public void removeFileOfTheList(String hostname)
	{
		this.ListOfFiles.remove(hostname);
	}
	
	public ComputerInformation getCi(String Hostname)
	{
		return (ComputerInformation)cil.get(Hostname);
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

}