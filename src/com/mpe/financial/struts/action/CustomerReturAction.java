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
import com.mpe.financial.model.Department;
import com.mpe.financial.model.Inventory;
import com.mpe.financial.model.Item;
import com.mpe.financial.model.ItemUnit;
import com.mpe.financial.model.Journal;
import com.mpe.financial.model.JournalDetail;
import com.mpe.financial.model.JournalDetailPK;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.SalesOrder;
import com.mpe.financial.model.SalesOrderDetail;
import com.mpe.financial.model.DeliveryOrder;
import com.mpe.financial.model.DeliveryOrderDetail;
import com.mpe.financial.model.CustomerRetur;
import com.mpe.financial.model.CustomerReturDetail;
import com.mpe.financial.model.CustomerReturDetailPK;
import com.mpe.financial.model.Tax;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.Warehouse;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.JournalDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.SalesOrderDAO;
import com.mpe.financial.model.dao.DeliveryOrderDAO;
import com.mpe.financial.model.dao.CustomerReturDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.TaxDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.WarehouseDAO;
import com.mpe.financial.struts.form.CustomerReturForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class CustomerReturAction extends Action {
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
		CustomerReturForm salesOrderForm = (CustomerReturForm) form;
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
						if (salesOrderForm.getString("subaction")!=null && salesOrderForm.getString("subaction").equalsIgnoreCase("refresh")) {
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
					} else if ("DEBITMEMO".equalsIgnoreCase(action)) {
						forward = performDebitMemo(mapping, form, request, response);
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
		CustomerReturForm form = (CustomerReturForm) actionForm;
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
			Criteria criteria = CustomerReturDAO.getInstance().getSession().createCriteria(CustomerRetur.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromReturDate")!=null)criteria.add(Restrictions.ge("ReturDate", new Date(form.getCalendar("fromReturDate").getTime().getTime())));
			if (form.getCalendar("toReturDate")!=null)criteria.add(Restrictions.le("ReturDate", new Date(form.getCalendar("toReturDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = CustomerReturDAO.getInstance().getSession().createCriteria(CustomerRetur.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromReturDate")!=null)criteria.add(Restrictions.ge("ReturDate", new Date(form.getCalendar("fromReturDate").getTime().getTime())));
			if (form.getCalendar("toReturDate")!=null)criteria.add(Restrictions.le("ReturDate", new Date(form.getCalendar("toReturDate").getTime().getTime())));
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
			request.setAttribute("CUSTOMERRETUR",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("customerRetur");
			
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				CustomerReturDAO.getInstance().closeSessionForReal();
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
		CustomerReturForm form = (CustomerReturForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
		      Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class).add(Restrictions.eq("Code", new String(form.getString("itemCode")))).uniqueResult();
		      form.setString("itemId", item.getId());
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			// remove
			CustomerRetur obj = (CustomerRetur)httpSession.getAttribute("customerRetur");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVECUSTOMERRETURDETAIL")) {
				CustomerReturDetail removeCustomerReturDetail = null;
				Iterator iterator = obj.getCustomerReturDetails().iterator();
				while (iterator.hasNext()) {
					CustomerReturDetail customerReturDetail = (CustomerReturDetail)iterator.next();
					if (form.getLong("itemId") == customerReturDetail.getId().getItem().getId()) {
						removeCustomerReturDetail = customerReturDetail;
					}
				}
				if (removeCustomerReturDetail!=null) {
					Set set = obj.getCustomerReturDetails();
					set.remove(removeCustomerReturDetail);
					obj.setCustomerReturDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("customerRetur", obj);
			}
			// relationships
			List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemUnitLst", itemUnitLst);
			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerLst", customerLst);
			List warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("warehouseLst", warehouseLst);
			/*
			List itemLst = ItemDAO.getInstance().getSession().createCriteria(Item.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("itemLst", itemLst);
			*/
			List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("currencyLst", currencyLst);
			List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("locationLst", locationLst);
			if (form.getLong("customerReturId") == 0) {
				List deliveryOrderLst = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class)
					.add(Restrictions.eq("Customer.Id", new Long(obj!=null?(obj.getCustomer()!=null?obj.getCustomer().getId():form.getLong("customerId")):form.getLong("customerId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.add(Restrictions.eq("BonKuning", Boolean.FALSE))
					.add(Restrictions.eq("Store", Boolean.FALSE))
					.list();
				request.setAttribute("deliveryOrderLst", deliveryOrderLst);
				form.setString("customerReturId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getCustomerReturNumber());
				form.setCurentCalendar("returDate");
				if (obj!=null) {
					form.setString("customerReturId",obj.getId());
					form.setString("note",obj.getNote());
					form.setCalendar("returDate",obj.getReturDate());
					form.setString("deliveryOrderId",obj.getDeliveryOrder()!=null?obj.getDeliveryOrder().getId():0);
					form.setString("number",obj.getNumber());
					form.setString("reference",obj.getReference());
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
					form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
					Set customerReturDetailLst = obj.getCustomerReturDetails();
					request.setAttribute("customerReturDetailLst", customerReturDetailLst);
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
					obj = CustomerReturDAO.getInstance().get(form.getLong("customerReturId"));
					httpSession.setAttribute("customerRetur",obj);
				}
				form.setString("customerReturId",obj.getId());
				form.setString("note",obj.getNote());
				form.setCalendar("returDate",obj.getReturDate());
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				form.setString("deliveryOrderId",obj.getDeliveryOrder()!=null?obj.getDeliveryOrder().getId():0);
				form.setString("number",obj.getNumber());
				form.setString("reference",obj.getReference());
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				List deliveryOrderLst = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class)
					.add(Restrictions.eq("Customer.Id", new Long(obj!=null?(obj.getCustomer()!=null?obj.getCustomer().getId():form.getLong("customerId")):form.getLong("customerId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.createCriteria("CustomerRetur", "CustomerRetur")
					.add(Restrictions.eq("CustomerRetur.Id", new Long(obj.getId())))
					.list();
				request.setAttribute("deliveryOrderLst", deliveryOrderLst);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
				form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
				Set customerReturDetailLst = obj.getCustomerReturDetails();
				request.setAttribute("customerReturDetailLst", customerReturDetailLst);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
			}
			if (form.getLong("itemId") > 0) {
				//boolean isNewData = true;
				if (obj!=null && obj.getCustomerReturDetails()!=null) {
					Iterator iterator = obj.getCustomerReturDetails().iterator();
					while (iterator.hasNext()) {
						CustomerReturDetail customerReturDetail = (CustomerReturDetail)iterator.next();
						if (form.getLong("itemId") == customerReturDetail.getId().getItem().getId()) {
							//isNewData = false;
							form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), customerReturDetail.getPrice()));
							form.setString("costPrice", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), customerReturDetail.getCostPrice()));
							form.setString("quantity", customerReturDetail.getQuantity());
							form.setString("itemCode", customerReturDetail.getId().getItem().getCode());
							form.setString("discountProcent", customerReturDetail.getDiscountProcent());
							form.setString("exchangeRate", customerReturDetail.getExchangeRate());
							form.setString("costPriceExchangeRate", customerReturDetail.getCostPriceExchangeRate());
							form.setString("warehouseId", customerReturDetail.getWarehouse()!=null?customerReturDetail.getWarehouse().getId():0);
							form.setString("itemUnitId", customerReturDetail.getItemUnit()!=null?customerReturDetail.getItemUnit().getId():0);
							form.setString("customerReturDetailCurrencyId", customerReturDetail.getCurrency()!=null?customerReturDetail.getCurrency().getId():0);
							form.setString("costPriceCurrencyId", customerReturDetail.getCostPriceCurrency()!=null?customerReturDetail.getCostPriceCurrency().getId():0);
							form.setString("taxDetailId", customerReturDetail.getTax()!=null?customerReturDetail.getTax().getId():0);
							form.setString("taxDetailAmount", customerReturDetail.getTaxAmount());
							form.setString("itemDescription", customerReturDetail.getDescription());
							form.setString("itemDiscountAmount", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), customerReturDetail.getDiscountAmount()));
							form.setString("itemDiscountProcent", customerReturDetail.getDiscountProcent());
						}
					}
				}
			}
			request.setAttribute("customerReturDetailAmount", obj!=null?obj.getFormatedCustomerReturDetailAmount():"-");
			request.setAttribute("amountTax", obj!=null?obj.getFormatedAmountTax():"-");
			request.setAttribute("amountDiscount", obj!=null?obj.getFormatedAmountDiscount():"-");
			request.setAttribute("amountAfterDiscount", obj!=null?obj.getFormatedAmountAfterDiscount():"-");
			request.setAttribute("amountAfterTaxAndDiscount", obj!=null?obj.getFormatedAmountAfterTaxAndDiscount():"-");
		}catch(Exception ex) {
			try {
				List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("itemUnitLst", itemUnitLst);
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
				List warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("warehouseLst", warehouseLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
				List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("currencyLst", currencyLst);
				List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("taxLst", taxLst);
				List deliveryOrderLst = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class)
					.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
				request.setAttribute("deliveryOrderLst", deliveryOrderLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				CustomerReturDAO.getInstance().closeSessionForReal();
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
		CustomerReturForm form = (CustomerReturForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = CustomerReturDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("customerRetur");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			CustomerRetur obj = (CustomerRetur)httpSession.getAttribute("customerRetur");
			if (form.getLong("customerReturId") == 0) {
				obj = (CustomerRetur)CustomerReturDAO.getInstance().getSession().createCriteria(CustomerRetur.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (CustomerRetur)httpSession.getAttribute("customerRetur");
					DeliveryOrder deliveryOrder = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
					if (deliveryOrder!=null) form.setString("salesOrderId", deliveryOrder.getSalesOrder().getId());
					if (obj==null) {
						obj = new CustomerRetur();
						obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
						obj.setDeliveryOrder(deliveryOrder);
						obj.setProject(deliveryOrder.getProject());
						obj.setDepartment(deliveryOrder.getDepartment());
						Set set = obj.getCustomerReturDetails();
						if (set==null) set = new LinkedHashSet();
						Iterator iterator = deliveryOrder.getDeliveryOrderDetails().iterator();
						while (iterator.hasNext()) {
							DeliveryOrderDetail deliveryOrderDetail  = (DeliveryOrderDetail)iterator.next();
							if (deliveryOrderDetail.getQuantity()>0) {
								CustomerReturDetail customerReturDetail = new CustomerReturDetail();
								customerReturDetail.setItemUnit(deliveryOrderDetail.getItemUnit());
								customerReturDetail.setWarehouse(deliveryOrderDetail.getWarehouse());
								CustomerReturDetailPK customerReturDetailPK = new CustomerReturDetailPK();
								customerReturDetailPK.setItem(deliveryOrderDetail.getId().getItem());
								customerReturDetailPK.setCustomerRetur(obj);
								customerReturDetail.setId(customerReturDetailPK);
								customerReturDetail.setCurrency(deliveryOrderDetail.getCurrency());
								customerReturDetail.setExchangeRate(deliveryOrderDetail.getExchangeRate());
								customerReturDetail.setPrice(deliveryOrderDetail.getPrice());
								customerReturDetail.setDiscountProcent(deliveryOrderDetail.getDiscountProcent());
								customerReturDetail.setCostPriceExchangeRate(deliveryOrderDetail.getCostPriceExchangeRate());
								customerReturDetail.setCostPrice(deliveryOrderDetail.getCostPrice());
								customerReturDetail.setQuantity(deliveryOrderDetail.getQuantity());
								customerReturDetail.setCostPriceCurrency(deliveryOrderDetail.getCostPriceCurrency());
								customerReturDetail.setDiscountAmount(deliveryOrderDetail.getDiscountAmount());
								customerReturDetail.setDescription(deliveryOrderDetail.getDescription());
								customerReturDetail.setTax(deliveryOrderDetail.getTax());
								customerReturDetail.setTaxAmount(deliveryOrderDetail.getTaxAmount());
								customerReturDetail.setUnitConversion(deliveryOrderDetail.getUnitConversion());
								set.add(customerReturDetail);
							}
						}
						obj.setCustomerReturDetails(set);
					}
					obj.setCurrency(deliveryOrder!=null?(deliveryOrder.getSalesOrder()!=null?deliveryOrder.getSalesOrder().getCurrency():null):null);
					double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("returDate"));
					obj.setExchangeRate(e);
					obj.setNote(form.getString("note"));
					obj.setReference(form.getString("reference"));
					obj.setNumber(form.getString("number"));
					obj.setOrganization(users.getOrganization());
					obj.setReturDate(form.getCalendar("returDate")!=null?form.getCalendar("returDate").getTime():null);
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
					// create new customerRetur
					if (form.getLong("deliveryOrderId")!=obj.getDeliveryOrder().getId()) {
						Set set = new LinkedHashSet();
						Iterator iterator = deliveryOrder.getDeliveryOrderDetails().iterator();
						while (iterator.hasNext()) {
							DeliveryOrderDetail deliveryOrderDetail  = (DeliveryOrderDetail)iterator.next();
							if (deliveryOrderDetail.getQuantity()>0) {
								CustomerReturDetail customerReturDetail = new CustomerReturDetail();
								customerReturDetail.setItemUnit(deliveryOrderDetail.getItemUnit());
								customerReturDetail.setWarehouse(deliveryOrderDetail.getWarehouse());
								CustomerReturDetailPK customerReturDetailPK = new CustomerReturDetailPK();
								customerReturDetailPK.setItem(deliveryOrderDetail.getId().getItem());
								customerReturDetailPK.setCustomerRetur(obj);
								customerReturDetail.setId(customerReturDetailPK);
								customerReturDetail.setCurrency(deliveryOrderDetail.getCurrency());
								customerReturDetail.setExchangeRate(deliveryOrderDetail.getExchangeRate());
								customerReturDetail.setPrice(deliveryOrderDetail.getPrice());
								customerReturDetail.setQuantity(deliveryOrderDetail.getQuantity());
								customerReturDetail.setDiscountProcent(deliveryOrderDetail.getDiscountProcent());
								customerReturDetail.setCostPriceExchangeRate(deliveryOrderDetail.getCostPriceExchangeRate());
								customerReturDetail.setCostPrice(deliveryOrderDetail.getCostPrice());
								customerReturDetail.setCostPriceCurrency(deliveryOrderDetail.getCostPriceCurrency());
								customerReturDetail.setDiscountAmount(deliveryOrderDetail.getDiscountAmount());
								customerReturDetail.setDescription(deliveryOrderDetail.getDescription());
								customerReturDetail.setTax(deliveryOrderDetail.getTax());
								customerReturDetail.setTaxAmount(deliveryOrderDetail.getTaxAmount());
								customerReturDetail.setUnitConversion(deliveryOrderDetail.getUnitConversion());
								set.add(customerReturDetail);
							}
						}
						obj.setCustomerReturDetails(set);
						obj.setDeliveryOrder(deliveryOrder);
					}
					// create journal
					Journal journal = new Journal();
					journal.setCustomerRetur(obj);
					journal.setCurrency(obj.getCurrency());
					journal.setProject(deliveryOrder.getProject());
					journal.setExchangeRate(e);
					journal.setJournalDate(obj.getReturDate());
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
					// debit #1
					JournalDetail journalDetail = new JournalDetail();
					JournalDetailPK journalDetailPK = new JournalDetailPK();
					journalDetailPK.setChartOfAccount(organizationSetup.getInventoryAccount());
					journalDetailPK.setJournal(journal);
					journalDetail.setId(journalDetailPK);
					journalDetail.setDepartment(obj.getDepartment());
					journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==true?obj.getCustomerReturDetailCostPriceAmount():-obj.getCustomerReturDetailCostPriceAmount());
					set.add(journalDetail);
					// debit #2
					JournalDetail journalDetail2 = new JournalDetail();
					JournalDetailPK journalDetailPK2 = new JournalDetailPK();
					journalDetailPK2.setChartOfAccount(organizationSetup.getCustomerReturAccount());
					journalDetailPK2.setJournal(journal);
					journalDetail2.setId(journalDetailPK2);
					journalDetail2.setAmount(organizationSetup.getCustomerReturAccount().isDebit()==true?obj.getCustomerReturDetailAmount():-obj.getCustomerReturDetailAmount());
					journalDetail2.setDepartment(obj.getDepartment());
					set.add(journalDetail2);
					// credit #1
					JournalDetail journalDetail3 = new JournalDetail();
					JournalDetailPK journalDetailPK3 = new JournalDetailPK();
					journalDetailPK3.setJournal(journal);
					if (customers.getChartOfAccount()==null) {
						journalDetailPK3.setChartOfAccount(organizationSetup.getArAccount());
						journalDetail3.setAmount(organizationSetup.getArAccount().isDebit()==false?obj.getCustomerReturDetailAmount():-obj.getCustomerReturDetailAmount());
					} else {
						journalDetailPK3.setChartOfAccount(customers.getChartOfAccount());
						journalDetail3.setAmount(customers.getChartOfAccount().isDebit()==false?obj.getCustomerReturDetailAmount():-obj.getCustomerReturDetailAmount());
					}
					journalDetail3.setDepartment(obj.getDepartment());
					journalDetail3.setId(journalDetailPK3);
					set.add(journalDetail3);
					// credit #2
					JournalDetail journalDetail4 = new JournalDetail();
					JournalDetailPK journalDetailPK4 = new JournalDetailPK();
					journalDetailPK4.setChartOfAccount(organizationSetup.getCogsAccount());
					journalDetailPK4.setJournal(journal);
					journalDetail4.setId(journalDetailPK4);
					journalDetail4.setDepartment(obj.getDepartment());
					journalDetail4.setAmount(organizationSetup.getCogsAccount().isDebit()==false?obj.getCustomerReturDetailCostPriceAmount():-obj.getCustomerReturDetailCostPriceAmount());
					set.add(journalDetail4);
					journal.setJournalDetails(set);
					obj.setJournal(journal);
				} else {
					List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("itemUnitLst", itemUnitLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
					List warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("warehouseLst", warehouseLst);
					List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("locationLst", locationLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
					List deliveryOrderLst = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class)
						.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
					request.setAttribute("deliveryOrderLst", deliveryOrderLst);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = CustomerReturDAO.getInstance().get(form.getLong("customerReturId"));
				DeliveryOrder deliveryOrder = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
				if (deliveryOrder!=null) form.setString("salesOrderId", deliveryOrder.getSalesOrder().getId());
				obj.setDeliveryOrder(deliveryOrder);
				obj.setProject(deliveryOrder.getProject());
				obj.setDepartment(deliveryOrder.getDepartment());
				obj.setNote(form.getString("note"));
				obj.setNumber(form.getString("number"));
				obj.setOrganization(users.getOrganization());
				obj.setCurrency(deliveryOrder!=null?(deliveryOrder.getSalesOrder()!=null?deliveryOrder.getSalesOrder().getCurrency():null):null);
				double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("returDate"));
				obj.setExchangeRate(e);
				obj.setReturDate(form.getCalendar("returDate")!=null?form.getCalendar("returDate").getTime():null);
				//obj.setStatus(CommonConstants.OPEN");
				//obj.setCustomerPaymentStatus(CommonConstants.OPEN);
				Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setCustomer(customers);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
				obj.setCustomerAlias(customersAlias);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("customerReturId"));
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				// create journal
				//Journal journal = obj.getJournal();
				Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
					.add(Restrictions.eq("CustomerRetur.Id", new Long(obj.getId()))).uniqueResult();
				journal.setCustomerRetur(obj);
				journal.setCurrency(obj.getCurrency());
				journal.setProject(deliveryOrder.getProject());
				journal.setExchangeRate(e);
				journal.setJournalDate(obj.getReturDate());
				journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
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
				// debit #1
				JournalDetail journalDetail = new JournalDetail();
				JournalDetailPK journalDetailPK = new JournalDetailPK();
				journalDetailPK.setChartOfAccount(organizationSetup.getInventoryAccount());
				journalDetailPK.setJournal(journal);
				journalDetail.setId(journalDetailPK);
				journalDetail.setDepartment(obj.getDepartment());
				journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==true?obj.getCustomerReturDetailCostPriceAmount():-obj.getCustomerReturDetailCostPriceAmount());
				set.add(journalDetail);
				// debit #2
				JournalDetail journalDetail2 = new JournalDetail();
				JournalDetailPK journalDetailPK2 = new JournalDetailPK();
				journalDetailPK2.setChartOfAccount(organizationSetup.getCustomerReturAccount());
				journalDetailPK2.setJournal(journal);
				journalDetail2.setId(journalDetailPK2);
				journalDetail2.setDepartment(obj.getDepartment());
				journalDetail2.setAmount(organizationSetup.getCustomerReturAccount().isDebit()==true?obj.getCustomerReturDetailAmount():-obj.getCustomerReturDetailAmount());
				set.add(journalDetail2);
				// credit #1
				JournalDetail journalDetail3 = new JournalDetail();
				JournalDetailPK journalDetailPK3 = new JournalDetailPK();
				journalDetailPK3.setJournal(journal);
				if (customers.getChartOfAccount()==null) {
					journalDetailPK3.setChartOfAccount(organizationSetup.getArAccount());
					journalDetail3.setAmount(organizationSetup.getArAccount().isDebit()==false?obj.getCustomerReturDetailAmount():-obj.getCustomerReturDetailAmount());
				} else {
					journalDetailPK3.setChartOfAccount(customers.getChartOfAccount());
					journalDetail3.setAmount(customers.getChartOfAccount().isDebit()==false?obj.getCustomerReturDetailAmount():-obj.getCustomerReturDetailAmount());
				}
				journalDetail3.setDepartment(obj.getDepartment());
				journalDetail3.setId(journalDetailPK3);
				set.add(journalDetail3);
				// credit #2
				JournalDetail journalDetail4 = new JournalDetail();
				JournalDetailPK journalDetailPK4 = new JournalDetailPK();
				journalDetailPK4.setChartOfAccount(organizationSetup.getCogsAccount());
				journalDetailPK4.setJournal(journal);
				journalDetail4.setId(journalDetailPK4);
				journalDetail4.setDepartment(obj.getDepartment());
				journalDetail4.setAmount(organizationSetup.getCogsAccount().isDebit()==false?obj.getCustomerReturDetailCostPriceAmount():-obj.getCustomerReturDetailCostPriceAmount());
				set.add(journalDetail4);
				journal.setJournalDetails(set);
				obj.setJournal(journal);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDCUSTOMERRETURDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("quantity")>0) {
					CustomerReturDetail customerReturDetail = new CustomerReturDetail();
					//Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					customerReturDetail.setItemUnit(itemUnit);
					CustomerReturDetailPK customerReturDetailPK = new CustomerReturDetailPK();
					customerReturDetailPK.setItem(inventory);
					customerReturDetailPK.setCustomerRetur(obj);
					customerReturDetail.setId(customerReturDetailPK);
					Warehouse warehouse = WarehouseDAO.getInstance().get(form.getLong("warehouseId"));
					customerReturDetail.setWarehouse(warehouse);
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("customerReturDetailCurrencyId"));
					Currency currency2 = CurrencyDAO.getInstance().get(form.getLong("costPriceCurrencyId"));
					customerReturDetail.setCurrency(currency);
					customerReturDetail.setExchangeRate(form.getDouble("exchangeRate"));
					customerReturDetail.setPrice(form.getDouble("price"));
					customerReturDetail.setQuantity(form.getDouble("quantity"));
					customerReturDetail.setCostPriceExchangeRate(form.getDouble("costPriceExchangeRate"));
					customerReturDetail.setCostPrice(form.getDouble("costPrice"));
					customerReturDetail.setDiscountProcent(form.getDouble("discountProcent"));
					customerReturDetail.setCostPriceCurrency(currency2);
					Tax taxDetail = TaxDAO.getInstance().get(form.getLong("taxDetailId"));
					customerReturDetail.setTax(taxDetail);
					customerReturDetail.setTaxAmount(taxDetail!=null?taxDetail.getQuantity():0);
					customerReturDetail.setDiscountAmount(form.getDouble("itemDiscountAmount"));
					customerReturDetail.setDiscountProcent(form.getDouble("itemDiscountProcent"));
					customerReturDetail.setDescription(form.getString("itemDescription"));
					customerReturDetail.setUnitConversion(ItemUnitDAO.getInstance().getConversion(itemUnit, inventory.getItemUnit(), inventory));
					Set set = obj.getCustomerReturDetails();
					if (set==null) set = new LinkedHashSet();
					CustomerReturDetail removeCustomerReturDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						CustomerReturDetail customerReturDetail2 = (CustomerReturDetail)iterator.next();
						if (form.getLong("itemId")==customerReturDetail2.getId().getItem().getId()) {
							removeCustomerReturDetail = customerReturDetail2;
						}
					}
					if (removeCustomerReturDetail!=null) {
						set.remove(removeCustomerReturDetail);
						set.add(customerReturDetail);
					} else {
						set.add(customerReturDetail);
					}
					obj.setCustomerReturDetails(set);
					// netral
					form.setString("itemId", "");
					form.setString("warehouseId", "");
					form.setString("itemCode", "");
					form.setString("price", "");
					form.setString("quantity", "");
					form.setString("discountProcent", "");
					form.setString("itemUnitId", "");
					form.setString("costPrice", "");
					form.setString("costPriceExchangeRate", "");
					form.setString("customerReturDetailCurrencyId", "");
					form.setString("costPriceCurrencyId", "");
				}
				// netral
				form.setString("itemId", "");
				form.setString("warehouseId", "");
				form.setString("itemCode", "");
				form.setString("price", "");
				form.setString("quantity", "");
				form.setString("discountProcent", "");
				form.setString("itemUnitId", "");
				form.setString("costPrice", "");
				form.setString("costPriceExchangeRate", "");
				form.setString("customerReturDetailCurrencyId", "");
				form.setString("costPriceCurrencyId", "");
			}
			// save to session
			httpSession.setAttribute("customerRetur", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateCustomerReturNumber(session);
					RunningNumberDAO.getInstance().updateJournalNumber(session);
					if (obj.getDeliveryOrder()!=null) form.setString("salesOrderId", obj.getDeliveryOrder().getSalesOrder().getId());
					CustomerReturDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					transaction = session.beginTransaction();
					if (obj.getDeliveryOrder()!=null) form.setString("salesOrderId", obj.getDeliveryOrder().getSalesOrder().getId());
					CustomerReturDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("customerRetur");
				// finish
				ActionForward forward = mapping.findForward("update_status");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?customerReturId="+form.getLong("customerReturId")+"&salesOrderId="+form.getLong("salesOrderId"));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				if (transaction!=null) transaction.rollback();
				try {
					List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("itemUnitLst", itemUnitLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
					List warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("warehouseLst", warehouseLst);
					List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("locationLst", locationLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
					List deliveryOrderLst = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class)
						.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
					request.setAttribute("deliveryOrderLst", deliveryOrderLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			ex.printStackTrace();
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				CustomerReturDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?customerReturId="+form.getLong("customerReturId"));
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
		CustomerReturForm form = (CustomerReturForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// update po => deliveryOrderStatus!!!
			boolean isClosed = true;
			SalesOrder salesOrder = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
			if (salesOrder!=null) {
				Iterator iterator = salesOrder.getSalesOrderDetails().iterator();
				while (iterator.hasNext()) {
					SalesOrderDetail salesOrderDetail = (SalesOrderDetail)iterator.next();
					if (salesOrderDetail.getQuantity()!=(salesOrderDetail.getDeliveryQuantity()-salesOrderDetail.getReturQuantity())) isClosed = false;
				}
				if (isClosed)salesOrder.setDeliveryOrderStatus(CommonConstants.CLOSE);
				else salesOrder.setDeliveryOrderStatus(CommonConstants.PARTIAL);
				SalesOrderDAO.getInstance().update(salesOrder);
			}
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				CustomerReturDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
	}
	
	/** 
	 * Method performDebitMemo
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performDebitMemo(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//CustomerReturForm form = (CustomerReturForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				CustomerReturDAO.getInstance().closeSessionForReal();
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
		CustomerReturForm form = (CustomerReturForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			CustomerRetur customerRetur = CustomerReturDAO.getInstance().get(form.getLong("customerReturId"));
			request.setAttribute("customerRetur", customerRetur);
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				CustomerReturDAO.getInstance().closeSessionForReal();
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
		CustomerReturForm form = (CustomerReturForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			CustomerRetur customerRetur = CustomerReturDAO.getInstance().get(form.getLong("customerReturId"));
			
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
			cell = new Cell(new Phrase("Tanggal : "+customerRetur.getFormatedReturDate(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Kepada : "+customerRetur.getCustomer().getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tlp. "+users.getOrganization().getTelephone()+" - Fax. "+users.getOrganization().getFax(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Telp. : "+customerRetur.getCustomer().getTelephone(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Fax. : "+customerRetur.getCustomer().getFax(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			//cell = new Cell(new Phrase("Attn. : "+customerRetur.getCustomer().getContactPerson(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell = new Cell(new Phrase("Attn. : "+"-", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
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
			cell = new Cell(new Phrase("NOTA KREDIT", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
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
			cell = new Cell(new Phrase("NO. : "+customerRetur.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(5);
			table2.addCell(cell);
			cell = new Cell(new Phrase("LPB : "+customerRetur.getDeliveryOrder().getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
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
			Iterator iterator = customerRetur.getCustomerReturDetails().iterator();
			while (iterator.hasNext()) {
			    CustomerReturDetail detail = (CustomerReturDetail)iterator.next();
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
			cell = new Cell(new Phrase(customerRetur.getFormatedAmountAfterTaxAndDiscount(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
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
				CustomerReturDAO.getInstance().closeSessionForReal();
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
		CustomerReturForm form = (CustomerReturForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			CustomerReturDAO.getInstance().delete(form.getLong("customerReturId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				CustomerReturDAO.getInstance().closeSessionForReal();
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
		errors.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.global",ex.getMessage()));
		saveErrors(request,errors);
	}

}