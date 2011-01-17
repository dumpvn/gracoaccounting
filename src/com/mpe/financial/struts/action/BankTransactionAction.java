//---------------------------------------------------------
// Application: Accounting
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright © 2004 MPE
// Generated at Fri Sep 10 14:02:37 GMT+07:00 2004
//---------------------------------------------------------

package com.mpe.financial.struts.action;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.lowagie.text.Cell;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.mpe.common.AmountSay;
import com.mpe.common.CommonConstants;
import com.mpe.common.CommonUtil;
import com.mpe.common.Formater;
import com.mpe.common.Pager;
import com.mpe.financial.model.BankAccount;
import com.mpe.financial.model.BankTransaction;
import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.Currency;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.Department;
import com.mpe.financial.model.Journal;
import com.mpe.financial.model.JournalDetail;
import com.mpe.financial.model.JournalDetailPK;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.dao.BankAccountDAO;
import com.mpe.financial.model.dao.BankTransactionDAO;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.JournalDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.struts.form.BankTransactionForm;

public class BankTransactionAction extends Action {
	Log log = LogFactory.getFactory().getInstance(this.getClass());
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/** 
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		throws Exception {
		//DataSource ds = (DataSource) getDataSource(request, "dbaccounting");
		//Connection conn = null;
		BankTransactionForm bankTransactionForm = (BankTransactionForm) form;
		ActionForward forward = null;
		String action = mapping.getParameter();
		HttpSession session = request.getSession();
		Users users = (Users)session.getAttribute(CommonConstants.USER);
		Set lst = (Set)session.getAttribute(CommonConstants.VIEWACCESS);
			if (users!=null) {
				if (CommonUtil.isHasRoleAccess(lst,request.getServletPath())) {
					request.setAttribute("VIEWS",lst);
					if ("".equalsIgnoreCase(action)) {
						forward = mapping.findForward("home");
					} else if ("LIST".equalsIgnoreCase(action)) {
						forward = performPartialList(mapping, form, request, response);
					} else if ("FORM".equalsIgnoreCase(action)) {
						forward = performForm(mapping, form, request, response);
					} else if ("SAVE".equalsIgnoreCase(action)) {
						if (bankTransactionForm.getString("subaction")!=null && bankTransactionForm.getString("subaction").length()>0 && bankTransactionForm.getString("subaction").equalsIgnoreCase("refresh")) {
							forward = performForm(mapping, form, request, response);
						} else {
							forward = performSave(mapping, form, request, response);
						}
					} else if ("DETAIL".equalsIgnoreCase(action)) { 
						forward = performDetail(mapping, form, request, response);
					} else if ("DELETE".equalsIgnoreCase(action)) {
						forward = performDelete(mapping, form, request, response);
					} else if ("TRANSFERLIST".equalsIgnoreCase(action)) {
						forward = performTransferPartialList(mapping, form, request, response);
					} else if ("TRANSFERFORM".equalsIgnoreCase(action)) {
						forward = performTransferForm(mapping, form, request, response);
					} else if ("TRANSFERSAVE".equalsIgnoreCase(action)) {
					  if (bankTransactionForm.getString("subaction")!=null && bankTransactionForm.getString("subaction").length()>0) {
					      forward = performTransferForm(mapping, form, request, response);
					  } else {
					      forward = performTransferSave(mapping, form, request, response);
					  }
					} else if ("TRANSFERDETAIL".equalsIgnoreCase(action)) { 
						forward = performTransferDetail(mapping, form, request, response);
					} else if ("TRANSFERDELETE".equalsIgnoreCase(action)) {
						forward = performTransferDelete(mapping, form, request, response);
					} else if ("PDF".equalsIgnoreCase(action)) {
						forward = performPdf(mapping, form, request, response);
					} 
					return forward;
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.privilage",request.getContextPath()+mapping.getPath()));
					saveMessages(request,errors);				
					return mapping.findForward("home");
				}
			} else {
				return mapping.findForward("index");
			}
	}
	
	/** 
	 * Method performList
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performPartialList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		BankTransactionForm form = (BankTransactionForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			int start = 0;
			int count = 0;
			int total = 0;
			try {
				start = Integer.parseInt(request.getParameter("start"));
			}catch(Exception ex) {
			}
			try {
				count = Integer.parseInt(request.getParameter("count"));
			}catch(Exception ex) {
				try {
					ResourceBundle prop = ResourceBundle.getBundle("resource.ApplicationResources");
					count = Integer.parseInt(prop.getString("max.item.per.page"));
				}catch(Exception exx) {
				}
			}
			//save start and count attribute on session
			httpSession.setAttribute(CommonConstants.START,Integer.toString(start));
			httpSession.setAttribute(CommonConstants.COUNT,Integer.toString(count));
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("projectLst", projectLst);
			Criteria criteria = BankTransactionDAO.getInstance().getSession().createCriteria(BankTransaction.class);
			//if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromTransactionDate")!=null)criteria.add(Restrictions.ge("TransactionDate", new Date(form.getCalendar("fromTransactionDate").getTime().getTime())));
			if (form.getCalendar("toTransactionDate")!=null)criteria.add(Restrictions.le("TransactionDate", new Date(form.getCalendar("toTransactionDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			criteria.add(Restrictions.eq("Transfer", Boolean.FALSE));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = BankTransactionDAO.getInstance().getSession().createCriteria(BankTransaction.class);
			//if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromTransactionDate")!=null)criteria.add(Restrictions.ge("TransactionDate", new Date(form.getCalendar("fromTransactionDate").getTime().getTime())));
			if (form.getCalendar("toTransactionDate")!=null)criteria.add(Restrictions.le("TransactionDate", new Date(form.getCalendar("toTransactionDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			criteria.add(Restrictions.eq("Transfer", Boolean.FALSE));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("BANKTRANSACTION",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("bankTransaction");
			httpSession.removeAttribute("journal");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				BankTransactionDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
		

	/** 
	 * Method performForm
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */			
	private ActionForward performForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		BankTransactionForm form = (BankTransactionForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// remove
			BankTransaction obj = (BankTransaction)httpSession.getAttribute("bankTransaction");
			Journal journal = (Journal)httpSession.getAttribute("journal");
			if (form.getLong("chartOfAccountId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEBANKTRANSACTIONDETAIL")) {
				JournalDetail removeJournalDetail = null;
				Iterator iterator = journal.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail)iterator.next();
					if (form.getLong("chartOfAccountId") == journalDetail.getId().getChartOfAccount().getId()) {
							removeJournalDetail = journalDetail;
					}
				}
				if (removeJournalDetail!=null) {
					Set set = journal.getJournalDetails();
					set.remove(removeJournalDetail);
					journal.setJournalDetails(set);
				}
				form.setString("subaction", "");
				form.setString("chartOfAccountId", "");
				httpSession.setAttribute("journal", journal);
			}
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			// relationships
			List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("currencyLst",currencyLst);
			List bankAccountLst =BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.addOrder(Order.asc("Name"))
				.list();
			request.setAttribute("bankAccountLst",bankAccountLst);
			List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("vendorLst", vendorLst);
			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerLst", customerLst);
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			List departmentLst = DepartmentDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("departmentLst", departmentLst);
			//boolean reverse = false;
			if (form.getLong("bankTransactionId") == 0) {
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				if (obj==null) {
					form.setString("bankTransactionId",0);
					form.setCurentCalendar("transactionDate");
					form.setString("isTransfer","N");
					form.setString("isManualTransaction","Y");
					form.setString("number", RunningNumberDAO.getInstance().getBankTransactionNumber());
				} else {
					form.setCalendar("transactionDate",obj.getTransactionDate());
					form.setString("reference",obj.getReference());
					form.setString("number",obj.getNumber());
					form.setString("amount",Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), obj.getAmount()));
					//form.setString("isDebit",obj.isDebit()==true?"Y":"N");
					form.setString("isOnlineTransfer",obj.isOnlineTransfer()==true?"Y":"N");
					form.setString("isTransfer",obj.isTransfer()==true?"Y":"N");
					form.setString("isManualTransaction",obj.isManualTransaction()==true?"Y":"N");
					form.setString("note",obj.getNote());	
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("bankAccountId",obj.getFromBankAccount()!=null?obj.getFromBankAccount().getId():(obj.getToBankAccount()!=null?obj.getToBankAccount().getId():0));
					form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
					form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
					form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
					if (journal!=null) {
						Set journalDetailLst = journal.getJournalDetails();
						request.setAttribute("journalDetailLst", journalDetailLst);
					}
				}
			} else {
				if (obj==null) {
					obj = BankTransactionDAO.getInstance().get(form.getLong("bankTransactionId"));
					journal = obj.getJournal();
					httpSession.setAttribute("bankTransaction",obj);
					httpSession.setAttribute("journal",journal);
				}
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				form.setString("bankTransactionId",obj.getId());
				form.setCalendar("transactionDate",obj.getTransactionDate());
				form.setString("reference",obj.getReference());
				form.setString("number",obj.getNumber());
				form.setString("amount",Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), obj.getAmount()));
				//form.setString("isDebit",obj.isDebit()==true?"Y":"N");
				form.setString("isOnlineTransfer",obj.isOnlineTransfer()==true?"Y":"N");
				form.setString("isTransfer",obj.isTransfer()==true?"Y":"N");
				form.setString("isManualTransaction",obj.isManualTransaction()==true?"Y":"N");
				form.setString("note",obj.getNote());	
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("bankAccountId",obj.getFromBankAccount()!=null?obj.getFromBankAccount().getId():(obj.getToBankAccount()!=null?obj.getToBankAccount().getId():0));
				form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				if (journal!=null) {
					Set journalDetailLst = journal.getJournalDetails();
					//log.info("D : "+journalDetailLst.size());
					request.setAttribute("journalDetailLst", journalDetailLst);
				}
			}
			if (journal!=null && form.getLong("chartOfAccountId") > 0) {
				Iterator iterator = journal.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail)iterator.next();
					if (form.getLong("chartOfAccountId") == journalDetail.getId().getChartOfAccount().getId()) {
						form.setString("detailAmount", Formater.getFormatedOutputForm(journalDetail.getAmount()));
						form.setString("journalDetailDescription", journalDetail.getDescription());
						form.setString("chartOfAccountNumber", journalDetail.getId().getChartOfAccount().getNumber());
						form.setString("departmentId", journalDetail.getDepartment()!=null?journalDetail.getDepartment().getId():0);
					}
				}
			}
			// get total
			double debitAmount = 0;
			double creditAmount = 0;
			if (journal!=null) {
				java.util.Iterator iterator = journal.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail) iterator.next();
					if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
						if (journalDetail.getAmount()>0) {
							debitAmount = debitAmount + journalDetail.getAmount();
						} else {
							creditAmount = creditAmount + (-journalDetail.getAmount());
						}
					} else if (journalDetail.getId().getChartOfAccount().isDebit()==false){
						if (journalDetail.getAmount()<0) {
							debitAmount = debitAmount + (-journalDetail.getAmount());
						} else {
							creditAmount = creditAmount + journalDetail.getAmount();
						}
					}
				}
			}
			request.setAttribute("debitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitAmount));
			request.setAttribute("creditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditAmount));
		}catch(Exception ex) {
			try {
				List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("currencyLst",currencyLst);
				List bankAccountLst =BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.addOrder(Order.asc("Name"))
					.list();
				request.setAttribute("bankAccountLst",bankAccountLst);
				List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("vendorLst", vendorLst);
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				BankTransactionDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
	}
	
	/** 
	 * Method performSave
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		BankTransactionForm form = (BankTransactionForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
				if (isCancelled(request)) {
					httpSession.removeAttribute("journalDetailLst");
					ActionForward forward = mapping.findForward("list_redir");
					StringBuffer sb = new StringBuffer(forward.getPath());
					sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
					return new ActionForward(sb.toString(),true);
				}
				OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
				BankTransaction obj = (BankTransaction)httpSession.getAttribute("bankTransaction");
				Journal journal = (Journal)httpSession.getAttribute("journal");
				// debit --> in/to
				if (form.getLong("bankTransactionId") == 0) {
					if (obj==null) obj = new BankTransaction();
					BankAccount bankAccount =BankAccountDAO.getInstance().get(form.getLong("bankAccountId"));
					obj.setTransactionDate(form.getCalendar("transactionDate")!=null?form.getCalendar("transactionDate").getTime():null);
					obj.setAmount(form.getDouble("amount"));
					//obj.setChartOfAccount(bankAccount.getChartOfAccount());
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
					obj.setProject(project);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(vendors);
					double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("transactionDate"));
					obj.setExchangeRate(e);
					if (form.getDouble("amount")>0) {
						obj.setToBankAccount(bankAccount);
					} else {
						obj.setFromBankAccount(bankAccount);
					}
					obj.setManualTransaction(form.getString("isManualTransaction").length()>0?true:false);
					obj.setOnlineTransfer(form.getString("isOnlineTransfer").length()>0?true:false);
					obj.setPosted(false);
					obj.setReconcileBankFrom(false);
					obj.setReconcileBankTo(false);
					obj.setTransfer(false);
					obj.setNote(form.getString("note"));
					obj.setNumber(form.getString("number"));
					obj.setReference(form.getString("reference"));
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					obj.setOrganization(users.getOrganization());
					// create journal
					if (journal==null) {
						journal = new Journal();
						journal.setCurrency(currency);
						journal.setCustomer(customers);
						journal.setProject(obj.getProject());
						journal.setExchangeRate(e);
						journal.setJournalDate(obj.getTransactionDate());
						journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
						journal.setOrganization(users.getOrganization());
						journal.setPosted(false);
						journal.setReference(form.getString("reference"));
						journal.setVendor(vendors);
						journal.setCreateBy(users);
						journal.setCreateOn(form.getTimestamp("createOn"));
						// create journal detail #1
						JournalDetail journalDetail = new JournalDetail();
						JournalDetailPK journalDetailPK = new JournalDetailPK();
						journalDetailPK.setChartOfAccount(bankAccount.getChartOfAccount());
						journalDetailPK.setJournal(journal);
						journalDetail.setId(journalDetailPK);
						journalDetail.setAmount(form.getDouble("amount"));
						Set set = journal.getJournalDetails();
						if (set==null) set = new LinkedHashSet();
						set.add(journalDetail);
						journal.setJournalDetails(set);
						journal.setBankTransaction(obj);
					}
			} else {
					if (obj==null) obj = BankTransactionDAO.getInstance().load(form.getLong("bankTransactionId"));
					BankAccount bankAccount =BankAccountDAO.getInstance().get(form.getLong("bankAccountId"));
					obj.setTransactionDate(form.getCalendar("transactionDate")!=null?form.getCalendar("transactionDate").getTime():null);
					obj.setAmount(form.getDouble("amount"));
					//obj.setChartOfAccount(bankAccount.getChartOfAccount());
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
					obj.setProject(project);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(vendors);
					double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("transactionDate"));
					obj.setExchangeRate(e);
					if (form.getDouble("amount")>0) {
						obj.setToBankAccount(bankAccount);
					} else {
						obj.setFromBankAccount(bankAccount);
					}
					obj.setManualTransaction(form.getString("isManualTransaction").length()>0?true:false);
					obj.setOnlineTransfer(form.getString("isOnlineTransfer").length()>0?true:false);
					//obj.setPosted(false);
					obj.setReconcileBankFrom(false);
					obj.setReconcileBankTo(false);
					obj.setTransfer(false);
					obj.setNote(form.getString("note"));
					obj.setNumber(form.getString("number"));
					obj.setReference(form.getString("reference"));
					Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
					obj.setCreateBy(createBy);
					obj.setCreateOn(form.getTimestamp("createOn"));
					obj.setChangeBy(users);
					obj.setChangeOn(form.getTimestamp("changeOn"));
					obj.setOrganization(users.getOrganization());
					// update journal detail if amount changed!
					if (obj.getJournal()!=null) {
						journal.setCurrency(currency);
						journal.setProject(obj.getProject());
						journal.setCustomer(customers);
						journal.setExchangeRate(e);
						journal.setJournalDate(obj.getTransactionDate());
						journal.setOrganization(users.getOrganization());
						journal.setPosted(false);
						journal.setReference(form.getString("reference"));
						journal.setVendor(vendors);
						journal.setCreateBy(createBy);
						journal.setCreateOn(form.getTimestamp("createOn"));
						journal.setChangeBy(users);
						journal.setChangeOn(form.getTimestamp("changeOn"));
						Set set = journal.getJournalDetails();
						Iterator iterator = set.iterator();
						// create journal detail #1
						JournalDetail removeJournalDetail = null;
						JournalDetail journalDetail2 = null;
						while (iterator.hasNext()) {
							JournalDetail journalDetail = (JournalDetail)iterator.next();
							if (bankAccount.getChartOfAccount().getId()==journalDetail.getId().getChartOfAccount().getId()) {
								removeJournalDetail = journalDetail;
								journalDetail2 = new JournalDetail();
								JournalDetailPK journalDetailPK = new JournalDetailPK();
								journalDetailPK.setChartOfAccount(bankAccount.getChartOfAccount());
								journalDetailPK.setJournal(journal);
								journalDetail2.setId(journalDetailPK);
								journalDetail2.setAmount(form.getDouble("amount"));
							}
						}
						if (removeJournalDetail!=null && journalDetail2!=null) {
							set.remove(removeJournalDetail);
							set.add(journalDetail2);
						}
						journal.setJournalDetails(set);
						journal.setBankTransaction(obj);
						obj.setJournal(journal);
					}
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDBANKTRANSACTIONDETAIL")) {
				if (form.getLong("chartOfAccountId") >0 || form.getString("chartOfAccountNumber").length()>0) {
				  JournalDetail journalDetail = new JournalDetail();
					ChartOfAccount chartOfAccount = null;
					if (form.getLong("chartOfAccountId") >0) chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
					if (form.getString("chartOfAccountNumber").length()>0) chartOfAccount = (ChartOfAccount)ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class).add(Restrictions.eq("Number", form.getString("chartOfAccountNumber"))).setMaxResults(1).uniqueResult();
					JournalDetailPK journalDetailPK = new JournalDetailPK();
					journalDetailPK.setChartOfAccount(chartOfAccount);
					journalDetailPK.setJournal(journal);
					journalDetail.setId(journalDetailPK);
					journalDetail.setAmount(form.getDouble("detailAmount"));
					journalDetail.setDescription(form.getString("journalDetailDescription"));
					Department department = DepartmentDAO.getInstance().get(form.getLong("departmentId"));
					journalDetail.setDepartment(department);
					Set set = journal.getJournalDetails();
					if (set==null) set = new LinkedHashSet();
					JournalDetail removeJournalDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						JournalDetail JournalDetail2 = (JournalDetail)iterator.next();
						if (form.getLong("chartOfAccountId")==JournalDetail2.getId().getChartOfAccount().getId()) {
							removeJournalDetail = JournalDetail2;
						}
					}
					if (removeJournalDetail!=null) {
						set.remove(removeJournalDetail);
						set.add(journalDetail);
					} else {
						set.add(journalDetail);
					}
					journal.setJournalDetails(set);
					// netral
					form.setString("chartOfAccountId", "");
					form.setString("detailAmount", "");
					form.setString("subaction", "");
					form.setString("departmentId", "");
					form.setString("journalDetailDescription", "");
				}
				// netral
				form.setString("chartOfAccountId", "");
				form.setString("detailAmount", "");
				form.setString("subaction", "");
				form.setString("departmentId", "");
				form.setString("journalDetailDescription", "");
				obj.setJournal(journal);
			}
			// save to session
			httpSession.setAttribute("journal", journal);
			httpSession.setAttribute("bankTransaction", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					Session session = BankTransactionDAO.getInstance().getSession();
					Transaction transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateJournalNumber(session);
					RunningNumberDAO.getInstance().updateBankTransactionNumber(session);
					BankTransactionDAO.getInstance().save(obj, session);
					JournalDAO.getInstance().save(journal, session);
					transaction.commit();
				} else {
					Session session = BankTransactionDAO.getInstance().getSession();
					Transaction transaction = session.beginTransaction();
					BankTransactionDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("journal");
				httpSession.removeAttribute("bankTransaction");
				// finish
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("currencyLst",currencyLst);
				List bankAccountLst =BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.addOrder(Order.asc("Name"))
					.list();
				request.setAttribute("bankAccountLst",bankAccountLst);
				List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("vendorLst", vendorLst);
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				BankTransactionDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?bankTransactionId="+form.getLong("bankTransactionId"));
		return new ActionForward(sb.toString(),true);
	}
	
	/** 
	 * Method performList
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performTransferPartialList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		BankTransactionForm form = (BankTransactionForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			int start = 0;
			int count = 0;
			int total = 0;
			try {
				start = Integer.parseInt(request.getParameter("start"));
			}catch(Exception ex) {
			}
			try {
				count = Integer.parseInt(request.getParameter("count"));
			}catch(Exception ex) {
				try {
					ResourceBundle prop = ResourceBundle.getBundle("resource.ApplicationResources");
					count = Integer.parseInt(prop.getString("max.item.per.page"));
				}catch(Exception exx) {
				}
			}
			//save start and count attribute on httpSession
			httpSession.setAttribute(CommonConstants.START,Integer.toString(start));
			httpSession.setAttribute(CommonConstants.COUNT,Integer.toString(count));
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				//.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("projectLst", projectLst);
			Criteria criteria = BankTransactionDAO.getInstance().getSession().createCriteria(BankTransaction.class);
			//if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromTransactionDate")!=null)criteria.add(Restrictions.ge("TransactionDate", new Date(form.getCalendar("fromTransactionDate").getTime().getTime())));
			if (form.getCalendar("toTransactionDate")!=null)criteria.add(Restrictions.le("TransactionDate", new Date(form.getCalendar("toTransactionDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			criteria.add(Restrictions.eq("Transfer", Boolean.TRUE));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = BankTransactionDAO.getInstance().getSession().createCriteria(BankTransaction.class);
			//if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromTransactionDate")!=null)criteria.add(Restrictions.ge("TransactionDate", new Date(form.getCalendar("fromTransactionDate").getTime().getTime())));
			if (form.getCalendar("toTransactionDate")!=null)criteria.add(Restrictions.le("TransactionDate", new Date(form.getCalendar("toTransactionDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			criteria.add(Restrictions.eq("Transfer", Boolean.TRUE));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("BANKTRANSFER",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("bankTransaction");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				BankTransactionDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
		

	/** 
	 * Method performTransferForm
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */			
	private ActionForward performTransferForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		BankTransactionForm form = (BankTransactionForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// relationships
			List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("currencyLst",currencyLst);
			List bankAccountLst =BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.addOrder(Order.asc("Name"))
				.list();
			request.setAttribute("bankAccountLst",bankAccountLst);
			if (form.getLong("bankTransactionId") == 0) {
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				form.setString("bankTransactionId",0);
				form.setCurentCalendar("transactionDate");
				form.setString("isTransfer","Y");
				form.setString("number",RunningNumberDAO.getInstance().getBankTransactionNumber());
				form.setString("isManualTransaction","Y");
				form.setString("isPosted","N");
				form.setString("reconcileBankFrom","N");
				form.setString("reconcileBankTo","N");
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					//.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				BankTransaction obj = BankTransactionDAO.getInstance().get(form.getLong("bankTransactionId"));
				form.setString("bankTransactionId",obj.getId());
				form.setCalendar("transactionDate",obj.getTransactionDate());
				form.setString("reference",obj.getReference());
				form.setString("amount",obj.getAmount());
				//form.setString("isDebit",obj.isDebit());
				form.setString("isOnlineTransfer",obj.isOnlineTransfer()==true?"Y":"N");
				form.setString("isTransfer",obj.isTransfer()==true?"Y":"N");
				form.setString("isManualTransaction",obj.isManualTransaction()==true?"Y":"N");
				form.setString("note",obj.getNote());
				form.setString("number",obj.getNumber());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("fromBankAccountId",obj.getFromBankAccount()!=null?obj.getFromBankAccount().getId():0);
				form.setString("toBankAccountId",obj.getToBankAccount()!=null?obj.getToBankAccount().getId():0);
				form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
			}
		}catch(Exception ex) {
			try {
				List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("currencyLst",currencyLst);
				List bankAccountLst =BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.addOrder(Order.asc("Name"))
					.list();
				request.setAttribute("bankAccountLst",bankAccountLst);
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				BankTransactionDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
	}
	
	
	/** 
	 * Method performTransferSave
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performTransferSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		BankTransactionForm form = (BankTransactionForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = null;
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			if(form.getLong("fromBankAccountId")==form.getLong("toBankAccountId")){
				List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("currencyLst",currencyLst);
				List bankAccountLst =BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.addOrder(Order.asc("Name"))
					.list();
				request.setAttribute("bankAccountLst",bankAccountLst);
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				ActionMessages errors = new ActionMessages();
				errors.add("errorDuplicate",new ActionMessage("error.duplicate.account"));
				saveErrors(request,errors);
				return (new ActionForward(mapping.getInput()));
			} else {
				if (form.getLong("bankTransactionId") == 0) {
					BankTransaction obj = new BankTransaction();
					BankAccount fromBankAccount =BankAccountDAO.getInstance().get(form.getLong("fromBankAccountId"));
					BankAccount toBankAccount =BankAccountDAO.getInstance().get(form.getLong("toBankAccountId"));
					obj.setTransactionDate(form.getCalendar("transactionDate")!=null?form.getCalendar("transactionDate").getTime():null);
					obj.setAmount(form.getDouble("amount"));
					//obj.setChartOfAccount(fromBankAccount.getChartOfAccount());
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
					obj.setProject(project);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(vendors);
					double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("transactionDate"));
					obj.setExchangeRate(e);
					obj.setToBankAccount(fromBankAccount);
					obj.setFromBankAccount(toBankAccount);
					obj.setManualTransaction(form.getString("isManualTransaction").length()>0?true:false);
					obj.setOnlineTransfer(form.getString("isOnlineTransfer").length()>0?true:false);
					obj.setPosted(false);
					obj.setReconcileBankFrom(false);
					obj.setReconcileBankTo(false);
					obj.setTransfer(form.getString("isTransfer").length()>0?true:false);
					obj.setNote(form.getString("note"));
					obj.setNumber(form.getString("number"));
					obj.setReference(form.getString("reference"));
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					obj.setOrganization(users.getOrganization());
					// create journal
					Journal journal = new Journal();
					journal.setBankTransaction(obj);
					journal.setCurrency(currency);
					journal.setCustomer(customers);
					journal.setExchangeRate(e);
					journal.setJournalDate(obj.getTransactionDate());
					journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
					journal.setOrganization(users.getOrganization());
					journal.setPosted(false);
					journal.setReference(form.getString("reference"));
					journal.setVendor(vendors);
					journal.setProject(project);
					journal.setCreateBy(users);
					journal.setCreateOn(form.getTimestamp("createOn"));
					// journal detail
					JournalDetail journalDetail = new JournalDetail();
					JournalDetailPK journalDetailPK = new JournalDetailPK();
					journalDetailPK.setChartOfAccount(fromBankAccount.getChartOfAccount());
					journalDetailPK.setJournal(journal);
					journalDetail.setId(journalDetailPK);
					journalDetail.setAmount(-form.getDouble("amount"));
					// #2
					JournalDetail journalDetail2 = new JournalDetail();
					JournalDetailPK journalDetailPK2 = new JournalDetailPK();
					journalDetailPK2.setChartOfAccount(toBankAccount.getChartOfAccount());
					journalDetailPK2.setJournal(journal);
					journalDetail2.setId(journalDetailPK2);
					journalDetail2.setAmount(form.getDouble("amount"));
					Set set = journal.getJournalDetails();
					if (set==null) set = new LinkedHashSet();
					set.add(journalDetail);
					set.add(journalDetail2);
					journal.setJournalDetails(set);
					// save all
					session = BankTransactionDAO.getInstance().getSession();
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateJournalNumber(session);
					RunningNumberDAO.getInstance().updateBankTransactionNumber(session);
					BankTransactionDAO.getInstance().save(obj, session);
					JournalDAO.getInstance().save(journal, session);
					transaction.commit();
				} else {
					BankTransaction obj = BankTransactionDAO.getInstance().load(form.getLong("bankTransactionId"));
					BankAccount fromBankAccount =BankAccountDAO.getInstance().get(form.getLong("fromBankAccountId"));
					BankAccount toBankAccount =BankAccountDAO.getInstance().get(form.getLong("toBankAccountId"));
					obj.setTransactionDate(form.getCalendar("transactionDate")!=null?form.getCalendar("transactionDate").getTime():null);
					obj.setAmount(form.getDouble("amount"));
					//obj.setChartOfAccount(bankAccount.getChartOfAccount());
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
					obj.setProject(project);
					Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(vendors);
					double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("transactionDate"));
					obj.setExchangeRate(e);
					obj.setToBankAccount(fromBankAccount);
					obj.setFromBankAccount(toBankAccount);
					obj.setManualTransaction(form.getString("isManualTransaction").length()>0?true:false);
					obj.setOnlineTransfer(form.getString("isOnlineTransfer").length()>0?true:false);
					obj.setPosted(false);
					obj.setReconcileBankFrom(false);
					obj.setReconcileBankTo(false);
					obj.setTransfer(form.getString("isTransfer").length()>0?true:false);
					obj.setNote(form.getString("note"));
					obj.setNumber(form.getString("number"));
					obj.setReference(form.getString("reference"));
					Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
					obj.setCreateBy(createBy);
					obj.setCreateOn(form.getTimestamp("createOn"));
					obj.setChangeBy(users);
					obj.setChangeOn(form.getTimestamp("changeOn"));
					Journal journal = obj.getJournal();
					journal.setCurrency(currency);
					journal.setCustomer(customers);
					journal.setProject(project);
					journal.setExchangeRate(e);
					journal.setJournalDate(obj.getTransactionDate());
					journal.setOrganization(users.getOrganization());
					journal.setPosted(false);
					journal.setReference(form.getString("reference"));
					journal.setVendor(vendors);
					journal.setCreateBy(createBy);
					journal.setCreateOn(form.getTimestamp("createOn"));
					journal.setChangeBy(users);
					journal.setChangeOn(form.getTimestamp("changeOn"));
					journal.getJournalDetails().removeAll(journal.getJournalDetails());
					Set set = journal.getJournalDetails();
					// journal detail
					JournalDetail journalDetail2 = new JournalDetail();
					JournalDetailPK journalDetailPK2 = new JournalDetailPK();
					journalDetailPK2.setChartOfAccount(fromBankAccount.getChartOfAccount());
					journalDetailPK2.setJournal(journal);
					journalDetail2.setId(journalDetailPK2);
					journalDetail2.setAmount(-form.getDouble("amount"));
					set.add(journalDetail2);
					// #2
					JournalDetail journalDetail3 = new JournalDetail();
					JournalDetailPK journalDetailPK3 = new JournalDetailPK();
					journalDetailPK3.setChartOfAccount(toBankAccount.getChartOfAccount());
					journalDetailPK3.setJournal(journal);
					journalDetail3.setId(journalDetailPK3);
					journalDetail3.setAmount(form.getDouble("amount"));
					set.add(journalDetail3);
					journal.setJournalDetails(set);
					obj.setJournal(journal);
					// save all
					session = BankTransactionDAO.getInstance().getSession();
					transaction = session.beginTransaction();
					BankTransactionDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
			}
		} catch(Exception ex) {
			try {
				transaction.rollback();
				try {
					List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("currencyLst",currencyLst);
					List bankAccountLst =BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.addOrder(Order.asc("Name"))
						.list();
					request.setAttribute("bankAccountLst",bankAccountLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				BankTransactionDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
	}

		
	/** 
	 * Method performDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		BankTransactionForm form = (BankTransactionForm) actionForm;
		//HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			BankTransaction obj = BankTransactionDAO.getInstance().get(form.getLong("bankTransactionId"));
			request.setAttribute("bankTransaction", obj);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				BankTransactionDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("detail");
	}
	
		
	/** 
	 * Method performDelete
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		BankTransactionForm form = (BankTransactionForm) actionForm;
		HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT)+"&transactionDate="+httpSession.getAttribute("transactionDate")+"&toTransactionDate="+httpSession.getAttribute("toTransactionDate")+"&reference="+httpSession.getAttribute("reference")+"&fromBankAccountId="+httpSession.getAttribute("fromBankAccountId")+"&toBankAccountId="+httpSession.getAttribute("toBankAccountId"));
				return new ActionForward(sb.toString(),true);
			}
			// delete bank transaction
			BankTransactionDAO.getInstance().delete(form.getLong("bankTransactionId"));
		}catch(Exception ex) {
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				BankTransactionDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT)+"&transactionDate="+httpSession.getAttribute("transactionDate")+"&toTransactionDate="+httpSession.getAttribute("toTransactionDate")+"&reference="+httpSession.getAttribute("reference")+"&fromBankAccountId="+httpSession.getAttribute("fromBankAccountId")+"&toBankAccountId="+httpSession.getAttribute("toBankAccountId"));
		return new ActionForward(sb.toString(),true);
	}
	
	/** 
	 * Method performTransferDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performTransferDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		BankTransactionForm form = (BankTransactionForm) actionForm;
		//HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			BankTransaction obj = BankTransactionDAO.getInstance().get(form.getLong("bankTransactionId"));
			request.setAttribute("bankTransaction", obj);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				BankTransactionDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("detail");
	}
	
		
	/** 
	 * Method performTransferDelete
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performTransferDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		BankTransactionForm form = (BankTransactionForm) actionForm;
		HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT)+"&fromTransactionDate="+httpSession.getAttribute("fromTransactionDate")+"&toTransactionDate="+httpSession.getAttribute("toTransactionDate")+"&reference="+httpSession.getAttribute("reference")+"&fromBankAccountId="+httpSession.getAttribute("fromBankAccountId")+"&toBankAccountId="+httpSession.getAttribute("toBankAccountId"));
				return new ActionForward(sb.toString(),true);
			}
			// delete bank transaction
			BankTransactionDAO.getInstance().delete(form.getLong("bankTransactionId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				BankTransactionDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT)+"&fromTransactionDate="+httpSession.getAttribute("fromTransactionDate")+"&toTransactionDate="+httpSession.getAttribute("toTransactionDate")+"&reference="+httpSession.getAttribute("reference")+"&fromBankAccountId="+httpSession.getAttribute("fromBankAccountId")+"&toBankAccountId="+httpSession.getAttribute("toBankAccountId"));
		return new ActionForward(sb.toString(),true);
	}
	
	/** 
	 * Method performTransferPdf
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		BankTransactionForm form = (BankTransactionForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    // write to pdf document
		    Rectangle pageSize = new Rectangle(612, 936);
				com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,36,36,36,36);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter.getInstance(document, baos);
				  
				// footer page
				//HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
				//footer.setBorder(Rectangle.NO_BORDER);
				//document.setFooter(footer);
				document.open();
				
				BankTransaction bankTransaction = BankTransactionDAO.getInstance().get(form.getLong("bankTransactionId"));
				
				// table upper
				Table table1 = new Table(3);
				table1.setWidth(100);
				table1.setCellsFitPage(true);
				float[] a = {10,50,40};
				table1.setWidths(a);
				table1.setBorder(Rectangle.NO_BORDER);
				table1.setCellsFitPage(true);
				table1.setBorderWidth(1);
				table1.setPadding(1);
				table1.setSpacing(0);
				
				Cell cell = null;
				if (users.getOrganization().getLogoContentType()!=null && users.getOrganization().getLogoContentType().indexOf("image")!=-1) {
					try {
					    byte[] out = users.getOrganization().getLogo();
					    Image image = Image.getInstance(Toolkit.getDefaultToolkit().createImage(out), null);
					    image.scaleAbsoluteHeight(45);
					    image.scaleAbsoluteWidth(45);
					    cell = new Cell(image);
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setRowspan(2);
							table1.addCell(cell);
					} catch(Exception ex) {
					    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setRowspan(2);
							table1.addCell(cell);
					}
				} else {
			    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setRowspan(2);
					table1.addCell(cell);
				}
				
				cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("No. "+bankTransaction.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table1.addCell(cell);
				cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("Date : "+bankTransaction.getFormatedTransactionDate(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table1.addCell(cell);
				for (int j=0; j<6; j++) {
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setColspan(3);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table1.addCell(cell);
				}
				document.add(table1);
				
				Table table = new Table(2);
				table.setWidth(100);
				table.setCellsFitPage(true);
				table.setBorder(Rectangle.NO_BORDER);
				table.setBorderWidth(1);
				table.setPadding(1);
				table.setSpacing(0);
				int w[] = {70, 30};
				table.setWidths(w);
				table.setAutoFillEmptyCells(true);
				// set table title
				
				if (bankTransaction.isDebit()==true) {
					cell = new Cell(new Phrase("RECEIPT VOUCHER", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.NORMAL)));
					cell.setColspan(2);
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					for (int j=0; j<4; j++) {
						cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setColspan(2);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
					}
					cell = new Cell(new Phrase("Received from : "+(bankTransaction.getCustomer()!=null?bankTransaction.getCustomer().getCompany():" - "), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new Cell(new Phrase("Credit A/C : "+(bankTransaction.getToBankAccount()!=null?bankTransaction.getToBankAccount().getBank().getName():" - "), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				} else {
					cell = new Cell(new Phrase("PAYMENT VOUCHER", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.NORMAL)));
					cell.setColspan(2);
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					for (int j=0; j<4; j++) {
						cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setColspan(2);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
					}
					cell = new Cell(new Phrase("Paid To : "+(bankTransaction.getVendor()!=null?bankTransaction.getVendor().getCompany():" - "), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new Cell(new Phrase("Debit A/C : "+(bankTransaction.getFromBankAccount()!=null?bankTransaction.getFromBankAccount().getBank().getName():" - "), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell); 
				}
				
				cell = new Cell(new Phrase("Through BANK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table.addCell(cell);
				cell = new Cell(new Phrase("Amount",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table.addCell(cell);
				table.endHeaders();	
				
				// data
				cell = new Cell(new Phrase((bankTransaction.getReference().length()>0?bankTransaction.getReference()+"\n":"")+bankTransaction.getNote(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase((bankTransaction.getCurrency()!=null?bankTransaction.getCurrency().getSymbol():"")+" "+bankTransaction.getFormatedAmount()+"  ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
				int m = 31;
				int l = 0;
				//int l = (bankTransaction.getReference()+bankTransaction.getNote()).length();
				//m = m - Math.round(l / 90);
				if (bankTransaction.getReference()!=null && bankTransaction.getReference().length()>0) l = 1;
				if (bankTransaction.getNote().indexOf("\n")>0) {
				    StringTokenizer tokenizer = new StringTokenizer(bankTransaction.getNote(), "\n");
				    while (tokenizer.hasMoreTokens()) {
				        tokenizer.nextToken();
				        l++;
				    }
				    m = m - Math.round(4 *(l - 1));
				} else {
				    l = (bankTransaction.getNote()).length();
				    m = m - Math.round(l / 20);
				}
				// space
				for (int i=0; i<m; i++) {
						cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setNoWrap(false);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setNoWrap(false);
						cell.setBorder(Rectangle.LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
				}
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase("SAY : "+AmountSay.getSaying(bankTransaction.getFormatedAmount()).trim()+" "+bankTransaction.getCurrency().getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				document.add(table);
				
				// TT
				Table table2 = new Table(3);
				table2.setWidth(99);
				table2.setCellsFitPage(true);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				int w2[] = {33, 33, 33};
				table2.setWidths(w2);
				table2.setAutoFillEmptyCells(true);
				
				// space
				for (int i=0; i<25; i++) {
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setColspan(3);
					cell.setNoWrap(false);
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
				}
				
				cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				if (bankTransaction.isDebit()==true) {
					cell = new Cell(new Phrase("Receipt by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
				} else {
			    cell = new Cell(new Phrase("Name & Signature of Payee", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
				}
				cell = new Cell(new Phrase("Checked by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Approved by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				
				
				document.add(table2);
				
				
				document.close();
				//send pdf to browser
				response.setContentType("application/pdf");
				response.setContentLength(baos.size());
				ServletOutputStream out = response.getOutputStream();
				baos.writeTo(out);
				out.flush();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
		}finally {
			try {
				BankTransactionDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return null;
	}
	
	/** 
	 * Method generalError
	 * @param HttpServletRequest request
	 * @param Exception ex
	 */
	private void generalError(HttpServletRequest request, Exception ex) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.global",ex));
		saveMessages(request,errors);
	}

}