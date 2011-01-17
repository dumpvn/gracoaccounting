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

import com.mpe.financial.model.BankAccount;
import com.mpe.financial.model.BankTransaction;
import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.Currency;
import com.mpe.financial.model.Journal;
import com.mpe.financial.model.JournalDetail;
import com.mpe.financial.model.JournalDetailPK;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.PurchaseOrder;
import com.mpe.financial.model.PaymentToVendor;
import com.mpe.financial.model.PaymentToVendorDetail;
import com.mpe.financial.model.PaymentToVendorDetailPK;
import com.mpe.financial.model.Receiving;
import com.mpe.financial.model.ReturToVendor;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.VendorBill;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.dao.BankAccountDAO;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.JournalDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.PurchaseOrderDAO;
import com.mpe.financial.model.dao.PaymentToVendorDAO;
import com.mpe.financial.model.dao.ReturToVendorDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.VendorBillDAO;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.struts.form.PaymentToVendorForm;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class PaymentToVendorAction extends Action {
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
		PaymentToVendorForm purchaseOrderForm = (PaymentToVendorForm) form;
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
		PaymentToVendorForm form = (PaymentToVendorForm) actionForm;
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
			Criteria criteria = PaymentToVendorDAO.getInstance().getSession().createCriteria(PaymentToVendor.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromPaymentDate")!=null)criteria.add(Restrictions.ge("PaymentDate", new Date(form.getCalendar("fromPaymentDate").getTime().getTime())));
			if (form.getCalendar("toPaymentDate")!=null)criteria.add(Restrictions.le("PaymentDate", new Date(form.getCalendar("toPaymentDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("vendorId")>0) criteria.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = PaymentToVendorDAO.getInstance().getSession().createCriteria(PaymentToVendor.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromPaymentDate")!=null)criteria.add(Restrictions.ge("PaymentDate", new Date(form.getCalendar("fromPaymentDate").getTime().getTime())));
			if (form.getCalendar("toPaymentDate")!=null)criteria.add(Restrictions.le("PaymentDate", new Date(form.getCalendar("toPaymentDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("vendorId")>0) criteria.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("PAYMENTTOVENDOR",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("paymentToVendor");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				PaymentToVendorDAO.getInstance().closeSessionForReal();
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
		PaymentToVendorForm form = (PaymentToVendorForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// remove vendor bill
			PaymentToVendor obj = (PaymentToVendor)httpSession.getAttribute("paymentToVendor");
			if (form.getLong("vendorBillId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEVENDORBILL")) {
				PaymentToVendorDetail removePaymentToVendorDetail = null;
				Iterator iterator = obj.getPaymentToVendorDetails().iterator();
				while (iterator.hasNext()) {
					PaymentToVendorDetail paymentToVendorDetail = (PaymentToVendorDetail)iterator.next();
					if (form.getLong("vendorBillId") == paymentToVendorDetail.getId().getVendorBill().getId()) {
						removePaymentToVendorDetail = paymentToVendorDetail;
					}
				}
				if (removePaymentToVendorDetail!=null) {
					Set set = obj.getPaymentToVendorDetails();
					set.remove(removePaymentToVendorDetail);
					obj.setPaymentToVendorDetails(set);
				}
				form.setString("subaction", "");
				form.setString("vendorBillId", "");
				httpSession.setAttribute("paymentToVendor", obj);
			}
			// remove retur
			if (form.getLong("returToVendorId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVERETURTOVENDOR")) {
			    ReturToVendor removeReturToVendor = null;
					Iterator iterator = obj.getReturToVendors().iterator();
					while (iterator.hasNext()) {
						ReturToVendor returToVendor = (ReturToVendor)iterator.next();
						if (form.getLong("returToVendorId") == returToVendor.getId()) {
						    removeReturToVendor = returToVendor;
						}
					}
					if (removeReturToVendor!=null) {
						Set set = obj.getReturToVendors();
						set.remove(removeReturToVendor);
						obj.setReturToVendors(set);
					}
					form.setString("subaction", "");
					form.setString("returToVendorId", "");
					httpSession.setAttribute("paymentToVendor", obj);
			}
			// relationships
			List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("vendorLst", vendorLst);
			List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("currencyLst", currencyLst);
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			List bankAccountLst = BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("bankAccountLst", bankAccountLst);
			List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("locationLst", locationLst);
			if (form.getLong("paymentToVendorId") == 0) {
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				form.setString("paymentToVendorId",0);
				form.setCurentTimestamp("createOn");
				form.setString("number", RunningNumberDAO.getInstance().getPaymentToVendorNumber());
				form.setCurentCalendar("paymentDate");
				if (obj!=null) {
					form.setString("paymentToVendorId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setCalendar("paymentDate",obj.getPaymentDate());
					form.setString("number",obj.getNumber());
					form.setString("cardNo",obj.getCardNo());
					form.setString("checkNo",obj.getCheckNo());
					form.setString("reference",obj.getReference());
					form.setString("description",obj.getDescription());
					form.setString("method",obj.getMethod());
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
					form.setString("exceedAccountId",obj.getExceedAccount()!=null?obj.getExceedAccount().getId():0);
					form.setString("bankAccountId",obj.getBankAccount()!=null?obj.getBankAccount().getId():0);
					form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
					form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
					Set paymentToVendorDetailLst = obj.getPaymentToVendorDetails();
					request.setAttribute("paymentToVendorDetailLst", paymentToVendorDetailLst);
					Set returToVendorLst = obj.getReturToVendors();
					request.setAttribute("returToVendorLst", returToVendorLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = PaymentToVendorDAO.getInstance().get(form.getLong("paymentToVendorId"));
					httpSession.setAttribute("paymentToVendor",obj);
				}
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				form.setString("paymentToVendorId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setCalendar("paymentDate",obj.getPaymentDate());
				form.setString("number",obj.getNumber());
				form.setString("cardNo",obj.getCardNo());
				form.setString("checkNo",obj.getCheckNo());
				form.setString("reference",obj.getReference());
				form.setString("description",obj.getDescription());
				form.setString("method",obj.getMethod());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("projectId",obj.getProject()!=null?obj.getProject().getId():0);
				form.setString("exceedAccountId",obj.getExceedAccount()!=null?obj.getExceedAccount().getId():0);
				form.setString("bankAccountId",obj.getBankAccount()!=null?obj.getBankAccount().getId():0);
				form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				Set paymentToVendorDetailLst = obj.getPaymentToVendorDetails();
				request.setAttribute("paymentToVendorDetailLst", paymentToVendorDetailLst);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set returToVendorLst = obj.getReturToVendors();
				request.setAttribute("returToVendorLst", returToVendorLst);
			}
			if (form.getLong("vendorBillId") > 0) {
				if (obj!=null && obj.getPaymentToVendorDetails()!=null) {
					Iterator iterator = obj.getPaymentToVendorDetails().iterator();
					while (iterator.hasNext()) {
						PaymentToVendorDetail paymentToVendorDetail = (PaymentToVendorDetail)iterator.next();
						if (form.getLong("vendorBillId") == paymentToVendorDetail.getId().getVendorBill().getId()) {
							request.setAttribute("vendorBill", paymentToVendorDetail.getId().getVendorBill());
							form.setString("paymentAmount", Formater.getFormatedOutputForm(obj.getNumberOfDigit(), paymentToVendorDetail.getPaymentAmount()));
							//form.setString("giroAmount", Formater.getFormatedOutputForm(paymentToVendorDetail.getGiroAmount()));
							form.setString("vendorBillExchangeRate", Formater.getFormatedOutputForm(5, paymentToVendorDetail.getVendorBillExchangeRate()));
							form.setString("vendorBillAmount", Formater.getFormatedOutputForm(obj.getNumberOfDigit(), paymentToVendorDetail.getVendorBillAmount()));
						}
					}
				}
			}
			request.setAttribute("paymentToVendorDetailAmount", obj!=null?obj.getFormatedPaymentToVendorDetailAmount():"-");
			request.setAttribute("returToVendorAmount", obj!=null?obj.getFormatedReturToVendorAmount():"-");
			request.setAttribute("paymentAmount", obj!=null?obj.getFormatedPaymentAmount():"-");
		}catch(Exception ex) {
			try {
				List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("vendorLst", vendorLst);
				List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("currencyLst", currencyLst);
				List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				List bankAccountLst = BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("bankAccountLst", bankAccountLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				PaymentToVendorDAO.getInstance().closeSessionForReal();
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
		PaymentToVendorForm form = (PaymentToVendorForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = PaymentToVendorDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("paymentToVendor");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			PaymentToVendor obj = (PaymentToVendor)httpSession.getAttribute("paymentToVendor");
			if (form.getLong("paymentToVendorId") == 0) {
				obj = (PaymentToVendor)PaymentToVendorDAO.getInstance().getSession().createCriteria(PaymentToVendor.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (PaymentToVendor)httpSession.getAttribute("paymentToVendor");
					if (obj==null) {
						obj = new PaymentToVendor();
						obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
						Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
						obj.setCurrency(currency);
						// vendor bill
						Set set = obj.getPaymentToVendorDetails();
						if (set==null) set = new LinkedHashSet();
						Criteria criteria = VendorBillDAO.getInstance().getSession().createCriteria(VendorBill.class)
							.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("PaymentToVendorStatus", new String(CommonConstants.CLOSE)))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)));
						if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
						List vendorBillLst = criteria.list();
						Iterator iterator = vendorBillLst.iterator();
						while (iterator.hasNext()) {
							VendorBill vendorBill = (VendorBill)iterator.next();
							if (vendorBill.getDifferenceAmount() > 0) {
								PaymentToVendorDetail paymentToVendorDetail = new PaymentToVendorDetail();
								//paymentToVendorDetail.setType("G");
								paymentToVendorDetail.setVendorBillAmount(vendorBill.getVendorBillAfterDiscountAndTaxAndPrepayment());
								//double a = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(vendorBill.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate"));
								//paymentToVendorDetail.setVendorBillExchangeRate(a);
								double a = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(vendorBill.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate")));
								paymentToVendorDetail.setVendorBillExchangeRate(a);
								//log.info("paymentToVendorDetail.getVendorBillExchangeRate() : "+a);
								paymentToVendorDetail.setPaymentAmount(vendorBill.getVendorBillAfterDiscountAndTaxAndPrepayment() * a);
								PaymentToVendorDetailPK paymentToVendorDetailPK = new PaymentToVendorDetailPK();
								paymentToVendorDetailPK.setPaymentToVendor(obj);
								paymentToVendorDetailPK.setVendorBill(vendorBill);
								paymentToVendorDetail.setId(paymentToVendorDetailPK);
								//paymentToVendorDetail.setVendorBillExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(vendorBill.getCurrency(), obj.getCurrency(), organizationSetup));
								set.add(paymentToVendorDetail);
							}
						}
						obj.setPaymentToVendorDetails(set);
						// retur
						Set set2 = obj.getReturToVendors();
						if (set2==null)set2 = new LinkedHashSet();
						Criteria criteria2 = ReturToVendorDAO.getInstance().getSession().createCriteria(ReturToVendor.class)
							.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("PaymentToVendorStatus", new String(CommonConstants.CLOSE)))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)));
						if (form.getLong("projectId")>0) criteria2.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
						List returToVendorLst = criteria2.list();
						Iterator iterator2 = returToVendorLst.iterator();
						while (iterator2.hasNext()) {
						    ReturToVendor returToVendor = (ReturToVendor)iterator2.next();
						    set2.add(returToVendor);
						}
						obj.setReturToVendors(set2);
					}
					obj.setDescription(form.getString("description"));
					obj.setNumber(form.getString("number"));
					obj.setCardNo(form.getString("cardNo"));
					obj.setCheckNo(form.getString("checkNo"));
					obj.setMethod(form.getString("method"));
					obj.setReference(form.getString("reference"));
					obj.setPosted(false);
					obj.setOrganization(users.getOrganization());
					obj.setPaymentDate(form.getCalendar("paymentDate")!=null?form.getCalendar("paymentDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					BankAccount bankAccount = BankAccountDAO.getInstance().get(form.getLong("bankAccountId"));
					obj.setBankAccount(bankAccount);
					ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("exceedAccountId"));
					obj.setExceedAccount(chartOfAccount);
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
					obj.setProject(project);
					//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("paymentDate"));
					double e = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("paymentDate")));
					obj.setExchangeRate(e);
					Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(vendors);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					obj.setLocation(location);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					// create new paymentToVendor
					if (form.getLong("vendorId")!=obj.getVendor().getId()) {
						Set set = new LinkedHashSet();
						Criteria criteria = VendorBillDAO.getInstance().getSession().createCriteria(VendorBill.class)
							.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("PaymentToVendorStatus", new String(CommonConstants.CLOSE)))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)));
						if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
						List vendorBillLst = criteria.list();
						Iterator iterator = vendorBillLst.iterator();
						while (iterator.hasNext()) {
							VendorBill vendorBill = (VendorBill)iterator.next();
							if (vendorBill.getDifferenceAmount() > 0) {
								PaymentToVendorDetail paymentToVendorDetail = new PaymentToVendorDetail();
								//paymentToVendorDetail.setType("G");
								paymentToVendorDetail.setVendorBillAmount(vendorBill.getVendorBillAfterDiscountAndTaxAndPrepayment());
								double a = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(vendorBill.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate")));
								paymentToVendorDetail.setVendorBillExchangeRate(a);
								paymentToVendorDetail.setPaymentAmount(vendorBill.getVendorBillAfterDiscountAndTaxAndPrepayment() * a);
								PaymentToVendorDetailPK paymentToVendorDetailPK = new PaymentToVendorDetailPK();
								paymentToVendorDetailPK.setPaymentToVendor(obj);
								paymentToVendorDetailPK.setVendorBill(vendorBill);
								paymentToVendorDetail.setId(paymentToVendorDetailPK);
								//paymentToVendorDetail.setVendorBillExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(vendorBill.getCurrency(), obj.getCurrency(), organizationSetup));
								set.add(paymentToVendorDetail);
							}
						}
						obj.setPaymentToVendorDetails(set);
						// retur
						Set set2 = obj.getReturToVendors();
						if (set2==null)set2 = new LinkedHashSet();
						Criteria criteria2 = ReturToVendorDAO.getInstance().getSession().createCriteria(ReturToVendor.class)
							.add(Restrictions.eq("Vendor.Id", new Long(form.getLong("vendorId"))))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.ne("PaymentToVendorStatus", new String(CommonConstants.CLOSE)))
							.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)));
						if (form.getLong("projectId")>0) criteria2.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
						List returToVendorLst = criteria2.list();
						Iterator iterator2 = returToVendorLst.iterator();
						while (iterator2.hasNext()) {
						    ReturToVendor returToVendor = (ReturToVendor)iterator2.next();
						    set2.add(returToVendor);
						}
						obj.setReturToVendors(set2);
					}
					BankTransaction bankTransaction = new BankTransaction();
					bankTransaction.setNumber(RunningNumberDAO.getInstance().getBankTransactionNumber());
					bankTransaction.setAmount(obj.getPaymentToVendorDetailAmount());
					bankTransaction.setTransactionDate(obj.getPaymentDate());
					bankTransaction.setFromBankAccount(obj.getBankAccount());
					bankTransaction.setProject(obj.getProject());
					bankTransaction.setCurrency(currency);
					bankTransaction.setVendor(vendors);
					bankTransaction.setExchangeRate(e);
					bankTransaction.setOrganization(users.getOrganization());
					bankTransaction.setPosted(false);
					bankTransaction.setReconcileBankFrom(false);
					bankTransaction.setPaymentToVendor(obj);
					bankTransaction.setCreateBy(users);
					bankTransaction.setCreateOn(form.getTimestamp("createOn"));
					// create journal
					Journal journal = new Journal();
					journal.setPaymentToVendor(obj);
					journal.setCurrency(obj.getCurrency());
					journal.setExchangeRate(e);
					journal.setJournalDate(obj.getPaymentDate());
					journal.setProject(obj.getProject());
					journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
					journal.setOrganization(users.getOrganization());
					journal.setPosted(false);
					journal.setReference(form.getString("reference"));
					journal.setVendor(vendors);
					journal.setCreateBy(users);
					journal.setCreateOn(form.getTimestamp("createOn"));
					// journal detail
					Set set = null;
					if (set==null) set = new LinkedHashSet();
					// credit
					JournalDetail journalDetail = new JournalDetail();
					JournalDetailPK journalDetailPK = new JournalDetailPK();
					journalDetailPK.setChartOfAccount(bankAccount.getChartOfAccount());
					journalDetailPK.setJournal(journal);
					journalDetail.setId(journalDetailPK);
					if (bankAccount!=null)journalDetail.setAmount(bankAccount.getChartOfAccount().isDebit()==false?obj.getPaymentToVendorDetailAmount():-obj.getPaymentToVendorDetailAmount());
					set.add(journalDetail);
					// debit
					if (obj.getPaymentToVendorDetailAmount()-obj.getVendorBillAmount()>0) {
						JournalDetail journalDetail2 = new JournalDetail();
						JournalDetailPK journalDetailPK2 = new JournalDetailPK();
						journalDetailPK2.setChartOfAccount(obj.getExceedAccount());
						journalDetailPK2.setJournal(journal);
						journalDetail2.setId(journalDetailPK2);
						if (obj.getExceedAccount()!=null)journalDetail2.setAmount(obj.getExceedAccount().isDebit()==true?obj.getPaymentToVendorDetailAmount()-obj.getVendorBillAmount():-(obj.getPaymentToVendorDetailAmount()-obj.getVendorBillAmount()));
						set.add(journalDetail2);
					}
					// selisih kurs => TODO
					// debit
					JournalDetail journalDetail3 = new JournalDetail();
					JournalDetailPK journalDetailPK3 = new JournalDetailPK();
					journalDetailPK3.setJournal(journal);
					if (vendors.getChartOfAccount()==null) {
						journalDetailPK3.setChartOfAccount(organizationSetup.getApAccount());
						journalDetail3.setAmount(organizationSetup.getApAccount().isDebit()==true?obj.getVendorBillAmount():-obj.getVendorBillAmount());
					} else {
						journalDetailPK3.setChartOfAccount(vendors.getChartOfAccount());
						journalDetail3.setAmount(vendors.getChartOfAccount().isDebit()==true?obj.getVendorBillAmount():-obj.getVendorBillAmount());
					}
					journalDetail3.setId(journalDetailPK3);
					set.add(journalDetail3);
					journal.setJournalDetails(set);
					obj.setJournal(journal);
					bankTransaction.setJournal(journal);
					obj.setJournal(journal);
					journal.setBankTransaction(bankTransaction);
					obj.setBankTransaction(bankTransaction);
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = PaymentToVendorDAO.getInstance().load(form.getLong("paymentToVendorId"));
				obj.setDescription(form.getString("description"));
				obj.setNumber(form.getString("number"));
				obj.setCardNo(form.getString("cardNo"));
				obj.setCheckNo(form.getString("checkNo"));
				obj.setMethod(form.getString("method"));
				obj.setReference(form.getString("reference"));
				//obj.setPosted(false);
				obj.setOrganization(users.getOrganization());
				obj.setPaymentDate(form.getCalendar("paymentDate")!=null?form.getCalendar("paymentDate").getTime():null);
				//obj.setStatus(CommonConstants.OPEN);
				BankAccount bankAccount = BankAccountDAO.getInstance().get(form.getLong("bankAccountId"));
				obj.setBankAccount(bankAccount);
				ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("exceedAccountId"));
				obj.setExceedAccount(chartOfAccount);
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
				obj.setProject(project);
				//double e = CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("paymentDate"));
				double e = Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("paymentDate")));
				obj.setExchangeRate(e);
				Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
				obj.setVendor(vendors);
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				obj.setLocation(location);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				// bankTransaction
				BankTransaction bankTransaction = obj.getBankTransaction();
				bankTransaction.setAmount(obj.getPaymentToVendorDetailAmount());
				bankTransaction.setTransactionDate(obj.getPaymentDate());
				bankTransaction.setFromBankAccount(obj.getBankAccount());
				bankTransaction.setCurrency(currency);
				bankTransaction.setProject(obj.getProject());
				bankTransaction.setVendor(vendors);
				bankTransaction.setExchangeRate(e);
				bankTransaction.setOrganization(users.getOrganization());
				//bankTransaction.setPosted(false);
				//bankTransaction.setReconcileBankFrom(false);
				bankTransaction.setPaymentToVendor(obj);
				bankTransaction.setCreateBy(createBy);
				bankTransaction.setCreateOn(form.getTimestamp("createOn"));
				bankTransaction.setChangeBy(users);
				bankTransaction.setChangeOn(form.getTimestamp("changeOn"));
				// create journal
				//Journal journal = obj.getJournal();
				Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
					.add(Restrictions.eq("PaymentToVendor.Id", new Long(obj.getId()))).uniqueResult();
				//journal.setPaymentToVendor(obj);
				journal.setCurrency(obj.getCurrency());
				journal.setExchangeRate(e);
				journal.setJournalDate(obj.getPaymentDate());
				//journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
				journal.setOrganization(users.getOrganization());
				//journal.setPosted(false);
				journal.setReference(form.getString("reference"));
				journal.setVendor(vendors);
				journal.setProject(obj.getProject());
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
				journalDetailPK.setChartOfAccount(obj.getBankAccount().getChartOfAccount());
				journalDetailPK.setJournal(journal);
				journalDetail.setId(journalDetailPK);
				if (obj.getBankAccount()!=null)journalDetail.setAmount(obj.getBankAccount().getChartOfAccount().isDebit()==false?obj.getPaymentToVendorDetailAmount():-obj.getPaymentToVendorDetailAmount());
				set.add(journalDetail);
				// debit
				if (obj.getPaymentToVendorDetailAmount()-obj.getVendorBillAmount()>0) {
					JournalDetail journalDetail2 = new JournalDetail();
					JournalDetailPK journalDetailPK2 = new JournalDetailPK();
					journalDetailPK2.setChartOfAccount(obj.getExceedAccount());
					journalDetailPK2.setJournal(journal);
					journalDetail2.setId(journalDetailPK2);
					if (obj.getExceedAccount()!=null)journalDetail2.setAmount(obj.getExceedAccount().isDebit()==true?obj.getPaymentToVendorDetailAmount()-obj.getVendorBillAmount():-(obj.getPaymentToVendorDetailAmount()-obj.getVendorBillAmount()));
					set.add(journalDetail2);
				}
				// selisih kurs => TODO
				// debit
				JournalDetail journalDetail3 = new JournalDetail();
				JournalDetailPK journalDetailPK3 = new JournalDetailPK();
				journalDetailPK3.setJournal(journal);
				if (vendors.getChartOfAccount()==null) {
					journalDetailPK3.setChartOfAccount(organizationSetup.getApAccount());
					journalDetail3.setAmount(organizationSetup.getApAccount().isDebit()==true?obj.getVendorBillAmount():-obj.getVendorBillAmount());
				} else {
					journalDetailPK3.setChartOfAccount(vendors.getChartOfAccount());
					journalDetail3.setAmount(vendors.getChartOfAccount().isDebit()==true?obj.getVendorBillAmount():-obj.getVendorBillAmount());
				}
				journalDetail3.setId(journalDetailPK3);
				set.add(journalDetail3);
				journal.setJournalDetails(set);
				obj.setJournal(journal);
				//bankTransaction.setJournal(journal);
				//journal.setBankTransaction(bankTransaction);
				obj.setBankTransaction(bankTransaction);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDVENDORBILL")) {
				if (form.getLong("vendorBillId") >0) {
				  PaymentToVendorDetail paymentToVendorDetail = new PaymentToVendorDetail();
					PaymentToVendorDetailPK paymentToVendorDetailPK = new PaymentToVendorDetailPK();
					VendorBill vendorBill = VendorBillDAO.getInstance().get(form.getLong("vendorBillId"));
					paymentToVendorDetailPK.setVendorBill(vendorBill);
					paymentToVendorDetailPK.setPaymentToVendor(obj);
					paymentToVendorDetail.setId(paymentToVendorDetailPK);
					//paymentToVendorDetail.setType(form.getString("type"));
					paymentToVendorDetail.setVendorBillAmount(form.getDouble("vendorBillAmount"));
					//paymentToVendorDetail.setCashAmount(form.getDouble("cashAmount"));
					paymentToVendorDetail.setPaymentAmount(form.getDouble("paymentAmount"));
					//paymentToVendorDetail.setVendorBillExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(vendorBill.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("paymentDate")));
					paymentToVendorDetail.setVendorBillExchangeRate(form.getDouble("vendorBillExchangeRate"));
					Set set = obj.getPaymentToVendorDetails();
					if (set==null) set = new LinkedHashSet();
					PaymentToVendorDetail removePaymentToVendorDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						PaymentToVendorDetail paymentToVendorDetail2 = (PaymentToVendorDetail)iterator.next();
						if (form.getLong("vendorBillId")==paymentToVendorDetail2.getId().getVendorBill().getId()) {
							removePaymentToVendorDetail = paymentToVendorDetail2;
						}
					}
					if (removePaymentToVendorDetail!=null) {
						set.remove(removePaymentToVendorDetail);
						set.add(paymentToVendorDetail);
					} else {
						set.add(paymentToVendorDetail);
					}

					obj.setPaymentToVendorDetails(set);
					// netral
					form.setString("vendorBillId", "");
					form.setString("paymentAmount", "");
					form.setString("vendorBillExchangeRate", "");
					form.setString("vendorBillAmount", "");
					form.setString("type", "");
				}
				// netral
				form.setString("vendorBillId", "");
				form.setString("paymentAmount", "");
				form.setString("vendorBillExchangeRate", "");
				form.setString("vendorBillAmount", "");
				form.setString("type", "");
			}
			// save to session
			httpSession.setAttribute("paymentToVendor", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updatePaymentToVendorNumber(session);
					RunningNumberDAO.getInstance().updateJournalNumber(session);
					RunningNumberDAO.getInstance().updateBankTransactionNumber(session);
					PaymentToVendorDAO.getInstance().save(obj, session);
					transaction.commit();
					form.setString("paymentToVendorId", obj.getId());
				} else {
					transaction = session.beginTransaction();
					PaymentToVendorDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("paymentToVendor");
				// finish
				ActionForward forward = mapping.findForward("update_status");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?paymentToVendorId="+form.getLong("paymentToVendorId")+"&purchaseOrderId="+form.getLong("purchaseOrderId"));
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
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					List bankAccountLst = BankAccountDAO.getInstance().getSession().createCriteria(BankAccount.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("bankAccountLst", bankAccountLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				PaymentToVendorDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?paymentToVendorId="+form.getLong("paymentToVendorId"));
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
		PaymentToVendorForm form = (PaymentToVendorForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			Session session = PaymentToVendorDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			long purchaseOrderId = 0;
			// vendor Bill
			PaymentToVendor paymentToVendor = PaymentToVendorDAO.getInstance().get(form.getLong("paymentToVendorId"));
			if (paymentToVendor!=null) {
				Set paymentToVendorDetailLst = paymentToVendor.getPaymentToVendorDetails();
				Iterator iterator = paymentToVendorDetailLst.iterator();
				while (iterator.hasNext()) {
					PaymentToVendorDetail paymentToVendorDetail = (PaymentToVendorDetail)iterator.next();
					VendorBill vendorBill = paymentToVendorDetail.getId().getVendorBill();
					//log.info("Amount : "+vendorBill.getAmount());
					if (Formater.getFormatedOutputResult(vendorBill.getNumberOfDigit(), vendorBill.getDifferenceAmount())<=0) {
						vendorBill.setPaymentToVendorStatus(CommonConstants.CLOSE);
					} else {
						if (vendorBill.getPaymentToVendorAmount()>0) {
							vendorBill.setPaymentToVendorStatus(CommonConstants.PARTIAL);
						}
					}
					// update
					VendorBillDAO.getInstance().getSession().merge(vendorBill);
					purchaseOrderId = vendorBill.getReceiving().getPurchaseOrder().getId();
					//VendorBillDAO.getInstance().update(vendorBill, session);
				}
				// retur
				Set returToVendorLst = paymentToVendor.getReturToVendors();
				Iterator iterator2 = returToVendorLst.iterator();
				while (iterator2.hasNext()) {
				    ReturToVendor returToVendor = (ReturToVendor) iterator2.next();
				    returToVendor.setPaymentToVendorStatus(CommonConstants.CLOSE);
				    ReturToVendorDAO.getInstance().getSession().merge(returToVendor);
				}
			}
			boolean isClosed = true;
			//log.info("A : "+purchaseOrderId);
			if (purchaseOrderId > 0) {
				// update PO => paymentToVendorStatus!!!
				PurchaseOrder purchaseOrder = PurchaseOrderDAO.getInstance().get(purchaseOrderId);
				Set receivingLst = purchaseOrder.getReceivings();
				Iterator iterator2 = receivingLst.iterator();
				while (iterator2.hasNext()) {
					Receiving receiving = (Receiving)iterator2.next();
					VendorBill vendorBill = receiving.getVendorBill();
					if (vendorBill==null || (vendorBill!=null && !vendorBill.getPaymentToVendorStatus().equalsIgnoreCase(CommonConstants.CLOSE))) {
						isClosed = false;
					}
				}
				if (isClosed) {
					purchaseOrder.setPaymentToVendorStatus(CommonConstants.CLOSE);
					// update
					PurchaseOrderDAO.getInstance().update(purchaseOrder, session);
				} else {
					purchaseOrder.setPaymentToVendorStatus(CommonConstants.PARTIAL);
					// update
					PurchaseOrderDAO.getInstance().update(purchaseOrder, session);
				}
			}
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			//return (new ActionForward(mapping.getInput()));
			log.info("Error : "+ex);
		}finally {
			try {
				PaymentToVendorDAO.getInstance().closeSessionForReal();
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
		PaymentToVendorForm form = (PaymentToVendorForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			PaymentToVendor paymentToVendor = PaymentToVendorDAO.getInstance().get(form.getLong("paymentToVendorId"));
			request.setAttribute("paymentToVendor", paymentToVendor);
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				PaymentToVendorDAO.getInstance().closeSessionForReal();
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
		PaymentToVendorForm form = (PaymentToVendorForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			PaymentToVendorDAO.getInstance().delete(form.getLong("paymentToVendorId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				PaymentToVendorDAO.getInstance().closeSessionForReal();
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