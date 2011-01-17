package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemPosReportStockPenjualanGlobalTokoCounter;



public class ItemPosReportStockPenjualanGlobalTokoCounter extends BaseItemPosReportStockPenjualanGlobalTokoCounter {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemPosReportStockPenjualanGlobalTokoCounter () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemPosReportStockPenjualanGlobalTokoCounter (long itemId) {
		super(itemId);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedPrice() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPrice());
	}


}