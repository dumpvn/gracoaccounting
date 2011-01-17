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
import com.mpe.financial.model.Bank;
import com.mpe.financial.model.ItemPosReportBarangTerjual;
import com.mpe.financial.model.ItemPosReportDetailReceivableCreditCard;
import com.mpe.financial.model.ItemPosReportPenjualanDetail;
import com.mpe.financial.model.ItemPosReportPenjualanGlobal;
import com.mpe.financial.model.ItemPosReportRekapReceivableCreditCard;
import com.mpe.financial.model.ItemPosReportStockOpname;
import com.mpe.financial.model.ItemPosReportStockPenjualanGlobalTokoCounter;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.PosOrder;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.Warehouse;
import com.mpe.financial.model.dao.BankDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.PosOrderDAO;
import com.mpe.financial.model.dao.WarehouseDAO;
import com.mpe.financial.struts.form.ItemForm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mpe.common.*;

public class ItemPosReportAction extends Action {
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
		ItemForm itemForm = (ItemForm) form;
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
					} else if ("BARANGTERJUAL".equalsIgnoreCase(action)) {
						forward = performBarangTerjual(mapping, form, request, response);
					} else if ("BARANGTERJUALPDF".equalsIgnoreCase(action)) {
						forward = performBarangTerjualPdf(mapping, form, request, response);
					} else if ("PENJUALANGLOBAL".equalsIgnoreCase(action)) {
						forward = performPenjualanGlobal(mapping, form, request, response);
					} else if ("PENJUALANGLOBALPDF".equalsIgnoreCase(action)) {
						forward = performPenjualanGlobalPdf(mapping, form, request, response);
					} else if ("PENJUALANGLOBALTOKOCOUNTER".equalsIgnoreCase(action)) {
						forward = performPenjualanGlobalTokoCounter(mapping, form, request, response);
					} else if ("PENJUALANGLOBALTOKOCOUNTERPDF".equalsIgnoreCase(action)) {
						forward = performPenjualanGlobalTokoCounterPdf(mapping, form, request, response);
					} else if ("STOCKGLOBALTOKOCOUNTER".equalsIgnoreCase(action)) {
						forward = performStockGlobalTokoCounter(mapping, form, request, response);
					} else if ("STOCKGLOBALTOKOCOUNTERPDF".equalsIgnoreCase(action)) {
						forward = performStockGlobalTokoCounterPdf(mapping, form, request, response);
					} else if ("STOCKGLOBALTOKOCOUNTER".equalsIgnoreCase(action)) {
						forward = performStockGlobalTokoCounter(mapping, form, request, response);
					} else if ("STOCKGLOBALTOKOCOUNTERPDF".equalsIgnoreCase(action)) {
						forward = performStockGlobalTokoCounterPdf(mapping, form, request, response);
					} else if ("PENJUALANDETAIL".equalsIgnoreCase(action)) {
						forward = performPenjualanDetail(mapping, form, request, response);
					} else if ("PENJUALANDETAILPDF".equalsIgnoreCase(action)) {
						forward = performPenjualanDetailPdf(mapping, form, request, response);
					} else if ("PENJUALANHARIAN".equalsIgnoreCase(action)) {
						forward = performPenjualanHarian(mapping, form, request, response);
					} else if ("PENJUALANHARIANPDF".equalsIgnoreCase(action)) {
						forward = performPenjualanHarianPdf(mapping, form, request, response);
					} else if ("STOCKOPNAME".equalsIgnoreCase(action)) {
						forward = performStockOpname(mapping, form, request, response);
					} else if ("STOCKOPNAMEPDF".equalsIgnoreCase(action)) {
						forward = performStockOpnamePdf(mapping, form, request, response);
					} else if ("KOMISISPG".equalsIgnoreCase(action)) {
						forward = performKomisiSpg(mapping, form, request, response);
					} else if ("KOMISISPGPDF".equalsIgnoreCase(action)) {
						forward = performKomisiSpgPdf(mapping, form, request, response);
					} else if ("REKAPRECEIVABLECREDITCARD".equalsIgnoreCase(action)) {
						forward = performRekapReceivableCreditCard(mapping, form, request, response);
					} else if ("REKAPRECEIVABLECREDITCARDPDF".equalsIgnoreCase(action)) {
						forward = performRekapReceivableCreditCardPdf(mapping, form, request, response);
					} else if ("DETAILRECEIVABLECREDITCARD".equalsIgnoreCase(action)) {
						forward = performDetailReceivableCreditCard(mapping, form, request, response);
					} else if ("DETAILRECEIVABLECREDITCARDPDF".equalsIgnoreCase(action)) {
						forward = performDetailReceivableCreditCardPdf(mapping, form, request, response);
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
	 * Method performBarangTerjual
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performBarangTerjual(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		    List locationLst = LocationDAO.getInstance().findAll(Order.asc("Code"));
		    request.setAttribute("locationLst", locationLst);
		    Session session = ItemDAO.getInstance().getSession();
		    List barangTerjualLst = new LinkedList();
		    String addSql = "";
		    if (form.getLong("itemMerkId")>0) addSql = "where b.item_merk_id = "+form.getLong("itemMerkId") +" "; 
		    String sql = "";
		    sql = "select a.item_id as {ipr.ItemId}, b.code as {ipr.Merk}, c.code as {ipr.Type}, a.name as {ipr.Name}, d.code as {ipr.Color} from item a "+
					"join item_merk b on a.item_merk_id=b.item_merk_id " +
					"join item_type c on a.item_type_id=c.item_type_id " +
					"join item_color d on a.item_color_id=d.item_color_id " + addSql +
					"group by a.item_merk_id, a.item_color_id, a.item_type_id";
		    barangTerjualLst = session.createSQLQuery(sql)
					.addEntity("ipr", ItemPosReportBarangTerjual.class)
					.list();
			
		    request.setAttribute("barangTerjualLst", barangTerjualLst);
		    
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
		
	/** 
	 * Method performBarangTerjualPdf
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performBarangTerjualPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {

				
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
	 * Method performBarangTerjual
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performPenjualanGlobal(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		    List locationLst = LocationDAO.getInstance().findAll(Order.asc("Code"));
		    request.setAttribute("locationLst", locationLst);
		    // find warehouse of location
		    long warehouseId = 0;
		    Warehouse warehouse = (Warehouse)WarehouseDAO.getInstance().getSession().createCriteria(Warehouse.class).add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId")))).uniqueResult();
		    if (warehouse!=null) warehouseId = warehouse.getId();
		    Session session = ItemDAO.getInstance().getSession();
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
		    if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate");
		    List penjualanGlobalLst = new LinkedList();
		    String addSql = "";
		    /*if (form.getLong("itemTypeId")>0)*/ addSql = "and a.item_type_id = "+form.getLong("itemTypeId") +" "; 
		    String sql = "";
		    sql = "select a.item_id as {ipr.ItemId}, b.code as {ipr.Merk}, a.name as {ipr.Name}, a.code as {ipr.Code}, m.price as {ipr.Price}, " +
					"IFNULL((select sum(e.quantity) from item_first_balance e where e.item_id=a.item_id and e.location_id="+form.getLong("locationId")+"),0) as {ipr.StockAwal}, " +
					"IFNULL((select sum(c.quantity) from delivery_order_detail c join delivery_order d on c.delivery_order_id=d.delivery_order_id where c.item_id=a.item_id and d.location_id="+form.getLong("locationId")+" and d.delivery_date >=:fromDate and d.delivery_date <=:toDate),0) as {ipr.Masuk}, " +
					"IFNULL((select sum(h.moved_quantity) from mutation_detail h join mutation i on h.mutation_id=i.mutation_id where h.item_id=a.item_id and i.to_warehouse_id="+warehouseId+" and i.location_id="+form.getLong("locationId")+" and i.mutation_date >=:fromDate and i.mutation_date <=:toDate),0) as {ipr.TukarMasuk}, " +
					"IFNULL((select sum(f.quantity) from customer_retur_detail f join customer_retur g on f.customer_retur_id=g.customer_retur_id where f.item_id=a.item_id and g.location_id="+form.getLong("locationId")+" and g.retur_date >= :fromDate and g.retur_date <= :toDate),0) as {ipr.Retur}, " +
					"IFNULL((select sum(h.moved_quantity) from mutation_detail h join mutation i on h.mutation_id=i.mutation_id where h.item_id=a.item_id and i.from_warehouse_id="+warehouseId+" and i.location_id="+form.getLong("locationId")+" and i.mutation_date >=:fromDate and i.mutation_date <=:toDate),0) as {ipr.TukarKeluar}, " +
					"IFNULL((select sum(k.quantity) from pos_order_detail k join pos_order l on k.pos_order_id=l.pos_order_id where k.item_id=a.item_id and l.location_id="+form.getLong("locationId")+" and l.pos_order_date >=:fromDate and l.pos_order_date <= :toDate),0) as {ipr.Jual}, " +
					"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {ipr.NumberOfDigit} " +
					"from item a " +
					"join item_merk b on a.item_merk_id=b.item_merk_id join item_price m on m.item_id=a.item_id where m.is_default='Y' " + addSql +" " +
					"group by a.item_id";
		    
		    penjualanGlobalLst = session.createSQLQuery(sql)
				.addEntity("ipr", ItemPosReportPenjualanGlobal.class)
				.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
				.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
				.list();
		
		    request.setAttribute("penjualanGlobalLst", penjualanGlobalLst);
		    
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
		
	/** 
	 * Method performBarangTerjualPdf
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performPenjualanGlobalPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {

				
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
	 * Method performBarangTerjual
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performPenjualanGlobalTokoCounter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		    List locationLst = LocationDAO.getInstance().findAll(Order.asc("Code"));
		    request.setAttribute("locationLst", locationLst);
		    Session session = ItemDAO.getInstance().getSession();
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
		    if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate");
		    List penjualanGlobalTokoCounterLst = new LinkedList();
		    String sql = "";
		    sql = "select a.item_id as {ipr.ItemId}, b.code as {ipr.Merk}, c.code as {ipr.Type}, a.name as {ipr.Name}, a.code as {ipr.Code}, d.code as {ipr.Gender}, e.price as {ipr.Price}, " +
		    	"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {ipr.NumberOfDigit} " +
		    	"from item a "+
		    	"join item_merk b on a.item_merk_id=b.item_merk_id " +
					"join item_type c on a.item_type_id=c.item_type_id " +
					"join item_color d on a.item_color_id=d.item_color_id " + 
					"join item_price e on a.item_id=e.item_id " + 
					"where e.is_default='Y' " +
					"group by a.item_merk_id, a.item_gender_id ";
		    penjualanGlobalTokoCounterLst = session.createSQLQuery(sql)
					.addEntity("ipr", ItemPosReportStockPenjualanGlobalTokoCounter.class)
					.list();
			
		    request.setAttribute("penjualanGlobalTokoCounterLst", penjualanGlobalTokoCounterLst);
		    
		    
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
		
	/** 
	 * Method performBarangTerjualPdf
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performPenjualanGlobalTokoCounterPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {

				
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
	 * Method performBarangTerjual
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performStockGlobalTokoCounter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		    List locationLst = LocationDAO.getInstance().findAll(Order.asc("Code"));
		    request.setAttribute("locationLst", locationLst);
		    Session session = ItemDAO.getInstance().getSession();
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
		    if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate");
		    List stockGlobalTokoCounterLst = new LinkedList();
		    String sql = "";
		    sql = "select a.item_id as {ipr.ItemId}, b.code as {ipr.Merk}, c.code as {ipr.Type}, a.name as {ipr.Name}, a.code as {ipr.Code}, d.code as {ipr.Gender}, e.price as {ipr.Price}, " +
		    	"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {ipr.NumberOfDigit} " +
		    	"from item a "+
		    	"join item_merk b on a.item_merk_id=b.item_merk_id " +
					"join item_type c on a.item_type_id=c.item_type_id " +
					"join item_color d on a.item_color_id=d.item_color_id " + 
					"join item_price e on a.item_id=e.item_id " + 
					"where e.is_default='Y' " +
					"group by a.item_merk_id, a.item_gender_id ";
		    stockGlobalTokoCounterLst = session.createSQLQuery(sql)
					.addEntity("ipr", ItemPosReportStockPenjualanGlobalTokoCounter.class)
					.list();
			
		    request.setAttribute("stockGlobalTokoCounterLst", stockGlobalTokoCounterLst);
		    
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
		
	/** 
	 * Method performBarangTerjualPdf
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performStockGlobalTokoCounterPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {

				
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
	 * Method performBarangTerjual
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performPenjualanDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		    List locationLst = LocationDAO.getInstance().findAll(Order.asc("Code"));
		    request.setAttribute("locationLst", locationLst);
		    Session session = ItemDAO.getInstance().getSession();
		    
		    String itemSizeSql = "";
		    List itemSizeLst = new LinkedList();
/*		    itemSizeSql = "select a.item_size_id as {is.Id}, b.code as {is.Code}, b.name as {is.Name} from item a join item_size b on a.item_size_id=b.item_size_id where a.item_merk_id = "+form.getLong("itemMerkId")+" group by a.item_size_id ";
		    itemSizeLst = session.createSQLQuery(itemSizeSql)
					.addEntity("is", ItemSize.class)
					.list();*/
		    request.setAttribute("itemSizeLst", itemSizeLst);
		    
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
		    if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate");
		    List penjualanDetailLst = new LinkedList();
		    String sql = "";
		    sql = "select a.pos_order_id as {ipr.PosOrderId}, a.pos_order_date as {ipr.Date}, a.pos_order_number as {ipr.Number}, a.note as {ipr.Note}, " +
		    		"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {ipr.NumberOfDigit} " +
		    		"from pos_order a join pos_order_detail b on a.pos_order_id=b.pos_order_id " +
		    		"join item c on b.item_id=c.item_id " +
		    		"where a.pos_order_date >=:fromDate and a.pos_order_date<=:toDate and a.location_id= "+form.getLong("locationId")+" " +
		    		"and c.item_merk_id=" +form.getLong("itemMerkId")+" " +
		    		"group by a.pos_order_id";
		    penjualanDetailLst = session.createSQLQuery(sql)
					.addEntity("ipr", ItemPosReportPenjualanDetail.class)
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
			
		    request.setAttribute("penjualanDetailLst", penjualanDetailLst);
		    
		    
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
		
	/** 
	 * Method performBarangTerjualPdf
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performPenjualanDetailPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {

				
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
	 * Method performBarangTerjual
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performPenjualanHarian(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		    List locationLst = LocationDAO.getInstance().findAll(Order.asc("Code"));
		    request.setAttribute("locationLst", locationLst);
		    Session session = ItemDAO.getInstance().getSession();
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
		    if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate");
		    List penjualanHarianLst = PosOrderDAO.getInstance().getSession().createCriteria(PosOrder.class)
		    	.add(Restrictions.eq("Location.Id", new Long(form.getLong("locationId"))))
		    	.add(Restrictions.eq("PosOrderDate", new Date(form.getCalendar("fromDate").getTime().getTime())))
		    	.addOrder(Order.asc("Id")).list();
		    request.setAttribute("penjualanHarianLst", penjualanHarianLst);
		    
		    
		    
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
		
	/** 
	 * Method performBarangTerjualPdf
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performPenjualanHarianPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {

				
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
	 * Method performBarangTerjual
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performStockOpname(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		    List locationLst = LocationDAO.getInstance().findAll(Order.asc("Code"));
		    request.setAttribute("locationLst", locationLst);
		    Session session = ItemDAO.getInstance().getSession();
		    
		    String itemSizeSql = "";
		    List itemSizeLst = new LinkedList();
		    /*itemSizeSql = "select a.item_size_id as {is.Id}, b.code as {is.Code}, b.name as {is.Name} from item a join item_size b on a.item_size_id=b.item_size_id where a.item_merk_id = "+form.getLong("itemMerkId")+" group by a.item_size_id ";
		    itemSizeLst = session.createSQLQuery(itemSizeSql)
					.addEntity("is", ItemSize.class)
					.list();*/
		    request.setAttribute("itemSizeLst", itemSizeLst);
		    
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
		    if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate");
		    List stockOpnameLst = new LinkedList();
		    String sql = "";
		    sql = "select a.item_id as {ipr.ItemId}, b.code as {ipr.Merk}, c.code as {ipr.Type}, a.name as {ipr.Name}, a.code as {ipr.Code}, d.code as {ipr.Color}, " +
		    	"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {ipr.NumberOfDigit} " +
		    	"from item a "+
		    	"join item_merk b on a.item_merk_id=b.item_merk_id " +
					"join item_type c on a.item_type_id=c.item_type_id " +
					"join item_color d on a.item_color_id=d.item_color_id " +
					"where a.item_merk_id="+form.getLong("itemMerkId")+" " +
					"group by a.item_type_id, a.item_color_id ";
		    stockOpnameLst = session.createSQLQuery(sql)
					.addEntity("ipr", ItemPosReportStockOpname.class)
					.list();
			
		    request.setAttribute("stockOpnameLst", stockOpnameLst);
		    
		    
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
		
	/** 
	 * Method performBarangTerjualPdf
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performStockOpnamePdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {

				
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
	 * Method performBarangTerjual
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performKomisiSpg(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		    List locationLst = LocationDAO.getInstance().findAll(Order.asc("Code"));
		    request.setAttribute("locationLst", locationLst);
		    Session session = ItemDAO.getInstance().getSession();
		    
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
		    if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate");
		    
		    
		    
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
		
	/** 
	 * Method performBarangTerjualPdf
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performKomisiSpgPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {

				
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
	 * Method performBarangTerjual
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performRekapReceivableCreditCard(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		    Session session = ItemDAO.getInstance().getSession();
		    List bankList = BankDAO.getInstance().findAll(Order.asc("Name"));
		    request.setAttribute("bankList", bankList);
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
		    if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate");
		    
		    List rekapReceivableCreditCardLst = new LinkedList();
		    String sql = "";
		    sql = "select a.location_id as {ipr.LocationId}, a.name as {ipr.Name}," +
		    	"IFNULL(0,0) as {ipr.BeginningGross}, " +
		    	"IFNULL(0,0) as {ipr.BeginningNetto}, " +
		    	"IFNULL(0,0) as {ipr.BeginningAdm}, " +
		    	"IFNULL((select sum(b.cash_amount) from pos_order b where b.location_id=a.location_id and b.pos_order_date >= :fromDate and b.pos_order_date <= :toDate),0) as {ipr.TransactionGross}, " +
		    	"IFNULL((select sum(c.amount) from paid_credit_card c join pos_order b on c.pos_order_id=b.pos_order_id where b.location_id=a.location_id and b.pos_order_date >= :fromDate and b.pos_order_date <= :toDate),0) as {ipr.TransactionNetto}, " +
		    	"IFNULL((select sum(c.adm) from paid_credit_card c join pos_order b on c.pos_order_id=b.pos_order_id where b.location_id=a.location_id and b.pos_order_date >= :fromDate and b.pos_order_date <= :toDate),0) as {ipr.TransactionAdm}, " +
		    	"IFNULL((select sum(c.amount+c.adm) from paid_credit_card c join pos_order b on c.pos_order_id=b.pos_order_id where b.location_id=a.location_id and b.pos_order_date >= :fromDate and b.pos_order_date <= :toDate and !isnull(c.paid_date)),0) as {ipr.PaidGross}, " +
		    	"IFNULL((select sum(c.amount) from paid_credit_card c join pos_order b on c.pos_order_id=b.pos_order_id where b.location_id=a.location_id and b.pos_order_date >= :fromDate and b.pos_order_date <= :toDate and !isnull(c.paid_date)),0) as {ipr.PaidNetto}, " +
		    	"IFNULL((select sum(c.adm) from paid_credit_card c join pos_order b on c.pos_order_id=b.pos_order_id where b.location_id=a.location_id and b.pos_order_date >= :fromDate and b.pos_order_date <= :toDate and !isnull(c.paid_date)),0) as {ipr.PaidAdm}, " +
		    	"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {ipr.NumberOfDigit} " +
		    	"from location a "+
					"group by a.location_id ";
		    rekapReceivableCreditCardLst = session.createSQLQuery(sql)
					.addEntity("ipr", ItemPosReportRekapReceivableCreditCard.class)
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
		    
		    request.setAttribute("rekapReceivableCreditCardLst", rekapReceivableCreditCardLst);
		    
		    
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
		
	/** 
	 * Method performBarangTerjualPdf
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performRekapReceivableCreditCardPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    
		    OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		    Session session = ItemDAO.getInstance().getSession();
		    List bankList = BankDAO.getInstance().findAll(Order.asc("Name"));
		    request.setAttribute("bankList", bankList);
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
		    if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate");
		    
		    List rekapReceivableCreditCardLst = new LinkedList();
		    String sql = "";
		    sql = "select a.location_id as {ipr.LocationId}, a.name as {ipr.Name}," +
		    	"IFNULL(0,0) as {ipr.BeginningGross}, " +
		    	"IFNULL(0,0) as {ipr.BeginningNetto}, " +
		    	"IFNULL(0,0) as {ipr.BeginningAdm}, " +
		    	"IFNULL((select sum(b.cash_amount) from pos_order b where b.location_id=a.location_id and b.pos_order_date >= :fromDate and b.pos_order_date <= :toDate),0) as {ipr.TransactionGross}, " +
		    	"IFNULL((select sum(c.amount) from paid_credit_card c join pos_order b on c.pos_order_id=b.pos_order_id where b.location_id=a.location_id and b.pos_order_date >= :fromDate and b.pos_order_date <= :toDate),0) as {ipr.TransactionNetto}, " +
		    	"IFNULL((select sum(c.adm) from paid_credit_card c join pos_order b on c.pos_order_id=b.pos_order_id where b.location_id=a.location_id and b.pos_order_date >= :fromDate and b.pos_order_date <= :toDate),0) as {ipr.TransactionAdm}, " +
		    	"IFNULL((select sum(c.amount+c.adm) from paid_credit_card c join pos_order b on c.pos_order_id=b.pos_order_id where b.location_id=a.location_id and b.pos_order_date >= :fromDate and b.pos_order_date <= :toDate and !isnull(c.paid_date)),0) as {ipr.PaidGross}, " +
		    	"IFNULL((select sum(c.amount) from paid_credit_card c join pos_order b on c.pos_order_id=b.pos_order_id where b.location_id=a.location_id and b.pos_order_date >= :fromDate and b.pos_order_date <= :toDate and !isnull(c.paid_date)),0) as {ipr.PaidNetto}, " +
		    	"IFNULL((select sum(c.adm) from paid_credit_card c join pos_order b on c.pos_order_id=b.pos_order_id where b.location_id=a.location_id and b.pos_order_date >= :fromDate and b.pos_order_date <= :toDate and !isnull(c.paid_date)),0) as {ipr.PaidAdm}, " +
		    	"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {ipr.NumberOfDigit} " +
		    	"from location a "+
					"group by a.location_id ";
		    rekapReceivableCreditCardLst = session.createSQLQuery(sql)
					.addEntity("ipr", ItemPosReportRekapReceivableCreditCard.class)
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
		    
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
				
				Table table2 = new Table(15 + bankList.size());
				table2.setWidth(100);
				float w2[] = new float[(bankList.size())+15];
				for (int g=0; g<((bankList.size())+15); g++) {
					if (g == 0) {
						w2[g] = 3;
					} else if (g == 1) {
						w2[g] = 10;
					} else if (g == 2) {
						w2[g] = 5;
					} else if (g == 3) {
						w2[g] = 5;
					} else if (g == 4) {
						w2[g] = 5;
					} else if (g == 5) {
						w2[g] = 5;
					} else if (g == 6) {
						w2[g] = 5;
					} else if (g == 7) {
						w2[g] = 5;
					} else if (g == 8) {
							w2[g] = 5;
					} else if (g == 9) {
							w2[g] = 5;
					} else if (g == 10) {
							w2[g] = 5;
					} else if (g == 11) {
							w2[g] = 5;
					} else if (g == 12) {
							w2[g] = 5;
					} else if (g == 13) {
							w2[g] = 5;
					} else if (g == (bankList.size())+14) {
						w2[g] = 5;
					} else {
						w2[g] = 22/bankList.size();
					}
				}
				table2.setWidths(w2);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setCellsFitPage(true);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(bankList.size()+15);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(bankList.size()+15);
				table2.addCell(cell);
				cell = new Cell(new Phrase("REKAP RECEIVABLE CREDIT CARD", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(bankList.size()+15);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(bankList.size()+15);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(bankList.size()+15);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(bankList.size()+15);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("STORE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("BEGINNING",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("TRANSACTION",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PAID",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ENDING",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setColspan(3);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				Iterator iterator = bankList.iterator();
				while (iterator.hasNext()) {
				  Bank bank = (Bank)iterator.next();
					cell = new Cell(new Phrase(bank.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
					cell.setBorderWidth(1);
					cell.setRowspan(2);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
					table2.addCell(cell);
				}
				cell = new Cell(new Phrase("TOTAL",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setRowspan(2);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);

				cell = new Cell(new Phrase("GROSS",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("NETTO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ADM",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("GROSS",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("NETTO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ADM",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("GROSS",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("NETTO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ADM",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("GROSS",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("NETTO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ADM",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT);
				table2.addCell(cell);
				
				table2.endHeaders();
				
				int i = 0;
				Iterator iterator2 = rekapReceivableCreditCardLst.iterator();
				while (iterator2.hasNext()) {
				    ItemPosReportRekapReceivableCreditCard card = (ItemPosReportRekapReceivableCreditCard)iterator2.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(card.getName(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(card.getFormatedBeginningGross(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(card.getFormatedBeginningNetto(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(card.getFormatedBeginningAdm(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(card.getFormatedTransactionGross(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(card.getFormatedTransactionNetto(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(card.getFormatedTransactionAdm(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(card.getFormatedPaidGross(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(card.getFormatedPaidNetto(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(card.getFormatedPaidAdm(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						
						iterator = bankList.iterator();
						while (iterator.hasNext()) {
						  Bank bank = (Bank)iterator.next();
						  cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
							cell.setBorder(Rectangle.RIGHT);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table2.addCell(cell);
						}
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						
				}
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(bankList.size()+15);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(bankList.size()+15);
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
	 * Method performBarangTerjual
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performDetailReceivableCreditCard(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		    List locationLst = LocationDAO.getInstance().findAll(Order.asc("Code"));
		    request.setAttribute("locationLst", locationLst);
		    List bankList = BankDAO.getInstance().findAll(Order.asc("Name"));
		    request.setAttribute("bankList", bankList);
		    Session session = ItemDAO.getInstance().getSession();
		    
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
		    if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate");
		   
		    List detailReceivableCreditCardLst = new LinkedList();
		    String sql = "";
		    sql = "select a.pos_order_id as {ipr.PosOrderId}, a.pos_order_date as {ipr.PosOrderDate}, a.credit_card_number as {ipr.CreditCardNumber}, a.credit_card_adm as {ipr.CreditCardAdm}, a.cash_amount as {ipr.TotalCostAfterDiscountAndTax}, b.paid_date as {ipr.PaidDate}, c.name as {ipr.Bank}, " +
		    	"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {ipr.NumberOfDigit} " +
		    	"from pos_order a "+
		    	"join paid_credit_card b on a.pos_order_id=b.pos_order_id " +
					"join bank c on a.bank_id=c.bank_id " +
					"where a.location_id="+form.getLong("locationId")+" " +
					"and a.pos_order_date >= :fromDate and a.pos_order_date <= :toDate " +
					"group by a.pos_order_id ";
		    detailReceivableCreditCardLst = session.createSQLQuery(sql)
					.addEntity("ipr", ItemPosReportDetailReceivableCreditCard.class)
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
		    
		    request.setAttribute("detailReceivableCreditCardLst", detailReceivableCreditCardLst);
		    
		    
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
	}
	
		
	/** 
	 * Method performBarangTerjualPdf
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performDetailReceivableCreditCardPdf(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		ItemForm form = (ItemForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {

		    OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		    Location location = LocationDAO.getInstance().get(form.getLong("locationId"));
		    List bankList = BankDAO.getInstance().findAll(Order.asc("Name"));
		    
		    Session session = ItemDAO.getInstance().getSession();
		    
		    if (form.getCalendar("fromDate")==null) form.setCurentCalendar("fromDate");
		    if (form.getCalendar("toDate")==null) form.setCurentCalendar("toDate");
		   
		    List detailReceivableCreditCardLst = new LinkedList();
		    String sql = "";
		    sql = "select a.pos_order_id as {ipr.PosOrderId}, a.pos_order_date as {ipr.PosOrderDate}, a.credit_card_number as {ipr.CreditCardNumber}, a.credit_card_adm as {ipr.CreditCardAdm}, a.cash_amount as {ipr.TotalCostAfterDiscountAndTax}, b.paid_date as {ipr.PaidDate}, c.name as {ipr.Bank}, " +
		    	"(select e.number_of_digit from organization_setup e where e.organization_id=a.organization_id) as {ipr.NumberOfDigit} " +
		    	"from pos_order a "+
		    	"join paid_credit_card b on a.pos_order_id=b.pos_order_id " +
					"join bank c on a.bank_id=c.bank_id " +
					"where a.location_id="+form.getLong("locationId")+" " +
					"and a.pos_order_date >= :fromDate and a.pos_order_date <= :toDate " +
					"group by a.pos_order_id ";
		    detailReceivableCreditCardLst = session.createSQLQuery(sql)
					.addEntity("ipr", ItemPosReportDetailReceivableCreditCard.class)
					.setDate("fromDate", new Date(form.getCalendar("fromDate").getTime().getTime()))
					.setDate("toDate", new Date(form.getCalendar("toDate").getTime().getTime()))
					.list();
		    
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
				
				Table table2 = new Table(9 + bankList.size());
				table2.setWidth(100);
				float w2[] = new float[(bankList.size())+9];
				for (int g=0; g<((bankList.size())+9); g++) {
					if (g == 0) {
						w2[g] = 5;
					} else if (g == 1) {
						w2[g] = 7;
					} else if (g == 2) {
						w2[g] = 10;
					} else if (g == 3) {
						w2[g] = 10;
					} else if (g == 4) {
						w2[g] = 8;
					} else if (g == 5) {
						w2[g] = 10;
					} else if (g == 6) {
						w2[g] = 10;
					} else if (g == 7) {
						w2[g] = 7;
					} else if (g == (bankList.size())+8) {
						w2[g] = 10;
					} else {
						w2[g] = 23/bankList.size();
					}
				}
				table2.setWidths(w2);
				table2.setBorder(Rectangle.NO_BORDER);
				table2.setCellsFitPage(true);
				table2.setBorderWidth(1);
				table2.setPadding(1);
				table2.setSpacing(0);
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(bankList.size()+9);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(bankList.size()+9);
				table2.addCell(cell);
				cell = new Cell(new Phrase("DETAIL RECEIVABLE CREDIT CARD", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(bankList.size()+9);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(bankList.size()+9);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(bankList.size()+9);
				table2.addCell(cell);
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(bankList.size()+9);
				table2.addCell(cell);
				
				cell = new Cell(new Phrase("NO",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("DATE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("CREDIT CARD",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("POINT REEDEM",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("ADM",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PAYMENT",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("PAID DATE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("BANK",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				Iterator iterator = bankList.iterator();
				while (iterator.hasNext()) {
				  Bank bank = (Bank)iterator.next();
					cell = new Cell(new Phrase(bank.getName(),FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
					cell.setBorderWidth(1);
					cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
					table2.addCell(cell);
				}
				cell = new Cell(new Phrase("DIFFERENCE",FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(new Color(0xC0, 0xC0, 0xC0));
				cell.setBorderWidth(1);
				cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
				table2.addCell(cell);
				table2.endHeaders();
				
				int i = 0;
				Iterator iterator2 = detailReceivableCreditCardLst.iterator();
				while (iterator2.hasNext()) {
				    ItemPosReportDetailReceivableCreditCard card = (ItemPosReportDetailReceivableCreditCard)iterator2.next();
				    cell = new Cell(new Phrase(++i+".", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(card.getFormatedPosOrderDate(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(card.getCreditCardNumber(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(card.getFormatedCreditCardAdm(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(card.getFormatedTotalCostAfterDiscountAndTax(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table2.addCell(cell);
				    cell = new Cell(new Phrase(card.getFormatedPaidDate(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						cell = new Cell(new Phrase(card.getBank(), FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						iterator = bankList.iterator();
						while (iterator.hasNext()) {
						  Bank bank = (Bank)iterator.next();
						  cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
							cell.setBorder(Rectangle.RIGHT);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table2.addCell(cell);
						}
						cell = new Cell(new Phrase("", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
						cell.setBorder(Rectangle.RIGHT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table2.addCell(cell);
						
				}
				
				cell = new Cell(new Phrase(" ", FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
				cell.setBorder(Rectangle.TOP);
				cell.setColspan(bankList.size()+9);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table2.addCell(cell);
				cell = new Cell(new Phrase("Printed By : "+users.getUserName()+"            Checked By,           Approved By,", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setColspan(bankList.size()+9);
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