//---------------------------------------------------------
// Application: Garage
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright © 2005 MPE
// Generated at Tue Sep 06 20:58:49 GMT+07:00 2005
//---------------------------------------------------------

package com.mpe.financial.struts.action;

import javax.servlet.ServletOutputStream;
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

import com.lowagie.text.Cell;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.mpe.financial.model.BankTransaction;
import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.Currency;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.Department;
import com.mpe.financial.model.GeneralLedger;
import com.mpe.financial.model.Journal;
import com.mpe.financial.model.JournalDetail;
import com.mpe.financial.model.JournalDetailPK;
import com.mpe.financial.model.JournalType;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.dao.BankTransactionDAO;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.GeneralLedgerDAO;
import com.mpe.financial.model.dao.JournalDAO;
import com.mpe.financial.model.dao.JournalTypeDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.struts.form.JournalForm;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

import com.mpe.common.*;

public class JournalAction extends Action {
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
		JournalForm journalForm = (JournalForm) form;
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
						if (journalForm.getString("subaction")!=null && journalForm.getString("subaction").length()>0) {
							forward = performPostingForm(mapping, form, request, response);
						} else {
							forward = performPartialList(mapping, form, request, response);
						}
					} else if ("FORM".equalsIgnoreCase(action)) {
						forward = performForm(mapping, form, request, response);
					} else if ("SAVE".equalsIgnoreCase(action)) {
						forward = performSave(mapping, form, request, response);
					} else if ("DETAIL".equalsIgnoreCase(action)) { 
						forward = performDetail(mapping, form, request, response);
					} else if ("DELETE".equalsIgnoreCase(action)) {
						forward = performDelete(mapping, form, request, response);
					} else if ("POSTINGFORM".equalsIgnoreCase(action)) {
						forward = performPostingForm(mapping, form, request, response);
					} else if ("POSTINGCONFIRM".equalsIgnoreCase(action)) {
						forward = performPostingConfirm(mapping, form, request, response);
					} else if ("UNPOSTINGFORM".equalsIgnoreCase(action)) {
						forward = performUnpostingForm(mapping, form, request, response);
					} else if ("UNPOSTINGCONFIRM".equalsIgnoreCase(action)) {
						forward = performUnpostingConfirm(mapping, form, request, response);
					} else if ("POSTINGDATEFORM".equalsIgnoreCase(action)) {
						forward = performPostingDateForm(mapping, form, request, response);
					} else if ("POSTINGDATECONFIRM".equalsIgnoreCase(action)) {
						forward = performPostingDateConfirm(mapping, form, request, response);
					} else if ("PDF".equalsIgnoreCase(action)) { 
						forward = performPdf(mapping, form, request, response);
					} else if ("REPORTPDF".equalsIgnoreCase(action)) { 
						forward = performReportPdf(mapping, form, request, response);
					} else if ("APLIST".equalsIgnoreCase(action)) {
						if (journalForm.getString("subaction")!=null && journalForm.getString("subaction").length()>0) {
							forward = performApPostingForm(mapping, form, request, response);
						} else {
							forward = performApList(mapping, form, request, response);
						}
					} else if ("APFORM".equalsIgnoreCase(action)) {
						forward = performApForm(mapping, form, request, response);
					} else if ("APSAVE".equalsIgnoreCase(action)) {
						forward = performApSave(mapping, form, request, response);
					} else if ("APDETAIL".equalsIgnoreCase(action)) { 
						forward = performApDetail(mapping, form, request, response);
					} else if ("APDELETE".equalsIgnoreCase(action)) {
						forward = performApDelete(mapping, form, request, response);
					} else if ("APPOSTINGFORM".equalsIgnoreCase(action)) {
						forward = performApPostingForm(mapping, form, request, response);
					} else if ("APPOSTINGCONFIRM".equalsIgnoreCase(action)) {
						forward = performApPostingConfirm(mapping, form, request, response);
					} else if ("APUNPOSTINGFORM".equalsIgnoreCase(action)) {
						forward = performApUnpostingForm(mapping, form, request, response);
					} else if ("APUNPOSTINGCONFIRM".equalsIgnoreCase(action)) {
						forward = performApUnpostingConfirm(mapping, form, request, response);
					} else if ("APPOSTINGDATEFORM".equalsIgnoreCase(action)) {
						forward = performApPostingDateForm(mapping, form, request, response);
					} else if ("APPOSTINGDATECONFIRM".equalsIgnoreCase(action)) {
						forward = performApPostingDateConfirm(mapping, form, request, response);
					} else if ("APPDF".equalsIgnoreCase(action)) { 
						forward = performApPdf(mapping, form, request, response);
					} else if ("APREPORTPDF".equalsIgnoreCase(action)) { 
						forward = performApReportPdf(mapping, form, request, response);
					} else if ("ARLIST".equalsIgnoreCase(action)) {
						if (journalForm.getString("subaction")!=null && journalForm.getString("subaction").length()>0) {
							forward = performArPostingForm(mapping, form, request, response);
						} else {
							forward = performArList(mapping, form, request, response);
						}
					} else if ("ARFORM".equalsIgnoreCase(action)) {
						forward = performArForm(mapping, form, request, response);
					} else if ("ARSAVE".equalsIgnoreCase(action)) {
						forward = performArSave(mapping, form, request, response);
					} else if ("ARDETAIL".equalsIgnoreCase(action)) { 
						forward = performArDetail(mapping, form, request, response);
					} else if ("ARDELETE".equalsIgnoreCase(action)) {
						forward = performArDelete(mapping, form, request, response);
					} else if ("ARPOSTINGFORM".equalsIgnoreCase(action)) {
						forward = performArPostingForm(mapping, form, request, response);
					} else if ("ARPOSTINGCONFIRM".equalsIgnoreCase(action)) {
						forward = performArPostingConfirm(mapping, form, request, response);
					} else if ("ARUNPOSTINGFORM".equalsIgnoreCase(action)) {
						forward = performArUnpostingForm(mapping, form, request, response);
					} else if ("ARUNPOSTINGCONFIRM".equalsIgnoreCase(action)) {
						forward = performArUnpostingConfirm(mapping, form, request, response);
					} else if ("ARPOSTINGDATEFORM".equalsIgnoreCase(action)) {
						forward = performArPostingDateForm(mapping, form, request, response);
					} else if ("ARPOSTINGDATECONFIRM".equalsIgnoreCase(action)) {
						forward = performArPostingDateConfirm(mapping, form, request, response);
					} else if ("ARPDF".equalsIgnoreCase(action)) { 
						forward = performArPdf(mapping, form, request, response);
					} else if ("ARREPORTPDF".equalsIgnoreCase(action)) { 
						forward = performArReportPdf(mapping, form, request, response);
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
		JournalForm form = (JournalForm) actionForm;
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
			if (form.getString("isPosted")==null || form.getString("isPosted").length()==0) form.setString("isPosted", "N");
			//save start and count attribute on session
			httpSession.setAttribute(CommonConstants.START,Integer.toString(start));
			httpSession.setAttribute(CommonConstants.COUNT,Integer.toString(count));
			List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("journalTypeLst", journalTypeLst);
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("projectLst", projectLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
			Criteria criteria = JournalDAO.getInstance().getSession().createCriteria(Journal.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("journalTypeId")>0) criteria.add(Restrictions.eq("JournalType.Id", new Long(form.getLong("journalTypeId"))));
			if (form.getString("isPosted")!=null && form.getString("isPosted").equalsIgnoreCase("Y"))criteria.add(Restrictions.eq("Posted", Boolean.TRUE));
			if (form.getString("isPosted")!=null && form.getString("isPosted").equalsIgnoreCase("N"))criteria.add(Restrictions.eq("Posted", Boolean.FALSE));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			if (form.getCalendar("journalDate")!=null)criteria.add(Restrictions.eq("JournalDate", new Date(form.getCalendar("journalDate").getTime().getTime())));
			if (form.getLong("departmentId")>0) criteria.createCriteria("JournalDetails", "JournalDetail").add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = JournalDAO.getInstance().getSession().createCriteria(Journal.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			if (form.getCalendar("journalDate")!=null)criteria.add(Restrictions.eq("JournalDate", new Date(form.getCalendar("journalDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("journalTypeId")>0) criteria.add(Restrictions.eq("JournalType.Id", new Long(form.getLong("journalTypeId"))));
			if (form.getString("isPosted")!=null && form.getString("isPosted").equalsIgnoreCase("Y"))criteria.add(Restrictions.eq("Posted", Boolean.TRUE));
			if (form.getString("isPosted")!=null && form.getString("isPosted").equalsIgnoreCase("N"))criteria.add(Restrictions.eq("Posted", Boolean.FALSE));
			if (form.getLong("departmentId")>0) criteria.createCriteria("JournalDetails", "JournalDetail").add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			//criteria.addOrder(Order.desc("Name"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("JOURNAL",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
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
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// remove
			Journal obj = (Journal)httpSession.getAttribute("journal");
			if (form.getLong("chartOfAccountId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEJOURNALDETAIL")) {
				JournalDetail removeJournalDetail = null;
				Iterator iterator = obj.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail)iterator.next();
					if (form.getLong("chartOfAccountId") == journalDetail.getId().getChartOfAccount().getId() && form.getInt("chartOfAccountSequence")==journalDetail.getId().getChartOfAccountSequence()) {
							removeJournalDetail = journalDetail;
					}
				}
				if (removeJournalDetail!=null) {
					Set set = obj.getJournalDetails();
					set.remove(removeJournalDetail);
					obj.setJournalDetails(set);
				}
				form.setString("subaction", "");
				form.setString("chartOfAccountId", "");
				form.setString("chartOfAccountSequence", "");
				httpSession.setAttribute("journal", obj);
			}
			// relationships
			List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("journalTypeLst", journalTypeLst);
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Number"));
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
			List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("vendorLst", vendorLst);
			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerLst", customerLst);
			List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("currencyLst", currencyLst);
			if (form.getLong("journalId") == 0) {
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				if (obj==null) {
					form.setString("journalId",0);
					form.setCurentTimestamp("createOn");
					form.setString("number", RunningNumberDAO.getInstance().getJournalNumber());
					form.setCurentCalendar("journalDate");
				} else if (obj!=null) {
					form.setString("journalId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setString("number",obj.getNumber());
					form.setString("reference",obj.getReference());
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
					form.setCalendar("journalDate",obj.getJournalDate());
					form.setString("journalTypeId",obj.getJournalType()!=null?obj.getJournalType().getId():0);
					form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
					Set journalDetailLst = obj.getJournalDetails();
					request.setAttribute("journalDetailLst", journalDetailLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = JournalDAO.getInstance().get(form.getLong("journalId"));
					httpSession.setAttribute("journal",obj);
				}
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				//httpSession.setAttribute("Journal",obj);
				form.setString("journalId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("number",obj.getNumber());
				form.setString("reference",obj.getReference());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				form.setCalendar("journalDate",obj.getJournalDate());
				form.setString("journalTypeId",obj.getJournalType()!=null?obj.getJournalType().getId():0);
				form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set journalDetailLst = obj.getJournalDetails();
				request.setAttribute("journalDetailLst", journalDetailLst);
			}
			if (obj!=null && form.getLong("chartOfAccountId") > 0) {
				Iterator iterator = obj.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail)iterator.next();
					if (form.getLong("chartOfAccountId") == journalDetail.getId().getChartOfAccount().getId() && form.getInt("chartOfAccountSequence")==journalDetail.getId().getChartOfAccountSequence()) {
						form.setString("amount", Formater.getFormatedOutputForm(journalDetail.getAmount()));
						form.setString("departmentId", journalDetail.getDepartment()!=null?journalDetail.getDepartment().getId():0);
						form.setString("journalDetailDescription", journalDetail.getDescription());
						form.setString("chartOfAccountSequence", journalDetail.getId().getChartOfAccountSequence());
						form.setString("chartOfAccountNumber", journalDetail.getId().getChartOfAccount().getNumber());
					}
				}
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			// get total
			double debitAmount = 0;
			double creditAmount = 0;
			if (obj!=null) {
				java.util.Iterator iterator = obj.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail) iterator.next();
					if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
						if (journalDetail.getAmount()>0) {
							debitAmount = debitAmount + journalDetail.getAmount();
						} else {
							creditAmount = creditAmount + (-journalDetail.getAmount());
						}
					} else if (journalDetail.getId().getChartOfAccount().isDebit()==false){
						if (journalDetail.getAmount()<0) {
							debitAmount = debitAmount + (-journalDetail.getAmount());
						} else {
							creditAmount = creditAmount + journalDetail.getAmount();
						}
					}
				}
			}
			request.setAttribute("debitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitAmount));
			request.setAttribute("creditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditAmount));
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
				List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("journalTypeLst", journalTypeLst);
				List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Number"));
				request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("departmentLst", departmentLst);
				List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("vendorLst", vendorLst);
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("currencyLst", currencyLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
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
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("journal");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Journal obj = (Journal)httpSession.getAttribute("journal");
			//if (obj==null) obj = new Journal();
			if (form.getLong("journalId") == 0) {
				obj = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
				.add(Restrictions.eq("Number", form.getString("number")))
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.uniqueResult();
				if (obj==null) {
					obj = (Journal)httpSession.getAttribute("journal");
					if (obj==null) obj = new Journal();
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					obj.setDescription(form.getString("description"));
					OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
					//log.info("ER : "+CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup));
					obj.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("journalDate")));
					obj.setJournalDate(form.getCalendar("journalDate")!=null?form.getCalendar("journalDate").getTime():null);
					JournalType journalType = JournalTypeDAO.getInstance().get(form.getLong("journalTypeId"));
					obj.setJournalType(journalType);
					obj.setNumber(form.getString("number"));
					obj.setOrganization(users.getOrganization());
					obj.setNumberOfDigit(organizationSetup.getNumberOfDigit());
					obj.setPosted(false);
					obj.setReference(form.getString("reference"));
					Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(vendors);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
				} else {
					List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("journalTypeLst", journalTypeLst);
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
					List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("vendorLst", vendorLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("currencyLst", currencyLst);
					Set journalDetailLst = obj!=null?obj.getJournalDetails():new LinkedHashSet();
					request.setAttribute("journalDetailLst", journalDetailLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					// err
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = JournalDAO.getInstance().get(form.getLong("journalId"));
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setCustomer(customers);
				obj.setDescription(form.getString("description"));
				//OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
				//obj.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup));
				obj.setJournalDate(form.getCalendar("journalDate")!=null?form.getCalendar("journalDate").getTime():null);
				JournalType journalType = JournalTypeDAO.getInstance().get(form.getLong("journalTypeId"));
				obj.setJournalType(journalType);
				obj.setNumber(form.getString("number"));
				obj.setOrganization(users.getOrganization());
				//obj.setPosted(false);
				obj.setReference(form.getString("reference"));
				Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
				obj.setVendor(vendors);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("journalId"));
				//JournalDAO.getInstance().update(obj);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDJOURNALDETAIL")) {
				if (form.getLong("chartOfAccountId") >0 || form.getString("chartOfAccountNumber").length()>0) {
				  JournalDetail journalDetail = new JournalDetail();
					ChartOfAccount chartOfAccount = null;
					if (form.getLong("chartOfAccountId")>0) chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
					if (form.getString("chartOfAccountNumber").length()>0) chartOfAccount = (ChartOfAccount)ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class).add(Restrictions.eq("Number", form.getString("chartOfAccountNumber"))).setMaxResults(1).uniqueResult();
					JournalDetailPK journalDetailPK = new JournalDetailPK();
					journalDetailPK.setChartOfAccount(chartOfAccount);
					journalDetailPK.setJournal(obj);
					journalDetailPK.setChartOfAccountSequence(form.getInt("chartOfAccountSequence")>0?form.getInt("chartOfAccountSequence"):(obj!=null?(obj.getJournalDetails()!=null?obj.getJournalDetails().size()+1:1):1));
					//log.info(" Y : "+journalDetailPK.getChartOfAccountSequence());
					journalDetail.setId(journalDetailPK);
					journalDetail.setAmount(form.getDouble("amount"));
					journalDetail.setDescription(form.getString("journalDetailDescription").length()>0?form.getString("journalDetailDescription"):obj.getDescription());
					Department department = DepartmentDAO.getInstance().get(form.getLong("departmentId"));
					journalDetail.setDepartment(department);
					Set set = obj.getJournalDetails();
					if (set==null) set = new LinkedHashSet();
					JournalDetail removeJournalDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						JournalDetail JournalDetail2 = (JournalDetail)iterator.next();
						if (form.getLong("chartOfAccountId")==JournalDetail2.getId().getChartOfAccount().getId() && form.getInt("chartOfAccountSequence")==journalDetail.getId().getChartOfAccountSequence()) {
							removeJournalDetail = JournalDetail2;
						}
					}
					if (removeJournalDetail!=null) {
						set.remove(removeJournalDetail);
						set.add(journalDetail);
					} else {
						set.add(journalDetail);
					}
					obj.setJournalDetails(set);
					//log.info("S : "+set.size());
					// netral
					form.setString("chartOfAccountId", "");
					form.setString("chartOfAccountNumber", "");
					form.setString("amount", "");
					form.setString("departmentId", "");
					form.setString("chartOfAccountSequence", "");
					form.setString("journalDetailDescription", "");
					form.setString("subaction", "");
				}
				// netral
				form.setString("chartOfAccountId", "");
				form.setString("chartOfAccountNumber", "");
				form.setString("amount", "");
				form.setString("departmentId", "");
				form.setString("chartOfAccountSequence", "");
				form.setString("journalDetailDescription", "");
				form.setString("subaction", "");
			}
			// save to session
			httpSession.setAttribute("journal", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					Session session = JournalDAO.getInstance().getSession();
					Transaction transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateJournalNumber(session);
					JournalDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					JournalDAO.getInstance().update(obj);
				}
				// remove session
				httpSession.removeAttribute("journal");
				// finish
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
				try {
					List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("journalTypeLst", journalTypeLst);
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Number"));
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
					List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("vendorLst", vendorLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("currencyLst", currencyLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?journalId="+form.getLong("journalId"));
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
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			Journal journal = JournalDAO.getInstance().get(form.getLong("journalId"));
			request.setAttribute("journal", journal);
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			// get total
			double debitAmount = 0;
			double creditAmount = 0;
			if (journal!=null) {
				java.util.Iterator iterator = journal.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail) iterator.next();
					if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
						if (journalDetail.getAmount()>0) {
							debitAmount = debitAmount + journalDetail.getAmount();
						} else {
							creditAmount = creditAmount + (-journalDetail.getAmount());
						}
					} else if (journalDetail.getId().getChartOfAccount().isDebit()==false){
						if (journalDetail.getAmount()<0) {
							debitAmount = debitAmount + (-journalDetail.getAmount());
						} else {
							creditAmount = creditAmount + journalDetail.getAmount();
						}
					}
				}
			}
			request.setAttribute("debitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitAmount));
			request.setAttribute("creditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditAmount));
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
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
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Session session = JournalDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			Journal journal = JournalDAO.getInstance().get(form.getLong("journalId"));
			journal.getJournalDetails().removeAll(journal.getJournalDetails());
			JournalDAO.getInstance().delete(journal, session);
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
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
	private ActionForward performPostingForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
				int count = 0;
				try {
					count = Integer.parseInt(request.getParameter("count"));
				}catch(Exception ex) {
					try {
						ResourceBundle prop = ResourceBundle.getBundle("resource.ApplicationResources");
						count = Integer.parseInt(prop.getString("max.item.per.page"));
					}catch(Exception exx) {
					}
				}
				List journalLst = new java.util.LinkedList();
				for (int i=0; i<count; i++) {
					if (form.getString("isPostedLst",i).length()>0) {
						Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
						.add(Restrictions.eq("Id", new Long(form.getLong("journalIdLst",i))))
						.add(Restrictions.eq("Posted", Boolean.FALSE))
						.uniqueResult();
						journalLst.add(journal);
					}
				}
				httpSession.setAttribute("journalLst", journalLst);
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
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
	private ActionForward performPostingConfirm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("journalLst");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			List journalLst = (List) httpSession.getAttribute("journalLst");
			Session session = JournalDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			if (journalLst!=null && journalLst.size() > 0) {
				java.util.Iterator iterator = journalLst.iterator();
				while (iterator.hasNext()) {
					Journal obj = (Journal)iterator.next();
					obj.setPosted(true);
					// get journalDetail of jounal
					Iterator iterator2 = obj.getJournalDetails().iterator();
					while (iterator2.hasNext()) {
						JournalDetail journalDetail = (JournalDetail)iterator2.next();
						//ChartOfAccount chartOfAccount = journalDetail.getId().getChartOfAccount();
						GeneralLedger generalLedger = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("LedgerDate", new Date(obj.getJournalDate().getTime())))
						.add(Restrictions.eq("ChartOfAccount.Id", new Long(journalDetail.getId().getChartOfAccount().getId())))
						.add(Restrictions.eq("Closed", Boolean.FALSE))
						.add(Restrictions.eq("Setup", Boolean.FALSE))
						.uniqueResult();
						if (generalLedger!=null) {
							generalLedger.setAmount(generalLedger.getAmount()+(journalDetail.getAmount() * obj.getExchangeRate()));
							// relation
							Set set = generalLedger.getJournals();
							if (set==null)set = new LinkedHashSet();
							set.add(obj);
							generalLedger.setJournals(set);
							GeneralLedgerDAO.getInstance().update(generalLedger, session);
						} else {
							generalLedger = new GeneralLedger();
							generalLedger.setChartOfAccount(journalDetail.getId().getChartOfAccount());
							generalLedger.setAmount(journalDetail.getAmount() * obj.getExchangeRate());
							generalLedger.setOrganization(users.getOrganization()); 
							generalLedger.setClosed(false);
							generalLedger.setCurrency(organizationSetup.getDefaultCurrency());
							generalLedger.setDebit(journalDetail.getId().getChartOfAccount().isDebit());
							generalLedger.setLedgerDate(obj.getJournalDate());
							generalLedger.setSetup(false);
							// relation
							Set set = generalLedger.getJournals();
							if (set==null)set = new LinkedHashSet();
							set.add(obj);
							generalLedger.setJournals(set);
							GeneralLedgerDAO.getInstance().save(generalLedger, session);
						}
					}
					// update journal
					JournalDAO.getInstance().update(obj, session);
				}
				httpSession.removeAttribute("journalLst");
			}
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("confirm");
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
	private ActionForward performUnpostingForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			form.setCurentCalendar("fromUnpostingDate");
			form.setCurentCalendar("toUnpostingDate");
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				return mapping.findForward("form");
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
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
	private ActionForward performUnpostingConfirm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Session session = JournalDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			if (form.getCalendar("fromUnpostingDate")!=null && form.getCalendar("toUnpostingDate")!=null) {
			  List generalLedgerLst = GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
				.add(Restrictions.ge("LedgerDate", new Date(form.getCalendar("fromUnpostingDate").getTime().getTime())))
				.add(Restrictions.le("LedgerDate", new Date(form.getCalendar("toUnpostingDate").getTime().getTime())))
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Closed", Boolean.FALSE))
				.add(Restrictions.eq("Setup", Boolean.FALSE)).list();
			  
			  java.util.Iterator iterator = generalLedgerLst.iterator();
				while (iterator.hasNext()) {
					GeneralLedger generalLedger = (GeneralLedger)iterator.next();
					Set journalLst = generalLedger.getJournals();
					Iterator iterator2 = journalLst.iterator();
					while (iterator2.hasNext()) {
						Journal journal = (Journal)iterator2.next();
						journal.setPosted(false);
						//JournalDAO.getInstance().update(journal, session);
						//set.remove(journal);
						//generalLedger.setJournals(set);
						JournalDAO.getInstance().update(journal, session);
					}
					generalLedger.getJournals().clear();
					generalLedger.setJournals(null);
					GeneralLedgerDAO.getInstance().delete(generalLedger, session);
				}
				
			} else {
				// cek sign ","
				if (form.getString("number")!=null && form.getString("number").indexOf(",")>0) {
					StringTokenizer stringTokenizer = new StringTokenizer(form.getString("number"), ",");
					while (stringTokenizer.hasMoreTokens()) {
						String string = (String)stringTokenizer.nextToken();
						Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
							.add(Restrictions.eq("Number", string.trim()))
							.setMaxResults(1)
							.uniqueResult();
						Iterator iterator = journal.getJournalDetails().iterator();
						while (iterator.hasNext()) {
							JournalDetail journalDetail = (JournalDetail)iterator.next();
							GeneralLedger generalLedger = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
								.add(Restrictions.eq("LedgerDate", new Date(journal.getJournalDate().getTime())))
								.add(Restrictions.eq("ChartOfAccount.Id", new Long(journalDetail.getId().getChartOfAccount().getId())))
								.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
								.add(Restrictions.eq("Closed", Boolean.FALSE))
								.add(Restrictions.eq("Setup", Boolean.FALSE))
								.setMaxResults(1)
								.uniqueResult();
							//log.info("[ GL ]");
							generalLedger.setAmount(generalLedger.getAmount() - (journalDetail.getAmount()*journal.getExchangeRate()));
							generalLedger.getJournals().remove(journal);
							GeneralLedgerDAO.getInstance().saveOrUpdate(generalLedger, session);
						}
						journal.setPosted(false);
						JournalDAO.getInstance().saveOrUpdate(journal, session);
					}
				} else {
					Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
						.add(Restrictions.eq("Number", form.getString("number")))
						.setMaxResults(1)
						.uniqueResult();
					Iterator iterator = journal.getJournalDetails().iterator();
					while (iterator.hasNext()) {
						JournalDetail journalDetail = (JournalDetail)iterator.next();
						GeneralLedger generalLedger = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
							.add(Restrictions.eq("LedgerDate", new Date(journal.getJournalDate().getTime())))
							.add(Restrictions.eq("ChartOfAccount.Id", new Long(journalDetail.getId().getChartOfAccount().getId())))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.eq("Closed", Boolean.FALSE))
							.add(Restrictions.eq("Setup", Boolean.FALSE))
							.setMaxResults(1)
							.uniqueResult();
						generalLedger.setAmount(generalLedger.getAmount() - (journalDetail.getAmount()*journal.getExchangeRate()));
						generalLedger.getJournals().remove(journal);
						GeneralLedgerDAO.getInstance().saveOrUpdate(generalLedger, session);
					}
					journal.setPosted(false);
					JournalDAO.getInstance().saveOrUpdate(journal, session);
				}
			}
			transaction.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("confirm");
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
	private ActionForward performPostingDateForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			form.setCurentCalendar("fromJournalDate");
			form.setCurentCalendar("toJournalDate");
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				return mapping.findForward("form");
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
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
	private ActionForward performPostingDateConfirm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    // devide date per 5 days
		    Calendar fromDate = form.getCalendar("fromJournalDate");
		    Calendar toDate = form.getCalendar("toJournalDate");
		    //int days = toDate.get(Calendar.DAY_OF_YEAR) - fromDate.get(Calendar.DAY_OF_YEAR) + 1;
		    //int l = Math.round(days/5);
		    //Calendar calendar = new GregorianCalendar();
		    //calendar = (Calendar)fromDate.clone();
		    //log.info("L : "+l);
		    //for (int i=0; i<=l; i++) {
		      //log.info("L : "+l+"//"+i);
		      //fromDate.set(Calendar.DAY_OF_YEAR, fromDate.get(Calendar.DAY_OF_YEAR) + (5));
		      //if (i<l) calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + (5));
		      //else if (i==l) calendar = (Calendar)toDate.clone();
			    // search journal from - to and unposting status
		      //log.info("journalLst : "++);
					List journalLst = JournalDAO.getInstance().getSession().createCriteria(Journal.class)
						.add(Restrictions.ge("JournalDate", new Date(fromDate.getTime().getTime())))
						.add(Restrictions.le("JournalDate", new Date(toDate.getTime().getTime())))
						.add(Restrictions.eq("Posted", Boolean.FALSE))
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.list();
					//log.info("journalLst : "+journalLst.size());
					// process
					Session session = JournalDAO.getInstance().getSession();
					Transaction transaction = session.beginTransaction();
					OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
					if (journalLst!=null && journalLst.size() > 0) {
						java.util.Iterator iterator = journalLst.iterator();
						while (iterator.hasNext()) {
							Journal obj = (Journal)iterator.next();
							obj.setPosted(true);
							// get journalDetail of jounal
							Iterator iterator2 = obj.getJournalDetails().iterator();
							while (iterator2.hasNext()) {
								JournalDetail journalDetail = (JournalDetail)iterator2.next();
								//ChartOfAccount chartOfAccount = journalDetail.getId().getChartOfAccount();
								GeneralLedger generalLedger = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
								.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
								.add(Restrictions.eq("LedgerDate", new Date(obj.getJournalDate().getTime())))
								.add(Restrictions.eq("ChartOfAccount.Id", new Long(journalDetail.getId().getChartOfAccount().getId())))
								.add(Restrictions.eq("Closed", Boolean.FALSE))
								.add(Restrictions.eq("Setup", Boolean.FALSE))
								.uniqueResult();
								if (generalLedger!=null) {
									generalLedger.setAmount(generalLedger.getAmount()+(journalDetail.getAmount() * obj.getExchangeRate()));
									// relation
									Set set = generalLedger.getJournals();
									if (set==null)set = new LinkedHashSet();
									set.add(obj);
									generalLedger.setJournals(set);
									GeneralLedgerDAO.getInstance().update(generalLedger, session);
								} else {
									generalLedger = new GeneralLedger();
									generalLedger.setChartOfAccount(journalDetail.getId().getChartOfAccount());
									generalLedger.setAmount(journalDetail.getAmount() * obj.getExchangeRate());
									generalLedger.setOrganization(users.getOrganization()); 
									generalLedger.setClosed(false);
									generalLedger.setCurrency(organizationSetup.getDefaultCurrency());
									generalLedger.setDebit(journalDetail.getId().getChartOfAccount().isDebit());
									generalLedger.setLedgerDate(obj.getJournalDate());
									generalLedger.setSetup(false);
									// relation
									Set set = generalLedger.getJournals();
									if (set==null)set = new LinkedHashSet();
									set.add(obj);
									generalLedger.setJournals(set);
									GeneralLedgerDAO.getInstance().save(generalLedger, session);
								}
							}
							// update journal
							JournalDAO.getInstance().update(obj, session);
						}
						//httpSession.removeAttribute("journalLst");
					}
					transaction.commit();
					
		    //}
		    
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("confirm");
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
	private ActionForward performPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			Journal journal = JournalDAO.getInstance().get(form.getLong("journalId"));			
			
			if (journal!=null && journal.getBankTransaction()!=null) {
			    ///
			    Rectangle pageSize = new Rectangle(612, 936);
					com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,36,36,36,36);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					PdfWriter.getInstance(document, baos);
					  
					// footer page
					//HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
					//footer.setBorder(Rectangle.NO_BORDER);
					//document.setFooter(footer);
					document.open();
					
					BankTransaction bankTransaction = BankTransactionDAO.getInstance().get(journal.getBankTransaction().getId());
					
					// table upper
					Table table1 = new Table(3);
					table1.setWidth(100);
					table1.setCellsFitPage(true);
					float[] a = {10,50,40};
					table1.setWidths(a);
					table1.setBorder(Rectangle.NO_BORDER);
					table1.setCellsFitPage(true);
					table1.setBorderWidth(1);
					table1.setPadding(1);
					table1.setSpacing(0);
					
					Cell cell = null;
					if (users.getOrganization().getLogoContentType()!=null && users.getOrganization().getLogoContentType().indexOf("image")!=-1) {
						try {
						    byte[] out = users.getOrganization().getLogo();
						    Image image = Image.getInstance(Toolkit.getDefaultToolkit().createImage(out), null);
						    image.scaleAbsoluteHeight(45);
						    image.scaleAbsoluteWidth(45);
						    cell = new Cell(image);
								cell.setBorder(Rectangle.NO_BORDER);
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setRowspan(2);
								table1.addCell(cell);
						} catch(Exception ex) {
						    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
								cell.setBorder(Rectangle.NO_BORDER);
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setRowspan(2);
								table1.addCell(cell);
						}
					} else {
				    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setRowspan(2);
						table1.addCell(cell);
					}
					
					cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table1.addCell(cell);
					cell = new Cell(new Phrase("No. "+bankTransaction.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table1.addCell(cell);
					cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table1.addCell(cell);
					cell = new Cell(new Phrase("Date : "+bankTransaction.getFormatedTransactionDate(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table1.addCell(cell);
					for (int j=0; j<6; j++) {
						cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setColspan(3);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table1.addCell(cell);
					}
					document.add(table1);
					
					Table table = new Table(2);
					table.setWidth(100);
					table.setCellsFitPage(true);
					table.setBorder(Rectangle.NO_BORDER);
					table.setBorderWidth(1);
					table.setPadding(1);
					table.setSpacing(0);
					int w[] = {70, 30};
					table.setWidths(w);
					table.setAutoFillEmptyCells(true);
					// set table title
					
					if (bankTransaction.isDebit()==true) {
						cell = new Cell(new Phrase("RECEIPT VOUCHER", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.NORMAL)));
						cell.setColspan(2);
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						for (int j=0; j<4; j++) {
							cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setColspan(2);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);
						}
						cell = new Cell(new Phrase("Received from : "+(bankTransaction.getCustomer()!=null?bankTransaction.getCustomer().getCompany():"-"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						cell = new Cell(new Phrase("Credit A/C : "+(bankTransaction.getToBankAccount()!=null?bankTransaction.getToBankAccount().getBank().getName():" - "), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
					} else {
						cell = new Cell(new Phrase("PAYMENT VOUCHER", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.NORMAL)));
						cell.setColspan(2);
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						for (int j=0; j<4; j++) {
							cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setColspan(2);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);
						}
						cell = new Cell(new Phrase("Paid To : "+(bankTransaction.getVendor()!=null?bankTransaction.getVendor().getCompany():"-"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						cell = new Cell(new Phrase("Debit A/C : "+(bankTransaction.getFromBankAccount()!=null?bankTransaction.getFromBankAccount().getBank().getName():" - "), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell); 
					}
					
					cell = new Cell(new Phrase("Through BANK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
					table.addCell(cell);
					cell = new Cell(new Phrase("Amount",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
					table.addCell(cell);
					table.endHeaders();	
					
					// data
					cell = new Cell(new Phrase((bankTransaction.getReference().length()>0?bankTransaction.getReference()+"\n":"")+bankTransaction.getNote(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new Cell(new Phrase((bankTransaction.getCurrency()!=null?bankTransaction.getCurrency().getSymbol():"")+" "+bankTransaction.getFormatedAmount()+"  ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);
					int m = 31;
					int l = 0;
					//int l = (bankTransaction.getReference()+bankTransaction.getNote()).length();
					//m = m - Math.round(l / 90);
					if (bankTransaction.getReference()!=null && bankTransaction.getReference().length()>0) l = 1;
					if (bankTransaction.getNote().indexOf("\n")>0) {
					    StringTokenizer tokenizer = new StringTokenizer(bankTransaction.getNote(), "\n");
					    while (tokenizer.hasMoreTokens()) {
					        tokenizer.nextToken();
					        l++;
					    }
					    m = m - Math.round(4 *(l - 1));
					} else {
					    l = (bankTransaction.getNote()).length();
					    m = m - Math.round(l / 20);
					}
					// space
					for (int i=0; i<m; i++) {
							cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setBorder(Rectangle.RIGHT);
							cell.setNoWrap(false);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);
							cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setNoWrap(false);
							cell.setBorder(Rectangle.LEFT);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);
					}
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new Cell(new Phrase("SAY : "+AmountSay.getSaying(bankTransaction.getFormatedAmount()).trim()+" "+bankTransaction.getCurrency().getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.BOTTOM);
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					document.add(table);
					
					// TT
					Table table2 = new Table(3);
					table2.setWidth(99);
					table2.setCellsFitPage(true);
					table2.setBorder(Rectangle.NO_BORDER);
					table2.setBorderWidth(1);
					table2.setPadding(1);
					table2.setSpacing(0);
					int w2[] = {33, 33, 33};
					table2.setWidths(w2);
					table2.setAutoFillEmptyCells(true);
					
					// space
					for (int i=0; i<25; i++) {
						cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setColspan(3);
						cell.setNoWrap(false);
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell);
					}
					
					cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					if (bankTransaction.isDebit()==true) {
						cell = new Cell(new Phrase("Receipt by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell);
					} else {
				    cell = new Cell(new Phrase("Name & Signature of Payee", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell);
					}
					cell = new Cell(new Phrase("Checked by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					cell = new Cell(new Phrase("Approved by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					
					
					document.add(table2);
					
					
					document.close();
					//send pdf to browser
					response.setContentType("application/pdf");
					response.setContentLength(baos.size());
					ServletOutputStream out = response.getOutputStream();
					baos.writeTo(out);
					out.flush();
					
			} else {
		    Rectangle pageSize = new Rectangle(612, 936);
				com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,36,36,36,36);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter.getInstance(document, baos);
				  
				// footer page
				//HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
				//footer.setBorder(Rectangle.NO_BORDER);
				//document.setFooter(footer);
				document.open();
				
				//BankTransaction bankTransaction = BankTransactionDAO.getInstance().get(journal.getBankTransaction().getId());
				
				// table upper
				Table table1 = new Table(3);
				table1.setWidth(100);
				table1.setCellsFitPage(true);
				float[] a = {10,50,40};
				table1.setWidths(a);
				table1.setBorder(Rectangle.NO_BORDER);
				table1.setCellsFitPage(true);
				table1.setBorderWidth(1);
				table1.setPadding(1);
				table1.setSpacing(0);
				
				Cell cell = null;
				if (users.getOrganization().getLogoContentType()!=null && users.getOrganization().getLogoContentType().indexOf("image")!=-1) {
					try {
					    byte[] out = users.getOrganization().getLogo();
					    Image image = Image.getInstance(Toolkit.getDefaultToolkit().createImage(out), null);
					    image.scaleAbsoluteHeight(45);
					    image.scaleAbsoluteWidth(45);
					    cell = new Cell(image);
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setRowspan(2);
							table1.addCell(cell);
					} catch(Exception ex) {
					    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setRowspan(2);
							table1.addCell(cell);
					}
				} else {
			    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setRowspan(2);
					table1.addCell(cell);
				}
				
				cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("No. "+journal.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table1.addCell(cell);
				cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("Date : "+journal.getFormatedJournalDate(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table1.addCell(cell);
				for (int j=0; j<6; j++) {
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setColspan(3);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table1.addCell(cell);
				}
				document.add(table1);
				
				Table table = new Table(2);
				table.setWidth(100);
				table.setCellsFitPage(true);
				table.setBorder(Rectangle.NO_BORDER);
				table.setBorderWidth(1);
				table.setPadding(1);
				table.setSpacing(0);
				int w[] = {70, 30};
				table.setWidths(w);
				table.setAutoFillEmptyCells(true);
				// set table title
				
				cell = new Cell(new Phrase("JOURNAL VOUCHER", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.NORMAL)));
				cell.setColspan(2);
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				for (int j=0; j<4; j++) {
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				}
				cell = new Cell(new Phrase("Received from : "+"-", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase("Credit A/C : "+"-", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new Cell(new Phrase("Through BANK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table.addCell(cell);
				cell = new Cell(new Phrase("Amount",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table.addCell(cell);
				table.endHeaders();	
				
				// data
				cell = new Cell(new Phrase((journal.getReference()!=null?(journal.getReference().length()>0?journal.getReference()+"\n":""):"")+journal.getDescription(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase((journal.getCurrency()!=null?journal.getCurrency().getSymbol():"")+" "+journal.getFormatedAmount()+"  ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
				int m = 31;
				int l = 0;
				//int l = (bankTransaction.getReference()+bankTransaction.getNote()).length();
				//m = m - Math.round(l / 90);
				if (journal.getReference()!=null && journal.getReference().length()>0) l = 1;
				if (journal.getDescription()!=null && journal.getDescription().indexOf("\n")>0) {
				    StringTokenizer tokenizer = new StringTokenizer(journal.getDescription(), "\n");
				    while (tokenizer.hasMoreTokens()) {
				        tokenizer.nextToken();
				        l++;
				    }
				    m = m - Math.round(4 *(l - 1));
				} else {
				    l = journal.getDescription()!=null?(journal.getDescription()).length():0;
				    m = m - Math.round(l / 20);
				}
				// space
				for (int i=0; i<m; i++) {
						cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setNoWrap(false);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setNoWrap(false);
						cell.setBorder(Rectangle.LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
				}
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase("SAY : "+AmountSay.getSaying(journal.getFormatedAmount()).trim()+" "+journal.getCurrency().getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				document.add(table);
				
				// TT
				Table table2 = new Table(3);
				table2.setWidth(99);
				table2.setCellsFitPage(true);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				int w2[] = {33, 33, 33};
				table2.setWidths(w2);
				table2.setAutoFillEmptyCells(true);
				
				// space
				for (int i=0; i<25; i++) {
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setColspan(3);
					cell.setNoWrap(false);
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
				}
				
				cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Created by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Checked by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Approved by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				
				
				document.add(table2);
				
				
				document.close();
				//send pdf to browser
				response.setContentType("application/pdf");
				response.setContentLength(baos.size());
				ServletOutputStream out = response.getOutputStream();
				baos.writeTo(out);
				out.flush();
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return null;
	}
	
	/** 
	 * Method performReport
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performReportPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			
				if (form.getCalendar("fromJournalDate")==null) form.setCurentCalendar("fromJournalDate");
				if (form.getCalendar("toJournalDate")==null) form.setCurentCalendar("toJournalDate");
			
				List journalLst = JournalDAO.getInstance().getSession().createCriteria(Journal.class)
					.add(Restrictions.ge("JournalDate", new Date(form.getCalendar("fromJournalDate").getTime().getTime())))
					.add(Restrictions.le("JournalDate", new Date(form.getCalendar("toJournalDate").getTime().getTime())))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					//.add(Restrictions.ne("Edit", Boolean.TRUE))
					.addOrder(Order.asc("JournalDate"))
					.list();
				
				// write to pdf document
				Rectangle pageSize = new Rectangle(612, 936);
				com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,36,36,36,36);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter.getInstance(document, baos);
				  
				// footer page
				HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
				footer.setBorder(Rectangle.NO_BORDER);
				document.setFooter(footer);
				document.open();
				
				// table upper
				Table table1 = new Table(2);
				table1.setWidth(100);
				table1.setCellsFitPage(true);
				float[] a = {50,50};
				table1.setWidths(a);
				table1.setBorder(Rectangle.NO_BORDER);
				table1.setBorderWidth(1);
				table1.setPadding(1);
				table1.setSpacing(0);
				
				Cell cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("Date : "+form.getString("fromJournalDate")+" - "+form.getString("toJournalDate"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				document.add(table1);
				
				// header table
				Table firstTable = new Table(6);
				firstTable.setWidth(100);
				firstTable.setCellsFitPage(true);
				firstTable.setBorderWidth(1);
				firstTable.setBorder(Rectangle.NO_BORDER);
				firstTable.setPadding(1);
				firstTable.setSpacing(0);
				int b2[] = {10, 12, 38, 20, 10, 10};
				firstTable.setWidths(b2);
				
				cell = new Cell(new Phrase("Date",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("Number",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("COA",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("Description",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("Debet",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("Kredit",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				firstTable.addCell(cell);
				firstTable.endHeaders();
				
				Iterator iterator2 = journalLst.iterator();
				while (iterator2.hasNext()) {
					Journal journal = (Journal)iterator2.next();
					int j=0;
					Iterator iterator = journal.getJournalDetails()!=null?journal.getJournalDetails().iterator():new LinkedList().iterator();
					while (iterator.hasNext()) {
					  JournalDetail journalDetail = (JournalDetail)iterator.next();
					  if (j==0) {
							cell = new Cell(new Phrase(Formater.getFormatedDate(journal.getJournalDate()),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
							cell = new Cell(new Phrase(journal.getNumber(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
					  } else {
				      cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
							cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
					  }
					  cell = new Cell(new Phrase(journalDetail.getId().getChartOfAccount().getNumber()+"\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorder(Rectangle.RIGHT);
						firstTable.addCell(cell);
						cell = new Cell(new Phrase(journalDetail.getDescription(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorder(Rectangle.RIGHT);
						firstTable.addCell(cell);
						
						//debit
						if(journalDetail.getId().getChartOfAccount().isDebit()==true){
							if(journalDetail.getAmount() > 0){
								cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion()+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorder(Rectangle.RIGHT);
								firstTable.addCell(cell);
							} else {
								cell = new Cell(new Phrase(""+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorder(Rectangle.RIGHT);
								firstTable.addCell(cell);
							}
						} else if (journalDetail.getId().getChartOfAccount().isDebit()==false) {
							if(journalDetail.getAmount() < 0){
								cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion()+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorder(Rectangle.RIGHT);
								firstTable.addCell(cell);
							} else {
								cell = new Cell(new Phrase(""+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorder(Rectangle.RIGHT);
								firstTable.addCell(cell);
							}
						}
						//credit
						if (journalDetail.getId().getChartOfAccount().isDebit()==false) {
						  if(journalDetail.getAmount() > 0){
								cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorder(Rectangle.NO_BORDER);
								firstTable.addCell(cell);
						  }else{
							  cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, com.lowagie.text.Font.NORMAL)));
							  cell.setVerticalAlignment(Element.ALIGN_RIGHT);
							  cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							  cell.setBorder(Rectangle.NO_BORDER);
							  firstTable.addCell(cell);
						  }
						} else if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
							if(journalDetail.getAmount() < 0){
							  cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
							  cell.setVerticalAlignment(Element.ALIGN_RIGHT);
							  cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							  cell.setBorder(Rectangle.NO_BORDER);
							  firstTable.addCell(cell);
							}else{
								cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorder(Rectangle.NO_BORDER);
								firstTable.addCell(cell);
							}
						}
						j++;
					}
					// end journal
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP);
					firstTable.addCell(cell);
				}
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(6);
				firstTable.addCell(cell);
				document.add(firstTable);
				
				document.close();
				//send pdf to browser
				response.setContentType("application/pdf");
				response.setContentLength(baos.size());
				ServletOutputStream out = response.getOutputStream();
				baos.writeTo(out);
				out.flush();
				
				
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return null;
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
	private ActionForward performApList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
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
			if (form.getString("isPosted")==null || form.getString("isPosted").length()==0) form.setString("isPosted", "N");
			//save start and count attribute on session
			httpSession.setAttribute(CommonConstants.START,Integer.toString(start));
			httpSession.setAttribute(CommonConstants.COUNT,Integer.toString(count));
			List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("journalTypeLst", journalTypeLst);
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("projectLst", projectLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
			Criteria criteria = JournalDAO.getInstance().getSession().createCriteria(Journal.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("journalTypeId")>0) criteria.add(Restrictions.eq("JournalType.Id", new Long(form.getLong("journalTypeId"))));
			if (form.getString("isPosted")!=null && form.getString("isPosted").equalsIgnoreCase("Y"))criteria.add(Restrictions.eq("Posted", Boolean.TRUE));
			if (form.getString("isPosted")!=null && form.getString("isPosted").equalsIgnoreCase("N"))criteria.add(Restrictions.eq("Posted", Boolean.FALSE));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			if (form.getCalendar("journalDate")!=null)criteria.add(Restrictions.eq("JournalDate", new Date(form.getCalendar("journalDate").getTime().getTime())));
			if (form.getLong("departmentId")>0) criteria.createCriteria("JournalDetails", "JournalDetail").add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = JournalDAO.getInstance().getSession().createCriteria(Journal.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			if (form.getCalendar("journalDate")!=null)criteria.add(Restrictions.eq("JournalDate", new Date(form.getCalendar("journalDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("journalTypeId")>0) criteria.add(Restrictions.eq("JournalType.Id", new Long(form.getLong("journalTypeId"))));
			if (form.getString("isPosted")!=null && form.getString("isPosted").equalsIgnoreCase("Y"))criteria.add(Restrictions.eq("Posted", Boolean.TRUE));
			if (form.getString("isPosted")!=null && form.getString("isPosted").equalsIgnoreCase("N"))criteria.add(Restrictions.eq("Posted", Boolean.FALSE));
			if (form.getLong("departmentId")>0) criteria.createCriteria("JournalDetails", "JournalDetail").add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			//criteria.addOrder(Order.desc("Name"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("JOURNAL",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
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
	private ActionForward performApForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// remove
			Journal obj = (Journal)httpSession.getAttribute("journal");
			if (form.getLong("chartOfAccountId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEJOURNALDETAIL")) {
				JournalDetail removeJournalDetail = null;
				Iterator iterator = obj.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail)iterator.next();
					if (form.getLong("chartOfAccountId") == journalDetail.getId().getChartOfAccount().getId()) {
							removeJournalDetail = journalDetail;
					}
				}
				if (removeJournalDetail!=null) {
					Set set = obj.getJournalDetails();
					set.remove(removeJournalDetail);
					obj.setJournalDetails(set);
				}
				form.setString("subaction", "");
				form.setString("chartOfAccountId", "");
				httpSession.setAttribute("journal", obj);
			}
			// relationships
			List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("journalTypeLst", journalTypeLst);
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
			List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("vendorLst", vendorLst);
			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerLst", customerLst);
			List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("currencyLst", currencyLst);
			if (form.getLong("journalId") == 0) {
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				if (obj==null) {
					form.setString("journalId",0);
					form.setCurentTimestamp("createOn");
					form.setString("number", RunningNumberDAO.getInstance().getJournalNumber());
					form.setCurentCalendar("journalDate");
				} else if (obj!=null) {
					form.setString("journalId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setString("number",obj.getNumber());
					form.setString("reference",obj.getReference());
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
					form.setCalendar("journalDate",obj.getJournalDate());
					form.setString("journalTypeId",obj.getJournalType()!=null?obj.getJournalType().getId():0);
					form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
					Set journalDetailLst = obj.getJournalDetails();
					request.setAttribute("journalDetailLst", journalDetailLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = JournalDAO.getInstance().get(form.getLong("journalId"));
					httpSession.setAttribute("journal",obj);
				}
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				//httpSession.setAttribute("Journal",obj);
				form.setString("journalId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("number",obj.getNumber());
				form.setString("reference",obj.getReference());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				form.setCalendar("journalDate",obj.getJournalDate());
				form.setString("journalTypeId",obj.getJournalType()!=null?obj.getJournalType().getId():0);
				form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set journalDetailLst = obj.getJournalDetails();
				request.setAttribute("journalDetailLst", journalDetailLst);
			}
			if (obj!=null && form.getLong("chartOfAccountId") > 0) {
				Iterator iterator = obj.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail)iterator.next();
					if (form.getLong("chartOfAccountId") == journalDetail.getId().getChartOfAccount().getId()) {
						form.setString("amount", Formater.getFormatedOutputForm(journalDetail.getAmount()));
						form.setString("departmentId", journalDetail.getDepartment()!=null?journalDetail.getDepartment().getId():0);
						form.setString("journalDetailDescription", journalDetail.getDescription());
						form.setString("chartOfAccountNumber", journalDetail.getId().getChartOfAccount().getNumber());
					}
				}
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			// get total
			double debitAmount = 0;
			double creditAmount = 0;
			if (obj!=null) {
				java.util.Iterator iterator = obj.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail) iterator.next();
					if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
						if (journalDetail.getAmount()>0) {
							debitAmount = debitAmount + journalDetail.getAmount();
						} else {
							creditAmount = creditAmount + (-journalDetail.getAmount());
						}
					} else if (journalDetail.getId().getChartOfAccount().isDebit()==false){
						if (journalDetail.getAmount()<0) {
							debitAmount = debitAmount + (-journalDetail.getAmount());
						} else {
							creditAmount = creditAmount + journalDetail.getAmount();
						}
					}
				}
			}
			request.setAttribute("debitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitAmount));
			request.setAttribute("creditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditAmount));
		}catch(Exception ex) {
			try {
				List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("journalTypeLst", journalTypeLst);
				List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("departmentLst", departmentLst);
				List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("vendorLst", vendorLst);
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("currencyLst", currencyLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
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
	private ActionForward performApSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("journal");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Journal obj = (Journal)httpSession.getAttribute("journal");
			//if (obj==null) obj = new Journal();
			if (form.getLong("journalId") == 0) {
				obj = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
				.add(Restrictions.eq("Number", form.getString("number")))
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.uniqueResult();
				if (obj==null) {
					obj = (Journal)httpSession.getAttribute("journal");
					if (obj==null) obj = new Journal();
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					obj.setDescription(form.getString("description"));
					OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
					//log.info("ER : "+CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup));
					obj.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("journalDate")));
					obj.setJournalDate(form.getCalendar("journalDate")!=null?form.getCalendar("journalDate").getTime():null);
					JournalType journalType = JournalTypeDAO.getInstance().get(form.getLong("journalTypeId"));
					obj.setJournalType(journalType);
					obj.setNumber(form.getString("number"));
					obj.setOrganization(users.getOrganization());
					obj.setPosted(false);
					obj.setReference(form.getString("reference"));
					Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(vendors);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
				} else {
					List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("journalTypeLst", journalTypeLst);
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
					List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("vendorLst", vendorLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("currencyLst", currencyLst);
					Set journalDetailLst = obj!=null?obj.getJournalDetails():new LinkedHashSet();
					request.setAttribute("journalDetailLst", journalDetailLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = JournalDAO.getInstance().get(form.getLong("journalId"));
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setCustomer(customers);
				obj.setDescription(form.getString("description"));
				//OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
				//obj.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup));
				obj.setJournalDate(form.getCalendar("journalDate")!=null?form.getCalendar("journalDate").getTime():null);
				JournalType journalType = JournalTypeDAO.getInstance().get(form.getLong("journalTypeId"));
				obj.setJournalType(journalType);
				obj.setNumber(form.getString("number"));
				obj.setOrganization(users.getOrganization());
				//obj.setPosted(false);
				obj.setReference(form.getString("reference"));
				Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
				obj.setVendor(vendors);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("journalId"));
				//JournalDAO.getInstance().update(obj);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDJOURNALDETAIL")) {
				if (form.getLong("chartOfAccountId") >0 || form.getString("chartOfAccountNumber").length()>0) {
				  JournalDetail journalDetail = new JournalDetail();
					ChartOfAccount chartOfAccount = null;
					if (form.getLong("chartOfAccountId")>0) chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
					if (form.getString("chartOfAccountNumber").length()>0) chartOfAccount = (ChartOfAccount)ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class).add(Restrictions.eq("Number", form.getString("chartOfAccountNumber"))).setMaxResults(1).uniqueResult();
					JournalDetailPK journalDetailPK = new JournalDetailPK();
					journalDetailPK.setChartOfAccount(chartOfAccount);
					journalDetailPK.setJournal(obj);
					journalDetail.setId(journalDetailPK);
					journalDetail.setAmount(form.getDouble("amount"));
					journalDetail.setDescription(form.getString("journalDetailDescription"));
					Department department = DepartmentDAO.getInstance().get(form.getLong("departmentId"));
					journalDetail.setDepartment(department);
					Set set = obj.getJournalDetails();
					if (set==null) set = new LinkedHashSet();
					JournalDetail removeJournalDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						JournalDetail JournalDetail2 = (JournalDetail)iterator.next();
						if (form.getLong("chartOfAccountId")==JournalDetail2.getId().getChartOfAccount().getId()) {
							removeJournalDetail = JournalDetail2;
						}
					}
					if (removeJournalDetail!=null) {
						set.remove(removeJournalDetail);
						set.add(journalDetail);
					} else {
						set.add(journalDetail);
					}
					obj.setJournalDetails(set);
					//log.info("S : "+set.size());
					// netral
					form.setString("chartOfAccountId", "");
					form.setString("chartOfAccountNumber", "");
					form.setString("amount", "");
					form.setString("departmentId", "");
					form.setString("journalDetailDescription", "");
					form.setString("subaction", "");
				}
				// netral
				form.setString("chartOfAccountId", "");
				form.setString("chartOfAccountNumber", "");
				form.setString("amount", "");
				form.setString("departmentId", "");
				form.setString("journalDetailDescription", "");
				form.setString("subaction", "");
			}
			// save to session
			httpSession.setAttribute("journal", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					Session session = JournalDAO.getInstance().getSession();
					Transaction transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateJournalNumber(session);
					JournalDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					JournalDAO.getInstance().update(obj);
				}
				// remove session
				httpSession.removeAttribute("journal");
				// finish
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				try {
					List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("journalTypeLst", journalTypeLst);
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
					List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("vendorLst", vendorLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("currencyLst", currencyLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?journalId="+form.getLong("journalId"));
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
	private ActionForward performApDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			Journal journal = JournalDAO.getInstance().get(form.getLong("journalId"));
			request.setAttribute("journal", journal);
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			// get total
			double debitAmount = 0;
			double creditAmount = 0;
			if (journal!=null) {
				java.util.Iterator iterator = journal.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail) iterator.next();
					if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
						if (journalDetail.getAmount()>0) {
							debitAmount = debitAmount + journalDetail.getAmount();
						} else {
							creditAmount = creditAmount + (-journalDetail.getAmount());
						}
					} else if (journalDetail.getId().getChartOfAccount().isDebit()==false){
						if (journalDetail.getAmount()<0) {
							debitAmount = debitAmount + (-journalDetail.getAmount());
						} else {
							creditAmount = creditAmount + journalDetail.getAmount();
						}
					}
				}
			}
			request.setAttribute("debitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitAmount));
			request.setAttribute("creditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditAmount));
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
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
	private ActionForward performApDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Session session = JournalDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			Journal journal = JournalDAO.getInstance().get(form.getLong("journalId"));
			journal.getJournalDetails().removeAll(journal.getJournalDetails());
			JournalDAO.getInstance().delete(journal, session);
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
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
	private ActionForward performApPostingForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
				List journalLst = new java.util.LinkedList();
				for (int i=0; i<10; i++) {
					if (form.getString("isPostedLst",i).length()>0) {
						Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
						.add(Restrictions.eq("Id", new Long(form.getLong("journalIdLst",i))))
						.add(Restrictions.eq("Posted", Boolean.FALSE))
						.uniqueResult();
						journalLst.add(journal);
					}
				}
				httpSession.setAttribute("journalLst", journalLst);
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
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
	private ActionForward performApPostingConfirm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("journalLst");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			List journalLst = (List) httpSession.getAttribute("journalLst");
			Session session = JournalDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			if (journalLst!=null && journalLst.size() > 0) {
				java.util.Iterator iterator = journalLst.iterator();
				while (iterator.hasNext()) {
					Journal obj = (Journal)iterator.next();
					obj.setPosted(true);
					// get journalDetail of jounal
					Iterator iterator2 = obj.getJournalDetails().iterator();
					while (iterator2.hasNext()) {
						JournalDetail journalDetail = (JournalDetail)iterator2.next();
						//ChartOfAccount chartOfAccount = journalDetail.getId().getChartOfAccount();
						GeneralLedger generalLedger = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("LedgerDate", new Date(obj.getJournalDate().getTime())))
						.add(Restrictions.eq("ChartOfAccount.Id", new Long(journalDetail.getId().getChartOfAccount().getId())))
						.add(Restrictions.eq("Closed", Boolean.FALSE))
						.add(Restrictions.eq("Setup", Boolean.FALSE))
						.uniqueResult();
						if (generalLedger!=null) {
							generalLedger.setAmount(generalLedger.getAmount()+(journalDetail.getAmount() * obj.getExchangeRate()));
							// relation
							Set set = generalLedger.getJournals();
							if (set==null)set = new LinkedHashSet();
							set.add(obj);
							generalLedger.setJournals(set);
							GeneralLedgerDAO.getInstance().update(generalLedger, session);
						} else {
							generalLedger = new GeneralLedger();
							generalLedger.setChartOfAccount(journalDetail.getId().getChartOfAccount());
							generalLedger.setAmount(journalDetail.getAmount() * obj.getExchangeRate());
							generalLedger.setOrganization(users.getOrganization()); 
							generalLedger.setClosed(false);
							generalLedger.setCurrency(organizationSetup.getDefaultCurrency());
							generalLedger.setDebit(journalDetail.getId().getChartOfAccount().isDebit());
							generalLedger.setLedgerDate(obj.getJournalDate());
							generalLedger.setSetup(false);
							// relation
							Set set = generalLedger.getJournals();
							if (set==null)set = new LinkedHashSet();
							set.add(obj);
							generalLedger.setJournals(set);
							GeneralLedgerDAO.getInstance().save(generalLedger, session);
						}
					}
					// update journal
					JournalDAO.getInstance().update(obj, session);
				}
				httpSession.removeAttribute("journalLst");
			}
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("confirm");
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
	private ActionForward performApUnpostingForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			form.setCurentCalendar("fromUnpostingDate");
			form.setCurentCalendar("toUnpostingDate");
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				return mapping.findForward("form");
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
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
	private ActionForward performApUnpostingConfirm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Session session = JournalDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			if (form.getCalendar("fromUnpostingDate")!=null && form.getCalendar("toUnpostingDate")!=null) {
			  List generalLedgerLst = GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
				.add(Restrictions.ge("LedgerDate", new Date(form.getCalendar("fromUnpostingDate").getTime().getTime())))
				.add(Restrictions.le("LedgerDate", new Date(form.getCalendar("toUnpostingDate").getTime().getTime())))
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Closed", Boolean.FALSE))
				.add(Restrictions.eq("Setup", Boolean.FALSE)).list();
			  
			  java.util.Iterator iterator = generalLedgerLst.iterator();
				while (iterator.hasNext()) {
					GeneralLedger generalLedger = (GeneralLedger)iterator.next();
					Set journalLst = generalLedger.getJournals();
					Iterator iterator2 = journalLst.iterator();
					while (iterator2.hasNext()) {
						Journal journal = (Journal)iterator2.next();
						journal.setPosted(false);
						//JournalDAO.getInstance().update(journal, session);
						//set.remove(journal);
						//generalLedger.setJournals(set);
						JournalDAO.getInstance().update(journal, session);
					}
					generalLedger.getJournals().clear();
					generalLedger.setJournals(null);
					GeneralLedgerDAO.getInstance().delete(generalLedger, session);
				}
				
			} else {
				// cek sign ","
				if (form.getString("number")!=null && form.getString("number").indexOf(",")>0) {
					StringTokenizer stringTokenizer = new StringTokenizer(form.getString("number"), ",");
					while (stringTokenizer.hasMoreTokens()) {
						String string = (String)stringTokenizer.nextToken();
						Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
							.add(Restrictions.eq("Number", string.trim()))
							.setMaxResults(1)
							.uniqueResult();
						Iterator iterator = journal.getJournalDetails().iterator();
						while (iterator.hasNext()) {
							JournalDetail journalDetail = (JournalDetail)iterator.next();
							GeneralLedger generalLedger = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
								.add(Restrictions.eq("LedgerDate", new Date(journal.getJournalDate().getTime())))
								.add(Restrictions.eq("ChartOfAccount.Id", new Long(journalDetail.getId().getChartOfAccount().getId())))
								.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
								.add(Restrictions.eq("Closed", Boolean.FALSE))
								.add(Restrictions.eq("Setup", Boolean.FALSE))
								.setMaxResults(1)
								.uniqueResult();
							//log.info("[ GL ]");
							generalLedger.setAmount(generalLedger.getAmount() - (journalDetail.getAmount()*journal.getExchangeRate()));
							generalLedger.getJournals().remove(journal);
							GeneralLedgerDAO.getInstance().saveOrUpdate(generalLedger, session);
						}
						journal.setPosted(false);
						JournalDAO.getInstance().saveOrUpdate(journal, session);
					}
				} else {
					Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
						.add(Restrictions.eq("Number", form.getString("number")))
						.setMaxResults(1)
						.uniqueResult();
					Iterator iterator = journal.getJournalDetails().iterator();
					while (iterator.hasNext()) {
						JournalDetail journalDetail = (JournalDetail)iterator.next();
						GeneralLedger generalLedger = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
							.add(Restrictions.eq("LedgerDate", new Date(journal.getJournalDate().getTime())))
							.add(Restrictions.eq("ChartOfAccount.Id", new Long(journalDetail.getId().getChartOfAccount().getId())))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.eq("Closed", Boolean.FALSE))
							.add(Restrictions.eq("Setup", Boolean.FALSE))
							.setMaxResults(1)
							.uniqueResult();
						generalLedger.setAmount(generalLedger.getAmount() - (journalDetail.getAmount()*journal.getExchangeRate()));
						generalLedger.getJournals().remove(journal);
						GeneralLedgerDAO.getInstance().saveOrUpdate(generalLedger, session);
					}
					journal.setPosted(false);
					JournalDAO.getInstance().saveOrUpdate(journal, session);
				}
			}
			transaction.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("confirm");
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
	private ActionForward performApPostingDateForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			form.setCurentCalendar("fromJournalDate");
			form.setCurentCalendar("toJournalDate");
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				return mapping.findForward("form");
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
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
	private ActionForward performApPostingDateConfirm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    // devide date per 5 days
		    Calendar fromDate = form.getCalendar("fromJournalDate");
		    Calendar toDate = form.getCalendar("toJournalDate");
		    //int days = toDate.get(Calendar.DAY_OF_YEAR) - fromDate.get(Calendar.DAY_OF_YEAR) + 1;
		    //int l = Math.round(days/5);
		    //Calendar calendar = new GregorianCalendar();
		    //calendar = (Calendar)fromDate.clone();
		    //log.info("L : "+l);
		    //for (int i=0; i<=l; i++) {
		      //log.info("L : "+l+"//"+i);
		      //fromDate.set(Calendar.DAY_OF_YEAR, fromDate.get(Calendar.DAY_OF_YEAR) + (5));
		      //if (i<l) calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + (5));
		      //else if (i==l) calendar = (Calendar)toDate.clone();
			    // search journal from - to and unposting status
		      //log.info("journalLst : "++);
					List journalLst = JournalDAO.getInstance().getSession().createCriteria(Journal.class)
						.add(Restrictions.ge("JournalDate", new Date(fromDate.getTime().getTime())))
						.add(Restrictions.le("JournalDate", new Date(toDate.getTime().getTime())))
						.add(Restrictions.eq("Posted", Boolean.FALSE))
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.list();
					//log.info("journalLst : "+journalLst.size());
					// process
					Session session = JournalDAO.getInstance().getSession();
					Transaction transaction = session.beginTransaction();
					OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
					if (journalLst!=null && journalLst.size() > 0) {
						java.util.Iterator iterator = journalLst.iterator();
						while (iterator.hasNext()) {
							Journal obj = (Journal)iterator.next();
							obj.setPosted(true);
							// get journalDetail of jounal
							Iterator iterator2 = obj.getJournalDetails().iterator();
							while (iterator2.hasNext()) {
								JournalDetail journalDetail = (JournalDetail)iterator2.next();
								//ChartOfAccount chartOfAccount = journalDetail.getId().getChartOfAccount();
								GeneralLedger generalLedger = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
								.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
								.add(Restrictions.eq("LedgerDate", new Date(obj.getJournalDate().getTime())))
								.add(Restrictions.eq("ChartOfAccount.Id", new Long(journalDetail.getId().getChartOfAccount().getId())))
								.add(Restrictions.eq("Closed", Boolean.FALSE))
								.add(Restrictions.eq("Setup", Boolean.FALSE))
								.uniqueResult();
								if (generalLedger!=null) {
									generalLedger.setAmount(generalLedger.getAmount()+(journalDetail.getAmount() * obj.getExchangeRate()));
									// relation
									Set set = generalLedger.getJournals();
									if (set==null)set = new LinkedHashSet();
									set.add(obj);
									generalLedger.setJournals(set);
									GeneralLedgerDAO.getInstance().update(generalLedger, session);
								} else {
									generalLedger = new GeneralLedger();
									generalLedger.setChartOfAccount(journalDetail.getId().getChartOfAccount());
									generalLedger.setAmount(journalDetail.getAmount() * obj.getExchangeRate());
									generalLedger.setOrganization(users.getOrganization()); 
									generalLedger.setClosed(false);
									generalLedger.setCurrency(organizationSetup.getDefaultCurrency());
									generalLedger.setDebit(journalDetail.getId().getChartOfAccount().isDebit());
									generalLedger.setLedgerDate(obj.getJournalDate());
									generalLedger.setSetup(false);
									// relation
									Set set = generalLedger.getJournals();
									if (set==null)set = new LinkedHashSet();
									set.add(obj);
									generalLedger.setJournals(set);
									GeneralLedgerDAO.getInstance().save(generalLedger, session);
								}
							}
							// update journal
							JournalDAO.getInstance().update(obj, session);
						}
						//httpSession.removeAttribute("journalLst");
					}
					transaction.commit();
					
		    //}
		    
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("confirm");
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
	private ActionForward performApPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			Journal journal = JournalDAO.getInstance().get(form.getLong("journalId"));			
			
			if (journal!=null && journal.getBankTransaction()!=null) {
			    ///
			    Rectangle pageSize = new Rectangle(612, 936);
					com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,36,36,36,36);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					PdfWriter.getInstance(document, baos);
					  
					// footer page
					//HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
					//footer.setBorder(Rectangle.NO_BORDER);
					//document.setFooter(footer);
					document.open();
					
					BankTransaction bankTransaction = BankTransactionDAO.getInstance().get(journal.getBankTransaction().getId());
					
					// table upper
					Table table1 = new Table(3);
					table1.setWidth(100);
					table1.setCellsFitPage(true);
					float[] a = {10,50,40};
					table1.setWidths(a);
					table1.setBorder(Rectangle.NO_BORDER);
					table1.setCellsFitPage(true);
					table1.setBorderWidth(1);
					table1.setPadding(1);
					table1.setSpacing(0);
					
					Cell cell = null;
					if (users.getOrganization().getLogoContentType()!=null && users.getOrganization().getLogoContentType().indexOf("image")!=-1) {
						try {
						    byte[] out = users.getOrganization().getLogo();
						    Image image = Image.getInstance(Toolkit.getDefaultToolkit().createImage(out), null);
						    image.scaleAbsoluteHeight(45);
						    image.scaleAbsoluteWidth(45);
						    cell = new Cell(image);
								cell.setBorder(Rectangle.NO_BORDER);
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setRowspan(2);
								table1.addCell(cell);
						} catch(Exception ex) {
						    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
								cell.setBorder(Rectangle.NO_BORDER);
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setRowspan(2);
								table1.addCell(cell);
						}
					} else {
				    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setRowspan(2);
						table1.addCell(cell);
					}
					
					cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table1.addCell(cell);
					cell = new Cell(new Phrase("No. "+bankTransaction.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table1.addCell(cell);
					cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table1.addCell(cell);
					cell = new Cell(new Phrase("Date : "+bankTransaction.getFormatedTransactionDate(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table1.addCell(cell);
					for (int j=0; j<6; j++) {
						cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setColspan(3);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table1.addCell(cell);
					}
					document.add(table1);
					
					Table table = new Table(2);
					table.setWidth(100);
					table.setCellsFitPage(true);
					table.setBorder(Rectangle.NO_BORDER);
					table.setBorderWidth(1);
					table.setPadding(1);
					table.setSpacing(0);
					int w[] = {70, 30};
					table.setWidths(w);
					table.setAutoFillEmptyCells(true);
					// set table title
					
					if (bankTransaction.isDebit()==true) {
						cell = new Cell(new Phrase("RECEIPT VOUCHER", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.NORMAL)));
						cell.setColspan(2);
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						for (int j=0; j<4; j++) {
							cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setColspan(2);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);
						}
						cell = new Cell(new Phrase("Received from : "+(bankTransaction.getCustomer()!=null?bankTransaction.getCustomer().getCompany():"-"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						cell = new Cell(new Phrase("Credit A/C : "+(bankTransaction.getToBankAccount()!=null?bankTransaction.getToBankAccount().getBank().getName():" - "), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
					} else {
						cell = new Cell(new Phrase("PAYMENT VOUCHER", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.NORMAL)));
						cell.setColspan(2);
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						for (int j=0; j<4; j++) {
							cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setColspan(2);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);
						}
						cell = new Cell(new Phrase("Paid To : "+(bankTransaction.getVendor()!=null?bankTransaction.getVendor().getCompany():"-"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						cell = new Cell(new Phrase("Debit A/C : "+(bankTransaction.getFromBankAccount()!=null?bankTransaction.getFromBankAccount().getBank().getName():" - "), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell); 
					}
					
					cell = new Cell(new Phrase("Through BANK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
					table.addCell(cell);
					cell = new Cell(new Phrase("Amount",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
					table.addCell(cell);
					table.endHeaders();	
					
					// data
					cell = new Cell(new Phrase((bankTransaction.getReference().length()>0?bankTransaction.getReference()+"\n":"")+bankTransaction.getNote(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new Cell(new Phrase((bankTransaction.getCurrency()!=null?bankTransaction.getCurrency().getSymbol():"")+" "+bankTransaction.getFormatedAmount()+"  ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);
					int m = 31;
					int l = 0;
					//int l = (bankTransaction.getReference()+bankTransaction.getNote()).length();
					//m = m - Math.round(l / 90);
					if (bankTransaction.getReference()!=null && bankTransaction.getReference().length()>0) l = 1;
					if (bankTransaction.getNote().indexOf("\n")>0) {
					    StringTokenizer tokenizer = new StringTokenizer(bankTransaction.getNote(), "\n");
					    while (tokenizer.hasMoreTokens()) {
					        tokenizer.nextToken();
					        l++;
					    }
					    m = m - Math.round(4 *(l - 1));
					} else {
					    l = (bankTransaction.getNote()).length();
					    m = m - Math.round(l / 20);
					}
					// space
					for (int i=0; i<m; i++) {
							cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setBorder(Rectangle.RIGHT);
							cell.setNoWrap(false);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);
							cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setNoWrap(false);
							cell.setBorder(Rectangle.LEFT);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);
					}
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new Cell(new Phrase("SAY : "+AmountSay.getSaying(bankTransaction.getFormatedAmount()).trim()+" "+bankTransaction.getCurrency().getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.BOTTOM);
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					document.add(table);
					
					// TT
					Table table2 = new Table(3);
					table2.setWidth(99);
					table2.setCellsFitPage(true);
					table2.setBorder(Rectangle.NO_BORDER);
					table2.setBorderWidth(1);
					table2.setPadding(1);
					table2.setSpacing(0);
					int w2[] = {33, 33, 33};
					table2.setWidths(w2);
					table2.setAutoFillEmptyCells(true);
					
					// space
					for (int i=0; i<25; i++) {
						cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setColspan(3);
						cell.setNoWrap(false);
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell);
					}
					
					cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					if (bankTransaction.isDebit()==true) {
						cell = new Cell(new Phrase("Receipt by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell);
					} else {
				    cell = new Cell(new Phrase("Name & Signature of Payee", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell);
					}
					cell = new Cell(new Phrase("Checked by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					cell = new Cell(new Phrase("Approved by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					
					
					document.add(table2);
					
					
					document.close();
					//send pdf to browser
					response.setContentType("application/pdf");
					response.setContentLength(baos.size());
					ServletOutputStream out = response.getOutputStream();
					baos.writeTo(out);
					out.flush();
					
			} else {
		    Rectangle pageSize = new Rectangle(612, 936);
				com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,36,36,36,36);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter.getInstance(document, baos);
				  
				// footer page
				//HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
				//footer.setBorder(Rectangle.NO_BORDER);
				//document.setFooter(footer);
				document.open();
				
				//BankTransaction bankTransaction = BankTransactionDAO.getInstance().get(journal.getBankTransaction().getId());
				
				// table upper
				Table table1 = new Table(3);
				table1.setWidth(100);
				table1.setCellsFitPage(true);
				float[] a = {10,50,40};
				table1.setWidths(a);
				table1.setBorder(Rectangle.NO_BORDER);
				table1.setCellsFitPage(true);
				table1.setBorderWidth(1);
				table1.setPadding(1);
				table1.setSpacing(0);
				
				Cell cell = null;
				if (users.getOrganization().getLogoContentType()!=null && users.getOrganization().getLogoContentType().indexOf("image")!=-1) {
					try {
					    byte[] out = users.getOrganization().getLogo();
					    Image image = Image.getInstance(Toolkit.getDefaultToolkit().createImage(out), null);
					    image.scaleAbsoluteHeight(45);
					    image.scaleAbsoluteWidth(45);
					    cell = new Cell(image);
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setRowspan(2);
							table1.addCell(cell);
					} catch(Exception ex) {
					    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setRowspan(2);
							table1.addCell(cell);
					}
				} else {
			    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setRowspan(2);
					table1.addCell(cell);
				}
				
				cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("No. "+journal.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table1.addCell(cell);
				cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("Date : "+journal.getFormatedJournalDate(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table1.addCell(cell);
				for (int j=0; j<6; j++) {
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setColspan(3);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table1.addCell(cell);
				}
				document.add(table1);
				
				Table table = new Table(2);
				table.setWidth(100);
				table.setCellsFitPage(true);
				table.setBorder(Rectangle.NO_BORDER);
				table.setBorderWidth(1);
				table.setPadding(1);
				table.setSpacing(0);
				int w[] = {70, 30};
				table.setWidths(w);
				table.setAutoFillEmptyCells(true);
				// set table title
				
				cell = new Cell(new Phrase("JOURNAL VOUCHER", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.NORMAL)));
				cell.setColspan(2);
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				for (int j=0; j<4; j++) {
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				}
				cell = new Cell(new Phrase("Received from : "+"-", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase("Credit A/C : "+"-", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new Cell(new Phrase("Through BANK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table.addCell(cell);
				cell = new Cell(new Phrase("Amount",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table.addCell(cell);
				table.endHeaders();	
				
				// data
				cell = new Cell(new Phrase((journal.getReference()!=null?(journal.getReference().length()>0?journal.getReference()+"\n":""):"")+journal.getDescription(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase((journal.getCurrency()!=null?journal.getCurrency().getSymbol():"")+" "+journal.getFormatedAmount()+"  ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
				int m = 31;
				int l = 0;
				//int l = (bankTransaction.getReference()+bankTransaction.getNote()).length();
				//m = m - Math.round(l / 90);
				if (journal.getReference()!=null && journal.getReference().length()>0) l = 1;
				if (journal.getDescription()!=null && journal.getDescription().indexOf("\n")>0) {
				    StringTokenizer tokenizer = new StringTokenizer(journal.getDescription(), "\n");
				    while (tokenizer.hasMoreTokens()) {
				        tokenizer.nextToken();
				        l++;
				    }
				    m = m - Math.round(4 *(l - 1));
				} else {
				    l = journal.getDescription()!=null?(journal.getDescription()).length():0;
				    m = m - Math.round(l / 20);
				}
				// space
				for (int i=0; i<m; i++) {
						cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setNoWrap(false);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setNoWrap(false);
						cell.setBorder(Rectangle.LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
				}
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase("SAY : "+AmountSay.getSaying(journal.getFormatedAmount()).trim()+" "+journal.getCurrency().getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				document.add(table);
				
				// TT
				Table table2 = new Table(3);
				table2.setWidth(99);
				table2.setCellsFitPage(true);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				int w2[] = {33, 33, 33};
				table2.setWidths(w2);
				table2.setAutoFillEmptyCells(true);
				
				// space
				for (int i=0; i<25; i++) {
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setColspan(3);
					cell.setNoWrap(false);
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
				}
				
				cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Created by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Checked by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Approved by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				
				
				document.add(table2);
				
				
				document.close();
				//send pdf to browser
				response.setContentType("application/pdf");
				response.setContentLength(baos.size());
				ServletOutputStream out = response.getOutputStream();
				baos.writeTo(out);
				out.flush();
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return null;
	}
	
	/** 
	 * Method performReport
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performApReportPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			
				if (form.getCalendar("fromJournalDate")==null) form.setCurentCalendar("fromJournalDate");
				if (form.getCalendar("toJournalDate")==null) form.setCurentCalendar("toJournalDate");
			
				List journalLst = JournalDAO.getInstance().getSession().createCriteria(Journal.class)
					.add(Restrictions.ge("JournalDate", new Date(form.getCalendar("fromJournalDate").getTime().getTime())))
					.add(Restrictions.le("JournalDate", new Date(form.getCalendar("toJournalDate").getTime().getTime())))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					//.add(Restrictions.ne("Edit", Boolean.TRUE))
					.addOrder(Order.asc("JournalDate"))
					.list();
				
				// write to pdf document
				Rectangle pageSize = new Rectangle(612, 936);
				com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,36,36,36,36);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter.getInstance(document, baos);
				  
				// footer page
				HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
				footer.setBorder(Rectangle.NO_BORDER);
				document.setFooter(footer);
				document.open();
				
				// table upper
				Table table1 = new Table(2);
				table1.setWidth(100);
				table1.setCellsFitPage(true);
				float[] a = {50,50};
				table1.setWidths(a);
				table1.setBorder(Rectangle.NO_BORDER);
				table1.setBorderWidth(1);
				table1.setPadding(1);
				table1.setSpacing(0);
				
				Cell cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("Date : "+form.getString("fromJournalDate")+" - "+form.getString("toJournalDate"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				document.add(table1);
				
				// header table
				Table firstTable = new Table(6);
				firstTable.setWidth(100);
				firstTable.setCellsFitPage(true);
				firstTable.setBorderWidth(1);
				firstTable.setBorder(Rectangle.NO_BORDER);
				firstTable.setPadding(1);
				firstTable.setSpacing(0);
				int b2[] = {10, 12, 38, 20, 10, 10};
				firstTable.setWidths(b2);
				
				cell = new Cell(new Phrase("Date",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("Number",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("COA",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("Description",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("Debet",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("Kredit",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				firstTable.addCell(cell);
				firstTable.endHeaders();
				
				Iterator iterator2 = journalLst.iterator();
				while (iterator2.hasNext()) {
					Journal journal = (Journal)iterator2.next();
					int j=0;
					Iterator iterator = journal.getJournalDetails()!=null?journal.getJournalDetails().iterator():new LinkedList().iterator();
					while (iterator.hasNext()) {
					  JournalDetail journalDetail = (JournalDetail)iterator.next();
					  if (j==0) {
							cell = new Cell(new Phrase(Formater.getFormatedDate(journal.getJournalDate()),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
							cell = new Cell(new Phrase(journal.getNumber(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
					  } else {
				      cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
							cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
					  }
					  cell = new Cell(new Phrase(journalDetail.getId().getChartOfAccount().getNumber()+"\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorder(Rectangle.RIGHT);
						firstTable.addCell(cell);
						cell = new Cell(new Phrase(journalDetail.getDescription(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorder(Rectangle.RIGHT);
						firstTable.addCell(cell);
						
						//debit
						if(journalDetail.getId().getChartOfAccount().isDebit()==true){
							if(journalDetail.getAmount() > 0){
								cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion()+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorder(Rectangle.RIGHT);
								firstTable.addCell(cell);
							} else {
								cell = new Cell(new Phrase(""+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorder(Rectangle.RIGHT);
								firstTable.addCell(cell);
							}
						} else if (journalDetail.getId().getChartOfAccount().isDebit()==false) {
							if(journalDetail.getAmount() < 0){
								cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion()+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorder(Rectangle.RIGHT);
								firstTable.addCell(cell);
							} else {
								cell = new Cell(new Phrase(""+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorder(Rectangle.RIGHT);
								firstTable.addCell(cell);
							}
						}
						//credit
						if (journalDetail.getId().getChartOfAccount().isDebit()==false) {
						  if(journalDetail.getAmount() > 0){
								cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorder(Rectangle.NO_BORDER);
								firstTable.addCell(cell);
						  }else{
							  cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, com.lowagie.text.Font.NORMAL)));
							  cell.setVerticalAlignment(Element.ALIGN_RIGHT);
							  cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							  cell.setBorder(Rectangle.NO_BORDER);
							  firstTable.addCell(cell);
						  }
						} else if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
							if(journalDetail.getAmount() < 0){
							  cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
							  cell.setVerticalAlignment(Element.ALIGN_RIGHT);
							  cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							  cell.setBorder(Rectangle.NO_BORDER);
							  firstTable.addCell(cell);
							}else{
								cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorder(Rectangle.NO_BORDER);
								firstTable.addCell(cell);
							}
						}
						j++;
					}
					// end journal
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP);
					firstTable.addCell(cell);
				}
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(6);
				firstTable.addCell(cell);
				document.add(firstTable);
				
				document.close();
				//send pdf to browser
				response.setContentType("application/pdf");
				response.setContentLength(baos.size());
				ServletOutputStream out = response.getOutputStream();
				baos.writeTo(out);
				out.flush();
				
				
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return null;
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
	private ActionForward performArList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
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
			if (form.getString("isPosted")==null || form.getString("isPosted").length()==0) form.setString("isPosted", "N");
			//save start and count attribute on session
			httpSession.setAttribute(CommonConstants.START,Integer.toString(start));
			httpSession.setAttribute(CommonConstants.COUNT,Integer.toString(count));
			List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("journalTypeLst", journalTypeLst);
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("projectLst", projectLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
			Criteria criteria = JournalDAO.getInstance().getSession().createCriteria(Journal.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("journalTypeId")>0) criteria.add(Restrictions.eq("JournalType.Id", new Long(form.getLong("journalTypeId"))));
			if (form.getString("isPosted")!=null && form.getString("isPosted").equalsIgnoreCase("Y"))criteria.add(Restrictions.eq("Posted", Boolean.TRUE));
			if (form.getString("isPosted")!=null && form.getString("isPosted").equalsIgnoreCase("N"))criteria.add(Restrictions.eq("Posted", Boolean.FALSE));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			if (form.getCalendar("journalDate")!=null)criteria.add(Restrictions.eq("JournalDate", new Date(form.getCalendar("journalDate").getTime().getTime())));
			if (form.getLong("departmentId")>0) criteria.createCriteria("JournalDetails", "JournalDetail").add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = JournalDAO.getInstance().getSession().createCriteria(Journal.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			if (form.getCalendar("journalDate")!=null)criteria.add(Restrictions.eq("JournalDate", new Date(form.getCalendar("journalDate").getTime().getTime())));
			if (form.getLong("projectId")>0) criteria.add(Restrictions.eq("Project.Id", new Long(form.getLong("projectId"))));
			if (form.getLong("journalTypeId")>0) criteria.add(Restrictions.eq("JournalType.Id", new Long(form.getLong("journalTypeId"))));
			if (form.getString("isPosted")!=null && form.getString("isPosted").equalsIgnoreCase("Y"))criteria.add(Restrictions.eq("Posted", Boolean.TRUE));
			if (form.getString("isPosted")!=null && form.getString("isPosted").equalsIgnoreCase("N"))criteria.add(Restrictions.eq("Posted", Boolean.FALSE));
			if (form.getLong("departmentId")>0) criteria.createCriteria("JournalDetails", "JournalDetail").add(Restrictions.eq("Department.Id", new Long(form.getLong("departmentId"))));
			//criteria.addOrder(Order.desc("Name"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("JOURNAL",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
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
	private ActionForward performArForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			// remove
			Journal obj = (Journal)httpSession.getAttribute("journal");
			if (form.getLong("chartOfAccountId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEJOURNALDETAIL")) {
				JournalDetail removeJournalDetail = null;
				Iterator iterator = obj.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail)iterator.next();
					if (form.getLong("chartOfAccountId") == journalDetail.getId().getChartOfAccount().getId()) {
							removeJournalDetail = journalDetail;
					}
				}
				if (removeJournalDetail!=null) {
					Set set = obj.getJournalDetails();
					set.remove(removeJournalDetail);
					obj.setJournalDetails(set);
				}
				form.setString("subaction", "");
				form.setString("chartOfAccountId", "");
				httpSession.setAttribute("journal", obj);
			}
			// relationships
			List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("journalTypeLst", journalTypeLst);
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
			request.setAttribute("departmentLst", departmentLst);
			List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("vendorLst", vendorLst);
			List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
			request.setAttribute("customerLst", customerLst);
			List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("currencyLst", currencyLst);
			if (form.getLong("journalId") == 0) {
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Status", new String(CommonConstants.OPEN)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				if (obj==null) {
					form.setString("journalId",0);
					form.setCurentTimestamp("createOn");
					form.setString("number", RunningNumberDAO.getInstance().getJournalNumber());
					form.setCurentCalendar("journalDate");
				} else if (obj!=null) {
					form.setString("journalId",obj.getId());
					form.setString("description",obj.getDescription());
					form.setString("number",obj.getNumber());
					form.setString("reference",obj.getReference());
					form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
					form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
					form.setCalendar("journalDate",obj.getJournalDate());
					form.setString("journalTypeId",obj.getJournalType()!=null?obj.getJournalType().getId():0);
					form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
					Set journalDetailLst = obj.getJournalDetails();
					request.setAttribute("journalDetailLst", journalDetailLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				if (obj==null) {
					obj = JournalDAO.getInstance().get(form.getLong("journalId"));
					httpSession.setAttribute("journal",obj);
				}
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("projectLst", projectLst);
				//httpSession.setAttribute("Journal",obj);
				form.setString("journalId",obj.getId());
				form.setString("description",obj.getDescription());
				form.setString("number",obj.getNumber());
				form.setString("reference",obj.getReference());
				form.setString("currencyId",obj.getCurrency()!=null?obj.getCurrency().getId():0);
				form.setString("customerId",obj.getCustomer()!=null?obj.getCustomer().getId():0);
				form.setCalendar("journalDate",obj.getJournalDate());
				form.setString("journalTypeId",obj.getJournalType()!=null?obj.getJournalType().getId():0);
				form.setString("vendorId",obj.getVendor()!=null?obj.getVendor().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
				Set journalDetailLst = obj.getJournalDetails();
				request.setAttribute("journalDetailLst", journalDetailLst);
			}
			if (obj!=null && form.getLong("chartOfAccountId") > 0) {
				Iterator iterator = obj.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail)iterator.next();
					if (form.getLong("chartOfAccountId") == journalDetail.getId().getChartOfAccount().getId()) {
						form.setString("amount", Formater.getFormatedOutputForm(journalDetail.getAmount()));
						form.setString("departmentId", journalDetail.getDepartment()!=null?journalDetail.getDepartment().getId():0);
						form.setString("journalDetailDescription", journalDetail.getDescription());
						form.setString("chartOfAccountNumber", journalDetail.getId().getChartOfAccount().getNumber());
					}
				}
			}
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			// get total
			double debitAmount = 0;
			double creditAmount = 0;
			if (obj!=null) {
				java.util.Iterator iterator = obj.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail) iterator.next();
					if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
						if (journalDetail.getAmount()>0) {
							debitAmount = debitAmount + journalDetail.getAmount();
						} else {
							creditAmount = creditAmount + (-journalDetail.getAmount());
						}
					} else if (journalDetail.getId().getChartOfAccount().isDebit()==false){
						if (journalDetail.getAmount()<0) {
							debitAmount = debitAmount + (-journalDetail.getAmount());
						} else {
							creditAmount = creditAmount + journalDetail.getAmount();
						}
					}
				}
			}
			request.setAttribute("debitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitAmount));
			request.setAttribute("creditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditAmount));
		}catch(Exception ex) {
			try {
				List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("journalTypeLst", journalTypeLst);
				List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("chartOfAccountLst", chartOfAccountLst);
				List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
				request.setAttribute("departmentLst", departmentLst);
				List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("vendorLst", vendorLst);
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
				request.setAttribute("currencyLst", currencyLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
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
	private ActionForward performArSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("journal");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Journal obj = (Journal)httpSession.getAttribute("journal");
			//if (obj==null) obj = new Journal();
			if (form.getLong("journalId") == 0) {
				obj = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
				.add(Restrictions.eq("Number", form.getString("number")))
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.uniqueResult();
				if (obj==null) {
					obj = (Journal)httpSession.getAttribute("journal");
					if (obj==null) obj = new Journal();
					Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
					obj.setCurrency(currency);
					Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
					obj.setCustomer(customers);
					obj.setDescription(form.getString("description"));
					OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
					//log.info("ER : "+CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup));
					obj.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup, form.getCalendar("journalDate")));
					obj.setJournalDate(form.getCalendar("journalDate")!=null?form.getCalendar("journalDate").getTime():null);
					JournalType journalType = JournalTypeDAO.getInstance().get(form.getLong("journalTypeId"));
					obj.setJournalType(journalType);
					obj.setNumber(form.getString("number"));
					obj.setOrganization(users.getOrganization());
					obj.setPosted(false);
					obj.setReference(form.getString("reference"));
					Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
					obj.setVendor(vendors);
					obj.setCreateBy(users);
					obj.setCreateOn(form.getTimestamp("createOn"));
				} else {
					List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("journalTypeLst", journalTypeLst);
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
					List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("vendorLst", vendorLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("currencyLst", currencyLst);
					Set journalDetailLst = obj!=null?obj.getJournalDetails():new LinkedHashSet();
					request.setAttribute("journalDetailLst", journalDetailLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				if (obj==null) obj = JournalDAO.getInstance().get(form.getLong("journalId"));
				Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
				obj.setCurrency(currency);
				Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
				obj.setCustomer(customers);
				obj.setDescription(form.getString("description"));
				//OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
				//obj.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(currency, organizationSetup.getDefaultCurrency(), organizationSetup));
				obj.setJournalDate(form.getCalendar("journalDate")!=null?form.getCalendar("journalDate").getTime():null);
				JournalType journalType = JournalTypeDAO.getInstance().get(form.getLong("journalTypeId"));
				obj.setJournalType(journalType);
				obj.setNumber(form.getString("number"));
				obj.setOrganization(users.getOrganization());
				//obj.setPosted(false);
				obj.setReference(form.getString("reference"));
				Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
				obj.setVendor(vendors);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy);
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("journalId"));
				//JournalDAO.getInstance().update(obj);
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDJOURNALDETAIL")) {
				if (form.getLong("chartOfAccountId") >0 || form.getString("chartOfAccountNumber").length()>0) {
				  JournalDetail journalDetail = new JournalDetail();
					ChartOfAccount chartOfAccount = null;
					if (form.getLong("chartOfAccountId")>0) chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
					if (form.getString("chartOfAccountNumber").length()>0) chartOfAccount = (ChartOfAccount)ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class).add(Restrictions.eq("Number", form.getString("chartOfAccountNumber"))).setMaxResults(1).uniqueResult();
					JournalDetailPK journalDetailPK = new JournalDetailPK();
					journalDetailPK.setChartOfAccount(chartOfAccount);
					journalDetailPK.setJournal(obj);
					journalDetail.setId(journalDetailPK);
					journalDetail.setAmount(form.getDouble("amount"));
					journalDetail.setDescription(form.getString("journalDetailDescription"));
					Department department = DepartmentDAO.getInstance().get(form.getLong("departmentId"));
					journalDetail.setDepartment(department);
					Set set = obj.getJournalDetails();
					if (set==null) set = new LinkedHashSet();
					JournalDetail removeJournalDetail = null;
					Iterator iterator = set.iterator();
					while (iterator.hasNext()) {
						JournalDetail JournalDetail2 = (JournalDetail)iterator.next();
						if (form.getLong("chartOfAccountId")==JournalDetail2.getId().getChartOfAccount().getId()) {
							removeJournalDetail = JournalDetail2;
						}
					}
					if (removeJournalDetail!=null) {
						set.remove(removeJournalDetail);
						set.add(journalDetail);
					} else {
						set.add(journalDetail);
					}
					obj.setJournalDetails(set);
					//log.info("S : "+set.size());
					// netral
					form.setString("chartOfAccountId", "");
					form.setString("chartOfAccountNumber", "");
					form.setString("amount", "");
					form.setString("departmentId", "");
					form.setString("journalDetailDescription", "");
					form.setString("subaction", "");
				}
				// netral
				form.setString("chartOfAccountId", "");
				form.setString("chartOfAccountNumber", "");
				form.setString("amount", "");
				form.setString("departmentId", "");
				form.setString("journalDetailDescription", "");
				form.setString("subaction", "");
			}
			// save to session
			httpSession.setAttribute("journal", obj);
			// save all
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("SAVEALL")) {
				if (obj.getId()==0) {
					Session session = JournalDAO.getInstance().getSession();
					Transaction transaction = session.beginTransaction();
					RunningNumberDAO.getInstance().updateJournalNumber(session);
					JournalDAO.getInstance().save(obj, session);
					transaction.commit();
				} else {
					JournalDAO.getInstance().update(obj);
				}
				// remove session
				httpSession.removeAttribute("journal");
				// finish
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
		}catch(Exception ex) {
			try {
				try {
					List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("journalTypeLst", journalTypeLst);
					List chartOfAccountLst = ChartOfAccountDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Name")).list();
					request.setAttribute("departmentLst", departmentLst);
					List vendorLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("vendorLst", vendorLst);
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class).add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Name"));
					request.setAttribute("currencyLst", currencyLst);
					List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.ne("Status", new String(CommonConstants.CANCEL)))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("projectLst", projectLst);
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?journalId="+form.getLong("journalId"));
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
	private ActionForward performArDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			Journal journal = JournalDAO.getInstance().get(form.getLong("journalId"));
			request.setAttribute("journal", journal);
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			// get total
			double debitAmount = 0;
			double creditAmount = 0;
			if (journal!=null) {
				java.util.Iterator iterator = journal.getJournalDetails().iterator();
				while (iterator.hasNext()) {
					JournalDetail journalDetail = (JournalDetail) iterator.next();
					if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
						if (journalDetail.getAmount()>0) {
							debitAmount = debitAmount + journalDetail.getAmount();
						} else {
							creditAmount = creditAmount + (-journalDetail.getAmount());
						}
					} else if (journalDetail.getId().getChartOfAccount().isDebit()==false){
						if (journalDetail.getAmount()<0) {
							debitAmount = debitAmount + (-journalDetail.getAmount());
						} else {
							creditAmount = creditAmount + journalDetail.getAmount();
						}
					}
				}
			}
			request.setAttribute("debitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitAmount));
			request.setAttribute("creditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditAmount));
		} catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
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
	private ActionForward performArDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Session session = JournalDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			Journal journal = JournalDAO.getInstance().get(form.getLong("journalId"));
			journal.getJournalDetails().removeAll(journal.getJournalDetails());
			JournalDAO.getInstance().delete(journal, session);
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
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
	private ActionForward performArPostingForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
				List journalLst = new java.util.LinkedList();
				for (int i=0; i<10; i++) {
					if (form.getString("isPostedLst",i).length()>0) {
						Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
						.add(Restrictions.eq("Id", new Long(form.getLong("journalIdLst",i))))
						.add(Restrictions.eq("Posted", Boolean.FALSE))
						.uniqueResult();
						journalLst.add(journal);
					}
				}
				httpSession.setAttribute("journalLst", journalLst);
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
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
	private ActionForward performArPostingConfirm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				httpSession.removeAttribute("journalLst");
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			List journalLst = (List) httpSession.getAttribute("journalLst");
			Session session = JournalDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			if (journalLst!=null && journalLst.size() > 0) {
				java.util.Iterator iterator = journalLst.iterator();
				while (iterator.hasNext()) {
					Journal obj = (Journal)iterator.next();
					obj.setPosted(true);
					// get journalDetail of jounal
					Iterator iterator2 = obj.getJournalDetails().iterator();
					while (iterator2.hasNext()) {
						JournalDetail journalDetail = (JournalDetail)iterator2.next();
						//ChartOfAccount chartOfAccount = journalDetail.getId().getChartOfAccount();
						GeneralLedger generalLedger = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("LedgerDate", new Date(obj.getJournalDate().getTime())))
						.add(Restrictions.eq("ChartOfAccount.Id", new Long(journalDetail.getId().getChartOfAccount().getId())))
						.add(Restrictions.eq("Closed", Boolean.FALSE))
						.add(Restrictions.eq("Setup", Boolean.FALSE))
						.uniqueResult();
						if (generalLedger!=null) {
							generalLedger.setAmount(generalLedger.getAmount()+(journalDetail.getAmount() * obj.getExchangeRate()));
							// relation
							Set set = generalLedger.getJournals();
							if (set==null)set = new LinkedHashSet();
							set.add(obj);
							generalLedger.setJournals(set);
							GeneralLedgerDAO.getInstance().update(generalLedger, session);
						} else {
							generalLedger = new GeneralLedger();
							generalLedger.setChartOfAccount(journalDetail.getId().getChartOfAccount());
							generalLedger.setAmount(journalDetail.getAmount() * obj.getExchangeRate());
							generalLedger.setOrganization(users.getOrganization()); 
							generalLedger.setClosed(false);
							generalLedger.setCurrency(organizationSetup.getDefaultCurrency());
							generalLedger.setDebit(journalDetail.getId().getChartOfAccount().isDebit());
							generalLedger.setLedgerDate(obj.getJournalDate());
							generalLedger.setSetup(false);
							// relation
							Set set = generalLedger.getJournals();
							if (set==null)set = new LinkedHashSet();
							set.add(obj);
							generalLedger.setJournals(set);
							GeneralLedgerDAO.getInstance().save(generalLedger, session);
						}
					}
					// update journal
					JournalDAO.getInstance().update(obj, session);
				}
				httpSession.removeAttribute("journalLst");
			}
			transaction.commit();
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("confirm");
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
	private ActionForward performArUnpostingForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			form.setCurentCalendar("fromUnpostingDate");
			form.setCurentCalendar("toUnpostingDate");
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				return mapping.findForward("form");
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
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
	private ActionForward performArUnpostingConfirm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				//sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			Session session = JournalDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			if (form.getCalendar("fromUnpostingDate")!=null && form.getCalendar("toUnpostingDate")!=null) {
			  List generalLedgerLst = GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
				.add(Restrictions.ge("LedgerDate", new Date(form.getCalendar("fromUnpostingDate").getTime().getTime())))
				.add(Restrictions.le("LedgerDate", new Date(form.getCalendar("toUnpostingDate").getTime().getTime())))
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
				.add(Restrictions.eq("Closed", Boolean.FALSE))
				.add(Restrictions.eq("Setup", Boolean.FALSE)).list();
			  
			  java.util.Iterator iterator = generalLedgerLst.iterator();
				while (iterator.hasNext()) {
					GeneralLedger generalLedger = (GeneralLedger)iterator.next();
					Set journalLst = generalLedger.getJournals();
					Iterator iterator2 = journalLst.iterator();
					while (iterator2.hasNext()) {
						Journal journal = (Journal)iterator2.next();
						journal.setPosted(false);
						//JournalDAO.getInstance().update(journal, session);
						//set.remove(journal);
						//generalLedger.setJournals(set);
						JournalDAO.getInstance().update(journal, session);
					}
					generalLedger.getJournals().clear();
					generalLedger.setJournals(null);
					GeneralLedgerDAO.getInstance().delete(generalLedger, session);
				}
				
			} else {
				// cek sign ","
				if (form.getString("number")!=null && form.getString("number").indexOf(",")>0) {
					StringTokenizer stringTokenizer = new StringTokenizer(form.getString("number"), ",");
					while (stringTokenizer.hasMoreTokens()) {
						String string = (String)stringTokenizer.nextToken();
						Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
							.add(Restrictions.eq("Number", string.trim()))
							.setMaxResults(1)
							.uniqueResult();
						Iterator iterator = journal.getJournalDetails().iterator();
						while (iterator.hasNext()) {
							JournalDetail journalDetail = (JournalDetail)iterator.next();
							GeneralLedger generalLedger = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
								.add(Restrictions.eq("LedgerDate", new Date(journal.getJournalDate().getTime())))
								.add(Restrictions.eq("ChartOfAccount.Id", new Long(journalDetail.getId().getChartOfAccount().getId())))
								.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
								.add(Restrictions.eq("Closed", Boolean.FALSE))
								.add(Restrictions.eq("Setup", Boolean.FALSE))
								.setMaxResults(1)
								.uniqueResult();
							//log.info("[ GL ]");
							generalLedger.setAmount(generalLedger.getAmount() - (journalDetail.getAmount()*journal.getExchangeRate()));
							generalLedger.getJournals().remove(journal);
							GeneralLedgerDAO.getInstance().saveOrUpdate(generalLedger, session);
						}
						journal.setPosted(false);
						JournalDAO.getInstance().saveOrUpdate(journal, session);
					}
				} else {
					Journal journal = (Journal)JournalDAO.getInstance().getSession().createCriteria(Journal.class)
						.add(Restrictions.eq("Number", form.getString("number")))
						.setMaxResults(1)
						.uniqueResult();
					Iterator iterator = journal.getJournalDetails().iterator();
					while (iterator.hasNext()) {
						JournalDetail journalDetail = (JournalDetail)iterator.next();
						GeneralLedger generalLedger = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
							.add(Restrictions.eq("LedgerDate", new Date(journal.getJournalDate().getTime())))
							.add(Restrictions.eq("ChartOfAccount.Id", new Long(journalDetail.getId().getChartOfAccount().getId())))
							.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
							.add(Restrictions.eq("Closed", Boolean.FALSE))
							.add(Restrictions.eq("Setup", Boolean.FALSE))
							.setMaxResults(1)
							.uniqueResult();
						generalLedger.setAmount(generalLedger.getAmount() - (journalDetail.getAmount()*journal.getExchangeRate()));
						generalLedger.getJournals().remove(journal);
						GeneralLedgerDAO.getInstance().saveOrUpdate(generalLedger, session);
					}
					journal.setPosted(false);
					JournalDAO.getInstance().saveOrUpdate(journal, session);
				}
			}
			transaction.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("confirm");
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
	private ActionForward performArPostingDateForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			form.setCurentCalendar("fromJournalDate");
			form.setCurentCalendar("toJournalDate");
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				return mapping.findForward("form");
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
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
	private ActionForward performArPostingDateConfirm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    // devide date per 5 days
		    Calendar fromDate = form.getCalendar("fromJournalDate");
		    Calendar toDate = form.getCalendar("toJournalDate");
		    //int days = toDate.get(Calendar.DAY_OF_YEAR) - fromDate.get(Calendar.DAY_OF_YEAR) + 1;
		    //int l = Math.round(days/5);
		    //Calendar calendar = new GregorianCalendar();
		    //calendar = (Calendar)fromDate.clone();
		    //log.info("L : "+l);
		    //for (int i=0; i<=l; i++) {
		      //log.info("L : "+l+"//"+i);
		      //fromDate.set(Calendar.DAY_OF_YEAR, fromDate.get(Calendar.DAY_OF_YEAR) + (5));
		      //if (i<l) calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + (5));
		      //else if (i==l) calendar = (Calendar)toDate.clone();
			    // search journal from - to and unposting status
		      //log.info("journalLst : "++);
					List journalLst = JournalDAO.getInstance().getSession().createCriteria(Journal.class)
						.add(Restrictions.ge("JournalDate", new Date(fromDate.getTime().getTime())))
						.add(Restrictions.le("JournalDate", new Date(toDate.getTime().getTime())))
						.add(Restrictions.eq("Posted", Boolean.FALSE))
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.list();
					//log.info("journalLst : "+journalLst.size());
					// process
					Session session = JournalDAO.getInstance().getSession();
					Transaction transaction = session.beginTransaction();
					OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
					if (journalLst!=null && journalLst.size() > 0) {
						java.util.Iterator iterator = journalLst.iterator();
						while (iterator.hasNext()) {
							Journal obj = (Journal)iterator.next();
							obj.setPosted(true);
							// get journalDetail of jounal
							Iterator iterator2 = obj.getJournalDetails().iterator();
							while (iterator2.hasNext()) {
								JournalDetail journalDetail = (JournalDetail)iterator2.next();
								//ChartOfAccount chartOfAccount = journalDetail.getId().getChartOfAccount();
								GeneralLedger generalLedger = (GeneralLedger)GeneralLedgerDAO.getInstance().getSession().createCriteria(GeneralLedger.class)
								.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
								.add(Restrictions.eq("LedgerDate", new Date(obj.getJournalDate().getTime())))
								.add(Restrictions.eq("ChartOfAccount.Id", new Long(journalDetail.getId().getChartOfAccount().getId())))
								.add(Restrictions.eq("Closed", Boolean.FALSE))
								.add(Restrictions.eq("Setup", Boolean.FALSE))
								.uniqueResult();
								if (generalLedger!=null) {
									generalLedger.setAmount(generalLedger.getAmount()+(journalDetail.getAmount() * obj.getExchangeRate()));
									// relation
									Set set = generalLedger.getJournals();
									if (set==null)set = new LinkedHashSet();
									set.add(obj);
									generalLedger.setJournals(set);
									GeneralLedgerDAO.getInstance().update(generalLedger, session);
								} else {
									generalLedger = new GeneralLedger();
									generalLedger.setChartOfAccount(journalDetail.getId().getChartOfAccount());
									generalLedger.setAmount(journalDetail.getAmount() * obj.getExchangeRate());
									generalLedger.setOrganization(users.getOrganization()); 
									generalLedger.setClosed(false);
									generalLedger.setCurrency(organizationSetup.getDefaultCurrency());
									generalLedger.setDebit(journalDetail.getId().getChartOfAccount().isDebit());
									generalLedger.setLedgerDate(obj.getJournalDate());
									generalLedger.setSetup(false);
									// relation
									Set set = generalLedger.getJournals();
									if (set==null)set = new LinkedHashSet();
									set.add(obj);
									generalLedger.setJournals(set);
									GeneralLedgerDAO.getInstance().save(generalLedger, session);
								}
							}
							// update journal
							JournalDAO.getInstance().update(obj, session);
						}
						//httpSession.removeAttribute("journalLst");
					}
					transaction.commit();
					
		    //}
		    
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("confirm");
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
	private ActionForward performArPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			Journal journal = JournalDAO.getInstance().get(form.getLong("journalId"));			
			
			if (journal!=null && journal.getBankTransaction()!=null) {
			    ///
			    Rectangle pageSize = new Rectangle(612, 936);
					com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,36,36,36,36);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					PdfWriter.getInstance(document, baos);
					  
					// footer page
					//HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
					//footer.setBorder(Rectangle.NO_BORDER);
					//document.setFooter(footer);
					document.open();
					
					BankTransaction bankTransaction = BankTransactionDAO.getInstance().get(journal.getBankTransaction().getId());
					
					// table upper
					Table table1 = new Table(3);
					table1.setWidth(100);
					table1.setCellsFitPage(true);
					float[] a = {10,50,40};
					table1.setWidths(a);
					table1.setBorder(Rectangle.NO_BORDER);
					table1.setCellsFitPage(true);
					table1.setBorderWidth(1);
					table1.setPadding(1);
					table1.setSpacing(0);
					
					Cell cell = null;
					if (users.getOrganization().getLogoContentType()!=null && users.getOrganization().getLogoContentType().indexOf("image")!=-1) {
						try {
						    byte[] out = users.getOrganization().getLogo();
						    Image image = Image.getInstance(Toolkit.getDefaultToolkit().createImage(out), null);
						    image.scaleAbsoluteHeight(45);
						    image.scaleAbsoluteWidth(45);
						    cell = new Cell(image);
								cell.setBorder(Rectangle.NO_BORDER);
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setRowspan(2);
								table1.addCell(cell);
						} catch(Exception ex) {
						    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
								cell.setBorder(Rectangle.NO_BORDER);
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setRowspan(2);
								table1.addCell(cell);
						}
					} else {
				    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setRowspan(2);
						table1.addCell(cell);
					}
					
					cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table1.addCell(cell);
					cell = new Cell(new Phrase("No. "+bankTransaction.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table1.addCell(cell);
					cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table1.addCell(cell);
					cell = new Cell(new Phrase("Date : "+bankTransaction.getFormatedTransactionDate(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table1.addCell(cell);
					for (int j=0; j<6; j++) {
						cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setColspan(3);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table1.addCell(cell);
					}
					document.add(table1);
					
					Table table = new Table(2);
					table.setWidth(100);
					table.setCellsFitPage(true);
					table.setBorder(Rectangle.NO_BORDER);
					table.setBorderWidth(1);
					table.setPadding(1);
					table.setSpacing(0);
					int w[] = {70, 30};
					table.setWidths(w);
					table.setAutoFillEmptyCells(true);
					// set table title
					
					if (bankTransaction.isDebit()==true) {
						cell = new Cell(new Phrase("RECEIPT VOUCHER", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.NORMAL)));
						cell.setColspan(2);
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						for (int j=0; j<4; j++) {
							cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setColspan(2);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);
						}
						cell = new Cell(new Phrase("Received from : "+(bankTransaction.getCustomer()!=null?bankTransaction.getCustomer().getCompany():"-"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						cell = new Cell(new Phrase("Credit A/C : "+(bankTransaction.getToBankAccount()!=null?bankTransaction.getToBankAccount().getBank().getName():" - "), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
					} else {
						cell = new Cell(new Phrase("PAYMENT VOUCHER", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.NORMAL)));
						cell.setColspan(2);
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						for (int j=0; j<4; j++) {
							cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setColspan(2);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);
						}
						cell = new Cell(new Phrase("Paid To : "+(bankTransaction.getVendor()!=null?bankTransaction.getVendor().getCompany():"-"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						cell = new Cell(new Phrase("Debit A/C : "+(bankTransaction.getFromBankAccount()!=null?bankTransaction.getFromBankAccount().getBank().getName():" - "), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell); 
					}
					
					cell = new Cell(new Phrase("Through BANK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
					table.addCell(cell);
					cell = new Cell(new Phrase("Amount",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
					table.addCell(cell);
					table.endHeaders();	
					
					// data
					cell = new Cell(new Phrase((bankTransaction.getReference().length()>0?bankTransaction.getReference()+"\n":"")+bankTransaction.getNote(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new Cell(new Phrase((bankTransaction.getCurrency()!=null?bankTransaction.getCurrency().getSymbol():"")+" "+bankTransaction.getFormatedAmount()+"  ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					table.addCell(cell);
					int m = 31;
					int l = 0;
					//int l = (bankTransaction.getReference()+bankTransaction.getNote()).length();
					//m = m - Math.round(l / 90);
					if (bankTransaction.getReference()!=null && bankTransaction.getReference().length()>0) l = 1;
					if (bankTransaction.getNote().indexOf("\n")>0) {
					    StringTokenizer tokenizer = new StringTokenizer(bankTransaction.getNote(), "\n");
					    while (tokenizer.hasMoreTokens()) {
					        tokenizer.nextToken();
					        l++;
					    }
					    m = m - Math.round(4 *(l - 1));
					} else {
					    l = (bankTransaction.getNote()).length();
					    m = m - Math.round(l / 20);
					}
					// space
					for (int i=0; i<m; i++) {
							cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setBorder(Rectangle.RIGHT);
							cell.setNoWrap(false);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);
							cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setNoWrap(false);
							cell.setBorder(Rectangle.LEFT);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);
					}
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					cell = new Cell(new Phrase("SAY : "+AmountSay.getSaying(bankTransaction.getFormatedAmount()).trim()+" "+bankTransaction.getCurrency().getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.BOTTOM);
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
					document.add(table);
					
					// TT
					Table table2 = new Table(3);
					table2.setWidth(99);
					table2.setCellsFitPage(true);
					table2.setBorder(Rectangle.NO_BORDER);
					table2.setBorderWidth(1);
					table2.setPadding(1);
					table2.setSpacing(0);
					int w2[] = {33, 33, 33};
					table2.setWidths(w2);
					table2.setAutoFillEmptyCells(true);
					
					// space
					for (int i=0; i<25; i++) {
						cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setColspan(3);
						cell.setNoWrap(false);
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell);
					}
					
					cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					if (bankTransaction.isDebit()==true) {
						cell = new Cell(new Phrase("Receipt by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell);
					} else {
				    cell = new Cell(new Phrase("Name & Signature of Payee", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table2.addCell(cell);
					}
					cell = new Cell(new Phrase("Checked by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					cell = new Cell(new Phrase("Approved by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
					
					
					document.add(table2);
					
					
					document.close();
					//send pdf to browser
					response.setContentType("application/pdf");
					response.setContentLength(baos.size());
					ServletOutputStream out = response.getOutputStream();
					baos.writeTo(out);
					out.flush();
					
			} else {
		    Rectangle pageSize = new Rectangle(612, 936);
				com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,36,36,36,36);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter.getInstance(document, baos);
				  
				// footer page
				//HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
				//footer.setBorder(Rectangle.NO_BORDER);
				//document.setFooter(footer);
				document.open();
				
				//BankTransaction bankTransaction = BankTransactionDAO.getInstance().get(journal.getBankTransaction().getId());
				
				// table upper
				Table table1 = new Table(3);
				table1.setWidth(100);
				table1.setCellsFitPage(true);
				float[] a = {10,50,40};
				table1.setWidths(a);
				table1.setBorder(Rectangle.NO_BORDER);
				table1.setCellsFitPage(true);
				table1.setBorderWidth(1);
				table1.setPadding(1);
				table1.setSpacing(0);
				
				Cell cell = null;
				if (users.getOrganization().getLogoContentType()!=null && users.getOrganization().getLogoContentType().indexOf("image")!=-1) {
					try {
					    byte[] out = users.getOrganization().getLogo();
					    Image image = Image.getInstance(Toolkit.getDefaultToolkit().createImage(out), null);
					    image.scaleAbsoluteHeight(45);
					    image.scaleAbsoluteWidth(45);
					    cell = new Cell(image);
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setRowspan(2);
							table1.addCell(cell);
					} catch(Exception ex) {
					    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
							cell.setBorder(Rectangle.NO_BORDER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setRowspan(2);
							table1.addCell(cell);
					}
				} else {
			    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setRowspan(2);
					table1.addCell(cell);
				}
				
				cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("No. "+journal.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table1.addCell(cell);
				cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("Date : "+journal.getFormatedJournalDate(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table1.addCell(cell);
				for (int j=0; j<6; j++) {
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setColspan(3);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table1.addCell(cell);
				}
				document.add(table1);
				
				Table table = new Table(2);
				table.setWidth(100);
				table.setCellsFitPage(true);
				table.setBorder(Rectangle.NO_BORDER);
				table.setBorderWidth(1);
				table.setPadding(1);
				table.setSpacing(0);
				int w[] = {70, 30};
				table.setWidths(w);
				table.setAutoFillEmptyCells(true);
				// set table title
				
				cell = new Cell(new Phrase("JOURNAL VOUCHER", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.NORMAL)));
				cell.setColspan(2);
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
				for (int j=0; j<4; j++) {
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setColspan(2);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				}
				cell = new Cell(new Phrase("Received from : "+"-", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase("Credit A/C : "+"-", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				
				cell = new Cell(new Phrase("Through BANK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table.addCell(cell);
				cell = new Cell(new Phrase("Amount",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table.addCell(cell);
				table.endHeaders();	
				
				// data
				cell = new Cell(new Phrase((journal.getReference()!=null?(journal.getReference().length()>0?journal.getReference()+"\n":""):"")+journal.getDescription(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase((journal.getCurrency()!=null?journal.getCurrency().getSymbol():"")+" "+journal.getFormatedAmount()+"  ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cell);
				int m = 31;
				int l = 0;
				//int l = (bankTransaction.getReference()+bankTransaction.getNote()).length();
				//m = m - Math.round(l / 90);
				if (journal.getReference()!=null && journal.getReference().length()>0) l = 1;
				if (journal.getDescription()!=null && journal.getDescription().indexOf("\n")>0) {
				    StringTokenizer tokenizer = new StringTokenizer(journal.getDescription(), "\n");
				    while (tokenizer.hasMoreTokens()) {
				        tokenizer.nextToken();
				        l++;
				    }
				    m = m - Math.round(4 *(l - 1));
				} else {
				    l = journal.getDescription()!=null?(journal.getDescription()).length():0;
				    m = m - Math.round(l / 20);
				}
				// space
				for (int i=0; i<m; i++) {
						cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setNoWrap(false);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setNoWrap(false);
						cell.setBorder(Rectangle.LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
				}
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				cell = new Cell(new Phrase("SAY : "+AmountSay.getSaying(journal.getFormatedAmount()).trim()+" "+journal.getCurrency().getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.BOTTOM);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
				document.add(table);
				
				// TT
				Table table2 = new Table(3);
				table2.setWidth(99);
				table2.setCellsFitPage(true);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				int w2[] = {33, 33, 33};
				table2.setWidths(w2);
				table2.setAutoFillEmptyCells(true);
				
				// space
				for (int i=0; i<25; i++) {
					cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setColspan(3);
					cell.setNoWrap(false);
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table2.addCell(cell);
				}
				
				cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("_________________________", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Created by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Checked by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Approved by", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table2.addCell(cell);
				
				
				document.add(table2);
				
				
				document.close();
				//send pdf to browser
				response.setContentType("application/pdf");
				response.setContentLength(baos.size());
				ServletOutputStream out = response.getOutputStream();
				baos.writeTo(out);
				out.flush();
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return null;
	}
	
	/** 
	 * Method performReport
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performArReportPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			
				if (form.getCalendar("fromJournalDate")==null) form.setCurentCalendar("fromJournalDate");
				if (form.getCalendar("toJournalDate")==null) form.setCurentCalendar("toJournalDate");
			
				List journalLst = JournalDAO.getInstance().getSession().createCriteria(Journal.class)
					.add(Restrictions.ge("JournalDate", new Date(form.getCalendar("fromJournalDate").getTime().getTime())))
					.add(Restrictions.le("JournalDate", new Date(form.getCalendar("toJournalDate").getTime().getTime())))
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					//.add(Restrictions.ne("Edit", Boolean.TRUE))
					.addOrder(Order.asc("JournalDate"))
					.list();
				
				// write to pdf document
				Rectangle pageSize = new Rectangle(612, 936);
				com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,36,36,36,36);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter.getInstance(document, baos);
				  
				// footer page
				HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
				footer.setBorder(Rectangle.NO_BORDER);
				document.setFooter(footer);
				document.open();
				
				// table upper
				Table table1 = new Table(2);
				table1.setWidth(100);
				table1.setCellsFitPage(true);
				float[] a = {50,50};
				table1.setWidths(a);
				table1.setBorder(Rectangle.NO_BORDER);
				table1.setBorderWidth(1);
				table1.setPadding(1);
				table1.setSpacing(0);
				
				Cell cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("Date : "+form.getString("fromJournalDate")+" - "+form.getString("toJournalDate"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				document.add(table1);
				
				// header table
				Table firstTable = new Table(6);
				firstTable.setWidth(100);
				firstTable.setCellsFitPage(true);
				firstTable.setBorderWidth(1);
				firstTable.setBorder(Rectangle.NO_BORDER);
				firstTable.setPadding(1);
				firstTable.setSpacing(0);
				int b2[] = {10, 12, 38, 20, 10, 10};
				firstTable.setWidths(b2);
				
				cell = new Cell(new Phrase("Date",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("Number",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("COA",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("Description",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("Debet",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("Kredit",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
				firstTable.addCell(cell);
				firstTable.endHeaders();
				
				Iterator iterator2 = journalLst.iterator();
				while (iterator2.hasNext()) {
					Journal journal = (Journal)iterator2.next();
					int j=0;
					Iterator iterator = journal.getJournalDetails()!=null?journal.getJournalDetails().iterator():new LinkedList().iterator();
					while (iterator.hasNext()) {
					  JournalDetail journalDetail = (JournalDetail)iterator.next();
					  if (j==0) {
							cell = new Cell(new Phrase(Formater.getFormatedDate(journal.getJournalDate()),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
							cell = new Cell(new Phrase(journal.getNumber(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
					  } else {
				      cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
							cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
					  }
					  cell = new Cell(new Phrase(journalDetail.getId().getChartOfAccount().getNumber()+"\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorder(Rectangle.RIGHT);
						firstTable.addCell(cell);
						cell = new Cell(new Phrase(journalDetail.getDescription(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorder(Rectangle.RIGHT);
						firstTable.addCell(cell);
						
						//debit
						if(journalDetail.getId().getChartOfAccount().isDebit()==true){
							if(journalDetail.getAmount() > 0){
								cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion()+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorder(Rectangle.RIGHT);
								firstTable.addCell(cell);
							} else {
								cell = new Cell(new Phrase(""+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorder(Rectangle.RIGHT);
								firstTable.addCell(cell);
							}
						} else if (journalDetail.getId().getChartOfAccount().isDebit()==false) {
							if(journalDetail.getAmount() < 0){
								cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion()+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorder(Rectangle.RIGHT);
								firstTable.addCell(cell);
							} else {
								cell = new Cell(new Phrase(""+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorder(Rectangle.RIGHT);
								firstTable.addCell(cell);
							}
						}
						//credit
						if (journalDetail.getId().getChartOfAccount().isDebit()==false) {
						  if(journalDetail.getAmount() > 0){
								cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorder(Rectangle.NO_BORDER);
								firstTable.addCell(cell);
						  }else{
							  cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, com.lowagie.text.Font.NORMAL)));
							  cell.setVerticalAlignment(Element.ALIGN_RIGHT);
							  cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							  cell.setBorder(Rectangle.NO_BORDER);
							  firstTable.addCell(cell);
						  }
						} else if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
							if(journalDetail.getAmount() < 0){
							  cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
							  cell.setVerticalAlignment(Element.ALIGN_RIGHT);
							  cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							  cell.setBorder(Rectangle.NO_BORDER);
							  firstTable.addCell(cell);
							}else{
								cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_CENTER);
								cell.setBorder(Rectangle.NO_BORDER);
								firstTable.addCell(cell);
							}
						}
						j++;
					}
					// end journal
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
					firstTable.addCell(cell);
					cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_RIGHT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.TOP);
					firstTable.addCell(cell);
				}
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(6);
				firstTable.addCell(cell);
				document.add(firstTable);
				
				document.close();
				//send pdf to browser
				response.setContentType("application/pdf");
				response.setContentLength(baos.size());
				ServletOutputStream out = response.getOutputStream();
				baos.writeTo(out);
				out.flush();
				
				
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return null;
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