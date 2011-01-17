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
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.RekapMutationPayableReport;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.struts.form.VendorsForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class VendorReportAction extends Action {
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
					} else if ("PDFLIST".equalsIgnoreCase(action)) {
						forward = performPdfList(mapping, form, request, response);
					} else if ("REKAPMUTATIONPAYABLELIST".equalsIgnoreCase(action)) {
						forward = performRekapMutationPayableList(mapping, form, request, response);
					} else if ("REKAPMUTATIONPAYABLEPDFLIST".equalsIgnoreCase(action)) {
						forward = performRekapMutationPayablePdfList(mapping, form, request, response);
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
				Criteria criteria = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class);
				if (form.getString("company")!=null && form.getString("company").length()>0)criteria.add(Restrictions.like("Company", "%"+form.getString("company")+"%"));
				criteria.setProjection(Projections.rowCount());
				total = ((Integer)criteria.list().iterator().next()).intValue();
				// partial data
				criteria = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class);
				if (form.getString("company")!=null && form.getString("company").length()>0)criteria.add(Restrictions.like("Company", "%"+form.getString("company")+"%"));
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
	 * Method performDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performPdfList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		VendorsForm form = (VendorsForm) actionForm;
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
				
				Criteria criteria = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class);
				if (form.getString("company")!=null && form.getString("company").length()>0)criteria.add(Restrictions.like("Company", "%"+form.getString("company")+"%"));
				criteria.setProjection(Projections.rowCount());
				// partial data
				criteria = VendorsDAO.getInstance().getSession().createCriteria(Vendors.class);
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
				cell = new Cell(new Phrase("SUPPLIER TABLE", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
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
				cell = new Cell(new Phrase("SUPPLIER NAME",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
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
				    Vendors vendors = (Vendors)iterator.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(vendors.getCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(vendors.getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(vendors.getAddress()+" "+vendors.getCity()+" "+vendors.getPostalCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(vendors.getNpwp(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(vendors.getTelephone()+"/"+vendors.getFax()+"\n"+vendors.getContactPerson(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
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
			generalError(request,ex);
		} finally {
			try {
				VendorsDAO.getInstance().closeSessionForReal();
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
	private ActionForward performRekapMutationPayableList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		VendorsForm form = (VendorsForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
				if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate");
				OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
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
						"select a.vendor_id as {rmp.VendorId}, a.code as {rmp.Code}, a.company as {rmp.Company}, " +
						"IFNULL((select b.amount * b.exchange_rate from vendor_first_balance b where b.vendor_id=a.vendor_id), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity) from vendor_bill_detail d where d.vendor_bill_id=c.vendor_bill_id)) from vendor_bill c where c.status <> 'CANCEL' and c.vendor_id=a.vendor_id and c.bill_date >= :setupDate and c.bill_date < :fromDate), 0) - IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.vendor_bill_exchange_rate) from payment_to_vendor_vendor_bill g where g.payment_to_vendor_id=h.payment_to_vendor_id)) from payment_to_vendor h where h.status <> 'CANCEL' and h.payment_date >= :setupDate and h.payment_date < :fromDate and h.vendor_id=a.vendor_id), 0) - IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity) from retur_to_vendor_detail f where f.retur_to_vendor_id=e.retur_to_vendor_id)) from retur_to_vendor e where e.status <> 'CANCEL' and e.vendor_id=a.vendor_id and e.retur_date >= :setupDate and e.retur_date < :fromDate), 0)  as {rmp.FirstBalance}, " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity) from vendor_bill_detail d where d.vendor_bill_id=c.vendor_bill_id)) from vendor_bill c where c.status <> 'CANCEL' and c.vendor_id=a.vendor_id and c.bill_date >= :fromDate and c.bill_date <= :toDate), 0) as {rmp.Credit}, " +
						"IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.vendor_bill_exchange_rate) from payment_to_vendor_vendor_bill g where g.payment_to_vendor_id=h.payment_to_vendor_id)) from payment_to_vendor h where h.status <> 'CANCEL' and h.payment_date >= :fromDate and h.payment_date <= :toDate and h.vendor_id=a.vendor_id), 0) as {rmp.DebitPayment}," +
						"IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity) from retur_to_vendor_detail f where f.retur_to_vendor_id=e.retur_to_vendor_id)) from retur_to_vendor e where e.status <> 'CANCEL' and e.vendor_id=a.vendor_id and e.retur_date >= :fromDate and e.retur_date <= :toDate), 0) as {rmp.DebitRetur}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=a.organization_id) as {rmp.NumberOfDigit} " +
						"from vendors a " +
						"where " +
						"IFNULL((select b.amount * b.exchange_rate from vendor_first_balance b where b.vendor_id=a.vendor_id), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity) from vendor_bill_detail d where d.vendor_bill_id=c.vendor_bill_id)) from vendor_bill c where c.status <> 'CANCEL' and c.vendor_id=a.vendor_id and c.bill_date >= :setupDate and c.bill_date < :fromDate), 0) - IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.vendor_bill_exchange_rate) from payment_to_vendor_vendor_bill g where g.payment_to_vendor_id=h.payment_to_vendor_id)) from payment_to_vendor h where h.status <> 'CANCEL' and h.payment_date >= :setupDate and h.payment_date < :fromDate and h.vendor_id=a.vendor_id), 0) - IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity) from retur_to_vendor_detail f where f.retur_to_vendor_id=e.retur_to_vendor_id)) from retur_to_vendor e where e.status <> 'CANCEL' and e.vendor_id=a.vendor_id and e.retur_date >= :setupDate and e.retur_date < :fromDate), 0)<>0 OR " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity) from vendor_bill_detail d where d.vendor_bill_id=c.vendor_bill_id)) from vendor_bill c where c.status <> 'CANCEL' and c.vendor_id=a.vendor_id and c.bill_date >= :fromDate and c.bill_date <= :toDate), 0)<>0 OR " +
						"IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.vendor_bill_exchange_rate) from payment_to_vendor_vendor_bill g where g.payment_to_vendor_id=h.payment_to_vendor_id)) from payment_to_vendor h where h.status <> 'CANCEL' and h.payment_date >= :fromDate and h.payment_date <= :toDate and h.vendor_id=a.vendor_id), 0)<>0 OR " +
						"IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity) from retur_to_vendor_detail f where f.retur_to_vendor_id=e.retur_to_vendor_id)) from retur_to_vendor e where e.status <> 'CANCEL' and e.vendor_id=a.vendor_id and e.retur_date >= :fromDate and e.retur_date <= :toDate), 0)<>0 " +
						"AND a.organization_id = :organizationId " + x +" "+
						"group by a.vendor_id " + y;
				list = VendorsDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmp", RekapMutationPayableReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				request.setAttribute("REKAPMUTATIONPAYABLE", list);
				
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
	 * Method performDetail
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performRekapMutationPayablePdfList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		VendorsForm form = (VendorsForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
				if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate");
				OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
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
						"select a.vendor_id as {rmp.VendorId}, a.code as {rmp.Code}, a.company as {rmp.Company}, " +
						"IFNULL((select b.amount * b.exchange_rate from vendor_first_balance b where b.vendor_id=a.vendor_id), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity) from vendor_bill_detail d where d.vendor_bill_id=c.vendor_bill_id)) from vendor_bill c where c.status <> 'CANCEL' and c.vendor_id=a.vendor_id and c.bill_date >= :setupDate and c.bill_date < :fromDate), 0) - IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.vendor_bill_exchange_rate) from payment_to_vendor_vendor_bill g where g.payment_to_vendor_id=h.payment_to_vendor_id)) from payment_to_vendor h where h.status <> 'CANCEL' and h.payment_date >= :setupDate and h.payment_date < :fromDate and h.vendor_id=a.vendor_id), 0) - IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity) from retur_to_vendor_detail f where f.retur_to_vendor_id=e.retur_to_vendor_id)) from retur_to_vendor e where e.status <> 'CANCEL' and e.vendor_id=a.vendor_id and e.retur_date >= :setupDate and e.retur_date < :fromDate), 0)  as {rmp.FirstBalance}, " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity) from vendor_bill_detail d where d.vendor_bill_id=c.vendor_bill_id)) from vendor_bill c where c.status <> 'CANCEL' and c.vendor_id=a.vendor_id and c.bill_date >= :fromDate and c.bill_date <= :toDate), 0) as {rmp.Credit}, " +
						"IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.vendor_bill_exchange_rate) from payment_to_vendor_vendor_bill g where g.payment_to_vendor_id=h.payment_to_vendor_id)) from payment_to_vendor h where h.status <> 'CANCEL' and h.payment_date >= :fromDate and h.payment_date <= :toDate and h.vendor_id=a.vendor_id), 0) as {rmp.DebitPayment}," +
						"IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity) from retur_to_vendor_detail f where f.retur_to_vendor_id=e.retur_to_vendor_id)) from retur_to_vendor e where e.status <> 'CANCEL' and e.vendor_id=a.vendor_id and e.retur_date >= :fromDate and e.retur_date <= :toDate), 0) as {rmp.DebitRetur}, " +
						"(select d.number_of_digit from organization_setup d where d.organization_id=a.organization_id) as {rmp.NumberOfDigit} " +
						"from vendors a " +
						"where " +
						"IFNULL((select b.amount * b.exchange_rate from vendor_first_balance b where b.vendor_id=a.vendor_id), 0) + IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity) from vendor_bill_detail d where d.vendor_bill_id=c.vendor_bill_id)) from vendor_bill c where c.status <> 'CANCEL' and c.vendor_id=a.vendor_id and c.bill_date >= :setupDate and c.bill_date < :fromDate), 0) - IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.vendor_bill_exchange_rate) from payment_to_vendor_vendor_bill g where g.payment_to_vendor_id=h.payment_to_vendor_id)) from payment_to_vendor h where h.status <> 'CANCEL' and h.payment_date >= :setupDate and h.payment_date < :fromDate and h.vendor_id=a.vendor_id), 0) - IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity) from retur_to_vendor_detail f where f.retur_to_vendor_id=e.retur_to_vendor_id)) from retur_to_vendor e where e.status <> 'CANCEL' and e.vendor_id=a.vendor_id and e.retur_date >= :setupDate and e.retur_date < :fromDate), 0)<>0 OR " +
						"IFNULL((select sum((1 - (c.discount_procent / 100)) * (1 + (c.tax_amount / 100)) * c.exchange_rate * (select sum(d.exchange_rate*d.price*d.quantity) from vendor_bill_detail d where d.vendor_bill_id=c.vendor_bill_id)) from vendor_bill c where c.status <> 'CANCEL' and c.vendor_id=a.vendor_id and c.bill_date >= :fromDate and c.bill_date <= :toDate), 0)<>0 OR " +
						"IFNULL((select sum(h.exchange_rate * (select sum(g.payment_amount * g.vendor_bill_exchange_rate) from payment_to_vendor_vendor_bill g where g.payment_to_vendor_id=h.payment_to_vendor_id)) from payment_to_vendor h where h.status <> 'CANCEL' and h.payment_date >= :fromDate and h.payment_date <= :toDate and h.vendor_id=a.vendor_id), 0)<>0 OR " +
						"IFNULL((select sum(e.exchange_rate * (select sum(f.exchange_rate*f.price*f.quantity) from retur_to_vendor_detail f where f.retur_to_vendor_id=e.retur_to_vendor_id)) from retur_to_vendor e where e.status <> 'CANCEL' and e.vendor_id=a.vendor_id and e.retur_date >= :fromDate and e.retur_date <= :toDate), 0)<>0 " +
						"AND a.organization_id = :organizationId " + x +" "+
						"group by a.vendor_id " + y;
				list = VendorsDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmp", RekapMutationPayableReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				//request.setAttribute("REKAPMUTATIONPAYABLE", list);
				
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
				float[] b = {5,10,25,15,15,15,15};
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
				cell = new Cell(new Phrase("REKAP MUTATION PAYABLE", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
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
				cell = new Cell(new Phrase("SUPPLIER NAME",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
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
				
				// data
				int i = 0;
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
				    RekapMutationPayableReport rekapMutationPayableReport = (RekapMutationPayableReport)iterator.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(rekapMutationPayableReport.getCode(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(rekapMutationPayableReport.getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(rekapMutationPayableReport.getFormatedFirstBalance(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(rekapMutationPayableReport.getFormatedDebit(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(rekapMutationPayableReport.getFormatedCredit(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(rekapMutationPayableReport.getFormatedEndBalance(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
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
			ex.printStackTrace();
		} finally {
			try {
				VendorsDAO.getInstance().closeSessionForReal();
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