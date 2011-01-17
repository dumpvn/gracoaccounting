package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseOrganization;



public class Organization extends BaseOrganization {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Organization () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Organization (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Organization (
		long id,
		java.lang.String name) {

		super (
			id,
			name);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedNpwpDate() {
	    return Formater.getFormatedDate(getNpwpDate());
	}


}