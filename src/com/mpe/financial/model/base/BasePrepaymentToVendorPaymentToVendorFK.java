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

public abstract class BasePrepaymentToVendorPaymentToVendorFK  implements Serializable {

	public static String REF = "PrepaymentToVendorPaymentToVendorFK";
	public static String PROP_AMOUNT = "Amount";


	// constructors
	public BasePrepaymentToVendorPaymentToVendorFK () {
		initialize();
	}

	protected void initialize () {}



	// fields
	private double amount;

	// many to one
	private com.mpe.financial.model.PaymentToVendor paymentToVendor;






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
	 * Return the value associated with the column: payment_to_vendor_id
	 */
	public com.mpe.financial.model.PaymentToVendor getPaymentToVendor () {
		return paymentToVendor;
	}

	/**
	 * Set the value related to the column: payment_to_vendor_id
	 * @param paymentToVendor the payment_to_vendor_id value
	 */
	public void setPaymentToVendor (com.mpe.financial.model.PaymentToVendor paymentToVendor) {
		this.paymentToVendor = paymentToVendor;
	}








	public String toString () {
		return super.toString();
	}


}