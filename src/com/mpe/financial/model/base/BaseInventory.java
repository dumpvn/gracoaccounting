package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the inventory table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="inventory"
 */

public abstract class BaseInventory extends com.mpe.financial.model.Item  implements Serializable {

	public static String REF = "Inventory";
	public static String PROP_MINIMUM_STOCK = "MinimumStock";
	public static String PROP_MAXIMUM_STOCK = "MaximumStock";


	// constructors
	public BaseInventory () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseInventory (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public BaseInventory (
		long id,
		com.mpe.financial.model.Organization organization,
		java.lang.String type,
		java.lang.String name,
		java.lang.String code,
		boolean active) {

		super (
			id,
			organization,
			name,
			active);
	}



	private int hashCode = Integer.MIN_VALUE;


	// fields
	private double minimumStock;
	private double maximumStock;

	// many to one
	private com.mpe.financial.model.ItemUnit itemUnit;
	private com.mpe.financial.model.ChartOfAccount inventoryAccount;






	/**
	 * Return the value associated with the column: minimum_stock
	 */
	public double getMinimumStock () {
		return minimumStock;
	}

	/**
	 * Set the value related to the column: minimum_stock
	 * @param minimumStock the minimum_stock value
	 */
	public void setMinimumStock (double minimumStock) {
		this.minimumStock = minimumStock;
	}



	/**
	 * Return the value associated with the column: maximum_stock
	 */
	public double getMaximumStock () {
		return maximumStock;
	}

	/**
	 * Set the value related to the column: maximum_stock
	 * @param maximumStock the maximum_stock value
	 */
	public void setMaximumStock (double maximumStock) {
		this.maximumStock = maximumStock;
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



	/**
	 * Return the value associated with the column: inventory_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getInventoryAccount () {
		return inventoryAccount;
	}

	/**
	 * Set the value related to the column: inventory_account_id
	 * @param inventoryAccount the inventory_account_id value
	 */
	public void setInventoryAccount (com.mpe.financial.model.ChartOfAccount inventoryAccount) {
		this.inventoryAccount = inventoryAccount;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.Inventory)) return false;
		else {
			com.mpe.financial.model.Inventory inventory = (com.mpe.financial.model.Inventory) obj;
			return (this.getId() == inventory.getId());
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