//---------------------------------------------------------
//Application: Garage
//Author     : Agung Hadiwaluyo
//(Milan Fanz Club)
//Copyright © 2005 MPE
//Generated at Sat Sep 03 19:38:16 GMT+07:00 2005
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
import org.hibernate.criterion.Order;
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
import com.mpe.financial.model.ApAgingDetailReport;
import com.mpe.financial.model.ApAgingReport;
import com.mpe.financial.model.Currency;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.VendorBill;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.VendorBillDAO;
import com.mpe.financial.model.dao.VendorsDAO;
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

public class ApAgingAction extends Action {
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
				} else if ("DETAIL".equalsIgnoreCase(action)) {
					forward = performDetail(mapping, form, request, response);
				} else if ("REPORT".equalsIgnoreCase(action)) {
					forward = performReport(mapping, form, request, response);
				} else if ("POSISIHUTANG".equalsIgnoreCase(action)) {
					forward = performPosisiHutang(mapping, form, request, response);
				} else if ("POSISIHUTANGPDF".equalsIgnoreCase(action)) {
					forward = performPosisiHutangPdf(mapping, form, request, response);
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
	  
		List vendorsLst = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
			.addOrder(Order.asc("Company"))
			.list();
		request.setAttribute("vendorsLst", vendorsLst);
		List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
			.addOrder(Order.asc("Name")).list();
		request.setAttribute("currencyLst", currencyLst);
		//OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		Session session = ChartOfAccountDAO.getInstance().getSession();
		List apAgingLst = new LinkedList();
		// select invoice not complete
		String sql = "" +
				"select a.vendor_id as {ar.VendorId}, a.company as {ar.Company}, a.address as {ar.Address}, a.city as {ar.City}, a.postal_code as {ar.PostalCode}, " +
				//"(sum(if(to_days(b.bill_due)-to_days(current_date)>=0, (select ((sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0)+IFNULL(b.other_cost_1,0)+IFNULL(b.other_cost_2,0)+IFNULL(b.other_cost_3,0), 0))) as {ar.Aging0}, " +
				//"(sum(if(to_days(b.bill_due)-to_days(current_date)<0 and to_days(b.bill_due)-to_days(current_date)>=-30, (select ((sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0)+IFNULL(b.other_cost_1,0)+IFNULL(b.other_cost_2,0)+IFNULL(b.other_cost_3,0),0))) as {ar.Aging030}, " +
				//"(sum(if(to_days(b.bill_due)-to_days(current_date)<-30 and to_days(b.bill_due)-to_days(current_date)>=-60, (select ((sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0)+IFNULL(b.other_cost_1,0)+IFNULL(b.other_cost_2,0)+IFNULL(b.other_cost_3,0),0))) as {ar.Aging3060}, " +
				//"(sum(if(to_days(b.bill_due)-to_days(current_date)<-60 and to_days(b.bill_due)-to_days(current_date)>=-90, (select ((sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0)+IFNULL(b.other_cost_1,0)+IFNULL(b.other_cost_2,0)+IFNULL(b.other_cost_3,0),0))) as {ar.Aging6090}, " +
				//"(sum(if(to_days(b.bill_due)-to_days(current_date)<-90, (select ((sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id)+IFNULL(b.other_cost_1,0)+IFNULL(b.other_cost_2,0)+IFNULL(b.other_cost_3,0),0),0))) as {ar.Aging90}, " +
				
				"(sum(if(to_days(b.bill_due)-to_days(current_date)>=0, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount*(1/a.vendor_bill_exchange_rate)) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0), 0))) as {ar.Aging0}, " +
				"(sum(if(to_days(b.bill_due)-to_days(current_date)<0 and to_days(b.bill_due)-to_days(current_date)>=-30, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount*(1/a.vendor_bill_exchange_rate)) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0),0))) as {ar.Aging030}, " +
				"(sum(if(to_days(b.bill_due)-to_days(current_date)<-30 and to_days(b.bill_due)-to_days(current_date)>=-60, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount*(1/a.vendor_bill_exchange_rate)) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0),0))) as {ar.Aging3060}, " +
				"(sum(if(to_days(b.bill_due)-to_days(current_date)<-60 and to_days(b.bill_due)-to_days(current_date)>=-90, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount*(1/a.vendor_bill_exchange_rate)) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0),0))) as {ar.Aging6090}, " +
				"(sum(if(to_days(b.bill_due)-to_days(current_date)<-90, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount*(1/a.vendor_bill_exchange_rate)) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0),0))) as {ar.Aging90}, " +
				"(select d.number_of_digit from organization_setup d where d.organization_id=a.organization_id) as {ar.NumberOfDigit} " +
				"from vendors a join vendor_bill b on a.vendor_id=b.vendor_id " +
				//"where ((select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-(select sum((d.cash_amount+d.giro_amount)*e.exchange_rate) from payment_to_vendor_vendor_bill d join payment_to_vendor e on d.payment_to_vendor_id=e.payment_to_vendor_id where d.vendor_bill_id=b.vendor_bill_id)-(select sum(f.amount*g.exchange_rate) from vendor_bill_prepayment_to_vendor f join prepayment_to_vendor g on f.prepayment_to_vendor_id=g.prepayment_to_vendor_id where f.vendor_bill_id=b.vendor_bill_id))>0 " +
				"where " +
				"b.payment_to_vendor_status <> 'CLOSE' " +
				"and a.organization_id = :organizationId " +
				"and b.currency_id = :currencyId "+
				"group by a.vendor_id " +
				"order by a.company asc ";
		apAgingLst = session.createSQLQuery(sql)
			.addEntity("ar", ApAgingReport.class)
			.setLong("organizationId", users.getOrganization().getId())
			.setLong("currencyId", form.getLong("currencyId"))
			.list();
		
		request.setAttribute("APAGING", apAgingLst);

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
private ActionForward performDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
	GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
	HttpSession httpSession = request.getSession();
	Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
	try {
	  
		Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
		request.setAttribute("vendor", vendors);
		Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
		request.setAttribute("currency", currency);
		
		//OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		Session session = ChartOfAccountDAO.getInstance().getSession();
		List apAgingLst = new LinkedList();
		// select invoice not complete
		String x = "";
		if (form.getString("aging").equalsIgnoreCase("0")) x = "and to_days(b.bill_due)-to_days(current_date)>=0 ";
		else if (form.getString("aging").equalsIgnoreCase("030")) x = "and to_days(b.bill_due)-to_days(current_date)<0 and to_days(b.bill_due)-to_days(current_date)>=-30 ";
		else if (form.getString("aging").equalsIgnoreCase("3060")) x = "and to_days(b.bill_due)-to_days(current_date)<-30 and to_days(b.bill_due)-to_days(current_date)>=-60 ";
		else if (form.getString("aging").equalsIgnoreCase("6090")) x = "and to_days(b.bill_due)-to_days(current_date)<-60 and to_days(b.bill_due)-to_days(current_date)>=-90 ";
		else if (form.getString("aging").equalsIgnoreCase("90")) x = "and to_days(b.bill_due)-to_days(current_date)<-90 ";
		
		String sql = "" +
				"select b.vendor_bill_id as {ar.Id}, b.number as {ar.Number}, b.voucher as {ar.Voucher}, b.description as {ar.Description}, b.bill_date as {ar.BillDate}, b.bill_due as {ar.BillDue}, " +
				"IFNULL((select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount*(1/a.vendor_bill_exchange_rate)) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0), 0) as {ar.Amount}, "+
				"(select d.number_of_digit from organization_setup d where d.organization_id=b.organization_id) as {ar.NumberOfDigit} " +
				"from vendor_bill b  " +
				"where " +
				"b.payment_to_vendor_status <> 'CLOSE' " +
				"and b.organization_id = :organizationId " +
				"and b.currency_id = :currencyId " +
				"and b.vendor_id = :vendorId "+
				x +
				"order by b.bill_date asc ";
		apAgingLst = session.createSQLQuery(sql)
			.addEntity("ar", ApAgingDetailReport.class)
			.setLong("organizationId", users.getOrganization().getId())
			.setLong("currencyId", form.getLong("currencyId"))
			.setLong("vendorId", form.getLong("vendorId"))
			.list();
		
		request.setAttribute("APAGING", apAgingLst);

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
private ActionForward performReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
	GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
	HttpSession httpSession = request.getSession();
	Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
	try {
	  
		Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
		request.setAttribute("vendors", vendors);
		Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
		request.setAttribute("currency", currency);
		OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		Session session = ChartOfAccountDAO.getInstance().getSession();
		List apAgingLst = new LinkedList();
		// select invoice not complete
		String sql = "" +
				"select a.vendor_id as {ar.VendorId}, a.company as {ar.Company}, a.address as {ar.Address}, a.city as {ar.City}, a.postal_code as {ar.PostalCode}, " +
				//"(sum(if(to_days(b.bill_due)-to_days(current_date)>=0, (select ((sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0)+IFNULL(b.other_cost_1,0)+IFNULL(b.other_cost_2,0)+IFNULL(b.other_cost_3,0), 0))) as {ar.Aging0}, " +
				//"(sum(if(to_days(b.bill_due)-to_days(current_date)<0 and to_days(b.bill_due)-to_days(current_date)>=-30, (select ((sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0)+IFNULL(b.other_cost_1,0)+IFNULL(b.other_cost_2,0)+IFNULL(b.other_cost_3,0),0))) as {ar.Aging030}, " +
				//"(sum(if(to_days(b.bill_due)-to_days(current_date)<-30 and to_days(b.bill_due)-to_days(current_date)>=-60, (select ((sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0)+IFNULL(b.other_cost_1,0)+IFNULL(b.other_cost_2,0)+IFNULL(b.other_cost_3,0),0))) as {ar.Aging3060}, " +
				//"(sum(if(to_days(b.bill_due)-to_days(current_date)<-60 and to_days(b.bill_due)-to_days(current_date)>=-90, (select ((sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0)+IFNULL(b.other_cost_1,0)+IFNULL(b.other_cost_2,0)+IFNULL(b.other_cost_3,0),0))) as {ar.Aging6090}, " +
				//"(sum(if(to_days(b.bill_due)-to_days(current_date)<-90, (select ((sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id)+IFNULL(b.other_cost_1,0)+IFNULL(b.other_cost_2,0)+IFNULL(b.other_cost_3,0),0),0))) as {ar.Aging90}, " +
				
				"(sum(if(to_days(b.bill_due)-to_days(current_date)>=0, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount*(1/a.vendor_bill_exchange_rate)) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0), 0))) as {ar.Aging0}, " +
				"(sum(if(to_days(b.bill_due)-to_days(current_date)<0 and to_days(b.bill_due)-to_days(current_date)>=-30, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount*(1/a.vendor_bill_exchange_rate)) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0),0))) as {ar.Aging030}, " +
				"(sum(if(to_days(b.bill_due)-to_days(current_date)<-30 and to_days(b.bill_due)-to_days(current_date)>=-60, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount*(1/a.vendor_bill_exchange_rate)) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0),0))) as {ar.Aging3060}, " +
				"(sum(if(to_days(b.bill_due)-to_days(current_date)<-60 and to_days(b.bill_due)-to_days(current_date)>=-90, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount*(1/a.vendor_bill_exchange_rate)) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0),0))) as {ar.Aging6090}, " +
				"(sum(if(to_days(b.bill_due)-to_days(current_date)<-90, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-b.discount_amount from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-IFNULL((select sum(a.amount) from vendor_bill_prepayment_to_vendor a where a.vendor_bill_id=b.vendor_bill_id),0)-IFNULL((select sum(a.payment_amount*(1/a.vendor_bill_exchange_rate)) from payment_to_vendor_vendor_bill a where a.vendor_bill_id=b.vendor_bill_id),0),0))) as {ar.Aging90}, " +
				"(select d.number_of_digit from organization_setup d where d.organization_id=a.organization_id) as {ar.NumberOfDigit} " +
				"from vendors a join vendor_bill b on a.vendor_id=b.vendor_id " +
				//"where ((select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from vendor_bill_detail c where c.vendor_bill_id=b.vendor_bill_id)-(select sum((d.cash_amount+d.giro_amount)*e.exchange_rate) from payment_to_vendor_vendor_bill d join payment_to_vendor e on d.payment_to_vendor_id=e.payment_to_vendor_id where d.vendor_bill_id=b.vendor_bill_id)-(select sum(f.amount*g.exchange_rate) from vendor_bill_prepayment_to_vendor f join prepayment_to_vendor g on f.prepayment_to_vendor_id=g.prepayment_to_vendor_id where f.vendor_bill_id=b.vendor_bill_id))>0 " +
				"where " +
				"b.payment_to_vendor_status <> 'CLOSE' " +
				"and a.organization_id = :organizationId " +
				"and b.currency_id = :currencyId "+
				"group by a.vendor_id " +
				"order by a.company asc ";
		apAgingLst = session.createSQLQuery(sql)
			.addEntity("ar", ApAgingReport.class)
			.setLong("organizationId", users.getOrganization().getId())
			.setLong("currencyId", form.getLong("currencyId"))
			.list();
		
		// write to pdf document
		Rectangle pageSize = new Rectangle(612, 936);
		com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,30,25,25,25);
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
		if (vendors!=null) {
				cell = new Cell(new Phrase("Vendor : "+vendors.getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
			}
		if (currency!=null) {
			cell = new Cell(new Phrase("Currency : "+currency.getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
		}
		cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table1.addCell(cell);
		document.add(table1);
		
		Table table2 = new Table(8);
		table2.setWidth(100);
		table2.setCellsFitPage(true);
		table2.setBorder(Rectangle.NO_BORDER);
		table2.setBorderWidth(1);
		table2.setPadding(1);
		table2.setSpacing(0);
		float[] w2 = {4,18,13,13,13,13,13,13};
		table2.setWidths(w2);
		table2.setAutoFillEmptyCells(true);
		
		cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("Company",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("Current",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("0-30",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("30-60",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("60-90",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		cell = new Cell(new Phrase(">90",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("Total",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		table2.endHeaders();
		
		//data
		// loop
		int h = 0;
		double a0=0, a030=0, a3060=0, a6090=0, a90=0, aTot=0;
		Iterator iterator3 = apAgingLst.iterator();
		while (iterator3.hasNext()) {
		    ApAgingReport apAgingReport = (ApAgingReport)iterator3.next();
		    a0 = a0 + apAgingReport.getAging0();
		    a030 = a030 + apAgingReport.getAging030();
		    a3060 = a3060 + apAgingReport.getAging3060();
		    a6090 = a6090 + apAgingReport.getAging6090();
		    a90 = a90 + apAgingReport.getAging90();
		    aTot = aTot + apAgingReport.getAgingTotal();
		    cell = new Cell(new Phrase(++h+".",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(apAgingReport.getCompany(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(apAgingReport.getFormatedAging0(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(apAgingReport.getFormatedAging030(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(apAgingReport.getFormatedAging3060(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(apAgingReport.getFormatedAging6090(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(apAgingReport.getFormatedAging90(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(apAgingReport.getFormatedAgingTotal(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
		}
		cell = new Cell(new Phrase("Total",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorderWidth(1);
		cell.setColspan(2);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		table2.addCell(cell);
		cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), a0),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		table2.addCell(cell);
		cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), a030),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		table2.addCell(cell);
		cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), a3060),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		table2.addCell(cell);
		cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), a6090),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		table2.addCell(cell);
		cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), a90),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		table2.addCell(cell);
		cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), aTot),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
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
private ActionForward performPosisiHutang(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
	GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
	HttpSession httpSession = request.getSession();
	Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
	try {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MONTH, form.getInt("month"));
		calendar.set(Calendar.YEAR, form.getInt("year"));
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
	  List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Symbol"));
	  request.setAttribute("currencyLst", currencyLst);
		Criteria criteria = VendorBillDAO.getInstance().getSession().createCriteria(VendorBill.class)
			.add(Restrictions.ge("BillDue", new Date(organizationSetup.getSetupDate().getTime())))
			.add(Restrictions.le("BillDue", new Date(calendar.getTime().getTime())))
			.add(Restrictions.ne("Status", new String("CANCEL")))
			.add(Restrictions.ne("PaymentToVendorStatus", new String("CLOSE")))
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
			.add(Restrictions.eq("Currency.Id", new Long(form.getLong("currencyId"))));
		if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
		    criteria.addOrder(Order.asc(form.getString("orderBy")));
		} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
		    criteria.addOrder(Order.desc(form.getString("orderBy")));
		} else if (form.getString("orderBy")==null || form.getString("orderBy").length()==0) {
		    criteria.addOrder(Order.desc("Number"));
		}
		List vendorBillLst = criteria.list();
		request.setAttribute("vendorBillLst", vendorBillLst);

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
private ActionForward performPosisiHutangPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
	GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
	HttpSession httpSession = request.getSession();
	Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
	try {
		Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MONTH, form.getInt("month"));
		calendar.set(Calendar.YEAR, form.getInt("year"));
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
	  List currencyLst = CurrencyDAO.getInstance().findAll(Order.asc("Symbol"));
	  request.setAttribute("currencyLst", currencyLst);
		Criteria criteria = VendorBillDAO.getInstance().getSession().createCriteria(VendorBill.class)
			.add(Restrictions.ge("BillDue", new Date(organizationSetup.getSetupDate().getTime())))
			.add(Restrictions.le("BillDue", new Date(calendar.getTime().getTime())))
			.add(Restrictions.ne("Status", new String("CANCEL")))
			.add(Restrictions.ne("PaymentToVendorStatus", new String("CLOSE")))
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
			.add(Restrictions.eq("Currency.Id", new Long(form.getLong("currencyId"))));
		if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			criteria.addOrder(Order.asc(form.getString("orderBy")));
		} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			criteria.addOrder(Order.desc(form.getString("orderBy")));
		} else if (form.getString("orderBy")==null || form.getString("orderBy").length()==0) {
		  criteria.addOrder(Order.desc("Number"));
		}
		List vendorBillLst = criteria.list();
		//request.setAttribute("vendorBillLst", vendorBillLst);
		
		// write to pdf document
		Rectangle pageSize = new Rectangle(612, 936);
		com.lowagie.text.Document document = new com.lowagie.text.Document(pageSize,30,25,25,25);
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
		String month[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		cell = new Cell(new Phrase("Posisi Hutang sampai dengan : "+month[form.getInt("month")]+" - "+form.getString("year"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table1.addCell(cell);
		if (currency!=null) {
			cell = new Cell(new Phrase("Currency : "+currency.getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
		}
		cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table1.addCell(cell);
		document.add(table1);
		
		Table table2 = new Table(9);
		table2.setWidth(100);
		table2.setCellsFitPage(true);
		table2.setBorder(Rectangle.NO_BORDER);
		table2.setBorderWidth(1);
		table2.setPadding(1);
		table2.setSpacing(0);
		float[] w2 = {3,7,17,8,7,15,13,19,11};
		table2.setWidths(w2);
		table2.setAutoFillEmptyCells(true);
		
		cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("Bill Due",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("Vendor",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("VB",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("Bill Date",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("PO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("Voucher",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("Description",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		cell = new Cell(new Phrase("Amount",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
		cell.setBorderWidth(1);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
		table2.addCell(cell);
		table2.endHeaders();
		
		//data
		// loop
		int h = 0;
		double total=0;
		Iterator iterator3 = vendorBillLst.iterator();
		while (iterator3.hasNext()) {
		    VendorBill vendorBill = (VendorBill)iterator3.next();
		    total = total + vendorBill.getDifferenceAmount();
		    cell = new Cell(new Phrase(++h+".",FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(vendorBill.getFormatedBillDue(),FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(vendorBill.getVendor().getCompany(),FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(vendorBill.getNumber(),FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(vendorBill.getFormatedBillDate(),FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(vendorBill.getReceiving()!=null?(vendorBill.getReceiving().getPurchaseOrder()!=null?(vendorBill.getReceiving().getPurchaseOrder().getNumber()):""):"",FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(vendorBill.getVoucher(),FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(vendorBill.getDescription(),FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(vendorBill.getFormatedDifferenceAmount(),FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
		}
		cell = new Cell(new Phrase("Total",FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorderWidth(1);
		cell.setColspan(8);
		cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
		table2.addCell(cell);
		cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), total),FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
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
		generalError(request,ex);
		return mapping.findForward("list");
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