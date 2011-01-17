package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemStatus;



public class ItemStatus extends BaseItemStatus {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemStatus () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemStatus (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ItemStatus (
		long id,
		java.lang.String code,
		int variable,
		boolean defaultStatus) {

		super (
			id,
			code,
			variable,
			defaultStatus);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}


}