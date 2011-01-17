package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseCreditCardDetail;



public class CreditCardDetail extends BaseCreditCardDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CreditCardDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CreditCardDetail (com.mpe.financial.model.CreditCardDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CreditCardDetail (
		com.mpe.financial.model.CreditCardDetailPK id,
		boolean chargeToLocation) {

		super (
			id,
			chargeToLocation);
	}

/*[CONSTRUCTOR MARKER END]*/


}