package com.mpe.financial.model.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import com.mpe.financial.model.dao.ReceivingDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseReceivingDAO extends com.mpe.financial.model.dao._RootDAO {

	// query name references


	public static ReceivingDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static ReceivingDAO getInstance () {
		if (null == instance) instance = new ReceivingDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.mpe.financial.model.Receiving.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a com.mpe.financial.model.Receiving
	 */
	public com.mpe.financial.model.Receiving cast (Object object) {
		return (com.mpe.financial.model.Receiving) object;
	}

	public com.mpe.financial.model.Receiving get(long key)
	{
		return (com.mpe.financial.model.Receiving) get(getReferenceClass(), new java.lang.Long(key));
	}

	public com.mpe.financial.model.Receiving get(long key, Session s)
	{
		return (com.mpe.financial.model.Receiving) get(getReferenceClass(), new java.lang.Long(key), s);
	}

	public com.mpe.financial.model.Receiving load(long key)
	{
		return (com.mpe.financial.model.Receiving) load(getReferenceClass(), new java.lang.Long(key));
	}

	public com.mpe.financial.model.Receiving load(long key, Session s)
	{
		return (com.mpe.financial.model.Receiving) load(getReferenceClass(), new java.lang.Long(key), s);
	}

	public com.mpe.financial.model.Receiving loadInitialize(long key, Session s) 
	{ 
		com.mpe.financial.model.Receiving obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param receiving a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Long save(com.mpe.financial.model.Receiving receiving)
	{
		return (java.lang.Long) super.save(receiving);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param receiving a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.Long save(com.mpe.financial.model.Receiving receiving, Session s)
	{
		return (java.lang.Long) save((Object) receiving, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param receiving a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.mpe.financial.model.Receiving receiving)
	{
		saveOrUpdate((Object) receiving);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param receiving a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(com.mpe.financial.model.Receiving receiving, Session s)
	{
		saveOrUpdate((Object) receiving, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param receiving a transient instance containing updated state
	 */
	public void update(com.mpe.financial.model.Receiving receiving) 
	{
		update((Object) receiving);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param receiving a transient instance containing updated state
	 * @param the Session
	 */
	public void update(com.mpe.financial.model.Receiving receiving, Session s)
	{
		update((Object) receiving, s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(long id)
	{
		delete((Object) load(id));
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param id the instance ID to be removed
	 * @param s the Session
	 */
	public void delete(long id, Session s)
	{
		delete((Object) load(id, s), s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param receiving the instance to be removed
	 */
	public void delete(com.mpe.financial.model.Receiving receiving)
	{
		delete((Object) receiving);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param receiving the instance to be removed
	 * @param s the Session
	 */
	public void delete(com.mpe.financial.model.Receiving receiving, Session s)
	{
		delete((Object) receiving, s);
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
	public void refresh (com.mpe.financial.model.Receiving receiving, Session s)
	{
		refresh((Object) receiving, s);
	}


}