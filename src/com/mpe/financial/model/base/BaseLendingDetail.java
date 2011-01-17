package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the lending_detail table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="lending_detail"
 */

public abstract class BaseLendingDetail  implements Serializable {

	public static String REF = "LendingDetail";
	public static String PROP_LENDING_QUANTITY = "LendingQuantity";
	public static String PROP_RETUR_QUANTITY = "ReturQuantity";
	public static String PROP_DESCRIPTION = "Description";


	// constructors
	public BaseLendingDetail () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseLendingDetail (com.mpe.financial.model.LendingDetailPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseLendingDetail (
		com.mpe.financial.model.LendingDetailPK id,
		com.mpe.financial.model.ItemUnit itemUnit,
		double lendingQuantity,
		double returQuantity) {

		this.setId(id);
		this.setItemUnit(itemUnit);
		this.setLendingQuantity(lendingQuantity);
		this.setReturQuantity(returQuantity);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.LendingDetailPK id;

	// fields
	private double lendingQuantity;
	private double returQuantity;
	private java.lang.String description;

	// many to one
	private com.mpe.financial.model.ItemUnit itemUnit;
	private com.mpe.financial.model.Warehouse warehouse;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.LendingDetailPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.LendingDetailPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: lending_quantity
	 */
	public double getLendingQuantity () {
		return lendingQuantity;
	}

	/**
	 * Set the value related to the column: lending_quantity
	 * @param lendingQuantity the lending_quantity value
	 */
	public void setLendingQuantity (double lendingQuantity) {
		this.lendingQuantity = lendingQuantity;
	}



	/**
	 * Return the value associated with the column: retur_quantity
	 */
	public double getReturQuantity () {
		return returQuantity;
	}

	/**
	 * Set the value related to the column: retur_quantity
	 * @param returQuantity the retur_quantity value
	 */
	public void setReturQuantity (double returQuantity) {
		this.returQuantity = returQuantity;
	}



	/**
	 * Return the value associated with the column: description
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: description
	 * @param description the description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
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
	 * Return the value associated with the column: warehouse_id
	 */
	public com.mpe.financial.model.Warehouse getWarehouse () {
		return warehouse;
	}

	/**
	 * Set the value related to the column: warehouse_id
	 * @param warehouse the warehouse_id value
	 */
	public void setWarehouse (com.mpe.financial.model.Warehouse warehouse) {
		this.warehouse = warehouse;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.LendingDetail)) return false;
		else {
			com.mpe.financial.model.LendingDetail lendingDetail = (com.mpe.financial.model.LendingDetail) obj;
			if (null == this.getId() || null == lendingDetail.getId()) return false;
			else return (this.getId().equals(lendingDetail.getId()));
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