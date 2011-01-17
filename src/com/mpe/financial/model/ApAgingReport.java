package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseApAgingReport;



public class ApAgingReport extends BaseApAgingReport {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ApAgingReport () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ApAgingReport (long vendorId) {
		super(vendorId);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedAging0() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAging0());
	}
	
	public String getFormatedAging030() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAging030());
	}
	
	public String getFormatedAging3060() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAging3060());
	}
	
	public String getFormatedAging6090() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAging6090());
	}
	
	public String getFormatedAging90() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAging90());
	}
	
	public double getAgingTotal() {
	    return getAging0() + getAging030() + getAging3060() + getAging6090() + getAging90();
	}
	
	public String getFormatedAgingTotal() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAgingTotal());
	}


}