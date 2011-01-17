package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the stock_opname table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="stock_opname"
 */

public abstract class BaseStockOpname  implements Serializable {

	public static String REF = "StockOpname";
	public static String PROP_STOCK_OPNAME_DATE = "StockOpnameDate";
	public static String PROP_NUMBER = "Number";
	public static String PROP_STATUS = "Status";
	public static String PROP_POSTED = "Posted";
	public static String PROP_NOTE = "Note";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseStockOpname () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseStockOpname (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseStockOpname (
		long id,
		com.mpe.financial.model.Warehouse warehouse,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.StockOpnameType stockOpnameType,
		com.mpe.financial.model.Organization organization,
		java.util.Date stockOpnameDate,
		java.lang.String number,
		java.lang.String status) {

		this.setId(id);
		this.setWarehouse(warehouse);
		this.setCurrency(currency);
		this.setStockOpnameType(stockOpnameType);
		this.setOrganization(organization);
		this.setStockOpnameDate(stockOpnameDate);
		this.setNumber(number);
		this.setStatus(status);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date stockOpnameDate;
	private java.lang.String number;
	private java.lang.String status;
	private boolean posted;
	private java.lang.String note;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private int numberOfDigit;

	// one to one
	private com.mpe.financial.model.Journal journal;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Warehouse warehouse;
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.StockOpnameType stockOpnameType;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.Customers customer;

	// collections
	private java.util.Set stockOpnameDetails;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="stock_opname_id"
     */
	public long getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: stock_opname_date
	 */
	public java.util.Date getStockOpnameDate () {
		return stockOpnameDate;
	}

	/**
	 * Set the value related to the column: stock_opname_date
	 * @param stockOpnameDate the stock_opname_date value
	 */
	public void setStockOpnameDate (java.util.Date stockOpnameDate) {
		this.stockOpnameDate = stockOpnameDate;
	}



	/**
	 * Return the value associated with the column: number
	 */
	public java.lang.String getNumber () {
		return number;
	}

	/**
	 * Set the value related to the column: number
	 * @param number the number value
	 */
	public void setNumber (java.lang.String number) {
		this.number = number;
	}



	/**
	 * Return the value associated with the column: status
	 */
	public java.lang.String getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (java.lang.String status) {
		this.status = status;
	}



	/**
	 * Return the value associated with the column: is_posted
	 */
	public boolean isPosted () {
		return posted;
	}

	/**
	 * Set the value related to the column: is_posted
	 * @param posted the is_posted value
	 */
	public void setPosted (boolean posted) {
		this.posted = posted;
	}



	/**
	 * Return the value associated with the column: note
	 */
	public java.lang.String getNote () {
		return note;
	}

	/**
	 * Set the value related to the column: note
	 * @param note the note value
	 */
	public void setNote (java.lang.String note) {
		this.note = note;
	}



	/**
	 * Return the value associated with the column: create_on
	 */
	public java.util.Date getCreateOn () {
		return createOn;
	}

	/**
	 * Set the value related to the column: create_on
	 * @param createOn the create_on value
	 */
	public void setCreateOn (java.util.Date createOn) {
		this.createOn = createOn;
	}



	/**
	 * Return the value associated with the column: change_on
	 */
	public java.util.Date getChangeOn () {
		return changeOn;
	}

	/**
	 * Set the value related to the column: change_on
	 * @param changeOn the change_on value
	 */
	public void setChangeOn (java.util.Date changeOn) {
		this.changeOn = changeOn;
	}



	/**
	 * Return the value associated with the column: NumberOfDigit
	 */
	public int getNumberOfDigit () {
		return numberOfDigit;
	}

	/**
	 * Set the value related to the column: NumberOfDigit
	 * @param numberOfDigit the NumberOfDigit value
	 */
	public void setNumberOfDigit (int numberOfDigit) {
		this.numberOfDigit = numberOfDigit;
	}



	/**
	 * Return the value associated with the column: Journal
	 */
	public com.mpe.financial.model.Journal getJournal () {
		return journal;
	}

	/**
	 * Set the value related to the column: Journal
	 * @param journal the Journal value
	 */
	public void setJournal (com.mpe.financial.model.Journal journal) {
		this.journal = journal;
	}



	/**
	 * Return the value associated with the column: create_by
	 */
	public com.mpe.financial.model.Users getCreateBy () {
		return createBy;
	}

	/**
	 * Set the value related to the column: create_by
	 * @param createBy the create_by value
	 */
	public void setCreateBy (com.mpe.financial.model.Users createBy) {
		this.createBy = createBy;
	}



	/**
	 * Return the value associated with the column: change_by
	 */
	public com.mpe.financial.model.Users getChangeBy () {
		return changeBy;
	}

	/**
	 * Set the value related to the column: change_by
	 * @param changeBy the change_by value
	 */
	public void setChangeBy (com.mpe.financial.model.Users changeBy) {
		this.changeBy = changeBy;
	}



	/**
	 * Return the value associated with the column: warehouse_id
	 */
	public com.mpe.financial.model.Warehouse getWarehouse () {
		return warehouse;
	}

	/**
	 * Set the value related to the column: warehouse_id
	 * @param warehouse the warehouse_id value
	 */
	public void setWarehouse (com.mpe.financial.model.Warehouse warehouse) {
		this.warehouse = warehouse;
	}



	/**
	 * Return the value associated with the column: currency_id
	 */
	public com.mpe.financial.model.Currency getCurrency () {
		return currency;
	}

	/**
	 * Set the value related to the column: currency_id
	 * @param currency the currency_id value
	 */
	public void setCurrency (com.mpe.financial.model.Currency currency) {
		this.currency = currency;
	}



	/**
	 * Return the value associated with the column: stock_opname_type_id
	 */
	public com.mpe.financial.model.StockOpnameType getStockOpnameType () {
		return stockOpnameType;
	}

	/**
	 * Set the value related to the column: stock_opname_type_id
	 * @param stockOpnameType the stock_opname_type_id value
	 */
	public void setStockOpnameType (com.mpe.financial.model.StockOpnameType stockOpnameType) {
		this.stockOpnameType = stockOpnameType;
	}



	/**
	 * Return the value associated with the column: organization_id
	 */
	public com.mpe.financial.model.Organization getOrganization () {
		return organization;
	}

	/**
	 * Set the value related to the column: organization_id
	 * @param organization the organization_id value
	 */
	public void setOrganization (com.mpe.financial.model.Organization organization) {
		this.organization = organization;
	}



	/**
	 * Return the value associated with the column: location_id
	 */
	public com.mpe.financial.model.Location getLocation () {
		return location;
	}

	/**
	 * Set the value related to the column: location_id
	 * @param location the location_id value
	 */
	public void setLocation (com.mpe.financial.model.Location location) {
		this.location = location;
	}



	/**
	 * Return the value associated with the column: customer_id
	 */
	public com.mpe.financial.model.Customers getCustomer () {
		return customer;
	}

	/**
	 * Set the value related to the column: customer_id
	 * @param customer the customer_id value
	 */
	public void setCustomer (com.mpe.financial.model.Customers customer) {
		this.customer = customer;
	}



	/**
	 * Return the value associated with the column: StockOpnameDetails
	 */
	public java.util.Set getStockOpnameDetails () {
		return stockOpnameDetails;
	}

	/**
	 * Set the value related to the column: StockOpnameDetails
	 * @param stockOpnameDetails the StockOpnameDetails value
	 */
	public void setStockOpnameDetails (java.util.Set stockOpnameDetails) {
		this.stockOpnameDetails = stockOpnameDetails;
	}

	public void addToStockOpnameDetails (com.mpe.financial.model.StockOpnameDetail stockOpnameDetail) {
		if (null == getStockOpnameDetails()) setStockOpnameDetails(new java.util.HashSet());
		getStockOpnameDetails().add(stockOpnameDetail);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.StockOpname)) return false;
		else {
			com.mpe.financial.model.StockOpname stockOpname = (com.mpe.financial.model.StockOpname) obj;
			return (this.getId() == stockOpname.getId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}