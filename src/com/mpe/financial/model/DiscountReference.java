package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseDiscountReference;



public class DiscountReference extends BaseDiscountReference {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DiscountReference () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DiscountReference (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DiscountReference (
		long id,
		com.mpe.financial.model.Organization organization,
		java.lang.String discountType,
		boolean active) {

		super (
			id,
			organization,
			discountType,
			active);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedValidFrom() {
	    return Formater.getFormatedDate(getValidFrom());
	}
	
	public String getFormatedValidTo() {
	    return Formater.getFormatedDate(getValidTo());
	}
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}


}