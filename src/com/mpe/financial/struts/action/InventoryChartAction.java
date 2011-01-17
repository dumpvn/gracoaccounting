//---------------------------------------------------------
// Application: Garage
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright © 2005 MPE
// Generated at Sat Sep 03 19:38:16 GMT+07:00 2005
//---------------------------------------------------------

package com.mpe.financial.struts.action;

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

import com.mpe.financial.model.Inventory;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.struts.form.GeneralLedgerForm;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mpe.common.*;

public class InventoryChartAction extends Action {
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
					} else if ("MINIMUMINVENTORY".equalsIgnoreCase(action)) {
						forward = performMinimumInventory(mapping, form, request, response);
					} else if ("TOPSALES".equalsIgnoreCase(action)) {
					    forward = performTopSales(mapping, form, request, response);
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
	private ActionForward performMinimumInventory(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			//OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			Session session = InventoryDAO.getInstance().getSession();
			List apAgingLst = new LinkedList();
			// select invoice not complete
			String sql = 
			    "select " +
			    "a.item_id, a.code, a.name, " +
			    "d.minimum_stock, " + 
					"IFNULL(sum(b.quantity), 0), " +
					"(select d.number_of_digit from organization_setup d where d.organization_id=a.organization_id) as {ar.NumberOfDigit} " +
					"from item a join inventory d on a.item_id=d.item_id join inventory_warehouse b on a.item_id=b.item_id join warehouse c on c.warehouse_id=b.warehouse_id" +
					"where " +
					"a.organization_id = :organizationId " +
					"and ISNULL(c.location_id) " +
					"and ISNULL(c.customer_id) " +
					"and d.minimum_stock - IFNULL(sum(b.quantity), 0) < 0 " +
					"group by a.item_id " +
					"order by (d.minimum_stock - IFNULL(sum(b.quantity), 0)) ASC";
			apAgingLst = session.createSQLQuery(sql)
				.addEntity("ar", Inventory.class)
				.setLong("organizationId", users.getOrganization().getId())
				.list();
			
			request.setAttribute("APAGING", apAgingLst);

		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				InventoryDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
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
	private ActionForward performTopSales(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		//GeneralLedgerForm form = (GeneralLedgerForm) actionForm;
		//HttpSession httpSession = request.getSession();
		//Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			//OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			//Session session = InventoryDAO.getInstance().getSession();
			
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				InventoryDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("list");
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