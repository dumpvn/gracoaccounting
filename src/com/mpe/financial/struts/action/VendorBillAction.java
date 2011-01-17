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
import com.mpe.financial.model.Journal;
import com.mpe.financial.model.JournalDetail;
import com.mpe.financial.model.JournalDetailPK;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.PrepaymentToVendor;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.PurchaseOrder;
import com.mpe.financial.model.Receiving;
import com.mpe.financial.model.ReceivingDetail;
import com.mpe.financial.model.VendorBill;
import com.mpe.financial.model.VendorBillDetail;
import com.mpe.financial.model.VendorBillDetailPK;
import com.mpe.financial.model.Tax;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.VendorBillPrepayment;
import com.mpe.financial.model.VendorBillPrepaymentPK;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.PrepaymentToVendorDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.ReceivingDAO;
import com.mpe.financial.model.dao.VendorBillDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.TaxDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.struts.form.VendorBillForm;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class VendorBillAction extends Action {
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
		VendorBillForm purchaseOrderForm = (VendorBillForm) form;
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
		VendorBillForm form = (VendorBillForm) actionForm;
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
			Criteria criteria = VendorBillDAO.getInstance().getSession().createCriteria(VendorBill.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromVendorBillDate")!=null)criteria.add(Restrictions.ge("VendorBillDate", new Date(form.getCalendar("fromVendorBillDate").getTime().getTime())));
			if (form.getCalendar("toVendorBillDate")!=null)criteria.add(Restrictions.le("VendorBillDate", new Date(form.getCalendar("toVendorBillDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("vendorId")>0) criteria.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))));
			if (form.getLong("departmentId")>0) criteria.add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = VendorBillDAO.getInstance().getSession().createCriteria(VendorBill.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromVendorBillDate")!=null)criteria.add(Restrictions.ge("VendorBillDate", new Date(form.getCalendar("fromVendorBillDate").getTime().getTime())));
			if (form.getCalendar("toVendorBillDate")!=null)criteria.add(Restrictions.le("VendorBillDate", new Date(form.getCalendar("toVendorBillDate").getTime().getTime())));
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
			request.setAttribute("VENDORBILL",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("vendorBill");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				VendorBillDAO.getInstance().closeSessionForReal();
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
		VendorBillForm form = (VendorBillForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			VendorBill obj = (VendorBill)httpSession.getAttribute("vendorBill");
			// relationships
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
			/*
			List termLst = TermDAO.getInstance().getSession().createCriteria(Term.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("termLst", termLst);
			*/
			List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("locationLst", locationLst);
			if (form.getLong("vendorBillId") == 0) {
				form.setString("vendorBillId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getVendorBillNumber());
				form.setCurentCalendar("billDate");
				form.setCurentCalendar("billDue");
				if (obj!=null) {
					form.setString("vendorBillId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setString("receivingId",obj.getReceiving()!=null?obj.getReceiving().getId():0);
					form.setString("number",obj.getNumber());
					form.setCalendar("billDate",obj.getBillDate());
					form.setCalendar("billDue",obj.getBillDue());
					//form.setString("bankAccountId",obj.getBankAccount()!=null?obj.getBankAccount().getId():0);
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("voucher",obj.getVoucher());
					form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
					form.setString("taxAmount",obj.getTaxAmount());
					form.setString("discountProcent",obj.getDiscountProcent());
					//form.setString("termId",obj.getTerm()!=null?obj.getTerm().getId():0);
					form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
					form.setString("receivingId",obj.getReceiving()!=null?obj.getReceiving().getId():0);
					Set vendorBillDetailLst = obj.getVendorBillDetails();
					request.setAttribute("vendorBillDetailLst", vendorBillDetailLst);
					Set vendorBillPrepaymentLst = obj.getVendorBillPrepayments();
					request.setAttribute("vendorBillPrepaymentLst", vendorBillPrepaymentLst);
				}
				List receivingLst = ReceivingDAO.getInstance().getSession().createCriteria(Receiving.class)
					.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("VendorBillStatus", new String(CommonConstants.CLOSE)))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
				request.setAttribute("receivingLst", receivingLst);
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = VendorBillDAO.getInstance().get(form.getLong("vendorBillId"));
					httpSession.setAttribute("vendorBill",obj);
				}
				form.setString("vendorBillId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("receivingId",obj.getReceiving()!=null?obj.getReceiving().getId():0);
				form.setString("number",obj.getNumber());
				form.setCalendar("billDate",obj.getBillDate());
				form.setCalendar("billDue",obj.getBillDue());
				//form.setString("bankAccountId",obj.getBankAccount()!=null?obj.getBankAccount().getId():0);
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("voucher",obj.getVoucher());
				form.setString("taxId",obj.getTax()!=null?obj.getTax().getId():0);
				form.setString("taxAmount",obj.getTaxAmount());
				form.setString("discountProcent",obj.getDiscountProcent());
				form.setString("taxSerialNumber", obj.getTaxSerialNumber());
				form.setCalendar("taxDate",obj.getTaxDate());
				//form.setString("termId",obj.getTerm()!=null?obj.getTerm().getId():0);
				form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
				List receivingLst = ReceivingDAO.getInstance().getSession().createCriteria(Receiving.class)
					.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL))).list();
				request.setAttribute("receivingLst", receivingLst);
				form.setString("receivingId",obj.getReceiving()!=null?obj.getReceiving().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set vendorBillDetailLst = obj.getVendorBillDetails();
				request.setAttribute("vendorBillDetailLst", vendorBillDetailLst);
				Set vendorBillPrepaymentLst = obj.getVendorBillPrepayments();
				request.setAttribute("vendorBillPrepaymentLst", vendorBillPrepaymentLst);
			}
			request.setAttribute("formatedVendorBillDetailAmount", obj!=null?obj.getFormatedVendorBillDetailAmount():"-");
			request.setAttribute("formatedVendorBillDiscountAmount", obj!=null?obj.getFormatedVendorBillDiscountAmount():"-");
			request.setAttribute("formatedVendorBillAfterDiscount", obj!=null?obj.getFormatedVendorBillAfterDiscount():"-");
			request.setAttribute("formatedVendorBillTaxAmount", obj!=null?obj.getFormatedVendorBillTaxAmount():"-");
			request.setAttribute("formatedVendorBillAfterDiscountAndTax", obj!=null?obj.getFormatedVendorBillAfterDiscountAndTax():"-");
			request.setAttribute("formatedVendorBillPrepaymentAmount", obj!=null?obj.getFormatedVendorBillPrepaymentAmount():"-");
			request.setAttribute("formatedVendorBillAfterDiscountAndTaxAndPrepayment", obj!=null?obj.getFormatedVendorBillAfterDiscountAndTaxAndPrepayment():"-");
		}catch(Exception ex) {
			try {
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
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				VendorBillDAO.getInstance().closeSessionForReal();
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
		VendorBillForm form = (VendorBillForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = VendorBillDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("vendorBill");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			VendorBill obj = (VendorBill)httpSession.getAttribute("vendorBill");
			if (form.getLong("vendorBillId") == 0) {
				obj = (VendorBill)VendorBillDAO.getInstance().getSession().createCriteria(VendorBill.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (VendorBill)httpSession.getAttribute("vendorBill");
					if (obj==null) obj = new VendorBill();
					Receiving receiving = ReceivingDAO.getInstance().get(form.getLong("receivingId"));
					PurchaseOrder purchaseOrder = receiving!=null?receiving.getPurchaseOrder():null;
					obj.setReceiving(receiving);
					obj.setDescription(form.getString("description"));
					obj.setNumber(form.getString("number"));
					obj.setCurrency(purchaseOrder!=null?purchaseOrder.getCurrency():null);
					obj.setProject(purchaseOrder!=null?purchaseOrder.getProject():null);
					obj.setDepartment(purchaseOrder!=null?purchaseOrder.getDepartment():null);
					obj.setDiscountProcent(purchaseOrder!=null?purchaseOrder.getDiscountProcent():0);
					//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup,form.getCalendar("billDate"));
					double e = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("billDate")));
					obj.setExchangeRate(e);
					//obj.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("billDate"))));
					obj.setPosted(false);
					obj.setVoucher(form.getString("voucher"));
					obj.setTaxSerialNumber(form.getString("taxSerialNumber"));
					obj.setTax(purchaseOrder!=null?purchaseOrder.getTax():null);
					obj.setTaxAmount(purchaseOrder!=null?purchaseOrder.getTaxAmount():0);
					obj.setOrganization(users.getOrganization());
					obj.setBillDate(form.getCalendar("billDate")!=null?form.getCalendar("billDate").getTime():null);
					obj.setBillDue(form.getCalendar("billDue")!=null?form.getCalendar("billDue").getTime():null);
					obj.setTaxDate(form.getCalendar("taxDate")!=null?form.getCalendar("taxDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					obj.setPaymentToVendorStatus(CommonConstants.OPEN);
					Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(vendors);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
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
							vendorBillDetail.setUnitConversion(receivingDetail.getUnitConversion());
							set.add(vendorBillDetail);
						}
						obj.setVendorBillDetails(set);
					}
					// get prepayment
					if (purchaseOrder!=null) {
						List prepaymentToVendorLst = PrepaymentToVendorDAO.getInstance().getSession().createCriteria(PrepaymentToVendor.class)
							.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))))
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
					//journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("billDate"))));
					journal.setJournalDate(obj.getBillDate());
					journal.setProject(purchaseOrder!=null?purchaseOrder.getProject():null);
					journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
					journal.setOrganization(users.getOrganization());
					journal.setPosted(false);
					journal.setReference(form.getString("voucher"));
					journal.setVendor(vendors);
					journal.setCreateBy(users);
					journal.setCreateOn(form.getTimestamp("createOn"));
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
					if (obj.getTax()!=null && obj.getTaxAmount()>0) {
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
					if (vendors.getChartOfAccount()==null) {
						journalDetailPK3.setChartOfAccount(organizationSetup.getApAccount());
						journalDetail3.setAmount(organizationSetup.getApAccount().isDebit()==false?obj.getVendorBillAfterDiscountAndTaxAndPrepayment():-obj.getVendorBillAfterDiscountAndTaxAndPrepayment());
					} else {
						journalDetailPK3.setChartOfAccount(vendors.getChartOfAccount());
						journalDetail3.setAmount(vendors.getChartOfAccount().isDebit()==false?obj.getVendorBillAfterDiscountAndTaxAndPrepayment():-obj.getVendorBillAfterDiscountAndTaxAndPrepayment());
					}
					journalDetail3.setDepartment(purchaseOrder!=null?purchaseOrder.getDepartment():null);
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
				if (obj==null) obj = VendorBillDAO.getInstance().load(form.getLong("vendorBillId"));
				Receiving receiving = ReceivingDAO.getInstance().get(form.getLong("receivingId"));
				PurchaseOrder purchaseOrder = receiving!=null?receiving.getPurchaseOrder():null;
				obj.setReceiving(receiving);
				obj.setDescription(form.getString("description"));
				obj.setNumber(form.getString("number"));
				obj.setCurrency(purchaseOrder!=null?purchaseOrder.getCurrency():null);
				obj.setDiscountProcent(purchaseOrder!=null?purchaseOrder.getDiscountProcent():0);
				obj.setDepartment(purchaseOrder!=null?purchaseOrder.getDepartment():null);
				//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("billDate"));
				double e = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("billDate")));
				obj.setExchangeRate(e);
				//obj.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("billDate"))));
				obj.setPosted(false);
				obj.setVoucher(form.getString("voucher"));
				obj.setTaxSerialNumber(form.getString("taxSerialNumber"));
				obj.setTax(purchaseOrder!=null?purchaseOrder.getTax():null);
				obj.setTaxAmount(purchaseOrder!=null?purchaseOrder.getTaxAmount():0);
				obj.setProject(purchaseOrder!=null?purchaseOrder.getProject():null);
				obj.setOrganization(users.getOrganization());
				obj.setBillDate(form.getCalendar("billDate")!=null?form.getCalendar("billDate").getTime():null);
				obj.setBillDue(form.getCalendar("billDue")!=null?form.getCalendar("billDue").getTime():null);
				obj.setTaxDate(form.getCalendar("taxDate")!=null?form.getCalendar("taxDate").getTime():null);
				//obj.setStatus(CommonConstants.OPEN);
				if (obj.getDifferenceAmount()==0) {
					obj.setPaymentToVendorStatus(CommonConstants.CLOSE);
				} else {
					if (obj.getPaymentToVendorAmount()==0) {
						obj.setPaymentToVendorStatus(CommonConstants.OPEN);
					} else obj.setPaymentToVendorStatus(CommonConstants.PARTIAL);
				}
				Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
				obj.setVendor(vendors);
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				// create new receiving
				if (receiving!=null) {
					obj.getVendorBillDetails().removeAll(obj.getVendorBillDetails());
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
						vendorBillDetail.setUnitConversion(receivingDetail.getUnitConversion());
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
				Journal journal = obj.getJournal();
				journal.setVendorBill(obj);
				journal.setCurrency(obj.getCurrency());
				journal.setExchangeRate(e);
				//journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("billDate"))));
				journal.setJournalDate(obj.getBillDate());
				journal.setProject(purchaseOrder!=null?purchaseOrder.getProject():null);
				//journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
				journal.setOrganization(users.getOrganization());
				//journal.setPosted(false);
				journal.setReference(form.getString("voucher"));
				journal.setVendor(vendors);
				journal.setCreateBy(users);
				journal.setCreateOn(form.getTimestamp("createOn"));
				journal.setCreateBy(createBy);
				journal.setCreateOn(form.getTimestamp("createOn"));
				journal.setChangeBy(users);
				journal.setChangeOn(form.getTimestamp("changeOn"));
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
				if (obj.getTax()!=null && obj.getTaxAmount()>0) {
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
				if (vendors.getChartOfAccount()==null) {
					journalDetailPK3.setChartOfAccount(organizationSetup.getApAccount());
					journalDetail3.setAmount(organizationSetup.getApAccount().isDebit()==false?obj.getVendorBillAfterDiscountAndTaxAndPrepayment():-obj.getVendorBillAfterDiscountAndTaxAndPrepayment());
				} else {
					journalDetailPK3.setChartOfAccount(vendors.getChartOfAccount());
					journalDetail3.setAmount(vendors.getChartOfAccount().isDebit()==false?obj.getVendorBillAfterDiscountAndTaxAndPrepayment():-obj.getVendorBillAfterDiscountAndTaxAndPrepayment());
				}
				journalDetail3.setDepartment(purchaseOrder!=null?purchaseOrder.getDepartment():null);
				journalDetail3.setId(journalDetailPK3);
				set.add(journalDetail3);
				journal.setJournalDetails(set);
				obj.setJournal(journal);
			}
			// save to session
			httpSession.setAttribute("vendorBill", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
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
				sb.append("?vendorBillId="+form.getLong("vendorBillId")+"&receivingId="+form.getLong("receivingId"));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
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
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Ap", Boolean.TRUE))
						.addOrder(Order.asc("Name")).list();
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
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?vendorBillId="+form.getLong("vendorBillId"));
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
		VendorBillForm form = (VendorBillForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			VendorBill vendorBill = VendorBillDAO.getInstance().get(form.getLong("vendorBillId"));
			request.setAttribute("vendorBill", vendorBill);
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				VendorBillDAO.getInstance().closeSessionForReal();
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
		VendorBillForm form = (VendorBillForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Session session = VendorBillDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			VendorBillDAO.getInstance().delete(form.getLong("vendorBillId"), session);
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			ex.printStackTrace();
		}finally {
			try {
				VendorBillDAO.getInstance().closeSessionForReal();
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
		VendorBillForm form = (VendorBillForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			Session session = ReceivingDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			// update receiving status
			Receiving receiving = ReceivingDAO.getInstance().get(form.getLong("receivingId"));
			receiving.setVendorBillStatus(CommonConstants.CLOSE);
			ReceivingDAO.getInstance().update(receiving, session);
			// update prepayment to vendor
			List list = PrepaymentToVendorDAO.getInstance().getSession().createCriteria(PrepaymentToVendor.class)
				.add(Restrictions.eq("PurchaseOrder.Id", new Long(receiving.getPurchaseOrder().getId()))).list();
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				PrepaymentToVendor prepaymentToVendor = (PrepaymentToVendor)iterator.next();
				if (prepaymentToVendor.getVendorBillPaymentAmount()>0 && prepaymentToVendor.getAmount()-prepaymentToVendor.getVendorBillPaymentAmount()>0) {
					prepaymentToVendor.setVendorBillStatus(CommonConstants.PARTIAL);
				} else if (prepaymentToVendor.getAmount()==prepaymentToVendor.getVendorBillPaymentAmount()) {
					prepaymentToVendor.setVendorBillStatus(CommonConstants.CLOSE);
				} else if (prepaymentToVendor.getVendorBillPaymentAmount()==0) {
					prepaymentToVendor.setVendorBillStatus(CommonConstants.OPEN);
				}
				PrepaymentToVendorDAO.getInstance().update(prepaymentToVendor, session);
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
				VendorBillDAO.getInstance().closeSessionForReal();
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