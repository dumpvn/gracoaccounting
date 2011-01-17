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
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.Customers;
import com.mpe.financial.model.Department;
import com.mpe.financial.model.GeneralLedgerReport;
import com.mpe.financial.model.JournalDetail;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Project;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Vendors;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.DepartmentDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.ProjectDAO;
import com.mpe.financial.model.dao.VendorsDAO;
import com.mpe.financial.struts.form.GeneralLedgerForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mpe.common.*;

public class GeneralLedgerReportAction extends Action {
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
					} else if ("PDF".equalsIgnoreCase(action)) {
						forward = performPdf(mapping, form, request, response);
					} else if ("JOURNALDETAILLISTPDF".equalsIgnoreCase(action)) {
						forward = performJournalDetailListPdf(mapping, form, request, response);
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
	private ActionForward performPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			Project project = ProjectDAO.getInstance().get(form.getLong("projectId"));
			Department department = DepartmentDAO.getInstance().get(form.getLong("departmentId"));
			Customers customers = CustomersDAO.getInstance().get(form.getLong("customerId"));
			Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
			//Ship ship = ShipDAO.getInstance().get(form.getLong("shipId"));
			//com.mpe.financial.model.Document documentShip = DocumentDAO.getInstance().get(form.getLong("documentId"));
			ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
			
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
			if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
			//session.enableFilter("fromDate").setParameter("fromDate", new Date());
			//session.enableFilter("toDate").setParameter("toDate", new Date());
			//session.enableFilter("organizationId").setParameter("organizationId", new Long(users.getOrganization().getId()));
			//List list = session.createQuery("select generalLedgerReport from GeneralLedgerReport generalLedgerReport").list();
			List list = new LinkedList();
			String a = "";
			String b = "";
			if (form.getLong("projectId")>0) a = a + " and d.project_id = "+form.getLong("projectId")+" ";
			if (form.getLong("departmentId")>0) a = a + " and c.department_id = "+form.getLong("departmentId")+" ";
			if (form.getLong("customerId")>0) {
			    a = a + " and d.customer_id = "+form.getLong("customerId")+" ";
			    b = b + " and b.customer_id = "+form.getLong("customerId")+" ";
			}
			if (form.getLong("vendorId")>0) {
			    a = a + " and d.vendor_id = "+form.getLong("vendorId")+" ";
			    b = b + " and b.vendor_id = "+form.getLong("vendorId")+" ";
			}
			if (form.getLong("shipId")>0) a = a + " and d.ship_id = "+form.getLong("shipId")+" ";
			if (form.getLong("documentId")>0) a = a + " and d.document_id = "+form.getLong("documentId")+" ";
			String sql = "" +
					"select a.chart_of_account_id as {gl.ChartOfAccountId}, a.number as {gl.Number}, a.name as {gl.Name}, a.type as {gl.Type}, a.groups as {gl.Groups}, a.is_debit as {gl.Debit}, " +
					"IFNULL((select sum(b.amount) from general_ledger b where b.chart_of_account_id=a.chart_of_account_id and b.organization_id = :organizationId and b.is_setup='Y' and b.is_closed='N' "+b+" ), 0) as {gl.FirstSetupAmount}, " +
					"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :setupDate and d.journal_date < :fromDate and d.is_posted = 'Y' "+a+"), 0) as {gl.PreviousAmount}, " +
					"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :fromDate and d.journal_date <= :toDate and d.is_posted = 'Y' "+a+"), 0) as {gl.Amount}, " +
					"(select e.number_of_digit from organization_setup e where e.organization_id=:organizationId) as {gl.NumberOfDigit} " +
					"from chart_of_account a where 1=1 " +
					"and (" +
					"(IFNULL((select sum(b.amount) from general_ledger b where b.chart_of_account_id=a.chart_of_account_id and b.organization_id = :organizationId and b.is_setup='Y' and b.is_closed='N' "+b+" ), 0))<>0 " +
					"or (IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :setupDate and d.journal_date < :fromDate and d.is_posted = 'Y' "+a+"), 0))<>0 " +
					"or (IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :fromDate and d.journal_date <= :toDate and d.is_posted = 'Y' "+a+"), 0))<>0 " +
					")" +
					"";
			if (organizationSetup.getProfitLossAccount()!=null) sql = sql + " and a.chart_of_account_id <> "+organizationSetup.getProfitLossAccount().getId();
			if (form.getLong("chartOfAccountId")>0) sql = sql + " and a.chart_of_account_id = "+form.getLong("chartOfAccountId");
			sql = sql + " order by a.number asc ";
			list = session.createSQLQuery(sql)
			.addEntity("gl", GeneralLedgerReport.class)
			.setLong("organizationId", users.getOrganization().getId())
			.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
			.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
			.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
			.list();
			request.setAttribute("GENERALLEDGER", list);
			double firstAmount = 0;
			double debitAmount = 0;
			double creditAmount = 0;
			double endAmount = 0;
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				GeneralLedgerReport generalLedgerReport = (GeneralLedgerReport)iterator.next();
				if (generalLedgerReport.isDebit()==true) {
					if (generalLedgerReport.getFirstAmount()>=0) {
					    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
					} else {
					    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
					}
					if (generalLedgerReport.getAmount()>=0) {
						debitAmount = debitAmount + generalLedgerReport.getAmount();
					} else {
						creditAmount = creditAmount - generalLedgerReport.getAmount();
					}
					if (generalLedgerReport.getEndAmount()>=0) {
					    endAmount = endAmount + generalLedgerReport.getEndAmount();
					} else {
					    endAmount = endAmount + generalLedgerReport.getEndAmount();
					}
				} else {
					if (generalLedgerReport.getFirstAmount()>=0) {
					    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
					} else {
					    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
					}
					if (generalLedgerReport.getAmount()>=0) {
						creditAmount = creditAmount + generalLedgerReport.getAmount();
					} else {
						debitAmount = debitAmount - generalLedgerReport.getAmount();
					}
					if (generalLedgerReport.getEndAmount()>=0) {
					    endAmount = endAmount + generalLedgerReport.getEndAmount();
					} else {
					    endAmount = endAmount + generalLedgerReport.getEndAmount();
					}
				}
			}
			//request.setAttribute("firstDebitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), firstDebitAmount));
			//request.setAttribute("firstCreditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), firstCreditAmount));
			//request.setAttribute("debitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitAmount));
			//request.setAttribute("creditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditAmount));
			//request.setAttribute("endDebitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), endDebitAmount));
			//request.setAttribute("endCreditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), endCreditAmount));
			
			// page size --> 1inch = 72points
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
			if (department!=null) {
				cell = new Cell(new Phrase(department.getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
			}
			if (chartOfAccount!=null) {
				cell = new Cell(new Phrase(chartOfAccount.getNumberName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
			}
			if (customers!=null) {
				cell = new Cell(new Phrase(customers.getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
			}
			if (vendors!=null) {
				cell = new Cell(new Phrase(vendors.getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
			}
/*			if (ship!=null) {
				cell = new Cell(new Phrase(ship.getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
			}
			if (documentShip!=null) {
				cell = new Cell(new Phrase(documentShip.getNumberEtaEtd(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
			}*/
			if (project!=null) {
				cell = new Cell(new Phrase(project.getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
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
			
			Table table2 = new Table(7);
			table2.setWidth(100);
			table2.setCellsFitPage(true);
			table2.setBorder(Rectangle.NO_BORDER);
			table2.setBorderWidth(1);
			table2.setPadding(1);
			table2.setSpacing(0);
			float[] a3 = {5,15,20,15,15,15,15};
			table2.setWidths(a3);
			table2.setAutoFillEmptyCells(true);
			
			cell = new Cell(new Phrase("No",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Number",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Chart of Account",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("First Balance",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Debit",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("Kredit",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase("End Balance",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			table2.endHeaders();
			
			//data				
			// loop
			int i=0;
			Iterator iterator7 = list.iterator();
			while (iterator7.hasNext()) {
			    GeneralLedgerReport generalLedger = (GeneralLedgerReport)iterator7.next();
			    cell = new Cell(new Phrase(++i+".",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(generalLedger.getNumber(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
					table2.addCell(cell);
			    cell = new Cell(new Phrase(generalLedger.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
					table2.addCell(cell);
					cell = new Cell(new Phrase(generalLedger.getFormatedFirstAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
					table2.addCell(cell);
					if (generalLedger.getAmount()>0) {
					    if (generalLedger.isDebit()==true) {
					        cell = new Cell(new Phrase(generalLedger.getFormatedAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setBorderWidth(1);
									cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
									table2.addCell(cell);
					    } else {
					        cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setBorderWidth(1);
									cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
									table2.addCell(cell);
					    }
					} else {
					    if (generalLedger.isDebit()==false) {
					        cell = new Cell(new Phrase(generalLedger.getFormatedAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setBorderWidth(1);
									cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
									table2.addCell(cell);
					    } else {
					        cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setBorderWidth(1);
									cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
									table2.addCell(cell);
					    }
					}
					if (generalLedger.getAmount()>0) {
					    if (generalLedger.isDebit()==false) {
					        cell = new Cell(new Phrase(generalLedger.getFormatedAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setBorderWidth(1);
									cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
									table2.addCell(cell);
					    } else {
					        cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setBorderWidth(1);
									cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
									table2.addCell(cell);
					    }
					} else {
					    if (generalLedger.isDebit()==true) {
					        cell = new Cell(new Phrase(generalLedger.getFormatedAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setBorderWidth(1);
									cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
									table2.addCell(cell);
					    } else {
					        cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
									cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
									cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cell.setBorderWidth(1);
									cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
									table2.addCell(cell);
					    }
					}
					cell = new Cell(new Phrase(generalLedger.getFormatedEndAmount(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
					table2.addCell(cell);
					
					
					
			}
			cell = new Cell(new Phrase("\t",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(1);
			cell.setColspan(3);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), firstAmount),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitAmount),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditAmount),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			table2.addCell(cell);
			cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), endAmount),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
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
	private ActionForward performJournalDetailListPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			/*
			List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
			.addOrder(Order.asc("Name")).list();
			request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			List departmentLst = DepartmentDAO.getInstance().getSession().createCriteria(Department.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("departmentLst", departmentLst);
			List projectLst = ProjectDAO.getInstance().getSession().createCriteria(Project.class)
			.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId()))).list();
			request.setAttribute("projectLst", projectLst);
			*/
			//ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
			Vendors vendors = VendorsDAO.getInstance().get(form.getLong("vendorId"));
			Customers customers  = CustomersDAO.getInstance().get(form.getLong("customerId"));
			//Ship ship = ShipDAO.getInstance().get(form.getLong("shipId"));
			//com.mpe.financial.model.Document documentShip = DocumentDAO.getInstance().get(form.getLong("documentId"));
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			if (form.getCalendar("fromDate")==null)form.setCalendar("fromDate", organizationSetup.getSetupDate());
			if (form.getCalendar("toDate")==null)form.setCurentCalendar("toDate");
			//session.enableFilter("fromDate").setParameter("fromDate", new Date());
			//session.enableFilter("toDate").setParameter("toDate", new Date());
			//session.enableFilter("organizationId").setParameter("organizationId", new Long(users.getOrganization().getId()));
			//List list = session.createQuery("select generalLedgerReport from GeneralLedgerReport generalLedgerReport").list();
			Criteria criteria3Criteria = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class);
			if (form.getLong("chartOfAccountId")>0) criteria3Criteria.add(Restrictions.eq("Id", new Long(form.getLong("chartOfAccountId"))));
			criteria3Criteria.addOrder(Order.asc("Number"));
			List chartOfAccountList = criteria3Criteria.list();
			List chartOfAccountList2 = new LinkedList();
			double totalEndAmount = 0;
			
			Iterator iterator = chartOfAccountList.iterator();
			while (iterator.hasNext()) {
				ChartOfAccount chartOfAccount = (ChartOfAccount)iterator.next();
				
				String b = "";
				if (form.getLong("projectId")>0) b = b + " and journal.Project.Id = "+form.getLong("projectId")+" ";
				if (form.getLong("departmentId")>0) b = b + " and journal.Department.Id = "+form.getLong("departmentId")+" ";
				if (form.getLong("customerId")>0) b = b + " and journal.Customer.Id = "+form.getLong("customerId")+" ";
				if (form.getLong("vendorId")>0) b = b + " and journal.Vendor.Id = "+form.getLong("vendorId")+" ";
				//if (form.getLong("shipId")>0) b = b + " and journal.Ship.Id = "+form.getLong("shipId")+" ";
				//if (form.getLong("documentId")>0) b = b + " and journal.Document.Id = "+form.getLong("documentId")+" ";
				if (chartOfAccount!=null) b = b + " and journalDetail.Id.ChartOfAccount.Id = "+chartOfAccount.getId()+" ";
				List list2 = session.createQuery("select distinct journalDetail from JournalDetail journalDetail join journalDetail.Id.Journal journal where journal.JournalDate >= :fromDate and journal.JournalDate <= :toDate and journal.Posted='Y' and journal.Organization.Id = :organizationId "+b+" order by journalDetail.Id.ChartOfAccount.Id ASC, journal.JournalDate ASC, journal.Number ASC ")
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				//request.setAttribute("JOURNALDETAIL", list2);
				chartOfAccount.setJournalDetailList(list2);
							
				List list = new LinkedList();
				String a = "";
				String c = "";
				//if (form.getLong("projectId")>0) a = a + " and d.project_id = "+form.getLong("projectId")+" ";
				//if (form.getLong("departmentId")>0) a = a + " and c.department_id = "+form.getLong("departmentId")+" ";
				if (form.getLong("customerId")>0) {
				    a = a + " and d.customer_id = "+form.getLong("customerId")+" ";
				    c = c + " and b.customer_id = "+form.getLong("customerId")+" ";
				}
				if (form.getLong("vendorId")>0) {
				    a = a + " and d.vendor_id = "+form.getLong("vendorId")+" ";
				    c = c + " and b.vendor_id = "+form.getLong("vendorId")+" ";
				}
				//if (form.getLong("shipId")>0) a = a + " and d.ship_id = "+form.getLong("shipId")+" ";
				//if (form.getLong("documentId")>0) a = a + " and d.document_id = "+form.getLong("documentId")+" ";
				String sql = "" +
						"select a.chart_of_account_id as {gl.ChartOfAccountId}, a.number as {gl.Number}, a.name as {gl.Name}, a.type as {gl.Type}, a.groups as {gl.Groups}, a.is_debit as {gl.Debit}, " +
						"IFNULL((select sum(b.amount) from general_ledger b where b.chart_of_account_id=a.chart_of_account_id and b.organization_id = :organizationId and b.is_setup='Y' and b.is_closed='N' "+c+" ), 0) as {gl.FirstSetupAmount}, " +
						"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :setupDate and d.journal_date < :fromDate and d.is_posted = 'Y' "+a+"), 0) as {gl.PreviousAmount}, " +
						"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :fromDate and d.journal_date <= :toDate and d.is_posted = 'Y' "+a+"), 0) as {gl.Amount}, " +
						"(select e.number_of_digit from organization_setup e where e.organization_id=:organizationId) as {gl.NumberOfDigit} " +
						"from chart_of_account a " +
						"";
				//if (form.getLong("chartOfAccountId")>0) sql = sql + "where a.chart_of_account_id = "+form.getLong("chartOfAccountId");
				if (chartOfAccount!=null) sql = sql + "where a.chart_of_account_id = "+chartOfAccount.getId()+" ";
				sql = sql + " order by a.number asc ";
				list = session.createSQLQuery(sql)
					.addEntity("gl", GeneralLedgerReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				//request.setAttribute("GENERALLEDGER", list);
				
				
				double firstAmount = 0;
				//double firstCreditAmount = 0;
				double debitAmount = 0;
				double creditAmount = 0;
				double endAmount = 0;
				//double endCreditAmount = 0;
				Iterator iterator2Iterator = list.iterator();
				while (iterator2Iterator.hasNext()) {
					GeneralLedgerReport generalLedgerReport = (GeneralLedgerReport)iterator2Iterator.next();
					if (generalLedgerReport.isDebit()==true) {
						if (generalLedgerReport.getFirstAmount()>=0) {
						    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
						} else {
						    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
						}
						if (generalLedgerReport.getAmount()>=0) {
							debitAmount = debitAmount + generalLedgerReport.getAmount();
						} else {
							creditAmount = creditAmount - generalLedgerReport.getAmount();
						}
						if (generalLedgerReport.getEndAmount()>=0) {
						    endAmount = endAmount + generalLedgerReport.getEndAmount();
						    totalEndAmount = totalEndAmount + generalLedgerReport.getEndAmount();
						} else {
						    endAmount = endAmount + generalLedgerReport.getEndAmount();
						    totalEndAmount = totalEndAmount + generalLedgerReport.getEndAmount();
						}
					} else {
						if (generalLedgerReport.getFirstAmount()>=0) {
						    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
						} else {
						    firstAmount = firstAmount + generalLedgerReport.getFirstAmount();
						}
						if (generalLedgerReport.getAmount()>=0) {
							creditAmount = creditAmount + generalLedgerReport.getAmount();
						} else {
							debitAmount = debitAmount - generalLedgerReport.getAmount();
						}
						if (generalLedgerReport.getEndAmount()>=0) {
						    endAmount = endAmount + generalLedgerReport.getEndAmount();
						    totalEndAmount = totalEndAmount + generalLedgerReport.getEndAmount();
						} else {
						    endAmount = endAmount + generalLedgerReport.getEndAmount();
						    totalEndAmount = totalEndAmount + generalLedgerReport.getEndAmount();
						}
					}
				}
				chartOfAccount.setFirstAmount(firstAmount);
				chartOfAccount.setNumberOfDigit(organizationSetup.getNumberOfDigit());
				chartOfAccountList2.add(chartOfAccount);
				
			}
			
			//write to pdf document
			Rectangle pageSize = new Rectangle(612, 936);
			Document document = new Document(pageSize,30,25,25,25);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			// footer page
			HeaderFooter footer = new HeaderFooter(new Phrase("Page: ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)), true);
			footer.setBorder(Rectangle.NO_BORDER);
			document.setFooter(footer);
			document.open();
			
			//title
			// table upper
			Table table1 = new Table(2);
			table1.setWidth(100);
			table1.setCellsFitPage(true);
			float[] a2 = {50,50};
			table1.setWidths(a2);
			table1.setBorder(Rectangle.NO_BORDER);
			table1.setBorderWidth(1);
			table1.setPadding(1);
			table1.setSpacing(0);
			
			Cell cell = new Cell(new Phrase(users.getOrganization().getName(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);
			/*cell = new Cell(new Phrase("COA : "+(chartOfAccount!=null?chartOfAccount.getNumberName():""), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.addCell(cell);*/
			if (vendors!=null) {
				cell = new Cell(new Phrase("Vendor : "+(vendors!=null?vendors.getCompany():""), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
			}
			if (customers!=null) {
				cell = new Cell(new Phrase("Customer : "+(customers!=null?customers.getCompany():""), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table1.addCell(cell);
			}
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
			
			// header table
			Table firstTable = new Table(6);
			firstTable.setWidth(100);
			firstTable.setCellsFitPage(true);
			firstTable.setBorderWidth(1);
			firstTable.setBorder(Rectangle.NO_BORDER);
			firstTable.setPadding(1);
			firstTable.setSpacing(0);
			int b2[] = {15, 10, 45, 10, 10, 10};
			firstTable.setWidths(b2);
			
			cell = new Cell(new Phrase("Date",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			firstTable.addCell(cell);
			cell = new Cell(new Phrase("Number",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			firstTable.addCell(cell);
			
			cell = new Cell(new Phrase("Description",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			firstTable.addCell(cell);
			cell = new Cell(new Phrase("Debet",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			firstTable.addCell(cell);
			cell = new Cell(new Phrase("Kredit",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			firstTable.addCell(cell);
			cell = new Cell(new Phrase("Saldo",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
			firstTable.addCell(cell);
			firstTable.endHeaders();
			
			Iterator iterator2 = chartOfAccountList2.iterator();
			while (iterator2.hasNext()) {
				ChartOfAccount chartOfAccount = (ChartOfAccount)iterator2.next();
				
				// first balance
				cell = new Cell(new Phrase(chartOfAccount.getNumberName(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("Saldo Awal",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), chartOfAccount.getFirstAmount()),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(Rectangle.LEFT);
				firstTable.addCell(cell);
				// data
				//int i=0;
				Iterator iterator3 = chartOfAccount.getJournalDetailList()!=null?chartOfAccount.getJournalDetailList().iterator():new LinkedList().iterator();
				while (iterator3.hasNext()) {
					JournalDetail journalDetail = (JournalDetail)iterator3.next();
					cell = new Cell(new Phrase(Formater.getFormatedDate(journalDetail.getId().getJournal().getJournalDate()),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.RIGHT);
					firstTable.addCell(cell);
					
					cell = new Cell(new Phrase(journalDetail.getId().getJournal().getNumber(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.RIGHT);
					firstTable.addCell(cell);
					
	/*				cell = new Cell(new Phrase(journalDetail.getId().getJournal().getReference(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(Rectangle.RIGHT);
					firstTable.addCell(cell);*/
					cell = new Cell(new Phrase(journalDetail.getDescription(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorder(Rectangle.RIGHT);
					firstTable.addCell(cell);
					//debit
					if(journalDetail.getId().getChartOfAccount().isDebit()==true){
						if(journalDetail.getAmount() > 0){
							cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_RIGHT);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
						} else {
							cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_RIGHT);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
						}
					} else if (journalDetail.getId().getChartOfAccount().isDebit()==false) {
						if(journalDetail.getAmount() < 0){
							cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_RIGHT);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
						} else {
							cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_RIGHT);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
						}
					}
					//credit
					if (journalDetail.getId().getChartOfAccount().isDebit()==false) {
					  if(journalDetail.getAmount() > 0){
							cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_RIGHT);
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
					  }else{
						  cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, com.lowagie.text.Font.NORMAL)));
						  cell.setVerticalAlignment(Element.ALIGN_RIGHT);
						  cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						  cell.setBorder(Rectangle.RIGHT);
						  firstTable.addCell(cell);
					  }
					} else if (journalDetail.getId().getChartOfAccount().isDebit()==true) {
						if(journalDetail.getAmount() < 0){
						  cell = new Cell(new Phrase(journalDetail.getFormatedAmountConversion(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
						  cell.setVerticalAlignment(Element.ALIGN_RIGHT);
						  cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						  cell.setBorder(Rectangle.RIGHT);
						  firstTable.addCell(cell);
						}else{
							cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, com.lowagie.text.Font.NORMAL)));
							cell.setVerticalAlignment(Element.ALIGN_RIGHT);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setBorder(Rectangle.RIGHT);
							firstTable.addCell(cell);
						}
					}
					cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.NORMAL)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(Rectangle.LEFT);
					firstTable.addCell(cell);
				}
				// end balance
				cell = new Cell(new Phrase(" ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.BOLD)));
				cell.setColspan(3);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), chartOfAccount.getDebitAmount()),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), chartOfAccount.getCreditAmount()),FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase("",FontFactory.getFont(FontFactory.TIMES_ROMAN, 6, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_RIGHT);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				firstTable.addCell(cell);
			}
				
			document.add(firstTable);
			
			
			
			//send pdf to browser
			document.close();
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream out = response.getOutputStream();
			baos.writeTo(out);
			out.flush();
			
			/*
			request.setAttribute("firstDebitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), firstDebitAmount));
			request.setAttribute("firstCreditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), firstCreditAmount));
			request.setAttribute("debitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitAmount));
			request.setAttribute("creditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditAmount));
			request.setAttribute("endDebitAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), endDebitAmount));
			request.setAttribute("endCreditAmount", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), endCreditAmount));
			*/
		}catch(Exception ex){
			generalError(request,ex);
			log.info("[ Error generate PDF : "+ex+" ]");
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