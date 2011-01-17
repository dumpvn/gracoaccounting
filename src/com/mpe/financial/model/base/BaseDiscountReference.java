package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the discount_reference table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="discount_reference"
 */

public abstract class BaseDiscountReference  implements Serializable {

	public static String REF = "DiscountReference";
	public static String PROP_DISCOUNT_TYPE = "DiscountType";
	public static String PROP_AMOUNT = "Amount";
	public static String PROP_VALID_FROM = "ValidFrom";
	public static String PROP_VALID_TO = "ValidTo";
	public static String PROP_ACTIVE = "Active";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";


	// constructors
	public BaseDiscountReference () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDiscountReference (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseDiscountReference (
		long id,
		com.mpe.financial.model.Organization organization,
		java.lang.String discountType,
		boolean active) {

		this.setId(id);
		this.setOrganization(organization);
		this.setDiscountType(discountType);
		this.setActive(active);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.lang.String discountType;
	private double amount;
	private java.util.Date validFrom;
	private java.util.Date validTo;
	private boolean active;
	private java.util.Date createOn;
	private java.util.Date changeOn;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Customers customer;
	private com.mpe.financial.model.Location location;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="discount_reference_id"
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
	 * Return the value associated with the column: discount_type
	 */
	public java.lang.String getDiscountType () {
		return discountType;
	}

	/**
	 * Set the value related to the column: discount_type
	 * @param discountType the discount_type value
	 */
	public void setDiscountType (java.lang.String discountType) {
		this.discountType = discountType;
	}



	/**
	 * Return the value associated with the column: amount
	 */
	public double getAmount () {
		return amount;
	}

	/**
	 * Set the value related to the column: amount
	 * @param amount the amount value
	 */
	public void setAmount (double amount) {
		this.amount = amount;
	}



	/**
	 * Return the value associated with the column: valid_from
	 */
	public java.util.Date getValidFrom () {
		return validFrom;
	}

	/**
	 * Set the value related to the column: valid_from
	 * @param validFrom the valid_from value
	 */
	public void setValidFrom (java.util.Date validFrom) {
		this.validFrom = validFrom;
	}



	/**
	 * Return the value associated with the column: valid_to
	 */
	public java.util.Date getValidTo () {
		return validTo;
	}

	/**
	 * Set the value related to the column: valid_to
	 * @param validTo the valid_to value
	 */
	public void setValidTo (java.util.Date validTo) {
		this.validTo = validTo;
	}



	/**
	 * Return the value associated with the column: is_active
	 */
	public boolean isActive () {
		return active;
	}

	/**
	 * Set the value related to the column: is_active
	 * @param active the is_active value
	 */
	public void setActive (boolean active) {
		this.active = active;
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





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.DiscountReference)) return false;
		else {
			com.mpe.financial.model.DiscountReference discountReference = (com.mpe.financial.model.DiscountReference) obj;
			return (this.getId() == discountReference.getId());
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