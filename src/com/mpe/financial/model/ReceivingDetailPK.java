package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseReceivingDetailPK;

public class ReceivingDetailPK extends BaseReceivingDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ReceivingDetailPK () {}
	
	public ReceivingDetailPK (
		com.mpe.financial.model.Receiving receiving,
		com.mpe.financial.model.Item item) {

		super (
			receiving,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}