package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseCustomerPayment;



public class CustomerPayment extends BaseCustomerPayment {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomerPayment () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CustomerPayment (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CustomerPayment (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Customers customer,
		com.mpe.financial.model.BankAccount bankAccount,
		java.util.Date paymentDate,
		java.lang.String number,
		double amount,
		boolean posted,
		java.lang.String method,
		double exchangeRate) {

		super (
			id,
			currency,
			customer,
			bankAccount,
			paymentDate,
			number,
			amount,
			posted,
			method,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedPaymentDate() {
	    return Formater.getFormatedDate(getPaymentDate());
	}
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	public double getInvoiceAmount() {
		double x = 0;
		try {
			Set set = getCustomerPaymentDetails()!=null?getCustomerPaymentDetails():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				CustomerPaymentDetail customerPaymentDetail = (CustomerPaymentDetail)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(),(customerPaymentDetail.getInvoiceAmount()*customerPaymentDetail.getInvoiceExchangeRate()));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public double getCustomerPaymentDetailAmount() {
		double x = 0;
		try {
			Set set = getCustomerPaymentDetails()!=null?getCustomerPaymentDetails():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				CustomerPaymentDetail customerPaymentDetail = (CustomerPaymentDetail)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), (customerPaymentDetail.getPaymentAmount()));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedInvoiceAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceAmount());
	}
	
	public String getFormatedCustomerPaymentDetailAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getCustomerPaymentDetailAmount());
	}
	
	public double getCustomerReturAmount() {
		double x = 0;
		try {
			Set set = getCustomerReturs()!=null?getCustomerReturs():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				CustomerRetur customerRetur = (CustomerRetur)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(),(customerRetur.getAmountAfterTaxAndDiscount()));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedCustomerReturAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getCustomerReturAmount());
	}
	
	public double getPaymentAmount() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getCustomerPaymentDetailAmount() - getCustomerReturAmount());
	}
	
	public String getFormatedPaymentAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPaymentAmount());
	}

	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}

}