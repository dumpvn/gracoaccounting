package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseDeliveryOrder;



public class DeliveryOrder extends BaseDeliveryOrder {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DeliveryOrder () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DeliveryOrder (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DeliveryOrder (
		long id,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Customers customer,
		java.util.Date deliveryDate,
		java.lang.String number,
		boolean posted,
		boolean bonKuning,
		boolean store,
		double exchangeRate) {

		super (
			id,
			organization,
			customer,
			deliveryDate,
			number,
			posted,
			bonKuning,
			store,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	// for bon kuning
	public double getDiscountAmount1() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getDeliveryOrderDetailAmount() * getDiscount1()/100);
	}
	
	public String getFormatedDiscountAmount1() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getDiscountAmount1());
	}
	
	public double getAmountAfterDiscount1() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getDeliveryOrderDetailAmount() - getDiscountAmount1());
	}
	
	public String getFormatedAmountAfterDiscount1() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterDiscount1());
	}
	
	public double getDiscountAmount2() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getAmountAfterDiscount1() * getDiscount2()/100);
	}
	
	public String getFormatedDiscountAmount2() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getDiscountAmount2());
	}
	
	public double getAmountAfterDiscount1Discount2() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getAmountAfterDiscount1() - getDiscountAmount2());
	}
	
	public String getFormatedAmountAfterDiscount1Discount2() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterDiscount1Discount2());
	}
	
	public double getDiscountAmount3() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getAmountAfterDiscount1Discount2() * getDiscount3()/100);
	}
	
	public String getFormatedDiscountAmount3() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getDiscountAmount3());
	}
	
	public double getAmountAfterDiscount1Discount2Discount3() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getAmountAfterDiscount1Discount2() - getDiscountAmount3());
	}
	
	public String getFormatedDeliveryDate() {
	    return Formater.getFormatedDate(getDeliveryDate());
	}
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	public double getDeliveryOrderDetailCostPriceAmount() {
	    double a = 0;
	    try {
  			Set set = getDeliveryOrderDetails()!=null?getDeliveryOrderDetails():new LinkedHashSet();
  			Iterator iterator = set.iterator();
  			while (iterator.hasNext()) {
  				DeliveryOrderDetail deliveryOrderDetail = (DeliveryOrderDetail)iterator.next();
  				a = a + Formater.getFormatedOutputResult(getNumberOfDigit(), deliveryOrderDetail.getCostPriceQuantity());
  			}
  		} catch(Exception ex) {
  			ex.printStackTrace();
  		}
	    return a;
	}
	
	public double getDeliveryOrderDetailAmount() {
		double x = 0;
		Iterator iterator = getDeliveryOrderDetails()!=null?getDeliveryOrderDetails().iterator():new LinkedList().iterator();
		while (iterator.hasNext()) {
		  DeliveryOrderDetail deliveryOrderDetail = (DeliveryOrderDetail)iterator.next();
			x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), deliveryOrderDetail.getPriceQuantityAfterDiscountTax());
		}
		return x;
	}
	
	public double getAmountTax() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getSalesOrder()!=null?((getDeliveryOrderDetailAmount()-getAmountDiscount()) * getSalesOrder().getTaxAmount()/100):0);
	}
	
	public String getFormatedAmountTax() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountTax());
	}
	
	public double getAmountDiscount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getSalesOrder()!=null?(getDeliveryOrderDetailAmount() * getSalesOrder().getDiscountProcent()/100):0);
	}
	
	public String getFormatedAmountDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountDiscount());
	}
	
	public String getFormatedDeliveryOrderDetailAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getDeliveryOrderDetailAmount());
	}
	
	public double getAmountAfterTaxAndDiscount() {
		if (isBonKuning()) {
		    return getAmountAfterDiscount1Discount2Discount3();
		} else
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getDeliveryOrderDetailAmount() - getAmountDiscount() + getAmountTax());
	}
	
	public String getFormatedAmountAfterTaxAndDiscount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterTaxAndDiscount());
	}

	public double getAmountAfterDiscount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getDeliveryOrderDetailAmount() - getAmountDiscount());
	}
	
	public String getFormatedAmountAfterDiscount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterDiscount());
	}
		
}