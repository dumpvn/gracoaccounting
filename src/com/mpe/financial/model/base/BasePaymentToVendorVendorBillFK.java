package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the vendor_bill table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="vendor_bill"
 */

public abstract class BasePaymentToVendorVendorBillFK  implements Serializable {

	public static String REF = "PaymentToVendorVendorBillFK";
	public static String PROP_VENDOR_BILL_AMOUNT = "VendorBillAmount";
	public static String PROP_PAYMENT_AMOUNT = "PaymentAmount";
	public static String PROP_VENDOR_BILL_EXCHANGE_RATE = "VendorBillExchangeRate";


	// constructors
	public BasePaymentToVendorVendorBillFK () {
		initialize();
	}

	protected void initialize () {}



	// fields
	private double vendorBillAmount;
	private double paymentAmount;
	private double vendorBillExchangeRate;

	// many to one
	private com.mpe.financial.model.PaymentToVendor paymentToVendor;






	/**
	 * Return the value associated with the column: vendor_bill_amount
	 */
	public double getVendorBillAmount () {
		return vendorBillAmount;
	}

	/**
	 * Set the value related to the column: vendor_bill_amount
	 * @param vendorBillAmount the vendor_bill_amount value
	 */
	public void setVendorBillAmount (double vendorBillAmount) {
		this.vendorBillAmount = vendorBillAmount;
	}



	/**
	 * Return the value associated with the column: payment_amount
	 */
	public double getPaymentAmount () {
		return paymentAmount;
	}

	/**
	 * Set the value related to the column: payment_amount
	 * @param paymentAmount the payment_amount value
	 */
	public void setPaymentAmount (double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}



	/**
	 * Return the value associated with the column: vendor_bill_exchange_rate
	 */
	public double getVendorBillExchangeRate () {
		return vendorBillExchangeRate;
	}

	/**
	 * Set the value related to the column: vendor_bill_exchange_rate
	 * @param vendorBillExchangeRate the vendor_bill_exchange_rate value
	 */
	public void setVendorBillExchangeRate (double vendorBillExchangeRate) {
		this.vendorBillExchangeRate = vendorBillExchangeRate;
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