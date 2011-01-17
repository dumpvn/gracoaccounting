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
import com.mpe.financial.model.PurchaseOrder;
import com.mpe.financial.model.PurchaseOrderDetail;
import com.mpe.financial.model.Receiving;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.PurchaseOrderDAO;

import com.mpe.financial.struts.form.PurchaseOrderForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class PurchaseOrderReportAction extends Action {
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
		//PurchaseOrderForm purchaseOrderForm = (PurchaseOrderForm) form;
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
				} else if ("OUTSTANDINGLIST".equalsIgnoreCase(action)) {
					forward = performOutstandingList(mapping, form, request, response);
				} else if ("OUTSTANDINGPDFLIST".equalsIgnoreCase(action)) {
					forward = performOutstandingPdfList(mapping, form, request, response);
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
	private ActionForward performOutstandingList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		PurchaseOrderForm form = (PurchaseOrderForm) actionForm;
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
			Criteria criteria = PurchaseOrderDAO.getInstance().getSession().createCriteria(PurchaseOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromPurchaseDate")!=null)criteria.add(Restrictions.ge("PurchaseDate", new Date(form.getCalendar("fromPurchaseDate").getTime().getTime())));
			if (form.getCalendar("toPurchaseDate")!=null)criteria.add(Restrictions.le("PurchaseDate", new Date(form.getCalendar("toPurchaseDate").getTime().getTime())));
			criteria.add(Restrictions.ne("ReceivingStatus", new String(CommonConstants.CLOSE)));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = PurchaseOrderDAO.getInstance().getSession().createCriteria(PurchaseOrder.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
			if (form.getCalendar("fromPurchaseDate")!=null)criteria.add(Restrictions.ge("PurchaseDate", new Date(form.getCalendar("fromPurchaseDate").getTime().getTime())));
			if (form.getCalendar("toPurchaseDate")!=null)criteria.add(Restrictions.le("PurchaseDate", new Date(form.getCalendar("toPurchaseDate").getTime().getTime())));
			criteria.add(Restrictions.ne("ReceivingStatus", new String(CommonConstants.CLOSE)));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("PURCHASEORDER",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
			httpSession.removeAttribute("purchaseOrder");
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				PurchaseOrderDAO.getInstance().closeSessionForReal();
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
	private ActionForward performOutstandingPdfList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		PurchaseOrderForm form = (PurchaseOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
				Criteria criteria = PurchaseOrderDAO.getInstance().getSession().createCriteria(PurchaseOrder.class);
				if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
				if (form.getCalendar("fromPurchaseDate")!=null)criteria.add(Restrictions.ge("PurchaseDate", new Date(form.getCalendar("fromPurchaseDate").getTime().getTime())));
				if (form.getCalendar("toPurchaseDate")!=null)criteria.add(Restrictions.le("PurchaseDate", new Date(form.getCalendar("toPurchaseDate").getTime().getTime())));
				criteria.setProjection(Projections.rowCount());
				// partial data
				criteria = PurchaseOrderDAO.getInstance().getSession().createCriteria(PurchaseOrder.class);
				if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.eq("Number", form.getString("number")));
				if (form.getCalendar("fromPurchaseDate")!=null)criteria.add(Restrictions.ge("PurchaseDate", new Date(form.getCalendar("fromPurchaseDate").getTime().getTime())));
				if (form.getCalendar("toPurchaseDate")!=null)criteria.add(Restrictions.le("PurchaseDate", new Date(form.getCalendar("toPurchaseDate").getTime().getTime())));
				if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
				    criteria.addOrder(Order.asc(form.getString("orderBy")));
				} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
				    criteria.addOrder(Order.desc(form.getString("orderBy")));
				}
				List list = criteria.list();
				
				// pdf
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
				
				Table table2 = new Table(11);
				table2.setWidth(100);
				table2.setCellsFitPage(true);
				float[] b = {5,10,10,8,8,8,10,15,10,8,8};
				table2.setWidths(b);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setCellsFitPage(true);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(11);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(11);
				table2.addCell(cell);
				cell = new Cell(new Phrase("OUTSTANDING ORDER", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(11);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(11);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(11);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(11);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("DATE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ARTICLE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("MERK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("COLOR",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("SIZE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("LPB",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("OUTSTANDING",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("LPB NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("DATE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
				table2.addCell(cell);
				table2.endHeaders();
				
				// data
				int i = 0;
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
				    PurchaseOrder purchaseOrder = (PurchaseOrder)iterator.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(purchaseOrder.getFormatedPurchaseDate(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						Table t3 = new Table(1);
						Iterator iterator3 = purchaseOrder.getPurchaseOrderDetails().iterator();
						while (iterator3.hasNext()) {
						    PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator3.next();
						    cell = new Cell(new Phrase(purchaseOrderDetail.getId().getItem().getCode(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
								cell.setBorder(Rectangle.RIGHT);
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								t3.addCell(cell);
						}
						table2.insertTable(t3);
						Table t4 = new Table(1);
						Iterator iterator4 = purchaseOrder.getPurchaseOrderDetails().iterator();
						while (iterator4.hasNext()) {
						    PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator4.next();
						    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
							cell.setBorder(Rectangle.RIGHT);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							t4.addCell(cell);
						}
						table2.insertTable(t4);
						Table t5 = new Table(1);
						Iterator iterator5 = purchaseOrder.getPurchaseOrderDetails().iterator();
						while (iterator5.hasNext()) {
						    PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator5.next();
						    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
							cell.setBorder(Rectangle.RIGHT);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							t5.addCell(cell);
						}
						table2.insertTable(t5);
						Table t6 = new Table(1);
						Iterator iterator6 = purchaseOrder.getPurchaseOrderDetails().iterator();
						while (iterator6.hasNext()) {
						    PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator6.next();
						    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
							cell.setBorder(Rectangle.RIGHT);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							t6.addCell(cell);
						}
						table2.insertTable(t6);
						Table t7 = new Table(1);
						Iterator iterator7 = purchaseOrder.getPurchaseOrderDetails().iterator();
						while (iterator7.hasNext()) {
						    PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator7.next();
						    cell = new Cell(new Phrase(Double.toString(purchaseOrderDetail.getQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
							cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							t7.addCell(cell);
						}
						table2.insertTable(t7);
						Table t8 = new Table(1);
						Iterator iterator8 = purchaseOrder.getReceivings().iterator();
						while (iterator8.hasNext()) {
						    Receiving receiving = (Receiving)iterator8.next();
						    cell = new Cell(new Phrase(receiving.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
							cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							t8.addCell(cell);
						}
						table2.insertTable(t8);
						Table t9 = new Table(1);
						Iterator iterator9 = purchaseOrder.getReceivings().iterator();
						while (iterator9.hasNext()) {
						    Receiving receiving = (Receiving)iterator9.next();
						    cell = new Cell(new Phrase(receiving.getFormatedReceivingDate(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
							cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							t9.addCell(cell);
						}
						table2.insertTable(t9);
						Table t10 = new Table(1);
						Iterator iterator10 = purchaseOrder.getPurchaseOrderDetails().iterator();
						while (iterator10.hasNext()) {
						    PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator10.next();
						    cell = new Cell(new Phrase(Double.toString(purchaseOrderDetail.getReceiveQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
							cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							t10.addCell(cell);
						}
						table2.insertTable(t10);
						Table t11 = new Table(1);
						Iterator iterator11 = purchaseOrder.getPurchaseOrderDetails().iterator();
						while (iterator11.hasNext()) {
						    PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator11.next();
						    cell = new Cell(new Phrase(Double.toString(purchaseOrderDetail.getOutstandingQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
							cell.setBorder(Rectangle.LEFT);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							t11.addCell(cell);
						}
						table2.insertTable(t11);
				}
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(11);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(11);
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
			ex.printStackTrace();
		}finally {
			try {
				PurchaseOrderDAO.getInstance().closeSessionForReal();
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