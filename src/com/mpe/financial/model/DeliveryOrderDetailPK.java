package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseDeliveryOrderDetailPK;

public class DeliveryOrderDetailPK extends BaseDeliveryOrderDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DeliveryOrderDetailPK () {}
	
	public DeliveryOrderDetailPK (
		com.mpe.financial.model.DeliveryOrder deliveryOrder,
		com.mpe.financial.model.Item item) {

		super (
			deliveryOrder,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}