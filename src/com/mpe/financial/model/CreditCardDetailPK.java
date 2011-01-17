package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseCreditCardDetailPK;

public class CreditCardDetailPK extends BaseCreditCardDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CreditCardDetailPK () {}
	
	public CreditCardDetailPK (
		com.mpe.financial.model.CreditCard creditCard,
		com.mpe.financial.model.Location location) {

		super (
			creditCard,
			location);
	}
/*[CONSTRUCTOR MARKER END]*/


}