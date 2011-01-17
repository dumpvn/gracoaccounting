package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseDepartment;



public class Department extends BaseDepartment {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Department () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Department (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Department (
		long id,
		com.mpe.financial.model.Organization organization,
		java.lang.String code,
		java.lang.String name) {

		super (
			id,
			organization,
			code,
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