//Created by Xslt generator for Eclipse.
//XSL :  not found (java.io.FileNotFoundException:  (The system cannot find the path specified))
//Default XSL used : easystruts.jar$org.easystruts.xslgen.JavaClass.xsl

package com.mpe.financial.struts.action;


import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Restrictions;

import com.mpe.common.CommonConstants;
import com.mpe.financial.struts.form.LogonForm;
import com.mpe.financial.model.Inventory;
import com.mpe.financial.model.ItemCategory;
import com.mpe.financial.model.ItemFirstBalance;
import com.mpe.financial.model.Organization;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemCategoryDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemFirstBalanceDAO;
import com.mpe.financial.model.dao.OrganizationDAO;
import com.mpe.financial.model.dao.UsersDAO;

/** 
* LogonAction.java created by EasyStruts - XsltGen.
* http://easystruts.sf.net
* created on 06-11-2003
* 
* XDoclet definition:
* @struts:action path="/logon" name="logonForm" input="/user/logon.jsp" parameter="LOGON" validate="true"
*/
public class Import extends Action {
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
public ActionForward execute(
	ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response)
	throws Exception {
	String action = mapping.getParameter();
	if ("".equalsIgnoreCase(action)) {
		return mapping.findForward("index");
	} else if ("IMPORT".equalsIgnoreCase(action)) {
		return performImport(mapping, form, request, response);
	} else {
		return mapping.findForward("index");	
	}
}

/** 
* Method performLogon
* @param ActionMapping mapping
* @param ActionForm form
* @param HttpServletRequest request
* @param HttpServletResponse response
* @return ActionForward
* @throws Exception
*/
private ActionForward performImport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
	LogonForm form = (LogonForm) actionForm;
	HttpSession httpSession = request.getSession();  
	try {
		OrganizationSetup organization = (OrganizationSetup)((Users)httpSession.getAttribute(CommonConstants.USER)).getOrganization();
		/*
		File file = new File("D:\\project\\java\\web\\financial\\graco\\0.1\\doc\\new/280307.xls");
		// read excel file
		Workbook workbook = Workbook.getWorkbook(file);
		// read sheet
		Sheet sheet = workbook.getSheet(0);
		if (1==1) {
			int x = 2, end = 0;
			while (end==0) {
				Cell ax =null, bx =null, cx = null;
				try {
					//ax = sheet.getCell(0,x); // Jenis
					//bx = sheet.getCell(1,x);	// Gender
				  
					ax = sheet.getCell(0,x); // Merk
					bx = sheet.getCell(1,x); // Article
					cx = sheet.getCell(2,x); // Color
					
				}catch (Exception exx) {
				}
				// check end
				if (bx == null || bx.getContents().length()==0) {
					end = 1;
				} else {
					Inventory item = null;
					item = (Inventory)InventoryDAO.getInstance().getSession().createCriteria(Inventory.class)
						.add(Restrictions.eq("Code", new String(bx!=null?bx.getContents().trim():""))).uniqueResult();
					if (item==null) {
					    log.info("item : "+bx.getContents().trim());
						item = new Inventory();
						item.setType("PRODUCT");
						item.setCode(bx.getContents().trim());
						item.setName(cx.getContents().trim());
						item.setActive(true);
						Organization organization = OrganizationDAO.getInstance().get(1);
						item.setOrganization(organization);
						InventoryDAO.getInstance().save(item);
						
						
					}
					
					x++;
				}
			}
		}
		*/
		/*
		File file = new File("D:\\project\\java\\web\\financial\\graco\\0.1\\doc\\new/Stock_Kategori.xls");
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet sheet = workbook.getSheet(0);
		if (1==1) {
			int x = 2, end = 0;
			while (end==0) {
				Cell ax =null, bx =null, cx = null;
				try {
					//ax = sheet.getCell(0,x); // Jenis
					//bx = sheet.getCell(1,x);	// Gender
				  
					ax = sheet.getCell(0,x); // Merk
					bx = sheet.getCell(1,x); // Article
					cx = sheet.getCell(3,x); // Color
					
				}catch (Exception exx) {
				}
				// check end
				if (bx == null || bx.getContents().length()==0) {
					end = 1;
				} else {
					Inventory item = (Inventory)InventoryDAO.getInstance().getSession().createCriteria(Inventory.class).add(Restrictions.eq("Code", bx.getContents().trim())).uniqueResult();
					//item = (Inventory)InventoryDAO.getInstance().getSession().createCriteria(Inventory.class)
					//.add(Restrictions.eq("Code", new String(gx!=null?gx.getContents().toUpperCase():""))).uniqueResult();
					if (item!=null) {
						ItemCategory itemCategory = (ItemCategory)ItemCategoryDAO.getInstance().getSession().createCriteria(ItemCategory.class).add(Restrictions.eq("Name", cx.getContents().trim())).uniqueResult();
						if (itemCategory==null) {
							itemCategory = new ItemCategory();
							itemCategory.setName(cx.getContents().trim());
							ItemCategoryDAO.getInstance().save(itemCategory);
						}
						item.setItemCategory(itemCategory);
						ItemDAO.getInstance().update(item);
					}
					
					x++;
				}
			}
		}
		*/
		
		File file = new File("D:\\project\\java\\web\\financial\\graco\\0.1\\doc\\new/280307.xls");
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet sheet = workbook.getSheet(0);
		if (1==1) {
			int x = 2, end = 0;
			while (end==0) {
				Cell ax =null, bx =null, cx = null;
				try {
					//ax = sheet.getCell(0,x); // Jenis
					//bx = sheet.getCell(1,x);	// Gender
				  
					ax = sheet.getCell(0,x); // Merk
					bx = sheet.getCell(1,x); // Article
					cx = sheet.getCell(3,x); // Color
					
				}catch (Exception exx) {
				}
				// check end
				if (bx == null || bx.getContents().length()==0) {
					end = 1;
				} else {
					Inventory item = (Inventory)InventoryDAO.getInstance().getSession().createCriteria(Inventory.class).add(Restrictions.eq("Code", bx.getContents().trim())).uniqueResult();
					log.info("T : "+bx.getContents().trim());
					if (item!=null) {
						log.info("D");
						ItemFirstBalance firstBalance = (ItemFirstBalance)ItemFirstBalanceDAO.getInstance().getSession().createCriteria(ItemFirstBalance.class).add(Restrictions.eq("Item.Id", new Long(item.getId()))).uniqueResult();
						//item = (Inventory)InventoryDAO.getInstance().getSession().createCriteria(Inventory.class)
						//.add(Restrictions.eq("Code", new String(gx!=null?gx.getContents().toUpperCase():""))).uniqueResult();
						if (firstBalance==null) {
							firstBalance = new ItemFirstBalance();
							firstBalance.setFirstBalanceDate(organization.getSetupDate());
							firstBalance.setCurrency(item.getCurrency());
							firstBalance.setItem(item);
							firstBalance.setItemUnit(item.getItemUnit());
							firstBalance.setOrganization(organization);
							firstBalance.setQuantity(Double.parseDouble(cx.getContents().trim()));
							ItemFirstBalanceDAO.getInstance().saveOrUpdate(firstBalance);
						}
					}
					x++;
				}
			}
		}
		
		/*
		File file = new File("D:\\project\\java\\web\\financial\\graco\\0.1\\doc\\new/Stock_Kategori.xls");
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet sheet = workbook.getSheet(0);
		if (1==1) {
			int x = 2, end = 0;
			while (end==0) {
				Cell ax =null, bx =null, cx = null, dx=null;
				try {
					//ax = sheet.getCell(0,x); // Jenis
					//bx = sheet.getCell(1,x);	// Gender
				  
					ax = sheet.getCell(0,x); // Merk
					bx = sheet.getCell(1,x); // Article
					cx = sheet.getCell(3,x); // Color
					dx = sheet.getCell(4,x); // Color
					
				}catch (Exception exx) {
				}
				// check end
				if (bx == null || bx.getContents().length()==0) {
					end = 1;
				} else {
					Inventory item = (Inventory)InventoryDAO.getInstance().getSession().createCriteria(Inventory.class).add(Restrictions.eq("Code", bx.getContents().trim())).uniqueResult();
					if (item!=null) {
						item.setDescription(dx.getContents().trim());
						ItemDAO.getInstance().update(item);
					}
					x++;
				}
			}
		}
		*/
	}catch(Exception ex) {
		ex.printStackTrace();
	} finally {
		try {
			UsersDAO.closeCurrentThreadSessions();
		} catch(Exception exx) {
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
	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.global",ex.getMessage()));
	saveErrors(request,errors);
}

}

