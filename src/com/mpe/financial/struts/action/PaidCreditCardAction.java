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
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpe.financial.model.PaidCreditCard;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.PaidCreditCardDAO;
import com.mpe.financial.struts.form.PaidCreditCardForm;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class PaidCreditCardAction extends Action {
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
		PaidCreditCardForm locationForm = (PaidCreditCardForm) form;
		ActionForward forward = null;
		String action = mapping.getParameter();
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Set lst = (Set)httpSession.getAttribute(CommonConstants.VIEWACCESS);
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
						if (locationForm.getString("subaction")!=null && locationForm.getString("subaction").length()>0) {
							forward = performForm(mapping, form, request, response);
						} else {
							forward = performSave(mapping, form, request, response);
						}
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
		PaidCreditCardForm form = (PaidCreditCardForm) actionForm;
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
			//save start and count attribute on httpSession
			httpSession.setAttribute(CommonConstants.START,Integer.toString(start));
			httpSession.setAttribute(CommonConstants.COUNT,Integer.toString(count));
			List locationLst = LocationDAO.getInstance().findAll(Order.asc("Code"));
			request.setAttribute("locationLst", locationLst);
			Criteria criteria = PaidCreditCardDAO.getInstance().getSession().createCriteria(PaidCreditCard.class);
			if (form.getLong("locationId")>0)criteria.add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId"))));
			if (form.getCalendar("paidDate")!=null)criteria.add(Restrictions.eq("PaidDate", new Date(form.getCalendar("paidDate").getTime().getTime())));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = PaidCreditCardDAO.getInstance().getSession().createCriteria(PaidCreditCard.class);
			if (form.getLong("locationId")>0)criteria.add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId"))));
			if (form.getCalendar("paidDate")!=null)criteria.add(Restrictions.eq("PaidDate", new Date(form.getCalendar("paidDate").getTime().getTime())));
			//criteria.addOrder(Order.asc("ContactPerson"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("PAIDCREDITCARD",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				PaidCreditCardDAO.getInstance().closeSessionForReal();
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
		PaidCreditCardForm form = (PaidCreditCardForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
		  
			if (form.getLong("paidCreditCardId") == 0) {
				form.setString("paidCreditCardId",0);
				form.setCurentTimestamp("createOn");
				//form.setString("isActive", "Y");
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				PaidCreditCard obj = PaidCreditCardDAO.getInstance().get(form.getLong("paidCreditCardId"));
				form.setString("paidCreditCardId",obj.getId());
				form.setCalendar("paidDate",obj.getPaidDate());
				form.setString("adm",obj.getAdm());
				form.setString("amount",obj.getAmount());
				form.setString("creditCardNumber",obj.getCreditCardNumber());
				form.setString("bankId",obj.getBank()!=null?obj.getBank().getId():0);
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				form.setString("organizationId",obj.getOrganization()!=null?obj.getOrganization().getId():0);
				form.setString("isMaster",obj.isMaster()==true?"Y":"N");
			}
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				PaidCreditCardDAO.getInstance().closeSessionForReal();
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
		PaidCreditCardForm form = (PaidCreditCardForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			PaidCreditCard obj = new PaidCreditCard();
			if (form.getLong("paidCreditCardId") == 0) {				
				// none
			} else {
				obj = PaidCreditCardDAO.getInstance().load(form.getLong("paidCreditCardId"));
				obj.setPaidDate(form.getCalendar("paidDate")!=null?form.getCalendar("paidDate").getTime():null);
				obj.setAdm(form.getDouble("adm"));
				obj.setAmount(form.getDouble("amount"));
				PaidCreditCardDAO.getInstance().update(obj);
			}
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				PaidCreditCardDAO.getInstance().closeSessionForReal();
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
		PaidCreditCardForm form = (PaidCreditCardForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			PaidCreditCard paidCreditCard = PaidCreditCardDAO.getInstance().get(form.getLong("paidCreditCardId"));
			request.setAttribute("paidCreditCard", paidCreditCard);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				PaidCreditCardDAO.getInstance().closeSessionForReal();
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
		PaidCreditCardForm form = (PaidCreditCardForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			PaidCreditCardDAO.getInstance().delete(form.getLong("paidCreditCardId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				PaidCreditCardDAO.getInstance().closeSessionForReal();
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