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
import org.hibernate.Session;

import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.ProfitLossReport;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.struts.form.GeneralLedgerForm;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mpe.common.*;

public class TrialBalanceSheetAction extends Action {
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
			
			String sql = "select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
					"IFNULL((select sum(b.amount) from general_ledger b where a.chart_of_account_id = b.chart_of_account_id and b.organization_id = :organizationId and b.ledger_date >= :fromDate and b.ledger_date <= :toDate and b.is_closed = 'N'), 0) as {pf.Amount}, " +
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
			assetLst = session.createSQLQuery(sql)
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
			liabilityLst = session.createSQLQuery(sql)
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
			equityLst = session.createSQLQuery(sql)
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
				if (organizationSetup.getProfitLossAccount()!=null && profitLossReport.getChartOfAccountId()==organizationSetup.getProfitLossAccount().getId()) {
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