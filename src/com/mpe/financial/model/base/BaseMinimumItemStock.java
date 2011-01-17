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

public abstract class BaseMinimumItemStock  implements Serializable {

	public static String REF = "MinimumItemStock";
	public static String PROP_CODE = "Code";
	public static String PROP_NAME = "Name";
	public static String PROP_QUANTITY = "Quantity";
	public static String PROP_MINIMUM_QUANTITY = "MinimumQuantity";


	// constructors
	public BaseMinimumItemStock () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMinimumItemStock (long itemId) {
		this.setItemId(itemId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private long itemId;

	// fields
	private java.lang.String code;
	private java.lang.String name;
	private double quantity;
	private double minimumQuantity;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  column="ItemId"
     */
	public long getItemId () {
		return itemId;
	}

	/**
	 * Set the unique identifier of this class
	 * @param itemId the new ID
	 */
	public void setItemId (long itemId) {
		this.itemId = itemId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: Code
	 */
	public java.lang.String getCode () {
		return code;
	}

	/**
	 * Set the value related to the column: Code
	 * @param code the Code value
	 */
	public void setCode (java.lang.String code) {
		this.code = code;
	}



	/**
	 * Return the value associated with the column: Name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: Name
	 * @param name the Name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: Quantity
	 */
	public double getQuantity () {
		return quantity;
	}

	/**
	 * Set the value related to the column: Quantity
	 * @param quantity the Quantity value
	 */
	public void setQuantity (double quantity) {
		this.quantity = quantity;
	}



	/**
	 * Return the value associated with the column: MinimumQuantity
	 */
	public double getMinimumQuantity () {
		return minimumQuantity;
	}

	/**
	 * Set the value related to the column: MinimumQuantity
	 * @param minimumQuantity the MinimumQuantity value
	 */
	public void setMinimumQuantity (double minimumQuantity) {
		this.minimumQuantity = minimumQuantity;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.MinimumItemStock)) return false;
		else {
			com.mpe.financial.model.MinimumItemStock minimumItemStock = (com.mpe.financial.model.MinimumItemStock) obj;
			return (this.getItemId() == minimumItemStock.getItemId());
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			return (int) this.getItemId();
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}