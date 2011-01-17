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
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.mpe.financial.model.ChartGroup;
import com.mpe.financial.model.ChartOfAccount;
import com.mpe.financial.model.JournalDetail;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.ChartGroupDAO;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.financial.struts.form.ChartOfAccountForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class ChartOfAccountAction extends Action {
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
		//ChartOfAccountForm uomForm = (ChartOfAccountForm) form;
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
					} else if ("FORM".equalsIgnoreCase(action)) {
						forward = performForm(mapping, form, request, response);
					} else if ("SAVE".equalsIgnoreCase(action)) {
						forward = performSave(mapping, form, request, response);
					} else if ("DETAIL".equalsIgnoreCase(action)) { 
						forward = performDetail(mapping, form, request, response);
					} else if ("DELETE".equalsIgnoreCase(action)) {
						forward = performDelete(mapping, form, request, response);
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
		ChartOfAccountForm form = (ChartOfAccountForm) actionForm;
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
			List chartGroupLst = ChartGroupDAO.getInstance().findAll(Order.asc("Name"));
		  request.setAttribute("chartGroupLst", chartGroupLst);
		  List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class).add(Restrictions.isNull("Parent")).addOrder(Order.asc("Name")).list();
		  request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			Criteria criteria = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
			if (form.getLong("chartGroupId")>0)criteria.add(Restrictions.eq("ChartGroup.Id", new Long(form.getLong("chartGroupId"))));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
			if (form.getLong("chartGroupId")>0)criteria.add(Restrictions.eq("ChartGroup.Id", new Long(form.getLong("chartGroupId"))));
			//criteria.addOrder(Order.desc("Name"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("CHARTOFACCOUNT",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
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
	 * Method performForm
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */			
	private ActionForward performForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ChartOfAccountForm form = (ChartOfAccountForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			// relationships
		  List chartGroupLst = ChartGroupDAO.getInstance().findAll(Order.asc("Name"));
		  request.setAttribute("chartGroupLst", chartGroupLst);
		  List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class).add(Restrictions.isNull("Parent")).addOrder(Order.asc("Name")).list();
		  request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			if (form.getLong("chartOfAccountId") == 0) {
				form.setString("chartOfAccountId",0);
				form.setCurentTimestamp("createOn");
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
			} else {
				ChartOfAccount obj = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
				form.setString("chartOfAccountId",obj.getId());
				form.setString("number",obj.getNumber());
				form.setString("name",obj.getName());
				form.setString("isDebit",obj.isDebit()==true?"Y":"N");
				form.setString("type",obj.getType());
				form.setString("description",obj.getDescription());
				form.setString("groups",obj.getGroups());
				form.setString("chartGroupId",obj.getChartGroup()!=null?obj.getChartGroup().getId():0);
				form.setString("parentId",obj.getParent()!=null?obj.getParent().getId():0);
				form.setString("createBy",obj.getCreateBy()!=null?obj.getCreateBy().getId():0);
				form.setTimestamp("createOn",obj.getCreateOn());
				form.setString("changeBy",obj.getChangeBy()!=null?obj.getChangeBy().getId():0);
				form.setCurentTimestamp("changeOn");
			}
		}catch(Exception ex) {
			try {
				  List chartGroupLst = ChartGroupDAO.getInstance().findAll(Order.asc("Name"));
				  request.setAttribute("chartGroupLst", chartGroupLst);
				  List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class).add(Restrictions.isNull("Parent")).addOrder(Order.asc("Name")).list();
				  request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
	}
	
	/** 
	 * Method performSave
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ChartOfAccountForm form = (ChartOfAccountForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			ChartOfAccount obj = null;
			String a[] = {"Bank","Account receivable","Account payable","Fixed asset","Other asset","Other current liability","Long term liability","Equity","Income","Cost of goods sold","Expense","Other income","Other expense"};
			String b[] = {"Y","Y","N","Y","Y","N","N","N","N","Y","Y","N","Y"};
			String g[] = {"Asset","Asset","Liability","Asset","Asset","Liability","Liability","Equity","Revenue","Expense","Expense","Revenue","Expense"};
			int x = 0;
			if (form.getLong("chartOfAccountId") == 0) {
				obj = (ChartOfAccount)ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class).add(Restrictions.eq("Number", form.getString("number"))).add(Restrictions.eq("Name", form.getString("name"))).uniqueResult();
				if (obj==null) {
					obj = new ChartOfAccount();
					for (int c=0; c<a.length; c++) {
						if (a[c].equalsIgnoreCase(form.getString("type"))) x = c;
					}
					obj.setName(form.getString("name"));
					ChartGroup chartGroup = ChartGroupDAO.getInstance().get(form.getLong("chartGroupId"));
					obj.setChartGroup(chartGroup);
					obj.setDebit(b[x].equalsIgnoreCase("Y")?true:false);
					obj.setNumber(form.getString("number"));
					ChartOfAccount parent = ChartOfAccountDAO.getInstance().get(form.getLong("parentId"));
					obj.setParent(parent);
					obj.setType(form.getString("type"));
					obj.setDescription(form.getString("description"));
					obj.setGroups(g[x]);
					obj.setCreateBy(users); 
					obj.setCreateOn(form.getTimestamp("createOn"));
					ChartOfAccountDAO.getInstance().save(obj);
				} else {
					  List chartGroupLst = ChartGroupDAO.getInstance().findAll(Order.asc("Name"));
					  request.setAttribute("chartGroupLst", chartGroupLst);
					  List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class).add(Restrictions.isNull("Parent")).addOrder(Order.asc("Name")).list();
					  request.setAttribute("chartOfAccountLst", chartOfAccountLst);
					  ///
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("number")+" OR "+form.getString("name")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			} else {
				obj = ChartOfAccountDAO.getInstance().load(form.getLong("chartOfAccountId"));
				for (int c=0; c<a.length; c++) {
					if (a[c].equalsIgnoreCase(form.getString("type"))) x = c;
				}
				obj.setName(form.getString("name"));
				ChartGroup chartGroup = ChartGroupDAO.getInstance().get(form.getLong("chartGroupId"));
				obj.setChartGroup(chartGroup);
				obj.setDebit(b[x].equalsIgnoreCase("Y")?true:false);
				obj.setNumber(form.getString("number"));
				ChartOfAccount parent = ChartOfAccountDAO.getInstance().get(form.getLong("parentId"));
				obj.setParent(parent);
				obj.setType(form.getString("type"));
				obj.setDescription(form.getString("description"));
				obj.setGroups(g[x]);
				Users createBy = UsersDAO.getInstance().get(form.getLong("createBy"));
				obj.setCreateBy(createBy); 
				obj.setCreateOn(form.getTimestamp("createOn"));
				obj.setChangeBy(users);
				obj.setChangeOn(form.getTimestamp("changeOn"));
				obj.setId(form.getLong("chartOfAccountId"));
				ChartOfAccountDAO.getInstance().update(obj);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
				  List chartGroupLst = ChartGroupDAO.getInstance().findAll(Order.asc("Name"));
				  request.setAttribute("chartGroupLst", chartGroupLst);
				  List chartOfAccountLst = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class).add(Restrictions.isNull("Parent")).addOrder(Order.asc("Name")).list();
				  request.setAttribute("chartOfAccountLst", chartOfAccountLst);
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ChartOfAccountForm form = (ChartOfAccountForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			ChartOfAccount chartOfAccount = ChartOfAccountDAO.getInstance().get(form.getLong("chartOfAccountId"));
			request.setAttribute("chartOfAccount", chartOfAccount);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("detail");
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
	private ActionForward performDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ChartOfAccountForm form = (ChartOfAccountForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			if (isCancelled(request)) {
				ActionForward forward = mapping.findForward("list_redir");
				StringBuffer sb = new StringBuffer(forward.getPath());
				sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
				return new ActionForward(sb.toString(),true);
			}
			ChartOfAccountDAO.getInstance().delete(form.getLong("chartOfAccountId"));
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				ChartOfAccountDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("list_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?start="+httpSession.getAttribute(CommonConstants.START)+"&count="+httpSession.getAttribute(CommonConstants.COUNT));
		return new ActionForward(sb.toString(),true);
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
		ChartOfAccountForm form = (ChartOfAccountForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			
			Criteria criteria = ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class);
			if (form.getString("number")!=null && form.getString("number").length()>0)criteria.add(Restrictions.like("Number", "%"+form.getString("number")+"%"));
			if (form.getString("name")!=null && form.getString("name").length()>0)criteria.add(Restrictions.like("Name", "%"+form.getString("name")+"%"));
			if (form.getLong("chartGroupId")>0)criteria.add(Restrictions.eq("ChartGroup.Id", new Long(form.getLong("chartGroupId"))));
			//criteria.addOrder(Order.desc("Name"));
/*			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}*/
			//criteria.setFirstResult(start);
			//criteria.setMaxResults(count);
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			List list = criteria.list();
			//request.setAttribute("CHARTOFACCOUNT",list);
			//String pager = Pager.generatePager(start, count, total);
			//String pagerItem = Pager.generatePagerItem(start, count, total);
			//request.setAttribute("PAGER",pager);
			//request.setAttribute("PAGERITEM",pagerItem);
			
			
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
			
			document.add(table1);
			
			// header table
			Table firstTable = new Table(5);
			firstTable.setWidth(100);
			firstTable.setCellsFitPage(true);
			firstTable.setBorderWidth(1);
			firstTable.setBorder(Rectangle.NO_BORDER);
			firstTable.setPadding(1);
			firstTable.setSpacing(0);
			int b2[] = {20,20,20,20,20};
			firstTable.setWidths(b2);
			
			cell = new Cell(new Phrase("Number",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			firstTable.addCell(cell);
			cell = new Cell(new Phrase("Name",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			firstTable.addCell(cell);
			cell = new Cell(new Phrase("Parent",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			firstTable.addCell(cell);
			cell = new Cell(new Phrase("Group",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			firstTable.addCell(cell);
			cell = new Cell(new Phrase("Type",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
			cell.setBorderWidth(1);
			cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
			firstTable.addCell(cell);

			firstTable.endHeaders();
			
			Iterator iterator2 = list.iterator();
			while (iterator2.hasNext()) {
				ChartOfAccount chartOfAccount = (ChartOfAccount)iterator2.next();
				cell = new Cell(new Phrase(chartOfAccount.getNumber(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase(chartOfAccount.getName(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase(chartOfAccount.getParent()!=null?chartOfAccount.getParent().getName():"-",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase(chartOfAccount.getGroups(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT);
				firstTable.addCell(cell);
				cell = new Cell(new Phrase(chartOfAccount.getType(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT);
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