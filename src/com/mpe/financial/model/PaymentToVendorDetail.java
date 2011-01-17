package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BasePaymentToVendorDetail;



public class PaymentToVendorDetail extends BasePaymentToVendorDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PaymentToVendorDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public PaymentToVendorDetail (com.mpe.financial.model.PaymentToVendorDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public PaymentToVendorDetail (
		com.mpe.financial.model.PaymentToVendorDetailPK id,
		double paymentAmount,
		double vendorBillAmount,
		double vendorBillExchangeRate) {

		super (
			id,
			paymentAmount,
			vendorBillAmount,
			vendorBillExchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	
	public String getFormatedPaymentAmount() {
		return Formater.getFormatedOutput(getId().getPaymentToVendor().getNumberOfDigit(), getPaymentAmount());
	}

	
	public String getFormatedVendorBillAmount() {
		return Formater.getFormatedOutput(getId().getPaymentToVendor().getNumberOfDigit(), getVendorBillAmount());
	}

	public String getFormatedVendorBillExchangeRate() {
		return Formater.getFormatedOutput(getVendorBillExchangeRate());
	}

}