package com.mpe.financial.model.base;

import java.io.Serializable;


public abstract class BaseJournalDetailPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private com.mpe.financial.model.ChartOfAccount chartOfAccount;
	private com.mpe.financial.model.Journal journal;
	private int chartOfAccountSequence;


	public BaseJournalDetailPK () {}
	
	public BaseJournalDetailPK (
		com.mpe.financial.model.ChartOfAccount chartOfAccount,
		com.mpe.financial.model.Journal journal,
		int chartOfAccountSequence) {

		this.setChartOfAccount(chartOfAccount);
		this.setJournal(journal);
		this.setChartOfAccountSequence(chartOfAccountSequence);
	}


	/**
	 * Return the value associated with the column: chart_of_account_id
	 */
	public com.mpe.financial.model.ChartOfAccount getChartOfAccount () {
		return chartOfAccount;
	}

	/**
	 * Set the value related to the column: chart_of_account_id
	 * @param chartOfAccount the chart_of_account_id value
	 */
	public void setChartOfAccount (com.mpe.financial.model.ChartOfAccount chartOfAccount) {
		this.chartOfAccount = chartOfAccount;
	}



	/**
	 * Return the value associated with the column: journal_id
	 */
	public com.mpe.financial.model.Journal getJournal () {
		return journal;
	}

	/**
	 * Set the value related to the column: journal_id
	 * @param journal the journal_id value
	 */
	public void setJournal (com.mpe.financial.model.Journal journal) {
		this.journal = journal;
	}



	/**
	 * Return the value associated with the column: chart_of_account_sequence
	 */
	public int getChartOfAccountSequence () {
		return chartOfAccountSequence;
	}

	/**
	 * Set the value related to the column: chart_of_account_sequence
	 * @param chartOfAccountSequence the chart_of_account_sequence value
	 */
	public void setChartOfAccountSequence (int chartOfAccountSequence) {
		this.chartOfAccountSequence = chartOfAccountSequence;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.mpe.financial.model.JournalDetailPK)) return false;
		else {
			com.mpe.financial.model.JournalDetailPK mObj = (com.mpe.financial.model.JournalDetailPK) obj;
			if (null != this.getChartOfAccount() && null != mObj.getChartOfAccount()) {
				if (!this.getChartOfAccount().equals(mObj.getChartOfAccount())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getJournal() && null != mObj.getJournal()) {
				if (!this.getJournal().equals(mObj.getJournal())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (this.getChartOfAccountSequence() != mObj.getChartOfAccountSequence()) {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuffer sb = new StringBuffer();
			if (null != this.getChartOfAccount()) {
				sb.append(this.getChartOfAccount().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getJournal()) {
				sb.append(this.getJournal().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			sb.append(new java.lang.Integer(this.getChartOfAccountSequence()).hashCode());
			sb.append(":");
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}