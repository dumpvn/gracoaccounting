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
import com.mpe.financial.model.Department;
import com.mpe.financial.model.Inventory;
import com.mpe.financial.model.Item;
import com.mpe.financial.model.ItemUnit;
import com.mpe.financial.model.ItemVendor;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.PrepaymentToVendor;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.PurchaseOrder;
import com.mpe.financial.model.PurchaseOrderDetail;
import com.mpe.financial.model.PurchaseOrderDetailPK;
import com.mpe.financial.model.PurchaseRequest;
import com.mpe.financial.model.PurchaseRequestDetail;
import com.mpe.financial.model.Tax;
import com.mpe.financial.model.TermOfPayment;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.PrepaymentToVendorDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.PurchaseOrderDAO;
import com.mpe.financial.model.dao.PurchaseRequestDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.TaxDAO;
import com.mpe.financial.model.dao.TermOfPaymentDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.struts.form.PurchaseOrderForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class PurchaseOrderAction extends Action {
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
		PurchaseOrderForm purchaseOrderForm = (PurchaseOrderForm) form;
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
		PurchaseOrderForm form = (PurchaseOrderForm) actionForm;
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
			List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("vendorLst", vendorLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
			Criteria criteria = PurchaseOrderDAO.getInstance().getSession().createCriteria(PurchaseOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromPurchaseDate")!=null)criteria.add(Restrictions.ge("PurchaseDate", new Date(form.getCalendar("fromPurchaseDate").getTime().getTime())));
			if (form.getCalendar("toPurchaseDate")!=null)criteria.add(Restrictions.le("PurchaseDate", new Date(form.getCalendar("toPurchaseDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("vendorId")>0) criteria.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = PurchaseOrderDAO.getInstance().getSession().createCriteria(PurchaseOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromPurchaseDate")!=null)criteria.add(Restrictions.ge("PurchaseDate", new Date(form.getCalendar("fromPurchaseDate").getTime().getTime())));
			if (form.getCalendar("toPurchaseDate")!=null)criteria.add(Restrictions.le("PurchaseDate", new Date(form.getCalendar("toPurchaseDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("vendorId")>0) criteria.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("PURCHASEORDER",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("purchaseOrder");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				PurchaseOrderDAO.getInstance().closeSessionForReal();
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
		PurchaseOrderForm form = (PurchaseOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
				Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class).add(Restrictions.eq("Code", new String(form.getString("itemCode")))).uniqueResult();
				form.setString("itemId", item.getId());
			}
		  	OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			// remove
			PurchaseOrder obj = (PurchaseOrder)httpSession.getAttribute("purchaseOrder");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEPURCHASEORDERDETAIL")) {
				PurchaseOrderDetail removePurchaseOrderDetail = null;
				Iterator iterator = obj.getPurchaseOrderDetails().iterator();
				while (iterator.hasNext()) {
					PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator.next();
					if (form.getLong("itemId") == purchaseOrderDetail.getId().getItem().getId()) {
						removePurchaseOrderDetail = purchaseOrderDetail;
					}
				}
				if (removePurchaseOrderDetail!=null) {
					Set set = obj.getPurchaseOrderDetails();
					set.remove(removePurchaseOrderDetail);
					obj.setPurchaseOrderDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("purchaseOrder", obj);
			}
			// relationships
			List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemUnitLst", itemUnitLst);
			List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("vendorLst", vendorLst);
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("projectLst", projectLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
			List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("currencyLst", currencyLst);
			List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Ap", Boolean.TRUE))
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("taxLst", taxLst);
			List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("locationLst", locationLst);
			List termOfPaymentLst = TermOfPaymentDAO.getInstance().getSession().createCriteria(TermOfPayment.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("termOfPaymentLst", termOfPaymentLst);
			if (form.getLong("purchaseOrderId") == 0) {
				form.setString("purchaseOrderId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getPurchaseOrderNumber());
				form.setCurentCalendar("purchaseDate");
				if (obj!=null) {
					form.setString("purchaseOrderId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setString("discountAmount",obj.getDiscountAmount());
					form.setString("discountProcent",obj.getDiscountProcent());
					if (obj.getNumber()!=null && obj.getNumber().length()>0)form.setString("number",obj.getNumber());
					else form.setString("number", RunningNumberDAO.getInstance().getPurchaseOrderNumber());
					form.setString("taxAmount",obj.getTaxAmount());
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
					if (obj.getPurchaseDate()!=null) form.setCalendar("purchaseDate",obj.getPurchaseDate());
					else form.setCurentCalendar("purchaseDate");
					form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
					form.setString("departmentId",obj.getDepartment()!=null?obj.getDepartment().getId():0);
					form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
					//form.setString("creditLimit",obj.getCreditLimit());
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					Set purchaseOrderDetailLst = obj.getPurchaseOrderDetails();
					request.setAttribute("purchaseOrderDetailLst", purchaseOrderDetailLst);
					if (VendorsDAO.getInstance().isOverCreditLimit(obj.getVendor(), organizationSetup.getSetupDate(), null, obj.getAmountAfterTaxAndDiscount())) {
						ActionMessages errors = new ActionMessages();
						errors.add("errorOverLimit", new ActionMessage("error.over.limit"));
						saveErrors(request,errors);
					}
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = PurchaseOrderDAO.getInstance().get(form.getLong("purchaseOrderId"));
					httpSession.setAttribute("purchaseOrder",obj);
				}
				form.setString("purchaseOrderId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("discountAmount",obj.getDiscountAmount());
				form.setString("discountProcent",obj.getDiscountProcent());
				form.setString("number",obj.getNumber());
				form.setString("taxAmount",obj.getTaxAmount());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
				form.setString("departmentId",obj.getDepartment()!=null?obj.getDepartment().getId():0);
				form.setCalendar("purchaseDate",obj.getPurchaseDate());
				form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
				form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
				//form.setString("creditLimit",obj.getCreditLimit());
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set purchaseOrderDetailLst = obj.getPurchaseOrderDetails();
				request.setAttribute("purchaseOrderDetailLst", purchaseOrderDetailLst);
				if (VendorsDAO.getInstance().isOverCreditLimit(obj.getVendor(), organizationSetup.getSetupDate(), obj.getPurchaseDate(), 0)) {
					ActionMessages errors = new ActionMessages();
					errors.add("errorOverLimit", new ActionMessage("error.over.limit"));
					saveErrors(request,errors);
				}
			}
			if (form.getLong("itemId") > 0) {
				boolean isNewData = true;
				String sql = "" +
						"select a.purchase_tax_id as TaxId from item_vendor a where a.item_id="+form.getLong("itemId")+" " +
						"union " +
						"select a.purchase_tax_id as TaxId from item a where a.item_id="+form.getLong("itemId");
				List list = ItemDAO.getInstance().getSession().createSQLQuery(sql).addScalar("TaxId", Hibernate.LONG).list();
				List taxDetailLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Ap", Boolean.TRUE))
					.add(Restrictions.in("Id", list))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("taxDetailLst", taxDetailLst);
				if (obj!=null && obj.getPurchaseOrderDetails()!=null) {
					Iterator iterator = obj.getPurchaseOrderDetails().iterator();
					while (iterator.hasNext()) {
						PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator.next();
						if (form.getLong("itemId") == purchaseOrderDetail.getId().getItem().getId()) {
							isNewData = false;
							form.setString("itemCode", purchaseOrderDetail.getId().getItem().getCode());
							form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), purchaseOrderDetail.getPrice()));
							form.setString("quantity", purchaseOrderDetail.getQuantity());
							form.setString("itemDescription", purchaseOrderDetail.getDescription());
							form.setString("itemUnitId", purchaseOrderDetail.getItemUnit()!=null?purchaseOrderDetail.getItemUnit().getId():0);
							form.setString("taxDetailId", purchaseOrderDetail.getTax()!=null?purchaseOrderDetail.getTax().getId():0);
							form.setString("taxDetailAmount", purchaseOrderDetail.getTaxAmount());
							form.setString("purchaseOrderDetailCurrencyId", purchaseOrderDetail.getCurrency()!=null?purchaseOrderDetail.getCurrency().getId():0);
							form.setString("itemDiscountAmount", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), purchaseOrderDetail.getDiscountAmount()));
							form.setString("itemDiscountProcent", purchaseOrderDetail.getDiscountProcent());
						}
					}
				}
				if (isNewData) {
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					if (inventory!=null) {
						if (inventory.getItemVendors()!=null) {
							Iterator iterator2 = inventory.getItemVendors().iterator();
							boolean isRightVendor = false;
							while (iterator2.hasNext()) {
								ItemVendor itemVendor = (ItemVendor)iterator2.next();
								if (itemVendor.getId().getVendor().getId()==form.getLong("vendorId")) {
									form.setString("itemCode", inventory.getCode());
									form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), itemVendor.getCostPrice()));
									form.setString("purchaseOrderDetailCurrencyId", itemVendor.getCurrency().getId());
									form.setString("taxDetailId", itemVendor.getPurchaseTax()!=null?itemVendor.getPurchaseTax().getId():0);
									form.setString("taxDetailAmount", itemVendor.getPurchaseTax()!=null?itemVendor.getPurchaseTax().getQuantity():0);
									isRightVendor = true;
								}
							}
							if (!isRightVendor) {
								form.setString("itemCode", inventory.getCode());
								form.setString("taxDetailId", inventory.getPurchaseTax()!=null?inventory.getPurchaseTax().getId():0);
								form.setString("taxDetailAmount", inventory.getPurchaseTax()!=null?inventory.getPurchaseTax().getQuantity():0);
								form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), inventory.getCostPrice()));
								form.setString("purchaseOrderDetailCurrencyId", inventory.getCurrency()!=null?inventory.getCurrency().getId():form.getLong("currencyId"));
							}
						} else {
							form.setString("itemCode", inventory.getCode());
							form.setString("taxDetailId", inventory.getPurchaseTax()!=null?inventory.getPurchaseTax().getId():0);
							form.setString("taxDetailAmount", inventory.getPurchaseTax()!=null?inventory.getPurchaseTax().getQuantity():0);
							form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), inventory.getCostPrice()));
							form.setString("purchaseOrderDetailCurrencyId", inventory.getCurrency()!=null?inventory.getCurrency().getId():form.getLong("currencyId"));
						}
						form.setString("itemUnitId", inventory.getItemUnit()!=null?inventory.getItemUnit().getId():0);
					}
				}
			}
			request.setAttribute("purchaseOrderDetailAmount", obj!=null?obj.getFormatedPurchaseOrderDetailAmount():"-");
			request.setAttribute("amountTax", obj!=null?obj.getFormatedAmountTax():"-");
			request.setAttribute("amountDiscount", obj!=null?obj.getFormatedAmountDiscount():"-");
			request.setAttribute("amountAfterDiscount", obj!=null?obj.getFormatedAmountAfterDiscount():"-");
			request.setAttribute("amountAfterTaxAndDiscount", obj!=null?obj.getFormatedAmountAfterTaxAndDiscount():"-");
		}catch(Exception ex) {
			try {
				List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("itemUnitLst", itemUnitLst);
				List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("vendorLst", vendorLst);
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("departmentLst", departmentLst);
				List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("currencyLst", currencyLst);
				List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Ap", Boolean.TRUE))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("taxLst", taxLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
				List termOfPaymentLst = TermOfPaymentDAO.getInstance().getSession().createCriteria(TermOfPayment.class)
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("termOfPaymentLst", termOfPaymentLst);
			}catch(Exception exx) {
			}
			ex.printStackTrace();
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				PurchaseOrderDAO.getInstance().closeSessionForReal();
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
		PurchaseOrderForm form = (PurchaseOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = PurchaseOrderDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("purchaseOrder");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			PurchaseOrder obj = (PurchaseOrder)httpSession.getAttribute("purchaseOrder");
			PrepaymentToVendor prepaymentToVendor = (PrepaymentToVendor)httpSession.getAttribute("prepaymentToVendor");
			if (form.getLong("purchaseOrderId") == 0) {
				obj = (PurchaseOrder)PurchaseOrderDAO.getInstance().getSession().createCriteria(PurchaseOrder.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (PurchaseOrder)httpSession.getAttribute("purchaseOrder");
					if (obj==null) obj = new PurchaseOrder();
					obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					obj.setDescription(form.getString("description"));
					obj.setDiscountAmount(form.getDouble("discountAmount"));
					obj.setDiscountProcent(form.getDouble("discountProcent"));
					obj.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("purchaseDate"))));
					obj.setNumber(form.getString("number"));
					obj.setOrganization(users.getOrganization());
					Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
					obj.setProject(project);
					Department department = DepartmentDAO.getInstance().get(form.getLong("departmentId"));
					obj.setDepartment(department);
					if (form.getCalendar("purchaseDate")==null) form.setCurentCalendar("purchaseDate");
					obj.setPurchaseDate(form.getCalendar("purchaseDate")!=null?form.getCalendar("purchaseDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					obj.setReceivingStatus(CommonConstants.OPEN);
					obj.setPaymentToVendorStatus(CommonConstants.OPEN);
					Tax tax = TaxDAO.getInstance().get(form.getLong("taxId"));
					obj.setTax(tax);
					if (tax!=null)obj.setTaxAmount(tax.getQuantity());
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(vendors);
					TermOfPayment termOfPayment = TermOfPaymentDAO.getInstance().get(form.getLong("termOfPaymentId"));
					obj.setTermOfPayment(termOfPayment);
					//obj.setCreditLimit(vendors!=null?vendors.getCreditLimit():0);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					Set set = new LinkedHashSet();
					set.add(obj);
					// cek prepayment
					if (form.getDouble("prepaymentAmount")>0) {
						if (prepaymentToVendor==null) prepaymentToVendor = new PrepaymentToVendor();
						prepaymentToVendor.setCurrency(currency);
						prepaymentToVendor.setExchangeRate(obj.getExchangeRate());
						prepaymentToVendor.setNumber(RunningNumberDAO.getInstance().getPrepaymentToVendorNumber());
						prepaymentToVendor.setOrganization(users.getOrganization());
						prepaymentToVendor.setPosted(false);
						prepaymentToVendor.setPrepaymentDate(obj.getPurchaseDate());
						prepaymentToVendor.setPurchaseOrder(obj);
						prepaymentToVendor.setStatus(CommonConstants.OPEN);
						prepaymentToVendor.setVendor(obj.getVendor());
						prepaymentToVendor.setProject(obj.getProject());
						prepaymentToVendor.setDepartment(obj.getDepartment());
					}
					// request
					if (form.getLong("purchaseRequestId")>0) {
				    PurchaseRequest purchaseRequest = PurchaseRequestDAO.getInstance().get(form.getLong("purchaseRequestId"));
				    obj.setCurrency(purchaseRequest.getCurrency());
						// default number of digit
						obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
						obj.setDescription(purchaseRequest.getDescription());
						obj.setDiscountAmount(purchaseRequest.getDiscountAmount());
						obj.setDiscountProcent(purchaseRequest.getDiscountProcent());
						obj.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("purchaseDate"))));
						obj.setOrganization(users.getOrganization());
						obj.setStatus(CommonConstants.OPEN);
						obj.setReceivingStatus(CommonConstants.OPEN);
						obj.setPaymentToVendorStatus(CommonConstants.OPEN);
						obj.setVendor(purchaseRequest.getVendor());
						obj.setProject(purchaseRequest.getProject());
						obj.setLocation(purchaseRequest.getLocation());
						// if rfq
						obj.setPurchaseRequest(purchaseRequest);
						if (purchaseRequest.getVendor()==null) {
						    //obj.setCreditLimit(vendors!=null?vendors.getCreditLimit():0);
						} else {
						    //obj.setCreditLimit(purchaseRequest.getVendor().getCreditLimit());
						}
						obj.setCreateBy(users);
						Set set2 = obj.getPurchaseOrderDetails();
						if (set2==null) set2 = new LinkedHashSet();
						Iterator iterator = purchaseRequest.getPurchaseRequestDetails().iterator();
						while (iterator.hasNext()) {
						    PurchaseRequestDetail purchaseRequestDetail = (PurchaseRequestDetail)iterator.next();
						    PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
							purchaseOrderDetail.setItemUnit(purchaseRequestDetail.getItemUnit());
							PurchaseOrderDetailPK purchaseOrderDetailPK = new PurchaseOrderDetailPK();
							purchaseOrderDetailPK.setItem(purchaseRequestDetail.getId().getItem());
							purchaseOrderDetailPK.setPurchaseOrder(obj);
							purchaseOrderDetail.setDescription(purchaseRequestDetail.getDescription());
							purchaseOrderDetail.setId(purchaseOrderDetailPK);
							purchaseOrderDetail.setCurrency(purchaseRequestDetail.getCurrency());
							purchaseOrderDetail.setPrice(purchaseRequestDetail.getPrice());
							//purchaseOrderDetail.setExchangeRate(purchaseRequestDetail.getExchangeRate());
							//log.info("Cur : "+currency.getName());
							purchaseOrderDetail.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, obj.getCurrency(), organizationSetup, form.getCalendar("purchaseDate"))));
							purchaseOrderDetail.setQuantity(purchaseRequestDetail.getQuantity());
							purchaseOrderDetail.setUnitConversion(purchaseRequestDetail.getUnitConversion());
							set2.add(purchaseOrderDetail);
						}
						obj.setPurchaseOrderDetails(set2);
					}
				} else {
					List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("itemUnitLst", itemUnitLst);
					List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("vendorLst", vendorLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Ap", Boolean.TRUE))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
					List termOfPaymentLst = TermOfPaymentDAO.getInstance().getSession().createCriteria(TermOfPayment.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("termOfPaymentLst", termOfPaymentLst);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = PurchaseOrderDAO.getInstance().get(form.getLong("purchaseOrderId"));
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				obj.setDescription(form.getString("description"));
				obj.setDiscountAmount(form.getDouble("discountAmount"));
				obj.setDiscountProcent(form.getDouble("discountProcent"));
				obj.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("purchaseDate"))));
				obj.setNumber(form.getString("number"));
				obj.setOrganization(users.getOrganization());
				Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
				obj.setProject(project);
				Department department = DepartmentDAO.getInstance().get(form.getLong("departmentId"));
				obj.setDepartment(department);
				obj.setPurchaseDate(form.getCalendar("purchaseDate")!=null?form.getCalendar("purchaseDate").getTime():null);
				//obj.setStatus("OPEN");
				//obj.setReceivingStatus("OPEN");
				Tax tax = TaxDAO.getInstance().get(form.getLong("taxId"));
				obj.setTax(tax);
				if (tax!=null)obj.setTaxAmount(tax.getQuantity());
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
				obj.setVendor(vendors);
				TermOfPayment termOfPayment = TermOfPaymentDAO.getInstance().get(form.getLong("termOfPaymentId"));
				obj.setTermOfPayment(termOfPayment);
				//obj.setCreditLimit(vendors!=null?vendors.getCreditLimit():0);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("purchaseOrderId"));
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDPURCHASEORDERDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("quantity")>0) {
					PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
					//Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					purchaseOrderDetail.setItemUnit(itemUnit);
					PurchaseOrderDetailPK purchaseOrderDetailPK = new PurchaseOrderDetailPK();
					purchaseOrderDetailPK.setItem(inventory);
					purchaseOrderDetailPK.setPurchaseOrder(obj);
					purchaseOrderDetail.setId(purchaseOrderDetailPK);
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("purchaseOrderDetailCurrencyId")>0?form.getLong("purchaseOrderDetailCurrencyId"):form.getLong("currencyId"));
					purchaseOrderDetail.setCurrency(currency);
					Tax taxDetail = TaxDAO.getInstance().get(form.getLong("taxDetailId"));
					purchaseOrderDetail.setTax(taxDetail);
					purchaseOrderDetail.setTaxAmount(taxDetail!=null?taxDetail.getQuantity():0);
					//log.info("Cur : "+currency.getName());
					purchaseOrderDetail.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, obj.getCurrency(), organizationSetup, form.getCalendar("purchaseDate"))));
					purchaseOrderDetail.setPrice(form.getDouble("price"));
					purchaseOrderDetail.setQuantity(form.getDouble("quantity"));
					purchaseOrderDetail.setDiscountAmount(form.getDouble("itemDiscountAmount"));
					purchaseOrderDetail.setDiscountProcent(form.getDouble("itemDiscountProcent"));
					purchaseOrderDetail.setDescription(form.getString("itemDescription"));
					purchaseOrderDetail.setUnitConversion(ItemUnitDAO.getInstance().getConversion(itemUnit, inventory.getItemUnit(), inventory));
					Set set = obj.getPurchaseOrderDetails();
					if (set==null) set = new LinkedHashSet();
					PurchaseOrderDetail removePurchaseOrderDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						PurchaseOrderDetail purchaseOrderDetail2 = (PurchaseOrderDetail)iterator.next();
						if (form.getLong("itemId")==purchaseOrderDetail2.getId().getItem().getId()) {
							removePurchaseOrderDetail = purchaseOrderDetail2;
						}
					}
					if (removePurchaseOrderDetail!=null) {
						set.remove(removePurchaseOrderDetail);
						set.add(purchaseOrderDetail);
					} else {
						set.add(purchaseOrderDetail);
					}
					obj.setPurchaseOrderDetails(set);
				}
			}
			// save to session
			httpSession.setAttribute("purchaseOrder", obj);
			httpSession.setAttribute("prepaymentToVendor", prepaymentToVendor);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updatePurchaseOrderNumber(session);
					// update status
					/*boolean isClosed = true;
					Iterator iterator = obj.getPurchaseOrderDetails().iterator();
					while (iterator.hasNext()) {
						PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator.next();
						if (purchaseOrderDetail.getQuantity()!=(purchaseOrderDetail.getReceiveQuantity()-purchaseOrderDetail.getReturQuantity())) isClosed = false;
					}*/
					//if (isClosed)obj.setReceivingStatus(CommonConstants.CLOSE);
					//else obj.setReceivingStatus(CommonConstants.PARTIAL);
					if (!VendorsDAO.getInstance().isOverCreditLimit(obj.getVendor(), organizationSetup.getSetupDate(), null, obj.getAmountAfterTaxAndDiscount())) {
						PurchaseOrderDAO.getInstance().save(obj, session);
						if (prepaymentToVendor!=null) {
							RunningNumberDAO.getInstance().updatePrepaymentToVendorNumber(session);
							PrepaymentToVendorDAO.getInstance().save(prepaymentToVendor);
						}
						transaction.commit();
					}
				} else {
					transaction = session.beginTransaction();
					// update status
					if (!VendorsDAO.getInstance().isOverCreditLimit(obj.getVendor(), organizationSetup.getSetupDate(), obj.getPurchaseDate(), 0)) {
						boolean isClosed = true;
						double received = 0;
						Iterator iterator = obj.getPurchaseOrderDetails().iterator();
						while (iterator.hasNext()) {
							PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator.next();
							received = purchaseOrderDetail.getReceiveQuantity();
							if (purchaseOrderDetail.getQuantity()!=(purchaseOrderDetail.getReceiveQuantity()-purchaseOrderDetail.getReturQuantity())) isClosed = false;
						}
						if (isClosed)obj.setReceivingStatus(CommonConstants.CLOSE);
						else {
							if (received>0) obj.setReceivingStatus(CommonConstants.PARTIAL);
							else obj.setReceivingStatus(CommonConstants.OPEN);
						}
						PurchaseOrderDAO.getInstance().getSession().merge(obj);
						transaction.commit();
					}
				}
				// remove session
				httpSession.removeAttribute("purchaseOrder");
				httpSession.removeAttribute("prepaymentToVendor");
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
					List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("vendorLst", vendorLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Ap", Boolean.TRUE))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
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
				PurchaseOrderDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?purchaseOrderId="+form.getLong("purchaseOrderId"));
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
		PurchaseOrderForm form = (PurchaseOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			PurchaseOrder purchaseOrder = PurchaseOrderDAO.getInstance().get(form.getLong("purchaseOrderId"));
			request.setAttribute("purchaseOrder", purchaseOrder);
			if (VendorsDAO.getInstance().isOverCreditLimit(purchaseOrder.getVendor(), organizationSetup.getSetupDate(), purchaseOrder.getPurchaseDate(), 0)) {
				ActionMessages errors = new ActionMessages();
				errors.add("errorOverLimit", new ActionMessage("error.over.limit"));
				saveErrors(request,errors);
			}
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				PurchaseOrderDAO.getInstance().closeSessionForReal();
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
		PurchaseOrderForm form = (PurchaseOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			PurchaseOrderDAO.getInstance().delete(form.getLong("purchaseOrderId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				PurchaseOrderDAO.getInstance().closeSessionForReal();
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
	private ActionForward performPrinted(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		PurchaseOrderForm form = (PurchaseOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		  String font = "HELVETICA";
	    try {
				ResourceBundle prop = ResourceBundle.getBundle("resource.ApplicationResources");
				font = prop.getString("font");
			}catch(Exception exx) {
			}
		    
			PurchaseOrder purchaseOrder = PurchaseOrderDAO.getInstance().get(form.getLong("purchaseOrderId"));
			
			// pdf
	    // write to pdf document
			Document document = new Document(PageSize.A4,36,36,36,36);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			  
			// footer page
			HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(font, 8, Font.BOLD)), true);
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
			
			Cell cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(font, 12, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tanggal : "+purchaseOrder.getFormatedPurchaseDate(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Kepada : "+purchaseOrder.getVendor().getCompany(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tlp. "+users.getOrganization().getTelephone()+" - Fax. "+users.getOrganization().getFax(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Telp. : "+purchaseOrder.getVendor().getTelephone(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Fax. : "+purchaseOrder.getVendor().getFax(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			//cell = new Cell(new Phrase("Attn. : "+purchaseOrder.getVendor().getContactPerson(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell = new Cell(new Phrase("Attn. : "+"-", FontFactory.getFont(font, 10, Font.NORMAL)));
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
			
			cell = new Cell(new Phrase(" ", FontFactory.getFont(font, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(9);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(font, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(9);
			table2.addCell(cell);
			cell = new Cell(new Phrase("PURCHASE ORDER", FontFactory.getFont(font, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(9);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(font, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(9);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(font, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(9);
			table2.addCell(cell);
			cell = new Cell(new Phrase("NO. : "+purchaseOrder.getNumber(), FontFactory.getFont(font, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(9);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("NO",FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("MERK",FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("ARTICLE",FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("WARNA",FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("KET",FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("SIZE",FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("QTY",FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("SATUAN",FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			table2.endHeaders();
			
			int j = 0;
			Iterator iterator = purchaseOrder.getPurchaseOrderDetails().iterator();
			while (iterator.hasNext()) {
			    PurchaseOrderDetail detail = (PurchaseOrderDetail)iterator.next();
			    cell = new Cell(new Phrase(++j+".", FontFactory.getFont(font, 6, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase("", FontFactory.getFont(font, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getId().getItem().getCode(), FontFactory.getFont(font, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase("", FontFactory.getFont(font, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getDescription(), FontFactory.getFont(font, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase("", FontFactory.getFont(font, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getQuantity()+" "+detail.getItemUnit().getSymbol(), FontFactory.getFont(font, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getFormatedPrice(), FontFactory.getFont(font, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getFormatedPriceQuantity(), FontFactory.getFont(font, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
			}
			cell = new Cell(new Phrase("TOTAL", FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(8);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(purchaseOrder.getFormatedPurchaseOrderDetailAmount(), FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("TERBILANG : "+AmountSay.getSaying(purchaseOrder.getFormatedPurchaseOrderDetailAmount())+" "+purchaseOrder.getCurrency().getName(), FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(users.getOrganization().getCity()+", "+purchaseOrder.getFormatedPurchaseDate(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			
			for (int i=0; i<15; i++) {
					cell = new Cell(new Phrase(" ", FontFactory.getFont(font, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setColspan(9);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
			}
			cell = new Cell(new Phrase("(_______________) (_______________)", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Supplier Acc       Bag Pembelian   ", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Persyaratan : ", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("1. Tanggal pengiriman : hari dari tanggal PO", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("2. Pembayaran dilakukan 3 bulan setelah barang diterima", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("3. Mohon dicantumkan No PO pada invoice dam surat jalan untuk accounting dept.", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("4. PO tidak dapat diganti tanpa pemberitahuan tertulis dari kami", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("5. Jika tidak tepat waktu pengirimannya maka PO ditutup dan pengiriman selanjutnya tunggu PO baru", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("6. Semua biaya untuk retur barang dibebankan kepada supplier", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("7. Pengiriman barang harus PO full dan dilakukan selama jam kerja jika tidak di TOLAK", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("8. Barang yang cacat, luntur, dan atau tidak layak untuk dijual dikembalikan kepada supplier", FontFactory.getFont(font, 10, Font.NORMAL)));
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
				PurchaseOrderDAO.getInstance().closeSessionForReal();
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
		errors.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.global",ex.getMessage()));
		saveErrors(request,errors);
	}

}