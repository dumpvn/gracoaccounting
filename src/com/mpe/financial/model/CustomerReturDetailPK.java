package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseCustomerReturDetailPK;

public class CustomerReturDetailPK extends BaseCustomerReturDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomerReturDetailPK () {}
	
	public CustomerReturDetailPK (
		com.mpe.financial.model.CustomerRetur customerRetur,
		com.mpe.financial.model.Item item) {

		super (
			customerRetur,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}