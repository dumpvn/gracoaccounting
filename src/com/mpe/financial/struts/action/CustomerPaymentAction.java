//---------------------------------------------------------
// Application: Garage
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright © 2005 MPE
// Generated at Tue Sep 06 20:58:49 GMT+07:00 2005
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

import com.mpe.financial.model.BankAccount;
import com.mpe.financial.model.BankTransaction;
import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.Currency;
import com.mpe.financial.model.CustomerPaymentDetailPK;
import com.mpe.financial.model.CustomerRetur;
import com.mpe.financial.model.Department;
import com.mpe.financial.model.InvoicePolos;
import com.mpe.financial.model.InvoiceSimple;
import com.mpe.financial.model.Journal;
import com.mpe.financial.model.JournalDetail;
import com.mpe.financial.model.JournalDetailPK;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.SalesOrder;
import com.mpe.financial.model.CustomerPayment;
import com.mpe.financial.model.CustomerPaymentDetail;
import com.mpe.financial.model.DeliveryOrder;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Invoice;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.dao.BankAccountDAO;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.CustomerReturDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.InvoicePolosDAO;
import com.mpe.financial.model.dao.InvoiceSimpleDAO;
import com.mpe.financial.model.dao.JournalDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.SalesOrderDAO;
import com.mpe.financial.model.dao.CustomerPaymentDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.InvoiceDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.struts.form.CustomerPaymentForm;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class CustomerPaymentAction extends Action {
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
		CustomerPaymentForm purchaseOrderForm = (CustomerPaymentForm) form;
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
						if (purchaseOrderForm.getString("subaction")!=null && purchaseOrderForm.getString("subaction").equalsIgnoreCase("refresh")) {
							forward = performForm(mapping, form, request, response);
						} else {
							forward = performSave(mapping, form, request, response);
						}
					} else if ("DETAIL".equalsIgnoreCase(action)) { 
						forward = performDetail(mapping, form, request, response);
					} else if ("DELETE".equalsIgnoreCase(action)) {
						forward = performDelete(mapping, form, request, response);
					} else if ("UPDATESTATUS".equalsIgnoreCase(action)) {
						forward = performUpdateStatus(mapping, form, request, response);
					} else if ("KWITANSI".equalsIgnoreCase(action)) {
						forward = performKwitansi(mapping, form, request, response);
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
		CustomerPaymentForm form = (CustomerPaymentForm) actionForm;
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
			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerLst", customerLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
			Criteria criteria = CustomerPaymentDAO.getInstance().getSession().createCriteria(CustomerPayment.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromPaymentDate")!=null)criteria.add(Restrictions.ge("PaymentDate", new Date(form.getCalendar("fromPaymentDate").getTime().getTime())));
			if (form.getCalendar("toPaymentDate")!=null)criteria.add(Restrictions.le("PaymentDate", new Date(form.getCalendar("toPaymentDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = CustomerPaymentDAO.getInstance().getSession().createCriteria(CustomerPayment.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromPaymentDate")!=null)criteria.add(Restrictions.ge("PaymentDate", new Date(form.getCalendar("fromPaymentDate").getTime().getTime())));
			if (form.getCalendar("toPaymentDate")!=null)criteria.add(Restrictions.le("PaymentDate", new Date(form.getCalendar("toPaymentDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("CUSTOMERPAYMENT",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("customerPayment");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				CustomerPaymentDAO.getInstance().closeSessionForReal();
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
		CustomerPaymentForm form = (CustomerPaymentForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// remove
			CustomerPayment obj = (CustomerPayment)httpSession.getAttribute("customerPayment");
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEINVOICE")) {
				CustomerPaymentDetail removeCustomerPaymentDetail = null;
				Iterator iterator = obj.getCustomerPaymentDetails().iterator();
				while (iterator.hasNext()) {
					CustomerPaymentDetail customerPaymentDetail = (CustomerPaymentDetail)iterator.next();
					if (form.getLong("invoiceId") > 0 && customerPaymentDetail.getInvoice()!=null && form.getLong("invoiceId") == customerPaymentDetail.getInvoice().getId()) {
						removeCustomerPaymentDetail = customerPaymentDetail;
					} else if (form.getLong("invoiceSimpleId") > 0 && customerPaymentDetail.getInvoiceSimple()!=null && form.getLong("invoiceSimpleId") == customerPaymentDetail.getInvoiceSimple().getId()) {
						removeCustomerPaymentDetail = customerPaymentDetail;
					} else if (form.getLong("invoicePolosId") > 0 && customerPaymentDetail.getInvoicePolos()!=null && form.getLong("invoicePolosId") == customerPaymentDetail.getInvoicePolos().getId()) {
						removeCustomerPaymentDetail = customerPaymentDetail;
					}
				}
				if (removeCustomerPaymentDetail!=null) {
					Set set = obj.getCustomerPaymentDetails();
					set.remove(removeCustomerPaymentDetail);
					obj.setCustomerPaymentDetails(set);
				}
				form.setString("subaction", "");
				form.setString("invoiceId", "");
				form.setString("invoiceSimpleId", "");
				form.setString("invoicePolosId", "");
				httpSession.setAttribute("customerPayment", obj);
			}
			// remove retur
			if (form.getLong("customerReturId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVECUSTOMERRETUR")) {
			    CustomerRetur removeCustomerRetur = null;
					Iterator iterator = obj.getCustomerReturs().iterator();
					while (iterator.hasNext()) {
					  CustomerRetur customerRetur = (CustomerRetur)iterator.next();
						if (form.getLong("customerReturId") == customerRetur.getId()) {
						    removeCustomerRetur = customerRetur;
						}
					}
					if (removeCustomerRetur!=null) {
						Set set = obj.getCustomerReturs();
						set.remove(removeCustomerRetur);
						obj.setCustomerReturs(set);
					}
					form.setString("subaction", "");
					form.setString("customerReturId", "");
					httpSession.setAttribute("customerPayment", obj);
			}
			// relationships
			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerLst", customerLst);
			List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("currencyLst", currencyLst);
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			List bankAccountLst = BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("bankAccountLst", bankAccountLst);
			List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("locationLst", locationLst);
			if (form.getLong("customerPaymentId") == 0) {
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				form.setString("customerPaymentId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getCustomerPaymentNumber());
				form.setCurentCalendar("paymentDate");
				if (obj!=null) {
					form.setString("customerPaymentId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setCalendar("paymentDate",obj.getPaymentDate());
					form.setString("number",obj.getNumber());
					form.setString("cardNo",obj.getCardNo());
					form.setString("checkNo",obj.getCheckNo());
					form.setString("reference",obj.getReference());
					form.setString("description",obj.getDescription());
					form.setString("method",obj.getMethod());
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
					form.setString("exceedAccountId",obj.getExceedAccount()!=null?obj.getExceedAccount().getId():0);
					form.setString("bankAccountId",obj.getBankAccount()!=null?obj.getBankAccount().getId():0);
					form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
					form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					Set customerPaymentDetailLst = obj.getCustomerPaymentDetails();
					request.setAttribute("customerPaymentDetailLst", customerPaymentDetailLst);
					Set customerReturLst = obj.getCustomerReturs();
					request.setAttribute("customerReturLst", customerReturLst);
				} else {
						Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
						List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
							.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
						request.setAttribute("customerAliasLst", customerAliasLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = CustomerPaymentDAO.getInstance().get(form.getLong("customerPaymentId"));
					httpSession.setAttribute("customerPayment",obj);
				}
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				form.setString("customerPaymentId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setCalendar("paymentDate",obj.getPaymentDate());
				form.setString("number",obj.getNumber());
				form.setString("cardNo",obj.getCardNo());
				form.setString("checkNo",obj.getCheckNo());
				form.setString("reference",obj.getReference());
				form.setString("description",obj.getDescription());
				form.setString("method",obj.getMethod());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
				form.setString("exceedAccountId",obj.getExceedAccount()!=null?obj.getExceedAccount().getId():0);
				form.setString("bankAccountId",obj.getBankAccount()!=null?obj.getBankAccount().getId():0);
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
				form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				Set customerPaymentDetailLst = obj.getCustomerPaymentDetails();
				request.setAttribute("customerPaymentDetailLst", customerPaymentDetailLst);
				Set customerReturLst = obj.getCustomerReturs();
				request.setAttribute("customerReturLst", customerReturLst);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
			}
			// edit invoice
			if (form.getLong("invoiceId") > 0) {
				if (obj!=null && obj.getCustomerPaymentDetails()!=null) {
					Iterator iterator = obj.getCustomerPaymentDetails().iterator();
					while (iterator.hasNext()) {
						CustomerPaymentDetail customerPaymentDetail = (CustomerPaymentDetail)iterator.next();
						if (customerPaymentDetail.getInvoice()!=null && form.getLong("invoiceId") == customerPaymentDetail.getInvoice().getId()) {
							request.setAttribute("invoice", customerPaymentDetail.getInvoice());
							form.setString("paymentAmount", Formater.getFormatedOutputForm(obj.getNumberOfDigit(), customerPaymentDetail.getPaymentAmount()));
							form.setString("invoiceExchangeRate", Formater.getFormatedOutputForm(5, customerPaymentDetail.getInvoiceExchangeRate()));
							form.setString("invoiceAmount", Formater.getFormatedOutputForm(obj.getNumberOfDigit(), customerPaymentDetail.getInvoiceAmount()));
						}
					}
				}
			}
			// edit invoice simple
			if (form.getLong("invoiceSimpleId") > 0) {
				if (obj!=null && obj.getCustomerPaymentDetails()!=null) {
					Iterator iterator = obj.getCustomerPaymentDetails().iterator();
					while (iterator.hasNext()) {
						CustomerPaymentDetail customerPaymentDetail = (CustomerPaymentDetail)iterator.next();
						if (customerPaymentDetail.getInvoiceSimple()!=null && form.getLong("invoiceSimpleId") == customerPaymentDetail.getInvoiceSimple().getId()) {
							request.setAttribute("invoice", customerPaymentDetail.getInvoiceSimple());
							form.setString("paymentAmount", Formater.getFormatedOutputForm(obj.getNumberOfDigit(), customerPaymentDetail.getPaymentAmount()));
							form.setString("invoiceExchangeRate", Formater.getFormatedOutputForm(5, customerPaymentDetail.getInvoiceExchangeRate()));
							form.setString("invoiceAmount", Formater.getFormatedOutputForm(obj.getNumberOfDigit(), customerPaymentDetail.getInvoiceAmount()));
						}
					}
				}
			}
			// edit invoice
			if (form.getLong("invoicePolosId") > 0) {
				if (obj!=null && obj.getCustomerPaymentDetails()!=null) {
					Iterator iterator = obj.getCustomerPaymentDetails().iterator();
					while (iterator.hasNext()) {
						CustomerPaymentDetail customerPaymentDetail = (CustomerPaymentDetail)iterator.next();
						if (customerPaymentDetail.getInvoicePolos()!=null && form.getLong("invoicePolosId") == customerPaymentDetail.getInvoicePolos().getId()) {
							request.setAttribute("invoice", customerPaymentDetail.getInvoicePolos());
							form.setString("paymentAmount", Formater.getFormatedOutputForm(obj.getNumberOfDigit(), customerPaymentDetail.getPaymentAmount()));
							form.setString("invoiceExchangeRate", Formater.getFormatedOutputForm(5, customerPaymentDetail.getInvoiceExchangeRate()));
							form.setString("invoiceAmount", Formater.getFormatedOutputForm(obj.getNumberOfDigit(), customerPaymentDetail.getInvoiceAmount()));
						}
					}
				}
			}
			request.setAttribute("customerPaymentDetailAmount", obj!=null?obj.getFormatedCustomerPaymentDetailAmount():"-");
			request.setAttribute("customerReturAmount", obj!=null?obj.getFormatedCustomerReturAmount():"-");
			request.setAttribute("paymentAmount", obj!=null?obj.getFormatedPaymentAmount():"-");
		}catch(Exception ex) {
			try {
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("currencyLst", currencyLst);
				List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				List bankAccountLst = BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("bankAccountLst", bankAccountLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
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
				CustomerPaymentDAO.getInstance().closeSessionForReal();
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
		CustomerPaymentForm form = (CustomerPaymentForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = CustomerPaymentDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("customerPayment");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			CustomerPayment obj = (CustomerPayment)httpSession.getAttribute("customerPayment");
			if (form.getLong("customerPaymentId") == 0) {
				obj = (CustomerPayment)CustomerPaymentDAO.getInstance().getSession().createCriteria(CustomerPayment.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (CustomerPayment)httpSession.getAttribute("customerPayment");
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					if (obj==null) {
						obj = new CustomerPayment();
						obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
						obj.setCurrency(currency);
						Set set = obj.getCustomerPaymentDetails();
						if (set==null) set = new LinkedHashSet();
						// invoice standar
						Criteria criteria = InvoiceDAO.getInstance().getSession().createCriteria(Invoice.class)
							.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("CustomerPaymentStatus", new String(CommonConstants.CLOSE)))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)));
						if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
						List invoiceLst = criteria.list();
						Iterator iterator = invoiceLst.iterator();
						while (iterator.hasNext()) {
							Invoice invoice = (Invoice)iterator.next();
							if (invoice.getDifferenceAmount() > 0) {
								CustomerPaymentDetail customerPaymentDetail = new CustomerPaymentDetail();
								customerPaymentDetail.setInvoiceAmount(invoice.getInvoiceAfterDiscountAndTaxAndPrepayment());
								//customerPaymentDetail.setPaymentAmount(invoice.getInvoiceAfterDiscountAndTaxAndPrepayment());
								CustomerPaymentDetailPK customerPaymentDetailPK = new CustomerPaymentDetailPK();
								customerPaymentDetailPK.setCustomerPayment(obj);
								customerPaymentDetail.setInvoice(invoice);
								customerPaymentDetailPK.setIdentity(invoice.getNumber());
								customerPaymentDetail.setId(customerPaymentDetailPK);
								//customerPaymentDetail.setInvoiceExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(invoice.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate")));
								double a = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(invoice.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate")));
								customerPaymentDetail.setInvoiceExchangeRate(a);
								customerPaymentDetail.setPaymentAmount(invoice.getInvoiceAfterDiscountAndTaxAndPrepayment() * a);
								set.add(customerPaymentDetail);
							}
						}
						// invoice simple
						Criteria criteria2 = InvoiceSimpleDAO.getInstance().getSession().createCriteria(InvoiceSimple.class)
							.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("CustomerPaymentStatus", new String(CommonConstants.CLOSE)))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)));
						if (form.getLong("projectId")>0) criteria2.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
						List invoiceSimpleLst = criteria2.list();
						Iterator iterator2 = invoiceSimpleLst.iterator();
						while (iterator2.hasNext()) {
							InvoiceSimple invoiceSimple = (InvoiceSimple)iterator2.next();
							if (invoiceSimple.getDifferenceAmount() > 0) {
								CustomerPaymentDetail customerPaymentDetail = new CustomerPaymentDetail();
								customerPaymentDetail.setInvoiceAmount(invoiceSimple.getInvoiceSimpleAfterDiscountAndTaxAndPrepayment());
								//customerPaymentDetail.setPaymentAmount(invoiceSimple.getInvoiceSimpleAfterDiscountAndTaxAndPrepayment());
								CustomerPaymentDetailPK customerPaymentDetailPK = new CustomerPaymentDetailPK();
								customerPaymentDetailPK.setCustomerPayment(obj);
								customerPaymentDetail.setInvoiceSimple(invoiceSimple);
								customerPaymentDetailPK.setIdentity(invoiceSimple.getNumber());
								customerPaymentDetail.setId(customerPaymentDetailPK);
								double a = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(invoiceSimple.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate")));
								customerPaymentDetail.setInvoiceExchangeRate(a);
								customerPaymentDetail.setPaymentAmount(invoiceSimple.getInvoiceSimpleAfterDiscountAndTaxAndPrepayment() * a);
								set.add(customerPaymentDetail);
							}
						}
						// invoice polos
						Criteria criteria3 = InvoicePolosDAO.getInstance().getSession().createCriteria(InvoicePolos.class)
							.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("CustomerPaymentStatus", new String(CommonConstants.CLOSE)))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)));
						if (form.getLong("projectId")>0) criteria3.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
						List invoicePolosLst = criteria3.list();
						Iterator iterator3 = invoicePolosLst.iterator();
						while (iterator3.hasNext()) {
							InvoicePolos invoicePolos = (InvoicePolos)iterator3.next();
							if (invoicePolos.getDifferenceAmount() > 0) {
								CustomerPaymentDetail customerPaymentDetail = new CustomerPaymentDetail();
								customerPaymentDetail.setInvoiceAmount(invoicePolos.getInvoicePolosAfterDiscountAndTaxAndPrepayment());
								//customerPaymentDetail.setPaymentAmount(invoicePolos.getInvoicePolosAfterDiscountAndTaxAndPrepayment());
								CustomerPaymentDetailPK customerPaymentDetailPK = new CustomerPaymentDetailPK();
								customerPaymentDetailPK.setCustomerPayment(obj);
								customerPaymentDetail.setInvoicePolos(invoicePolos);
								customerPaymentDetailPK.setIdentity(invoicePolos.getNumber());
								customerPaymentDetail.setId(customerPaymentDetailPK);
								double a = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(invoicePolos.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate")));
								customerPaymentDetail.setInvoiceExchangeRate(a);
								customerPaymentDetail.setPaymentAmount(invoicePolos.getInvoicePolosAfterDiscountAndTaxAndPrepayment() * a);
								set.add(customerPaymentDetail);
							}
						}
						obj.setCustomerPaymentDetails(set);
						// retur
						Set set2 = obj.getCustomerReturs();
						if (set2==null)set2 = new LinkedHashSet();
						Criteria criteria4 = CustomerReturDAO.getInstance().getSession().createCriteria(CustomerRetur.class)
							.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("CustomerPaymentStatus", new String(CommonConstants.CLOSE)))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)));
						if (form.getLong("projectId")>0) criteria4.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
						List customerReturLst = criteria4.list();
						Iterator iterator4 = customerReturLst.iterator();
						while (iterator4.hasNext()) {
						    CustomerRetur customerRetur = (CustomerRetur)iterator4.next();
						    set2.add(customerRetur);
						}
						obj.setCustomerReturs(set2);
					}
					obj.setDescription(form.getString("description"));
					obj.setNumber(form.getString("number"));
					obj.setCardNo(form.getString("cardNo"));
					obj.setCheckNo(form.getString("checkNo"));
					obj.setMethod(form.getString("method"));
					obj.setReference(form.getString("reference"));
					obj.setPosted(false);
					obj.setOrganization(users.getOrganization());
					obj.setPaymentDate(form.getCalendar("paymentDate")!=null?form.getCalendar("paymentDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					BankAccount bankAccount = BankAccountDAO.getInstance().get(form.getLong("bankAccountId"));
					obj.setBankAccount(bankAccount);
					ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("exceedAccountId"));
					obj.setExceedAccount(chartOfAccount);
					//Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("paymentDate"));
					double e = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("paymentDate")));
					obj.setExchangeRate(e);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
					obj.setProject(project);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
					obj.setCustomerAlias(customersAlias);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					// create new customerPayment
					if (form.getLong("customerId")!=obj.getCustomer().getId()) {
						Set set = new LinkedHashSet();
						// invoice standar
						List invoiceLst = InvoiceDAO.getInstance().getSession().createCriteria(Invoice.class)
							.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("CustomerPaymentStatus", new String(CommonConstants.CLOSE)))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
						Iterator iterator = invoiceLst.iterator();
						while (iterator.hasNext()) {
							Invoice invoice = (Invoice)iterator.next();
							if (invoice.getDifferenceAmount() > 0) {
								CustomerPaymentDetail customerPaymentDetail = new CustomerPaymentDetail();
								customerPaymentDetail.setInvoiceAmount(invoice.getInvoiceAfterDiscountAndTaxAndPrepayment());
								//customerPaymentDetail.setPaymentAmount(invoice.getInvoiceAfterDiscountAndTaxAndPrepayment());
								CustomerPaymentDetailPK customerPaymentDetailPK = new CustomerPaymentDetailPK();
								customerPaymentDetailPK.setCustomerPayment(obj);
								customerPaymentDetail.setInvoice(invoice);
								customerPaymentDetailPK.setIdentity(invoice.getNumber());
								customerPaymentDetail.setId(customerPaymentDetailPK);
								//customerPaymentDetail.setInvoiceExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(invoice.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate")));
								double a = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(invoice.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate")));
								customerPaymentDetail.setInvoiceExchangeRate(a);
								customerPaymentDetail.setPaymentAmount(invoice.getInvoiceAfterDiscountAndTaxAndPrepayment() * a);
								set.add(customerPaymentDetail);
							}
						}
						// invoice simple
						List invoiceSimpleLst = InvoiceSimpleDAO.getInstance().getSession().createCriteria(InvoiceSimple.class)
							.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("CustomerPaymentStatus", new String(CommonConstants.CLOSE)))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
						Iterator iterator2 = invoiceSimpleLst.iterator();
						while (iterator2.hasNext()) {
							InvoiceSimple invoiceSimple = (InvoiceSimple)iterator2.next();
							if (invoiceSimple.getDifferenceAmount() > 0) {
								CustomerPaymentDetail customerPaymentDetail = new CustomerPaymentDetail();
								customerPaymentDetail.setInvoiceAmount(invoiceSimple.getInvoiceSimpleAfterDiscountAndTaxAndPrepayment());
								//customerPaymentDetail.setPaymentAmount(invoiceSimple.getInvoiceSimpleAfterDiscountAndTaxAndPrepayment());
								CustomerPaymentDetailPK customerPaymentDetailPK = new CustomerPaymentDetailPK();
								customerPaymentDetailPK.setCustomerPayment(obj);
								customerPaymentDetail.setInvoiceSimple(invoiceSimple);
								customerPaymentDetailPK.setIdentity(invoiceSimple.getNumber());
								customerPaymentDetail.setId(customerPaymentDetailPK);
								//customerPaymentDetail.setInvoiceExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(invoiceSimple.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate")));
								double a = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(invoiceSimple.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate")));
								customerPaymentDetail.setInvoiceExchangeRate(a);
								customerPaymentDetail.setPaymentAmount(invoiceSimple.getInvoiceSimpleAfterDiscountAndTaxAndPrepayment() * a);
								set.add(customerPaymentDetail);
							}
						}
						// invoice polos
						List invoicePolosLst = InvoicePolosDAO.getInstance().getSession().createCriteria(InvoicePolos.class)
							.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("CustomerPaymentStatus", new String(CommonConstants.CLOSE)))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
						Iterator iterator3 = invoicePolosLst.iterator();
						while (iterator3.hasNext()) {
							InvoicePolos invoicePolos = (InvoicePolos)iterator3.next();
							if (invoicePolos.getDifferenceAmount() > 0) {
								CustomerPaymentDetail customerPaymentDetail = new CustomerPaymentDetail();
								customerPaymentDetail.setInvoiceAmount(invoicePolos.getInvoicePolosAfterDiscountAndTaxAndPrepayment());
								//customerPaymentDetail.setPaymentAmount(invoicePolos.getInvoicePolosAfterDiscountAndTaxAndPrepayment());
								CustomerPaymentDetailPK customerPaymentDetailPK = new CustomerPaymentDetailPK();
								customerPaymentDetailPK.setCustomerPayment(obj);
								customerPaymentDetail.setInvoicePolos(invoicePolos);
								customerPaymentDetailPK.setIdentity(invoicePolos.getNumber());
								customerPaymentDetail.setId(customerPaymentDetailPK);
								//customerPaymentDetail.setInvoiceExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(invoicePolos.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate")));
								double a = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(invoicePolos.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate")));
								customerPaymentDetail.setInvoiceExchangeRate(a);
								customerPaymentDetail.setPaymentAmount(invoicePolos.getInvoicePolosAfterDiscountAndTaxAndPrepayment() * a);
								set.add(customerPaymentDetail);
							}
						}
						obj.setCustomerPaymentDetails(set);
						// retur
						Set set2 = obj.getCustomerReturs();
						if (set2==null)set2 = new LinkedHashSet();
						List customerReturLst = CustomerReturDAO.getInstance().getSession().createCriteria(CustomerRetur.class)
							.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("CustomerPaymentStatus", new String(CommonConstants.CLOSE)))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
						Iterator iterator4 = customerReturLst.iterator();
						while (iterator4.hasNext()) {
						    CustomerRetur customerRetur = (CustomerRetur)iterator4.next();
						    set2.add(customerRetur);
						}
						obj.setCustomerReturs(set2);
					}
					BankTransaction bankTransaction = new BankTransaction();
					bankTransaction.setNumber(RunningNumberDAO.getInstance().getBankTransactionNumber());
					bankTransaction.setAmount(obj.getCustomerPaymentDetailAmount());
					bankTransaction.setTransactionDate(obj.getPaymentDate());
					bankTransaction.setProject(obj.getProject());
					bankTransaction.setToBankAccount(obj.getBankAccount());
					bankTransaction.setCurrency(currency);
					bankTransaction.setCustomer(customers);
					bankTransaction.setExchangeRate(e);
					bankTransaction.setOrganization(users.getOrganization());
					bankTransaction.setPosted(false);
					bankTransaction.setReconcileBankTo(false);
					bankTransaction.setCustomerPayment(obj);
					bankTransaction.setCreateBy(users);
					bankTransaction.setCreateOn(form.getTimestamp("createOn"));
					// create journal
					Journal journal = new Journal();
					journal.setCustomerPayment(obj);
					journal.setCurrency(obj.getCurrency());
					journal.setProject(obj.getProject());
					journal.setExchangeRate(e);
					journal.setJournalDate(obj.getPaymentDate());
					journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
					journal.setOrganization(users.getOrganization());
					journal.setPosted(false);
					journal.setReference(form.getString("reference"));
					journal.setCustomer(customers);
					journal.setCreateBy(users);
					journal.setCreateOn(form.getTimestamp("createOn"));
					// journal detail
					Set set = null;
					if (set==null) set = new LinkedHashSet();
					// debit
					JournalDetail journalDetail = new JournalDetail();
					JournalDetailPK journalDetailPK = new JournalDetailPK();
					journalDetailPK.setChartOfAccount(bankAccount.getChartOfAccount());
					journalDetailPK.setJournal(journal);
					journalDetail.setId(journalDetailPK);
					if (bankAccount!=null)journalDetail.setAmount(bankAccount.getChartOfAccount().isDebit()==true?obj.getCustomerPaymentDetailAmount():-obj.getCustomerPaymentDetailAmount());
					set.add(journalDetail);
					// credit
					if (obj.getCustomerPaymentDetailAmount()-obj.getInvoiceAmount()>0) {
						JournalDetail journalDetail2 = new JournalDetail();
						JournalDetailPK journalDetailPK2 = new JournalDetailPK();
						journalDetailPK2.setChartOfAccount(obj.getExceedAccount());
						journalDetailPK2.setJournal(journal);
						journalDetail2.setId(journalDetailPK2);
						if (obj.getExceedAccount()!=null)journalDetail2.setAmount(obj.getExceedAccount().isDebit()==false?obj.getCustomerPaymentDetailAmount()-obj.getInvoiceAmount():-(obj.getCustomerPaymentDetailAmount()-obj.getInvoiceAmount()));
						set.add(journalDetail2);
					}
					// selisih kurs => TODO
					// credit
					JournalDetail journalDetail3 = new JournalDetail();
					JournalDetailPK journalDetailPK3 = new JournalDetailPK();
					journalDetailPK3.setJournal(journal);
					if (customers.getChartOfAccount()==null) {
						journalDetailPK3.setChartOfAccount(organizationSetup.getArAccount());
						journalDetail3.setAmount(organizationSetup.getArAccount().isDebit()==false?obj.getInvoiceAmount():-obj.getInvoiceAmount());
					} else {
						journalDetailPK3.setChartOfAccount(customers.getChartOfAccount());
						journalDetail3.setAmount(customers.getChartOfAccount().isDebit()==false?obj.getInvoiceAmount():-obj.getInvoiceAmount());
					}
					journalDetail3.setId(journalDetailPK3);
					set.add(journalDetail3);
					journal.setJournalDetails(set);
					obj.setJournal(journal);
					bankTransaction.setJournal(journal);
					journal.setBankTransaction(bankTransaction);
					obj.setBankTransaction(bankTransaction);
				} else {
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					List bankAccountLst = BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("bankAccountLst", bankAccountLst);
					List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("locationLst", locationLst);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = CustomerPaymentDAO.getInstance().get(form.getLong("customerPaymentId"));
				obj.setDescription(form.getString("description"));
				obj.setNumber(form.getString("number"));
				obj.setCardNo(form.getString("cardNo"));
				obj.setCheckNo(form.getString("checkNo"));
				obj.setMethod(form.getString("method"));
				obj.setReference(form.getString("reference"));
				//obj.setPosted(false);
				obj.setOrganization(users.getOrganization());
				obj.setPaymentDate(form.getCalendar("paymentDate")!=null?form.getCalendar("paymentDate").getTime():null);
				//obj.setStatus(CommonConstants.OPEN);
				BankAccount bankAccount = BankAccountDAO.getInstance().get(form.getLong("bankAccountId"));
				obj.setBankAccount(bankAccount);
				ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("exceedAccountId"));
				obj.setExceedAccount(chartOfAccount);
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
				obj.setProject(project);
				double e = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("paymentDate")));
				//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("paymentDate"));
				obj.setExchangeRate(e);
				Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setCustomer(customers);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
				obj.setCustomerAlias(customersAlias);
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				// bankTransaction
				BankTransaction bankTransaction = obj.getBankTransaction();
				bankTransaction.setAmount(obj.getCustomerPaymentDetailAmount());
				bankTransaction.setTransactionDate(obj.getPaymentDate());
				bankTransaction.setFromBankAccount(obj.getBankAccount());
				bankTransaction.setProject(obj.getProject());
				bankTransaction.setCurrency(currency);
				bankTransaction.setCustomer(customers);
				bankTransaction.setExchangeRate(e);
				bankTransaction.setOrganization(users.getOrganization());
				//bankTransaction.setPosted(false);
				//bankTransaction.setReconcileBankFrom(false);
				bankTransaction.setCustomerPayment(obj);
				bankTransaction.setCreateBy(createBy);
				bankTransaction.setCreateOn(form.getTimestamp("createOn"));
				bankTransaction.setChangeBy(users);
				bankTransaction.setChangeOn(form.getTimestamp("changeOn"));
				// create journal
				//Journal journal = obj.getJournal();
				obj.setJournal(null);
				Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
					.add(Restrictions.eq("CustomerPayment.Id", new Long(obj.getId()))).uniqueResult();
				journal.setCustomerPayment(obj);
				journal.setCurrency(obj.getCurrency());
				journal.setProject(obj.getProject());
				journal.setExchangeRate(e);
				journal.setJournalDate(obj.getPaymentDate());
				//journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
				journal.setOrganization(users.getOrganization());
				//journal.setPosted(false);
				journal.setReference(form.getString("reference"));
				journal.setCustomer(customers);
				journal.setCreateBy(createBy);
				journal.setCreateOn(form.getTimestamp("createOn"));
				journal.setChangeBy(users);
				journal.setChangeOn(form.getTimestamp("changeOn"));
				// journal detail
				journal.getJournalDetails().removeAll(journal.getJournalDetails());
				Set set = journal.getJournalDetails();
				// debit
				JournalDetail journalDetail = new JournalDetail();
				JournalDetailPK journalDetailPK = new JournalDetailPK();
				journalDetailPK.setChartOfAccount(bankAccount.getChartOfAccount());
				journalDetailPK.setJournal(journal);
				journalDetail.setId(journalDetailPK);
				if (bankAccount!=null)journalDetail.setAmount(bankAccount.getChartOfAccount().isDebit()==true?obj.getCustomerPaymentDetailAmount():-obj.getCustomerPaymentDetailAmount());
				set.add(journalDetail);
				// credit
				if (obj.getCustomerPaymentDetailAmount()-obj.getInvoiceAmount()>0) {
					JournalDetail journalDetail2 = new JournalDetail();
					JournalDetailPK journalDetailPK2 = new JournalDetailPK();
					journalDetailPK2.setChartOfAccount(obj.getExceedAccount());
					journalDetailPK2.setJournal(journal);
					journalDetail2.setId(journalDetailPK2);
					if (obj.getExceedAccount()!=null)journalDetail2.setAmount(obj.getExceedAccount().isDebit()==false?obj.getCustomerPaymentDetailAmount()-obj.getInvoiceAmount():-(obj.getCustomerPaymentDetailAmount()-obj.getInvoiceAmount()));
					set.add(journalDetail2);
				}
				// selisih kurs => TODO
				// credit
				JournalDetail journalDetail3 = new JournalDetail();
				JournalDetailPK journalDetailPK3 = new JournalDetailPK();
				journalDetailPK3.setJournal(journal);
				if (customers.getChartOfAccount()==null) {
					journalDetailPK3.setChartOfAccount(organizationSetup.getArAccount());
					journalDetail3.setAmount(organizationSetup.getArAccount().isDebit()==false?obj.getInvoiceAmount():-obj.getInvoiceAmount());
				} else {
					journalDetailPK3.setChartOfAccount(customers.getChartOfAccount());
					journalDetail3.setAmount(customers.getChartOfAccount().isDebit()==false?obj.getInvoiceAmount():-obj.getInvoiceAmount());
				}
				journalDetail3.setId(journalDetailPK3);
				set.add(journalDetail3);
				journal.setJournalDetails(set);
				obj.setJournal(journal);
				bankTransaction.setJournal(journal);
				journal.setBankTransaction(bankTransaction);
				obj.setBankTransaction(bankTransaction);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDINVOICE")) {
				if (form.getLong("invoiceId") >0 || form.getLong("invoicePolosId") >0 || form.getLong("invoiceSimpleId") >0) {
				  CustomerPaymentDetail customerPaymentDetail = new CustomerPaymentDetail();
					CustomerPaymentDetailPK customerPaymentDetailPK = new CustomerPaymentDetailPK();
					if (form.getLong("invoiceId")>0) {
						Invoice invoice = InvoiceDAO.getInstance().get(form.getLong("invoiceId"));
						customerPaymentDetail.setInvoice(invoice);
						customerPaymentDetailPK.setIdentity(invoice.getNumber());
					} else if (form.getLong("invoiceSimpleId")>0) {
				    InvoiceSimple invoiceSimple = InvoiceSimpleDAO.getInstance().get(form.getLong("invoiceSimpleId"));
				    customerPaymentDetail.setInvoiceSimple(invoiceSimple);
				    customerPaymentDetailPK.setIdentity(invoiceSimple.getNumber());
					} else if (form.getLong("invoicePolosId")>0) {
				    InvoicePolos invoicePolos = InvoicePolosDAO.getInstance().get(form.getLong("invoicePolosId"));
				    customerPaymentDetail.setInvoicePolos(invoicePolos);
				    customerPaymentDetailPK.setIdentity(invoicePolos.getNumber());
					}
					customerPaymentDetailPK.setCustomerPayment(obj);
					customerPaymentDetail.setId(customerPaymentDetailPK);
					customerPaymentDetail.setInvoiceAmount(form.getDouble("invoiceAmount"));
					customerPaymentDetail.setPaymentAmount(form.getDouble("paymentAmount"));
					customerPaymentDetail.setInvoiceExchangeRate(form.getDouble("invoiceExchangeRate"));
					Set set = obj.getCustomerPaymentDetails();
					if (set==null) set = new LinkedHashSet();
					CustomerPaymentDetail removeCustomerPaymentDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						CustomerPaymentDetail customerPaymentDetail2 = (CustomerPaymentDetail)iterator.next();
						if (customerPaymentDetail2.getInvoice()!=null && form.getLong("invoiceId")==customerPaymentDetail2.getInvoice().getId()) {
							removeCustomerPaymentDetail = customerPaymentDetail2;
						} else if (customerPaymentDetail2.getInvoiceSimple()!=null && form.getLong("invoiceSimpleId")==customerPaymentDetail2.getInvoiceSimple().getId()) {
						  removeCustomerPaymentDetail = customerPaymentDetail2;
						} else if (customerPaymentDetail2.getInvoicePolos()!=null && form.getLong("invoicePolosId")==customerPaymentDetail2.getInvoicePolos().getId()) {
						  removeCustomerPaymentDetail = customerPaymentDetail2;
						}
					}
					if (removeCustomerPaymentDetail!=null) {
						set.remove(removeCustomerPaymentDetail);
						set.add(customerPaymentDetail);
					} else {
						set.add(customerPaymentDetail);
					}

					obj.setCustomerPaymentDetails(set);
					// netral
					form.setString("invoiceId", "");
					form.setString("invoiceSimpleId", "");
					form.setString("invoicePolosId", "");
					form.setString("invoiceExchangeRate", "");
					form.setString("paymentAmount", "");
					form.setString("invoiceAmount", "");
				}
				// netral
				form.setString("invoiceId", "");
				form.setString("invoiceSimpleId", "");
				form.setString("invoicePolosId", "");
				form.setString("invoiceExchangeRate", "");
				form.setString("paymentAmount", "");
				form.setString("invoiceAmount", "");
			}
			// save to session
			httpSession.setAttribute("customerPayment", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateCustomerPaymentNumber(session);
					RunningNumberDAO.getInstance().updateJournalNumber(session);
					RunningNumberDAO.getInstance().updateBankTransactionNumber(session);
					CustomerPaymentDAO.getInstance().save(obj, session);
					transaction.commit();
					form.setString("customerPaymentId", obj.getId());
				} else {
					transaction = session.beginTransaction();
					CustomerPaymentDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("customerPayment");
				// finish
				ActionForward forward = mapping.findForward("update_status");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?customerPaymentId="+form.getLong("customerPaymentId"));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				if (transaction!=null) transaction.rollback();
				try {
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					List bankAccountLst = BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("bankAccountLst", bankAccountLst);
					List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("locationLst", locationLst);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
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
				CustomerPaymentDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?customerPaymentId="+form.getLong("customerPaymentId"));
		return new ActionForward(sb.toString(),true);
	}
	
	/** 
	 * Method performUpdateStatus
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performUpdateStatus(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomerPaymentForm form = (CustomerPaymentForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			Session session = CustomerPaymentDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			//long salesOrderId = 0;
			// customer Bill
			CustomerPayment customerPayment = CustomerPaymentDAO.getInstance().get(form.getLong("customerPaymentId"));
			if (customerPayment!=null) {
				Set customerPaymentDetailLst = customerPayment.getCustomerPaymentDetails();
				Iterator iterator = customerPaymentDetailLst.iterator();
				while (iterator.hasNext()) {
					CustomerPaymentDetail customerPaymentDetail = (CustomerPaymentDetail)iterator.next();
					long salesOrderId = 0;
					Invoice invoice = customerPaymentDetail.getInvoice();
					//log.info("Amount : "+invoice.getAmount());
					if (invoice!=null) {
					  //log.info("Amount : "+invoice.getDifferenceAmount());
					  salesOrderId = invoice.getDeliveryOrder()!=null?(invoice.getDeliveryOrder().getSalesOrder()!=null?invoice.getDeliveryOrder().getSalesOrder().getId():0):0;
						if (Formater.getFormatedOutputResult(invoice.getNumberOfDigit(), invoice.getDifferenceAmount())<=0) {
							invoice.setCustomerPaymentStatus(CommonConstants.CLOSE);
						} else {
							if (invoice.getCustomerPaymentAmount()>0) {
								invoice.setCustomerPaymentStatus(CommonConstants.PARTIAL);
							}
						}
						InvoiceDAO.getInstance().getSession().merge(invoice);
					}
					InvoicePolos invoicePolos = customerPaymentDetail.getInvoicePolos();
					//log.info("Amount : "+invoice.getAmount());
					if (invoicePolos!=null) {
					  //log.info("Amount : "+invoicePolos.getDifferenceAmount());
					  salesOrderId = invoicePolos.getDeliveryOrder()!=null?(invoicePolos.getDeliveryOrder().getSalesOrder()!=null?invoicePolos.getDeliveryOrder().getSalesOrder().getId():0):0;
						if (Formater.getFormatedOutputResult(invoice.getNumberOfDigit(), invoicePolos.getDifferenceAmount())<=0) {
						    invoicePolos.setCustomerPaymentStatus(CommonConstants.CLOSE);
						} else {
							if (invoicePolos.getCustomerPaymentAmount()>0) {
							    invoicePolos.setCustomerPaymentStatus(CommonConstants.PARTIAL);
							}
						}
						InvoicePolosDAO.getInstance().getSession().merge(invoicePolos);
					}
					InvoiceSimple invoiceSimple = customerPaymentDetail.getInvoiceSimple();
					//log.info("Amount : "+invoice.getAmount());
					if (invoiceSimple!=null) {
					  //log.info("Amount : "+invoiceSimple.getDifferenceAmount());
					  salesOrderId = invoiceSimple.getDeliveryOrder()!=null?(invoiceSimple.getDeliveryOrder().getSalesOrder()!=null?invoiceSimple.getDeliveryOrder().getSalesOrder().getId():0):0;
					  //log.info("Amount : "+invoiceSimple.getDifferenceAmount());
						if (Formater.getFormatedOutputResult(invoice.getNumberOfDigit(), invoiceSimple.getDifferenceAmount())<=0) {
						    invoiceSimple.setCustomerPaymentStatus(CommonConstants.CLOSE);
						} else {
							if (invoiceSimple.getCustomerPaymentAmount()>0) {
							    invoiceSimple.setCustomerPaymentStatus(CommonConstants.PARTIAL);
							}
						}
						InvoiceSimpleDAO.getInstance().getSession().merge(invoiceSimple);
					}
					//log.info("A1");
					//salesOrderId = invoice.getDeliveryOrder()!=null?(invoice.getDeliveryOrder().getSalesOrder()!=null?invoice.getDeliveryOrder().getSalesOrder().getId():0):0;
					if (salesOrderId > 0) {
					  //log.info("A");
						boolean isClosed = true;
						// update PO => customerPaymentStatus!!!
						SalesOrder salesOrder = SalesOrderDAO.getInstance().get(salesOrderId);
						Set deliveryOrderLst = salesOrder.getDeliveryOrders();
						Iterator iterator2 = deliveryOrderLst.iterator();
						while (iterator2.hasNext()) {
							DeliveryOrder deliveryOrder = (DeliveryOrder)iterator2.next();
							Invoice invoice2 = deliveryOrder.getInvoice();
							InvoicePolos invoicePolos2 = deliveryOrder.getInvoicePolos();
							InvoiceSimple invoiceSimple2 = deliveryOrder.getInvoiceSimple();
							if (invoice2==null && invoicePolos2==null && invoiceSimple2==null) {
							    isClosed = false;
							} else {
								if (invoice2!=null && !invoice2.getCustomerPaymentStatus().equalsIgnoreCase(CommonConstants.CLOSE)) {
									isClosed = false;
								}
								if (invoicePolos2!=null && !invoicePolos2.getCustomerPaymentStatus().equalsIgnoreCase(CommonConstants.CLOSE)) {
										isClosed = false;
								}
								if (invoiceSimple2!=null && !invoiceSimple2.getCustomerPaymentStatus().equalsIgnoreCase(CommonConstants.CLOSE)) {
										isClosed = false;
								}
							}
						}
						//log.info("B");
						if (isClosed) {
						    salesOrder.setCustomerPaymentStatus(CommonConstants.CLOSE);
							// update
							SalesOrderDAO.getInstance().update(salesOrder, session);
						} else {
						    salesOrder.setCustomerPaymentStatus(CommonConstants.PARTIAL);
							// update
							SalesOrderDAO.getInstance().update(salesOrder, session);
						}
						//log.info("C");
					}
					//log.info("D");
					//InvoiceDAO.getInstance().update(invoice, session);
					// retur
					Set customerReturLst = customerPayment.getCustomerReturs();
					Iterator iterator2 = customerReturLst.iterator();
					while (iterator2.hasNext()) {
					    CustomerRetur customerRetur = (CustomerRetur) iterator2.next();
					    customerRetur.setCustomerPaymentStatus(CommonConstants.CLOSE);
					    CustomerReturDAO.getInstance().getSession().merge(customerRetur);
					}
					//log.info("E");
				}
			}
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			//return (new ActionForward(mapping.getInput()));
			log.info("Error : "+ex);
		}finally {
			try {
				CustomerPaymentDAO.getInstance().closeSessionForReal();
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
		CustomerPaymentForm form = (CustomerPaymentForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			CustomerPayment customerPayment = CustomerPaymentDAO.getInstance().get(form.getLong("customerPaymentId"));
			request.setAttribute("customerPayment", customerPayment);
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				CustomerPaymentDAO.getInstance().closeSessionForReal();
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
		CustomerPaymentForm form = (CustomerPaymentForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			CustomerPaymentDAO.getInstance().delete(form.getLong("customerPaymentId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				CustomerPaymentDAO.getInstance().closeSessionForReal();
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
	private ActionForward performKwitansi(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomerPaymentForm form = (CustomerPaymentForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			CustomerPayment customerPayment = CustomerPaymentDAO.getInstance().get(form.getLong("customerPaymentId"));
			request.setAttribute("customerPayment", customerPayment);
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				CustomerPaymentDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("detail");
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