//---------------------------------------------------------
// Application: Garage
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright © 2005 MPE
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
import com.mpe.financial.model.DeliveryOrderDetail;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.DeliveryOrder;
import com.mpe.financial.model.Tax;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.DeliveryOrderDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.TaxDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.struts.form.DeliveryOrderForm;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class RekapBonKuningAction extends Action {
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
		DeliveryOrderForm purchaseOrderForm = (DeliveryOrderForm) form;
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
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
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
			Criteria criteria = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromBonKuningDate")!=null)criteria.add(Restrictions.ge("DeliveryDate", new Date(form.getCalendar("fromBonKuningDate").getTime().getTime())));
			if (form.getCalendar("toBonKuningDate")!=null)criteria.add(Restrictions.le("DeliveryDate", new Date(form.getCalendar("toBonKuningDate").getTime().getTime())));
			criteria.add(Restrictions.eq("Rekap", Boolean.TRUE));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromBonKuningDate")!=null)criteria.add(Restrictions.ge("DeliveryDate", new Date(form.getCalendar("fromBonKuningDate").getTime().getTime())));
			if (form.getCalendar("toBonKuningDate")!=null)criteria.add(Restrictions.le("DeliveryDate", new Date(form.getCalendar("toBonKuningDate").getTime().getTime())));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.add(Restrictions.eq("Rekap", Boolean.TRUE));
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("REKAP",list);
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
				DeliveryOrderDAO.getInstance().closeSessionForReal();
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
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			//List itemSizeLst = new LinkedList();
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			DeliveryOrder obj = (DeliveryOrder)httpSession.getAttribute("deliveryOrder");
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
			if (form.getLong("deliveryOrderId") == 0) {
				form.setString("deliveryOrderId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getDeliveryOrderNumber());
				if (form.getCalendar("deliveryDate")==null) form.setCurentCalendar("deliveryDate");
				/*
				if (obj.getDeliveryOrder()!=null && obj.getDeliveryOrder().getSalesOrder()!=null) {
				    Calendar calendar = form.getCalendar("invoiceDate");
				    calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + obj.getDeliveryOrder().getSalesOrder().getCreditLimit());
				    form.setCalendar("invoiceDue", calendar);
				}*/
				if (form.getLong("currencyId")==0) form.setString("currencyId", organizationSetup.getDefaultCurrency().getId()); 
				form.setString("taxSerialNumber", organizationSetup.getNpwpSn()+"."+RunningNumberDAO.getInstance().getStandartNpwpTaxNumber());
				//form.setCalendar("taxDate", organizationSetup.getNpwpDate());
				if (form.getCalendar("taxDate")==null) form.setCurentCalendar("taxDate");
				if (obj!=null) {
					form.setString("deliveryOrderId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setString("number",obj.getNumber());
					form.setCalendar("deliveryDate",obj.getDeliveryDate());
					form.setCalendar("fromBonKuningDate",obj.getFromBonKuningDate());
					form.setCalendar("toBonKuningDate",obj.getToBonKuningDate());
					form.setString("isDetail",obj.isDetail()==true?"Y":"N");
					//form.setString("bankAccountId",obj.getBankAccount()!=null?obj.getBankAccount().getId():0);
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					//form.setString("reference",obj.getReference());
					form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
					form.setString("taxAmount",obj.getTaxAmount());
					//form.setString("taxProcent",obj.getTaxProcent());
					//form.setString("discountProcent",obj.getDiscount1());
					form.setString("discount1",obj.getDiscount1());
					//form.setString("termId",obj.getTerm()!=null?obj.getTerm().getId():0);
					form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
					form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
					Set deliveryOrderLst = obj.getDeliveryOrders();
					request.setAttribute("deliveryOrderLst", deliveryOrderLst);
				} else {
				    Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
						List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
							.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
						request.setAttribute("customerAliasLst", customerAliasLst);
				}
/*				List deliveryOrderLst = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class)
					.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("InvoiceStatus", new String(CommonConstants.CLOSE)))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.add(Restrictions.eq("BonKuning", Boolean.TRUE))
					.list();*/
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
					httpSession.setAttribute("invoice",obj);
				}
				form.setString("deliveryOrderId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("number",obj.getNumber());
				form.setCalendar("deliveryDate",obj.getDeliveryDate());
				//form.setCalendar("invoiceDue",obj.getInvoiceDue());
				form.setCalendar("fromBonKuningDate",obj.getFromBonKuningDate());
				form.setCalendar("toBonKuningDate",obj.getToBonKuningDate());
				form.setString("isDetail",obj.isDetail()==true?"Y":"N");
				//form.setString("bankAccountId",obj.getBankAccount()!=null?obj.getBankAccount().getId():0);
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				//form.setString("reference",obj.getReference());
				form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
				form.setString("taxAmount",obj.getTaxAmount());
				//form.setString("discountProcent",obj.getDiscountProcent());
				form.setString("discount1",obj.getDiscount1());
				//form.setString("taxProcent",obj.getTaxProcent());
				//form.setString("termId",obj.getTerm()!=null?obj.getTerm().getId():0);
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
				form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set deliveryOrderLst = obj.getDeliveryOrders();
				request.setAttribute("deliveryOrderLst", deliveryOrderLst);
			}
			request.setAttribute("deliveryOrderDetailAmount", obj!=null?obj.getFormatedDeliveryOrderDetailAmount():"-");
			request.setAttribute("amountTax", obj!=null?obj.getFormatedAmountTax():"-");
			request.setAttribute("amountDiscount", obj!=null?obj.getFormatedAmountDiscount():"-");
			request.setAttribute("amountAfterDiscount", obj!=null?obj.getFormatedAmountAfterDiscount():"-");
			request.setAttribute("amountAfterTaxAndDiscount", obj!=null?obj.getFormatedAmountAfterTaxAndDiscount():"-");
		}catch(Exception ex) {
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
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
			}catch(Exception exx) {
			}
			ex.printStackTrace();
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				DeliveryOrderDAO.getInstance().closeSessionForReal();
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
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = DeliveryOrderDAO.getInstance().getSession();
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
			DeliveryOrder obj = (DeliveryOrder)httpSession.getAttribute("invoice");
			if (form.getLong("deliveryOrderId") == 0) {
				obj = (DeliveryOrder)DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (DeliveryOrder)httpSession.getAttribute("invoice");
					if (obj==null) obj = new DeliveryOrder();
					obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
					obj.setDescription(form.getString("description"));
					obj.setNumber(form.getString("number"));
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("invoiceDate"));
					obj.setExchangeRate(e);
					obj.setPosted(false);
					//obj.setNpwpNumber(form.getString("taxSerialNumber"));
					//obj.setNpwpDate(form.getCalendar("taxDate")!=null?form.getCalendar("taxDate").getTime():null);
					//obj.setReference(form.getString("reference"));
					Tax tax = TaxDAO.getInstance().get(form.getLong("taxId"));
					obj.setTax(tax);
					obj.setTaxAmount(tax!=null?tax.getQuantity():form.getDouble("taxAmount"));
					// cek bon kuning or NOT!
					obj.setOrganization(users.getOrganization());
					//obj.setInvoiceDate(form.getCalendar("invoiceDate")!=null?form.getCalendar("invoiceDate").getTime():null);
					//obj.setInvoiceDue(form.getCalendar("invoiceDue")!=null?form.getCalendar("invoiceDue").getTime():null);
					obj.setDeliveryDate(form.getCalendar("deliveryDate")!=null?form.getCalendar("deliveryDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					obj.setInvoiceStatus(CommonConstants.OPEN);
					//obj.setDetail(form.getString("isDetail").length()>0?true:false);
					obj.setRekap(true);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
					obj.setCustomerAlias(customersAlias);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					obj.setPosted(false);
					obj.setBonKuning(false);
					obj.setStore(false);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					obj.setFromBonKuningDate(form.getCalendar("fromBonKuningDate")!=null?form.getCalendar("fromBonKuningDate").getTime():null);
					obj.setToBonKuningDate(form.getCalendar("toBonKuningDate")!=null?form.getCalendar("toBonKuningDate").getTime():null);
					obj.setDetail(form.getString("isDetail").length()>0?true:false);
					// create new deliveryOrder
					Set set2 = new LinkedHashSet();
					if (form.getCalendar("fromBonKuningDate")!=null && form.getCalendar("toBonKuningDate")!=null) {
						List deliveryOrderLst = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class)
							.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("InvoiceStatus", new String(CommonConstants.CLOSE)))
							.add(Restrictions.ge("DeliveryDate", new Date(form.getCalendar("fromBonKuningDate").getTime().getTime())))
							.add(Restrictions.le("DeliveryDate", new Date(form.getCalendar("toBonKuningDate").getTime().getTime())))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
							.add(Restrictions.eq("Store", Boolean.FALSE))
							.add(Restrictions.eq("BonKuning", Boolean.TRUE))
							.list();
						Iterator iterator = deliveryOrderLst.iterator();
						while (iterator.hasNext()) {
							DeliveryOrder deliveryOrder = (DeliveryOrder)iterator.next();
							set2.add(deliveryOrder);
							obj.setDiscount1(deliveryOrder.getDiscount1());
						}
						obj.setDeliveryOrders(set2);
					}
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = DeliveryOrderDAO.getInstance().load(form.getLong("deliveryOrderId"));
				obj.setDescription(form.getString("description"));
				obj.setNumber(form.getString("number"));
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("invoiceDate"));
				obj.setExchangeRate(e);
				//obj.setNpwpNumber(form.getString("taxSerialNumber"));
				//obj.setNpwpDate(form.getCalendar("taxDate")!=null?form.getCalendar("taxDate").getTime():null);
				//obj.setPosted(false);
				//obj.setReference(form.getString("reference"));
				//obj.setDetail(form.getString("isDetail").length()>0?true:false);
				obj.setRekap(true);
				Tax tax = TaxDAO.getInstance().get(form.getLong("taxId"));
				obj.setTax(tax);
				obj.setTaxAmount(tax!=null?tax.getQuantity():form.getDouble("taxAmount"));
				//obj.setTaxProcent(form.getDouble("taxProcent"));
				// cek bon kuning or NOT!
				obj.setOrganization(users.getOrganization());
				//obj.setInvoiceDate(form.getCalendar("invoiceDate")!=null?form.getCalendar("invoiceDate").getTime():null);
				//obj.setInvoiceDue(form.getCalendar("invoiceDue")!=null?form.getCalendar("invoiceDue").getTime():null);
				obj.setDeliveryDate(form.getCalendar("deliveryDate")!=null?form.getCalendar("deliveryDate").getTime():null);
				//obj.setStatus(CommonConstants.OPEN);
				/*if (obj.getDifferenceAmount()==0) {
					obj.setCustomerPaymentStatus(CommonConstants.CLOSE);
				} else {
					if (obj.getCustomerPaymentAmount()==0) {
						obj.setCustomerPaymentStatus(CommonConstants.OPEN);
					} else obj.setCustomerPaymentStatus(CommonConstants.PARTIAL);
				}*/
				if (obj.getInvoice()==null) obj.setInvoiceStatus(CommonConstants.OPEN);
				else obj.setInvoiceStatus(CommonConstants.CLOSE);
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
				obj.setPosted(false);
				obj.setBonKuning(false);
				obj.setStore(false);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setFromBonKuningDate(form.getCalendar("fromBonKuningDate")!=null?form.getCalendar("fromBonKuningDate").getTime():null);
				obj.setToBonKuningDate(form.getCalendar("toBonKuningDate")!=null?form.getCalendar("toBonKuningDate").getTime():null);
				obj.setDetail(form.getString("isDetail").length()>0?true:false);
			}
			// save to session
			httpSession.setAttribute("deliveryOrder", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					//log.info("A1");
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateDeliveryOrderNumber(session);
					DeliveryOrderDAO.getInstance().save(obj, session);
					form.setString("deliveryOrderId", obj.getId());
					transaction.commit();
				} else {
					transaction = session.beginTransaction();
					//log.info("A2");
					DeliveryOrderDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("deliveryOrder");
				// finish
				ActionForward forward = mapping.findForward("update_status");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?deliveryOrderId="+form.getLong("deliveryOrderId"));
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
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Ap", Boolean.FALSE))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
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
				DeliveryOrderDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?deliveryOrderId="+form.getLong("deliveryOrderId"));
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
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		//HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			//OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			DeliveryOrder invoice = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
			request.setAttribute("invoice", invoice);
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				DeliveryOrderDAO.getInstance().closeSessionForReal();
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
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			DeliveryOrder deliveryOrder = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
			
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
			cell = new Cell(new Phrase("Tanggal : "+deliveryOrder.getFormatedDeliveryDate(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Kepada : "+deliveryOrder.getCustomer().getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tlp. "+users.getOrganization().getTelephone()+" - Fax. "+users.getOrganization().getFax(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Telp. : "+deliveryOrder.getCustomer().getTelephone(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Fax. : "+deliveryOrder.getCustomer().getFax(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Attn. : "+deliveryOrder.getCustomer().getContactPerson(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			document.add(table1);
			
			Table table2 = new Table(6);
			table2.setWidth(100);
			table2.setCellsFitPage(true);
			float[] b = {5,10,45,15,10,15};
			table2.setWidths(b);
			table2.setBorder(Rectangle.NO_BORDER);
			table2.setCellsFitPage(true);
			table2.setBorderWidth(1);
			table2.setPadding(1);
			table2.setSpacing(0);
			
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase("FAKTUR PENJUALAN", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase("NO. : "+deliveryOrder.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setColspan(6);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("No",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Qty",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Nama Barang",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Harga Satuan",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Disc",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Jumlah",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);

			table2.endHeaders();
			
			int j = 0;
			Iterator iterator = deliveryOrder.getDeliveryOrderDetails().iterator();
			while (iterator.hasNext()) {
			    DeliveryOrderDetail detail = (DeliveryOrderDetail)iterator.next();
			    cell = new Cell(new Phrase(++j+".", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
					cell = new Cell(new Phrase(detail.getQuantity()+" "+detail.getItemUnit().getSymbol(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getId().getItem().getName(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getFormatedPrice(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
					cell = new Cell(new Phrase(detail.getFormatedPriceQuantityDiscount(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getFormatedPriceQuantity(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
			}
			
			cell = new Cell(new Phrase("SUBTOTAL", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(deliveryOrder.getFormatedDeliveryOrderDetailAmount(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("DISC", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(deliveryOrder.getFormatedAmountDiscount(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("TAX", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(deliveryOrder.getFormatedAmountTax(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("TOTAL", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(deliveryOrder.getFormatedAmountAfterTaxAndDiscount(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			document.add(table2);
			
			Table table3 = new Table(3);
			table3.setWidth(100);
			table3.setCellsFitPage(true);
			float[] c = {33,33,33};
			table3.setWidths(c);
			table3.setBorder(Rectangle.NO_BORDER);
			table3.setCellsFitPage(true);
			table3.setBorderWidth(1);
			table3.setPadding(1);
			table3.setSpacing(0);
			
			cell = new Cell(new Phrase("TANDA TERIMA", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table3.addCell(cell);
			
/*			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table3.addCell(cell);
			
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(invoice.getInvoiceDue());
			Calendar calendar2 = new GregorianCalendar();
			calendar2.setTime(invoice.getInvoiceDate());
			cell = new Cell(new Phrase("PEMBAYARAN : KREDIT "+(calendar.get(Calendar.DAY_OF_YEAR)-calendar2.get(Calendar.DAY_OF_YEAR))+" HARI", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.addCell(cell);*/
			
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table3.addCell(cell);
			
			cell = new Cell(new Phrase("Cap Perusahaan", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			table3.addCell(cell);
			cell = new Cell(new Phrase("Perhatian : ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			table3.addCell(cell);
			cell = new Cell(new Phrase("* Barang yang sudah dibeli, tidak dapat ditukar atau dikembalikan kecuali ada perjanjian sebelumnya", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			table3.addCell(cell);
			cell = new Cell(new Phrase("* Harga yang telah disetujui tidak dapat berubah", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			table3.addCell(cell);
			cell = new Cell(new Phrase("* Bila terjadi retur, harap menyebutkan tanggal dan no faktur penjualan", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			table3.addCell(cell);
			
			document.add(table3);
			
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
				DeliveryOrderDAO.getInstance().closeSessionForReal();
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
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Session session = DeliveryOrderDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			DeliveryOrderDAO.getInstance().delete(form.getLong("deliveryOrderId"), session);
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			ex.printStackTrace();
		}finally {
			try {
			    DeliveryOrderDAO.getInstance().closeSessionForReal();
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
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			Session session = DeliveryOrderDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			// update deliveryOrder status
			DeliveryOrder deliveryOrder = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
			Set set = deliveryOrder.getDeliveryOrders();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				DeliveryOrder deliveryOrder2 = (DeliveryOrder)iterator.next();
				deliveryOrder2.setInvoiceStatus(CommonConstants.CLOSE);
				DeliveryOrderDAO.getInstance().update(deliveryOrder2, session);
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
				DeliveryOrderDAO.getInstance().closeSessionForReal();
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