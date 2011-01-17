package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseStockOpname;



public class StockOpname extends BaseStockOpname {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public StockOpname () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public StockOpname (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public StockOpname (
		long id,
		com.mpe.financial.model.Warehouse warehouse,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.StockOpnameType stockOpnameType,
		com.mpe.financial.model.Organization organization,
		java.util.Date stockOpnameDate,
		java.lang.String number,
		java.lang.String status) {

		super (
			id,
			warehouse,
			currency,
			stockOpnameType,
			organization,
			stockOpnameDate,
			number,
			status);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedStockOpnameDate() {
	    return Formater.getFormatedDate(getStockOpnameDate());
	}
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	public double getStockOpnameDetailAmount() {
		double x = 0;
		try {
			Set set = getStockOpnameDetails()!=null?getStockOpnameDetails():new LinkedHashSet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				StockOpnameDetail stockOpnameDetail = (StockOpnameDetail)iterator.next();
				x = x + stockOpnameDetail.getPriceDifference();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return x;
	}

	public String getFormatedStockOpnameDetailAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getStockOpnameDetailAmount());
	}

}