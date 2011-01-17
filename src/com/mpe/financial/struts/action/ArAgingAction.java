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
import com.mpe.financial.model.ArAgingReport;
import com.mpe.financial.model.Currency;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.struts.form.GeneralLedgerForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mpe.common.*;

public class ArAgingAction extends Action {
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
						//forward = performDetail(mapping, form, request, response);
					} else if ("REPORT".equalsIgnoreCase(action)) {
						forward = performReport(mapping, form, request, response);
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
			List customersLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
				.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("customersLst", customersLst);
			List currencyLst = CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("currencyLst", currencyLst);
			//OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			List arAgingLst = new LinkedList();
			// select invoice not complete
			String sql = "select a.customer_id as {ar.CustomerId}, a.company as {ar.Company}, a.address as {ar.Address}, a.city as {ar.City}, a.postal_code as {ar.PostalCode}, " +
					//"(sum(if(to_days(b.invoice_due)-to_days(current_date)>=0,(select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id),0))) as {ar.Aging0}, " +
					//"(sum(if(to_days(b.invoice_due)-to_days(current_date)<0 and to_days(b.invoice_due)-to_days(current_date)>=-30,(select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id),0))) as {ar.Aging030}, " +
					//"(sum(if(to_days(b.invoice_due)-to_days(current_date)<-30 and to_days(b.invoice_due)-to_days(current_date)>=-60,(select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id),0))) as {ar.Aging3060}, " +
					//"(sum(if(to_days(b.invoice_due)-to_days(current_date)<-60 and to_days(b.invoice_due)-to_days(current_date)>=-90,(select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id),0))) as {ar.Aging6090}, " +
					//"(sum(if(to_days(b.invoice_due)-to_days(current_date)<-90,(select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id),0))) as {ar.Aging90}, " +
					//"(select d.number_of_digit from organization_setup d where d.organization_id=a.organization_id) as {ar.NumberOfDigit} " +
					//"from customers a join invoice b on a.customer_id=b.customer_id " +
					//"where ((select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id)-(select sum(d.amount*e.exchange_rate) from customer_payment_invoice d join customer_payment e on d.customer_payment_id=e.customer_payment_id where d.invoice_id=b.invoice_id)-(select sum(f.amount*g.exchange_rate) from invoice_customer_prepayment f join customer_prepayment g on f.customer_prepayment_id=g.customer_prepayment_id where f.invoice_id=b.invoice_id))>0 " +
					"(sum(if(to_days(b.invoice_due)-to_days(current_date)>=0, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-IFNULL(b.discount_amount,0) from invoice_detail c where c.invoice_id=b.invoice_id)-IFNULL((select sum(a.amount) from invoice_customer_prepayment a where a.invoice_id=b.invoice_id),0)-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_id=b.invoice_id),0), 0))) as {ar.Aging0}, " +
					"(sum(if(to_days(b.invoice_due)-to_days(current_date)<0 and to_days(b.invoice_due)-to_days(current_date)>=-30, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-IFNULL(b.discount_amount,0) from invoice_detail c where c.invoice_id=b.invoice_id)-IFNULL((select sum(a.amount) from invoice_customer_prepayment a where a.invoice_id=b.invoice_id),0)-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_id=b.invoice_id),0),0))) as {ar.Aging030}, " +
					"(sum(if(to_days(b.invoice_due)-to_days(current_date)<-30 and to_days(b.invoice_due)-to_days(current_date)>=-60, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-IFNULL(b.discount_amount,0) from invoice_detail c where c.invoice_id=b.invoice_id)-IFNULL((select sum(a.amount) from invoice_customer_prepayment a where a.invoice_id=b.invoice_id),0)-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_id=b.invoice_id),0),0))) as {ar.Aging3060}, " +
					"(sum(if(to_days(b.invoice_due)-to_days(current_date)<-60 and to_days(b.invoice_due)-to_days(current_date)>=-90, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-IFNULL(b.discount_amount,0) from invoice_detail c where c.invoice_id=b.invoice_id)-IFNULL((select sum(a.amount) from invoice_customer_prepayment a where a.invoice_id=b.invoice_id),0)-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_id=b.invoice_id),0),0))) as {ar.Aging6090}, " +
					"(sum(if(to_days(b.invoice_due)-to_days(current_date)<-90, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-IFNULL(b.discount_amount,0) from invoice_detail c where c.invoice_id=b.invoice_id)-IFNULL((select sum(a.amount) from invoice_customer_prepayment a where a.invoice_id=b.invoice_id),0)-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_id=b.invoice_id),0),0))) as {ar.Aging90}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=a.organization_id) as {ar.NumberOfDigit} " +
					"from customers a join invoice b on a.customer_id=b.customer_id " +
					//"where ((select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id)-(select sum((d.cash_amount+d.giro_amount)*e.exchange_rate) from customer_payment_invoice d join payment_to_vendor e on d.payment_to_customer_id=e.payment_to_customer_id where d.invoice_id=b.invoice_id)-(select sum(f.amount*g.exchange_rate) from invoice_customer_prepayment f join customer_prepayment g on f.prepayment_to_customer_id=g.prepayment_to_customer_id where f.invoice_id=b.invoice_id))>0 " +
					"where " +
					"b.customer_payment_status <> 'CLOSE' " +
					"and a.organization_id = :organizationId " +
					"and b.currency_id = :currencyId "+
					"group by a.customer_id " +
					"order by a.company asc ";
			
					//"where " + 
					//"b.customer_payment_status <> 'CLOSE' " +
					//"and a.organization_id = :organizationId " +
					//"group by a.customer_id " +
					//"order by a.company asc ";
			arAgingLst = session.createSQLQuery(sql)
				.addEntity("ar", ArAgingReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setLong("currencyId", form.getLong("currencyId"))
				.list();
			
			request.setAttribute("ARAGING", arAgingLst);

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
		  
			Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
			//request.setAttribute("vendors", vendors);
			Currency currency = CurrencyDAO.getInstance().get(form.getLong("currencyId"));
			//request.setAttribute("currency", currency);
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			List arAgingLst = new LinkedList();
			// select invoice not complete
			String sql = "select a.customer_id as {ar.CustomerId}, a.company as {ar.Company}, a.address as {ar.Address}, a.city as {ar.City}, a.postal_code as {ar.PostalCode}, " +
					//"(sum(if(to_days(b.invoice_due)-to_days(current_date)>=0,(select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id),0))) as {ar.Aging0}, " +
					//"(sum(if(to_days(b.invoice_due)-to_days(current_date)<0 and to_days(b.invoice_due)-to_days(current_date)>=-30,(select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id),0))) as {ar.Aging030}, " +
					//"(sum(if(to_days(b.invoice_due)-to_days(current_date)<-30 and to_days(b.invoice_due)-to_days(current_date)>=-60,(select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id),0))) as {ar.Aging3060}, " +
					//"(sum(if(to_days(b.invoice_due)-to_days(current_date)<-60 and to_days(b.invoice_due)-to_days(current_date)>=-90,(select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id),0))) as {ar.Aging6090}, " +
					//"(sum(if(to_days(b.invoice_due)-to_days(current_date)<-90,(select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id),0))) as {ar.Aging90}, " +
					//"(select d.number_of_digit from organization_setup d where d.organization_id=a.organization_id) as {ar.NumberOfDigit} " +
					//"from customers a join invoice b on a.customer_id=b.customer_id " +
					//"where ((select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id)-(select sum(d.amount*e.exchange_rate) from customer_payment_invoice d join customer_payment e on d.customer_payment_id=e.customer_payment_id where d.invoice_id=b.invoice_id)-(select sum(f.amount*g.exchange_rate) from invoice_customer_prepayment f join customer_prepayment g on f.customer_prepayment_id=g.customer_prepayment_id where f.invoice_id=b.invoice_id))>0 " +
					"(sum(if(to_days(b.invoice_due)-to_days(current_date)>=0, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-IFNULL(b.discount_amount,0) from invoice_detail c where c.invoice_id=b.invoice_id)-IFNULL((select sum(a.amount) from invoice_customer_prepayment a where a.invoice_id=b.invoice_id),0)-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_id=b.invoice_id),0), 0))) as {ar.Aging0}, " +
					"(sum(if(to_days(b.invoice_due)-to_days(current_date)<0 and to_days(b.invoice_due)-to_days(current_date)>=-30, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-IFNULL(b.discount_amount,0) from invoice_detail c where c.invoice_id=b.invoice_id)-IFNULL((select sum(a.amount) from invoice_customer_prepayment a where a.invoice_id=b.invoice_id),0)-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_id=b.invoice_id),0),0))) as {ar.Aging030}, " +
					"(sum(if(to_days(b.invoice_due)-to_days(current_date)<-30 and to_days(b.invoice_due)-to_days(current_date)>=-60, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-IFNULL(b.discount_amount,0) from invoice_detail c where c.invoice_id=b.invoice_id)-IFNULL((select sum(a.amount) from invoice_customer_prepayment a where a.invoice_id=b.invoice_id),0)-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_id=b.invoice_id),0),0))) as {ar.Aging3060}, " +
					"(sum(if(to_days(b.invoice_due)-to_days(current_date)<-60 and to_days(b.invoice_due)-to_days(current_date)>=-90, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-IFNULL(b.discount_amount,0) from invoice_detail c where c.invoice_id=b.invoice_id)-IFNULL((select sum(a.amount) from invoice_customer_prepayment a where a.invoice_id=b.invoice_id),0)-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_id=b.invoice_id),0),0))) as {ar.Aging6090}, " +
					"(sum(if(to_days(b.invoice_due)-to_days(current_date)<-90, (select ((sum(c.quantity*c.price*c.exchange_rate*(1-(c.discount_procent/100))))*(1-(b.discount_procent/100))*(1+(b.tax_amount/100)))-IFNULL(b.discount_amount,0) from invoice_detail c where c.invoice_id=b.invoice_id)-IFNULL((select sum(a.amount) from invoice_customer_prepayment a where a.invoice_id=b.invoice_id),0)-IFNULL((select sum(a.payment_amount*(1/a.invoice_exchange_rate)) from customer_payment_invoice a where a.invoice_id=b.invoice_id),0),0))) as {ar.Aging90}, " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=a.organization_id) as {ar.NumberOfDigit} " +
					"from customers a join invoice b on a.customer_id=b.customer_id " +
					//"where ((select sum(c.quantity*c.price*c.exchange_rate*b.exchange_rate) from invoice_detail c where c.invoice_id=b.invoice_id)-(select sum((d.cash_amount+d.giro_amount)*e.exchange_rate) from customer_payment_invoice d join payment_to_vendor e on d.payment_to_customer_id=e.payment_to_customer_id where d.invoice_id=b.invoice_id)-(select sum(f.amount*g.exchange_rate) from invoice_customer_prepayment f join customer_prepayment g on f.prepayment_to_customer_id=g.prepayment_to_customer_id where f.invoice_id=b.invoice_id))>0 " +
					"where " +
					"b.customer_payment_status <> 'CLOSE' " +
					"and a.organization_id = :organizationId " +
					"and b.currency_id = :currencyId "+
					"group by a.customer_id " +
					"order by a.company asc ";
			
					//"where " + 
					//"b.customer_payment_status <> 'CLOSE' " +
					//"and a.organization_id = :organizationId " +
					//"group by a.customer_id " +
					//"order by a.company asc ";
			arAgingLst = session.createSQLQuery(sql)
				.addEntity("ar", ArAgingReport.class)
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
			if (customers!=null) {
					cell = new Cell(new Phrase("Customer : "+customers.getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
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
			Iterator iterator3 = arAgingLst.iterator();
			while (iterator3.hasNext()) {
			    ArAgingReport apAgingReport = (ArAgingReport)iterator3.next();
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