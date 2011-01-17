package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseStockOpnameDetailPK;

public class StockOpnameDetailPK extends BaseStockOpnameDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public StockOpnameDetailPK () {}
	
	public StockOpnameDetailPK (
		com.mpe.financial.model.StockOpname stockOpname,
		com.mpe.financial.model.Item item) {

		super (
			stockOpname,
			item);
	}
/*[CONSTRUCTOR MARKER END]*/


}