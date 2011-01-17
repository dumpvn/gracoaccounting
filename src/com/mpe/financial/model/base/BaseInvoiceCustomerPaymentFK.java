package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the invoice table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="invoice"
 */

public abstract class BaseInvoiceCustomerPaymentFK  implements Serializable {

	public static String REF = "InvoiceCustomerPaymentFK";
	public static String PROP_AMOUNT = "Amount";


	// constructors
	public BaseInvoiceCustomerPaymentFK () {
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseInvoiceCustomerPaymentFK (
		com.mpe.financial.model.CustomerPayment customerPayment,
		double amount) {

		this.setCustomerPayment(customerPayment);
		this.setAmount(amount);
		initialize();
	}

	protected void initialize () {}



	// fields
	private double amount;

	// many to one
	private com.mpe.financial.model.CustomerPayment customerPayment;






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
	 * Return the value associated with the column: customer_payment_id
	 */
	public com.mpe.financial.model.CustomerPayment getCustomerPayment () {
		return customerPayment;
	}

	/**
	 * Set the value related to the column: customer_payment_id
	 * @param customerPayment the customer_payment_id value
	 */
	public void setCustomerPayment (com.mpe.financial.model.CustomerPayment customerPayment) {
		this.customerPayment = customerPayment;
	}








	public String toString () {
		return super.toString();
	}


}