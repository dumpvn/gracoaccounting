package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemCategory;



public class ItemCategory extends BaseItemCategory {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemCategory () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemCategory (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ItemCategory (
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