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
import com.mpe.financial.model.ItemPrice;
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
import com.mpe.financial.model.DeliveryOrderDetailPK;
import com.mpe.financial.model.Tax;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.Warehouse;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemPriceDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.JournalDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.SalesOrderDAO;
import com.mpe.financial.model.dao.DeliveryOrderDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.TaxDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.WarehouseDAO;
import com.mpe.financial.struts.form.DeliveryOrderForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class DeliveryOrderAction extends Action {
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
					} else if ("STORELIST".equalsIgnoreCase(action)) {
							forward = performStorePartialList(mapping, form, request, response);
					} else if ("STOREFORM".equalsIgnoreCase(action)) {
						forward = performStoreForm(mapping, form, request, response);
					} else if ("STORESAVE".equalsIgnoreCase(action)) {
						if (purchaseOrderForm.getString("subaction")!=null && purchaseOrderForm.getString("subaction").equalsIgnoreCase("refresh")) {
							forward = performStoreForm(mapping, form, request, response);
						} else {
							forward = performStoreSave(mapping, form, request, response);
						}
					} else if ("STOREDETAIL".equalsIgnoreCase(action)) { 
						forward = performStoreDetail(mapping, form, request, response);
					} else if ("STOREDELETE".equalsIgnoreCase(action)) {
						forward = performStoreDelete(mapping, form, request, response);
					} else if ("STOREUPDATESTATUS".equalsIgnoreCase(action)) {
						forward = performStoreUpdateStatus(mapping, form, request, response);
					} else if ("STOREPRINTED".equalsIgnoreCase(action)) {
							forward = performPrinted(mapping, form, request, response);
					} else if ("BONKUNINGLIST".equalsIgnoreCase(action)) {
							forward = performBonKuningPartialList(mapping, form, request, response);
					} else if ("BONKUNINGFORM".equalsIgnoreCase(action)) {
						forward = performBonKuningForm(mapping, form, request, response);
					} else if ("BONKUNINGSAVE".equalsIgnoreCase(action)) {
						//log.info("G1 : "+purchaseOrderForm.getString("subaction"));
					  if (purchaseOrderForm.getString("subaction")!=null && purchaseOrderForm.getString("subaction").equalsIgnoreCase("refresh")) {
				      forward = performBonKuningForm(mapping, form, request, response);
						} else {
							forward = performBonKuningSave(mapping, form, request, response);
						}
					} else if ("BONKUNINGDETAIL".equalsIgnoreCase(action)) { 
						forward = performBonKuningDetail(mapping, form, request, response);
					} else if ("BONKUNINGDELETE".equalsIgnoreCase(action)) {
						forward = performBonKuningDelete(mapping, form, request, response);
					} else if ("BONKUNINGUPDATESTATUS".equalsIgnoreCase(action)) {
						forward = performBonKuningUpdateStatus(mapping, form, request, response);
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
			Criteria criteria = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromDeliveryDate")!=null)criteria.add(Restrictions.ge("DeliveryDate", new Date(form.getCalendar("fromDeliveryDate").getTime().getTime())));
			if (form.getCalendar("toDeliveryDate")!=null)criteria.add(Restrictions.le("DeliveryDate", new Date(form.getCalendar("toDeliveryDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.add(Restrictions.eq("BonKuning", Boolean.FALSE));
			criteria.add(Restrictions.eq("Store", Boolean.FALSE));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromDeliveryDate")!=null)criteria.add(Restrictions.ge("DeliveryDate", new Date(form.getCalendar("fromDeliveryDate").getTime().getTime())));
			if (form.getCalendar("toDeliveryDate")!=null)criteria.add(Restrictions.le("DeliveryDate", new Date(form.getCalendar("toDeliveryDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.add(Restrictions.eq("BonKuning", Boolean.FALSE));
			criteria.add(Restrictions.eq("Store", Boolean.FALSE));
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("DELIVERYORDER",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("deliveryOrder");
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
			if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
		      Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class).add(Restrictions.eq("Code", new String(form.getString("itemCode")))).uniqueResult();
		      form.setString("itemId", item.getId());
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			// remove
			DeliveryOrder obj = (DeliveryOrder)httpSession.getAttribute("deliveryOrder");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEDELIVERYORDERDETAIL")) {
				DeliveryOrderDetail removeDeliveryOrderDetail = null;
				Iterator iterator = obj.getDeliveryOrderDetails().iterator();
				while (iterator.hasNext()) {
					DeliveryOrderDetail deliveryOrderDetail = (DeliveryOrderDetail)iterator.next();
					if (form.getLong("itemId") == deliveryOrderDetail.getId().getItem().getId()) {
						removeDeliveryOrderDetail = deliveryOrderDetail;
					}
				}
				if (removeDeliveryOrderDetail!=null) {
					Set set = obj.getDeliveryOrderDetails();
					set.remove(removeDeliveryOrderDetail);
					obj.setDeliveryOrderDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("deliveryOrder", obj);
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
			List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("currencyLst", currencyLst);
			List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("locationLst", locationLst);
			if (form.getLong("deliveryOrderId") == 0) {
				List salesOrderLst = SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class)
					.add(Restrictions.eq("Customer.Id", new Long(obj!=null?(obj.getCustomer()!=null?obj.getCustomer().getId():form.getLong("customerId")):form.getLong("customerId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("DeliveryOrderStatus", new String(CommonConstants.CLOSE)))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.list();
				request.setAttribute("salesOrderLst", salesOrderLst);
				//log.info("C : "+salesOrderLst.size());
				form.setString("deliveryOrderId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getDeliveryOrderNumber());
				form.setCurentCalendar("deliveryDate");
				if (obj!=null) {
					form.setString("deliveryOrderId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setCalendar("deliveryDate",obj.getDeliveryDate());
					form.setString("salesOrderId",obj.getSalesOrder()!=null?obj.getSalesOrder().getId():0);
					form.setString("number",obj.getNumber());
					form.setString("policeNumber",obj.getPoliceNumber());
					form.setString("vehicle",obj.getVehicle());
					form.setString("expedition",obj.getExpedition());
					form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
					form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
					form.setString("departmentId",obj.getDepartment()!=null?obj.getDepartment().getId():0);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
					form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					Set deliveryOrderDetailLst = obj.getDeliveryOrderDetails();
					request.setAttribute("deliveryOrderDetailLst", deliveryOrderDetailLst);
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
					obj = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
					httpSession.setAttribute("deliveryOrder",obj);
				}
				form.setString("deliveryOrderId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("salesOrderId",obj.getSalesOrder()!=null?obj.getSalesOrder().getId():0);
				form.setString("number",obj.getNumber());
				form.setString("policeNumber",obj.getPoliceNumber());
				form.setString("vehicle",obj.getVehicle());
				form.setString("expedition",obj.getExpedition());
				form.setCalendar("deliveryDate",obj.getDeliveryDate());
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
				form.setString("departmentId",obj.getDepartment()!=null?obj.getDepartment().getId():0);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
				form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				List salesOrderLst = SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class)
					.add(Restrictions.eq("Customer.Id", new Long(obj!=null?(obj.getCustomer()!=null?obj.getCustomer().getId():form.getLong("customerId")):form.getLong("customerId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.createCriteria("DeliveryOrders", "DeliveryOrder")
					.add(Restrictions.eq("DeliveryOrder.Id", new Long(obj.getId())))
					.list();
				request.setAttribute("salesOrderLst", salesOrderLst);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set deliveryOrderDetailLst = obj.getDeliveryOrderDetails();
				request.setAttribute("deliveryOrderDetailLst", deliveryOrderDetailLst);
			}
			if (form.getLong("itemId") > 0) {
				//boolean isNewData = true;
				if (obj!=null && obj.getDeliveryOrderDetails()!=null) {
					Iterator iterator = obj.getDeliveryOrderDetails().iterator();
					while (iterator.hasNext()) {
						DeliveryOrderDetail deliveryOrderDetail = (DeliveryOrderDetail)iterator.next();
						if (form.getLong("itemId") == deliveryOrderDetail.getId().getItem().getId()) {
							//isNewData = false;
							form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), deliveryOrderDetail.getPrice()));
							form.setString("quantity", deliveryOrderDetail.getQuantity());
							form.setString("itemCode", deliveryOrderDetail.getId().getItem().getCode());
							form.setString("costPrice", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), deliveryOrderDetail.getCostPrice()));
							form.setString("costPriceExchangeRate", deliveryOrderDetail.getCostPriceExchangeRate());
							form.setString("discountProcent", deliveryOrderDetail.getDiscountProcent());
							form.setString("exchangeRate", deliveryOrderDetail.getExchangeRate());
							form.setString("itemUnitId", deliveryOrderDetail.getItemUnit()!=null?deliveryOrderDetail.getItemUnit().getId():0);
							form.setString("warehouseId", deliveryOrderDetail.getWarehouse()!=null?deliveryOrderDetail.getWarehouse().getId():0);
							form.setString("deliveryOrderDetailCurrencyId", deliveryOrderDetail.getCurrency()!=null?deliveryOrderDetail.getCurrency().getId():0);
							form.setString("costPriceCurrencyId", deliveryOrderDetail.getCostPriceCurrency()!=null?deliveryOrderDetail.getCostPriceCurrency().getId():0);
							form.setString("taxDetailId", deliveryOrderDetail.getTax()!=null?deliveryOrderDetail.getTax().getId():0);
							form.setString("taxDetailAmount", deliveryOrderDetail.getTaxAmount());
							form.setString("itemDescription", deliveryOrderDetail.getDescription());
							form.setString("itemDiscountAmount", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), deliveryOrderDetail.getDiscountAmount()));
							form.setString("itemDiscountProcent", deliveryOrderDetail.getDiscountProcent());
						}
					}
				}
			}
			request.setAttribute("deliveryOrderDetailAmount", obj!=null?obj.getFormatedDeliveryOrderDetailAmount():"-");
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
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
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
				List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("taxLst", taxLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
			}catch(Exception exx) {
			}
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
				httpSession.removeAttribute("deliveryOrder");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			DeliveryOrder obj = (DeliveryOrder)httpSession.getAttribute("deliveryOrder");
			if (form.getLong("deliveryOrderId") == 0) {
				obj = (DeliveryOrder)DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (DeliveryOrder)httpSession.getAttribute("deliveryOrder");
					SalesOrder salesOrder = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
					if (obj==null) {
						obj = new DeliveryOrder();
						obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
						obj.setSalesOrder(salesOrder);
						Set set = obj.getDeliveryOrderDetails();
						if (set==null) set = new LinkedHashSet();
						Iterator iterator = salesOrder.getSalesOrderDetails().iterator();
						while (iterator.hasNext()) {
							SalesOrderDetail salesOrderDetail = (SalesOrderDetail)iterator.next();
							if (salesOrderDetail.getQuantity()>(salesOrderDetail.getDeliveryQuantity()-salesOrderDetail.getReturQuantity())) {
								DeliveryOrderDetail deliveryOrderDetail = new DeliveryOrderDetail();
								deliveryOrderDetail.setItemUnit(salesOrderDetail.getItemUnit());
								DeliveryOrderDetailPK deliveryOrderDetailPK = new DeliveryOrderDetailPK();
								deliveryOrderDetailPK.setItem(salesOrderDetail.getId().getItem());
								deliveryOrderDetailPK.setDeliveryOrder(obj);
								deliveryOrderDetail.setId(deliveryOrderDetailPK);
								deliveryOrderDetail.setCurrency(salesOrderDetail.getCurrency());
								deliveryOrderDetail.setExchangeRate(salesOrderDetail.getExchangeRate());
								deliveryOrderDetail.setPrice(salesOrderDetail.getPrice());
								deliveryOrderDetail.setDiscountProcent(salesOrderDetail.getDiscountProcent());
								deliveryOrderDetail.setQuantity(salesOrderDetail.getQuantity()-salesOrderDetail.getDeliveryQuantity()+salesOrderDetail.getReturQuantity());
								deliveryOrderDetail.setCostPriceExchangeRate(salesOrderDetail.getCostPriceExchangeRate());
								deliveryOrderDetail.setCostPrice(salesOrderDetail.getCostPrice());
								deliveryOrderDetail.setCostPriceCurrency(salesOrderDetail.getCostPriceCurrency());
								deliveryOrderDetail.setDiscountAmount(salesOrderDetail.getDiscountAmount());
								deliveryOrderDetail.setDescription(salesOrderDetail.getDescription());
								deliveryOrderDetail.setTax(salesOrderDetail.getTax());
								deliveryOrderDetail.setTaxAmount(salesOrderDetail.getTaxAmount());
								deliveryOrderDetail.setUnitConversion(salesOrderDetail.getUnitConversion());
								set.add(deliveryOrderDetail);
							}
						}
						obj.setDeliveryOrderDetails(set);
					}
					//obj.setSalesOrder(salesOrder);
					obj.setDescription(form.getString("description"));
					obj.setNumber(form.getString("number"));
					obj.setOrganization(users.getOrganization());
					obj.setProject(salesOrder.getProject());
					obj.setDepartment(salesOrder.getDepartment());
					obj.setDeliveryDate(form.getCalendar("deliveryDate")!=null?form.getCalendar("deliveryDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
					obj.setCustomerAlias(customersAlias);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					obj.setCreateBy(users);
					obj.setPosted(false);
					obj.setBonKuning(false);
					obj.setStore(false);
					obj.setPoliceNumber(form.getString("policeNumber"));
					obj.setVehicle(form.getString("vehicle"));
					obj.setExpedition(form.getString("expedition"));
					obj.setInvoiceStatus(CommonConstants.OPEN);
					obj.setCreateOn(form.getTimestamp("createOn"));
					// create new deliveryOrder
					if (form.getLong("salesOrderId")!=obj.getSalesOrder().getId()) {
						Set set = new LinkedHashSet();
						Iterator iterator = salesOrder.getSalesOrderDetails().iterator();
						while (iterator.hasNext()) {
							SalesOrderDetail salesOrderDetail = (SalesOrderDetail)iterator.next();
							DeliveryOrderDetail deliveryOrderDetail = new DeliveryOrderDetail();
							deliveryOrderDetail.setItemUnit(salesOrderDetail.getItemUnit());
							DeliveryOrderDetailPK deliveryOrderDetailPK = new DeliveryOrderDetailPK();
							deliveryOrderDetailPK.setItem(salesOrderDetail.getId().getItem());
							deliveryOrderDetailPK.setDeliveryOrder(obj);
							deliveryOrderDetail.setId(deliveryOrderDetailPK);
							deliveryOrderDetail.setCurrency(salesOrderDetail.getCurrency());
							deliveryOrderDetail.setExchangeRate(salesOrderDetail.getExchangeRate());
							deliveryOrderDetail.setPrice(salesOrderDetail.getPrice());
							deliveryOrderDetail.setDiscountProcent(salesOrderDetail.getDiscountProcent());
							deliveryOrderDetail.setQuantity(salesOrderDetail.getQuantity()-salesOrderDetail.getDeliveryQuantity()+salesOrderDetail.getReturQuantity());
							deliveryOrderDetail.setCostPriceCurrency(salesOrderDetail.getCostPriceCurrency());
							deliveryOrderDetail.setCostPriceExchangeRate(salesOrderDetail.getCostPriceExchangeRate());
							deliveryOrderDetail.setCostPrice(salesOrderDetail.getCostPrice());
							deliveryOrderDetail.setDiscountAmount(salesOrderDetail.getDiscountAmount());
							deliveryOrderDetail.setDescription(salesOrderDetail.getDescription());
							deliveryOrderDetail.setTax(salesOrderDetail.getTax());
							deliveryOrderDetail.setTaxAmount(salesOrderDetail.getTaxAmount());
							deliveryOrderDetail.setUnitConversion(salesOrderDetail.getUnitConversion());
							set.add(deliveryOrderDetail);
						}
						obj.setDeliveryOrderDetails(set);
						obj.setSalesOrder(salesOrder);
					}
					// create journal
					Journal journal = new Journal();
					journal.setDeliveryOrder(obj);
					journal.setCurrency(obj.getSalesOrder().getCurrency());
					//journal.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getSalesOrder().getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("deliveryDate")));
					journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getSalesOrder().getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("deliveryDate"))));
					journal.setJournalDate(obj.getDeliveryDate());
					journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
					journal.setOrganization(users.getOrganization());
					journal.setProject(obj.getProject());
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
					journalDetailPK.setChartOfAccount(organizationSetup.getInventoryAccount());
					journalDetailPK.setJournal(journal);
					journalDetail.setId(journalDetailPK);
					journalDetail.setDepartment(obj.getDepartment());
					journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==false?obj.getDeliveryOrderDetailCostPriceAmount():-obj.getDeliveryOrderDetailCostPriceAmount());
					set.add(journalDetail);
					// debit
					JournalDetail journalDetail3 = new JournalDetail();
					JournalDetailPK journalDetailPK3 = new JournalDetailPK();
					journalDetailPK3.setJournal(journal);
					journalDetailPK3.setChartOfAccount(organizationSetup.getCogsAccount());
					journalDetail3.setAmount(organizationSetup.getCogsAccount().isDebit()==true?obj.getDeliveryOrderDetailCostPriceAmount():-obj.getDeliveryOrderDetailCostPriceAmount());
					journalDetail3.setId(journalDetailPK3);
					journalDetail3.setDepartment(obj.getDepartment());
					set.add(journalDetail3);
					journal.setJournalDetails(set);
					obj.setJournal(journal);
				} else {
					List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("itemUnitLst", itemUnitLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
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
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
					List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("locationLst", locationLst);
					// err
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
				SalesOrder salesOrder = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
				obj.setSalesOrder(salesOrder);
				obj.setProject(salesOrder.getProject());
				obj.setDepartment(salesOrder.getDepartment());
				obj.setDescription(form.getString("description"));
				obj.setNumber(form.getString("number"));
				obj.setPoliceNumber(form.getString("policeNumber"));
				obj.setVehicle(form.getString("vehicle"));
				obj.setExpedition(form.getString("expedition"));
				obj.setOrganization(users.getOrganization());
				obj.setDeliveryDate(form.getCalendar("deliveryDate")!=null?form.getCalendar("deliveryDate").getTime():null);
				//obj.setStatus(CommonConstants.OPEN");
				Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setCustomer(customers);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
				obj.setCustomerAlias(customersAlias);
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				if (obj.getInvoice()==null) obj.setInvoiceStatus(CommonConstants.OPEN);
				else obj.setInvoiceStatus(CommonConstants.CLOSE);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("deliveryOrderId"));
				//obj.setPosted(false);
				obj.setBonKuning(false);
				obj.setStore(false);
				// create journal
				//Journal journal = obj.getJournal();
				Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
					.add(Restrictions.eq("DeliveryOrder.Id", new Long(obj.getId()))).uniqueResult();
				journal.setDeliveryOrder(obj);
				journal.setCurrency(obj.getSalesOrder().getCurrency());
				journal.setProject(obj.getProject());
				//journal.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getSalesOrder().getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("deliveryDate")));
				journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getSalesOrder().getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("deliveryDate"))));
				journal.setJournalDate(obj.getDeliveryDate());
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
				// credit
				JournalDetail journalDetail = new JournalDetail();
				JournalDetailPK journalDetailPK = new JournalDetailPK();
				journalDetailPK.setChartOfAccount(organizationSetup.getInventoryAccount());
				journalDetailPK.setJournal(journal);
				journalDetail.setId(journalDetailPK);
				journalDetail.setDepartment(obj.getDepartment());
				journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==false?obj.getDeliveryOrderDetailCostPriceAmount():-obj.getDeliveryOrderDetailCostPriceAmount());
				set.add(journalDetail);
				// debit
				JournalDetail journalDetail3 = new JournalDetail();
				JournalDetailPK journalDetailPK3 = new JournalDetailPK();
				journalDetailPK3.setJournal(journal);
				journalDetailPK3.setChartOfAccount(organizationSetup.getCogsAccount());
				journalDetail3.setAmount(organizationSetup.getCogsAccount().isDebit()==true?obj.getDeliveryOrderDetailCostPriceAmount():-obj.getDeliveryOrderDetailCostPriceAmount());
				journalDetail3.setId(journalDetailPK3);
				journalDetail3.setDepartment(obj.getDepartment());
				set.add(journalDetail3);
				journal.setJournalDetails(set);
				obj.setJournal(journal);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDDELIVERYORDERDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("quantity")>0) {
					DeliveryOrderDetail deliveryOrderDetail = new DeliveryOrderDetail();
					//Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					deliveryOrderDetail.setItemUnit(itemUnit);
					DeliveryOrderDetailPK deliveryOrderDetailPK = new DeliveryOrderDetailPK();
					deliveryOrderDetailPK.setItem(inventory);
					deliveryOrderDetailPK.setDeliveryOrder(obj);
					deliveryOrderDetail.setId(deliveryOrderDetailPK);
					Warehouse warehouse = WarehouseDAO.getInstance().get(form.getLong("warehouseId"));
					deliveryOrderDetail.setWarehouse(warehouse);
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("deliveryOrderDetailCurrencyId"));
					deliveryOrderDetail.setCurrency(currency);
					Currency currency2 = CurrencyDAO.getInstance().get(form.getLong("costPriceCurrencyId"));
					deliveryOrderDetail.setExchangeRate(form.getDouble("exchangeRate"));
					deliveryOrderDetail.setCostPriceCurrency(currency2);
					deliveryOrderDetail.setPrice(form.getDouble("price"));
					deliveryOrderDetail.setQuantity(form.getDouble("quantity"));
					deliveryOrderDetail.setDiscountProcent(form.getDouble("discountProcent"));
					deliveryOrderDetail.setCostPriceExchangeRate(form.getDouble("costPriceExchangeRate"));
					deliveryOrderDetail.setCostPrice(form.getDouble("costPrice"));
					Tax taxDetail = TaxDAO.getInstance().get(form.getLong("taxDetailId"));
					deliveryOrderDetail.setTax(taxDetail);
					deliveryOrderDetail.setTaxAmount(taxDetail!=null?taxDetail.getQuantity():0);
					deliveryOrderDetail.setDiscountAmount(form.getDouble("itemDiscountAmount"));
					deliveryOrderDetail.setDiscountProcent(form.getDouble("itemDiscountProcent"));
					deliveryOrderDetail.setDescription(form.getString("itemDescription"));
					deliveryOrderDetail.setUnitConversion(ItemUnitDAO.getInstance().getConversion(itemUnit, inventory.getItemUnit(), inventory));
					Set set = obj.getDeliveryOrderDetails();
					if (set==null) set = new LinkedHashSet();
					DeliveryOrderDetail removeDeliveryOrderDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						DeliveryOrderDetail deliveryOrderDetail2 = (DeliveryOrderDetail)iterator.next();
						if (form.getLong("itemId")==deliveryOrderDetail2.getId().getItem().getId()) {
							removeDeliveryOrderDetail = deliveryOrderDetail2;
						}
					}
					if (removeDeliveryOrderDetail!=null) {
						set.remove(removeDeliveryOrderDetail);
						set.add(deliveryOrderDetail);
					} else {
						set.add(deliveryOrderDetail);
					}

					obj.setDeliveryOrderDetails(set);
					// netral
					form.setString("itemId", "");
					form.setString("warehouseId", "");
					form.setString("itemCode", "");
					form.setString("price", "");
					form.setString("quantity", "");
					form.setString("itemUnitId", "");
					form.setString("discountProcent", "");
					form.setString("costPrice", "");
					form.setString("costPriceExchangeRate", "");
					form.setString("deliveryOrderDetailCurrencyId", "");
					form.setString("costPriceCurrencyId", "");
				}
				// netral
				form.setString("itemId", "");
				form.setString("warehouseId", "");
				form.setString("itemCode", "");
				form.setString("price", "");
				form.setString("quantity", "");
				form.setString("itemUnitId", "");
				form.setString("discountProcent", "");
				form.setString("costPrice", "");
				form.setString("costPriceExchangeRate", "");
				form.setString("deliveryOrderDetailCurrencyId", "");
				form.setString("costPriceCurrencyId", "");
			}
			// save to session
			httpSession.setAttribute("deliveryOrder", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateDeliveryOrderNumber(session);
					RunningNumberDAO.getInstance().updateJournalNumber(session);
/*					Warehouse fromWarehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class).add(Restrictions.isNull("Location")).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).setMaxResults(1).uniqueResult();
					Warehouse toWarehouse = null;
					if (form.getLong("locationId")>0) {
					    toWarehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class).add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId")))).setMaxResults(1).uniqueResult();
					} else {
					    toWarehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class).add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId")))).setMaxResults(1).uniqueResult();
					}
					InventoryWarehouseDAO.getInstance().updateInventoryWarehouseFromDeliveryOrderStoreLocation(obj, null, fromWarehouse, toWarehouse, session);*/
					DeliveryOrderDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					transaction = session.beginTransaction();
					
					DeliveryOrderDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("deliveryOrder");
				// finish
				ActionForward forward = mapping.findForward("update_status");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?deliveryOrderId="+form.getLong("deliveryOrderId")+"&salesOrderId="+form.getLong("salesOrderId"));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
				if (transaction!=null) transaction.rollback();
				try {
					List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("itemUnitLst", itemUnitLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
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
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
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
			// update PO => deliveryOrderStatus!!!
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
		try {
			DeliveryOrder deliveryOrder = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
			request.setAttribute("deliveryOrder", deliveryOrder);
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
			cell = new Cell(new Phrase("Tanggal : "+deliveryOrder.getFormatedDeliveryDate(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Nama : "+deliveryOrder.getCustomer().getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tlp. "+users.getOrganization().getTelephone()+" - Fax. "+users.getOrganization().getFax(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Alamat : "+deliveryOrder.getCustomer().getAddress()+" "+deliveryOrder.getCustomer().getCity()+" "+deliveryOrder.getCustomer().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Telp. : "+deliveryOrder.getCustomer().getTelephone(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Fax. : "+deliveryOrder.getCustomer().getFax(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);

			document.add(table1);
			
			Table table2 = new Table(3);
			table2.setWidth(100);
			table2.setCellsFitPage(true);
			float[] b = {20,20,60};
			table2.setWidths(b);
			table2.setBorder(Rectangle.NO_BORDER);
			table2.setCellsFitPage(true);
			table2.setBorderWidth(1);
			table2.setPadding(1);
			table2.setSpacing(0);
			
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			table2.addCell(cell);
			cell = new Cell(new Phrase("SURAT JALAN", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			table2.addCell(cell);
			cell = new Cell(new Phrase("NO. : "+deliveryOrder.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			table2.addCell(cell);
			cell = new Cell(new Phrase("No. PO : "+deliveryOrder.getSalesOrder().getPurchaseOrder(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setColspan(1);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("BANYAKNYA",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("NAMA BARANG",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setColspan(2);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			table2.endHeaders();
			
			int j = 0;
			Iterator iterator = deliveryOrder.getDeliveryOrderDetails().iterator();
			while (iterator.hasNext()) {
			    DeliveryOrderDetail detail = (DeliveryOrderDetail)iterator.next();
			    cell = new Cell(new Phrase(detail.getQuantity()+" "+detail.getItemUnit().getSymbol(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("        ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(detail.getId().getItem().getCode()+" "+detail.getId().getItem().getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
				
			}
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("Received By, " + "                 Printed By, : "+"             "+"                 Checked By,                            Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			for (int i=0; i<25; i++) {
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(3);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
			}
			cell = new Cell(new Phrase("(                        )" + "                  ("+users.getUserName()+")                           (                             )                        (                       )", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(3);
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
			DeliveryOrderDAO.getInstance().delete(form.getLong("deliveryOrderId"));
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
	 * Method performList
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performStorePartialList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
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
			Criteria criteria = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromDeliveryDate")!=null)criteria.add(Restrictions.ge("DeliveryDate", new Date(form.getCalendar("fromDeliveryDate").getTime().getTime())));
			if (form.getCalendar("toDeliveryDate")!=null)criteria.add(Restrictions.le("DeliveryDate", new Date(form.getCalendar("toDeliveryDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.add(Restrictions.eq("Store", Boolean.TRUE));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromDeliveryDate")!=null)criteria.add(Restrictions.ge("DeliveryDate", new Date(form.getCalendar("fromDeliveryDate").getTime().getTime())));
			if (form.getCalendar("toDeliveryDate")!=null)criteria.add(Restrictions.le("DeliveryDate", new Date(form.getCalendar("toDeliveryDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.add(Restrictions.eq("Store", Boolean.TRUE));
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("DELIVERYORDER",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("deliveryOrder");
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
	private ActionForward performStoreForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
		      Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class).add(Restrictions.eq("Code", new String(form.getString("itemCode")))).uniqueResult();
		      form.setString("itemId", item.getId());
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			// remove
			DeliveryOrder obj = (DeliveryOrder)httpSession.getAttribute("deliveryOrder");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEDELIVERYORDERDETAIL")) {
				DeliveryOrderDetail removeDeliveryOrderDetail = null;
				Iterator iterator = obj.getDeliveryOrderDetails().iterator();
				while (iterator.hasNext()) {
					DeliveryOrderDetail deliveryOrderDetail = (DeliveryOrderDetail)iterator.next();
					if (form.getLong("itemId") == deliveryOrderDetail.getId().getItem().getId()) {
						removeDeliveryOrderDetail = deliveryOrderDetail;
					}
				}
				if (removeDeliveryOrderDetail!=null) {
					Set set = obj.getDeliveryOrderDetails();
					set.remove(removeDeliveryOrderDetail);
					obj.setDeliveryOrderDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("deliveryOrder", obj);
			}
			// relationships
			List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemUnitLst", itemUnitLst);
			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Store", Boolean.TRUE))
				.addOrder(Order.asc("Company")).list();
			request.setAttribute("customerLst", customerLst);
			/*
			List itemLst = ItemDAO.getInstance().getSession().createCriteria(Item.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("itemLst", itemLst);
			*/
			List warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("warehouseLst", warehouseLst);
			List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("currencyLst", currencyLst);
			List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("locationLst", locationLst);
			if (form.getLong("deliveryOrderId") == 0) {
				List salesOrderLst = SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class)
					.add(Restrictions.eq("Customer.Id", new Long(obj!=null?(obj.getCustomer()!=null?obj.getCustomer().getId():form.getLong("customerId")):form.getLong("customerId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("DeliveryOrderStatus", new String(CommonConstants.CLOSE)))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.createCriteria("Customer")
					.add(Restrictions.eq("Store", Boolean.TRUE))
					.list();
				request.setAttribute("salesOrderLst", salesOrderLst);
				//log.info("C : "+salesOrderLst.size());
				form.setString("deliveryOrderId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getDeliveryOrderNumber());
				form.setCurentCalendar("deliveryDate");
				if (obj!=null) {
					form.setString("deliveryOrderId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setCalendar("deliveryDate",obj.getDeliveryDate());
					form.setString("salesOrderId",obj.getSalesOrder()!=null?obj.getSalesOrder().getId():0);
					form.setString("number",obj.getNumber());
					form.setString("policeNumber",obj.getPoliceNumber());
					form.setString("vehicle",obj.getVehicle());
					form.setString("expedition",obj.getExpedition());
					form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
					form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
					form.setString("departmentId",obj.getDepartment()!=null?obj.getDepartment().getId():0);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
					form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					Set deliveryOrderDetailLst = obj.getDeliveryOrderDetails();
					request.setAttribute("deliveryOrderDetailLst", deliveryOrderDetailLst);
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
					obj = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
					httpSession.setAttribute("deliveryOrder",obj);
				}
				form.setString("deliveryOrderId",obj.getId());
				form.setString("description",obj.getDescription());
				//form.setString("salesOrderId",obj.getSalesOrder()!=null?obj.getSalesOrder().getId():0);
				form.setString("number",obj.getNumber());
				form.setString("policeNumber",obj.getPoliceNumber());
				form.setString("vehicle",obj.getVehicle());
				form.setString("expedition",obj.getExpedition());
				form.setCalendar("deliveryDate",obj.getDeliveryDate());
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
				form.setString("departmentId",obj.getDepartment()!=null?obj.getDepartment().getId():0);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
				form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				List salesOrderLst = SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class)
					//.add(Restrictions.eq("Customer.Id", new Long(obj!=null?(obj.getCustomer()!=null?obj.getCustomer().getId():form.getLong("customerId")):form.getLong("customerId"))))
					//.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					//.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					//.createCriteria("DeliveryOrders", "DeliveryOrder")
					//.add(Restrictions.eq("DeliveryOrder.Id", new Long(obj.getId())))
					.add(Restrictions.eq("Id", new Long(obj.getSalesOrder()!=null?obj.getSalesOrder().getId():0)))
					.list();
				//log.info("salesOrderLst : "+salesOrderLst.size());
				request.setAttribute("salesOrderLst", salesOrderLst);
				form.setString("salesOrderId",obj.getSalesOrder()!=null?obj.getSalesOrder().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set deliveryOrderDetailLst = obj.getDeliveryOrderDetails();
				request.setAttribute("deliveryOrderDetailLst", deliveryOrderDetailLst);
			}
			if (form.getLong("itemId") > 0) {
				//boolean isNewData = true;
				if (obj!=null && obj.getDeliveryOrderDetails()!=null) {
					Iterator iterator = obj.getDeliveryOrderDetails().iterator();
					while (iterator.hasNext()) {
						DeliveryOrderDetail deliveryOrderDetail = (DeliveryOrderDetail)iterator.next();
						if (form.getLong("itemId") == deliveryOrderDetail.getId().getItem().getId()) {
							//isNewData = false;
							form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), deliveryOrderDetail.getPrice()));
							form.setString("quantity", deliveryOrderDetail.getQuantity());
							form.setString("itemCode", deliveryOrderDetail.getId().getItem().getCode());
							form.setString("costPrice", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(),deliveryOrderDetail.getCostPrice()));
							form.setString("costPriceExchangeRate", deliveryOrderDetail.getCostPriceExchangeRate());
							form.setString("discountProcent", deliveryOrderDetail.getDiscountProcent());
							form.setString("exchangeRate", deliveryOrderDetail.getExchangeRate());
							form.setString("warehouseId", deliveryOrderDetail.getWarehouse()!=null?deliveryOrderDetail.getWarehouse().getId():0);
							form.setString("itemUnitId", deliveryOrderDetail.getItemUnit()!=null?deliveryOrderDetail.getItemUnit().getId():0);
							form.setString("deliveryOrderDetailCurrencyId", deliveryOrderDetail.getCurrency()!=null?deliveryOrderDetail.getCurrency().getId():0);
							form.setString("costPriceCurrencyId", deliveryOrderDetail.getCostPriceCurrency()!=null?deliveryOrderDetail.getCostPriceCurrency().getId():0);
							form.setString("taxDetailId", deliveryOrderDetail.getTax()!=null?deliveryOrderDetail.getTax().getId():0);
							form.setString("taxDetailAmount", deliveryOrderDetail.getTaxAmount());
							form.setString("itemDescription", deliveryOrderDetail.getDescription());
							form.setString("itemDiscountAmount", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), deliveryOrderDetail.getDiscountAmount()));
							form.setString("itemDiscountProcent", deliveryOrderDetail.getDiscountProcent());
						}
					}
				}
			}
			request.setAttribute("deliveryOrderDetailAmount", obj!=null?obj.getFormatedDeliveryOrderDetailAmount():"-");
			request.setAttribute("amountTax", obj!=null?obj.getFormatedAmountTax():"-");
			request.setAttribute("amountDiscount", obj!=null?obj.getFormatedAmountDiscount():"-");
			request.setAttribute("amountAfterDiscount", obj!=null?obj.getFormatedAmountAfterDiscount():"-");
			request.setAttribute("amountAfterTaxAndDiscount", obj!=null?obj.getFormatedAmountAfterTaxAndDiscount():"-");
		}catch(Exception ex) {
			try {
				List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("itemUnitLst", itemUnitLst);
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Store", Boolean.TRUE))
					.addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
				/*
				List itemLst = ItemDAO.getInstance().getSession().createCriteria(Item.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("itemLst", itemLst);
				*/
				List warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("warehouseLst", warehouseLst);
				List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("currencyLst", currencyLst);
				List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("taxLst", taxLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
			}catch(Exception exx) {
			}
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
	private ActionForward performStoreSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = DeliveryOrderDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("deliveryOrder");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			DeliveryOrder obj = (DeliveryOrder)httpSession.getAttribute("deliveryOrder");
			if (form.getLong("deliveryOrderId") == 0) {
				obj = (DeliveryOrder)DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (DeliveryOrder)httpSession.getAttribute("deliveryOrder");
					SalesOrder salesOrder = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
					if (obj==null) {
						obj = new DeliveryOrder();
						obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
						obj.setSalesOrder(salesOrder);
						obj.setProject(salesOrder.getProject());
						Set set = obj.getDeliveryOrderDetails();
						if (set==null) set = new LinkedHashSet();
						Iterator iterator = salesOrder.getSalesOrderDetails().iterator();
						while (iterator.hasNext()) {
							SalesOrderDetail salesOrderDetail = (SalesOrderDetail)iterator.next();
							if (salesOrderDetail.getQuantity()>(salesOrderDetail.getDeliveryQuantity()-salesOrderDetail.getReturQuantity())) {
								DeliveryOrderDetail deliveryOrderDetail = new DeliveryOrderDetail();
								deliveryOrderDetail.setItemUnit(salesOrderDetail.getItemUnit());
								DeliveryOrderDetailPK deliveryOrderDetailPK = new DeliveryOrderDetailPK();
								deliveryOrderDetailPK.setItem(salesOrderDetail.getId().getItem());
								deliveryOrderDetailPK.setDeliveryOrder(obj);
								deliveryOrderDetail.setId(deliveryOrderDetailPK);
								deliveryOrderDetail.setCurrency(salesOrderDetail.getCurrency());
								deliveryOrderDetail.setExchangeRate(salesOrderDetail.getExchangeRate());
								deliveryOrderDetail.setPrice(salesOrderDetail.getPrice());
								deliveryOrderDetail.setDiscountProcent(salesOrderDetail.getDiscountProcent());
								deliveryOrderDetail.setQuantity(salesOrderDetail.getQuantity()-salesOrderDetail.getDeliveryQuantity()+salesOrderDetail.getReturQuantity());
								deliveryOrderDetail.setCostPriceCurrency(salesOrderDetail.getCostPriceCurrency());
								deliveryOrderDetail.setCostPriceExchangeRate(salesOrderDetail.getCostPriceExchangeRate());
								deliveryOrderDetail.setCostPrice(salesOrderDetail.getCostPrice());
								deliveryOrderDetail.setDiscountAmount(salesOrderDetail.getDiscountAmount());
								deliveryOrderDetail.setDescription(salesOrderDetail.getDescription());
								deliveryOrderDetail.setTax(salesOrderDetail.getTax());
								deliveryOrderDetail.setTaxAmount(salesOrderDetail.getTaxAmount());
								deliveryOrderDetail.setUnitConversion(salesOrderDetail.getUnitConversion());
								set.add(deliveryOrderDetail);
							}
						}
						obj.setDeliveryOrderDetails(set);
					}
					//obj.setSalesOrder(salesOrder);
					obj.setDescription(form.getString("description"));
					obj.setNumber(form.getString("number"));
					obj.setPoliceNumber(form.getString("policeNumber"));
					obj.setVehicle(form.getString("vehicle"));
					obj.setExpedition(form.getString("expedition"));
					obj.setOrganization(users.getOrganization());
					obj.setDeliveryDate(form.getCalendar("deliveryDate")!=null?form.getCalendar("deliveryDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
					obj.setCustomerAlias(customersAlias);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					obj.setCreateBy(users);
					obj.setPosted(false);
					obj.setBonKuning(false);
					obj.setStore(true);
					obj.setInvoiceStatus(CommonConstants.OPEN);
					obj.setCreateOn(form.getTimestamp("createOn"));
					// create new deliveryOrder
					if (form.getLong("salesOrderId")!=obj.getSalesOrder().getId()) {
						Set set = new LinkedHashSet();
						Iterator iterator = salesOrder.getSalesOrderDetails().iterator();
						while (iterator.hasNext()) {
							SalesOrderDetail salesOrderDetail = (SalesOrderDetail)iterator.next();
							DeliveryOrderDetail deliveryOrderDetail = new DeliveryOrderDetail();
							deliveryOrderDetail.setItemUnit(salesOrderDetail.getItemUnit());
							DeliveryOrderDetailPK deliveryOrderDetailPK = new DeliveryOrderDetailPK();
							deliveryOrderDetailPK.setItem(salesOrderDetail.getId().getItem());
							deliveryOrderDetailPK.setDeliveryOrder(obj);
							deliveryOrderDetail.setId(deliveryOrderDetailPK);
							deliveryOrderDetail.setCurrency(salesOrderDetail.getCurrency());
							deliveryOrderDetail.setExchangeRate(salesOrderDetail.getExchangeRate());
							deliveryOrderDetail.setPrice(salesOrderDetail.getPrice());
							deliveryOrderDetail.setDiscountProcent(salesOrderDetail.getDiscountProcent());
							deliveryOrderDetail.setQuantity(salesOrderDetail.getQuantity()-salesOrderDetail.getDeliveryQuantity()+salesOrderDetail.getReturQuantity());
							deliveryOrderDetail.setCostPriceCurrency(salesOrderDetail.getCostPriceCurrency());
							deliveryOrderDetail.setCostPriceExchangeRate(salesOrderDetail.getCostPriceExchangeRate());
							deliveryOrderDetail.setCostPrice(salesOrderDetail.getCostPrice());
							deliveryOrderDetail.setDiscountAmount(salesOrderDetail.getDiscountAmount());
							deliveryOrderDetail.setDescription(salesOrderDetail.getDescription());
							deliveryOrderDetail.setTax(salesOrderDetail.getTax());
							deliveryOrderDetail.setTaxAmount(salesOrderDetail.getTaxAmount());
							deliveryOrderDetail.setUnitConversion(salesOrderDetail.getUnitConversion());
							set.add(deliveryOrderDetail);
						}
						obj.setDeliveryOrderDetails(set);
						obj.setSalesOrder(salesOrder);
					}
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("name")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
				SalesOrder salesOrder = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
				obj.setSalesOrder(salesOrder);
				obj.setProject(salesOrder.getProject());
				obj.setDescription(form.getString("description"));
				obj.setNumber(form.getString("number"));
				obj.setPoliceNumber(form.getString("policeNumber"));
				obj.setVehicle(form.getString("vehicle"));
				obj.setExpedition(form.getString("expedition"));
				obj.setOrganization(users.getOrganization());
				obj.setDeliveryDate(form.getCalendar("deliveryDate")!=null?form.getCalendar("deliveryDate").getTime():null);
				//obj.setStatus(CommonConstants.OPEN");
				Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setCustomer(customers);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
				obj.setCustomerAlias(customersAlias);
				obj.setBonKuning(false);
				obj.setStore(true);
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				if (obj.getInvoice()==null) obj.setInvoiceStatus(CommonConstants.OPEN);
				else obj.setInvoiceStatus(CommonConstants.CLOSE);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("deliveryOrderId"));
				//obj.setPosted(false);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDDELIVERYORDERDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("quantity")>0) {
					DeliveryOrderDetail deliveryOrderDetail = new DeliveryOrderDetail();
					//Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					deliveryOrderDetail.setItemUnit(itemUnit);
					DeliveryOrderDetailPK deliveryOrderDetailPK = new DeliveryOrderDetailPK();
					deliveryOrderDetailPK.setItem(inventory);
					deliveryOrderDetailPK.setDeliveryOrder(obj);
					deliveryOrderDetail.setId(deliveryOrderDetailPK);
					Warehouse warehouse = WarehouseDAO.getInstance().get(form.getLong("warehouseId"));
					deliveryOrderDetail.setWarehouse(warehouse);
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("deliveryOrderDetailCurrencyId"));
					deliveryOrderDetail.setCurrency(currency);
					Currency currency2 = CurrencyDAO.getInstance().get(form.getLong("costPriceCurrencyId"));
					deliveryOrderDetail.setCostPriceCurrency(currency2);
					deliveryOrderDetail.setExchangeRate(form.getDouble("exchangeRate"));
					deliveryOrderDetail.setPrice(form.getDouble("price"));
					deliveryOrderDetail.setQuantity(form.getDouble("quantity"));
					deliveryOrderDetail.setDiscountProcent(form.getDouble("discountProcent"));
					deliveryOrderDetail.setCostPriceExchangeRate(form.getDouble("costPriceExchangeRate"));
					deliveryOrderDetail.setCostPrice(form.getDouble("costPrice"));
					Tax taxDetail = TaxDAO.getInstance().get(form.getLong("taxDetailId"));
					deliveryOrderDetail.setTax(taxDetail);
					deliveryOrderDetail.setTaxAmount(taxDetail!=null?taxDetail.getQuantity():0);
					deliveryOrderDetail.setDiscountAmount(form.getDouble("itemDiscountAmount"));
					deliveryOrderDetail.setDiscountProcent(form.getDouble("itemDiscountProcent"));
					deliveryOrderDetail.setDescription(form.getString("itemDescription"));
					deliveryOrderDetail.setUnitConversion(ItemUnitDAO.getInstance().getConversion(itemUnit, inventory.getItemUnit(), inventory));
					Set set = obj.getDeliveryOrderDetails();
					if (set==null) set = new LinkedHashSet();
					DeliveryOrderDetail removeDeliveryOrderDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						DeliveryOrderDetail deliveryOrderDetail2 = (DeliveryOrderDetail)iterator.next();
						if (form.getLong("itemId")==deliveryOrderDetail2.getId().getItem().getId()) {
							removeDeliveryOrderDetail = deliveryOrderDetail2;
						}
					}
					if (removeDeliveryOrderDetail!=null) {
						set.remove(removeDeliveryOrderDetail);
						set.add(deliveryOrderDetail);
					} else {
						set.add(deliveryOrderDetail);
					}

					obj.setDeliveryOrderDetails(set);
					// netral
					form.setString("itemId", "");
					form.setString("warehouseId", "");
					form.setString("itemCode", "");
					form.setString("price", "");
					form.setString("quantity", "");
					form.setString("itemUnitId", "");
					form.setString("discountProcent", "");
					form.setString("costPrice", "");
					form.setString("costPriceExchangeRate", "");
					form.setString("deliveryOrderDetailCurrencyId", "");
					form.setString("costPriceCurrencyId", "");
				}
				// netral
				form.setString("itemId", "");
				form.setString("warehouseId", "");
				form.setString("itemCode", "");
				form.setString("price", "");
				form.setString("quantity", "");
				form.setString("itemUnitId", "");
				form.setString("discountProcent", "");
				form.setString("costPrice", "");
				form.setString("costPriceExchangeRate", "");
				form.setString("deliveryOrderDetailCurrencyId", "");
				form.setString("costPriceCurrencyId", "");
			}
			// save to session
			httpSession.setAttribute("deliveryOrder", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateDeliveryOrderNumber(session);
					DeliveryOrderDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					transaction = session.beginTransaction();
					DeliveryOrderDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("deliveryOrder");
				// finish
				ActionForward forward = mapping.findForward("update_status");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?deliveryOrderId="+form.getLong("deliveryOrderId")+"&salesOrderId="+form.getLong("salesOrderId"));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
				if (transaction!=null) transaction.rollback();
				try {
					List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("itemUnitLst", itemUnitLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Store", Boolean.TRUE))
						.addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
					List warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("warehouseLst", warehouseLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
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
	 * Method performUpdateStatus
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performStoreUpdateStatus(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// update PO => deliveryOrderStatus!!!
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
	 * Method performDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performStoreDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			DeliveryOrder deliveryOrder = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
			request.setAttribute("deliveryOrder", deliveryOrder);
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
	 * Method performDelete
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performStoreDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			DeliveryOrderDAO.getInstance().delete(form.getLong("deliveryOrderId"));
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
	 * Method performList
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performBonKuningPartialList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
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
			Criteria criteria = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromDeliveryDate")!=null)criteria.add(Restrictions.ge("DeliveryDate", new Date(form.getCalendar("fromDeliveryDate").getTime().getTime())));
			if (form.getCalendar("toDeliveryDate")!=null)criteria.add(Restrictions.le("DeliveryDate", new Date(form.getCalendar("toDeliveryDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			criteria.add(Restrictions.eq("BonKuning", Boolean.TRUE));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromDeliveryDate")!=null)criteria.add(Restrictions.ge("DeliveryDate", new Date(form.getCalendar("fromDeliveryDate").getTime().getTime())));
			if (form.getCalendar("toDeliveryDate")!=null)criteria.add(Restrictions.le("DeliveryDate", new Date(form.getCalendar("toDeliveryDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.add(Restrictions.eq("BonKuning", Boolean.TRUE));
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("DELIVERYORDER",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("deliveryOrder");
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
	private ActionForward performBonKuningForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
		      Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class).add(Restrictions.eq("Code", new String(form.getString("itemCode")))).uniqueResult();
		      form.setString("itemId", item.getId());
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			// remove
			DeliveryOrder obj = (DeliveryOrder)httpSession.getAttribute("deliveryOrder");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEDELIVERYORDERDETAIL")) {
				DeliveryOrderDetail removeDeliveryOrderDetail = null;
				Iterator iterator = obj.getDeliveryOrderDetails().iterator();
				while (iterator.hasNext()) {
					DeliveryOrderDetail deliveryOrderDetail = (DeliveryOrderDetail)iterator.next();
					if (form.getLong("itemId") == deliveryOrderDetail.getId().getItem().getId()) {
						removeDeliveryOrderDetail = deliveryOrderDetail;
					}
				}
				if (removeDeliveryOrderDetail!=null) {
					Set set = obj.getDeliveryOrderDetails();
					set.remove(removeDeliveryOrderDetail);
					obj.setDeliveryOrderDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("deliveryOrder", obj);
			}
			// relationships
			List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemUnitLst", itemUnitLst);
			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerLst", customerLst);
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
			if (form.getLong("deliveryOrderId") == 0) {
				List salesOrderLst = SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class)
					.add(Restrictions.eq("Customer.Id", new Long(obj!=null?(obj.getCustomer()!=null?obj.getCustomer().getId():form.getLong("customerId")):form.getLong("customerId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("DeliveryOrderStatus", new String(CommonConstants.CLOSE)))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
				request.setAttribute("salesOrderLst", salesOrderLst);
				//log.info("C : "+salesOrderLst.size());
				form.setString("deliveryOrderId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getDeliveryOrderNumber());
				form.setCurentCalendar("deliveryDate");
				if (obj!=null) {
					form.setString("deliveryOrderId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setCalendar("deliveryDate",obj.getDeliveryDate());
					form.setString("salesOrderId",obj.getSalesOrder()!=null?obj.getSalesOrder().getId():0);
					form.setString("number",obj.getNumber());
					form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					Set deliveryOrderDetailLst = obj.getDeliveryOrderDetails();
					request.setAttribute("deliveryOrderDetailLst", deliveryOrderDetailLst);
					form.setString("discount1", obj.getDiscount1());
					form.setString("discount2", obj.getDiscount2());
					form.setString("discount3", obj.getDiscount3());
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
					httpSession.setAttribute("deliveryOrder",obj);
				}
				form.setString("deliveryOrderId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("salesOrderId",obj.getSalesOrder()!=null?obj.getSalesOrder().getId():0);
				form.setString("number",obj.getNumber());
				form.setCalendar("deliveryDate",obj.getDeliveryDate());
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				List salesOrderLst = SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class)
					.add(Restrictions.eq("Customer.Id", new Long(obj!=null?(obj.getCustomer()!=null?obj.getCustomer().getId():form.getLong("customerId")):form.getLong("customerId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.createCriteria("DeliveryOrders", "DeliveryOrder")
					.add(Restrictions.eq("DeliveryOrder.Id", new Long(obj.getId())))
					.list();
				request.setAttribute("salesOrderLst", salesOrderLst);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set deliveryOrderDetailLst = obj.getDeliveryOrderDetails();
				request.setAttribute("deliveryOrderDetailLst", deliveryOrderDetailLst);
				form.setString("discount1", obj.getDiscount1());
				form.setString("discount2", obj.getDiscount2());
				form.setString("discount3", obj.getDiscount3());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
			}
			//log.info("A");
			if (form.getLong("itemId") > 0) {
				boolean isNewData = true;
				//log.info("B");
				if (obj!=null && obj.getDeliveryOrderDetails()!=null) {
					Iterator iterator = obj.getDeliveryOrderDetails().iterator();
					while (iterator.hasNext()) {
						DeliveryOrderDetail deliveryOrderDetail = (DeliveryOrderDetail)iterator.next();
						if (form.getLong("itemId") == deliveryOrderDetail.getId().getItem().getId()) {
							isNewData = false;
							form.setString("price", Formater.getFormatedOutputForm(deliveryOrderDetail.getPrice()));
							form.setString("quantity", deliveryOrderDetail.getQuantity());
							form.setString("costPrice", deliveryOrderDetail.getCostPrice());
							form.setString("itemCode", deliveryOrderDetail.getId().getItem().getCode());
							form.setString("costPriceExchangeRate", deliveryOrderDetail.getCostPriceExchangeRate());
							form.setString("discountProcent", deliveryOrderDetail.getDiscountProcent());
							form.setString("exchangeRate", deliveryOrderDetail.getExchangeRate());
							form.setString("itemUnitId", deliveryOrderDetail.getItemUnit()!=null?deliveryOrderDetail.getItemUnit().getId():0);
							form.setString("deliveryOrderDetailCurrencyId", deliveryOrderDetail.getCurrency()!=null?deliveryOrderDetail.getCurrency().getId():0);
							form.setString("costPriceCurrencyId", deliveryOrderDetail.getCostPriceCurrency()!=null?deliveryOrderDetail.getCostPriceCurrency().getId():0);
						}
					}
				}
				//log.info("C");
				if (isNewData) {
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					if (inventory!=null) {
					  //log.info("X");
						Customers customers = null;
						if (customers==null || (obj!=null && obj.getCustomer()==null)) customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
						String sql = "select itemPrice from ItemPrice itemPrice where itemPrice.Id.Item.Id = "+form.getLong("itemId");
						if (customers!=null && customers.getItemPriceCategory()!=null) sql = sql + " and itemPrice.Id.ItemPriceCategory.Id = "+customers.getItemPriceCategory().getId();
						else sql = sql + " and itemPrice.Default = 'Y'";
						ItemPrice itemPrice = (ItemPrice)ItemPriceDAO.getInstance().getSession().createQuery(sql).uniqueResult();
						if (itemPrice!=null) {
							form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), itemPrice.getPrice()));
							form.setString("deliveryOrderDetailCurrencyId", itemPrice.getCurrency()!=null?itemPrice.getCurrency().getId():0);
						} else {
							sql = "select itemPrice from ItemPrice itemPrice where itemPrice.Id.Item.Id = "+form.getLong("itemId") + " and itemPrice.Default = 'Y'";
							itemPrice = (ItemPrice)ItemPriceDAO.getInstance().getSession().createQuery(sql).uniqueResult();
							if (itemPrice!=null) {
								form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), itemPrice.getPrice()));
								form.setString("deliveryOrderDetailCurrencyId", itemPrice.getCurrency()!=null?itemPrice.getCurrency().getId():0);
							}
						}
						form.setString("itemCode", inventory.getCode());
						form.setString("itemUnitId", inventory.getItemUnit()!=null?inventory.getItemUnit().getId():0);
						form.setString("costPrice", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), inventory.getCostPrice()));
						form.setString("costPriceCurrencyId", inventory.getCurrency()!=null?inventory.getCurrency().getId():0);
					}
				}
			}
			request.setAttribute("deliveryOrderDetailAmount", obj!=null?obj.getFormatedDeliveryOrderDetailAmount():"-");
			//request.setAttribute("amountTax", obj!=null?obj.getFormatedAmountTax():"-");
			request.setAttribute("amountDiscount1", obj!=null?obj.getFormatedDiscountAmount1():"-");
			request.setAttribute("amountDiscount2", obj!=null?obj.getFormatedDiscountAmount2():"-");
			request.setAttribute("amountDiscount3", obj!=null?obj.getFormatedDiscountAmount3():"-");
			request.setAttribute("amountAfterDiscount1", obj!=null?obj.getFormatedAmountAfterDiscount1():"-");
			request.setAttribute("amountAfterDiscount1Discount2", obj!=null?obj.getFormatedAmountAfterDiscount1Discount2():"-");
			request.setAttribute("amountAfterTaxAndDiscount", obj!=null?obj.getFormatedAmountAfterTaxAndDiscount():"-");
		}catch(Exception ex) {
			try {
				List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("itemUnitLst", itemUnitLst);
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				/*
				List itemLst = ItemDAO.getInstance().getSession().createCriteria(Item.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("itemLst", itemLst);
				*/
				List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("currencyLst", currencyLst);
				List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("taxLst", taxLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
			}catch(Exception exx) {
			}
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
	private ActionForward performBonKuningSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = DeliveryOrderDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("deliveryOrder");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			DeliveryOrder obj = (DeliveryOrder)httpSession.getAttribute("deliveryOrder");
			if (form.getLong("deliveryOrderId") == 0) {
				obj = (DeliveryOrder)DeliveryOrderDAO.getInstance().getSession().createCriteria(DeliveryOrder.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
				  //log.info("A");
					obj = (DeliveryOrder)httpSession.getAttribute("deliveryOrder");
					if (obj==null) obj = new DeliveryOrder();
					obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
					obj.setDescription(form.getString("description"));
					obj.setNumber(form.getString("number"));
					obj.setOrganization(users.getOrganization());
					obj.setDeliveryDate(form.getCalendar("deliveryDate")!=null?form.getCalendar("deliveryDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					obj.setCreateBy(users);
					obj.setPosted(false);
					obj.setBonKuning(true);
					obj.setStore(false);
					obj.setDiscount1(form.getDouble("discount1"));
					obj.setDiscount2(form.getDouble("discount2"));
					obj.setDiscount3(form.getDouble("discount3"));
					obj.setInvoiceStatus(CommonConstants.OPEN);
					obj.setCreateOn(form.getTimestamp("createOn"));
					// create journal
					Journal journal = new Journal();
					journal.setDeliveryOrder(obj);
					journal.setCurrency(obj.getCurrency());
					//journal.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("deliveryDate")));
					journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("deliveryDate"))));
					journal.setJournalDate(obj.getDeliveryDate());
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
					journalDetailPK.setChartOfAccount(organizationSetup.getInventoryAccount());
					journalDetailPK.setJournal(journal);
					journalDetail.setId(journalDetailPK);
					journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==false?obj.getDeliveryOrderDetailCostPriceAmount():-obj.getDeliveryOrderDetailCostPriceAmount());
					set.add(journalDetail);
					// debit
					JournalDetail journalDetail3 = new JournalDetail();
					JournalDetailPK journalDetailPK3 = new JournalDetailPK();
					journalDetailPK3.setJournal(journal);
					journalDetailPK3.setChartOfAccount(organizationSetup.getCogsAccount());
					journalDetail3.setAmount(organizationSetup.getCogsAccount().isDebit()==true?obj.getDeliveryOrderDetailCostPriceAmount():-obj.getDeliveryOrderDetailCostPriceAmount());
					journalDetail3.setId(journalDetailPK3);
					set.add(journalDetail3);
					journal.setJournalDetails(set);
					obj.setJournal(journal);
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("name")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = DeliveryOrderDAO.getInstance().load(form.getLong("deliveryOrderId"));
				//SalesOrder salesOrder = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
				//obj.setSalesOrder(salesOrder);
				obj.setDescription(form.getString("description"));
				obj.setNumber(form.getString("number"));
				obj.setOrganization(users.getOrganization());
				obj.setDeliveryDate(form.getCalendar("deliveryDate")!=null?form.getCalendar("deliveryDate").getTime():null);
				//obj.setStatus(CommonConstants.OPEN");
				Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setCustomer(customers);
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				obj.setDiscount1(form.getDouble("discount1"));
				obj.setDiscount2(form.getDouble("discount2"));
				obj.setDiscount3(form.getDouble("discount3"));
				if (obj.getInvoice()==null) obj.setInvoiceStatus(CommonConstants.OPEN);
				else obj.setInvoiceStatus(CommonConstants.CLOSE);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
				obj.setId(form.getLong("deliveryOrderId"));
				//obj.setPosted(false);
				obj.setBonKuning(true);
				obj.setStore(false);
				// create journal
				Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
					.add(Restrictions.eq("DeliveryOrder.Id", new Long(obj.getId()))).uniqueResult();
				journal.setDeliveryOrder(obj);
				journal.setCurrency(obj.getCurrency());
				journal.setJournalDate(obj.getDeliveryDate());
				//journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
				journal.setOrganization(users.getOrganization());
				//journal.setPosted(false);
				journal.setReference(form.getString("reference"));
				journal.setCustomer(customers);
				journal.setCreateBy(createBy);
				journal.setCreateOn(form.getTimestamp("createOn"));
				journal.setChangeBy(users);
				journal.setChangeOn(form.getTimestamp("changeOn"));
				journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("deliveryDate"))));
				// journal detail
				journal.getJournalDetails().removeAll(journal.getJournalDetails());
				Set set = journal.getJournalDetails();
				// credit
				JournalDetail journalDetail = new JournalDetail();
				JournalDetailPK journalDetailPK = new JournalDetailPK();
				journalDetailPK.setChartOfAccount(organizationSetup.getInventoryAccount());
				journalDetailPK.setJournal(journal);
				journalDetail.setId(journalDetailPK);
				journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==false?obj.getDeliveryOrderDetailCostPriceAmount():-obj.getDeliveryOrderDetailCostPriceAmount());
				set.add(journalDetail);
				// debit
				JournalDetail journalDetail3 = new JournalDetail();
				JournalDetailPK journalDetailPK3 = new JournalDetailPK();
				journalDetailPK3.setJournal(journal);
				journalDetailPK3.setChartOfAccount(organizationSetup.getCogsAccount());
				journalDetail3.setAmount(organizationSetup.getCogsAccount().isDebit()==true?obj.getDeliveryOrderDetailCostPriceAmount():-obj.getDeliveryOrderDetailCostPriceAmount());
				journalDetail3.setId(journalDetailPK3);
				set.add(journalDetail3);
				journal.setJournalDetails(set);
				obj.setJournal(journal);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDDELIVERYORDERDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("quantity")>0) {
					DeliveryOrderDetail deliveryOrderDetail = new DeliveryOrderDetail();
					//log.info("itemid = "+form.getLong("itemId"));
					Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					deliveryOrderDetail.setItemUnit(itemUnit);
					DeliveryOrderDetailPK deliveryOrderDetailPK = new DeliveryOrderDetailPK();
					deliveryOrderDetailPK.setItem(item);
					deliveryOrderDetailPK.setDeliveryOrder(obj);
					deliveryOrderDetail.setId(deliveryOrderDetailPK);
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("deliveryOrderDetailCurrencyId"));
					Currency currency2 = CurrencyDAO.getInstance().get(form.getLong("costPriceCurrencyId"));
					deliveryOrderDetail.setCurrency(currency);
					deliveryOrderDetail.setCostPriceCurrency(currency2);
					deliveryOrderDetail.setExchangeRate(1);
					deliveryOrderDetail.setPrice(form.getDouble("price"));
					deliveryOrderDetail.setQuantity(form.getDouble("quantity"));
					deliveryOrderDetail.setDiscountProcent(form.getDouble("discountProcent"));
					deliveryOrderDetail.setCostPriceExchangeRate(Formater.getFormatedOutputResult(organizationSetup.getNumberOfDigit(), CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency2, currency, organizationSetup, form.getCalendar("deliveryDate"))));
					deliveryOrderDetail.setCostPrice(form.getDouble("costPrice"));
					Tax taxDetail = TaxDAO.getInstance().get(form.getLong("taxDetailId"));
					deliveryOrderDetail.setTax(taxDetail);
					deliveryOrderDetail.setTaxAmount(taxDetail!=null?taxDetail.getQuantity():0);
					deliveryOrderDetail.setDiscountAmount(form.getDouble("itemDiscountAmount"));
					deliveryOrderDetail.setDiscountProcent(form.getDouble("itemDiscountProcent"));
					deliveryOrderDetail.setDescription(form.getString("itemDescription"));
					Set set = obj.getDeliveryOrderDetails();
					if (set==null) set = new LinkedHashSet();
					DeliveryOrderDetail removeDeliveryOrderDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						DeliveryOrderDetail deliveryOrderDetail2 = (DeliveryOrderDetail)iterator.next();
						if (form.getLong("itemId")==deliveryOrderDetail2.getId().getItem().getId()) {
							removeDeliveryOrderDetail = deliveryOrderDetail2;
						}
					}
					if (removeDeliveryOrderDetail!=null) {
						set.remove(removeDeliveryOrderDetail);
						set.add(deliveryOrderDetail);
					} else {
						set.add(deliveryOrderDetail);
					}

					obj.setDeliveryOrderDetails(set);
					// netral
					form.setString("itemId", "");
					form.setString("itemCode", "");
					form.setString("price", "");
					form.setString("quantity", "");
					form.setString("itemUnitId", "");
					form.setString("discountProcent", "");
					form.setString("costPrice", "");
					form.setString("costPriceExchangeRate", "");
					form.setString("deliveryOrderDetailCurrencyId", "");
					form.setString("costPriceCurrencyId", "");
				}
				// netral
				form.setString("itemId", "");
				form.setString("itemCode", "");
				form.setString("price", "");
				form.setString("quantity", "");
				form.setString("itemUnitId", "");
				form.setString("discountProcent", "");
				form.setString("costPrice", "");
				form.setString("costPriceExchangeRate", "");
				form.setString("deliveryOrderDetailCurrencyId", "");
				form.setString("costPriceCurrencyId", "");
			}
			// save to session
			httpSession.setAttribute("deliveryOrder", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateDeliveryOrderNumber(session);
					RunningNumberDAO.getInstance().updateJournalNumber(session);
/*					Warehouse warehouse = null;
					if (form.getLong("locationId")>0) {
					    warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class).add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId")))).setMaxResults(1).uniqueResult();
					} else {
					    warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class).add(Restrictions.isNull("Location")).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).setMaxResults(1).uniqueResult();
					}
					InventoryWarehouseDAO.getInstance().updateInventoryWarehouseFromDeliveryOrder(obj, null, warehouse, session);*/
					DeliveryOrderDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					transaction = session.beginTransaction();
					DeliveryOrderDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("deliveryOrder");
				// finish
				ActionForward forward = mapping.findForward("update_status");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?deliveryOrderId="+form.getLong("deliveryOrderId")+"&salesOrderId="+form.getLong("salesOrderId"));
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
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					/*
					List itemLst = ItemDAO.getInstance().getSession().createCriteria(Item.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("itemLst", itemLst);
					*/
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
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
	 * Method performUpdateStatus
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performBonKuningUpdateStatus(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// update PO => deliveryOrderStatus!!!
		  /*
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
			*/
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
	 * Method performDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performBonKuningDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			DeliveryOrder deliveryOrder = DeliveryOrderDAO.getInstance().get(form.getLong("deliveryOrderId"));
			request.setAttribute("deliveryOrder", deliveryOrder);
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
	 * Method performDelete
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performBonKuningDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		DeliveryOrderForm form = (DeliveryOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			DeliveryOrderDAO.getInstance().delete(form.getLong("deliveryOrderId"));
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
		errors.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.global",ex.getMessage()));
		saveErrors(request,errors);
	}

}