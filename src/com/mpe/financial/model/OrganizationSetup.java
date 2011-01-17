package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseOrganizationSetup;



public class OrganizationSetup extends BaseOrganizationSetup {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public OrganizationSetup () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public OrganizationSetup (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public OrganizationSetup (
		long id,
		java.lang.String name) {

		super (
			id,
			name);
	}

/*[CONSTRUCTOR MARKER END]*/


}