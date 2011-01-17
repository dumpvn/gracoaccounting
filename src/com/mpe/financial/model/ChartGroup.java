package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseChartGroup;



public class ChartGroup extends BaseChartGroup {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ChartGroup () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ChartGroup (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ChartGroup (
		long id,
		java.lang.String groups,
		java.lang.String name,
		boolean debit) {

		super (
			id,
			groups,
			name,
			debit);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}


}