package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the pos_order table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="pos_order"
 */

public abstract class BasePosOrder  implements Serializable {

	public static String REF = "PosOrder";
	public static String PROP_POS_ORDER_DATE = "PosOrderDate";
	public static String PROP_START_TIME = "StartTime";
	public static String PROP_CASH_AMOUNT = "CashAmount";
	public static String PROP_CHANGES_AMOUNT = "ChangesAmount";
	public static String PROP_CREDIT_CARD_ADM = "CreditCardAdm";
	public static String PROP_CREDIT_CARD_NUMBER = "CreditCardNumber";
	public static String PROP_STATUS = "Status";
	public static String PROP_PAYMENT_METHOD = "PaymentMethod";
	public static String PROP_DISCOUNT_PROCENT = "DiscountProcent";
	public static String PROP_TAX_PROCENT = "TaxProcent";
	public static String PROP_POS_ORDER_NUMBER = "PosOrderNumber";
	public static String PROP_POSTED = "Posted";
	public static String PROP_CREATE_ON = "CreateOn";
	public static String PROP_CHANGE_ON = "ChangeOn";
	public static String PROP_NUMBER_OF_DIGIT = "NumberOfDigit";


	// constructors
	public BasePosOrder () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BasePosOrder (long id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BasePosOrder (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Location location,
		java.util.Date posOrderDate,
		java.lang.String paymentMethod,
		java.lang.String posOrderNumber,
		boolean posted) {

		this.setId(id);
		this.setCurrency(currency);
		this.setOrganization(organization);
		this.setLocation(location);
		this.setPosOrderDate(posOrderDate);
		this.setPaymentMethod(paymentMethod);
		this.setPosOrderNumber(posOrderNumber);
		this.setPosted(posted);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long id;

	// fields
	private java.util.Date posOrderDate;
	private java.util.Date startTime;
	private double cashAmount;
	private double changesAmount;
	private double creditCardAdm;
	private java.lang.String creditCardNumber;
	private java.lang.String status;
	private java.lang.String paymentMethod;
	private double discountProcent;
	private double taxProcent;
	private java.lang.String posOrderNumber;
	private boolean posted;
	private java.util.Date createOn;
	private java.util.Date changeOn;
	private int numberOfDigit;

	// many to one
	private com.mpe.financial.model.Users createBy;
	private com.mpe.financial.model.Users changeBy;
	private com.mpe.financial.model.Currency currency;
	private com.mpe.financial.model.Organization organization;
	private com.mpe.financial.model.Member member;
	private com.mpe.financial.model.Location location;
	private com.mpe.financial.model.Bank bank;
	private com.mpe.financial.model.Salesman salesman;

	// collections
	private java.util.Set posOrderDetails;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="increment"
     *  column="pos_order_id"
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
	 * Return the value associated with the column: pos_order_date
	 */
	public java.util.Date getPosOrderDate () {
		return posOrderDate;
	}

	/**
	 * Set the value related to the column: pos_order_date
	 * @param posOrderDate the pos_order_date value
	 */
	public void setPosOrderDate (java.util.Date posOrderDate) {
		this.posOrderDate = posOrderDate;
	}



	/**
	 * Return the value associated with the column: start_time
	 */
	public java.util.Date getStartTime () {
		return startTime;
	}

	/**
	 * Set the value related to the column: start_time
	 * @param startTime the start_time value
	 */
	public void setStartTime (java.util.Date startTime) {
		this.startTime = startTime;
	}



	/**
	 * Return the value associated with the column: cash_amount
	 */
	public double getCashAmount () {
		return cashAmount;
	}

	/**
	 * Set the value related to the column: cash_amount
	 * @param cashAmount the cash_amount value
	 */
	public void setCashAmount (double cashAmount) {
		this.cashAmount = cashAmount;
	}



	/**
	 * Return the value associated with the column: changes_amount
	 */
	public double getChangesAmount () {
		return changesAmount;
	}

	/**
	 * Set the value related to the column: changes_amount
	 * @param changesAmount the changes_amount value
	 */
	public void setChangesAmount (double changesAmount) {
		this.changesAmount = changesAmount;
	}



	/**
	 * Return the value associated with the column: credit_card_adm
	 */
	public double getCreditCardAdm () {
		return creditCardAdm;
	}

	/**
	 * Set the value related to the column: credit_card_adm
	 * @param creditCardAdm the credit_card_adm value
	 */
	public void setCreditCardAdm (double creditCardAdm) {
		this.creditCardAdm = creditCardAdm;
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
	 * Return the value associated with the column: status
	 */
	public java.lang.String getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (java.lang.String status) {
		this.status = status;
	}



	/**
	 * Return the value associated with the column: payment_method
	 */
	public java.lang.String getPaymentMethod () {
		return paymentMethod;
	}

	/**
	 * Set the value related to the column: payment_method
	 * @param paymentMethod the payment_method value
	 */
	public void setPaymentMethod (java.lang.String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}



	/**
	 * Return the value associated with the column: discount_procent
	 */
	public double getDiscountProcent () {
		return discountProcent;
	}

	/**
	 * Set the value related to the column: discount_procent
	 * @param discountProcent the discount_procent value
	 */
	public void setDiscountProcent (double discountProcent) {
		this.discountProcent = discountProcent;
	}



	/**
	 * Return the value associated with the column: tax_procent
	 */
	public double getTaxProcent () {
		return taxProcent;
	}

	/**
	 * Set the value related to the column: tax_procent
	 * @param taxProcent the tax_procent value
	 */
	public void setTaxProcent (double taxProcent) {
		this.taxProcent = taxProcent;
	}



	/**
	 * Return the value associated with the column: pos_order_number
	 */
	public java.lang.String getPosOrderNumber () {
		return posOrderNumber;
	}

	/**
	 * Set the value related to the column: pos_order_number
	 * @param posOrderNumber the pos_order_number value
	 */
	public void setPosOrderNumber (java.lang.String posOrderNumber) {
		this.posOrderNumber = posOrderNumber;
	}



	/**
	 * Return the value associated with the column: is_posted
	 */
	public boolean isPosted () {
		return posted;
	}

	/**
	 * Set the value related to the column: is_posted
	 * @param posted the is_posted value
	 */
	public void setPosted (boolean posted) {
		this.posted = posted;
	}



	/**
	 * Return the value associated with the column: create_on
	 */
	public java.util.Date getCreateOn () {
		return createOn;
	}

	/**
	 * Set the value related to the column: create_on
	 * @param createOn the create_on value
	 */
	public void setCreateOn (java.util.Date createOn) {
		this.createOn = createOn;
	}



	/**
	 * Return the value associated with the column: change_on
	 */
	public java.util.Date getChangeOn () {
		return changeOn;
	}

	/**
	 * Set the value related to the column: change_on
	 * @param changeOn the change_on value
	 */
	public void setChangeOn (java.util.Date changeOn) {
		this.changeOn = changeOn;
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



	/**
	 * Return the value associated with the column: create_by
	 */
	public com.mpe.financial.model.Users getCreateBy () {
		return createBy;
	}

	/**
	 * Set the value related to the column: create_by
	 * @param createBy the create_by value
	 */
	public void setCreateBy (com.mpe.financial.model.Users createBy) {
		this.createBy = createBy;
	}



	/**
	 * Return the value associated with the column: change_by
	 */
	public com.mpe.financial.model.Users getChangeBy () {
		return changeBy;
	}

	/**
	 * Set the value related to the column: change_by
	 * @param changeBy the change_by value
	 */
	public void setChangeBy (com.mpe.financial.model.Users changeBy) {
		this.changeBy = changeBy;
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
	 * Return the value associated with the column: member_id
	 */
	public com.mpe.financial.model.Member getMember () {
		return member;
	}

	/**
	 * Set the value related to the column: member_id
	 * @param member the member_id value
	 */
	public void setMember (com.mpe.financial.model.Member member) {
		this.member = member;
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
	 * Return the value associated with the column: salesman_id
	 */
	public com.mpe.financial.model.Salesman getSalesman () {
		return salesman;
	}

	/**
	 * Set the value related to the column: salesman_id
	 * @param salesman the salesman_id value
	 */
	public void setSalesman (com.mpe.financial.model.Salesman salesman) {
		this.salesman = salesman;
	}



	/**
	 * Return the value associated with the column: PosOrderDetails
	 */
	public java.util.Set getPosOrderDetails () {
		return posOrderDetails;
	}

	/**
	 * Set the value related to the column: PosOrderDetails
	 * @param posOrderDetails the PosOrderDetails value
	 */
	public void setPosOrderDetails (java.util.Set posOrderDetails) {
		this.posOrderDetails = posOrderDetails;
	}

	public void addToPosOrderDetails (com.mpe.financial.model.PosOrderDetail posOrderDetail) {
		if (null == getPosOrderDetails()) setPosOrderDetails(new java.util.HashSet());
		getPosOrderDetails().add(posOrderDetail);
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.PosOrder)) return false;
		else {
			com.mpe.financial.model.PosOrder posOrder = (com.mpe.financial.model.PosOrder) obj;
			return (this.getId() == posOrder.getId());
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