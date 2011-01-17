package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseApAgingDetailReport;



public class ApAgingDetailReport extends BaseApAgingDetailReport {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ApAgingDetailReport () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ApAgingDetailReport (long id) {
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAmount());
	}
	
	public String getFormatedBillDate() {
	    return Formater.getFormatedDate(getBillDate());
	}
	
	public String getFormatedBillDue() {
	    return Formater.getFormatedDate(getBillDue());
	}


}