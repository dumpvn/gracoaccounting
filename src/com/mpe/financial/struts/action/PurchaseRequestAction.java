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
import com.mpe.financial.model.ItemVendor;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.PurchaseRequest;
import com.mpe.financial.model.PurchaseRequestDetail;
import com.mpe.financial.model.PurchaseRequestDetailPK;
import com.mpe.financial.model.Tax;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.PurchaseRequestDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.TaxDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.struts.form.PurchaseRequestForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class PurchaseRequestAction extends Action {
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
		PurchaseRequestForm purchaseOrderForm = (PurchaseRequestForm) form;
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
		PurchaseRequestForm form = (PurchaseRequestForm) actionForm;
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
			Criteria criteria = PurchaseRequestDAO.getInstance().getSession().createCriteria(PurchaseRequest.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromRequestDate")!=null)criteria.add(Restrictions.ge("RequestDate", new Date(form.getCalendar("fromRequestDate").getTime().getTime())));
			if (form.getCalendar("toRequestDate")!=null)criteria.add(Restrictions.le("RequestDate", new Date(form.getCalendar("toRequestDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("vendorId")>0) criteria.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = PurchaseRequestDAO.getInstance().getSession().createCriteria(PurchaseRequest.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromRequestDate")!=null)criteria.add(Restrictions.ge("RequestDate", new Date(form.getCalendar("fromRequestDate").getTime().getTime())));
			if (form.getCalendar("toRequestDate")!=null)criteria.add(Restrictions.le("RequestDate", new Date(form.getCalendar("toRequestDate").getTime().getTime())));
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
			request.setAttribute("PURCHASEREQUEST",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("purchaseRequest");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				PurchaseRequestDAO.getInstance().closeSessionForReal();
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
		PurchaseRequestForm form = (PurchaseRequestForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		  if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
		      Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class).add(Restrictions.eq("Code", new String(form.getString("itemCode")))).uniqueResult();
		      form.setString("itemId", item.getId());
		  }
			// remove
			PurchaseRequest obj = (PurchaseRequest)httpSession.getAttribute("purchaseRequest");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEPURCHASEORDERDETAIL")) {
				PurchaseRequestDetail removePurchaseRequestDetail = null;
				Iterator iterator = obj.getPurchaseRequestDetails().iterator();
				while (iterator.hasNext()) {
					PurchaseRequestDetail purchaseRequestDetail = (PurchaseRequestDetail)iterator.next();
					if (form.getLong("itemId") == purchaseRequestDetail.getId().getItem().getId()) {
						removePurchaseRequestDetail = purchaseRequestDetail;
					}
				}
				if (removePurchaseRequestDetail!=null) {
					Set set = obj.getPurchaseRequestDetails();
					set.remove(removePurchaseRequestDetail);
					obj.setPurchaseRequestDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("purchaseRequest", obj);
			}
			// relationships
			List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemUnitLst", itemUnitLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
			List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("vendorLst", vendorLst);
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
			if (form.getLong("purchaseRequestId") == 0) {
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				form.setString("purchaseRequestId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getPurchaseRequestNumber());
				form.setCurentCalendar("requestDate");
				if (obj!=null) {
					form.setString("purchaseRequestId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setString("discountAmount",obj.getDiscountAmount());
					form.setString("discountProcent",obj.getDiscountProcent());
					form.setString("number",obj.getNumber());
					form.setString("taxAmount",obj.getTaxAmount());
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
					form.setCalendar("requestDate",obj.getRequestDate());
					form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
					//form.setString("termId",obj.getTerm()!=null?obj.getTerm().getId():0);
					form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
					form.setString("departmentId",obj.getDepartment()!=null?obj.getDepartment().getId():0);
					//form.setString("creditLimit",obj.getCreditLimit());
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					Set purchaseRequestDetailLst = obj.getPurchaseRequestDetails();
					request.setAttribute("purchaseRequestDetailLst", purchaseRequestDetailLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = PurchaseRequestDAO.getInstance().get(form.getLong("purchaseRequestId"));
					httpSession.setAttribute("purchaseRequest",obj);
				}
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				form.setString("purchaseRequestId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("discountAmount",obj.getDiscountAmount());
				form.setString("discountProcent",obj.getDiscountProcent());
				form.setString("number",obj.getNumber());
				form.setString("taxAmount",obj.getTaxAmount());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
				form.setCalendar("requestDate",obj.getRequestDate());
				form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
				//form.setString("termId",obj.getTerm()!=null?obj.getTerm().getId():0);
				form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
				//form.setString("creditLimit",obj.getCreditLimit());
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				form.setString("departmentId",obj.getDepartment()!=null?obj.getDepartment().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set purchaseRequestDetailLst = obj.getPurchaseRequestDetails();
				request.setAttribute("purchaseRequestDetailLst", purchaseRequestDetailLst);
			}
			if (form.getLong("itemId") > 0) {
				boolean isNewData = true;
				if (obj!=null && obj.getPurchaseRequestDetails()!=null) {
					Iterator iterator = obj.getPurchaseRequestDetails().iterator();
					while (iterator.hasNext()) {
						PurchaseRequestDetail purchaseRequestDetail = (PurchaseRequestDetail)iterator.next();
						if (form.getLong("itemId") == purchaseRequestDetail.getId().getItem().getId()) {
							isNewData = false;
							form.setString("itemCode", purchaseRequestDetail.getId().getItem().getCode());
							form.setString("price", Formater.getFormatedOutputForm(purchaseRequestDetail.getPrice()));
							form.setString("quantity", purchaseRequestDetail.getQuantity());
							form.setString("itemDescription", purchaseRequestDetail.getDescription());
							form.setString("itemUnitId", Formater.getFormatedOutputForm(purchaseRequestDetail.getItemUnit()!=null?purchaseRequestDetail.getItemUnit().getId():0));
							form.setString("purchaseRequestDetailCurrencyId", Formater.getFormatedOutputForm(purchaseRequestDetail.getCurrency()!=null?purchaseRequestDetail.getCurrency().getId():0));
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
									form.setString("price", Formater.getFormatedOutputForm(itemVendor.getCostPrice()));
									form.setString("purchaseRequestDetailCurrencyId", itemVendor.getCurrency().getId());
									isRightVendor = true;
								}
							}
							if (!isRightVendor) {
							  form.setString("itemCode", inventory.getCode());
								form.setString("price", Formater.getFormatedOutputForm(inventory.getCostPrice()));
								form.setString("purchaseRequestDetailCurrencyId", inventory.getCurrency()!=null?inventory.getCurrency().getId():0);
							}
						} else {
						  form.setString("itemCode", inventory.getCode());
							form.setString("price", Formater.getFormatedOutputForm(inventory.getCostPrice()));
							form.setString("purchaseRequestDetailCurrencyId", inventory.getCurrency()!=null?inventory.getCurrency().getId():0);
						}
						form.setString("itemUnitId", Formater.getFormatedOutputForm(inventory.getItemUnit()!=null?inventory.getItemUnit().getId():0));
					}
				}
			}
			request.setAttribute("purchaseRequestDetailAmount", obj!=null?obj.getFormatedPurchaseRequestDetailAmount():"-");
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
					.add(Restrictions.eq("Ap", Boolean.TRUE))
					.addOrder(Order.asc("Name")).list();
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
				PurchaseRequestDAO.getInstance().closeSessionForReal();
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
		PurchaseRequestForm form = (PurchaseRequestForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = PurchaseRequestDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("purchaseRequest");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			PurchaseRequest obj = (PurchaseRequest)httpSession.getAttribute("purchaseRequest");
			if (form.getLong("purchaseRequestId") == 0) {
				obj = (PurchaseRequest)PurchaseRequestDAO.getInstance().getSession().createCriteria(PurchaseRequest.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (PurchaseRequest)httpSession.getAttribute("purchaseRequest");
					if (obj==null) obj = new PurchaseRequest();
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					obj.setDescription(form.getString("description"));
					obj.setDiscountAmount(form.getDouble("discountAmount"));
					obj.setDiscountProcent(form.getDouble("discountProcent"));
					obj.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("requestDate")));
					obj.setNumber(form.getString("number"));
					obj.setOrganization(users.getOrganization());
					Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
					obj.setProject(project);
					obj.setRequestDate(form.getCalendar("requestDate")!=null?form.getCalendar("requestDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					Tax tax = TaxDAO.getInstance().get(form.getLong("taxId"));
					obj.setTax(tax);
					Department department = DepartmentDAO.getInstance().get(form.getLong("taxId"));
					obj.setDepartment(department);
					if (tax!=null)obj.setTaxAmount(tax.getQuantity());
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(vendors);
					//obj.setCreditLimit(vendors!=null?vendors.getCreditLimit():0);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					Set set = new LinkedHashSet();
					set.add(obj);
				} else {
					List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("itemUnitLst", itemUnitLst);
					List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("vendorLst", vendorLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Ap", Boolean.TRUE))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
					// err
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = PurchaseRequestDAO.getInstance().get(form.getLong("purchaseRequestId"));
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				obj.setDescription(form.getString("description"));
				obj.setDiscountAmount(form.getDouble("discountAmount"));
				obj.setDiscountProcent(form.getDouble("discountProcent"));
				obj.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("requestDate")));
				obj.setNumber(form.getString("number"));
				obj.setOrganization(users.getOrganization());
				Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
				obj.setProject(project);
				obj.setRequestDate(form.getCalendar("requestDate")!=null?form.getCalendar("requestDate").getTime():null);
				//obj.setStatus("OPEN");
				//obj.setReceivingStatus("OPEN");
				Tax tax = TaxDAO.getInstance().get(form.getLong("taxId"));
				obj.setTax(tax);
				Department department = DepartmentDAO.getInstance().get(form.getLong("taxId"));
				obj.setDepartment(department);
				if (tax!=null)obj.setTaxAmount(tax.getQuantity());
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
				obj.setVendor(vendors);
				//obj.setCreditLimit(vendors!=null?vendors.getCreditLimit():0);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("purchaseRequestId"));
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDPURCHASEORDERDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("quantity")>0) {
					PurchaseRequestDetail purchaseRequestDetail = new PurchaseRequestDetail();
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					//Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					purchaseRequestDetail.setItemUnit(itemUnit);
					PurchaseRequestDetailPK purchaseRequestDetailPK = new PurchaseRequestDetailPK();
					purchaseRequestDetailPK.setItem(inventory);
					purchaseRequestDetailPK.setPurchaseRequest(obj);
					purchaseRequestDetail.setId(purchaseRequestDetailPK);
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("purchaseRequestDetailCurrencyId"));
					purchaseRequestDetail.setCurrency(currency);
					//log.info("Cur : "+currency.getName());
					purchaseRequestDetail.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, obj.getCurrency(), organizationSetup, form.getCalendar("requestDate")));
					purchaseRequestDetail.setUnitConversion(ItemUnitDAO.getInstance().getConversion(itemUnit, inventory.getItemUnit(), inventory));
					purchaseRequestDetail.setPrice(form.getDouble("price"));
					purchaseRequestDetail.setQuantity(form.getDouble("quantity"));
					purchaseRequestDetail.setDescription(form.getString("itemDescription"));
					purchaseRequestDetail.setUnitConversion(ItemUnitDAO.getInstance().getConversion(itemUnit, inventory.getItemUnit(), inventory));
					Set set = obj.getPurchaseRequestDetails();
					if (set==null) set = new LinkedHashSet();
					PurchaseRequestDetail removePurchaseRequestDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						PurchaseRequestDetail purchaseRequestDetail2 = (PurchaseRequestDetail)iterator.next();
						if (form.getLong("itemId")==purchaseRequestDetail2.getId().getItem().getId()) {
							removePurchaseRequestDetail = purchaseRequestDetail2;
						}
					}
					if (removePurchaseRequestDetail!=null) {
						set.remove(removePurchaseRequestDetail);
						set.add(purchaseRequestDetail);
					} else {
						set.add(purchaseRequestDetail);
					}
					obj.setPurchaseRequestDetails(set);
				}
			}
			// save to session
			httpSession.setAttribute("purchaseRequest", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updatePurchaseRequestNumber(session);
					// update status
					PurchaseRequestDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					transaction = session.beginTransaction();
					PurchaseRequestDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("purchaseRequest");
				httpSession.removeAttribute("prepaymentToVendor");
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
					List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("vendorLst", vendorLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List taxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Ap", Boolean.TRUE))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("taxLst", taxLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				PurchaseRequestDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?purchaseRequestId="+form.getLong("purchaseRequestId"));
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
		PurchaseRequestForm form = (PurchaseRequestForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			PurchaseRequest purchaseRequest = PurchaseRequestDAO.getInstance().get(form.getLong("purchaseRequestId"));
			request.setAttribute("purchaseRequest", purchaseRequest);
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				PurchaseRequestDAO.getInstance().closeSessionForReal();
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
		PurchaseRequestForm form = (PurchaseRequestForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			PurchaseRequestDAO.getInstance().delete(form.getLong("purchaseRequestId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				PurchaseRequestDAO.getInstance().closeSessionForReal();
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
		PurchaseRequestForm form = (PurchaseRequestForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		  String font = "HELVETICA";
	    try {
				ResourceBundle prop = ResourceBundle.getBundle("resource.ApplicationResources");
				font = prop.getString("font");
			}catch(Exception exx) {
			}
		    
			PurchaseRequest purchaseRequest = PurchaseRequestDAO.getInstance().get(form.getLong("purchaseRequestId"));
			
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
			cell = new Cell(new Phrase("Tanggal : "+purchaseRequest.getFormatedRequestDate(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Kepada : "+purchaseRequest.getVendor().getCompany(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Tlp. "+users.getOrganization().getTelephone()+" - Fax. "+users.getOrganization().getFax(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Telp. : "+purchaseRequest.getVendor().getTelephone(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Fax. : "+purchaseRequest.getVendor().getFax(), FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			//cell = new Cell(new Phrase("Attn. : "+purchaseRequest.getVendor().getContactPerson(), FontFactory.getFont(font, 10, Font.NORMAL)));
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
			cell = new Cell(new Phrase("PURCHASE REQUEST", FontFactory.getFont(font, 15, Font.BOLD)));
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
			cell = new Cell(new Phrase("NO. : "+purchaseRequest.getNumber(), FontFactory.getFont(font, 15, Font.BOLD)));
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
			cell = new Cell(new Phrase("ITEM",FontFactory.getFont(font, 10, Font.BOLD)));
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
			Iterator iterator = purchaseRequest.getPurchaseRequestDetails().iterator();
			while (iterator.hasNext()) {
			    PurchaseRequestDetail detail = (PurchaseRequestDetail)iterator.next();
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
/*			cell = new Cell(new Phrase("TOTAL", FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(8);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(purchaseRequest.getFormatedPurchaseRequestDetailAmount(), FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setBorder(Rectangle.TOP);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("TERBILANG : "+AmountSay.getSaying(purchaseRequest.getFormatedPurchaseRequestDetailAmount())+" "+purchaseRequest.getCurrency().getName(), FontFactory.getFont(font, 10, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.addCell(cell);*/
			cell = new Cell(new Phrase(" ", FontFactory.getFont(font, 10, Font.NORMAL)));
			cell.setBorder(Rectangle.TOP);
			cell.setColspan(9);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(users.getOrganization().getCity()+", "+purchaseRequest.getFormatedRequestDate(), FontFactory.getFont(font, 10, Font.NORMAL)));
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
/*			
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
			table2.addCell(cell);*/
			
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
				PurchaseRequestDAO.getInstance().closeSessionForReal();
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