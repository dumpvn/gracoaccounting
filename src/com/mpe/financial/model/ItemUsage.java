package com.mpe.financial.model;

import java.util.Iterator;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemUsage;



public class ItemUsage extends BaseItemUsage {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemUsage () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemUsage (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ItemUsage (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		java.lang.String number,
		java.lang.String status,
		java.util.Date usageDate) {

		super (
			id,
			currency,
			organization,
			number,
			status,
			usageDate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public double getItemUsageAmount() {
		double a = 0;
		try {
			Iterator iterator = getItemUsageDetails().iterator();
			while (iterator.hasNext()) {
				ItemUsageDetail itemUsageDetail = (ItemUsageDetail) iterator.next();
				a = a + itemUsageDetail.getItemPriceAmount();
			}
		} catch(Exception ex) {
			
		}
		return a;
	}
	
	public String getFormatedItemUsageAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getItemUsageAmount());
	}

	public String getFormatedUsageDate() {
		return Formater.getFormatedDate(getUsageDate());
	}
	
	public String getFormatedCreateOn() {
    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}

}