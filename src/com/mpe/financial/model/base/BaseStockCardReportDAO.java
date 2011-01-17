package com.mpe.financial.model.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import com.mpe.financial.model.dao.StockCardReportDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseStockCardReportDAO extends com.mpe.financial.model.dao._RootDAO {

	// query name references


	public static StockCardReportDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static StockCardReportDAO getInstance () {
		if (null == instance) instance = new StockCardReportDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.mpe.financial.model.StockCardReport.class;
	}

    public Order getDefaultOrder () {
		return Order.asc("Name");
    }

	/**
	 * Cast the object as a com.mpe.financial.model.StockCardReport
	 */
	public com.mpe.financial.model.StockCardReport cast (Object object) {
		return (com.mpe.financial.model.StockCardReport) object;
	}

	public com.mpe.financial.model.StockCardReport get(java.lang.String key)
	{
		return (com.mpe.financial.model.StockCardReport) get(getReferenceClass(), key);
	}

	public com.mpe.financial.model.StockCardReport get(java.lang.String key, Session s)
	{
		return (com.mpe.financial.model.StockCardReport) get(getReferenceClass(), key, s);
	}

	public com.mpe.financial.model.StockCardReport load(java.lang.String key)
	{
		return (com.mpe.financial.model.StockCardReport) load(getReferenceClass(), key);
	}

	public com.mpe.financial.model.StockCardReport load(java.lang.String key, Session s)
	{
		return (com.mpe.financial.model.StockCardReport) load(getReferenceClass(), key, s);
	}

	public com.mpe.financial.model.StockCardReport loadInitialize(java.lang.String key, Session s) 
	{ 
		com.mpe.financial.model.StockCardReport obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param stockCardReport a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(com.mpe.financial.model.StockCardReport stockCardReport)
	{
		return (java.lang.String) super.save(stockCardReport);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param stockCardReport a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.String save(com.mpe.financial.model.StockCardReport stockCardReport, Session s)
	{
		return (java.lang.String) save((Object) stockCardReport, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param stockCardReport a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.mpe.financial.model.StockCardReport stockCardReport)
	{
		saveOrUpdate((Object) stockCardReport);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param stockCardReport a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(com.mpe.financial.model.StockCardReport stockCardReport, Session s)
	{
		saveOrUpdate((Object) stockCardReport, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param stockCardReport a transient instance containing updated state
	 */
	public void update(com.mpe.financial.model.StockCardReport stockCardReport) 
	{
		update((Object) stockCardReport);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param stockCardReport a transient instance containing updated state
	 * @param the Session
	 */
	public void update(com.mpe.financial.model.StockCardReport stockCardReport, Session s)
	{
		update((Object) stockCardReport, s);
	}


	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param stockCardReport the instance to be removed
	 */
	public void delete(com.mpe.financial.model.StockCardReport stockCardReport)
	{
		delete((Object) stockCardReport);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param stockCardReport the instance to be removed
	 * @param s the Session
	 */
	public void delete(com.mpe.financial.model.StockCardReport stockCardReport, Session s)
	{
		delete((Object) stockCardReport, s);
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
	public void refresh (com.mpe.financial.model.StockCardReport stockCardReport, Session s)
	{
		refresh((Object) stockCardReport, s);
	}


}