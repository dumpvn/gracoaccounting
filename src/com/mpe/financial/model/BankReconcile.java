package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseBankReconcile;



public class BankReconcile extends BaseBankReconcile {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public BankReconcile () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public BankReconcile (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public BankReconcile (
		long id,
		com.mpe.financial.model.Organization organization,
		java.util.Date reconcileDate,
		double beginningBalance,
		boolean posted) {

		super (
			id,
			organization,
			reconcileDate,
			beginningBalance,
			posted);
	}

/*[CONSTRUCTOR MARKER END]*/


}