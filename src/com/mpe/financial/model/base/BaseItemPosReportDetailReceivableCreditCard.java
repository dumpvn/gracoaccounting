package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the  table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table=""
 */

public abstract class BaseItemPosReportDetailReceivableCreditCard  implements Serializable {

	public static String REF = "ItemPosReportDetailReceivableCreditCard";
	public static String PROP_POS_ORDER_DATE = "PosOrderDate";
	public static String PROP_CREDIT_CARD_NUMBER = "CreditCardNumber";
	public static String PROP_CREDIT_CARD_ADM = "CreditCardAdm";
	public static String PROP_TOTAL_COST_AFTER_DISCOUNT_AND_TAX = "TotalCostAfterDiscountAndTax";
	public static String PROP_PAID_DATE = "PaidDate";
	public static String PROP_BANK = "Bank";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BaseItemPosReportDetailReceivableCreditCard () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemPosReportDetailReceivableCreditCard (long posOrderId) {
		this.setPosOrderId(posOrderId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long posOrderId;

	// fields
	private java.util.Date posOrderDate;
	private java.lang.String creditCardNumber;
	private double creditCardAdm;
	private double totalCostAfterDiscountAndTax;
	private java.util.Date paidDate;
	private java.lang.String bank;
	private int numberOfDigit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="PosOrderId"
     */
	public long getPosOrderId () {
		return posOrderId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param posOrderId the new ID
	 */
	public void setPosOrderId (long posOrderId) {
		this.posOrderId = posOrderId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: PosOrderDate
	 */
	public java.util.Date getPosOrderDate () {
		return posOrderDate;
	}

	/**
	 * Set the value related to the column: PosOrderDate
	 * @param posOrderDate the PosOrderDate value
	 */
	public void setPosOrderDate (java.util.Date posOrderDate) {
		this.posOrderDate = posOrderDate;
	}



	/**
	 * Return the value associated with the column: CreditCardNumber
	 */
	public java.lang.String getCreditCardNumber () {
		return creditCardNumber;
	}

	/**
	 * Set the value related to the column: CreditCardNumber
	 * @param creditCardNumber the CreditCardNumber value
	 */
	public void setCreditCardNumber (java.lang.String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}



	/**
	 * Return the value associated with the column: CreditCardAdm
	 */
	public double getCreditCardAdm () {
		return creditCardAdm;
	}

	/**
	 * Set the value related to the column: CreditCardAdm
	 * @param creditCardAdm the CreditCardAdm value
	 */
	public void setCreditCardAdm (double creditCardAdm) {
		this.creditCardAdm = creditCardAdm;
	}



	/**
	 * Return the value associated with the column: TotalCostAfterDiscountAndTax
	 */
	public double getTotalCostAfterDiscountAndTax () {
		return totalCostAfterDiscountAndTax;
	}

	/**
	 * Set the value related to the column: TotalCostAfterDiscountAndTax
	 * @param totalCostAfterDiscountAndTax the TotalCostAfterDiscountAndTax value
	 */
	public void setTotalCostAfterDiscountAndTax (double totalCostAfterDiscountAndTax) {
		this.totalCostAfterDiscountAndTax = totalCostAfterDiscountAndTax;
	}



	/**
	 * Return the value associated with the column: PaidDate
	 */
	public java.util.Date getPaidDate () {
		return paidDate;
	}

	/**
	 * Set the value related to the column: PaidDate
	 * @param paidDate the PaidDate value
	 */
	public void setPaidDate (java.util.Date paidDate) {
		this.paidDate = paidDate;
	}



	/**
	 * Return the value associated with the column: Bank
	 */
	public java.lang.String getBank () {
		return bank;
	}

	/**
	 * Set the value related to the column: Bank
	 * @param bank the Bank value
	 */
	public void setBank (java.lang.String bank) {
		this.bank = bank;
	}



	/**
	 * Return the value associated with the column: NumberOfDigit
	 */
	public int getNumberOfDigit () {
		return numberOfDigit;
	}

	/**
	 * Set the value related to the column: NumberOfDigit
	 * @param numberOfDigit the NumberOfDigit value
	 */
	public void setNumberOfDigit (int numberOfDigit) {
		this.numberOfDigit = numberOfDigit;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.ItemPosReportDetailReceivableCreditCard)) return false;
		else {
			com.mpe.financial.model.ItemPosReportDetailReceivableCreditCard itemPosReportDetailReceivableCreditCard = (com.mpe.financial.model.ItemPosReportDetailReceivableCreditCard) obj;
			return (this.getPosOrderId() == itemPosReportDetailReceivableCreditCard.getPosOrderId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getPosOrderId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}