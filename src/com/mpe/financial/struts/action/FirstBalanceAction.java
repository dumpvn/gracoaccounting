//---------------------------------------------------------
// Application: Garage
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright © 2005 MPE
// Generated at Sat Sep 03 19:38:16 GMT+07:00 2005
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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.CustomerFirstBalance;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.GeneralLedger;
import com.mpe.financial.model.Inventory;
import com.mpe.financial.model.Item;
import com.mpe.financial.model.ItemFirstBalance;
import com.mpe.financial.model.ItemUnit;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.VendorFirstBalance;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.Warehouse;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.CustomerFirstBalanceDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.GeneralLedgerDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemFirstBalanceDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.VendorFirstBalanceDAO;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.model.dao.WarehouseDAO;
import com.mpe.financial.struts.form.GeneralLedgerForm;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import com.mpe.common.*;

public class FirstBalanceAction extends Action {
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
	  GeneralLedgerForm generalLedgerForm = (GeneralLedgerForm) form;
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
					} else if ("FORM".equalsIgnoreCase(action)) {
						forward = performForm(mapping, form, request, response);
					} else if ("SAVE".equalsIgnoreCase(action)) {
						forward = performSave(mapping, form, request, response);
					} else if ("DELETE".equalsIgnoreCase(action)) {
						forward = performDelete(mapping, form, request, response);
					} else if ("VENDORFORM".equalsIgnoreCase(action)) {
						forward = performVendorForm(mapping, form, request, response);
					} else if ("VENDORSAVE".equalsIgnoreCase(action)) {
						forward = performVendorSave(mapping, form, request, response);
					} else if ("VENDORDELETE".equalsIgnoreCase(action)) {
						forward = performVendorDelete(mapping, form, request, response);
					} else if ("CUSTOMERFORM".equalsIgnoreCase(action)) {
						forward = performCustomerForm(mapping, form, request, response);
					} else if ("CUSTOMERSAVE".equalsIgnoreCase(action)) {
						forward = performCustomerSave(mapping, form, request, response);
					} else if ("CUSTOMERDELETE".equalsIgnoreCase(action)) {
						forward = performCustomerDelete(mapping, form, request, response);
					} else if ("INVENTORYFORM".equalsIgnoreCase(action)) {
						forward = performInventoryForm(mapping, form, request, response);
					} else if ("INVENTORYSAVE".equalsIgnoreCase(action)) {
					  if (generalLedgerForm.getString("subaction")!=null && generalLedgerForm.getString("subaction").equalsIgnoreCase("refresh")) {
					      forward = performInventoryForm(mapping, form, request, response);
					  } else forward = performInventorySave(mapping, form, request, response);
					} else if ("INVENTORYDELETE".equalsIgnoreCase(action)) {
						forward = performInventoryDelete(mapping, form, request, response);
					} else if ("CUSTOMERINVENTORYFORM".equalsIgnoreCase(action)) {
						forward = performCustomerInventoryForm(mapping, form, request, response);
					} else if ("CUSTOMERINVENTORYSAVE".equalsIgnoreCase(action)) {
						forward = performCustomerInventorySave(mapping, form, request, response);
					} else if ("CUSTOMERINVENTORYDELETE".equalsIgnoreCase(action)) {
						forward = performCustomerInventoryDelete(mapping, form, request, response);
					} else if ("LOCATIONINVENTORYFORM".equalsIgnoreCase(action)) {
						forward = performLocationInventoryForm(mapping, form, request, response);
					} else if ("LOCATIONINVENTORYSAVE".equalsIgnoreCase(action)) {
						forward = performLocationInventorySave(mapping, form, request, response);
					} else if ("LOCATIONINVENTORYDELETE".equalsIgnoreCase(action)) {
						forward = performLocationInventoryDelete(mapping, form, request, response);
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
	 * Method performForm
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */			
	private ActionForward performForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// relationships
		  List firstBalanceLst = GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
		  .add(Restrictions.eq("Setup", Boolean.TRUE))
			.add(Restrictions.eq("Closed", Boolean.FALSE))
			.addOrder(Order.asc("ChartOfAccount")).list();
		  request.setAttribute("firstBalanceLst", firstBalanceLst);
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  request.setAttribute("defaultCurrency", organizationSetup.getDefaultCurrency());
		  List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
		  request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			if (form.getLong("generalLedgerId") == 0) {
				form.setString("generalLedgerId",0);
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				GeneralLedger obj = GeneralLedgerDAO.getInstance().get(form.getLong("generalLedgerId"));
				form.setString("generalLedgerId",obj.getId());
				form.setString("chartOfAccountId",obj.getChartOfAccount()!=null?obj.getChartOfAccount().getId():0);
				form.setString("amount",Formater.getFormatedOutputForm(obj.getAmount()));
			}
			// get total
			double debitAmountTotal = 0;
			double creditAmountTotal = 0;
			java.util.Iterator iterator = firstBalanceLst.iterator();
			while (iterator.hasNext()) {
				GeneralLedger generalLedger = (GeneralLedger) iterator.next();
				ChartOfAccount chartOfAccount = generalLedger.getChartOfAccount();
				if (generalLedger.getAmount()>0) {
					if (generalLedger.isDebit()==true) {
					    debitAmountTotal = debitAmountTotal + generalLedger.getAmount();
					} else {
					    creditAmountTotal = creditAmountTotal + generalLedger.getAmount();
					}
				} else {
					if (generalLedger.isDebit()==true) {
					    creditAmountTotal = creditAmountTotal - generalLedger.getAmount();
					} else {
					    debitAmountTotal = debitAmountTotal - generalLedger.getAmount();
					}
				}
			}
			request.setAttribute("debitAmountTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitAmountTotal));
			request.setAttribute("creditAmountTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditAmountTotal));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
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
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			GeneralLedger obj = null;
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			if (form.getLong("generalLedgerId") == 0) {
				obj = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Setup", Boolean.TRUE))
				.add(Restrictions.eq("ChartOfAccount.Id", new Long(form.getLong("chartOfAccountId")))).uniqueResult();
				if (obj==null) {
				  Calendar calendar = new GregorianCalendar();
					obj = new GeneralLedger();
					ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
					obj.setChartOfAccount(chartOfAccount);
					obj.setAmount(form.getDouble("amount"));
					obj.setOrganization(users.getOrganization()); 
					obj.setClosed(false);
					obj.setCurrency(organizationSetup.getDefaultCurrency());
					obj.setDebit(chartOfAccount.isDebit());
					obj.setLedgerDate(calendar.getTime());
					obj.setSetup(true);
					GeneralLedgerDAO.getInstance().save(obj);
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("name")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				obj = GeneralLedgerDAO.getInstance().load(form.getLong("generalLedgerId"));
				Calendar calendar = new GregorianCalendar();
				obj = new GeneralLedger();
				ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
				obj.setChartOfAccount(chartOfAccount);
				obj.setAmount(form.getDouble("amount"));
				obj.setOrganization(users.getOrganization()); 
				obj.setClosed(false);
				obj.setCurrency(organizationSetup.getDefaultCurrency());
				obj.setDebit(chartOfAccount.isDebit());
				obj.setLedgerDate(calendar.getTime());
				obj.setSetup(true);
				obj.setId(form.getLong("generalLedgerId"));
				GeneralLedgerDAO.getInstance().update(obj);
			}
		}catch(Exception ex) {
			try {
				try {
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			GeneralLedgerDAO.getInstance().delete(form.getLong("generalLedgerId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performVendorForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// relationships
		  List firstVendorBalanceLst = VendorFirstBalanceDAO.getInstance().getSession().createCriteria(VendorFirstBalance.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
		  	.list();
		  request.setAttribute("firstVendorBalanceLst", firstVendorBalanceLst);
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  request.setAttribute("defaultCurrency", organizationSetup.getDefaultCurrency());
		  List vendorLst = VendorsDAO.getInstance().findAll(Order.asc("Company"));
		  request.setAttribute("vendorLst", vendorLst);
			if (form.getLong("vendorFirstBalanceId") == 0) {
				form.setString("vendorFirstBalanceId",0);
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				VendorFirstBalance obj = VendorFirstBalanceDAO.getInstance().get(form.getLong("vendorFirstBalanceId"));
				form.setString("vendorFirstBalanceId",obj.getId());
				form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
				form.setString("amount",Formater.getFormatedOutputForm(obj.getAmount()));
				
			}
			// get total
			double amountTotal = 0;
			java.util.Iterator iterator = firstVendorBalanceLst.iterator();
			while (iterator.hasNext()) {
				VendorFirstBalance vendorFirstBalance = (VendorFirstBalance) iterator.next();
				amountTotal = amountTotal + vendorFirstBalance.getAmount();
			}
			request.setAttribute("amountTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), amountTotal));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
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
	private ActionForward performVendorSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = null;
		Transaction transaction = null;
		try {
		  session = VendorFirstBalanceDAO.getInstance().getSession();
		  transaction = session.beginTransaction();
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  // save vendor first balance
		  VendorFirstBalance vendorFirstBalance = null;
		  ChartOfAccount apAccount = null;
		  double difference = 0;
		  if (form.getLong("vendorFirstBalanceId") == 0) {
		      Calendar calendar = new GregorianCalendar();
		      vendorFirstBalance = new VendorFirstBalance();
		      difference = form.getDouble("amount") - vendorFirstBalance.getAmount();
		      vendorFirstBalance.setAmount(form.getDouble("amount"));
		      vendorFirstBalance.setCurrency(organizationSetup.getDefaultCurrency());
		      vendorFirstBalance.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(vendorFirstBalance.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup,calendar));
		      vendorFirstBalance.setFirstBalanceDate(calendar.getTime());
		      vendorFirstBalance.setOrganization(users.getOrganization());
		      Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
		      apAccount = vendors.getChartOfAccount(); 
		      vendorFirstBalance.setVendor(vendors);
		      VendorFirstBalanceDAO.getInstance().save(vendorFirstBalance, session);
		  } else {
		      vendorFirstBalance = VendorFirstBalanceDAO.getInstance().load(form.getLong("vendorFirstBalanceId"));
		      difference = form.getDouble("amount") - vendorFirstBalance.getAmount();
		      vendorFirstBalance.setAmount(form.getDouble("amount"));
		      vendorFirstBalance.setCurrency(organizationSetup.getDefaultCurrency());
		      Calendar calendar = new GregorianCalendar();
		      calendar.setTime(vendorFirstBalance.getFirstBalanceDate());
		      vendorFirstBalance.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(vendorFirstBalance.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, calendar));
		      //vendorFirstBalance.setFirstBalanceDate(calendar.getTime());
		      vendorFirstBalance.setOrganization(users.getOrganization());
		      Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
		      apAccount = vendors.getChartOfAccount(); 
		      vendorFirstBalance.setVendor(vendors);
		      VendorFirstBalanceDAO.getInstance().update(vendorFirstBalance, session);
		  }
		  if (apAccount==null)apAccount = organizationSetup.getApAccount();
		  // save GL
			GeneralLedger obj = null;
			if (vendorFirstBalance!=null && apAccount!=null) {
				obj = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Setup", Boolean.TRUE))
				.add(Restrictions.eq("ChartOfAccount.Id", new Long(apAccount.getId()))).uniqueResult();
				if (obj==null) {
					obj = new GeneralLedger();
					obj.setChartOfAccount(apAccount);
					obj.setAmount(vendorFirstBalance.getAmount());
					obj.setOrganization(users.getOrganization()); 
					obj.setClosed(false);
					obj.setCurrency(organizationSetup.getDefaultCurrency());
					obj.setDebit(apAccount.isDebit());
					obj.setLedgerDate(vendorFirstBalance.getFirstBalanceDate());
					obj.setSetup(true);
					GeneralLedgerDAO.getInstance().save(obj, session);
				} else {
					obj.setChartOfAccount(apAccount);
					obj.setAmount(obj.getAmount() + difference);
					obj.setOrganization(users.getOrganization()); 
					obj.setClosed(false);
					obj.setCurrency(organizationSetup.getDefaultCurrency());
					obj.setDebit(apAccount.isDebit());
					obj.setLedgerDate(vendorFirstBalance.getFirstBalanceDate());
					obj.setSetup(true);
					GeneralLedgerDAO.getInstance().update(obj, session);
				}
			}
			transaction.commit();
		}catch(Exception ex) {
			try {
				try {
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performVendorDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Session session = VendorFirstBalanceDAO.getInstance().getSession();
		  Transaction transaction = session.beginTransaction();
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  VendorFirstBalance vendorFirstBalance = VendorFirstBalanceDAO.getInstance().load(form.getLong("vendorFirstBalanceId"));
			ChartOfAccount apAccount = vendorFirstBalance.getVendor().getChartOfAccount();
			if (apAccount==null) apAccount = organizationSetup.getApAccount(); 
		  GeneralLedger obj = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Setup", Boolean.TRUE))
				.add(Restrictions.eq("ChartOfAccount.Id", new Long(apAccount.getId()))).uniqueResult();
		  if (obj!=null) {
		      obj.setAmount(obj.getAmount()-vendorFirstBalance.getAmount());
		      GeneralLedgerDAO.getInstance().update(obj, session);
		  }
		  VendorFirstBalanceDAO.getInstance().delete(vendorFirstBalance, session);
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performCustomerForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// relationships
		  List firstCustomerBalanceLst = CustomerFirstBalanceDAO.getInstance().getSession().createCriteria(CustomerFirstBalance.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
		  	.list();
		  request.setAttribute("firstCustomerBalanceLst", firstCustomerBalanceLst);
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  request.setAttribute("defaultCurrency", organizationSetup.getDefaultCurrency());
		  List customerLst = CustomersDAO.getInstance().findAll(Order.asc("Company"));
		  request.setAttribute("customerLst", customerLst);
			if (form.getLong("customerFirstBalanceId") == 0) {
				form.setString("customerFirstBalanceId",0);
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				CustomerFirstBalance obj = CustomerFirstBalanceDAO.getInstance().get(form.getLong("customerFirstBalanceId"));
				form.setString("customerFirstBalanceId",obj.getId());
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				form.setString("amount",Formater.getFormatedOutputForm(obj.getAmount()));
				
			}
			// get total
			double amountTotal = 0;
			java.util.Iterator iterator = firstCustomerBalanceLst.iterator();
			while (iterator.hasNext()) {
			  CustomerFirstBalance customerFirstBalance = (CustomerFirstBalance) iterator.next();
				amountTotal = amountTotal + customerFirstBalance.getAmount();
			}
			request.setAttribute("amountTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), amountTotal));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
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
	private ActionForward performCustomerSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = null;
		Transaction transaction = null;
		try {
		  session = VendorFirstBalanceDAO.getInstance().getSession();
		  transaction = session.beginTransaction();
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  // save vendor first balance
		  CustomerFirstBalance customerFirstBalance = null;
		  ChartOfAccount arAccount = null;
		  double difference = 0;
		  if (form.getLong("customerFirstBalanceId") == 0) {
		      Calendar calendar = new GregorianCalendar();
		      customerFirstBalance = new CustomerFirstBalance();
		      difference = form.getDouble("amount") - customerFirstBalance.getAmount();
		      customerFirstBalance.setAmount(form.getDouble("amount"));
		      customerFirstBalance.setCurrency(organizationSetup.getDefaultCurrency());
		      customerFirstBalance.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(customerFirstBalance.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, calendar));
		      customerFirstBalance.setFirstBalanceDate(calendar.getTime());
		      customerFirstBalance.setOrganization(users.getOrganization());
		      Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
		      arAccount = customers.getChartOfAccount(); 
		      customerFirstBalance.setCustomer(customers);
		      CustomerFirstBalanceDAO.getInstance().save(customerFirstBalance, session);
		  } else {
		      customerFirstBalance = CustomerFirstBalanceDAO.getInstance().load(form.getLong("customerFirstBalanceId"));
		      difference = form.getDouble("amount") - customerFirstBalance.getAmount();
		      customerFirstBalance.setAmount(form.getDouble("amount"));
		      customerFirstBalance.setCurrency(organizationSetup.getDefaultCurrency());
		      Calendar calendar = new GregorianCalendar();
		      calendar.setTime(customerFirstBalance.getFirstBalanceDate());
		      customerFirstBalance.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(customerFirstBalance.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, calendar));
		      //vendorFirstBalance.setFirstBalanceDate(calendar.getTime());
		      customerFirstBalance.setOrganization(users.getOrganization());
		      Customers customers = CustomersDAO.getInstance().get(form.getLong("customersId"));
		      arAccount = customers.getChartOfAccount(); 
		      customerFirstBalance.setCustomer(customers);
		      CustomerFirstBalanceDAO.getInstance().update(customerFirstBalance, session);
		  }
		  if (arAccount==null)arAccount = organizationSetup.getArAccount();
		  // save GL
			GeneralLedger obj = null;
			if (customerFirstBalance!=null && arAccount!=null) {
				obj = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Setup", Boolean.TRUE))
				.add(Restrictions.eq("ChartOfAccount.Id", new Long(arAccount.getId()))).uniqueResult();
				if (obj==null) {
					obj = new GeneralLedger();
					obj.setChartOfAccount(arAccount);
					obj.setAmount(customerFirstBalance.getAmount());
					obj.setOrganization(users.getOrganization()); 
					obj.setClosed(false);
					obj.setCurrency(organizationSetup.getDefaultCurrency());
					obj.setDebit(arAccount.isDebit());
					obj.setLedgerDate(customerFirstBalance.getFirstBalanceDate());
					obj.setSetup(true);
					GeneralLedgerDAO.getInstance().save(obj, session);
				} else {
					obj.setChartOfAccount(arAccount);
					obj.setAmount(obj.getAmount() + difference);
					obj.setOrganization(users.getOrganization()); 
					obj.setClosed(false);
					obj.setCurrency(organizationSetup.getDefaultCurrency());
					obj.setDebit(arAccount.isDebit());
					obj.setLedgerDate(customerFirstBalance.getFirstBalanceDate());
					obj.setSetup(true);
					GeneralLedgerDAO.getInstance().update(obj, session);
				}
			}
			transaction.commit();
		}catch(Exception ex) {
			try {
				try {
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performCustomerDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Session session = VendorFirstBalanceDAO.getInstance().getSession();
		  Transaction transaction = session.beginTransaction();
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  CustomerFirstBalance customerFirstBalance = CustomerFirstBalanceDAO.getInstance().load(form.getLong("customerFirstBalanceId"));
			ChartOfAccount arAccount = customerFirstBalance.getCustomer().getChartOfAccount();
			if (arAccount==null) arAccount = organizationSetup.getApAccount(); 
		  GeneralLedger obj = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Setup", Boolean.TRUE))
				.add(Restrictions.eq("ChartOfAccount.Id", new Long(arAccount.getId()))).uniqueResult();
		  if (obj!=null) {
		      obj.setAmount(obj.getAmount()-customerFirstBalance.getAmount());
		      GeneralLedgerDAO.getInstance().update(obj, session);
		  }
		  CustomerFirstBalanceDAO.getInstance().delete(customerFirstBalance, session);
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performInventoryForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// relationships
		  List firstItemBalanceLst = ItemFirstBalanceDAO.getInstance().getSession().createCriteria(ItemFirstBalance.class)
				.add(Restrictions.isNull("Location.Id"))
				.add(Restrictions.isNull("Customer.Id"))
		  	.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
		  	.list();
		  request.setAttribute("firstItemBalanceLst", firstItemBalanceLst);
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  request.setAttribute("defaultCurrency", organizationSetup.getDefaultCurrency());
		  List itemLst = ItemDAO.getInstance().findAll(Order.asc("Name"));
		  request.setAttribute("itemLst", itemLst);
		  List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
		  request.setAttribute("itemUnitLst", itemUnitLst);
			if (form.getLong("itemFirstBalanceId") == 0) {
			  if (form.getLong("itemId")>0) {
			      Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
			      /*ItemPrice itemPrice = (ItemPrice)ItemPriceDAO.getInstance().getSession().createCriteria(ItemPrice.class)
			      .add(Restrictions.eq("Id.Item.Id", new Long(form.getLong("itemId"))))
			      .add(Restrictions.eq("Default", Boolean.TRUE)).uniqueResult();
			      if (itemPrice!=null) form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), itemPrice.getPrice()));*/
			      form.setString("price", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), item.getCostPrice()));
			      
			  }
				form.setString("itemFirstBalanceId",0);
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				ItemFirstBalance obj = ItemFirstBalanceDAO.getInstance().get(form.getLong("itemFirstBalanceId"));
				form.setString("itemFirstBalanceId",obj.getId());
				form.setString("itemId",obj.getItem()!=null?obj.getItem().getId():0);
				form.setString("price",Formater.getFormatedOutputForm(obj.getPrice()));
				form.setString("quantity",obj.getQuantity());
				form.setString("itemUnitId",obj.getItemUnit()!=null?obj.getItemUnit().getId():0);
			}
			// get total
			double amountTotal = 0;
			java.util.Iterator iterator = firstItemBalanceLst.iterator();
			while (iterator.hasNext()) {
			  ItemFirstBalance itemFirstBalance = (ItemFirstBalance) iterator.next();
				amountTotal = amountTotal + itemFirstBalance.getQuantityPrice();
			}
			request.setAttribute("amountTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), amountTotal));
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
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
	private ActionForward performInventorySave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = null;
		Transaction transaction = null;
		try {
		  session = ItemFirstBalanceDAO.getInstance().getSession();
		  transaction = session.beginTransaction();
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  // save vendor first balance
		  ItemFirstBalance itemFirstBalance = null;
		  ChartOfAccount inventoryAccount = null;
		  double differenceQuantity = 0;
		  double differencePrice = 0;
		  if (form.getLong("customerFirstBalanceId") == 0) {
		      Calendar calendar = new GregorianCalendar();
		      itemFirstBalance = new ItemFirstBalance();
		      differenceQuantity = form.getDouble("quantity") - itemFirstBalance.getQuantity();
		      differencePrice = form.getDouble("price") - itemFirstBalance.getPrice();
		      itemFirstBalance.setQuantity(form.getDouble("quantity"));
		      itemFirstBalance.setPrice(form.getDouble("price"));
		      itemFirstBalance.setCurrency(organizationSetup.getDefaultCurrency());
		      itemFirstBalance.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(itemFirstBalance.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, calendar));
		      itemFirstBalance.setFirstBalanceDate(calendar.getTime());
		      itemFirstBalance.setOrganization(users.getOrganization());
		      Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
		      inventoryAccount = item.getCostPriceAccount();
		      itemFirstBalance.setItem(item);
		      ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
		      itemFirstBalance.setItemUnit(itemUnit);
		      ItemFirstBalanceDAO.getInstance().save(itemFirstBalance, session);
		  } else {
		      itemFirstBalance = ItemFirstBalanceDAO.getInstance().load(form.getLong("itemFirstBalanceId"));
		      differenceQuantity = form.getDouble("quantity") - itemFirstBalance.getQuantity();
		      differencePrice = form.getDouble("price") - itemFirstBalance.getPrice();
		      itemFirstBalance.setQuantity(form.getDouble("quantity"));
		      itemFirstBalance.setPrice(form.getDouble("price"));
		      itemFirstBalance.setCurrency(organizationSetup.getDefaultCurrency());
		      Calendar calendar = new GregorianCalendar();
		      calendar.setTime(itemFirstBalance.getFirstBalanceDate());
		      itemFirstBalance.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(itemFirstBalance.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, calendar));
		      //itemFirstBalance.setFirstBalanceDate(calendar.getTime());
		      itemFirstBalance.setOrganization(users.getOrganization());
		      Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
		      inventoryAccount = item.getCostPriceAccount();
		      itemFirstBalance.setItem(item);
		      ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
		      itemFirstBalance.setItemUnit(itemUnit);
		      ItemFirstBalanceDAO.getInstance().update(itemFirstBalance, session);
		  }
		  if (inventoryAccount==null)inventoryAccount = organizationSetup.getInventoryAccount();
		  // save GL
			GeneralLedger obj = null;
			if (itemFirstBalance!=null && inventoryAccount!=null) {
				obj = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Setup", Boolean.TRUE))
				.add(Restrictions.eq("ChartOfAccount.Id", new Long(inventoryAccount.getId()))).uniqueResult();
				if (obj==null) {
					obj = new GeneralLedger();
					obj.setChartOfAccount(inventoryAccount);
					obj.setAmount(itemFirstBalance.getQuantityPrice());
					obj.setOrganization(users.getOrganization()); 
					obj.setClosed(false);
					obj.setCurrency(organizationSetup.getDefaultCurrency());
					obj.setDebit(inventoryAccount.isDebit());
					obj.setLedgerDate(itemFirstBalance.getFirstBalanceDate());
					obj.setSetup(true);
					GeneralLedgerDAO.getInstance().save(obj, session);
				} else {
					obj.setChartOfAccount(inventoryAccount);
					obj.setAmount(obj.getAmount() + (differencePrice * differenceQuantity));
					obj.setOrganization(users.getOrganization()); 
					obj.setClosed(false);
					obj.setCurrency(organizationSetup.getDefaultCurrency());
					obj.setDebit(inventoryAccount.isDebit());
					obj.setLedgerDate(itemFirstBalance.getFirstBalanceDate());
					obj.setSetup(true);
					GeneralLedgerDAO.getInstance().update(obj, session);
				}
			}
			// save warehouse
			Warehouse warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.isNull("Location.Id"))
				.add(Restrictions.isNull("Customer.Id"))
				.uniqueResult();
			Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
			if (inventory!=null && warehouse!=null) {
			    InventoryDAO.getInstance().update(inventory, session);
			}
			transaction.commit();
		}catch(Exception ex) {
			try {
				try {
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performInventoryDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			Session session = ItemFirstBalanceDAO.getInstance().getSession();
		  Transaction transaction = session.beginTransaction();
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  ItemFirstBalance itemFirstBalance = ItemFirstBalanceDAO.getInstance().load(form.getLong("itemFirstBalanceId"));
			ChartOfAccount inventoryAccount = itemFirstBalance.getItem().getCostPriceAccount();
			if (inventoryAccount==null) inventoryAccount = organizationSetup.getInventoryAccount(); 
		  GeneralLedger obj = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Setup", Boolean.TRUE))
				.add(Restrictions.eq("ChartOfAccount.Id", new Long(inventoryAccount.getId()))).uniqueResult();
		  if (obj!=null) {
		      obj.setAmount(obj.getAmount()-itemFirstBalance.getQuantityPrice());
		      GeneralLedgerDAO.getInstance().update(obj, session);
		  }
		  Warehouse warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.isNull("Location.Id"))
				.add(Restrictions.isNull("Customer.Id"))
				.uniqueResult();
		  Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
			if (inventory!=null && warehouse!=null) {
			    InventoryDAO.getInstance().update(inventory, session);
			}
		  ItemFirstBalanceDAO.getInstance().delete(itemFirstBalance, session);
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performCustomerInventoryForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// relationships
	    List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Store", Boolean.TRUE))
		  	.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
		  	.list();
		  request.setAttribute("customerLst", customerLst);
		  List firstCustomerItemBalanceLst = ItemFirstBalanceDAO.getInstance().getSession().createCriteria(ItemFirstBalance.class)
				.add(Restrictions.isNull("Location.Id"))
				.add(Restrictions.isNotNull("Customer.Id"))
		  	.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
		  	.list();
		  request.setAttribute("firstCustomerItemBalanceLst", firstCustomerItemBalanceLst);
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  request.setAttribute("defaultCurrency", organizationSetup.getDefaultCurrency());
		  List itemLst = ItemDAO.getInstance().findAll(Order.asc("Name"));
		  request.setAttribute("itemLst", itemLst);
		  List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
		  request.setAttribute("itemUnitLst", itemUnitLst);
			if (form.getLong("customerItemFirstBalanceId") == 0) {
				form.setString("customerItemFirstBalanceId",0);
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				ItemFirstBalance obj = ItemFirstBalanceDAO.getInstance().get(form.getLong("customerItemFirstBalanceId"));
				form.setString("customerItemFirstBalanceId",obj.getId());
				form.setString("itemId",obj.getItem()!=null?obj.getItem().getId():0);
				form.setString("price",Formater.getFormatedOutputForm(obj.getPrice()));
				form.setString("quantity",obj.getQuantity());
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				form.setString("itemUnitId",obj.getItemUnit()!=null?obj.getItemUnit().getId():0);
			}
			// get total
			double amountTotal = 0;
			java.util.Iterator iterator = firstCustomerItemBalanceLst.iterator();
			while (iterator.hasNext()) {
			  ItemFirstBalance itemFirstBalance = (ItemFirstBalance) iterator.next();
				amountTotal = amountTotal + itemFirstBalance.getQuantityPrice();
			}
			request.setAttribute("amountTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), amountTotal));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
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
	private ActionForward performCustomerInventorySave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = null;
		Transaction transaction = null;
		try {
		  session = ItemFirstBalanceDAO.getInstance().getSession();
		  transaction = session.beginTransaction();
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  // save vendor first balance
		  ItemFirstBalance itemFirstBalance = null;
		  ChartOfAccount inventoryAccount = null;
		  double differenceQuantity = 0;
		  double differencePrice = 0;
		  if (form.getLong("customerItemFirstBalanceId") == 0) {
		      Calendar calendar = new GregorianCalendar();
		      itemFirstBalance = new ItemFirstBalance();
		      differenceQuantity = form.getDouble("quantity") - itemFirstBalance.getQuantity();
		      differencePrice = form.getDouble("price") - itemFirstBalance.getPrice();
		      itemFirstBalance.setQuantity(form.getDouble("quantity"));
		      itemFirstBalance.setPrice(form.getDouble("price"));
		      itemFirstBalance.setCurrency(organizationSetup.getDefaultCurrency());
		      itemFirstBalance.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(itemFirstBalance.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, calendar));
		      itemFirstBalance.setFirstBalanceDate(calendar.getTime());
		      itemFirstBalance.setOrganization(users.getOrganization());
		      Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
		      inventoryAccount = item.getCostPriceAccount();
		      itemFirstBalance.setItem(item);
		      Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
		      itemFirstBalance.setCustomer(customers);
		      ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
		      itemFirstBalance.setItemUnit(itemUnit);
		      ItemFirstBalanceDAO.getInstance().save(itemFirstBalance, session);
		  } else {
		      itemFirstBalance = ItemFirstBalanceDAO.getInstance().load(form.getLong("customerItemFirstBalanceId"));
		      differenceQuantity = form.getDouble("quantity") - itemFirstBalance.getQuantity();
		      differencePrice = form.getDouble("price") - itemFirstBalance.getPrice();
		      itemFirstBalance.setQuantity(form.getDouble("quantity"));
		      itemFirstBalance.setPrice(form.getDouble("price"));
		      itemFirstBalance.setCurrency(organizationSetup.getDefaultCurrency());
		      Calendar calendar = new GregorianCalendar();
		      calendar.setTime(itemFirstBalance.getFirstBalanceDate());
		      itemFirstBalance.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(itemFirstBalance.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, calendar));
		      //itemFirstBalance.setFirstBalanceDate(calendar.getTime());
		      itemFirstBalance.setOrganization(users.getOrganization());
		      Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
		      inventoryAccount = item.getCostPriceAccount();
		      itemFirstBalance.setItem(item);
		      Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
		      itemFirstBalance.setCustomer(customers);
		      ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
		      itemFirstBalance.setItemUnit(itemUnit);
		      ItemFirstBalanceDAO.getInstance().update(itemFirstBalance, session);
		  }
		  if (inventoryAccount==null)inventoryAccount = organizationSetup.getInventoryAccount();
		  // save GL
			GeneralLedger obj = null;
			if (itemFirstBalance!=null && inventoryAccount!=null) {
				obj = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Setup", Boolean.TRUE))
					.add(Restrictions.eq("ChartOfAccount.Id", new Long(inventoryAccount.getId()))).uniqueResult();
				if (obj==null) {
					obj = new GeneralLedger();
					obj.setChartOfAccount(inventoryAccount);
					obj.setAmount(itemFirstBalance.getQuantityPrice());
					obj.setOrganization(users.getOrganization()); 
					obj.setClosed(false);
					obj.setCurrency(organizationSetup.getDefaultCurrency());
					obj.setDebit(inventoryAccount.isDebit());
					obj.setLedgerDate(itemFirstBalance.getFirstBalanceDate());
					obj.setSetup(true);
					GeneralLedgerDAO.getInstance().save(obj, session);
				} else {
					obj.setChartOfAccount(inventoryAccount);
					obj.setAmount(obj.getAmount() + (differencePrice * differenceQuantity));
					obj.setOrganization(users.getOrganization()); 
					obj.setClosed(false);
					obj.setCurrency(organizationSetup.getDefaultCurrency());
					obj.setDebit(inventoryAccount.isDebit());
					obj.setLedgerDate(itemFirstBalance.getFirstBalanceDate());
					obj.setSetup(true);
					GeneralLedgerDAO.getInstance().update(obj, session);
				}
			}
			// save warehouse
			Warehouse warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.isNull("Location.Id"))
				.add(Restrictions.eq("Customer.Id", new Long(itemFirstBalance.getCustomer().getId())))
				.uniqueResult();
			Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
			if (inventory!=null && warehouse!=null) {
			    InventoryDAO.getInstance().getSession().merge(inventory);
			}
			transaction.commit();
		}catch(Exception ex) {
			try {
				try {
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performCustomerInventoryDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			Session session = ItemFirstBalanceDAO.getInstance().getSession();
		  Transaction transaction = session.beginTransaction();
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  ItemFirstBalance itemFirstBalance = ItemFirstBalanceDAO.getInstance().load(form.getLong("customerItemFirstBalanceId"));
			ChartOfAccount inventoryAccount = itemFirstBalance.getItem().getCostPriceAccount();
			if (inventoryAccount==null) inventoryAccount = organizationSetup.getInventoryAccount(); 
		  GeneralLedger obj = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Setup", Boolean.TRUE))
				.add(Restrictions.eq("ChartOfAccount.Id", new Long(inventoryAccount.getId()))).uniqueResult();
		  if (obj!=null) {
		      obj.setAmount(obj.getAmount()-itemFirstBalance.getQuantityPrice());
		      GeneralLedgerDAO.getInstance().update(obj, session);
		  }
		  Warehouse warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.isNull("Location.Id"))
				.add(Restrictions.eq("Customer.Id", new Long(itemFirstBalance.getCustomer().getId())))
				.uniqueResult();
		  Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
			if (inventory!=null && warehouse!=null) {
			    InventoryDAO.getInstance().update(inventory, session);
			}
		  ItemFirstBalanceDAO.getInstance().delete(itemFirstBalance, session);
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performLocationInventoryForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// relationships
	    List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
		  	.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
		  	.list();
		  request.setAttribute("locationLst", locationLst);
		  List firstLocationItemBalanceLst = ItemFirstBalanceDAO.getInstance().getSession().createCriteria(ItemFirstBalance.class)
				.add(Restrictions.isNotNull("Location.Id"))
				.add(Restrictions.isNull("Customer.Id"))
		  	.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
		  	.list();
		  request.setAttribute("firstLocationItemBalanceLst", firstLocationItemBalanceLst);
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  request.setAttribute("defaultCurrency", organizationSetup.getDefaultCurrency());
		  List itemLst = ItemDAO.getInstance().findAll(Order.asc("Name"));
		  request.setAttribute("itemLst", itemLst);
		  List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
		  request.setAttribute("itemUnitLst", itemUnitLst);
			if (form.getLong("locationItemFirstBalanceId") == 0) {
				form.setString("locationItemFirstBalanceId",0);
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				ItemFirstBalance obj = ItemFirstBalanceDAO.getInstance().get(form.getLong("locationItemFirstBalanceId"));
				form.setString("locationItemFirstBalanceId",obj.getId());
				form.setString("itemId",obj.getItem()!=null?obj.getItem().getId():0);
				form.setString("price",Formater.getFormatedOutputForm(obj.getPrice()));
				form.setString("quantity",obj.getQuantity());
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				form.setString("itemUnitId",obj.getItemUnit()!=null?obj.getItemUnit().getId():0);
			}
			// get total
			double amountTotal = 0;
			java.util.Iterator iterator = firstLocationItemBalanceLst.iterator();
			while (iterator.hasNext()) {
			  ItemFirstBalance itemFirstBalance = (ItemFirstBalance) iterator.next();
				amountTotal = amountTotal + itemFirstBalance.getQuantityPrice();
			}
			request.setAttribute("amountTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), amountTotal));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
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
	private ActionForward performLocationInventorySave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = null;
		Transaction transaction = null;
		try {
		  session = ItemFirstBalanceDAO.getInstance().getSession();
		  transaction = session.beginTransaction();
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  // save vendor first balance
		  ItemFirstBalance itemFirstBalance = null;
		  ChartOfAccount inventoryAccount = null;
		  double differenceQuantity = 0;
		  double differencePrice = 0;
		  if (form.getLong("locationItemFirstBalanceId") == 0) {
		      Calendar calendar = new GregorianCalendar();
		      itemFirstBalance = new ItemFirstBalance();
		      differenceQuantity = form.getDouble("quantity") - itemFirstBalance.getQuantity();
		      differencePrice = form.getDouble("price") - itemFirstBalance.getPrice();
		      itemFirstBalance.setQuantity(form.getDouble("quantity"));
		      itemFirstBalance.setPrice(form.getDouble("price"));
		      itemFirstBalance.setCurrency(organizationSetup.getDefaultCurrency());
		      itemFirstBalance.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(itemFirstBalance.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, calendar));
		      itemFirstBalance.setFirstBalanceDate(calendar.getTime());
		      itemFirstBalance.setOrganization(users.getOrganization());
		      Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
		      inventoryAccount = item.getCostPriceAccount();
		      itemFirstBalance.setItem(item);
		      Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
		      itemFirstBalance.setLocation(location);
		      ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
		      itemFirstBalance.setItemUnit(itemUnit);
		      ItemFirstBalanceDAO.getInstance().save(itemFirstBalance, session);
		  } else {
		      itemFirstBalance = ItemFirstBalanceDAO.getInstance().load(form.getLong("locationItemFirstBalanceId"));
		      differenceQuantity = form.getDouble("quantity") - itemFirstBalance.getQuantity();
		      differencePrice = form.getDouble("price") - itemFirstBalance.getPrice();
		      itemFirstBalance.setQuantity(form.getDouble("quantity"));
		      itemFirstBalance.setPrice(form.getDouble("price"));
		      itemFirstBalance.setCurrency(organizationSetup.getDefaultCurrency());
		      Calendar calendar = new GregorianCalendar();
		      calendar.setTime(itemFirstBalance.getFirstBalanceDate());
		      itemFirstBalance.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(itemFirstBalance.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, calendar));
		      //itemFirstBalance.setFirstBalanceDate(calendar.getTime());
		      itemFirstBalance.setOrganization(users.getOrganization());
		      Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
		      inventoryAccount = item.getCostPriceAccount();
		      itemFirstBalance.setItem(item);
		      Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
		      itemFirstBalance.setLocation(location);
		      ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
		      itemFirstBalance.setItemUnit(itemUnit);
		      ItemFirstBalanceDAO.getInstance().update(itemFirstBalance, session);
		  }
		  if (inventoryAccount==null)inventoryAccount = organizationSetup.getInventoryAccount();
		  // save GL
			GeneralLedger obj = null;
			if (itemFirstBalance!=null && inventoryAccount!=null) {
				obj = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Setup", Boolean.TRUE))
					.add(Restrictions.eq("ChartOfAccount.Id", new Long(inventoryAccount.getId()))).uniqueResult();
				if (obj==null) {
					obj = new GeneralLedger();
					obj.setChartOfAccount(inventoryAccount);
					obj.setAmount(itemFirstBalance.getQuantityPrice());
					obj.setOrganization(users.getOrganization()); 
					obj.setClosed(false);
					obj.setCurrency(organizationSetup.getDefaultCurrency());
					obj.setDebit(inventoryAccount.isDebit());
					obj.setLedgerDate(itemFirstBalance.getFirstBalanceDate());
					obj.setSetup(true);
					GeneralLedgerDAO.getInstance().save(obj, session);
				} else {
					obj.setChartOfAccount(inventoryAccount);
					obj.setAmount(obj.getAmount() + (differencePrice * differenceQuantity));
					obj.setOrganization(users.getOrganization()); 
					obj.setClosed(false);
					obj.setCurrency(organizationSetup.getDefaultCurrency());
					obj.setDebit(inventoryAccount.isDebit());
					obj.setLedgerDate(itemFirstBalance.getFirstBalanceDate());
					obj.setSetup(true);
					GeneralLedgerDAO.getInstance().update(obj, session);
				}
			}
			// save warehouse
			Warehouse warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.isNull("Customer.Id"))
				.add(Restrictions.eq("Location.Id", new Long(itemFirstBalance.getLocation().getId())))
				.uniqueResult();
			Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
			if (inventory!=null && warehouse!=null) {
			    InventoryDAO.getInstance().getSession().merge(inventory);
			}
			transaction.commit();
		}catch(Exception ex) {
			try {
				try {
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performLocationInventoryDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			Session session = ItemFirstBalanceDAO.getInstance().getSession();
		  Transaction transaction = session.beginTransaction();
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  ItemFirstBalance itemFirstBalance = ItemFirstBalanceDAO.getInstance().load(form.getLong("locationItemFirstBalanceId"));
			ChartOfAccount inventoryAccount = itemFirstBalance.getItem().getCostPriceAccount();
			if (inventoryAccount==null) inventoryAccount = organizationSetup.getInventoryAccount(); 
		  GeneralLedger obj = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Setup", Boolean.TRUE))
				.add(Restrictions.eq("ChartOfAccount.Id", new Long(inventoryAccount.getId()))).uniqueResult();
		  if (obj!=null) {
		      obj.setAmount(obj.getAmount()-itemFirstBalance.getQuantityPrice());
		      GeneralLedgerDAO.getInstance().update(obj, session);
		  }
		  Warehouse warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.isNull("Customer.Id"))
				.add(Restrictions.eq("Location.Id", new Long(itemFirstBalance.getLocation().getId())))
				.uniqueResult();
		  Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
			if (inventory!=null && warehouse!=null) {
			    InventoryDAO.getInstance().update(inventory, session);
			}
		  ItemFirstBalanceDAO.getInstance().delete(itemFirstBalance, session);
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
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