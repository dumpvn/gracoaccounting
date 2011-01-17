//---------------------------------------------------------
// Application: Corporate
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright © 2005 O3
// Generated at Wed Jun 15 21:19:53 GMT+07:00 2005
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
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import com.mpe.common.CommonConstants;
import com.mpe.common.CommonUtil;
import com.mpe.common.Pager;
import com.mpe.financial.struts.form.UserForm;
import com.mpe.financial.model.GeneralLedgerReport;
import com.mpe.financial.model.MinimumItemStock;
import com.mpe.financial.model.Organization;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Role;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.OrganizationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.RoleDAO;
import com.mpe.financial.model.dao.UsersDAO;

public class UserAction extends Action {
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
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		ActionForward forward = null;
		String action = mapping.getParameter();
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Set lst = (Set)httpSession.getAttribute(CommonConstants.VIEWACCESS);
		if (users!=null) {
			if (CommonUtil.isHasRoleAccess(lst,request.getServletPath())) {
				if ("".equalsIgnoreCase(action)) {
					forward = mapping.findForward("home");
				} else if ("HOME".equalsIgnoreCase(action)) {
					forward = performHome(mapping, form, request, response);
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
		UserForm form = (UserForm) actionForm;
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
			Criteria criteria = UsersDAO.getInstance().getSession().createCriteria(Users.class);
			if (form.getString("userName")!=null && form.getString("userName").length()>0)criteria.add(Restrictions.like("UserName", "%"+form.getString("userName")+"%"));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = UsersDAO.getInstance().getSession().createCriteria(Users.class);
			if (form.getString("userName")!=null && form.getString("userName").length()>0)criteria.add(Restrictions.like("UserName", "%"+form.getString("userName")+"%"));
			//criteria.addOrder(Order.asc("UserName"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List results = criteria.list();
			request.setAttribute("USER",results);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				UsersDAO.getInstance().closeSessionForReal();
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
		UserForm form = (UserForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			List lst = RoleDAO.getInstance().findAll(Order.asc("Id"));
			request.setAttribute("roleLst",lst);
			// relationships
			List organizationLst = OrganizationDAO.getInstance().findAll();
			request.setAttribute("organizationLst", organizationLst);
			if (form.getLong("userId")==0) {
				form.setString("userId",0);
				form.setString("isActive", "Y");
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT, httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				Users obj = UsersDAO.getInstance().get(form.getLong("userId"));
				form.setString("userId",obj.getId());
				Set set = obj.getRoles();
				String collectionSelect[] = new String[set.size()];
				java.util.Iterator itr = set.iterator();
				for (int i=0; i<collectionSelect.length; i++) {
					Role role = (Role)itr.next();
					collectionSelect[i] = (String.valueOf(role.getId()));
				}
				// more than 5 ==> look javascript validation
				form.setString("userPass","123456");
				form.setCollectionSelect("roleId",collectionSelect);
				form.setString("isActive",obj.isActive()?"Y":"N");
				form.setString("userName",obj.getUserName());
				form.setString("userType",obj.getUserType());
				form.setString("organizationId", obj.getOrganization()!=null?obj.getOrganization().getId():0);
			}
		}catch(Exception ex) {
			log.info("Err : "+ex);
		}finally {
			try {
				RoleDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
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
		UserForm form = (UserForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {				
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			if (form.getLong("userId")==0) {
				Users obj = (Users)UsersDAO.getInstance().getSession().createCriteria(Users.class).add(Restrictions.eq("UserName", form.getString("userName"))).uniqueResult();
				if (obj==null) {
					obj = new Users();
					obj.setUserName(form.getString("userName"));
					obj.setUserPass(CommonUtil.digest(form.getString("userPass")));
					obj.setActive(form.getString("isActive").length()>0?true:false);
					obj.setUserType(form.getString("userType"));
					Organization organization = OrganizationDAO.getInstance().get(form.getLong("organizationId"));
					obj.setOrganization(organization);
					// role
					String[] roleId = form.getCollectionSelect("roleId");
					Set set = new LinkedHashSet();
					for (int i=0; i<roleId.length; i++) {
						Role role = RoleDAO.getInstance().get(Long.parseLong(roleId[i]));
						set.add(role);
					}
					obj.setRoles(set);
					UsersDAO.getInstance().save(obj);
				} else {
					List lst = RoleDAO.getInstance().findAll(Order.asc("Id"));
					request.setAttribute("roleLst",lst);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("userName")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				Users obj = UsersDAO.getInstance().load(form.getLong("userId"));
				obj.setUserName(form.getString("userName"));
				if (form.getString("confirmUserPass").length()>0) obj.setUserPass(CommonUtil.digest(form.getString("confirmUserPass")));
				obj.setActive(form.getString("isActive").length()>0?true:false);
				obj.setUserType(form.getString("userType"));
				Organization organization = OrganizationDAO.getInstance().get(form.getLong("organizationId"));
				obj.setOrganization(organization);
				// role
				String[] roleId = form.getCollectionSelect("roleId");
				Set set = new LinkedHashSet();
				for (int i=0; i<roleId.length; i++) {
					Role role = RoleDAO.getInstance().get(Long.parseLong(roleId[i]));
					set.add(role);
				}
				obj.setRoles(set);
				UsersDAO.getInstance().update(obj);
			}
		}catch(Exception ex) {
			try {
				List lst = RoleDAO.getInstance().findAll(Order.asc("Id"));
				request.setAttribute("roleLst",lst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				RoleDAO.getInstance().closeSessionForReal();
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
		UserForm form = (UserForm) actionForm;
		try {
			Users users = UsersDAO.getInstance().get(form.getLong("userId"));
			request.setAttribute("user", users);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				UsersDAO.getInstance().closeSessionForReal();
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
		UserForm form = (UserForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			UsersDAO.getInstance().delete(form.getLong("userId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
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
	 * Method performHome
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performHome(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {	
		//UserForm form = (UserForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			// bank type balance
			String sql = "select a.chart_of_account_id as {gl.ChartOfAccountId}, a.number as {gl.Number}, a.name as {gl.Name}, a.type as {gl.Type}, a.groups as {gl.Groups}, a.is_debit as {gl.Debit}, " +
				"IFNULL((select sum(b.amount) from general_ledger b where b.chart_of_account_id=a.chart_of_account_id and b.organization_id = :organizationId and b.is_setup='Y' and b.is_closed='N'), 0) as {gl.FirstSetupAmount}, " +
				"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :setupDate and d.journal_date < :fromDate and d.is_posted = 'Y'), 0) as {gl.PreviousAmount}, " +
				"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :fromDate and d.journal_date <= :toDate and d.is_posted = 'Y'), 0) as {gl.Amount}, " +
				"(select e.number_of_digit from organization_setup e where e.organization_id=:organizationId) as {gl.NumberOfDigit} " +
				"from chart_of_account a where a.Type = 'Bank'" +
				"";
			List bankChartOfAccountLst = session.createSQLQuery(sql)
			.addEntity("gl", GeneralLedgerReport.class)
			.setLong("organizationId", users.getOrganization().getId())
			.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
			.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
			.setDate("toDate", new Date((new GregorianCalendar()).getTime().getTime()))
			.list();
			request.setAttribute("bankChartOfAccountLst", bankChartOfAccountLst);
			
			String sql2 = "" +
				"SELECT a.item_id as {mis.ItemId}, a.code as {mis.Code}, a.name as {mis.Name}, " +
				"IFNULL(((select sum(e.quantity) from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date < :toDate and f.status<>'CANCEL' and e.item_id=a.item_id)-(select sum(g.quantity) from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date < :toDate and g.item_id=a.item_id)-(select sum(e.quantity) from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id) and e.item_id=a.item_id)+(select sum(e.quantity) from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id) and e.item_id=a.item_id)-(select sum(e.difference) from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id) and isnull(f.customer_id) and e.item_id=a.item_id)),0) as {mis.Quantity}, " +
				"j.minimum_stock as {mis.MinimumQuantity} " +
				"FROM item a join inventory j on a.item_id=j.item_id " +
				"where (j.minimum_stock > IFNULL(((select sum(e.quantity) from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date < :toDate and f.status<>'CANCEL' and e.item_id=a.item_id)-(select sum(g.quantity) from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date < :toDate and g.item_id=a.item_id)-(select sum(e.quantity) from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id) and e.item_id=a.item_id)+(select sum(e.quantity) from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id) and e.item_id=a.item_id)-(select sum(e.difference) from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id) and isnull(f.customer_id) and e.item_id=a.item_id)),0)) " +
				"order by (j.minimum_stock - IFNULL(((select sum(e.quantity) from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date < :toDate and f.status<>'CANCEL' and e.item_id=a.item_id)-(select sum(g.quantity) from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date < :toDate and g.item_id=a.item_id)-(select sum(e.quantity) from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id) and e.item_id=a.item_id)+(select sum(e.quantity) from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id) and e.item_id=a.item_id)-(select sum(e.difference) from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id) and isnull(f.customer_id) and e.item_id=a.item_id)),0)) DESC " +
				"limit 0,5 " +
				"";
			List minimumStockLst = session.createSQLQuery(sql2)
			.addEntity("mis", MinimumItemStock.class)
			.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
			.setDate("toDate", new Date((new GregorianCalendar()).getTime().getTime()))
			.list();
			request.setAttribute("minimumStockLst", minimumStockLst);
			
			
		} catch(Exception ex) {
			generalError(request,ex);
			log.info("[Error HOME : "+ex+"]");
			return mapping.findForward("index");
		} finally {
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