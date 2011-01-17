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

public abstract class BaseCustomerPaymentInvoiceFK  implements Serializable {

	public static String REF = "CustomerPaymentInvoiceFK";
	public static String PROP_INVOICE_AMOUNT = "InvoiceAmount";
	public static String PROP_PAYMENT_AMOUNT = "PaymentAmount";
	public static String PROP_INVOICE_EXCHANGE_RATE = "InvoiceExchangeRate";


	// constructors
	public BaseCustomerPaymentInvoiceFK () {
		initialize();
	}

	protected void initialize () {}



	// fields
	private double invoiceAmount;
	private double paymentAmount;
	private double invoiceExchangeRate;

	// many to one
	private com.mpe.financial.model.CustomerPayment customerPayment;






	/**
	 * Return the value associated with the column: invoice_amount
	 */
	public double getInvoiceAmount () {
		return invoiceAmount;
	}

	/**
	 * Set the value related to the column: invoice_amount
	 * @param invoiceAmount the invoice_amount value
	 */
	public void setInvoiceAmount (double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}



	/**
	 * Return the value associated with the column: payment_amount
	 */
	public double getPaymentAmount () {
		return paymentAmount;
	}

	/**
	 * Set the value related to the column: payment_amount
	 * @param paymentAmount the payment_amount value
	 */
	public void setPaymentAmount (double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}



	/**
	 * Return the value associated with the column: invoice_exchange_rate
	 */
	public double getInvoiceExchangeRate () {
		return invoiceExchangeRate;
	}

	/**
	 * Set the value related to the column: invoice_exchange_rate
	 * @param invoiceExchangeRate the invoice_exchange_rate value
	 */
	public void setInvoiceExchangeRate (double invoiceExchangeRate) {
		this.invoiceExchangeRate = invoiceExchangeRate;
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