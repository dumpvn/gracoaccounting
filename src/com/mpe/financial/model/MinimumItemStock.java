package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseMinimumItemStock;



public class MinimumItemStock extends BaseMinimumItemStock {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public MinimumItemStock () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public MinimumItemStock (long itemId) {
		super(itemId);
	}

/*[CONSTRUCTOR MARKER END]*/


}