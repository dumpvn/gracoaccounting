package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseCreditCardDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.CreditCard creditCard;
	private com.mpe.financial.model.Location location;


	public BaseCreditCardDetailPK () {}
	
	public BaseCreditCardDetailPK (
		com.mpe.financial.model.CreditCard creditCard,
		com.mpe.financial.model.Location location) {

		this.setCreditCard(creditCard);
		this.setLocation(location);
	}


	/**
	 * Return the value associated with the column: credit_card_id
	 */
	public com.mpe.financial.model.CreditCard getCreditCard () {
		return creditCard;
	}

	/**
	 * Set the value related to the column: credit_card_id
	 * @param creditCard the credit_card_id value
	 */
	public void setCreditCard (com.mpe.financial.model.CreditCard creditCard) {
		this.creditCard = creditCard;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.CreditCardDetailPK)) return false;
		else {
			com.mpe.financial.model.CreditCardDetailPK mObj = (com.mpe.financial.model.CreditCardDetailPK) obj;
			if (null != this.getCreditCard() && null != mObj.getCreditCard()) {
				if (!this.getCreditCard().equals(mObj.getCreditCard())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getLocation() && null != mObj.getLocation()) {
				if (!this.getLocation().equals(mObj.getLocation())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuffer sb = new StringBuffer();
			if (null != this.getCreditCard()) {
				sb.append(this.getCreditCard().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getLocation()) {
				sb.append(this.getLocation().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}