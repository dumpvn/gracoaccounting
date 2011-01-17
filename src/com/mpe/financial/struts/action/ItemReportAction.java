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
import com.mpe.financial.model.Inventory;
import com.mpe.financial.model.Item;
import com.mpe.financial.model.ItemCategory;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.RekapMutationStockReport;
import com.mpe.financial.model.StockCardReport;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.CustomersDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemCategoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.RekapMutationStockReportDAO;
import com.mpe.financial.model.dao.StockCardReportDAO;
import com.mpe.financial.struts.form.ItemForm;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class ItemReportAction extends Action {
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
		//ItemForm itemForm = (ItemForm) form;
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
					} else if ("STOCKCARDOFFICE".equalsIgnoreCase(action)) {
						forward = performStockCardOffice(mapping, form, request, response);
					} else if ("STOCKCARDOFFICEPDF".equalsIgnoreCase(action)) {
						forward = performStockCardOfficePdf(mapping, form, request, response);
					} else if ("RECAPITULATIONSTOCKOFFICE".equalsIgnoreCase(action)) {
						forward = performRecapitulationStockOffice(mapping, form, request, response);
					} else if ("RECAPITULATIONSTOCKOFFICEPDF".equalsIgnoreCase(action)) {
						forward = performRecapitulationStockOfficePdf(mapping, form, request, response);
					} else if ("STOCKCARDCOUNTER".equalsIgnoreCase(action)) {
						forward = performStockCardCounter(mapping, form, request, response);
					} else if ("STOCKCARDCOUNTERPDF".equalsIgnoreCase(action)) {
						forward = performStockCardCounterPdf(mapping, form, request, response);
					} else if ("RECAPITULATIONSTOCKCOUNTER".equalsIgnoreCase(action)) {
						forward = performRecapitulationStockCounter(mapping, form, request, response);
					} else if ("RECAPITULATIONSTOCKCOUNTERPDF".equalsIgnoreCase(action)) {
						forward = performRecapitulationStockCounterPdf(mapping, form, request, response);
					} else if ("STOCKCARDGLOBALCOUNTER".equalsIgnoreCase(action)) {
						forward = performStockCardGlobalCounter(mapping, form, request, response);
					} else if ("STOCKCARDGLOBALCOUNTERPDF".equalsIgnoreCase(action)) {
						forward = performStockCardGlobalCounterPdf(mapping, form, request, response);
					} else if ("RECAPITULATIONSTOCKGLOBALCOUNTER".equalsIgnoreCase(action)) {
						forward = performRecapitulationStockGlobalCounter(mapping, form, request, response);
					} else if ("RECAPITULATIONSTOCKGLOBALCOUNTERPDF".equalsIgnoreCase(action)) {
						forward = performRecapitulationStockGlobalCounterPdf(mapping, form, request, response);
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
		ItemForm form = (ItemForm) actionForm;
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
			List itemCategoryLst = ItemCategoryDAO.getInstance().getSession().createCriteria(ItemCategory.class)
				.addOrder(Order.asc("Name")).list();
			request.setAttribute("itemCategoryLst",itemCategoryLst);
			
			Criteria criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
			if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code")));
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
			//if (form.getLong("itemMerkId")>0)criteria.add(Restrictions.eq("ItemMerk.Id", new Long(form.getLong("itemMerkId"))));
			//if (form.getLong("itemTypeId")>0)criteria.add(Restrictions.eq("ItemType.Id", new Long(form.getLong("itemTypeId"))));
			//if (form.getLong("itemColorId")>0)criteria.add(Restrictions.eq("ItemColor.Id", new Long(form.getLong("itemColorId"))));
			if (form.getLong("itemCategoryId")>0){
				criteria.createCriteria("ItemCategory").add(Restrictions.eq("Id", new Long(form.getLong("itemCategoryId"))));
			}
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
			if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code")));
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
			//if (form.getLong("itemMerkId")>0)criteria.add(Restrictions.eq("ItemMerk.Id", new Long(form.getLong("itemMerkId"))));
			//if (form.getLong("itemTypeId")>0)criteria.add(Restrictions.eq("ItemType.Id", new Long(form.getLong("itemTypeId"))));
			//if (form.getLong("itemColorId")>0)criteria.add(Restrictions.eq("ItemColor.Id", new Long(form.getLong("itemColorId"))));
			if (form.getLong("itemCategoryId")>0){
				criteria.createCriteria("ItemCategory").add(Restrictions.eq("Id", new Long(form.getLong("itemCategoryId"))));
			}
			//criteria.addOrder(Order.asc("BrandName"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("ITEM",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				ItemCategoryDAO.getInstance().closeSessionForReal();
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
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
				Criteria criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
				if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code")));
				if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
				//if (form.getLong("itemMerkId")>0)criteria.add(Restrictions.eq("ItemMerk.Id", new Long(form.getLong("itemMerkId"))));
				//if (form.getLong("itemTypeId")>0)criteria.add(Restrictions.eq("ItemType.Id", new Long(form.getLong("itemTypeId"))));
				//if (form.getLong("itemColorId")>0)criteria.add(Restrictions.eq("ItemColor.Id", new Long(form.getLong("itemColorId"))));
				if (form.getLong("itemCategoryId")>0){
					criteria.createCriteria("ItemCategory").add(Restrictions.eq("Id", new Long(form.getLong("itemCategoryId"))));
				}
				criteria.setProjection(Projections.rowCount());
				// partial data
				criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
				if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code")));
				if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
				//if (form.getLong("itemMerkId")>0)criteria.add(Restrictions.eq("ItemMerk.Id", new Long(form.getLong("itemMerkId"))));
				//if (form.getLong("itemTypeId")>0)criteria.add(Restrictions.eq("ItemType.Id", new Long(form.getLong("itemTypeId"))));
				//if (form.getLong("itemColorId")>0)criteria.add(Restrictions.eq("ItemColor.Id", new Long(form.getLong("itemColorId"))));
				if (form.getLong("itemCategoryId")>0){
					criteria.createCriteria("ItemCategory").add(Restrictions.eq("Id", new Long(form.getLong("itemCategoryId"))));
				}
				//criteria.addOrder(Order.asc("BrandName"));
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
				
				Table table2 = new Table(8);
				table2.setWidth(100);
				table2.setCellsFitPage(true);
				float[] b = {5,25,15,10,10,10,15,10};
				table2.setWidths(b);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setCellsFitPage(true);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(8);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(8);
				table2.addCell(cell);
				cell = new Cell(new Phrase("STOCK TABLE", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(8);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(8);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(8);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(8);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ARTICLE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("MERK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("COLOR",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("SIZE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("UNIT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PRICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ACTIVE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				table2.endHeaders();
				
				int i = 0;
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
				    Item item =(Item)iterator.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(item.getCode(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						Inventory inventory = InventoryDAO.getInstance().get(item.getId());
				    cell = new Cell(new Phrase(inventory!=null?(inventory.getItemUnit()!=null?inventory.getItemUnit().getSymbol():""):"", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(inventory.getFormatedPrice(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(item.isActive()==true?"Y":"N", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				}
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(8);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(8);
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
				ItemDAO.getInstance().closeSessionForReal();
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
	private ActionForward performStockCardOffice(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
				if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
				if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate"); 
				
				// debit : receiving & customer retur
				// kredit : vendor retur & delivery order & stok-opname
				String x = "";
				//if (form.getLong("itemMerkId")>0) x = "and b.item_merk_id = "+form.getLong("itemMerkId")+" ";
				//if (form.getLong("itemGenderId")>0) x = "and b.item_gender_id = "+form.getLong("itemGenderId")+" ";
				//if (form.getLong("itemTypeId")>0) x = "and b.item_type_id = "+form.getLong("itemTypeId")+" ";
				//if (form.getLong("itemColorId")>0) x = "and b.item_color_id = "+form.getLong("itemColorId")+" ";
				
				// beginning
				String beginningSql = "" +
						"select concat(a.number,b.item_id) as {rmi.Id}, b.name as {rmi.Name}, sum(a.quantity) as {rmi.Quantity}, a.number as {rmi.Number}, a.tdate as {rmi.StockCardDate}, b.cost_price as {rmi.Price}, concat(b.code) as {rmi.Code}, (select e.number_of_digit from organization_setup e where e.organization_id=b.organization_id) as {rmi.NumberOfDigit} from " +
						"((select e.item_id,e.quantity,f.number,f.receiving_date as tdate from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id)) union (select g.item_id,-g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id and h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date < :toDate) UNION (select e.item_id,-e.quantity,f.number,f.retur_date as tdate from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id)) UNION (select e.item_id,e.quantity,f.number,f.retur_date as tdate from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id)) UNION (select e.item_id,-e.difference as quantity, f.number,f.stock_opname_date as tdate from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id) and isnull(f.customer_id))) a "+
						"join item b on a.item_id=b.item_id " +
						"where 1=1 " + x + " " +
						"group by concat(a.number,b.item_id) " +
						"order by a.tdate ASC";
				
				List beginningList = StockCardReportDAO.getInstance().getSession().createSQLQuery(beginningSql)
					.addEntity("rmi", StockCardReport.class)
					.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("toDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.list();
				Iterator iterator = beginningList.iterator();
				double debitTotal = 0, creditTotal = 0;
				int debitQuantity = 0, creditQuantity = 0;
				while (iterator.hasNext()) {
					StockCardReport stockCardReport = (StockCardReport)iterator.next();
					if (stockCardReport.getQuantity()>0) {
						debitQuantity = debitQuantity + stockCardReport.getQuantity();
						debitTotal = debitTotal + (stockCardReport.getQuantity() * stockCardReport.getPrice());
					} else {
						creditQuantity = creditQuantity - stockCardReport.getQuantity();
						creditTotal = creditTotal - (stockCardReport.getQuantity() * stockCardReport.getPrice());
					}
				}
				request.setAttribute("debitQuantity", String.valueOf(debitQuantity));
				request.setAttribute("creditQuantity", String.valueOf(creditQuantity));
				request.setAttribute("debitTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal));
				request.setAttribute("creditTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal));
				
				// data
				String sql = "" +
						"select concat(a.number,b.item_id) as {rmi.Id}, b.name as {rmi.Name}, sum(a.quantity) as {rmi.Quantity}, a.number as {rmi.Number}, a.tdate as {rmi.StockCardDate}, b.cost_price as {rmi.Price}, concat(b.code) as {rmi.Code}, (select e.number_of_digit from organization_setup e where e.organization_id=b.organization_id) as {rmi.NumberOfDigit} from " +
						"((select e.item_id,e.quantity,f.number,f.receiving_date as tdate from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date <= :toDate and f.status<>'CANCEL' and isnull(f.location_id)) union (select g.item_id,-g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date <= :toDate) UNION (select e.item_id,-e.quantity,f.number,f.retur_date as tdate from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date <= :toDate and f.status<>'CANCEL' and isnull(f.location_id)) UNION (select e.item_id,e.quantity,f.number,f.retur_date as tdate from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date <= :toDate and f.status<>'CANCEL' and isnull(f.location_id)) UNION (select e.item_id,-e.difference as quantity, f.number,f.stock_opname_date as tdate from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date <= :toDate and f.status<>'CANCEL' and isnull(f.location_id) and isnull(f.customer_id))) a "+
						"join item b on a.item_id=b.item_id " +
						"where 1=1 " + x + " " +
						"group by concat(a.number,b.item_id) " +
						"order by a.tdate ASC";
				
				List list = StockCardReportDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmi", StockCardReport.class)
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				request.setAttribute("STOCKCARD", list);
				
				// last balance
				Iterator iterator2 = list.iterator();
				double debitTotal2 = debitTotal, creditTotal2 = creditTotal;
				int debitQuantity2 = debitQuantity, creditQuantity2 = creditQuantity;
				while (iterator2.hasNext()) {
					StockCardReport stockCardReport = (StockCardReport)iterator2.next();
					if (stockCardReport.getQuantity()>0) {
						debitQuantity2 = debitQuantity2 + stockCardReport.getQuantity();
						debitTotal2 = debitTotal2 + (stockCardReport.getQuantity() * stockCardReport.getPrice());
					} else {
						creditQuantity2 = creditQuantity2 - stockCardReport.getQuantity();
						creditTotal2 = creditTotal2 - (stockCardReport.getQuantity() * stockCardReport.getPrice());
					}
				}
				request.setAttribute("debitQuantity2", String.valueOf(debitQuantity2));
				request.setAttribute("creditQuantity2", String.valueOf(creditQuantity2));
				request.setAttribute("debitTotal2", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal2));
				request.setAttribute("creditTotal2", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal2));
				
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
	private ActionForward performStockCardOfficePdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
				OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
				if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
				if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate"); 
				
				// debit : receiving & customer retur
				// kredit : vendor retur & delivery order & stok-opname
				String x = "";
				//if (form.getLong("itemMerkId")>0) x = "and b.item_merk_id = "+form.getLong("itemMerkId")+" ";
				//if (form.getLong("itemGenderId")>0) x = "and b.item_gender_id = "+form.getLong("itemGenderId")+" ";
				//if (form.getLong("itemTypeId")>0) x = "and b.item_type_id = "+form.getLong("itemTypeId")+" ";
				//if (form.getLong("itemColorId")>0) x = "and b.item_color_id = "+form.getLong("itemColorId")+" ";
				
				// beginning
				String beginningSql = "" +
						"select concat(a.number,b.item_id) as {rmi.Id}, b.name as {rmi.Name}, sum(a.quantity) as {rmi.Quantity}, a.number as {rmi.Number}, a.tdate as {rmi.StockCardDate}, b.cost_price as {rmi.Price}, concat(b.code) as {rmi.Code}, (select e.number_of_digit from organization_setup e where e.organization_id=b.organization_id) as {rmi.NumberOfDigit} from " +
						"((select e.item_id,e.quantity,f.number,f.receiving_date as tdate from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id)) union (select g.item_id,-g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date < :toDate) UNION (select e.item_id,-e.quantity,f.number,f.retur_date as tdate from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id)) UNION (select e.item_id,e.quantity,f.number,f.retur_date as tdate from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id)) UNION (select e.item_id,-e.difference as quantity, f.number,f.stock_opname_date as tdate from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date < :toDate and f.status<>'CANCEL' and isnull(f.location_id) and isnull(f.customer_id))) a "+
						"join item b on a.item_id=b.item_id " +
						"where 1=1 " + x + " " +
						"group by concat(a.number,b.item_id) " +
						"order by a.tdate ASC";
				
				List beginningList = StockCardReportDAO.getInstance().getSession().createSQLQuery(beginningSql)
					.addEntity("rmi", StockCardReport.class)
					.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("toDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.list();
				Iterator iterator = beginningList.iterator();
				double debitTotal = 0, creditTotal = 0;
				int debitQuantity = 0, creditQuantity = 0;
				while (iterator.hasNext()) {
					StockCardReport stockCardReport = (StockCardReport)iterator.next();
					if (stockCardReport.getQuantity()>0) {
						debitQuantity = debitQuantity + stockCardReport.getQuantity();
						debitTotal = debitTotal + (stockCardReport.getQuantity() * stockCardReport.getPrice());
					} else {
						creditQuantity = creditQuantity - stockCardReport.getQuantity();
						creditTotal = creditTotal - (stockCardReport.getQuantity() * stockCardReport.getPrice());
					}
				}
				//request.setAttribute("debitQuantity", String.valueOf(debitQuantity));
				//request.setAttribute("creditQuantity", String.valueOf(creditQuantity));
				//request.setAttribute("debitTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal));
				//request.setAttribute("creditTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal));
				
				// data
				String sql = "" +
						"select concat(a.number,b.item_id) as {rmi.Id}, b.name as {rmi.Name}, sum(a.quantity) as {rmi.Quantity}, a.number as {rmi.Number}, a.tdate as {rmi.StockCardDate}, b.cost_price as {rmi.Price}, concat(b.code) as {rmi.Code}, (select e.number_of_digit from organization_setup e where e.organization_id=b.organization_id) as {rmi.NumberOfDigit} from " +
						"((select e.item_id,e.quantity,f.number,f.receiving_date as tdate from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date <= :toDate and f.status<>'CANCEL' and isnull(f.location_id)) union (select g.item_id,-g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date <= :toDate) UNION (select e.item_id,-e.quantity,f.number,f.retur_date as tdate from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date <= :toDate and f.status<>'CANCEL' and isnull(f.location_id)) UNION (select e.item_id,e.quantity,f.number,f.retur_date as tdate from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date <= :toDate and f.status<>'CANCEL' and isnull(f.location_id)) UNION (select e.item_id,-e.difference as quantity, f.number,f.stock_opname_date as tdate from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date <= :toDate and f.status<>'CANCEL' and isnull(f.location_id) and isnull(f.customer_id))) a "+
						"join item b on a.item_id=b.item_id " +
						"where 1=1 " + x + " " +
						"group by concat(a.number,b.item_id) " +
						"order by a.tdate ASC";
				
				List list = StockCardReportDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmi", StockCardReport.class)
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				//request.setAttribute("STOCKCARD", list);
				
				// last balance
				Iterator iterator2 = list.iterator();
				double debitTotal2 = debitTotal, creditTotal2 = creditTotal;
				int debitQuantity2 = debitQuantity, creditQuantity2 = creditQuantity;
				while (iterator2.hasNext()) {
					StockCardReport stockCardReport = (StockCardReport)iterator2.next();
					if (stockCardReport.getQuantity()>0) {
						debitQuantity2 = debitQuantity2 + stockCardReport.getQuantity();
						debitTotal2 = debitTotal2 + (stockCardReport.getQuantity() * stockCardReport.getPrice());
					} else {
						creditQuantity2 = creditQuantity2 - stockCardReport.getQuantity();
						creditTotal2 = creditTotal2 - (stockCardReport.getQuantity() * stockCardReport.getPrice());
					}
				}
				//request.setAttribute("debitQuantity2", String.valueOf(debitQuantity2));
				//request.setAttribute("creditQuantity2", String.valueOf(creditQuantity2));
				//request.setAttribute("debitTotal2", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal2));
				//request.setAttribute("creditTotal2", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal2));
				
				//pdf
		    // write to pdf document
				Document document = new Document(PageSize.A4.rotate(),30,20,20,20);
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
				
				Table table2 = new Table(13);
				table2.setWidth(100);
				table2.setCellsFitPage(true);
				float[] b = {3,7,10,10,11,10,11,3,8,10,3,8,10};
				table2.setWidths(b);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setCellsFitPage(true);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase("STOCK CARD OFFICE", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Date : "+form.getString("fromDate")+" - "+form.getString("toDate"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("DATE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("NUMBER",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("MERK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TYPE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("GENDER",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("COLOR",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
	
				cell = new Cell(new Phrase("DEBIT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("CREDIT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PRICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PRICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				table2.endHeaders();
				
				// beginning
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase("Beginning : ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setColspan(6);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Integer.toString(debitQuantity), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Integer.toString(creditQuantity), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				
				int i = 0;
				Iterator iterator3 = list.iterator();
				while (iterator3.hasNext()) {
				    StockCardReport stockCardReport = (StockCardReport)iterator3.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getFormatedStockCardDate(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(Integer.toString(stockCardReport.getDebitQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getFormatedPrice(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedDebitTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Integer.toString(stockCardReport.getCreditQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getFormatedPrice(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedCreditTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
				}
				
				// end
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase("Ending : ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setColspan(6);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Integer.toString(debitQuantity2), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal2), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Integer.toString(creditQuantity2), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal2), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.LEFT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(13);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(13);
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
				ItemDAO.getInstance().closeSessionForReal();
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
	private ActionForward performRecapitulationStockOffice(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    
	/*		    int start = 0;
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
				}*/
		    OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
				if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
				if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate"); 
				/*Criteria criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
				if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code")));
				if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
				criteria.setProjection(Projections.rowCount());
				total = ((Integer)criteria.list().iterator().next()).intValue();*/
				// data report
				List list = new LinkedList();
				String x = "";
				String y = "";
				if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
				    y = "order by "+form.getString("orderBy")+" ASC ";
				} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
				    y = "order by "+form.getString("orderBy")+" DESC ";
				}
				if (form.getString("code")!=null && form.getString("code").length()>0) x = "and a.code like '%"+form.getString("code")+"%' ";
				if (form.getString("name")!=null && form.getString("name").length()>0) x = "and a.name like '%"+form.getString("name")+"%' ";
				//if (form.getLong("itemMerkId")>0) x = "and a.item_merk_id = "+form.getLong("itemMerkId")+" ";
				//if (form.getLong("itemGenderId")>0) x = "and a.item_gender_id = "+form.getLong("itemGenderId")+" ";
				//if (form.getLong("itemTypeId")>0) x = "and a.item_type_id = "+form.getLong("itemTypeId")+" ";
				String sql = "" +
					"select a.item_id as {rmi.ItemId}, a.code as {rmi.Code}, a.name as {rmi.Name}, f.price as {rmi.Price}, " +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and isnull(g.customer_id) and isnull(g.location_id)), 0) + IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and isnull(i.location_id)), 0) + IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and isnull(o.location_id)), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and isnull(k.location_id)), 0) - IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and is_bon_kuning='N'), 0) - IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and isnull(q.location_id) and isnull(q.customer_id)), 0) as {rmi.BeginningQuantity}, " +
					"IFNULL((select (g.quantity * g.price * g.exchange_rate) from inventory_first_balance g where g.item_id=a.item_id and isnull(g.customer_id) and isnull(g.location_id)), 0) + IFNULL((select sum(h.quantity * h.price * h.exchange_rate) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and isnull(i.location_id)), 0) + IFNULL((select sum(n.quantity * n.price * n.exchange_rate) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and isnull(o.location_id)), 0) - IFNULL((select sum(j.quantity * j.price * j.exchange_rate) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and isnull(k.location_id)), 0) - IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and is_bon_kuning='N'), 0) - IFNULL((select sum(p.difference *p.price * p.exchange_rate) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and isnull(q.location_id) and isnull(q.customer_id)), 0)  as {rmi.BeginningTotal}, " +
					"IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and isnull(i.location_id)), 0) as {rmi.ReceivingQuantity}, " +
					"IFNULL((select sum(h.quantity * h.price * h.exchange_rate) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and isnull(i.location_id)), 0) as {rmi.ReceivingTotal}, " +
					"IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and isnull(k.location_id)), 0) as {rmi.ReturToVendorQuantity}, " +
					"IFNULL((select sum(j.quantity * j.price * j.exchange_rate) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and isnull(k.location_id)), 0) as {rmi.ReturToVendorTotal}, " +
					"IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and is_bon_kuning='N'), 0) as {rmi.DeliveryOrderQuantity}, " +
					"IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and is_bon_kuning='N'), 0) as {rmi.DeliveryOrderTotal}, " +
					"IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and isnull(o.location_id)), 0) as {rmi.CustomerReturQuantity}, " +
					"IFNULL((select sum(n.quantity * n.price * n.exchange_rate) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and isnull(o.location_id)), 0) as {rmi.CustomerReturTotal}, " +
					"IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and isnull(q.location_id) and isnull(q.customer_id)), 0) as {rmi.StockOpnameQuantity}, " +
					"IFNULL((select sum(p.difference *p.price * p.exchange_rate) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and isnull(q.location_id) and isnull(q.customer_id)), 0) as {rmi.StockOpnameTotal}, " +
					
					"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {rmi.NumberOfDigit} " +
					"from item a join item_price f on f.item_id=a.item_id " +
					"where " +
					"a.organization_id = :organizationId " + x +
					"and (" +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and isnull(g.customer_id) and isnull(g.location_id)), 0) + IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and isnull(i.location_id)), 0) + IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and isnull(o.location_id)), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and isnull(k.location_id)), 0) - IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and is_bon_kuning='N'), 0) - IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and isnull(q.location_id) and isnull(q.customer_id)), 0)<>0 OR " +
					"IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and isnull(i.location_id)), 0)<>0 OR " +
					"IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and isnull(k.location_id)), 0)<>0 OR " +
					"IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and isnull(q.location_id) and isnull(q.customer_id)), 0)<>0 OR " +
					"IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and isnull(o.location_id)), 0)<>0 OR " +
					"IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and is_bon_kuning='N'), 0)<>0 OR " +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and isnull(g.customer_id) and isnull(g.location_id)), 0)<>0 " + 
					") and f.is_default='Y' " +
					"group by a.item_id " + y;
				list = RekapMutationStockReportDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmi", RekapMutationStockReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					//.setInteger("limit", count)
					//.setInteger("offset", start)
					.list();
				request.setAttribute("REKAPMUTATIONSTOCK", list);
				/*String pager = Pager.generatePager(start, count, total);
				String pagerItem = Pager.generatePagerItem(start, count, total);
				request.setAttribute("PAGER",pager);
				request.setAttribute("PAGERITEM",pagerItem);*/
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
	private ActionForward performRecapitulationStockOfficePdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
				Criteria criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
				if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code")));
				if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
				criteria.setProjection(Projections.rowCount());
				// data report
				List list = new LinkedList();
				String x = "";
				String y = "";
				if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
				    y = "order by "+form.getString("orderBy")+" ASC ";
				} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
				    y = "order by "+form.getString("orderBy")+" DESC ";
				}
				if (form.getString("code")!=null && form.getString("code").length()>0) x = "and a.code like '%"+form.getString("code")+"%' ";
				if (form.getString("name")!=null && form.getString("name").length()>0) x = "and a.name like '%"+form.getString("name")+"%' ";
				//if (form.getLong("itemMerkId")>0) x = "and a.item_merk_id = "+form.getLong("itemMerkId")+" ";
				//if (form.getLong("itemGenderId")>0) x = "and a.item_gender_id = "+form.getLong("itemGenderId")+" ";
				//if (form.getLong("itemTypeId")>0) x = "and a.item_type_id = "+form.getLong("itemTypeId")+" ";
				String sql = "" +
					"select a.item_id as {rmi.ItemId}, a.code as {rmi.Code}, a.name as {rmi.Name}, f.price as {rmi.Price}, " +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and isnull(g.customer_id) and isnull(g.location_id)), 0) + IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and isnull(i.location_id)), 0) + IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and isnull(o.location_id)), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and isnull(k.location_id)), 0) - IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and is_bon_kuning='N'), 0) - IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and isnull(q.location_id) and isnull(q.customer_id)), 0) as {rmi.BeginningQuantity}, " +
					"IFNULL((select (g.quantity * g.price * g.exchange_rate) from inventory_first_balance g where g.item_id=a.item_id and isnull(g.customer_id) and isnull(g.location_id)), 0) + IFNULL((select sum(h.quantity * h.price * h.exchange_rate) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and isnull(i.location_id)), 0) + IFNULL((select sum(n.quantity * n.price * n.exchange_rate) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and isnull(o.location_id)), 0) - IFNULL((select sum(j.quantity * j.price * j.exchange_rate) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and isnull(k.location_id)), 0) - IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and is_bon_kuning='N'), 0) - IFNULL((select sum(p.difference *p.price * p.exchange_rate) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and isnull(q.location_id) and isnull(q.customer_id)), 0)  as {rmi.BeginningTotal}, " +
					"IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and isnull(i.location_id)), 0) as {rmi.ReceivingQuantity}, " +
					"IFNULL((select sum(h.quantity * h.price * h.exchange_rate) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and isnull(i.location_id)), 0) as {rmi.ReceivingTotal}, " +
					"IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and isnull(k.location_id)), 0) as {rmi.ReturToVendorQuantity}, " +
					"IFNULL((select sum(j.quantity * j.price * j.exchange_rate) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and isnull(k.location_id)), 0) as {rmi.ReturToVendorTotal}, " +
					"IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and is_bon_kuning='N'), 0) as {rmi.DeliveryOrderQuantity}, " +
					"IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and is_bon_kuning='N'), 0) as {rmi.DeliveryOrderTotal}, " +
					"IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and isnull(o.location_id)), 0) as {rmi.CustomerReturQuantity}, " +
					"IFNULL((select sum(n.quantity * n.price * n.exchange_rate) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and isnull(o.location_id)), 0) as {rmi.CustomerReturTotal}, " +
					"IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and isnull(q.location_id) and isnull(q.customer_id)), 0) as {rmi.StockOpnameQuantity}, " +
					"IFNULL((select sum(p.difference *p.price * p.exchange_rate) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and isnull(q.location_id) and isnull(q.customer_id)), 0) as {rmi.StockOpnameTotal}, " +
					
					"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {rmi.NumberOfDigit} " +
					"from item a join item_price f on f.item_id=a.item_id " +
					"where " +
					"a.organization_id = :organizationId " + x +
					"and (" +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and isnull(g.customer_id) and isnull(g.location_id)), 0) + IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and isnull(i.location_id)), 0) + IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and isnull(o.location_id)), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and isnull(k.location_id)), 0) - IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and is_bon_kuning='N'), 0) - IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and isnull(q.location_id) and isnull(q.customer_id)), 0)<>0 OR " +
					"IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and isnull(i.location_id)), 0)<>0 OR " +
					"IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and isnull(k.location_id)), 0)<>0 OR " +
					"IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and isnull(q.location_id) and isnull(q.customer_id)), 0)<>0 OR " +
					"IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and isnull(o.location_id)), 0)<>0 OR " +
					"IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and is_bon_kuning='N'), 0)<>0 OR " +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and isnull(g.customer_id) and isnull(g.location_id)), 0)<>0 " + 
					") and f.is_default='Y' " +
					"group by a.item_id " + y;
				OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
				//.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
				list = RekapMutationStockReportDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmi", RekapMutationStockReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				
				//pdf
		    // write to pdf document
				Document document = new Document(PageSize.A4.rotate(),30,20,20,20);
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
				
				Table table2 = new Table(20);
				table2.setWidth(100);
				table2.setCellsFitPage(true);
				float[] b = {3,7,5,4,4,3,7,7,3,7,3,7,3,7,3,7,3,7,3,7};
				table2.setWidths(b);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setCellsFitPage(true);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase("RECAPITULATION STOCK OFFICE", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Date : "+form.getString("fromDate")+" - "+form.getString("toDate"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ARTICLE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("MERK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("COLOR",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("SIZE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("BEGINNING",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("RECEIVING",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PURCHASE RETURN",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("INVOICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("SALES RETURN",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ADJUSTMENT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ENDING BALANCE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PRICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				table2.endHeaders();
				
				int i = 0;
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
				    RekapMutationStockReport stockCardReport = (RekapMutationStockReport)iterator.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getBeginningQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getFormatedPrice(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedBeginningTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getReceivingQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedReceivingTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getReturToVendorQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedReturToVendorTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getDeliveryOrderQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedDeliveryOrderTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getCustomerReturQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedCustomerReturTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getStockOpnameQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedStockOpnameTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getEndingQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedEndingTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
				}
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(20);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(20);
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
				ItemDAO.getInstance().closeSessionForReal();
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
	private ActionForward performStockCardCounter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
				if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate"); 
		    
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Store", Boolean.TRUE))
					.addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
				
				// debit : receiving & customer retur
				// kredit : vendor retur & delivery order & stok-opname
				String x = "";
				//if (form.getLong("itemMerkId")>0) x = "and b.item_merk_id = "+form.getLong("itemMerkId")+" ";
				//if (form.getLong("itemGenderId")>0) x = "and b.item_gender_id = "+form.getLong("itemGenderId")+" ";
				//if (form.getLong("itemTypeId")>0) x = "and b.item_type_id = "+form.getLong("itemTypeId")+" ";
				//if (form.getLong("itemColorId")>0) x = "and b.item_color_id = "+form.getLong("itemColorId")+" ";
				
				// beginning
				String beginningSql = "" +
						"select concat(a.number,b.item_id) as {rmi.Id}, b.name as {rmi.Name}, sum(a.quantity) as {rmi.Quantity}, a.number as {rmi.Number}, a.tdate as {rmi.StockCardDate}, b.cost_price as {rmi.Price}, concat(b.code) as {rmi.Code}, (select e.number_of_digit from organization_setup e where e.organization_id=b.organization_id) as {rmi.NumberOfDigit} from " +
						"((select e.item_id,e.quantity,f.number,f.receiving_date as tdate from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date < :toDate and f.status<>'CANCEL' and f.location_id="+form.getLong("locationId")+") UNION (select g.item_id,g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date < :toDate and (h.customer_id="+form.getLong("customerId")+" OR h.location_id="+form.getLong("locationId")+")) UNION (select g.item_id,-g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='Y' and h.delivery_date >= :fromDate and h.delivery_date < :toDate and (h.customer_id="+form.getLong("customerId")+" OR h.location_id="+form.getLong("locationId")+")) UNION (select e.item_id,-e.quantity,f.number,f.retur_date as tdate from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and f.location_id="+form.getLong("locationId")+") UNION (select e.item_id,e.quantity,f.number,f.retur_date as tdate from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and f.location_id="+form.getLong("locationId")+") UNION (select e.item_id,-e.difference as quantity, f.number,f.stock_opname_date as tdate from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date < :toDate and f.status<>'CANCEL' and (f.location_id="+form.getLong("locationId")+" OR f.customer_id="+form.getLong("customerId")+"))) a "+
						"join item b on a.item_id=b.item_id " +
						"where 1=1 " + x + " " +
						"group by concat(a.number,b.item_id) " +
						"order by a.tdate ASC";
				
				List beginningList = StockCardReportDAO.getInstance().getSession().createSQLQuery(beginningSql)
					.addEntity("rmi", StockCardReport.class)
					.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("toDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.list();
				Iterator iterator = beginningList.iterator();
				double debitTotal = 0, creditTotal = 0;
				int debitQuantity = 0, creditQuantity = 0;
				while (iterator.hasNext()) {
					StockCardReport stockCardReport = (StockCardReport)iterator.next();
					if (stockCardReport.getQuantity()>0) {
						debitQuantity = debitQuantity + stockCardReport.getQuantity();
						debitTotal = debitTotal + (stockCardReport.getQuantity() * stockCardReport.getPrice());
					} else {
						creditQuantity = creditQuantity - stockCardReport.getQuantity();
						creditTotal = creditTotal - (stockCardReport.getQuantity() * stockCardReport.getPrice());
					}
				}
				request.setAttribute("debitQuantity", String.valueOf(debitQuantity));
				request.setAttribute("creditQuantity", String.valueOf(creditQuantity));
				request.setAttribute("debitTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal));
				request.setAttribute("creditTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal));
				
				// data
				String sql = "" +
						"select concat(a.number,b.item_id) as {rmi.Id}, b.name as {rmi.Name}, sum(a.quantity) as {rmi.Quantity}, a.number as {rmi.Number}, a.tdate as {rmi.StockCardDate}, b.cost_price as {rmi.Price}, concat(b.code) as {rmi.Code},  (select e.number_of_digit from organization_setup e where e.organization_id=b.organization_id) as {rmi.NumberOfDigit} from " +
						"((select e.item_id,e.quantity,f.number,f.receiving_date as tdate from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date <= :toDate and f.status<>'CANCEL' and f.location_id="+form.getLong("locationId")+") UNION (select g.item_id,g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date <= :toDate and (h.customer_id="+form.getLong("customerId")+" OR h.location_id="+form.getLong("locationId")+")) UNION (select g.item_id,-g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='Y' and h.delivery_date >= :fromDate and h.delivery_date <= :toDate and (h.customer_id="+form.getLong("customerId")+" OR h.location_id="+form.getLong("locationId")+")) UNION (select e.item_id,-e.quantity,f.number,f.retur_date as tdate from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date <= :toDate and f.status<>'CANCEL' and f.location_id="+form.getLong("locationId")+") UNION (select e.item_id,e.quantity,f.number,f.retur_date as tdate from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date <= :toDate and f.status<>'CANCEL' and f.location_id="+form.getLong("locationId")+") UNION (select e.item_id,-e.difference as quantity, f.number,f.stock_opname_date as tdate from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date <= :toDate and f.status<>'CANCEL' and (f.location_id="+form.getLong("locationId")+" OR f.customer_id="+form.getLong("customerId")+"))) a "+
						"join item b on a.item_id=b.item_id " +
						"where 1=1 " + x + " " +
						"group by concat(a.number,b.item_id) " +
						"order by a.tdate ASC";
				
				List list = StockCardReportDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmi", StockCardReport.class)
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				request.setAttribute("STOCKCARD", list);
				
				// last balance
				Iterator iterator2 = list.iterator();
				double debitTotal2 = debitTotal, creditTotal2 = creditTotal;
				int debitQuantity2 = debitQuantity, creditQuantity2 = creditQuantity;
				while (iterator2.hasNext()) {
					StockCardReport stockCardReport = (StockCardReport)iterator2.next();
					if (stockCardReport.getQuantity()>0) {
						debitQuantity2 = debitQuantity2 + stockCardReport.getQuantity();
						debitTotal2 = debitTotal2 + (stockCardReport.getQuantity() * stockCardReport.getPrice());
					} else {
						creditQuantity2 = creditQuantity2 - stockCardReport.getQuantity();
						creditTotal2 = creditTotal2 - (stockCardReport.getQuantity() * stockCardReport.getPrice());
					}
				}
				request.setAttribute("debitQuantity2", String.valueOf(debitQuantity2));
				request.setAttribute("creditQuantity2", String.valueOf(creditQuantity2));
				request.setAttribute("debitTotal2", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal2));
				request.setAttribute("creditTotal2", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal2));
				
				
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
	private ActionForward performStockCardCounterPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
				if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate"); 
		    //List itemMerkLst = ItemMerkDAO.getInstance().findAll(Order.asc("Name"));
		    //request.setAttribute("itemMerkLst", itemMerkLst);
		    //List itemTypeLst = ItemTypeDAO.getInstance().findAll(Order.asc("Name"));
		    //request.setAttribute("itemTypeLst", itemTypeLst);
		    //List itemGenderLst = ItemGenderDAO.getInstance().findAll(Order.asc("Name"));
		    //request.setAttribute("itemGenderLst", itemGenderLst);
				
				/*List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Store", Boolean.TRUE))
					.addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);*/
				
				// debit : receiving & customer retur
				// kredit : vendor retur & delivery order & stok-opname
				String x = "";
				//if (form.getLong("itemMerkId")>0) x = "and b.item_merk_id = "+form.getLong("itemMerkId")+" ";
				//if (form.getLong("itemGenderId")>0) x = "and b.item_gender_id = "+form.getLong("itemGenderId")+" ";
				//if (form.getLong("itemTypeId")>0) x = "and b.item_type_id = "+form.getLong("itemTypeId")+" ";
				//if (form.getLong("itemColorId")>0) x = "and b.item_color_id = "+form.getLong("itemColorId")+" ";
				
				String beginningSql = "" +
				"select concat(a.number,b.item_id) as {rmi.Id}, b.name as {rmi.Name}, sum(a.quantity) as {rmi.Quantity}, a.number as {rmi.Number}, a.tdate as {rmi.StockCardDate}, b.cost_price as {rmi.Price}, concat(b.code) as {rmi.Code}, (select e.number_of_digit from organization_setup e where e.organization_id=b.organization_id) as {rmi.NumberOfDigit} from " +
				"((select e.item_id,e.quantity,f.number,f.receiving_date as tdate from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date < :toDate and f.status<>'CANCEL' and f.location_id="+form.getLong("locationId")+") UNION (select g.item_id,g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date < :toDate and (h.customer_id="+form.getLong("customerId")+" OR h.location_id="+form.getLong("locationId")+")) UNION (select g.item_id,-g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='Y' and h.delivery_date >= :fromDate and h.delivery_date < :toDate and (h.customer_id="+form.getLong("customerId")+" OR h.location_id="+form.getLong("locationId")+")) UNION (select e.item_id,-e.quantity,f.number,f.retur_date as tdate from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and f.location_id="+form.getLong("locationId")+") UNION (select e.item_id,e.quantity,f.number,f.retur_date as tdate from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and f.location_id="+form.getLong("locationId")+") UNION (select e.item_id,-e.difference as quantity, f.number,f.stock_opname_date as tdate from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date < :toDate and f.status<>'CANCEL' and (f.location_id="+form.getLong("locationId")+" OR f.customer_id="+form.getLong("customerId")+"))) a "+
				"join item b on a.item_id=b.item_id " +
				"where 1=1 " + x + " " +
				"group by concat(a.number,b.item_id) " +
				"order by a.tdate ASC";
		
		List beginningList = StockCardReportDAO.getInstance().getSession().createSQLQuery(beginningSql)
			.addEntity("rmi", StockCardReport.class)
			.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
			.setDate("toDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
			.list();
		Iterator iterator = beginningList.iterator();
		double debitTotal = 0, creditTotal = 0;
		int debitQuantity = 0, creditQuantity = 0;
		while (iterator.hasNext()) {
			StockCardReport stockCardReport = (StockCardReport)iterator.next();
			if (stockCardReport.getQuantity()>0) {
				debitQuantity = debitQuantity + stockCardReport.getQuantity();
				debitTotal = debitTotal + (stockCardReport.getQuantity() * stockCardReport.getPrice());
			} else {
				creditQuantity = creditQuantity - stockCardReport.getQuantity();
				creditTotal = creditTotal - (stockCardReport.getQuantity() * stockCardReport.getPrice());
			}
		}
		request.setAttribute("debitQuantity", String.valueOf(debitQuantity));
		request.setAttribute("creditQuantity", String.valueOf(creditQuantity));
		request.setAttribute("debitTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal));
		request.setAttribute("creditTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal));
		
		// data
		String sql = "" +
				"select concat(a.number,b.item_id) as {rmi.Id}, b.name as {rmi.Name}, sum(a.quantity) as {rmi.Quantity}, a.number as {rmi.Number}, a.tdate as {rmi.StockCardDate}, b.cost_price as {rmi.Price}, concat(b.code) as {rmi.Code},  (select e.number_of_digit from organization_setup e where e.organization_id=b.organization_id) as {rmi.NumberOfDigit} from " +
				"((select e.item_id,e.quantity,f.number,f.receiving_date as tdate from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date <= :toDate and f.status<>'CANCEL' and f.location_id="+form.getLong("locationId")+") UNION (select g.item_id,g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date <= :toDate and (h.customer_id="+form.getLong("customerId")+" OR h.location_id="+form.getLong("locationId")+")) UNION (select g.item_id,-g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='Y' and h.delivery_date >= :fromDate and h.delivery_date <= :toDate and (h.customer_id="+form.getLong("customerId")+" OR h.location_id="+form.getLong("locationId")+")) UNION (select e.item_id,-e.quantity,f.number,f.retur_date as tdate from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date <= :toDate and f.status<>'CANCEL' and f.location_id="+form.getLong("locationId")+") UNION (select e.item_id,e.quantity,f.number,f.retur_date as tdate from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date <= :toDate and f.status<>'CANCEL' and f.location_id="+form.getLong("locationId")+") UNION (select e.item_id,-e.difference as quantity, f.number,f.stock_opname_date as tdate from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date <= :toDate and f.status<>'CANCEL' and (f.location_id="+form.getLong("locationId")+" OR f.customer_id="+form.getLong("customerId")+"))) a "+
				"join item b on a.item_id=b.item_id " +
				"where 1=1 " + x + " " +
				"group by concat(a.number,b.item_id) " +
				"order by a.tdate ASC";
				
				List list = StockCardReportDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmi", StockCardReport.class)
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				//request.setAttribute("STOCKCARD", list);
				
				// last balance
				Iterator iterator2 = list.iterator();
				double debitTotal2 = debitTotal, creditTotal2 = creditTotal;
				int debitQuantity2 = debitQuantity, creditQuantity2 = creditQuantity;
				while (iterator2.hasNext()) {
					StockCardReport stockCardReport = (StockCardReport)iterator2.next();
					if (stockCardReport.getQuantity()>0) {
						debitQuantity2 = debitQuantity2 + stockCardReport.getQuantity();
						debitTotal2 = debitTotal2 + (stockCardReport.getQuantity() * stockCardReport.getPrice());
					} else {
						creditQuantity2 = creditQuantity2 - stockCardReport.getQuantity();
						creditTotal2 = creditTotal2 - (stockCardReport.getQuantity() * stockCardReport.getPrice());
					}
				}
				//request.setAttribute("debitQuantity2", String.valueOf(debitQuantity2));
				//request.setAttribute("creditQuantity2", String.valueOf(creditQuantity2));
				//request.setAttribute("debitTotal2", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal2));
				//request.setAttribute("creditTotal2", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal2));
				
				//pdf
		    // write to pdf document
				Document document = new Document(PageSize.A4.rotate(),30,20,20,20);
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
				
				Table table2 = new Table(13);
				table2.setWidth(100);
				table2.setCellsFitPage(true);
				float[] b = {3,7,10,10,11,10,11,3,8,10,3,8,10};
				table2.setWidths(b);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setCellsFitPage(true);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase("STOCK CARD COUNTER", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
				Customers customers  = CustomersDAO.getInstance().get(form.getLong("customerId"));
				if (location!=null) {
					cell = new Cell(new Phrase("Location : "+location.getName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setColspan(13);
					table2.addCell(cell);
				}
				if (customers!=null) {
					cell = new Cell(new Phrase("Store : "+customers.getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setColspan(13);
					table2.addCell(cell);
				}
				cell = new Cell(new Phrase("Date : "+form.getString("fromDate")+" - "+form.getString("toDate"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("DATE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("NUMBER",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("MERK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TYPE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("GENDER",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("COLOR",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
	
				cell = new Cell(new Phrase("DEBIT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("CREDIT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PRICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PRICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				table2.endHeaders();
				
				// beginning
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase("Beginning : ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setColspan(6);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Integer.toString(debitQuantity), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Integer.toString(creditQuantity), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				
				int i = 0;
				Iterator iterator3 = list.iterator();
				while (iterator3.hasNext()) {
				    StockCardReport stockCardReport = (StockCardReport)iterator3.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getFormatedStockCardDate(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(Integer.toString(stockCardReport.getDebitQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getFormatedPrice(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedDebitTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Integer.toString(stockCardReport.getCreditQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getFormatedPrice(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedCreditTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
				}
				
				// end
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase("Ending : ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setColspan(6);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Integer.toString(debitQuantity2), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal2), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Integer.toString(creditQuantity2), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal2), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.LEFT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(13);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(13);
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
				ItemDAO.getInstance().closeSessionForReal();
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
	private ActionForward performRecapitulationStockCounter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    
	/*		    int start = 0;
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
				}*/
				
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Store", Boolean.TRUE))
					.addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
				
				if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
				if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate"); 
	/*				Criteria criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
				if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code")));
				if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
				criteria.setProjection(Projections.rowCount());
				total = ((Integer)criteria.list().iterator().next()).intValue();*/
				// data report
				List list = new LinkedList();
				String x = "";
				String y = "";
				if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
				    y = "order by "+form.getString("orderBy")+" ASC ";
				} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
				    y = "order by "+form.getString("orderBy")+" DESC ";
				}
				if (form.getString("code")!=null && form.getString("code").length()>0) x = "and a.code like '%"+form.getString("code")+"%' ";
				if (form.getString("name")!=null && form.getString("name").length()>0) x = "and a.name like '%"+form.getString("name")+"%' ";
				//if (form.getLong("itemMerkId")>0) x = "and a.item_merk_id = "+form.getLong("itemMerkId")+" ";
				//if (form.getLong("itemGenderId")>0) x = "and a.item_gender_id = "+form.getLong("itemGenderId")+" ";
				//if (form.getLong("itemTypeId")>0) x = "and a.item_type_id = "+form.getLong("itemTypeId")+" ";
				String sql = "" +
					"select a.item_id as {rmi.ItemId}, a.code as {rmi.Code}, a.name as {rmi.Name}, f.price as {rmi.Price}, " +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and (g.customer_id="+form.getLong("customerId")+" OR g.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and (i.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and o.location_id="+form.getLong("locationId")+"), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and k.location_id="+form.getLong("locationId")+"), 0) - IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) - IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and (q.customer_id="+form.getLong("customerId")+" OR q.location_id="+form.getLong("locationId")+")), 0) as {rmi.BeginningQuantity}, " +
					"IFNULL((select (g.quantity * g.price * g.exchange_rate) from inventory_first_balance g where g.item_id=a.item_id and (g.customer_id="+form.getLong("customerId")+" OR g.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(h.quantity * h.price * h.exchange_rate) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and i.location_id="+form.getLong("locationId")+"), 0) + IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(n.quantity * n.price * n.exchange_rate) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and o.location_id="+form.getLong("locationId")+"), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and k.location_id="+form.getLong("locationId")+"), 0) - IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) - IFNULL((select sum(p.difference *p.price * p.exchange_rate) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id  and (q.customer_id="+form.getLong("customerId")+" OR q.location_id="+form.getLong("locationId")+")), 0)  as {rmi.BeginningTotal}, " +
					
					"IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and (i.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) as {rmi.ReceivingQuantity}, " +
					"IFNULL((select sum(h.quantity * h.price * h.exchange_rate) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and i.location_id="+form.getLong("locationId")+"), 0) + IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) as {rmi.ReceivingTotal}, " +
					"IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and k.location_id="+form.getLong("locationId")+"), 0) as {rmi.ReturToVendorQuantity}, " +
					"IFNULL((select sum(j.quantity * j.price * j.exchange_rate) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and k.location_id="+form.getLong("locationId")+"), 0) as {rmi.ReturToVendorTotal}, " +
					"IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) as {rmi.DeliveryOrderQuantity}, " +
					"IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) as {rmi.DeliveryOrderTotal}, " +
					"IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and o.location_id="+form.getLong("locationId")+"), 0) as {rmi.CustomerReturQuantity}, " +
					"IFNULL((select sum(n.quantity * n.price * n.exchange_rate) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and o.location_id="+form.getLong("locationId")+"), 0) as {rmi.CustomerReturTotal}, " +
					"IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and (q.customer_id="+form.getLong("customerId")+" OR q.location_id="+form.getLong("locationId")+")), 0) as {rmi.StockOpnameQuantity}, " +
					"IFNULL((select sum(p.difference *p.price * p.exchange_rate) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id  and (q.customer_id="+form.getLong("customerId")+" OR q.location_id="+form.getLong("locationId")+")), 0) as {rmi.StockOpnameTotal}, " +
					
					"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {rmi.NumberOfDigit} " +
					"from item a join item_price f on f.item_id=a.item_id " +
					"where " +
					"a.organization_id = :organizationId " + x +
					"and f.is_default='Y' " +
					"and (" +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and (g.customer_id="+form.getLong("customerId")+" OR g.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and (i.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and o.location_id="+form.getLong("locationId")+"), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and k.location_id="+form.getLong("locationId")+"), 0) - IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) - IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and (q.customer_id="+form.getLong("customerId")+" OR q.location_id="+form.getLong("locationId")+")), 0) <> 0 OR " +
					"IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and (i.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0)<>0 OR " +
					"IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and k.location_id="+form.getLong("locationId")+"), 0)<>0 OR " +
					"IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0)<>0 OR " +
					"IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and o.location_id="+form.getLong("locationId")+"), 0)<>0 OR " +
					"IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and (q.customer_id="+form.getLong("customerId")+" OR q.location_id="+form.getLong("locationId")+")), 0)<>0 OR " +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and (g.customer_id="+form.getLong("customerId")+" OR g.location_id="+form.getLong("locationId")+")), 0)<>0 " +
					")" +
					"group by a.item_id " + y;
				OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
				list = RekapMutationStockReportDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmi", RekapMutationStockReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					//.setInteger("limit", count)
					//.setInteger("offset", start)
					.list();
				request.setAttribute("REKAPMUTATIONSTOCK", list);
	/*				String pager = Pager.generatePager(start, count, total);
				String pagerItem = Pager.generatePagerItem(start, count, total);
				request.setAttribute("PAGER",pager);
				request.setAttribute("PAGERITEM",pagerItem);*/
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
	private ActionForward performRecapitulationStockCounterPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
				Criteria criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
				if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code")));
				if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
				criteria.setProjection(Projections.rowCount());
				// data report
				List list = new LinkedList();
				String x = "";
				String y = "";
				if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
				    y = "order by "+form.getString("orderBy")+" ASC ";
				} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
				    y = "order by "+form.getString("orderBy")+" DESC ";
				}
				if (form.getString("code")!=null && form.getString("code").length()>0) x = "and a.code like '%"+form.getString("code")+"%' ";
				if (form.getString("name")!=null && form.getString("name").length()>0) x = "and a.name like '%"+form.getString("name")+"%' ";
				//if (form.getLong("itemMerkId")>0) x = "and a.item_merk_id = "+form.getLong("itemMerkId")+" ";
				//if (form.getLong("itemGenderId")>0) x = "and a.item_gender_id = "+form.getLong("itemGenderId")+" ";
				//if (form.getLong("itemTypeId")>0) x = "and a.item_type_id = "+form.getLong("itemTypeId")+" ";
				String sql = "" +
					"select a.item_id as {rmi.ItemId}, a.code as {rmi.Code}, a.name as {rmi.Name}, f.price as {rmi.Price}, " +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and (g.customer_id="+form.getLong("customerId")+" OR g.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and (i.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and o.location_id="+form.getLong("locationId")+"), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and k.location_id="+form.getLong("locationId")+"), 0) - IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) - IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and (q.customer_id="+form.getLong("customerId")+" OR q.location_id="+form.getLong("locationId")+")), 0) as {rmi.BeginningQuantity}, " +
					"IFNULL((select (g.quantity * g.price * g.exchange_rate) from inventory_first_balance g where g.item_id=a.item_id and (g.customer_id="+form.getLong("customerId")+" OR g.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(h.quantity * h.price * h.exchange_rate) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and i.location_id="+form.getLong("locationId")+"), 0) + IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(n.quantity * n.price * n.exchange_rate) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and o.location_id="+form.getLong("locationId")+"), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and k.location_id="+form.getLong("locationId")+"), 0) - IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) - IFNULL((select sum(p.difference *p.price * p.exchange_rate) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id  and (q.customer_id="+form.getLong("customerId")+" OR q.location_id="+form.getLong("locationId")+")), 0)  as {rmi.BeginningTotal}, " +
					
					"IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and (i.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) as {rmi.ReceivingQuantity}, " +
					"IFNULL((select sum(h.quantity * h.price * h.exchange_rate) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and i.location_id="+form.getLong("locationId")+"), 0) + IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) as {rmi.ReceivingTotal}, " +
					"IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and k.location_id="+form.getLong("locationId")+"), 0) as {rmi.ReturToVendorQuantity}, " +
					"IFNULL((select sum(j.quantity * j.price * j.exchange_rate) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and k.location_id="+form.getLong("locationId")+"), 0) as {rmi.ReturToVendorTotal}, " +
					"IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) as {rmi.DeliveryOrderQuantity}, " +
					"IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) as {rmi.DeliveryOrderTotal}, " +
					"IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and o.location_id="+form.getLong("locationId")+"), 0) as {rmi.CustomerReturQuantity}, " +
					"IFNULL((select sum(n.quantity * n.price * n.exchange_rate) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and o.location_id="+form.getLong("locationId")+"), 0) as {rmi.CustomerReturTotal}, " +
					"IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and (q.customer_id="+form.getLong("customerId")+" OR q.location_id="+form.getLong("locationId")+")), 0) as {rmi.StockOpnameQuantity}, " +
					"IFNULL((select sum(p.difference *p.price * p.exchange_rate) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id  and (q.customer_id="+form.getLong("customerId")+" OR q.location_id="+form.getLong("locationId")+")), 0) as {rmi.StockOpnameTotal}, " +
					
					"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {rmi.NumberOfDigit} " +
					"from item a join item_price f on f.item_id=a.item_id " +
					"where " +
					"a.organization_id = :organizationId " + x +
					"and f.is_default='Y' " +
					"and (" +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and (g.customer_id="+form.getLong("customerId")+" OR g.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and (i.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and o.location_id="+form.getLong("locationId")+"), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and k.location_id="+form.getLong("locationId")+"), 0) - IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0) - IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and (q.customer_id="+form.getLong("customerId")+" OR q.location_id="+form.getLong("locationId")+")), 0) <> 0 OR " +
					"IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and (i.location_id="+form.getLong("locationId")+")), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0)<>0 OR " +
					"IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and k.location_id="+form.getLong("locationId")+"), 0)<>0 OR " +
					"IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (m.customer_id="+form.getLong("customerId")+" OR m.location_id="+form.getLong("locationId")+")), 0)<>0 OR " +
					"IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and o.location_id="+form.getLong("locationId")+"), 0)<>0 OR " +
					"IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and (q.customer_id="+form.getLong("customerId")+" OR q.location_id="+form.getLong("locationId")+")), 0)<>0 OR " +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and (g.customer_id="+form.getLong("customerId")+" OR g.location_id="+form.getLong("locationId")+")), 0)<>0 " +
					")" +
					"group by a.item_id " + y;
				OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
				//.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
				list = RekapMutationStockReportDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmi", RekapMutationStockReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				
				//pdf
		    // write to pdf document
				Document document = new Document(PageSize.A4.rotate(),30,20,20,20);
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
				
				Table table2 = new Table(20);
				table2.setWidth(100);
				table2.setCellsFitPage(true);
				float[] b = {3,7,5,4,4,3,7,7,3,7,3,7,3,7,3,7,3,7,3,7};
				table2.setWidths(b);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setCellsFitPage(true);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase("RECAPITULATION STOCK OFFICE", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ARTICLE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("MERK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("COLOR",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("SIZE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("BEGINNING",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("RECEIVING",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PURCHASE RETURN",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("INVOICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("SALES RETURN",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ADJUSTMENT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ENDING BALANCE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PRICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				table2.endHeaders();
				
				int i = 0;
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
				    RekapMutationStockReport stockCardReport = (RekapMutationStockReport)iterator.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getBeginningQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getFormatedPrice(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedBeginningTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getReceivingQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedReceivingTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getReturToVendorQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedReturToVendorTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getDeliveryOrderQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedDeliveryOrderTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getCustomerReturQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedCustomerReturTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getStockOpnameQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedStockOpnameTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getEndingQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedEndingTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
				}
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(20);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(20);
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
				ItemDAO.getInstance().closeSessionForReal();
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
	private ActionForward performStockCardGlobalCounter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
			ItemForm form = (ItemForm) actionForm;
			HttpSession httpSession = request.getSession();
			Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
			try {
				
			    OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
					if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate"); 
			    
					
					List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.add(Restrictions.eq("Store", Boolean.TRUE))
						.addOrder(Order.asc("Company")).list();
					request.setAttribute("customerLst", customerLst);
					List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
						.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
						.addOrder(Order.asc("Name")).list();
					request.setAttribute("locationLst", locationLst);
					
					// debit : receiving & customer retur
					// kredit : vendor retur & delivery order & stok-opname
					String x = "";
					//if (form.getLong("itemMerkId")>0) x = "and b.item_merk_id = "+form.getLong("itemMerkId")+" ";
					//if (form.getLong("itemGenderId")>0) x = "and b.item_gender_id = "+form.getLong("itemGenderId")+" ";
					//if (form.getLong("itemTypeId")>0) x = "and b.item_type_id = "+form.getLong("itemTypeId")+" ";
					//if (form.getLong("itemColorId")>0) x = "and b.item_color_id = "+form.getLong("itemColorId")+" ";
					
					// beginning
					String beginningSql = "" +
							"select concat(a.number,b.item_id) as {rmi.Id}, b.name as {rmi.Name}, sum(a.quantity) as {rmi.Quantity}, a.number as {rmi.Number}, a.tdate as {rmi.StockCardDate}, b.cost_price as {rmi.Price}, concat(b.code) as {rmi.Code}, (select e.number_of_digit from organization_setup e where e.organization_id=b.organization_id) as {rmi.NumberOfDigit} from " +
							"((select e.item_id,e.quantity,f.number,f.receiving_date as tdate from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date < :toDate and f.status<>'CANCEL' and !isnull(f.location_id)) UNION (select g.item_id,g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date < :toDate and (!isnull(h.customer_id) OR !isnull(h.location_id))) UNION (select g.item_id,-g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='Y' and h.delivery_date >= :fromDate and h.delivery_date < :toDate and (!isnull(h.customer_id) OR !isnull(h.location_id))) UNION (select e.item_id,-e.quantity,f.number,f.retur_date as tdate from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and !isnull(f.location_id)) UNION (select e.item_id,e.quantity,f.number,f.retur_date as tdate from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and !isnull(f.location_id)) UNION (select e.item_id,-e.difference as quantity, f.number,f.stock_opname_date as tdate from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date < :toDate and f.status<>'CANCEL' and (!isnull(f.location_id) OR !isnull(f.customer_id)))) a "+
							"join item b on a.item_id=b.item_id " +
							"where 1=1 " + x + " " +
							"group by concat(a.number,b.item_id) " +
							"order by a.tdate ASC";
					
					List beginningList = StockCardReportDAO.getInstance().getSession().createSQLQuery(beginningSql)
						.addEntity("rmi", StockCardReport.class)
						.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
						.setDate("toDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
						.list();
					Iterator iterator = beginningList.iterator();
					double debitTotal = 0, creditTotal = 0;
					int debitQuantity = 0, creditQuantity = 0;
					while (iterator.hasNext()) {
						StockCardReport stockCardReport = (StockCardReport)iterator.next();
						if (stockCardReport.getQuantity()>0) {
							debitQuantity = debitQuantity + stockCardReport.getQuantity();
							debitTotal = debitTotal + (stockCardReport.getQuantity() * stockCardReport.getPrice());
						} else {
							creditQuantity = creditQuantity - stockCardReport.getQuantity();
							creditTotal = creditTotal - (stockCardReport.getQuantity() * stockCardReport.getPrice());
						}
					}
					request.setAttribute("debitQuantity", String.valueOf(debitQuantity));
					request.setAttribute("creditQuantity", String.valueOf(creditQuantity));
					request.setAttribute("debitTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal));
					request.setAttribute("creditTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal));
					
					// data
					String sql = "" +
							"select concat(a.number,b.item_id) as {rmi.Id}, b.name as {rmi.Name}, sum(a.quantity) as {rmi.Quantity}, a.number as {rmi.Number}, a.tdate as {rmi.StockCardDate}, b.cost_price as {rmi.Price}, concat(b.code) as {rmi.Code},  b.name as {rmi.Name}, (select e.number_of_digit from organization_setup e where e.organization_id=b.organization_id) as {rmi.NumberOfDigit} from " +
							"((select e.item_id,e.quantity,f.number,f.receiving_date as tdate from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date <= :toDate and f.status<>'CANCEL' and !isnull(f.location_id)) UNION (select g.item_id,g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date <= :toDate and (!isnull(h.customer_id) OR !isnull(h.location_id))) UNION (select g.item_id,-g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='Y' and h.delivery_date >= :fromDate and h.delivery_date <= :toDate and (!isnull(h.customer_id) OR !isnull(h.location_id))) UNION (select e.item_id,-e.quantity,f.number,f.retur_date as tdate from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date <= :toDate and f.status<>'CANCEL' and !isnull(f.location_id)) UNION (select e.item_id,e.quantity,f.number,f.retur_date as tdate from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date <= :toDate and f.status<>'CANCEL' and !isnull(f.location_id)) UNION (select e.item_id,-e.difference as quantity, f.number,f.stock_opname_date as tdate from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date <= :toDate and f.status<>'CANCEL' and (!isnull(f.location_id) OR !isnull(f.customer_id)))) a "+
							"join item b on a.item_id=b.item_id " +
							"where 1=1 " + x + " " +
							"group by concat(a.number,b.item_id) " +
							"order by a.tdate ASC";
					
					List list = StockCardReportDAO.getInstance().getSession().createSQLQuery(sql)
						.addEntity("rmi", StockCardReport.class)
						.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
						.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
						.list();
					request.setAttribute("STOCKCARD", list);
					
					// last balance
					Iterator iterator2 = list.iterator();
					double debitTotal2 = debitTotal, creditTotal2 = creditTotal;
					int debitQuantity2 = debitQuantity, creditQuantity2 = creditQuantity;
					while (iterator2.hasNext()) {
						StockCardReport stockCardReport = (StockCardReport)iterator2.next();
						if (stockCardReport.getQuantity()>0) {
							debitQuantity2 = debitQuantity2 + stockCardReport.getQuantity();
							debitTotal2 = debitTotal2 + (stockCardReport.getQuantity() * stockCardReport.getPrice());
						} else {
							creditQuantity2 = creditQuantity2 - stockCardReport.getQuantity();
							creditTotal2 = creditTotal2 - (stockCardReport.getQuantity() * stockCardReport.getPrice());
						}
					}
					request.setAttribute("debitQuantity2", String.valueOf(debitQuantity2));
					request.setAttribute("creditQuantity2", String.valueOf(creditQuantity2));
					request.setAttribute("debitTotal2", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal2));
					request.setAttribute("creditTotal2", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal2));
					
				
			    
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
	private ActionForward performStockCardGlobalCounterPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
				
		    OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
				if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate"); 
		    
				List customerLst = CustomersDAO.getInstance().getSession().createCriteria(Customers.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.add(Restrictions.eq("Store", Boolean.TRUE))
					.addOrder(Order.asc("Company")).list();
				request.setAttribute("customerLst", customerLst);
				List locationLst = LocationDAO.getInstance().getSession().createCriteria(Location.class)
					.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())))
					.addOrder(Order.asc("Name")).list();
				request.setAttribute("locationLst", locationLst);
				
				// debit : receiving & customer retur
				// kredit : vendor retur & delivery order & stok-opname
				String x = "";
				//if (form.getLong("itemMerkId")>0) x = "and b.item_merk_id = "+form.getLong("itemMerkId")+" ";
				//if (form.getLong("itemGenderId")>0) x = "and b.item_gender_id = "+form.getLong("itemGenderId")+" ";
				//if (form.getLong("itemTypeId")>0) x = "and b.item_type_id = "+form.getLong("itemTypeId")+" ";
				//if (form.getLong("itemColorId")>0) x = "and b.item_color_id = "+form.getLong("itemColorId")+" ";
				
				// beginning
				String beginningSql = "" +
					"select concat(a.number,b.item_id) as {rmi.Id}, b.name as {rmi.Name}, sum(a.quantity) as {rmi.Quantity}, a.number as {rmi.Number}, a.tdate as {rmi.StockCardDate}, b.cost_price as {rmi.Price}, concat(b.code) as {rmi.Code}, (select e.number_of_digit from organization_setup e where e.organization_id=b.organization_id) as {rmi.NumberOfDigit} from " +
					"((select e.item_id,e.quantity,f.number,f.receiving_date as tdate from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date < :toDate and f.status<>'CANCEL' and !isnull(f.location_id)) UNION (select g.item_id,g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date < :toDate and (!isnull(h.customer_id) OR !isnull(h.location_id))) UNION (select g.item_id,-g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='Y' and h.delivery_date >= :fromDate and h.delivery_date < :toDate and (!isnull(h.customer_id) OR !isnull(h.location_id))) UNION (select e.item_id,-e.quantity,f.number,f.retur_date as tdate from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and !isnull(f.location_id)) UNION (select e.item_id,e.quantity,f.number,f.retur_date as tdate from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date < :toDate and f.status<>'CANCEL' and !isnull(f.location_id)) UNION (select e.item_id,-e.difference as quantity, f.number,f.stock_opname_date as tdate from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date < :toDate and f.status<>'CANCEL' and (!isnull(f.location_id) OR !isnull(f.customer_id)))) a "+
					"join item b on a.item_id=b.item_id " +
					"where 1=1 " + x + " " +
					"group by concat(a.number,b.item_id) " +
					"order by a.tdate ASC";
			
			List beginningList = StockCardReportDAO.getInstance().getSession().createSQLQuery(beginningSql)
				.addEntity("rmi", StockCardReport.class)
				.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
				.setDate("toDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.list();
			Iterator iterator = beginningList.iterator();
			double debitTotal = 0, creditTotal = 0;
			int debitQuantity = 0, creditQuantity = 0;
			while (iterator.hasNext()) {
				StockCardReport stockCardReport = (StockCardReport)iterator.next();
				if (stockCardReport.getQuantity()>0) {
					debitQuantity = debitQuantity + stockCardReport.getQuantity();
					debitTotal = debitTotal + (stockCardReport.getQuantity() * stockCardReport.getPrice());
				} else {
					creditQuantity = creditQuantity - stockCardReport.getQuantity();
					creditTotal = creditTotal - (stockCardReport.getQuantity() * stockCardReport.getPrice());
				}
			}
			request.setAttribute("debitQuantity", String.valueOf(debitQuantity));
			request.setAttribute("creditQuantity", String.valueOf(creditQuantity));
			request.setAttribute("debitTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal));
			request.setAttribute("creditTotal", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal));
			
			// data
			String sql = "" +
					"select concat(a.number,b.item_id) as {rmi.Id}, b.name as {rmi.Name}, sum(a.quantity) as {rmi.Quantity}, a.number as {rmi.Number}, a.tdate as {rmi.StockCardDate}, b.cost_price as {rmi.Price}, concat(b.code) as {rmi.Code},  b.name as {rmi.Name}, (select e.number_of_digit from organization_setup e where e.organization_id=b.organization_id) as {rmi.NumberOfDigit} from " +
					"((select e.item_id,e.quantity,f.number,f.receiving_date as tdate from receiving_detail e join receiving f on e.receiving_id=f.receiving_id where f.receiving_date >= :fromDate and f.receiving_date <= :toDate and f.status<>'CANCEL' and !isnull(f.location_id)) UNION (select g.item_id,g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='N' and h.delivery_date >= :fromDate and h.delivery_date <= :toDate and (!isnull(h.customer_id) OR !isnull(h.location_id))) UNION (select g.item_id,-g.quantity,h.number,h.delivery_date as tdate from delivery_order_detail g join delivery_order h on g.delivery_order_id=h.delivery_order_id where h.is_rekap='N' and h.is_bon_kuning='Y' and h.delivery_date >= :fromDate and h.delivery_date <= :toDate and (!isnull(h.customer_id) OR !isnull(h.location_id))) UNION (select e.item_id,-e.quantity,f.number,f.retur_date as tdate from retur_to_vendor_detail e join retur_to_vendor f on e.retur_to_vendor_id=f.retur_to_vendor_id where f.retur_date >= :fromDate and f.retur_date <= :toDate and f.status<>'CANCEL' and !isnull(f.location_id)) UNION (select e.item_id,e.quantity,f.number,f.retur_date as tdate from customer_retur_detail e join customer_retur f on e.customer_retur_id=f.customer_retur_id where f.retur_date >= :fromDate and f.retur_date <= :toDate and f.status<>'CANCEL' and !isnull(f.location_id)) UNION (select e.item_id,-e.difference as quantity, f.number,f.stock_opname_date as tdate from stock_opname_detail e join stock_opname f on e.stock_opname_id=f.stock_opname_id where f.stock_opname_date >= :fromDate and f.stock_opname_date <= :toDate and f.status<>'CANCEL' and (!isnull(f.location_id) OR !isnull(f.customer_id)))) a "+
					"join item b on a.item_id=b.item_id " +
					"where 1=1 " + x + " " +
					"group by concat(a.number,b.item_id) " +
					"order by a.tdate ASC";
				
				List list = StockCardReportDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmi", StockCardReport.class)
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				request.setAttribute("STOCKCARD", list);
				
				// last balance
				Iterator iterator2 = list.iterator();
				double debitTotal2 = debitTotal, creditTotal2 = creditTotal;
				int debitQuantity2 = debitQuantity, creditQuantity2 = creditQuantity;
				while (iterator2.hasNext()) {
					StockCardReport stockCardReport = (StockCardReport)iterator2.next();
					if (stockCardReport.getQuantity()>0) {
						debitQuantity2 = debitQuantity2 + stockCardReport.getQuantity();
						debitTotal2 = debitTotal2 + (stockCardReport.getQuantity() * stockCardReport.getPrice());
					} else {
						creditQuantity2 = creditQuantity2 - stockCardReport.getQuantity();
						creditTotal2 = creditTotal2 - (stockCardReport.getQuantity() * stockCardReport.getPrice());
					}
				}
				request.setAttribute("debitQuantity2", String.valueOf(debitQuantity2));
				request.setAttribute("creditQuantity2", String.valueOf(creditQuantity2));
				request.setAttribute("debitTotal2", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal2));
				request.setAttribute("creditTotal2", Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal2));
				
				
				//pdf
		    // write to pdf document
				Document document = new Document(PageSize.A4.rotate(),30,20,20,20);
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
				
				Table table2 = new Table(13);
				table2.setWidth(100);
				table2.setCellsFitPage(true);
				float[] b = {3,7,10,10,11,10,11,3,8,10,3,8,10};
				table2.setWidths(b);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setCellsFitPage(true);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase("STOCK CARD GLOBAL COUNTER", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Date : "+form.getString("fromDate")+" - "+form.getString("toDate"), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(13);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("DATE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("NUMBER",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("MERK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TYPE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("GENDER",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("COLOR",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
	
				cell = new Cell(new Phrase("DEBIT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("CREDIT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PRICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PRICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				table2.endHeaders();
				
				// beginning
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase("Beginning : ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setColspan(6);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Integer.toString(debitQuantity), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Integer.toString(creditQuantity), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				
				int i = 0;
				Iterator iterator3 = list.iterator();
				while (iterator3.hasNext()) {
				    StockCardReport stockCardReport = (StockCardReport)iterator3.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getFormatedStockCardDate(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getNumber(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(Integer.toString(stockCardReport.getDebitQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getFormatedPrice(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedDebitTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Integer.toString(stockCardReport.getCreditQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getFormatedPrice(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedCreditTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
				}
				
				// end
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase("Ending : ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setColspan(6);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Integer.toString(debitQuantity2), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), debitTotal2), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Integer.toString(creditQuantity2), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table2.addCell(cell);
		    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase(Formater.getFormatedOutput(organizationSetup.getNumberOfDigit(), creditTotal2), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.LEFT | Rectangle.TOP);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(13);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(13);
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
				ItemDAO.getInstance().closeSessionForReal();
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
	private ActionForward performRecapitulationStockGlobalCounter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    
	/*		    int start = 0;
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
				}*/
				if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
				if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate"); 
	/*				Criteria criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
				if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code")));
				if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
				criteria.setProjection(Projections.rowCount());
				total = ((Integer)criteria.list().iterator().next()).intValue();*/
				// data report
				List list = new LinkedList();
				String x = "";
				String y = "";
				if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
				    y = "order by "+form.getString("orderBy")+" ASC ";
				} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
				    y = "order by "+form.getString("orderBy")+" DESC ";
				}
				if (form.getString("code")!=null && form.getString("code").length()>0) x = "and a.code like '%"+form.getString("code")+"%' ";
				if (form.getString("name")!=null && form.getString("name").length()>0) x = "and a.name like '%"+form.getString("name")+"%' ";
				//if (form.getLong("itemMerkId")>0) x = "and a.item_merk_id = "+form.getLong("itemMerkId")+" ";
				//if (form.getLong("itemGenderId")>0) x = "and a.item_gender_id = "+form.getLong("itemGenderId")+" ";
				//if (form.getLong("itemTypeId")>0) x = "and a.item_type_id = "+form.getLong("itemTypeId")+" ";
				String sql = "" +
					"select a.item_id as {rmi.ItemId}, a.code as {rmi.Code}, a.name as {rmi.Name}, f.price as {rmi.Price}, " +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and (!isnull(g.customer_id) OR !isnull(g.location_id))), 0) + IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and !isnull(i.location_id)), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) + IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and !isnull(o.location_id)), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and !isnull(k.location_id)), 0) - IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) - IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and (!isnull(q.customer_id) OR !isnull(q.location_id))), 0)  as {rmi.BeginningQuantity}, " +
					"IFNULL((select (g.quantity * g.price * g.exchange_rate) from inventory_first_balance g where g.item_id=a.item_id and (!isnull(g.customer_id) OR !isnull(g.location_id))), 0) + IFNULL((select sum(h.quantity * h.price * h.exchange_rate) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and !isnull(i.location_id)), 0) + IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) + IFNULL((select sum(n.quantity * n.price * n.exchange_rate) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and !isnull(o.location_id)), 0) - IFNULL((select sum(j.quantity * j.price * j.exchange_rate) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and !isnull(k.location_id)), 0) - IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) - IFNULL((select sum(p.difference *p.price * p.exchange_rate) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and (!isnull(q.customer_id) OR !isnull(q.location_id))), 0)  as {rmi.BeginningTotal}, " +							
					"IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and !isnull(i.location_id)), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) as {rmi.ReceivingQuantity}, " +
					"IFNULL((select sum(h.quantity * h.price * h.exchange_rate) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and !isnull(i.location_id)), 0) + IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) as {rmi.ReceivingTotal}, " +
					"IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and !isnull(k.location_id)), 0) as {rmi.ReturToVendorQuantity}, " +
					"IFNULL((select sum(j.quantity * j.price * j.exchange_rate) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and !isnull(k.location_id)), 0) as {rmi.ReturToVendorTotal}, " +
					"IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) as {rmi.DeliveryOrderQuantity}, " +
					"IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) as {rmi.DeliveryOrderTotal}, " +
					"IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and !isnull(o.location_id)), 0) as {rmi.CustomerReturQuantity}, " +
					"IFNULL((select sum(n.quantity * n.price * n.exchange_rate) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and !isnull(o.location_id)), 0) as {rmi.CustomerReturTotal}, " +
					"IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and (!isnull(q.customer_id) OR !isnull(q.location_id))), 0) as {rmi.StockOpnameQuantity}, " +
					"IFNULL((select sum(p.difference *p.price * p.exchange_rate) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and (!isnull(q.customer_id) OR !isnull(q.location_id))), 0) as {rmi.StockOpnameTotal}, " +
					
					"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {rmi.NumberOfDigit} " +
					"from item a join item_price f on f.item_id=a.item_id " +
					"where " +
					"a.organization_id = :organizationId " + x +
					"and f.is_default='Y' " +
					"and (" +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and (!isnull(g.customer_id) OR !isnull(g.location_id))), 0) + IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and !isnull(i.location_id)), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) + IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and !isnull(o.location_id)), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and !isnull(k.location_id)), 0) - IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) - IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and (!isnull(q.customer_id) OR !isnull(q.location_id))), 0)<>0 OR " +
					"IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and !isnull(i.location_id)), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0)<>0 OR " +
					"IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and !isnull(k.location_id)), 0)<>0 OR " +
					"IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0)<>0 OR " +
					"IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and !isnull(o.location_id)), 0)<>0 OR " +
					"IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and (!isnull(q.customer_id) OR !isnull(q.location_id))), 0)<>0 OR " +
					"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and (!isnull(g.customer_id) OR !isnull(g.location_id))), 0)<>0 " +
					")" +
					"group by a.item_id " + y;
				
				OrganizationSetup organizationSetup =(OrganizationSetup)users.getOrganization();
				list = RekapMutationStockReportDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmi", RekapMutationStockReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					//.setInteger("limit", count)
					//.setInteger("offset", start)
					.list();
				request.setAttribute("REKAPMUTATIONSTOCK", list);
	/*				String pager = Pager.generatePager(start, count, total);
				String pagerItem = Pager.generatePagerItem(start, count, total);
				request.setAttribute("PAGER",pager);
				request.setAttribute("PAGERITEM",pagerItem);*/
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
	private ActionForward performRecapitulationStockGlobalCounterPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
				Criteria criteria = ItemDAO.getInstance().getSession().createCriteria(Item.class);
				if (form.getString("code")!=null && form.getString("code").length()>0)criteria.add(Restrictions.like("Code", form.getString("code")));
				if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
				criteria.setProjection(Projections.rowCount());
				// data report
				List list = new LinkedList();
				String x = "";
				String y = "";
				if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
				    y = "order by "+form.getString("orderBy")+" ASC ";
				} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
				    y = "order by "+form.getString("orderBy")+" DESC ";
				}
				if (form.getString("code")!=null && form.getString("code").length()>0) x = "and a.code like '%"+form.getString("code")+"%' ";
				if (form.getString("name")!=null && form.getString("name").length()>0) x = "and a.name like '%"+form.getString("name")+"%' ";
				//if (form.getLong("itemMerkId")>0) x = "and a.item_merk_id = "+form.getLong("itemMerkId")+" ";
				//if (form.getLong("itemGenderId")>0) x = "and a.item_gender_id = "+form.getLong("itemGenderId")+" ";
				//if (form.getLong("itemTypeId")>0) x = "and a.item_type_id = "+form.getLong("itemTypeId")+" ";
				String sql = "" +
				"select a.item_id as {rmi.ItemId}, a.code as {rmi.Code}, a.name as {rmi.Name}, f.price as {rmi.Price}, " +
				"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and (!isnull(g.customer_id) OR !isnull(g.location_id))), 0) + IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and !isnull(i.location_id)), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) + IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and !isnull(o.location_id)), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and !isnull(k.location_id)), 0) - IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) - IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and (!isnull(q.customer_id) OR !isnull(q.location_id))), 0)  as {rmi.BeginningQuantity}, " +
				"IFNULL((select (g.quantity * g.price * g.exchange_rate) from inventory_first_balance g where g.item_id=a.item_id and (!isnull(g.customer_id) OR !isnull(g.location_id))), 0) + IFNULL((select sum(h.quantity * h.price * h.exchange_rate) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and !isnull(i.location_id)), 0) + IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) + IFNULL((select sum(n.quantity * n.price * n.exchange_rate) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and !isnull(o.location_id)), 0) - IFNULL((select sum(j.quantity * j.price * j.exchange_rate) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and !isnull(k.location_id)), 0) - IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) - IFNULL((select sum(p.difference *p.price * p.exchange_rate) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and (!isnull(q.customer_id) OR !isnull(q.location_id))), 0)  as {rmi.BeginningTotal}, " +							
				"IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and !isnull(i.location_id)), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) as {rmi.ReceivingQuantity}, " +
				"IFNULL((select sum(h.quantity * h.price * h.exchange_rate) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and !isnull(i.location_id)), 0) + IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) as {rmi.ReceivingTotal}, " +
				"IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and !isnull(k.location_id)), 0) as {rmi.ReturToVendorQuantity}, " +
				"IFNULL((select sum(j.quantity * j.price * j.exchange_rate) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and !isnull(k.location_id)), 0) as {rmi.ReturToVendorTotal}, " +
				"IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) as {rmi.DeliveryOrderQuantity}, " +
				"IFNULL((select sum(l.quantity * l.price * l.exchange_rate) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) as {rmi.DeliveryOrderTotal}, " +
				"IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and !isnull(o.location_id)), 0) as {rmi.CustomerReturQuantity}, " +
				"IFNULL((select sum(n.quantity * n.price * n.exchange_rate) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and !isnull(o.location_id)), 0) as {rmi.CustomerReturTotal}, " +
				"IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and (!isnull(q.customer_id) OR !isnull(q.location_id))), 0) as {rmi.StockOpnameQuantity}, " +
				"IFNULL((select sum(p.difference *p.price * p.exchange_rate) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and (!isnull(q.customer_id) OR !isnull(q.location_id))), 0) as {rmi.StockOpnameTotal}, " +
				
				"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {rmi.NumberOfDigit} " +
				"from item a join item_price f on f.item_id=a.item_id " +
				"where " +
				"a.organization_id = :organizationId " + x +
				"and f.is_default='Y' " +
				"and (" +
				"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and (!isnull(g.customer_id) OR !isnull(g.location_id))), 0) + IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :setupDate and i.receiving_date < :fromDate and h.item_id=a.item_id and !isnull(i.location_id)), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) + IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :setupDate and o.retur_date < :fromDate and n.item_id=a.item_id and !isnull(o.location_id)), 0) - IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :setupDate and k.retur_date < :fromDate and j.item_id=a.item_id and !isnull(k.location_id)), 0) - IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :setupDate and m.delivery_date < :fromDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0) - IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :setupDate and q.stock_opname_date < :fromDate and p.item_id=a.item_id and (!isnull(q.customer_id) OR !isnull(q.location_id))), 0)<>0 OR " +
				"IFNULL((select sum(h.quantity) from receiving_detail h join receiving i on h.receiving_id=i.receiving_id where i.status <> 'CANCEL' and i.receiving_date >= :fromDate and i.receiving_date <= :toDate and h.item_id=a.item_id and !isnull(i.location_id)), 0) + IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='N' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0)<>0 OR " +
				"IFNULL((select sum(j.quantity) from retur_to_vendor_detail j join retur_to_vendor k on j.retur_to_vendor_id=k.retur_to_vendor_id where k.status <> 'CANCEL' and k.retur_date >= :fromDate and k.retur_date <= :toDate and j.item_id=a.item_id and !isnull(k.location_id)), 0)<>0 OR " +
				"IFNULL((select sum(l.quantity) from delivery_order_detail l join delivery_order m on l.delivery_order_id=m.delivery_order_id where m.status <> 'CANCEL' and m.is_rekap='N' and m.delivery_date >= :fromDate and m.delivery_date <= :toDate and l.item_id=a.item_id and m.is_bon_kuning='Y' and (!isnull(m.customer_id) OR !isnull(m.location_id))), 0)<>0 OR " +
				"IFNULL((select sum(n.quantity) from customer_retur_detail n join customer_retur o on n.customer_retur_id=o.customer_retur_id where o.status <> 'CANCEL' and o.retur_date >= :fromDate and o.retur_date <= :toDate and n.item_id=a.item_id and !isnull(o.location_id)), 0)<>0 OR " +
				"IFNULL((select sum(p.difference) from stock_opname_detail p join stock_opname q on p.stock_opname_id=q.stock_opname_id where q.status <> 'CANCEL' and q.stock_opname_date >= :fromDate and q.stock_opname_date <= :toDate and p.item_id=a.item_id and (!isnull(q.customer_id) OR !isnull(q.location_id))), 0)<>0 OR " +
				"IFNULL((select g.quantity from inventory_first_balance g where g.item_id=a.item_id and (!isnull(g.customer_id) OR !isnull(g.location_id))), 0)<>0 " +
				")" +
				"group by a.item_id " + y;
				OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
				//.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
				list = RekapMutationStockReportDAO.getInstance().getSession().createSQLQuery(sql)
					.addEntity("rmi", RekapMutationStockReport.class)
					.setLong("organizationId", users.getOrganization().getId())
					.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
				
				//pdf
		    // write to pdf document
				Document document = new Document(PageSize.A4.rotate(),30,20,20,20);
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
				
				Table table2 = new Table(20);
				table2.setWidth(100);
				table2.setCellsFitPage(true);
				float[] b = {3,7,5,4,4,3,7,7,3,7,3,7,3,7,3,7,3,7,3,7};
				table2.setWidths(b);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setCellsFitPage(true);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase("RECAPITULATION STOCK OFFICE", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(20);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ARTICLE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("MERK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("COLOR",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("SIZE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("BEGINNING",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("RECEIVING",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PURCHASE RETURN",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("INVOICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("SALES RETURN",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ADJUSTMENT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ENDING BALANCE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PRICE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("QTY",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				
				table2.endHeaders();
				
				int i = 0;
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
				    RekapMutationStockReport stockCardReport = (RekapMutationStockReport)iterator.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getCode(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getBeginningQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(stockCardReport.getFormatedPrice(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedBeginningTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getReceivingQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedReceivingTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getReturToVendorQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedReturToVendorTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getDeliveryOrderQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedDeliveryOrderTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getCustomerReturQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedCustomerReturTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getStockOpnameQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedStockOpnameTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						cell = new Cell(new Phrase(Double.toString(stockCardReport.getEndingQuantity()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(stockCardReport.getFormatedEndingTotal(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
				}
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(20);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(20);
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
		errors.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.global", ex));
		saveErrors(request,errors);
	}

}