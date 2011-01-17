package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseBankAccount;



public class BankAccount extends BaseBankAccount {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public BankAccount () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public BankAccount (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public BankAccount (
		long id,
		com.mpe.financial.model.Currency currency,
		java.lang.String number,
		java.lang.String name) {

		super (
			id,
			currency,
			number,
			name);
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
	
	public String getNumberName() {
		return getNumber() + " :: " + getName();
	}

	public String getBankNumberName() {
		return (getBank()!=null?getBank().getName()+" :: ":"")+getNumber() + " :: " + getName();
	}

}