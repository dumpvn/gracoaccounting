package com.mpe.financial.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the mutation_detail table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="mutation_detail"
 */

public abstract class BaseMutationDetail  implements Serializable {

	public static String REF = "MutationDetail";
	public static String PROP_PREVIOUS_QUANTITY = "PreviousQuantity";
	public static String PROP_MOVED_QUANTITY = "MovedQuantity";


	// constructors
	public BaseMutationDetail () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMutationDetail (com.mpe.financial.model.MutationDetailPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMutationDetail (
		com.mpe.financial.model.MutationDetailPK id,
		com.mpe.financial.model.ItemUnit itemUnit,
		double previousQuantity,
		double movedQuantity) {

		this.setId(id);
		this.setItemUnit(itemUnit);
		this.setPreviousQuantity(previousQuantity);
		this.setMovedQuantity(movedQuantity);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.mpe.financial.model.MutationDetailPK id;

	// fields
	private double previousQuantity;
	private double movedQuantity;

	// many to one
	private com.mpe.financial.model.ItemUnit itemUnit;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.mpe.financial.model.MutationDetailPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.mpe.financial.model.MutationDetailPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: previous_quantity
	 */
	public double getPreviousQuantity () {
		return previousQuantity;
	}

	/**
	 * Set the value related to the column: previous_quantity
	 * @param previousQuantity the previous_quantity value
	 */
	public void setPreviousQuantity (double previousQuantity) {
		this.previousQuantity = previousQuantity;
	}



	/**
	 * Return the value associated with the column: moved_quantity
	 */
	public double getMovedQuantity () {
		return movedQuantity;
	}

	/**
	 * Set the value related to the column: moved_quantity
	 * @param movedQuantity the moved_quantity value
	 */
	public void setMovedQuantity (double movedQuantity) {
		this.movedQuantity = movedQuantity;
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
		if (!(obj instanceof com.mpe.financial.model.MutationDetail)) return false;
		else {
			com.mpe.financial.model.MutationDetail mutationDetail = (com.mpe.financial.model.MutationDetail) obj;
			if (null == this.getId() || null == mutationDetail.getId()) return false;
			else return (this.getId().equals(mutationDetail.getId()));
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