package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseJournalDetail;



public class JournalDetail extends BaseJournalDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public JournalDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public JournalDetail (com.mpe.financial.model.JournalDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public JournalDetail (
		com.mpe.financial.model.JournalDetailPK id,
		double amount) {

		super (
			id,
			amount);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedAmount() {
		return Formater.getFormatedOutput(getId().getJournal().getNumberOfDigit(), getAmount()>0?getAmount():-getAmount());
	}

	public String getFormatedAmountConversion() {
		return Formater.getFormatedOutput(getId().getJournal().getNumberOfDigit(), getAmount()>0?(getAmount()*getId().getJournal().getExchangeRate()):-(getAmount()*getId().getJournal().getExchangeRate()));
	}
	
	public String getAbsFormatedAmount() {
		return Formater.getFormatedOutput(getId().getJournal().getNumberOfDigit(), Math.abs(getAmount()));		
	}
}