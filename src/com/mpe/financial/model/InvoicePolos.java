package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseInvoicePolos;



public class InvoicePolos extends BaseInvoicePolos {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoicePolos () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public InvoicePolos (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public InvoicePolos (
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
	
	public double getInvoicePolosDetailAmount() {
		double x = 0;
		try {
			Set set = getInvoicePolosDetails()!=null?getInvoicePolosDetails():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				InvoicePolosDetail invoicePolosDetail = (InvoicePolosDetail)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), invoicePolosDetail.getPriceQuantityAfterDiscountTax());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedInvoicePolosDetailAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePolosDetailAmount());
	}
	
	public double getInvoicePolosPrepaymentAmount() {
		double x = 0;
		try {
			Set set = getInvoicePolosPrepayments()!=null?getInvoicePolosPrepayments():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				InvoicePolosPrepayment invoicePolosPrepayment = (InvoicePolosPrepayment)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), invoicePolosPrepayment.getAmount());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedInvoicePolosPrepaymentAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePolosPrepaymentAmount());
	}
	
	public double getCustomerPaymentAmount() {
		double x = 0;
		try {
			Set set = getCustomerPayments()!=null?getCustomerPayments():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				CustomerPaymentInvoicePolosFK customerPaymentInvoicePolosFK = (CustomerPaymentInvoicePolosFK)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), customerPaymentInvoicePolosFK.getPaymentAmount());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedCustomerPaymentAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getCustomerPaymentAmount());
	}
	
	public double getInvoicePolosDiscountAmount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoicePolosDetailAmount() * (getDiscountProcent()/100));
	}
	
	public String getFormatedInvoicePolosDiscountAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePolosDiscountAmount());
	}
	
	public double getInvoicePolosAfterDiscount() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoicePolosDetailAmount() - getInvoicePolosDiscountAmount());
	}
	
	public String getFormatedInvoicePolosAfterDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePolosAfterDiscount());
	}
	
	public double getInvoicePolosTaxAmount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), (getInvoicePolosAfterDiscount1Discount2Discount3()) * (getTaxAmount()/100));
	}
	
	public String getFormatedInvoicePolosTaxAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePolosTaxAmount());
	}
	
	public double getDifferenceAmount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoicePolosAfterDiscountAndTaxAndPrepayment() - getCustomerPaymentAmount());
	}
	
	public double getInvoicePolosAfterDiscountAndTax() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoicePolosAfterDiscount1Discount2Discount3() + getInvoicePolosTaxAmount());
	}
	
	public double getInvoicePolosAfterDiscountAndTaxAndPrepayment() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoicePolosAfterDiscountAndTax() - getInvoicePolosPrepaymentAmount());
	}
	
	public String getFormatedInvoicePolosAfterDiscountAndTax() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePolosAfterDiscountAndTax());
	}
	
	public String getFormatedInvoicePolosAfterDiscountAndTaxAndPrepayment() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePolosAfterDiscountAndTaxAndPrepayment());
	}
	
	// for bon kuning
	public double getInvoicePolosDiscountAmount1() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoicePolosAfterDiscount() * getDiscount1()/100);
	}
	
	public String getFormatedInvoicePolosDiscountAmount1() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePolosDiscountAmount1());
	}
	
	public double getInvoicePolosAfterDiscount1() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoicePolosAfterDiscount() - getInvoicePolosDiscountAmount1());
	}
	
	public String getFormatedInvoicePolosAfterDiscount1() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePolosAfterDiscount1());
	}
	
	public double getInvoicePolosDiscountAmount2() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoicePolosAfterDiscount1() * getDiscount2()/100);
	}
	
	public String getFormatedInvoicePolosDiscountAmount2() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePolosDiscountAmount2());
	}
	
	public double getInvoicePolosAfterDiscount1Discount2() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoicePolosAfterDiscount1() - getInvoicePolosDiscountAmount2());
	}
	
	public String getFormatedInvoicePolosAfterDiscount1Discount2() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePolosAfterDiscount1Discount2());
	}
	
	public double getInvoicePolosDiscountAmount3() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoicePolosAfterDiscount1Discount2() * getDiscount3()/100);
	}
	
	public String getFormatedInvoicePolosDiscountAmount3() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePolosDiscountAmount3());
	}
	
	public double getInvoicePolosAfterDiscount1Discount2Discount3() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoicePolosAfterDiscount1Discount2() - getInvoicePolosDiscountAmount3());
	}
	
	public String getFormatedInvoicePolosAfterDiscount1Discount2Discount3() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePolosAfterDiscount1Discount2Discount3());
	}

	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}

}