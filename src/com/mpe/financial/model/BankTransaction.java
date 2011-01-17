package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseBankTransaction;



public class BankTransaction extends BaseBankTransaction {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public BankTransaction () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public BankTransaction (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public BankTransaction (
		long id,
		com.mpe.financial.model.Currency currency,
		java.lang.String number,
		java.util.Date transactionDate,
		double amount,
		boolean posted,
		double exchangeRate) {

		super (
			id,
			currency,
			number,
			transactionDate,
			amount,
			posted,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	public String getFormatedAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmount());
	}

	public String getFormatedTransactionDate() {
    return Formater.getFormatedDate(getTransactionDate());
	}
	
	public boolean isDebit() {
		if (getToBankAccount()!=null) return true;
		else return false;
	}

}