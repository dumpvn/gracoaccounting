package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemPosReportPenjualanGlobal;



public class ItemPosReportPenjualanGlobal extends BaseItemPosReportPenjualanGlobal {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemPosReportPenjualanGlobal () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemPosReportPenjualanGlobal (long itemId) {
		super(itemId);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedPrice() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPrice());
	}


}