package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseItemCustomFieldPK;

public class ItemCustomFieldPK extends BaseItemCustomFieldPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemCustomFieldPK () {}
	
	public ItemCustomFieldPK (
		com.mpe.financial.model.Item item,
		com.mpe.financial.model.CustomField customField) {

		super (
			item,
			customField);
	}
/*[CONSTRUCTOR MARKER END]*/


}