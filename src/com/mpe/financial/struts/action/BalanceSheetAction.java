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

import com.lowagie.text.Cell;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.ProfitLossReport;
import com.mpe.financial.model.ProfitLossReportGroup;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.struts.form.GeneralLedgerForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mpe.common.*;

public class BalanceSheetAction extends Action {
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
					} else if ("NERACA".equalsIgnoreCase(action)) {
						forward = performNeraca(mapping, form, request, response);
					} else if ("NERACADETAIL".equalsIgnoreCase(action)) {
						forward = performNeracaDetail(mapping, form, request, response);
					} else if ("NERACAPDF".equalsIgnoreCase(action)) {
						forward = performNeracaPdf(mapping, form, request, response);
					} else if ("NERACADETAILPDF".equalsIgnoreCase(action)) {
						forward = performNeracaDetailPdf(mapping, form, request, response);
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
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
			
			List assetLst = new LinkedList();
			List liabilityLst = new LinkedList();
			List equityLst = new LinkedList();
			List revenueLst = new LinkedList();
			List expenseLst = new LinkedList();
			
			String groupsSql = "select a.chart_group_id as {pf.ChartOfAccountId}, '0' as {pf.Number}, a.name as {pf.Name}, 'MPE' as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate and b.ledger_date <= :toDate and b.is_closed = 'N'), 0) as {pf.Amount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_group a " +
					"where a.groups = :groups " +
					"";
			
			String sql = "select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
				"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where a.chart_of_account_id = b.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y'), 0) as {pf.Amount}, " +
				"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
				"from chart_of_account a " +
				"where a.groups = :groups " +
				"";
			
			revenueLst = session.createSQLQuery(sql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Revenue")
				.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			
			expenseLst = session.createSQLQuery(sql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Expense")
				.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			
			// revenue & expense
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
			
			double assetTotal = 0;
			assetLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Asset")
				.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			request.setAttribute("ASSET", assetLst);
			Iterator iterator3 = assetLst.iterator();
			while (iterator3.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator3.next();
				assetTotal = assetTotal + profitLossReport.getAmount();
			}
			
			double liabilityTotal = 0;
			liabilityLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Liability")
				.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			request.setAttribute("LIABILITY", liabilityLst);
			Iterator iterator4 = liabilityLst.iterator();
			while (iterator4.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator4.next();
				liabilityTotal = liabilityTotal + profitLossReport.getAmount();
			}
			
			double equityTotal = 0;
			equityLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Equity")
				.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			// add profit-loss to equity chart!
			List newEquityLst = new LinkedList();
			Iterator iterator5 = equityLst.iterator();
			while (iterator5.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator5.next();
				if (organizationSetup.getProfitLossAccount()!=null && organizationSetup.getProfitLossAccount().getChartGroup()!=null && profitLossReport.getChartOfAccountId()==organizationSetup.getProfitLossAccount().getChartGroup().getId()) {
					profitLossReport.setAmount(revenueTotal - expenseTotal);
				}
				newEquityLst.add(profitLossReport);
			}
			request.setAttribute("EQUITY", newEquityLst);
			Iterator iterator6 = newEquityLst.iterator();
			while (iterator6.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator6.next();
				equityTotal = equityTotal + profitLossReport.getAmount();
			}
			
			request.setAttribute("assetTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), assetTotal));
			request.setAttribute("liabilityTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), liabilityTotal));
			request.setAttribute("equityTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), equityTotal));
			request.setAttribute("assetMinusLiabilityTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), assetTotal - liabilityTotal));
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
	 * Method performList
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performNeraca(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
			if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
			
			List assetLst = new LinkedList();
			List liabilityLst = new LinkedList();
			List equityLst = new LinkedList();
			List revenueLst = new LinkedList();
			List expenseLst = new LinkedList();
			
			
			
			String groupsSql = "" +
					"select a.chart_group_id as {pf.ChartOfAccountId}, '0' as {pf.Number}, a.name as {pf.Name}, 'MPE' as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate and b.ledger_date <= :toDate and b.is_closed = 'N'), 0) as {pf.Amount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_group a " +
					"where a.groups = :groups " +
					"";
			
			String sql = "" +
					"select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y'), 0) as {pf.Amount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_of_account a " +
					"where a.groups = :groups " +
					"order by a.number ASC ";
			
			String sql3 = "" +
					"select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y'), 0) as {pf.Amount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_of_account a " +
					"where isnull(a.parent_id) and a.chart_group_id = :chartGroupId " +
					"order by a.number ASC ";
			
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
			
			// revenue & expense
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
			
			double assetTotal = 0;
			assetLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Asset")
				.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			List assetLst2 = new LinkedList();
			//request.setAttribute("ASSET", assetLst);
			Iterator iterator3 = assetLst.iterator();
			while (iterator3.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator3.next();
				assetTotal = assetTotal + profitLossReport.getAmount();
				// search parent of group
				List assetParentList = session.createSQLQuery(sql3)
					.addEntity("pf", ProfitLossReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("chartGroupId", profitLossReport.getChartOfAccountId())
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				profitLossReport.setList(assetParentList);
				assetLst2.add(profitLossReport);
				
			}
			request.setAttribute("ASSET", assetLst2);
			
			String groupsSql2 = "" +
					"select a.chart_group_id as {pf2.ChartOfAccountId}, a.name as {pf2.Name}, a.groups as {pf2.Groups}, a.is_debit as {pf2.Debit}, " +
					"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate and b.ledger_date <= :toDate and b.is_closed = 'N'), 0) as {pf2.Amount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf2.NumberOfDigit} " +
					"from chart_group a " +
					"where a.groups = 'Liability' " +
					"";
			double liabilityTotal = 0;
			liabilityLst.clear();
			liabilityLst = session.createSQLQuery(groupsSql2)
				.addEntity("pf2", ProfitLossReportGroup.class)
				.setLong("organizationId", users.getOrganization().getId())
				//.setString("groups", "Liability")
				.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			
			//request.setAttribute("LIABILITY", liabilityLst);
			
			List liabilityLst2 = new LinkedList();
			Iterator iterator14 = liabilityLst.iterator();
			while (iterator14.hasNext()) {
			  ProfitLossReportGroup profitLossReport15 = (ProfitLossReportGroup)iterator14.next();
				//log.info("N : "+profitLossReport15.getName()+"//"+profitLossReport15.getGroups());
				liabilityTotal = liabilityTotal + profitLossReport15.getAmount();
				// search parent of group
				List liabilityParentList = session.createSQLQuery(sql3)
					.addEntity("pf", ProfitLossReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("chartGroupId", profitLossReport15.getChartOfAccountId())
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				profitLossReport15.setList(liabilityParentList);
				liabilityLst2.add(profitLossReport15);
				
			}
			request.setAttribute("LIABILITY", liabilityLst2);
			
			double equityTotal = 0;
			equityLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Equity")
				.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			// add profit-loss to equity chart!
			List newEquityLst = new LinkedList();
			Iterator iterator5 = equityLst.iterator();
			while (iterator5.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator5.next();
				if (organizationSetup.getProfitLossAccount()!=null && organizationSetup.getProfitLossAccount().getChartGroup()!=null && profitLossReport.getChartOfAccountId()==organizationSetup.getProfitLossAccount().getChartGroup().getId()) {
					profitLossReport.setAmount(revenueTotal - expenseTotal);
				} else {
					// search parent of group
					List equityParentList = session.createSQLQuery(sql3)
						.addEntity("pf", ProfitLossReport.class)
						.setLong("organizationId", users.getOrganization().getId())
						.setLong("chartGroupId", profitLossReport.getChartOfAccountId())
						.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
						.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
						.list();
					profitLossReport.setList(equityParentList);  
				}
				newEquityLst.add(profitLossReport);
			}
			request.setAttribute("EQUITY", newEquityLst);
			Iterator iterator6 = newEquityLst.iterator();
			while (iterator6.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator6.next();
				equityTotal = equityTotal + profitLossReport.getAmount();
			}
			
			request.setAttribute("assetTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), assetTotal));
			request.setAttribute("liabilityTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), liabilityTotal));
			request.setAttribute("equityTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), equityTotal));
			request.setAttribute("assetMinusLiabilityTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), assetTotal - liabilityTotal));
			request.setAttribute("liabilityEquityTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), equityTotal + liabilityTotal));
			
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
	 * Method performList
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performNeracaPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
			if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
			
			List assetLst = new LinkedList();
			List liabilityLst = new LinkedList();
			List equityLst = new LinkedList();
			List revenueLst = new LinkedList();
			List expenseLst = new LinkedList();
			
			
			
			String groupsSql = "select a.chart_group_id as {pf.ChartOfAccountId}, '0' as {pf.Number}, a.name as {pf.Name}, 'MPE' as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount) from general_ledger b left join chart_of_account c on b.chart_of_account_id=c.chart_of_account_id where c.chart_group_id = a.chart_group_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate and b.ledger_date <= :toDate and b.is_closed = 'N'), 0) as {pf.Amount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_group a " +
					"where a.groups = :groups " +
					"";
			
			String sql = "select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
				"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y'), 0) as {pf.Amount}, " +
				"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
				"from chart_of_account a " +
				"where a.groups = :groups " +
				"order by a.number ASC ";
			
			String sql3 = "select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
				"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id join chart_of_account d on b.chart_of_account_id=d.chart_of_account_id where d.parent_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y'), 0) as {pf.Amount}, " +
				"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
				"from chart_of_account a " +
				"where isnull(a.parent_id) and a.chart_group_id = :chartGroupId " +
				"order by a.number ASC ";
			
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
			
			// revenue & expense
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
			
			double assetTotal = 0;
			assetLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Asset")
				.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			List assetLst2 = new LinkedList();
			//request.setAttribute("ASSET", assetLst);
			Iterator iterator3 = assetLst.iterator();
			while (iterator3.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator3.next();
				assetTotal = assetTotal + profitLossReport.getAmount();
				// search parent of group
				List assetParentList = session.createSQLQuery(sql3)
					.addEntity("pf", ProfitLossReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("chartGroupId", profitLossReport.getChartOfAccountId())
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				profitLossReport.setList(assetParentList);
				assetLst2.add(profitLossReport);
				
			}
			//request.setAttribute("ASSET", assetLst2);
			
			double liabilityTotal = 0;
			liabilityLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Liability")
				.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			//request.setAttribute("LIABILITY", liabilityLst);
			List liabilityLst2 = new LinkedList();
			Iterator iterator4 = liabilityLst.iterator();
			while (iterator4.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator4.next();
				liabilityTotal = liabilityTotal + profitLossReport.getAmount();
				// search parent of group
				List liabilityParentList = session.createSQLQuery(sql3)
					.addEntity("pf", ProfitLossReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setLong("chartGroupId", profitLossReport.getChartOfAccountId())
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				profitLossReport.setList(liabilityParentList);
				liabilityLst2.add(profitLossReport);
				
			}
			//request.setAttribute("LIABILITY", liabilityLst2);
			
			double equityTotal = 0;
			equityLst = session.createSQLQuery(groupsSql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Equity")
				.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			// add profit-loss to equity chart!
			List newEquityLst = new LinkedList();
			Iterator iterator5 = equityLst.iterator();
			while (iterator5.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator5.next();
				if (organizationSetup.getProfitLossAccount()!=null && organizationSetup.getProfitLossAccount().getChartGroup()!=null && profitLossReport.getChartOfAccountId()==organizationSetup.getProfitLossAccount().getChartGroup().getId()) {
					profitLossReport.setAmount(revenueTotal - expenseTotal);
				} else {
					// search parent of group
					List equityParentList = session.createSQLQuery(sql3)
						.addEntity("pf", ProfitLossReport.class)
						.setLong("organizationId", users.getOrganization().getId())
						.setLong("chartGroupId", profitLossReport.getChartOfAccountId())
						.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
						.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
						.list();
					profitLossReport.setList(equityParentList);  
				}
				newEquityLst.add(profitLossReport);
			}
			//request.setAttribute("EQUITY", newEquityLst);
			Iterator iterator6 = newEquityLst.iterator();
			while (iterator6.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator6.next();
				equityTotal = equityTotal + profitLossReport.getAmount();
			}
			
			//request.setAttribute("assetTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), assetTotal));
			//request.setAttribute("liabilityTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), liabilityTotal));
			//request.setAttribute("equityTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), equityTotal));
			//request.setAttribute("assetMinusLiabilityTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), assetTotal - liabilityTotal));
			
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
			cell = new Cell(new Phrase("Neraca", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			cell = new Cell(new Phrase("Per-tanggal : "+form.getString("toDate"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
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
			table1.setWidths(a3);
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
			Iterator iterator7 = assetLst2.iterator();
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
			cell = new Cell(new Phrase("TOTAL ASSET",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), assetTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
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
			
			
			cell = new Cell(new Phrase("LIABILITY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
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
			
			Iterator iterator8 = liabilityLst2.iterator();
			while (iterator8.hasNext()) {
			    ProfitLossReport lossReport = (ProfitLossReport)iterator8.next();
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
					
					Iterator iterator9 = lossReport.getList().iterator();
					while (iterator9.hasNext()) {
					    ProfitLossReport lossReport2 = (ProfitLossReport)iterator9.next();
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
			cell = new Cell(new Phrase("TOTAL LIABILITY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), liabilityTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			/*
			cell = new Cell(new Phrase("ASSET - LIABILITY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), assetTotal - liabilityTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			*/
			cell = new Cell(new Phrase("\t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("\t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("EQUITY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
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
			
			Iterator iterator9 = newEquityLst.iterator();
			while (iterator9.hasNext()) {
			    ProfitLossReport lossReport = (ProfitLossReport)iterator9.next();
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
					
					Iterator iterator10 = lossReport.getList().iterator();
					while (iterator10.hasNext()) {
					    ProfitLossReport lossReport2 = (ProfitLossReport)iterator10.next();
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
			cell = new Cell(new Phrase("TOTAL EQUITY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), equityTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("TOTAL LIABILITY & EQUITY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), equityTotal + liabilityTotal),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			
			cell = new Cell(new Phrase("\t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
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
	 * Method performList
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performNeracaDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
			if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
			
			List parentLst = new LinkedList();		
			
			
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
			
			String sql = "" +
					"select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y'), 0) as {pf.Amount}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
					"from chart_of_account a " +
					"where a.groups = :groups " +
					"";
			
			List revenueLst = session.createSQLQuery(sql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Revenue")
				.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			
			List expenseLst = session.createSQLQuery(sql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Expense")
				.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			
			// revenue & expense
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

				List childList2 = new LinkedList();
				Iterator iterator4 = childList.iterator();
				while (iterator4.hasNext()) {
				    ProfitLossReport profitLossReport2 = (ProfitLossReport)iterator4.next();
				    if (organizationSetup.getProfitLossAccount()!=null && profitLossReport2.getChartOfAccountId()==organizationSetup.getProfitLossAccount().getId()) {
			        profitLossReport2.setAmount(revenueTotal - expenseTotal);
						}
				    childList2.add(profitLossReport2);
				}
				profitLossReport.setList(childList2);
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
	 * Method performList
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performNeracaDetailPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    
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
			
			String sql = "select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
				"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where b.chart_of_account_id=a.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y'), 0) as {pf.Amount}, " +
				"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
				"from chart_of_account a " +
				"where a.groups = :groups " +
				"";
			
			List revenueLst = session.createSQLQuery(sql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Revenue")
				.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			
			List expenseLst = session.createSQLQuery(sql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Expense")
				.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
			
			// revenue & expense
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
	
				List childList2 = new LinkedList();
				Iterator iterator4 = childList.iterator();
				while (iterator4.hasNext()) {
				    ProfitLossReport profitLossReport2 = (ProfitLossReport)iterator4.next();
				    if (organizationSetup.getProfitLossAccount()!=null && profitLossReport2.getChartOfAccountId()==organizationSetup.getProfitLossAccount().getId()) {
			        profitLossReport2.setAmount(revenueTotal - expenseTotal);
						}
				    childList2.add(profitLossReport2);
				}
				profitLossReport.setList(childList2);
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
			table1.setWidths(a3);
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