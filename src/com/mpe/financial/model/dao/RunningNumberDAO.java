package com.mpe.financial.model.dao;


import java.util.Calendar;
import java.util.GregorianCalendar;

import org.hibernate.Session;

import com.mpe.financial.model.RunningNumber;
import com.mpe.financial.model.base.BaseRunningNumberDAO;


public class RunningNumberDAO extends BaseRunningNumberDAO {

	/**
	 * Default constructor.  Can be used in place of getInstance()
	 */
	public RunningNumberDAO () {}
	
	public String getWarehouseNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getWarehouseNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getWarehousePrefix()+string+number.getWarehouseSuffix();
		}
		return string;
	}

	public void updateWarehouseNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getWarehouseNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setWarehouseNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getVendorBillNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getVendorBillNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getVendorBillPrefix()+string+number.getVendorBillSuffix();
		}
		return string;
	}

	public void updateVendorBillNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getVendorBillNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setVendorBillNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getStockOpnameNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getStockOpnameNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getStockOpnamePrefix()+string+number.getStockOpnameSuffix();
		}
		return string;
	}

	public void updateStockOpnameNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getStockOpnameNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setStockOpnameNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getItemUsageNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getItemUsageNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getItemUsagePrefix()+string+number.getItemUsageSuffix();
		}
		return string;
	}

	public void updateItemUsageNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getItemUsageNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setItemUsageNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getLendingNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getLendingNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getLendingPrefix()+string+number.getLendingSuffix();
		}
		return string;
	}

	public void updateLendingNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getLendingNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setLendingNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getSalesOrderNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getSalesOrderNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getSalesOrderPrefix()+string+number.getSalesOrderSuffix();
		}
		return string;
	}

	public void updateSalesOrderNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getSalesOrderNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setSalesOrderNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getReturToVendorNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getReturToVendorNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getReturToVendorPrefix()+string+number.getReturToVendorSuffix();
		}
		return string;
	}

	public void updateReturToVendorNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getReturToVendorNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setReturToVendorNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getReceivingNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getReceivingNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getReceivingPrefix()+string+number.getReceivingSuffix();
		}
		return string;
	}

	public void updateReceivingNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getReceivingNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setReceivingNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getPurchaseOrderNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getPurchaseOrderNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getPurchaseOrderPrefix()+string+number.getPurchaseOrderSuffix();
		}
		return string;
	}

	public void updatePurchaseOrderNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getPurchaseOrderNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setPurchaseOrderNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getPurchaseRequestNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getPurchaseRequestNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getPurchaseRequestPrefix()+string+number.getPurchaseRequestSuffix();
		}
		return string;
	}

	public void updatePurchaseRequestNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getPurchaseRequestNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setPurchaseRequestNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getPrepaymentToVendorNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getPrepaymentToVendorNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getPrepaymentToVendorPrefix()+string+number.getPrepaymentToVendorSuffix();
		}
		return string;
	}

	public void updatePrepaymentToVendorNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getPrepaymentToVendorNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setPrepaymentToVendorNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getPaymentToVendorNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getPaymentToVendorNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getPaymentToVendorPrefix()+string+number.getPaymentToVendorSuffix();
		}
		return string;
	}

	public void updatePaymentToVendorNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getPaymentToVendorNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setPaymentToVendorNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getStandartNpwpTaxNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getStandartNpwpTaxNumber();
			if (string==null || string.length()==0) {
			    string = "0000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
		}
		return string;
	}

	public void updateStandartNpwpTaxNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getStandartNpwpTaxNumber();
			if (string==null || string.length()==0) {
			    string = "0000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setStandartNpwpTaxNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getSimpleNpwpTaxNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getSimpleNpwpTaxNumber();
			if (string==null || string.length()==0) {
			    string = "00001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
		}
		return string;
	}

	public void updateSimpleNpwpTaxNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getSimpleNpwpTaxNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setSimpleNpwpTaxNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getMutationNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getMutationNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getMutationPrefix()+string+number.getMutationSuffix();
		}
		return string;
	}

	public void updateMutationNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getMutationNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setMutationNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getJournalNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getJournalNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getJournalPrefix()+string+number.getJournalSuffix();
		}
		return string;
	}

	public void updateJournalNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getJournalNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setJournalNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getItemNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getItemNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getItemPrefix()+string+number.getItemSuffix();
		}
		return string;
	}

	public void updateItemNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getItemNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setItemNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getInvoiceNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getInvoiceNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getInvoicePrefix()+string+number.getInvoiceSuffix();
		}
		return string;
	}

	public void updateInvoiceNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getInvoiceNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setInvoiceNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getCustomerReturNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getCustomerReturNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getCustomerReturPrefix()+string+number.getCustomerReturSuffix();
		}
		return string;
	}

	public void updateCustomerReturNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getCustomerReturNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setCustomerReturNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getCustomerPrepaymentNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getCustomerPrepaymentNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getCustomerPrepaymentPrefix()+string+number.getCustomerPrepaymentSuffix();
		}
		return string;
	}

	public void updateCustomerPrepaymentNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getCustomerPrepaymentNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setCustomerPrepaymentNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getCustomerPaymentNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getCustomerPaymentNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getCustomerPaymentPrefix()+string+number.getCustomerPaymentSuffix();
		}
		return string;
	}

	public void updateCustomerPaymentNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getCustomerPaymentNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setCustomerPaymentNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getDeliveryOrderNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getDeliveryOrderNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getDeliveryOrderPrefix()+string+number.getDeliveryOrderSuffix();
		}
		return string;
	}

	public void updateDeliveryOrderNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getDeliveryOrderNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setDeliveryOrderNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getPosOrderNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getPosOrderNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			Calendar calendar = new GregorianCalendar();
			string = calendar.get(Calendar.YEAR)+(String.valueOf(calendar.get(Calendar.MONTH)).length()>1?String.valueOf(calendar.get(Calendar.MONTH)):"0"+String.valueOf(calendar.get(Calendar.MONTH)))+string;
		}
		return string;
	}

	public void updatePosOrderNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getPosOrderNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setPosOrderNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getMemberNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getMemberNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			//Calendar calendar = new GregorianCalendar();
			//string = string;
		}
		return string;
	}

	public void updateMemberNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getMemberNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setMemberNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String getBankTransactionNumber() {
		RunningNumber number = (RunningNumber)getInstance().getSession().createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getBankTransactionNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			string = number.getBankTransactionPrefix()+string+number.getBankTransactionSuffix();
		}
		return string;
	}

	public void updateBankTransactionNumber(Session session) {
		RunningNumber number = (RunningNumber)session.createCriteria(RunningNumber.class).setMaxResults(1).uniqueResult();
		String string = null;
		if (number!=null) {
			// get number
			string = number.getBankTransactionNumber();
			if (string==null || string.length()==0) {
			    string = "000001";
			} else if (string!=null && string.equalsIgnoreCase(returnMax(string.length()))) {
				string = returnReset(string.length());
			} else {
				// update number
				String code = Integer.toString(Integer.parseInt(string)+1);
				string = returnNol(string.length()-code.length())+code;
			}
			number.setBankTransactionNumber(string);
			saveOrUpdate(number, session);
		}
	}
	
	public String returnNol(int i) {
		String x = "";
		for (int a=0; a<i; a++) {
			x = x + "0";
		}
		return x;
	}

	public String returnMax(int i) {
		String x = "";
		for (int a=0; a<i; a++) {
			x = x + "9";
		}
		return x;
	}
	
	public String returnReset(int i) {
		String x = "";
		for (int a=0; a<i; a++) {
			x = x + "0";
		}
		x = x.substring(0, (x.length()-1)) + "1";
		return x;
	}

}