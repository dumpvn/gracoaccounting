package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseLendingDetail;



public class LendingDetail extends BaseLendingDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public LendingDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public LendingDetail (com.mpe.financial.model.LendingDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public LendingDetail (
		com.mpe.financial.model.LendingDetailPK id,
		com.mpe.financial.model.ItemUnit itemUnit,
		double lendingQuantity,
		double returQuantity) {

		super (
			id,
			itemUnit,
			lendingQuantity,
			returQuantity);
	}

/*[CONSTRUCTOR MARKER END]*/


}