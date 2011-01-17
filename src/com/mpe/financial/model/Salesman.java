package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseSalesman;



public class Salesman extends BaseSalesman {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Salesman () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Salesman (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Salesman (
		long id,
		com.mpe.financial.model.Degree degree,
		com.mpe.financial.model.Organization organization,
		java.lang.String code,
		java.lang.String fullName,
		java.lang.String nickName,
		java.lang.String address,
		boolean active) {

		super (
			id,
			degree,
			organization,
			code,
			fullName,
			nickName,
			address,
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