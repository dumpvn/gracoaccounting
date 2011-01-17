package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the customer_payment table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="customer_payment"
 */

public abstract class BaseCustomerPaymentCustomerPrepaymentFK  implements Serializable {

	public static String REF = "CustomerPaymentCustomerPrepaymentFK";
	public static String PROP_AMOUNT = "Amount";


	// constructors
	public BaseCustomerPaymentCustomerPrepaymentFK () {
		initialize();
	}

	protected void initialize () {}



	// fields
	private double amount;

	// many to one
	private com.mpe.financial.model.CustomerPrepayment customerPrepayment;






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
	 * Return the value associated with the column: customer_prepayment_id
	 */
	public com.mpe.financial.model.CustomerPrepayment getCustomerPrepayment () {
		return customerPrepayment;
	}

	/**
	 * Set the value related to the column: customer_prepayment_id
	 * @param customerPrepayment the customer_prepayment_id value
	 */
	public void setCustomerPrepayment (com.mpe.financial.model.CustomerPrepayment customerPrepayment) {
		this.customerPrepayment = customerPrepayment;
	}








	public String toString () {
		return super.toString();
	}


}