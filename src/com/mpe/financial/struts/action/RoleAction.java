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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.CommonConstants;
import com.mpe.common.CommonUtil;
import com.mpe.common.Pager;
import com.mpe.financial.struts.form.RoleForm;
import com.mpe.financial.model.Role;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.RoleDAO;
import com.mpe.financial.model.dao.ViewDAO;
import com.mpe.financial.model.View;

public class RoleAction extends Action {
	Log log = LogFactory.getFactory().getInstance(this.getClass());
	
	/** 
	 * Method execute
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//RoleForm form = (Role) actionForm;
		ActionForward forward = null;
		String action = mapping.getParameter();
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Set lst = (Set)httpSession.getAttribute(CommonConstants.VIEWACCESS);
		if (users!=null) {
			if (CommonUtil.isHasRoleAccess(lst,request.getServletPath())) {
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
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.privilage",request.getContextPath()+mapping.getPath()));
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
		RoleForm form = (RoleForm) actionForm;
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
			Criteria criteria = RoleDAO.getInstance().getSession().createCriteria(Role.class);
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("RoleName", "%"+form.getString("name")+"%"));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = RoleDAO.getInstance().getSession().createCriteria(Role.class);
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("RoleName", "%"+form.getString("name")+"%"));
			//criteria.addOrder(Order.desc("Id"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List role = criteria.list();
			request.setAttribute("ROLE",role);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				RoleDAO.getInstance().closeSessionForReal();
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
		RoleForm form = (RoleForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// relationships
			if (form.getLong("roleId")==0) {
				List list = ViewDAO.getInstance().findAll(Order.asc("Id"));
				request.setAttribute("viewLst",list);
				List lst2 = new LinkedList();
				request.setAttribute("viewSltdLst",lst2);
				form.setString("roleId",0);
				request.setAttribute("roleId","0");
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				Role role = RoleDAO.getInstance().get(form.getLong("roleId"));
				List lst2 = role.getViews();
				List results = ViewDAO.getInstance().findAll(Order.asc("Id"));
				List list = new LinkedList();
				Iterator iterator = results.iterator();
				while (iterator.hasNext()) {
					boolean isSame = false;
					View view = (View)iterator.next();
					Iterator iterator2 = lst2.iterator();
					while (iterator2.hasNext()) {
						View view2 = (View)iterator2.next();
						if (view.getId() == view2.getId()) isSame = true;
					}
					if (!isSame) list.add(view);
				}
				request.setAttribute("viewLst",list);
				request.setAttribute("viewSltdLst",lst2);
				form.setString("roleId",role.getId());
				form.setString("name",role.getRoleName());
				form.setString("description",role.getDescription());
			}
		}catch(Exception ex) {
		}finally {
			try {
				RoleDAO.getInstance().closeSessionForReal();
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
		RoleForm form = (RoleForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {				
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Role obj =null;
			if (form.getLong("roleId")==0) {
				obj = (Role)RoleDAO.getInstance().getSession().createCriteria(Role.class).add(Restrictions.eq("RoleName", form.getString("name"))).uniqueResult();
				if (obj == null) {
					obj = new Role();
					obj.setRoleName(form.getString("name"));
					obj.setDescription(form.getString("description"));
					List list = new java.util.LinkedList();
					String[] viewId = form.getCollectionSelect("viewSltdLstId");
					for (int i=0; i<viewId.length; i++) {
						View view = ViewDAO.getInstance().get(Long.parseLong(viewId[i]));
						list.add(view);
					}
					obj.setViews(list);
					RoleDAO.getInstance().save(obj);
				} else {
					List list = ViewDAO.getInstance().findAll(Order.asc("Id"));
					request.setAttribute("viewLst",list);
					List lst2 = new LinkedList();
					request.setAttribute("viewSltdLst",lst2);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("name")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				obj = RoleDAO.getInstance().load(form.getLong("roleId"));
				obj.setRoleName(form.getString("name"));
				obj.setDescription(form.getString("description"));
				List list = new java.util.LinkedList();
				String[] viewId = form.getCollectionSelect("viewSltdLstId");
				for (int i=0; i<viewId.length; i++) {
					View view = ViewDAO.getInstance().get(Long.parseLong(viewId[i]));
					list.add(view);
				}
				obj.setViews(list);
				RoleDAO.getInstance().update(obj);
			}
			//load new role access
			/*
			Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
			httpSession.removeAttribute(CommonConstants.VIEWACCESS);
			java.util.Iterator iterator = users.getRoles().iterator();
			java.util.List list = new java.util.LinkedList();
			while (iterator.hasNext()) {
				Role role = (Role)iterator.next();
				list.addAll(role.getViews());
			}
			httpSession.setAttribute(CommonConstants.VIEWACCESS, list);
			*/
		}catch(Exception ex) {
			try {
				List list = ViewDAO.getInstance().findAll(Order.asc("Id"));
				request.setAttribute("viewLst",list);
				List list2 = new java.util.LinkedList();
				if (form.getLong("roleId") > 0) {
					Role role = RoleDAO.getInstance().get(form.getLong("roleId"));
					list2 = role.getViews();
				}
				request.setAttribute("viewSltdLst",list2);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				ViewDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				RoleDAO.getInstance().closeSessionForReal();
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
		RoleForm form = (RoleForm) actionForm;
		try {
			Role obj = RoleDAO.getInstance().get(form.getLong("roleId"));
			request.setAttribute("role", obj);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				RoleDAO.getInstance().closeSessionForReal();
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
		RoleForm form = (RoleForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			RoleDAO.getInstance().delete(form.getLong("roleId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				RoleDAO.getInstance().closeSessionForReal();
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
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.global",ex.getMessage()));
		saveErrors(request,errors);
	}

}