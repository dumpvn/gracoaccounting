package com.mpe.financial.model;

import java.util.LinkedList;
import java.util.List;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseProfitLossReportGroup;



public class ProfitLossReportGroup extends BaseProfitLossReportGroup {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ProfitLossReportGroup () {
		super();
	}
	
	List list = new LinkedList();

	/**
	 * Constructor for primary key
	 */
	public ProfitLossReportGroup (long chartOfAccountId) {
		super(chartOfAccountId);
	}

/*[CONSTRUCTOR MARKER END]*/
	
  /**
   * @return Returns the list.
   */
  public List getList() {
      return list;
  }
  /**
   * @param list The list to set.
   */
  public void setList(List list) {
      this.list = list;
  }
  
	public String getFormatedAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmount());
	}


}