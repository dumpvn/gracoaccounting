package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseStockCardReport;



public class StockCardReport extends BaseStockCardReport {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public StockCardReport () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public StockCardReport (java.lang.String id) {
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	
	public String getFormatedPrice() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPrice());
	}
	
	public int getDebitQuantity() {
	    return getQuantity()>=0?getQuantity():0;
	}
	
	public int getCreditQuantity() {
	    return getQuantity()<0?-getQuantity():0;
	}
	
	public double getDebitTotal() {
	    return getDebitQuantity() * getPrice();
	}
	
	public String getFormatedDebitTotal() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getDebitTotal());
	}
	
	public double getCreditTotal() {
	    return getCreditQuantity() * getPrice();
	}
	
	public String getFormatedCreditTotal() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getCreditTotal());
	}
	
	public double getEndingQuantity() {
	    return getDebitQuantity() + getCreditQuantity();
	}
	
	public double getEndingTotal() {
	    return getDebitTotal() + getCreditTotal();
	}
	
	public String getFormatedEndingTotal() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getEndingTotal());
	}
	
	public String getFormatedStockCardDate() {
	  return Formater.getFormatedDate(getStockCardDate());
	}


}