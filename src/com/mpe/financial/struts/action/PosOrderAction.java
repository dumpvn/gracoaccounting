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
import org.apache.struts.upload.FormFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mpe.financial.model.Currency;
import com.mpe.financial.model.Item;
import com.mpe.financial.model.ItemUnit;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.Member;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.PosOrder;
import com.mpe.financial.model.PosOrderDetail;
import com.mpe.financial.model.PosOrderDetailPK;
import com.mpe.financial.model.Salesman;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.ItemDAO;
import com.mpe.financial.model.dao.ItemUnitDAO;
import com.mpe.financial.model.dao.LocationDAO;
import com.mpe.financial.model.dao.MemberDAO;
import com.mpe.financial.model.dao.PosOrderDAO;
import com.mpe.financial.model.dao.SalesmanDAO;
import com.mpe.financial.struts.form.PosOrderForm;
import com.mpe.pos.xml.Memberx;
import com.mpe.pos.xml.PosOrderDetailx;
import com.mpe.pos.xml.PosOrderx;
import com.mpe.pos.xml.PosOrderxs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.mpe.common.*;

public class PosOrderAction extends Action {
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
		//PosOrderForm uomForm = (PosOrderForm) form;
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
					} else if ("DETAIL".equalsIgnoreCase(action)) { 
						forward = performDetail(mapping, form, request, response);
					} else if ("DOWNLOADFORM".equalsIgnoreCase(action)) {
						forward = performDownloadForm(mapping, form, request, response);
					} else if ("DOWNLOADCONFIRM".equalsIgnoreCase(action)) {
						forward = performDownloadConfirm(mapping, form, request, response);
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
		PosOrderForm form = (PosOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
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
			Criteria criteria = PosOrderDAO.getInstance().getSession().createCriteria(PosOrder.class);
			if (form.getString("posOrderNumber")!=null && form.getString("posOrderNumber").length()>0)criteria.add(Restrictions.like("PosOrderNumber", "%"+form.getString("posOrderNumber")+"%"));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			criteria.setProjection(Projections.rowCount());
			total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = PosOrderDAO.getInstance().getSession().createCriteria(PosOrder.class);
			if (form.getString("posOrderNumber")!=null && form.getString("posOrderNumber").length()>0)criteria.add(Restrictions.like("PosOrderNumber", "%"+form.getString("posOrderNumber")+"%"));
			criteria.add(Restrictions.eq("Organization.Id", new Long(users.getOrganization().getId())));
			//criteria.addOrder(Order.desc("Name"));
			if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("asc")) {
			    criteria.addOrder(Order.asc(form.getString("orderBy")));
			} else if (form.getString("orderBy")!=null && form.getString("orderBy").length()>0 && form.getString("ascDesc")!=null && form.getString("ascDesc").length()>0 && form.getString("ascDesc").equalsIgnoreCase("desc")) {
			    criteria.addOrder(Order.desc(form.getString("orderBy")));
			}
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			List list = criteria.list();
			request.setAttribute("POSORDER",list);
			String pager = Pager.generatePager(start, count, total);
			String pagerItem = Pager.generatePagerItem(start, count, total);
			request.setAttribute("PAGER",pager);
			request.setAttribute("PAGERITEM",pagerItem);
		}catch(Exception ex){
			generalError(request,ex);
			return mapping.findForward("list");
		} finally {
			try {
				PosOrderDAO.getInstance().closeSessionForReal();
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
	private ActionForward performDetail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		PosOrderForm form = (PosOrderForm) actionForm;
		//HttpSession httpSession = request.getSession();
		try {
			PosOrder posOrder = PosOrderDAO.getInstance().get(form.getLong("posOrderId"));
			request.setAttribute("posOrder", posOrder);
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				PosOrderDAO.getInstance().closeSessionForReal();
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
	private ActionForward performDownloadForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		PosOrderForm form = (PosOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		try {
			
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		}finally {
			try {
				PosOrderDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("form");
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
	private ActionForward performDownloadConfirm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		PosOrderForm form = (PosOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			OrganizationSetup organizationSetup = (OrganizationSetup)users.getOrganization();
			
			FormFile formFile = form.getFile("fileDownload");
			OutputStream outputStream = null;
			String path = getServlet().getServletConfig().getServletContext().getRealPath("/");
			if (formFile.getFileName()!=null && formFile.getFileName().length() > 0 && formFile.getFileSize()>0) {
				outputStream = new FileOutputStream(path+"upload/"+formFile.getFileName());
			}
			Session session = PosOrderDAO.getInstance().getSession();
			Transaction transaction = session.beginTransaction();
			// get file from server
			if (outputStream != null) {
				byte buffer[] = formFile.getFileData();
				outputStream.write(buffer);
				outputStream.close();
				File file = new File (path+"upload/"+formFile.getFileName());
				if (file != null) {
					PosOrderxs posOrderxs = null;
					posOrderxs = PosOrderxs.unmarshal(new FileReader(file));
					Location location = (Location)LocationDAO.getInstance().getSession().createCriteria(Location.class).add(Restrictions.eq("Name", posOrderxs.getLocationName())).uniqueResult();
					boolean hasDownload = true;
					// TODO
					if (hasDownload) {
						// member
						Memberx[] memberxLst = posOrderxs.getMemberx();
						for (int i=0; i<memberxLst.length; i++) {
							Memberx memberx = memberxLst[i];
							Member member = (Member)MemberDAO.getInstance().getSession().createCriteria(Member.class).add(Restrictions.eq("MemberNumber", memberx.getMemberNumber())).add(Restrictions.eq("Location.Id", new Long(location.getId()))).uniqueResult();
							if (member == null) {
								member = new Member();
								member.setFullName(memberx.getFullName());
								member.setMemberNumber(memberx.getMemberNumber());
								member.setLocation(location);
								MemberDAO.getInstance().save(member, session);
							}
						}
						// pos-order
						PosOrderx[] posOrderLst = posOrderxs.getPosOrderx();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						SimpleDateFormat sdt = new SimpleDateFormat("HH:mm");
						for (int i=0; i<posOrderLst.length; i++) {
							PosOrderx posOrderx = posOrderLst[i];
							PosOrder posOrder = new PosOrder();
							posOrder.setCashAmount(posOrderx.getCashAmount());
							posOrder.setChangesAmount(posOrderx.getChangesAmount());
							posOrder.setCreditCardAdm(posOrderx.getCreditCardAdm());
							posOrder.setCreditCardNumber(posOrderx.getCreditCardNumber());
							Currency currency = (Currency)CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
								.add(Restrictions.eq("Symbol", posOrderx.getCurrencySymbol())).setMaxResults(1).uniqueResult();
							posOrder.setCurrency(currency);
							posOrder.setDiscountProcent(posOrderx.getDiscountProcent());
							Calendar calendar = null;
							if (posOrderx.getEndTime()!=null && posOrderx.getEndTime().length()>0) {
								calendar = new GregorianCalendar();
								calendar.setTime(sdt.parse(posOrderx.getEndTime()));
							}
							//posOrder.setEndTime(calendar!=null?calendar.getTime():null);
							Calendar calendar2 = null;
							if (posOrderx.getStartTime()!=null && posOrderx.getStartTime().length()>0) {
								calendar2 = new GregorianCalendar();
								calendar2.setTime(sdt.parse(posOrderx.getStartTime()));
							}
							posOrder.setStartTime(calendar2!=null?calendar2.getTime():null);
							Calendar calendar3 = null;
							if (posOrderx.getPosOrderDate()!=null && posOrderx.getPosOrderDate().length()>0) {
								calendar3 = new GregorianCalendar();
								calendar3.setTime(sdf.parse(posOrderx.getPosOrderDate()));
							}
							posOrder.setPosOrderDate(calendar3!=null?calendar3.getTime():null);
							posOrder.setLocation(location);
							Member member = (Member)MemberDAO.getInstance().getSession().createCriteria(Member.class)
							.add(Restrictions.eq("MemberNumber", posOrderx.getMemberNumber())).setMaxResults(1).uniqueResult();
							posOrder.setMember(member);
							posOrder.setOrganization(users.getOrganization());
							posOrder.setPaymentMethod(posOrderx.getPaymentMethod());
							posOrder.setPosOrderNumber(posOrderx.getPosOrderNumber());
							Salesman salesman = (Salesman)SalesmanDAO.getInstance().getSession().createCriteria(Salesman.class)
							.add(Restrictions.eq("Code", posOrderx.getSalemanCode())).setMaxResults(1).uniqueResult();
							posOrder.setSalesman(salesman);
							posOrder.setStatus(posOrderx.getStatus());
							posOrder.setTaxProcent(posOrderx.getTaxProcent());
							// detail
							PosOrderDetailx[] posOrderDetailxLst = posOrderx.getPosOrderDetailx();
							Set set = new LinkedHashSet();
							for (int j=0; j<posOrderDetailxLst.length; j++) {
								PosOrderDetailx posOrderDetailx = posOrderDetailxLst[i];
								PosOrderDetail posOrderDetail = new PosOrderDetail();
								PosOrderDetailPK posOrderDetailPK = new PosOrderDetailPK();
								Currency currency2 = (Currency)CurrencyDAO.getInstance().getSession().createCriteria(Currency.class)
									.add(Restrictions.eq("Symbol", posOrderDetailx.getCurrencySymbol())).setMaxResults(1).uniqueResult();
								posOrderDetail.setCurrency(currency2);
								posOrderDetail.setDiscountProcent(posOrderDetailx.getDiscountProcent());
								posOrderDetail.setExchangeRate(posOrderDetailx.getExchangeRate());
								ItemUnit itemUnit  = (ItemUnit)ItemUnitDAO.getInstance().getSession().createCriteria(ItemUnit.class)
									.add(Restrictions.eq("Symbol", posOrderDetailx.getItemUnitSymbol())).setMaxResults(1).uniqueResult();
								posOrderDetail.setItemUnit(itemUnit);
								Item item = (Item)ItemDAO.getInstance().getSession().createCriteria(Item.class)
									.add(Restrictions.eq("Code", posOrderDetailx.getItemCode())).setMaxResults(1).uniqueResult();
								posOrderDetailPK.setItem(item);
								posOrderDetailPK.setPosOrder(posOrder);
								posOrderDetail.setId(posOrderDetailPK);
								posOrderDetail.setPrice(posOrderDetailx.getPrice());
								posOrderDetail.setQuantity(posOrderDetailx.getQuantity());
								set.add(posOrderDetail);
							}
							posOrder.setPosOrderDetails(set);
							PosOrderDAO.getInstance().save(posOrder, session);
						}
					}
				}
				transaction.commit();
			}
		}catch(Exception ex) {
			generalError(request,ex);
			return mapping.findForward("detail");
		} finally {
			try {
				PosOrderDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		return mapping.findForward("confirm");
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