package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseVendorBillPaymentToVendorFK;



public class VendorBillPaymentToVendorFK extends BaseVendorBillPaymentToVendorFK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public VendorBillPaymentToVendorFK () {
		super();
	}

	/**
	 * Constructor for required fields
	 */
	public VendorBillPaymentToVendorFK (
		com.mpe.financial.model.PaymentToVendor paymentToVendor,
		double amount) {

		super (
			paymentToVendor,
			amount);
	}

/*[CONSTRUCTOR MARKER END]*/


}