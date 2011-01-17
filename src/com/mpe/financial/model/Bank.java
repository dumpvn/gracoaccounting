package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseBank;



public class Bank extends BaseBank {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Bank () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Bank (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Bank (
		long id,
		com.mpe.financial.model.Organization organization,
		java.lang.String name) {

		super (
			id,
			organization,
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