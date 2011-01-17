package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the vendor_bill_prepayment_to_vendor table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="vendor_bill_prepayment_to_vendor"
 */

public abstract class BaseVendorBillPrepayment  implements Serializable {

	public static String REF = "VendorBillPrepayment";
	public static String PROP_AMOUNT = "Amount";


	// constructors
	public BaseVendorBillPrepayment () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseVendorBillPrepayment (com.mpe.financial.model.VendorBillPrepaymentPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseVendorBillPrepayment (
		com.mpe.financial.model.VendorBillPrepaymentPK id,
		double amount) {

		this.setId(id);
		this.setAmount(amount);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.VendorBillPrepaymentPK id;

	// fields
	private double amount;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.VendorBillPrepaymentPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.VendorBillPrepaymentPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
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





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.VendorBillPrepayment)) return false;
		else {
			com.mpe.financial.model.VendorBillPrepayment vendorBillPrepayment = (com.mpe.financial.model.VendorBillPrepayment) obj;
			if (null == this.getId() || null == vendorBillPrepayment.getId()) return false;
			else return (this.getId().equals(vendorBillPrepayment.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}