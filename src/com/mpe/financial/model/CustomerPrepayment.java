package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseCustomerPrepayment;



public class CustomerPrepayment extends BaseCustomerPrepayment {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomerPrepayment () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CustomerPrepayment (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CustomerPrepayment (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Customers customer,
		com.mpe.financial.model.SalesOrder salesOrder,
		com.mpe.financial.model.BankAccount bankAccount,
		java.util.Date prepaymentDate,
		java.lang.String number,
		double amount,
		java.lang.String invoiceStatus,
		double exchangeRate) {

		super (
			id,
			currency,
			organization,
			customer,
			salesOrder,
			bankAccount,
			prepaymentDate,
			number,
			amount,
			invoiceStatus,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedPrepaymentDate() {
    return Formater.getFormatedDate(getPrepaymentDate());
	}
	
	public String getFormatedCreateOn() {
    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	public String getFormatedAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmount());
	}


	public double getInvoicePaymentAmount() {
		double x = 0;
		try {
			// standar
			Set set = getInvoices()!=null?getInvoices():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				InvoiceCustomerPrepaymentFK invoiceCustomerPrepaymentFK = (InvoiceCustomerPrepaymentFK)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), invoiceCustomerPrepaymentFK.getAmount());
			}
			// polos
			Set set2 = getInvoicePolos()!=null?getInvoicePolos():new LinkedHashSet();
			Iterator iterator2 = set2.iterator();
			while (iterator2.hasNext()) {
				InvoicePolosCustomerPrepaymentFK invoicePolosCustomerPrepaymentFK = (InvoicePolosCustomerPrepaymentFK)iterator2.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(),invoicePolosCustomerPrepaymentFK.getAmount());
			}
			// simple
			Set set3 = getInvoiceSimples()!=null?getInvoiceSimples():new LinkedHashSet();
			Iterator iterator3 = set3.iterator();
			while (iterator3.hasNext()) {
				InvoiceSimpleCustomerPrepaymentFK invoiceSimpleCustomerPrepaymentFK = (InvoiceSimpleCustomerPrepaymentFK)iterator3.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(),invoiceSimpleCustomerPrepaymentFK.getAmount());
			}
		}catch(Exception ex) {}
		return x;
	}
	
	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}


}