//---------------------------------------------------------
// Application: Accounting
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright © 2004 MPE
// Generated at Fri Sep 10 14:02:37 GMT+07:00 2004
//---------------------------------------------------------

package com.mpe.financial.struts.action;

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
import java.util.*;

import org.hibernate.Criteria;
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
import com.mpe.financial.model.BankTransaction;
import com.mpe.financial.model.Currency;
import com.mpe.financial.model.Department;
import com.mpe.financial.model.Journal;
import com.mpe.financial.model.JournalDetail;
import com.mpe.financial.model.JournalDetailPK;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.CustomerPrepayment;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.SalesOrder;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.dao.BankAccountDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.CustomerPrepaymentDAO;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.JournalDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.SalesOrderDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.struts.form.CustomerPrepaymentForm;

public class CustomerPrepaymentAction extends Action {
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
		CustomerPrepaymentForm CustomerPrepaymentForm = (CustomerPrepaymentForm) form;
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
				  if (CustomerPrepaymentForm.getString("subaction")!=null && CustomerPrepaymentForm.getString("subaction").length()>0) {
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
		CustomerPrepaymentForm form = (CustomerPrepaymentForm) actionForm;
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
				.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("projectLst", projectLst);
			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerLst", customerLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
			Criteria criteria = CustomerPrepaymentDAO.getInstance().getSession().createCriteria(CustomerPrepayment.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromPrepaymentDate")!=null)criteria.add(Restrictions.ge("PrepaymentDate", new Date(form.getCalendar("fromPrepaymentDate").getTime().getTime())));
			if (form.getCalendar("toPrepaymentDate")!=null)criteria.add(Restrictions.le("PrepaymentDate", new Date(form.getCalendar("toPrepaymentDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = CustomerPrepaymentDAO.getInstance().getSession().createCriteria(CustomerPrepayment.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromPrepaymentDate")!=null)criteria.add(Restrictions.ge("PrepaymentDate", new Date(form.getCalendar("fromPrepaymentDate").getTime().getTime())));
			if (form.getCalendar("toPrepaymentDate")!=null)criteria.add(Restrictions.le("PrepaymentDate", new Date(form.getCalendar("toPrepaymentDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("CUSTOMERPREPAYMENT",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("customerPrepayment");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				CustomerPrepaymentDAO.getInstance().closeSessionForReal();
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
	private ActionForward performForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomerPrepaymentForm form = (CustomerPrepaymentForm) actionForm;
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
			List customerLst =CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.addOrder(Order.asc("Company"))
				.list();
			request.setAttribute("customerLst",customerLst);
			List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("locationLst", locationLst);
			if (form.getLong("customerPrepaymentId") == 0) {
				List salesOrderLst =SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
				.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
				.add(Restrictions.ne("CustomerPaymentStatus", new String(CommonConstants.CLOSE)))
				.addOrder(Order.asc("Number"))
				.list();
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
				request.setAttribute("salesOrderLst",salesOrderLst);
				form.setString("customerPrepaymentId",0);
				form.setCurentCalendar("prepaymentDate");
				form.setString("isPosted","N");
				form.setString("number",RunningNumberDAO.getInstance().getCustomerPrepaymentNumber());
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				CustomerPrepayment obj = CustomerPrepaymentDAO.getInstance().get(form.getLong("customerPrepaymentId"));
				form.setString("customerPrepaymentId",obj.getId());
				form.setCalendar("prepaymentDate",obj.getPrepaymentDate());
				form.setString("amount",obj.getAmount());
				form.setString("number",obj.getNumber());
				form.setString("description",obj.getDescription());
				form.setString("reference",obj.getReference());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("bankAccountId",obj.getBankAccount()!=null?obj.getBankAccount().getId():0);
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
				form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				List salesOrderLst =SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
					.add(Restrictions.eq("Id", new Long(obj.getSalesOrder()!=null?obj.getSalesOrder().getId():0)))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.addOrder(Order.asc("Number"))
					.list();
				request.setAttribute("salesOrderLst",salesOrderLst);
				form.setString("salesOrderId",obj.getSalesOrder()!=null?obj.getSalesOrder().getId():0);
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
				List customerLst =CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.addOrder(Order.asc("Company"))
					.list();
				request.setAttribute("customerLst",customerLst);
				List salesOrderLst =SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.add(Restrictions.ne("CustomerPaymentStatus", new String(CommonConstants.CLOSE)))
					.addOrder(Order.asc("Number"))
					.list();
				request.setAttribute("salesOrderLst",salesOrderLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				CustomerPrepaymentDAO.getInstance().closeSessionForReal();
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
	private ActionForward performSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomerPrepaymentForm form = (CustomerPrepaymentForm) actionForm;
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
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			CustomerPrepayment obj = null;
			if (form.getLong("customerPrepaymentId") == 0) {
				obj = (CustomerPrepayment)CustomerPrepaymentDAO.getInstance().getSession().createCriteria(CustomerPrepayment.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = new CustomerPrepayment();
					obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
					BankAccount bankAccount =BankAccountDAO.getInstance().get(form.getLong("bankAccountId"));
					obj.setPrepaymentDate(form.getCalendar("prepaymentDate")!=null?form.getCalendar("prepaymentDate").getTime():null);
					obj.setAmount(form.getDouble("amount"));
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
					obj.setCustomerAlias(customersAlias);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					SalesOrder salesOrder = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
					obj.setSalesOrder(salesOrder);
					obj.setProject(salesOrder.getProject());
					obj.setDepartment(salesOrder.getDepartment());
					//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("prepaymentDate"));
					double e = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("prepaymentDate")));
					obj.setExchangeRate(e);
					obj.setBankAccount(bankAccount);
					obj.setNumber(form.getString("number"));
					obj.setPosted(false);
					obj.setStatus(CommonConstants.OPEN);
					obj.setInvoiceStatus(CommonConstants.OPEN);
					obj.setDescription(form.getString("description"));
					obj.setReference(form.getString("reference"));
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					obj.setOrganization(users.getOrganization());
					// bank transaction
					BankTransaction bankTransaction = new BankTransaction();
					bankTransaction.setAmount(obj.getAmount());
					bankTransaction.setNumber(RunningNumberDAO.getInstance().getBankTransactionNumber());
					bankTransaction.setTransactionDate(obj.getPrepaymentDate());
					bankTransaction.setToBankAccount(bankAccount);
					bankTransaction.setProject(salesOrder.getProject());
					bankTransaction.setDepartment(salesOrder.getDepartment());
					bankTransaction.setCurrency(currency);
					bankTransaction.setCustomer(customers);
					bankTransaction.setExchangeRate(e);
					bankTransaction.setOrganization(users.getOrganization());
					bankTransaction.setPosted(false);
					bankTransaction.setReconcileBankTo(false);
					bankTransaction.setCustomerPrepayment(obj);
					bankTransaction.setCreateBy(users);
					bankTransaction.setCreateOn(form.getTimestamp("createOn"));
					// create journal
					Journal journal = new Journal();
					journal.setCustomerPrepayment(obj);
					journal.setProject(salesOrder.getProject());
					journal.setCurrency(currency);
					journal.setExchangeRate(e);
					journal.setJournalDate(obj.getPrepaymentDate());
					journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
					journal.setOrganization(users.getOrganization());
					journal.setPosted(false);
					journal.setReference(form.getString("reference"));
					journal.setCustomer(customers);
					journal.setCreateBy(users);
					journal.setCreateOn(form.getTimestamp("createOn"));
					// journal detail
					// credit
					JournalDetail journalDetail = new JournalDetail();
					JournalDetailPK journalDetailPK = new JournalDetailPK();
					journalDetailPK.setChartOfAccount(organizationSetup.getPrepaymentAccount());
					journalDetailPK.setJournal(journal);
					journalDetail.setId(journalDetailPK);
					journalDetail.setDepartment(obj.getDepartment());
					journalDetail.setAmount(organizationSetup.getPrepaymentAccount()!=null?(organizationSetup.getPrepaymentAccount().isDebit()==true?obj.getAmount():-obj.getAmount()):0);
					// debit
					JournalDetail journalDetail2 = new JournalDetail();
					JournalDetailPK journalDetailPK2 = new JournalDetailPK();
					journalDetailPK2.setChartOfAccount(bankAccount.getChartOfAccount());
					journalDetailPK2.setJournal(journal);
					journalDetail2.setId(journalDetailPK2);
					journalDetail2.setDepartment(obj.getDepartment());
					journalDetail2.setAmount(bankAccount.getChartOfAccount().isDebit()==true?obj.getAmount():-obj.getAmount());
					Set set = journal.getJournalDetails();
					if (set==null) set = new LinkedHashSet();
					set.add(journalDetail);
					set.add(journalDetail2);
					journal.setJournalDetails(set);
					bankTransaction.setJournal(journal);
					obj.setJournal(journal);
					journal.setBankTransaction(bankTransaction);
					obj.setBankTransaction(bankTransaction);
					// save all
					session = CustomerPrepaymentDAO.getInstance().getSession();
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateCustomerPrepaymentNumber(session);
					RunningNumberDAO.getInstance().updateJournalNumber(session);
					RunningNumberDAO.getInstance().updateBankTransactionNumber(session);
					CustomerPrepaymentDAO.getInstance().save(obj, session);
					JournalDAO.getInstance().save(journal, session);
					transaction.commit();
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				obj = CustomerPrepaymentDAO.getInstance().load(form.getLong("customerPrepaymentId"));
				BankAccount bankAccount =BankAccountDAO.getInstance().get(form.getLong("bankAccountId"));
				obj.setPrepaymentDate(form.getCalendar("prepaymentDate")!=null?form.getCalendar("prepaymentDate").getTime():null);
				obj.setAmount(form.getDouble("amount"));
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setCustomer(customers);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
				obj.setCustomerAlias(customersAlias);
				SalesOrder salesOrder = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
				obj.setSalesOrder(salesOrder);
				obj.setProject(salesOrder.getProject());
				obj.setDepartment(salesOrder.getDepartment());
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				double e = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("prepaymentDate")));
				//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("prepaymentDate"));
				obj.setExchangeRate(e);
				obj.setBankAccount(bankAccount);
				obj.setNumber(form.getString("number"));
				//obj.setPosted(false);
				//obj.setStatus(CommonConstants.OPEN);
				if (obj.getAmount()>obj.getInvoicePaymentAmount() && obj.getInvoicePaymentAmount()>0) {
					obj.setInvoiceStatus(CommonConstants.PARTIAL);
				} else if (obj.getAmount()==obj.getInvoicePaymentAmount()) {
					obj.setInvoiceStatus(CommonConstants.CLOSE);
				} else if (obj.getInvoicePaymentAmount()==0) obj.setInvoiceStatus(CommonConstants.OPEN);
				obj.setDescription(form.getString("description"));
				obj.setReference(form.getString("reference"));
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setOrganization(users.getOrganization());
				// bank transaction
				BankTransaction bankTransaction = obj.getBankTransaction();
				bankTransaction.setAmount(obj.getAmount());
				bankTransaction.setTransactionDate(obj.getPrepaymentDate());
				bankTransaction.setToBankAccount(bankAccount);
				bankTransaction.setProject(salesOrder.getProject());
				bankTransaction.setDepartment(salesOrder.getDepartment());
				bankTransaction.setCurrency(currency);
				bankTransaction.setCustomer(customers);
				bankTransaction.setExchangeRate(e);
				bankTransaction.setOrganization(users.getOrganization());
				//bankTransaction.setPosted(false);
				//bankTransaction.setReconcileBankFrom(false);
				bankTransaction.setCustomerPrepayment(obj);
				bankTransaction.setCreateBy(createBy);
				bankTransaction.setCreateOn(form.getTimestamp("createOn"));
				bankTransaction.setChangeBy(users);
				bankTransaction.setChangeOn(form.getTimestamp("changeOn"));
				// journal
				Journal journal = obj.getJournal();
				journal.setCurrency(currency);
				journal.setExchangeRate(e);
				journal.setJournalDate(obj.getPrepaymentDate());
				journal.setOrganization(users.getOrganization());
				journal.setProject(salesOrder.getProject());
				//journal.setPosted(false);
				journal.setReference(form.getString("reference"));
				journal.setCustomer(customers);
				journal.setCreateBy(createBy);
				journal.setCreateOn(form.getTimestamp("createOn"));
				journal.setChangeBy(users);
				journal.setChangeOn(form.getTimestamp("changeOn"));
				journal.getJournalDetails().removeAll(journal.getJournalDetails());
				Set set = journal.getJournalDetails();
				// credit
				JournalDetail journalDetail = new JournalDetail();
				JournalDetailPK journalDetailPK = new JournalDetailPK();
				journalDetailPK.setChartOfAccount(organizationSetup.getPrepaymentAccount());
				journalDetailPK.setJournal(journal);
				journalDetail.setId(journalDetailPK);
				journalDetail.setDepartment(obj.getDepartment());
				journalDetail.setAmount(organizationSetup.getPrepaymentAccount()!=null?(organizationSetup.getPrepaymentAccount().isDebit()==false?obj.getAmount():-obj.getAmount()):0);
				set.add(journalDetail);
				// debit
				JournalDetail journalDetail2 = new JournalDetail();
				JournalDetailPK journalDetailPK2 = new JournalDetailPK();
				journalDetailPK2.setChartOfAccount(bankAccount.getChartOfAccount());
				journalDetailPK2.setJournal(journal);
				journalDetail2.setId(journalDetailPK2);
				journalDetail2.setDepartment(obj.getDepartment());
				journalDetail2.setAmount(bankAccount.getChartOfAccount().isDebit()==true?obj.getAmount():-obj.getAmount());
				set.add(journalDetail2);
				journal.setJournalDetails(set);
				bankTransaction.setJournal(journal);
				obj.setJournal(journal);
				journal.setBankTransaction(bankTransaction);
				obj.setBankTransaction(bankTransaction);
				// save all
				session = CustomerPrepaymentDAO.getInstance().getSession();
				transaction = session.beginTransaction();
				CustomerPrepaymentDAO.getInstance().getSession().merge(obj);
				transaction.commit();
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
					List customerLst =CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.addOrder(Order.asc("Company"))
					.list();
					request.setAttribute("customerLst",customerLst);
					List salesOrderLst =SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
						.add(Restrictions.ne("CustomerPaymentStatus", new String(CommonConstants.CLOSE)))
						.addOrder(Order.asc("Number"))
						.list();
					request.setAttribute("salesOrderLst",salesOrderLst);
					List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("locationLst", locationLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				CustomerPrepaymentDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
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
	private ActionForward performDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomerPrepaymentForm form = (CustomerPrepaymentForm) actionForm;
		//HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			CustomerPrepayment obj = CustomerPrepaymentDAO.getInstance().get(form.getLong("customerPrepaymentId"));
			request.setAttribute("customerPrepayment", obj);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				CustomerPrepaymentDAO.getInstance().closeSessionForReal();
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
	private ActionForward performDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomerPrepaymentForm form = (CustomerPrepaymentForm) actionForm;
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
			CustomerPrepaymentDAO.getInstance().delete(form.getLong("customerPrepaymentId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				CustomerPrepaymentDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT)+"&fromTransactionDate="+httpSession.getAttribute("fromTransactionDate")+"&toTransactionDate="+httpSession.getAttribute("toTransactionDate")+"&reference="+httpSession.getAttribute("reference")+"&fromBankAccountId="+httpSession.getAttribute("fromBankAccountId")+"&toBankAccountId="+httpSession.getAttribute("toBankAccountId"));
		return new ActionForward(sb.toString(),true);
	}
	
	/** 
	 * Method generalError
	 * @param HttpServletRequest request
	 * @param Exception ex
	 */
	private void generalError(HttpServletRequest request, Exception ex) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.global",ex));
		saveErrors(request,errors);
	}

}