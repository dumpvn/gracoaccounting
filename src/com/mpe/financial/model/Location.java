package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseLocation;



public class Location extends BaseLocation {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Location () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Location (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Location (
		long id,
		com.mpe.financial.model.Organization organization,
		java.lang.String code,
		java.lang.String name,
		boolean active) {

		super (
			id,
			organization,
			code,
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


}