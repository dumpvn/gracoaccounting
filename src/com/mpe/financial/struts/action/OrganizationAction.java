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
import org.apache.struts.upload.FormFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.Currency;
import com.mpe.financial.model.Organization;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.RunningNumber;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.OrganizationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.struts.form.OrganizationForm;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class OrganizationAction extends Action {
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
		//DataSource ds = (DataSource) servlet.getServletContext().getAttribute(org.apache.struts.Globals.DATA_SOURCE_KEY);
		//Connection conn = null;
		//VendorForm vendorForm = (VendorForm) form;
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
						forward = performSave(mapping, form, request, response);
					} else if ("DETAIL".equalsIgnoreCase(action)) { 
						forward = performDetail(mapping, form, request, response);
					} else if ("DELETE".equalsIgnoreCase(action)) {
						forward = performDelete(mapping, form, request, response);
					} else if ("ORGANIZATIONSETUPFORM".equalsIgnoreCase(action)) {
							forward = performOrganizationSetupForm(mapping, form, request, response);
					} else if ("ORGANIZATIONSETUPSAVE".equalsIgnoreCase(action)) {
							forward = performOrganizationSetupSave(mapping, form, request, response);
					} else if ("RUNNINGNUMBERFORM".equalsIgnoreCase(action)) {
							forward = performRunningNumberForm(mapping, form, request, response);
					} else if ("RUNNINGNUMBERSAVE".equalsIgnoreCase(action)) {
							forward = performRunningNumberSave(mapping, form, request, response);
					} else if ("SHOWIMAGE".equalsIgnoreCase(action)) { 
						forward = performShowImage(mapping, form, request, response);
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
		OrganizationForm form = (OrganizationForm) actionForm;
		HttpSession httpSession = request.getSession();
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
			Criteria criteria = OrganizationDAO.getInstance().getSession().createCriteria(Organization.class);
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = OrganizationDAO.getInstance().getSession().createCriteria(Organization.class);
			//criteria.addOrder(Order.asc("OrganizationName"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("ORGANIZATION",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				OrganizationDAO.getInstance().closeSessionForReal();
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
		OrganizationForm form = (OrganizationForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// relationships
		  //List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class).addOrder(Order.asc("Name")).list();
		  //request.setAttribute("currencyLst", currencyLst);
			/*
		  List themeLst = ThemeDAO.getInstance().getSession().createCriteria(Theme.class).addOrder(Order.asc("Name")).list();
			request.setAttribute("themeLst", themeLst);
			*/
			if (form.getLong("organizationId") == 0) {
				form.setString("organizationId",0);
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				Organization obj = OrganizationDAO.getInstance().get(form.getLong("organizationId"));
				form.setString("organizationId",obj.getId());
				form.setString("name",obj.getName());
				form.setString("address",obj.getAddress());
				form.setString("city",obj.getCity());
				form.setString("postalCode",obj.getPostalCode());
				form.setString("province",obj.getProvince());
				form.setString("telephone",obj.getTelephone());
				form.setString("fax",obj.getFax());
				form.setString("email",obj.getEmail());
				form.setString("url",obj.getUrl());
				form.setString("npwp",obj.getNpwp());
				form.setCalendar("npwpDate",obj.getNpwpDate());
				form.setString("npwpSn",obj.getNpwpSn());
				form.setString("logoContentType",obj.getLogoContentType());
				//form.setString("themeId", obj.getTheme()!=null?obj.getTheme().getId():0);
				form.setString("approvedPerson",obj.getApprovalPerson());
				form.setString("position",obj.getPosition());
			}
		}catch(Exception ex) {
			try {
				/*
			    List themeLst = ThemeDAO.getInstance().getSession().createCriteria(Theme.class).addOrder(Order.asc("Name")).list();
					request.setAttribute("themeLst", themeLst);
					*/
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				OrganizationDAO.getInstance().closeSessionForReal();
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
		OrganizationForm form = (OrganizationForm) actionForm;
		HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			if (form.getLong("organizationId") == 0) {
			    OrganizationSetup obj = new OrganizationSetup();
					obj.setName(form.getString("name"));
					obj.setApprovalPerson(form.getString("approvedPerson"));
					obj.setAddress(form.getString("address"));
					obj.setCity(form.getString("city"));
					obj.setPostalCode(form.getString("postalCode"));
					obj.setProvince(form.getString("province"));
					obj.setNpwp(form.getString("npwp"));
					obj.setTelephone(form.getString("telephone"));
					obj.setFax(form.getString("fax"));
					obj.setEmail(form.getString("email"));
					obj.setNpwpDate(form.getCalendar("npwpDate")!=null?form.getCalendar("npwpDate").getTime():null);
					obj.setNpwpSn(form.getString("npwpSn"));
					obj.setPosition(form.getString("position"));
					FormFile file = form.getFile("logo");
					if (file!=null && file.getFileSize()>0 && file.getContentType().substring(0,5).equalsIgnoreCase("image")) {
					    obj.setLogo(file.getFileData());
					    obj.setLogoContentType(file.getContentType());
					}
					httpSession.setAttribute("organizationSetup", obj);
					//redirect to organization setup
					ActionForward forward = mapping.findForward("form_redir");
					StringBuffer sb = new StringBuffer(forward.getPath());
					//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
					return new ActionForward(sb.toString(),true);
			} else {
				  Organization obj = OrganizationDAO.getInstance().load(form.getLong("organizationId"));
				  obj.setName(form.getString("name"));
					obj.setApprovalPerson(form.getString("approvedPerson"));
					obj.setAddress(form.getString("address"));
					obj.setCity(form.getString("city"));
					obj.setPostalCode(form.getString("postalCode"));
					obj.setProvince(form.getString("province"));
					obj.setNpwp(form.getString("npwp"));
					obj.setTelephone(form.getString("telephone"));
					obj.setFax(form.getString("fax"));
					obj.setEmail(form.getString("email"));
					obj.setNpwpDate(form.getCalendar("npwpDate")!=null?form.getCalendar("npwpDate").getTime():null);
					obj.setNpwpSn(form.getString("npwpSn"));
					obj.setPosition(form.getString("position"));
					FormFile file = form.getFile("logo");
					if (file!=null && file.getFileSize()>0 && file.getContentType().substring(0,5).equalsIgnoreCase("image")) {
					    obj.setLogo(file.getFileData());
					    obj.setLogoContentType(file.getContentType());
					}
					if (form.getString("removeLogo")!=null && form.getString("removeLogo").equalsIgnoreCase("Y")) {
						obj.setLogo(null);
						obj.setLogoContentType(null);
					}
					OrganizationDAO.getInstance().saveOrUpdate(obj);
					// redirect to list
					ActionForward forward = mapping.findForward("list_redir");
					StringBuffer sb = new StringBuffer(forward.getPath());
					sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
					return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				/*
			    List themeLst = ThemeDAO.getInstance().getSession().createCriteria(Theme.class).addOrder(Order.asc("Name")).list();
					request.setAttribute("themeLst", themeLst);
					*/
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				OrganizationDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
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
		OrganizationForm form = (OrganizationForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			Organization organization = OrganizationDAO.getInstance().get(form.getLong("organizationId"));
			request.setAttribute("organization", organization);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				OrganizationDAO.getInstance().closeSessionForReal();
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
		OrganizationForm form = (OrganizationForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationDAO.getInstance().delete(form.getLong("organizationId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				OrganizationDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
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
	private ActionForward performOrganizationSetupForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		OrganizationForm form = (OrganizationForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// relationships
		  //List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class).addOrder(Order.asc("Name")).list();
		  //request.setAttribute("currencyLst", currencyLst);
		  List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class).addOrder(Order.asc("Name")).list();
			request.setAttribute("currencyLst", currencyLst);
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class).addOrder(Order.asc("Name")).list();
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			if (form.getLong("organizationId") == 0) {
				form.setString("organizationId",0);
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				OrganizationSetup obj = OrganizationSetupDAO.getInstance().get(form.getLong("organizationId"));
				form.setCalendar("setupDate",obj.getSetupDate());
				form.setString("numberOfDigit",obj.getNumberOfDigit());
				form.setString("currencyExchangeType",obj.getCurrencyExchangeType());
				form.setString("postingPeriode",obj.getPostingPeriode());
				form.setString("defaultCurrencyId",obj.getDefaultCurrency()!=null?obj.getDefaultCurrency().getId():0);
				form.setString("arAccountId",obj.getArAccount()!=null?obj.getArAccount().getId():0);
				form.setString("apAccountId",obj.getApAccount()!=null?obj.getApAccount().getId():0);
				form.setString("prePaymentAccountId",obj.getPrepaymentAccount()!=null?obj.getPrepaymentAccount().getId():0);
				form.setString("prePaymentToVendorAccountId",obj.getPrepaymentToVendorAccount()!=null?obj.getPrepaymentToVendorAccount().getId():0);
				form.setString("undepositAccountId",obj.getUndepositAccount()!=null?obj.getUndepositAccount().getId():0);
				form.setString("profitLossAccountId",obj.getProfitLossAccount()!=null?obj.getProfitLossAccount().getId():0);
				form.setString("retainedAccountId",obj.getRetainedAccount()!=null?obj.getRetainedAccount().getId():0);
				form.setString("inventoryAccountId",obj.getInventoryAccount()!=null?obj.getInventoryAccount().getId():0);
				form.setString("salesAccountId",obj.getSalesAccount()!=null?obj.getSalesAccount().getId():0);
				form.setString("cogsAccountId",obj.getCogsAccount()!=null?obj.getCogsAccount().getId():0);
				form.setString("realizedCurrencyLossGainAccountId",obj.getRealizedCurrencyLossGainAccount()!=null?obj.getRealizedCurrencyLossGainAccount().getId():0);
				form.setString("unrealizedCurrencyLossGainAccountId",obj.getUnrealizedCurrencyLossGainAccount()!=null?obj.getUnrealizedCurrencyLossGainAccount().getId():0);
				form.setString("customerReturnAccountId",obj.getCustomerReturAccount()!=null?obj.getCustomerReturAccount().getId():0);
				form.setString("stockOpnameAccountId",obj.getStockOpnameAccount()!=null?obj.getStockOpnameAccount().getId():0);
				form.setString("posTax",obj.getPosTax());
			}
		}catch(Exception ex) {
			try {
				  List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class).addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class).addOrder(Order.asc("Name")).list();
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				OrganizationDAO.getInstance().closeSessionForReal();
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
	private ActionForward performOrganizationSetupSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		OrganizationForm form = (OrganizationForm) actionForm;
		HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
		    	httpSession.removeAttribute("organizationSetup");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup obj = null;
			if (form.getLong("organizationId") == 0) {
			    // load from session
			    obj = (OrganizationSetup)httpSession.getAttribute("organizationSetup");
			    obj.setSetupDate(form.getCalendar("setupDate")!=null?form.getCalendar("setupDate").getTime():null);
				obj.setNumberOfDigit(form.getInt("numberOfDigit"));
				obj.setCurrencyExchangeType(form.getString("currencyExchangeType"));
				obj.setPostingPeriode(form.getString("postingPeriode"));
				obj.setPosTax(form.getDouble("posTax"));
				Currency defaultCurrency = CurrencyDAO.getInstance().get(form.getLong("defaultCurrencyId"));
				obj.setDefaultCurrency(defaultCurrency);
				ChartOfAccount arAccount = ChartOfAccountDAO.getInstance().get(form.getLong("arAccountId"));
				obj.setArAccount(arAccount);
				ChartOfAccount apAccount = ChartOfAccountDAO.getInstance().get(form.getLong("apAccountId"));
				obj.setApAccount(apAccount);
				ChartOfAccount prepaymentAccount = ChartOfAccountDAO.getInstance().get(form.getLong("prePaymentAccountId"));
				obj.setPrepaymentAccount(prepaymentAccount);
				ChartOfAccount prepaymentToVendorAccount = ChartOfAccountDAO.getInstance().get(form.getLong("prePaymentToVendorAccountId"));
				obj.setPrepaymentToVendorAccount(prepaymentToVendorAccount);
				ChartOfAccount undepositAccount = ChartOfAccountDAO.getInstance().get(form.getLong("undepositAccountId"));
				obj.setUndepositAccount(undepositAccount);
				ChartOfAccount profitLossAccount = ChartOfAccountDAO.getInstance().get(form.getLong("profitLossAccountId"));
				obj.setProfitLossAccount(profitLossAccount);
				ChartOfAccount retainedAccount = ChartOfAccountDAO.getInstance().get(form.getLong("retainedAccountId"));
				obj.setRetainedAccount(retainedAccount);
				ChartOfAccount inventoryAccount = ChartOfAccountDAO.getInstance().get(form.getLong("inventoryAccountId"));
				obj.setInventoryAccount(inventoryAccount);
				ChartOfAccount salesAccount = ChartOfAccountDAO.getInstance().get(form.getLong("salesAccountId"));
				obj.setSalesAccount(salesAccount);
				ChartOfAccount cogsAccount = ChartOfAccountDAO.getInstance().get(form.getLong("cogsAccountId"));
				obj.setCogsAccount(cogsAccount);
				ChartOfAccount realizedCurrencyLossGainAccount = ChartOfAccountDAO.getInstance().get(form.getLong("realizedCurrencyLossGainAccountId"));
				obj.setRealizedCurrencyLossGainAccount(realizedCurrencyLossGainAccount);
				ChartOfAccount unrealizedCurrencyLossGainAccount = ChartOfAccountDAO.getInstance().get(form.getLong("unrealizedCurrencyLossGainAccountId"));
				obj.setUnrealizedCurrencyLossGainAccount(unrealizedCurrencyLossGainAccount);
				ChartOfAccount customerReturnAccount = ChartOfAccountDAO.getInstance().get(form.getLong("customerReturnAccountId"));
				obj.setCustomerReturAccount(customerReturnAccount);
				ChartOfAccount stockOpnameAccount = ChartOfAccountDAO.getInstance().get(form.getLong("stockOpnameAccountId"));
				obj.setStockOpnameAccount(stockOpnameAccount);
				//OrganizationSetupDAO.getInstance().save(obj);
				httpSession.setAttribute("organizationSetup", obj);
				// redirect to running number
				ActionForward forward = mapping.findForward("form_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			} else {
				obj = OrganizationSetupDAO.getInstance().load(form.getLong("organizationId"));
				obj.setSetupDate(form.getCalendar("setupDate")!=null?form.getCalendar("setupDate").getTime():null);
				obj.setNumberOfDigit(form.getInt("numberOfDigit"));
				obj.setCurrencyExchangeType(form.getString("currencyExchangeType"));
				obj.setPostingPeriode(form.getString("postingPeriode"));
				obj.setPosTax(form.getDouble("posTax"));
				Currency defaultCurrency = CurrencyDAO.getInstance().get(form.getLong("defaultCurrencyId"));
				obj.setDefaultCurrency(defaultCurrency);
				ChartOfAccount arAccount = ChartOfAccountDAO.getInstance().get(form.getLong("arAccountId"));
				obj.setArAccount(arAccount);
				ChartOfAccount apAccount = ChartOfAccountDAO.getInstance().get(form.getLong("apAccountId"));
				obj.setApAccount(apAccount);
				ChartOfAccount prepaymentAccount = ChartOfAccountDAO.getInstance().get(form.getLong("prePaymentAccountId"));
				obj.setPrepaymentAccount(prepaymentAccount);
				ChartOfAccount prepaymentToVendorAccount = ChartOfAccountDAO.getInstance().get(form.getLong("prePaymentToVendorAccountId"));
				obj.setPrepaymentToVendorAccount(prepaymentToVendorAccount);
				ChartOfAccount undepositAccount = ChartOfAccountDAO.getInstance().get(form.getLong("undepositAccountId"));
				obj.setUndepositAccount(undepositAccount);
				ChartOfAccount profitLossAccount = ChartOfAccountDAO.getInstance().get(form.getLong("profitLossAccountId"));
				obj.setProfitLossAccount(profitLossAccount);
				ChartOfAccount retainedAccount = ChartOfAccountDAO.getInstance().get(form.getLong("retainedAccountId"));
				obj.setRetainedAccount(retainedAccount);
				ChartOfAccount inventoryAccount = ChartOfAccountDAO.getInstance().get(form.getLong("inventoryAccountId"));
				obj.setInventoryAccount(inventoryAccount);
				ChartOfAccount salesAccount = ChartOfAccountDAO.getInstance().get(form.getLong("salesAccountId"));
				obj.setSalesAccount(salesAccount);
				ChartOfAccount cogsAccount = ChartOfAccountDAO.getInstance().get(form.getLong("cogsAccountId"));
				obj.setCogsAccount(cogsAccount);
				ChartOfAccount realizedCurrencyLossGainAccount = ChartOfAccountDAO.getInstance().get(form.getLong("realizedCurrencyLossGainAccountId"));
				obj.setRealizedCurrencyLossGainAccount(realizedCurrencyLossGainAccount);
				ChartOfAccount unrealizedCurrencyLossGainAccount = ChartOfAccountDAO.getInstance().get(form.getLong("unrealizedCurrencyLossGainAccountId"));
				obj.setUnrealizedCurrencyLossGainAccount(unrealizedCurrencyLossGainAccount);
				ChartOfAccount customerReturnAccount = ChartOfAccountDAO.getInstance().get(form.getLong("customerReturnAccountId"));
				obj.setCustomerReturAccount(customerReturnAccount);
				ChartOfAccount stockOpnameAccount = ChartOfAccountDAO.getInstance().get(form.getLong("stockOpnameAccountId"));
				obj.setStockOpnameAccount(stockOpnameAccount);
				OrganizationDAO.getInstance().saveOrUpdate(obj);
				// redirect to list
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				  List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class).addOrder(Order.asc("Name")).list();
				  request.setAttribute("currencyLst", currencyLst);
				  List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class).addOrder(Order.asc("Name")).list();
				  request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				OrganizationDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
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
	private ActionForward performRunningNumberForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		OrganizationForm form = (OrganizationForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// relationships
			if (form.getLong("organizationId") == 0) {
				form.setString("organizationId",0);
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				RunningNumber runningNumber = RunningNumberDAO.getInstance().get(form.getLong("organizationId"));
				form.setString("runningNumberId",runningNumber.getId());
				form.setString("journalPrefix",runningNumber.getJournalPrefix());
				form.setString("journalSuffix",runningNumber.getJournalSuffix());
				form.setString("journalNumber",runningNumber.getJournalNumber());
				form.setString("itemPrefix",runningNumber.getItemPrefix());
				form.setString("itemSuffix",runningNumber.getItemSuffix());
				form.setString("itemNumber",runningNumber.getItemNumber());
				form.setString("warehousePrefix",runningNumber.getWarehousePrefix());
				form.setString("warehouseSuffix",runningNumber.getWarehouseSuffix());
				form.setString("warehouseNumber",runningNumber.getWarehouseNumber());
				form.setString("mutationPrefix",runningNumber.getMutationPrefix());
				form.setString("mutationSuffix",runningNumber.getMutationSuffix());
				form.setString("mutationNumber",runningNumber.getMutationNumber());
				form.setString("stockOpnamePrefix",runningNumber.getStockOpnamePrefix());
				form.setString("stockOpnameSuffix",runningNumber.getStockOpnameSuffix());
				form.setString("stockOpnameNumber",runningNumber.getStockOpnameNumber());
				form.setString("itemUsagePrefix",runningNumber.getItemUsagePrefix());
				form.setString("itemUsageSuffix",runningNumber.getItemUsageSuffix());
				form.setString("itemUsageNumber",runningNumber.getItemUsageNumber());
				form.setString("lendingPrefix",runningNumber.getLendingPrefix());
				form.setString("lendingSuffix",runningNumber.getLendingSuffix());
				form.setString("lendingNumber",runningNumber.getLendingNumber());
				form.setString("purchaseRequestPrefix",runningNumber.getPurchaseRequestPrefix());
				form.setString("purchaseRequestSuffix",runningNumber.getPurchaseRequestSuffix());
				form.setString("purchaseRequestNumber",runningNumber.getPurchaseRequestNumber());
				form.setString("purchaseOrderPrefix",runningNumber.getPurchaseOrderPrefix());
				form.setString("purchaseOrderSuffix",runningNumber.getPurchaseOrderSuffix());
				form.setString("purchaseOrderNumber",runningNumber.getPurchaseOrderNumber());
				form.setString("receivingPrefix",runningNumber.getReceivingPrefix());
				form.setString("receivingSuffix",runningNumber.getReceivingSuffix());
				form.setString("receivingNumber",runningNumber.getReceivingNumber());
				form.setString("vendorBillPrefix",runningNumber.getVendorBillPrefix());
				form.setString("vendorBillSuffix",runningNumber.getVendorBillSuffix());
				form.setString("vendorBillNumber",runningNumber.getVendorBillNumber());
				form.setString("paymentToVendorPrefix",runningNumber.getPaymentToVendorPrefix());
				form.setString("paymentToVendorSuffix",runningNumber.getPaymentToVendorSuffix());
				form.setString("paymentToVendorNumber",runningNumber.getPaymentToVendorNumber());
				form.setString("prepaymentToVendorPrefix",runningNumber.getPrepaymentToVendorPrefix());
				form.setString("prepaymentToVendorSuffix",runningNumber.getPrepaymentToVendorSuffix());
				form.setString("prepaymentToVendorNumber",runningNumber.getPrepaymentToVendorNumber());
				form.setString("returnToVendorPrefix",runningNumber.getReturToVendorPrefix());
				form.setString("returnToVendorSuffix",runningNumber.getReturToVendorSuffix());
				form.setString("returnToVendorNumber",runningNumber.getReturToVendorNumber());
				form.setString("salesOrderPrefix",runningNumber.getSalesOrderPrefix());
				form.setString("salesOrderSuffix",runningNumber.getSalesOrderSuffix());
				form.setString("salesOrderNumber",runningNumber.getSalesOrderNumber());
				form.setString("deliveryOrderPrefix",runningNumber.getDeliveryOrderPrefix());
				form.setString("deliveryOrderSuffix",runningNumber.getDeliveryOrderSuffix());
				form.setString("deliveryOrderNumber",runningNumber.getDeliveryOrderNumber());
				form.setString("invoicePrefix",runningNumber.getInvoicePrefix());
				form.setString("invoiceSuffix",runningNumber.getInvoiceSuffix());
				form.setString("invoiceNumber",runningNumber.getInvoiceNumber());
				form.setString("customerPaymentPrefix",runningNumber.getCustomerPaymentPrefix());
				form.setString("customerPaymentSuffix",runningNumber.getCustomerPaymentSuffix());
				form.setString("customerPaymentNumber",runningNumber.getCustomerPaymentNumber());
				form.setString("customerPrepaymentPrefix",runningNumber.getCustomerPrepaymentPrefix());
				form.setString("customerPrepaymentSuffix",runningNumber.getCustomerPrepaymentSuffix());
				form.setString("customerPrepaymentNumber",runningNumber.getCustomerPrepaymentNumber());
				form.setString("customerReturnPrefix",runningNumber.getCustomerReturPrefix());
				form.setString("customerReturnSuffix",runningNumber.getCustomerReturSuffix());
				form.setString("customerReturnNumber",runningNumber.getCustomerReturNumber());
				form.setString("standartNpwpTaxNumber",runningNumber.getStandartNpwpTaxNumber());
				form.setString("simpleNpwpTaxNumber",runningNumber.getSimpleNpwpTaxNumber());
				form.setString("posOrderNumber",runningNumber.getPosOrderNumber());
				form.setString("memberNumber",runningNumber.getMemberNumber());
				form.setString("bankTransactionPrefix",runningNumber.getBankTransactionPrefix());
				form.setString("bankTransactionSuffix",runningNumber.getBankTransactionSuffix());
				form.setString("bankTransactionNumber",runningNumber.getBankTransactionNumber());
			}
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				OrganizationDAO.getInstance().closeSessionForReal();
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
	private ActionForward performRunningNumberSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		OrganizationForm form = (OrganizationForm) actionForm;
		HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			RunningNumber runningNumber = new RunningNumber();
			if (form.getLong("organizationId") == 0) {
			    runningNumber.setJournalPrefix(form.getString("journalPrefix"));
			    runningNumber.setJournalSuffix(form.getString("journalSuffix"));
			    runningNumber.setJournalNumber(form.getString("journalNumber"));
			    runningNumber.setItemPrefix(form.getString("itemPrefix"));
			    runningNumber.setItemSuffix(form.getString("itemSuffix"));
			    runningNumber.setItemNumber(form.getString("itemNumber"));
			    runningNumber.setWarehousePrefix(form.getString("warehousePrefix"));
			    runningNumber.setWarehouseSuffix(form.getString("warehouseSuffix"));
			    runningNumber.setWarehouseNumber(form.getString("warehouseNumber"));
			    runningNumber.setMutationPrefix(form.getString("mutationPrefix"));
			    runningNumber.setMutationSuffix(form.getString("mutationSuffix"));
			    runningNumber.setMutationNumber(form.getString("mutationNumber"));
			    runningNumber.setStockOpnamePrefix(form.getString("stockOpnamePrefix"));
			    runningNumber.setStockOpnameSuffix(form.getString("stockOpnameSuffix"));
			    runningNumber.setStockOpnameNumber(form.getString("stockOpnameNumber"));
			    runningNumber.setItemUsagePrefix(form.getString("itemUsagePrefix"));
			    runningNumber.setItemUsageSuffix(form.getString("itemUsageSuffix"));
			    runningNumber.setItemUsageNumber(form.getString("itemUsageNumber"));
			    runningNumber.setLendingPrefix(form.getString("lendingPrefix"));
			    runningNumber.setLendingSuffix(form.getString("lendingSuffix"));
			    runningNumber.setLendingNumber(form.getString("lendingNumber"));
			    runningNumber.setPurchaseRequestPrefix(form.getString("purchaseRequestPrefix"));
			    runningNumber.setPurchaseRequestSuffix(form.getString("purchaseRequestSuffix"));
			    runningNumber.setPurchaseRequestNumber(form.getString("purchaseRequestNumber"));
			    runningNumber.setPurchaseOrderPrefix(form.getString("purchaseOrderPrefix"));
			    runningNumber.setPurchaseOrderSuffix(form.getString("purchaseOrderSuffix"));
			    runningNumber.setPurchaseOrderNumber(form.getString("purchaseOrderNumber"));
			    runningNumber.setReceivingPrefix(form.getString("receivingPrefix"));
			    runningNumber.setReceivingSuffix(form.getString("receivingSuffix"));
			    runningNumber.setReceivingNumber(form.getString("receivingNumber"));
			    runningNumber.setVendorBillPrefix(form.getString("vendorBillPrefix"));
			    runningNumber.setVendorBillSuffix(form.getString("vendorBillSuffix"));
			    runningNumber.setVendorBillNumber(form.getString("vendorBillNumber"));
			    runningNumber.setPaymentToVendorPrefix(form.getString("paymentToVendorPrefix"));
			    runningNumber.setPaymentToVendorSuffix(form.getString("paymentToVendorSuffix"));
			    runningNumber.setPaymentToVendorNumber(form.getString("paymentToVendorNumber"));
			    runningNumber.setPrepaymentToVendorPrefix(form.getString("prepaymentToVendorPrefix"));
			    runningNumber.setPrepaymentToVendorSuffix(form.getString("prepaymentToVendorSuffix"));
			    runningNumber.setPrepaymentToVendorNumber(form.getString("prepaymentToVendorNumber"));
			    runningNumber.setReturToVendorPrefix(form.getString("returnToVendorPrefix"));
			    runningNumber.setReturToVendorSuffix(form.getString("returnToVendorSuffix"));
			    runningNumber.setReturToVendorNumber(form.getString("returnToVendorNumber"));
			    runningNumber.setSalesOrderPrefix(form.getString("salesOrderPrefix"));
			    runningNumber.setSalesOrderSuffix(form.getString("salesOrderSuffix"));
			    runningNumber.setSalesOrderNumber(form.getString("salesOrderNumber"));
			    runningNumber.setDeliveryOrderPrefix(form.getString("deliveryOrderPrefix"));
			    runningNumber.setDeliveryOrderSuffix(form.getString("deliveryOrderSuffix"));
			    runningNumber.setDeliveryOrderNumber(form.getString("deliveryOrderNumber"));
			    runningNumber.setInvoicePrefix(form.getString("invoicePrefix"));
			    runningNumber.setInvoiceSuffix(form.getString("invoiceSuffix"));
			    runningNumber.setInvoiceNumber(form.getString("invoiceNumber"));
			    runningNumber.setCustomerPaymentPrefix(form.getString("customerPaymentPrefix"));
			    runningNumber.setCustomerPaymentSuffix(form.getString("customerPaymentSuffix"));
			    runningNumber.setCustomerPaymentNumber(form.getString("customerPaymentNumber"));
			    runningNumber.setCustomerPrepaymentPrefix(form.getString("customerPrepaymentPrefix"));
			    runningNumber.setCustomerPrepaymentSuffix(form.getString("customerPrepaymentSuffix"));
			    runningNumber.setCustomerPrepaymentNumber(form.getString("customerPrepaymentNumber"));
			    runningNumber.setCustomerReturPrefix(form.getString("customerReturnPrefix"));
			    runningNumber.setCustomerReturSuffix(form.getString("customerReturnSuffix"));
			    runningNumber.setCustomerReturNumber(form.getString("customerReturnNumber"));
			    runningNumber.setStandartNpwpTaxNumber(form.getString("standartNpwpTaxNumber"));
			    runningNumber.setSimpleNpwpTaxNumber(form.getString("simpleNpwpTaxNumber"));
			    runningNumber.setPosOrderNumber(form.getString("posOrderNumber"));
			    runningNumber.setMemberNumber(form.getString("memberNumber"));
			    runningNumber.setBankTransactionPrefix(form.getString("bankTransactionPrefix"));
			    runningNumber.setBankTransactionSuffix(form.getString("bankTransactionSuffix"));
			    runningNumber.setBankTransactionNumber(form.getString("bankTransactionNumber"));
			    Session session = OrganizationSetupDAO.getInstance().getSession();
			    Transaction transaction = session.beginTransaction();
			    try {
					    // save organizationSetup
					    OrganizationSetup organizationSetup = (OrganizationSetup)httpSession.getAttribute("organizationSetup");
					    //organizationSetup.setRunningNumber(runningNumber);
					    OrganizationSetupDAO.getInstance().save(organizationSetup, session);
					    // save running number
					    runningNumber.setOrganizationSetup(organizationSetup);
							RunningNumberDAO.getInstance().save(runningNumber, session);
							transaction.commit();
							// remove session
							httpSession.removeAttribute("organizationSetup");
			    }catch(Exception ex){
			        log.info("[ Err : "+ex+" ]");
			        transaction.rollback();
			    }
			} else {
			    runningNumber = RunningNumberDAO.getInstance().load(form.getLong("organizationId"));
				  runningNumber.setJournalPrefix(form.getString("journalPrefix"));
			    runningNumber.setJournalSuffix(form.getString("journalSuffix"));
			    runningNumber.setJournalNumber(form.getString("journalNumber"));
			    runningNumber.setItemPrefix(form.getString("itemPrefix"));
			    runningNumber.setItemSuffix(form.getString("itemSuffix"));
			    runningNumber.setItemNumber(form.getString("itemNumber"));
			    runningNumber.setWarehousePrefix(form.getString("warehousePrefix"));
			    runningNumber.setWarehouseSuffix(form.getString("warehouseSuffix"));
			    runningNumber.setWarehouseNumber(form.getString("warehouseNumber"));
			    runningNumber.setMutationPrefix(form.getString("mutationPrefix"));
			    runningNumber.setMutationSuffix(form.getString("mutationSuffix"));
			    runningNumber.setMutationNumber(form.getString("mutationNumber"));
			    runningNumber.setStockOpnamePrefix(form.getString("stockOpnamePrefix"));
			    runningNumber.setStockOpnameSuffix(form.getString("stockOpnameSuffix"));
			    runningNumber.setStockOpnameNumber(form.getString("stockOpnameNumber"));
			    runningNumber.setItemUsagePrefix(form.getString("itemUsagePrefix"));
			    runningNumber.setItemUsageSuffix(form.getString("itemUsageSuffix"));
			    runningNumber.setItemUsageNumber(form.getString("itemUsageNumber"));
			    runningNumber.setLendingPrefix(form.getString("lendingPrefix"));
			    runningNumber.setLendingSuffix(form.getString("lendingSuffix"));
			    runningNumber.setLendingNumber(form.getString("lendingNumber"));
			    runningNumber.setPurchaseRequestPrefix(form.getString("purchaseRequestPrefix"));
			    runningNumber.setPurchaseRequestSuffix(form.getString("purchaseRequestSuffix"));
			    runningNumber.setPurchaseRequestNumber(form.getString("purchaseRequestNumber"));
			    runningNumber.setPurchaseOrderPrefix(form.getString("purchaseOrderPrefix"));
			    runningNumber.setPurchaseOrderSuffix(form.getString("purchaseOrderSuffix"));
			    runningNumber.setPurchaseOrderNumber(form.getString("purchaseOrderNumber"));
			    runningNumber.setReceivingPrefix(form.getString("receivingPrefix"));
			    runningNumber.setReceivingSuffix(form.getString("receivingSuffix"));
			    runningNumber.setReceivingNumber(form.getString("receivingNumber"));
			    runningNumber.setVendorBillPrefix(form.getString("vendorBillPrefix"));
			    runningNumber.setVendorBillSuffix(form.getString("vendorBillSuffix"));
			    runningNumber.setVendorBillNumber(form.getString("vendorBillNumber"));
			    runningNumber.setPaymentToVendorPrefix(form.getString("paymentToVendorPrefix"));
			    runningNumber.setPaymentToVendorSuffix(form.getString("paymentToVendorSuffix"));
			    runningNumber.setPaymentToVendorNumber(form.getString("paymentToVendorNumber"));
			    runningNumber.setPrepaymentToVendorPrefix(form.getString("prepaymentToVendorPrefix"));
			    runningNumber.setPrepaymentToVendorSuffix(form.getString("prepaymentToVendorSuffix"));
			    runningNumber.setPrepaymentToVendorNumber(form.getString("prepaymentToVendorNumber"));
			    runningNumber.setReturToVendorPrefix(form.getString("returnToVendorPrefix"));
			    runningNumber.setReturToVendorSuffix(form.getString("returnToVendorSuffix"));
			    runningNumber.setReturToVendorNumber(form.getString("returnToVendorNumber"));
			    runningNumber.setSalesOrderPrefix(form.getString("salesOrderPrefix"));
			    runningNumber.setSalesOrderSuffix(form.getString("salesOrderSuffix"));
			    runningNumber.setSalesOrderNumber(form.getString("salesOrderNumber"));
			    runningNumber.setDeliveryOrderPrefix(form.getString("deliveryOrderPrefix"));
			    runningNumber.setDeliveryOrderSuffix(form.getString("deliveryOrderSuffix"));
			    runningNumber.setDeliveryOrderNumber(form.getString("deliveryOrderNumber"));
			    runningNumber.setInvoicePrefix(form.getString("invoicePrefix"));
			    runningNumber.setInvoiceSuffix(form.getString("invoiceSuffix"));
			    runningNumber.setInvoiceNumber(form.getString("invoiceNumber"));
			    runningNumber.setCustomerPaymentPrefix(form.getString("customerPaymentPrefix"));
			    runningNumber.setCustomerPaymentSuffix(form.getString("customerPaymentSuffix"));
			    runningNumber.setCustomerPaymentNumber(form.getString("customerPaymentNumber"));
			    runningNumber.setCustomerPrepaymentPrefix(form.getString("customerPrepaymentPrefix"));
			    runningNumber.setCustomerPrepaymentSuffix(form.getString("customerPrepaymentSuffix"));
			    runningNumber.setCustomerPrepaymentNumber(form.getString("customerPrepaymentNumber"));
			    runningNumber.setCustomerReturPrefix(form.getString("customerReturnPrefix"));
			    runningNumber.setCustomerReturSuffix(form.getString("customerReturnSuffix"));
			    runningNumber.setCustomerReturNumber(form.getString("customerReturnNumber"));
			    runningNumber.setStandartNpwpTaxNumber(form.getString("standartNpwpTaxNumber"));
			    runningNumber.setSimpleNpwpTaxNumber(form.getString("simpleNpwpTaxNumber"));
			    runningNumber.setPosOrderNumber(form.getString("posOrderNumber"));
			    runningNumber.setMemberNumber(form.getString("memberNumber"));
			    runningNumber.setBankTransactionPrefix(form.getString("bankTransactionPrefix"));
			    runningNumber.setBankTransactionSuffix(form.getString("bankTransactionSuffix"));
			    runningNumber.setBankTransactionNumber(form.getString("bankTransactionNumber"));
			    RunningNumberDAO.getInstance().saveOrUpdate(runningNumber);
			}
		}catch(Exception ex) {
			try {
				/*
			    List themeLst = ThemeDAO.getInstance().getSession().createCriteria(Theme.class).addOrder(Order.asc("Name")).list();
					request.setAttribute("themeLst", themeLst);
					*/
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				OrganizationDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
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
	private ActionForward performShowImage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//OrganizationForm form = (OrganizationForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
		  long id = Long.parseLong(request.getParameter("id"));
			Organization organization = OrganizationDAO.getInstance().get(id);
			request.setAttribute("contentType",organization.getLogoContentType());
			StringBuffer out = new StringBuffer();
			out.append(new String(organization.getLogo(), 0, organization.getLogo().length, "ISO-8859-1"));
			request.setAttribute("image", out.toString());
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				OrganizationDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("detail");
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