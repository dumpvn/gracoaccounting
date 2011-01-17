package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseCustomerRetur;



public class CustomerRetur extends BaseCustomerRetur {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomerRetur () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CustomerRetur (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CustomerRetur (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Customers customer,
		com.mpe.financial.model.DeliveryOrder deliveryOrder,
		java.util.Date returDate,
		java.lang.String number,
		double exchangeRate) {

		super (
			id,
			currency,
			organization,
			customer,
			deliveryOrder,
			returDate,
			number,
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
	
	public double getCustomerReturDetailAmount() {
		double x = 0;
		try {
			Set set = getCustomerReturDetails()!=null?getCustomerReturDetails():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				CustomerReturDetail customerReturDetail = (CustomerReturDetail)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), customerReturDetail.getPriceQuantityAfterDiscountTax());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public double getCustomerReturDetailCostPriceAmount() {
		double x = 0;
		try {
			Set set = getCustomerReturDetails()!=null?getCustomerReturDetails():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				CustomerReturDetail customerReturDetail = (CustomerReturDetail)iterator.next();
				x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), customerReturDetail.getCostPriceQuantity());
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}
	
	public double getAmountTax() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getDeliveryOrder()!=null?(getDeliveryOrder().getSalesOrder()!=null?((getCustomerReturDetailAmount()-getAmountDiscount()) * getDeliveryOrder().getSalesOrder().getTaxAmount()/100):0):0);
	}
	
	public String getFormatedAmountTax() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountTax());
	}
	
	public double getAmountDiscount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getDeliveryOrder()!=null?(getDeliveryOrder().getSalesOrder()!=null?(getCustomerReturDetailAmount() * getDeliveryOrder().getSalesOrder().getDiscountProcent()/100):0):0);
	}
	
	public double getAmountAfterDiscount() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getCustomerReturDetailAmount() - getAmountDiscount());
	}
	
	public String getFormatedAmountAfterDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterDiscount());
	}
	
	public String getFormatedAmountDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountDiscount());
	}
		
	public String getFormatedCustomerReturDetailAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getCustomerReturDetailAmount());
	}
	
	public double getAmountAfterTaxAndDiscount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getCustomerReturDetailAmount() - getAmountDiscount() + getAmountTax());
	}
	
	public String getFormatedAmountAfterTaxAndDiscount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterTaxAndDiscount());
	}

	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}

}