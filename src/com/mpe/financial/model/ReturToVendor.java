package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseReturToVendor;



public class ReturToVendor extends BaseReturToVendor {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ReturToVendor () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ReturToVendor (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ReturToVendor (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Vendors vendor,
		com.mpe.financial.model.Receiving receiving,
		java.util.Date returDate,
		java.lang.String number,
		boolean posted,
		double exchangeRate) {

		super (
			id,
			currency,
			organization,
			vendor,
			receiving,
			returDate,
			number,
			posted,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedReturDate() {
	    return Formater.getFormatedDate(getReturDate());
	}
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}

	public double getReturToVendorDetailAmount() {
		double x = 0;
		try {
			Set set = getReturToVendorDetails()!=null?getReturToVendorDetails():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				ReturToVendorDetail returToVendorDetail = (ReturToVendorDetail)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), returToVendorDetail.getPriceQuantityAfterDiscountTax());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public double getAmountTax() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getReceiving()!=null?(getReceiving().getPurchaseOrder()!=null?((getReturToVendorDetailAmount()-getAmountDiscount()) * getReceiving().getPurchaseOrder().getTaxAmount()/100):0):0);
	}
	
	public String getFormatedAmountTax() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountTax());
	}
	
	public double getAmountDiscount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getReceiving()!=null?(getReceiving().getPurchaseOrder()!=null?(getReturToVendorDetailAmount() * getReceiving().getPurchaseOrder().getDiscountProcent()/100):0):0);
	}
	
	public double getAmountAfterDiscount() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getReturToVendorDetailAmount() - getAmountDiscount());
	}
	
	public String getFormatedAmountAfterDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterDiscount());
	}
	
	public String getFormatedAmountDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountDiscount());
	}
	
	public String getFormatedReturToVendorDetailAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getReturToVendorDetailAmount());
	}
	
	public double getAmountAfterTaxAndDiscount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getReturToVendorDetailAmount() - getAmountDiscount() + getAmountTax());
	}
	
	public String getFormatedAmountAfterTaxAndDiscount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterTaxAndDiscount());
	}

}