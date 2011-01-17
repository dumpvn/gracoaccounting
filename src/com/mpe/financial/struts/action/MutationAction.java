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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpe.financial.model.Customers;
import com.mpe.financial.model.Inventory;
import com.mpe.financial.model.Item;
import com.mpe.financial.model.ItemUnit;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.Mutation;
import com.mpe.financial.model.MutationDetail;
import com.mpe.financial.model.MutationDetailPK;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Warehouse;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.MutationDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.WarehouseDAO;
import com.mpe.financial.struts.form.MutationForm;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class MutationAction extends Action {
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
		MutationForm mutationForm = (MutationForm) form;
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
					  if (mutationForm.getString("subaction")!=null && mutationForm.getString("subaction").equalsIgnoreCase("refresh")) {
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
		MutationForm form = (MutationForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
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
			Criteria criteria = MutationDAO.getInstance().getSession().createCriteria(Mutation.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getCalendar("fromMutationDate")!=null)criteria.add(Restrictions.ge("MutationDate", new Date(form.getCalendar("fromMutationDate").getTime().getTime())));
			if (form.getCalendar("toMutationDate")!=null)criteria.add(Restrictions.le("MutationDate", new Date(form.getCalendar("toMutationDate").getTime().getTime())));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = MutationDAO.getInstance().getSession().createCriteria(Mutation.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getCalendar("fromMutationDate")!=null)criteria.add(Restrictions.ge("MutationDate", new Date(form.getCalendar("fromMutationDate").getTime().getTime())));
			if (form.getCalendar("toMutationDate")!=null)criteria.add(Restrictions.le("MutationDate", new Date(form.getCalendar("toMutationDate").getTime().getTime())));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			//criteria.addOrder(Order.desc("Name"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("MUTATION",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("mutation");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				MutationDAO.getInstance().closeSessionForReal();
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
		MutationForm form = (MutationForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// remove
			Mutation obj = (Mutation)httpSession.getAttribute("mutation");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEMUTATIONDETAIL")) {
				MutationDetail removeMutationDetail = null;
				Iterator iterator = obj.getMutationDetails().iterator();
				while (iterator.hasNext()) {
				    MutationDetail mutationDetail = (MutationDetail)iterator.next();
					if (form.getLong("itemId") == mutationDetail.getId().getItem().getId()) {
					    removeMutationDetail = mutationDetail;
					}
				}
				if (removeMutationDetail!=null) {
					Set set = obj.getMutationDetails();
					set.remove(removeMutationDetail);
					obj.setMutationDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("purchaseOrder", obj);
			}
			// relationships
			List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("locationLst", locationLst);
			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Store", Boolean.TRUE)).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerLst", customerLst);
			List fromWarehouseLst = null;
			if (form.getLong("locationId")>0) {
			    fromWarehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.isNull("Customer.Id"))
						.add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId")))).list();
			} else if (form.getLong("customerId")>0) {
			    fromWarehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
						.add(Restrictions.isNull("Location.Id")).list();
			} else {
				fromWarehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.isNull("Customer.Id"))
					.add(Restrictions.isNull("Location.Id")).list();
			}
			request.setAttribute("fromWarehouseLst", fromWarehouseLst);
			List toWarehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				//.add(Restrictions.isNull("Customer.Id"))
				//.add(Restrictions.isNull("Location.Id"))
				.list();
			request.setAttribute("toWarehouseLst", toWarehouseLst);
			List itemLst = ItemDAO.getInstance().getSession().createCriteria(Item.class)
				.add(Restrictions.eq("Type", new String("Product"))).addOrder(Order.asc("Name")).list();
			request.setAttribute("itemLst", itemLst);
			List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemUnitLst", itemUnitLst);
			if (form.getLong("mutationId") == 0) {
				form.setString("mutationId",0);
				form.setString("number", RunningNumberDAO.getInstance().getMutationNumber());
				form.setCurentCalendar("mutationDate");
				form.setCurentTimestamp("createOn");
				if (obj!=null) {
				    form.setString("description",obj.getDescription());
						form.setString("number",obj.getNumber());
						form.setString("fromWarehouseId",obj.getFromWarehouse()!=null?obj.getFromWarehouse().getId():0);
						form.setCalendar("mutationDate",obj.getMutationDate());
						form.setString("toWarehouseId",obj.getToWarehouse()!=null?obj.getToWarehouse().getId():0);
						form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
						form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
						Set mutationDetailLst = obj.getMutationDetails();
						request.setAttribute("mutationDetailLst", mutationDetailLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
		    if (obj==null) {
		        obj = MutationDAO.getInstance().get(form.getLong("mutationId"));
		        httpSession.setAttribute("mutation",obj);
		    }
				form.setString("mutationId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("number",obj.getNumber());
				form.setString("fromWarehouseId",obj.getFromWarehouse()!=null?obj.getFromWarehouse().getId():0);
				form.setCalendar("mutationDate",obj.getMutationDate());
				form.setString("toWarehouseId",obj.getToWarehouse()!=null?obj.getToWarehouse().getId():0);
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set mutationDetailLst = obj.getMutationDetails();
				request.setAttribute("mutationDetailLst", mutationDetailLst);
			}
			if (form.getLong("itemId") > 0) {
				boolean isNewData = true;
				if (obj!=null && obj.getMutationDetails()!=null) {
					Iterator iterator = obj.getMutationDetails().iterator();
					while (iterator.hasNext()) {
					  MutationDetail mutationDetail = (MutationDetail)iterator.next();
						if (form.getLong("itemId") == mutationDetail.getId().getItem().getId()) {
							isNewData = false;
							form.setString("movedQuantity", mutationDetail.getMovedQuantity());
							form.setString("previousQuantity", mutationDetail.getPreviousQuantity());
							form.setString("itemUnitId", Formater.getFormatedOutputForm(mutationDetail.getItemUnit()!=null?mutationDetail.getItemUnit().getId():0));
						}
					}
				}
				if (isNewData) {
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					if (inventory!=null) {
					  boolean a = true;
					  //TODO
					  if (a) form.setString("previousQuantity", "");
						form.setString("itemUnitId", Formater.getFormatedOutputForm(inventory.getItemUnit()!=null?inventory.getItemUnit().getId():0));
					}
				}
			}
		}catch(Exception ex) {
			try {
			    List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("locationLst", locationLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Store", Boolean.TRUE)).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List fromWarehouseLst = null;
					if (form.getLong("locationId")>0) {
					    fromWarehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
								.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
								.add(Restrictions.isNull("Customer.Id"))
								.add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId")))).list();
					} else if (form.getLong("customerId")>0) {
					    fromWarehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
								.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
								.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
								.add(Restrictions.isNull("Location.Id")).list();
					} else {
						fromWarehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.isNull("Customer.Id"))
							.add(Restrictions.isNull("Location.Id")).list();
					}
					request.setAttribute("fromWarehouseLst", fromWarehouseLst);
					List toWarehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						//.add(Restrictions.isNull("Customer.Id"))
						//.add(Restrictions.isNull("Location.Id"))
						.list();
					request.setAttribute("toWarehouseLst", toWarehouseLst);
					List itemLst = ItemDAO.getInstance().getSession().createCriteria(Item.class)
						.add(Restrictions.eq("Type", new String("Product"))).addOrder(Order.asc("Name")).list();
					request.setAttribute("itemLst", itemLst);
					List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("itemUnitLst", itemUnitLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				MutationDAO.getInstance().closeSessionForReal();
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
		MutationForm form = (MutationForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = MutationDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("mutation");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			Mutation obj = (Mutation)httpSession.getAttribute("mutation");
			if (form.getLong("purchaseOrderId") == 0) {
				obj = (Mutation)MutationDAO.getInstance().getSession().createCriteria(Mutation.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (Mutation)httpSession.getAttribute("mutation");
					if (obj==null) obj = new Mutation();
					obj.setDescription(form.getString("description"));
					obj.setNumber(form.getString("number"));
					obj.setOrganization(users.getOrganization());
					Warehouse fromWarehouse = WarehouseDAO.getInstance().get(form.getLong("fromWarehouseId"));
					Warehouse toWarehouse = WarehouseDAO.getInstance().get(form.getLong("toWarehouseId"));
					obj.setFromWarehouse(fromWarehouse);
					obj.setToWarehouse(toWarehouse);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setLocation(location);
					obj.setCustomer(customers);
					obj.setMutationDate(form.getCalendar("mutationDate")!=null?form.getCalendar("mutationDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					obj.setOrganization(users.getOrganization());
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("name")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = MutationDAO.getInstance().get(form.getLong("mutationId"));
				obj.setDescription(form.getString("description"));
				obj.setNumber(form.getString("number"));
				obj.setOrganization(users.getOrganization());
				Warehouse fromWarehouse = WarehouseDAO.getInstance().get(form.getLong("fromWarehouseId"));
				Warehouse toWarehouse = WarehouseDAO.getInstance().get(form.getLong("toWarehouseId"));
				obj.setFromWarehouse(fromWarehouse);
				obj.setToWarehouse(toWarehouse);
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setLocation(location);
				obj.setCustomer(customers);
				obj.setOrganization(users.getOrganization());
				obj.setMutationDate(form.getCalendar("mutationDate")!=null?form.getCalendar("mutationDate").getTime():null);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDMUTATIONDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("previousQuantity")>0 && form.getDouble("movedQuantity")>0) {
				  MutationDetail mutationDetail = new MutationDetail();
					Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					mutationDetail.setItemUnit(itemUnit);
					MutationDetailPK mutationDetailPK = new MutationDetailPK();
					mutationDetailPK.setItem(item);
					mutationDetailPK.setMutation(obj);
					mutationDetail.setId(mutationDetailPK);
					mutationDetail.setPreviousQuantity(form.getDouble("previousQuantity"));
					mutationDetail.setMovedQuantity(form.getDouble("movedQuantity"));
					Set set = obj.getMutationDetails();
					if (set==null) set = new LinkedHashSet();
					MutationDetail removeMutationDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						MutationDetail mutationDetail2 = (MutationDetail)iterator.next();
						if (form.getLong("itemId")==mutationDetail2.getId().getItem().getId()) {
						    removeMutationDetail = mutationDetail2;
						}
					}
					if (removeMutationDetail!=null) {
						set.remove(removeMutationDetail);
						set.add(mutationDetail);
					} else {
						set.add(mutationDetail);
					}
					obj.setMutationDetails(set);
					// netral
					form.setString("itemId", "");
					form.setString("movedQuantity", "");
					form.setString("previousQuantity", "");
					form.setString("itemUnitId", "");
				}
				// netral
				form.setString("itemId", "");
				form.setString("movedQuantity", "");
				form.setString("previousQuantity", "");
				form.setString("itemUnitId", "");
			}
			// save to session
			httpSession.setAttribute("mutation", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateMutationNumber(session);
					MutationDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					transaction = session.beginTransaction();
					MutationDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("mutation");
				// finish
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				if (transaction!=null) transaction.rollback();
				try {
				    List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
						request.setAttribute("locationLst", locationLst);
						List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.eq("Store", Boolean.TRUE)).addOrder(Order.asc("Company")).list();
						request.setAttribute("customerLst", customerLst);
						List fromWarehouseLst = null;
						if (form.getLong("locationId")>0) {
						    fromWarehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
									.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
									.add(Restrictions.isNull("Customer.Id"))
									.add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId")))).list();
						} else if (form.getLong("customerId")>0) {
						    fromWarehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
									.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
									.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
									.add(Restrictions.isNull("Location.Id")).list();
						} else {
							fromWarehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
								.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
								.add(Restrictions.isNull("Customer.Id"))
								.add(Restrictions.isNull("Location.Id")).list();
						}
						request.setAttribute("fromWarehouseLst", fromWarehouseLst);
						List toWarehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							//.add(Restrictions.isNull("Customer.Id"))
							//.add(Restrictions.isNull("Location.Id"))
							.list();
						request.setAttribute("toWarehouseLst", toWarehouseLst);
						List itemLst = ItemDAO.getInstance().getSession().createCriteria(Item.class)
							.add(Restrictions.eq("Type", new String("Product"))).addOrder(Order.asc("Name")).list();
						request.setAttribute("itemLst", itemLst);
						List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
						request.setAttribute("itemUnitLst", itemUnitLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				MutationDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?purchaseOrderId="+form.getLong("purchaseOrderId"));
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
		MutationForm form = (MutationForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			Mutation mutation = MutationDAO.getInstance().get(form.getLong("mutationId"));
			request.setAttribute("mutation", mutation);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				MutationDAO.getInstance().closeSessionForReal();
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
		MutationForm form = (MutationForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			MutationDAO.getInstance().delete(form.getLong("mutationId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				MutationDAO.getInstance().closeSessionForReal();
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