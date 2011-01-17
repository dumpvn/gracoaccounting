//---------------------------------------------------------
// Application: Garage
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright � 2005 MPE
// Generated at Tue Sep 06 20:58:49 GMT+07:00 2005
//---------------------------------------------------------

package com.mpe.financial.struts.action;

import javax.servlet.ServletOutputStream;
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

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
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
import com.mpe.financial.model.DeliveryOrder;
import com.mpe.financial.model.DeliveryOrderDetail;
import com.mpe.financial.model.Invoice;
import com.mpe.financial.model.InvoiceDetail;
import com.mpe.financial.model.InvoiceDetailPK;
import com.mpe.financial.model.Tax;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.InvoicePrepayment;
import com.mpe.financial.model.InvoicePrepaymentPK;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.JournalDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.CustomerPrepaymentDAO;
import com.mpe.financial.model.dao.DeliveryOrderDAO;
import com.mpe.financial.model.dao.InvoiceDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.TaxDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.struts.form.InvoiceForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class InvoiceAction extends Action {
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
		InvoiceForm purchaseOrderForm = (InvoiceForm) form;
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
					} else if ("FAKTURPAJAK".equalsIgnoreCase(action)) { 
						forward = performFakturPajak(mapping, form, request, response);
					} else if ("PRINTED".equalsIgnoreCase(action)) { 
						forward = performPrinted(mapping, form, request, response);
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
		InvoiceForm form = (InvoiceForm) actionForm;
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
			Criteria criteria = InvoiceDAO.getInstance().getSession().createCriteria(Invoice.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromInvoiceDate")!=null)criteria.add(Restrictions.ge("InvoiceDate", new Date(form.getCalendar("fromInvoiceDate").getTime().getTime())));
			if (form.getCalendar("toInvoiceDate")!=null)criteria.add(Restrictions.le("InvoiceDate", new Date(form.getCalendar("toInvoiceDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = InvoiceDAO.getInstance().getSession().createCriteria(Invoice.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromInvoiceDate")!=null)criteria.add(Restrictions.ge("InvoiceDate", new Date(form.getCalendar("fromInvoiceDate").getTime().getTime())));
			if (form.getCalendar("toInvoiceDate")!=null)criteria.add(Restrictions.le("InvoiceDate", new Date(form.getCalendar("toInvoiceDate").getTime().getTime())));
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
			request.setAttribute("INVOICE",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("invoice");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				InvoiceDAO.getInstance().closeSessionForReal();
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
		InvoiceForm form = (InvoiceForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Invoice obj = (Invoice)httpSession.getAttribute("invoice");
			// relationships
			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerLst", customerLst);
			List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("currencyLst", currencyLst);
			List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Ap", Boolean.FALSE))
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("taxLst", taxLst);
			List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("locationLst", locationLst);
			if (form.getLong("invoiceId") == 0) {
				form.setString("invoiceId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getInvoiceNumber());
				if (form.getCalendar("invoiceDate")==null) form.setCurentCalendar("invoiceDate");
				/*
				if (obj.getDeliveryOrder()!=null && obj.getDeliveryOrder().getSalesOrder()!=null) {
				    Calendar calendar = form.getCalendar("invoiceDate");
				    calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + obj.getDeliveryOrder().getSalesOrder().getCreditLimit());
				    form.setCalendar("invoiceDue", calendar);
				}*/
				form.setString("taxSerialNumber", organizationSetup.getNpwpSn()+"."+RunningNumberDAO.getInstance().getStandartNpwpTaxNumber());
				//form.setCalendar("taxDate", organizationSetup.getNpwpDate());
				if (form.getCalendar("taxDate")==null) form.setCurentCalendar("taxDate");
				if (obj!=null) {
					form.setString("invoiceId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setString("deliveryOrderId",obj.getDeliveryOrder()!=null?obj.getDeliveryOrder().getId():0);
					form.setString("number",obj.getNumber());
					form.setCalendar("invoiceDate",obj.getInvoiceDate());
					form.setCalendar("invoiceDue",obj.getInvoiceDue());
					//form.setString("bankAccountId",obj.getBankAccount()!=null?obj.getBankAccount().getId():0);
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					form.setString("reference",obj.getReference());
					form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
					form.setString("taxAmount",obj.getTaxAmount());
					form.setString("discountProcent",obj.getDiscountProcent());
					form.setString("discount1",obj.getDiscount1());
					form.setString("discount2",obj.getDiscount2());
					form.setString("discount3",obj.getDiscount3());
					//form.setString("termId",obj.getTerm()!=null?obj.getTerm().getId():0);
					form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
					form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
					Set invoiceDetailLst = obj.getInvoiceDetails();
					request.setAttribute("invoiceDetailLst", invoiceDetailLst);
					Set invoicePrepaymentLst = obj.getInvoicePrepayments();
					request.setAttribute("invoicePrepaymentLst", invoicePrepaymentLst);
				} else {
				    Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
						List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
							.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
						request.setAttribute("customerAliasLst", customerAliasLst);
				}
				List deliveryOrderLst = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class)
					.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("InvoiceStatus", new String(CommonConstants.CLOSE)))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.add(Restrictions.eq("Store", Boolean.FALSE))
					.list();
				request.setAttribute("deliveryOrderLst", deliveryOrderLst);
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = InvoiceDAO.getInstance().get(form.getLong("invoiceId"));
					httpSession.setAttribute("invoice",obj);
				}
				form.setString("invoiceId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("deliveryOrderId",obj.getDeliveryOrder()!=null?obj.getDeliveryOrder().getId():0);
				form.setString("number",obj.getNumber());
				form.setCalendar("invoiceDate",obj.getInvoiceDate());
				form.setCalendar("invoiceDue",obj.getInvoiceDue());
				//form.setString("bankAccountId",obj.getBankAccount()!=null?obj.getBankAccount().getId():0);
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				form.setString("reference",obj.getReference());
				form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
				form.setString("taxAmount",obj.getTaxAmount());
				form.setString("discountProcent",obj.getDiscountProcent());
				form.setString("discount1",obj.getDiscount1());
				form.setString("discount2",obj.getDiscount2());
				form.setString("discount3",obj.getDiscount3());
				form.setString("taxSerialNumber", obj.getNpwpNumber());
				form.setCalendar("taxDate", obj.getNpwpDate());
				//form.setString("termId",obj.getTerm()!=null?obj.getTerm().getId():0);
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				List deliveryOrderLst = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class)
					.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.createCriteria("Invoice", "Invoice")
					.add(Restrictions.eq("Invoice.Id", new Long(obj.getId())))
					.list();
				request.setAttribute("deliveryOrderLst", deliveryOrderLst);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
				form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
				form.setString("deliveryOrderId",obj.getDeliveryOrder()!=null?obj.getDeliveryOrder().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set invoiceDetailLst = obj.getInvoiceDetails();
				request.setAttribute("invoiceDetailLst", invoiceDetailLst);
				Set invoicePrepaymentLst = obj.getInvoicePrepayments();
				request.setAttribute("invoicePrepaymentLst", invoicePrepaymentLst);
			}
			request.setAttribute("formatedInvoiceDetailAmount", obj!=null?obj.getFormatedInvoiceDetailAmount():"-");
			request.setAttribute("formatedInvoiceDiscountAmount", obj!=null?obj.getFormatedInvoiceDiscountAmount():"-");
			request.setAttribute("formatedInvoiceAfterDiscountAmount", obj!=null?obj.getFormatedInvoiceAfterDiscount():"-");
			request.setAttribute("formatedInvoiceDiscountAmount1", obj!=null?obj.getFormatedInvoiceDiscountAmount1():"-");
			request.setAttribute("formatedInvoiceAfterDiscountAmount1", obj!=null?obj.getFormatedInvoiceAfterDiscount1():"-");
			request.setAttribute("formatedInvoiceDiscountAmount2", obj!=null?obj.getFormatedInvoiceDiscountAmount2():"-");
			request.setAttribute("formatedInvoiceAfterDiscountAmount2", obj!=null?obj.getFormatedInvoiceAfterDiscount1Discount2():"-");
			request.setAttribute("formatedInvoiceDiscountAmount3", obj!=null?obj.getFormatedInvoiceDiscountAmount3():"-");
			request.setAttribute("formatedInvoiceAfterDiscountAmount3", obj!=null?obj.getFormatedInvoiceAfterDiscount1Discount2Discount3():"-");
			request.setAttribute("formatedInvoiceTaxAmount", obj!=null?obj.getFormatedInvoiceTaxAmount():"-");
			request.setAttribute("formatedInvoiceAfterDiscountAndTax", obj!=null?obj.getFormatedInvoiceAfterDiscountAndTax():"-");
			request.setAttribute("formatedInvoicePrepaymentAmount", obj!=null?obj.getFormatedInvoicePrepaymentAmount():"-");
			request.setAttribute("formatedInvoiceAfterDiscountAndTaxAndPrepayment", obj!=null?obj.getFormatedInvoiceAfterDiscountAndTaxAndPrepayment():"-");
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("currencyLst", currencyLst);
				List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Ap", Boolean.FALSE))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("taxLst", taxLst);
				List deliveryOrderLst = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class)
					.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.isNull("Invoice"))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
				request.setAttribute("deliveryOrderLst", deliveryOrderLst);
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
				InvoiceDAO.getInstance().closeSessionForReal();
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
		InvoiceForm form = (InvoiceForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = InvoiceDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("invoice");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Invoice obj = (Invoice)httpSession.getAttribute("invoice");
			if (form.getLong("invoiceId") == 0) {
				obj = (Invoice)InvoiceDAO.getInstance().getSession().createCriteria(Invoice.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (Invoice)httpSession.getAttribute("invoice");
					if (obj==null) obj = new Invoice();
					obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
					DeliveryOrder deliveryOrder = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
					SalesOrder salesOrder = deliveryOrder!=null?deliveryOrder.getSalesOrder():null;
					obj.setDeliveryOrder(deliveryOrder);
					obj.setProject(deliveryOrder.getProject());
					obj.setDepartment(deliveryOrder.getDepartment());
					obj.setDescription(form.getString("description"));
					obj.setNumber(form.getString("number"));
					//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("invoiceDate"));
					double e = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("invoiceDate")));
					obj.setExchangeRate(e);
					obj.setPosted(false);
					obj.setNpwpNumber(form.getString("taxSerialNumber"));
					obj.setNpwpDate(form.getCalendar("taxDate")!=null?form.getCalendar("taxDate").getTime():null);
					obj.setReference(form.getString("reference"));
					// cek bon kuning or NOT!
					if (deliveryOrder!=null && salesOrder!=null) {
						obj.setCurrency(salesOrder!=null?salesOrder.getCurrency():null);
						obj.setDiscountProcent(salesOrder!=null?salesOrder.getDiscountProcent():0);
						obj.setTax(salesOrder!=null?salesOrder.getTax():null);
						obj.setTaxAmount(salesOrder!=null?salesOrder.getTaxAmount():0);
					} else {
				    obj.setCurrency(deliveryOrder.getCurrency());
						obj.setDiscountProcent(0);
						obj.setTax(null);
						obj.setTaxAmount(0);
						obj.setDiscount1(deliveryOrder.getDiscount1());
						obj.setDiscount2(deliveryOrder.getDiscount2());
						obj.setDiscount3(deliveryOrder.getDiscount3());
					}
					if (obj.getDeliveryOrder()!=null && obj.getDeliveryOrder().getSalesOrder()!=null) {
					    Calendar calendar = form.getCalendar("invoiceDate");
					    calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + obj.getDeliveryOrder().getSalesOrder().getCreditLimit());
					    form.setCalendar("invoiceDue", calendar);
					}
					obj.setOrganization(users.getOrganization());
					obj.setInvoiceDate(form.getCalendar("invoiceDate")!=null?form.getCalendar("invoiceDate").getTime():null);
					obj.setInvoiceDue(form.getCalendar("invoiceDue")!=null?form.getCalendar("invoiceDue").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					obj.setCustomerPaymentStatus(CommonConstants.OPEN);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
					obj.setCustomerAlias(customersAlias);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					// create new deliveryOrder
					if (deliveryOrder!=null) {
						Set set = new LinkedHashSet();
						Iterator iterator = deliveryOrder.getDeliveryOrderDetails().iterator();
						while (iterator.hasNext()) {
							DeliveryOrderDetail deliveryOrderDetail = (DeliveryOrderDetail)iterator.next();
							InvoiceDetail invoiceDetail = new InvoiceDetail();
							invoiceDetail.setItemUnit(deliveryOrderDetail.getItemUnit());
							InvoiceDetailPK invoiceDetailPK = new InvoiceDetailPK();
							invoiceDetailPK.setItem(deliveryOrderDetail.getId().getItem());
							invoiceDetailPK.setInvoice(obj);
							invoiceDetail.setId(invoiceDetailPK);
							invoiceDetail.setCurrency(deliveryOrderDetail.getCurrency());
							invoiceDetail.setExchangeRate(deliveryOrderDetail.getExchangeRate());
							invoiceDetail.setPrice(deliveryOrderDetail.getPrice());
							invoiceDetail.setQuantity(deliveryOrderDetail.getQuantity());
							invoiceDetail.setDiscountProcent(deliveryOrderDetail.getDiscountProcent());
							invoiceDetail.setCostPriceExchangeRate(deliveryOrderDetail.getCostPriceExchangeRate());
							invoiceDetail.setCostPrice(deliveryOrderDetail.getCostPrice());
							invoiceDetail.setCostPriceCurrency(deliveryOrderDetail.getCostPriceCurrency());
							invoiceDetail.setDiscountAmount(deliveryOrderDetail.getDiscountAmount());
							invoiceDetail.setDescription(deliveryOrderDetail.getDescription());
							invoiceDetail.setTax(deliveryOrderDetail.getTax());
							invoiceDetail.setTaxAmount(deliveryOrderDetail.getTaxAmount());
							invoiceDetail.setUnitConversion(deliveryOrderDetail.getUnitConversion());
							set.add(invoiceDetail);
						}
						obj.setInvoiceDetails(set);
					}
					// get prepayment
					if (salesOrder!=null) {
						List prepaymentToCustomerLst = CustomerPrepaymentDAO.getInstance().getSession().createCriteria(CustomerPrepayment.class)
							.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
							.add(Restrictions.eq("SalesOrder.Id", new Long(salesOrder.getId())))
							.add(Restrictions.ne("InvoiceStatus", new String(CommonConstants.CLOSE))).list();
						Iterator iterator = prepaymentToCustomerLst.iterator();
						Set set = new LinkedHashSet();
						if (prepaymentToCustomerLst.size()>0) {
							while (iterator.hasNext()) {
								CustomerPrepayment customerPrepayment = (CustomerPrepayment)iterator.next();
								InvoicePrepayment invoicePrepayment = new InvoicePrepayment();
								// cek amount of bill
								if (customerPrepayment.getAmount()-customerPrepayment.getInvoicePaymentAmount()>0 && obj.getDifferenceAmount()>0) {
									InvoicePrepaymentPK invoicePrepaymentPK = new InvoicePrepaymentPK();
									invoicePrepaymentPK.setCustomerPrepayment(customerPrepayment);
									invoicePrepaymentPK.setInvoice(obj);
									invoicePrepayment.setId(invoicePrepaymentPK);
									if (customerPrepayment.getAmount() <= obj.getDifferenceAmount()) {
										invoicePrepayment.setAmount(customerPrepayment.getAmount() - customerPrepayment.getInvoicePaymentAmount());
									} else {
										invoicePrepayment.setAmount(obj.getDifferenceAmount());
									}
									set.add(invoicePrepayment);
								}
								obj.setInvoicePrepayments(set);
							}
						} else {
							obj.setInvoicePrepayments(set);
						}
					}
					// create journal
					Journal journal = new Journal();
					journal.setInvoice(obj);
					journal.setCurrency(obj.getCurrency());
					journal.setProject(deliveryOrder.getProject());
					journal.setExchangeRate(e);
					journal.setJournalDate(obj.getInvoiceDate());
					journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
					journal.setOrganization(users.getOrganization());
					journal.setPosted(false);
					journal.setReference(form.getString("reference"));
					journal.setCustomer(customers);
					journal.setCreateBy(users);
					journal.setCreateOn(form.getTimestamp("createOn"));
					// journal detail
					Set set = journal.getJournalDetails();
					if (set==null) set = new LinkedHashSet();
					// credit
					JournalDetail journalDetail = new JournalDetail();
					JournalDetailPK journalDetailPK = new JournalDetailPK();
					journalDetailPK.setChartOfAccount(organizationSetup.getSalesAccount());
					journalDetailPK.setJournal(journal);
					journalDetail.setId(journalDetailPK);
					journalDetail.setAmount(organizationSetup.getSalesAccount().isDebit()==false?(obj.getInvoiceAfterDiscountAndTaxAndPrepayment()-obj.getInvoiceTaxAmount()):-(obj.getInvoiceAfterDiscountAndTaxAndPrepayment()-obj.getInvoiceTaxAmount()));
					journalDetail.setDepartment(obj.getDepartment());
					set.add(journalDetail);
					// credit tax
					if (obj.getTax()!=null && obj.getTaxAmount()>0 && obj.getTax().getChartOfAccount()!=null) {
						JournalDetail journalDetail2 = new JournalDetail();
						JournalDetailPK journalDetailPK2 = new JournalDetailPK();
						journalDetailPK2.setChartOfAccount(obj.getTax().getChartOfAccount());
						journalDetailPK2.setJournal(journal);
						journalDetail2.setId(journalDetailPK2);
						journalDetail2.setDepartment(obj.getDepartment());
						journalDetail2.setAmount(obj.getTax().getChartOfAccount().isDebit()==false?obj.getInvoiceTaxAmount():-obj.getInvoiceTaxAmount());
						set.add(journalDetail2);
					}
					// debit
					JournalDetail journalDetail3 = new JournalDetail();
					JournalDetailPK journalDetailPK3 = new JournalDetailPK();
					journalDetailPK3.setJournal(journal);
					if (customers.getChartOfAccount()==null) {
						journalDetailPK3.setChartOfAccount(organizationSetup.getArAccount());
						journalDetail3.setAmount(organizationSetup.getArAccount().isDebit()==true?obj.getInvoiceAfterDiscountAndTaxAndPrepayment():-obj.getInvoiceAfterDiscountAndTaxAndPrepayment());
					} else {
						journalDetailPK3.setChartOfAccount(customers.getChartOfAccount());
						journalDetail3.setAmount(customers.getChartOfAccount().isDebit()==true?obj.getInvoiceAfterDiscountAndTaxAndPrepayment():-obj.getInvoiceAfterDiscountAndTaxAndPrepayment());
					}
					journalDetail3.setDepartment(obj.getDepartment());
					journalDetail3.setId(journalDetailPK3);
					set.add(journalDetail3);
					journal.setJournalDetails(set);
					obj.setJournal(journal);
				} else {
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Ap", Boolean.FALSE))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
					List deliveryOrderLst = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class)
						.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.isNull("Invoice"))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
					request.setAttribute("deliveryOrderLst", deliveryOrderLst);
					List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("locationLst", locationLst);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = InvoiceDAO.getInstance().load(form.getLong("invoiceId"));
				DeliveryOrder deliveryOrder = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
				SalesOrder salesOrder = deliveryOrder!=null?deliveryOrder.getSalesOrder():null;
				obj.setDeliveryOrder(deliveryOrder);
				obj.setProject(deliveryOrder.getProject());
				obj.setDepartment(deliveryOrder.getDepartment());
				obj.setDescription(form.getString("description"));
				obj.setNumber(form.getString("number"));
				//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("invoiceDate"));
				double e = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("invoiceDate")));
				obj.setExchangeRate(e);
				obj.setNpwpNumber(form.getString("taxSerialNumber"));
				obj.setNpwpDate(form.getCalendar("taxDate")!=null?form.getCalendar("taxDate").getTime():null);
				//obj.setPosted(false);
				obj.setReference(form.getString("reference"));
				// cek bon kuning or NOT!
				if (deliveryOrder!=null && salesOrder!=null) {
					obj.setCurrency(salesOrder!=null?salesOrder.getCurrency():null);
					obj.setDiscountProcent(salesOrder!=null?salesOrder.getDiscountProcent():0);
					obj.setTax(salesOrder!=null?salesOrder.getTax():null);
					obj.setTaxAmount(salesOrder!=null?salesOrder.getTaxAmount():0);
				} else if (deliveryOrder!=null && salesOrder==null) {
			    obj.setCurrency(deliveryOrder.getCurrency());
					obj.setDiscountProcent(0);
					obj.setTax(null);
					obj.setTaxAmount(0);
					obj.setDiscount1(deliveryOrder.getDiscount1());
					obj.setDiscount2(deliveryOrder.getDiscount2());
					obj.setDiscount3(deliveryOrder.getDiscount3());
				}
				obj.setOrganization(users.getOrganization());
				obj.setInvoiceDate(form.getCalendar("invoiceDate")!=null?form.getCalendar("invoiceDate").getTime():null);
				obj.setInvoiceDue(form.getCalendar("invoiceDue")!=null?form.getCalendar("invoiceDue").getTime():null);
				//obj.setStatus(CommonConstants.OPEN);
				if (obj.getDifferenceAmount()==0) {
					obj.setCustomerPaymentStatus(CommonConstants.CLOSE);
				} else {
					if (obj.getCustomerPaymentAmount()==0) {
						obj.setCustomerPaymentStatus(CommonConstants.OPEN);
					} else obj.setCustomerPaymentStatus(CommonConstants.PARTIAL);
				}
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
				// create new deliveryOrder
				/*
				if (deliveryOrder!=null) {
					Set set = new LinkedHashSet();
					Iterator iterator = deliveryOrder.getDeliveryOrderDetails().iterator();
					while (iterator.hasNext()) {
						DeliveryOrderDetail deliveryOrderDetail = (DeliveryOrderDetail)iterator.next();
						InvoiceDetail invoiceDetail = new InvoiceDetail();
						invoiceDetail.setItemUnit(deliveryOrderDetail.getItemUnit());
						InvoiceDetailPK invoiceDetailPK = new InvoiceDetailPK();
						invoiceDetailPK.setItem(deliveryOrderDetail.getId().getItem());
						invoiceDetailPK.setInvoice(obj);
						invoiceDetail.setId(invoiceDetailPK);
						invoiceDetail.setCurrency(deliveryOrderDetail.getCurrency());
						invoiceDetail.setExchangeRate(deliveryOrderDetail.getExchangeRate());
						invoiceDetail.setPrice(deliveryOrderDetail.getPrice());
						invoiceDetail.setQuantity(deliveryOrderDetail.getQuantity());
						set.add(invoiceDetail);
					}
					obj.setInvoiceDetails(set);
				}*/
				// get prepayment
				/*
				if (purchaseOrder!=null) {
					Set customerPrepaymentLst = obj.getPaymentToCustomers();
					Iterator iterator = customerPrepaymentLst.iterator();
					Set set = new LinkedHashSet();
					if (customerPrepaymentLst.size()>0) {
						while (iterator.hasNext()) {
							CustomerPrepayment customerPrepayment = (CustomerPrepayment)iterator.next();
							InvoicePrepayment invoicePrepayment = new InvoicePrepayment();
							// cek amount of bill
							if (customerPrepayment.getAmount()-customerPrepayment.getInvoicePaymentAmount()>0 && obj.getAmount()>0) {
								InvoicePrepaymentPK invoicePrepaymentPK = new InvoicePrepaymentPK();
								invoicePrepaymentPK.setCustomerPrepayment(customerPrepayment);
								invoicePrepaymentPK.setInvoice(obj);
								invoicePrepayment.setId(invoicePrepaymentPK);
								if (customerPrepayment.getAmount() <= obj.getAmount()) {
									invoicePrepayment.setAmount(customerPrepayment.getAmount() - customerPrepayment.getInvoicePaymentAmount());
								} else {
									invoicePrepayment.setAmount(obj.getAmount());
								}
								set.add(invoicePrepayment);
							}
							obj.setInvoicePrepayments(set);
						}
					} else {
						obj.setInvoicePrepayments(set);
					}
				}*/
				// create journal
				//Journal journal = obj.getJournal();
				Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
					.add(Restrictions.eq("Invoice.Id", new Long(obj.getId()))).uniqueResult();
				journal.setInvoice(obj);
				journal.setCurrency(obj.getCurrency());
				journal.setProject(deliveryOrder.getProject());
				journal.setExchangeRate(e);
				journal.setJournalDate(obj.getInvoiceDate());
				//journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
				journal.setOrganization(users.getOrganization());
				//journal.setPosted(false);
				journal.setReference(form.getString("reference"));
				journal.setCustomer(customers);
				journal.setCreateBy(users);
				journal.setCreateOn(form.getTimestamp("createOn"));
				journal.setCreateBy(createBy);
				journal.setCreateOn(form.getTimestamp("createOn"));
				journal.setChangeBy(users);
				journal.setChangeOn(form.getTimestamp("changeOn"));
				journal.getJournalDetails().removeAll(journal.getJournalDetails());
				// journal detail
				Set set = journal.getJournalDetails();
				// credit
				JournalDetail journalDetail = new JournalDetail();
				JournalDetailPK journalDetailPK = new JournalDetailPK();
				journalDetailPK.setChartOfAccount(organizationSetup.getSalesAccount());
				journalDetailPK.setJournal(journal);
				journalDetail.setId(journalDetailPK);
				journalDetail.setDepartment(obj.getDepartment());
				journalDetail.setAmount(organizationSetup.getSalesAccount().isDebit()==false?(obj.getInvoiceAfterDiscountAndTaxAndPrepayment()-obj.getInvoiceTaxAmount()):-(obj.getInvoiceAfterDiscountAndTaxAndPrepayment()-obj.getInvoiceTaxAmount()));
				set.add(journalDetail);
				// credit tax
				if (obj.getTax()!=null && obj.getTaxAmount()>0 && obj.getTax().getChartOfAccount()!=null) {
					JournalDetail journalDetail2 = new JournalDetail();
					JournalDetailPK journalDetailPK2 = new JournalDetailPK();
					journalDetailPK2.setChartOfAccount(obj.getTax().getChartOfAccount());
					journalDetailPK2.setJournal(journal);
					journalDetail2.setId(journalDetailPK2);
					journalDetail2.setDepartment(obj.getDepartment());
					journalDetail2.setAmount(obj.getTax().getChartOfAccount().isDebit()==false?obj.getInvoiceTaxAmount():-obj.getInvoiceTaxAmount());
					set.add(journalDetail2);
				}
				// debit
				JournalDetail journalDetail3 = new JournalDetail();
				JournalDetailPK journalDetailPK3 = new JournalDetailPK();
				journalDetailPK3.setJournal(journal);
				if (customers.getChartOfAccount()==null) {
					journalDetailPK3.setChartOfAccount(organizationSetup.getArAccount());
					journalDetail3.setAmount(organizationSetup.getArAccount().isDebit()==true?obj.getInvoiceAfterDiscountAndTaxAndPrepayment():-obj.getInvoiceAfterDiscountAndTaxAndPrepayment());
				} else {
					journalDetailPK3.setChartOfAccount(customers.getChartOfAccount());
					journalDetail3.setAmount(customers.getChartOfAccount().isDebit()==true?obj.getInvoiceAfterDiscountAndTaxAndPrepayment():-obj.getInvoiceAfterDiscountAndTaxAndPrepayment());
				}
				journalDetail3.setDepartment(obj.getDepartment());
				journalDetail3.setId(journalDetailPK3);
				set.add(journalDetail3);
				journal.setJournalDetails(set);
				obj.setJournal(journal);
			}
			// save to session
			httpSession.setAttribute("invoice", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					//log.info("A1");
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateInvoiceNumber(session);
					RunningNumberDAO.getInstance().updateJournalNumber(session);
					RunningNumberDAO.getInstance().updateStandartNpwpTaxNumber(session);
					InvoiceDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					transaction = session.beginTransaction();
					//log.info("A2");
					InvoiceDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("invoice");
				// finish
				ActionForward forward = mapping.findForward("update_status");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?invoiceId="+form.getLong("invoiceId")+"&deliveryOrderId="+form.getLong("deliveryOrderId"));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
				if (transaction!=null) transaction.rollback();
				try {
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Ap", Boolean.FALSE))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
					List deliveryOrderLst = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class)
						.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.isNull("Invoice"))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
					request.setAttribute("deliveryOrderLst", deliveryOrderLst);
					List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("locationLst", locationLst);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				InvoiceDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?invoiceId="+form.getLong("invoiceId"));
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
		InvoiceForm form = (InvoiceForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			Invoice invoice = InvoiceDAO.getInstance().get(form.getLong("invoiceId"));
			request.setAttribute("invoice", invoice);
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				InvoiceDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("detail");
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
	private ActionForward performPrinted(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		InvoiceForm form = (InvoiceForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			Invoice invoice = InvoiceDAO.getInstance().get(form.getLong("invoiceId"));
			
			// pdf
	    // write to pdf document
			Document document = new Document(PageSize.A4,36,36,36,36);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			  
			// footer page
			HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
			footer.setBorder(Rectangle.NO_BORDER);
			document.setFooter(footer);
			document.open();
			
			// table upper
			Table table1 = new Table(2);
			table1.setWidth(100);
			table1.setCellsFitPage(true);
			float[] a = {60,40};
			table1.setWidths(a);
			table1.setBorder(Rectangle.NO_BORDER);
			table1.setCellsFitPage(true);
			table1.setBorderWidth(1);
			table1.setPadding(0);
			table1.setSpacing(0);
			
			Cell cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tanggal : "+invoice.getFormatedInvoiceDate(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Kepada : "+invoice.getCustomer().getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tlp. "+users.getOrganization().getTelephone()+" - Fax. "+users.getOrganization().getFax(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Telp. : "+invoice.getCustomer().getTelephone(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Fax. : "+invoice.getCustomer().getFax(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Attn. : "+invoice.getCustomer().getContactPerson(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			document.add(table1);
			
			Table table2 = new Table(9);
			table2.setWidth(100);
			table2.setCellsFitPage(true);
			float[] b = {5,15,15,15,10,10,10,10,10};
			table2.setWidths(b);
			table2.setBorder(Rectangle.NO_BORDER);
			table2.setCellsFitPage(true);
			table2.setBorderWidth(1);
			table2.setPadding(1);
			table2.setSpacing(0);
			
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(9);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(9);
			table2.addCell(cell);
			cell = new Cell(new Phrase("FAKTUR PENJUALAN", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(9);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(9);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(9);
			table2.addCell(cell);
			cell = new Cell(new Phrase("NO. : "+invoice.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(5);
			table2.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setColspan(4);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("MERK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("ARTICLE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("WARNA",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("KETERANGAN",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("SIZE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("SATUAN",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			table2.endHeaders();
			
			int j = 0;
			Iterator iterator = invoice.getInvoiceDetails().iterator();
			while (iterator.hasNext()) {
			    InvoiceDetail detail = (InvoiceDetail)iterator.next();
			    cell = new Cell(new Phrase(++j+".", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getId().getItem().getCode(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getQuantity()+" "+detail.getItemUnit().getSymbol(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getFormatedPrice(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getFormatedPriceQuantity(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
			}
			
			cell = new Cell(new Phrase("TOTAL", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(8);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(invoice.getFormatedInvoiceAfterDiscountAndTaxAndPrepayment(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			
			document.add(table2);
			document.close();
			//send pdf to browser
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream out = response.getOutputStream();
			baos.writeTo(out);
			out.flush();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				InvoiceDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return null;
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
	private ActionForward performFakturPajak(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		InvoiceForm form = (InvoiceForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			Invoice invoice = InvoiceDAO.getInstance().get(form.getLong("invoiceId"));
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			
			//write to pdf document
			Document document = new Document(PageSize.A4,36,36,36,36);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			  
			// footer page
			HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
			footer.setBorder(Rectangle.NO_BORDER);
			document.setFooter(footer);
			document.open();
								
			Table titleTable = new Table(1);
			titleTable.setWidth(100);
			titleTable.setBorderWidth(0);
			titleTable.setBorder(Rectangle.NO_BORDER);
			titleTable.setPadding(1);
			titleTable.setSpacing(0);	
			
			Cell cell = new Cell(new Phrase("Lembar ke-1 : Untuk pembeli BKP/penerima JKP",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			titleTable.addCell(cell);
			
			cell = new Cell(new Phrase("sebagai bukti Pajak Masukan",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			titleTable.addCell(cell);
			
			cell = new Cell(new Phrase("Lembar ke-2 : Untuk PKP yang menerbitkan Faktur",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			titleTable.addCell(cell);
			
			cell = new Cell(new Phrase("Pajak Standar sebagai bukti Pajak Keluaran",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			titleTable.addCell(cell);
			
			cell = new Cell(new Phrase("Lembar ke-3 : Untuk Extra Copy",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			titleTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			titleTable.addCell(cell);
			
			cell = new Cell(new Phrase("FAKTUR PAJAK STANDAR",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.NO_BORDER);
			titleTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.NO_BORDER);
			titleTable.addCell(cell);
			
			document.add(titleTable);
			
			Table firstTable = new Table(7);
			firstTable.setWidth(100);
			firstTable.setBorderWidth(1);
			//firstTable.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
			firstTable.setPadding(1);
			firstTable.setSpacing(0);
			int b[] = {5,10,20,20,5,20,20};
			firstTable.setWidths(b);

			cell = new Cell(new Phrase("Kode dan Nomor Seri Faktur Pajak :",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.BOTTOM);
			cell.setColspan(3);
			firstTable.addCell(cell);
			
			
			cell = new Cell(new Phrase(invoice.getNpwpNumber(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(4);
			cell.setBorder(Rectangle.BOTTOM);
			firstTable.addCell(cell);
			firstTable.endHeaders();
			
			cell = new Cell(new Phrase("Pengusaha Kena Pajak :",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(7);
			cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("N a m a",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.LEFT);
			cell.setColspan(3);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(":",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(users.getOrganization().getName(),FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			cell.setBorder(Rectangle.RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("A l a m a t",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			cell.setBorder(Rectangle.LEFT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(":",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(users.getOrganization().getAddress()!=null?users.getOrganization().getAddress():"-",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			cell.setBorder(Rectangle.RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("NPWP",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			cell.setBorder(Rectangle.LEFT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(":",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(users.getOrganization().getNpwp()!=null?users.getOrganization().getNpwp():"-",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			cell.setBorder(Rectangle.RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Tanggal Pengukuhan PKP",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(":",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.BOTTOM);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(users.getOrganization().getFormatedNpwpDate(),FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Pembeli Barang Kena Pajak / Penerima Jasa Kena Pajak",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(7);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("N a m a",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(":",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(invoice.getCustomer()!=null?invoice.getCustomer().getCompany():"-",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("A l a m a t",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(":",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(invoice.getCustomer()!=null?invoice.getCustomer().getAddress():"-"+" "+invoice.getCustomer()!=null?invoice.getCustomer().getCity():"-",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("NPWP",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(":",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(invoice.getCustomer()!=null?invoice.getCustomer().getNpwp():"-",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("No. Urut",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setRowspan(3);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Nama Barang Kena Pajak/Jasa Kena Pajak",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(4);
			cell.setRowspan(3);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Harga Jual/Penggantian/Uang Muka/Termijn",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(2);
			cell.setRowspan(2);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Valas",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("("+organizationSetup.getDefaultCurrency().getSymbol()+")",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			firstTable.addCell(cell);
			
			java.util.Iterator iterator = invoice.getInvoiceDetails().iterator();
			int i = 0;
			while(iterator.hasNext()){
				InvoiceDetail invoiceItem = (InvoiceDetail)iterator.next();
				//No
				cell = new Cell(new Phrase(String.valueOf(++i), FontFactory.getFont(FontFactory.HELVETICA, 8)));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
				firstTable.addCell(cell);
				//item
				cell = new Cell(new Phrase(invoiceItem.getId().getItem()!=null?invoiceItem.getId().getItem().getName():"-"+" : "+invoiceItem.getQuantity()+" "+invoiceItem.getItemUnit()!=null?invoiceItem.getItemUnit().getName():"-"+" x "+" "+invoiceItem.getFormatedPrice(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
				cell.setColspan(4);
				firstTable.addCell(cell);
				//valas
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8)));
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
				firstTable.addCell(cell);
				//jumlah qty x price
				cell = new Cell(new Phrase(invoiceItem.getFormatedPriceQuantity(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
				firstTable.addCell(cell);
			}
			int s = 60;
			s = s - i;
			for (int g=0; g<s; g++) {
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8)));
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
				firstTable.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8)));
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
				cell.setColspan(4);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8)));
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
				firstTable.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8)));
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
				firstTable.addCell(cell);
				
			}

			cell = new Cell(new Phrase("Jumlah Harga Jual/Penggantian/Uang Muka/Termijn *)",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(5);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(invoice.getFormatedInvoiceDetailAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Dikurangi Potongan Harga Reg Disc :"+" "+invoice.getDiscountProcent()+"%",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(5);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(invoice.getFormatedInvoiceDiscountAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Dikurangi Uang Muka Yang Telah Diterima",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(5);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Dasar Pengenaan Pajak",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(5);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(invoice.getFormatedInvoiceAfterDiscount(),FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("PPN = 10% x Dasar Pengenaan Pajak",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(5);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(invoice.getFormatedInvoiceTaxAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Pajak Penjualan Atas Barang Mewah :",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(6);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("TARIF",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("DPP",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("PPn BM",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Jakarta,........................20..........",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("...%",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Rp...................",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Rp...................",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("...%",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Rp...................",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Rp...................",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("...%",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Rp...................",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Rp...................",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("...%",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Rp...................",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Rp...................",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT );
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("JUMLAH",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(2);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Rp...................",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("(_______________________)",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(4);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Nama / Jabatan",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			cell.setBorder(Rectangle.NO_BORDER);
			firstTable.addCell(cell);
			
			document.add(firstTable);
			
			Table endTable = new Table(1);
			endTable.setWidth(100);
			endTable.setBorderWidth(0);
			endTable.setBorder(Rectangle.NO_BORDER);
			endTable.setPadding(0);
			endTable.setSpacing(0);
			
			cell = new Cell(new Phrase("*) Coret yang tidak perlu",FontFactory.getFont(FontFactory.HELVETICA, 8)));
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			endTable.addCell(cell);
			
			document.add(endTable);
			
			document.close();	
				
			
			//send pdf to browser
		  response.setContentType("application/pdf");
		  response.setContentLength(baos.size());
		  ServletOutputStream out = response.getOutputStream();
		  baos.writeTo(out);
		  out.flush();
			
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				InvoiceDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return null;
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
		InvoiceForm form = (InvoiceForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Session session = InvoiceDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			InvoiceDAO.getInstance().delete(form.getLong("invoiceId"), session);
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			ex.printStackTrace();
		}finally {
			try {
				InvoiceDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
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
		InvoiceForm form = (InvoiceForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			Session session = DeliveryOrderDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			// update deliveryOrder status
			DeliveryOrder deliveryOrder = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
			deliveryOrder.setInvoiceStatus(CommonConstants.CLOSE);
			DeliveryOrderDAO.getInstance().update(deliveryOrder, session);
			// update prepayment to customer
			List list = CustomerPrepaymentDAO.getInstance().getSession().createCriteria(CustomerPrepayment.class)
				.add(Restrictions.eq("SalesOrder.Id", new Long(deliveryOrder.getSalesOrder().getId()))).list();
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				CustomerPrepayment customerPrepayment = (CustomerPrepayment)iterator.next();
				if (customerPrepayment.getInvoicePaymentAmount()>0 && customerPrepayment.getAmount()-customerPrepayment.getInvoicePaymentAmount()>0) {
					customerPrepayment.setInvoiceStatus(CommonConstants.PARTIAL);
				} else if (customerPrepayment.getAmount()==customerPrepayment.getInvoicePaymentAmount()) {
					customerPrepayment.setInvoiceStatus(CommonConstants.CLOSE);
				} else if (customerPrepayment.getInvoicePaymentAmount()==0) {
					customerPrepayment.setInvoiceStatus(CommonConstants.OPEN);
				}
				CustomerPrepaymentDAO.getInstance().update(customerPrepayment, session);
			}
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				InvoiceDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
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