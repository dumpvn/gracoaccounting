package com.mpe.financial.model;

import java.util.Iterator;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseJournal;



public class Journal extends BaseJournal {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Journal () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Journal (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Journal (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		java.lang.String number,
		java.util.Date journalDate,
		boolean posted,
		double exchangeRate) {

		super (
			id,
			currency,
			organization,
			number,
			journalDate,
			posted,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedJournalDate() {
    return Formater.getFormatedDate(getJournalDate());
	}
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	public double getAmount() {
		double x = 0;
		Iterator iterator = getJournalDetails().iterator();
		while (iterator.hasNext()) {
			JournalDetail journalDetail = (JournalDetail)iterator.next();
			if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
				if (journalDetail.getAmount()>0) {
					x = x + journalDetail.getAmount();
				}
			} else if (journalDetail.getId().getChartOfAccount().isDebit()==false){
				if (journalDetail.getAmount()<0) {
					x = x - journalDetail.getAmount();
				}
			}
		}
		return x;
	}
	
	public String getFormatedAmount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAmount());
	}

	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}

}