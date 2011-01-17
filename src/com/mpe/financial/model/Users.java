package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseUsers;



public class Users extends BaseUsers {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Users () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Users (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Users (
		long id,
		java.lang.String userName,
		java.lang.String userPass) {

		super (
			id,
			userName,
			userPass);
	}

/*[CONSTRUCTOR MARKER END]*/


}