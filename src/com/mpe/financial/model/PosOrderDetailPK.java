package com.mpe.financial.model;

import com.mpe.financial.model.base.BasePosOrderDetailPK;

public class PosOrderDetailPK extends BasePosOrderDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PosOrderDetailPK () {}
	
	public PosOrderDetailPK (
		com.mpe.financial.model.PosOrder posOrder,
		com.mpe.financial.model.Item item) {

		super (
			posOrder,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}