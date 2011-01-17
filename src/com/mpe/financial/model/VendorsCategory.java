package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseVendorsCategory;



public class VendorsCategory extends BaseVendorsCategory {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public VendorsCategory () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public VendorsCategory (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public VendorsCategory (
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