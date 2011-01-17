package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the credit_card_detail table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="credit_card_detail"
 */

public abstract class BaseCreditCardDetail  implements Serializable {

	public static String REF = "CreditCardDetail";
	public static String PROP_CHARGE_TO_LOCATION = "ChargeToLocation";


	// constructors
	public BaseCreditCardDetail () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCreditCardDetail (com.mpe.financial.model.CreditCardDetailPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCreditCardDetail (
		com.mpe.financial.model.CreditCardDetailPK id,
		boolean chargeToLocation) {

		this.setId(id);
		this.setChargeToLocation(chargeToLocation);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.CreditCardDetailPK id;

	// fields
	private boolean chargeToLocation;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.CreditCardDetailPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.CreditCardDetailPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: is_charge_to_location
	 */
	public boolean isChargeToLocation () {
		return chargeToLocation;
	}

	/**
	 * Set the value related to the column: is_charge_to_location
	 * @param chargeToLocation the is_charge_to_location value
	 */
	public void setChargeToLocation (boolean chargeToLocation) {
		this.chargeToLocation = chargeToLocation;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.CreditCardDetail)) return false;
		else {
			com.mpe.financial.model.CreditCardDetail creditCardDetail = (com.mpe.financial.model.CreditCardDetail) obj;
			if (null == this.getId() || null == creditCardDetail.getId()) return false;
			else return (this.getId().equals(creditCardDetail.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}