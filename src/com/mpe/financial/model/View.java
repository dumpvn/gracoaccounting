package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseView;



public class View extends BaseView {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public View () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public View (long id) {
		super(id);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getParentViewName() {
	    return getParent()!=null?getParent().getViewName()+" :: "+getViewName():getViewName();
	}


}