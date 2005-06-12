/*
 * Created on 4 juin 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pfe.migration.server.ejb.tool;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import pfe.migration.client.network.ComputerInformation;
import pfe.migration.server.ejb.bdd.HibernateUtil;

/**
 * @author dup
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SendingData implements Runnable 
{
	Transaction transaction;
	Session session;
	ComputerInformation ci;

	public SendingData(ComputerInformation ci) 
	{
		super();
	  	String ip = ci.getIp();
	  	this.ci = ci;
	}

	public void run()
	{
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
//		System.out.println(ci.ndhcp.getDhcpAdress());
	}
	
	public String getMac()
	{
		return this.mac;
	}

	public String getIp()
	{
		return this.ip;
	}
	
}

// TODO remettre les etapes du monitoring dans la classe ci-dessus 
//public void putComputerInformation(ComputerInformation ci) // String ip, // final  
//{
//	migrationInProgress.add(ip);
//	final ComputerInformation ref = null;
//	new Thread()
//	{
//	  public void run()
//	  {	  	
//		ref.setInfoNetwork(ci.getInfoNetwork());
//	  	String ip = ref.getIp();
	
//  	String ip = ci.getIp();
	//cml.CINewIp(ip);
  	
//  	Transaction transaction;
//	Session session;
//	try {
//		session = HibernateUtil.currentSession();
//		transaction = session.beginTransaction();
//		session.save(ci.gconf);
//		session.save(ci.ndhcp);
//		session.save(ci.udata);
//		transaction.commit();
//		HibernateUtil.closeSession();
//	} catch (HibernateException e) {
//		e.printStackTrace();
//	}
//	System.out.println(ci.ndhcp.getDhcpAdress());
		
//TODO signaler au module web chaque etape
//	  	// step1
//	  	ref.setInfoUser(ci.getInfoUser());
//	  	changeCmlStep(ip, 1, 0);
//		// step2
//	  	ref.setInfoPrograms(ci.getInfoPrograms());
//	  	changeCmlStep(ip, 2, 0);
//		// step3
//	  	changeCmlStep(ip, 3, 0);
//		computerList.add(ci);
//	  }
//	}.start();
//}

