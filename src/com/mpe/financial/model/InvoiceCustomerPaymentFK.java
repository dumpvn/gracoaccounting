package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseInvoiceCustomerPaymentFK;



public class InvoiceCustomerPaymentFK extends BaseInvoiceCustomerPaymentFK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoiceCustomerPaymentFK () {
		super();
	}

	/**
	 * Constructor for required fields
	 */
	public InvoiceCustomerPaymentFK (
		com.mpe.financial.model.CustomerPayment customerPayment,
		double amount) {

		super (
			customerPayment,
			amount);
	}

/*[CONSTRUCTOR MARKER END]*/


}