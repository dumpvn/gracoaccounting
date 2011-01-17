package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemUnit;



public class ItemUnit extends BaseItemUnit {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemUnit () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemUnit (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ItemUnit (
		long id,
		java.lang.String name,
		java.lang.String symbol,
		boolean base) {

		super (
			id,
			name,
			symbol,
			base);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}


}