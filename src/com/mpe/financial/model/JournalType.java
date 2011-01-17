package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseJournalType;



public class JournalType extends BaseJournalType {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public JournalType () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public JournalType (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public JournalType (
		long id,
		java.lang.String name) {

		super (
			id,
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