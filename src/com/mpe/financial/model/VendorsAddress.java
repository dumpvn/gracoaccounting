package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseVendorsAddress;



public class VendorsAddress extends BaseVendorsAddress {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public VendorsAddress () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public VendorsAddress (com.mpe.financial.model.VendorsAddressPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public VendorsAddress (
		com.mpe.financial.model.VendorsAddressPK id,
		java.lang.String name) {

		super (
			id,
			name);
	}

/*[CONSTRUCTOR MARKER END]*/


}