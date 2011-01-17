package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseInvoiceSimple;



public class InvoiceSimple extends BaseInvoiceSimple {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoiceSimple () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public InvoiceSimple (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public InvoiceSimple (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Customers customer,
		com.mpe.financial.model.DeliveryOrder deliveryOrder,
		java.util.Date invoiceDate,
		java.util.Date invoiceDue,
		java.lang.String number,
		boolean posted,
		double taxAmount,
		double discountProcent) {

		super (
			id,
			currency,
			customer,
			deliveryOrder,
			invoiceDate,
			invoiceDue,
			number,
			posted,
			taxAmount,
			discountProcent);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedInvoiceDate() {
	    return Formater.getFormatedDate(getInvoiceDate());
	}
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	public double getInvoiceSimpleDetailAmount() {
		double x = 0;
		try {
			Set set = getInvoiceSimpleDetails()!=null?getInvoiceSimpleDetails():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				InvoiceSimpleDetail invoiceSimpleDetail = (InvoiceSimpleDetail)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), invoiceSimpleDetail.getPriceQuantityAfterDiscountTax());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedInvoiceSimpleDetailAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceSimpleDetailAmount());
	}
	
	public double getInvoiceSimplePrepaymentAmount() {
		double x = 0;
		try {
			Set set = getInvoiceSimplePrepayments()!=null?getInvoiceSimplePrepayments():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				InvoiceSimplePrepayment invoiceSimplePrepayment = (InvoiceSimplePrepayment)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), invoiceSimplePrepayment.getAmount());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedInvoiceSimplePrepaymentAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceSimplePrepaymentAmount());
	}
	
	public double getCustomerPaymentAmount() {
		double x = 0;
		try {
			Set set = getCustomerPayments()!=null?getCustomerPayments():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				CustomerPaymentInvoiceSimpleFK customerPaymentInvoiceSimpleFK = (CustomerPaymentInvoiceSimpleFK)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), customerPaymentInvoiceSimpleFK.getPaymentAmount());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedCustomerPaymentAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getCustomerPaymentAmount());
	}
	
	public double getInvoiceSimpleDiscountAmount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceSimpleDetailAmount() * (getDiscountProcent()/100));
	}
	
	public String getFormatedInvoiceSimpleDiscountAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceSimpleDiscountAmount());
	}
	
	public double getInvoiceSimpleAfterDiscount() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceSimpleDetailAmount() - getInvoiceSimpleDiscountAmount());
	}
	
	public String getFormatedInvoiceSimpleAfterDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceSimpleAfterDiscount());
	}
	
	public double getInvoiceSimpleTaxAmount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), (getInvoiceSimpleAfterDiscount1Discount2Discount3()) * (getTaxAmount()/100));
	}
	
	public String getFormatedInvoiceSimpleTaxAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceSimpleTaxAmount());
	}
	
	public double getDifferenceAmount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceSimpleAfterDiscountAndTaxAndPrepayment() - getCustomerPaymentAmount());
	}
	
	public double getInvoiceSimpleAfterDiscountAndTax() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceSimpleAfterDiscount1Discount2Discount3() + getInvoiceSimpleTaxAmount());
	}
	
	public double getInvoiceSimpleAfterDiscountAndTaxAndPrepayment() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceSimpleAfterDiscountAndTax() - getInvoiceSimplePrepaymentAmount());
	}
	
	public String getFormatedInvoiceSimpleAfterDiscountAndTax() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceSimpleAfterDiscountAndTax());
	}
	
	public String getFormatedInvoiceSimpleAfterDiscountAndTaxAndPrepayment() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceSimpleAfterDiscountAndTaxAndPrepayment());
	}
	
	// for bon kuning
	public double getInvoiceSimpleDiscountAmount1() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceSimpleAfterDiscount() * getDiscount1()/100);
	}
	
	public String getFormatedInvoiceSimpleDiscountAmount1() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceSimpleDiscountAmount1());
	}
	
	public double getInvoiceSimpleAfterDiscount1() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceSimpleAfterDiscount() - getInvoiceSimpleDiscountAmount1());
	}
	
	public String getFormatedInvoiceSimpleAfterDiscount1() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceSimpleAfterDiscount1());
	}
	
	public double getInvoiceSimpleDiscountAmount2() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceSimpleAfterDiscount1() * getDiscount2()/100);
	}
	
	public String getFormatedInvoiceSimpleDiscountAmount2() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceSimpleDiscountAmount2());
	}
	
	public double getInvoiceSimpleAfterDiscount1Discount2() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceSimpleAfterDiscount1() - getInvoiceSimpleDiscountAmount2());
	}
	
	public String getFormatedInvoiceSimpleAfterDiscount1Discount2() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceSimpleAfterDiscount1Discount2());
	}
	
	public double getInvoiceSimpleDiscountAmount3() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceSimpleAfterDiscount1Discount2() * getDiscount3()/100);
	}
	
	public String getFormatedInvoiceSimpleDiscountAmount3() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceSimpleDiscountAmount3());
	}
	
	public double getInvoiceSimpleAfterDiscount1Discount2Discount3() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceSimpleAfterDiscount1Discount2() - getInvoiceSimpleDiscountAmount3());
	}
	
	public String getFormatedInvoiceSimpleAfterDiscount1Discount2Discount3() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceSimpleAfterDiscount1Discount2Discount3());
	}

	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}

}