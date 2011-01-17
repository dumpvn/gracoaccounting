package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseDegree;



public class Degree extends BaseDegree {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Degree () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Degree (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Degree (
		long id,
		java.lang.String code,
		java.lang.String name) {

		super (
			id,
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