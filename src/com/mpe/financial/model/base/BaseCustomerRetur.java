package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the customer_retur table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="customer_retur"
 */

public abstract class BaseCustomerRetur  implements Serializable {

	public static String REF = "CustomerRetur";
	public static String PROP_RETUR_DATE = "ReturDate";
	public static String PROP_NUMBER = "Number";
	public static String PROP_STATUS = "Status";
	public static String PROP_CUSTOMER_PAYMENT_STATUS = "CustomerPaymentStatus";
	public static String PROP_POSTED = "Posted";
	public static String PROP_NOTE = "Note";
	public static String PROP_REFERENCE = "Reference";
	public static String PROP_EXCHANGE_RATE = "ExchangeRate";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseCustomerRetur () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCustomerRetur (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCustomerRetur (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Customers customer,
		com.mpe.financial.model.DeliveryOrder deliveryOrder,
		java.util.Date returDate,
		java.lang.String number,
		double exchangeRate) {

		this.setId(id);
		this.setCurrency(currency);
		this.setOrganization(organization);
		this.setCustomer(customer);
		this.setDeliveryOrder(deliveryOrder);
		this.setReturDate(returDate);
		this.setNumber(number);
		this.setExchangeRate(exchangeRate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date returDate;
	private java.lang.String number;
	private java.lang.String status;
	private java.lang.String customerPaymentStatus;
	private boolean posted;
	private java.lang.String note;
	private java.lang.String reference;
	private double exchangeRate;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private int numberOfDigit;

	// one to one
	private com.mpe.financial.model.Journal journal;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Customers customer;
	private com.mpe.financial.model.Customers customerAlias;
	private com.mpe.financial.model.DeliveryOrder deliveryOrder;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.Department department;
	private com.mpe.financial.model.Project project;

	// collections
	private java.util.Set customerReturDetails;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="customer_retur_id"
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
	 * Return the value associated with the column: retur_date
	 */
	public java.util.Date getReturDate () {
		return returDate;
	}

	/**
	 * Set the value related to the column: retur_date
	 * @param returDate the retur_date value
	 */
	public void setReturDate (java.util.Date returDate) {
		this.returDate = returDate;
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
	 * Return the value associated with the column: customer_payment_status
	 */
	public java.lang.String getCustomerPaymentStatus () {
		return customerPaymentStatus;
	}

	/**
	 * Set the value related to the column: customer_payment_status
	 * @param customerPaymentStatus the customer_payment_status value
	 */
	public void setCustomerPaymentStatus (java.lang.String customerPaymentStatus) {
		this.customerPaymentStatus = customerPaymentStatus;
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
	 * Return the value associated with the column: reference
	 */
	public java.lang.String getReference () {
		return reference;
	}

	/**
	 * Set the value related to the column: reference
	 * @param reference the reference value
	 */
	public void setReference (java.lang.String reference) {
		this.reference = reference;
	}



	/**
	 * Return the value associated with the column: exchange_rate
	 */
	public double getExchangeRate () {
		return exchangeRate;
	}

	/**
	 * Set the value related to the column: exchange_rate
	 * @param exchangeRate the exchange_rate value
	 */
	public void setExchangeRate (double exchangeRate) {
		this.exchangeRate = exchangeRate;
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
	 * Return the value associated with the column: customer_alias_id
	 */
	public com.mpe.financial.model.Customers getCustomerAlias () {
		return customerAlias;
	}

	/**
	 * Set the value related to the column: customer_alias_id
	 * @param customerAlias the customer_alias_id value
	 */
	public void setCustomerAlias (com.mpe.financial.model.Customers customerAlias) {
		this.customerAlias = customerAlias;
	}



	/**
	 * Return the value associated with the column: delivery_order_id
	 */
	public com.mpe.financial.model.DeliveryOrder getDeliveryOrder () {
		return deliveryOrder;
	}

	/**
	 * Set the value related to the column: delivery_order_id
	 * @param deliveryOrder the delivery_order_id value
	 */
	public void setDeliveryOrder (com.mpe.financial.model.DeliveryOrder deliveryOrder) {
		this.deliveryOrder = deliveryOrder;
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
	 * Return the value associated with the column: department_id
	 */
	public com.mpe.financial.model.Department getDepartment () {
		return department;
	}

	/**
	 * Set the value related to the column: department_id
	 * @param department the department_id value
	 */
	public void setDepartment (com.mpe.financial.model.Department department) {
		this.department = department;
	}



	/**
	 * Return the value associated with the column: project_id
	 */
	public com.mpe.financial.model.Project getProject () {
		return project;
	}

	/**
	 * Set the value related to the column: project_id
	 * @param project the project_id value
	 */
	public void setProject (com.mpe.financial.model.Project project) {
		this.project = project;
	}



	/**
	 * Return the value associated with the column: CustomerReturDetails
	 */
	public java.util.Set getCustomerReturDetails () {
		return customerReturDetails;
	}

	/**
	 * Set the value related to the column: CustomerReturDetails
	 * @param customerReturDetails the CustomerReturDetails value
	 */
	public void setCustomerReturDetails (java.util.Set customerReturDetails) {
		this.customerReturDetails = customerReturDetails;
	}

	public void addToCustomerReturDetails (com.mpe.financial.model.CustomerReturDetail customerReturDetail) {
		if (null == getCustomerReturDetails()) setCustomerReturDetails(new java.util.HashSet());
		getCustomerReturDetails().add(customerReturDetail);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.CustomerRetur)) return false;
		else {
			com.mpe.financial.model.CustomerRetur customerRetur = (com.mpe.financial.model.CustomerRetur) obj;
			return (this.getId() == customerRetur.getId());
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