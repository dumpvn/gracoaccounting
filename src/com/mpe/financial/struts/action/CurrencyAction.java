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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.Currency;
import com.mpe.financial.model.CurrencyExchange;
import com.mpe.financial.model.CurrencyExchangePK;
import com.mpe.financial.model.CurrencyExchangeRateType;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.CurrencyExchangeRateTypeDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.struts.form.CurrencyForm;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class CurrencyAction extends Action {
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
		//CurrencyForm currencyForm = (CurrencyForm) form;
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
		CurrencyForm form = (CurrencyForm) actionForm;
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
			Criteria criteria = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class);
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class);
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
			//criteria.addOrder(Order.desc("Name"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("CURRENCY",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				CurrencyDAO.getInstance().closeSessionForReal();
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
		CurrencyForm form = (CurrencyForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// remove
			Currency obj = (Currency)httpSession.getAttribute("currency");
			if (form.getLong("toCurrencyId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVECURRENCY")) {
				CurrencyExchange removeCurrencyExchange = null;
				Iterator iterator = obj.getCurrencyExchangesByFromCurrency().iterator();
				while (iterator.hasNext()) {
					CurrencyExchange currencyExchange = (CurrencyExchange)iterator.next();
					if (form.getLong("toCurrencyId")==currencyExchange.getId().getToCurrency().getId() && currencyExchange.getId().getCurrencyExchangeRateType().getId()==form.getLong("currencyExchangeRateTypeId") && currencyExchange.getId().getValidFrom().getTime()==form.getCalendar("validFrom").getTime().getTime() && currencyExchange.getId().getValidTo().getTime()==form.getCalendar("validTo").getTime().getTime()) {
							removeCurrencyExchange = currencyExchange;
					}
				}
				if (removeCurrencyExchange!=null) {
					Set set = obj.getCurrencyExchangesByFromCurrency();
					set.remove(removeCurrencyExchange);
					obj.setCurrencyExchangesByFromCurrency(set);
				}
				form.setString("subaction", "");
				form.setString("toCurrencyId", "");
				form.setString("chartOfAccountId", "");
				form.setString("exchangeRate", "");
				form.setString("currencyExchangeRateTypeId", "");
				form.setString("subaction", "");
				form.setString("validFrom", "");
				form.setString("validTo", "");
				form.setString("exchangeRate", "");
				form.setString("isPosted", "");
				httpSession.setAttribute("currency", obj);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			// relationships
			List toCurrencyLst = CurrencyDAO.getInstance().findAll();
			request.setAttribute("toCurrencyLst", toCurrencyLst);
			List currencyExchangeRateTypeLst = CurrencyExchangeRateTypeDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("currencyExchangeRateTypeLst", currencyExchangeRateTypeLst);
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			if (form.getLong("currencyId") == 0) {
				form.setString("currencyId",0);
				form.setCurentTimestamp("createOn");
				if (obj!=null) {
					form.setString("currencyId",obj.getId());
					form.setString("name",obj.getName());
					form.setString("symbol",obj.getSymbol());
					Set currencyExchangeFromLst = obj.getCurrencyExchangesByFromCurrency();
					request.setAttribute("currencyExchangeFromLst", currencyExchangeFromLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					httpSession.setAttribute("currency",obj);
				}
				//httpSession.setAttribute("currency",obj);
				form.setString("currencyId",obj.getId());
				form.setString("name",obj.getName());
				form.setString("symbol",obj.getSymbol());
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set currencyExchangeFromLst = obj.getCurrencyExchangesByFromCurrency();
				request.setAttribute("currencyExchangeFromLst", currencyExchangeFromLst);
			}
			if (form.getLong("toCurrencyId") > 0) {
				//CurrencyExchange currencyExchange = CurrencyExchangeDAO.getInstance().load(form.getLong("currencyExchangeId"));
				//form.setString("exchangeRate", currencyExchange.getExchangeRate());
				//form.setString("toCurrencyId", currencyExchange.getToCurrency().getId());
				//form.setString("currencyExchangeId", currencyExchange.getId());
				Iterator iterator = obj.getCurrencyExchangesByFromCurrency().iterator();
				while (iterator.hasNext()) {
					CurrencyExchange currencyExchange = (CurrencyExchange)iterator.next();
					if (form.getLong("toCurrencyId")==currencyExchange.getId().getToCurrency().getId() && currencyExchange.getId().getCurrencyExchangeRateType().getId()==form.getLong("currencyExchangeRateTypeId") && currencyExchange.getId().getValidFrom().getTime()==form.getCalendar("validFrom").getTime().getTime() && currencyExchange.getId().getValidTo().getTime()==form.getCalendar("validTo").getTime().getTime()) {
						form.setString("exchangeRate", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), currencyExchange.getExchangeRate()));
						form.setString("chartOfAccountId", currencyExchange.getChartOfAccount()!=null?currencyExchange.getChartOfAccount().getId():0);
						form.setString("currencyExchangeRateTypeId", currencyExchange.getId().getCurrencyExchangeRateType()!=null?currencyExchange.getId().getCurrencyExchangeRateType().getId():0);
						form.setCalendar("validFrom", currencyExchange.getId().getValidFrom());
						form.setCalendar("validTo", currencyExchange.getId().getValidTo());
						form.setString("isPosted", currencyExchange.isPosted()==true?"Y":"N");
					}
				}
			}
		}catch(Exception ex) {
			try {
					List toCurrencyLst = CurrencyDAO.getInstance().findAll();
					request.setAttribute("toCurrencyLst", toCurrencyLst);
					List currencyExchangeRateTypeLst = CurrencyExchangeRateTypeDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("currencyExchangeRateTypeLst", currencyExchangeRateTypeLst);
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				CurrencyDAO.getInstance().closeSessionForReal();
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
		CurrencyForm form = (CurrencyForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("currency");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Currency obj = (Currency)httpSession.getAttribute("currency");
			//if (obj==null) obj = new Currency();
			if (form.getLong("currencyId") == 0) {
				obj = (Currency)CurrencyDAO.getInstance().getSession().createCriteria(Currency.class).add(Restrictions.eq("Name", form.getString("name"))).uniqueResult();
				if (obj==null) {
					obj = (Currency)httpSession.getAttribute("currency");
					if (obj==null) obj = new Currency();
					obj.setName(form.getString("name"));
					obj.setSymbol(form.getString("symbol"));
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					//CurrencyDAO.getInstance().save(obj);
					//form.setString("currencyId", obj.getId());
				} else {
					List toCurrencyLst = CurrencyDAO.getInstance().findAll();
					request.setAttribute("toCurrencyLst", toCurrencyLst);
					Set currencyExchangeFromLst = obj!=null?obj.getCurrencyExchangesByFromCurrency():new LinkedHashSet();
					request.setAttribute("currencyExchangeFromLst", currencyExchangeFromLst);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("name")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setName(form.getString("name"));
				obj.setSymbol(form.getString("symbol"));
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("currencyId"));
				//CurrencyDAO.getInstance().update(obj);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDCURRENCY")) {
				if (form.getLong("toCurrencyId") >0 && form.getDouble("exchangeRate")>0) {
				  CurrencyExchange currencyExchange = new CurrencyExchange();
					Currency toCurrency = CurrencyDAO.getInstance().get(form.getLong("toCurrencyId"));
					CurrencyExchangePK currencyExchangePK = new CurrencyExchangePK();
					currencyExchangePK.setFromCurrency(obj);
					currencyExchangePK.setToCurrency(toCurrency);
					currencyExchange.setId(currencyExchangePK);
					currencyExchange.setExchangeRate(Formater.getFormatedOutputResult(organizationSetup.getNumberOfDigit(), form.getDouble("exchangeRate")));
					currencyExchangePK.setValidFrom(form.getCalendar("validFrom")!=null?form.getCalendar("validFrom").getTime():null);
					currencyExchangePK.setValidTo(form.getCalendar("validTo")!=null?form.getCalendar("validTo").getTime():null);
					CurrencyExchangeRateType currencyExchangeRateType = CurrencyExchangeRateTypeDAO.getInstance().get(form.getLong("currencyExchangeRateTypeId"));
					currencyExchangePK.setCurrencyExchangeRateType(currencyExchangeRateType);
					currencyExchange.setPosted(form.getString("isPosted").length()>0?true:false);
					currencyExchange.setNumberOfDigit(organizationSetup.getNumberOfDigit());
					currencyExchange.setOrganization(users.getOrganization());
					ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
					currencyExchange.setChartOfAccount(chartOfAccount);
					Set set = obj.getCurrencyExchangesByFromCurrency();
					//obj.getCurrencyExchangesByFromCurrency().clear();
					if (set==null) set = new LinkedHashSet();
					CurrencyExchange removeCurrencyExchangeFK = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						CurrencyExchange currencyExchange2 = (CurrencyExchange)iterator.next();
						if (form.getLong("toCurrencyId")==currencyExchange2.getId().getToCurrency().getId() && currencyExchange2.getId().getCurrencyExchangeRateType().getId()==form.getLong("currencyExchangeRateTypeId") && currencyExchange2.getId().getValidFrom().getTime()==form.getCalendar("validFrom").getTime().getTime() && currencyExchange2.getId().getValidTo().getTime()==form.getCalendar("validTo").getTime().getTime()) {
						    removeCurrencyExchangeFK = currencyExchange2;
						}
					}
					if (removeCurrencyExchangeFK!=null) {
						set.remove(removeCurrencyExchangeFK);
						set.add(currencyExchange);
					} else {
						set.add(currencyExchange);
					}
					obj.setCurrencyExchangesByFromCurrency(set);
					// netral
					form.setString("toCurrencyId", "");
					form.setString("chartOfAccountId", "");
					form.setString("exchangeRate", "");
					form.setString("currencyExchangeRateTypeId", "");
					form.setString("subaction", "");
					form.setString("validFrom", "");
					form.setString("validTo", "");
					form.setString("exchangeRate", "");
					form.setString("isPosted", "");
				}
				// netral
				form.setString("toCurrencyId", "");
				form.setString("chartOfAccountId", "");
				form.setString("exchangeRate", "");
				form.setString("currencyExchangeRateTypeId", "");
				form.setString("subaction", "");
				form.setString("validFrom", "");
				form.setString("validTo", "");
				form.setString("exchangeRate", "");
				form.setString("isPosted", "");
			}
			// save to session
			httpSession.setAttribute("currency", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					CurrencyDAO.getInstance().save(obj);
				} else {
					// replace old session
					/*
					Set set = new LinkedHashSet();
					Iterator iterator = obj.getCurrencyExchangesByFromCurrency().iterator();
					while (iterator.hasNext()) {
						CurrencyExchangeFK currencyExchangeFK = (CurrencyExchangeFK)iterator.next();
						set.add(currencyExchangeFK);
					}
					obj.getCurrencyExchangesByFromCurrency().clear();
					obj.setCurrencyExchangesByFromCurrency(set);
					*/
					CurrencyDAO.getInstance().update(obj);
				}
				// remove session
				httpSession.removeAttribute("currency");
				// finish
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				try {
						List toCurrencyLst = CurrencyDAO.getInstance().findAll();
						request.setAttribute("toCurrencyLst", toCurrencyLst);
						List currencyExchangeRateTypeLst = CurrencyExchangeRateTypeDAO.getInstance().findAll(Order.asc("Name"));
						request.setAttribute("currencyExchangeRateTypeLst", currencyExchangeRateTypeLst);
						List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
						request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				CurrencyDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?currencyId="+form.getLong("currencyId"));
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
		CurrencyForm form = (CurrencyForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
			request.setAttribute("currency", currency);
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				CurrencyDAO.getInstance().closeSessionForReal();
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
		CurrencyForm form = (CurrencyForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			CurrencyDAO.getInstance().delete(form.getLong("currencyId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				CurrencyDAO.getInstance().closeSessionForReal();
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