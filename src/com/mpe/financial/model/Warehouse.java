package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseWarehouse;



public class Warehouse extends BaseWarehouse {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Warehouse () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Warehouse (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Warehouse (
		long id,
		com.mpe.financial.model.Organization organization,
		java.lang.String number,
		java.lang.String name) {

		super (
			id,
			organization,
			number,
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