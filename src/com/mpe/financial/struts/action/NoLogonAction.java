/*
 * Created on Mar 14, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mpe.financial.struts.action;

import java.awt.Color;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jCharts.axisChart.AxisChart;
import org.jCharts.chartData.AxisChartDataSet;
import org.jCharts.chartData.DataSeries;
import org.jCharts.encoders.ServletEncoderHelper;
import org.jCharts.properties.AxisProperties;
import org.jCharts.properties.BarChartProperties;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.types.ChartType;

import com.mpe.common.CommonConstants;
import com.mpe.financial.model.GeneralLedgerReport;
import com.mpe.financial.model.Inventory;
import com.mpe.financial.model.ItemFirstBalance;
import com.mpe.financial.model.ItemPrice;
import com.mpe.financial.model.ItemPriceCategory;
import com.mpe.financial.model.ItemPricePK;
import com.mpe.financial.model.ItemUnit;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.ProfitLossReport;
import com.mpe.financial.model.TopItemSales;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.ChartOfAccountDAO;
import com.mpe.financial.model.dao.GeneralLedgerDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemFirstBalanceDAO;
import com.mpe.financial.model.dao.ItemPriceCategoryDAO;
import com.mpe.financial.model.dao.ItemPriceDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;

/**
 * @author Agung Hadiwaluyo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NoLogonAction extends Action {
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
		ActionForward forward = null;
		String action = mapping.getParameter();
		HttpSession session = request.getSession();
		Users users = (Users)session.getAttribute(CommonConstants.USER);
		if (users!=null) {
			if ("".equalsIgnoreCase(action)) {
				forward = mapping.findForward("home");
			} else if ("BANKCHARTOFACCOUNTCHART".equalsIgnoreCase(action)) {
				forward = performBankChartOfAccountChart(mapping, form, request, response);
			} else if ("PROFITLOSSCHART".equalsIgnoreCase(action)) {
				forward = performProfitLossChart(mapping, form, request, response);
			} else if ("UPLOADDATA".equalsIgnoreCase(action)) {
				forward = performUploadData(mapping, form, request, response);
			} else if ("TOPSALESCHART".equalsIgnoreCase(action)) {
				forward = performTopSalesChart(mapping, form, request, response);
			}
			return forward;
		} else {
			return mapping.findForward("index");
		}
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
	private ActionForward performBankChartOfAccountChart(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		String m[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		java.util.Calendar calendar = new java.util.GregorianCalendar();
		try {
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			// bank type balance
			String sql = "select a.chart_of_account_id as {gl.ChartOfAccountId}, a.number as {gl.Number}, a.name as {gl.Name}, a.type as {gl.Type}, a.groups as {gl.Groups}, a.is_debit as {gl.Debit}, " +
				"IFNULL((select sum(b.amount) from general_ledger b where b.chart_of_account_id=a.chart_of_account_id and b.organization_id = :organizationId and b.is_setup='Y' and b.is_closed='N'), 0) as {gl.FirstSetupAmount}, " +
				"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :setupDate and d.journal_date < :fromDate and d.is_posted = 'Y'), 0) as {gl.PreviousAmount}, " +
				"IFNULL((select sum(c.amount * d.exchange_rate) from journal_detail c join journal d on c.journal_id=d.journal_id where c.chart_of_account_id=a.chart_of_account_id and d.organization_id = :organizationId and d.journal_date >= :fromDate and d.journal_date <= :toDate and d.is_posted = 'Y'), 0) as {gl.Amount}, " +
				"(select e.number_of_digit from organization_setup e where e.organization_id=:organizationId) as {gl.NumberOfDigit} " +
				"from chart_of_account a where a.Type = 'Bank'" +
				"";
			List bankChartOfAccountLst = session.createSQLQuery(sql)
			.addEntity("gl", GeneralLedgerReport.class)
			.setLong("organizationId", users.getOrganization().getId())
			.setDate("setupDate", new Date(organizationSetup.getSetupDate().getTime()))
			.setDate("fromDate", new Date(organizationSetup.getSetupDate().getTime()))
			.setDate("toDate", new Date((new GregorianCalendar()).getTime().getTime()))
			.list();
			
			// render chart
			String[] xAxisLabels= new String[bankChartOfAccountLst.size()];
			double[][] data= new double[1][bankChartOfAccountLst.size()];
			Iterator iterator = bankChartOfAccountLst.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				GeneralLedgerReport generalLedgerReport = (GeneralLedgerReport)iterator.next();
				xAxisLabels[i] = generalLedgerReport.getName();
				data[0][i] = generalLedgerReport.getEndAmount();
				i++;
			}
			DataSeries dataSeries = new DataSeries(xAxisLabels, null, null, null );
			
			String[] legendLabels= {"Cash - Bank ("+m[calendar.get(java.util.Calendar.MONTH)]+" "+calendar.get(java.util.Calendar.YEAR)+")"};
			java.awt.Paint[] paints= new java.awt.Paint[]{ Color.red.darker() };
			
			BarChartProperties barChartProperties= new BarChartProperties();
			//ValueLabelRenderer valueLabelRenderer = new ValueLabelRenderer( false, true, -1 );
			//valueLabelRenderer.setValueLabelPosition(ValueLabelPosition.ON_TOP );
			//valueLabelRenderer.useVerticalLabels(false);
			//barChartProperties.addPostRenderEventListener( valueLabelRenderer );

			AxisChartDataSet axisChartDataSet= new AxisChartDataSet( data, legendLabels, paints, ChartType.BAR, barChartProperties );
			dataSeries.addIAxisPlotDataSet( axisChartDataSet );

			ChartProperties chartProperties= new ChartProperties();

			// ---to make this plot horizontally, pass true to the AxisProperties Constructor
			AxisProperties axisProperties= new AxisProperties( true );
			//AxisProperties axisProperties= new AxisProperties();
			LegendProperties legendProperties= new LegendProperties();
			AxisChart axisChart= new AxisChart( dataSeries, chartProperties, axisProperties, legendProperties, 300, 200);

			ServletEncoderHelper.encodeJPEG13(axisChart, 1.0f, response);
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
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
	private ActionForward performProfitLossChart(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		String m[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		java.util.Calendar fromDate = new java.util.GregorianCalendar();
		fromDate.set(Calendar.DAY_OF_MONTH, 1);
		java.util.Calendar toDate = new java.util.GregorianCalendar();
		try {
			//OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			// bank type balance
			String sql = "select a.chart_of_account_id as {pf.ChartOfAccountId}, a.number as {pf.Number}, a.name as {pf.Name}, a.type as {pf.Type}, a.groups as {pf.Groups}, a.is_debit as {pf.Debit}, " +
				"IFNULL((select sum(b.amount * c.exchange_rate) from journal_detail b left join journal c on b.journal_id = c.journal_id where a.chart_of_account_id = b.chart_of_account_id and c.organization_id = :organizationId and c.journal_date >= :fromDate and c.journal_date <= :toDate and c.is_posted = 'Y'), 0) as {pf.Amount}, " +
				"(select d.number_of_digit from organization_setup d where d.organization_id=:organizationId) as {pf.NumberOfDigit} " +
				"from chart_of_account a " +
				"where a.groups = :groups " +
				"";
			List revenueLst = session.createSQLQuery(sql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Revenue")
				.setDate("fromDate", new Date(fromDate.getTime().getTime()))
				.setDate("toDate", new Date(toDate.getTime().getTime()))
				.list();
			
			List expenseLst = session.createSQLQuery(sql)
				.addEntity("pf", ProfitLossReport.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setString("groups", "Expense")
				.setDate("fromDate", new Date(fromDate.getTime().getTime()))
				.setDate("toDate", new Date(toDate.getTime().getTime()))
				.list();
			
			double revenueTotal = 0;
			double expenseTotal = 0;
			Iterator iterator = revenueLst.iterator();
			while (iterator.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator.next();
				revenueTotal = revenueTotal + profitLossReport.getAmount();
			}
			Iterator iterator2 = expenseLst.iterator();
			while (iterator2.hasNext()) {
				ProfitLossReport profitLossReport = (ProfitLossReport)iterator2.next();
				expenseTotal = expenseTotal + profitLossReport.getAmount();
			}
			
			// render chart
			String[] xAxisLabels= {"Revenue", "Expense", "Profit"};
			DataSeries dataSeries = new DataSeries(xAxisLabels, null, null, null );

			double[][] data= new double[][]{{ revenueTotal, expenseTotal, (revenueTotal-expenseTotal)}};
			String[] legendLabels= {"Profit - Loss ("+m[fromDate.get(java.util.Calendar.MONTH)]+" "+fromDate.get(java.util.Calendar.YEAR)+")"};
			java.awt.Paint[] paints= new java.awt.Paint[]{ Color.blue.darker() };
			
			BarChartProperties barChartProperties= new BarChartProperties();
			//ValueLabelRenderer valueLabelRenderer = new ValueLabelRenderer( false, true, -1 );
			//valueLabelRenderer.setValueLabelPosition(ValueLabelPosition.ON_TOP );
			//valueLabelRenderer.useVerticalLabels(false);
			//barChartProperties.addPostRenderEventListener( valueLabelRenderer );

			AxisChartDataSet axisChartDataSet= new AxisChartDataSet( data, legendLabels, paints, ChartType.BAR, barChartProperties );
			dataSeries.addIAxisPlotDataSet( axisChartDataSet );

			ChartProperties chartProperties= new ChartProperties();

			// ---to make this plot horizontally, pass true to the AxisProperties Constructor
			//AxisProperties axisProperties= new AxisProperties( true );
			AxisProperties axisProperties= new AxisProperties();
			LegendProperties legendProperties= new LegendProperties();
			AxisChart axisChart= new AxisChart( dataSeries, chartProperties, axisProperties, legendProperties, 300, 150);

			ServletEncoderHelper.encodeJPEG13(axisChart, 1.0f, response);
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
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
	private ActionForward performUploadData(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			/*
			File file = new File("D:/outsource/java/web/financial/graco/0.1/doc/coa.xls");
			// read excel file
			Workbook workbook = Workbook.getWorkbook(file);
			// read sheet
			Sheet sheet = workbook.getSheet(0);
			if (1==1) {
				int x = 2, end = 0;
				while (end==0) {
					Cell ax =null, bx =null;
					try {
						ax = sheet.getCell(0,x); // Kode baru
						bx = sheet.getCell(1,x);	// Nama
						
					}catch (Exception exx) {
					}
					// check end
					if (ax == null || ax.getContents().length()==0) {
						end = 1;
					} else {
						ChartOfAccount chartOfAccount = (ChartOfAccount)ChartOfAccountDAO.getInstance().getSession().createCriteria(ChartOfAccount.class)
						.add(Restrictions.eq("Number", new String(ax!=null?ax.getContents().trim():""))).uniqueResult();
						if (chartOfAccount==null) {
						    chartOfAccount = new ChartOfAccount();
						    chartOfAccount.setNumber(ax!=null?ax.getContents().trim():"");
						    chartOfAccount.setName(bx!=null?bx.getContents().trim():"");
						    ChartOfAccountDAO.getInstance().save(chartOfAccount);
						} else {
						    chartOfAccount.setNumber(ax!=null?ax.getContents().trim():"");
						    chartOfAccount.setName(bx!=null?bx.getContents().trim():"");
						    ChartOfAccountDAO.getInstance().update(chartOfAccount);
						}
						x++;
					}
				}
			}
			*/
			
			/*
			File file = new File("D:/outsource/java/web/financial/graco/0.1/doc/vendor.xls");
			// read excel file
			Workbook workbook = Workbook.getWorkbook(file);
			// read sheet
			Sheet sheet = workbook.getSheet(0);
			if (1==1) {
				int x = 1, end = 0;
				while (end==0) {
					Cell ax =null, bx =null, cx = null, dx = null, ex = null, fx = null, gx=null, hx=null;
					try {
						ax = sheet.getCell(0,x); // Kode baru
						bx = sheet.getCell(1,x); // Nama
						cx = sheet.getCell(2,x); // Alamat
						dx = sheet.getCell(3,x); // City
						ex = sheet.getCell(4,x); // Import?
						fx = sheet.getCell(5,x); // Kode suplier
						gx = sheet.getCell(6,x);
						hx = sheet.getCell(7,x);
					}catch (Exception exx) {
					}
					// check end
					if (bx == null || bx.getContents().length()==0) {
						end = 1;
					} else {
						Vendors vendors = null;
						if (vendors==null) {
						    vendors = new Vendors();
						    vendors.setCode(ax!=null?ax.getContents().trim():"");
						    vendors.setCompany(bx!=null?bx.getContents().trim():"");
						    vendors.setAddress(cx!=null?cx.getContents().trim():"");
						    vendors.setNpwp(ex!=null?ex.getContents().trim():"");
						    vendors.setTelephone(fx!=null?fx.getContents().trim():"");
						    vendors.setFax(gx!=null?gx.getContents().trim():"");
						    if (hx!=null && hx.getContents().trim().length()>0) {
						    	VendorsCommunication vendorsCommunication = new VendorsCommunication();
						    	VendorsCommunicationPK vendorsCommunicationPK = new VendorsCommunicationPK();
						    	vendorsCommunicationPK.setContactPerson(hx.getContents().trim());
						    	vendorsCommunicationPK.setVendors(vendors);
						    	vendorsCommunication.setId(vendorsCommunicationPK);
						    	Set set = new LinkedHashSet();
						    	set.add(vendorsCommunication);
						    	vendors.setVendorsCommunications(set);
						    }
						    vendors.setActive(true);
						    vendors.setCreateBy(users);
						    vendors.setOrganization(users.getOrganization());
							VendorsDAO.getInstance().save(vendors);
						} else {
						}
						x++;
					}
				}
			}
			*/
			
			/*
			File file = new File("D:/outsource/java/web/financial/graco/0.1/doc/customer.xls");
			// read excel file
			Workbook workbook = Workbook.getWorkbook(file);
			// read sheet
			Sheet sheet = workbook.getSheet(0);
			if (1==1) {
				int x = 1, end = 0;
				while (end==0) {
					Cell ax =null, bx =null, cx = null, dx = null, ex = null, fx = null, gx=null, hx=null, ix=null, jx=null;
					try {
						ax = sheet.getCell(0,x); // Kode baru
						bx = sheet.getCell(1,x); // Nama
						cx = sheet.getCell(2,x); // Alamat
						dx = sheet.getCell(3,x); // City
						ex = sheet.getCell(4,x); // Import?
						fx = sheet.getCell(5,x); // Kode suplier
						gx = sheet.getCell(6,x);
						hx = sheet.getCell(7,x);
						ix = sheet.getCell(8,x);
						jx = sheet.getCell(9,x);
					}catch (Exception exx) {
					}
					// check end
					if (bx == null || bx.getContents().length()==0) {
						end = 1;
					} else {
						Customers customers = null;
						if (customers==null) {
						    customers = new Customers();
						    customers.setCode(cx!=null?cx.getContents().trim():"");
						    customers.setCompany(bx!=null?bx.getContents().trim():"");
						    customers.setAddress(ex!=null?ex.getContents().trim():"");
						    customers.setNpwp(dx!=null?dx.getContents().trim():"");
						    customers.setTelephone(gx!=null?gx.getContents().trim():"");
						    customers.setFax(hx!=null?hx.getContents().trim():"");
						    if (fx!=null && fx.getContents().trim().length()>0) {
						    	CustomersAddress customersAddress = new CustomersAddress();
						    	CustomersAddressPK customersAddressPK = new CustomersAddressPK();
						    	customersAddressPK.setDeliveryAddress("Y");
						    	customersAddressPK.setInvoiceAddress("N");
						    	customersAddressPK.setInvoiceTaxAddress("N");
						    	customersAddress.setAddress(fx.getContents().trim());
						    	customersAddressPK.setCustomers(customers);
						    	customersAddress.setId(customersAddressPK);
						    	customersAddress.setCode(cx!=null?cx.getContents().trim():"");
						    	Set set = new LinkedHashSet();
						    	set.add(customersAddress);
						    	customers.setCustomersAddresses(set);
						    }
						    if (jx!=null && jx.getContents().trim().length()>0) {
						    	CustomersCommunication customersCommunication = new CustomersCommunication();
						    	CustomersCommunicationPK customersCommunicationPK = new CustomersCommunicationPK();
						    	customersCommunicationPK.setContactPerson(jx.getContents().trim());
						    	customersCommunicationPK.setCustomers(customers);
						    	customersCommunication.setId(customersCommunicationPK);
						    	Set set = new LinkedHashSet();
						    	set.add(customersCommunication);
						    	customers.setCustomersCommunications(set);
						    }
						    customers.setActive(true);
						    customers.setCreateBy(users);
						    customers.setOrganization(users.getOrganization());
							CustomersDAO.getInstance().save(customers);
						} else {
						}
						x++;
					}
				}
			}
			*/
			
			
/*			File file = new File("D:/Project/Java/web/financial/graco/0.1/doc/all stock 30 nov' 07.xls");
			// read excel file
			Workbook workbook = Workbook.getWorkbook(file);
			// read sheet
			Sheet sheet = workbook.getSheet(0);
			if (1==1) {
				int x = 3, end = 0;
				while (end==0) {
					Cell ax =null, bx =null, cx = null;
					try {
						ax = sheet.getCell(0,x); // Jenis
						bx = sheet.getCell(1,x);	// Gender
						cx = sheet.getCell(2,x); // Merk
					}catch (Exception exx) {
					}
					Calendar calendar = new GregorianCalendar();
					// check end
					if (cx == null || cx.getContents().length()==0) {
						end = 1;
					} else {
						Inventory item = (Inventory)InventoryDAO.getInstance().getSession().createCriteria(Inventory.class)
						.add(Restrictions.eq("Code", new String(ax!=null?ax.getContents().trim():""))).uniqueResult();
						if (item==null) {
						    item = new Inventory();
						    // item type

						    item.setCode(ax!=null?ax.getContents().trim():"");
						    item.setName(bx!=null?bx.getContents().trim():"");
						    Set set = new LinkedHashSet();
						    ItemPrice itemPrice = new ItemPrice();
						    itemPrice.setCurrency(organizationSetup.getDefaultCurrency());
						    itemPrice.setDefault(true);
						    ItemPricePK itemPricePK = new ItemPricePK();
						    itemPricePK.setItem(item);
						    ItemPriceCategory itemPriceCategory = ItemPriceCategoryDAO.getInstance().get(1);
						    itemPricePK.setItemPriceCategory(itemPriceCategory);
						    itemPrice.setId(itemPricePK);
						    itemPrice.setPrice(0);
						    ItemUnit itemUnit = (ItemUnit)ItemUnitDAO.getInstance().getSession().createCriteria(ItemUnit.class)
								.add(Restrictions.eq("Symbol", new String("PCS"))).uniqueResult();
						    item.setItemUnit(itemUnit);
						    itemPrice.setItemUnit(itemUnit);
						    set.add(itemPrice);
						    item.setItemPrices(set);
						    item.setType("Product");
						    item.setCurrency(organizationSetup.getDefaultCurrency());
						    item.setActive(true);
						    item.setCreateBy(users);
						    item.setOrganization(users.getOrganization());
						    log.info("[ ITEM : "+item.getCode()+" ]");
							InventoryDAO.getInstance().save(item);
							
							ItemFirstBalance firstBalance = new ItemFirstBalance();
							firstBalance.setCurrency(organizationSetup.getDefaultCurrency());
							firstBalance.setExchangeRate(1);
							firstBalance.setItem(item);
							firstBalance.setItemUnit(itemUnit);
							firstBalance.setOrganization(organizationSetup);
							firstBalance.setPrice(0);
							firstBalance.setQuantity(Double.parseDouble(cx.getContents()));
							firstBalance.setFirstBalanceDate(calendar.getTime());
							ItemFirstBalanceDAO.getInstance().save(firstBalance);
							
							
						} else {
							ItemFirstBalance firstBalance = (ItemFirstBalance)ItemFirstBalanceDAO.getInstance().getSession().createCriteria(ItemFirstBalance.class)
								.add(Restrictions.eq("Item.Id", new Long(item.getId()))).setMaxResults(1).uniqueResult();
							if (firstBalance==null && item!=null) {
								firstBalance = new ItemFirstBalance();
								firstBalance.setCurrency(organizationSetup.getDefaultCurrency());
								firstBalance.setExchangeRate(1);
								firstBalance.setItem(item);
								firstBalance.setItemUnit(item.getItemUnit());
								firstBalance.setOrganization(organizationSetup);
								firstBalance.setPrice(0);
								firstBalance.setQuantity(Double.parseDouble(cx.getContents()));
								firstBalance.setFirstBalanceDate(calendar.getTime());
								ItemFirstBalanceDAO.getInstance().save(firstBalance);
							}
						}
						x++;
					}
				}
			}*/
			
			File file = new File("D:/Project/Java/web/financial/graco/0.1/doc/new2/Harga Jual SRM-GRACO-07a110407.xls");
			// read excel file
			Workbook workbook = Workbook.getWorkbook(file);
			// read sheet
			Sheet sheet = workbook.getSheet(0);
			if (1==1) {
				int x = 3, end = 0;
				while (end==0) {
					Cell ax =null, bx =null, cx = null;
					try {
						ax = sheet.getCell(0,x); // Jenis
						bx = sheet.getCell(1,x);	// Gender
						cx = sheet.getCell(2,x); // Merk
					}catch (Exception exx) {
					}
					Calendar calendar = new GregorianCalendar();
					// check end
					if (cx == null || cx.getContents().length()==0) {
						end = 1;
					} else {
						Inventory item = (Inventory)InventoryDAO.getInstance().getSession().createCriteria(Inventory.class)
						.add(Restrictions.eq("Code", new String(ax!=null?ax.getContents().trim():""))).uniqueResult();
						if (item==null) {
						    
							
							
						} else {
							
							String sql = "" +
									"update item_price set price = "+cx.getContents().trim()+" where item_id="+item.getId();
							
							ItemPriceDAO.getInstance().getSession().createSQLQuery(sql).executeUpdate();
							
						}
						x++;
					}
				}
			}
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("confirm");
		}finally {
			try {
				ItemDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("confirm");
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
	private ActionForward performTopSalesChart(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		String m[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		Calendar fromDate = new java.util.GregorianCalendar();
		fromDate.set(Calendar.DAY_OF_MONTH, 1);
		Calendar toDate = new java.util.GregorianCalendar();
		try {
			//OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = ChartOfAccountDAO.getInstance().getSession();
			// bank type balance
			String sql = "" +
				"SELECT a.item_id as {tis.ItemId}, a.code as {tis.Code}, a.name as {tis.Name}, " +
				"IFNULL((select sum(b.quantity) from sales_order_detail b join sales_order c on b.sales_order_id=c.sales_order_id where b.item_id=a.item_id and c.order_date >= :fromDate and c.order_date <= :toDate and c.organization_id = :organizationId and c.status<>'START'), 0) as {tis.Quantity} " +
				"from item a " +
				"order by (select sum(b.quantity) from sales_order_detail b join sales_order c on b.sales_order_id=c.sales_order_id where b.item_id=a.item_id and c.order_date >= :fromDate and c.order_date <= :toDate and c.organization_id = :organizationId and c.status<>'START') DESC " +
				"limit 0, 5 " +
				"";
			List topItemSalesLst = session.createSQLQuery(sql)
				.addEntity("tis", TopItemSales.class)
				.setLong("organizationId", users.getOrganization().getId())
				.setDate("fromDate", new Date(fromDate.getTime().getTime()))
				.setDate("toDate", new Date(toDate.getTime().getTime()))
				.list();
			
			
			
			// render chart
			String[] xAxisLabels= new String[topItemSalesLst.size()];
			double[][] data= new double[1][topItemSalesLst.size()];
			Iterator iterator = topItemSalesLst.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				TopItemSales topItemSales = (TopItemSales)iterator.next();
				xAxisLabels[i] = topItemSales.getCode();
				data[0][i] = topItemSales.getQuantity();
				i++;
			}
			DataSeries dataSeries = new DataSeries(xAxisLabels, null, null, null );
			
			String[] legendLabels= {"Top Item Sales ("+m[fromDate.get(java.util.Calendar.MONTH)]+" "+fromDate.get(java.util.Calendar.YEAR)+")"};
			java.awt.Paint[] paints= new java.awt.Paint[]{ Color.red.darker() };
			
			BarChartProperties barChartProperties= new BarChartProperties();
			//ValueLabelRenderer valueLabelRenderer = new ValueLabelRenderer( false, true, -1 );
			//valueLabelRenderer.setValueLabelPosition(ValueLabelPosition.ON_TOP );
			//valueLabelRenderer.useVerticalLabels(false);
			//barChartProperties.addPostRenderEventListener( valueLabelRenderer );

			AxisChartDataSet axisChartDataSet= new AxisChartDataSet(data, legendLabels, paints, ChartType.BAR, barChartProperties);
			dataSeries.addIAxisPlotDataSet( axisChartDataSet );

			ChartProperties chartProperties= new ChartProperties();

			// ---to make this plot horizontally, pass true to the AxisProperties Constructor
			AxisProperties axisProperties= new AxisProperties();
			//AxisProperties axisProperties= new AxisProperties();
			LegendProperties legendProperties= new LegendProperties();
			AxisChart axisChart= new AxisChart( dataSeries, chartProperties, axisProperties, legendProperties, 300, 150);

			ServletEncoderHelper.encodeJPEG13(axisChart, 1.0f, response);
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				GeneralLedgerDAO.getInstance().closeSessionForReal();
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
