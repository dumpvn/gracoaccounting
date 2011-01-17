package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseMutationDetailPK;

public class MutationDetailPK extends BaseMutationDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MutationDetailPK () {}
	
	public MutationDetailPK (
		com.mpe.financial.model.Item item,
		com.mpe.financial.model.Mutation mutation) {

		super (
			item,
			mutation);
	}
/*[CONSTRUCTOR MARKER END]*/


}