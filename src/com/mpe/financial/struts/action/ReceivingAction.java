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
import com.mpe.financial.model.PrepaymentToVendor;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.PurchaseOrder;
import com.mpe.financial.model.PurchaseOrderDetail;
import com.mpe.financial.model.Receiving;
import com.mpe.financial.model.ReceivingDetail;
import com.mpe.financial.model.ReceivingDetailPK;
import com.mpe.financial.model.Tax;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.VendorBill;
import com.mpe.financial.model.VendorBillDetail;
import com.mpe.financial.model.VendorBillDetailPK;
import com.mpe.financial.model.VendorBillPrepayment;
import com.mpe.financial.model.VendorBillPrepaymentPK;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.Warehouse;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.PrepaymentToVendorDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.PurchaseOrderDAO;
import com.mpe.financial.model.dao.ReceivingDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.TaxDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.VendorBillDAO;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.model.dao.WarehouseDAO;
import com.mpe.financial.struts.form.ReceivingForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class ReceivingAction extends Action {
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
		ReceivingForm purchaseOrderForm = (ReceivingForm) form;
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
					} else if ("VENDORBILLSAVE".equalsIgnoreCase(action)) {
						forward = performVendorBillSave(mapping, form, request, response);
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
		ReceivingForm form = (ReceivingForm) actionForm;
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
			Criteria criteria = ReceivingDAO.getInstance().getSession().createCriteria(Receiving.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromReceivingDate")!=null)criteria.add(Restrictions.ge("ReceivingDate", new Date(form.getCalendar("fromReceivingDate").getTime().getTime())));
			if (form.getCalendar("toReceivingDate")!=null)criteria.add(Restrictions.le("ReceivingDate", new Date(form.getCalendar("toReceivingDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("vendorId")>0) criteria.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = ReceivingDAO.getInstance().getSession().createCriteria(Receiving.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromReceivingDate")!=null)criteria.add(Restrictions.ge("ReceivingDate", new Date(form.getCalendar("fromReceivingDate").getTime().getTime())));
			if (form.getCalendar("toReceivingDate")!=null)criteria.add(Restrictions.le("ReceivingDate", new Date(form.getCalendar("toReceivingDate").getTime().getTime())));
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
			request.setAttribute("RECEIVING",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("receiving");
			httpSession.removeAttribute("vendorBill");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ReceivingDAO.getInstance().closeSessionForReal();
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
		ReceivingForm form = (ReceivingForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
				Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class).add(Restrictions.eq("Code", new String(form.getString("itemCode")))).uniqueResult();
				form.setString("itemId", item.getId());
			}
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			// remove
			Receiving obj = (Receiving)httpSession.getAttribute("receiving");
			VendorBill vendorBill = (VendorBill)httpSession.getAttribute("vendorBill");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVERECEIVINGDETAIL")) {
				ReceivingDetail removeReceivingDetail = null;
				Iterator iterator = obj.getReceivingDetails().iterator();
				while (iterator.hasNext()) {
					ReceivingDetail receivingDetail = (ReceivingDetail)iterator.next();
					if (form.getLong("itemId") == receivingDetail.getId().getItem().getId()) {
						removeReceivingDetail = receivingDetail;
					}
				}
				if (removeReceivingDetail!=null) {
					Set set = obj.getReceivingDetails();
					set.remove(removeReceivingDetail);
					obj.setReceivingDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("receiving", obj);
			}
			// relationships
			List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemUnitLst", itemUnitLst);
			List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("vendorLst", vendorLst);
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
			List warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("warehouseLst", warehouseLst);
			if (form.getLong("receivingId") == 0) {
				List purchaseOrderLst = PurchaseOrderDAO.getInstance().getSession().createCriteria(PurchaseOrder.class)
					.add(Restrictions.eq("Vendor.Id", new Long(obj!=null?(obj.getVendor()!=null?obj.getVendor().getId():form.getLong("vendorId")):form.getLong("vendorId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("ReceivingStatus", new String(CommonConstants.CLOSE)))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
				request.setAttribute("purchaseOrderLst", purchaseOrderLst);
				//log.info("C : "+purchaseOrderLst.size());
				form.setString("receivingId",0);
				form.setString("vendorBillId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getReceivingNumber());
				if (form.getCalendar("receivingDate")==null) form.setCurentCalendar("receivingDate");
				if (form.getCalendar("taxDate")==null) form.setCurentCalendar("taxDate");
				form.setString("vendorBillNumber", RunningNumberDAO.getInstance().getVendorBillNumber());
				if (form.getString("isService").length()==0) form.setString("isService", "N");
				if (form.getString("type").length()==0) form.setString("type", "Normal");
				if (obj!=null) {
					form.setString("receivingId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setCalendar("receivingDate",obj.getReceivingDate());
					form.setString("purchaseOrderId",obj.getPurchaseOrder()!=null?obj.getPurchaseOrder().getId():0);
					form.setString("number",obj.getNumber());
					form.setString("isService",obj.isService()==true?"Y":"N");
					form.setString("type",obj.getType());
					form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					Set receivingDetailLst = obj.getReceivingDetails();
					request.setAttribute("receivingDetailLst", receivingDetailLst);
					// vendor bill
					if (vendorBill!=null) {
						form.setString("vendorBillId",vendorBill.getId());
						form.setString("taxSerialNumber",vendorBill.getTaxSerialNumber());
						form.setCalendar("taxDate",vendorBill.getTaxDate());
						form.setString("vendorBillNumber",vendorBill.getNumber());
						form.setString("voucher",vendorBill.getVoucher());
					}
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = ReceivingDAO.getInstance().get(form.getLong("receivingId"));
					httpSession.setAttribute("receiving",obj);
					httpSession.setAttribute("oldReceiving",obj);
					vendorBill = obj.getVendorBill();
					if (vendorBill!=null) httpSession.setAttribute("vendorBill",vendorBill);
				}
				form.setString("receivingId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("purchaseOrderId",obj.getPurchaseOrder()!=null?obj.getPurchaseOrder().getId():0);
				form.setString("number",obj.getNumber());
				form.setString("isService",obj.isService()==true?"Y":"N");
				form.setString("type",obj.getType());
				form.setCalendar("receivingDate",obj.getReceivingDate());
				form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
				List purchaseOrderLst = PurchaseOrderDAO.getInstance().getSession().createCriteria(PurchaseOrder.class)
					.add(Restrictions.eq("Vendor.Id", new Long(obj!=null?(obj.getVendor()!=null?obj.getVendor().getId():form.getLong("vendorId")):form.getLong("vendorId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.createCriteria("Receivings", "Receiving")
					.add(Restrictions.eq("Receiving.Id", new Long(obj.getId())))
					.list();
				request.setAttribute("purchaseOrderLst", purchaseOrderLst);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				Set receivingDetailLst = obj.getReceivingDetails();
				request.setAttribute("receivingDetailLst", receivingDetailLst);
				// vendorBill
				if (vendorBill!=null) {
				    form.setString("vendorBillId",vendorBill.getId());
					form.setString("taxSerialNumber",vendorBill.getTaxSerialNumber());
					form.setCalendar("taxDate",vendorBill.getTaxDate());
					form.setString("vendorBillNumber",vendorBill.getNumber());
					form.setString("voucher",vendorBill.getVoucher());
				}
			}
			if (form.getLong("itemId") > 0) {
				//boolean isNewData = true;
				if (obj!=null && obj.getReceivingDetails()!=null) {
					Iterator iterator = obj.getReceivingDetails().iterator();
					while (iterator.hasNext()) {
						ReceivingDetail receivingDetail = (ReceivingDetail)iterator.next();
						if (form.getLong("itemId") == receivingDetail.getId().getItem().getId()) {
							//isNewData = false;
							form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), receivingDetail.getPrice()));
							form.setString("quantity", receivingDetail.getQuantity());
							form.setString("itemCode", receivingDetail.getId().getItem().getCode());
							form.setString("itemDescription", receivingDetail.getDescription());
							form.setString("exchangeRate", receivingDetail.getExchangeRate());
							form.setString("taxDetailAmount", receivingDetail.getTaxAmount());
							form.setString("itemDiscountAmount", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), receivingDetail.getDiscountAmount()));
							form.setString("itemDiscountProcent", receivingDetail.getDiscountProcent());
							form.setString("taxDetailId", receivingDetail.getTax()!=null?receivingDetail.getTax().getId():0);
							form.setString("warehouseId", receivingDetail.getWarehouse()!=null?receivingDetail.getWarehouse().getId():0);
							form.setString("itemUnitId", Formater.getFormatedOutputForm(receivingDetail.getItemUnit()!=null?receivingDetail.getItemUnit().getId():0));
							form.setString("receivingDetailCurrencyId", Formater.getFormatedOutputForm(receivingDetail.getCurrency()!=null?receivingDetail.getCurrency().getId():0));
						}
					}
				}
			}
			request.setAttribute("receivingDetailAmount", obj!=null?obj.getFormatedReceivingDetailAmount():"-");
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
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("taxLst", taxLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
				List warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("warehouseLst", warehouseLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				ReceivingDAO.getInstance().closeSessionForReal();
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
		ReceivingForm form = (ReceivingForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = ReceivingDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("receiving");
				httpSession.removeAttribute("vendorBill");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			Receiving obj = (Receiving)httpSession.getAttribute("receiving");
			VendorBill vendorBill = (VendorBill)httpSession.getAttribute("vendorBill");
			if (form.getLong("receivingId") == 0) {
				obj = (Receiving)ReceivingDAO.getInstance().getSession().createCriteria(Receiving.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (Receiving)httpSession.getAttribute("receiving");
					PurchaseOrder purchaseOrder = PurchaseOrderDAO.getInstance().get(form.getLong("purchaseOrderId"));
					if (obj==null) {
						obj = new Receiving();
						obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
						obj.setPurchaseOrder(purchaseOrder);
						obj.setProject(purchaseOrder.getProject());
						obj.setDepartment(purchaseOrder.getDepartment());
						if (form.getLong("locationId")==0) form.setString("locationId", purchaseOrder.getLocation()!=null?purchaseOrder.getLocation().getId():0);
						Set set = obj.getReceivingDetails();
						if (set==null) set = new LinkedHashSet();
						Iterator iterator = purchaseOrder.getPurchaseOrderDetails().iterator();
						while (iterator.hasNext()) {
							PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator.next();
							if (purchaseOrderDetail.getQuantity()>(purchaseOrderDetail.getReceiveQuantity()-purchaseOrderDetail.getReturQuantity())) {
								ReceivingDetail receivingDetail = new ReceivingDetail();
								receivingDetail.setItemUnit(purchaseOrderDetail.getItemUnit());
								ReceivingDetailPK receivingDetailPK = new ReceivingDetailPK();
								receivingDetailPK.setItem(purchaseOrderDetail.getId().getItem());
								receivingDetailPK.setReceiving(obj);
								receivingDetail.setId(receivingDetailPK);
								receivingDetail.setCurrency(purchaseOrderDetail.getCurrency());
								receivingDetail.setExchangeRate(purchaseOrderDetail.getExchangeRate());
								receivingDetail.setPrice(purchaseOrderDetail.getPrice());
								receivingDetail.setTax(purchaseOrderDetail.getTax());
								receivingDetail.setTaxAmount(purchaseOrderDetail.getTaxAmount());
								receivingDetail.setDiscountAmount(purchaseOrderDetail.getDiscountAmount());
								receivingDetail.setDiscountProcent(purchaseOrderDetail.getDiscountProcent());
								receivingDetail.setDescription(purchaseOrderDetail.getDescription());
								receivingDetail.setQuantity(purchaseOrderDetail.getQuantity()-purchaseOrderDetail.getReceiveQuantity()+purchaseOrderDetail.getReturQuantity());
								receivingDetail.setUnitConversion(purchaseOrderDetail.getUnitConversion());
								set.add(receivingDetail);
							}
						}
						obj.setReceivingDetails(set);
					}
					//obj.setPurchaseOrder(purchaseOrder);
					obj.setDescription(form.getString("description"));
					obj.setNumber(form.getString("number"));
					obj.setOrganization(users.getOrganization());
					obj.setReceivingDate(form.getCalendar("receivingDate")!=null?form.getCalendar("receivingDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					obj.setService(form.getString("isService").equalsIgnoreCase("Y")?true:false);
					obj.setType(form.getString("type"));
					obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
					Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(vendors);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					obj.setCreateBy(users);
					obj.setVendorBillStatus(CommonConstants.CLOSE);
					obj.setCreateOn(form.getTimestamp("createOn"));
					// vendorBill
					if (vendorBill==null) vendorBill = new VendorBill();
					vendorBill.setTaxDate(form.getCalendar("taxDate")!=null?form.getCalendar("taxDate").getTime():null);
					vendorBill.setTaxSerialNumber(form.getString("taxSerialNumber"));
					vendorBill.setNumber(form.getString("vendorBillNumber"));
					vendorBill.setVoucher(form.getString("voucher"));
					// create new receiving
					if (form.getLong("purchaseOrderId")!=obj.getPurchaseOrder().getId()) {
						Set set = new LinkedHashSet();
						Iterator iterator = purchaseOrder.getPurchaseOrderDetails().iterator();
						while (iterator.hasNext()) {
							PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator.next();
							ReceivingDetail receivingDetail = new ReceivingDetail();
							receivingDetail.setItemUnit(purchaseOrderDetail.getItemUnit());
							ReceivingDetailPK receivingDetailPK = new ReceivingDetailPK();
							receivingDetailPK.setItem(purchaseOrderDetail.getId().getItem());
							receivingDetailPK.setReceiving(obj);
							receivingDetail.setId(receivingDetailPK);
							receivingDetail.setCurrency(purchaseOrderDetail.getCurrency());
							receivingDetail.setExchangeRate(purchaseOrderDetail.getExchangeRate());
							receivingDetail.setPrice(purchaseOrderDetail.getPrice());
							receivingDetail.setTax(purchaseOrderDetail.getTax());
							receivingDetail.setTaxAmount(purchaseOrderDetail.getTaxAmount());
							receivingDetail.setDiscountAmount(purchaseOrderDetail.getDiscountAmount());
							receivingDetail.setDiscountProcent(purchaseOrderDetail.getDiscountProcent());
							receivingDetail.setDescription(purchaseOrderDetail.getDescription());
							receivingDetail.setQuantity(purchaseOrderDetail.getQuantity()-purchaseOrderDetail.getReceiveQuantity()+purchaseOrderDetail.getReturQuantity());
							receivingDetail.setUnitConversion(purchaseOrderDetail.getUnitConversion());
							set.add(receivingDetail);
						}
						obj.setReceivingDetails(set);
						obj.setPurchaseOrder(purchaseOrder);
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
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
					List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("locationLst", locationLst);
					List warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("warehouseLst", warehouseLst);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = ReceivingDAO.getInstance().get(form.getLong("receivingId"));
				vendorBill = obj.getVendorBill();
				PurchaseOrder purchaseOrder = PurchaseOrderDAO.getInstance().get(form.getLong("purchaseOrderId"));
				obj.setPurchaseOrder(purchaseOrder);
				obj.setProject(purchaseOrder.getProject());
				obj.setDepartment(purchaseOrder.getDepartment());
				obj.setDescription(form.getString("description"));
				obj.setNumber(form.getString("number"));
				obj.setOrganization(users.getOrganization());
				obj.setReceivingDate(form.getCalendar("receivingDate")!=null?form.getCalendar("receivingDate").getTime():null);
				//obj.setStatus(CommonConstants.OPEN");
				Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
				obj.setVendor(vendors);
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				obj.setService(form.getString("isService").equalsIgnoreCase("Y")?true:false);
				obj.setType(form.getString("type"));
				if (obj.getVendorBill()==null) obj.setVendorBillStatus(CommonConstants.OPEN);
				else obj.setVendorBillStatus(CommonConstants.CLOSE);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("receivingId"));
				// vendorBill
				if (vendorBill==null) vendorBill = new VendorBill();
				vendorBill.setTaxDate(form.getCalendar("taxDate")!=null?form.getCalendar("taxDate").getTime():null);
				vendorBill.setTaxSerialNumber(form.getString("taxSerialNumber"));
				vendorBill.setNumber(form.getString("vendorBillNumber"));
				vendorBill.setVoucher(form.getString("voucher"));
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDRECEIVINGDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("quantity")>0) {
					ReceivingDetail receivingDetail = new ReceivingDetail();
					//Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					receivingDetail.setItemUnit(itemUnit);
					ReceivingDetailPK receivingDetailPK = new ReceivingDetailPK();
					receivingDetailPK.setItem(inventory);
					receivingDetailPK.setReceiving(obj);
					receivingDetail.setId(receivingDetailPK);
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("receivingDetailCurrencyId"));
					receivingDetail.setCurrency(currency);
					Tax tax = TaxDAO.getInstance().get(form.getLong("taxDetailId"));
					receivingDetail.setTax(tax);
					Warehouse warehouse = WarehouseDAO.getInstance().get(form.getLong("warehouseId"));
					receivingDetail.setWarehouse(warehouse);
					receivingDetail.setTaxAmount(form.getDouble("taxDetailAmount"));
					receivingDetail.setDiscountAmount(form.getDouble("itemDiscountAmount"));
					receivingDetail.setDiscountProcent(form.getDouble("itemDiscountProcent"));
					receivingDetail.setDescription(form.getString("itemDescription"));
					receivingDetail.setExchangeRate(form.getDouble("exchangeRate"));
					receivingDetail.setPrice(form.getDouble("price"));
					receivingDetail.setQuantity(form.getDouble("quantity"));
					receivingDetail.setUnitConversion(ItemUnitDAO.getInstance().getConversion(itemUnit, inventory.getItemUnit(), inventory));
					Set set = obj.getReceivingDetails();
					if (set==null) set = new LinkedHashSet();
					ReceivingDetail removeReceivingDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						ReceivingDetail receivingDetail2 = (ReceivingDetail)iterator.next();
						if (form.getLong("itemId")==receivingDetail2.getId().getItem().getId()) {
							removeReceivingDetail = receivingDetail2;
						}
					}
					if (removeReceivingDetail!=null) {
						set.remove(removeReceivingDetail);
						set.add(receivingDetail);
					} else {
						set.add(receivingDetail);
					}

					obj.setReceivingDetails(set);
				}
			}
			// save to session
			httpSession.setAttribute("receiving", obj);
			httpSession.setAttribute("vendorBill", vendorBill);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateReceivingNumber(session);
					ReceivingDAO.getInstance().save(obj, session);
					transaction.commit();
					form.setString("receivingId", obj.getId());
				} else {
					transaction = session.beginTransaction();
					ReceivingDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("receiving");
				httpSession.removeAttribute("oldReceiving");
				// finish
				ActionForward forward = mapping.findForward("vendor_bill");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?receivingId="+form.getLong("receivingId")+"&purchaseOrderId="+form.getLong("purchaseOrderId")+"&vendorBillId="+form.getLong("vendorBillId"));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
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
					List warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("warehouseLst", warehouseLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				ReceivingDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?receivingId="+form.getLong("receivingId"));
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
		ReceivingForm form = (ReceivingForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// update PO => receivingStatus!!!
			boolean isClosed = true;
			PurchaseOrder purchaseOrder = PurchaseOrderDAO.getInstance().get(form.getLong("purchaseOrderId"));
			if (purchaseOrder!=null) {
				Iterator iterator = purchaseOrder.getPurchaseOrderDetails().iterator();
				while (iterator.hasNext()) {
					PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator.next();
					if (purchaseOrderDetail.getQuantity()!=(purchaseOrderDetail.getReceiveQuantity()-purchaseOrderDetail.getReturQuantity())) isClosed = false;
				}
				if (isClosed)purchaseOrder.setReceivingStatus(CommonConstants.CLOSE);
				else purchaseOrder.setReceivingStatus(CommonConstants.PARTIAL);
				PurchaseOrderDAO.getInstance().update(purchaseOrder);
			}
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				ReceivingDAO.getInstance().closeSessionForReal();
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
		ReceivingForm form = (ReceivingForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			Receiving receiving = ReceivingDAO.getInstance().get(form.getLong("receivingId"));
			request.setAttribute("receiving", receiving);
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				ReceivingDAO.getInstance().closeSessionForReal();
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
		ReceivingForm form = (ReceivingForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			ReceivingDAO.getInstance().delete(form.getLong("receivingId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				ReceivingDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performVendorBillSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ReceivingForm form = (ReceivingForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = VendorBillDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			VendorBill obj = (VendorBill)httpSession.getAttribute("vendorBill");
			if (form.getLong("vendorBillId") == 0) {
				obj = (VendorBill)VendorBillDAO.getInstance().getSession().createCriteria(VendorBill.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (VendorBill)httpSession.getAttribute("vendorBill");
					if (obj==null) obj = new VendorBill();
					Receiving receiving = ReceivingDAO.getInstance().get(form.getLong("receivingId"));
					PurchaseOrder purchaseOrder = receiving!=null?receiving.getPurchaseOrder():null;
					if (purchaseOrder!=null) form.setString("purchaseOrderId", purchaseOrder.getId()); 
					obj.setReceiving(receiving);
					//obj.setDescription(form.getString("description"));
					//obj.setNumber(form.getString("number"));
					obj.setCurrency(purchaseOrder!=null?purchaseOrder.getCurrency():null);
					obj.setDiscountProcent(purchaseOrder!=null?purchaseOrder.getDiscountProcent():0);
					obj.setProject(purchaseOrder!=null?purchaseOrder.getProject():null);
					obj.setDepartment(purchaseOrder!=null?purchaseOrder.getDepartment():null);
					//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup);
					//obj.setExchangeRate(e);
					obj.setPosted(false);
					//obj.setReference(form.getString("reference"));
					obj.setTax(purchaseOrder!=null?purchaseOrder.getTax():null);
					obj.setTaxAmount(purchaseOrder!=null?purchaseOrder.getTaxAmount():0);
					obj.setOrganization(users.getOrganization());
					//obj.setBillDate(form.getCalendar("billDate")!=null?form.getCalendar("billDate").getTime():null);
					//obj.setBillDue(form.getCalendar("billDue")!=null?form.getCalendar("billDue").getTime():null);
					obj.setBillDate(receiving.getReceivingDate());
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(receiving.getReceivingDate());
					calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR) + purchaseOrder.getCreditLimit()));
					obj.setBillDue(calendar.getTime());
					obj.setStatus(CommonConstants.OPEN);
					double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, calendar);
					obj.setExchangeRate(e);
					obj.setPaymentToVendorStatus(CommonConstants.OPEN);
					//Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(receiving.getVendor());
					obj.setLocation(receiving.getLocation());
					obj.setCreateBy(users);
					obj.setCreateOn(receiving.getCreateOn());
					// create new receiving
					if (receiving!=null) {
						Set set = new LinkedHashSet();
						Iterator iterator = receiving.getReceivingDetails().iterator();
						while (iterator.hasNext()) {
							ReceivingDetail receivingDetail = (ReceivingDetail)iterator.next();
							VendorBillDetail vendorBillDetail = new VendorBillDetail();
							vendorBillDetail.setItemUnit(receivingDetail.getItemUnit());
							VendorBillDetailPK vendorBillDetailPK = new VendorBillDetailPK();
							vendorBillDetailPK.setItem(receivingDetail.getId().getItem());
							vendorBillDetailPK.setVendorBill(obj);
							vendorBillDetail.setId(vendorBillDetailPK);
							vendorBillDetail.setTax(receivingDetail.getTax());
							vendorBillDetail.setTaxAmount(receivingDetail.getTaxAmount());
							vendorBillDetail.setDiscountAmount(receivingDetail.getDiscountAmount());
							vendorBillDetail.setDiscountProcent(receivingDetail.getDiscountProcent());
							vendorBillDetail.setCurrency(receivingDetail.getCurrency());
							vendorBillDetail.setExchangeRate(receivingDetail.getExchangeRate());
							vendorBillDetail.setPrice(receivingDetail.getPrice());
							vendorBillDetail.setQuantity(receivingDetail.getQuantity());
							vendorBillDetail.setDescription(receivingDetail.getDescription());
							set.add(vendorBillDetail);
						}
						obj.setVendorBillDetails(set);
					}
					// get prepayment
					if (purchaseOrder!=null) {
						List prepaymentToVendorLst = PrepaymentToVendorDAO.getInstance().getSession().createCriteria(PrepaymentToVendor.class)
							.add(Restrictions.eq("Vendor.Id", new Long(receiving.getVendor().getId())))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
							.add(Restrictions.eq("PurchaseOrder.Id", new Long(purchaseOrder.getId())))
							.add(Restrictions.ne("VendorBillStatus", new String(CommonConstants.CLOSE))).list();
						Iterator iterator = prepaymentToVendorLst.iterator();
						Set set = new LinkedHashSet();
						if (prepaymentToVendorLst.size()>0) {
							while (iterator.hasNext()) {
								PrepaymentToVendor prepaymentToVendor = (PrepaymentToVendor)iterator.next();
								VendorBillPrepayment vendorBillPrepayment = new VendorBillPrepayment();
								// cek amount of bill
								if (prepaymentToVendor.getAmount()-prepaymentToVendor.getVendorBillPaymentAmount()>0 && obj.getDifferenceAmount()>0) {
									VendorBillPrepaymentPK vendorBillPrepaymentPK = new VendorBillPrepaymentPK();
									vendorBillPrepaymentPK.setPrepaymentToVendor(prepaymentToVendor);
									vendorBillPrepaymentPK.setVendorBill(obj);
									vendorBillPrepayment.setId(vendorBillPrepaymentPK);
									if (prepaymentToVendor.getAmount() <= obj.getDifferenceAmount()) {
										vendorBillPrepayment.setAmount(prepaymentToVendor.getAmount() - prepaymentToVendor.getVendorBillPaymentAmount());
									} else {
										vendorBillPrepayment.setAmount(obj.getDifferenceAmount());
									}
									set.add(vendorBillPrepayment);
								}
								obj.setVendorBillPrepayments(set);
							}
						} else {
							obj.setVendorBillPrepayments(set);
						}
					}
					// create journal
					Journal journal = new Journal();
					journal.setVendorBill(obj);
					journal.setCurrency(obj.getCurrency());
					journal.setExchangeRate(e);
					journal.setJournalDate(obj.getBillDate());
					journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
					journal.setOrganization(users.getOrganization());
					journal.setProject(purchaseOrder!=null?purchaseOrder.getProject():null);
					journal.setPosted(false);
					//journal.setReference(form.getString("reference"));
					journal.setVendor(receiving.getVendor());
					journal.setCreateBy(users);
					journal.setCreateOn(receiving.getCreateOn());
					// journal detail
					Set set = journal.getJournalDetails();
					if (set==null) set = new LinkedHashSet();
					// debit
					JournalDetail journalDetail = new JournalDetail();
					JournalDetailPK journalDetailPK = new JournalDetailPK();
					journalDetailPK.setChartOfAccount(organizationSetup.getInventoryAccount());
					journalDetailPK.setJournal(journal);
					journalDetail.setId(journalDetailPK);
					journalDetail.setDepartment(purchaseOrder!=null?purchaseOrder.getDepartment():null);
					journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==true?(obj.getVendorBillAfterDiscountAndTaxAndPrepayment()-obj.getVendorBillTaxAmount()):-(obj.getVendorBillAfterDiscountAndTaxAndPrepayment()-obj.getVendorBillTaxAmount()));
					set.add(journalDetail);
					// debit tax
					if (obj.getTax()!=null && obj.getTaxAmount()>0 && obj.getTax().getChartOfAccount()!=null) {
						JournalDetail journalDetail2 = new JournalDetail();
						JournalDetailPK journalDetailPK2 = new JournalDetailPK();
						journalDetailPK2.setChartOfAccount(obj.getTax().getChartOfAccount());
						journalDetailPK2.setJournal(journal);
						journalDetail2.setId(journalDetailPK2);
						journalDetail2.setDepartment(purchaseOrder!=null?purchaseOrder.getDepartment():null);
						journalDetail2.setAmount(obj.getTax().getChartOfAccount().isDebit()==true?obj.getVendorBillTaxAmount():-obj.getVendorBillTaxAmount());
						set.add(journalDetail2);
					}
					// credit
					JournalDetail journalDetail3 = new JournalDetail();
					JournalDetailPK journalDetailPK3 = new JournalDetailPK();
					journalDetailPK3.setJournal(journal);
					if (receiving.getVendor().getChartOfAccount()==null) {
						journalDetailPK3.setChartOfAccount(organizationSetup.getApAccount());
						journalDetail3.setAmount(organizationSetup.getApAccount().isDebit()==false?obj.getVendorBillAfterDiscountAndTaxAndPrepayment():-obj.getVendorBillAfterDiscountAndTaxAndPrepayment());
					} else {
						journalDetailPK3.setChartOfAccount(receiving.getVendor().getChartOfAccount());
						journalDetail3.setAmount(receiving.getVendor().getChartOfAccount().isDebit()==false?obj.getVendorBillAfterDiscountAndTaxAndPrepayment():-obj.getVendorBillAfterDiscountAndTaxAndPrepayment());
					}
					journalDetail3.setDepartment(purchaseOrder!=null?purchaseOrder.getDepartment():null);
					journalDetail3.setId(journalDetailPK3);
					set.add(journalDetail3);
					journal.setJournalDetails(set);
					obj.setJournal(journal);
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", obj.getNumber()));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = VendorBillDAO.getInstance().load(form.getLong("vendorBillId"));
				Receiving receiving = ReceivingDAO.getInstance().get(form.getLong("receivingId"));
				PurchaseOrder purchaseOrder = receiving!=null?receiving.getPurchaseOrder():null;
				if (purchaseOrder!=null) form.setString("purchaseOrderId", purchaseOrder.getId()); 
				obj.setReceiving(receiving);
				//obj.setDescription(form.getString("description"));
				//obj.setNumber(form.getString("number"));
				obj.setCurrency(purchaseOrder!=null?purchaseOrder.getCurrency():null);
				obj.setDiscountProcent(purchaseOrder!=null?purchaseOrder.getDiscountProcent():0);
				//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup);
				//obj.setExchangeRate(e);
				obj.setPosted(false);
				//obj.setReference(form.getString("reference"));
				obj.setProject(purchaseOrder!=null?purchaseOrder.getProject():null);
				obj.setTax(purchaseOrder!=null?purchaseOrder.getTax():null);
				obj.setTaxAmount(purchaseOrder!=null?purchaseOrder.getTaxAmount():0);
				obj.setOrganization(users.getOrganization());
				//obj.setBillDate(form.getCalendar("billDate")!=null?form.getCalendar("billDate").getTime():null);
				//obj.setBillDue(form.getCalendar("billDue")!=null?form.getCalendar("billDue").getTime():null);
				obj.setBillDate(receiving.getReceivingDate());
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(receiving.getReceivingDate());
				calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR) + purchaseOrder.getCreditLimit()));
				obj.setBillDue(calendar.getTime());
				double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, calendar);
				obj.setExchangeRate(e);
				//obj.setStatus(CommonConstants.OPEN);
				if (obj.getDifferenceAmount()==0) {
					obj.setPaymentToVendorStatus(CommonConstants.CLOSE);
				} else {
					if (obj.getPaymentToVendorAmount()==0) {
						obj.setPaymentToVendorStatus(CommonConstants.OPEN);
					} else obj.setPaymentToVendorStatus(CommonConstants.PARTIAL);
				}
				//Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
				obj.setVendor(receiving.getVendor());
				obj.setLocation(receiving.getLocation());
				//Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(receiving.getChangeBy());
				obj.setCreateOn(receiving.getCreateOn());
				obj.setChangeBy(users);
				obj.setChangeOn(receiving.getChangeOn());
				// create new receiving
				if (receiving!=null) {
				  obj.getVendorBillDetails().removeAll(obj.getVendorBillDetails());
					Set set = obj.getVendorBillDetails()!=null?obj.getVendorBillDetails():new LinkedHashSet();
					Iterator iterator = receiving.getReceivingDetails().iterator();
					while (iterator.hasNext()) {
						ReceivingDetail receivingDetail = (ReceivingDetail)iterator.next();
						VendorBillDetail vendorBillDetail = new VendorBillDetail();
						vendorBillDetail.setItemUnit(receivingDetail.getItemUnit());
						VendorBillDetailPK vendorBillDetailPK = new VendorBillDetailPK();
						vendorBillDetailPK.setItem(receivingDetail.getId().getItem());
						vendorBillDetailPK.setVendorBill(obj);
						vendorBillDetail.setId(vendorBillDetailPK);
						vendorBillDetail.setTax(receivingDetail.getTax());
						vendorBillDetail.setTaxAmount(receivingDetail.getTaxAmount());
						vendorBillDetail.setDiscountAmount(receivingDetail.getDiscountAmount());
						vendorBillDetail.setDiscountProcent(receivingDetail.getDiscountProcent());
						vendorBillDetail.setCurrency(receivingDetail.getCurrency());
						vendorBillDetail.setExchangeRate(receivingDetail.getExchangeRate());
						vendorBillDetail.setPrice(receivingDetail.getPrice());
						vendorBillDetail.setQuantity(receivingDetail.getQuantity());
						vendorBillDetail.setDescription(receivingDetail.getDescription());
						set.add(vendorBillDetail);
					}
					obj.setVendorBillDetails(set);
				}
				// get prepayment
				/*
				if (purchaseOrder!=null) {
					Set prepaymentToVendorLst = obj.getPaymentToVendors();
					Iterator iterator = prepaymentToVendorLst.iterator();
					Set set = new LinkedHashSet();
					if (prepaymentToVendorLst.size()>0) {
						while (iterator.hasNext()) {
							PrepaymentToVendor prepaymentToVendor = (PrepaymentToVendor)iterator.next();
							VendorBillPrepayment vendorBillPrepayment = new VendorBillPrepayment();
							// cek amount of bill
							if (prepaymentToVendor.getAmount()-prepaymentToVendor.getVendorBillPaymentAmount()>0 && obj.getAmount()>0) {
								VendorBillPrepaymentPK vendorBillPrepaymentPK = new VendorBillPrepaymentPK();
								vendorBillPrepaymentPK.setPrepaymentToVendor(prepaymentToVendor);
								vendorBillPrepaymentPK.setVendorBill(obj);
								vendorBillPrepayment.setId(vendorBillPrepaymentPK);
								if (prepaymentToVendor.getAmount() <= obj.getAmount()) {
									vendorBillPrepayment.setAmount(prepaymentToVendor.getAmount() - prepaymentToVendor.getVendorBillPaymentAmount());
								} else {
									vendorBillPrepayment.setAmount(obj.getAmount());
								}
								set.add(vendorBillPrepayment);
							}
							obj.setVendorBillPrepayments(set);
						}
					} else {
						obj.setVendorBillPrepayments(set);
					}
				}*/
				// create journal
				//log.info("A");
				Journal journal = obj.getJournal();
				journal.setVendorBill(obj);
				journal.setCurrency(obj.getCurrency());
				journal.setExchangeRate(e);
				journal.setJournalDate(obj.getBillDate());
				journal.setProject(purchaseOrder!=null?purchaseOrder.getProject():null);
				//journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
				journal.setOrganization(users.getOrganization());
				//journal.setPosted(false);
				//journal.setReference(form.getString("reference"));
				journal.setVendor(receiving.getVendor());
				
				journal.setCreateBy(users);
				journal.setCreateOn(receiving.getCreateOn());
				journal.setCreateBy(receiving.getCreateBy());
				journal.setChangeBy(users);
				journal.setChangeOn(receiving.getChangeOn());
				journal.getJournalDetails().removeAll(journal.getJournalDetails());
				// journal detail
				Set set = journal.getJournalDetails();
				// debit
				JournalDetail journalDetail = new JournalDetail();
				JournalDetailPK journalDetailPK = new JournalDetailPK();
				journalDetailPK.setChartOfAccount(organizationSetup.getInventoryAccount());
				journalDetailPK.setJournal(journal);
				journalDetail.setId(journalDetailPK);
				journalDetail.setDepartment(purchaseOrder!=null?purchaseOrder.getDepartment():null);
				journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==true?(obj.getVendorBillAfterDiscountAndTaxAndPrepayment()-obj.getVendorBillTaxAmount()):-(obj.getVendorBillAfterDiscountAndTaxAndPrepayment()-obj.getVendorBillTaxAmount()));
				set.add(journalDetail);
				// debit tax
				if (obj.getTax()!=null && obj.getTaxAmount()>0 && obj.getTax().getChartOfAccount()!=null) {
					JournalDetail journalDetail2 = new JournalDetail();
					JournalDetailPK journalDetailPK2 = new JournalDetailPK();
					journalDetailPK2.setChartOfAccount(obj.getTax().getChartOfAccount());
					journalDetailPK2.setJournal(journal);
					journalDetail2.setId(journalDetailPK2);
					journalDetail2.setDepartment(purchaseOrder!=null?purchaseOrder.getDepartment():null);
					journalDetail2.setAmount(obj.getTax().getChartOfAccount().isDebit()==true?obj.getVendorBillTaxAmount():-obj.getVendorBillTaxAmount());
					set.add(journalDetail2);
				}
				// credit
				JournalDetail journalDetail3 = new JournalDetail();
				JournalDetailPK journalDetailPK3 = new JournalDetailPK();
				journalDetailPK3.setJournal(journal);
				if (receiving.getVendor().getChartOfAccount()==null) {
					journalDetailPK3.setChartOfAccount(organizationSetup.getApAccount());
					journalDetail3.setAmount(organizationSetup.getApAccount().isDebit()==false?obj.getVendorBillAfterDiscountAndTaxAndPrepayment():-obj.getVendorBillAfterDiscountAndTaxAndPrepayment());
				} else {
					journalDetailPK3.setChartOfAccount(receiving.getVendor().getChartOfAccount());
					journalDetail3.setAmount(receiving.getVendor().getChartOfAccount().isDebit()==false?obj.getVendorBillAfterDiscountAndTaxAndPrepayment():-obj.getVendorBillAfterDiscountAndTaxAndPrepayment());
				}
				journalDetail3.setDepartment(purchaseOrder!=null?purchaseOrder.getDepartment():null);
				journalDetail3.setId(journalDetailPK3);
				set.add(journalDetail3);
				journal.setJournalDetails(set);
				obj.setJournal(journal);
				//log.info("B");
			}
			// save to session
			httpSession.setAttribute("vendorBill", obj);
			// save all
			//if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					//log.info("A1");
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateVendorBillNumber(session);
					RunningNumberDAO.getInstance().updateJournalNumber(session);
					VendorBillDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					transaction = session.beginTransaction();
					//log.info("A2");
					VendorBillDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("vendorBill");
				// finish
				ActionForward forward = mapping.findForward("update_status");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?receivingId="+form.getLong("receivingId")+"&purchaseOrderId="+form.getLong("purchaseOrderId"));
				return new ActionForward(sb.toString(),true);
			//}
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
				if (transaction!=null) transaction.rollback();
				try {
					List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("vendorLst", vendorLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
					/*
					List termLst = TermDAO.getInstance().getSession().createCriteria(Term.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("termLst", termLst);
					*/
					List receivingLst = ReceivingDAO.getInstance().getSession().createCriteria(Receiving.class)
						.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))))
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.isNull("VendorBill"))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
					request.setAttribute("receivingLst", receivingLst);
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
				VendorBillDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		/*
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?vendorBillId="+form.getLong("vendorBillId"));
		return new ActionForward(sb.toString(),true);
		*/
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
		ReceivingForm form = (ReceivingForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			Receiving receiving = ReceivingDAO.getInstance().get(form.getLong("receivingId"));
			
	    // write to pdf document
			Document document = new Document(PageSize.A4,20,20,20,20);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			  
			// footer page
			HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)), true);
			footer.setBorder(Rectangle.NO_BORDER);
			document.setFooter(footer);
			document.open();
			
			// table upper
			Table table1 = new Table(2);
			table1.setWidth(100);
			table1.setCellsFitPage(true);
			float[] a = {65,35};
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
			cell = new Cell(new Phrase("TGL : "+receiving.getFormatedReceivingDate(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("DARI : "+receiving.getVendor().getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tlp. "+users.getOrganization().getTelephone()+" - Fax. "+users.getOrganization().getFax(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			
			document.add(table1);
			
			Table table2 = new Table(7);
			table2.setWidth(100);
			table2.setCellsFitPage(true);
			float[] b = {5,20,15,15,10,10,25};
			table2.setWidths(b);
			table2.setBorder(Rectangle.NO_BORDER);
			table2.setCellsFitPage(true);
			table2.setBorderWidth(1);
			table2.setPadding(1);
			table2.setSpacing(0);
			
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(7);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(7);
			table2.addCell(cell);
			cell = new Cell(new Phrase("LAP. PENERIMAAN BARANG", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(7);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(7);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(7);
			table2.addCell(cell);
			cell = new Cell(new Phrase("NO. "+receiving.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(7);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("CODE",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("MERK",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("TYPE",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("WARNA",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("DESCRIPTION",FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			table2.endHeaders();
			
			int i = 0;
			Iterator iterator = receiving.getReceivingDetails().iterator();
			while (iterator.hasNext()) {
			    ReceivingDetail detail = (ReceivingDetail)iterator.next();
			    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getId().getItem().getCode(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(Double.toString(detail.getQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(detail.getDescription(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table2.addCell(cell);
			}
			
			cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(7);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("DIBUAT OLEH  ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(7);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			for (int j=0; j<15; j++) {
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setColspan(7);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table2.addCell(cell);
			}
			cell = new Cell(new Phrase("(KEPALA GUDANG)", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(7);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
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
				ReceivingDAO.getInstance().closeSessionForReal();
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