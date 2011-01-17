package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseInvoice;



public class Invoice extends BaseInvoice {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Invoice () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Invoice (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Invoice (
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
	
	public String getFormatedInvoiceDue() {
	    return Formater.getFormatedDate(getInvoiceDue());
	}
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	public double getInvoiceDetailAmount() {
		double x = 0;
		try {
			Set set = getInvoiceDetails()!=null?getInvoiceDetails():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				InvoiceDetail invoiceDetail = (InvoiceDetail)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), invoiceDetail.getPriceQuantityAfterDiscountTax());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedInvoiceDetailAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceDetailAmount());
	}
	
	public double getInvoicePrepaymentAmount() {
		double x = 0;
		try {
			Set set = getInvoicePrepayments()!=null?getInvoicePrepayments():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				InvoicePrepayment invoicePrepayment = (InvoicePrepayment)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), invoicePrepayment.getAmount());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedInvoicePrepaymentAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoicePrepaymentAmount());
	}
	
	public double getCustomerPaymentAmount() {
		double x = 0;
		try {
			Set set = getCustomerPayments()!=null?getCustomerPayments():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				CustomerPaymentInvoiceFK paymentToVendorInvoiceFK = (CustomerPaymentInvoiceFK)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), paymentToVendorInvoiceFK.getPaymentAmount());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedCustomerPaymentAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getCustomerPaymentAmount());
	}
	
	public double getInvoiceDiscountAmount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceDetailAmount() * (getDiscountProcent()/100));
	}
	
	public String getFormatedInvoiceDiscountAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceDiscountAmount());
	}
	
	public double getInvoiceAfterDiscount() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceDetailAmount() - getInvoiceDiscountAmount());
	}
	
	public String getFormatedInvoiceAfterDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceAfterDiscount());
	}
	
	public double getInvoiceTaxAmount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), (getInvoiceAfterDiscount1Discount2Discount3()) * (getTaxAmount()/100));
	}
	
	public String getFormatedInvoiceTaxAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceTaxAmount());
	}
	
	public double getDifferenceAmount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceAfterDiscountAndTaxAndPrepayment() - getCustomerPaymentAmount());
	}
	
	public double getInvoiceAfterDiscountAndTax() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceAfterDiscount1Discount2Discount3() + getInvoiceTaxAmount());
	}
	
	public double getInvoiceAfterDiscountAndTaxAndPrepayment() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceAfterDiscountAndTax() - getInvoicePrepaymentAmount());
	}
	
	public String getFormatedInvoiceAfterDiscountAndTax() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceAfterDiscountAndTax());
	}
	
	public String getFormatedInvoiceAfterDiscountAndTaxAndPrepayment() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceAfterDiscountAndTaxAndPrepayment());
	}
	
	// for bon kuning
	public double getInvoiceDiscountAmount1() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceAfterDiscount() * getDiscount1()/100);
	}
	
	public String getFormatedInvoiceDiscountAmount1() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceDiscountAmount1());
	}
	
	public double getInvoiceAfterDiscount1() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceAfterDiscount() - getInvoiceDiscountAmount1());
	}
	
	public String getFormatedInvoiceAfterDiscount1() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceAfterDiscount1());
	}
	
	public double getInvoiceDiscountAmount2() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceAfterDiscount1() * getDiscount2()/100);
	}
	
	public String getFormatedInvoiceDiscountAmount2() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceDiscountAmount2());
	}
	
	public double getInvoiceAfterDiscount1Discount2() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceAfterDiscount1() - getInvoiceDiscountAmount2());
	}
	
	public String getFormatedInvoiceAfterDiscount1Discount2() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceAfterDiscount1Discount2());
	}
	
	public double getInvoiceDiscountAmount3() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceAfterDiscount1Discount2() * getDiscount3()/100);
	}
	
	public String getFormatedInvoiceDiscountAmount3() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceDiscountAmount3());
	}
	
	public double getInvoiceAfterDiscount1Discount2Discount3() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getInvoiceAfterDiscount1Discount2() - getInvoiceDiscountAmount3());
	}
	
	public String getFormatedInvoiceAfterDiscount1Discount2Discount3() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getInvoiceAfterDiscount1Discount2Discount3());
	}

	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}

}