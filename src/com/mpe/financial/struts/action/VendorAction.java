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

import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.VendorsAddress;
import com.mpe.financial.model.VendorsAddressPK;
import com.mpe.financial.model.VendorsCategory;
import com.mpe.financial.model.VendorsCommunication;
import com.mpe.financial.model.VendorsCommunicationPK;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.VendorsCategoryDAO;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.struts.form.VendorsForm;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class VendorAction extends Action {
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
		//VendorsForm vendorForm = (VendorsForm) form;
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
		VendorsForm form = (VendorsForm) actionForm;
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
			List vendorCategoryLst = VendorsCategoryDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("vendorCategoryLst", vendorCategoryLst);
			Criteria criteria = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class);
			if (form.getString("company")!=null && form.getString("company").length()>0)criteria.add(Restrictions.like("Company", "%"+form.getString("company")+"%"));
			if (form.getLong("vendorCategoryId")>0) criteria.add(Restrictions.eq("VendorsCategory.Id", new Long(form.getLong("vendorCategoryId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class);
			if (form.getString("company")!=null && form.getString("company").length()>0)criteria.add(Restrictions.like("Company", "%"+form.getString("company")+"%"));
			if (form.getLong("vendorCategoryId")>0) criteria.add(Restrictions.eq("VendorsCategory.Id", new Long(form.getLong("vendorCategoryId"))));
			//criteria.addOrder(Order.asc("CompanyName"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("VENDOR",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("vendor");
			
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				VendorsDAO.getInstance().closeSessionForReal();
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
		VendorsForm form = (VendorsForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// relationships
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
		  .add(Restrictions.eq("Type", new String("Account payable"))).addOrder(Order.asc("Name")).list();
		  request.setAttribute("chartOfAccountLst", chartOfAccountLst);
		  List vendorCategoryLst = VendorsCategoryDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("vendorCategoryLst", vendorCategoryLst);
			if (form.getLong("vendorId") == 0) {
				form.setString("vendorId",0);
				form.setCurentTimestamp("createOn");
				form.setString("isActive", "Y");
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				Vendors obj = VendorsDAO.getInstance().get(form.getLong("vendorId"));
				form.setString("vendorId",obj.getId());
				form.setString("company",obj.getCompany());
				form.setString("areaCode",obj.getAreaCode());
				form.setString("address",obj.getAddress());
				form.setString("code",obj.getCode());
				form.setString("npwp",obj.getNpwp());
				form.setString("chartOfAccountId",obj.getChartOfAccount()!=null?obj.getChartOfAccount().getId():0);
				form.setString("vendorCategoryId",obj.getVendorsCategory()!=null?obj.getVendorsCategory().getId():0);
				form.setString("city",obj.getCity());
				form.setString("postalCode",obj.getPostalCode());
				form.setString("province",obj.getProvince());
				form.setString("country",obj.getCountry());
				form.setString("telephone",obj.getTelephone());
				form.setString("fax",obj.getFax());
				form.setString("email",obj.getEmail());
				form.setString("isActive",obj.isActive()==true?"Y":"N");
				form.setString("isImport",obj.isImport()==true?"Y":"N");
				form.setString("isBlockOverCreditLimit",obj.isBlockOverCreditLimit()==true?"Y":"N");
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				form.setString("description",obj.getDescription());
				//form.setString("contactPerson", obj.getContactPerson());
				//form.setString("position", obj.getPosition());
				form.setString("creditLimit", Formater.getFormatedOutputForm(organizationSetup.getNumberOfDigit(), obj.getCreditLimit()));
			}
		}catch(Exception ex) {
			try {
			    List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
				  .add(Restrictions.eq("Type", new String("Account payable"))).addOrder(Order.asc("Name")).list();
				  request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				  List vendorCategoryLst = VendorsCategoryDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("vendorCategoryLst", vendorCategoryLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				VendorsDAO.getInstance().closeSessionForReal();
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
		VendorsForm form = (VendorsForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Vendors obj = new Vendors();
			if (form.getLong("vendorId") == 0) {
				  //obj.setContactPerson(form.getString("contactPerson"));
				  //obj.setPosition(form.getString("position"));
					obj.setCompany(form.getString("company"));
					obj.setAreaCode(form.getString("areaCode"));
					ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
					obj.setChartOfAccount(chartOfAccount);
					VendorsCategory vendorsCategory = VendorsCategoryDAO.getInstance().get(form.getLong("vendorCategoryId"));
					obj.setVendorsCategory(vendorsCategory);
					obj.setCode(form.getString("code"));
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
					obj.setImport(form.getString("isImport").length()>0?true:false);
					obj.setBlockOverCreditLimit(form.getString("isBlockOverCreditLimit").length()>0?true:false);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
					obj.setDescription(form.getString("description"));
					obj.setCreditLimit(form.getDouble("creditLimit"));
					VendorsDAO.getInstance().save(obj);
			} else {
			    //obj.setContactPerson(form.getString("contactPerson"));
				  //obj.setPosition(form.getString("position"));
					obj.setCompany(form.getString("company"));
					obj.setAreaCode(form.getString("areaCode"));
					ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
					obj.setChartOfAccount(chartOfAccount);
					VendorsCategory vendorsCategory = VendorsCategoryDAO.getInstance().get(form.getLong("vendorCategoryId"));
					obj.setVendorsCategory(vendorsCategory);
					obj.setCode(form.getString("code"));
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
					obj.setImport(form.getString("isImport").length()>0?true:false);
					obj.setBlockOverCreditLimit(form.getString("isBlockOverCreditLimit").length()>0?true:false);
					Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
					obj.setCreateBy(createBy);
					obj.setCreateOn(form.getTimestamp("createOn"));
					obj.setChangeBy(users);
					obj.setChangeOn(form.getTimestamp("changeOn"));
					obj.setId(form.getLong("vendorId"));
					obj.setDescription(form.getString("description"));
					obj.setCreditLimit(form.getDouble("creditLimit"));
					VendorsDAO.getInstance().update(obj);
			}
		}catch(Exception ex) {
			try {
				try {
					  List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
					  request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					  List vendorCategoryLst = VendorsCategoryDAO.getInstance().findAll(Order.asc("Name"));
						request.setAttribute("vendorCategoryLst", vendorCategoryLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				VendorsDAO.getInstance().closeSessionForReal();
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
		VendorsForm form = (VendorsForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			Vendors vendor = VendorsDAO.getInstance().get(form.getLong("vendorId"));
			request.setAttribute("vendor", vendor);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				VendorsDAO.getInstance().closeSessionForReal();
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
		VendorsForm form = (VendorsForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			VendorsDAO.getInstance().delete(form.getLong("vendorId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				VendorsDAO.getInstance().closeSessionForReal();
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
		VendorsForm form = (VendorsForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			Vendors vendor = (Vendors)httpSession.getAttribute("vendor");
			// remove
			if (form.getString("addressCode").length() > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEDETAIL")) {
				VendorsAddress removeVendorsAddress = null;
				Iterator iterator = vendor.getVendorsAddresses().iterator();
				while (iterator.hasNext()) {
					VendorsAddress vendorAddress = (VendorsAddress)iterator.next();
					if (form.getString("addressCode").equalsIgnoreCase(vendorAddress.getId().getAddressCode())) {
						removeVendorsAddress = vendorAddress;
					}
				}
				if (removeVendorsAddress!=null) {
					Set set = vendor.getVendorsAddresses();
					set.remove(removeVendorsAddress);
					vendor.setVendorsAddresses(set);
				}
				form.setString("subaction", "");
				form.setString("addressCode", "");
				httpSession.setAttribute("vendor", vendor);
			}
			// relationships
			if (form.getLong("vendorId") > 0) {
				if (vendor==null) {
					vendor = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					httpSession.setAttribute("vendor", vendor);
				}
				request.setAttribute("vendor", vendor);
				Set vendorAddressLst = vendor.getVendorsAddresses();
				request.setAttribute("vendorAddressLst", vendorAddressLst);
				
			}
			if (form.getString("addressCode").length() > 0) {
				Iterator iterator = vendor.getVendorsAddresses().iterator();
				while (iterator.hasNext()) {
					VendorsAddress vendorAddress = (VendorsAddress)iterator.next();
					if (form.getString("addressCode").equalsIgnoreCase(vendorAddress.getId().getAddressCode())) {
						form.setString("address", vendorAddress.getAddress());
						form.setString("name", vendorAddress.getName());
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
				VendorsDAO.getInstance().closeSessionForReal();
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
		VendorsForm form = (VendorsForm) actionForm;
		HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Vendors obj = (Vendors) httpSession.getAttribute("vendor");
			if (form.getLong("vendorId") > 0) {
				if (obj==null) obj = VendorsDAO.getInstance().get(form.getLong("vendorId"));
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDDETAIL")) {
				if (form.getString("addressCode").length() >0) {
					VendorsAddress vendorAddress = new VendorsAddress();
					VendorsAddressPK vendorAddressPK = new VendorsAddressPK();
					vendorAddressPK.setVendors(obj);
					vendorAddressPK.setAddressCode(form.getString("addressCode"));
					vendorAddress.setId(vendorAddressPK);
					vendorAddress.setAddress(form.getString("address"));
					vendorAddress.setName(form.getString("name"));
					Set set = obj.getVendorsAddresses();
					if (set==null) set = new LinkedHashSet();
					VendorsAddress removeVendorsAddress = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						VendorsAddress vendorAddress2 = (VendorsAddress)iterator.next();
						if (form.getString("addressCode").equalsIgnoreCase(vendorAddress2.getId().getAddressCode())) {
							removeVendorsAddress = vendorAddress2;
						}
					}
					if (removeVendorsAddress!=null) {
						set.remove(removeVendorsAddress);
						set.add(vendorAddress);
					} else {
						set.add(vendorAddress);
					}
					obj.setVendorsAddresses(set);
				}
				// netral
				form.setString("addressCode", "");
				form.setString("address", "");
				form.setString("name", "");
				form.setString("subaction", "");
				httpSession.setAttribute("vendor", obj);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				Session session = VendorsDAO.getInstance().getSession();
				Transaction transaction = session.beginTransaction();
				VendorsDAO.getInstance().getSession().merge(obj);
				transaction.commit();
				httpSession.removeAttribute("vendor");
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
				VendorsDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?vendorId="+form.getLong("vendorId"));
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
		VendorsForm form = (VendorsForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			Vendors vendor = (Vendors)httpSession.getAttribute("vendor");
			// remove
			if (form.getString("contactPerson").length() > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEDETAIL")) {
				VendorsCommunication removeVendorsCommunication = null;
				Iterator iterator = vendor.getVendorsCommunications().iterator();
				while (iterator.hasNext()) {
					VendorsCommunication vendorCommunication = (VendorsCommunication)iterator.next();
					if (form.getString("contactPerson").equalsIgnoreCase(vendorCommunication.getId().getContactPerson())) {
						removeVendorsCommunication = vendorCommunication;
					}
				}
				if (removeVendorsCommunication!=null) {
					Set set = vendor.getVendorsCommunications();
					set.remove(removeVendorsCommunication);
					vendor.setVendorsCommunications(set);
				}
				form.setString("subaction", "");
				form.setString("contact", "");
				httpSession.setAttribute("vendor", vendor);
			}
			// relationships
			if (form.getLong("vendorId") > 0) {
				if (vendor==null) {
					vendor = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					httpSession.setAttribute("vendor", vendor);
				}
				request.setAttribute("vendor", vendor);
				Set vendorCommunicationLst = vendor.getVendorsCommunications();
				request.setAttribute("vendorCommunicationLst", vendorCommunicationLst);
				
			}
			if (form.getString("contactPerson").length() > 0) {
				Iterator iterator = vendor.getVendorsCommunications().iterator();
				while (iterator.hasNext()) {
					VendorsCommunication vendorCommunication = (VendorsCommunication)iterator.next();
					if (form.getString("contactPerson").equalsIgnoreCase(vendorCommunication.getId().getContactPerson())) {
						form.setString("title", vendorCommunication.getTitle());
						form.setString("fax", vendorCommunication.getFax());
						form.setString("email", vendorCommunication.getEmail());
						form.setString("mobile", vendorCommunication.getMobile());
						form.setString("officePhone", vendorCommunication.getOfficePhone());
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
				VendorsDAO.getInstance().closeSessionForReal();
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
		VendorsForm form = (VendorsForm) actionForm;
		HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Vendors obj = (Vendors) httpSession.getAttribute("vendor");
			if (form.getLong("vendorId") > 0) {
				if (obj==null) obj = VendorsDAO.getInstance().get(form.getLong("vendorId"));
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDDETAIL")) {
				if (form.getString("contactPerson").length() >0) {
					VendorsCommunication vendorCommunication = new VendorsCommunication();
					VendorsCommunicationPK vendorCommunicationPK = new VendorsCommunicationPK();
					vendorCommunicationPK.setVendors(obj);
					vendorCommunicationPK.setContactPerson(form.getString("contactPerson"));
					vendorCommunication.setId(vendorCommunicationPK);
					vendorCommunication.setTitle(form.getString("title"));
					vendorCommunication.setFax(form.getString("fax"));
					vendorCommunication.setMobile(form.getString("mobile"));
					vendorCommunication.setEmail(form.getString("email"));
					vendorCommunication.setOfficePhone(form.getString("officePhone"));
					Set set = obj.getVendorsCommunications();
					if (set==null) set = new LinkedHashSet();
					VendorsCommunication removeVendorsCommunication = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						VendorsCommunication vendorCommunication2 = (VendorsCommunication)iterator.next();
						if (form.getString("contactPerson").equalsIgnoreCase(vendorCommunication2.getId().getContactPerson())) {
							removeVendorsCommunication = vendorCommunication2;
						}
					}
					if (removeVendorsCommunication!=null) {
						set.remove(removeVendorsCommunication);
						set.add(vendorCommunication);
					} else {
						set.add(vendorCommunication);
					}
					obj.setVendorsCommunications(set);
				}
				// netral
				form.setString("contactPerson", "");
				form.setString("title", "");
				form.setString("fax", "");
				form.setString("email", "");
				form.setString("officePhone", "");
				form.setString("mobile", "");
				form.setString("subaction", "");
				httpSession.setAttribute("vendor", obj);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				Session session = VendorsDAO.getInstance().getSession();
				Transaction transaction = session.beginTransaction();
				VendorsDAO.getInstance().getSession().merge(obj);
				transaction.commit();
				httpSession.removeAttribute("vendor");
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
				VendorsDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?vendorId="+form.getLong("vendorId"));
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