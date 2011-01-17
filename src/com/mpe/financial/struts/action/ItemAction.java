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
import org.apache.struts.upload.FormFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.Currency;
import com.mpe.financial.model.CustomField;
import com.mpe.financial.model.Inventory;
import com.mpe.financial.model.Item;
import com.mpe.financial.model.ItemCategory;
import com.mpe.financial.model.ItemCustomField;
import com.mpe.financial.model.ItemCustomFieldPK;
import com.mpe.financial.model.ItemGroup;
import com.mpe.financial.model.ItemGroupPK;
import com.mpe.financial.model.ItemPrice;
import com.mpe.financial.model.ItemPriceCategory;
import com.mpe.financial.model.ItemPricePK;
import com.mpe.financial.model.ItemStatus;
import com.mpe.financial.model.ItemUnit;
import com.mpe.financial.model.ItemVendor;
import com.mpe.financial.model.ItemVendorPK;
import com.mpe.financial.model.Tax;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.CustomFieldDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemCategoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemPriceCategoryDAO;
import com.mpe.financial.model.dao.ItemStatusDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.TaxDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.struts.form.ItemForm;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class ItemAction extends Action {
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
		ItemForm itemForm = (ItemForm) form;
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
						if (itemForm.getString("subaction")!=null && itemForm.getString("subaction").length()>0 && itemForm.getString("subaction").equalsIgnoreCase("refresh")) {
							forward = performForm(mapping, form, request, response);
						} else {
							forward = performSave(mapping, form, request, response);
						}
					} else if ("DETAIL".equalsIgnoreCase(action)) { 
						forward = performDetail(mapping, form, request, response);
					} else if ("DELETE".equalsIgnoreCase(action)) {
						forward = performDelete(mapping, form, request, response);
					} else if ("SHOWIMAGE".equalsIgnoreCase(action)) {
						forward = performShowImage(mapping, form, request, response);
					} else if ("POPUP".equalsIgnoreCase(action)) {
						forward = performPopUp(mapping, form, request, response);
					} else if ("PRICEFORM".equalsIgnoreCase(action)) {
						forward = performPriceForm(mapping, form, request, response);
					} else if ("PRICESAVE".equalsIgnoreCase(action)) {
						forward = performPriceSave(mapping, form, request, response);
					} else if ("VENDORFORM".equalsIgnoreCase(action)) {
						forward = performVendorForm(mapping, form, request, response);
					} else if ("VENDORSAVE".equalsIgnoreCase(action)) {
						forward = performVendorSave(mapping, form, request, response);
					} else if ("GROUPFORM".equalsIgnoreCase(action)) {
						forward = performGroupForm(mapping, form, request, response);
					} else if ("GROUPSAVE".equalsIgnoreCase(action)) {
						forward = performGroupSave(mapping, form, request, response);
					} else if ("CUSTOMFIELDFORM".equalsIgnoreCase(action)) {
						forward = performCustomFieldForm(mapping, form, request, response);
					} else if ("CUSTOMFIELDSAVE".equalsIgnoreCase(action)) {
						forward = performCustomFieldSave(mapping, form, request, response);
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
		ItemForm form = (ItemForm) actionForm;
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
			List itemCategoryLst = ItemCategoryDAO.getInstance().getSession().createCriteria(ItemCategory.class).addOrder(Order.asc("Name")).list();
			request.setAttribute("itemCategoryLst",itemCategoryLst);
			Criteria criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
			if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code")));
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
			if (form.getLong("itemCategoryId")>0){
				criteria.createCriteria("ItemCategory").add(Restrictions.eq("Id", new Long(form.getLong("itemCategoryId"))));
			}
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
			if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code")));
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
			if (form.getLong("itemCategoryId")>0){
				criteria.createCriteria("ItemCategory").add(Restrictions.eq("Id", new Long(form.getLong("itemCategoryId"))));
			}
			//criteria.addOrder(Order.asc("BrandName"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("ITEM",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				ItemCategoryDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
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
	private ActionForward performPopUp(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
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
			
						
			//String sql = "SELECT item_type_id, item_gender_id, item_merk_id, item_color_id FROM item group by item_type_id, item_gender_id, item_merk_id, item_color_id";
			
			
			//List itemCategoryLst = ItemCategoryDAO.getInstance().getSession().createCriteria(ItemCategory.class).addOrder(Order.asc("Name")).list();
			//request.setAttribute("itemCategoryLst",itemCategoryLst);
			Criteria criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
			if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", "%"+form.getString("code")+"%"));
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
			if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", "%"+form.getString("code")+"%"));
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
			//criteria.addOrder(Order.asc("BrandName"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("ITEM",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				ItemCategoryDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		if (form.getString("subaction")==null || form.getString("subaction").length()==0) return mapping.findForward("list");
		else return mapping.findForward("list_item");
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
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// remove item price
			Inventory obj = (Inventory)httpSession.getAttribute("inventory");			
			// relationships
			List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("currencyLst",currencyLst);
			List itemCategoryLst = ItemCategoryDAO.getInstance().getSession().createCriteria(ItemCategory.class).addOrder(Order.asc("Name")).list();
			request.setAttribute("itemCategoryLst",itemCategoryLst);
			List itemUnitLst = ItemUnitDAO.getInstance().getSession().createCriteria(ItemUnit.class).addOrder(Order.asc("Name")).list();
			request.setAttribute("itemUnitLst", itemUnitLst);
			List itemStatusLst = ItemStatusDAO.getInstance().findAll(Order.asc("Code"));
			request.setAttribute("itemStatusLst", itemStatusLst);
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			List purchaseTaxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class).add(Restrictions.eq("Ap", Boolean.TRUE)).addOrder(Order.asc("Name")).list();
			request.setAttribute("purchaseTaxLst",purchaseTaxLst);
			if (form.getLong("itemId") == 0) {
				
				//form.setString("code", RunningNumberDAO.getInstance().getItemNumber());
				//if (form.getString("code")==null || form.getString("code").length()==0) form.setString("code", "00000");
				form.setString("code", RunningNumberDAO.getInstance().getItemNumber());
				form.setString("itemId",0);
				form.setCurentTimestamp("createOn");
				form.setString("isActive","Y");
				if (obj!=null) {
				    form.setString("itemCategoryId",obj.getItemCategory()!=null?obj.getItemCategory().getId():0);
					form.setString("itemId",obj.getId());
					form.setString("code",obj.getCode());
					form.setString("name",obj.getName());
					form.setString("costPrice",Formater.getFormatedOutputForm(obj.getNumberOfDigit(),obj.getCostPrice()));
					form.setString("depth",obj.getDepth());
					form.setString("description",obj.getDescription());
					form.setString("height",obj.getHeight());
					form.setString("textDimension",obj.getTextDimension());
					form.setString("type",obj.getType());
					form.setString("weight",obj.getWeight());
					form.setString("width",obj.getWidth());
					form.setString("costPriceAccountId",obj.getCostPriceAccount()!=null?obj.getCostPriceAccount().getId():0);
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("itemCategoryId",obj.getItemCategory()!=null?obj.getItemCategory().getId():0);
					form.setString("itemStatusId",obj.getItemStatus()!=null?obj.getItemStatus().getId():0);
					form.setString("isActive",obj.isActive()==true?"Y":"N");
					form.setString("itemUnitId",obj.getItemUnit()!=null?obj.getItemUnit().getId():0);
					// inventory
					form.setString("maximumStock",obj.getMaximumStock());
					form.setString("minimumStock",obj.getMinimumStock());
					form.setString("inventoryAccountId",obj.getInventoryAccount()!=null?obj.getInventoryAccount().getId():0);
					form.setString("purchaseTaxId",obj.getPurchaseTax()!=null?obj.getPurchaseTax().getId():0);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = InventoryDAO.getInstance().get(form.getLong("itemId"));
					httpSession.setAttribute("inventory",obj);
				}
				form.setString("itemCategoryId",obj.getItemCategory()!=null?obj.getItemCategory().getId():0);
				form.setString("itemId",obj.getId());
				form.setString("code",obj.getCode());
				form.setString("name",obj.getName());
				form.setString("costPrice",Formater.getFormatedOutputForm(obj.getNumberOfDigit(),obj.getCostPrice()));
				form.setString("depth",obj.getDepth());
				form.setString("description",obj.getDescription());
				form.setString("height",obj.getHeight());
				form.setString("textDimension",obj.getTextDimension());
				form.setString("type",obj.getType());
				form.setString("weight",obj.getWeight());
				form.setString("width",obj.getWidth());
				form.setString("costPriceAccountId",obj.getCostPriceAccount()!=null?obj.getCostPriceAccount().getId():0);
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("itemStatusId",obj.getItemStatus()!=null?obj.getItemStatus().getId():0);
				form.setString("isActive",obj.isActive()==true?"Y":"N");
				form.setString("itemUnitId",obj.getItemUnit()!=null?obj.getItemUnit().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				// inventory
				form.setString("maximumStock",obj.getMaximumStock());
				form.setString("minimumStock",obj.getMinimumStock());
				form.setString("inventoryAccountId",obj.getInventoryAccount()!=null?obj.getInventoryAccount().getId():0);
				form.setString("purchaseTaxId",obj.getPurchaseTax()!=null?obj.getPurchaseTax().getId():0);
			}
		}catch(Exception ex) {
			try {
				List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("currencyLst",currencyLst);
				List itemCategoryLst = ItemCategoryDAO.getInstance().getSession().createCriteria(ItemCategory.class).addOrder(Order.asc("Name")).list();
				request.setAttribute("itemCategoryLst",itemCategoryLst);
				List itemUnitLst = ItemUnitDAO.getInstance().getSession().createCriteria(ItemUnit.class).addOrder(Order.asc("Name")).list();
				request.setAttribute("itemUnitLst", itemUnitLst);
				List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				List purchaseTaxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class).add(Restrictions.eq("Ap", Boolean.TRUE)).addOrder(Order.asc("Name")).list();
				request.setAttribute("purchaseTaxLst",purchaseTaxLst);
				List itemStatusLst = ItemStatusDAO.getInstance().findAll(Order.asc("Code"));
				request.setAttribute("itemStatusLst", itemStatusLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
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
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
			  httpSession.removeAttribute("inventory");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Inventory obj = (Inventory)httpSession.getAttribute("inventory");
			if (form.getLong("itemId") == 0) {
				if (obj==null) obj = new Inventory();
				obj.setType(form.getString("type"));
				obj.setActive(form.getString("isActive").length()>0?true:false);
				obj.setCode(form.getString("code"));
				obj.setCostPrice(form.getDouble("costPrice"));
				ChartOfAccount costPriceAccount = ChartOfAccountDAO.getInstance().get(form.getLong("costPriceAccountId"));
				obj.setCostPriceAccount(costPriceAccount);
				obj.setDepth(form.getDouble("depth"));
				obj.setHeight(form.getDouble("height"));
				ChartOfAccount inventoryAccount = ChartOfAccountDAO.getInstance().get(form.getLong("inventoryAccountId"));
				obj.setInventoryAccount(inventoryAccount);
				obj.setDescription(form.getString("description"));
				ItemCategory itemCategory = ItemCategoryDAO.getInstance().get(form.getLong("itemCategoryId"));
				obj.setItemCategory(itemCategory);
				ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
				obj.setItemUnit(itemUnit);
				Tax tax = TaxDAO.getInstance().get(form.getLong("purchaseTaxId"));
				obj.setPurchaseTax(tax);
				obj.setMaximumStock(form.getDouble("maximumStock"));
				obj.setMinimumStock(form.getDouble("minimumStock"));
				obj.setName(form.getString("name"));
				ItemStatus itemStatus = ItemStatusDAO.getInstance().get(form.getLong("itemStatusId"));
				obj.setItemStatus(itemStatus);
				obj.setTextDimension(form.getString("textDimension"));
				obj.setWeight(form.getDouble("weight"));
				obj.setWidth(form.getDouble("width"));
				obj.setCreateBy(users);
				obj.setCreateOn(form.getTimestamp("createOn"));
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				obj.setOrganization(users.getOrganization());
				// image
				FormFile file = form.getFile("image");
				if (file!=null && file.getFileSize()>0 && file.getContentType().substring(0,5).equalsIgnoreCase("image")) {
					obj.setImageContentType(file.getContentType());
					obj.setImage(file.getFileData());
				}
			} else {
			  if (obj==null) obj = InventoryDAO.getInstance().get(form.getLong("itemId"));
				obj.setId(form.getLong("itemId"));
				obj.setType(form.getString("type"));
				obj.setActive(form.getString("isActive").length()>0?true:false);
				obj.setCode(form.getString("code"));
				obj.setCostPrice(form.getDouble("costPrice"));
				ChartOfAccount costPriceAccount = ChartOfAccountDAO.getInstance().get(form.getLong("costPriceAccountId"));
				obj.setCostPriceAccount(costPriceAccount);
				obj.setDepth(form.getDouble("depth"));
				obj.setHeight(form.getDouble("height"));
				ChartOfAccount inventoryAccount = ChartOfAccountDAO.getInstance().get(form.getLong("inventoryAccountId"));
				obj.setInventoryAccount(inventoryAccount);
				obj.setDescription(form.getString("description"));
				ItemCategory itemCategory = ItemCategoryDAO.getInstance().get(form.getLong("itemCategoryId"));
				obj.setItemCategory(itemCategory);
				Tax tax = TaxDAO.getInstance().get(form.getLong("purchaseTaxId"));
				obj.setPurchaseTax(tax);
				ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
				obj.setItemUnit(itemUnit);
				obj.setMaximumStock(form.getDouble("maximumStock"));
				obj.setMinimumStock(form.getDouble("minimumStock"));
				obj.setName(form.getString("name"));
				ItemStatus itemStatus = ItemStatusDAO.getInstance().get(form.getLong("itemStatusId"));
				obj.setItemStatus(itemStatus);
				obj.setTextDimension(form.getString("textDimension"));
				obj.setWeight(form.getDouble("weight"));
				obj.setWidth(form.getDouble("width"));
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setOrganization(users.getOrganization());
				// image
				FormFile file = form.getFile("image");
				if (file!=null && file.getFileSize()>0 && file.getContentType().substring(0,5).equalsIgnoreCase("image")) {
					obj.setImageContentType(file.getContentType());
					obj.setImage(file.getFileData());
				}
				//remove image
				if (form.getString("removeImage")!=null && form.getString("removeImage").length()>0) {
				    obj.setImageContentType(null);
					obj.setImage(null);
				}
			}
				
			// save to session
			httpSession.setAttribute("inventory", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					Session session = InventoryDAO.getInstance().getSession();
					Transaction transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateItemNumber(session);
					InventoryDAO.getInstance().save(obj, session);
					transaction.commit();
					//session.flush();
				} else {
					Transaction transaction = InventoryDAO.getInstance().getSession().beginTransaction();
					InventoryDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("inventory");
				// finish
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				try {
					List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("currencyLst",currencyLst);
					List itemCategoryLst = ItemCategoryDAO.getInstance().getSession().createCriteria(ItemCategory.class).addOrder(Order.asc("Name")).list();
					request.setAttribute("itemCategoryLst",itemCategoryLst);
					List itemUnitLst = ItemUnitDAO.getInstance().getSession().createCriteria(ItemUnit.class).addOrder(Order.asc("Name")).list();
					request.setAttribute("itemUnitLst", itemUnitLst);
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					List purchaseTaxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class).add(Restrictions.eq("Ap", Boolean.TRUE)).addOrder(Order.asc("Name")).list();
					request.setAttribute("purchaseTaxLst",purchaseTaxLst);
					List itemStatusLst = ItemStatusDAO.getInstance().findAll(Order.asc("Code"));
					request.setAttribute("itemStatusLst", itemStatusLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?itemId="+form.getLong("itemId")+"&itemTypeId="+form.getLong("itemTypeId")+"&itemGenderId="+form.getLong("itemGenderId")+"&itemMerkId="+form.getLong("itemMerkId")+"&itemColorId="+form.getLong("itemColorId")+"&itemSizeId="+form.getLong("itemSizeId"));
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
	private ActionForward performPriceForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// remove item price
			Inventory obj = (Inventory)httpSession.getAttribute("inventory");
			if (form.getLong("itemPriceCategoryId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEITEMPRICE")) {
				ItemPrice removeItemPrice = null;
				Iterator iterator = obj.getItemPrices().iterator();
				while (iterator.hasNext()) {
				  ItemPrice itemPrice = (ItemPrice)iterator.next();
				  if (form.getLong("itemPriceCategoryId") == itemPrice.getId().getItemPriceCategory().getId()) {
				  	removeItemPrice = itemPrice;
					}
				}
				if (removeItemPrice!=null) {
					Set set = obj.getItemPrices();
					set.remove(removeItemPrice);
					obj.setItemPrices(set);
				}
				form.setString("subaction", "");
				form.setString("itemPriceCategoryId", "");
				httpSession.setAttribute("inventory", obj);
			}
			
			// relationships
			List salesTaxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class).add(Restrictions.eq("Ap", Boolean.FALSE)).addOrder(Order.asc("Name")).list();
			request.setAttribute("salesTaxLst",salesTaxLst);
			List purchaseTaxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class).add(Restrictions.eq("Ap", Boolean.TRUE)).addOrder(Order.asc("Name")).list();
			request.setAttribute("purchaseTaxLst",purchaseTaxLst);
			List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("currencyLst",currencyLst);
			List itemUnitLst = ItemUnitDAO.getInstance().getSession().createCriteria(ItemUnit.class).addOrder(Order.asc("Name")).list();
			request.setAttribute("itemUnitLst", itemUnitLst);
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			List itemPriceCategoryLst = ItemPriceCategoryDAO.getInstance().getSession().createCriteria(ItemPriceCategory.class).addOrder(Order.asc("Name")).list();
			request.setAttribute("itemPriceCategoryLst",itemPriceCategoryLst);
			if (form.getLong("itemId") == 0) {
				//form.setString("code", RunningNumberDAO.getInstance().getItemNumber());
				//if (form.getString("code")==null || form.getString("code").length()==0) form.setString("code", "00000");
				form.setString("code", RunningNumberDAO.getInstance().getItemNumber());
				form.setString("itemId",0);
				form.setCurentTimestamp("createOn");
				form.setString("isActive","Y");
				if (obj!=null) {
					Set itemPriceLst = obj.getItemPrices();
					request.setAttribute("itemPriceLst", itemPriceLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = InventoryDAO.getInstance().get(form.getLong("itemId"));
					httpSession.setAttribute("inventory",obj);
				}
				Set itemPriceLst = obj.getItemPrices();
				request.setAttribute("itemPriceLst", itemPriceLst);
			}
			// item price
			if (form.getLong("itemPriceCategoryId") > 0) {
				Iterator iterator = obj.getItemPrices().iterator();
				while (iterator.hasNext()) {
					ItemPrice itemPrice = (ItemPrice)iterator.next();
					if (form.getLong("itemPriceCategoryId") == itemPrice.getId().getItemPriceCategory().getId()) {
						form.setString("price", Formater.getFormatedOutputForm(itemPrice.getPrice()));
						form.setString("chartOfAccountId", itemPrice.getChartOfAccount()!=null?itemPrice.getChartOfAccount().getId():0);
						form.setString("itemPriceCurrencyId", itemPrice.getCurrency()!=null?itemPrice.getCurrency().getId():0);
						form.setString("isDefault", itemPrice.isDefault()==true?"Y":"N");
						form.setString("itemUnitId", itemPrice.getItemUnit()!=null?itemPrice.getItemUnit().getId():0);
						form.setString("salesTaxId", itemPrice.getSalesTax()!=null?itemPrice.getSalesTax().getId():0);
					}
				}
			}
		}catch(Exception ex) {
			try {
				List salesTaxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class).add(Restrictions.eq("Ap", Boolean.FALSE)).addOrder(Order.asc("Name")).list();
				request.setAttribute("salesTaxLst",salesTaxLst);
				List purchaseTaxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class).add(Restrictions.eq("Ap", Boolean.TRUE)).addOrder(Order.asc("Name")).list();
				request.setAttribute("purchaseTaxLst",purchaseTaxLst);
				List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("currencyLst",currencyLst);
				List itemUnitLst = ItemUnitDAO.getInstance().getSession().createCriteria(ItemUnit.class).addOrder(Order.asc("Name")).list();
				request.setAttribute("itemUnitLst", itemUnitLst);
				List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				List itemPriceCategoryLst = ItemPriceCategoryDAO.getInstance().getSession().createCriteria(ItemPriceCategory.class).addOrder(Order.asc("Name")).list();
				request.setAttribute("itemPriceCategoryLst",itemPriceCategoryLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
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
	private ActionForward performPriceSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("inventory");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Inventory obj = (Inventory)httpSession.getAttribute("inventory");
			if (form.getLong("itemId") == 0) {
			  if (obj==null) obj = new Inventory();
			} else {
			  if (obj==null) obj = InventoryDAO.getInstance().get(form.getLong("itemId"));
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setOrganization(users.getOrganization());
			}
			// item price
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDITEMPRICE")) {
					if (form.getLong("itemPriceCategoryId") >0 && form.getDouble("price")>0) {
						ItemPrice itemPrice = new ItemPrice();
						Currency currency = CurrencyDAO.getInstance().get(form.getLong("itemPriceCurrencyId"));
						itemPrice.setCurrency(currency);
						itemPrice.setPrice(form.getDouble("price"));
						ItemPriceCategory itemPriceCategory = ItemPriceCategoryDAO.getInstance().get(form.getLong("itemPriceCategoryId"));
						ItemPricePK itemPricePK = new ItemPricePK();
						itemPricePK.setItemPriceCategory(itemPriceCategory);
						itemPricePK.setItem(obj);
						itemPrice.setId(itemPricePK);
						ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
						itemPrice.setItemUnit(itemUnit);
						Tax salesTax = TaxDAO.getInstance().get(form.getLong("salesTaxId"));
						itemPrice.setSalesTax(salesTax);
						itemPrice.setDefault(form.getString("isDefault").length()>0?true:false);
						ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
						itemPrice.setChartOfAccount(chartOfAccount);
						//itemPrice.setOrganization(users.getOrganization());
						Set set = obj.getItemPrices();
						if (set==null) set = new LinkedHashSet();
						ItemPrice removeItemPrice = null;
						Iterator iterator = set.iterator();
						while (iterator.hasNext()) {
						  ItemPrice itemPrice2 = (ItemPrice)iterator.next();
							if (form.getLong("itemPriceCategoryId")==itemPrice2.getId().getItemPriceCategory().getId()) {
								removeItemPrice = itemPrice2;
							}
						}
						if (removeItemPrice!=null) {
							set.remove(removeItemPrice);
							set.add(itemPrice);
						} else {
							set.add(itemPrice);
						}
						obj.setItemPrices(set);
						// netral
						form.setString("itemPriceCategoryId", "");
						form.setString("itemUnitId", "");
						form.setString("currencyId", "");
						form.setString("price", "");
						form.setString("chartOfAccountId", "");
						form.setString("isDefault", "");
						form.setString("salesTaxId", "");
					}
					// netral
					form.setString("itemPriceCategoryId", "");
					form.setString("itemUnitId", "");
					form.setString("currencyId", "");
					form.setString("price", "");
					form.setString("chartOfAccountId", "");
					form.setString("isDefault", "");
					form.setString("salesTaxId", "");
				}
				// save to session
				httpSession.setAttribute("inventory", obj);
				// save all
				if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
					if (obj.getId()==0) {
						Session session = InventoryDAO.getInstance().getSession();
						Transaction transaction = session.beginTransaction();
						//obj.setCode(form.getString("itemTypeCode")+form.getString("itemGenderCode")+form.getString("itemMerkCode")+obj.getCode()+form.getString("itemColorCode")+form.getString("itemSizeCode"));
						RunningNumberDAO.getInstance().updateItemNumber(session);
						InventoryDAO.getInstance().save(obj, session);
						transaction.commit();
						//session.flush();
					} else {
						Transaction transaction = InventoryDAO.getInstance().getSession().beginTransaction();
						InventoryDAO.getInstance().getSession().merge(obj);
						transaction.commit();
					}
					// remove session
					httpSession.removeAttribute("inventory");
					// finish
					ActionForward forward = mapping.findForward("list_redir");
					StringBuffer sb = new StringBuffer(forward.getPath());
					sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
					return new ActionForward(sb.toString(),true);
				}
		}catch(Exception ex) {
			try {
				try {
					List salesTaxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class).add(Restrictions.eq("Ap", Boolean.FALSE)).addOrder(Order.asc("Name")).list();
					request.setAttribute("salesTaxLst",salesTaxLst);
					List purchaseTaxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class).add(Restrictions.eq("Ap", Boolean.TRUE)).addOrder(Order.asc("Name")).list();
					request.setAttribute("purchaseTaxLst",purchaseTaxLst);
					List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("currencyLst",currencyLst);
					List itemUnitLst = ItemUnitDAO.getInstance().getSession().createCriteria(ItemUnit.class).addOrder(Order.asc("Name")).list();
					request.setAttribute("itemUnitLst", itemUnitLst);
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					List itemPriceCategoryLst = ItemPriceCategoryDAO.getInstance().getSession().createCriteria(ItemPriceCategory.class).addOrder(Order.asc("Name")).list();
					request.setAttribute("itemPriceCategoryLst",itemPriceCategoryLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?itemId="+form.getLong("itemId")+"&itemTypeId="+form.getLong("itemTypeId")+"&itemGenderId="+form.getLong("itemGenderId")+"&itemMerkId="+form.getLong("itemMerkId")+"&itemColorId="+form.getLong("itemColorId")+"&itemSizeId="+form.getLong("itemSizeId"));
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
	private ActionForward performVendorForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// remove item price
			Inventory obj = (Inventory)httpSession.getAttribute("inventory");
			// remove item vendor
			if (form.getLong("vendorId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEITEMVENDOR")) {
				ItemVendor removeItemVendor = null;
				Iterator iterator = obj.getItemVendors().iterator();
				while (iterator.hasNext()) {
				  ItemVendor itemVendor = (ItemVendor)iterator.next();
					if (form.getLong("vendorId") == itemVendor.getId().getVendor().getId()) {
						removeItemVendor = itemVendor;
					}
				}
				if (removeItemVendor!=null) {
					Set set = obj.getItemVendors();
					set.remove(removeItemVendor);
					obj.setItemVendors(set);
				}
				form.setString("subaction", "");
				form.setString("vendorId", "");
				httpSession.setAttribute("inventory", obj);
			}
			
			// relationships
			List purchaseTaxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class).add(Restrictions.eq("Ap", Boolean.TRUE)).addOrder(Order.asc("Name")).list();
			request.setAttribute("purchaseTaxLst",purchaseTaxLst);
			List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("currencyLst",currencyLst);
			List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Active", Boolean.TRUE)).addOrder(Order.asc("Company")).list();
			request.setAttribute("vendorLst", vendorLst);
			if (form.getLong("itemId") == 0) {
				//form.setString("code", RunningNumberDAO.getInstance().getItemNumber());
				//if (form.getString("code")==null || form.getString("code").length()==0) form.setString("code", "00000");
				form.setString("code", RunningNumberDAO.getInstance().getItemNumber());
				form.setString("itemId",0);
				form.setCurentTimestamp("createOn");
				form.setString("isActive","Y");
				if (obj!=null) {
						Set itemVendorsLst = obj.getItemVendors();
						request.setAttribute("itemVendorsLst", itemVendorsLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
		    if (obj==null) {
		        obj = InventoryDAO.getInstance().get(form.getLong("itemId"));
						httpSession.setAttribute("inventory",obj);
						request.setAttribute("inventory",obj);
				}
				Set itemVendorsLst = obj.getItemVendors();
				request.setAttribute("itemVendorsLst", itemVendorsLst);
			}
			// item vendor
			if (form.getLong("vendorId") > 0) {
				Iterator iterator = obj.getItemVendors().iterator();
				while (iterator.hasNext()) {
					ItemVendor itemVendor = (ItemVendor)iterator.next();
					if (form.getLong("vendorId") == itemVendor.getId().getVendor().getId()) {
						form.setString("itemVendorPrice", Formater.getFormatedOutputForm(itemVendor.getCostPrice()));
						form.setString("itemVendorCurrencyId", itemVendor.getCurrency()!=null?itemVendor.getCurrency().getId():0);
						form.setString("purchaseTaxId", itemVendor.getPurchaseTax()!=null?itemVendor.getPurchaseTax().getId():0);
					}
				}
			}
		}catch(Exception ex) {
			try {
				List purchaseTaxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class).add(Restrictions.eq("Ap", Boolean.TRUE)).addOrder(Order.asc("Name")).list();
				request.setAttribute("purchaseTaxLst",purchaseTaxLst);
				List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("currencyLst",currencyLst);
				List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Active", Boolean.TRUE)).addOrder(Order.asc("Company")).list();
				request.setAttribute("vendorLst", vendorLst);
			}catch(Exception exx) {
			}
			ex.printStackTrace();
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
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
	private ActionForward performVendorSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
			  httpSession.removeAttribute("inventory");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Inventory obj = (Inventory)httpSession.getAttribute("inventory");
			if (form.getLong("itemId") == 0) {
			  if (obj==null) obj = new Inventory();
			} else {
			  if (obj==null) obj = InventoryDAO.getInstance().get(form.getLong("itemId"));
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setOrganization(users.getOrganization());
			}
			// item vendor
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDITEMVENDOR")) {
					if (form.getLong("vendorId") >0 && form.getDouble("itemVendorPrice")>0) {
					  //log.info("A");
					  ItemVendor itemVendor = new ItemVendor();
						Currency currency = CurrencyDAO.getInstance().get(form.getLong("itemVendorCurrencyId"));
						itemVendor.setCurrency(currency);
						itemVendor.setCostPrice(form.getDouble("itemVendorPrice"));
						Tax tax = TaxDAO.getInstance().get(form.getLong("purchaseTaxId"));
						itemVendor.setPurchaseTax(tax);
						Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
						ItemVendorPK itemVendorPK = new ItemVendorPK(vendors, obj);
						itemVendor.setId(itemVendorPK);
						//itemVendor.setOrganization(users.getOrganization());
						Set set = obj.getItemVendors();
						if (set==null) set = new LinkedHashSet();
						ItemVendor removeItemVendor = null;
						Iterator iterator = set.iterator();
						while (iterator.hasNext()) {
						  ItemVendor itemVendor2 = (ItemVendor)iterator.next();
							if (form.getLong("vendorId")==itemVendor2.getId().getVendor().getId()) {
								removeItemVendor = itemVendor2;
							}
						}
						if (removeItemVendor!=null) {
							set.remove(removeItemVendor);
							set.add(itemVendor);
						} else {
							set.add(itemVendor);
						}
						//log.info("S : "+set.size());
						obj.setItemVendors(set);
						// netral
						form.setString("vendorId", "");
						form.setString("purchaseTaxId", "");
						form.setString("itemVendorCurrencyId", "");
						form.setString("itemVendorPrice", "");
					}
					// netral
					form.setString("vendorId", "");
					form.setString("purchaseTaxId", "");
					form.setString("itemVendorCurrencyId", "");
					form.setString("itemVendorPrice", "");
				}
				
				// save to session
				httpSession.setAttribute("inventory", obj);
				// save all
				if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
					if (obj.getId()==0) {
						Session session = InventoryDAO.getInstance().getSession();
						Transaction transaction = session.beginTransaction();
						//obj.setCode(form.getString("itemTypeCode")+form.getString("itemGenderCode")+form.getString("itemMerkCode")+obj.getCode()+form.getString("itemColorCode")+form.getString("itemSizeCode"));
						RunningNumberDAO.getInstance().updateItemNumber(session);
						InventoryDAO.getInstance().save(obj, session);
						transaction.commit();
						//session.flush();
					} else {
						Transaction transaction = InventoryDAO.getInstance().getSession().beginTransaction();
						InventoryDAO.getInstance().getSession().merge(obj);
						transaction.commit();
					}
					// remove session
					httpSession.removeAttribute("inventory");
					// finish
					ActionForward forward = mapping.findForward("list_redir");
					StringBuffer sb = new StringBuffer(forward.getPath());
					sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
					return new ActionForward(sb.toString(),true);
				}
		}catch(Exception ex) {
			try {
				try {
					List purchaseTaxLst = TaxDAO.getInstance().getSession().createCriteria(Tax.class).add(Restrictions.eq("Ap", Boolean.TRUE)).addOrder(Order.asc("Name")).list();
					request.setAttribute("purchaseTaxLst",purchaseTaxLst);
					List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("currencyLst",currencyLst);
					List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Active", Boolean.TRUE)).addOrder(Order.asc("Company")).list();
					request.setAttribute("vendorLst", vendorLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?itemId="+form.getLong("itemId")+"&itemTypeId="+form.getLong("itemTypeId")+"&itemGenderId="+form.getLong("itemGenderId")+"&itemMerkId="+form.getLong("itemMerkId")+"&itemColorId="+form.getLong("itemColorId")+"&itemSizeId="+form.getLong("itemSizeId"));
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
	private ActionForward performGroupForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// remove item price
			Inventory obj = (Inventory)httpSession.getAttribute("inventory");
			// remove item vendor
			if (form.getLong("groupId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEITEMVENDOR")) {
				ItemGroup removeItemGroup = null;
				Iterator iterator = obj.getItemGroups().iterator();
				while (iterator.hasNext()) {
					ItemGroup itemGroup = (ItemGroup)iterator.next();
					if (form.getLong("groupId") == itemGroup.getId().getGroup().getId()) {
						removeItemGroup = itemGroup;
					}
				}
				if (removeItemGroup!=null) {
					Set set = obj.getItemGroups();
					set.remove(removeItemGroup);
					obj.setItemGroups(set);
				}
				form.setString("subaction", "");
				form.setString("groupId", "");
				httpSession.setAttribute("inventory", obj);
			}
			
			// relationships
			List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("itemUnitLst",itemUnitLst);
			if (form.getLong("itemId") == 0) {
				//form.setString("code", RunningNumberDAO.getInstance().getItemNumber());
				//if (form.getString("code")==null || form.getString("code").length()==0) form.setString("code", "00000");
				form.setString("code", RunningNumberDAO.getInstance().getItemNumber());
				form.setString("itemId",0);
				form.setCurentTimestamp("createOn");
				if (obj!=null) {
					Set itemGroupLst = obj.getItemGroups();
					request.setAttribute("itemGroupLst", itemGroupLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
		    if (obj==null) {
		        obj = InventoryDAO.getInstance().get(form.getLong("itemId"));
						httpSession.setAttribute("inventory",obj);
						request.setAttribute("inventory",obj);
				}
			    Set itemGroupLst = obj.getItemGroups();
				request.setAttribute("itemGroupLst", itemGroupLst);
			}
			// item vendor
			if (form.getLong("groupId") > 0) {
				Iterator iterator = obj.getItemGroups().iterator();
				while (iterator.hasNext()) {
					ItemGroup itemGroup = (ItemGroup)iterator.next();
					if (form.getLong("groupId") == itemGroup.getId().getGroup().getId()) {
						form.setString("quantity", itemGroup.getQuantity());
						form.setString("note", itemGroup.getNote());
						form.setString("itemUnitId", itemGroup.getItemUnit()!=null?itemGroup.getItemUnit().getId():0);
					}
				}
			}
		}catch(Exception ex) {
			try {
				List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("itemUnitLst",itemUnitLst);
			}catch(Exception exx) {
			}
			ex.printStackTrace();
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
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
	private ActionForward performGroupSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("inventory");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Inventory obj = (Inventory)httpSession.getAttribute("inventory");
			if (form.getLong("itemId") == 0) {
			  if (obj==null) obj = new Inventory();
			} else {
			  if (obj==null) obj = InventoryDAO.getInstance().get(form.getLong("itemId"));
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setOrganization(users.getOrganization());
			}
			// item vendor
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDITEMVENDOR")) {
				if (form.getLong("groupId") >0 && form.getDouble("quantity")>0) {
					//log.info("A");
					ItemGroup itemGroup = new ItemGroup();
					ItemUnit itemUnit = ItemUnitDAO.getInstance().get(form.getLong("itemUnitId"));
					Item group = ItemDAO.getInstance().get(form.getLong("groupId"));
					ItemGroupPK itemGroupPK = new ItemGroupPK(obj, group);
					itemGroup.setId(itemGroupPK);
					itemGroup.setItemUnit(itemUnit);
					itemGroup.setNote(form.getString("note"));
					itemGroup.setQuantity(form.getDouble("quantity"));
					Set set = obj.getItemGroups();
					if (set==null) set = new LinkedHashSet();
					ItemGroup removeItemGroup = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						ItemGroup itemGroup2 = (ItemGroup)iterator.next();
						if (form.getLong("groupId")==itemGroup2.getId().getGroup().getId()) {
							removeItemGroup = itemGroup2;
						}
					}
					if (removeItemGroup!=null) {
						set.remove(removeItemGroup);
						set.add(itemGroup);
					} else {
						set.add(itemGroup);
					}
					//log.info("S : "+set.size());
					obj.setItemCustomFields(set);
					// netral
					form.setString("groupId", "");
					form.setString("itemUnitId", "");
					form.setString("quantity", "");
					form.setString("note", "");
				}
				// netral
				form.setString("groupId", "");
				form.setString("itemUnitId", "");
				form.setString("quantity", "");
				form.setString("note", "");
			}
			
			// save to session
			httpSession.setAttribute("inventory", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					Session session = InventoryDAO.getInstance().getSession();
					Transaction transaction = session.beginTransaction();
					//obj.setCode(form.getString("itemTypeCode")+form.getString("itemGenderCode")+form.getString("itemMerkCode")+obj.getCode()+form.getString("itemColorCode")+form.getString("itemSizeCode"));
					RunningNumberDAO.getInstance().updateItemNumber(session);
					InventoryDAO.getInstance().save(obj, session);
					transaction.commit();
					//session.flush();
				} else {
					Transaction transaction = InventoryDAO.getInstance().getSession().beginTransaction();
					InventoryDAO.getInstance().getSession().merge(obj);
					transaction.commit();
				}
				// remove session
				httpSession.removeAttribute("inventory");
				// finish
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				try {
					List itemUnitLst = ItemUnitDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("itemUnitLst",itemUnitLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?itemId="+form.getLong("itemId"));
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
	private ActionForward performCustomFieldForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// remove item price
			Inventory obj = (Inventory)httpSession.getAttribute("inventory");
			// remove item vendor
			if (form.getLong("customFieldId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEITEMVENDOR")) {
				ItemCustomField removeItemCustomField = null;
				Iterator iterator = obj.getItemGroups().iterator();
				while (iterator.hasNext()) {
					ItemCustomField itemCustomField = (ItemCustomField)iterator.next();
					if (form.getLong("customFieldId") == itemCustomField.getId().getCustomField().getId()) {
						removeItemCustomField = itemCustomField;
					}
				}
				if (removeItemCustomField!=null) {
					Set set = obj.getItemCustomFields();
					set.remove(removeItemCustomField);
					obj.setItemCustomFields(set);
				}
				form.setString("subaction", "");
				form.setString("customFieldId", "");
				httpSession.setAttribute("inventory", obj);
			}
			
			// relationships
			List customFieldLst = CustomFieldDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("customFieldLst",customFieldLst);
			if (form.getLong("itemId") == 0) {
				form.setString("itemId",0);
				form.setCurentTimestamp("createOn");
				if (obj!=null) {
					Set itemCustomFieldLst = obj.getItemCustomFields();
					request.setAttribute("itemCustomFieldLst", itemCustomFieldLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
		    if (obj==null) {
		        obj = InventoryDAO.getInstance().get(form.getLong("itemId"));
					httpSession.setAttribute("inventory",obj);
					request.setAttribute("inventory",obj);
				}
			    Set itemCustomFieldLst = obj.getItemCustomFields();
				request.setAttribute("itemCustomFieldLst", itemCustomFieldLst);
			}
			// item vendor
			if (form.getLong("customFieldId") > 0) {
				Iterator iterator = obj.getItemCustomFields().iterator();
				while (iterator.hasNext()) {
					ItemCustomField itemCustomField = (ItemCustomField)iterator.next();
					if (form.getLong("customFieldId") == itemCustomField.getId().getCustomField().getId()) {
						form.setString("value", itemCustomField.getValue());
					}
				}
			}
		}catch(Exception ex) {
			try {
				List customFieldLst = CustomFieldDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("customFieldLst",customFieldLst);
			}catch(Exception exx) {
			}
			ex.printStackTrace();
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
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
	private ActionForward performCustomFieldSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("inventory");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Inventory obj = (Inventory)httpSession.getAttribute("inventory");
			if (form.getLong("itemId") == 0) {
			  if (obj==null) obj = new Inventory();
			} else {
			  if (obj==null) obj = InventoryDAO.getInstance().get(form.getLong("itemId"));
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setOrganization(users.getOrganization());
			}
			// item vendor
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDITEMVENDOR")) {
					if (form.getLong("customFieldId") >0) {
						//log.info("A");
						ItemCustomField itemCustomField = new ItemCustomField();
						CustomField customField = CustomFieldDAO.getInstance().get(form.getLong("customFieldId"));
						ItemCustomFieldPK itemCustomFieldPK = new ItemCustomFieldPK(obj, customField);
						itemCustomField.setId(itemCustomFieldPK);
						itemCustomField.setValue(form.getString("value").length()>0?form.getString("value"):customField.getDefaultValue());
						Set set = obj.getItemCustomFields();
						if (set==null) set = new LinkedHashSet();
						ItemCustomField removeItemCustomField = null;
						Iterator iterator = set.iterator();
						while (iterator.hasNext()) {
							ItemCustomField itemCustomField2 = (ItemCustomField)iterator.next();
							if (form.getLong("customFieldId")==itemCustomField2.getId().getCustomField().getId()) {
								removeItemCustomField = itemCustomField2;
							}
						}
						if (removeItemCustomField!=null) {
							set.remove(removeItemCustomField);
							set.add(itemCustomField);
						} else {
							set.add(itemCustomField);
						}
						//log.info("S : "+set.size());
						obj.setItemCustomFields(set);
						// netral
						form.setString("customFieldId", "");
						form.setString("value", "");
					}
					// netral
					form.setString("customFieldId", "");
					form.setString("value", "");
				}
				
				// save to session
				httpSession.setAttribute("inventory", obj);
				// save all
				if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
					if (obj.getId()==0) {
						Session session = InventoryDAO.getInstance().getSession();
						Transaction transaction = session.beginTransaction();
						//obj.setCode(form.getString("itemTypeCode")+form.getString("itemGenderCode")+form.getString("itemMerkCode")+obj.getCode()+form.getString("itemColorCode")+form.getString("itemSizeCode"));
						RunningNumberDAO.getInstance().updateItemNumber(session);
						InventoryDAO.getInstance().save(obj, session);
						transaction.commit();
						//session.flush();
					} else {
						Transaction transaction = InventoryDAO.getInstance().getSession().beginTransaction();
						InventoryDAO.getInstance().getSession().merge(obj);
						transaction.commit();
					}
					// remove session
					httpSession.removeAttribute("inventory");
					// finish
					ActionForward forward = mapping.findForward("list_redir");
					StringBuffer sb = new StringBuffer(forward.getPath());
					sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
					return new ActionForward(sb.toString(),true);
				}
		}catch(Exception ex) {
			try {
				List customFieldLst = CustomFieldDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("customFieldLst",customFieldLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?itemId="+form.getLong("itemId"));
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
		ItemForm form = (ItemForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			Inventory inventory = InventoryDAO.getInstance().get(form.getLong("itemId"));
			request.setAttribute("inventory", inventory);
		}catch(Exception ex) {
			ex.printStackTrace();
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
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
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			ItemDAO.getInstance().delete(form.getLong("itemId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performShowImage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		try {
			Item item = ItemDAO.getInstance().get(form.getLong("itemId"));
			if (item != null) {
				//log.info("[Item image : "+itemImage.getItemImageContentType()+" // "+itemImage.getItemImage().length+"]");
				request.setAttribute("contentType", item.getImageContentType());
				StringBuffer out = new StringBuffer();
				out.append(new String(item.getImage(), 0, item.getImage().length, "ISO-8859-1"));
				request.setAttribute("image", out.toString());
			}
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			log.info("Err show image : "+ex);
			return mapping.findForward("detail");
		}finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("detail");
	}
		
	/** 
	 * Method generalError
	 * @param HttpServletRequest request
	 * @param Exception ex
	 */
	private void generalError(HttpServletRequest request, Exception ex) {
		ActionMessages errors = new ActionMessages();
		errors.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.global", ex));
		saveErrors(request,errors);
	}

}