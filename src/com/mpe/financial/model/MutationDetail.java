package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseMutationDetail;



public class MutationDetail extends BaseMutationDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MutationDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MutationDetail (com.mpe.financial.model.MutationDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public MutationDetail (
		com.mpe.financial.model.MutationDetailPK id,
		com.mpe.financial.model.ItemUnit itemUnit,
		double previousQuantity,
		double movedQuantity) {

		super (
			id,
			itemUnit,
			previousQuantity,
			movedQuantity);
	}

/*[CONSTRUCTOR MARKER END]*/


}