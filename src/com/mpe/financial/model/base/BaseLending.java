package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the lending table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="lending"
 */

public abstract class BaseLending  implements Serializable {

	public static String REF = "Lending";
	public static String PROP_NUMBER = "Number";
	public static String PROP_LENDING_DATE = "LendingDate";
	public static String PROP_RETUR_DATE = "ReturDate";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_STATUS = "Status";


	// constructors
	public BaseLending () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseLending (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseLending (
		long id,
		com.mpe.financial.model.Salesman salesman,
		com.mpe.financial.model.Organization organization,
		java.lang.String number,
		java.util.Date lendingDate,
		java.lang.String status) {

		this.setId(id);
		this.setSalesman(salesman);
		this.setOrganization(organization);
		this.setNumber(number);
		this.setLendingDate(lendingDate);
		this.setStatus(status);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String number;
	private java.util.Date lendingDate;
	private java.util.Date returDate;
	private java.lang.String description;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private java.lang.String status;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Salesman salesman;
	private com.mpe.financial.model.Organization organization;

	// collections
	private java.util.Set lendingDetails;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="lending_id"
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
	 * Return the value associated with the column: lending_date
	 */
	public java.util.Date getLendingDate () {
		return lendingDate;
	}

	/**
	 * Set the value related to the column: lending_date
	 * @param lendingDate the lending_date value
	 */
	public void setLendingDate (java.util.Date lendingDate) {
		this.lendingDate = lendingDate;
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
	 * Return the value associated with the column: salesman_id
	 */
	public com.mpe.financial.model.Salesman getSalesman () {
		return salesman;
	}

	/**
	 * Set the value related to the column: salesman_id
	 * @param salesman the salesman_id value
	 */
	public void setSalesman (com.mpe.financial.model.Salesman salesman) {
		this.salesman = salesman;
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
	 * Return the value associated with the column: LendingDetails
	 */
	public java.util.Set getLendingDetails () {
		return lendingDetails;
	}

	/**
	 * Set the value related to the column: LendingDetails
	 * @param lendingDetails the LendingDetails value
	 */
	public void setLendingDetails (java.util.Set lendingDetails) {
		this.lendingDetails = lendingDetails;
	}

	public void addToLendingDetails (com.mpe.financial.model.LendingDetail lendingDetail) {
		if (null == getLendingDetails()) setLendingDetails(new java.util.HashSet());
		getLendingDetails().add(lendingDetail);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.Lending)) return false;
		else {
			com.mpe.financial.model.Lending lending = (com.mpe.financial.model.Lending) obj;
			return (this.getId() == lending.getId());
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