package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseItemUsageDetailPK;

public class ItemUsageDetailPK extends BaseItemUsageDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemUsageDetailPK () {}
	
	public ItemUsageDetailPK (
		com.mpe.financial.model.ItemUsage itemUsage,
		com.mpe.financial.model.Item item) {

		super (
			itemUsage,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}