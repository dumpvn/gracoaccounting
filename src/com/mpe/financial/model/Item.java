package com.mpe.financial.model;

import java.util.Iterator;
import java.util.Set;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItem;



public class Item extends BaseItem {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Item () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Item (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Item (
		long id,
		com.mpe.financial.model.Organization organization,
		java.lang.String name,
		boolean active) {

		super (
			id,
			organization,
			name,
			active);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	public double getPrice() {
	    double z = 0;
	    try {
	        Set set = getItemPrices();
	        Iterator iterator = set.iterator();
	        while (iterator.hasNext()) {
	            ItemPrice itemPrice = (ItemPrice)iterator.next();
	            if (itemPrice.isDefault()==true) z = itemPrice.getPrice();
	        }
	    }catch(Exception ex){}
	    return z;
	}
	
	public String getFormatedPrice() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPrice());
	}


}