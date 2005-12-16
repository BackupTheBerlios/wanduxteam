package pfe.migration.server.ejb.bdd.base;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.Session;
import pfe.migration.server.ejb.bdd.dao.SofttypesDAO;

/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 *
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseSofttypesDAO extends pfe.migration.server.ejb.bdd.dao._RootDAO {

	public static SofttypesDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static SofttypesDAO getInstance () {
		if (null == instance) instance = new SofttypesDAO();
		return instance;
	}

	/**
	 * pfe.migration.server.ejb.bdd.dao._RootDAO _RootDAO.getReferenceClass()
	 */
	public Class getReferenceClass () {
		return pfe.migration.server.ejb.bdd.Softtypes.class;
	}
	
	public pfe.migration.server.ejb.bdd.Softtypes load(java.lang.Byte key)
		throws net.sf.hibernate.HibernateException {
		return (pfe.migration.server.ejb.bdd.Softtypes) load(getReferenceClass(), key);
	}

	public pfe.migration.server.ejb.bdd.Softtypes load(java.lang.Byte key, Session s)
		throws net.sf.hibernate.HibernateException {
		return (pfe.migration.server.ejb.bdd.Softtypes) load(getReferenceClass(), key, s);
	}

	public pfe.migration.server.ejb.bdd.Softtypes loadInitialize(java.lang.Byte key, Session s) 
			throws net.sf.hibernate.HibernateException { 
		pfe.migration.server.ejb.bdd.Softtypes obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param softtypes a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Byte save(pfe.migration.server.ejb.bdd.Softtypes softtypes)
		throws net.sf.hibernate.HibernateException {
		return (java.lang.Byte) super.save(softtypes);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param softtypes a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.Byte save(pfe.migration.server.ejb.bdd.Softtypes softtypes, Session s)
		throws net.sf.hibernate.HibernateException {
		return (java.lang.Byte) super.save(softtypes, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param softtypes a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(pfe.migration.server.ejb.bdd.Softtypes softtypes)
		throws net.sf.hibernate.HibernateException {
		super.saveOrUpdate(softtypes);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param softtypes a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(pfe.migration.server.ejb.bdd.Softtypes softtypes, Session s)
		throws net.sf.hibernate.HibernateException {
		super.saveOrUpdate(softtypes, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param softtypes a transient instance containing updated state
	 */
	public void update(pfe.migration.server.ejb.bdd.Softtypes softtypes) 
		throws net.sf.hibernate.HibernateException {
		super.update(softtypes);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param softtypes a transient instance containing updated state
	 * @param the Session
	 */
	public void update(pfe.migration.server.ejb.bdd.Softtypes softtypes, Session s)
		throws net.sf.hibernate.HibernateException {
		super.update(softtypes, s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Byte id)
		throws net.sf.hibernate.HibernateException {
		super.delete(load(id));
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param id the instance ID to be removed
	 * @param s the Session
	 */
	public void delete(java.lang.Byte id, Session s)
		throws net.sf.hibernate.HibernateException {
		super.delete(load(id, s), s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param softtypes the instance to be removed
	 */
	public void delete(pfe.migration.server.ejb.bdd.Softtypes softtypes)
		throws net.sf.hibernate.HibernateException {
		super.delete(softtypes);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param softtypes the instance to be removed
	 * @param s the Session
	 */
	public void delete(pfe.migration.server.ejb.bdd.Softtypes softtypes, Session s)
		throws net.sf.hibernate.HibernateException {
		super.delete(softtypes, s);
	}
	
	/**
	 * Re-read the state of the given instance from the underlying database. It is inadvisable to use this to implement
	 * long-running sessions that span many business tasks. This method is, however, useful in certain special circumstances.
	 * For example 
	 * <ul> 
	 * <li>where a database trigger alters the object state upon insert or update</li>
	 * <li>after executing direct SQL (eg. a mass update) in the same session</li>
	 * <li>after inserting a Blob or Clob</li>
	 * </ul>
	 */
	public void refresh (pfe.migration.server.ejb.bdd.Softtypes softtypes, Session s)
		throws net.sf.hibernate.HibernateException {
		super.refresh(softtypes, s);
	}

    public String getDefaultOrderProperty () {
		return "Name";
    }

}