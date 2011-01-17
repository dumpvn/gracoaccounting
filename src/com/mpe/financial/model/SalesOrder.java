package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedList;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseSalesOrder;



public class SalesOrder extends BaseSalesOrder {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SalesOrder () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SalesOrder (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SalesOrder (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Customers customer,
		java.util.Date orderDate,
		java.lang.String number,
		java.lang.String status,
		java.lang.String deliveryOrderStatus,
		java.lang.String customerPaymentStatus,
		double exchangeRate) {

		super (
			id,
			currency,
			organization,
			customer,
			orderDate,
			number,
			status,
			deliveryOrderStatus,
			customerPaymentStatus,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public double getSalesOrderDetailAmount() {
		double x = 0;
		Iterator iterator = getSalesOrderDetails()!=null?getSalesOrderDetails().iterator():new LinkedList().iterator();
		while (iterator.hasNext()) {
			SalesOrderDetail salesOrderDetail = (SalesOrderDetail)iterator.next();
			x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), salesOrderDetail.getPriceQuantityAfterDiscountTax());
		}
		return x;
	}
	
	public double getAmountTax() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), (getSalesOrderDetailAmount()-getAmountDiscount()) * getTaxAmount()/100);
	}
	
	public String getFormatedAmountTax() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountTax());
	}
	
	public double getAmountDiscount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getSalesOrderDetailAmount() * getDiscountProcent()/100);
	}
	
	public String getFormatedAmountDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountDiscount());
	}
	
	public String getFormatedSalesOrderDetailAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getSalesOrderDetailAmount());
	}
	
	public double getAmountAfterTaxAndDiscount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getSalesOrderDetailAmount() - getAmountDiscount() + getAmountTax());
	}
	
	public String getFormatedAmountAfterTaxAndDiscount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterTaxAndDiscount());
	}
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}

	public double getAmountAfterDiscount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getSalesOrderDetailAmount() - getAmountDiscount());
	}
	
	public String getFormatedAmountAfterDiscount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterDiscount());
	}
	
	public String getFormatedOrderDate() {
	    return Formater.getFormatedDate(getOrderDate());
	}
	
	public String getFormatedDeliveryDate() {
	    return Formater.getFormatedDate(getDeliveryDate());
	}
	
	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}

}