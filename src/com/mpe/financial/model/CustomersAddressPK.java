package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseCustomersAddressPK;

public class CustomersAddressPK extends BaseCustomersAddressPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomersAddressPK () {}
	
	public CustomersAddressPK (
		java.lang.String invoiceTaxAddress,
		java.lang.String invoiceAddress,
		java.lang.String deliveryAddress,
		com.mpe.financial.model.Customers customers) {

		super (
			invoiceTaxAddress,
			invoiceAddress,
			deliveryAddress,
			customers);
	}
/*[CONSTRUCTOR MARKER END]*/


}