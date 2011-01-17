package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseWarehouseInventoryFK;



public class WarehouseInventoryFK extends BaseWarehouseInventoryFK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public WarehouseInventoryFK () {
		super();
	}

	/**
	 * Constructor for required fields
	 */
	public WarehouseInventoryFK (
		com.mpe.financial.model.Inventory inventory,
		double currentStock) {

		super (
			inventory,
			currentStock);
	}

/*[CONSTRUCTOR MARKER END]*/


}