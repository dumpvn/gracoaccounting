package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the warehouse table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="warehouse"
 */

public abstract class BaseWarehouseInventoryFK  implements Serializable {

	public static String REF = "WarehouseInventoryFK";
	public static String PROP_CURRENT_STOCK = "CurrentStock";


	// constructors
	public BaseWarehouseInventoryFK () {
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseWarehouseInventoryFK (
		com.mpe.financial.model.Inventory inventory,
		double currentStock) {

		this.setInventory(inventory);
		this.setCurrentStock(currentStock);
		initialize();
	}

	protected void initialize () {}



	// fields
	private double currentStock;

	// many to one
	private com.mpe.financial.model.Inventory inventory;






	/**
	 * Return the value associated with the column: quantity
	 */
	public double getCurrentStock () {
		return currentStock;
	}

	/**
	 * Set the value related to the column: quantity
	 * @param currentStock the quantity value
	 */
	public void setCurrentStock (double currentStock) {
		this.currentStock = currentStock;
	}



	/**
	 * Return the value associated with the column: item_id
	 */
	public com.mpe.financial.model.Inventory getInventory () {
		return inventory;
	}

	/**
	 * Set the value related to the column: item_id
	 * @param inventory the item_id value
	 */
	public void setInventory (com.mpe.financial.model.Inventory inventory) {
		this.inventory = inventory;
	}








	public String toString () {
		return super.toString();
	}


}