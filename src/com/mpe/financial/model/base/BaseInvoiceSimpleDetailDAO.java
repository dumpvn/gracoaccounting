package com.mpe.financial.model.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import com.mpe.financial.model.dao.InvoiceSimpleDetailDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseInvoiceSimpleDetailDAO extends com.mpe.financial.model.dao._RootDAO {

	// query name references


	public static InvoiceSimpleDetailDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static InvoiceSimpleDetailDAO getInstance () {
		if (null == instance) instance = new InvoiceSimpleDetailDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.mpe.financial.model.InvoiceSimpleDetail.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a com.mpe.financial.model.InvoiceSimpleDetail
	 */
	public com.mpe.financial.model.InvoiceSimpleDetail cast (Object object) {
		return (com.mpe.financial.model.InvoiceSimpleDetail) object;
	}

	public com.mpe.financial.model.InvoiceSimpleDetail get(com.mpe.financial.model.InvoiceSimpleDetailPK key)
	{
		return (com.mpe.financial.model.InvoiceSimpleDetail) get(getReferenceClass(), key);
	}

	public com.mpe.financial.model.InvoiceSimpleDetail get(com.mpe.financial.model.InvoiceSimpleDetailPK key, Session s)
	{
		return (com.mpe.financial.model.InvoiceSimpleDetail) get(getReferenceClass(), key, s);
	}

	public com.mpe.financial.model.InvoiceSimpleDetail load(com.mpe.financial.model.InvoiceSimpleDetailPK key)
	{
		return (com.mpe.financial.model.InvoiceSimpleDetail) load(getReferenceClass(), key);
	}

	public com.mpe.financial.model.InvoiceSimpleDetail load(com.mpe.financial.model.InvoiceSimpleDetailPK key, Session s)
	{
		return (com.mpe.financial.model.InvoiceSimpleDetail) load(getReferenceClass(), key, s);
	}

	public com.mpe.financial.model.InvoiceSimpleDetail loadInitialize(com.mpe.financial.model.InvoiceSimpleDetailPK key, Session s) 
	{ 
		com.mpe.financial.model.InvoiceSimpleDetail obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param invoiceSimpleDetail a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.mpe.financial.model.InvoiceSimpleDetailPK save(com.mpe.financial.model.InvoiceSimpleDetail invoiceSimpleDetail)
	{
		return (com.mpe.financial.model.InvoiceSimpleDetailPK) super.save(invoiceSimpleDetail);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param invoiceSimpleDetail a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public com.mpe.financial.model.InvoiceSimpleDetailPK save(com.mpe.financial.model.InvoiceSimpleDetail invoiceSimpleDetail, Session s)
	{
		return (com.mpe.financial.model.InvoiceSimpleDetailPK) save((Object) invoiceSimpleDetail, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param invoiceSimpleDetail a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.mpe.financial.model.InvoiceSimpleDetail invoiceSimpleDetail)
	{
		saveOrUpdate((Object) invoiceSimpleDetail);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param invoiceSimpleDetail a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(com.mpe.financial.model.InvoiceSimpleDetail invoiceSimpleDetail, Session s)
	{
		saveOrUpdate((Object) invoiceSimpleDetail, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param invoiceSimpleDetail a transient instance containing updated state
	 */
	public void update(com.mpe.financial.model.InvoiceSimpleDetail invoiceSimpleDetail) 
	{
		update((Object) invoiceSimpleDetail);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param invoiceSimpleDetail a transient instance containing updated state
	 * @param the Session
	 */
	public void update(com.mpe.financial.model.InvoiceSimpleDetail invoiceSimpleDetail, Session s)
	{
		update((Object) invoiceSimpleDetail, s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.mpe.financial.model.InvoiceSimpleDetailPK id)
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
	public void delete(com.mpe.financial.model.InvoiceSimpleDetailPK id, Session s)
	{
		delete((Object) load(id, s), s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param invoiceSimpleDetail the instance to be removed
	 */
	public void delete(com.mpe.financial.model.InvoiceSimpleDetail invoiceSimpleDetail)
	{
		delete((Object) invoiceSimpleDetail);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param invoiceSimpleDetail the instance to be removed
	 * @param s the Session
	 */
	public void delete(com.mpe.financial.model.InvoiceSimpleDetail invoiceSimpleDetail, Session s)
	{
		delete((Object) invoiceSimpleDetail, s);
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
	public void refresh (com.mpe.financial.model.InvoiceSimpleDetail invoiceSimpleDetail, Session s)
	{
		refresh((Object) invoiceSimpleDetail, s);
	}


}