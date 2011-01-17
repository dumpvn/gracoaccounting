package com.mpe.financial.model;

import java.util.Iterator;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BasePosOrder;



public class PosOrder extends BasePosOrder {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PosOrder () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public PosOrder (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public PosOrder (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Location location,
		java.util.Date posOrderDate,
		java.lang.String paymentMethod,
		java.lang.String posOrderNumber,
		boolean posted) {

		super (
			id,
			currency,
			organization,
			location,
			posOrderDate,
			paymentMethod,
			posOrderNumber,
			posted);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public double getPosOrderDetailQuantityPrice() {
	    Set set = getPosOrderDetails();
	    double a = 0;
	    if (set!=null) {
	        Iterator iterator = set.iterator();
	        while (iterator.hasNext()) {
	            PosOrderDetail posOrderDetail = (PosOrderDetail)iterator.next();
	            a = a + posOrderDetail.getQuantityPrice();
	        }
	    }
	    return a;
	}
	
	public double getDiscountAmount() {
	    return (getDiscountProcent()/100) * getPosOrderDetailQuantityPrice();
	}
	
	public double getTotalAmountAfterDiscount() {
	    return getPosOrderDetailQuantityPrice() - getDiscountAmount();
	}
	
	public double getTaxAmount() {
	    return getTotalAmountAfterDiscount() * (getTaxProcent()/100);
	}

	public double getTotalAmountAfterDiscountAndTax() {
	    return getTotalAmountAfterDiscount() + getTaxAmount();
	}
	
	public String getFormatedPosOrderDetailQuantityPrice() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPosOrderDetailQuantityPrice());
	}
	
	public String getFormatedDiscountAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getDiscountAmount());
	}
	
	public String getFormatedTotalAmountAfterDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getTotalAmountAfterDiscount());
	}
	
	public String getFormatedTaxAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getTaxAmount());
	}
	
	public String getFormatedTotalAmountAfterDiscountAndTax() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getTotalAmountAfterDiscountAndTax());
	}
	
	public String getFormatedStartTime() {
	    return Formater.getFormatedTime(getStartTime());
	}
	
	public String getFormatedEndTime() {
	    return Formater.getFormatedTime(getStartTime());
	}
	
	public String getFormatedPosOrderDate() {
	    return Formater.getFormatedDate(getPosOrderDate());
	}

}