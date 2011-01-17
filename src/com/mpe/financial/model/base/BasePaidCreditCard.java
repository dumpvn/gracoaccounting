package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the paid_credit_card table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="paid_credit_card"
 */

public abstract class BasePaidCreditCard  implements Serializable {

	public static String REF = "PaidCreditCard";
	public static String PROP_PAID_DATE = "PaidDate";
	public static String PROP_CREDIT_CARD_NUMBER = "CreditCardNumber";
	public static String PROP_MASTER = "Master";
	public static String PROP_ADM = "Adm";
	public static String PROP_AMOUNT = "Amount";


	// constructors
	public BasePaidCreditCard () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePaidCreditCard (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BasePaidCreditCard (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Bank bank,
		com.mpe.financial.model.Location location,
		com.mpe.financial.model.PosOrder posOrder,
		java.lang.String creditCardNumber,
		double adm,
		double amount) {

		this.setId(id);
		this.setCurrency(currency);
		this.setOrganization(organization);
		this.setBank(bank);
		this.setLocation(location);
		this.setPosOrder(posOrder);
		this.setCreditCardNumber(creditCardNumber);
		this.setAdm(adm);
		this.setAmount(amount);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date paidDate;
	private java.lang.String creditCardNumber;
	private boolean master;
	private double adm;
	private double amount;

	// many to one
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Bank bank;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.PosOrder posOrder;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="paid_credit_card_id"
     */
	public long getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (long id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: paid_date
	 */
	public java.util.Date getPaidDate () {
		return paidDate;
	}

	/**
	 * Set the value related to the column: paid_date
	 * @param paidDate the paid_date value
	 */
	public void setPaidDate (java.util.Date paidDate) {
		this.paidDate = paidDate;
	}



	/**
	 * Return the value associated with the column: credit_card_number
	 */
	public java.lang.String getCreditCardNumber () {
		return creditCardNumber;
	}

	/**
	 * Set the value related to the column: credit_card_number
	 * @param creditCardNumber the credit_card_number value
	 */
	public void setCreditCardNumber (java.lang.String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}



	/**
	 * Return the value associated with the column: is_master
	 */
	public boolean isMaster () {
		return master;
	}

	/**
	 * Set the value related to the column: is_master
	 * @param master the is_master value
	 */
	public void setMaster (boolean master) {
		this.master = master;
	}



	/**
	 * Return the value associated with the column: adm
	 */
	public double getAdm () {
		return adm;
	}

	/**
	 * Set the value related to the column: adm
	 * @param adm the adm value
	 */
	public void setAdm (double adm) {
		this.adm = adm;
	}



	/**
	 * Return the value associated with the column: amount
	 */
	public double getAmount () {
		return amount;
	}

	/**
	 * Set the value related to the column: amount
	 * @param amount the amount value
	 */
	public void setAmount (double amount) {
		this.amount = amount;
	}



	/**
	 * Return the value associated with the column: currency_id
	 */
	public com.mpe.financial.model.Currency getCurrency () {
		return currency;
	}

	/**
	 * Set the value related to the column: currency_id
	 * @param currency the currency_id value
	 */
	public void setCurrency (com.mpe.financial.model.Currency currency) {
		this.currency = currency;
	}



	/**
	 * Return the value associated with the column: organization_id
	 */
	public com.mpe.financial.model.Organization getOrganization () {
		return organization;
	}

	/**
	 * Set the value related to the column: organization_id
	 * @param organization the organization_id value
	 */
	public void setOrganization (com.mpe.financial.model.Organization organization) {
		this.organization = organization;
	}



	/**
	 * Return the value associated with the column: bank_id
	 */
	public com.mpe.financial.model.Bank getBank () {
		return bank;
	}

	/**
	 * Set the value related to the column: bank_id
	 * @param bank the bank_id value
	 */
	public void setBank (com.mpe.financial.model.Bank bank) {
		this.bank = bank;
	}



	/**
	 * Return the value associated with the column: location_id
	 */
	public com.mpe.financial.model.Location getLocation () {
		return location;
	}

	/**
	 * Set the value related to the column: location_id
	 * @param location the location_id value
	 */
	public void setLocation (com.mpe.financial.model.Location location) {
		this.location = location;
	}



	/**
	 * Return the value associated with the column: pos_order_id
	 */
	public com.mpe.financial.model.PosOrder getPosOrder () {
		return posOrder;
	}

	/**
	 * Set the value related to the column: pos_order_id
	 * @param posOrder the pos_order_id value
	 */
	public void setPosOrder (com.mpe.financial.model.PosOrder posOrder) {
		this.posOrder = posOrder;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.PaidCreditCard)) return false;
		else {
			com.mpe.financial.model.PaidCreditCard paidCreditCard = (com.mpe.financial.model.PaidCreditCard) obj;
			return (this.getId() == paidCreditCard.getId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}