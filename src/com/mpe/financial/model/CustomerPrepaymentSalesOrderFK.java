package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseCustomerPrepaymentSalesOrderFK;



public class CustomerPrepaymentSalesOrderFK extends BaseCustomerPrepaymentSalesOrderFK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomerPrepaymentSalesOrderFK () {
		super();
	}

	/**
	 * Constructor for required fields
	 */
	public CustomerPrepaymentSalesOrderFK (
		double amount) {

		super (
			amount);
	}

/*[CONSTRUCTOR MARKER END]*/


}