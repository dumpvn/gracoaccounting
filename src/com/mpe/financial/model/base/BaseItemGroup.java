package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the item_group table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="item_group"
 */

public abstract class BaseItemGroup  implements Serializable {

	public static String REF = "ItemGroup";
	public static String PROP_QUANTITY = "Quantity";
	public static String PROP_NOTE = "Note";


	// constructors
	public BaseItemGroup () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseItemGroup (com.mpe.financial.model.ItemGroupPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseItemGroup (
		com.mpe.financial.model.ItemGroupPK id,
		com.mpe.financial.model.ItemUnit itemUnit,
		double quantity) {

		this.setId(id);
		this.setItemUnit(itemUnit);
		this.setQuantity(quantity);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.ItemGroupPK id;

	// fields
	private double quantity;
	private java.lang.String note;

	// many to one
	private com.mpe.financial.model.ItemUnit itemUnit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.ItemGroupPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.ItemGroupPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: quantity
	 */
	public double getQuantity () {
		return quantity;
	}

	/**
	 * Set the value related to the column: quantity
	 * @param quantity the quantity value
	 */
	public void setQuantity (double quantity) {
		this.quantity = quantity;
	}



	/**
	 * Return the value associated with the column: note
	 */
	public java.lang.String getNote () {
		return note;
	}

	/**
	 * Set the value related to the column: note
	 * @param note the note value
	 */
	public void setNote (java.lang.String note) {
		this.note = note;
	}



	/**
	 * Return the value associated with the column: item_unit_id
	 */
	public com.mpe.financial.model.ItemUnit getItemUnit () {
		return itemUnit;
	}

	/**
	 * Set the value related to the column: item_unit_id
	 * @param itemUnit the item_unit_id value
	 */
	public void setItemUnit (com.mpe.financial.model.ItemUnit itemUnit) {
		this.itemUnit = itemUnit;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.ItemGroup)) return false;
		else {
			com.mpe.financial.model.ItemGroup itemGroup = (com.mpe.financial.model.ItemGroup) obj;
			if (null == this.getId() || null == itemGroup.getId()) return false;
			else return (this.getId().equals(itemGroup.getId()));
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