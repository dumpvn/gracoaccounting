//---------------------------------------------------------
// Application: Garage
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright � 2005 MPE
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
import com.mpe.financial.model.ItemStatus;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.ItemStatusDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.struts.form.ItemStatusForm;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class ItemStatusAction extends Action {
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
		//ItemStatusForm productCategoryForm = (ItemStatusForm) form;
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
		ItemStatusForm form = (ItemStatusForm) actionForm;
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
			Criteria criteria = ItemStatusDAO.getInstance().getSession().createCriteria(ItemStatus.class);
			if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", "%"+form.getString("code")+"%"));
			//if (form.getLong("parentId")>0)criteria.add(Restrictions.eq("Parent.Id", new Long(form.getLong("parentId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = ItemStatusDAO.getInstance().getSession().createCriteria(ItemStatus.class);
			if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", "%"+form.getString("code")+"%"));
			//if (form.getLong("parentId")>0)criteria.add(Restrictions.eq("Parent.Id", new Long(form.getLong("parentId"))));
			//criteria.addOrder(Order.desc("Name"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("ITEMSTATUS",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemStatusDAO.getInstance().closeSessionForReal();
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
		ItemStatusForm form = (ItemStatusForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// relationships
			//List itemCategoryLst = ItemStatusDAO.getInstance().getSession().createQuery("from ItemStatus as pc where pc.Parent is null").list();
			//request.setAttribute("itemCategoryLst",itemCategoryLst);
			if (form.getLong("itemStatusId") == 0) {
				form.setString("isDefaultStatus","Y");
				/*
				List productIdList = ProductDAO.getInstance().findAll();
				request.setAttribute("productIdList",productIdList);
				List productSelectedIdList = new LinkedList();
				request.setAttribute("productSelectedIdList",productSelectedIdList);
				*/
				form.setString("itemStatusId",0);
				form.setCurentTimestamp("createOn");
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				/*
				List productIdList = ProductDAO.getInstance().findAll();
				request.setAttribute("productIdList",productIdList);
				List productSelectedIdList = ProductDAO.getInstance().getSession().createCriteria(Product.class).setFetchMode("ProductCategories", FetchMode.JOIN).createCriteria("ProductCategories").add(Expression.eq("Id", new Long(form.getLong("itemStatusId")))).list();
				request.setAttribute("productSelectedIdList",productSelectedIdList);
				*/
				ItemStatus obj = ItemStatusDAO.getInstance().get(form.getLong("itemStatusId"));
				form.setString("itemStatusId",obj.getId());
				form.setString("code",obj.getCode());
				form.setString("description",obj.getDescription());
				form.setString("variable",obj.getVariable());
				form.setString("isDefaultStatus",obj.isDefaultStatus()==true?"Y":"N");
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
			}
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			/*
			try {
				ProductDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}*/
			try {
				ItemStatusDAO.getInstance().closeSessionForReal();
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
		ItemStatusForm form = (ItemStatusForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			ItemStatus obj = null;
			if (form.getLong("itemStatusId") == 0) {
				obj = (ItemStatus)ItemStatusDAO.getInstance().getSession().createCriteria(ItemStatus.class).add(Restrictions.eq("Code", form.getString("code"))).uniqueResult();
				if (obj==null) {
					obj = new ItemStatus();
					obj.setCode(form.getString("code"));
					obj.setDescription(form.getString("description"));
					obj.setVariable(form.getInt("variable"));
					obj.setDefaultStatus(form.getString("isDefaultStatus").length()>0?true:false);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					//ItemStatus parent = ItemStatusDAO.getInstance().get(form.getLong("parentId"));
					//obj.setParent(parent);
					/*
					String[] productId = form.getCollectionSelect("productSelectedIdList");
					Set set = new LinkedHashSet();
					for (int i=0; i<productId.length; i++) {
						Product product = ProductDAO.getInstance().get(Long.parseLong(productId[i]));
						set.add(product);
					}
					obj.setProducts(set);
					*/
					ItemStatusDAO.getInstance().save(obj);
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("name")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				obj = ItemStatusDAO.getInstance().get(form.getLong("itemStatusId"));
				obj.setCode(form.getString("code"));
				obj.setDescription(form.getString("description"));
				obj.setVariable(form.getInt("variable"));
				obj.setDefaultStatus(form.getString("isDefaultStatus").length()>0?true:false);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				//ItemStatus parent = ItemStatusDAO.getInstance().get(form.getLong("parentId"));
				//obj.setParent(parent);
				//obj.setId(form.getLong("itemStatusId"));
				/*
				String[] productId = form.getCollectionSelect("productSelectedIdList");
				Set set = new LinkedHashSet();
				for (int i=0; i<productId.length; i++) {
					Product product = ProductDAO.getInstance().get(Long.parseLong(productId[i]));
					set.add(product);
				}
				obj.setProducts(set);
				*/
				ItemStatusDAO.getInstance().update(obj);
			}
		}catch(Exception ex) {
			try {
				try {
					//List itemStatusLst = ItemStatusDAO.getInstance().getSession().createQuery("from ItemStatus as pc where pc.Parent is null").list();
					//request.setAttribute("itemStatusLst",itemStatusLst);
					/*
					List productIdList = ProductDAO.getInstance().getSession().createCriteria(Product.class).setFetchMode("itemStatus", FetchMode.DEFAULT).add(Expression.eq("itemStatus.id", new Long(form.getLong("itemStatusId")))).list();
					request.setAttribute("productIdList",productIdList);
					*/
					//List productSelectedIdList = ProductDAO.getInstance().getSession().createQuery("from Product as p where p.itemStatusId = "+form.getLong("itemStatusId")).list();
					//request.setAttribute("productSelectedIdList",productSelectedIdList);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			/*
			try {
				ProductDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}*/
			try {
				ItemStatusDAO.getInstance().closeSessionForReal();
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
	private ActionForward performDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemStatusForm form = (ItemStatusForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			ItemStatus itemStatus = ItemStatusDAO.getInstance().get(form.getLong("itemStatusId"));
			request.setAttribute("itemStatus", itemStatus);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				ItemStatusDAO.getInstance().closeSessionForReal();
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
		ItemStatusForm form = (ItemStatusForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			ItemStatusDAO.getInstance().delete(form.getLong("itemStatusId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				ItemStatusDAO.getInstance().closeSessionForReal();
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