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

import com.mpe.financial.model.ItemUnit;
import com.mpe.financial.model.ItemUnitExchangeFK;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.struts.form.ItemUnitForm;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class ItemUnitAction extends Action {
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
		//ItemUnitForm currencyForm = (ItemUnitForm) form;
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
		ItemUnitForm form = (ItemUnitForm) actionForm;
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
			Criteria criteria = ItemUnitDAO.getInstance().getSession().createCriteria(ItemUnit.class);
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = ItemUnitDAO.getInstance().getSession().createCriteria(ItemUnit.class);
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
			request.setAttribute("ITEMUNIT",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemUnitDAO.getInstance().closeSessionForReal();
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
		ItemUnitForm form = (ItemUnitForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// remove
			ItemUnit obj = (ItemUnit)httpSession.getAttribute("itemUnit");
			if (form.getLong("toItemUnitId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEITEMUNIT")) {
				ItemUnitExchangeFK removeItemUnitExchangeFK = null;
				Iterator iterator = obj.getItemUnitExchangesByFromItemUnit().iterator();
				while (iterator.hasNext()) {
					ItemUnitExchangeFK itemUnitExchangeFK = (ItemUnitExchangeFK)iterator.next();
					if (form.getLong("toItemUnitId") == itemUnitExchangeFK.getToItemUnit().getId()) {
					    removeItemUnitExchangeFK = itemUnitExchangeFK;
					}
				}
				if (removeItemUnitExchangeFK!=null) {
					Set set = obj.getItemUnitExchangesByFromItemUnit();
					set.remove(removeItemUnitExchangeFK);
					obj.setItemUnitExchangesByFromItemUnit(set);
				}
				form.setString("subaction", "");
				form.setString("toItemUnitId", "");
				httpSession.setAttribute("itemUnit", obj);
			}
			// relationships
			List toItemUnitLst = ItemUnitDAO.getInstance().findAll();
			request.setAttribute("toItemUnitLst", toItemUnitLst);
			if (form.getLong("itemUnitId") == 0) {
				form.setString("itemUnitId",0);
				form.setCurentTimestamp("createOn");
				if (obj!=null) {
					form.setString("itemUnitId",obj.getId());
					form.setString("name",obj.getName());
					form.setString("isBase",obj.isBase()?"Y":"N");
					form.setString("symbol",obj.getSymbol());
					Set itemUnitExchangeFromLst = obj.getItemUnitExchangesByFromItemUnit();
					request.setAttribute("itemUnitExchangeFromLst", itemUnitExchangeFromLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					httpSession.setAttribute("itemUnit",obj);
				}
				//httpSession.setAttribute("itemUnit",obj);
				form.setString("itemUnitId",obj.getId());
				form.setString("name",obj.getName());
				form.setString("isBase",obj.isBase()?"Y":"N");
				form.setString("symbol",obj.getSymbol());
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set itemUnitExchangeFromLst = obj.getItemUnitExchangesByFromItemUnit();
				request.setAttribute("itemUnitExchangeFromLst", itemUnitExchangeFromLst);
			}
			if (form.getLong("toItemUnitId") > 0) {
				//ItemUnitExchange itemUnitExchange = ItemUnitExchangeDAO.getInstance().load(form.getLong("itemUnitExchangeId"));
				//form.setString("exchangeRate", itemUnitExchange.getExchangeRate());
				//form.setString("toItemUnitId", itemUnitExchange.getToItemUnit().getId());
				//form.setString("itemUnitExchangeId", itemUnitExchange.getId());
				Iterator iterator = obj.getItemUnitExchangesByFromItemUnit().iterator();
				while (iterator.hasNext()) {
					ItemUnitExchangeFK itemUnitExchangeFK = (ItemUnitExchangeFK)iterator.next();
					if (form.getLong("toItemUnitId") == itemUnitExchangeFK.getToItemUnit().getId()) {
						form.setString("conversion", Formater.getFormatedOutputForm(itemUnitExchangeFK.getConversion()));
					}
				}
			}
		}catch(Exception ex) {
			try {
					List toItemUnitLst = ItemUnitDAO.getInstance().findAll();
					request.setAttribute("toItemUnitLst", toItemUnitLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				ItemUnitDAO.getInstance().closeSessionForReal();
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
		ItemUnitForm form = (ItemUnitForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("itemUnit");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			ItemUnit obj = (ItemUnit)httpSession.getAttribute("itemUnit");
			//if (obj==null) obj = new ItemUnit();
			if (form.getLong("itemUnitId") == 0) {
				obj = (ItemUnit)ItemUnitDAO.getInstance().getSession().createCriteria(ItemUnit.class).add(Restrictions.eq("Name", form.getString("name"))).uniqueResult();
				if (obj==null) {
					obj = (ItemUnit)httpSession.getAttribute("itemUnit");
					if (obj==null) obj = new ItemUnit();
					obj.setName(form.getString("name"));
					obj.setSymbol(form.getString("symbol"));
					obj.setBase(form.getString("isBase").length()>0?true:false);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					//ItemUnitDAO.getInstance().save(obj);
					//form.setString("itemUnitId", obj.getId());
				} else {
					List toItemUnitLst = ItemUnitDAO.getInstance().findAll();
					request.setAttribute("toItemUnitLst", toItemUnitLst);
					Set itemUnitExchangeFromLst = obj!=null?obj.getItemUnitExchangesByFromItemUnit():new LinkedHashSet();
					request.setAttribute("itemUnitExchangeFromLst", itemUnitExchangeFromLst);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("name")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
				obj.setName(form.getString("name"));
				obj.setSymbol(form.getString("symbol"));
				obj.setBase(form.getString("isBase").length()>0?true:false);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("itemUnitId"));
				//ItemUnitDAO.getInstance().update(obj);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDITEMUNIT")) {
				if (form.getLong("toItemUnitId") >0 && form.getDouble("conversion")>0) {
				  ItemUnitExchangeFK itemUnitExchangeFK = new ItemUnitExchangeFK();
					ItemUnit toItemUnit = ItemUnitDAO.getInstance().get(form.getLong("toItemUnitId"));
					itemUnitExchangeFK.setToItemUnit(toItemUnit);
					itemUnitExchangeFK.setConversion(form.getDouble("conversion"));
					Set set = obj.getItemUnitExchangesByFromItemUnit();
					if (set==null) set = new LinkedHashSet();
					ItemUnitExchangeFK removeItemUnitExchangeFK = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						ItemUnitExchangeFK itemUnitExchangeFK2 = (ItemUnitExchangeFK)iterator.next();
						if (form.getLong("toItemUnitId")==itemUnitExchangeFK2.getToItemUnit().getId()) {
						    removeItemUnitExchangeFK = itemUnitExchangeFK2;
						}
					}
					if (removeItemUnitExchangeFK!=null) {
						set.remove(removeItemUnitExchangeFK);
						set.add(itemUnitExchangeFK);
					} else {
						set.add(itemUnitExchangeFK);
					}
					obj.setItemUnitExchangesByFromItemUnit(set);
					// netral
					form.setString("toItemUnitId", "");
					form.setString("conversion", "");
				}
				// netral
				form.setString("toItemUnitId", "");
				form.setString("conversion", "");
			}
			// save to session
			httpSession.setAttribute("itemUnit", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				ItemUnitDAO.getInstance().saveOrUpdate(obj);
				// remove session
				httpSession.removeAttribute("itemUnit");
				// finish
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				try {
					List toItemUnitLst = ItemUnitDAO.getInstance().findAll();
					request.setAttribute("toItemUnitLst", toItemUnitLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				ItemUnitDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?itemUnitId="+form.getLong("itemUnitId"));
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
		ItemUnitForm form = (ItemUnitForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
			request.setAttribute("itemUnit", itemUnit);
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				ItemUnitDAO.getInstance().closeSessionForReal();
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
		ItemUnitForm form = (ItemUnitForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			ItemUnitDAO.getInstance().delete(form.getLong("itemUnitId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				ItemUnitDAO.getInstance().closeSessionForReal();
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