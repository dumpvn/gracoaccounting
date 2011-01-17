//---------------------------------------------------------
// Application: Garage
// Author     : Agung Hadiwaluyo
// (Milan Fanz Club)
// Copyright © 2005 MPE
// Generated at Sat Sep 03 19:38:16 GMT+07:00 2005
//---------------------------------------------------------

package com.mpe.pos.struts.action;

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
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mpe.financial.model.Bank;
import com.mpe.financial.model.Inventory;
import com.mpe.financial.model.Item;
import com.mpe.financial.model.ItemPrice;
import com.mpe.financial.model.ItemUnit;
import com.mpe.financial.model.Location;
import com.mpe.financial.model.Member;
import com.mpe.financial.model.OrganizationSetup;
import com.mpe.financial.model.PaidCreditCard;
import com.mpe.financial.model.PosOrder;
import com.mpe.financial.model.PosOrderDetail;
import com.mpe.financial.model.PosOrderDetailPK;
import com.mpe.financial.model.Users;
import com.mpe.financial.model.dao.BankDAO;
import com.mpe.financial.model.dao.CurrencyDAO;
import com.mpe.financial.model.dao.InventoryDAO;
import com.mpe.financial.model.dao.ItemPriceDAO;
import com.mpe.financial.model.dao.MemberDAO;
import com.mpe.financial.model.dao.OrganizationSetupDAO;
import com.mpe.financial.model.dao.PaidCreditCardDAO;
import com.mpe.financial.model.dao.PosOrderDAO;
import com.mpe.financial.model.dao.RunningNumberDAO;
import com.mpe.financial.model.dao.UsersDAO;
import com.mpe.pos.struts.form.PosOrderForm;

import java.util.Iterator;
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
		PosOrderForm posOrderForm = (PosOrderForm) form;
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
					} else if ("FORM".equalsIgnoreCase(action)) {
						forward = performForm(mapping, form, request, response);
					} else if ("SAVE".equalsIgnoreCase(action)) {
					  if (posOrderForm.getString("subaction")!=null && posOrderForm.getString("subaction").length()>0 && posOrderForm.getString("subaction").equalsIgnoreCase("refresh")) {
					      forward = performForm(mapping, form, request, response);
					  } else {
					      forward = performSave(mapping, form, request, response);
					  }
					} else if ("PAYMENTFORM".equalsIgnoreCase(action)) { 
						forward = performPaymentForm(mapping, form, request, response);
					} else if ("PAYMENTSAVE".equalsIgnoreCase(action)) {
					  if (posOrderForm.getString("subaction")!=null && posOrderForm.getString("subaction").length()>0 && posOrderForm.getString("subaction").equalsIgnoreCase("refresh")) {
					      forward = performPaymentForm(mapping, form, request, response);
					  } else forward = performPaymentSave(mapping, form, request, response);
					} else if ("MEMBERFORM".equalsIgnoreCase(action)) { 
							forward = performMemberForm(mapping, form, request, response);
					} else if ("MEMBERSAVE".equalsIgnoreCase(action)) {
						forward = performMemberSave(mapping, form, request, response);
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
	 * Method performForm
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */			
	private ActionForward performForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		PosOrderForm form = (PosOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		  double discount = 0;
		  double tax = 0;
		  double adm = 0;
			try {
				ResourceBundle prop = ResourceBundle.getBundle("resource.ApplicationResources");
				discount = Double.parseDouble(prop.getString("posOrder.discount.amount"));
				tax = Double.parseDouble(prop.getString("posOrder.tax.amount"));
				adm = Double.parseDouble(prop.getString("posOrder.adm.amount"));
			}catch(Exception exx) {
			}
		  //OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
		  PosOrder posOrder = (PosOrder)httpSession.getAttribute("posOrder");
		  // cancel order
		  if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("CANCEL")) {
		      form.setString("subaction", "");
					httpSession.removeAttribute("posOrder");
					posOrder = null;
		  }
		  // remove lastItem
		  if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEPOSORDERDETAIL")) {
				if (form.getLong("itemId")>0) {
			    PosOrderDetail removePosOrderDetail = null;
					Iterator iterator = posOrder.getPosOrderDetails().iterator();
					if (iterator.hasNext()) {
						PosOrderDetail posOrderDetail = (PosOrderDetail)iterator.next();
						removePosOrderDetail = posOrderDetail;
					}
					if (removePosOrderDetail!=null) {
						Set set = posOrder.getPosOrderDetails();
						set.remove(removePosOrderDetail);
						posOrder.setPosOrderDetails(set);
					}
					form.setString("subaction", "");
					httpSession.setAttribute("posOrder", posOrder);
				}
			}
		  // remove order
		  if (form.getLong("itemId") > 0 && form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("REMOVEPOSORDERDETAIL")) {
		      //httpSession.removeAttribute("posOrder");
		      //httpSession.removeAttribute("member");
		  }
			// relationships
			if (form.getLong("posOrderId") == 0) {
				form.setString("posOrderId",0);
				form.setCurentTimestamp("createOn");
				form.setCurentCalendar("posOrderDate");
				form.setCurentTime("startTime");
				form.setCurentTime("endTime");
				form.setString("discount", discount);
				form.setString("tax", tax);
				if (form.getDouble("itemQuantity")==0) form.setString("itemQuantity", 1); 
				form.setString("posOrderNumber", RunningNumberDAO.getInstance().getPosOrderNumber());
				if (posOrder!=null) {
				  //log.info("X");
					form.setString("posOrderId",posOrder.getId());
					form.setString("discount",posOrder.getDiscountProcent());
					//form.setCalendar("endTime",posOrder.getEndTime());
					form.setString("memberId",posOrder.getMember()!=null?posOrder.getMember().getId():0);
					form.setString("memberNumber",posOrder.getMember()!=null?posOrder.getMember().getMemberNumber():"");
					form.setString("posOrderNumber",posOrder.getPosOrderNumber());
					form.setString("tax",posOrder.getTaxProcent());
					//form.setCalendar("endTime",posOrder.getEndTime());
					form.setCalendar("posOrderDate",posOrder.getPosOrderDate());
					Set posOrderDetailLst = posOrder.getPosOrderDetails();
					request.setAttribute("posOrderDetailLst", posOrderDetailLst);
				}
				//set default start at beginning of page
				httpSession.setAttribute(CommonConstants.START,"0");
				httpSession.setAttribute(CommonConstants.COUNT,httpSession.getAttribute(CommonConstants.COUNT));
				request.setAttribute("totalAmount", posOrder!=null?posOrder.getFormatedPosOrderDetailQuantityPrice():"");
				request.setAttribute("discount", posOrder!=null?posOrder.getFormatedDiscountAmount():"");
				request.setAttribute("totalAmountAfterDiscount", posOrder!=null?posOrder.getFormatedTotalAmountAfterDiscount():"");
				request.setAttribute("tax", posOrder!=null?posOrder.getFormatedTaxAmount():"");
				request.setAttribute("totalAmountAfterDiscountAndTax", posOrder!=null?posOrder.getFormatedTotalAmountAfterDiscountAndTax():"");
			}
		}catch(Exception ex) {
			try {
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		}finally {
			try {
				PosOrderDAO.getInstance().closeSessionForReal();
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
		PosOrderForm form = (PosOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		  OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
			PosOrder obj = (PosOrder)httpSession.getAttribute("posOrder");
			if (form.getLong("posOrderId") == 0) {
				obj = (PosOrder)PosOrderDAO.getInstance().getSession().createCriteria(PosOrder.class).add(Restrictions.eq("PosOrderNumber", form.getString("posOrderNumber"))).uniqueResult();
				if (obj==null) {
				  obj = (PosOrder)httpSession.getAttribute("posOrder");
					if (obj==null) obj = new PosOrder();
					obj.setCurrency(organizationSetup.getDefaultCurrency());
					obj.setLocation((Location)httpSession.getAttribute(CommonConstants.LOCATION));
					obj.setDiscountProcent(form.getDouble("discount"));
					//obj.setEndTime(form.getTime("endTime"));
					Member member = (Member)MemberDAO.getInstance().getSession().createCriteria(Member.class)
						.add(Restrictions.eq("MemberNumber", form.getString("memberNumber"))).uniqueResult();
					obj.setMember(member);
					obj.setPosOrderDate(form.getCalendar("posOrderDate")!=null?form.getCalendar("posOrderDate").getTime():null);
					obj.setPosOrderNumber(form.getString("posOrderNumber"));
					obj.setPosted(false);
					obj.setStartTime(form.getTime("startTime"));
					obj.setStatus(CommonConstants.START);
					obj.setTaxProcent(form.getDouble("tax"));
					obj.setOrganization(users.getOrganization());
					obj.setCreateBy(users); 
					obj.setCreateOn(form.getTimestamp("createOn"));
					//PosOrderDAO.getInstance().save(obj);
				} else {
					ActionMessages errors = new ActionMessages();
					errors.add("errorDuplicate",new ActionMessage("error.duplicate", form.getString("posOrderNumber")));
					saveErrors(request,errors);
					return (new ActionForward(mapping.getInput()));
				}
			}
			if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("ADDPOSORDERDETAIL")) {
					if (form.getString("itemCode")!=null && form.getString("itemCode").length()>0) {
					  PosOrderDetail posOrderDetail = new PosOrderDetail();
						Inventory inventory = (Inventory)InventoryDAO.getInstance().getSession().createCriteria(Inventory.class)
							.add(Restrictions.eq("Code", form.getString("itemCode"))).uniqueResult();
						ItemUnit itemUnit = inventory!=null?inventory.getItemUnit():null;
						posOrderDetail.setItemUnit(itemUnit);
						PosOrderDetailPK posOrderDetailPK = new PosOrderDetailPK();
						posOrderDetailPK.setItem((Item)inventory);
						posOrderDetailPK.setPosOrder(obj);
						posOrderDetail.setId(posOrderDetailPK);
						String sql = "select itemPrice from ItemPrice itemPrice where itemPrice.Id.Item.Id = "+inventory.getId();
						sql = sql + " and itemPrice.Default = 'Y'";
						ItemPrice itemPrice = (ItemPrice)ItemPriceDAO.getInstance().getSession().createQuery(sql).uniqueResult();
						posOrderDetail.setCurrency(itemPrice.getCurrency());
						posOrderDetail.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(itemPrice.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("posOrderDate")));
						posOrderDetail.setPrice(itemPrice.getPrice());
						posOrderDetail.setQuantity(form.getDouble("itemQuantity"));
						posOrderDetail.setDiscountProcent(0);
						Set set = obj.getPosOrderDetails();
						if (set==null) {
						    set = new LinkedHashSet();
						}
						//log.info("[ size : "+set.size()+" ]");
						PosOrderDetail removePosOrderDetail = null;
						Iterator iterator = set.iterator();
						while (iterator.hasNext()) {
							PosOrderDetail posOrderDetail2 = (PosOrderDetail)iterator.next();
							Inventory inventory2 = (Inventory)InventoryDAO.getInstance().getSession().createCriteria(Inventory.class)
								.add(Restrictions.eq("Code", form.getString("itemCode"))).uniqueResult();
							if (inventory2.getId()==posOrderDetail2.getId().getItem().getId()) {
							    removePosOrderDetail = posOrderDetail2;
							}
						}
						if (removePosOrderDetail!=null) {
							set.remove(removePosOrderDetail);
							set.add(posOrderDetail);
						} else {
							set.add(posOrderDetail);
						}
						obj.setPosOrderDetails(set);
						// netral
						form.setString("itemCode", "");
						form.setString("itemQuantity", "");
					}
					// netral
					form.setString("itemCode", "");
					form.setString("itemQuantity", "");
				}
				if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("UPDATEPOSORDERDETAIL")) {
					if (form.getLong("itemId") >0) {
					  PosOrderDetail posOrderDetail = new PosOrderDetail();
						Inventory inventory = (Inventory)InventoryDAO.getInstance().getSession().createCriteria(Inventory.class)
							.add(Restrictions.eq("Id", new Long(form.getLong("itemId")))).uniqueResult();
						ItemUnit itemUnit = inventory!=null?inventory.getItemUnit():null;
						posOrderDetail.setItemUnit(itemUnit);
						PosOrderDetailPK posOrderDetailPK = new PosOrderDetailPK();
						posOrderDetailPK.setItem((Item)inventory);
						posOrderDetailPK.setPosOrder(obj);
						posOrderDetail.setId(posOrderDetailPK);
						String sql = "select itemPrice from ItemPrice itemPrice where itemPrice.Id.Item.Id = "+inventory.getId();
						sql = sql + " and itemPrice.Default = 'Y'";
						ItemPrice itemPrice = (ItemPrice)ItemPriceDAO.getInstance().getSession().createQuery(sql).uniqueResult();
						posOrderDetail.setCurrency(itemPrice.getCurrency());
						posOrderDetail.setExchangeRate(CurrencyDAO.getInstance().getExchangeRateFromCurrencyToCurrency(itemPrice.getCurrency(), obj.getCurrency(), organizationSetup, form.getCalendar("posOrderDate")));
						posOrderDetail.setPrice(itemPrice.getPrice());
						posOrderDetail.setQuantity(form.getDouble("quantity"));
						posOrderDetail.setDiscountProcent(0);
						Set set = obj.getPosOrderDetails();
						if (set==null) set = new LinkedHashSet();
						PosOrderDetail removePosOrderDetail = null;
						Iterator iterator = set.iterator();
						while (iterator.hasNext()) {
							PosOrderDetail posOrderDetail2 = (PosOrderDetail)iterator.next();
							Inventory inventory2 = (Inventory)InventoryDAO.getInstance().getSession().createCriteria(Inventory.class)
								.add(Restrictions.eq("Id", new Long(form.getLong("itemId")))).uniqueResult();
							if (inventory2.getId()==posOrderDetail2.getId().getItem().getId()) {
							    removePosOrderDetail = posOrderDetail2;
							}
						}
						if (removePosOrderDetail!=null) {
							set.remove(removePosOrderDetail);
							set.add(posOrderDetail);
						} else {
							set.add(posOrderDetail);
						}
						//log.info("[ B : "+set.size()+" ]");
						obj.setPosOrderDetails(set);
					}
				}
				// save to session
				httpSession.setAttribute("posOrder", obj);
				// save all
				if (form.getString("subaction")!=null && form.getString("subaction").length()>0 && form.getString("subaction").equalsIgnoreCase("PAYMENT")) {
					// finish
					ActionForward forward = mapping.findForward("payment_form");
					StringBuffer sb = new StringBuffer(forward.getPath());
					sb.append("?posOrderId="+form.getLong("posOrderId"));
					return new ActionForward(sb.toString(),true);
				}
		}catch(Exception ex) {
			try {
				try {
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				PosOrderDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
			try {
				UsersDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?posOrderId="+form.getLong("posOrderId"));
		return new ActionForward(sb.toString(),true);
	}
	
	/** 
	 * Method performPaymentForm
	 * @param ActionMapping mapping
	 * @param ActionForm form
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @return ActionForward
	 * @throws Exception
	 */
	private ActionForward performPaymentForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		PosOrderForm form = (PosOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
			List list = BankDAO.getInstance().findAll(Order.asc("Name"));
			request.setAttribute("bankList", list);
	    double discount = 0;
		  double tax = 0;
		  double adm = 0;
			try {
				ResourceBundle prop = ResourceBundle.getBundle("resource.ApplicationResources");
				discount = Double.parseDouble(prop.getString("posOrder.discount.amount"));
				tax = Double.parseDouble(prop.getString("posOrder.tax.amount"));
				adm = Double.parseDouble(prop.getString("posOrder.adm.amount"));
			}catch(Exception exx) {
			}
			PosOrder obj = (PosOrder)httpSession.getAttribute("posOrder");
			if (form.getString("paymentMethod").length()>0 && form.getString("paymentMethod").equalsIgnoreCase("CREDITCARD")) {
			    obj.setCreditCardAdm(adm/100 * obj.getTotalAmountAfterDiscountAndTax());
			    form.setString("cashAmount", Formater.getFormatedOutputForm(obj.getTotalAmountAfterDiscountAndTax() + (adm/100 * obj.getTotalAmountAfterDiscountAndTax())));
			    form.setString("changeAmount", 0);
			} else {
			    form.setString("cashAmount", "");
			    form.setString("changeAmount", "");
			}
			httpSession.setAttribute("posOrder", obj);
		}catch(Exception ex) {
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				PosOrderDAO.getInstance().closeSessionForReal();
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
	private ActionForward performPaymentSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		PosOrderForm form = (PosOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    Session session = PosOrderDAO.getInstance().getSession();
		    Transaction transaction = session.beginTransaction();
		    //OrganizationSetup organizationSetup = OrganizationSetupDAO.getInstance().get(users.getOrganization().getId());
				PosOrder obj = (PosOrder)httpSession.getAttribute("posOrder");
				obj.setPaymentMethod(form.getString("paymentMethod"));
				obj.setCashAmount(form.getDouble("cashAmount"));
				obj.setChangesAmount(form.getDouble("changedAmount"));
				//obj.setCreditCardAmount(form.getDouble("creditCardAmount"));
				obj.setCreditCardNumber(form.getString("creditCardNumber"));
				Bank bank = BankDAO.getInstance().get(form.getLong("bankId"));
				obj.setBank(bank);
				RunningNumberDAO.getInstance().updatePosOrderNumber(session);
				PosOrderDAO.getInstance().save(obj, session);
				
				// save to paid
				PaidCreditCard paidCreditCard = new PaidCreditCard();
				paidCreditCard.setAmount(obj.getTotalAmountAfterDiscountAndTax());
				paidCreditCard.setAdm(obj.getCreditCardAdm());
				paidCreditCard.setLocation((Location)httpSession.getAttribute(CommonConstants.LOCATION));
				paidCreditCard.setBank(bank);
				paidCreditCard.setCurrency(obj.getCurrency());
				paidCreditCard.setOrganization(users.getOrganization());
				paidCreditCard.setCreditCardNumber(obj.getCreditCardNumber());
				paidCreditCard.setPosOrder(obj);
				PaidCreditCardDAO.getInstance().save(paidCreditCard, session);
				
				httpSession.removeAttribute("posOrder");
				transaction.commit();
		}catch(Exception ex) {
			try {
				try {
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				PosOrderDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?posOrderId="+form.getLong("posOrderId"));
		return new ActionForward(sb.toString(),true);
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
	private ActionForward performMemberForm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		PosOrderForm form = (PosOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		try {
		    PosOrder obj = (PosOrder)httpSession.getAttribute("posOrder");
		    Member member = obj!=null?obj.getMember():null;
		    if (member!=null) {
		        form.setString("memberAddress", member.getAddress());
		        form.setString("memberCity", member.getCity());
		        form.setString("memberEmail", member.getEmail());
		        form.setString("memberFax", member.getFax());
		        form.setString("memberFullName", member.getFullName());
		        form.setString("memberNumber", member.getMemberNumber());
		        form.setString("memerMobile", member.getMobile());
		        form.setString("memberNickName", member.getNickName());
		        form.setString("memberPostalCode", member.getPostalCode());
		        form.setString("memberProvince", member.getProvince());
		        form.setString("memberTelephone", member.getTelephone());
		        form.setCalendar("memberDate", member.getMemberDate());
		        form.setString("memberIsActive", member.isActive()==true?"Y":"N");
		        form.setString("memberId", member.getId());
		    } else {
		        form.setCurentCalendar("memberDate");
		        form.setString("memberNumber", RunningNumberDAO.getInstance().getMemberNumber());
		        form.setString("memberIsActive", "Y");
		    }
		}catch(Exception ex) {
			try {
				try {
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return mapping.findForward("form");
		} finally {
			try {
				PosOrderDAO.getInstance().closeSessionForReal();
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
	private ActionForward performMemberSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		PosOrderForm form = (PosOrderForm) actionForm;
		HttpSession httpSession = request.getSession();
		Users users = (Users)httpSession.getAttribute(CommonConstants.USER);
		Location location = (Location)httpSession.getAttribute(CommonConstants.LOCATION);
		Transaction transaction = null;
		try {
	    if (isCancelled(request)) {
	        ActionForward forward = mapping.findForward("form_redir");
	    		StringBuffer sb = new StringBuffer(forward.getPath());
	    		sb.append("?posOrderId="+form.getLong("posOrderId"));
	    		return new ActionForward(sb.toString(),true);
			}
	    Session session = MemberDAO.getInstance().getSession();
	    transaction = session.beginTransaction();
		  PosOrder obj = (PosOrder)httpSession.getAttribute("posOrder");
			if (form.getLong("memberId")==0) {
			    Member member = new Member();
			    member.setActive(true);
			    member.setAddress(form.getString("memberAddress"));
			    member.setCity(form.getString("memberCity"));
			    member.setCreateBy(users);
			    member.setCreateOn(form.getTimestamp("createOn"));
			    member.setEmail(form.getString("memberEmail"));
			    member.setFax(form.getString("memberFax"));
			    member.setFullName(form.getString("memberFullName"));
			    member.setLocation(location);
			    member.setMemberDate(form.getCalendar("memberDate")!=null?form.getCalendar("memberDate").getTime():null);
			    member.setMemberNumber(form.getString("memberNumber"));
			    member.setMobile(form.getString("memberMobile"));
			    member.setNickName(form.getString("memberNickName"));
			    member.setOrganization(users.getOrganization());
			    member.setPostalCode(form.getString("memberPostalCode"));
			    member.setProvince(form.getString("memberProvince"));
			    member.setTelephone(form.getString("memberTelephone"));
			    RunningNumberDAO.getInstance().updateMemberNumber(session);
			    MemberDAO.getInstance().save(member, session);
			    if (obj!=null) {
			        obj.setMember(member);
			        httpSession.setAttribute("posOrder", obj);
			    } else {
			        form.setString("memberId", member.getId());
			        form.setString("memberNumber", member.getMemberNumber());
			    }
			    transaction.commit();
			} else {
			    // nothing here
			}
		}catch(Exception ex) {
			try {
				try {
				    transaction.rollback();
				}catch(Exception exx) {
				}
			}catch(Exception exx) {
			}
			generalError(request,ex);
			return (new ActionForward(mapping.getInput()));
		} finally {
			try {
				PosOrderDAO.getInstance().closeSessionForReal();
			}catch(Exception exx) {
			}
		}
		ActionForward forward = mapping.findForward("form_redir");
		StringBuffer sb = new StringBuffer(forward.getPath());
		sb.append("?posOrderId="+form.getLong("posOrderId")+"&memberId="+form.getLong("memberId")+"&memberNumber="+form.getString("memberNumber"));
		return new ActionForward(sb.toString(),true);
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