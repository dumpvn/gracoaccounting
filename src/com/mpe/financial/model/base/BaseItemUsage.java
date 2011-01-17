package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the item_usage table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="item_usage"
 */

public abstract class BaseItemUsage  implements Serializable {

	public static String REF = "ItemUsage";
	public static String PROP_NUMBER = "Number";
	public static String PROP_STATUS = "Status";
	public static String PROP_USAGE_DATE = "UsageDate";
	public static String PROP_OUT_TIME = "OutTime";
	public static String PROP_RETUR_TIME = "ReturTime";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseItemUsage () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemUsage (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseItemUsage (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		java.lang.String number,
		java.lang.String status,
		java.util.Date usageDate) {

		this.setId(id);
		this.setCurrency(currency);
		this.setOrganization(organization);
		this.setNumber(number);
		this.setStatus(status);
		this.setUsageDate(usageDate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String number;
	private java.lang.String status;
	private java.util.Date usageDate;
	private java.util.Date outTime;
	private java.util.Date returTime;
	private java.lang.String description;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private int numberOfDigit;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Warehouse warehouse;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Location location;

	// collections
	private java.util.Set itemUsageDetails;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="item_usage_id"
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
	 * Return the value associated with the column: usage_date
	 */
	public java.util.Date getUsageDate () {
		return usageDate;
	}

	/**
	 * Set the value related to the column: usage_date
	 * @param usageDate the usage_date value
	 */
	public void setUsageDate (java.util.Date usageDate) {
		this.usageDate = usageDate;
	}



	/**
	 * Return the value associated with the column: out_time
	 */
	public java.util.Date getOutTime () {
		return outTime;
	}

	/**
	 * Set the value related to the column: out_time
	 * @param outTime the out_time value
	 */
	public void setOutTime (java.util.Date outTime) {
		this.outTime = outTime;
	}



	/**
	 * Return the value associated with the column: retur_time
	 */
	public java.util.Date getReturTime () {
		return returTime;
	}

	/**
	 * Set the value related to the column: retur_time
	 * @param returTime the retur_time value
	 */
	public void setReturTime (java.util.Date returTime) {
		this.returTime = returTime;
	}



	/**
	 * Return the value associated with the column: description
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: description
	 * @param description the description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
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
	 * Return the value associated with the column: ItemUsageDetails
	 */
	public java.util.Set getItemUsageDetails () {
		return itemUsageDetails;
	}

	/**
	 * Set the value related to the column: ItemUsageDetails
	 * @param itemUsageDetails the ItemUsageDetails value
	 */
	public void setItemUsageDetails (java.util.Set itemUsageDetails) {
		this.itemUsageDetails = itemUsageDetails;
	}

	public void addToItemUsageDetails (com.mpe.financial.model.ItemUsageDetail itemUsageDetail) {
		if (null == getItemUsageDetails()) setItemUsageDetails(new java.util.HashSet());
		getItemUsageDetails().add(itemUsageDetail);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.ItemUsage)) return false;
		else {
			com.mpe.financial.model.ItemUsage itemUsage = (com.mpe.financial.model.ItemUsage) obj;
			return (this.getId() == itemUsage.getId());
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