package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseMemberDiscount;



public class MemberDiscount extends BaseMemberDiscount {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MemberDiscount () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MemberDiscount (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MemberDiscount (
		long id,
		com.mpe.financial.model.Location location,
		java.lang.String name) {

		super (
			id,
			location,
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