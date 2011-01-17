package com.mpe.financial.model;

import com.mpe.financial.model.base.BaseJournalDetailPK;

public class JournalDetailPK extends BaseJournalDetailPK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public JournalDetailPK () {}
	
	public JournalDetailPK (
		com.mpe.financial.model.ChartOfAccount chartOfAccount,
		com.mpe.financial.model.Journal journal,
		int chartOfAccountSequence) {

		super (
			chartOfAccount,
			journal,
			chartOfAccountSequence);
	}
/*[CONSTRUCTOR MARKER END]*/


}