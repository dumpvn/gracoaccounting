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
import com.mpe.financial.model.PurchaseOrder;
import com.mpe.financial.model.PurchaseOrderDetail;
import com.mpe.financial.model.Receiving;
import com.mpe.financial.model.ReceivingDetail;
import com.mpe.financial.model.ReturToVendor;
import com.mpe.financial.model.ReturToVendorDetail;
import com.mpe.financial.model.ReturToVendorDetailPK;
import com.mpe.financial.model.Tax;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Vendors;
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
import com.mpe.financial.model.dao.PurchaseOrderDAO;
import com.mpe.financial.model.dao.ReceivingDAO;
import com.mpe.financial.model.dao.ReturToVendorDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.TaxDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.model.dao.WarehouseDAO;
import com.mpe.financial.struts.form.ReturToVendorForm;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class ReturToVendorAction extends Action {
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
		ReturToVendorForm purchaseOrderForm = (ReturToVendorForm) form;
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
					} else if ("DEBITMEMO".equalsIgnoreCase(action)) {
						forward = performDebitMemo(mapping, form, request, response);
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
		ReturToVendorForm form = (ReturToVendorForm) actionForm;
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
			Criteria criteria = ReturToVendorDAO.getInstance().getSession().createCriteria(ReturToVendor.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromReturDate")!=null)criteria.add(Restrictions.ge("ReturDate", new Date(form.getCalendar("fromReturDate").getTime().getTime())));
			if (form.getCalendar("toReturDate")!=null)criteria.add(Restrictions.le("ReturDate", new Date(form.getCalendar("toReturDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("vendorId")>0) criteria.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = ReturToVendorDAO.getInstance().getSession().createCriteria(ReturToVendor.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromReturDate")!=null)criteria.add(Restrictions.ge("ReturDate", new Date(form.getCalendar("fromReturDate").getTime().getTime())));
			if (form.getCalendar("toReturDate")!=null)criteria.add(Restrictions.le("ReturDate", new Date(form.getCalendar("toReturDate").getTime().getTime())));
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
			request.setAttribute("RETURTOVENDOR",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("returToVendor");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ReturToVendorDAO.getInstance().closeSessionForReal();
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
		ReturToVendorForm form = (ReturToVendorForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
	    if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
		      Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class).add(Restrictions.eq("Code", new String(form.getString("itemCode")))).uniqueResult();
		      form.setString("itemId", item.getId());
		  }
	    OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			// remove
			ReturToVendor obj = (ReturToVendor)httpSession.getAttribute("returToVendor");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVERETURTOVENDORDETAIL")) {
				ReturToVendorDetail removeReturToVendorDetail = null;
				Iterator iterator = obj.getReturToVendorDetails().iterator();
				while (iterator.hasNext()) {
					ReturToVendorDetail returToVendorDetail = (ReturToVendorDetail)iterator.next();
					if (form.getLong("itemId") == returToVendorDetail.getId().getItem().getId()) {
						removeReturToVendorDetail = returToVendorDetail;
					}
				}
				if (removeReturToVendorDetail!=null) {
					Set set = obj.getReturToVendorDetails();
					set.remove(removeReturToVendorDetail);
					obj.setReturToVendorDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("returToVendor", obj);
			}
			// relationships
			List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemUnitLst", itemUnitLst);
			List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("vendorLst", vendorLst);
			List warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("warehouseLst", warehouseLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
			List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("currencyLst", currencyLst);
			List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("locationLst", locationLst);
			if (form.getLong("returToVendorId") == 0) {
				List receivingLst = ReceivingDAO.getInstance().getSession().createCriteria(Receiving.class)
					.add(Restrictions.eq("Vendor.Id", new Long(obj!=null?(obj.getVendor()!=null?obj.getVendor().getId():form.getLong("vendorId")):form.getLong("vendorId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
				request.setAttribute("receivingLst", receivingLst);
				form.setString("returToVendorId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getReturToVendorNumber());
				form.setCurentCalendar("returDate");
				if (obj!=null) {
					form.setString("returToVendorId",obj.getId());
					form.setString("note",obj.getNote());
					form.setCalendar("returDate",obj.getReturDate());
					form.setString("receivingId",obj.getReceiving()!=null?obj.getReceiving().getId():0);
					form.setString("number",obj.getNumber());
					form.setString("reference",obj.getReference());
					form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					Set returToVendorDetailLst = obj.getReturToVendorDetails();
					request.setAttribute("returToVendorDetailLst", returToVendorDetailLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = ReturToVendorDAO.getInstance().get(form.getLong("returToVendorId"));
					httpSession.setAttribute("returToVendor",obj);
					httpSession.setAttribute("oldReturToVendor",obj);
				}
				form.setString("returToVendorId",obj.getId());
				form.setString("note",obj.getNote());
				form.setCalendar("returDate",obj.getReturDate());
				form.setString("receivingId",obj.getReceiving()!=null?obj.getReceiving().getId():0);
				form.setString("number",obj.getNumber());
				form.setString("reference",obj.getReference());
				form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				List receivingLst = ReceivingDAO.getInstance().getSession().createCriteria(Receiving.class)
					.add(Restrictions.eq("Vendor.Id", new Long(obj!=null?(obj.getVendor()!=null?obj.getVendor().getId():form.getLong("vendorId")):form.getLong("vendorId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.createCriteria("ReturToVendor", "ReturToVendor")
					.add(Restrictions.eq("ReturToVendor.Id", new Long(obj.getId())))
					.list();
				request.setAttribute("receivingLst", receivingLst);
				Set returToVendorDetailLst = obj.getReturToVendorDetails();
				request.setAttribute("returToVendorDetailLst", returToVendorDetailLst);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
			}
			if (form.getLong("itemId") > 0) {
				//boolean isNewData = true;
				if (obj!=null && obj.getReturToVendorDetails()!=null) {
					Iterator iterator = obj.getReturToVendorDetails().iterator();
					while (iterator.hasNext()) {
						ReturToVendorDetail returToVendorDetail = (ReturToVendorDetail)iterator.next();
						if (form.getLong("itemId") == returToVendorDetail.getId().getItem().getId()) {
							//isNewData = false;
							form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), returToVendorDetail.getPrice()));
							form.setString("quantity", returToVendorDetail.getQuantity());
							form.setString("itemCode", returToVendorDetail.getId().getItem().getCode());
							form.setString("itemDescription", returToVendorDetail.getDescription());
							form.setString("exchangeRate", returToVendorDetail.getExchangeRate());
							form.setString("itemUnitId", returToVendorDetail.getItemUnit()!=null?returToVendorDetail.getItemUnit().getId():0);
							form.setString("taxDetailAmount", returToVendorDetail.getTaxAmount());
							form.setString("itemDiscountAmount", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), returToVendorDetail.getDiscountAmount()));
							form.setString("itemDiscountProcent", returToVendorDetail.getDiscountProcent());
							form.setString("taxDetailId", returToVendorDetail.getTax()!=null?returToVendorDetail.getTax().getId():0);
							form.setString("warehouseId", returToVendorDetail.getWarehouse()!=null?returToVendorDetail.getWarehouse().getId():0);
							form.setString("returToVendorDetailCurrencyId", Formater.getFormatedOutputForm(returToVendorDetail.getCurrency()!=null?returToVendorDetail.getCurrency().getId():0));
						}
					}
				}
			}
			request.setAttribute("returToVendorDetailAmount", obj!=null?obj.getFormatedReturToVendorDetailAmount():"-");
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
				List receivingLst = ReceivingDAO.getInstance().getSession().createCriteria(Receiving.class)
					.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
				request.setAttribute("receivingLst", receivingLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				ReturToVendorDAO.getInstance().closeSessionForReal();
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
		ReturToVendorForm form = (ReturToVendorForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = ReturToVendorDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("returToVendor");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			ReturToVendor obj = (ReturToVendor)httpSession.getAttribute("returToVendor");
			if (form.getLong("returToVendorId") == 0) {
				obj = (ReturToVendor)ReturToVendorDAO.getInstance().getSession().createCriteria(ReturToVendor.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (ReturToVendor)httpSession.getAttribute("returToVendor");
					Receiving receiving = ReceivingDAO.getInstance().get(form.getLong("receivingId"));
					if (receiving!=null) form.setString("purchaseOrderId", receiving.getPurchaseOrder().getId());
					if (obj==null) {
						obj = new ReturToVendor();
						obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
						obj.setReceiving(receiving);
						obj.setProject(receiving.getProject());
						obj.setDepartment(receiving.getDepartment());
						if (form.getLong("locationId")==0) form.setString("locationId", receiving.getLocation()!=null?receiving.getLocation().getId():0); 
						Set set = obj.getReturToVendorDetails();
						if (set==null) set = new LinkedHashSet();
						Iterator iterator = receiving.getReceivingDetails().iterator();
						while (iterator.hasNext()) {
							ReceivingDetail receivingDetail  = (ReceivingDetail)iterator.next();
							if (receivingDetail.getQuantity()>0) {
								ReturToVendorDetail returToVendorDetail = new ReturToVendorDetail();
								returToVendorDetail.setItemUnit(receivingDetail.getItemUnit());
								returToVendorDetail.setWarehouse(receivingDetail.getWarehouse());
								ReturToVendorDetailPK returToVendorDetailPK = new ReturToVendorDetailPK();
								returToVendorDetailPK.setItem(receivingDetail.getId().getItem());
								returToVendorDetailPK.setReturToVendor(obj);
								returToVendorDetail.setId(returToVendorDetailPK);
								returToVendorDetail.setTax(receivingDetail.getTax());
								returToVendorDetail.setTaxAmount(receivingDetail.getTaxAmount());
								returToVendorDetail.setDiscountAmount(receivingDetail.getDiscountAmount());
								returToVendorDetail.setDiscountProcent(receivingDetail.getDiscountProcent());
								returToVendorDetail.setCurrency(receivingDetail.getCurrency());
								returToVendorDetail.setExchangeRate(receivingDetail.getExchangeRate());
								returToVendorDetail.setPrice(receivingDetail.getPrice());
								returToVendorDetail.setQuantity(receivingDetail.getQuantity());
								returToVendorDetail.setDescription(receivingDetail.getDescription());
								returToVendorDetail.setUnitConversion(receivingDetail.getUnitConversion());
								set.add(returToVendorDetail);
							}
						}
						obj.setReturToVendorDetails(set);
					}
					obj.setCurrency(receiving!=null?(receiving.getPurchaseOrder()!=null?receiving.getPurchaseOrder().getCurrency():null):null);
					//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("returDate"));
					double e = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("returDate")));
					obj.setExchangeRate(e);
					//obj.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("returDate"))));
					obj.setProject(receiving.getProject());
					obj.setNote(form.getString("note"));
					obj.setReference(form.getString("reference"));
					obj.setNumber(form.getString("number"));
					obj.setOrganization(users.getOrganization());
					obj.setReturDate(form.getCalendar("returDate")!=null?form.getCalendar("returDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					obj.setPaymentToVendorStatus(CommonConstants.OPEN);
					Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(vendors);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					// create new returToVendor
					if (form.getLong("receivingId")!=obj.getReceiving().getId()) {
						Set set = new LinkedHashSet();
						Iterator iterator = receiving.getReceivingDetails().iterator();
						while (iterator.hasNext()) {
							ReceivingDetail receivingDetail  = (ReceivingDetail)iterator.next();
							if (receivingDetail.getQuantity()>0) {
								ReturToVendorDetail returToVendorDetail = new ReturToVendorDetail();
								returToVendorDetail.setItemUnit(receivingDetail.getItemUnit());
								returToVendorDetail.setWarehouse(receivingDetail.getWarehouse());
								ReturToVendorDetailPK returToVendorDetailPK = new ReturToVendorDetailPK();
								returToVendorDetailPK.setItem(receivingDetail.getId().getItem());
								returToVendorDetailPK.setReturToVendor(obj);
								returToVendorDetail.setId(returToVendorDetailPK);
								returToVendorDetail.setTax(receivingDetail.getTax());
								returToVendorDetail.setTaxAmount(receivingDetail.getTaxAmount());
								returToVendorDetail.setDiscountAmount(receivingDetail.getDiscountAmount());
								returToVendorDetail.setDiscountProcent(receivingDetail.getDiscountProcent());
								returToVendorDetail.setCurrency(receivingDetail.getCurrency());
								returToVendorDetail.setExchangeRate(receivingDetail.getExchangeRate());
								returToVendorDetail.setPrice(receivingDetail.getPrice());
								returToVendorDetail.setQuantity(receivingDetail.getQuantity());
								returToVendorDetail.setDescription(receivingDetail.getDescription());
								returToVendorDetail.setUnitConversion(receivingDetail.getUnitConversion());
								set.add(returToVendorDetail);
							}
						}
						obj.setReturToVendorDetails(set);
						obj.setReceiving(receiving);
					}
					// create journal
					Journal journal = new Journal();
					journal.setReturToVendor(obj);
					journal.setCurrency(obj.getCurrency());
					journal.setProject(receiving.getProject());
					journal.setExchangeRate(e);
					//journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("returDate"))));
					journal.setJournalDate(obj.getReturDate());
					journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
					journal.setOrganization(users.getOrganization());
					journal.setPosted(false);
					journal.setReference(form.getString("reference"));
					journal.setVendor(vendors);
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
					journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==false?obj.getAmountAfterTaxAndDiscount():-obj.getAmountAfterTaxAndDiscount());
					set.add(journalDetail);
					// debit
					JournalDetail journalDetail3 = new JournalDetail();
					JournalDetailPK journalDetailPK3 = new JournalDetailPK();
					journalDetailPK3.setJournal(journal);
					if (vendors.getChartOfAccount()==null) {
						journalDetailPK3.setChartOfAccount(organizationSetup.getApAccount());
						journalDetail3.setAmount(organizationSetup.getApAccount().isDebit()==true?obj.getAmountAfterTaxAndDiscount():-obj.getAmountAfterTaxAndDiscount());
					} else {
						journalDetailPK3.setChartOfAccount(vendors.getChartOfAccount());
						journalDetail3.setAmount(vendors.getChartOfAccount().isDebit()==true?obj.getAmountAfterTaxAndDiscount():-obj.getAmountAfterTaxAndDiscount());
					}
					journalDetail3.setDepartment(obj.getDepartment());
					journalDetail3.setId(journalDetailPK3);
					set.add(journalDetail3);
					journal.setJournalDetails(set);
					obj.setJournal(journal);
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = ReturToVendorDAO.getInstance().get(form.getLong("returToVendorId"));
				Receiving receiving = ReceivingDAO.getInstance().get(form.getLong("receivingId"));
				if (receiving!=null) form.setString("purchaseOrderId", receiving.getPurchaseOrder().getId());
				obj.setReceiving(receiving);
				obj.setProject(receiving.getProject());
				obj.setNote(form.getString("note"));
				obj.setNumber(form.getString("number"));
				obj.setOrganization(users.getOrganization());
				obj.setCurrency(receiving!=null?(receiving.getPurchaseOrder()!=null?receiving.getPurchaseOrder().getCurrency():null):null);
				//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("returDate"));
				double e = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("returDate")));
				obj.setExchangeRate(e);
				//obj.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("returDate"))));
				obj.setReturDate(form.getCalendar("returDate")!=null?form.getCalendar("returDate").getTime():null);
				//obj.setStatus(CommonConstants.OPEN");
				//obj.setPaymentToVendorStatus(CommonConstants.OPEN);
				Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
				obj.setVendor(vendors);
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("returToVendorId"));
				// create journal
				//Journal journal = obj.getJournal();
				Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
					.add(Restrictions.eq("ReturToVendor.Id", new Long(obj.getId()))).uniqueResult();
				journal.setReturToVendor(obj);
				journal.setCurrency(obj.getCurrency());
				journal.setExchangeRate(e);
				//journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("returDate"))));
				journal.setJournalDate(obj.getReturDate());
				//journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
				journal.setOrganization(users.getOrganization());
				//journal.setPosted(false);
				journal.setReference(form.getString("reference"));
				journal.setVendor(vendors);
				journal.setProject(receiving.getProject());
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
				journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==false?obj.getAmountAfterTaxAndDiscount():-obj.getAmountAfterTaxAndDiscount());
				set.add(journalDetail);
				// debit
				JournalDetail journalDetail3 = new JournalDetail();
				JournalDetailPK journalDetailPK3 = new JournalDetailPK();
				journalDetailPK3.setJournal(journal);
				if (vendors.getChartOfAccount()==null) {
					journalDetailPK3.setChartOfAccount(organizationSetup.getApAccount());
					journalDetail3.setAmount(organizationSetup.getApAccount().isDebit()==true?obj.getAmountAfterTaxAndDiscount():-obj.getAmountAfterTaxAndDiscount());
				} else {
					journalDetailPK3.setChartOfAccount(vendors.getChartOfAccount());
					journalDetail3.setAmount(vendors.getChartOfAccount().isDebit()==true?obj.getReturToVendorDetailAmount():-obj.getReturToVendorDetailAmount());
				}
				journalDetail3.setDepartment(obj.getDepartment());
				journalDetail3.setId(journalDetailPK3);
				set.add(journalDetail3);
				journal.setJournalDetails(set);
				obj.setJournal(journal);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDRETURTOVENDORDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("quantity")>0) {
					ReturToVendorDetail returToVendorDetail = new ReturToVendorDetail();
					//Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					returToVendorDetail.setItemUnit(itemUnit);
					ReturToVendorDetailPK returToVendorDetailPK = new ReturToVendorDetailPK();
					returToVendorDetailPK.setItem(inventory);
					returToVendorDetailPK.setReturToVendor(obj);
					returToVendorDetail.setId(returToVendorDetailPK);
					Tax tax = TaxDAO.getInstance().get(form.getLong("taxDetailId"));
					returToVendorDetail.setTax(tax);
					Warehouse warehouse = WarehouseDAO.getInstance().get(form.getLong("warehouseId"));
					returToVendorDetail.setWarehouse(warehouse);
					returToVendorDetail.setTaxAmount(form.getDouble("taxDetailAmount"));
					returToVendorDetail.setDiscountAmount(form.getDouble("itemDiscountAmount"));
					returToVendorDetail.setDiscountProcent(form.getDouble("itemDiscountProcent"));
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("returToVendorDetailCurrencyId"));
					returToVendorDetail.setCurrency(currency);
					returToVendorDetail.setExchangeRate(form.getDouble("exchangeRate"));
					returToVendorDetail.setPrice(form.getDouble("price"));
					returToVendorDetail.setQuantity(form.getDouble("quantity"));
					returToVendorDetail.setDescription(form.getString("itemDescription"));
					returToVendorDetail.setUnitConversion(ItemUnitDAO.getInstance().getConversion(itemUnit, inventory.getItemUnit(), inventory));
					Set set = obj.getReturToVendorDetails();
					if (set==null) set = new LinkedHashSet();
					ReturToVendorDetail removeReturToVendorDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						ReturToVendorDetail returToVendorDetail2 = (ReturToVendorDetail)iterator.next();
						if (form.getLong("itemId")==returToVendorDetail2.getId().getItem().getId()) {
							removeReturToVendorDetail = returToVendorDetail2;
						}
					}
					if (removeReturToVendorDetail!=null) {
						set.remove(removeReturToVendorDetail);
						set.add(returToVendorDetail);
					} else {
						set.add(returToVendorDetail);
					}
					obj.setReturToVendorDetails(set);
				}
			}
			// save to session
			httpSession.setAttribute("returToVendor", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateReturToVendorNumber(session);
					RunningNumberDAO.getInstance().updateJournalNumber(session);
					if (obj.getReceiving()!=null) form.setString("purchaseOrderId", obj.getReceiving().getPurchaseOrder().getId());
					ReturToVendorDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					transaction = session.beginTransaction();
					if (obj.getReceiving()!=null) form.setString("purchaseOrderId", obj.getReceiving().getPurchaseOrder().getId());
					ReturToVendorDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("returToVendor");
				httpSession.removeAttribute("oldReturToVendor");
				// finish
				ActionForward forward = mapping.findForward("update_status");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?returToVendorId="+form.getLong("returToVendorId")+"&purchaseOrderId="+form.getLong("purchaseOrderId"));
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
					List warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("warehouseLst", warehouseLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
					List receivingLst = ReceivingDAO.getInstance().getSession().createCriteria(Receiving.class)
						.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))))
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
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
				ReturToVendorDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?returToVendorId="+form.getLong("returToVendorId"));
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
		ReturToVendorForm form = (ReturToVendorForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// update po => receivingStatus!!!
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
				ReturToVendorDAO.getInstance().closeSessionForReal();
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
		//ReturToVendorForm form = (ReturToVendorForm) actionForm;
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
				ReturToVendorDAO.getInstance().closeSessionForReal();
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
		ReturToVendorForm form = (ReturToVendorForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			ReturToVendor returToVendor = ReturToVendorDAO.getInstance().get(form.getLong("returToVendorId"));
			request.setAttribute("returToVendor", returToVendor);
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				ReturToVendorDAO.getInstance().closeSessionForReal();
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
		ReturToVendorForm form = (ReturToVendorForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			ReturToVendorDAO.getInstance().delete(form.getLong("returToVendorId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				ReturToVendorDAO.getInstance().closeSessionForReal();
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