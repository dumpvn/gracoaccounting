package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseVendorBill;



public class VendorBill extends BaseVendorBill {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public VendorBill () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public VendorBill (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public VendorBill (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Vendors vendor,
		com.mpe.financial.model.Receiving receiving,
		java.util.Date billDate,
		java.util.Date billDue,
		java.lang.String number,
		java.lang.String status,
		java.lang.String paymentToVendorStatus,
		boolean posted,
		double exchangeRate,
		double taxAmount) {

		super (
			id,
			currency,
			organization,
			vendor,
			receiving,
			billDate,
			billDue,
			number,
			status,
			paymentToVendorStatus,
			posted,
			exchangeRate,
			taxAmount);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedBillDate() {
	    return Formater.getFormatedDate(getBillDate());
	}
	
	public String getFormatedBillDue() {
	    return Formater.getFormatedDate(getBillDue());
	}
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	public double getVendorBillDetailAmount() {
		double x = 0;
		try {
			Set set = getVendorBillDetails()!=null?getVendorBillDetails():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				VendorBillDetail vendorBillDetail = (VendorBillDetail)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), vendorBillDetail.getPriceQuantityAfterDiscountTax());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedVendorBillDetailAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getVendorBillDetailAmount());
	}
	
	public double getVendorBillPrepaymentAmount() {
		double x = 0;
		try {
			Set set = getVendorBillPrepayments()!=null?getVendorBillPrepayments():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				VendorBillPrepayment vendorBillPrepayment = (VendorBillPrepayment)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), vendorBillPrepayment.getAmount());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedVendorBillPrepaymentAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getVendorBillPrepaymentAmount());
	}
	
	public double getPaymentToVendorAmount() {
		double x = 0;
		try {
			Set set = getPaymentToVendors()!=null?getPaymentToVendors():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				PaymentToVendorVendorBillFK paymentToVendorVendorBillFK = (PaymentToVendorVendorBillFK)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), paymentToVendorVendorBillFK.getPaymentAmount() * (1/paymentToVendorVendorBillFK.getVendorBillExchangeRate()));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public String getFormatedPaymentToVendorAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getPaymentToVendorAmount());
	}
	
	public double getVendorBillDiscountAmount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getVendorBillDetailAmount() * (getDiscountProcent()/100));
	}
	
	public String getFormatedVendorBillDiscountAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getVendorBillDiscountAmount());
	}
	
	public double getVendorBillTaxAmount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), (getVendorBillDetailAmount() - getVendorBillDiscountAmount()) * (getTaxAmount()/100));
	}
	
	public String getFormatedVendorBillTaxAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getVendorBillTaxAmount());
	}
	
	public double getDifferenceAmount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getVendorBillDetailAmount() - getVendorBillDiscountAmount() + getVendorBillTaxAmount() - getVendorBillPrepaymentAmount() - getPaymentToVendorAmount());
	}
	
	public String getFormatedDifferenceAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getDifferenceAmount());
	}
	
	public double getVendorBillAfterDiscountAndTax() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getVendorBillDetailAmount() - getVendorBillDiscountAmount() + getVendorBillTaxAmount());
	}
	
	public double getVendorBillAfterDiscount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getVendorBillDetailAmount() - getVendorBillDiscountAmount());
	}
	
	public String getFormatedVendorBillAfterDiscount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getVendorBillAfterDiscount());
	}
	
	public String getFormatedVendorBillAfterDiscountAndTax() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getVendorBillAfterDiscountAndTax());
	}
	
	public double getVendorBillAfterDiscountAndTaxAndPrepayment() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getVendorBillDetailAmount() - getVendorBillDiscountAmount() + getVendorBillTaxAmount() - getVendorBillPrepaymentAmount());
	}

	public String getFormatedVendorBillAfterDiscountAndTaxAndPrepayment() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getVendorBillAfterDiscountAndTaxAndPrepayment());
	}
	
	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}

}