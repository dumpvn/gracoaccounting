package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseCustomersCommunicationPK;

public class CustomersCommunicationPK extends BaseCustomersCommunicationPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomersCommunicationPK () {}
	
	public CustomersCommunicationPK (
		java.lang.String contactPerson,
		com.mpe.financial.model.Customers customers) {

		super (
			contactPerson,
			customers);
	}
/*[CONSTRUCTOR MARKER END]*/


}