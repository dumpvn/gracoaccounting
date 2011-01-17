package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the payment_to_vendor_vendor_bill table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="payment_to_vendor_vendor_bill"
 */

public abstract class BasePaymentToVendorDetail  implements Serializable {

	public static String REF = "PaymentToVendorDetail";
	public static String PROP_PAYMENT_AMOUNT = "PaymentAmount";
	public static String PROP_VENDOR_BILL_AMOUNT = "VendorBillAmount";
	public static String PROP_VENDOR_BILL_EXCHANGE_RATE = "VendorBillExchangeRate";


	// constructors
	public BasePaymentToVendorDetail () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePaymentToVendorDetail (com.mpe.financial.model.PaymentToVendorDetailPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BasePaymentToVendorDetail (
		com.mpe.financial.model.PaymentToVendorDetailPK id,
		double paymentAmount,
		double vendorBillAmount,
		double vendorBillExchangeRate) {

		this.setId(id);
		this.setPaymentAmount(paymentAmount);
		this.setVendorBillAmount(vendorBillAmount);
		this.setVendorBillExchangeRate(vendorBillExchangeRate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.PaymentToVendorDetailPK id;

	// fields
	private double paymentAmount;
	private double vendorBillAmount;
	private double vendorBillExchangeRate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.PaymentToVendorDetailPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.PaymentToVendorDetailPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
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





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.PaymentToVendorDetail)) return false;
		else {
			com.mpe.financial.model.PaymentToVendorDetail paymentToVendorDetail = (com.mpe.financial.model.PaymentToVendorDetail) obj;
			if (null == this.getId() || null == paymentToVendorDetail.getId()) return false;
			else return (this.getId().equals(paymentToVendorDetail.getId()));
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