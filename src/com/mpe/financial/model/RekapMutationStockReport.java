package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseRekapMutationStockReport;



public class RekapMutationStockReport extends BaseRekapMutationStockReport {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public RekapMutationStockReport () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public RekapMutationStockReport (long itemId) {
		super(itemId);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedPrice() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPrice());
	}
	
	public String getFormatedBeginningTotal() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getBeginningTotal());
	}
	
	public String getFormatedReceivingTotal() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getReceivingTotal());
	}
	
	public String getFormatedReturToVendorTotal() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getReturToVendorTotal());
	}
	
	public String getFormatedDeliveryOrderTotal() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getDeliveryOrderTotal());
	}
	
	public String getFormatedCustomerReturTotal() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getCustomerReturTotal());
	}
	
	public String getFormatedStockOpnameTotal() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getStockOpnameTotal());
	}

	public double getEndingQuantity() {
	    return getBeginningQuantity() + getReceivingQuantity() - getReturToVendorQuantity() - getDeliveryOrderQuantity() + getCustomerReturQuantity() - getStockOpnameQuantity();
	}

	public double getEndingTotal() {
	    return getBeginningTotal() + getReceivingTotal() - getReturToVendorTotal() - getDeliveryOrderTotal() + getCustomerReturTotal() - getStockOpnameTotal();
	}
	
	public String getFormatedEndingTotal() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getEndingTotal());
	}
	
}