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
import org.hibernate.Hibernate;
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
import com.mpe.financial.model.Journal;
import com.mpe.financial.model.JournalDetail;
import com.mpe.financial.model.JournalDetailPK;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.StockOpname;
import com.mpe.financial.model.StockOpnameDetail;
import com.mpe.financial.model.StockOpnameDetailPK;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.StockOpnameType;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Warehouse;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.StockOpnameDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.StockOpnameTypeDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.WarehouseDAO;
import com.mpe.financial.struts.form.StockOpnameForm;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class StockOpnameAction extends Action {
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
		StockOpnameForm stockOpnameForm = (StockOpnameForm) form;
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
		StockOpnameForm form = (StockOpnameForm) actionForm;
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
			Criteria criteria = StockOpnameDAO.getInstance().getSession().createCriteria(StockOpname.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getCalendar("fromStockOpnameDate")!=null)criteria.add(Restrictions.ge("StockOpnameDate", new Date(form.getCalendar("fromStockOpnameDate").getTime().getTime())));
			if (form.getCalendar("toStockOpnameDate")!=null)criteria.add(Restrictions.le("StockOpnameDate", new Date(form.getCalendar("toStockOpnameDate").getTime().getTime())));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = StockOpnameDAO.getInstance().getSession().createCriteria(StockOpname.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getCalendar("fromStockOpnameDate")!=null)criteria.add(Restrictions.ge("StockOpnameDate", new Date(form.getCalendar("fromStockOpnameDate").getTime().getTime())));
			if (form.getCalendar("toStockOpnameDate")!=null)criteria.add(Restrictions.le("StockOpnameDate", new Date(form.getCalendar("toStockOpnameDate").getTime().getTime())));
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
			request.setAttribute("STOCKOPNAME",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("stockOpname");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				StockOpnameDAO.getInstance().closeSessionForReal();
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
		StockOpnameForm form = (StockOpnameForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
		      Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class).add(Restrictions.eq("Code", new String(form.getString("itemCode")))).uniqueResult();
		      form.setString("itemId", item.getId());
			}
			// remove
			StockOpname obj = (StockOpname)httpSession.getAttribute("stockOpname");
			if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVESTOCKOPNAMEDETAIL")) {
				StockOpnameDetail removeStockOpnameDetail = null;
				Iterator iterator = obj.getStockOpnameDetails().iterator();
				while (iterator.hasNext()) {
				    StockOpnameDetail stockOpnameDetail = (StockOpnameDetail)iterator.next();
					if (form.getLong("itemId") == stockOpnameDetail.getId().getItem().getId()) {
					    removeStockOpnameDetail = stockOpnameDetail;
					}
				}
				if (removeStockOpnameDetail!=null) {
					Set set = obj.getStockOpnameDetails();
					set.remove(removeStockOpnameDetail);
					obj.setStockOpnameDetails(set);
				}
				form.setString("subaction", "");
				form.setString("itemId", "");
				httpSession.setAttribute("stockOpname", obj);
			}
			// relationships
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
/*			List itemLst = ItemDAO.getInstance().getSession().createCriteria(Item.class)
				.add(Restrictions.eq("Type", new String("Product"))).addOrder(Order.asc("Name")).list();
			request.setAttribute("itemLst", itemLst);*/
			List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemUnitLst", itemUnitLst);
			List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("currencyLst", currencyLst);
			List stockOpnameTypeLst = StockOpnameTypeDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("stockOpnameTypeLst", stockOpnameTypeLst);
			if (form.getLong("stockOpnameId") == 0) {
				form.setString("stockOpnameId",0);
				form.setString("number", RunningNumberDAO.getInstance().getStockOpnameNumber());
				form.setCurentCalendar("stockOpnameDate");
				form.setCurentTimestamp("createOn");
				if (obj!=null) {
				    form.setString("note",obj.getNote());
						form.setString("number",obj.getNumber());
						form.setString("warehouseId",obj.getWarehouse()!=null?obj.getWarehouse().getId():0);
						form.setString("stockOpnameTypeId",obj.getStockOpnameType()!=null?obj.getStockOpnameType().getId():0);
						form.setCalendar("stockOpnameDate",obj.getStockOpnameDate());
						form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
						form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
						form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
						Set stockOpnameDetailLst = obj.getStockOpnameDetails();
						request.setAttribute("stockOpnameDetailLst", stockOpnameDetailLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
		    if (obj==null) {
		        obj = StockOpnameDAO.getInstance().get(form.getLong("stockOpnameId"));
		        httpSession.setAttribute("stockOpname",obj);
		    }
				form.setString("stockOpnameId",obj.getId());
				form.setString("note",obj.getNote());
				form.setString("number",obj.getNumber());
				form.setString("warehouseId",obj.getWarehouse()!=null?obj.getWarehouse().getId():0);
				form.setString("stockOpnameTypeId",obj.getStockOpnameType()!=null?obj.getStockOpnameType().getId():0);
				form.setCalendar("stockOpnameDate",obj.getStockOpnameDate());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("locationId",obj.getLocation()!=null?obj.getLocation().getId():0);
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set stockOpnameDetailLst = obj.getStockOpnameDetails();
				request.setAttribute("stockOpnameDetailLst", stockOpnameDetailLst);
			}
			if (form.getLong("itemId") > 0) {
				boolean isNewData = true;
				if (obj!=null && obj.getStockOpnameDetails()!=null) {
					Iterator iterator = obj.getStockOpnameDetails().iterator();
					while (iterator.hasNext()) {
					  StockOpnameDetail stockOpnameDetail = (StockOpnameDetail)iterator.next();
						if (form.getLong("itemId") == stockOpnameDetail.getId().getItem().getId()) {
							isNewData = false;
							form.setString("currentQuantity", stockOpnameDetail.getQuantity() + stockOpnameDetail.getDifference());
							form.setString("previousQuantity", stockOpnameDetail.getQuantity());
							form.setString("exchangeRate", stockOpnameDetail.getExchangeRate());
							form.setString("itemUnitId", stockOpnameDetail.getItemUnit()!=null?stockOpnameDetail.getItemUnit().getId():0);
							form.setString("price", Formater.getFormatedOutputForm(stockOpnameDetail.getPrice()));
							form.setString("stockOpnameDetailCurrencyId", stockOpnameDetail.getCurrency()!=null?stockOpnameDetail.getCurrency().getId():0);
						}
					}
				}
				if (isNewData) {
					Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
					if (inventory!=null) {
						String x = "", y="", z="";
						if (form.getLong("locationId")>0) x = x + " and f.location_id="+form.getLong("locationId")+" ";
						if (form.getLong("warehouseId")>0) {
							y = y + " and e.warehouse_id="+form.getLong("warehouseId")+" ";
							z = z + " and f.warehouse_id="+form.getLong("warehouseId")+" ";
						}
						String sql = "" +
							"select ifnull(" +
							"(select sum(e.quantity) from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date < :toDate and f.status<>'CANCEL' "+x+y+" )" +
							" + " +
							"(select sum(-e.quantity) from delivery_order_detail e join delivery_order f on e.delivery_order_id=f.delivery_order_id and f.is_rekap='N' and f.is_bon_kuning='N' and f.delivery_date >= :fromDate and f.delivery_date < :toDate and f.status<>'CANCEL' "+x+y+" )" +
							" + " +
							"(select sum(-e.quantity) from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' "+x+y+" )" +
							" + " +
							"(select sum(e.quantity) from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' "+x+y+" )" +
							" + " +
							"(select sum(-e.difference) from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date < :toDate and f.status<>'CANCEL' "+x+z+" )" +
							"), 0) as Quantity ";
						double f = ((Double)InventoryDAO.getInstance().getSession().createSQLQuery(sql).addScalar("Quantity", Hibernate.DOUBLE)
								.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
								.setDate("toDate", new Date(form.getCalendar("stockOpnameDate").getTime().getTime()))
								.setMaxResults(1).uniqueResult()).doubleValue();
						
						form.setString("previousQuantity", f);
						form.setString("itemUnitId", inventory.getItemUnit()!=null?inventory.getItemUnit().getId():0);
						form.setString("price", Formater.getFormatedOutputForm(inventory.getCostPrice()));
						form.setString("stockOpnameDetailCurrencyId", inventory.getCurrency()!=null?inventory.getCurrency().getId():0);
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
				List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("itemUnitLst", itemUnitLst);
				List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("currencyLst", currencyLst);
				List stockOpnameTypeLst = StockOpnameTypeDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("stockOpnameTypeLst", stockOpnameTypeLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				StockOpnameDAO.getInstance().closeSessionForReal();
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
		StockOpnameForm form = (StockOpnameForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Session session = StockOpnameDAO.getInstance().getSession();
		Transaction transaction = null;
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("stockOpname");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			StockOpname obj = (StockOpname)httpSession.getAttribute("stockOpname");
			if (form.getLong("stockOpnameId") == 0) {
				obj = (StockOpname)StockOpnameDAO.getInstance().getSession().createCriteria(StockOpname.class).add(Restrictions.eq("Number", form.getString("number"))).uniqueResult();
				if (obj==null) {
					obj = (StockOpname)httpSession.getAttribute("stockOpname");
					if (obj==null) obj = new StockOpname();
					obj.setNote(form.getString("note"));
					obj.setOrganization(users.getOrganization());
					obj.setNumber(form.getString("number"));
					obj.setOrganization(users.getOrganization());
					Warehouse warehouse = WarehouseDAO.getInstance().get(form.getLong("warehouseId"));
					obj.setWarehouse(warehouse);
					StockOpnameType stockOpnameType = StockOpnameTypeDAO.getInstance().get(form.getLong("stockOpnameTypeId"));
					obj.setStockOpnameType(stockOpnameType);
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setLocation(location);
					obj.setCustomer(customers);
					obj.setStockOpnameDate(form.getCalendar("stockOpnameDate")!=null?form.getCalendar("stockOpnameDate").getTime():null);
					obj.setStatus(CommonConstants.OPEN);
					obj.setPosted(false);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					// create journal
					Journal journal = new Journal();
					journal.setStockOpname(obj);
					journal.setCurrency(obj.getCurrency());
					//journal.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("stockOpnameDate")));
					journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("stockOpnameDate"))));
					journal.setJournalDate(obj.getStockOpnameDate());
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
					journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==false?obj.getStockOpnameDetailAmount():-obj.getStockOpnameDetailAmount());
					set.add(journalDetail);
					// debit
					JournalDetail journalDetail3 = new JournalDetail();
					JournalDetailPK journalDetailPK3 = new JournalDetailPK();
					journalDetailPK3.setJournal(journal);
					journalDetailPK3.setChartOfAccount(organizationSetup.getStockOpnameAccount());
					journalDetail3.setAmount(organizationSetup.getStockOpnameAccount().isDebit()==true?obj.getStockOpnameDetailAmount():-obj.getStockOpnameDetailAmount());
					journalDetail3.setId(journalDetailPK3);
					set.add(journalDetail3);
					journal.setJournalDetails(set);
					obj.setJournal(journal);
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = StockOpnameDAO.getInstance().get(form.getLong("stockOpnameId"));
				obj.setNote(form.getString("note"));
				obj.setOrganization(users.getOrganization());
				obj.setNumber(form.getString("number"));
				obj.setOrganization(users.getOrganization());
				Warehouse warehouse = WarehouseDAO.getInstance().get(form.getLong("warehouseId"));
				obj.setWarehouse(warehouse);
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setLocation(location);
				obj.setCustomer(customers);
				StockOpnameType stockOpnameType = StockOpnameTypeDAO.getInstance().get(form.getLong("stockOpnameTypeId"));
				obj.setStockOpnameType(stockOpnameType);
				obj.setStockOpnameDate(form.getCalendar("stockOpnameDate")!=null?form.getCalendar("stockOpnameDate").getTime():null);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				// create journal
				Journal journal = obj.getJournal();
				journal.setStockOpname(obj);
				journal.setCurrency(obj.getCurrency());
				//journal.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup));
				journal.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(obj.getCurrency(), organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("stockOpnameDate"))));
				journal.setJournalDate(obj.getStockOpnameDate());
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
				journalDetail.setAmount(organizationSetup.getInventoryAccount().isDebit()==false?obj.getStockOpnameDetailAmount():-obj.getStockOpnameDetailAmount());
				set.add(journalDetail);
				// debit
				JournalDetail journalDetail3 = new JournalDetail();
				JournalDetailPK journalDetailPK3 = new JournalDetailPK();
				journalDetailPK3.setJournal(journal);
				journalDetailPK3.setChartOfAccount(organizationSetup.getStockOpnameAccount());
				journalDetail3.setAmount(organizationSetup.getStockOpnameAccount().isDebit()==true?obj.getStockOpnameDetailAmount():-obj.getStockOpnameDetailAmount());
				journalDetail3.setId(journalDetailPK3);
				set.add(journalDetail3);
				journal.setJournalDetails(set);
				obj.setJournal(journal);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDSTOCKOPNAMEDETAIL")) {
				if (form.getLong("itemId") >0 && form.getDouble("previousQuantity")>0 && form.getDouble("currentQuantity")>0) {
				  StockOpnameDetail stockOpnameDetail = new StockOpnameDetail();
					Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					stockOpnameDetail.setItemUnit(itemUnit);
					StockOpnameDetailPK stockOpnameDetailPK = new StockOpnameDetailPK();
					stockOpnameDetailPK.setItem(item);
					stockOpnameDetailPK.setStockOpname(obj);
					stockOpnameDetail.setId(stockOpnameDetailPK);
					stockOpnameDetail.setQuantity(form.getDouble("previousQuantity"));
					stockOpnameDetail.setDifference(form.getDouble("currentQuantity")-form.getDouble("previousQuantity"));
					stockOpnameDetail.setPrice(form.getDouble("price"));
					//stockOpnameDetail.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(item.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("stockOpnameDate")));
					stockOpnameDetail.setExchangeRate(Formater.getFormatedOutputResult(5, CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(item.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("stockOpnameDate"))));
					stockOpnameDetail.setCurrency(item.getCurrency());
					Set set = obj.getStockOpnameDetails();
					if (set==null) set = new LinkedHashSet();
					StockOpnameDetail removeStockOpnameDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						StockOpnameDetail stockOpnameDetail2 = (StockOpnameDetail)iterator.next();
						if (form.getLong("itemId")==stockOpnameDetail2.getId().getItem().getId()) {
						    removeStockOpnameDetail = stockOpnameDetail2;
						}
					}
					if (removeStockOpnameDetail!=null) {
						set.remove(removeStockOpnameDetail);
						set.add(stockOpnameDetail);
					} else {
						set.add(stockOpnameDetail);
					}
					obj.setStockOpnameDetails(set);
					// netral
					form.setString("itemId", "");
					form.setString("currentQuantity", "");
					form.setString("previousQuantity", "");
					form.setString("itemUnitId", "");
					form.setString("price", "");
				}
				// netral
				form.setString("itemId", "");
				form.setString("currentQuantity", "");
				form.setString("previousQuantity", "");
				form.setString("itemUnitId", "");
				form.setString("price", "");
			}
			// save to session
			httpSession.setAttribute("stockOpname", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateStockOpnameNumber(session);
					RunningNumberDAO.getInstance().updateJournalNumber(session);
/*					Warehouse warehouse = null;
					if (form.getLong("locationId")>0) {
					    warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class).add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId")))).setMaxResults(1).uniqueResult();
					} else {
					    warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class).add(Restrictions.isNull("Location")).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).setMaxResults(1).uniqueResult();
					}
					InventoryWarehouseDAO.getInstance().updateInventoryWarehouseFromStockOpname(obj, null, warehouse, session);*/
					StockOpnameDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					transaction = session.beginTransaction();
					StockOpnameDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("stockOpname");
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
						List stockOpnameTypeLst = StockOpnameTypeDAO.getInstance().findAll(Order.asc("Name"));
						request.setAttribute("stockOpnameTypeLst", stockOpnameTypeLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				StockOpnameDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?stockOpnameId="+form.getLong("stockOpnameId"));
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
		StockOpnameForm form = (StockOpnameForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			StockOpname stockOpname = StockOpnameDAO.getInstance().get(form.getLong("stockOpnameId"));
			request.setAttribute("stockOpname", stockOpname);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				StockOpnameDAO.getInstance().closeSessionForReal();
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
		StockOpnameForm form = (StockOpnameForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			StockOpnameDAO.getInstance().delete(form.getLong("stockOpnameId"));
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				StockOpnameDAO.getInstance().closeSessionForReal();
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