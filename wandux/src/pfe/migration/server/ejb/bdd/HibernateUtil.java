package pfe.migration.server.ejb.bdd;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

/**
 * Created on 4 févr. 2005
 * 
 * @author dup
 */
public class HibernateUtil
{

	private static final SessionFactory sessionFactory;

	static
	{
		try 
		{
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (HibernateException ex)
		{ 
			throw new RuntimeException("Problème de configuration : "
   			+ ex.getMessage(), ex);
		}
	}

	public static final ThreadLocal session = new ThreadLocal();

	public static Session currentSession() throws HibernateException 
	{
		Session s = (Session) session.get();
		if (s == null)
		{
			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
   }
	
	public static void closeSession() throws HibernateException
	{
		Session s = (Session) session.get();
		session.set(null);
		if (s != null)
			s.close();
	}
	
}
