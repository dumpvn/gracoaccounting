//---------------------------------------------------------
// Application: Garage
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright © 2005 MPE
// Generated at Sat Sep 03 19:38:16 GMT+07:00 2005
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
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.lowagie.text.Cell;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.mpe.financial.model.Department;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.ProfitLoss3Report;
import com.mpe.financial.model.ProfitLossReport;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.struts.form.GeneralLedgerForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mpe.common.*;

public class ProfitLossAction extends Action {
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
		//GeneralLedgerForm uomForm = (GeneralLedgerForm) form;
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
					} else if ("LABARUGI".equalsIgnoreCase(action)) {
						forward = performLabaRugi(mapping, form, request, response);
					} else if ("LABARUGIDETAIL".equalsIgnoreCase(action)) {
						forward = performLabaRugiDetail(mapping, form, request, response);
					} else if ("LABARUGIPDF".equalsIgnoreCase(action)) {
						forward = performLabaRugiPdf(mapping, form, request, response);
					} else if ("LABARUGIDETAILPDF".equalsIgnoreCase(action)) {
						forward = performLabaRugiDetailPdf(mapping, form, request, response);
						
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
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			/*
			String[] string = {new String("Revenue"), new String("Expense")};
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
			.add(Restrictions.in("Groups", string)).addOrder(Order.asc("Name")).list();
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			*/
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("departmentLst", departmentLst);
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("projectLst", projectLst);
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
			if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
			/*
			if (form.getCalendar("fromDate")!=null)session.enableFilter("fromDate").setParameter("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()));
			if (form.getCalendar("toDate")!=null)session.enableFilter("toDate").setParameter("toDate", new Date(form.getCalendar("toDate").getTime().getTime()));
			session.enableFilter("organizationId").setParameter("organizationId", new Long(users.getOrganization().getId()));
			// revenue
			List revenueLst = session.createQuery("select profitLossReport from ProfitLossReport profitLossReport where profitLossReport.Groups='Revenue'").list();
			request.setAttribute("REVENUE", revenueLst);
			// expense
			List expenseLst = session.createQuery("select profitLossReport from ProfitLossReport profitLossReport where profitLossReport.Groups='Expense'").list();
			request.setAttribute("EXPENSE", expenseLst);
			*/
			List revenueLst = new LinkedList();
			List expenseLst = new LinkedList();
			/*
			String sql = "select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, sum(b.amount * c.exchange_rate) as {pf.Amount}, (select d.number_of_digit from organization_setup d where d.organization_id=c.organization_id) as {pf.NumberOfDigit} from chart_of_account a " +
					"left join journal_detail b on a.chart_of_account_id = b.chart_of_account_id " +
					"left join journal c on b.journal_id = c.journal_id " +
					"where c.organization_id = :organizationId and a.groups = :groups and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y' " +
					"";
			*/
			String a = "";
			if (form.getLong("projectId")>0) a = a + " and c.project_id = "+form.getLong("projectId")+" ";
			if (form.getLong("departmentId")>0) a = a + " and b.department_id = "+form.getLong("departmentId")+" ";
			String sql = "select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where a.chart_of_account_id = b.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y' "+a+"), 0) as {pf.Amount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_of_account a " +
					"where a.groups = :groups " +
					"";
			revenueLst = session.createSQLQuery(sql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Revenue")
				.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			
			expenseLst = session.createSQLQuery(sql)
			.addEntity("pf", ProfitLossReport.class)
			.setLong("organizationId", users.getOrganization().getId())
			.setString("groups", "Expense")
			.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
			.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
			.list();
			
			request.setAttribute("REVENUE", revenueLst);
			request.setAttribute("EXPENSE", expenseLst);
			double revenueTotal = 0;
			double expenseTotal = 0;
			Iterator iterator = revenueLst.iterator();
			while (iterator.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator.next();
				revenueTotal = revenueTotal + profitLossReport.getAmount();
			}
			Iterator iterator2 = expenseLst.iterator();
			while (iterator2.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator2.next();
				expenseTotal = expenseTotal + profitLossReport.getAmount();
			}
			request.setAttribute("revenueTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), revenueTotal));
			request.setAttribute("expenseTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), expenseTotal));
			request.setAttribute("profitLossTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), revenueTotal - expenseTotal));
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
	/** 
	 * Method performLabaRugiDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performLabaRugi(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("departmentLst", departmentLst);
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("projectLst", projectLst);
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
			if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
			
			Calendar ytdFromDate = new GregorianCalendar();
			ytdFromDate.setTime(organizationSetup.getSetupDate());
			Calendar ytdToDate = new GregorianCalendar();
			Calendar currentFromDate = new GregorianCalendar();
			currentFromDate.set(Calendar.DAY_OF_MONTH, 1);
			Calendar currentToDate = new GregorianCalendar();
			Calendar lastFromDate = new GregorianCalendar();
			lastFromDate.set(Calendar.MONTH, lastFromDate.get(Calendar.MONTH)-1);
			lastFromDate.set(Calendar.DAY_OF_MONTH, 1);
			Calendar lastToDate = new GregorianCalendar();
			lastToDate.set(Calendar.MONTH, lastFromDate.get(Calendar.MONTH)-1);
			lastToDate.set(Calendar.DAY_OF_MONTH, lastToDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			String a = "";
			if (form.getLong("projectId")>0) a = a + " and c.project_id = "+form.getLong("projectId")+" ";
			if (form.getLong("departmentId")>0) a = a + " and b.department_id = "+form.getLong("departmentId")+" ";
			
			String groupsSql = "" +
					"select a.chart_group_id as {pf.ChartOfAccountId}, '0' as {pf.Number}, a.name as {pf.Name}, 'MPE' as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.chart_group_id=a.chart_group_id and c.organization_id = :organizationId and c.journal_date >= :fromDate1 and c.journal_date <= :toDate1 and c.is_posted = 'Y' "+a+"), 0) as {pf.CurrentAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.chart_group_id=a.chart_group_id and c.organization_id = :organizationId and c.journal_date >= :fromDate2 and c.journal_date <= :toDate2 and c.is_posted = 'Y' "+a+"), 0) as {pf.LastAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.chart_group_id=a.chart_group_id and c.organization_id = :organizationId and c.journal_date >= :fromDate3 and c.journal_date <= :toDate3 and c.is_posted = 'Y' "+a+"), 0) as {pf.YtdAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.chart_group_id=a.chart_group_id and c.organization_id = :organizationId and c.journal_date >= :fromDate4 and c.journal_date <= :toDate4 and c.is_posted = 'Y' "+a+"), 0) as {pf.FromToAmount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_group a " +
					"where a.groups = :groups " +
					"";
			
			String sql3 = "" +
					"select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate1 and c.journal_date <= :toDate1 and c.is_posted = 'Y' "+a+"), 0) as {pf.CurrentAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate2 and c.journal_date <= :toDate2 and c.is_posted = 'Y' "+a+"), 0) as {pf.LastAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate3 and c.journal_date <= :toDate3 and c.is_posted = 'Y' "+a+"), 0) as {pf.YtdAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate4 and c.journal_date <= :toDate4 and c.is_posted = 'Y' "+a+"), 0) as {pf.FromToAmount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_of_account a " +
					"where isnull(a.parent_id) and a.chart_group_id = :chartGroupId " +
					"order by a.number ASC";
			
			
			List revenueLst = new LinkedList();
			List expenseLst = new LinkedList();
			
			double currentRevenueTotal = 0;
			double lastRevenueTotal = 0;
			double ytdRevenueTotal = 0;
			double fromToRevenueTotal = 0;
			
			revenueLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLoss3Report.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Revenue")
				.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
				.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
				.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
				.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
				.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
				.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
				.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			List revenueLst2 = new LinkedList();
			//request.setAttribute("ASSET", assetLst);
			Iterator iterator3 = revenueLst.iterator();
			while (iterator3.hasNext()) {
				ProfitLoss3Report profitLossReport = (ProfitLoss3Report)iterator3.next();
				currentRevenueTotal = currentRevenueTotal + profitLossReport.getCurrentAmount();
				lastRevenueTotal = lastRevenueTotal + profitLossReport.getLastAmount();
				ytdRevenueTotal = ytdRevenueTotal + profitLossReport.getYtdAmount();
				fromToRevenueTotal = fromToRevenueTotal + profitLossReport.getFromToAmount();
				// search parent of group
				List revenueParentList = session.createSQLQuery(sql3)
					.addEntity("pf", ProfitLoss3Report.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("chartGroupId", profitLossReport.getChartOfAccountId())
					.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
					.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
					.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
					.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
					.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
					.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
					.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				profitLossReport.setList(revenueParentList);
				revenueLst2.add(profitLossReport);
				
			}
			request.setAttribute("REVENUE", revenueLst2);
			
			double currentExpenseTotal = 0;
			double lastExpenseTotal = 0;
			double ytdExpenseTotal = 0;
			double fromToExpenseTotal = 0;
			
			expenseLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLoss3Report.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Expense")
				.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
				.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
				.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
				.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
				.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
				.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
				.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			//request.setAttribute("LIABILITY", liabilityLst);
			List expenseLst2 = new LinkedList();
			Iterator iterator4 = expenseLst.iterator();
			while (iterator4.hasNext()) {
				ProfitLoss3Report profitLossReport = (ProfitLoss3Report)iterator4.next();
				currentExpenseTotal = currentExpenseTotal + profitLossReport.getCurrentAmount();
				lastExpenseTotal = lastExpenseTotal + profitLossReport.getLastAmount();
				ytdExpenseTotal = ytdExpenseTotal + profitLossReport.getYtdAmount();
				fromToExpenseTotal = fromToExpenseTotal + profitLossReport.getFromToAmount();
				// search parent of group
				List expenseParentList = session.createSQLQuery(sql3)
					.addEntity("pf", ProfitLoss3Report.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("chartGroupId", profitLossReport.getChartOfAccountId())
					.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
					.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
					.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
					.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
					.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
					.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
					.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				profitLossReport.setList(expenseParentList);
				expenseLst2.add(profitLossReport);
				
			}
			request.setAttribute("EXPENSE", expenseLst2);
			
			
			request.setAttribute("currentRevenueTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), currentRevenueTotal));
			request.setAttribute("currentExpenseTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), currentExpenseTotal));
			request.setAttribute("currentProfitLossTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), currentRevenueTotal - currentExpenseTotal));
			
			request.setAttribute("lastRevenueTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), lastRevenueTotal));
			request.setAttribute("lastExpenseTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), lastExpenseTotal));
			request.setAttribute("lastProfitLossTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), lastRevenueTotal - lastExpenseTotal));
			
			request.setAttribute("ytdRevenueTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), ytdRevenueTotal));
			request.setAttribute("ytdExpenseTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), ytdExpenseTotal));
			request.setAttribute("ytdProfitLossTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), ytdRevenueTotal - ytdExpenseTotal));
			
			request.setAttribute("fromToRevenueTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), fromToRevenueTotal));
			request.setAttribute("fromToExpenseTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), fromToExpenseTotal));
			request.setAttribute("fromToProfitLossTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), fromToRevenueTotal - fromToExpenseTotal));
		
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
	/** 
	 * Method performLabaRugiDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performLabaRugiPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
/*			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("departmentLst", departmentLst);
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("projectLst", projectLst);*/
			
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
			if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
			
			String a = "";
			if (form.getLong("projectId")>0) a = a + " and c.project_id = "+form.getLong("projectId")+" ";
			if (form.getLong("departmentId")>0) a = a + " and b.department_id = "+form.getLong("departmentId")+" ";
			
			Calendar ytdFromDate = new GregorianCalendar();
			ytdFromDate.setTime(organizationSetup.getSetupDate());
			Calendar ytdToDate = new GregorianCalendar();
			Calendar currentFromDate = new GregorianCalendar();
			currentFromDate.set(Calendar.DAY_OF_MONTH, 1);
			Calendar currentToDate = new GregorianCalendar();
			Calendar lastFromDate = new GregorianCalendar();
			lastFromDate.set(Calendar.MONTH, lastFromDate.get(Calendar.MONTH)-1);
			lastFromDate.set(Calendar.DAY_OF_MONTH, 1);
			Calendar lastToDate = new GregorianCalendar();
			lastToDate.set(Calendar.MONTH, lastFromDate.get(Calendar.MONTH)-1);
			lastToDate.set(Calendar.DAY_OF_MONTH, lastToDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			//String a = "";
			//if (form.getLong("projectId")>0) a = a + " and c.project_id = "+form.getLong("projectId")+" ";
			//if (form.getLong("departmentId")>0) a = a + " and b.department_id = "+form.getLong("departmentId")+" ";
			
			String groupsSql = "" +
					"select a.chart_group_id as {pf.ChartOfAccountId}, '0' as {pf.Number}, a.name as {pf.Name}, 'MPE' as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.chart_group_id=a.chart_group_id and c.organization_id = :organizationId and c.journal_date >= :fromDate1 and c.journal_date <= :toDate1 and c.is_posted = 'Y' "+a+"), 0) as {pf.CurrentAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.chart_group_id=a.chart_group_id and c.organization_id = :organizationId and c.journal_date >= :fromDate2 and c.journal_date <= :toDate2 and c.is_posted = 'Y' "+a+"), 0) as {pf.LastAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.chart_group_id=a.chart_group_id and c.organization_id = :organizationId and c.journal_date >= :fromDate3 and c.journal_date <= :toDate3 and c.is_posted = 'Y' "+a+"), 0) as {pf.YtdAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.chart_group_id=a.chart_group_id and c.organization_id = :organizationId and c.journal_date >= :fromDate4 and c.journal_date <= :toDate4 and c.is_posted = 'Y' "+a+"), 0) as {pf.FromToAmount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_group a " +
					"where a.groups = :groups " +
					"";
			
			String sql3 = "" +
					"select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate1 and c.journal_date <= :toDate1 and c.is_posted = 'Y' "+a+"), 0) as {pf.CurrentAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate2 and c.journal_date <= :toDate2 and c.is_posted = 'Y' "+a+"), 0) as {pf.LastAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate3 and c.journal_date <= :toDate3 and c.is_posted = 'Y' "+a+"), 0) as {pf.YtdAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate4 and c.journal_date <= :toDate4 and c.is_posted = 'Y' "+a+"), 0) as {pf.FromToAmount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_of_account a " +
					"where isnull(a.parent_id) and a.chart_group_id = :chartGroupId " +
					"order by a.number ASC";
			
			
			List revenueLst = new LinkedList();
			List expenseLst = new LinkedList();
			
			double currentRevenueTotal = 0;
			double lastRevenueTotal = 0;
			double ytdRevenueTotal = 0;
			double fromToRevenueTotal = 0;
			
			revenueLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLoss3Report.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Revenue")
				.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
				.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
				.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
				.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
				.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
				.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
				.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			List revenueLst2 = new LinkedList();
			//request.setAttribute("ASSET", assetLst);
			Iterator iterator3 = revenueLst.iterator();
			while (iterator3.hasNext()) {
				ProfitLoss3Report profitLossReport = (ProfitLoss3Report)iterator3.next();
				currentRevenueTotal = currentRevenueTotal + profitLossReport.getCurrentAmount();
				lastRevenueTotal = lastRevenueTotal + profitLossReport.getLastAmount();
				ytdRevenueTotal = ytdRevenueTotal + profitLossReport.getYtdAmount();
				fromToRevenueTotal = fromToRevenueTotal + profitLossReport.getFromToAmount();
				// search parent of group
				List revenueParentList = session.createSQLQuery(sql3)
					.addEntity("pf", ProfitLoss3Report.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("chartGroupId", profitLossReport.getChartOfAccountId())
					.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
					.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
					.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
					.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
					.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
					.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
					.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				profitLossReport.setList(revenueParentList);
				revenueLst2.add(profitLossReport);
				
			}
			//request.setAttribute("REVENUE", revenueLst2);
			
			double currentExpenseTotal = 0;
			double lastExpenseTotal = 0;
			double ytdExpenseTotal = 0;
			double fromToExpenseTotal = 0;
			
			expenseLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLoss3Report.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Expense")
				.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
				.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
				.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
				.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
				.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
				.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
				.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			//request.setAttribute("LIABILITY", liabilityLst);
			List expenseLst2 = new LinkedList();
			Iterator iterator4 = expenseLst.iterator();
			while (iterator4.hasNext()) {
				ProfitLoss3Report profitLossReport = (ProfitLoss3Report)iterator4.next();
				currentExpenseTotal = currentExpenseTotal + profitLossReport.getCurrentAmount();
				lastExpenseTotal = lastExpenseTotal + profitLossReport.getLastAmount();
				ytdExpenseTotal = ytdExpenseTotal + profitLossReport.getYtdAmount();
				fromToExpenseTotal = fromToExpenseTotal + profitLossReport.getFromToAmount();
				// search parent of group
				List expenseParentList = session.createSQLQuery(sql3)
					.addEntity("pf", ProfitLoss3Report.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("chartGroupId", profitLossReport.getChartOfAccountId())
					.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
					.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
					.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
					.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
					.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
					.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
					.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				profitLossReport.setList(expenseParentList);
				expenseLst2.add(profitLossReport);
				
			}
			
			
			//request.setAttribute("revenueTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), revenueTotal));
			//request.setAttribute("expenseTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), expenseTotal));
			//request.setAttribute("profitLossTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), revenueTotal - expenseTotal));
			
			Rectangle pageSize = new Rectangle(612, 936);
			com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,35,20,20,20);
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
			float[] a2 = {50,50};
			table1.setWidths(a2);
			table1.setBorder(Rectangle.NO_BORDER);
			table1.setCellsFitPage(true);
			table1.setBorderWidth(1);
			table1.setPadding(1);
			table1.setSpacing(0);
			
			Cell cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Laba (Rugi)", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Periode : "+form.getString("fromDate")+" - "+form.getString("toDate"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
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
			
			Table table2 = new Table(5);
			table2.setWidth(100);
			table2.setCellsFitPage(true);
			table2.setBorder(Rectangle.NO_BORDER);
			table2.setBorderWidth(1);
			table2.setPadding(1);
			table2.setSpacing(0);
			float[] a3 = {40,15,15,15,15};
			table2.setWidths(a3);
			table2.setAutoFillEmptyCells(true);
			
			cell = new Cell(new Phrase("ASSET",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setColspan(4);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			table2.endHeaders();
			
			//data				
			// loop
			Iterator iterator7 = revenueLst2.iterator();
			while (iterator7.hasNext()) {
			    ProfitLoss3Report lossReport = (ProfitLoss3Report)iterator7.next();
			    cell = new Cell(new Phrase(lossReport.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(lossReport.getFormatedCurrentAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(lossReport.getFormatedLastAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(lossReport.getFormatedYtdAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(lossReport.getFormatedFromToAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				Iterator iterator8 = lossReport.getList().iterator();
				while (iterator8.hasNext()) {
				    ProfitLoss3Report lossReport2 = (ProfitLoss3Report)iterator8.next();
				    cell = new Cell(new Phrase(lossReport2.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.RIGHT);
					table2.addCell(cell);
					cell = new Cell(new Phrase(lossReport2.getFormatedCurrentAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.LEFT);
					table2.addCell(cell);
					cell = new Cell(new Phrase(lossReport2.getFormatedLastAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.LEFT);
					table2.addCell(cell);
					cell = new Cell(new Phrase(lossReport2.getFormatedYtdAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.LEFT);
					table2.addCell(cell);
					cell = new Cell(new Phrase(lossReport2.getFormatedFromToAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.LEFT);
					table2.addCell(cell);
				}
					
			}
			cell = new Cell(new Phrase("\t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), currentRevenueTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), lastRevenueTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), ytdRevenueTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), fromToRevenueTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" \t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" \t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" \t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" \t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" \t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			
			Iterator iterator = expenseLst2.iterator();
			while (iterator.hasNext()) {
			    ProfitLoss3Report lossReport = (ProfitLoss3Report)iterator.next();
			    cell = new Cell(new Phrase(lossReport.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(lossReport.getFormatedCurrentAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(lossReport.getFormatedLastAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(lossReport.getFormatedYtdAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(lossReport.getFormatedFromToAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				Iterator iterator8 = lossReport.getList().iterator();
				while (iterator8.hasNext()) {
				    ProfitLoss3Report lossReport2 = (ProfitLoss3Report)iterator8.next();
				    cell = new Cell(new Phrase(lossReport2.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.RIGHT);
					table2.addCell(cell);
					cell = new Cell(new Phrase(lossReport2.getFormatedCurrentAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.LEFT);
					table2.addCell(cell);
					cell = new Cell(new Phrase(lossReport2.getFormatedLastAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.LEFT);
					table2.addCell(cell);
					cell = new Cell(new Phrase(lossReport2.getFormatedYtdAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.LEFT);
					table2.addCell(cell);
					cell = new Cell(new Phrase(lossReport2.getFormatedFromToAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.LEFT);
					table2.addCell(cell);
				}
					
			}
			cell = new Cell(new Phrase("\t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), currentExpenseTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), lastExpenseTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), ytdExpenseTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), fromToExpenseTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Laba Rugi ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), currentRevenueTotal-currentExpenseTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), lastRevenueTotal-lastExpenseTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), ytdRevenueTotal-ytdExpenseTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), fromToRevenueTotal-fromToExpenseTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			
/*			cell = new Cell(new Phrase("\t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);*/
			
			
			document.add(table2);
			
			
			document.close();
			//send pdf to browser
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream out = response.getOutputStream();
			baos.writeTo(out);
			out.flush();
		
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return null;
	}
	
	/** 
	 * Method performLabaRugiDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performLabaRugiDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {

		    List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
				request.setAttribute("departmentLst", departmentLst);
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
				request.setAttribute("projectLst", projectLst);
				
				OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
				Session session = ChartOfAccountDAO.getInstance().getSession();
				if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
				if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
				
				List parentLst = new LinkedList();
				
				
				Calendar ytdFromDate = new GregorianCalendar();
				ytdFromDate.setTime(organizationSetup.getSetupDate());
				Calendar ytdToDate = new GregorianCalendar();
				Calendar currentFromDate = new GregorianCalendar();
				currentFromDate.set(Calendar.DAY_OF_MONTH, 1);
				Calendar currentToDate = new GregorianCalendar();
				Calendar lastFromDate = new GregorianCalendar();
				lastFromDate.set(Calendar.MONTH, lastFromDate.get(Calendar.MONTH)-1);
				lastFromDate.set(Calendar.DAY_OF_MONTH, 1);
				Calendar lastToDate = new GregorianCalendar();
				lastToDate.set(Calendar.MONTH, lastFromDate.get(Calendar.MONTH)-1);
				lastToDate.set(Calendar.DAY_OF_MONTH, lastToDate.getActualMaximum(Calendar.DAY_OF_MONTH));
				
				
				String groupsSql = "" +
						"select a.chart_group_id as {pf.ChartOfAccountId}, '0' as {pf.Number}, a.name as {pf.Name}, 'MPE' as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
						"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate1 and b.ledger_date <= :toDate1 and b.is_closed = 'N'), 0) as {pf.CurrentAmount}, " +
						"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate2 and b.ledger_date <= :toDate2 and b.is_closed = 'N'), 0) as {pf.LastAmount}, " +
						"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate3 and b.ledger_date <= :toDate3 and b.is_closed = 'N'), 0) as {pf.YtdAmount}, " +
						"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate4 and b.ledger_date <= :toDate4 and b.is_closed = 'N'), 0) as {pf.FromToAmount}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
						"from chart_group a " +
						"where a.chart_group_id = :ChartGroupId " +
						"";
				
				String sqlChild = "" +
						"select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate1 and c.journal_date <= :toDate1 and c.is_posted = 'Y'), 0) as {pf.CurrentAmount}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate2 and c.journal_date <= :toDate2 and c.is_posted = 'Y'), 0) as {pf.LastAmount}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate3 and c.journal_date <= :toDate3 and c.is_posted = 'Y'), 0) as {pf.YtdAmount}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate4 and c.journal_date <= :toDate4 and c.is_posted = 'Y'), 0) as {pf.FromToAmount}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
						"from chart_of_account a " +
						"where a.parent_id = :ParentId " +
						"order by a.number asc";
				
				String sqlParent = "" +
						"select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate1 and c.journal_date <= :toDate1 and c.is_posted = 'Y'), 0) as {pf.CurrentAmount}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate2 and c.journal_date <= :toDate2 and c.is_posted = 'Y'), 0) as {pf.LastAmount}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate3 and c.journal_date <= :toDate3 and c.is_posted = 'Y'), 0) as {pf.YtdAmount}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate4 and c.journal_date <= :toDate4 and c.is_posted = 'Y'), 0) as {pf.FromToAmount}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
						"from chart_of_account a " +
						"where isnull(a.parent_id) and a.chart_group_id = :ChartGroupId " +
						"";
				
				ProfitLoss3Report chartGroup = (ProfitLoss3Report)session.createSQLQuery(groupsSql)
					.addEntity("pf", ProfitLoss3Report.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("ChartGroupId", form.getLong("chartGroupId"))
					.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
					.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
					.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
					.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
					.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
					.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
					.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
					.setMaxResults(1).uniqueResult();
				
				request.setAttribute("chartGroup", chartGroup);
				
				parentLst = session.createSQLQuery(sqlParent)
					.addEntity("pf", ProfitLoss3Report.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("ChartGroupId", form.getLong("chartGroupId"))
					.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
					.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
					.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
					.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
					.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
					.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
					.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				
				List parentLst2 = new LinkedList();
				//request.setAttribute("ASSET", assetLst);
				Iterator iterator3 = parentLst.iterator();
				while (iterator3.hasNext()) {
					ProfitLoss3Report profitLossReport = (ProfitLoss3Report)iterator3.next();
					// search parent of group
					List childList = session.createSQLQuery(sqlChild)
						.addEntity("pf", ProfitLoss3Report.class)
						.setLong("organizationId", users.getOrganization().getId())
						.setLong("ParentId", profitLossReport.getChartOfAccountId())
						.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
						.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
						.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
						.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
						.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
						.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
						.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
						.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
						.list();
					profitLossReport.setList(childList);
					parentLst2.add(profitLossReport);
					
				}
				request.setAttribute("PARENT", parentLst2);
		    
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
	/** 
	 * Method performLabaRugiDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performLabaRugiDetailPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {

/*		    List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
				request.setAttribute("departmentLst", departmentLst);
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
				request.setAttribute("projectLst", projectLst);*/
				
				OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
				Session session = ChartOfAccountDAO.getInstance().getSession();
				if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
				if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
				
				List parentLst = new LinkedList();		
				
				
				Calendar ytdFromDate = new GregorianCalendar();
				ytdFromDate.setTime(organizationSetup.getSetupDate());
				Calendar ytdToDate = new GregorianCalendar();
				Calendar currentFromDate = new GregorianCalendar();
				currentFromDate.set(Calendar.DAY_OF_MONTH, 1);
				Calendar currentToDate = new GregorianCalendar();
				Calendar lastFromDate = new GregorianCalendar();
				lastFromDate.set(Calendar.MONTH, lastFromDate.get(Calendar.MONTH)-1);
				lastFromDate.set(Calendar.DAY_OF_MONTH, 1);
				Calendar lastToDate = new GregorianCalendar();
				lastToDate.set(Calendar.MONTH, lastFromDate.get(Calendar.MONTH)-1);
				lastToDate.set(Calendar.DAY_OF_MONTH, lastToDate.getActualMaximum(Calendar.DAY_OF_MONTH));
				
				
				String groupsSql = "" +
						"select a.chart_group_id as {pf.ChartOfAccountId}, '0' as {pf.Number}, a.name as {pf.Name}, 'MPE' as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
						"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate1 and b.ledger_date <= :toDate1 and b.is_closed = 'N'), 0) as {pf.CurrentAmount}, " +
						"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate2 and b.ledger_date <= :toDate2 and b.is_closed = 'N'), 0) as {pf.LastAmount}, " +
						"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate3 and b.ledger_date <= :toDate3 and b.is_closed = 'N'), 0) as {pf.YtdAmount}, " +
						"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate4 and b.ledger_date <= :toDate4 and b.is_closed = 'N'), 0) as {pf.FromToAmount}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
						"from chart_group a " +
						"where a.chart_group_id = :ChartGroupId " +
						"";
				
				String sqlChild = "" +
						"select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate1 and c.journal_date <= :toDate1 and c.is_posted = 'Y'), 0) as {pf.CurrentAmount}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate2 and c.journal_date <= :toDate2 and c.is_posted = 'Y'), 0) as {pf.LastAmount}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate3 and c.journal_date <= :toDate3 and c.is_posted = 'Y'), 0) as {pf.YtdAmount}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate4 and c.journal_date <= :toDate4 and c.is_posted = 'Y'), 0) as {pf.FromToAmount}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
						"from chart_of_account a " +
						"where a.parent_id = :ParentId " +
						"order by a.number asc";
				
				String sqlParent = "" +
						"select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate1 and c.journal_date <= :toDate1 and c.is_posted = 'Y'), 0) as {pf.CurrentAmount}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate2 and c.journal_date <= :toDate2 and c.is_posted = 'Y'), 0) as {pf.LastAmount}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate3 and c.journal_date <= :toDate3 and c.is_posted = 'Y'), 0) as {pf.YtdAmount}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate4 and c.journal_date <= :toDate4 and c.is_posted = 'Y'), 0) as {pf.FromToAmount}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
						"from chart_of_account a " +
						"where isnull(a.parent_id) and a.chart_group_id = :ChartGroupId " +
						"";
				
				ProfitLoss3Report chartGroup = (ProfitLoss3Report)session.createSQLQuery(groupsSql)
					.addEntity("pf", ProfitLoss3Report.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("ChartGroupId", form.getLong("chartGroupId"))
					.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
					.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
					.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
					.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
					.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
					.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
					.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
					.setMaxResults(1).uniqueResult();
				
				request.setAttribute("chartGroup", chartGroup);
				
				parentLst = session.createSQLQuery(sqlParent)
					.addEntity("pf", ProfitLoss3Report.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("ChartGroupId", form.getLong("chartGroupId"))
					.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
					.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
					.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
					.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
					.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
					.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
					.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				
				List parentLst2 = new LinkedList();
				//request.setAttribute("ASSET", assetLst);
				Iterator iterator3 = parentLst.iterator();
				while (iterator3.hasNext()) {
					ProfitLoss3Report profitLossReport = (ProfitLoss3Report)iterator3.next();
					// search parent of group
					List childList = session.createSQLQuery(sqlChild)
						.addEntity("pf", ProfitLoss3Report.class)
						.setLong("organizationId", users.getOrganization().getId())
						.setLong("ParentId", profitLossReport.getChartOfAccountId())
						.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
						.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
						.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
						.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
						.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
						.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
						.setDate("fromDate4", new Date(form.getCalendar("fromDate").getTime().getTime()))
						.setDate("toDate4", new Date(form.getCalendar("toDate").getTime().getTime()))
						.list();
					profitLossReport.setList(childList);
					parentLst2.add(profitLossReport);
					
				}
				//request.setAttribute("PARENT", parentLst2);
				
				
				
				Rectangle pageSize = new Rectangle(612, 936);
				com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,35,20,20,20);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter.getInstance(document, baos);
				  
				// footer page
				HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
				footer.setBorder(Rectangle.NO_BORDER);
				document.setFooter(footer);
				document.open();
				
				// table upper
				Table table1 = new Table(5);
				table1.setWidth(100);
				table1.setCellsFitPage(true);
				float[] a2 = {40,15,15,15,15};
				table1.setWidths(a2);
				table1.setBorder(Rectangle.NO_BORDER);
				table1.setCellsFitPage(true);
				table1.setBorderWidth(1);
				table1.setPadding(1);
				table1.setSpacing(0);
				
				Cell cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(5);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("Periode : "+form.getString("fromDate")+" - "+form.getString("toDate"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(5);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(5);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				document.add(table1);
				
				Table table2 = new Table(5);
				table2.setWidth(100);
				table2.setCellsFitPage(true);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				float[] a3 = {40,15,15,15,15};
				table2.setWidths(a3);
				table2.setAutoFillEmptyCells(true);
				
				cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Current",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Last",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("YTD",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("From-To",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase(chartGroup.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(chartGroup.getFormatedCurrentAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(chartGroup.getFormatedLastAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(chartGroup.getFormatedYtdAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(chartGroup.getFormatedFromToAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				table2.endHeaders();
				
				//data				
				// loop
				Iterator iterator4 = parentLst2.iterator();
				while (iterator4.hasNext()) {
				    ProfitLoss3Report lossReport = (ProfitLoss3Report)iterator4.next();
				    cell = new Cell(new Phrase(lossReport.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorderWidth(1);
						cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(lossReport.getFormatedCurrentAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorderWidth(1);
						cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(lossReport.getFormatedLastAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorderWidth(1);
						cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(lossReport.getFormatedYtdAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorderWidth(1);
						cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(lossReport.getFormatedFromToAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorderWidth(1);
						cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
						table2.addCell(cell);
						
						Iterator iterator5 = lossReport.getList().iterator();
						while (iterator5.hasNext()) {
						    ProfitLoss3Report lossReport2 = (ProfitLoss3Report)iterator5.next();
						    cell = new Cell(new Phrase(lossReport2.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setBorderWidth(1);
								cell.setBorder(Rectangle.RIGHT);
								table2.addCell(cell);
								cell = new Cell(new Phrase(lossReport2.getFormatedCurrentAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorderWidth(1);
								cell.setBorder(Rectangle.LEFT);
								table2.addCell(cell);
								cell = new Cell(new Phrase(lossReport2.getFormatedLastAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorderWidth(1);
								cell.setBorder(Rectangle.LEFT);
								table2.addCell(cell);
								cell = new Cell(new Phrase(lossReport2.getFormatedYtdAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorderWidth(1);
								cell.setBorder(Rectangle.LEFT);
								table2.addCell(cell);
								cell = new Cell(new Phrase(lossReport2.getFormatedFromToAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorderWidth(1);
								cell.setBorder(Rectangle.LEFT);
								table2.addCell(cell);
						}
						
				}
				cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP);
				table2.addCell(cell);
				cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP);
				table2.addCell(cell);
				cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP);
				table2.addCell(cell);
				cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP);
				table2.addCell(cell);
				cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP);
				table2.addCell(cell);
				
				document.add(table2);
				
				
				document.close();
				//send pdf to browser
				response.setContentType("application/pdf");
				response.setContentLength(baos.size());
				ServletOutputStream out = response.getOutputStream();
				baos.writeTo(out);
				out.flush();
		    
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return null;
	}
	
	/** 
	 * Method performLabaRugiDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performLabaRugi2(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("departmentLst", departmentLst);
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("projectLst", projectLst);
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
			if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
			
			Calendar ytdFromDate = new GregorianCalendar();
			ytdFromDate.setTime(organizationSetup.getSetupDate());
			Calendar ytdToDate = new GregorianCalendar();
			Calendar currentFromDate = new GregorianCalendar();
			currentFromDate.set(Calendar.DAY_OF_MONTH, 1);
			Calendar currentToDate = new GregorianCalendar();
			Calendar lastFromDate = new GregorianCalendar();
			lastFromDate.set(Calendar.MONTH, lastFromDate.get(Calendar.MONTH)-1);
			lastFromDate.set(Calendar.DAY_OF_MONTH, 1);
			Calendar lastToDate = new GregorianCalendar();
			lastToDate.set(Calendar.MONTH, lastFromDate.get(Calendar.MONTH)-1);
			lastToDate.set(Calendar.DAY_OF_MONTH, lastToDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			String a = "";
			if (form.getLong("projectId")>0) a = a + " and c.project_id = "+form.getLong("projectId")+" ";
			if (form.getLong("departmentId")>0) a = a + " and b.department_id = "+form.getLong("departmentId")+" ";
			
			String groupsSql = "" +
					"select a.chart_group_id as {pf.ChartOfAccountId}, '0' as {pf.Number}, a.name as {pf.Name}, 'MPE' as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.chart_group_id=a.chart_group_id and c.organization_id = :organizationId and c.journal_date >= :fromDate1 and c.journal_date <= :toDate1 and c.is_posted = 'Y' "+a+"), 0) as {pf.CurrentAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.chart_group_id=a.chart_group_id and c.organization_id = :organizationId and c.journal_date >= :fromDate2 and c.journal_date <= :toDate2 and c.is_posted = 'Y' "+a+"), 0) as {pf.LastAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.chart_group_id=a.chart_group_id and c.organization_id = :organizationId and c.journal_date >= :fromDate3 and c.journal_date <= :toDate3 and c.is_posted = 'Y' "+a+"), 0) as {pf.YtdAmount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_group a " +
					"where a.groups = :groups " +
					"";
			
			String sql3 = "" +
					"select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate1 and c.journal_date <= :toDate1 and c.is_posted = 'Y' "+a+"), 0) as {pf.CurrentAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate2 and c.journal_date <= :toDate2 and c.is_posted = 'Y' "+a+"), 0) as {pf.LastAmount}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate3 and c.journal_date <= :toDate3 and c.is_posted = 'Y' "+a+"), 0) as {pf.YtdAmount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_of_account a " +
					"where isnull(a.parent_id) and a.chart_group_id = :chartGroupId " +
					"order by a.number ASC";
			
			
			List revenueLst = new LinkedList();
			List expenseLst = new LinkedList();
			
			double currentRevenueTotal = 0;
			double lastRevenueTotal = 0;
			double ytdRevenueTotal = 0;
			
			revenueLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLoss3Report.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Revenue")
				.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
				.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
				.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
				.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
				.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
				.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
				.list();
			List revenueLst2 = new LinkedList();
			//request.setAttribute("ASSET", assetLst);
			Iterator iterator3 = revenueLst.iterator();
			while (iterator3.hasNext()) {
				ProfitLoss3Report profitLossReport = (ProfitLoss3Report)iterator3.next();
				currentRevenueTotal = currentRevenueTotal + profitLossReport.getCurrentAmount();
				lastRevenueTotal = lastRevenueTotal + profitLossReport.getLastAmount();
				ytdRevenueTotal = ytdRevenueTotal + profitLossReport.getYtdAmount();
				// search parent of group
				List revenueParentList = session.createSQLQuery(sql3)
					.addEntity("pf", ProfitLoss3Report.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("chartGroupId", profitLossReport.getChartOfAccountId())
					.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
					.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
					.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
					.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
					.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
					.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
					.list();
				profitLossReport.setList(revenueParentList);
				revenueLst2.add(profitLossReport);
				
			}
			request.setAttribute("REVENUE", revenueLst2);
			
			double currentExpenseTotal = 0;
			double lastExpenseTotal = 0;
			double ytdExpenseTotal = 0;
			
			expenseLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLoss3Report.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Expense")
				.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
				.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
				.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
				.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
				.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
				.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
				.list();
			//request.setAttribute("LIABILITY", liabilityLst);
			List expenseLst2 = new LinkedList();
			Iterator iterator4 = expenseLst.iterator();
			while (iterator4.hasNext()) {
				ProfitLoss3Report profitLossReport = (ProfitLoss3Report)iterator4.next();
				currentExpenseTotal = currentExpenseTotal + profitLossReport.getCurrentAmount();
				lastExpenseTotal = lastExpenseTotal + profitLossReport.getLastAmount();
				ytdExpenseTotal = ytdExpenseTotal + profitLossReport.getYtdAmount();
				// search parent of group
				List expenseParentList = session.createSQLQuery(sql3)
					.addEntity("pf", ProfitLoss3Report.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("chartGroupId", profitLossReport.getChartOfAccountId())
					.setDate("fromDate1", new Date(currentFromDate.getTime().getTime()))
					.setDate("toDate1", new Date(currentToDate.getTime().getTime()))
					.setDate("fromDate2", new Date(lastFromDate.getTime().getTime()))
					.setDate("toDate2", new Date(lastToDate.getTime().getTime()))
					.setDate("fromDate3", new Date(ytdFromDate.getTime().getTime()))
					.setDate("toDate3", new Date(ytdToDate.getTime().getTime()))
					.list();
				profitLossReport.setList(expenseParentList);
				expenseLst2.add(profitLossReport);
				
			}
			request.setAttribute("EXPENSE", expenseLst2);
			
			
			request.setAttribute("currentRevenueTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), currentRevenueTotal));
			request.setAttribute("currentExpenseTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), currentExpenseTotal));
			request.setAttribute("currentProfitLossTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), currentRevenueTotal - currentExpenseTotal));
			
			request.setAttribute("lastRevenueTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), lastRevenueTotal));
			request.setAttribute("lastExpenseTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), lastExpenseTotal));
			request.setAttribute("lastProfitLossTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), lastRevenueTotal - lastExpenseTotal));
			
			request.setAttribute("ytdRevenueTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), ytdRevenueTotal));
			request.setAttribute("ytdExpenseTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), ytdExpenseTotal));
			request.setAttribute("ytdProfitLossTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), ytdRevenueTotal - ytdExpenseTotal));
		
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
	/** 
	 * Method performLabaRugiDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performLabaRugiPdf2(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
/*			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("departmentLst", departmentLst);
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("projectLst", projectLst);*/
			
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
			if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
			
			String a = "";
			if (form.getLong("projectId")>0) a = a + " and c.project_id = "+form.getLong("projectId")+" ";
			if (form.getLong("departmentId")>0) a = a + " and b.department_id = "+form.getLong("departmentId")+" ";
			
			String groupsSql = "select a.chart_group_id as {pf.ChartOfAccountId}, '0' as {pf.Number}, a.name as {pf.Name}, 'MPE' as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
				"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.chart_group_id=a.chart_group_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y' "+a+"), 0) as {pf.Amount}, " +
				"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
				"from chart_group a " +
				"where a.groups = :groups " +
				"";
			
			String sql3 = "select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
				"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y' "+a+"), 0) as {pf.Amount}, " +
				"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
				"from chart_of_account a " +
				"where isnull(a.parent_id) and a.chart_group_id = :chartGroupId " +
				"order by a.number ASC ";
			
			
			List revenueLst = new LinkedList();
			List expenseLst = new LinkedList();
			
			double revenueTotal = 0;
			revenueLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Revenue")
				.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			List revenueLst2 = new LinkedList();
			//request.setAttribute("ASSET", assetLst);
			Iterator iterator3 = revenueLst.iterator();
			while (iterator3.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator3.next();
				revenueTotal = revenueTotal + profitLossReport.getAmount();
				// search parent of group
				List revenueParentList = session.createSQLQuery(sql3)
					.addEntity("pf", ProfitLossReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("chartGroupId", profitLossReport.getChartOfAccountId())
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				profitLossReport.setList(revenueParentList);
				revenueLst2.add(profitLossReport);
				
			}
			//request.setAttribute("REVENUE", revenueLst2);
			
			double expenseTotal = 0;
			expenseLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Expense")
				.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			//request.setAttribute("LIABILITY", liabilityLst);
			List expenseLst2 = new LinkedList();
			Iterator iterator4 = expenseLst.iterator();
			while (iterator4.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator4.next();
				expenseTotal = expenseTotal + profitLossReport.getAmount();
				// search parent of group
				List expenseParentList = session.createSQLQuery(sql3)
					.addEntity("pf", ProfitLossReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("chartGroupId", profitLossReport.getChartOfAccountId())
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				profitLossReport.setList(expenseParentList);
				expenseLst2.add(profitLossReport);
				
			}
			//request.setAttribute("EXPENSE", expenseLst2);
			
			
			//request.setAttribute("revenueTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), revenueTotal));
			//request.setAttribute("expenseTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), expenseTotal));
			//request.setAttribute("profitLossTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), revenueTotal - expenseTotal));
			
			Rectangle pageSize = new Rectangle(612, 936);
			com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,35,20,20,20);
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
			float[] a2 = {50,50};
			table1.setWidths(a2);
			table1.setBorder(Rectangle.NO_BORDER);
			table1.setCellsFitPage(true);
			table1.setBorderWidth(1);
			table1.setPadding(1);
			table1.setSpacing(0);
			
			Cell cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Laba (Rugi)", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Periode : "+form.getString("fromDate")+" - "+form.getString("toDate"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
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
			
			Table table2 = new Table(2);
			table2.setWidth(100);
			table2.setCellsFitPage(true);
			table2.setBorder(Rectangle.NO_BORDER);
			table2.setBorderWidth(1);
			table2.setPadding(1);
			table2.setSpacing(0);
			float[] a3 = {60,40};
			table2.setWidths(a3);
			table2.setAutoFillEmptyCells(true);
			
			cell = new Cell(new Phrase("ASSET",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			table2.endHeaders();
			
			//data				
			// loop
			Iterator iterator7 = revenueLst2.iterator();
			while (iterator7.hasNext()) {
			    ProfitLossReport lossReport = (ProfitLossReport)iterator7.next();
			    cell = new Cell(new Phrase(lossReport.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
					table2.addCell(cell);
					cell = new Cell(new Phrase(lossReport.getFormatedAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
					table2.addCell(cell);
					
					Iterator iterator8 = lossReport.getList().iterator();
					while (iterator8.hasNext()) {
					    ProfitLossReport lossReport2 = (ProfitLossReport)iterator8.next();
					    cell = new Cell(new Phrase(lossReport2.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setBorderWidth(1);
							cell.setBorder(Rectangle.RIGHT);
							table2.addCell(cell);
							cell = new Cell(new Phrase(lossReport2.getFormatedAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setBorderWidth(1);
							cell.setBorder(Rectangle.LEFT);
							table2.addCell(cell);
					}
					
			}
			cell = new Cell(new Phrase("\t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), revenueTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" \t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" \t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			
			Iterator iterator = expenseLst2.iterator();
			while (iterator.hasNext()) {
			    ProfitLossReport lossReport = (ProfitLossReport)iterator.next();
			    cell = new Cell(new Phrase(lossReport.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
					table2.addCell(cell);
					cell = new Cell(new Phrase(lossReport.getFormatedAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
					table2.addCell(cell);
					
					Iterator iterator8 = lossReport.getList().iterator();
					while (iterator8.hasNext()) {
					    ProfitLossReport lossReport2 = (ProfitLossReport)iterator8.next();
					    cell = new Cell(new Phrase(lossReport2.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setBorderWidth(1);
							cell.setBorder(Rectangle.RIGHT);
							table2.addCell(cell);
							cell = new Cell(new Phrase(lossReport2.getFormatedAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setBorderWidth(1);
							cell.setBorder(Rectangle.LEFT);
							table2.addCell(cell);
					}
					
			}
			cell = new Cell(new Phrase("\t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), expenseTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Laba Rugi ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), revenueTotal-expenseTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			
/*			cell = new Cell(new Phrase("\t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(" \t", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);*/
			
			
			document.add(table2);
			
			
			document.close();
			//send pdf to browser
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream out = response.getOutputStream();
			baos.writeTo(out);
			out.flush();
		
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return null;
	}
	
	/** 
	 * Method performLabaRugiDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performLabaRugiDetail2(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {

		    List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
				request.setAttribute("departmentLst", departmentLst);
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
				request.setAttribute("projectLst", projectLst);
				
				OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
				Session session = ChartOfAccountDAO.getInstance().getSession();
				if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
				if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
				
				List parentLst = new LinkedList();
				
				
				Calendar ytdFromDate = new GregorianCalendar();
				ytdFromDate.setTime(organizationSetup.getSetupDate());
				Calendar ytdToDate = new GregorianCalendar();
				Calendar currentFromDate = new GregorianCalendar();
				currentFromDate.set(Calendar.DAY_OF_MONTH, 1);
				Calendar currentToDate = new GregorianCalendar();
				Calendar lastFromDate = new GregorianCalendar();
				lastFromDate.set(Calendar.MONTH, lastFromDate.get(Calendar.MONTH)-1);
				lastFromDate.set(Calendar.DAY_OF_MONTH, 1);
				Calendar lastToDate = new GregorianCalendar();
				lastToDate.set(Calendar.MONTH, lastFromDate.get(Calendar.MONTH)-1);
				lastToDate.set(Calendar.DAY_OF_MONTH, lastToDate.getActualMaximum(Calendar.DAY_OF_MONTH));
				
				
				String groupsSql = "" +
						"select a.chart_group_id as {pf.ChartOfAccountId}, '0' as {pf.Number}, a.name as {pf.Name}, 'MPE' as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
						"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate and b.ledger_date <= :toDate and b.is_closed = 'N'), 0) as {pf.Amount}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
						"from chart_group a " +
						"where a.chart_group_id = :ChartGroupId " +
						"";
				
				String sqlChild = "" +
						"select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y'), 0) as {pf.Amount}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
						"from chart_of_account a " +
						"where a.parent_id = :ParentId " +
						"order by a.number asc";
				
				String sqlParent = "" +
						"select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
						"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y'), 0) as {pf.Amount}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
						"from chart_of_account a " +
						"where isnull(a.parent_id) and a.chart_group_id = :ChartGroupId " +
						"";
				
				ProfitLossReport chartGroup = (ProfitLossReport)session.createSQLQuery(groupsSql)
					.addEntity("pf", ProfitLossReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("ChartGroupId", form.getLong("chartGroupId"))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.setMaxResults(1).uniqueResult();
				
				request.setAttribute("chartGroup", chartGroup);
				
				parentLst = session.createSQLQuery(sqlParent)
					.addEntity("pf", ProfitLossReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("ChartGroupId", form.getLong("chartGroupId"))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				
				List parentLst2 = new LinkedList();
				//request.setAttribute("ASSET", assetLst);
				Iterator iterator3 = parentLst.iterator();
				while (iterator3.hasNext()) {
					ProfitLossReport profitLossReport = (ProfitLossReport)iterator3.next();
					// search parent of group
					List childList = session.createSQLQuery(sqlChild)
						.addEntity("pf", ProfitLossReport.class)
						.setLong("organizationId", users.getOrganization().getId())
						.setLong("ParentId", profitLossReport.getChartOfAccountId())
						.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
						.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
						.list();
					profitLossReport.setList(childList);
					parentLst2.add(profitLossReport);
					
				}
				request.setAttribute("PARENT", parentLst2);
		    
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
	/** 
	 * Method performLabaRugiDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performLabaRugiDetailPdf2(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {

/*		    List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
				request.setAttribute("departmentLst", departmentLst);
				List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
				request.setAttribute("projectLst", projectLst);*/
				
				OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
				Session session = ChartOfAccountDAO.getInstance().getSession();
				if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
				if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
				
				List parentLst = new LinkedList();		
				
				
				String groupsSql = "select a.chart_group_id as {pf.ChartOfAccountId}, '0' as {pf.Number}, a.name as {pf.Name}, 'MPE' as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
						"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate and b.ledger_date <= :toDate and b.is_closed = 'N'), 0) as {pf.Amount}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
						"from chart_group a " +
						"where a.chart_group_id = :ChartGroupId " +
						"";
				
				String sqlChild = "select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y'), 0) as {pf.Amount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_of_account a " +
					"where a.parent_id = :ParentId " +
					"order by a.number asc";
				
				String sqlParent = "select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y'), 0) as {pf.Amount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_of_account a " +
					"where isnull(a.parent_id) and a.chart_group_id = :ChartGroupId " +
					"";
				
				ProfitLossReport chartGroup = (ProfitLossReport)session.createSQLQuery(groupsSql)
					.addEntity("pf", ProfitLossReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("ChartGroupId", form.getLong("chartGroupId"))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.setMaxResults(1).uniqueResult();
				
				request.setAttribute("chartGroup", chartGroup);
				
				parentLst = session.createSQLQuery(sqlParent)
					.addEntity("pf", ProfitLossReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("ChartGroupId", form.getLong("chartGroupId"))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				
				List parentLst2 = new LinkedList();
				//request.setAttribute("ASSET", assetLst);
				Iterator iterator3 = parentLst.iterator();
				while (iterator3.hasNext()) {
					ProfitLossReport profitLossReport = (ProfitLossReport)iterator3.next();
					// search parent of group
					List childList = session.createSQLQuery(sqlChild)
						.addEntity("pf", ProfitLossReport.class)
						.setLong("organizationId", users.getOrganization().getId())
						.setLong("ParentId", profitLossReport.getChartOfAccountId())
						.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
						.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
						.list();
					profitLossReport.setList(childList);
					parentLst2.add(profitLossReport);
					
				}
				//request.setAttribute("PARENT", parentLst2);
				Rectangle pageSize = new Rectangle(612, 936);
				com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,35,20,20,20);
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
				float[] a2 = {50,50};
				table1.setWidths(a2);
				table1.setBorder(Rectangle.NO_BORDER);
				table1.setCellsFitPage(true);
				table1.setBorderWidth(1);
				table1.setPadding(1);
				table1.setSpacing(0);
				
				Cell cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("Periode : "+form.getString("fromDate")+" - "+form.getString("toDate"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
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
				
				Table table2 = new Table(2);
				table2.setWidth(100);
				table2.setCellsFitPage(true);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				float[] a3 = {60,40};
				table2.setWidths(a3);
				table2.setAutoFillEmptyCells(true);
				
				cell = new Cell(new Phrase(chartGroup.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(chartGroup.getFormatedAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				table2.endHeaders();
				
				//data				
				// loop
				Iterator iterator4 = parentLst2.iterator();
				while (iterator4.hasNext()) {
				    ProfitLossReport lossReport = (ProfitLossReport)iterator4.next();
				    cell = new Cell(new Phrase(lossReport.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorderWidth(1);
						cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(lossReport.getFormatedAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorderWidth(1);
						cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
						table2.addCell(cell);
						
						Iterator iterator5 = lossReport.getList().iterator();
						while (iterator5.hasNext()) {
						    ProfitLossReport lossReport2 = (ProfitLossReport)iterator5.next();
						    cell = new Cell(new Phrase(lossReport2.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setBorderWidth(1);
								cell.setBorder(Rectangle.RIGHT);
								table2.addCell(cell);
								cell = new Cell(new Phrase(lossReport2.getFormatedAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
								cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cell.setBorderWidth(1);
								cell.setBorder(Rectangle.LEFT);
								table2.addCell(cell);
						}
						
				}
				cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP);
				table2.addCell(cell);
				cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP);
				table2.addCell(cell);
				
				document.add(table2);
				
				
				document.close();
				//send pdf to browser
				response.setContentType("application/pdf");
				response.setContentLength(baos.size());
				ServletOutputStream out = response.getOutputStream();
				baos.writeTo(out);
				out.flush();
		    
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
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