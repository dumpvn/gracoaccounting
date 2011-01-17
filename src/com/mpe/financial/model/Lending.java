package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseLending;



public class Lending extends BaseLending {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Lending () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Lending (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Lending (
		long id,
		com.mpe.financial.model.Salesman salesman,
		com.mpe.financial.model.Organization organization,
		java.lang.String number,
		java.util.Date lendingDate,
		java.lang.String status) {

		super (
			id,
			salesman,
			organization,
			number,
			lendingDate,
			status);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	public String getFormatedLendingDate() {
    return Formater.getFormatedDate(getLendingDate());
	}

	public String getFormatedReturDate() {
    return Formater.getFormatedDate(getReturDate());
	}

}