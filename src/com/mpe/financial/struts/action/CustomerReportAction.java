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
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.RekapMutationReceivableReport;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.struts.form.CustomersForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class CustomerReportAction extends Action {
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
		//CustomersForm customerForm = (CustomersForm) form;
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
					} else if ("PDFLIST".equalsIgnoreCase(action)) {
						forward = performPdfList(mapping, form, request, response);
					} else if ("REKAPMUTATIONRECEIVABLELIST".equalsIgnoreCase(action)) {
						forward = performRekapMutationReceivableList(mapping, form, request, response);
					} else if ("REKAPMUTATIONRECEIVABLEPDFLIST".equalsIgnoreCase(action)) {
						forward = performRekapMutationReceivablePdfList(mapping, form, request, response);
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
			Criteria criteria = CustomersDAO.getInstance().getSession().createCriteria(Customers.class);
			if (form.getString("contactPerson")!=null && form.getString("contactPerson").length()>0)criteria.add(Restrictions.like("ContactPerson", form.getString("contactPerson"), MatchMode.ANYWHERE));
			if (form.getString("company")!=null && form.getString("company").length()>0)criteria.add(Restrictions.like("Company", form.getString("company"), MatchMode.ANYWHERE));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = CustomersDAO.getInstance().getSession().createCriteria(Customers.class);
			if (form.getString("contactPerson")!=null && form.getString("contactPerson").length()>0)criteria.add(Restrictions.like("ContactPerson", form.getString("contactPerson"), MatchMode.ANYWHERE));
			if (form.getString("company")!=null && form.getString("company").length()>0)criteria.add(Restrictions.like("Company", form.getString("company"), MatchMode.ANYWHERE));
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
	 * Method performDelete
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performPdfList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomersForm form = (CustomersForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    // write to pdf document
				Document document = new Document(PageSize.A4,36,36,36,36);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter.getInstance(document, baos);
				  
				// footer page
				HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
				footer.setBorder(Rectangle.NO_BORDER);
				document.setFooter(footer);
				document.open();
				
				Criteria criteria = CustomersDAO.getInstance().getSession().createCriteria(Customers.class);
				if (form.getString("company")!=null && form.getString("company").length()>0)criteria.add(Restrictions.like("Company", "%"+form.getString("company")+"%"));
				criteria.setProjection(Projections.rowCount());
				// partial data
				criteria = CustomersDAO.getInstance().getSession().createCriteria(Customers.class);
				if (form.getString("company")!=null && form.getString("company").length()>0)criteria.add(Restrictions.like("Company", "%"+form.getString("company")+"%"));
				//criteria.addOrder(Order.asc("CompanyName"));
				if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
				    criteria.addOrder(Order.asc(form.getString("orderBy")));
				} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
				    criteria.addOrder(Order.desc(form.getString("orderBy")));
				}
				List list = criteria.list();
				
				// table upper
				Table table1 = new Table(1);
				table1.setWidth(100);
				table1.setCellsFitPage(true);
				float[] a = {100};
				table1.setWidths(a);
				table1.setBorder(Rectangle.NO_BORDER);
				table1.setCellsFitPage(true);
				table1.setBorderWidth(1);
				table1.setPadding(0);
				table1.setSpacing(0);
				
				Cell cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("Tlp. "+users.getOrganization().getTelephone()+" - Fax. "+users.getOrganization().getFax(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				document.add(table1);
				
				Table table2 = new Table(6);
				table2.setWidth(100);
				table2.setCellsFitPage(true);
				float[] b = {5,10,20,30,15,20};
				table2.setWidths(b);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setCellsFitPage(true);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(6);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(6);
				table2.addCell(cell);
				cell = new Cell(new Phrase("CUSTOMER TABLE", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(6);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(6);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(6);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(6);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("CODE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("CUSTOMER NAME",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ADDRESS",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("NPWP",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PHONE/FAX\nCONTACT PERSON",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				table2.endHeaders();	
				
				int i = 0;
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
				    Customers customers = (Customers)iterator.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(customers.getCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(customers.getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(customers.getAddress()+" "+customers.getCity()+" "+customers.getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(customers.getNpwp(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(customers.getTelephone()+"/"+customers.getFax()+"\n"+customers.getContactPerson(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.NO_BORDER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				}
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(6);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Printed By : "+users.getUserName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(6);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
				
				document.add(table2);
				
				document.close();
				//send pdf to browser
				response.setContentType("application/pdf");
				response.setContentLength(baos.size());
				ServletOutputStream out = response.getOutputStream();
				baos.writeTo(out);
				out.flush();
		}catch(Exception ex) {
		}finally {
			try {
				CustomersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return null;
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
	private ActionForward performRekapMutationReceivableList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomersForm form = (CustomersForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
				if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
				if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate"); 
				//save start and count attribute on session
				// data report
				List list = new LinkedList();
				String x = "";
				String y = "";
				if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
				    y = "order by "+form.getString("orderBy")+" ASC ";
				} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
				    y = "order by "+form.getString("orderBy")+" DESC ";
				}
				if (form.getString("company")!=null && form.getString("company").length()>0) x = "and a.company like '%"+form.getString("company")+"%' ";
				String sql = "" +
						"select a.customer_id as {rmr.CustomerId}, a.code as {rmr.Code}, a.company as {rmr.Company}, " +
						"IFNULL((select b.amount * b.exchange_rate from customer_first_balance b where b.customer_id=a.customer_id), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_detail d where d.invoice_id=c.invoice_id)) from invoice c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :setupDate and c.invoice_date < :fromDate), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_polos_detail d where d.invoice_polos_id=c.invoice_polos_id)) from invoice_polos c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :setupDate and c.invoice_date < :fromDate), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_simple_detail d where d.invoice_simple_id=c.invoice_simple_id)) from invoice_simple c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :setupDate and c.invoice_date < :fromDate), 0) - IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.invoice_exchange_rate) from customer_payment_invoice g where g.customer_payment_id=h.customer_payment_id)) from customer_payment h where h.status <> 'CANCEL' and h.payment_date >= :setupDate and h.payment_date < :fromDate and h.customer_id=a.customer_id), 0) - IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity*(1-(f.discount_procent/100))) from customer_retur_detail f where f.customer_retur_id=e.customer_retur_id)) from customer_retur e where e.status <> 'CANCEL' and e.customer_id=a.customer_id and e.retur_date >= :setupDate and e.retur_date < :fromDate), 0) as {rmr.FirstBalance}, " +						
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_detail d where d.invoice_id=c.invoice_id)) from invoice c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :fromDate and c.invoice_date <= :toDate), 0) as {rmr.Debit}, " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_polos_detail d where d.invoice_polos_id=c.invoice_polos_id)) from invoice_polos c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :fromDate and c.invoice_date <= :toDate), 0) as {rmr.DebitPolos}, " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_simple_detail d where d.invoice_simple_id=c.invoice_simple_id)) from invoice_simple c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :fromDate and c.invoice_date <= :toDate), 0) as {rmr.DebitSimple}, " +
						"IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.invoice_exchange_rate) from customer_payment_invoice g where g.customer_payment_id=h.customer_payment_id)) from customer_payment h where h.status <> 'CANCEL' and h.payment_date >= :fromDate and h.payment_date <= :toDate and h.customer_id=a.customer_id), 0) as {rmr.CreditPayment}," + 
						"IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity*(1-(f.discount_procent/100))) from customer_retur_detail f where f.customer_retur_id=e.customer_retur_id)) from customer_retur e where e.status <> 'CANCEL' and e.customer_id=a.customer_id and e.retur_date >= :fromDate and e.retur_date <= :toDate), 0) as {rmr.CreditRetur}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=a.organization_id) as {rmr.NumberOfDigit} " +
						"from customers a " +
						"where " +
						"IFNULL((select b.amount * b.exchange_rate from customer_first_balance b where b.customer_id=a.customer_id), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_detail d where d.invoice_id=c.invoice_id)) from invoice c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :setupDate and c.invoice_date < :fromDate), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_polos_detail d where d.invoice_polos_id=c.invoice_polos_id)) from invoice_polos c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :setupDate and c.invoice_date < :fromDate), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_simple_detail d where d.invoice_simple_id=c.invoice_simple_id)) from invoice_simple c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :setupDate and c.invoice_date < :fromDate), 0) - IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.invoice_exchange_rate) from customer_payment_invoice g where g.customer_payment_id=h.customer_payment_id)) from customer_payment h where h.status <> 'CANCEL' and h.payment_date >= :setupDate and h.payment_date < :fromDate and h.customer_id=a.customer_id), 0) - IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity*(1-(f.discount_procent/100))) from customer_retur_detail f where f.customer_retur_id=e.customer_retur_id)) from customer_retur e where e.status <> 'CANCEL' and e.customer_id=a.customer_id and e.retur_date >= :setupDate and e.retur_date < :fromDate), 0)<>0 OR " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_detail d where d.invoice_id=c.invoice_id)) from invoice c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :fromDate and c.invoice_date <= :toDate), 0)<>0 OR " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_polos_detail d where d.invoice_polos_id=c.invoice_polos_id)) from invoice_polos c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :fromDate and c.invoice_date <= :toDate), 0)<>0 OR " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_simple_detail d where d.invoice_simple_id=c.invoice_simple_id)) from invoice_simple c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :fromDate and c.invoice_date <= :toDate), 0)<>0 OR " +
						"IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.invoice_exchange_rate) from customer_payment_invoice g where g.customer_payment_id=h.customer_payment_id)) from customer_payment h where h.status <> 'CANCEL' and h.payment_date >= :fromDate and h.payment_date <= :toDate and h.customer_id=a.customer_id), 0)<>0 OR " +
						"IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity*(1-(f.discount_procent/100))) from customer_retur_detail f where f.customer_retur_id=e.customer_retur_id)) from customer_retur e where e.status <> 'CANCEL' and e.customer_id=a.customer_id and e.retur_date >= :fromDate and e.retur_date <= :toDate), 0)<>0 " +
						"AND a.organization_id = :organizationId " + x +" "+
						"group by a.customer_id " + y;
				list = CustomersDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmr", RekapMutationReceivableReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				request.setAttribute("REKAPMUTATIONRECEIVABLE", list);
				
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
		return mapping.findForward("list");
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
	private ActionForward performRekapMutationReceivablePdfList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomersForm form = (CustomersForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
				if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
				if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate"); 
				//save start and count attribute on session
				// data report
				List list = new LinkedList();
				String x = "";
				String y = "";
				if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
				    y = "order by "+form.getString("orderBy")+" ASC ";
				} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
				    y = "order by "+form.getString("orderBy")+" DESC ";
				}
				if (form.getString("company")!=null && form.getString("company").length()>0) x = "and a.company like '%"+form.getString("company")+"%' ";
				String sql = "" +
						"select a.customer_id as {rmr.CustomerId}, a.code as {rmr.Code}, a.company as {rmr.Company}, " +
						"IFNULL((select b.amount * b.exchange_rate from customer_first_balance b where b.customer_id=a.customer_id), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_detail d where d.invoice_id=c.invoice_id)) from invoice c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :setupDate and c.invoice_date < :fromDate), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_polos_detail d where d.invoice_polos_id=c.invoice_polos_id)) from invoice_polos c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :setupDate and c.invoice_date < :fromDate), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_simple_detail d where d.invoice_simple_id=c.invoice_simple_id)) from invoice_simple c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :setupDate and c.invoice_date < :fromDate), 0) - IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.invoice_exchange_rate) from customer_payment_invoice g where g.customer_payment_id=h.customer_payment_id)) from customer_payment h where h.status <> 'CANCEL' and h.payment_date >= :setupDate and h.payment_date < :fromDate and h.customer_id=a.customer_id), 0) - IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity*(1-(f.discount_procent/100))) from customer_retur_detail f where f.customer_retur_id=e.customer_retur_id)) from customer_retur e where e.status <> 'CANCEL' and e.customer_id=a.customer_id and e.retur_date >= :setupDate and e.retur_date < :fromDate), 0) as {rmr.FirstBalance}, " +						
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_detail d where d.invoice_id=c.invoice_id)) from invoice c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :fromDate and c.invoice_date <= :toDate), 0) as {rmr.Debit}, " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_polos_detail d where d.invoice_polos_id=c.invoice_polos_id)) from invoice_polos c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :fromDate and c.invoice_date <= :toDate), 0) as {rmr.DebitPolos}, " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_simple_detail d where d.invoice_simple_id=c.invoice_simple_id)) from invoice_simple c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :fromDate and c.invoice_date <= :toDate), 0) as {rmr.DebitSimple}, " +
						"IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.invoice_exchange_rate) from customer_payment_invoice g where g.customer_payment_id=h.customer_payment_id)) from customer_payment h where h.status <> 'CANCEL' and h.payment_date >= :fromDate and h.payment_date <= :toDate and h.customer_id=a.customer_id), 0) as {rmr.CreditPayment}," + 
						"IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity*(1-(f.discount_procent/100))) from customer_retur_detail f where f.customer_retur_id=e.customer_retur_id)) from customer_retur e where e.status <> 'CANCEL' and e.customer_id=a.customer_id and e.retur_date >= :fromDate and e.retur_date <= :toDate), 0) as {rmr.CreditRetur}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=a.organization_id) as {rmr.NumberOfDigit} " +
						"from customers a " +
						"where " +
						"IFNULL((select b.amount * b.exchange_rate from customer_first_balance b where b.customer_id=a.customer_id), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_detail d where d.invoice_id=c.invoice_id)) from invoice c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :setupDate and c.invoice_date < :fromDate), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_polos_detail d where d.invoice_polos_id=c.invoice_polos_id)) from invoice_polos c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :setupDate and c.invoice_date < :fromDate), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_simple_detail d where d.invoice_simple_id=c.invoice_simple_id)) from invoice_simple c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :setupDate and c.invoice_date < :fromDate), 0) - IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.invoice_exchange_rate) from customer_payment_invoice g where g.customer_payment_id=h.customer_payment_id)) from customer_payment h where h.status <> 'CANCEL' and h.payment_date >= :setupDate and h.payment_date < :fromDate and h.customer_id=a.customer_id), 0) - IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity*(1-(f.discount_procent/100))) from customer_retur_detail f where f.customer_retur_id=e.customer_retur_id)) from customer_retur e where e.status <> 'CANCEL' and e.customer_id=a.customer_id and e.retur_date >= :setupDate and e.retur_date < :fromDate), 0)<>0 OR " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_detail d where d.invoice_id=c.invoice_id)) from invoice c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :fromDate and c.invoice_date <= :toDate), 0)<>0 OR " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_polos_detail d where d.invoice_polos_id=c.invoice_polos_id)) from invoice_polos c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :fromDate and c.invoice_date <= :toDate), 0)<>0 OR " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity*(1-(d.discount_procent/100))) from invoice_simple_detail d where d.invoice_simple_id=c.invoice_simple_id)) from invoice_simple c where c.status <> 'CANCEL' and c.customer_id=a.customer_id and c.invoice_date >= :fromDate and c.invoice_date <= :toDate), 0)<>0 OR " +
						"IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.invoice_exchange_rate) from customer_payment_invoice g where g.customer_payment_id=h.customer_payment_id)) from customer_payment h where h.status <> 'CANCEL' and h.payment_date >= :fromDate and h.payment_date <= :toDate and h.customer_id=a.customer_id), 0)<>0 OR " +
						"IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity*(1-(f.discount_procent/100))) from customer_retur_detail f where f.customer_retur_id=e.customer_retur_id)) from customer_retur e where e.status <> 'CANCEL' and e.customer_id=a.customer_id and e.retur_date >= :fromDate and e.retur_date <= :toDate), 0)<>0 " +
						"AND a.organization_id = :organizationId " + x +" "+
						"group by a.customer_id " + y;
				list = CustomersDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmr", RekapMutationReceivableReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				request.setAttribute("REKAPMUTATIONRECEIVABLE", list);
				
				//pdf
		    // write to pdf document
				Document document = new Document(PageSize.A4,36,36,36,36);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter.getInstance(document, baos);
				  
				// footer page
				HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)), true);
				footer.setBorder(Rectangle.NO_BORDER);
				document.setFooter(footer);
				document.open();
				
				// table upper
				Table table1 = new Table(1);
				table1.setWidth(100);
				table1.setCellsFitPage(true);
				float[] a = {100};
				table1.setWidths(a);
				table1.setBorder(Rectangle.NO_BORDER);
				table1.setCellsFitPage(true);
				table1.setBorderWidth(1);
				table1.setPadding(0);
				table1.setSpacing(0);
				
				Cell cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase(users.getOrganization().getAddress()+" "+users.getOrganization().getCity()+" "+users.getOrganization().getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				cell = new Cell(new Phrase("Tlp. "+users.getOrganization().getTelephone()+" - Fax. "+users.getOrganization().getFax(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
				document.add(table1);
				
				Table table2 = new Table(7);
				table2.setWidth(100);
				table2.setCellsFitPage(true);
				float[] b = {5,6,25,11,11,11,11};
				table2.setWidths(b);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setCellsFitPage(true);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(7);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(7);
				table2.addCell(cell);
				cell = new Cell(new Phrase("REKAP MUTATION RECEIVABLE", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(7);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(7);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(7);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(7);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("CODE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("CUSTOMER NAME",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("BEGINNING BALANCE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("DEBIT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("CREDIT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ENDING BALANCE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				table2.endHeaders();
				
				int i = 0;
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
				    RekapMutationReceivableReport report = (RekapMutationReceivableReport)iterator.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(report.getCode(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(report.getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(report.getFormatedFirstBalance(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(report.getFormatedDebit(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(report.getFormatedCredit(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(report.getFormatedEndBalance(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				}
				
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(7);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(7);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
				
				document.add(table2);
				document.close();
				//send pdf to browser
				response.setContentType("application/pdf");
				response.setContentLength(baos.size());
				ServletOutputStream out = response.getOutputStream();
				baos.writeTo(out);
				out.flush();
				
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