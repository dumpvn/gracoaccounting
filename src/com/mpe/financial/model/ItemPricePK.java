package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseItemPricePK;

public class ItemPricePK extends BaseItemPricePK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemPricePK () {}
	
	public ItemPricePK (
		com.mpe.financial.model.ItemPriceCategory itemPriceCategory,
		com.mpe.financial.model.Item item) {

		super (
			itemPriceCategory,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}