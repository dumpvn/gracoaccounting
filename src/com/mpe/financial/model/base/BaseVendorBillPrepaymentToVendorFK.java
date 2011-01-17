package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the prepayment_to_vendor table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="prepayment_to_vendor"
 */

public abstract class BaseVendorBillPrepaymentToVendorFK  implements Serializable {

	public static String REF = "VendorBillPrepaymentToVendorFK";
	public static String PROP_AMOUNT = "Amount";


	// constructors
	public BaseVendorBillPrepaymentToVendorFK () {
		initialize();
	}

	protected void initialize () {}



	// fields
	private double amount;

	// many to one
	private com.mpe.financial.model.VendorBill vendorBill;






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
	 * Return the value associated with the column: vendor_bill_id
	 */
	public com.mpe.financial.model.VendorBill getVendorBill () {
		return vendorBill;
	}

	/**
	 * Set the value related to the column: vendor_bill_id
	 * @param vendorBill the vendor_bill_id value
	 */
	public void setVendorBill (com.mpe.financial.model.VendorBill vendorBill) {
		this.vendorBill = vendorBill;
	}








	public String toString () {
		return super.toString();
	}


}