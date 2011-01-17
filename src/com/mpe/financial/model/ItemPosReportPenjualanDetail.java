package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemPosReportPenjualanDetail;



public class ItemPosReportPenjualanDetail extends BaseItemPosReportPenjualanDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemPosReportPenjualanDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemPosReportPenjualanDetail (long posOrderId) {
		super(posOrderId);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedDate() {
	    return Formater.getFormatedDate(getDate());
	}


}