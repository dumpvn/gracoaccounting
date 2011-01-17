package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseProject;



public class Project extends BaseProject {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Project () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Project (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Project (
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
	
	public String getFormatedStartDate() {
	    return Formater.getFormatedDate(getStartDate());
	}
	
	public String getFormatedEndDate() {
    return Formater.getFormatedDate(getEndDate());
	}


}