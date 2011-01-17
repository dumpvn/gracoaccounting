package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseItemGroup;



public class ItemGroup extends BaseItemGroup {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemGroup () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemGroup (com.mpe.financial.model.ItemGroupPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ItemGroup (
		com.mpe.financial.model.ItemGroupPK id,
		com.mpe.financial.model.ItemUnit itemUnit,
		double quantity) {

		super (
			id,
			itemUnit,
			quantity);
	}

/*[CONSTRUCTOR MARKER END]*/


}