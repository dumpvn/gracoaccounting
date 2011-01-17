package com.mpe.financial.model.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import com.mpe.financial.model.dao.PurchaseRequestDetailDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BasePurchaseRequestDetailDAO extends com.mpe.financial.model.dao._RootDAO {

	// query name references


	public static PurchaseRequestDetailDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static PurchaseRequestDetailDAO getInstance () {
		if (null == instance) instance = new PurchaseRequestDetailDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.mpe.financial.model.PurchaseRequestDetail.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a com.mpe.financial.model.PurchaseRequestDetail
	 */
	public com.mpe.financial.model.PurchaseRequestDetail cast (Object object) {
		return (com.mpe.financial.model.PurchaseRequestDetail) object;
	}

	public com.mpe.financial.model.PurchaseRequestDetail get(com.mpe.financial.model.PurchaseRequestDetailPK key)
	{
		return (com.mpe.financial.model.PurchaseRequestDetail) get(getReferenceClass(), key);
	}

	public com.mpe.financial.model.PurchaseRequestDetail get(com.mpe.financial.model.PurchaseRequestDetailPK key, Session s)
	{
		return (com.mpe.financial.model.PurchaseRequestDetail) get(getReferenceClass(), key, s);
	}

	public com.mpe.financial.model.PurchaseRequestDetail load(com.mpe.financial.model.PurchaseRequestDetailPK key)
	{
		return (com.mpe.financial.model.PurchaseRequestDetail) load(getReferenceClass(), key);
	}

	public com.mpe.financial.model.PurchaseRequestDetail load(com.mpe.financial.model.PurchaseRequestDetailPK key, Session s)
	{
		return (com.mpe.financial.model.PurchaseRequestDetail) load(getReferenceClass(), key, s);
	}

	public com.mpe.financial.model.PurchaseRequestDetail loadInitialize(com.mpe.financial.model.PurchaseRequestDetailPK key, Session s) 
	{ 
		com.mpe.financial.model.PurchaseRequestDetail obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param purchaseRequestDetail a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.mpe.financial.model.PurchaseRequestDetailPK save(com.mpe.financial.model.PurchaseRequestDetail purchaseRequestDetail)
	{
		return (com.mpe.financial.model.PurchaseRequestDetailPK) super.save(purchaseRequestDetail);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param purchaseRequestDetail a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public com.mpe.financial.model.PurchaseRequestDetailPK save(com.mpe.financial.model.PurchaseRequestDetail purchaseRequestDetail, Session s)
	{
		return (com.mpe.financial.model.PurchaseRequestDetailPK) save((Object) purchaseRequestDetail, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param purchaseRequestDetail a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.mpe.financial.model.PurchaseRequestDetail purchaseRequestDetail)
	{
		saveOrUpdate((Object) purchaseRequestDetail);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param purchaseRequestDetail a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(com.mpe.financial.model.PurchaseRequestDetail purchaseRequestDetail, Session s)
	{
		saveOrUpdate((Object) purchaseRequestDetail, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param purchaseRequestDetail a transient instance containing updated state
	 */
	public void update(com.mpe.financial.model.PurchaseRequestDetail purchaseRequestDetail) 
	{
		update((Object) purchaseRequestDetail);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param purchaseRequestDetail a transient instance containing updated state
	 * @param the Session
	 */
	public void update(com.mpe.financial.model.PurchaseRequestDetail purchaseRequestDetail, Session s)
	{
		update((Object) purchaseRequestDetail, s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.mpe.financial.model.PurchaseRequestDetailPK id)
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
	public void delete(com.mpe.financial.model.PurchaseRequestDetailPK id, Session s)
	{
		delete((Object) load(id, s), s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param purchaseRequestDetail the instance to be removed
	 */
	public void delete(com.mpe.financial.model.PurchaseRequestDetail purchaseRequestDetail)
	{
		delete((Object) purchaseRequestDetail);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param purchaseRequestDetail the instance to be removed
	 * @param s the Session
	 */
	public void delete(com.mpe.financial.model.PurchaseRequestDetail purchaseRequestDetail, Session s)
	{
		delete((Object) purchaseRequestDetail, s);
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
	public void refresh (com.mpe.financial.model.PurchaseRequestDetail purchaseRequestDetail, Session s)
	{
		refresh((Object) purchaseRequestDetail, s);
	}


}