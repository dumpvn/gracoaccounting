package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the prepayment_to_vendor table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="prepayment_to_vendor"
 */

public abstract class BasePrepaymentToVendorPurchaseOrderFK  implements Serializable {

	public static String REF = "PrepaymentToVendorPurchaseOrderFK";
	public static String PROP_AMOUNT = "Amount";


	// constructors
	public BasePrepaymentToVendorPurchaseOrderFK () {
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BasePrepaymentToVendorPurchaseOrderFK (
		double amount) {

		this.setAmount(amount);
		initialize();
	}

	protected void initialize () {}



	// fields
	private double amount;

	// many to one
	private com.mpe.financial.model.PurchaseOrder purchaseOrder;






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
	 * Return the value associated with the column: purchase_order_id
	 */
	public com.mpe.financial.model.PurchaseOrder getPurchaseOrder () {
		return purchaseOrder;
	}

	/**
	 * Set the value related to the column: purchase_order_id
	 * @param purchaseOrder the purchase_order_id value
	 */
	public void setPurchaseOrder (com.mpe.financial.model.PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}








	public String toString () {
		return super.toString();
	}


}