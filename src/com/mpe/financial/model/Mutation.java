package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseMutation;



public class Mutation extends BaseMutation {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Mutation () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Mutation (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Mutation (
		long id,
		com.mpe.financial.model.Warehouse toWarehouse,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Warehouse fromWarehouse,
		java.util.Date mutationDate,
		java.lang.String number) {

		super (
			id,
			toWarehouse,
			organization,
			fromWarehouse,
			mutationDate,
			number);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}


}