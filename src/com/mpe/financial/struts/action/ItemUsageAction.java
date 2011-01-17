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
import com.mpe.financial.model.ItemUsage;
import com.mpe.financial.model.ItemUsageDetail;
import com.mpe.financial.model.ItemUsageDetailPK;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Warehouse;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.ItemUsageDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.WarehouseDAO;
import com.mpe.financial.struts.form.ItemUsageForm;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class ItemUsageAction extends Action {
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
		ItemUsageForm stockOpnameForm = (ItemUsageForm) form;
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
		ItemUsageForm form = (ItemUsageForm) actionForm;
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
			Criteria criteria = ItemUsageDAO.getInstance().getSession().createCriteria(ItemUsage.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getCalendar("fromUsageDate")!=null)criteria.add(Restrictions.ge("UsageDate", new Date(form.getCalendar("fromUsageDate").getTime().getTime())));
			if (form.getCalendar("toUsageDate")!=null)criteria.add(Restrictions.le("UsageDate", new Date(form.getCalendar("toUsageDate").getTime().getTime())));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = ItemUsageDAO.getInstance().getSession().createCriteria(ItemUsage.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getCalendar("fromUsageDate")!=null)criteria.add(Restrictions.ge("UsageDate", new Date(form.getCalendar("fromUsageDate").getTime().getTime())));
			if (form.getCalendar("toUsageDate")!=null)criteria.add(Restrictions.le("UsageDate", new Date(form.getCalendar("toUsageDate").getTime().getTime())));
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
			request.setAttribute("ITEMUSAGE",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("itemUsage");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemUsageDAO.getInstance().closeSessionForReal();
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
		ItemUsageForm form = (ItemUsageForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
	    if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
		      Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class).add(Restrictions.eq("Code", new String(form.getString("itemCode")))).uniqueResult();
		      form.setString("itemId", item.getId());
		  }
			// remove
			ItemUsage obj = (ItemUsage)httpSession.getAttribute("itemUsage");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVESTOCKOPNAMEDETAIL")) {
				ItemUsageDetail removeItemUsageDetail = null;
				Iterator iterator = obj.getItemUsageDetails().iterator();
				while (iterator.hasNext()) {
				    ItemUsageDetail itemUsageDetail = (ItemUsageDetail)iterator.next();
					if (form.getLong("itemId") == itemUsageDetail.getId().getItem().getId()) {
					    removeItemUsageDetail = itemUsageDetail;
					}
				}
				if (removeItemUsageDetail!=null) {
					Set set = obj.getItemUsageDetails();
					set.remove(removeItemUsageDetail);
					obj.setItemUsageDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("itemUsage", obj);
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
			if (form.getLong("itemUsageId") == 0) {
				form.setString("itemUsageId",0);
				form.setString("number", RunningNumberDAO.getInstance().getItemUsageNumber());
				form.setCurentCalendar("usageDate");
				form.setCurentTimestamp("createOn");
				if (obj!=null) {
				    form.setString("description",obj.getDescription());
						form.setString("number",obj.getNumber());
						form.setString("warehouseId",obj.getWarehouse()!=null?obj.getWarehouse().getId():0);
						form.setCalendar("usageDate",obj.getUsageDate());
						form.setTime("outTime",obj.getOutTime());
						form.setTime("returTime",obj.getReturTime());
						form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
						//form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
						form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
						Set itemUsageDetailLst = obj.getItemUsageDetails();
						request.setAttribute("itemUsageDetailLst", itemUsageDetailLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
		    if (obj==null) {
		        obj = ItemUsageDAO.getInstance().get(form.getLong("itemUsageId"));
		        httpSession.setAttribute("itemUsage",obj);
		    }
				form.setString("itemUsageId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("number",obj.getNumber());
				form.setString("warehouseId",obj.getWarehouse()!=null?obj.getWarehouse().getId():0);
				form.setCalendar("usageDate",obj.getUsageDate());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				//form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set itemUsageDetailLst = obj.getItemUsageDetails();
				request.setAttribute("itemUsageDetailLst", itemUsageDetailLst);
			}
			if (form.getLong("itemId") > 0) {
				boolean isNewData = true;
				if (obj!=null && obj.getItemUsageDetails()!=null) {
					Iterator iterator = obj.getItemUsageDetails().iterator();
					while (iterator.hasNext()) {
					  ItemUsageDetail itemUsageDetail = (ItemUsageDetail)iterator.next();
						if (form.getLong("itemId") == itemUsageDetail.getId().getItem().getId()) {
							isNewData = false;
							form.setString("itemCode", itemUsageDetail.getId().getItem().getCode());
							form.setString("outQuantity", itemUsageDetail.getOutQuantity());
							form.setString("returQuantity", itemUsageDetail.getReturQuantity());
							form.setString("exchangeRate", itemUsageDetail.getExchangeRate());
							form.setString("note", itemUsageDetail.getNote());
							form.setString("itemUnitId", itemUsageDetail.getItemUnit()!=null?itemUsageDetail.getItemUnit().getId():0);
							form.setString("price", Formater.getFormatedOutputForm(itemUsageDetail.getPrice()));
							form.setString("itemUsageDetailCurrencyId", itemUsageDetail.getCurrency()!=null?itemUsageDetail.getCurrency().getId():0);
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
						form.setString("price", Formater.getFormatedOutputForm(inventory.getCostPrice()));
						form.setString("itemUsageDetailCurrencyId", inventory.getCurrency()!=null?inventory.getCurrency().getId():0);
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
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				ItemUsageDAO.getInstance().closeSessionForReal();
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
		ItemUsageForm form = (ItemUsageForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = ItemUsageDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("itemUsage");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			ItemUsage obj = (ItemUsage)httpSession.getAttribute("itemUsage");
			if (form.getLong("itemUsageId") == 0) {
				obj = (ItemUsage)ItemUsageDAO.getInstance().getSession().createCriteria(ItemUsage.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (ItemUsage)httpSession.getAttribute("itemUsage");
					if (obj==null) obj = new ItemUsage();
					obj.setDescription(form.getString("description"));
					obj.setOrganization(users.getOrganization());
					obj.setNumber(form.getString("number"));
					obj.setOutTime(form.getTime("outTime"));
					obj.setReturTime(form.getTime("returTime"));
					obj.setOrganization(users.getOrganization());
					Warehouse warehouse = WarehouseDAO.getInstance().get(form.getLong("warehouseId"));
					obj.setWarehouse(warehouse);
					//Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(organizationSetup.getDefaultCurrency());
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					//Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setLocation(location);
					//obj.setCustomer(customers);
					obj.setUsageDate(form.getCalendar("usageDate")!=null?form.getCalendar("usageDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					//obj.setPosted(false);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					// create journal
/*					Journal journal = new Journal();
					journal.setItemUsage(obj);
					journal.setCurrency(obj.getCurrency());
					//journal.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("usageDate")));
					journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("usageDate"))));
					journal.setJournalDate(obj.getUsageDate());
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
					journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==false?obj.getItemUsageDetailAmount():-obj.getItemUsageDetailAmount());
					set.add(journalDetail);
					// debit
					JournalDetail journalDetail3 = new JournalDetail();
					JournalDetailPK journalDetailPK3 = new JournalDetailPK();
					journalDetailPK3.setJournal(journal);
					journalDetailPK3.setChartOfAccount(organizationSetup.getItemUsageAccount());
					journalDetail3.setAmount(organizationSetup.getItemUsageAccount().isDebit()==true?obj.getItemUsageDetailAmount():-obj.getItemUsageDetailAmount());
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
				if (obj==null) obj = ItemUsageDAO.getInstance().get(form.getLong("itemUsageId"));
				obj.setDescription(form.getString("description"));
				obj.setOrganization(users.getOrganization());
				obj.setNumber(form.getString("number"));
				obj.setOutTime(form.getTime("outTime"));
				obj.setReturTime(form.getTime("returTime"));
				obj.setOrganization(users.getOrganization());
				Warehouse warehouse = WarehouseDAO.getInstance().get(form.getLong("warehouseId"));
				obj.setWarehouse(warehouse);
				//Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				//obj.setCurrency(currency);
				obj.setCurrency(organizationSetup.getDefaultCurrency());
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				//Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setLocation(location);
				//obj.setCustomer(customers);
				obj.setUsageDate(form.getCalendar("usageDate")!=null?form.getCalendar("usageDate").getTime():null);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				// create journal
/*				Journal journal = obj.getJournal();
				journal.setItemUsage(obj);
				journal.setCurrency(obj.getCurrency());
				//journal.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup));
				journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("usageDate"))));
				journal.setJournalDate(obj.getUsageDate());
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
				journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==false?obj.getItemUsageDetailAmount():-obj.getItemUsageDetailAmount());
				set.add(journalDetail);
				// debit
				JournalDetail journalDetail3 = new JournalDetail();
				JournalDetailPK journalDetailPK3 = new JournalDetailPK();
				journalDetailPK3.setJournal(journal);
				journalDetailPK3.setChartOfAccount(organizationSetup.getItemUsageAccount());
				journalDetail3.setAmount(organizationSetup.getItemUsageAccount().isDebit()==true?obj.getItemUsageDetailAmount():-obj.getItemUsageDetailAmount());
				journalDetail3.setId(journalDetailPK3);
				set.add(journalDetail3);
				journal.setJournalDetails(set);
				obj.setJournal(journal);*/
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDSTOCKOPNAMEDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("outQuantity")>0) {
				  ItemUsageDetail itemUsageDetail = new ItemUsageDetail();
					Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					itemUsageDetail.setItemUnit(itemUnit);
					ItemUsageDetailPK itemUsageDetailPK = new ItemUsageDetailPK();
					itemUsageDetailPK.setItem(item);
					itemUsageDetailPK.setItemUsage(obj);
					itemUsageDetail.setId(itemUsageDetailPK);
					itemUsageDetail.setOutQuantity(form.getDouble("outQuantity"));
					itemUsageDetail.setReturQuantity(form.getDouble("returQuantity"));
					itemUsageDetail.setPrice(form.getDouble("price"));
					itemUsageDetail.setNote(form.getString("note"));
					//itemUsageDetail.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(item.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("usageDate")));
					itemUsageDetail.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(item.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("usageDate"))));
					itemUsageDetail.setCurrency(item.getCurrency());
					Set set = obj.getItemUsageDetails();
					if (set==null) set = new LinkedHashSet();
					ItemUsageDetail removeItemUsageDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						ItemUsageDetail itemUsageDetail2 = (ItemUsageDetail)iterator.next();
						if (form.getLong("itemId")==itemUsageDetail2.getId().getItem().getId()) {
						    removeItemUsageDetail = itemUsageDetail2;
						}
					}
					if (removeItemUsageDetail!=null) {
						set.remove(removeItemUsageDetail);
						set.add(itemUsageDetail);
					} else {
						set.add(itemUsageDetail);
					}
					obj.setItemUsageDetails(set);
					// netral
					form.setString("itemId", "");
					form.setString("outQuantity", "");
					form.setString("returQuantity", "");
					form.setString("itemUnitId", "");
					form.setString("price", "");
					form.setString("note", "");
				}
				// netral
				form.setString("itemId", "");
				form.setString("outQuantity", "");
				form.setString("returQuantity", "");
				form.setString("itemUnitId", "");
				form.setString("price", "");
				form.setString("note", "");
			}
			// save to session
			httpSession.setAttribute("itemUsage", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateItemUsageNumber(session);
					//RunningNumberDAO.getInstance().updateJournalNumber(session);
/*					Warehouse warehouse = null;
					if (form.getLong("locationId")>0) {
					    warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class).add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId")))).setMaxResults(1).uniqueResult();
					} else {
					    warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class).add(Restrictions.isNull("Location")).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).setMaxResults(1).uniqueResult();
					}
					InventoryWarehouseDAO.getInstance().updateInventoryWarehouseFromItemUsage(obj, null, warehouse, session);*/
					ItemUsageDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					transaction = session.beginTransaction();
					ItemUsageDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("itemUsage");
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
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				ItemUsageDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?itemUsageId="+form.getLong("itemUsageId"));
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
		ItemUsageForm form = (ItemUsageForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			ItemUsage itemUsage = ItemUsageDAO.getInstance().get(form.getLong("itemUsageId"));
			request.setAttribute("itemUsage", itemUsage);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				ItemUsageDAO.getInstance().closeSessionForReal();
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
		ItemUsageForm form = (ItemUsageForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			ItemUsageDAO.getInstance().delete(form.getLong("itemUsageId"));
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				ItemUsageDAO.getInstance().closeSessionForReal();
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