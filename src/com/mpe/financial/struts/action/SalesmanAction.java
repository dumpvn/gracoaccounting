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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpe.financial.model.Degree;
import com.mpe.financial.model.Department;
import com.mpe.financial.model.Salesman;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.DegreeDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.SalesmanDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.struts.form.SalesmanForm;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class SalesmanAction extends Action {
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
		SalesmanForm customerForm = (SalesmanForm) form;
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
						if (customerForm.getString("subaction")!=null && customerForm.getString("subaction").length()>0) {
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
		SalesmanForm form = (SalesmanForm) actionForm;
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
			Criteria criteria = SalesmanDAO.getInstance().getSession().createCriteria(Salesman.class);
			if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code"), MatchMode.ANYWHERE));
			if (form.getString("fullName")!=null && form.getString("fullName").length()>0)criteria.add(Restrictions.like("FullName", form.getString("fullName"), MatchMode.ANYWHERE));
			if (form.getString("nickName")!=null && form.getString("nickName").length()>0)criteria.add(Restrictions.like("NickName", form.getString("nickName"), MatchMode.ANYWHERE));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = SalesmanDAO.getInstance().getSession().createCriteria(Salesman.class);
			if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code"), MatchMode.ANYWHERE));
			if (form.getString("fullName")!=null && form.getString("fullName").length()>0)criteria.add(Restrictions.like("FullName", form.getString("fullName"), MatchMode.ANYWHERE));
			if (form.getString("nickName")!=null && form.getString("nickName").length()>0)criteria.add(Restrictions.like("NickName", form.getString("nickName"), MatchMode.ANYWHERE));
			//criteria.addOrder(Order.asc("ContactPerson"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("SALESMAN",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				SalesmanDAO.getInstance().closeSessionForReal();
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
		SalesmanForm form = (SalesmanForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			//relationship
			List degreeLst = DegreeDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("degreeLst", degreeLst);
			List departmentLst = DepartmentDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("departmentLst", departmentLst);
			if (form.getLong("salesmanId") == 0) {
				form.setString("salesmanId",0);
				form.setCurentTimestamp("createOn");
				form.setString("isActive", "Y");
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				Salesman obj = SalesmanDAO.getInstance().get(form.getLong("salesmanId"));
				form.setString("salesmanId",obj.getId());
				form.setString("code",obj.getCode());
				form.setString("fullName",obj.getFullName());
				form.setString("nickName",obj.getNickName());
				form.setString("address",obj.getAddress());
				form.setString("city",obj.getCity());
				form.setString("postalCode",obj.getPostalCode());
				form.setString("province",obj.getProvince());
				form.setString("telephone",obj.getTelephone());
				form.setString("mobile",obj.getMobile());
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				form.setString("isActive", obj.isActive()==true?"Y":"N");
				form.setString("degreeId",obj.getDegree()!=null?obj.getDegree().getId():0);
				form.setString("departmentId",obj.getDepartment()!=null?obj.getDepartment().getId():0);
			}
		}catch(Exception ex) {
			try {
				List degreeLst = DegreeDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("degreeLst", degreeLst);
				List departmentLst = DepartmentDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("departmentLst", departmentLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				SalesmanDAO.getInstance().closeSessionForReal();
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
		SalesmanForm form = (SalesmanForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Salesman obj = new Salesman();
			if (form.getLong("salesmanId") == 0) {				
				obj.setCode(form.getString("code"));
				obj.setFullName(form.getString("fullName"));
				obj.setNickName(form.getString("nickName"));
				obj.setOrganization(users.getOrganization());
				obj.setAddress(form.getString("address"));
				obj.setCity(form.getString("city"));
				obj.setPostalCode(form.getString("postalCode"));
				obj.setProvince(form.getString("province"));
				obj.setMobile(form.getString("mobile"));
				obj.setTelephone(form.getString("telephone"));
				obj.setCreateBy(users);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setActive(form.getString("isActive").length()>0?true:false);
				Degree degree = DegreeDAO.getInstance().get(form.getLong("degreeId"));
				obj.setDegree(degree);
				Department department = DepartmentDAO.getInstance().get(form.getLong("departmentId"));
				obj.setDepartment(department);
				obj.setOrganization(users.getOrganization());
				SalesmanDAO.getInstance().save(obj);
			} else {
				obj = SalesmanDAO.getInstance().load(form.getLong("salesmanId"));
				obj.setCode(form.getString("code"));
				obj.setFullName(form.getString("fullName"));
				obj.setNickName(form.getString("nickName"));
				obj.setOrganization(users.getOrganization());
				obj.setAddress(form.getString("address"));
				obj.setCity(form.getString("city"));
				obj.setPostalCode(form.getString("postalCode"));
				obj.setProvince(form.getString("province"));
				obj.setMobile(form.getString("mobile"));
				obj.setTelephone(form.getString("telephone"));
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setActive(form.getString("isActive").length()>0?true:false);
				obj.setOrganization(users.getOrganization());
				Degree degree = DegreeDAO.getInstance().get(form.getLong("degreeId"));
				obj.setDegree(degree);
				Department department = DepartmentDAO.getInstance().get(form.getLong("departmentId"));
				obj.setDepartment(department);
				SalesmanDAO.getInstance().update(obj);
			}
		}catch(Exception ex) {
			try {
				List degreeLst = DegreeDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("degreeLst", degreeLst);
				List departmentLst = DepartmentDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("departmentLst", departmentLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				SalesmanDAO.getInstance().closeSessionForReal();
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
		SalesmanForm form = (SalesmanForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			Salesman salesman = SalesmanDAO.getInstance().get(form.getLong("salesmanId"));
			request.setAttribute("salesman", salesman);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				SalesmanDAO.getInstance().closeSessionForReal();
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
		SalesmanForm form = (SalesmanForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			SalesmanDAO.getInstance().delete(form.getLong("salesmanId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				SalesmanDAO.getInstance().closeSessionForReal();
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