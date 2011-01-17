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

import com.mpe.financial.model.Bank;
import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.CreditCard;
import com.mpe.financial.model.CreditCardDetail;
import com.mpe.financial.model.CreditCardDetailPK;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.BankDAO;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CreditCardDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.struts.form.CreditCardForm;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class CreditCardAction extends Action {
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
		//CreditCardForm currencyForm = (CreditCardForm) form;
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
		CreditCardForm form = (CreditCardForm) actionForm;
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
			Criteria criteria = CreditCardDAO.getInstance().getSession().createCriteria(CreditCard.class);
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = CreditCardDAO.getInstance().getSession().createCriteria(CreditCard.class);
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
			request.setAttribute("CREDITCARD",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				CreditCardDAO.getInstance().closeSessionForReal();
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
		CreditCardForm form = (CreditCardForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// remove
			CreditCard obj = (CreditCard)httpSession.getAttribute("creditCard");
			if (form.getLong("locationId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEDETAIL")) {
				CreditCardDetail removeCreditCardDetail = null;
				Iterator iterator = obj.getCreditCardDetails().iterator();
				while (iterator.hasNext()) {
					CreditCardDetail creditCardDetail = (CreditCardDetail)iterator.next();
					if (form.getLong("locationId") == creditCardDetail.getId().getLocation().getId()) {
						removeCreditCardDetail = creditCardDetail;
					}
				}
				if (removeCreditCardDetail!=null) {
					Set set = obj.getCreditCardDetails();
					set.remove(removeCreditCardDetail);
					obj.setCreditCardDetails(set);
				}
				form.setString("subaction", "");
				form.setString("locationId", "");
				httpSession.setAttribute("creditCard", obj);
			}
			// relationships
			List bankLst = BankDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("bankLst", bankLst);
			List locationLst = LocationDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("locationLst", locationLst);
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			if (form.getLong("creditCardId") == 0) {
				form.setString("creditCardId",0);
				form.setCurentTimestamp("createOn");
				if (obj!=null) {
					form.setString("creditCardId",obj.getId());
					form.setString("name",obj.getName());
					form.setString("discount",obj.getDiscount());
					form.setString("bankId",obj.getBank()!=null?obj.getBank().getId():0);
					form.setString("chartOfAccountId",obj.getChartOfAccount()!=null?obj.getChartOfAccount().getId():0);					
					Set creditCardDetailLst = obj.getCreditCardDetails();
					request.setAttribute("creditCardDetailLst", creditCardDetailLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = CreditCardDAO.getInstance().get(form.getLong("creditCardId"));
					httpSession.setAttribute("creditCard",obj);
				}
				//httpSession.setAttribute("creditCard",obj);
				form.setString("creditCardId",obj.getId());
				form.setString("name",obj.getName());
				form.setString("discount",obj.getDiscount());
				form.setString("bankId",obj.getBank()!=null?obj.getBank().getId():0);
				form.setString("chartOfAccountId",obj.getChartOfAccount()!=null?obj.getChartOfAccount().getId():0);					
				Set creditCardDetailLst = obj.getCreditCardDetails();
				request.setAttribute("creditCardDetailLst", creditCardDetailLst);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
			}
			if (form.getLong("locationId") > 0) {
				Iterator iterator = obj.getCreditCardDetails().iterator();
				while (iterator.hasNext()) {
					CreditCardDetail creditCardDetail = (CreditCardDetail)iterator.next();
					if (form.getLong("locationId") == creditCardDetail.getId().getLocation().getId()) {
						form.setString("isChargeToLocation", creditCardDetail.isChargeToLocation()==true?"Y":"N");
					}
				}
			}
		}catch(Exception ex) {
			try {
				List bankLst = BankDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("bankLst", bankLst);
				List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				List locationLst = LocationDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("locationLst", locationLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				CreditCardDAO.getInstance().closeSessionForReal();
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
		CreditCardForm form = (CreditCardForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("creditCard");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			CreditCard obj = (CreditCard)httpSession.getAttribute("creditCard");
			//if (obj==null) obj = new CreditCard();
			if (form.getLong("creditCardId") == 0) {
				obj = (CreditCard)CreditCardDAO.getInstance().getSession().createCriteria(CreditCard.class).add(Restrictions.eq("Name", form.getString("name"))).uniqueResult();
				if (obj==null) {
					obj = (CreditCard)httpSession.getAttribute("creditCard");
					if (obj==null) obj = new CreditCard();
					obj.setName(form.getString("name"));
					Bank bank = BankDAO.getInstance().get(form.getLong("bankId"));
					obj.setBank(bank);
					ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
					obj.setChartOfAccount(chartOfAccount);
					obj.setDiscount(form.getDouble("discount"));
					obj.setOrganization(users.getOrganization());
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					//CreditCardDAO.getInstance().save(obj);
					//form.setString("creditCardId", obj.getId());
				} else {
					List bankLst = BankDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("bankLst", bankLst);
					List chartOfAccountLst = BankDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("name")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = CreditCardDAO.getInstance().get(form.getLong("creditCardId"));
				obj.setName(form.getString("name"));
				Bank bank = BankDAO.getInstance().get(form.getLong("bankId"));
				obj.setBank(bank);
				ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
				obj.setChartOfAccount(chartOfAccount);
				obj.setDiscount(form.getDouble("discount"));
				obj.setOrganization(users.getOrganization());
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("creditCardId"));
				//CreditCardDAO.getInstance().update(obj);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDDETAIL")) {
				if (form.getLong("locationId") >0) {
				  CreditCardDetail creditCardDetail = new CreditCardDetail();
				  CreditCardDetailPK creditCardDetailPK = new CreditCardDetailPK();
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					creditCardDetailPK.setCreditCard(obj);
					creditCardDetailPK.setLocation(location);
					creditCardDetail.setChargeToLocation(form.getString("isChargeToLocation").length()>0?true:false);
					creditCardDetail.setId(creditCardDetailPK);
					Set set = obj.getCreditCardDetails();
					if (set==null) set = new LinkedHashSet();
					CreditCardDetail removeCreditCardDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						CreditCardDetail creditCardDetail2 = (CreditCardDetail)iterator.next();
						if (form.getLong("locationId")==creditCardDetail2.getId().getLocation().getId()) {
							removeCreditCardDetail = creditCardDetail2;
						}
					}
					if (removeCreditCardDetail!=null) {
						set.remove(removeCreditCardDetail);
						set.add(creditCardDetail);
					} else {
						set.add(creditCardDetail);
					}
					obj.setCreditCardDetails(set);
					// netral
					form.setString("locationId", "");
					form.setString("isChargeToLocation", "");
				}
				// netral
				form.setString("locationId", "");
				form.setString("isChargeToLocation", "");
			}
			// save to session
			httpSession.setAttribute("creditCard", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				CreditCardDAO.getInstance().saveOrUpdate(obj);
				// remove session
				httpSession.removeAttribute("creditCard");
				// finish
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				List bankLst = BankDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("bankLst", bankLst);
				List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				List locationLst = LocationDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("locationLst", locationLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				CreditCardDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?creditCardId="+form.getLong("creditCardId"));
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
		CreditCardForm form = (CreditCardForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			CreditCard creditCard = CreditCardDAO.getInstance().get(form.getLong("creditCardId"));
			request.setAttribute("creditCard", creditCard);
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				CreditCardDAO.getInstance().closeSessionForReal();
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
		CreditCardForm form = (CreditCardForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			CreditCardDAO.getInstance().delete(form.getLong("creditCardId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				CreditCardDAO.getInstance().closeSessionForReal();
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