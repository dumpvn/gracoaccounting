package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the customer_payment_invoice table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="customer_payment_invoice"
 */

public abstract class BaseCustomerPaymentDetail  implements Serializable {

	public static String REF = "CustomerPaymentDetail";
	public static String PROP_PAYMENT_AMOUNT = "PaymentAmount";
	public static String PROP_INVOICE_AMOUNT = "InvoiceAmount";
	public static String PROP_INVOICE_EXCHANGE_RATE = "InvoiceExchangeRate";


	// constructors
	public BaseCustomerPaymentDetail () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCustomerPaymentDetail (com.mpe.financial.model.CustomerPaymentDetailPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCustomerPaymentDetail (
		com.mpe.financial.model.CustomerPaymentDetailPK id,
		double paymentAmount,
		double invoiceAmount,
		double invoiceExchangeRate) {

		this.setId(id);
		this.setPaymentAmount(paymentAmount);
		this.setInvoiceAmount(invoiceAmount);
		this.setInvoiceExchangeRate(invoiceExchangeRate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.CustomerPaymentDetailPK id;

	// fields
	private double paymentAmount;
	private double invoiceAmount;
	private double invoiceExchangeRate;

	// many to one
	private com.mpe.financial.model.Invoice invoice;
	private com.mpe.financial.model.InvoiceSimple invoiceSimple;
	private com.mpe.financial.model.InvoicePolos invoicePolos;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.CustomerPaymentDetailPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.CustomerPaymentDetailPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
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
	 * Return the value associated with the column: invoice_id
	 */
	public com.mpe.financial.model.Invoice getInvoice () {
		return invoice;
	}

	/**
	 * Set the value related to the column: invoice_id
	 * @param invoice the invoice_id value
	 */
	public void setInvoice (com.mpe.financial.model.Invoice invoice) {
		this.invoice = invoice;
	}



	/**
	 * Return the value associated with the column: invoice_simple_id
	 */
	public com.mpe.financial.model.InvoiceSimple getInvoiceSimple () {
		return invoiceSimple;
	}

	/**
	 * Set the value related to the column: invoice_simple_id
	 * @param invoiceSimple the invoice_simple_id value
	 */
	public void setInvoiceSimple (com.mpe.financial.model.InvoiceSimple invoiceSimple) {
		this.invoiceSimple = invoiceSimple;
	}



	/**
	 * Return the value associated with the column: invoice_polos_id
	 */
	public com.mpe.financial.model.InvoicePolos getInvoicePolos () {
		return invoicePolos;
	}

	/**
	 * Set the value related to the column: invoice_polos_id
	 * @param invoicePolos the invoice_polos_id value
	 */
	public void setInvoicePolos (com.mpe.financial.model.InvoicePolos invoicePolos) {
		this.invoicePolos = invoicePolos;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.CustomerPaymentDetail)) return false;
		else {
			com.mpe.financial.model.CustomerPaymentDetail customerPaymentDetail = (com.mpe.financial.model.CustomerPaymentDetail) obj;
			if (null == this.getId() || null == customerPaymentDetail.getId()) return false;
			else return (this.getId().equals(customerPaymentDetail.getId()));
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