package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the purchase_order table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="purchase_order"
 */

public abstract class BasePurchaseOrderPrepaymentToVendorFK  implements Serializable {

	public static String REF = "PurchaseOrderPrepaymentToVendorFK";
	public static String PROP_AMOUNT = "Amount";


	// constructors
	public BasePurchaseOrderPrepaymentToVendorFK () {
		initialize();
	}

	protected void initialize () {}



	// fields
	private double amount;

	// many to one
	private com.mpe.financial.model.PrepaymentToVendor prepaymentToVendor;






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
	 * Return the value associated with the column: prepayment_to_vendor_id
	 */
	public com.mpe.financial.model.PrepaymentToVendor getPrepaymentToVendor () {
		return prepaymentToVendor;
	}

	/**
	 * Set the value related to the column: prepayment_to_vendor_id
	 * @param prepaymentToVendor the prepayment_to_vendor_id value
	 */
	public void setPrepaymentToVendor (com.mpe.financial.model.PrepaymentToVendor prepaymentToVendor) {
		this.prepaymentToVendor = prepaymentToVendor;
	}








	public String toString () {
		return super.toString();
	}


}