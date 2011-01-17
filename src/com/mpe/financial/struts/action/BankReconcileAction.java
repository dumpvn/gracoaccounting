//---------------------------------------------------------
// Application: Accounting
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright © 2004 MPE
// Generated at Tue Aug 03 14:42:29 GMT+07:00 2004
//---------------------------------------------------------

package com.mpe.financial.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpe.common.CommonConstants;
import com.mpe.common.CommonUtil;
import com.mpe.common.Formater;
import com.mpe.common.Pager;
import com.mpe.financial.model.BankAccount;
import com.mpe.financial.model.BankReconcile;
import com.mpe.financial.model.BankTransaction;
import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.Journal;
import com.mpe.financial.model.JournalDetail;
import com.mpe.financial.model.JournalDetailPK;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.BankAccountDAO;
import com.mpe.financial.model.dao.BankReconcileDAO;
import com.mpe.financial.model.dao.BankTransactionDAO;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.struts.form.BankReconcileForm;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class BankReconcileAction extends Action {
	Log log = LogFactory.getFactory().getInstance(this.getClass());

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
		BankReconcileForm bankReconcileForm = (BankReconcileForm) form;
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
						if (bankReconcileForm.getString("subaction")!=null && bankReconcileForm.getString("subaction").length()>0 && bankReconcileForm.getString("subaction").equalsIgnoreCase("refresh")) {
							forward = performForm(mapping, form, request, response);
						} else {
							forward = performSave(mapping, form, request, response);
						}
					} else if ("DETAIL".equalsIgnoreCase(action)) { 
						forward = performDetail(mapping, form, request, response);
					} else if ("DELETE".equalsIgnoreCase(action)) {
						forward = performDelete(mapping, form, request, response);
					}
					return forward;
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.privilage",request.getContextPath()+mapping.getPath()));
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
		BankReconcileForm form = (BankReconcileForm) actionForm;
		HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
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
			Criteria criteria = BankReconcileDAO.getInstance().getSession().createCriteria(BankReconcile.class);
			//if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromTransactionDate")!=null)criteria.add(Restrictions.ge("TransactionDate", new Date(form.getCalendar("fromTransactionDate").getTime().getTime())));
			if (form.getCalendar("toTransactionDate")!=null)criteria.add(Restrictions.le("TransactionDate", new Date(form.getCalendar("toTransactionDate").getTime().getTime())));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = BankReconcileDAO.getInstance().getSession().createCriteria(BankReconcile.class);
			//if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromReconcileDate")!=null)criteria.add(Restrictions.ge("ReconcileDate", new Date(form.getCalendar("fromReconcileDate").getTime().getTime())));
			if (form.getCalendar("toReconcileDate")!=null)criteria.add(Restrictions.le("ReconcileDate", new Date(form.getCalendar("toReconcileDate").getTime().getTime())));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("BANKRECONCILE",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				BankReconcileDAO.getInstance().closeSessionForReal();
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
		BankReconcileForm form = (BankReconcileForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// relationships
			List bankAccountLst =BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.addOrder(Order.asc("Name"))
				.list();
			request.setAttribute("bankAccountLst", bankAccountLst);
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			if (form.getLong("bankReconcileId") == 0) {
				form.setString("bankReconcileId", 0);
				form.setCurentCalendar("reconcileDate");
				form.setString("isPosted", "N");
				//set default start at beginning of page
				// get last reconcile
				Calendar lastReconcileDate = (Calendar)BankReconcileDAO.getInstance().getSession()
					.createSQLQuery("select max(br.reconcile_date) as reconcileDate from bank_reconcile br where br.organization_id="+users.getOrganization().getId())
					.addScalar("reconcileDate", Hibernate.CALENDAR).uniqueResult();
				if (lastReconcileDate==null) {
					lastReconcileDate = new GregorianCalendar();
					lastReconcileDate.setTime(organizationSetup.getSetupDate());
				}
				form.setCalendar("lastReconcileDate", lastReconcileDate);
				if (form.getLong("bankAccountId")>0) {
					BankAccount bankAccount = BankAccountDAO.getInstance().get(form.getLong("bankAccountId"));
					List bankTransactionLst = BankTransactionDAO.getInstance().getSession()
						.createQuery("select bt from BankTransaction bt where bt.Organization.Id="+users.getOrganization().getId()+" " +
								"and (bt.FromBankAccount.Id="+bankAccount.getId()+" OR bt.ToBankAccount.Id="+bankAccount.getId()+") and (bt.ReconcileBankFrom<>'Y' OR bt.ReconcileBankTo<>'Y') order by bt.TransactionDate ASC")
						.list();
					request.setAttribute("bankTransactionLst", bankTransactionLst);
					// get beginning balance of bankAccount
					Double a = (Double)BankReconcileDAO.getInstance().getSession()
						.createSQLQuery("select sum(a.amount) as amount from general_ledger a where a.organization_id="+users.getOrganization().getId()+" " +
								"and a.chart_of_account_id="+bankAccount.getChartOfAccount().getId()+" and a.is_closed='N' and a.ledger_date>= :fromLedgerDate and a.ledger_date<= :toLedgerDate")
						.addScalar("amount", Hibernate.DOUBLE)
						.setDate("fromLedgerDate", form.getCalendar("lastReconcileDate").getTime())
						.setDate("toLedgerDate", form.getCalendar("reconcileDate").getTime())
						.uniqueResult();
					form.setString("beginningBalance", Formater.getFormatedOutputForm(a.doubleValue()));
				}
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				BankReconcile obj = BankReconcileDAO.getInstance().get(form.getLong("bankReconcileId"));
				form.setString("bankReconcileId",obj.getId());
				form.setCalendar("reconcileDate",obj.getReconcileDate());
				form.setString("beginningBalance",obj.getBeginningBalance());
				form.setString("endingBalance", Formater.getFormatedOutputForm(obj.getEndingBalance()));
				form.setString("serviceCharge",Formater.getFormatedOutputForm(obj.getServiceCharge()));
				form.setString("interestCharge",Formater.getFormatedOutputForm(obj.getInterestCharge()));
				form.setString("clearedBalance",Formater.getFormatedOutputForm(obj.getClearedBalance()));
				form.setString("difference",Formater.getFormatedOutputForm(obj.getDifference()));
				form.setString("description",obj.getDescription());
				form.setString("bankAccountId",obj.getBankAccount()!=null?obj.getBankAccount().getId():0);
				form.setString("serviceChargeAccountId",obj.getServiceChargeAccount()!=null?obj.getServiceChargeAccount().getId():0);
				form.setString("interestEarnedAccountId",obj.getInterestEarnedAccount()!=null?obj.getInterestEarnedAccount().getId():0);
				//form.setString("journalChartOfAccountId",obj.getJournalChartOfAccount());
				List bankTransactionLst = BankTransactionDAO.getInstance().getSession().createCriteria(BankTransaction.class)
					.add(Restrictions.eq("", new Long(obj.getId()))).list();
				request.setAttribute("bankTransactionLst", bankTransactionLst);
			}
		}catch(Exception ex) {
			try {
				List bankAccountLst =BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.addOrder(Order.asc("Name"))
					.list();
				request.setAttribute("bankAccountLst",bankAccountLst);
				List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				BankReconcileDAO.getInstance().closeSessionForReal();
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
		BankReconcileForm form = (BankReconcileForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT)+"&reconcileDate="+httpSession.getAttribute("reconcileDate")+"&toReconcileDate="+httpSession.getAttribute("toReconcileDate")+"&bankAccountId="+httpSession.getAttribute("bankAccountId"));
				return new ActionForward(sb.toString(),true);
			}
			Session session = BankReconcileDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			BankReconcile obj = null;
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			if (form.getLong("bankReconcileId") == 0) {
				obj = new BankReconcile();
				obj.setReconcileDate(form.getCalendar("reconcileDate")!=null?form.getCalendar("reconcileDate").getTime():null);
				obj.setBeginningBalance(form.getDouble("beginningBalance"));
				obj.setEndingBalance(form.getDouble("endingBalance"));
				obj.setServiceCharge(form.getDouble("serviceCharge"));
				obj.setInterestCharge(form.getDouble("interestCharge"));
				obj.setClearedBalance(form.getFloat("clearedBalance"));
				obj.setDifference(form.getDouble("difference"));
				obj.setPosted(false);
				obj.setDescription(form.getString("description"));
				BankAccount bankAccount = BankAccountDAO.getInstance().get(form.getLong("bankAccountId"));
				obj.setBankAccount(bankAccount);
				List bankTransactionLst = BankTransactionDAO.getInstance().getSession()
				.createQuery("select bt from BankTransaction bt where bt.Organization.Id="+users.getOrganization().getId()+" " +
						"and (bt.FromBankAccount.Id="+bankAccount.getId()+" OR bt.ToBankAccount.Id="+bankAccount.getId()+") and (bt.ReconcileBankFrom<>'Y' OR bt.ReconcileBankTo<>'Y') order by bt.TransactionDate ASC")
				.list();
				// update transaction
				double bankTransactionAmount = 0;
				Set set = new LinkedHashSet();
				for (int i=0; i<bankTransactionLst.size(); i++) {
					if (form.getLong("bankTransactionIdList", i)>0) {
						BankTransaction bankTransaction = BankTransactionDAO.getInstance().get(form.getLong("bankTransactionIdList", i));
						// check transfer between account or just transaction
						if (bankTransaction.getFromBankAccount().getId()>0) {
							bankTransaction.setReconcileBankFrom(true);
							BankTransactionDAO.getInstance().update(bankTransaction,session);
						} else if (bankTransaction.getToBankAccount().getId()>0) {
							bankTransaction.setReconcileBankTo(true);
							BankTransactionDAO.getInstance().update(bankTransaction, session);
						}
						bankTransactionAmount = bankTransactionAmount + bankTransaction.getAmount();
						set.add(bankTransaction);
					}
				}
				obj.setBankTransactions(set);
				// create journal ending balance
				if ((form.getDouble("beginningBalance")+bankTransactionAmount-form.getDouble("serviceCharge")+form.getDouble("interestCharge"))!=form.getDouble("endingBalance") && form.getLong("journalChartOfAccountId")>0) {
					Journal journal = new Journal();
					journal.setBankReconcile(obj);
					journal.setCurrency(obj.getCurrency());
					journal.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("reconcileDate")));
					journal.setJournalDate(obj.getReconcileDate());
					journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
					journal.setOrganization(users.getOrganization());
					journal.setPosted(false);
					journal.setReference(form.getString("reference"));
					journal.setCreateBy(users);
					journal.setCreateOn(form.getTimestamp("createOn"));
					// create journal detail
					Set set2 = journal.getJournalDetails();
					if (set2==null) set2 = new LinkedHashSet();
					// credit
					JournalDetail journalDetail = new JournalDetail();
					JournalDetailPK journalDetailPK = new JournalDetailPK();
					journalDetailPK.setChartOfAccount(obj.getBankAccount().getChartOfAccount());
					journalDetailPK.setJournal(journal);
					journalDetail.setId(journalDetailPK);
					journalDetail.setAmount(obj.getBankAccount().getChartOfAccount().isDebit()==false?(form.getDouble("endingBalance")-(form.getDouble("beginningBalance")+bankTransactionAmount-form.getDouble("serviceCharge")+form.getDouble("interestCharge"))):-(form.getDouble("endingBalance")-(form.getDouble("beginningBalance")+bankTransactionAmount-form.getDouble("serviceCharge")+form.getDouble("interestCharge"))));
					set2.add(journalDetail);
					// debit
					ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("journalChartOfAccountId"));
					JournalDetail journalDetail3 = new JournalDetail();
					JournalDetailPK journalDetailPK3 = new JournalDetailPK();
					journalDetailPK3.setJournal(journal);
					journalDetailPK3.setChartOfAccount(chartOfAccount);
					journalDetail3.setAmount(chartOfAccount.isDebit()==true?(form.getDouble("endingBalance")-(form.getDouble("beginningBalance")+bankTransactionAmount-form.getDouble("serviceCharge")+form.getDouble("interestCharge"))):-(form.getDouble("endingBalance")-(form.getDouble("beginningBalance")+bankTransactionAmount-form.getDouble("serviceCharge")+form.getDouble("interestCharge"))));
					journalDetail3.setId(journalDetailPK3);
					set2.add(journalDetail3);
					//journal.setJournalDetails(set2);
					//obj.setJournals(set2);
					if (form.getDouble("serviceCharge") > 0 && form.getLong("serviceChargeAccountId")>0) {
					    ChartOfAccount chartOfAccount2 = ChartOfAccountDAO.getInstance().get(form.getLong("serviceChargeAccountId"));
					    JournalDetail journalDetail2 = new JournalDetail();
							JournalDetailPK journalDetailPK2 = new JournalDetailPK();
							journalDetailPK2.setChartOfAccount(chartOfAccount2);
							journalDetailPK2.setJournal(journal);
							journalDetail2.setId(journalDetailPK2);
							journalDetail2.setAmount(chartOfAccount2.isDebit()==false?(form.getDouble("serviceCharge")):-(form.getDouble("serviceCharge")));
							set2.add(journalDetail2);
					}
					if (form.getDouble("interestCharge") > 0 && form.getLong("interestEarnedAccountId")>0) {
					    ChartOfAccount chartOfAccount2 = ChartOfAccountDAO.getInstance().get(form.getLong("interestEarnedAccountId"));
					    JournalDetail journalDetail2 = new JournalDetail();
							JournalDetailPK journalDetailPK2 = new JournalDetailPK();
							journalDetailPK2.setChartOfAccount(chartOfAccount2);
							journalDetailPK2.setJournal(journal);
							journalDetail2.setId(journalDetailPK2);
							journalDetail2.setAmount(chartOfAccount2.isDebit()==false?(form.getDouble("serviceCharge")):-(form.getDouble("serviceCharge")));
							set2.add(journalDetail2);
					}
					journal.setJournalDetails(set2);
					obj.setJournal(journal);
				}
			} else {	
			    
			}
			transaction.commit();
		}catch(Exception ex) {
			try {
				try {
					List bankAccountLst =BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.addOrder(Order.asc("Name"))
						.list();
					request.setAttribute("bankAccountLst",bankAccountLst);
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				BankReconcileDAO.getInstance().closeSessionForReal();
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
		BankReconcileForm form = (BankReconcileForm) actionForm;
		//HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			BankReconcile bankReconcile = BankReconcileDAO.getInstance().get(form.getLong("bankReconcileId"));
			request.setAttribute("bankReconcile", bankReconcile);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				BankReconcileDAO.getInstance().closeSessionForReal();
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
		BankReconcileForm form = (BankReconcileForm) actionForm;
		HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT)+"&reconcileDate="+httpSession.getAttribute("reconcileDate")+"&toReconcileDate="+httpSession.getAttribute("toReconcileDate")+"&bankAccountId="+httpSession.getAttribute("bankAccountId"));
				return new ActionForward(sb.toString(),true);
			}
			//delete reconcile
			BankReconcileDAO.getInstance().delete(form.getLong("bankReconcileId"));
		} catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				BankReconcileDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT)+"&reconcileDate="+httpSession.getAttribute("reconcileDate")+"&toReconcileDate="+httpSession.getAttribute("toReconcileDate")+"&bankAccountId="+httpSession.getAttribute("bankAccountId"));
		return new ActionForward(sb.toString(),true);
	}
	
	/** 
	 * Method generalError
	 * @param HttpServletRequest request
	 * @param Exception ex
	 */
	private void generalError(HttpServletRequest request, Exception ex) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.global",ex.getMessage()));
		saveMessages(request,errors);
	}

}