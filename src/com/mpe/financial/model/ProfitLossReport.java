package com.mpe.financial.model;

import java.util.LinkedList;
import java.util.List;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseProfitLossReport;



public class ProfitLossReport extends BaseProfitLossReport {
	private static final long serialVersionUID = 1L;
	
	List list = new LinkedList();

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ProfitLossReport () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ProfitLossReport (long chartOfAccountId) {
		super(chartOfAccountId);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAmount());
	}
	
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