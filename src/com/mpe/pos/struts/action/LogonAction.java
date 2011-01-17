// Created by Xslt generator for Eclipse.
// XSL :  not found (java.io.FileNotFoundException:  (The system cannot find the path specified))
// Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package com.mpe.pos.struts.action;


import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.mpe.common.CommonConstants;
import com.mpe.common.CommonUtil;
import com.mpe.pos.struts.form.LogonForm;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.Role;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.View;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao._RootDAO;

/** 
 * LogonAction.java created by EasyStruts - XsltGen.
 * http://easystruts.sf.net
 * created on 06-11-2003
 * 
 * XDoclet definition:
 * @struts:action path="/logon" name="logonForm" input="/user/logon.jsp" parameter="LOGON" validate="true"
 */
public class LogonAction extends Action {
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
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		String action = mapping.getParameter();
		if ("".equalsIgnoreCase(action)) {
			return mapping.findForward("index");
		} else if ("LOGON".equalsIgnoreCase(action)) {
			return performLogon(mapping, form, request, response);
		} else if ("LOGOFF".equalsIgnoreCase(action)) {
			return performLogoff(mapping, form, request, response);
		} else if ("LOGONFORM".equalsIgnoreCase(action)) {
			return performLogonForm(mapping, form, request, response);
		} else {
			return mapping.findForward("index");	
		}
	}
	
	/** 
	 * Method performLogon
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performLogon(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		LogonForm form = (LogonForm) actionForm;
		HttpSession httpSession = request.getSession();  
		try {
			// guest
			if (form.getString("userName")==null || form.getString("userName").length()==0) {
				Users users = (Users) UsersDAO.getInstance().getSession().createCriteria(Users.class).add(Expression.eq("UserName", "guest")).uniqueResult();
				if (users!=null) {
					httpSession.setAttribute(CommonConstants.USER, users);
					java.util.Iterator iterator = users.getRoles().iterator();
					List list = new java.util.LinkedList();
					while (iterator.hasNext()) {
						Role role = (Role)iterator.next();
						list = (role.getViews());
					}
					httpSession.setAttribute(CommonConstants.VIEWACCESS,list);
				}
			} else {
				Users users = (Users) UsersDAO.getInstance().getSession().createCriteria(Users.class)
				.add(Expression.eq("UserName", form.getString("userName")))
				.uniqueResult();
				if (users!=null) {
					if (users.isActive()) {
						if (users.getUserPass().equals(CommonUtil.digest(form.getString("userPass")))) {
							httpSession.setAttribute(CommonConstants.USER, users);
							java.util.Iterator iterator = users.getRoles().iterator();
							Set set = new LinkedHashSet();
							while (iterator.hasNext()) {
								Role role = (Role)iterator.next();
								//list = (role.getViews());
								List viewLst = role.getViews();
								Iterator iterator2 = viewLst.iterator();
								while (iterator2.hasNext()) {
									View view = (View)iterator2.next();
									//log.info("[ View :"+view.getViewName()+"]");
									set.add(view);
								}
								//location
								Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
								httpSession.setAttribute(CommonConstants.LOCATION, location);
							}
							/*
							Iterator iterator2 = set.iterator();
							while (iterator2.hasNext()) {
								View view = (View)iterator2.next();
								log.info("[ FinalView :"+view.getViewName()+"]");
							}*/
							//log.info("[ size : "+list.size()+" ]");
							httpSession.setAttribute(CommonConstants.VIEWACCESS,set);
							// check if customer
						} else {
					    List list = LocationDAO.getInstance().findAll(Order.asc("Name"));
			        request.setAttribute("locationLst", list);
							ActionMessages errors = new ActionMessages();
							errors.add("errorPassword", new ActionMessage("error.password"));
							saveErrors(request,errors);
							//return mapping.findForward("success");
							return (new ActionForward(mapping.getInput()));
						}
					} else {
				    List list = LocationDAO.getInstance().findAll(Order.asc("Name"));
		        request.setAttribute("locationLst", list);
						ActionMessages errors = new ActionMessages();
						errors.add("errorUnactive",new ActionMessage("error.unactive.user"));
						saveErrors(request,errors);
						//return mapping.findForward("success");
						return (new ActionForward(mapping.getInput()));
					}
				} else {
			    List list = LocationDAO.getInstance().findAll(Order.asc("Name"));
	        request.setAttribute("locationLst", list);
					ActionMessages errors = new ActionMessages();
					errors.add("errorPassword",new ActionMessage("error.password"));
					saveErrors(request,errors);
					//return mapping.findForward("success");
					return (new ActionForward(mapping.getInput()));
				}
			}
		}catch(Exception ex) {
			generalError(request,ex);
			log.info("[ Error logon : "+ex+" ]");
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				UsersDAO.closeCurrentThreadSessions();
			} catch(Exception exx) {
			}
		}
		return mapping.findForward("success");
	}

	/** 
	 * Method performLogoff
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performLogoff(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpSession = request.getSession();
		try {
			httpSession.invalidate();
			_RootDAO.closeCurrentThreadSessions();
		} catch(Exception ex) {
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}
		return mapping.findForward("index");
	}
	
	/** 
	 * Method performLogonForm
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performLogonForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
	    try {
	        List list = LocationDAO.getInstance().findAll(Order.asc("Name"));
	        request.setAttribute("locationLst", list);
	    } catch(Exception ex) {
	    } finally {
	        try {
	            LocationDAO.getInstance().closeSessionForReal();
	        }catch(Exception ex){}
	    }
	    return mapping.findForward("index");
	}

	
	/** 
	 * Method generalError
	 * @param HttpServletRequest request
	 * @param Exception ex
	 */
	private void generalError(HttpServletRequest request, Exception ex) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.global",ex.getMessage()));
		saveErrors(request,errors);
	}

}
