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
import org.hibernate.Hibernate;
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
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.Department;
import com.mpe.financial.model.DiscountReference;
import com.mpe.financial.model.Inventory;
import com.mpe.financial.model.Item;
import com.mpe.financial.model.ItemPrice;
import com.mpe.financial.model.ItemUnit;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.PrepaymentToVendor;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.SalesOrder;
import com.mpe.financial.model.SalesOrderDetail;
import com.mpe.financial.model.SalesOrderDetailPK;
import com.mpe.financial.model.Salesman;
import com.mpe.financial.model.Tax;
import com.mpe.financial.model.TermOfPayment;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.DiscountReferenceDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemPriceDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.PrepaymentToVendorDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.SalesOrderDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.SalesmanDAO;
import com.mpe.financial.model.dao.TaxDAO;
import com.mpe.financial.model.dao.TermOfPaymentDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.struts.form.SalesOrderForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class SalesOrderAction extends Action {
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
		SalesOrderForm purchaseOrderForm = (SalesOrderForm) form;
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
					} else if ("PRINTED".equalsIgnoreCase(action)) { 
						forward = performPrinted(mapping, form, request, response);
					} else if ("PROJECTLIST".equalsIgnoreCase(action)) {
						forward = performProjectPartialList(mapping, form, request, response);
					} else if ("PROJECTFORM".equalsIgnoreCase(action)) {
						forward = performProjectForm(mapping, form, request, response);
					} else if ("PROJECTSAVE".equalsIgnoreCase(action)) {
						if (purchaseOrderForm.getString("subaction")!=null && purchaseOrderForm.getString("subaction").equalsIgnoreCase("refresh")) {
							forward = performProjectForm(mapping, form, request, response);
						} else {
							forward = performProjectSave(mapping, form, request, response);
						}
					} else if ("PROJECTDETAIL".equalsIgnoreCase(action)) { 
						forward = performProjectDetail(mapping, form, request, response);
					} else if ("PROJECTDELETE".equalsIgnoreCase(action)) {
						forward = performProjectDelete(mapping, form, request, response);
					} else if ("PROJECTPRINTED".equalsIgnoreCase(action)) { 
						forward = performProjectPrinted(mapping, form, request, response);
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
		SalesOrderForm form = (SalesOrderForm) actionForm;
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
			Criteria criteria = SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromOrderDate")!=null)criteria.add(Restrictions.ge("OrderDate", new Date(form.getCalendar("fromOrderDate").getTime().getTime())));
			if (form.getCalendar("toOrderDate")!=null)criteria.add(Restrictions.le("OrderDate", new Date(form.getCalendar("toOrderDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.add(Restrictions.isNull("Project.Id"));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromOrderDate")!=null)criteria.add(Restrictions.ge("OrderDate", new Date(form.getCalendar("fromOrderDate").getTime().getTime())));
			if (form.getCalendar("toOrderDate")!=null)criteria.add(Restrictions.le("OrderDate", new Date(form.getCalendar("toOrderDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.add(Restrictions.isNotNull("Project.Id"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("SALESORDER",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("salesOrder");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				SalesOrderDAO.getInstance().closeSessionForReal();
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
		SalesOrderForm form = (SalesOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
				Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class).add(Restrictions.eq("Code", new String(form.getString("itemCode")))).uniqueResult();
				form.setString("itemId", item.getId());
			}
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			// remove
			SalesOrder obj = (SalesOrder)httpSession.getAttribute("salesOrder");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVESALESORDERDETAIL")) {
				SalesOrderDetail removeSalesOrderDetail = null;
				Iterator iterator = obj.getSalesOrderDetails().iterator();
				while (iterator.hasNext()) {
					SalesOrderDetail salesOrderDetail = (SalesOrderDetail)iterator.next();
					if (form.getLong("itemId") == salesOrderDetail.getId().getItem().getId()) {
						removeSalesOrderDetail = salesOrderDetail;
					}
				}
				if (removeSalesOrderDetail!=null) {
					Set set = obj.getSalesOrderDetails();
					set.remove(removeSalesOrderDetail);
					obj.setSalesOrderDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("salesOrder", obj);
			}
			// relationships
			List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemUnitLst", itemUnitLst);
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
			List salesmanLst = SalesmanDAO.getInstance().getSession().createCriteria(Salesman.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("FullName")).list();
			request.setAttribute("salesmanLst", salesmanLst);
			List termOfPaymentLst = TermOfPaymentDAO.getInstance().getSession().createCriteria(TermOfPayment.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("termOfPaymentLst", termOfPaymentLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
/*			Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
			List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerAliasLst", customerAliasLst);*/
			if (form.getLong("salesOrderId") == 0) {
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				form.setString("salesOrderId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getSalesOrderNumber());
				if (form.getCalendar("orderDate")==null) form.setCurentCalendar("orderDate");
				if (form.getCalendar("deliveryDate")==null) form.setCurentCalendar("deliveryDate");
				DiscountReference discountReference = (DiscountReference)DiscountReferenceDAO.getInstance().getSession().createCriteria(DiscountReference.class)
					.add(Restrictions.eq("DiscountType", "SO"))
					.add(Restrictions.eq("Active", Boolean.TRUE))
					.add(Restrictions.ge("ValidFrom", new Date(form.getCalendar("orderDate").getTime().getTime())))
					.add(Restrictions.le("ValidTo", new Date(form.getCalendar("orderDate").getTime().getTime())))
					.setMaxResults(1)
					.uniqueResult();
				if (discountReference!=null) form.setString("discountProcent", discountReference.getAmount());
				if (obj!=null) {
					form.setString("salesOrderId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setString("discountAmount",obj.getDiscountAmount());
					form.setString("discountProcent",obj.getDiscountProcent());
					form.setString("number",obj.getNumber());
					form.setString("taxAmount",obj.getTaxAmount());
					form.setString("creditLimit",obj.getCreditLimit());
					form.setString("purchaseOrder",obj.getPurchaseOrder());
					form.setCalendar("deliveryDate",obj.getDeliveryDate());
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
					form.setString("departmentId",obj.getDepartment()!=null?obj.getDepartment().getId():0);
					form.setString("salesmanId",obj.getSalesman()!=null?obj.getSalesman().getId():0);
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					form.setString("termOfPaymentId",obj.getTermOfPayment()!=null?obj.getTermOfPayment().getId():0);
					form.setCalendar("orderDate",obj.getOrderDate());
					form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
					//form.setString("termId",obj.getTerm()!=null?obj.getTerm().getId():0);
					form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
					form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
					Set salesOrderDetailLst = obj.getSalesOrderDetails();
					request.setAttribute("salesOrderDetailLst", salesOrderDetailLst);
					if (CustomersDAO.getInstance().isOverCreditLimit(obj.getCustomer(), organizationSetup.getSetupDate(), null, obj.getAmountAfterTaxAndDiscount())) {
						ActionMessages errors = new ActionMessages();
						errors.add("errorOverLimit", new ActionMessage("error.over.limit"));
						saveErrors(request,errors);
					}
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
					obj = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
					httpSession.setAttribute("salesOrder",obj);
				}
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				form.setString("salesOrderId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("discountAmount",obj.getDiscountAmount());
				form.setString("discountProcent",obj.getDiscountProcent());
				form.setString("number",obj.getNumber());
				form.setString("taxAmount",obj.getTaxAmount());
				form.setString("creditLimit",obj.getCreditLimit());
				form.setString("purchaseOrder",obj.getPurchaseOrder());
				form.setCalendar("deliveryDate",obj.getDeliveryDate());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
				form.setString("departmentId",obj.getDepartment()!=null?obj.getDepartment().getId():0);
				form.setString("termOfPaymentId",obj.getTermOfPayment()!=null?obj.getTermOfPayment().getId():0);
				form.setCalendar("orderDate",obj.getOrderDate());
				form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
				//form.setString("termId",obj.getTerm()!=null?obj.getTerm().getId():0);
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
				form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
				form.setString("salesmanId",obj.getSalesman()!=null?obj.getSalesman().getId():0);
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set salesOrderDetailLst = obj.getSalesOrderDetails();
				request.setAttribute("salesOrderDetailLst", salesOrderDetailLst);
				if (CustomersDAO.getInstance().isOverCreditLimit(obj.getCustomer(), organizationSetup.getSetupDate(), obj.getOrderDate(), 0)) {
					ActionMessages errors = new ActionMessages();
					errors.add("errorOverLimit", new ActionMessage("error.over.limit"));
					saveErrors(request,errors);
				}
			}
			if (form.getLong("itemId") > 0) {
				boolean isNewData = true;
				String sql = "" +
					"select a.sales_tax_id as TaxId from item_price a where a.item_id="+form.getLong("itemId");
				List list = ItemDAO.getInstance().getSession().createSQLQuery(sql).addScalar("TaxId", Hibernate.LONG).list();
				List taxDetailLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Ap", Boolean.FALSE))
					.add(Restrictions.in("Id", list))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("taxDetailLst", taxDetailLst);
				if (obj!=null && obj.getSalesOrderDetails()!=null) {
					Iterator iterator = obj.getSalesOrderDetails().iterator();
					while (iterator.hasNext()) {
						SalesOrderDetail salesOrderDetail = (SalesOrderDetail)iterator.next();
						if (form.getLong("itemId") == salesOrderDetail.getId().getItem().getId()) {
							isNewData = false;
							form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(),salesOrderDetail.getPrice()));
							form.setString("quantity", salesOrderDetail.getQuantity());
							form.setString("itemCode", salesOrderDetail.getId().getItem().getCode());
							form.setString("costPrice", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), salesOrderDetail.getCostPrice()));
							form.setString("itemDiscountProcent", salesOrderDetail.getDiscountProcent());
							form.setString("itemUnitId", salesOrderDetail.getItemUnit()!=null?salesOrderDetail.getItemUnit().getId():0);
							form.setString("salesOrderDetailCurrencyId", salesOrderDetail.getCurrency()!=null?salesOrderDetail.getCurrency().getId():0);
							form.setString("costPriceCurrencyId", salesOrderDetail.getCostPriceCurrency()!=null?salesOrderDetail.getCostPriceCurrency().getId():0);
							form.setString("taxDetailId", salesOrderDetail.getTax()!=null?salesOrderDetail.getTax().getId():0);
							form.setString("taxDetailAmount", salesOrderDetail.getTaxAmount());
							form.setString("itemDescription", salesOrderDetail.getDescription());
							form.setString("itemDiscountAmount", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), salesOrderDetail.getDiscountAmount()));
							form.setString("itemDiscountProcent", salesOrderDetail.getDiscountProcent());
						}
					}
				}
				if (isNewData) {
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					if (inventory!=null) {
						Customers customers = null;
						if (customers==null || (obj!=null && obj.getCustomer()==null)) customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
						String sql2 = "select itemPrice from ItemPrice itemPrice where itemPrice.Id.Item.Id = "+form.getLong("itemId");
						if (customers!=null && customers.getItemPriceCategory()!=null) sql2 = sql2 + " and itemPrice.Id.ItemPriceCategory.Id = "+customers.getItemPriceCategory().getId();
						else sql2 = sql2 + " and itemPrice.Default = 'Y'";
						ItemPrice itemPrice = (ItemPrice)ItemPriceDAO.getInstance().getSession().createQuery(sql2).uniqueResult();
						if (itemPrice!=null) {
							form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), itemPrice.getPrice()));
							form.setString("salesOrderDetailCurrencyId", itemPrice.getCurrency()!=null?itemPrice.getCurrency().getId():form.getLong("currencyId"));
							form.setString("taxDetailId", itemPrice.getSalesTax()!=null?itemPrice.getSalesTax().getId():0);
							form.setString("taxDetailAmount", itemPrice.getSalesTax()!=null?itemPrice.getSalesTax().getQuantity():0);
						} else {
							sql2 = "select itemPrice from ItemPrice itemPrice where itemPrice.Id.Item.Id = "+form.getLong("itemId") + " and itemPrice.Default = 'Y'";
							itemPrice = (ItemPrice)ItemPriceDAO.getInstance().getSession().createQuery(sql2).uniqueResult();
							if (itemPrice!=null) {
								form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), itemPrice.getPrice()));
								form.setString("salesOrderDetailCurrencyId", itemPrice.getCurrency()!=null?itemPrice.getCurrency().getId():form.getLong("currencyId"));
								
							}
						}
						form.setString("itemUnitId", inventory.getItemUnit()!=null?inventory.getItemUnit().getId():0);
						form.setString("itemCode", inventory.getCode());
						form.setString("costPrice", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), inventory.getCostPrice()));
						form.setString("costPriceCurrencyId", inventory.getCurrency()!=null?inventory.getCurrency().getId():form.getLong("currencyId"));
					}
				}
			}
			request.setAttribute("salesOrderDetailAmount", obj!=null?obj.getFormatedSalesOrderDetailAmount():"-");
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
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				List termOfPaymentLst = TermOfPaymentDAO.getInstance().getSession().createCriteria(TermOfPayment.class)
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("termOfPaymentLst", termOfPaymentLst);
				List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("departmentLst", departmentLst);
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
				List salesmanLst = SalesmanDAO.getInstance().getSession().createCriteria(Salesman.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("FullName")).list();
				request.setAttribute("salesmanLst", salesmanLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				SalesOrderDAO.getInstance().closeSessionForReal();
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
		SalesOrderForm form = (SalesOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = SalesOrderDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("salesOrder");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			SalesOrder obj = (SalesOrder)httpSession.getAttribute("salesOrder");
			PrepaymentToVendor customerPrepayment = (PrepaymentToVendor)httpSession.getAttribute("customerPrepayment");
			if (form.getLong("salesOrderId") == 0) {
				obj = (SalesOrder)SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (SalesOrder)httpSession.getAttribute("salesOrder");
					if (obj==null) obj = new SalesOrder();
					obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					obj.setDescription(form.getString("description"));
					//obj.setDiscountAmount(form.getDouble("discountAmount"));
					obj.setDiscountProcent(form.getDouble("discountProcent"));
					//obj.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("orderDate")));
					obj.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("orderDate"))));
					obj.setNumber(form.getString("number"));
					obj.setPurchaseOrder(form.getString("purchaseOrder"));
					obj.setOrganization(users.getOrganization());
					Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
					obj.setProject(project);
					Department department = DepartmentDAO.getInstance().get(form.getLong("departmentId"));
					obj.setDepartment(department);
					obj.setOrderDate(form.getCalendar("orderDate")!=null?form.getCalendar("orderDate").getTime():null);
					obj.setDeliveryDate(form.getCalendar("deliveryDate")!=null?form.getCalendar("deliveryDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					obj.setDeliveryOrderStatus(CommonConstants.OPEN);
					obj.setCustomerPaymentStatus(CommonConstants.OPEN);
					Tax tax = TaxDAO.getInstance().get(form.getLong("taxId"));
					obj.setTax(tax);
					if (tax!=null)obj.setTaxAmount(tax.getQuantity());
					//Term term = TermDAO.getInstance().get(form.getLong("termId"));
					//obj.setTerm(term);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
					obj.setCustomerAlias(customersAlias);
					//if (customers!=null) obj.setCreditLimit(customers.getCreditLimit());
					TermOfPayment termOfPayment = TermOfPaymentDAO.getInstance().get(form.getLong("termOfPaymentId"));
					obj.setTermOfPayment(termOfPayment);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					Salesman salesman = SalesmanDAO.getInstance().get(form.getLong("salesmanId"));
					obj.setSalesman(salesman);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					Set set = new LinkedHashSet();
					set.add(obj);
					// cek prepayment
					/*
					if (form.getDouble("prepaymentAmount")>0) {
						if (customerPrepayment==null) customerPrepayment = new PrepaymentToVendor();
						customerPrepayment.setCurrency(currency);
						customerPrepayment.setExchangeRate(obj.getExchangeRate());
						customerPrepayment.setNumber(RunningNumberDAO.getInstance().getPrepaymentToVendorNumber());
						customerPrepayment.setOrganization(users.getOrganization());
						customerPrepayment.setPosted(false);
						customerPrepayment.setPrepaymentDate(obj.getPurchaseDate());
						customerPrepayment.setSalesOrder(obj);
						customerPrepayment.setStatus(CommonConstants.OPEN);
						customerPrepayment.setVendor(obj.getVendor());
					}*/
				} else {
					List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("itemUnitLst", itemUnitLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
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
					List salesmanLst = SalesmanDAO.getInstance().getSession().createCriteria(Salesman.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("FullName")).list();
					request.setAttribute("salesmanLst", salesmanLst);
					List termOfPaymentLst = TermOfPaymentDAO.getInstance().getSession().createCriteria(TermOfPayment.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("termOfPaymentLst", termOfPaymentLst);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				obj.setDescription(form.getString("description"));
				//obj.setDiscountAmount(form.getDouble("discountAmount"));
				obj.setDiscountProcent(form.getDouble("discountProcent"));
				//obj.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("orderDate")));
				obj.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("orderDate"))));
				obj.setNumber(form.getString("number"));
				obj.setPurchaseOrder(form.getString("purchaseOrder"));
				obj.setOrganization(users.getOrganization());
				Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
				obj.setProject(project);
				Department department = DepartmentDAO.getInstance().get(form.getLong("departmentId"));
				obj.setDepartment(department);
				obj.setOrderDate(form.getCalendar("orderDate")!=null?form.getCalendar("orderDate").getTime():null);
				obj.setDeliveryDate(form.getCalendar("deliveryDate")!=null?form.getCalendar("deliveryDate").getTime():null);
				//obj.setStatus("OPEN");
				//obj.setReceivingStatus("OPEN");
				Tax tax = TaxDAO.getInstance().get(form.getLong("taxId"));
				obj.setTax(tax);
				if (tax!=null)obj.setTaxAmount(tax.getQuantity());
				Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setCustomer(customers);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
				obj.setCustomerAlias(customersAlias);
				TermOfPayment termOfPayment = TermOfPaymentDAO.getInstance().get(form.getLong("termOfPaymentId"));
				obj.setTermOfPayment(termOfPayment);
				//if (customers!=null) obj.setCreditLimit(customers.getCreditLimit());
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				Salesman salesman = SalesmanDAO.getInstance().get(form.getLong("salesmanId"));
				obj.setSalesman(salesman);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("salesOrderId"));
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDSALESORDERDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("quantity")>0) {
					SalesOrderDetail salesOrderDetail = new SalesOrderDetail();
					//Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
				  	Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					salesOrderDetail.setItemUnit(itemUnit);
					SalesOrderDetailPK salesOrderDetailPK = new SalesOrderDetailPK();
					salesOrderDetailPK.setItem(inventory);
					salesOrderDetailPK.setSalesOrder(obj);
					salesOrderDetail.setId(salesOrderDetailPK);
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("salesOrderDetailCurrencyId")>0?form.getLong("salesOrderDetailCurrencyId"):form.getLong("currencyId"));
					salesOrderDetail.setCurrency(currency);
					salesOrderDetail.setDiscountProcent(form.getDouble("itemDiscountProcent"));
					//salesOrderDetail.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, obj.getCurrency(), organizationSetup, form.getCalendar("orderDate")));
					salesOrderDetail.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, obj.getCurrency(), organizationSetup, form.getCalendar("orderDate"))));
					salesOrderDetail.setPrice(form.getDouble("price"));
					salesOrderDetail.setQuantity(form.getDouble("quantity"));
					salesOrderDetail.setCostPrice(form.getDouble("costPrice"));
					Currency currency2 = CurrencyDAO.getInstance().get(form.getLong("costPriceCurrencyId")>0?form.getLong("costPriceCurrencyId"):form.getLong("currencyId"));
					Tax taxDetail = TaxDAO.getInstance().get(form.getLong("taxDetailId"));
					salesOrderDetail.setTax(taxDetail);
					salesOrderDetail.setTaxAmount(taxDetail!=null?taxDetail.getQuantity():0);
					salesOrderDetail.setDiscountAmount(form.getDouble("itemDiscountAmount"));
					salesOrderDetail.setDiscountProcent(form.getDouble("itemDiscountProcent"));
					salesOrderDetail.setDescription(form.getString("itemDescription"));
					salesOrderDetail.setCostPriceCurrency(currency2);
					//salesOrderDetail.setCostPriceExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency2, obj.getCurrency(), organizationSetup, form.getCalendar("orderDate")));
					salesOrderDetail.setCostPriceExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency2, obj.getCurrency(), organizationSetup, form.getCalendar("orderDate"))));
					salesOrderDetail.setUnitConversion(ItemUnitDAO.getInstance().getConversion(itemUnit, inventory.getItemUnit(), inventory));
					Set set = obj.getSalesOrderDetails();
					if (set==null) set = new LinkedHashSet();
					SalesOrderDetail removeSalesOrderDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						SalesOrderDetail salesOrderDetail2 = (SalesOrderDetail)iterator.next();
						if (form.getLong("itemId")==salesOrderDetail2.getId().getItem().getId()) {
							removeSalesOrderDetail = salesOrderDetail2;
						}
					}
					if (removeSalesOrderDetail!=null) {
						set.remove(removeSalesOrderDetail);
						set.add(salesOrderDetail);
					} else {
						set.add(salesOrderDetail);
					}
					obj.setSalesOrderDetails(set);
					// netral
					form.setString("itemId", "");
					form.setString("itemCode", "");
					form.setString("price", "");
					form.setString("quantity", "");
					form.setString("itemUnitId", "");
					form.setString("costPrice", "");
					form.setString("itemDiscountProcent", "");
					form.setString("salesOrderDetailCurrencyId", "");
				}
				// netral
				form.setString("itemId", "");
				form.setString("itemCode", "");
				form.setString("price", "");
				form.setString("quantity", "");
				form.setString("itemUnitId", "");
				form.setString("itemDiscountProcent", "");
				form.setString("costPrice", "");
				form.setString("salesOrderDetailCurrencyId", "");
			}
			// save to session
			httpSession.setAttribute("salesOrder", obj);
			httpSession.setAttribute("customerPrepayment", customerPrepayment);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					if (!CustomersDAO.getInstance().isOverCreditLimit(obj.getCustomer(), organizationSetup.getSetupDate(), null, obj.getAmountAfterTaxAndDiscount())) {
						RunningNumberDAO.getInstance().updateSalesOrderNumber(session);
						SalesOrderDAO.getInstance().save(obj, session);
						if (customerPrepayment!=null) {
							RunningNumberDAO.getInstance().updateCustomerPrepaymentNumber(session);
							PrepaymentToVendorDAO.getInstance().save(customerPrepayment);
						}
						transaction.commit();
					}
				} else {
					transaction = session.beginTransaction();
					// update status
					if (!CustomersDAO.getInstance().isOverCreditLimit(obj.getCustomer(), organizationSetup.getSetupDate(), obj.getOrderDate(), 0)) {
						boolean isClosed = true;
						double received = 0;
						Iterator iterator = obj.getSalesOrderDetails().iterator();
						while (iterator.hasNext()) {
							SalesOrderDetail salesOrderDetail = (SalesOrderDetail)iterator.next();
							received = salesOrderDetail.getDeliveryQuantity();
							if (salesOrderDetail.getQuantity()!=(salesOrderDetail.getDeliveryQuantity()-salesOrderDetail.getReturQuantity())) isClosed = false;
						}
						if (isClosed)obj.setDeliveryOrderStatus(CommonConstants.CLOSE);
						else {
							if (received>0) obj.setDeliveryOrderStatus(CommonConstants.PARTIAL);
							else obj.setDeliveryOrderStatus(CommonConstants.OPEN);
						}
						SalesOrderDAO.getInstance().getSession().merge(obj);
						transaction.commit();
					}
				}
				// remove session
				httpSession.removeAttribute("salesOrder");
				httpSession.removeAttribute("customerPrepayment");
				// finish
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
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
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
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
					List salesmanLst = SalesmanDAO.getInstance().getSession().createCriteria(Salesman.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("FullName")).list();
					request.setAttribute("salesmanLst", salesmanLst);
					List termOfPaymentLst = TermOfPaymentDAO.getInstance().getSession().createCriteria(TermOfPayment.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("termOfPaymentLst", termOfPaymentLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				SalesOrderDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?salesOrderId="+form.getLong("salesOrderId"));
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
		SalesOrderForm form = (SalesOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			SalesOrder salesOrder = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
			request.setAttribute("salesOrder", salesOrder);
			if (CustomersDAO.getInstance().isOverCreditLimit(salesOrder.getCustomer(), organizationSetup.getSetupDate(), salesOrder.getOrderDate(), 0)) {
				ActionMessages errors = new ActionMessages();
				errors.add("errorOverLimit", new ActionMessage("error.over.limit"));
				saveErrors(request,errors);
			}
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				SalesOrderDAO.getInstance().closeSessionForReal();
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
		SalesOrderForm form = (SalesOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			SalesOrder salesOrder = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
			
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
			cell = new Cell(new Phrase("No : "+salesOrder.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tanggal : "+salesOrder.getFormatedOrderDate(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tlp. "+users.getOrganization().getTelephone()+" - Fax. "+users.getOrganization().getFax(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("PO : "+salesOrder.getPurchaseOrder(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tgl kirim : "+salesOrder.getFormatedDeliveryDate(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Customer : "+salesOrder.getCustomer().getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Alamat : "+salesOrder.getCustomer().getAddress()+" "+salesOrder.getCustomer().getCity()+" "+salesOrder.getCustomer().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Term of Payment : "+salesOrder.getTermOfPayment()!=null?salesOrder.getTermOfPayment().getName():"-", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);			
			
			document.add(table1);
			
			Table table2 = new Table(6);
			table2.setWidth(100);
			table2.setCellsFitPage(true);
			float[] b = {5,30,25,10,15,15};
			table2.setWidths(b);
			table2.setBorder(Rectangle.NO_BORDER);
			table2.setCellsFitPage(true);
			table2.setBorderWidth(1);
			table2.setPadding(1);
			table2.setSpacing(0);
			
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase("SURAT PESANAN", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Pembayaran : Tunai/Kredit "+salesOrder.getCreditLimit()+" hari", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setColspan(3);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("PART",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
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
			Iterator iterator = salesOrder.getSalesOrderDetails().iterator();
			while (iterator.hasNext()) {
			    SalesOrderDetail detail = (SalesOrderDetail)iterator.next();
			    cell = new Cell(new Phrase(++j+".", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getId().getItem().getCode(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getDescription(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
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
			cell = new Cell(new Phrase("SUBTOTAL", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(salesOrder.getFormatedSalesOrderDetailAmount(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("DISCOUNT", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(salesOrder.getFormatedAmountDiscount(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("PPN", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(salesOrder.getFormatedAmountTax(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("GRAND TOTAL", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(salesOrder.getFormatedAmountAfterTaxAndDiscount(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("Salesman : "+salesOrder.getSalesman()!=null?salesOrder.getSalesman().getFullName():"-", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Description : "+salesOrder.getDescription(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(6);
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
				SalesOrderDAO.getInstance().closeSessionForReal();
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
		SalesOrderForm form = (SalesOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			SalesOrderDAO.getInstance().delete(form.getLong("salesOrderId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				SalesOrderDAO.getInstance().closeSessionForReal();
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
	private ActionForward performProjectPartialList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		SalesOrderForm form = (SalesOrderForm) actionForm;
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
			Criteria criteria = SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromOrderDate")!=null)criteria.add(Restrictions.ge("OrderDate", new Date(form.getCalendar("fromOrderDate").getTime().getTime())));
			if (form.getCalendar("toOrderDate")!=null)criteria.add(Restrictions.le("OrderDate", new Date(form.getCalendar("toOrderDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.add(Restrictions.isNotNull("Project.Id"));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromOrderDate")!=null)criteria.add(Restrictions.ge("OrderDate", new Date(form.getCalendar("fromOrderDate").getTime().getTime())));
			if (form.getCalendar("toOrderDate")!=null)criteria.add(Restrictions.le("OrderDate", new Date(form.getCalendar("toOrderDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("customerId")>0) criteria.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.add(Restrictions.isNotNull("Project.Id"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("SALESORDER",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("salesOrder");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				SalesOrderDAO.getInstance().closeSessionForReal();
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
	private ActionForward performProjectForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		SalesOrderForm form = (SalesOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
				Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class).add(Restrictions.eq("Code", new String(form.getString("itemCode")))).uniqueResult();
				form.setString("itemId", item.getId());
			}
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			// remove
			SalesOrder obj = (SalesOrder)httpSession.getAttribute("salesOrder");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVESALESORDERDETAIL")) {
				SalesOrderDetail removeSalesOrderDetail = null;
				Iterator iterator = obj.getSalesOrderDetails().iterator();
				while (iterator.hasNext()) {
					SalesOrderDetail salesOrderDetail = (SalesOrderDetail)iterator.next();
					if (form.getLong("itemId") == salesOrderDetail.getId().getItem().getId()) {
						removeSalesOrderDetail = salesOrderDetail;
					}
				}
				if (removeSalesOrderDetail!=null) {
					Set set = obj.getSalesOrderDetails();
					set.remove(removeSalesOrderDetail);
					obj.setSalesOrderDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("salesOrder", obj);
			}
			// relationships
			List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemUnitLst", itemUnitLst);
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
			List salesmanLst = SalesmanDAO.getInstance().getSession().createCriteria(Salesman.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("FullName")).list();
			request.setAttribute("salesmanLst", salesmanLst);
			List termOfPaymentLst = TermOfPaymentDAO.getInstance().getSession().createCriteria(TermOfPayment.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("termOfPaymentLst", termOfPaymentLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
/*			Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
			List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerAliasLst", customerAliasLst);*/
			if (form.getLong("salesOrderId") == 0) {
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				form.setString("salesOrderId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getSalesOrderNumber());
				if (form.getCalendar("orderDate")==null) form.setCurentCalendar("orderDate");
				if (form.getCalendar("deliveryDate")==null) form.setCurentCalendar("deliveryDate");
				if (obj!=null) {
					form.setString("salesOrderId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setString("discountAmount",obj.getDiscountAmount());
					form.setString("discountProcent",obj.getDiscountProcent());
					form.setString("number",obj.getNumber());
					form.setString("taxAmount",obj.getTaxAmount());
					form.setString("creditLimit",obj.getCreditLimit());
					form.setString("purchaseOrder",obj.getPurchaseOrder());
					form.setCalendar("deliveryDate",obj.getDeliveryDate());
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
					form.setString("departmentId",obj.getDepartment()!=null?obj.getDepartment().getId():0);
					form.setString("salesmanId",obj.getSalesman()!=null?obj.getSalesman().getId():0);
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					form.setString("termOfPaymentId",obj.getTermOfPayment()!=null?obj.getTermOfPayment().getId():0);
					form.setCalendar("orderDate",obj.getOrderDate());
					form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
					//form.setString("termId",obj.getTerm()!=null?obj.getTerm().getId():0);
					form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
					List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerAliasLst", customerAliasLst);
					form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
					Set salesOrderDetailLst = obj.getSalesOrderDetails();
					request.setAttribute("salesOrderDetailLst", salesOrderDetailLst);
					if (CustomersDAO.getInstance().isOverCreditLimit(obj.getCustomer(), organizationSetup.getSetupDate(), null, obj.getAmountAfterTaxAndDiscount())) {
						ActionMessages errors = new ActionMessages();
						errors.add("errorOverLimit", new ActionMessage("error.over.limit"));
						saveErrors(request,errors);
					}
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
					obj = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
					httpSession.setAttribute("salesOrder",obj);
				}
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				form.setString("salesOrderId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("discountAmount",obj.getDiscountAmount());
				form.setString("discountProcent",obj.getDiscountProcent());
				form.setString("number",obj.getNumber());
				form.setString("taxAmount",obj.getTaxAmount());
				form.setString("creditLimit",obj.getCreditLimit());
				form.setString("purchaseOrder",obj.getPurchaseOrder());
				form.setCalendar("deliveryDate",obj.getDeliveryDate());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
				form.setString("departmentId",obj.getDepartment()!=null?obj.getDepartment().getId():0);
				form.setString("termOfPaymentId",obj.getTermOfPayment()!=null?obj.getTermOfPayment().getId():0);
				form.setCalendar("orderDate",obj.getOrderDate());
				form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
				//form.setString("termId",obj.getTerm()!=null?obj.getTerm().getId():0);
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerId"));
				List customerAliasLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Id", new Long(customersAlias!=null?(customersAlias.getCustomerAlias()!=null?customersAlias.getCustomerAlias().getId():0):0))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerAliasLst", customerAliasLst);
				form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
				form.setString("salesmanId",obj.getSalesman()!=null?obj.getSalesman().getId():0);
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set salesOrderDetailLst = obj.getSalesOrderDetails();
				request.setAttribute("salesOrderDetailLst", salesOrderDetailLst);
				if (CustomersDAO.getInstance().isOverCreditLimit(obj.getCustomer(), organizationSetup.getSetupDate(), obj.getOrderDate(), 0)) {
					ActionMessages errors = new ActionMessages();
					errors.add("errorOverLimit", new ActionMessage("error.over.limit"));
					saveErrors(request,errors);
				}
			}
			if (form.getLong("itemId") > 0) {
				boolean isNewData = true;
				String sql = "" +
					"select a.sales_tax_id as TaxId from item_price a where a.item_id="+form.getLong("itemId");
				List list = ItemDAO.getInstance().getSession().createSQLQuery(sql).addScalar("TaxId", Hibernate.LONG).list();
				List taxDetailLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Ap", Boolean.FALSE))
					.add(Restrictions.in("Id", list))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("taxDetailLst", taxDetailLst);
				if (obj!=null && obj.getSalesOrderDetails()!=null) {
					Iterator iterator = obj.getSalesOrderDetails().iterator();
					while (iterator.hasNext()) {
						SalesOrderDetail salesOrderDetail = (SalesOrderDetail)iterator.next();
						if (form.getLong("itemId") == salesOrderDetail.getId().getItem().getId()) {
							isNewData = false;
							form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(),salesOrderDetail.getPrice()));
							form.setString("quantity", salesOrderDetail.getQuantity());
							form.setString("itemCode", salesOrderDetail.getId().getItem().getCode());
							form.setString("costPrice", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), salesOrderDetail.getCostPrice()));
							form.setString("itemDiscountProcent", salesOrderDetail.getDiscountProcent());
							form.setString("itemUnitId", salesOrderDetail.getItemUnit()!=null?salesOrderDetail.getItemUnit().getId():0);
							form.setString("salesOrderDetailCurrencyId", salesOrderDetail.getCurrency()!=null?salesOrderDetail.getCurrency().getId():0);
							form.setString("costPriceCurrencyId", salesOrderDetail.getCostPriceCurrency()!=null?salesOrderDetail.getCostPriceCurrency().getId():0);
							form.setString("taxDetailId", salesOrderDetail.getTax()!=null?salesOrderDetail.getTax().getId():0);
							form.setString("taxDetailAmount", salesOrderDetail.getTaxAmount());
							form.setString("itemDiscountAmount", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), salesOrderDetail.getDiscountAmount()));
							form.setString("itemDiscountProcent", salesOrderDetail.getDiscountProcent());
							form.setString("itemDescription", salesOrderDetail.getDescription());
						}
					}
				}
				if (isNewData) {
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					if (inventory!=null) {
						Customers customers = null;
						if (customers==null || (obj!=null && obj.getCustomer()==null)) customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
						String sql2 = "select itemPrice from ItemPrice itemPrice where itemPrice.Id.Item.Id = "+form.getLong("itemId");
						if (customers!=null && customers.getItemPriceCategory()!=null) sql2 = sql2 + " and itemPrice.Id.ItemPriceCategory.Id = "+customers.getItemPriceCategory().getId();
						else sql2 = sql2 + " and itemPrice.Default = 'Y'";
						ItemPrice itemPrice = (ItemPrice)ItemPriceDAO.getInstance().getSession().createQuery(sql2).uniqueResult();
						if (itemPrice!=null) {
							form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), itemPrice.getPrice()));
							form.setString("salesOrderDetailCurrencyId", itemPrice.getCurrency()!=null?itemPrice.getCurrency().getId():form.getLong("currencyId"));
							form.setString("taxDetailId", itemPrice.getSalesTax()!=null?itemPrice.getSalesTax().getId():0);
							form.setString("taxDetailAmount", itemPrice.getSalesTax()!=null?itemPrice.getSalesTax().getQuantity():0);
						} else {
							sql2 = "select itemPrice from ItemPrice itemPrice where itemPrice.Id.Item.Id = "+form.getLong("itemId") + " and itemPrice.Default = 'Y'";
							itemPrice = (ItemPrice)ItemPriceDAO.getInstance().getSession().createQuery(sql2).uniqueResult();
							if (itemPrice!=null) {
								form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), itemPrice.getPrice()));
								form.setString("salesOrderDetailCurrencyId", itemPrice.getCurrency()!=null?itemPrice.getCurrency().getId():form.getLong("currencyId"));
								
							}
						}
						form.setString("itemUnitId", inventory.getItemUnit()!=null?inventory.getItemUnit().getId():0);
						form.setString("itemCode", inventory.getCode());
						form.setString("costPrice", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), inventory.getCostPrice()));
						form.setString("costPriceCurrencyId", inventory.getCurrency()!=null?inventory.getCurrency().getId():form.getLong("currencyId"));
					}
				}
			}
			request.setAttribute("salesOrderDetailAmount", obj!=null?obj.getFormatedSalesOrderDetailAmount():"-");
			request.setAttribute("amountTax", obj!=null?obj.getFormatedAmountTax():"-");
			request.setAttribute("amountDiscount", obj!=null?obj.getFormatedAmountDiscount():"-");
			request.setAttribute("amountAfterDiscount", obj!=null?obj.getFormatedAmountAfterDiscount():"-");
			request.setAttribute("amountAfterTaxAndDiscount", obj!=null?obj.getFormatedAmountAfterTaxAndDiscount():"-");
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
				List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("itemUnitLst", itemUnitLst);
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				List termOfPaymentLst = TermOfPaymentDAO.getInstance().getSession().createCriteria(TermOfPayment.class)
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("termOfPaymentLst", termOfPaymentLst);
				List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("departmentLst", departmentLst);
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
				List salesmanLst = SalesmanDAO.getInstance().getSession().createCriteria(Salesman.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("FullName")).list();
				request.setAttribute("salesmanLst", salesmanLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				SalesOrderDAO.getInstance().closeSessionForReal();
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
	private ActionForward performProjectSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		SalesOrderForm form = (SalesOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = SalesOrderDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("salesOrder");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			SalesOrder obj = (SalesOrder)httpSession.getAttribute("salesOrder");
			PrepaymentToVendor customerPrepayment = (PrepaymentToVendor)httpSession.getAttribute("customerPrepayment");
			if (form.getLong("salesOrderId") == 0) {
				obj = (SalesOrder)SalesOrderDAO.getInstance().getSession().createCriteria(SalesOrder.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (SalesOrder)httpSession.getAttribute("salesOrder");
					if (obj==null) obj = new SalesOrder();
					obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					obj.setDescription(form.getString("description"));
					//obj.setDiscountAmount(form.getDouble("discountAmount"));
					obj.setDiscountProcent(form.getDouble("discountProcent"));
					//obj.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("orderDate")));
					obj.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("orderDate"))));
					obj.setNumber(form.getString("number"));
					obj.setPurchaseOrder(form.getString("purchaseOrder"));
					obj.setOrganization(users.getOrganization());
					Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
					obj.setProject(project);
					Department department = DepartmentDAO.getInstance().get(form.getLong("departmentId"));
					obj.setDepartment(department);
					obj.setOrderDate(form.getCalendar("orderDate")!=null?form.getCalendar("orderDate").getTime():null);
					obj.setDeliveryDate(form.getCalendar("deliveryDate")!=null?form.getCalendar("deliveryDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					obj.setDeliveryOrderStatus(CommonConstants.OPEN);
					obj.setCustomerPaymentStatus(CommonConstants.OPEN);
					Tax tax = TaxDAO.getInstance().get(form.getLong("taxId"));
					obj.setTax(tax);
					if (tax!=null)obj.setTaxAmount(tax.getQuantity());
					//Term term = TermDAO.getInstance().get(form.getLong("termId"));
					//obj.setTerm(term);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
					obj.setCustomerAlias(customersAlias);
					//if (customers!=null) obj.setCreditLimit(customers.getCreditLimit());
					TermOfPayment termOfPayment = TermOfPaymentDAO.getInstance().get(form.getLong("termOfPaymentId"));
					obj.setTermOfPayment(termOfPayment);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					Salesman salesman = SalesmanDAO.getInstance().get(form.getLong("salesmanId"));
					obj.setSalesman(salesman);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					Set set = new LinkedHashSet();
					set.add(obj);
					// cek prepayment
					/*
					if (form.getDouble("prepaymentAmount")>0) {
						if (customerPrepayment==null) customerPrepayment = new PrepaymentToVendor();
						customerPrepayment.setCurrency(currency);
						customerPrepayment.setExchangeRate(obj.getExchangeRate());
						customerPrepayment.setNumber(RunningNumberDAO.getInstance().getPrepaymentToVendorNumber());
						customerPrepayment.setOrganization(users.getOrganization());
						customerPrepayment.setPosted(false);
						customerPrepayment.setPrepaymentDate(obj.getPurchaseDate());
						customerPrepayment.setSalesOrder(obj);
						customerPrepayment.setStatus(CommonConstants.OPEN);
						customerPrepayment.setVendor(obj.getVendor());
					}*/
				} else {
					List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("itemUnitLst", itemUnitLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
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
					List salesmanLst = SalesmanDAO.getInstance().getSession().createCriteria(Salesman.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("FullName")).list();
					request.setAttribute("salesmanLst", salesmanLst);
					List termOfPaymentLst = TermOfPaymentDAO.getInstance().getSession().createCriteria(TermOfPayment.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("termOfPaymentLst", termOfPaymentLst);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				obj.setDescription(form.getString("description"));
				//obj.setDiscountAmount(form.getDouble("discountAmount"));
				obj.setDiscountProcent(form.getDouble("discountProcent"));
				//obj.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("orderDate")));
				obj.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("orderDate"))));
				obj.setNumber(form.getString("number"));
				obj.setPurchaseOrder(form.getString("purchaseOrder"));
				obj.setOrganization(users.getOrganization());
				Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
				obj.setProject(project);
				Department department = DepartmentDAO.getInstance().get(form.getLong("departmentId"));
				obj.setDepartment(department);
				obj.setOrderDate(form.getCalendar("orderDate")!=null?form.getCalendar("orderDate").getTime():null);
				obj.setDeliveryDate(form.getCalendar("deliveryDate")!=null?form.getCalendar("deliveryDate").getTime():null);
				//obj.setStatus("OPEN");
				//obj.setReceivingStatus("OPEN");
				Tax tax = TaxDAO.getInstance().get(form.getLong("taxId"));
				obj.setTax(tax);
				if (tax!=null)obj.setTaxAmount(tax.getQuantity());
				Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setCustomer(customers);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
				obj.setCustomerAlias(customersAlias);
				TermOfPayment termOfPayment = TermOfPaymentDAO.getInstance().get(form.getLong("termOfPaymentId"));
				obj.setTermOfPayment(termOfPayment);
				//if (customers!=null) obj.setCreditLimit(customers.getCreditLimit());
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				Salesman salesman = SalesmanDAO.getInstance().get(form.getLong("salesmanId"));
				obj.setSalesman(salesman);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("salesOrderId"));
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDSALESORDERDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("quantity")>0) {
					SalesOrderDetail salesOrderDetail = new SalesOrderDetail();
					//Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					salesOrderDetail.setItemUnit(itemUnit);
					SalesOrderDetailPK salesOrderDetailPK = new SalesOrderDetailPK();
					salesOrderDetailPK.setItem(inventory);
					salesOrderDetailPK.setSalesOrder(obj);
					salesOrderDetail.setId(salesOrderDetailPK);
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("salesOrderDetailCurrencyId")>0?form.getLong("salesOrderDetailCurrencyId"):form.getLong("currencyId"));
					salesOrderDetail.setCurrency(currency);
					salesOrderDetail.setDiscountProcent(form.getDouble("itemDiscountProcent"));
					//salesOrderDetail.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, obj.getCurrency(), organizationSetup, form.getCalendar("orderDate")));
					salesOrderDetail.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, obj.getCurrency(), organizationSetup, form.getCalendar("orderDate"))));
					salesOrderDetail.setPrice(form.getDouble("price"));
					salesOrderDetail.setQuantity(form.getDouble("quantity"));
					salesOrderDetail.setCostPrice(form.getDouble("costPrice"));
					Currency currency2 = CurrencyDAO.getInstance().get(form.getLong("costPriceCurrencyId")>0?form.getLong("costPriceCurrencyId"):form.getLong("currencyId"));
					Tax taxDetail = TaxDAO.getInstance().get(form.getLong("taxDetailId"));
					salesOrderDetail.setTax(taxDetail);
					salesOrderDetail.setTaxAmount(taxDetail!=null?taxDetail.getQuantity():0);
					salesOrderDetail.setDiscountAmount(form.getDouble("itemDiscountAmount"));
					salesOrderDetail.setDiscountProcent(form.getDouble("itemDiscountProcent"));
					salesOrderDetail.setDescription(form.getString("itemDescription"));
					salesOrderDetail.setCostPriceCurrency(currency2);
					//salesOrderDetail.setCostPriceExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency2, obj.getCurrency(), organizationSetup, form.getCalendar("orderDate")));
					salesOrderDetail.setCostPriceExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency2, obj.getCurrency(), organizationSetup, form.getCalendar("orderDate"))));
					salesOrderDetail.setUnitConversion(ItemUnitDAO.getInstance().getConversion(itemUnit, inventory.getItemUnit(), inventory));
					Set set = obj.getSalesOrderDetails();
					if (set==null) set = new LinkedHashSet();
					SalesOrderDetail removeSalesOrderDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						SalesOrderDetail salesOrderDetail2 = (SalesOrderDetail)iterator.next();
						if (form.getLong("itemId")==salesOrderDetail2.getId().getItem().getId()) {
							removeSalesOrderDetail = salesOrderDetail2;
						}
					}
					if (removeSalesOrderDetail!=null) {
						set.remove(removeSalesOrderDetail);
						set.add(salesOrderDetail);
					} else {
						set.add(salesOrderDetail);
					}
					obj.setSalesOrderDetails(set);
					// netral
					form.setString("itemId", "");
					form.setString("itemCode", "");
					form.setString("price", "");
					form.setString("quantity", "");
					form.setString("itemUnitId", "");
					form.setString("costPrice", "");
					form.setString("itemDiscountProcent", "");
					form.setString("salesOrderDetailCurrencyId", "");
				}
				// netral
				form.setString("itemId", "");
				form.setString("itemCode", "");
				form.setString("price", "");
				form.setString("quantity", "");
				form.setString("itemUnitId", "");
				form.setString("itemDiscountProcent", "");
				form.setString("costPrice", "");
				form.setString("salesOrderDetailCurrencyId", "");
			}
			// save to session
			httpSession.setAttribute("salesOrder", obj);
			httpSession.setAttribute("customerPrepayment", customerPrepayment);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					if (!CustomersDAO.getInstance().isOverCreditLimit(obj.getCustomer(), organizationSetup.getSetupDate(), null, obj.getAmountAfterTaxAndDiscount())) {
						RunningNumberDAO.getInstance().updateSalesOrderNumber(session);
						SalesOrderDAO.getInstance().save(obj, session);
						if (customerPrepayment!=null) {
							RunningNumberDAO.getInstance().updateCustomerPrepaymentNumber(session);
							PrepaymentToVendorDAO.getInstance().save(customerPrepayment);
						}
						transaction.commit();
					}
				} else {
					transaction = session.beginTransaction();
					// update status
					if (!CustomersDAO.getInstance().isOverCreditLimit(obj.getCustomer(), organizationSetup.getSetupDate(), obj.getOrderDate(), 0)) {
						boolean isClosed = true;
						double received = 0;
						Iterator iterator = obj.getSalesOrderDetails().iterator();
						while (iterator.hasNext()) {
							SalesOrderDetail salesOrderDetail = (SalesOrderDetail)iterator.next();
							received = salesOrderDetail.getDeliveryQuantity();
							if (salesOrderDetail.getQuantity()!=(salesOrderDetail.getDeliveryQuantity()-salesOrderDetail.getReturQuantity())) isClosed = false;
						}
						if (isClosed)obj.setDeliveryOrderStatus(CommonConstants.CLOSE);
						else {
							if (received>0) obj.setDeliveryOrderStatus(CommonConstants.PARTIAL);
							else obj.setDeliveryOrderStatus(CommonConstants.OPEN);
						}
						SalesOrderDAO.getInstance().getSession().merge(obj);
						transaction.commit();
					}
				}
				// remove session
				httpSession.removeAttribute("salesOrder");
				httpSession.removeAttribute("customerPrepayment");
				// finish
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
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
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
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
					List salesmanLst = SalesmanDAO.getInstance().getSession().createCriteria(Salesman.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("FullName")).list();
					request.setAttribute("salesmanLst", salesmanLst);
					List termOfPaymentLst = TermOfPaymentDAO.getInstance().getSession().createCriteria(TermOfPayment.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("termOfPaymentLst", termOfPaymentLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				SalesOrderDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?salesOrderId="+form.getLong("salesOrderId"));
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
	private ActionForward performProjectDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		SalesOrderForm form = (SalesOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			SalesOrder salesOrder = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
			request.setAttribute("salesOrder", salesOrder);
			if (CustomersDAO.getInstance().isOverCreditLimit(salesOrder.getCustomer(), organizationSetup.getSetupDate(), salesOrder.getOrderDate(), 0)) {
				ActionMessages errors = new ActionMessages();
				errors.add("errorOverLimit", new ActionMessage("error.over.limit"));
				saveErrors(request,errors);
			}
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				SalesOrderDAO.getInstance().closeSessionForReal();
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
	private ActionForward performProjectPrinted(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		SalesOrderForm form = (SalesOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			SalesOrder salesOrder = SalesOrderDAO.getInstance().get(form.getLong("salesOrderId"));
			
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
			cell = new Cell(new Phrase("No : "+salesOrder.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tanggal : "+salesOrder.getFormatedOrderDate(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tlp. "+users.getOrganization().getTelephone()+" - Fax. "+users.getOrganization().getFax(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("PO : "+salesOrder.getPurchaseOrder(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tgl kirim : "+salesOrder.getFormatedDeliveryDate(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Customer : "+salesOrder.getCustomer().getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Alamat : "+salesOrder.getCustomer().getAddress()+" "+salesOrder.getCustomer().getCity()+" "+salesOrder.getCustomer().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Term of Payment : "+salesOrder.getTermOfPayment()!=null?salesOrder.getTermOfPayment().getName():"-", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);			
			
			document.add(table1);
			
			Table table2 = new Table(6);
			table2.setWidth(100);
			table2.setCellsFitPage(true);
			float[] b = {5,30,25,10,15,15};
			table2.setWidths(b);
			table2.setBorder(Rectangle.NO_BORDER);
			table2.setCellsFitPage(true);
			table2.setBorderWidth(1);
			table2.setPadding(1);
			table2.setSpacing(0);
			
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase("SURAT PESANAN", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(6);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(3);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Pembayaran : Tunai/Kredit "+salesOrder.getCreditLimit()+" hari", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setColspan(3);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("PART",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
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
			Iterator iterator = salesOrder.getSalesOrderDetails().iterator();
			while (iterator.hasNext()) {
			    SalesOrderDetail detail = (SalesOrderDetail)iterator.next();
			    cell = new Cell(new Phrase(++j+".", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getId().getItem().getCode(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getDescription(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
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
			cell = new Cell(new Phrase("SUBTOTAL", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(salesOrder.getFormatedSalesOrderDetailAmount(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("DISCOUNT", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(salesOrder.getFormatedAmountDiscount(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("PPN", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(salesOrder.getFormatedAmountTax(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("GRAND TOTAL", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(salesOrder.getFormatedAmountAfterTaxAndDiscount(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("Salesman : "+(salesOrder.getSalesman()!=null?salesOrder.getSalesman().getFullName():"-"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Description : "+salesOrder.getDescription(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(6);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(6);
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
				SalesOrderDAO.getInstance().closeSessionForReal();
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
	private ActionForward performProjectDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		SalesOrderForm form = (SalesOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			SalesOrderDAO.getInstance().delete(form.getLong("salesOrderId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				SalesOrderDAO.getInstance().closeSessionForReal();
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