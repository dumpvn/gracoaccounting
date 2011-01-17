package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the mutation table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="mutation"
 */

public abstract class BaseMutation  implements Serializable {

	public static String REF = "Mutation";
	public static String PROP_MUTATION_DATE = "MutationDate";
	public static String PROP_NUMBER = "Number";
	public static String PROP_STATUS = "Status";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";


	// constructors
	public BaseMutation () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMutation (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMutation (
		long id,
		com.mpe.financial.model.Warehouse toWarehouse,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Warehouse fromWarehouse,
		java.util.Date mutationDate,
		java.lang.String number) {

		this.setId(id);
		this.setToWarehouse(toWarehouse);
		this.setOrganization(organization);
		this.setFromWarehouse(fromWarehouse);
		this.setMutationDate(mutationDate);
		this.setNumber(number);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date mutationDate;
	private java.lang.String number;
	private java.lang.String status;
	private java.lang.String description;
	private java.util.Date createOn;
	private java.util.Date changeOn;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Warehouse toWarehouse;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Warehouse fromWarehouse;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.Customers customer;

	// collections
	private java.util.Set mutationDetails;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="mutation_id"
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
	 * Return the value associated with the column: mutation_date
	 */
	public java.util.Date getMutationDate () {
		return mutationDate;
	}

	/**
	 * Set the value related to the column: mutation_date
	 * @param mutationDate the mutation_date value
	 */
	public void setMutationDate (java.util.Date mutationDate) {
		this.mutationDate = mutationDate;
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
	 * Return the value associated with the column: to_warehouse_id
	 */
	public com.mpe.financial.model.Warehouse getToWarehouse () {
		return toWarehouse;
	}

	/**
	 * Set the value related to the column: to_warehouse_id
	 * @param toWarehouse the to_warehouse_id value
	 */
	public void setToWarehouse (com.mpe.financial.model.Warehouse toWarehouse) {
		this.toWarehouse = toWarehouse;
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
	 * Return the value associated with the column: from_warehouse_id
	 */
	public com.mpe.financial.model.Warehouse getFromWarehouse () {
		return fromWarehouse;
	}

	/**
	 * Set the value related to the column: from_warehouse_id
	 * @param fromWarehouse the from_warehouse_id value
	 */
	public void setFromWarehouse (com.mpe.financial.model.Warehouse fromWarehouse) {
		this.fromWarehouse = fromWarehouse;
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
	 * Return the value associated with the column: MutationDetails
	 */
	public java.util.Set getMutationDetails () {
		return mutationDetails;
	}

	/**
	 * Set the value related to the column: MutationDetails
	 * @param mutationDetails the MutationDetails value
	 */
	public void setMutationDetails (java.util.Set mutationDetails) {
		this.mutationDetails = mutationDetails;
	}

	public void addToMutationDetails (com.mpe.financial.model.MutationDetail mutationDetail) {
		if (null == getMutationDetails()) setMutationDetails(new java.util.HashSet());
		getMutationDetails().add(mutationDetail);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.Mutation)) return false;
		else {
			com.mpe.financial.model.Mutation mutation = (com.mpe.financial.model.Mutation) obj;
			return (this.getId() == mutation.getId());
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