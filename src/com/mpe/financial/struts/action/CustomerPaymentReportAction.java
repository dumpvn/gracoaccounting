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
import com.mpe.financial.model.CustomerPayment;
import com.mpe.financial.model.CustomerPaymentDetail;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.CustomerPaymentDAO;
import com.mpe.financial.struts.form.CustomerPaymentForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class CustomerPaymentReportAction extends Action {
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
		//CustomerPaymentForm purchaseOrderForm = (CustomerPaymentForm) form;
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
					} else if ("PDFLIST".equalsIgnoreCase(action)) {
						forward = performPdfList(mapping, form, request, response);
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
		CustomerPaymentForm form = (CustomerPaymentForm) actionForm;
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
			Criteria criteria = CustomerPaymentDAO.getInstance().getSession().createCriteria(CustomerPayment.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromPaymentDate")!=null)criteria.add(Restrictions.ge("PaymentDate", new Date(form.getCalendar("fromPaymentDate").getTime().getTime())));
			if (form.getCalendar("toPaymentDate")!=null)criteria.add(Restrictions.le("PaymentDate", new Date(form.getCalendar("toPaymentDate").getTime().getTime())));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = CustomerPaymentDAO.getInstance().getSession().createCriteria(CustomerPayment.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromPaymentDate")!=null)criteria.add(Restrictions.ge("PaymentDate", new Date(form.getCalendar("fromPaymentDate").getTime().getTime())));
			if (form.getCalendar("toPaymentDate")!=null)criteria.add(Restrictions.le("PaymentDate", new Date(form.getCalendar("toPaymentDate").getTime().getTime())));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("CUSTOMERPAYMENT",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("customerPayment");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				CustomerPaymentDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
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
	private ActionForward performPdfList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		CustomerPaymentForm form = (CustomerPaymentForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
				Criteria criteria = CustomerPaymentDAO.getInstance().getSession().createCriteria(CustomerPayment.class);
				if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
				if (form.getCalendar("fromPaymentDate")!=null)criteria.add(Restrictions.ge("PaymentDate", new Date(form.getCalendar("fromPaymentDate").getTime().getTime())));
				if (form.getCalendar("toPaymentDate")!=null)criteria.add(Restrictions.le("PaymentDate", new Date(form.getCalendar("toPaymentDate").getTime().getTime())));
				criteria.setProjection(Projections.rowCount());
				// partial data
				criteria = CustomerPaymentDAO.getInstance().getSession().createCriteria(CustomerPayment.class);
				if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
				if (form.getCalendar("fromPaymentDate")!=null)criteria.add(Restrictions.ge("PaymentDate", new Date(form.getCalendar("fromPaymentDate").getTime().getTime())));
				if (form.getCalendar("toPaymentDate")!=null)criteria.add(Restrictions.le("PaymentDate", new Date(form.getCalendar("toPaymentDate").getTime().getTime())));
				if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
				    criteria.addOrder(Order.asc(form.getString("orderBy")));
				} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
				    criteria.addOrder(Order.desc(form.getString("orderBy")));
				}
				List list = criteria.list();
				
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
				
				Table table2 = new Table(12);
				table2.setWidth(100);
				table2.setCellsFitPage(true);
				float[] b = {5,15,7,7,7,7,7,9,9,9,9,9};
				table2.setWidths(b);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setCellsFitPage(true);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(12);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(12);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PAYMENT", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(12);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(12);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(12);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(12);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("CUSTOMER NAME",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("DATE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("NUMBER",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("INVOICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(4);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("CASH",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("GIRO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("DATE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("DISCOUNT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TAX",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("BANK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("NUMBER",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				table2.endHeaders();
				
				// data
				int i = 0;
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
				    CustomerPayment customerPayment = (CustomerPayment)iterator.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(customerPayment.getCustomer().getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(customerPayment.getFormatedPaymentDate(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(customerPayment.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						Table t3 = new Table(1);
						Iterator iterator3 = customerPayment.getCustomerPaymentDetails().iterator();
						while (iterator3.hasNext()) {
						    CustomerPaymentDetail customerPaymentDetail   = (CustomerPaymentDetail)iterator3.next();
						    cell = new Cell(new Phrase(customerPaymentDetail.getInvoice()!=null?customerPaymentDetail.getInvoice().getFormatedInvoiceDate():"", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
								cell.setBorder(Rectangle.RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								t3.addCell(cell);
						}
						table2.insertTable(t3);
						Table t4 = new Table(1);
						Iterator iterator4 = customerPayment.getCustomerPaymentDetails().iterator();
						while (iterator4.hasNext()) {
						    CustomerPaymentDetail customerPaymentDetail   = (CustomerPaymentDetail)iterator4.next();
						    cell = new Cell(new Phrase(customerPaymentDetail.getInvoice()!=null?customerPaymentDetail.getInvoice().getFormatedInvoiceDiscountAmount():"", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
								cell.setBorder(Rectangle.RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								t4.addCell(cell);
						}
						table2.insertTable(t4);
						Table t5 = new Table(1);
						Iterator iterator5 = customerPayment.getCustomerPaymentDetails().iterator();
						while (iterator5.hasNext()) {
						    CustomerPaymentDetail customerPaymentDetail   = (CustomerPaymentDetail)iterator5.next();
						    cell = new Cell(new Phrase(customerPaymentDetail.getInvoice()!=null?customerPaymentDetail.getInvoice().getFormatedInvoiceTaxAmount():"", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
								cell.setBorder(Rectangle.RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								t5.addCell(cell);
						}
						table2.insertTable(t5);
						Table t6 = new Table(1);
						Iterator iterator6 = customerPayment.getCustomerPaymentDetails().iterator();
						while (iterator6.hasNext()) {
						    CustomerPaymentDetail customerPaymentDetail   = (CustomerPaymentDetail)iterator6.next();
						    cell = new Cell(new Phrase(customerPaymentDetail.getInvoice()!=null?customerPaymentDetail.getInvoice().getFormatedInvoiceAfterDiscountAndTaxAndPrepayment():"", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
								cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
								t6.addCell(cell);
						}
						cell = new Cell(new Phrase(customerPayment.getFormatedPaymentAmount(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						table2.insertTable(t6);
						cell = new Cell(new Phrase(customerPayment.getBankAccount()!=null?(customerPayment.getBankAccount().getBank()!=null?(customerPayment.getBankAccount().getBank().getName()):""):"", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(customerPayment.getBankAccount()!=null?(customerPayment.getBankAccount().getNumber()):"", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(customerPayment.getFormatedPaymentAmount(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
				}				
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(12);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(12);
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
				
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				CustomerPaymentDAO.getInstance().closeSessionForReal();
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