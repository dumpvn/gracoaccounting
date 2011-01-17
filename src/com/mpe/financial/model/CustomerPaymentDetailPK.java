package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseCustomerPaymentDetailPK;

public class CustomerPaymentDetailPK extends BaseCustomerPaymentDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomerPaymentDetailPK () {}
	
	public CustomerPaymentDetailPK (
		com.mpe.financial.model.CustomerPayment customerPayment,
		java.lang.String identity) {

		super (
			customerPayment,
			identity);
	}
/*[CONSTRUCTOR MARKER END]*/


}