package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseChartOfAccount;



public class ChartOfAccount extends BaseChartOfAccount {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ChartOfAccount () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ChartOfAccount (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ChartOfAccount (
		long id,
		java.lang.String number,
		java.lang.String name) {

		super (
			id,
			number,
			name);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getNumberName() {
	    return getNumber() + " :: " + getName();
	}
	
	public String getNameNumber() {
    return getName() + " :: " + getNumber();
	}
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	List journalDetailList;

	public List getJournalDetailList() {
		return journalDetailList;
	}

	public void setJournalDetailList(List journalDetailList) {
		this.journalDetailList = journalDetailList;
	}
	
	int numberOfDigit;
	
	
	double firstAmount;

	public double getFirstAmount() {
		return firstAmount;
	}

	public void setFirstAmount(double firstAmount) {
		this.firstAmount = firstAmount;
	}
	
	public String getFormatedFirstAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getFirstAmount());
	}

	public int getNumberOfDigit() {
		return numberOfDigit;
	}

	public void setNumberOfDigit(int numberOfDigit) {
		this.numberOfDigit = numberOfDigit;
	}
	

	public double getCreditAmount() {
		double creditAmount = 0;
		Iterator iterator = getJournalDetailList()!=null?getJournalDetailList().iterator():new LinkedList().iterator();
		while (iterator.hasNext()) {
			JournalDetail journalDetail = (JournalDetail)iterator.next();
			if (!journalDetail.getId().getChartOfAccount().isDebit() && journalDetail.getAmount()>=0) {
				creditAmount = creditAmount + journalDetail.getAmount();
			} else if (journalDetail.getId().getChartOfAccount().isDebit() && journalDetail.getAmount()<0) {
				creditAmount = creditAmount - journalDetail.getAmount();
			}
			
		}
		return creditAmount;
	}


	public double getDebitAmount() {
		double debitAmount = 0;
		Iterator iterator = getJournalDetailList()!=null?getJournalDetailList().iterator():new LinkedList().iterator();
		while (iterator.hasNext()) {
			JournalDetail journalDetail = (JournalDetail)iterator.next();
			if (journalDetail.getId().getChartOfAccount().isDebit() && journalDetail.getAmount()>=0) {
				debitAmount = debitAmount + journalDetail.getAmount();
			} else if (!journalDetail.getId().getChartOfAccount().isDebit() && journalDetail.getAmount()<0) {
				debitAmount = debitAmount - journalDetail.getAmount();
			}
			
		}
		return debitAmount;
	}


	public String getFormatedDebitAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getDebitAmount());
	}
	
	public String getFormatedCreditAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getCreditAmount());
	}
	
	


}