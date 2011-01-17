package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseInventory;



public class Inventory extends BaseInventory {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Inventory () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Inventory (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Inventory (
		long id,
		com.mpe.financial.model.Organization organization,
		java.lang.String type,
		java.lang.String name,
		java.lang.String code,
		boolean active) {

		super (
			id,
			organization,
			type,
			name,
			code,
			active);
	}

/*[CONSTRUCTOR MARKER END]*/


}