package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseCustomerPaymentDetail;



public class CustomerPaymentDetail extends BaseCustomerPaymentDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomerPaymentDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CustomerPaymentDetail (com.mpe.financial.model.CustomerPaymentDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CustomerPaymentDetail (
		com.mpe.financial.model.CustomerPaymentDetailPK id,
		double paymentAmount,
		double invoiceAmount,
		double invoiceExchangeRate) {

		super (
			id,
			paymentAmount,
			invoiceAmount,
			invoiceExchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	
	public String getFormatedPaymentAmount() {
		return Formater.getFormatedOutput(getId().getCustomerPayment().getNumberOfDigit(), getPaymentAmount());
	}
	
	public String getFormatedInvoiceAmount() {
		return Formater.getFormatedOutput(getId().getCustomerPayment().getNumberOfDigit(), getInvoiceAmount());
	}
	
	public String getFormatedInvoiceExchangeRate() {
		return Formater.getFormatedOutput(getInvoiceExchangeRate());
	}


}