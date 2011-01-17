package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the receiving table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="receiving"
 */

public abstract class BaseReceiving  implements Serializable {

	public static String REF = "Receiving";
	public static String PROP_RECEIVING_DATE = "ReceivingDate";
	public static String PROP_NUMBER = "Number";
	public static String PROP_STATUS = "Status";
	public static String PROP_VENDOR_BILL_STATUS = "VendorBillStatus";
	public static String PROP_DESCRIPTION = "Description";
	public static String PROP_TYPE = "Type";
	public static String PROP_SERVICE = "Service";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseReceiving () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseReceiving (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseReceiving (
		long id,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.PurchaseOrder purchaseOrder,
		com.mpe.financial.model.Vendors vendor,
		java.util.Date receivingDate,
		java.lang.String number,
		java.lang.String status,
		java.lang.String vendorBillStatus) {

		this.setId(id);
		this.setOrganization(organization);
		this.setPurchaseOrder(purchaseOrder);
		this.setVendor(vendor);
		this.setReceivingDate(receivingDate);
		this.setNumber(number);
		this.setStatus(status);
		this.setVendorBillStatus(vendorBillStatus);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date receivingDate;
	private java.lang.String number;
	private java.lang.String status;
	private java.lang.String vendorBillStatus;
	private java.lang.String description;
	private java.lang.String type;
	private boolean service;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private int numberOfDigit;

	// one to one
	private com.mpe.financial.model.VendorBill vendorBill;
	private com.mpe.financial.model.ReturToVendor returToVendor;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.PurchaseOrder purchaseOrder;
	private com.mpe.financial.model.Vendors vendor;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.Department department;
	private com.mpe.financial.model.Project project;

	// collections
	private java.util.Set receivingDetails;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="receiving_id"
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
	 * Return the value associated with the column: receiving_date
	 */
	public java.util.Date getReceivingDate () {
		return receivingDate;
	}

	/**
	 * Set the value related to the column: receiving_date
	 * @param receivingDate the receiving_date value
	 */
	public void setReceivingDate (java.util.Date receivingDate) {
		this.receivingDate = receivingDate;
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
	 * Return the value associated with the column: vendor_bill_status
	 */
	public java.lang.String getVendorBillStatus () {
		return vendorBillStatus;
	}

	/**
	 * Set the value related to the column: vendor_bill_status
	 * @param vendorBillStatus the vendor_bill_status value
	 */
	public void setVendorBillStatus (java.lang.String vendorBillStatus) {
		this.vendorBillStatus = vendorBillStatus;
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
	 * Return the value associated with the column: type
	 */
	public java.lang.String getType () {
		return type;
	}

	/**
	 * Set the value related to the column: type
	 * @param type the type value
	 */
	public void setType (java.lang.String type) {
		this.type = type;
	}



	/**
	 * Return the value associated with the column: is_service
	 */
	public boolean isService () {
		return service;
	}

	/**
	 * Set the value related to the column: is_service
	 * @param service the is_service value
	 */
	public void setService (boolean service) {
		this.service = service;
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
	 * Return the value associated with the column: VendorBill
	 */
	public com.mpe.financial.model.VendorBill getVendorBill () {
		return vendorBill;
	}

	/**
	 * Set the value related to the column: VendorBill
	 * @param vendorBill the VendorBill value
	 */
	public void setVendorBill (com.mpe.financial.model.VendorBill vendorBill) {
		this.vendorBill = vendorBill;
	}



	/**
	 * Return the value associated with the column: ReturToVendor
	 */
	public com.mpe.financial.model.ReturToVendor getReturToVendor () {
		return returToVendor;
	}

	/**
	 * Set the value related to the column: ReturToVendor
	 * @param returToVendor the ReturToVendor value
	 */
	public void setReturToVendor (com.mpe.financial.model.ReturToVendor returToVendor) {
		this.returToVendor = returToVendor;
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
	 * Return the value associated with the column: purchase_order_id
	 */
	public com.mpe.financial.model.PurchaseOrder getPurchaseOrder () {
		return purchaseOrder;
	}

	/**
	 * Set the value related to the column: purchase_order_id
	 * @param purchaseOrder the purchase_order_id value
	 */
	public void setPurchaseOrder (com.mpe.financial.model.PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}



	/**
	 * Return the value associated with the column: vendor_id
	 */
	public com.mpe.financial.model.Vendors getVendor () {
		return vendor;
	}

	/**
	 * Set the value related to the column: vendor_id
	 * @param vendor the vendor_id value
	 */
	public void setVendor (com.mpe.financial.model.Vendors vendor) {
		this.vendor = vendor;
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
	 * Return the value associated with the column: ReceivingDetails
	 */
	public java.util.Set getReceivingDetails () {
		return receivingDetails;
	}

	/**
	 * Set the value related to the column: ReceivingDetails
	 * @param receivingDetails the ReceivingDetails value
	 */
	public void setReceivingDetails (java.util.Set receivingDetails) {
		this.receivingDetails = receivingDetails;
	}

	public void addToReceivingDetails (com.mpe.financial.model.ReceivingDetail receivingDetail) {
		if (null == getReceivingDetails()) setReceivingDetails(new java.util.HashSet());
		getReceivingDetails().add(receivingDetail);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.Receiving)) return false;
		else {
			com.mpe.financial.model.Receiving receiving = (com.mpe.financial.model.Receiving) obj;
			return (this.getId() == receiving.getId());
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