<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>

<html:html>
<HEAD><TITLE><bean:message key="page.index.title"/></TITLE>
<LINK href="<html:rewrite page="/js/styleHeader.css"/>" type=text/css rel=stylesheet>
<html:base/>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightMargin=0 marginwidth="0" marginheight="0">

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0 valign="top">
  <!-- header start -->
  <tiles:insert template='/common/header.jsp'/>
  <!-- header end -->
  
  <tr valign="top"><td>
    
<!-- content -->    
      <TABLE height=50 cellSpacing=1 cellPadding=1 border=0 width="100%" valign="top"><BR><BR>
        <TR>
          <TD align=middle width="80%">
            <TABLE cellSpacing=0 cellPadding=0 width="92%" border=0>
              <TR>
                <TD width=9 height=25><html:img page="/images/left_title.gif"/></TD>
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.vendorBillTitle.title"/></TD></TR>
            </TABLE>
          </TD>
        </TR>
        <TR>
          <TD align=middle width="80%" height=10>&nbsp;</TD></TR>
        <TR>
          <TD align=middle width="80%">
			
			<!-- CONTENT START -->
	      <table width="97%" border="0" cellspacing="2" cellpadding="0">
	      	<tr valign="top"><td><html:errors/></td></tr>
    			<tr valign="top"><td>&nbsp;</td></tr>
    			<html:form action="/vendorBill/detail">
					<html:hidden property="vendorBillId"/>								        			
          <tr>
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              	<tr valign="top">
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.location.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="vendorBill" property="location">
			                  	<bean:write name="vendorBill" property="location.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="vendorBill" property="number"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.billDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="vendorBill" property="formatedBillDate"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.billDue.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="vendorBill" property="formatedBillDue"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.vendor.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="vendorBill" property="vendor">
			                  	<bean:write name="vendorBill" property="vendor.company"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.receiving.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="vendorBill" property="receiving">
			                  	<bean:write name="vendorBill" property="receiving.number"/>
			                  	</logic:present>
			                  </td>
			                </tr>
											<tr> 
			                  <td width="30%" align="right"><bean:message key="page.department.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="vendorBill" property="department">
			                  	<bean:write name="vendorBill" property="department.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.project.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="vendorBill" property="project">
			                  	<bean:write name="vendorBill" property="project.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.tax.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="vendorBill" property="tax">
			                  	<bean:write name="vendorBill" property="tax.name"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.discount.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="vendorBill" property="discountProcent"/>%</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.taxAmount.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="vendorBill" property="taxAmount"/>%</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.currency.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="vendorBill" property="currency">
			                  	<bean:write name="vendorBill" property="currency.name"/>
			                  	</logic:present>
			                  	&nbsp;<i>(<bean:write name="vendorBill" property="formatedExchangeRate" scope="request"/>)</i></i>
			                  </td>
			                </tr>
              			</table>
              		</td>
              		<td width="10%"></td>
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.termOfPayment.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="vendorBill" property="receiving">
			                  	<logic:present name="vendorBill" property="receiving.purchaseOrder">
													<logic:present name="vendorBill" property="receiving.purchaseOrder.termOfPayment">
			                  	<bean:write name="vendorBill" property="receiving.purchaseOrder.termOfPayment.name"/>
			                  	</logic:present>
													</logic:present>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.voucher.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="vendorBill" property="voucher"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.taxSerialNumber.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="vendorBill" property="taxSerialNumber"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.taxDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="vendorBill" property="taxDate"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="vendorBill" property="description"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="vendorBill" property="createBy">
			                  	<bean:write name="vendorBill" property="createBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="vendorBill" property="formatedCreateOn" scope="request"/></td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="vendorBill" property="changeBy">
			                  	<bean:write name="vendorBill" property="changeBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="vendorBill" property="formatedChangeOn" scope="request"/></td>
			                </tr>
              			</table>
              		</td>
              	</tr>
              	
                
                
              </table>
            </td>
          </tr>
          <tr>
          	<td>&nbsp;</td>
          </tr>
          <tr>
    				<td>
    					<table width="100%">
    						<tr bgcolor="#FFFFFF"> 
			            <td align="center"> 
			              <table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
			                <tr align="center"> 
			                  <td width="5%" class=titleField>&nbsp;</td>
			                  <td class=titleField><bean:message key="page.item.title"/></td>
			                  <td class=titleField><bean:message key="page.price.title"/></td>
			                  <td class=titleField><bean:message key="page.quantity.title"/></td>
												<td class=titleField><bean:message key="page.discount.title"/></td>
												<td class=titleField><bean:message key="page.tax.title"/></td>
			                  <td class=titleField><bean:message key="page.price.title"/>x<bean:message key="page.quantity.title"/></td>
			                  <td class=titleField><bean:message key="page.description.title"/></td>
			                </tr>
			<%
			int i = 0;
			%>
											<logic:present name="vendorBill" property="vendorBillDetails">
			                <logic:iterate id="vendorBillDetail" name="vendorBill" property="vendorBillDetails" type="com.mpe.financial.model.VendorBillDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="vendorBillDetail" property="id.item">
													<bean:write name="vendorBillDetail" property="id.item.code" scope="page"/>&nbsp;
													<bean:write name="vendorBillDetail" property="id.item.name" scope="page"/>
													</logic:present>
												</td>
												<td>
													<i>(<bean:write name="vendorBillDetail" property="formatedExchangeRate" scope="page"/>)</i>
													<bean:write name="vendorBillDetail" property="formatedPrice" scope="page"/>
													<!--
													<logic:present name="vendorBillDetail" property="currency">
													<bean:write name="vendorBillDetail" property="currency.name" scope="page"/>
													</logic:present>
													-->
												</td>
												<td><bean:write name="vendorBillDetail" property="quantity" scope="page"/>&nbsp;
													<logic:present name="vendorBillDetail" property="itemUnit">
													<bean:write name="vendorBillDetail" property="itemUnit.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="vendorBillDetail" property="formatedDiscountAmount" scope="page"/>/<bean:write name="vendorBillDetail" property="discountProcent" scope="page"/>%</td>
												<td>
													<logic:present name="vendorBillDetail" property="tax">
													<bean:write name="vendorBillDetail" property="tax.name" scope="page"/>
													</logic:present>&nbsp;<i>(<bean:write name="vendorBillDetail" property="taxAmount" scope="page"/>%)</i>
												</td>
			                  <td align="right">
			                  	<!--
			                  	<logic:present name="vendorBill" property="currency">
													<bean:write name="vendorBill" property="currency.symbol" scope="request"/>&nbsp;
													</logic:present>
													-->
			                  	<bean:write name="vendorBillDetail" property="formatedPriceQuantity" scope="page"/>
												</td>
												<td><bean:write name="vendorBillDetail" property="description" scope="page"/></td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="vendorBill" property="currency">
													<bean:write name="vendorBill" property="currency.symbol" scope="request"/>&nbsp;
													</logic:present>
													-->
			                		<logic:present name="vendorBill"><bean:write name="vendorBill" property="formatedVendorBillDetailAmount" scope="request"/>&nbsp;
			                		</logic:present>
			                	</td>
			                	<td>&nbsp;</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.discount.title"/>&nbsp;<logic:present name="vendorBill">(<bean:write name="vendorBill" property="discountProcent"/>%)</logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="vendorBill" property="currency">
													<bean:write name="vendorBill" property="currency.symbol" scope="request"/>&nbsp;
													</logic:present>
													-->
			                		<logic:present name="vendorBill"><bean:write name="vendorBill" property="formatedVendorBillDiscountAmount" scope="request"/>&nbsp;
			                		</logic:present>
			                	</b></td>
			                	<td>&nbsp;</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="vendorBill" property="currency">
													<bean:write name="vendorBill" property="currency.symbol" scope="request"/>&nbsp;
													</logic:present>
													-->
			                		<logic:present name="vendorBill"><bean:write name="vendorBill" property="formatedVendorBillAfterDiscount" scope="request"/>&nbsp;
			                		</logic:present>
			                	</b></td>
			                	<td>&nbsp;</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.tax.title"/>&nbsp;<logic:present name="vendorBill">(<bean:write name="vendorBill" property="taxAmount"/>%)</logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="vendorBill" property="currency">
													<bean:write name="vendorBill" property="currency.symbol" scope="request"/>&nbsp;
													</logic:present>
													-->
			                		<logic:present name="vendorBill"><bean:write name="vendorBill" property="formatedVendorBillTaxAmount" scope="request"/>&nbsp;
			                		</logic:present>
			                	</b></td>
			                	<td>&nbsp;</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="vendorBill" property="currency">
													<bean:write name="vendorBill" property="currency.symbol" scope="request"/>&nbsp;
													</logic:present>
													-->
			                		<logic:present name="vendorBill"><bean:write name="vendorBill" property="formatedVendorBillAfterDiscountAndTax" scope="request"/>&nbsp;
			                		</logic:present>
			                	</b></td>
			                	<td>&nbsp;</td>
			                </tr>
			              </table>
			            </td>
			          </tr>
    					</table>
    				</td>
    			</tr>
    			<tr>
          	<td>&nbsp;</td>
          </tr>
          <tr>
    				<td>
    					<table width="100%">
    						<tr bgcolor="#FFFFFF"> 
			            <td align="center"> 
			              <table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
			                <tr align="center"> 
			                  <td width="5%" class=titleField>&nbsp;</td>
			                  <td class=titleField><bean:message key="page.number.title"/></td>
			                  <td class=titleField><bean:message key="page.prepaymentDate.title"/></td>
			                  <td class=titleField><bean:message key="page.purchaseOrder.title"/></td>
			                  <td class=titleField><bean:message key="page.amount.title"/></td>
			                </tr>
			<%
			int j = 0;
			%>
											<logic:present name="vendorBill" property="vendorBillPrepayments">
			                <logic:iterate id="vendorBillPrepayment" name="vendorBill" property="vendorBillPrepayments" type="com.mpe.financial.model.VendorBillPrepayment">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++j%>.</td>
												<td>
													<logic:present name="vendorBillPrepayment" property="id.prepaymentToVendor">
													<bean:write name="vendorBillPrepayment" property="id.prepaymentToVendor.number" scope="page"/>
													</logic:present>
												</td>
												<td>
													<logic:present name="vendorBillPrepayment" property="id.prepaymentToVendor">
													<bean:write name="vendorBillPrepayment" property="id.prepaymentToVendor.prepaymentDate" scope="page"/>
													</logic:present>
												</td>
												<td>
													<logic:present name="vendorBillPrepayment" property="id.prepaymentToVendor">
													<bean:write name="vendorBillPrepayment" property="id.prepaymentToVendor.purchaseOrder.number" scope="page"/>
													</logic:present>
												</td>
												<td align="right">
													<logic:present name="vendorBillPrepayment" property="id.prepaymentToVendor">
													<bean:write name="vendorBillPrepayment" property="id.prepaymentToVendor.amount" scope="page"/>&nbsp;
													<!--<bean:write name="vendorBillPrepayment" property="id.prepaymentToVendor.currency.name" scope="page"/>-->
													</logic:present>
												</td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="4"><b><bean:message key="page.prepaymentAmount.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="vendorBill" property="currency">
													<bean:write name="vendorBill" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<logic:present name="vendorBill"><bean:write name="vendorBill" property="formatedVendorBillPrepaymentAmount" scope="request"/>
			                		</logic:present>
			                	</b></td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="4"><b><bean:message key="page.amount.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="vendorBill" property="currency">
													<bean:write name="vendorBill" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<logic:present name="vendorBill"><bean:write name="vendorBill" property="formatedVendorBillAfterDiscountAndTaxAndPrepayment" scope="request"/>
			                		</logic:present>
			                	</b></td>
			                </tr>
			              </table>
			            </td>
			          </tr>
    					</table>
    				</td>
    			</tr>
          <tr>
    				<td>
    					<table width="100%">
    						<tr valign="top"><td>&nbsp;</td></tr>
    					</table>
    				</td>
    			</tr>
    			<tr>
    				<td align="center">
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/vendorBill/form.do"/>?vendorBillId=<bean:write name="vendorBill" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/vendorBill/delete.do"/>?vendorBillId=<bean:write name="vendorBill" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/vendorBill/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
    				</td>
    			</tr>
          </html:form>
        </table> 
			<!-- CONTENT END -->
          </TD>
        </TR>
      </TABLE>
                    
      </TD>
    </TR>
  <!-- footer start -->
  <tiles:insert template='/common/footer.jsp'/>
  <!-- footer end -->
 </TABLE>
</BODY>
</html:html>








