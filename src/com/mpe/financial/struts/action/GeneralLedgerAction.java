//---------------------------------------------------------
// Application: Garage
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright © 2005 MPE
// Generated at Sat Sep 03 19:38:16 GMT+07:00 2005
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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.Department;
import com.mpe.financial.model.GeneralLedger;
import com.mpe.financial.model.GeneralLedgerReport;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.ProfitLossReport;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.GeneralLedgerDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.struts.form.GeneralLedgerForm;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mpe.common.*;

public class GeneralLedgerAction extends Action {
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
		//GeneralLedgerForm uomForm = (GeneralLedgerForm) form;
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
					} else if ("CLOSINGFORM".equalsIgnoreCase(action)) {
						forward = performClosingForm(mapping, form, request, response);
					} else if ("CLOSINGCONFIRM".equalsIgnoreCase(action)) {
						forward = performClosingConfirm(mapping, form, request, response);
					} else if ("UNCLOSINGFORM".equalsIgnoreCase(action)) {
						forward = performUnclosingForm(mapping, form, request, response);
					} else if ("UNCLOSINGCONFIRM".equalsIgnoreCase(action)) {
						forward = performUnclosingConfirm(mapping, form, request, response);
					} else if ("JOURNALDETAILLIST".equalsIgnoreCase(action)) {
						forward = performJournalDetailList(mapping, form, request, response);
					}
					return forward;
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.privilage",request.getContextPath()+mapping.getPath()));
					saveErrors(request,errors);				
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
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// vendor
			Criteria criteria = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.addOrder(Order.asc("Company"));
			if (form.getLong("chartOfAccountId")>0) criteria.add(Restrictions.eq("ChartOfAccount.Id", new Long(form.getLong("chartOfAccountId"))));
			List vendorLst = criteria.list();
			request.setAttribute("vendorLst", vendorLst);
			// customer
			Criteria criteria2 = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.addOrder(Order.asc("Company"));
			if (form.getLong("chartOfAccountId")>0) criteria2.add(Restrictions.eq("ChartOfAccount.Id", new Long(form.getLong("chartOfAccountId"))));
			List customerLst = criteria2.list();
			request.setAttribute("customerLst", customerLst);
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
			.addOrder(Order.asc("Name")).list();
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("departmentLst", departmentLst);
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("projectLst", projectLst);
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
			if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
			//session.enableFilter("fromDate").setParameter("fromDate", new Date());
			//session.enableFilter("toDate").setParameter("toDate", new Date());
			//session.enableFilter("organizationId").setParameter("organizationId", new Long(users.getOrganization().getId()));
			//List list = session.createQuery("select generalLedgerReport from GeneralLedgerReport generalLedgerReport").list();
			List list = new LinkedList();
			String a = "";
			String b = "";
			if (form.getLong("projectId")>0) a = a + " and d.project_id = "+form.getLong("projectId")+" ";
			if (form.getLong("departmentId")>0) a = a + " and c.department_id = "+form.getLong("departmentId")+" ";
			if (form.getLong("customerId")>0) {
			    a = a + " and d.customer_id = "+form.getLong("customerId")+" ";
			    b = b + " and b.customer_id = "+form.getLong("customerId")+" ";
			}
			if (form.getLong("vendorId")>0) {
			    a = a + " and d.vendor_id = "+form.getLong("vendorId")+" ";
			    b = b + " and b.vendor_id = "+form.getLong("vendorId")+" ";
			}
			//if (form.getLong("shipId")>0) a = a + " and d.ship_id = "+form.getLong("shipId")+" ";
			//if (form.getLong("documentId")>0) a = a + " and d.document_id = "+form.getLong("documentId")+" ";
			String sql = "" +
					"select a.chart_of_account_id as {gl.ChartOfAccountId}, a.number as {gl.Number}, a.name as {gl.Name}, a.type as {gl.Type}, a.groups as {gl.Groups}, a.is_debit as {gl.Debit}, " +
					"IFNULL((select sum(b.amount) from general_ledger b where b.chart_of_account_id=a.chart_of_account_id and b.organization_id = :organizationId and b.is_setup='Y' and b.is_closed='N' "+b+" ), 0) as {gl.FirstSetupAmount}, " +
					"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :setupDate and d.journal_date < :fromDate and d.is_posted = 'Y' "+a+"), 0) as {gl.PreviousAmount}, " +
					"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :fromDate and d.journal_date <= :toDate and d.is_posted = 'Y' "+a+"), 0) as {gl.Amount}, " +
					"(select e.number_of_digit from organization_setup e where e.organization_id=:organizationId) as {gl.NumberOfDigit} " +
					"from chart_of_account a where 1=1 " +
					"and (" +
					"(IFNULL((select sum(b.amount) from general_ledger b where b.chart_of_account_id=a.chart_of_account_id and b.organization_id = :organizationId and b.is_setup='Y' and b.is_closed='N' "+b+" ), 0))<>0 " +
					"or (IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :setupDate and d.journal_date < :fromDate and d.is_posted = 'Y' "+a+"), 0))<>0 " +
					"or (IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :fromDate and d.journal_date <= :toDate and d.is_posted = 'Y' "+a+"), 0))<>0 " +
					")" +
					"";
			if (organizationSetup.getProfitLossAccount()!=null) sql = sql + " and a.chart_of_account_id <> "+organizationSetup.getProfitLossAccount().getId();
			if (form.getLong("chartOfAccountId")>0) sql = sql + " and a.chart_of_account_id = "+form.getLong("chartOfAccountId");
			sql = sql + " order by a.number asc ";
			list = session.createSQLQuery(sql)
			.addEntity("gl", GeneralLedgerReport.class)
			.setLong("organizationId", users.getOrganization().getId())
			.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
			.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
			.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
			.list();
			request.setAttribute("GENERALLEDGER", list);
			double firstAmount = 0;
			double debitAmount = 0;
			double creditAmount = 0;
			double endAmount = 0;
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				GeneralLedgerReport generalLedgerReport = (GeneralLedgerReport)iterator.next();
				if (generalLedgerReport.isDebit()==true) {
					if (generalLedgerReport.getFirstAmount()>=0) {
					    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
					} else {
					    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
					}
					if (generalLedgerReport.getAmount()>=0) {
						debitAmount = debitAmount + generalLedgerReport.getAmount();
					} else {
						creditAmount = creditAmount - generalLedgerReport.getAmount();
					}
					if (generalLedgerReport.getEndAmount()>=0) {
					    endAmount = endAmount + generalLedgerReport.getEndAmount();
					} else {
					    endAmount = endAmount + generalLedgerReport.getEndAmount();
					}
				} else {
					if (generalLedgerReport.getFirstAmount()>=0) {
					    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
					} else {
					    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
					}
					if (generalLedgerReport.getAmount()>=0) {
						creditAmount = creditAmount + generalLedgerReport.getAmount();
					} else {
						debitAmount = debitAmount - generalLedgerReport.getAmount();
					}
					if (generalLedgerReport.getEndAmount()>=0) {
					    endAmount = endAmount + generalLedgerReport.getEndAmount();
					} else {
					    endAmount = endAmount + generalLedgerReport.getEndAmount();
					}
				}
			}
			request.setAttribute("firstAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), firstAmount));
			//request.setAttribute("firstCreditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), firstCreditAmount));
			request.setAttribute("debitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitAmount));
			request.setAttribute("creditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditAmount));
			request.setAttribute("endAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), endAmount));
			//request.setAttribute("endCreditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), endCreditAmount));
			
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
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
	private ActionForward performJournalDetailList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
			.addOrder(Order.asc("Name")).list();
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("departmentLst", departmentLst);
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("projectLst", projectLst);
			// vendor
			Criteria criteria = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.addOrder(Order.asc("Company"));
			if (form.getLong("chartOfAccountId")>0) criteria.add(Restrictions.eq("ChartOfAccount.Id", new Long(form.getLong("chartOfAccountId"))));
			List vendorLst = criteria.list();
			request.setAttribute("vendorLst", vendorLst);
			// customer
			Criteria criteria2 = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.addOrder(Order.asc("Company"));
			if (form.getLong("chartOfAccountId")>0) criteria2.add(Restrictions.eq("ChartOfAccount.Id", new Long(form.getLong("chartOfAccountId"))));
			List customerLst = criteria2.list();
			request.setAttribute("customerLst", customerLst);
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
			if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
			//session.enableFilter("fromDate").setParameter("fromDate", new Date());
			//session.enableFilter("toDate").setParameter("toDate", new Date());
			//session.enableFilter("organizationId").setParameter("organizationId", new Long(users.getOrganization().getId()));
			//List list = session.createQuery("select generalLedgerReport from GeneralLedgerReport generalLedgerReport").list();
			
			Criteria criteria3Criteria = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class);
			if (form.getLong("chartOfAccountId")>0) criteria3Criteria.add(Restrictions.eq("Id", new Long(form.getLong("chartOfAccountId"))));
			criteria3Criteria.addOrder(Order.asc("Number"));
			List chartOfAccountList = criteria3Criteria.list();
			List chartOfAccountList2 = new LinkedList();
			double totalEndAmount = 0;
			
			Iterator iterator = chartOfAccountList.iterator();
			while (iterator.hasNext()) {
				ChartOfAccount chartOfAccount = (ChartOfAccount)iterator.next();
				
				String b = "";
				if (form.getLong("projectId")>0) b = b + " and journal.Project.Id = "+form.getLong("projectId")+" ";
				if (form.getLong("departmentId")>0) b = b + " and journal.Department.Id = "+form.getLong("departmentId")+" ";
				if (form.getLong("customerId")>0) b = b + " and journal.Customer.Id = "+form.getLong("customerId")+" ";
				if (form.getLong("vendorId")>0) b = b + " and journal.Vendor.Id = "+form.getLong("vendorId")+" ";
				//if (form.getLong("shipId")>0) b = b + " and journal.Ship.Id = "+form.getLong("shipId")+" ";
				//if (form.getLong("documentId")>0) b = b + " and journal.Document.Id = "+form.getLong("documentId")+" ";
				if (chartOfAccount!=null) b = b + " and journalDetail.Id.ChartOfAccount.Id = "+chartOfAccount.getId()+" ";
				List list2 = session.createQuery("select distinct journalDetail from JournalDetail journalDetail join journalDetail.Id.Journal journal where journal.JournalDate >= :fromDate and journal.JournalDate <= :toDate and journal.Posted='Y' and journal.Organization.Id = :organizationId "+b+" order by journalDetail.Id.ChartOfAccount.Id ASC, journal.JournalDate ASC, journal.Number ASC ")
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				//request.setAttribute("JOURNALDETAIL", list2);
				chartOfAccount.setJournalDetailList(list2);
							
				List list = new LinkedList();
				String a = "";
				String c = "";
				//if (form.getLong("projectId")>0) a = a + " and d.project_id = "+form.getLong("projectId")+" ";
				//if (form.getLong("departmentId")>0) a = a + " and c.department_id = "+form.getLong("departmentId")+" ";
				if (form.getLong("customerId")>0) {
				    a = a + " and d.customer_id = "+form.getLong("customerId")+" ";
				    c = c + " and b.customer_id = "+form.getLong("customerId")+" ";
				}
				if (form.getLong("vendorId")>0) {
				    a = a + " and d.vendor_id = "+form.getLong("vendorId")+" ";
				    c = c + " and b.vendor_id = "+form.getLong("vendorId")+" ";
				}
				//if (form.getLong("shipId")>0) a = a + " and d.ship_id = "+form.getLong("shipId")+" ";
				//if (form.getLong("documentId")>0) a = a + " and d.document_id = "+form.getLong("documentId")+" ";
				String sql = "" +
						"select a.chart_of_account_id as {gl.ChartOfAccountId}, a.number as {gl.Number}, a.name as {gl.Name}, a.type as {gl.Type}, a.groups as {gl.Groups}, a.is_debit as {gl.Debit}, " +
						"IFNULL((select sum(b.amount) from general_ledger b where b.chart_of_account_id=a.chart_of_account_id and b.organization_id = :organizationId and b.is_setup='Y' and b.is_closed='N' "+c+" ), 0) as {gl.FirstSetupAmount}, " +
						"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :setupDate and d.journal_date < :fromDate and d.is_posted = 'Y' "+a+"), 0) as {gl.PreviousAmount}, " +
						"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :fromDate and d.journal_date <= :toDate and d.is_posted = 'Y' "+a+"), 0) as {gl.Amount}, " +
						"(select e.number_of_digit from organization_setup e where e.organization_id=:organizationId) as {gl.NumberOfDigit} " +
						"from chart_of_account a " +
						"";
				//if (form.getLong("chartOfAccountId")>0) sql = sql + "where a.chart_of_account_id = "+form.getLong("chartOfAccountId");
				if (chartOfAccount!=null) sql = sql + "where a.chart_of_account_id = "+chartOfAccount.getId()+" ";
				sql = sql + " order by a.number asc ";
				list = session.createSQLQuery(sql)
					.addEntity("gl", GeneralLedgerReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				//request.setAttribute("GENERALLEDGER", list);
				
				
				double firstAmount = 0;
				//double firstCreditAmount = 0;
				double debitAmount = 0;
				double creditAmount = 0;
				double endAmount = 0;
				//double endCreditAmount = 0;
				Iterator iterator2Iterator = list.iterator();
				while (iterator2Iterator.hasNext()) {
					GeneralLedgerReport generalLedgerReport = (GeneralLedgerReport)iterator2Iterator.next();
					if (generalLedgerReport.isDebit()==true) {
						if (generalLedgerReport.getFirstAmount()>=0) {
						    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
						} else {
						    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
						}
						if (generalLedgerReport.getAmount()>=0) {
							debitAmount = debitAmount + generalLedgerReport.getAmount();
						} else {
							creditAmount = creditAmount - generalLedgerReport.getAmount();
						}
						if (generalLedgerReport.getEndAmount()>=0) {
						    endAmount = endAmount + generalLedgerReport.getEndAmount();
						    totalEndAmount = totalEndAmount + generalLedgerReport.getEndAmount();
						} else {
						    endAmount = endAmount + generalLedgerReport.getEndAmount();
						    totalEndAmount = totalEndAmount + generalLedgerReport.getEndAmount();
						}
					} else {
						if (generalLedgerReport.getFirstAmount()>=0) {
						    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
						} else {
						    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
						}
						if (generalLedgerReport.getAmount()>=0) {
							creditAmount = creditAmount + generalLedgerReport.getAmount();
						} else {
							debitAmount = debitAmount - generalLedgerReport.getAmount();
						}
						if (generalLedgerReport.getEndAmount()>=0) {
						    endAmount = endAmount + generalLedgerReport.getEndAmount();
						    totalEndAmount = totalEndAmount + generalLedgerReport.getEndAmount();
						} else {
						    endAmount = endAmount + generalLedgerReport.getEndAmount();
						    totalEndAmount = totalEndAmount + generalLedgerReport.getEndAmount();
						}
					}
				}
				chartOfAccount.setFirstAmount(firstAmount);
				chartOfAccount.setNumberOfDigit(organizationSetup.getNumberOfDigit());
				chartOfAccountList2.add(chartOfAccount);
				
			}
			
			request.setAttribute("chartOfAccountList", chartOfAccountList2);
			
			//request.setAttribute("firstDebitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), firstDebitAmount));
			//request.setAttribute("firstCreditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), firstCreditAmount));
			//request.setAttribute("firstAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), firstAmount));
			//request.setAttribute("debitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitAmount));
			//request.setAttribute("creditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditAmount));
			//request.setAttribute("endDebitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), endDebitAmount));
			//request.setAttribute("endCreditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), endCreditAmount));
			request.setAttribute("totalEndAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), totalEndAmount));
			
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
	/** 
	 * Method performClosingForm
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performClosingForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			form.setCalendar("setupDate", organizationSetup.getSetupDate());
			Calendar fromDate = new java.util.GregorianCalendar();
			fromDate.setTime(organizationSetup.getSetupDate());
			fromDate.set(Calendar.MONTH, fromDate.getActualMaximum(Calendar.MONTH));
			fromDate.set(Calendar.DATE, fromDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			form.setCalendar("fromDate", fromDate);
			java.util.Calendar toDate = new java.util.GregorianCalendar();
			toDate.setTime(organizationSetup.getSetupDate());
			toDate.set(Calendar.YEAR, toDate.get(Calendar.YEAR)+1);
			toDate.set(Calendar.MONTH, 0);
			toDate.set(Calendar.DATE, 1);
			form.setCalendar("toDate", toDate);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("form");
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
	}
	
	/** 
	 * Method performClosingConfirm
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performClosingConfirm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Transaction transaction = null;
		try {
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			java.util.Calendar toLastDate = new java.util.GregorianCalendar();
			toLastDate.setTime(organizationSetup.getSetupDate());
			toLastDate.set(Calendar.MONTH, toLastDate.getActualMaximum(Calendar.MONTH));
			toLastDate.set(Calendar.DATE, toLastDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			// new fiscalDate
			java.util.Calendar fromNewDate = new java.util.GregorianCalendar();
			fromNewDate.setTime(organizationSetup.getSetupDate());
			fromNewDate.set(Calendar.YEAR, fromNewDate.get(Calendar.YEAR)+1);
			fromNewDate.set(Calendar.MONTH, 0);
			fromNewDate.set(Calendar.DATE, 1);
			
			// revenue
			List revenueLst = new LinkedList();
			List expenseLst = new LinkedList();
			Session session = ChartOfAccountDAO.getInstance().getSession();
			transaction = session.beginTransaction();
			String sql = "select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
				"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where a.chart_of_account_id = b.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y'), 0) as {pf.Amount}, " +
				"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
				"from chart_of_account a " +
				"where a.groups = :groups " +
				"";
			revenueLst = session.createSQLQuery(sql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Revenue")
				.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
				.setDate("toDate", new Date(toLastDate.getTime().getTime()))
				.list();
			
			expenseLst = session.createSQLQuery(sql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Expense")
				.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
				.setDate("toDate", new Date(toLastDate.getTime().getTime()))
				.list();
			
			double revenueTotal = 0;
			double expenseTotal = 0;
			Iterator iterator = revenueLst.iterator();
			while (iterator.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator.next();
				revenueTotal = revenueTotal + profitLossReport.getAmount();
			}
			Iterator iterator2 = expenseLst.iterator();
			while (iterator2.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator2.next();
				expenseTotal = expenseTotal + profitLossReport.getAmount();
			}
			
			String sqlgl = "select a.chart_of_account_id as {gl.ChartOfAccountId}, a.number as {gl.Number}, a.name as {gl.Name}, a.type as {gl.Type}, a.groups as {gl.Groups}, a.is_debit as {gl.Debit}, " +
				"IFNULL((select sum(b.amount) from general_ledger b where b.chart_of_account_id=a.chart_of_account_id and b.organization_id = :organizationId and b.is_setup='Y' and b.is_closed='N'), 0) as {gl.FirstSetupAmount}, " +
				"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :setupDate and d.journal_date < :fromDate and d.is_posted = 'Y'), 0) as {gl.PreviousAmount}, " +
				"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :fromDate and d.journal_date <= :toDate and d.is_posted = 'Y'), 0) as {gl.Amount}, " +
				"(select e.number_of_digit from organization_setup e where e.organization_id=:organizationId) as {gl.NumberOfDigit} " +
				"from chart_of_account a " +
				"";
			List list = session.createSQLQuery(sqlgl)
			.addEntity("gl", GeneralLedgerReport.class)
			.setLong("organizationId", users.getOrganization().getId())
			.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
			.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
			.setDate("toDate", new Date(toLastDate.getTime().getTime()))
			.list();
			
			// update old general ledger
			session.createQuery("update GeneralLedger generalLedger set generalLedger.Closed = "+"'Y'"+" where generalLedger.Organization.Id = "+users.getOrganization().getId() + " and generalLedger.Closed = "+"'N'").executeUpdate();
			
			// update GL from start-end fiscal year
			java.util.Iterator iterator3 = list.iterator();
			while (iterator3.hasNext()) {
				GeneralLedgerReport generalLedgerReport = (GeneralLedgerReport)iterator3.next();
				// create setup GL for newFiscalDate
				ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(generalLedgerReport.getChartOfAccountId());
				//log.info("GT : "+chartOfAccount.getGroups()+" // "+chartOfAccount.getEndAmount()+" // "+firstNewFiscalDate);
				if (chartOfAccount.getGroups()!=null && chartOfAccount.getGroups().length()>0 && chartOfAccount.getGroups().equalsIgnoreCase("Revenue")) {
					// check group of revenue? --> set 0
					//generalLedger.setAmount(0);
				} else if (chartOfAccount.getGroups()!=null && chartOfAccount.getGroups().length()>0 && chartOfAccount.getGroups().equalsIgnoreCase("Expense")) {
					// check group of revenue? --> set 0
					//generalLedger.setAmount(0);
				} else if (organizationSetup.getRetainedAccount()!=null && chartOfAccount.getId()==organizationSetup.getRetainedAccount().getId()) {
					// check if chartOfAccount == retainedEarningAccountId of organizationSetup
					GeneralLedger generalLedger = new GeneralLedger();
					generalLedger.setLedgerDate(fromNewDate.getTime());
					generalLedger.setDebit(chartOfAccount.isDebit());
					generalLedger.setClosed(false);
					generalLedger.setSetup(true);
					generalLedger.setChartOfAccount(chartOfAccount);
					generalLedger.setCurrency(organizationSetup.getDefaultCurrency());
					generalLedger.setOrganization(users.getOrganization());
					generalLedger.setAmount(generalLedgerReport.getEndAmount() + revenueTotal - expenseTotal);
					GeneralLedgerDAO.getInstance().save(generalLedger, session);
				} else {
					GeneralLedger generalLedger = new GeneralLedger();
					generalLedger.setLedgerDate(fromNewDate.getTime());
					generalLedger.setDebit(chartOfAccount.isDebit());
					generalLedger.setClosed(false);
					generalLedger.setSetup(true);
					generalLedger.setChartOfAccount(chartOfAccount);
					generalLedger.setCurrency(organizationSetup.getDefaultCurrency());
					generalLedger.setOrganization(users.getOrganization());
					generalLedger.setAmount(generalLedgerReport.getEndAmount());
					GeneralLedgerDAO.getInstance().save(generalLedger, session);
				}
			}
			// update organizationSetup -->SetupDate & DISABLE SETUP
			organizationSetup.setSetupDate(fromNewDate.getTime());
			OrganizationSetupDAO.getInstance().update(organizationSetup, session);
			transaction.commit();
			session.close();
		}catch(Exception ex){
			try {
				transaction.rollback();
			}catch(Exception exx){}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("confirm");
	}
	
	/** 
	 * Method performUnclosingForm
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performUnclosingForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			form.setCalendar("setupDate", organizationSetup.getSetupDate());
			// previous date
			Calendar fromDate = new GregorianCalendar();
			fromDate.setTime(organizationSetup.getSetupDate());
			fromDate.set(Calendar.MONTH, 0);
			fromDate.set(Calendar.DATE, 1);
			fromDate.set(Calendar.YEAR, (fromDate.get(Calendar.YEAR))-1);
			form.setCalendar("fromDate", fromDate);
			Calendar toDate = new GregorianCalendar();
			toDate.setTime(organizationSetup.getSetupDate());
			toDate.set(Calendar.MONTH, toDate.getActualMaximum(Calendar.MONTH));
			toDate.set(Calendar.DATE, toDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			toDate.set(Calendar.YEAR, (toDate.get(Calendar.YEAR))-1);
			form.setCalendar("toDate", toDate);
			// cek previous setup data?
			Criteria criteria = GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class);
			criteria.add(Restrictions.eq("Setup", Boolean.TRUE));
			criteria.add(Restrictions.eq("Closed", Boolean.TRUE));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			criteria.add(Restrictions.ge("LedgerDate", new Date(fromDate.getTime().getTime())));
			criteria.add(Restrictions.le("LedgerDate", new Date(toDate.getTime().getTime())));
			criteria.setProjection(Projections.rowCount());
			int total = ((Integer)criteria.list().iterator().next()).intValue();
			request.setAttribute("total", String.valueOf(total));
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
	}
	
	/** 
	 * Method performUnclosingConfirm
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performUnclosingConfirm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Transaction transaction = null;
		try {
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			java.util.Calendar fromDate = new java.util.GregorianCalendar();
			fromDate.setTime(organizationSetup.getSetupDate());
			fromDate.set(Calendar.MONTH, 0);
			fromDate.set(Calendar.DATE, 1);
			fromDate.set(Calendar.YEAR, (fromDate.get(Calendar.YEAR))-1);
			java.util.Calendar toDate = new java.util.GregorianCalendar();
			toDate.setTime(organizationSetup.getSetupDate());
			toDate.set(Calendar.MONTH, toDate.getActualMaximum(Calendar.MONTH));
			toDate.set(Calendar.DATE, toDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			toDate.set(Calendar.YEAR, (toDate.get(Calendar.YEAR))-1);
			Session session = GeneralLedgerDAO.getInstance().getSession();
			transaction = session.beginTransaction();
			
			// remove setup GL at current fiscalDate
			session.createQuery("delete GeneralLedger generalLedger where generalLedger.Organization.Id = "+users.getOrganization().getId()+" " +
					"and generalLedger.Closed = "+"'N'").executeUpdate();
			
			// move previous GL to master GL
			Criteria criteria = GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class);
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			criteria.add(Restrictions.ge("LedgerDate", new Date(fromDate.getTime().getTime())));
			criteria.add(Restrictions.le("LedgerDate", new Date(toDate.getTime().getTime())));
			List list = criteria.list();
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				GeneralLedger generalLedger = (GeneralLedger)iterator.next();
				// move to master
				generalLedger.setClosed(false);
				GeneralLedgerDAO.getInstance().update(generalLedger, session);
			}
			// update organization setup
			organizationSetup.setSetupDate(fromDate.getTime());
			OrganizationSetupDAO.getInstance().update(organizationSetup, session);
			transaction.commit();
			session.close();
		}catch(Exception ex){
			try {
				transaction.rollback();
			}catch(Exception exx){}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("confirm");
	}
		
	/** 
	 * Method generalError
	 * @param HttpServletRequest request
	 * @param Exception ex
	 */
	private void generalError(HttpServletRequest request, Exception ex) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.global",ex.getMessage()));
		saveErrors(request,errors);
	}

}