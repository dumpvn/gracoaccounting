package com.mpe.financial.struts.action;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.lowagie.text.Cell;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.mpe.common.CommonConstants;
import com.mpe.common.CommonUtil;
import com.mpe.common.Formater;
import com.mpe.common.Pager;
import com.mpe.financial.model.Journal;
import com.mpe.financial.model.JournalDetail;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.JournalDAO;
import com.mpe.financial.model.dao.JournalTypeDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.struts.form.JournalForm;
import com.mpe.report.tools.ListOfJournalHeaderFooter;

public class JournalReportAction extends Action {
	Log log = LogFactory.getFactory().getInstance(this.getClass());

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
			System.out.println("JournaReportAction.execute() ");
			ActionForward forward = null;
			String action = mapping.getParameter();
			System.out.println(action);
			HttpSession session = request.getSession();
			Users users = (Users)session.getAttribute(CommonConstants.USER);
			Set lst = (Set)session.getAttribute(CommonConstants.VIEWACCESS);
			if (users!=null) {
				if (CommonUtil.isHasRoleAccess(lst,request.getServletPath())) {
					if ("".equalsIgnoreCase(action)) {
						forward = mapping.findForward("home");
					} else if ("LISTDETAIL".equalsIgnoreCase(action)) {
						forward = performPartialList(mapping, form, request, response);
					} else if ("PDF".equalsIgnoreCase(action)) {
						forward = performPdf(mapping, form, request, response);
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

	private ActionForward performPartialList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);	
		try {
			int start = 0;
			int count = 0;
			int total = 0;
			System.out.println("prop start");
			ResourceBundle prop = ResourceBundle.getBundle("resource.ApplicationResources");
			
			try {
				start = Integer.parseInt(request.getParameter("start"));
			}catch(Exception ex) {
				ex.printStackTrace();
			}	
			System.out.println("prop stops");
			try {
				count = Integer.parseInt(prop.getString("max.item.per.page"));
			}catch(Exception ex) {
				ex.printStackTrace();
			}
//			save start and count attribute on session
			httpSession.setAttribute(CommonConstants.START,Integer.toString(start));
			httpSession.setAttribute(CommonConstants.COUNT,Integer.toString(count));
			
			List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("journalTypeLst", journalTypeLst);
			
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());			
			if (form.getCalendar("fromJournalDate")==null)form.setCalendar("fromJournalDate", organizationSetup.getSetupDate());
			if (form.getCalendar("toJournalDate")==null)form.setCurentCalendar("toJournalDate");
			Criteria criteria = JournalDAO.getInstance().getSession().createCriteria(Journal.class);		
			criteria.add(Expression.ge("JournalDate", new Date(form.getCalendar("fromJournalDate").getTime().getTime())));
			criteria.add(Expression.le("JournalDate", new Date(form.getCalendar("toJournalDate").getTime().getTime())));
			if (form.getLong("journalTypeId")>0) criteria.add(Restrictions.eq("JournalType.Id", new Long(form.getLong("journalTypeId"))));			
			criteria.addOrder(Order.desc("JournalDate"));
			total = criteria.list().size();
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List journals = criteria.list();

			request.setAttribute("JOURNAL",journals); 
			// calculate total debit and credit
			Iterator iterator = journals.iterator();
			double totalDebit = 0;
			double totalKredit = 0;
			while (iterator.hasNext()) {
				Journal j = (Journal) iterator.next();
				Set jurnalDetails = j.getJournalDetails();
				Iterator iterDetail = jurnalDetails.iterator();
				while (iterDetail.hasNext()) {
					JournalDetail journalDetail = (JournalDetail) iterDetail.next();
					if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
						if (journalDetail.getAmount()>0) {
							totalDebit = totalDebit + journalDetail.getAmount();
						} else {
							totalKredit = totalKredit + Math.abs(journalDetail.getAmount());
						}
					} else if (journalDetail.getId().getChartOfAccount().isDebit()==false){
						if (journalDetail.getAmount()<0) {
							totalDebit = totalDebit + Math.abs(journalDetail.getAmount());
						} else {
							totalKredit = totalKredit + journalDetail.getAmount();
						}
					}					
				}				
			}
			request.setAttribute("totalDebit", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), totalDebit));
			request.setAttribute("totalKredit", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), totalKredit));
			
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
				exx.printStackTrace();
			}
		}		

		return mapping.findForward("list");

	}
	
	private ActionForward performPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		JournalForm form = (JournalForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);	
		try {
//			int start = 0;
//			int count = 0;
			int total = 0;
			System.out.println("pdf report start");

			List journalTypeLst = JournalTypeDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("journalTypeLst", journalTypeLst);
			
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());			
			if (form.getCalendar("fromJournalDate")==null)form.setCalendar("fromJournalDate", organizationSetup.getSetupDate());
			if (form.getCalendar("toJournalDate")==null)form.setCurentCalendar("toJournalDate");
			Criteria criteria = JournalDAO.getInstance().getSession().createCriteria(Journal.class);		
			criteria.add(Expression.ge("JournalDate", new Date(form.getCalendar("fromJournalDate").getTime().getTime())));
			criteria.add(Expression.le("JournalDate", new Date(form.getCalendar("toJournalDate").getTime().getTime())));
			if (form.getLong("journalTypeId")>0) criteria.add(Restrictions.eq("JournalType.Id", new Long(form.getLong("journalTypeId"))));			
			criteria.addOrder(Order.desc("JournalDate"));
			total = criteria.list().size();

			List journals = criteria.list();
			// calculate total debit and credit
			Iterator iterator = journals.iterator();
			double totalDebit = 0;
			double totalKredit = 0;
			while (iterator.hasNext()) {
				Journal j = (Journal) iterator.next();
				Set jurnalDetails = j.getJournalDetails();
				Iterator iterDetail = jurnalDetails.iterator();
				while (iterDetail.hasNext()) {
					JournalDetail journalDetail = (JournalDetail) iterDetail.next();
					if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
						if (journalDetail.getAmount()>0) {
							totalDebit = totalDebit + journalDetail.getAmount();
						} else {
							totalKredit = totalKredit + Math.abs(journalDetail.getAmount());
						}
					} else if (journalDetail.getId().getChartOfAccount().isDebit()==false){
						if (journalDetail.getAmount()<0) {
							totalDebit = totalDebit + Math.abs(journalDetail.getAmount());
						} else {
							totalKredit = totalKredit + journalDetail.getAmount();
						}
					}					
				}				
			}			
			// start create pdf document
			com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4,30,25,85,25);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			ListOfJournalHeaderFooter header = new ListOfJournalHeaderFooter();
			header.setReportTitle("LIST OF JOURNAL");
			header.setCompanyName(users.getOrganization().getName());
			header.setFromToDate(Formater.getFormatedDate(form.getCalendar("fromJournalDate").getTime()) + " - " + Formater.getFormatedDate(form.getCalendar("toJournalDate").getTime()));
			header.createHeaderTable();
			writer.setPageEvent(header);
			
//			// header page
//			HeaderFooter header = new HeaderFooter(new Phrase("LIST OF JOURNAL ( " + Formater.getFormatedDate(form.getCalendar("fromJournalDate").getTime()) + " - " + Formater.getFormatedDate(form.getCalendar("toJournalDate").getTime()) + " )",FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD)), true);
//			header.setBorder(Rectangle.NO_BORDER);
//			document.setHeader(header);
			
			// footer page
			HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)), true);
			footer.setBorder(Rectangle.NO_BORDER);
			document.setFooter(footer);
			
			document.open();			
			
			Table table = new Table(7);
			table.setWidth(100);
			table.setCellsFitPage(true);
			table.setBorder(Rectangle.NO_BORDER);
			table.setBorderWidth(1);
			table.setPadding(1);
			table.setSpacing(0);
			float[] a3 = {5,10,15,20,20,15,15};
			table.setWidths(a3);
			table.setAutoFillEmptyCells(true);
			float borderWidth = 0.5f;
			
			Cell cell = new Cell(new Phrase("No",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(borderWidth);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT);
			table.addCell(cell);
			cell = new Cell(new Phrase("Journal Date",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(borderWidth);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table.addCell(cell);
			cell = new Cell(new Phrase("Number",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(borderWidth);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table.addCell(cell);
			cell = new Cell(new Phrase("Chart of Account",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(borderWidth);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table.addCell(cell);
			cell = new Cell(new Phrase("Description",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(borderWidth);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table.addCell(cell);
			cell = new Cell(new Phrase("Debit",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(borderWidth);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table.addCell(cell);
			cell = new Cell(new Phrase("Credit",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(borderWidth);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table.addCell(cell);
			table.endHeaders();

//			// data start
			int i = 0;
			iterator = journals.iterator();
			while (iterator.hasNext()) {
				Journal journal = (Journal) iterator.next();
				Set journalDetails = journal.getJournalDetails();
				Iterator iterDetail = journalDetails.iterator();	
				int rowSpan = journalDetails.size();
				String creditSpaces = "     ";
				i++;
				int j = 0;
				while (iterDetail.hasNext()) {
					JournalDetail journalDetail = (JournalDetail) iterDetail.next();
					if (j==0) {
						// No
					    cell = new Cell(new Phrase(i+".",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorderWidth(borderWidth);
						cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
						cell.setRowspan(rowSpan);
						table.addCell(cell);
						// Journal Date
						String formatedJournalDate = journal.getFormatedJournalDate();
					    cell = new Cell(new Phrase(formatedJournalDate,FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setBorderWidth(borderWidth);
						cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
						cell.setRowspan(rowSpan);
						table.addCell(cell);	
						// Number
						String number = journal.getNumber();
					    cell = new Cell(new Phrase(number,FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorderWidth(borderWidth);
						cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
						cell.setRowspan(rowSpan);
						table.addCell(cell);	
					}
					String coa = "coa";
					String debit = "debit";
					String kredit = "kredit";
					if (journalDetail.getId().getChartOfAccount().isDebit()==true) {							
						if (journalDetail.getAmount()>0) {
							coa = journalDetail.getId().getChartOfAccount().getNumberName();
							debit = journalDetail.getFormatedAmount();
							kredit = "";
						} else {
							coa = creditSpaces + journalDetail.getId().getChartOfAccount().getNumberName();	
							debit = "";
							kredit = journalDetail.getAbsFormatedAmount();
						}
					} else if (journalDetail.getId().getChartOfAccount().isDebit()==false){						
						if (journalDetail.getAmount()<0) {
							coa = journalDetail.getId().getChartOfAccount().getNumberName();
							debit = journalDetail.getAbsFormatedAmount();
							kredit = "";
						} else {
							coa = creditSpaces + journalDetail.getId().getChartOfAccount().getNumberName();
							debit = "";
							kredit = journalDetail.getFormatedAmount();
						}
					}
					// Chart of Account
				    cell = new Cell(new Phrase(coa,FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorderWidth(borderWidth);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
					cell.setRowspan(1);
					table.addCell(cell);	
					// Description
					if (j==0) {						
						String description = journal.getDescription();
					    cell = new Cell(new Phrase(description,FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorderWidth(borderWidth);
						cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
						cell.setRowspan(rowSpan);
						table.addCell(cell);
					}
					// Debit
				    cell = new Cell(new Phrase(debit,FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(borderWidth);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
					cell.setRowspan(1);
					table.addCell(cell);	
					// Kredit
				    cell = new Cell(new Phrase(kredit,FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(borderWidth);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
					cell.setRowspan(1);
					table.addCell(cell);					
					j++;
				}
				
			}
			
			cell = new Cell(new Phrase("\t",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setColspan(5);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
			table.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), totalDebit),FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), totalKredit),FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
			table.addCell(cell);			
			
			
			document.add(table);
			
			//data stop

//			 Table table2 = new Table(4);
//			 table2.setBorderWidth(1);
//			 table2.setBorderColor(new Color(0, 0, 255));
//			 Cell cell2 = new Cell("header");
//			 cell2.setHeader(true);
//			 cell2.setColspan(3);
//			 table.addCell(cell2);
//			 cell2 = new Cell("example cell with colspan 1 and rowspan 2");
//			 cell2.setRowspan(2);
//			 cell2.setBorderColor(new Color(255, 0, 0));
//			 table2.addCell(cell2);
//			 table2.addCell("1.1");
//			 table2.addCell(cell2);
//			 table2.addCell("2.1");
//			 table2.addCell("1.2");
//			 table2.addCell("2.2");
//			 
//			 
//			 document.add(table2);
			document.close();
			//send pdf to browser
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream out = response.getOutputStream();
			baos.writeTo(out);
			out.flush();			
			
			// stop create pdf document
			
			
			System.out.println("pdf report stop");
			
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
			try {
				JournalDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
				exx.printStackTrace();
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
