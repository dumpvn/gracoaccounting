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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.CustomersAddress;
import com.mpe.financial.model.CustomersAddressPK;
import com.mpe.financial.model.CustomersCategory;
import com.mpe.financial.model.CustomersCommunication;
import com.mpe.financial.model.CustomersCommunicationPK;
import com.mpe.financial.model.ItemPriceCategory;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CustomersCategoryDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.ItemPriceCategoryDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.struts.form.CustomersForm;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class CustomerAction extends Action {
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
		CustomersForm customerForm = (CustomersForm) form;
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
					} else if ("ADDRESSFORM".equalsIgnoreCase(action)) {
						forward = performAddressForm(mapping, form, request, response);
					} else if ("ADDRESSSAVE".equalsIgnoreCase(action)) {
						forward = performAddressSave(mapping, form, request, response);
					} else if ("COMMUNICATIONFORM".equalsIgnoreCase(action)) {
						forward = performCommunicationForm(mapping, form, request, response);
					} else if ("COMMUNICATIONSAVE".equalsIgnoreCase(action)) {
						forward = performCommunicationSave(mapping, form, request, response);
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
		CustomersForm form = (CustomersForm) actionForm;
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
			List customerCategoryLst = CustomersCategoryDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("customerCategoryLst", customerCategoryLst);
			Criteria criteria = CustomersDAO.getInstance().getSession().createCriteria(Customers.class);
			if (form.getString("contactPerson")!=null && form.getString("contactPerson").length()>0)criteria.add(Restrictions.like("ContactPerson", form.getString("contactPerson"), MatchMode.ANYWHERE));
			if (form.getString("company")!=null && form.getString("company").length()>0)criteria.add(Restrictions.like("Company", form.getString("company"), MatchMode.ANYWHERE));
			if (form.getString("isStore")!=null && form.getString("isStore").length()>0 && form.getString("isStore").equalsIgnoreCase("Y"))criteria.add(Restrictions.eq("Store", Boolean.TRUE));
			else if (form.getString("isStore")!=null && form.getString("isStore").length()>0 && form.getString("isStore").equalsIgnoreCase("N"))criteria.add(Restrictions.eq("Store", Boolean.FALSE));
			if (form.getLong("customerCategoryId")>0) criteria.add(Restrictions.eq("CustomersCategory.Id", new Long(form.getLong("customerCategoryId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = CustomersDAO.getInstance().getSession().createCriteria(Customers.class);
			if (form.getString("contactPerson")!=null && form.getString("contactPerson").length()>0)criteria.add(Restrictions.like("ContactPerson", form.getString("contactPerson"), MatchMode.ANYWHERE));
			if (form.getString("company")!=null && form.getString("company").length()>0)criteria.add(Restrictions.like("Company", form.getString("company"), MatchMode.ANYWHERE));
			if (form.getString("isStore")!=null && form.getString("isStore").length()>0 && form.getString("isStore").equalsIgnoreCase("Y"))criteria.add(Restrictions.eq("Store", Boolean.TRUE));
			else if (form.getString("isStore")!=null && form.getString("isStore").length()>0 && form.getString("isStore").equalsIgnoreCase("N"))criteria.add(Restrictions.eq("Store", Boolean.FALSE));
			if (form.getLong("customerCategoryId")>0) criteria.add(Restrictions.eq("CustomersCategory.Id", new Long(form.getLong("customerCategoryId"))));
			//criteria.addOrder(Order.asc("ContactPerson"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("CUSTOMER",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("customer");
			
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				CustomersDAO.getInstance().closeSessionForReal();
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
		CustomersForm form = (CustomersForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
				.add(Restrictions.eq("Type", new String("Account receivable"))).addOrder(Order.asc("Name")).list();
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			List itemPriceCategoryLst = ItemPriceCategoryDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemPriceCategoryLst", itemPriceCategoryLst);
			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
			  	.add(Restrictions.ne("Id", new Long(form.getLong("customerId")))).addOrder(Order.asc("Code")).list();
			request.setAttribute("customerLst", customerLst);
			List customerCategoryLst = CustomersCategoryDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("customerCategoryLst", customerCategoryLst);
			if (form.getLong("customerId") == 0) {
				form.setString("customerId",0);
				form.setCurentTimestamp("createOn");
				form.setString("isActive", "Y");
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				Customers obj = CustomersDAO.getInstance().get(form.getLong("customerId"));
				form.setString("customerId",obj.getId());
				//form.setString("contactPerson",obj.getContactPerson());
				//form.setString("position",obj.getPosition());
				form.setString("company",obj.getCompany());
				form.setString("areaCode",obj.getAreaCode());
				form.setString("npwp",obj.getNpwp());
				form.setString("code",obj.getCode());
				form.setString("chartOfAccountId",obj.getChartOfAccount()!=null?obj.getChartOfAccount().getId():0);
				form.setString("itemPriceCategoryId",obj.getItemPriceCategory()!=null?obj.getItemPriceCategory().getId():0);
				form.setString("customerCategoryId",obj.getCustomersCategory()!=null?obj.getCustomersCategory().getId():0);
				form.setString("address",obj.getAddress());
				form.setString("city",obj.getCity());
				form.setString("postalCode",obj.getPostalCode());
				form.setString("province",obj.getProvince());
				form.setString("country",obj.getCountry());
				form.setString("telephone",obj.getTelephone());
				form.setString("fax",obj.getFax());
				form.setString("email",obj.getEmail());
				form.setString("description",obj.getDescription());
				form.setString("isActive",obj.isActive()==true?"Y":"N");
				form.setString("isBlockOverCreditLimit",obj.isBlockOverCreditLimit()==true?"Y":"N");
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				form.setString("creditLimit", obj.getCreditLimit());
				form.setString("isStore", obj.isStore()==true?"Y":"N");
				form.setString("customerAliasId",obj.getCustomerAlias()!=null?obj.getCustomerAlias().getId():0);
			}
		}catch(Exception ex) {
			try {
			    List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
				  .add(Restrictions.eq("Type", new String("Account receivable"))).addOrder(Order.asc("Name")).list();
				  request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				  List itemPriceCategoryLst = ItemPriceCategoryDAO.getInstance().findAll(Order.asc("Name"));
				  request.setAttribute("itemPriceCategoryLst", itemPriceCategoryLst);
				  List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
			  	.add(Restrictions.ne("Id", new Long(form.getLong("customerId")))).addOrder(Order.asc("Code")).list();
			  request.setAttribute("customerLst", customerLst);
			  List customerCategoryLst = CustomersCategoryDAO.getInstance().findAll(Order.asc("Name"));
			  request.setAttribute("customerCategoryLst", customerCategoryLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				CustomersDAO.getInstance().closeSessionForReal();
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
		CustomersForm form = (CustomersForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Customers obj = new Customers();
			if (form.getLong("customerId") == 0) {				
				//obj.setContactPerson(form.getString("contactPerson"));
				//obj.setPosition(form.getString("position"));
				obj.setCompany(form.getString("company"));
				obj.setAreaCode(form.getString("areaCode"));
				ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
				obj.setChartOfAccount(chartOfAccount);
				obj.setCode(form.getString("code"));
				obj.setDescription(form.getString("description"));
				ItemPriceCategory itemPriceCategory = ItemPriceCategoryDAO.getInstance().get(form.getLong("itemPriceCategoryId"));
				obj.setItemPriceCategory(itemPriceCategory);
				CustomersCategory customersCategory = CustomersCategoryDAO.getInstance().get(form.getLong("customerCategoryId"));
				obj.setCustomersCategory(customersCategory);
				obj.setNpwp(form.getString("npwp"));
				obj.setOrganization(users.getOrganization());
				obj.setAddress(form.getString("address"));
				obj.setCity(form.getString("city"));
				obj.setPostalCode(form.getString("postalCode"));
				obj.setProvince(form.getString("province"));
				obj.setCountry(form.getString("country"));
				obj.setTelephone(form.getString("telephone"));
				obj.setFax(form.getString("fax"));
				obj.setEmail(form.getString("email"));
				obj.setActive(form.getString("isActive").length()>0?true:false);
				obj.setCreateBy(users);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setCreditLimit(form.getDouble("creditLimit"));
				obj.setStore(form.getString("isStore").length()>0?true:false);
				obj.setBlockOverCreditLimit(form.getString("isBlockOverCreditLimit").length()>0?true:false);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
				obj.setCustomerAlias(customersAlias);
				CustomersDAO.getInstance().save(obj);
			} else {
				obj = CustomersDAO.getInstance().load(form.getLong("customerId"));
				//obj.setContactPerson(form.getString("contactPerson"));
				//obj.setPosition(form.getString("position"));
				obj.setCompany(form.getString("company"));
				obj.setAreaCode(form.getString("areaCode"));
				ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
				obj.setChartOfAccount(chartOfAccount);
				obj.setCode(form.getString("code"));
				obj.setDescription(form.getString("description"));
				ItemPriceCategory itemPriceCategory = ItemPriceCategoryDAO.getInstance().get(form.getLong("itemPriceCategoryId"));
				obj.setItemPriceCategory(itemPriceCategory);
				CustomersCategory customersCategory = CustomersCategoryDAO.getInstance().get(form.getLong("customerCategoryId"));
				obj.setCustomersCategory(customersCategory);
				obj.setNpwp(form.getString("npwp"));
				obj.setOrganization(users.getOrganization());
				obj.setAddress(form.getString("address"));
				obj.setCity(form.getString("city"));
				obj.setPostalCode(form.getString("postalCode"));
				obj.setProvince(form.getString("province"));
				obj.setCountry(form.getString("country"));
				obj.setTelephone(form.getString("telephone"));
				obj.setFax(form.getString("fax"));
				obj.setEmail(form.getString("email"));
				obj.setActive(form.getString("isActive").length()>0?true:false);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setCreditLimit(form.getDouble("creditLimit"));
				obj.setStore(form.getString("isStore").length()>0?true:false);
				obj.setBlockOverCreditLimit(form.getString("isBlockOverCreditLimit").length()>0?true:false);
				Customers customersAlias = CustomersDAO.getInstance().get(form.getLong("customerAliasId"));
				obj.setCustomerAlias(customersAlias);
				CustomersDAO.getInstance().update(obj);
			}
		}catch(Exception ex) {
			try {
			    List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
				  .add(Restrictions.eq("Type", new String("Account receivable"))).addOrder(Order.asc("Name")).list();
				  request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				  List itemPriceCategoryLst = ItemPriceCategoryDAO.getInstance().findAll(Order.asc("Name"));
				  request.setAttribute("itemPriceCategoryLst", itemPriceCategoryLst);
				  List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
			  	.add(Restrictions.ne("Id", new Long(form.getLong("customerId")))).addOrder(Order.asc("Code")).list();
			  request.setAttribute("customerLst", customerLst);
			  List customerCategoryLst = CustomersCategoryDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("customerCategoryLst", customerCategoryLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				CustomersDAO.getInstance().closeSessionForReal();
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
		CustomersForm form = (CustomersForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			Customers customer = CustomersDAO.getInstance().get(form.getLong("customerId"));
			request.setAttribute("customer", customer);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				CustomersDAO.getInstance().closeSessionForReal();
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
		CustomersForm form = (CustomersForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			//UsersDAO.getInstance().delete(form.getLong("customerId"));
			CustomersDAO.getInstance().delete(form.getLong("customerId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				CustomersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performAddressForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomersForm form = (CustomersForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			Customers customer = (Customers)httpSession.getAttribute("customer");
			// remove
			if (form.getString("addressType").length() > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEDETAIL")) {
				CustomersAddress removeCustomersAddress = null;
				Iterator iterator = customer.getCustomersAddresses().iterator();
				while (iterator.hasNext()) {
					CustomersAddress customerAddress = (CustomersAddress)iterator.next();
					if (form.getString("addressType").equalsIgnoreCase("DELIVERY") && customerAddress.getId().getDeliveryAddress().equalsIgnoreCase("Y")) {
						removeCustomersAddress = customerAddress;
					} else if (form.getString("addressType").equalsIgnoreCase("INVOICE") && customerAddress.getId().getInvoiceAddress().equalsIgnoreCase("Y")) {
						removeCustomersAddress = customerAddress;
					} else {
						removeCustomersAddress = customerAddress;
					}
				}
				if (removeCustomersAddress!=null) {
					Set set = customer.getCustomersAddresses();
					set.remove(removeCustomersAddress);
					customer.setCustomersAddresses(set);
				}
				form.setString("subaction", "");
				form.setString("addressType", "");
				httpSession.setAttribute("customer", customer);
			}
			// relationships
			if (form.getLong("customerId") > 0) {
				if (customer==null) {
					customer = CustomersDAO.getInstance().get(form.getLong("customerId"));
					httpSession.setAttribute("customer", customer);
				}
				request.setAttribute("customer", customer);
				Set customerAddressLst = customer.getCustomersAddresses();
				request.setAttribute("customerAddressLst", customerAddressLst);
				
			}
			if (form.getString("addressType").length() > 0) {
				Iterator iterator = customer.getCustomersAddresses().iterator();
				while (iterator.hasNext()) {
					CustomersAddress customerAddress = (CustomersAddress)iterator.next();
					if (form.getString("addressType").equalsIgnoreCase("DELIVERY") && customerAddress.getId().getDeliveryAddress().equalsIgnoreCase("Y")) {
						form.setString("address", customerAddress.getAddress());
						form.setString("city", customerAddress.getCity());
						form.setString("code", customerAddress.getCode());
						form.setString("postalCode", customerAddress.getPostalCode());
					} else if (form.getString("addressType").equalsIgnoreCase("INVOICE") && customerAddress.getId().getInvoiceAddress().equalsIgnoreCase("Y")) {
						form.setString("address", customerAddress.getAddress());
						form.setString("city", customerAddress.getCity());
						form.setString("code", customerAddress.getCode());
						form.setString("postalCode", customerAddress.getPostalCode());
					} else {
						form.setString("address", customerAddress.getAddress());
						form.setString("city", customerAddress.getCity());
						form.setString("code", customerAddress.getCode());
						form.setString("postalCode", customerAddress.getPostalCode());
					}
				}
			}
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				CustomersDAO.getInstance().closeSessionForReal();
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
	private ActionForward performAddressSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomersForm form = (CustomersForm) actionForm;
		HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Customers obj = (Customers) httpSession.getAttribute("customer");
			if (form.getLong("customerId") > 0) {
				if (obj==null) obj = CustomersDAO.getInstance().get(form.getLong("customerId"));
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDDETAIL")) {
				if (form.getString("addressType").length() >0) {
					CustomersAddress customerAddress = new CustomersAddress();
					CustomersAddressPK customerAddressPK = new CustomersAddressPK();
					customerAddressPK.setCustomers(obj);
					if (form.getString("addressType").equalsIgnoreCase("DELIVERY")) {
						customerAddressPK.setDeliveryAddress("Y");
						customerAddressPK.setInvoiceAddress("N");
						customerAddressPK.setInvoiceTaxAddress("N");
					} else if (form.getString("addressType").equalsIgnoreCase("INVOICE")) {
						customerAddressPK.setDeliveryAddress("N");
						customerAddressPK.setInvoiceAddress("Y");
						customerAddressPK.setInvoiceTaxAddress("N");
					} else {
						customerAddressPK.setDeliveryAddress("N");
						customerAddressPK.setInvoiceAddress("N");
						customerAddressPK.setInvoiceTaxAddress("Y");
					}
					customerAddress.setId(customerAddressPK);
					customerAddress.setAddress(form.getString("address"));
					customerAddress.setCity(form.getString("city"));
					customerAddress.setCode(form.getString("code"));
					customerAddress.setPostalCode(form.getString("postalCode"));
					Set set = obj.getCustomersAddresses();
					if (set==null) set = new LinkedHashSet();
					CustomersAddress removeCustomersAddress = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						CustomersAddress customerAddress2 = (CustomersAddress)iterator.next();
						if (form.getString("addressType").equalsIgnoreCase("DELIVERY") && customerAddress2.getId().getDeliveryAddress().equalsIgnoreCase("Y")) {
							removeCustomersAddress = customerAddress2;
						} else if (form.getString("addressType").equalsIgnoreCase("INVOICE") && customerAddress2.getId().getInvoiceAddress().equalsIgnoreCase("Y")) {
							removeCustomersAddress = customerAddress2;
						} else if (form.getString("addressType").equalsIgnoreCase("INVOICE TAX") && customerAddress2.getId().getInvoiceTaxAddress().equalsIgnoreCase("Y")) {
							removeCustomersAddress = customerAddress2;
						}
					}
					if (removeCustomersAddress!=null) {
						set.remove(removeCustomersAddress);
						set.add(customerAddress);
					} else {
						set.add(customerAddress);
					}
					obj.setCustomersAddresses(set);
				}
				// netral
				form.setString("code", "");
				form.setString("address", "");
				form.setString("postalCode", "");
				form.setString("city", "");
				form.setString("addressType", "");
				form.setString("subaction", "");
				httpSession.setAttribute("customer", obj);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				Session session = CustomersDAO.getInstance().getSession();
				Transaction transaction = session.beginTransaction();
				CustomersDAO.getInstance().getSession().merge(obj);
				transaction.commit();
				httpSession.removeAttribute("customer");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				try {
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				CustomersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?customerId="+form.getLong("customerId"));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performCommunicationForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomersForm form = (CustomersForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			Customers customer = (Customers)httpSession.getAttribute("customer");
			// remove
			if (form.getString("contactPerson").length() > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEDETAIL")) {
				CustomersCommunication removeCustomersCommunication = null;
				Iterator iterator = customer.getCustomersCommunications().iterator();
				while (iterator.hasNext()) {
					CustomersCommunication customerCommunication = (CustomersCommunication)iterator.next();
					if (form.getString("contactPerson").equalsIgnoreCase(customerCommunication.getId().getContactPerson())) {
						removeCustomersCommunication = customerCommunication;
					}
				}
				if (removeCustomersCommunication!=null) {
					Set set = customer.getCustomersCommunications();
					set.remove(removeCustomersCommunication);
					customer.setCustomersCommunications(set);
				}
				form.setString("subaction", "");
				form.setString("contactPerson", "");
				httpSession.setAttribute("customer", customer);
			}
			// relationships
			if (form.getLong("customerId") > 0) {
				if (customer==null) {
					customer = CustomersDAO.getInstance().get(form.getLong("customerId"));
					httpSession.setAttribute("customer", customer);
				}
				request.setAttribute("customer", customer);
				Set customerCommunicationLst = customer.getCustomersCommunications();
				request.setAttribute("customerCommunicationLst", customerCommunicationLst);
				
			}
			if (form.getString("contactPerson").length() > 0) {
				Iterator iterator = customer.getCustomersCommunications().iterator();
				while (iterator.hasNext()) {
					CustomersCommunication customerCommunication = (CustomersCommunication)iterator.next();
					if (form.getString("contactPerson").equalsIgnoreCase(customerCommunication.getId().getContactPerson())) {
						form.setString("title", customerCommunication.getTitle());
						form.setString("fax", customerCommunication.getFax());
						form.setString("mobile", customerCommunication.getMobile());
						form.setString("officePhone", customerCommunication.getOfficePhone());
						form.setString("email", customerCommunication.getEmail());
					}
				}
			}
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				CustomersDAO.getInstance().closeSessionForReal();
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
	private ActionForward performCommunicationSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomersForm form = (CustomersForm) actionForm;
		HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Customers obj = (Customers) httpSession.getAttribute("customer");
			if (form.getLong("customerId") > 0) {
				if (obj==null) obj = CustomersDAO.getInstance().get(form.getLong("customerId"));
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDDETAIL")) {
				if (form.getString("contactPerson").length() >0) {
					CustomersCommunication customerCommunication = new CustomersCommunication();
					CustomersCommunicationPK customerCommunicationPK = new CustomersCommunicationPK();
					customerCommunicationPK.setCustomers(obj);
					customerCommunicationPK.setContactPerson(form.getString("contactPerson"));
					customerCommunication.setId(customerCommunicationPK);
					customerCommunication.setTitle(form.getString("title"));
					customerCommunication.setFax(form.getString("fax"));
					customerCommunication.setMobile(form.getString("mobile"));
					customerCommunication.setOfficePhone(form.getString("officePhone"));
					customerCommunication.setEmail(form.getString("email"));
					Set set = obj.getCustomersCommunications();
					if (set==null) set = new LinkedHashSet();
					CustomersCommunication removeCustomersCommunication = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						CustomersCommunication customerCommunication2 = (CustomersCommunication)iterator.next();
						if (form.getString("contactPerson").equalsIgnoreCase(customerCommunication2.getId().getContactPerson())) {
							removeCustomersCommunication = customerCommunication2;
						}
					}
					if (removeCustomersCommunication!=null) {
						set.remove(removeCustomersCommunication);
						set.add(customerCommunication);
					} else {
						set.add(customerCommunication);
					}
					obj.setCustomersCommunications(set);
				}
				// netral
				form.setString("contactPerson", "");
				form.setString("title", "");
				form.setString("fax", "");
				form.setString("officePhone", "");
				form.setString("mobile", "");
				form.setString("subaction", "");
				httpSession.setAttribute("customer", obj);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				Session session = CustomersDAO.getInstance().getSession();
				Transaction transaction = session.beginTransaction();
				CustomersDAO.getInstance().getSession().merge(obj);
				transaction.commit();
				httpSession.removeAttribute("customer");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				try {
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				CustomersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?customerId="+form.getLong("customerId"));
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