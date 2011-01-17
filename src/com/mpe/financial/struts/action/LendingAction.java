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

import com.mpe.financial.model.Currency;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.Inventory;
import com.mpe.financial.model.Item;
import com.mpe.financial.model.ItemUnit;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.Lending;
import com.mpe.financial.model.LendingDetail;
import com.mpe.financial.model.LendingDetailPK;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Salesman;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Warehouse;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.LendingDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.SalesmanDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.WarehouseDAO;
import com.mpe.financial.struts.form.LendingForm;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class LendingAction extends Action {
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
		LendingForm stockOpnameForm = (LendingForm) form;
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
				    if (stockOpnameForm.getString("subaction")!=null && stockOpnameForm.getString("subaction").equalsIgnoreCase("refresh")) {
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
		LendingForm form = (LendingForm) actionForm;
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
			Criteria criteria = LendingDAO.getInstance().getSession().createCriteria(Lending.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getCalendar("fromLendingDate")!=null)criteria.add(Restrictions.ge("LendingDate", new Date(form.getCalendar("fromLendingDate").getTime().getTime())));
			if (form.getCalendar("toLendingDate")!=null)criteria.add(Restrictions.le("LendingDate", new Date(form.getCalendar("toLendingDate").getTime().getTime())));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = LendingDAO.getInstance().getSession().createCriteria(Lending.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getCalendar("fromLendingDate")!=null)criteria.add(Restrictions.ge("LendingDate", new Date(form.getCalendar("fromLendingDate").getTime().getTime())));
			if (form.getCalendar("toLendingDate")!=null)criteria.add(Restrictions.le("LendingDate", new Date(form.getCalendar("toLendingDate").getTime().getTime())));
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
			request.setAttribute("LENDING",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("lending");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				LendingDAO.getInstance().closeSessionForReal();
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
		LendingForm form = (LendingForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
	    if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
		      Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class).add(Restrictions.eq("Code", new String(form.getString("itemCode")))).uniqueResult();
		      form.setString("itemId", item.getId());
		  }
			// remove
			Lending obj = (Lending)httpSession.getAttribute("lending");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEDETAIL")) {
				LendingDetail removeLendingDetail = null;
				Iterator iterator = obj.getLendingDetails().iterator();
				while (iterator.hasNext()) {
				    LendingDetail lendingDetail = (LendingDetail)iterator.next();
					if (form.getLong("itemId") == lendingDetail.getId().getItem().getId()) {
					    removeLendingDetail = lendingDetail;
					}
				}
				if (removeLendingDetail!=null) {
					Set set = obj.getLendingDetails();
					set.remove(removeLendingDetail);
					obj.setLendingDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("lending", obj);
			}
			// relationships
			List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("locationLst", locationLst);
/*			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Store", Boolean.TRUE)).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerLst", customerLst);*/
			List warehouseLst = null;
			if (form.getLong("locationId")>0) {
			    warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.isNull("Customer.Id"))
						.add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId")))).list();
			} else if (form.getLong("customerId")>0) {
			    warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
						.add(Restrictions.isNull("Location.Id")).list();
			} else {
			    warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.isNull("Customer.Id"))
					.add(Restrictions.isNull("Location.Id")).list();
			}
			request.setAttribute("warehouseLst", warehouseLst);
/*			List itemLst = ItemDAO.getInstance().getSession().createCriteria(Item.class)
				.add(Restrictions.eq("Type", new String("Product"))).addOrder(Order.asc("Name")).list();
			request.setAttribute("itemLst", itemLst);*/
			List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemUnitLst", itemUnitLst);
			/*List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("currencyLst", currencyLst);*/
			List salesmanLst = SalesmanDAO.getInstance().findAll(Order.asc("FullName"));
			request.setAttribute("salesmanLst", salesmanLst);
			if (form.getLong("lendingId") == 0) {
				form.setString("lendingId",0);
				form.setString("number", RunningNumberDAO.getInstance().getLendingNumber());
				form.setCurentCalendar("lendingDate");
				form.setCurentTimestamp("createOn");
				if (obj!=null) {
				    form.setString("description",obj.getDescription());
						form.setString("number",obj.getNumber());
						//form.setString("warehouseId",obj.getWarehouse()!=null?obj.getWarehouse().getId():0);
						form.setCalendar("lendingDate",obj.getLendingDate());
						form.setCalendar("returDate",obj.getReturDate());
						//form.setTime("outTime",obj.getOutTime());
						//form.setTime("returTime",obj.getReturTime());
						//form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
						//form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
						//form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
						form.setString("description",obj.getDescription());
						form.setString("salesmanId",obj.getSalesman()!=null?obj.getSalesman().getId():0);
						Set lendingDetailLst = obj.getLendingDetails();
						request.setAttribute("lendingDetailLst", lendingDetailLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
		    if (obj==null) {
		        obj = LendingDAO.getInstance().get(form.getLong("lendingId"));
		        httpSession.setAttribute("lending",obj);
		    }
				form.setString("lendingId",obj.getId());
				form.setString("number",obj.getNumber());
				//form.setString("warehouseId",obj.getWarehouse()!=null?obj.getWarehouse().getId():0);
				form.setCalendar("lendingDate",obj.getLendingDate());
				form.setCalendar("returDate",obj.getReturDate());
				//form.setTime("outTime",obj.getOutTime());
				//form.setTime("returTime",obj.getReturTime());
				//form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				//form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				//form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("description",obj.getDescription());
				form.setString("salesmanId",obj.getSalesman()!=null?obj.getSalesman().getId():0);
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set lendingDetailLst = obj.getLendingDetails();
				request.setAttribute("lendingDetailLst", lendingDetailLst);
			}
			if (form.getLong("itemId") > 0) {
				boolean isNewData = true;
				if (obj!=null && obj.getLendingDetails()!=null) {
					Iterator iterator = obj.getLendingDetails().iterator();
					while (iterator.hasNext()) {
					  LendingDetail lendingDetail = (LendingDetail)iterator.next();
						if (form.getLong("itemId") == lendingDetail.getId().getItem().getId()) {
							isNewData = false;
							form.setString("itemCode", lendingDetail.getId().getItem().getCode());
							form.setString("lendingQuantity", lendingDetail.getLendingQuantity());
							form.setString("returQuantity", lendingDetail.getReturQuantity());
							form.setString("itemDescription", lendingDetail.getDescription());
							form.setString("itemUnitId", lendingDetail.getItemUnit()!=null?lendingDetail.getItemUnit().getId():0);
						}
					}
				}
				if (isNewData) {
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					if (inventory!=null) {
				    /*Set set = inventory.getInventoryWarehouses();
					  Iterator iterator = set.iterator();
					  boolean a = true;
					  while (iterator.hasNext()) {
					      InventoryWarehouse inventoryWarehouse = (InventoryWarehouse)iterator.next();
					      if (inventoryWarehouse.getId().getWarehouse().getId() == form.getLong("warehouseId")) {
					          form.setString("previousQuantity", inventoryWarehouse.getQuantity());
					          a = false;
					      }
					  }
					  if (a) form.setString("previousQuantity", "");*/
						form.setString("itemUnitId", inventory.getItemUnit()!=null?inventory.getItemUnit().getId():0);
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
					List warehouseLst = null;
					if (form.getLong("locationId")>0) {
					    warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
								.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
								.add(Restrictions.isNull("Customer.Id"))
								.add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId")))).list();
					} else if (form.getLong("customerId")>0) {
					    warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
								.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
								.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
								.add(Restrictions.isNull("Location.Id")).list();
					} else {
					    warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.isNull("Customer.Id"))
							.add(Restrictions.isNull("Location.Id")).list();
					}
					request.setAttribute("warehouseLst", warehouseLst);
/*					List itemLst = ItemDAO.getInstance().getSession().createCriteria(Item.class)
						.add(Restrictions.eq("Type", new String("Product"))).addOrder(Order.asc("Name")).list();
					request.setAttribute("itemLst", itemLst);*/
					List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("itemUnitLst", itemUnitLst);
					List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("currencyLst", currencyLst);
					List salesmanLst = SalesmanDAO.getInstance().findAll(Order.asc("FullName"));
					request.setAttribute("salesmanLst", salesmanLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				LendingDAO.getInstance().closeSessionForReal();
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
		LendingForm form = (LendingForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = LendingDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("lending");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Lending obj = (Lending)httpSession.getAttribute("lending");
			if (form.getLong("lendingId") == 0) {
				obj = (Lending)LendingDAO.getInstance().getSession().createCriteria(Lending.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (Lending)httpSession.getAttribute("lending");
					if (obj==null) obj = new Lending();
					obj.setDescription(form.getString("description"));
					obj.setOrganization(users.getOrganization());
					obj.setNumber(form.getString("number"));
					obj.setDescription(form.getString("description"));
					obj.setOrganization(users.getOrganization());
					//Warehouse warehouse = WarehouseDAO.getInstance().get(form.getLong("warehouseId"));
					//obj.setWarehouse(warehouse);
					//Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					//obj.setCurrency(organizationSetup.getDefaultCurrency());
					//Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					//Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					//obj.setLocation(location);
					//obj.setCustomer(customers);
					obj.setLendingDate(form.getCalendar("lendingDate")!=null?form.getCalendar("lendingDate").getTime():null);
					obj.setReturDate(form.getCalendar("returDate")!=null?form.getCalendar("returDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					Salesman salesman = SalesmanDAO.getInstance().get(form.getLong("salesmanId"));
					obj.setSalesman(salesman);
					//obj.setPosted(false);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					// create journal
/*					Journal journal = new Journal();
					journal.setLending(obj);
					journal.setCurrency(obj.getCurrency());
					//journal.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("lendingDate")));
					journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("lendingDate"))));
					journal.setJournalDate(obj.getLendingDate());
					journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
					journal.setOrganization(users.getOrganization());
					journal.setPosted(false);
					//journal.setReference(form.getString("reference"));
					journal.setCreateBy(users);
					journal.setCreateOn(form.getTimestamp("createOn"));
					// journal detail
					Set set = journal.getJournalDetails();
					if (set==null) set = new LinkedHashSet();
					// credit
					JournalDetail journalDetail = new JournalDetail();
					JournalDetailPK journalDetailPK = new JournalDetailPK();
					journalDetailPK.setChartOfAccount(organizationSetup.getInventoryAccount());
					journalDetailPK.setJournal(journal);
					journalDetail.setId(journalDetailPK);
					journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==false?obj.getLendingDetailAmount():-obj.getLendingDetailAmount());
					set.add(journalDetail);
					// debit
					JournalDetail journalDetail3 = new JournalDetail();
					JournalDetailPK journalDetailPK3 = new JournalDetailPK();
					journalDetailPK3.setJournal(journal);
					journalDetailPK3.setChartOfAccount(organizationSetup.getLendingAccount());
					journalDetail3.setAmount(organizationSetup.getLendingAccount().isDebit()==true?obj.getLendingDetailAmount():-obj.getLendingDetailAmount());
					journalDetail3.setId(journalDetailPK3);
					set.add(journalDetail3);
					journal.setJournalDetails(set);
					obj.setJournal(journal);*/
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = LendingDAO.getInstance().get(form.getLong("lendingId"));
				obj.setDescription(form.getString("description"));
				obj.setOrganization(users.getOrganization());
				obj.setNumber(form.getString("number"));
				obj.setDescription(form.getString("description"));
				//obj.setOutTime(form.getTime("outTime"));
				//obj.setReturTime(form.getTime("returTime"));
				obj.setOrganization(users.getOrganization());
				//Warehouse warehouse = WarehouseDAO.getInstance().get(form.getLong("warehouseId"));
				//obj.setWarehouse(warehouse);
				//Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				//obj.setCurrency(currency);
				//obj.setCurrency(organizationSetup.getDefaultCurrency());
				//Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				Salesman salesman = SalesmanDAO.getInstance().get(form.getLong("salesmanId"));
				obj.setSalesman(salesman);
				//obj.setCustomer(customers);
				obj.setLendingDate(form.getCalendar("lendingDate")!=null?form.getCalendar("lendingDate").getTime():null);
				obj.setReturDate(form.getCalendar("returDate")!=null?form.getCalendar("returDate").getTime():null);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				// create journal
/*				Journal journal = obj.getJournal();
				journal.setLending(obj);
				journal.setCurrency(obj.getCurrency());
				//journal.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup));
				journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("lendingDate"))));
				journal.setJournalDate(obj.getLendingDate());
				//journal.setNumber(RunningNumberDAO.getInstance().getJournalNumber());
				journal.setOrganization(users.getOrganization());
				//journal.setPosted(false);
				journal.setCreateBy(createBy);
				journal.setCreateOn(form.getTimestamp("createOn"));
				journal.setChangeBy(users);
				journal.setChangeOn(form.getTimestamp("changeOn"));
				// journal detail
				journal.getJournalDetails().removeAll(journal.getJournalDetails());
				Set set = journal.getJournalDetails();
				// credit
				JournalDetail journalDetail = new JournalDetail();
				JournalDetailPK journalDetailPK = new JournalDetailPK();
				journalDetailPK.setChartOfAccount(organizationSetup.getInventoryAccount());
				journalDetailPK.setJournal(journal);
				journalDetail.setId(journalDetailPK);
				journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==false?obj.getLendingDetailAmount():-obj.getLendingDetailAmount());
				set.add(journalDetail);
				// debit
				JournalDetail journalDetail3 = new JournalDetail();
				JournalDetailPK journalDetailPK3 = new JournalDetailPK();
				journalDetailPK3.setJournal(journal);
				journalDetailPK3.setChartOfAccount(organizationSetup.getLendingAccount());
				journalDetail3.setAmount(organizationSetup.getLendingAccount().isDebit()==true?obj.getLendingDetailAmount():-obj.getLendingDetailAmount());
				journalDetail3.setId(journalDetailPK3);
				set.add(journalDetail3);
				journal.setJournalDetails(set);
				obj.setJournal(journal);*/
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("lendingQuantity")>0) {
				  LendingDetail lendingDetail = new LendingDetail();
					Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					lendingDetail.setItemUnit(itemUnit);
					LendingDetailPK lendingDetailPK = new LendingDetailPK();
					lendingDetailPK.setItem(item);
					lendingDetailPK.setLending(obj);
					lendingDetail.setId(lendingDetailPK);
					lendingDetail.setLendingQuantity(form.getDouble("lendingQuantity"));
					lendingDetail.setReturQuantity(form.getDouble("returQuantity"));
					//lendingDetail.setPrice(form.getDouble("price"));
					lendingDetail.setDescription(form.getString("itemDescription"));
					//lendingDetail.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(item.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("lendingDate")));
					//lendingDetail.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(item.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("lendingDate"))));
					//lendingDetail.setCurrency(item.getCurrency());
					Set set = obj.getLendingDetails();
					if (set==null) set = new LinkedHashSet();
					LendingDetail removeLendingDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						LendingDetail lendingDetail2 = (LendingDetail)iterator.next();
						if (form.getLong("itemId")==lendingDetail2.getId().getItem().getId()) {
						    removeLendingDetail = lendingDetail2;
						}
					}
					if (removeLendingDetail!=null) {
						set.remove(removeLendingDetail);
						set.add(lendingDetail);
					} else {
						set.add(lendingDetail);
					}
					obj.setLendingDetails(set);
					// netral
					form.setString("itemId", "");
					form.setString("lendingQuantity", "");
					form.setString("returQuantity", "");
					form.setString("itemUnitId", "");
					form.setString("price", "");
					form.setString("itemDescription", "");
				}
				// netral
				form.setString("itemId", "");
				form.setString("lendingQuantity", "");
				form.setString("returQuantity", "");
				form.setString("itemUnitId", "");
				form.setString("price", "");
				form.setString("itemDescription", "");
			}
			// save to session
			httpSession.setAttribute("lending", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateLendingNumber(session);
					//RunningNumberDAO.getInstance().updateJournalNumber(session);
/*					Warehouse warehouse = null;
					if (form.getLong("locationId")>0) {
					    warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class).add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId")))).setMaxResults(1).uniqueResult();
					} else {
					    warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class).add(Restrictions.isNull("Location")).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).setMaxResults(1).uniqueResult();
					}
					InventoryWarehouseDAO.getInstance().updateInventoryWarehouseFromLending(obj, null, warehouse, session);*/
					LendingDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					transaction = session.beginTransaction();
					LendingDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("lending");
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
						List warehouseLst = null;
						if (form.getLong("locationId")>0) {
						    warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
									.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
									.add(Restrictions.isNull("Customer.Id"))
									.add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId")))).list();
						} else if (form.getLong("customerId")>0) {
						    warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
									.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
									.add(Restrictions.eq("Customer.Id", new Long(form.getLong("customerId"))))
									.add(Restrictions.isNull("Location.Id")).list();
						} else {
						    warehouseLst = WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class)
								.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
								.add(Restrictions.isNull("Customer.Id"))
								.add(Restrictions.isNull("Location.Id")).list();
						}
						request.setAttribute("warehouseLst", warehouseLst);
/*						List itemLst = ItemDAO.getInstance().getSession().createCriteria(Item.class)
							.add(Restrictions.eq("Type", new String("Product"))).addOrder(Order.asc("Name")).list();
						request.setAttribute("itemLst", itemLst);*/
						List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
						request.setAttribute("itemUnitLst", itemUnitLst);
						List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
							.addOrder(Order.asc("Name")).list();
						request.setAttribute("currencyLst", currencyLst);
						List salesmanLst = SalesmanDAO.getInstance().findAll(Order.asc("FullName"));
						request.setAttribute("salesmanLst", salesmanLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				LendingDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?lendingId="+form.getLong("lendingId"));
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
		LendingForm form = (LendingForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			Lending lending = LendingDAO.getInstance().get(form.getLong("lendingId"));
			request.setAttribute("lending", lending);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				LendingDAO.getInstance().closeSessionForReal();
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
		LendingForm form = (LendingForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			LendingDAO.getInstance().delete(form.getLong("lendingId"));
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				LendingDAO.getInstance().closeSessionForReal();
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