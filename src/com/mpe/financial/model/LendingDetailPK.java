package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseLendingDetailPK;

public class LendingDetailPK extends BaseLendingDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public LendingDetailPK () {}
	
	public LendingDetailPK (
		com.mpe.financial.model.Lending lending,
		com.mpe.financial.model.Item item) {

		super (
			lending,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}