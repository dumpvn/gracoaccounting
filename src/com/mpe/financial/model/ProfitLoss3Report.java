package com.mpe.financial.model;

import java.util.LinkedList;
import java.util.List;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseProfitLoss3Report;



public class ProfitLoss3Report extends BaseProfitLoss3Report {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ProfitLoss3Report () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ProfitLoss3Report (long chartOfAccountId) {
		super(chartOfAccountId);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCurrentAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getCurrentAmount());
	}
	
	public String getFormatedLastAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getLastAmount());
	}
	
	public String getFormatedYtdAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getYtdAmount());
	}
	
	public String getFormatedFromToAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getFromToAmount());
	}
	
	List list = new LinkedList();
	
    /**
     * @return Returns the set.
     */
    public List getList() {
        return list;
    }
    /**
     * @param set The set to set.
     */
    public void setList(List list) {
        this.list = list;
    }


}