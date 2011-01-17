package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseItemGroupPK;

public class ItemGroupPK extends BaseItemGroupPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemGroupPK () {}
	
	public ItemGroupPK (
		com.mpe.financial.model.Item item,
		com.mpe.financial.model.Item group) {

		super (
			item,
			group);
	}
/*[CONSTRUCTOR MARKER END]*/


}