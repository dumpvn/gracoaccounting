package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemPriceCategory;



public class ItemPriceCategory extends BaseItemPriceCategory {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemPriceCategory () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemPriceCategory (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ItemPriceCategory (
		long id,
		java.lang.String name) {

		super (
			id,
			name);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}


}