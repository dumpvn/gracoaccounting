package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BasePaymentToVendor;



public class PaymentToVendor extends BasePaymentToVendor {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PaymentToVendor () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public PaymentToVendor (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public PaymentToVendor (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Vendors vendor,
		com.mpe.financial.model.BankAccount bankAccount,
		java.util.Date paymentDate,
		java.lang.String number,
		double exchangeRate) {

		super (
			id,
			currency,
			organization,
			vendor,
			bankAccount,
			paymentDate,
			number,
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
	
	public double getVendorBillAmount() {
		double x = 0;
		try {
			Set set = getPaymentToVendorDetails()!=null?getPaymentToVendorDetails():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				PaymentToVendorDetail paymentToVendorDetail = (PaymentToVendorDetail)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), (paymentToVendorDetail.getVendorBillAmount() * paymentToVendorDetail.getVendorBillExchangeRate()));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public double getPaymentToVendorDetailAmount() {
		double x = 0;
		try {
			Set set = getPaymentToVendorDetails()!=null?getPaymentToVendorDetails():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				PaymentToVendorDetail paymentToVendorDetail = (PaymentToVendorDetail)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), (paymentToVendorDetail.getPaymentAmount()));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public double getReturToVendorAmount() {
		double x = 0;
		try {
			Set set = getReturToVendors()!=null?getReturToVendors():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				ReturToVendor returToVendor = (ReturToVendor)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), (returToVendor.getAmountAfterTaxAndDiscount()));
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public double getPaymentAmount() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getPaymentToVendorDetailAmount() - getReturToVendorAmount());
	}
	
	public String getFormatedPaymentAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPaymentAmount());
	}
	
	public String getFormatedReturToVendorAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getReturToVendorAmount());
	}
	
	public String getFormatedPaymentToVendorDetailAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPaymentToVendorDetailAmount());
	}
	
	public String getFormatedVendorBillAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getVendorBillAmount());
	}

	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}
	
}