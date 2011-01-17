package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BasePrepaymentToVendor;



public class PrepaymentToVendor extends BasePrepaymentToVendor {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PrepaymentToVendor () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public PrepaymentToVendor (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public PrepaymentToVendor (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Vendors vendor,
		com.mpe.financial.model.PurchaseOrder purchaseOrder,
		java.util.Date prepaymentDate,
		java.lang.String number,
		java.lang.String status,
		java.lang.String vendorBillStatus,
		double exchangeRate) {

		super (
			id,
			currency,
			organization,
			vendor,
			purchaseOrder,
			prepaymentDate,
			number,
			status,
			vendorBillStatus,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}

	public String getFormatedAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmount());
	}
	
	public double getVendorBillPaymentAmount() {
		double x = 0;
		try {
			Set set = getVendorBills()!=null?getVendorBills():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				VendorBillPrepaymentToVendorFK vendorBillPrepaymentToVendorFK = (VendorBillPrepaymentToVendorFK)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), vendorBillPrepaymentToVendorFK.getAmount());
			}
		}catch(Exception ex) {}
		return x;
	}
	
	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}

}