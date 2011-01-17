package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseMember;



public class Member extends BaseMember {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Member () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Member (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Member (
		long id,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Location location,
		com.mpe.financial.model.MemberDiscount memberDiscount,
		java.lang.String memberNumber,
		java.util.Date memberDate,
		boolean active) {

		super (
			id,
			organization,
			location,
			memberDiscount,
			memberNumber,
			memberDate,
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