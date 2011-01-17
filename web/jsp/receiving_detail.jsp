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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.receivingTitle.title"/></TD></TR>
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
    			<html:form action="/receiving/detail">
					<html:hidden property="receivingId"/>								        			
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
			                  	<logic:present name="receiving" property="location">
			                  	<bean:write name="receiving" property="location.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="receiving" property="number" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.receivingDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="receiving" property="formatedReceivingDate" scope="request"/>&nbsp;(dd/MM/yyyy)</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.vendor.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="receiving" property="vendor">
			                  	<bean:write name="receiving" property="vendor.company" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.purchaseOrder.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="receiving" property="purchaseOrder">
			                  	<bean:write name="receiving" property="purchaseOrder.number" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
											<tr> 
			                  <td width="30%" align="right"><bean:message key="page.department.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="receiving" property="department">
			                  	<bean:write name="receiving" property="department.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.project.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="receiving" property="project">
			                  	<bean:write name="receiving" property="project.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.isService.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="receiving" property="service" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.type.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="receiving" property="type" scope="request"/></td>
			                </tr>
			                <!--
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.currency.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="receiving" property="currency">
			                  	<bean:write name="receiving" property="currency.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                -->
              			</table>
              		</td>
              		<td width="10%"></td>
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.taxSerialNumber.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="receiving" property="vendorBill">
			                  	<bean:write name="receiving" property="vendorBill.taxSerialNumber" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.vendorBill.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="receiving" property="vendorBill">
			                  	<bean:write name="receiving" property="vendorBill.number" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.taxDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="receiving" property="vendorBill">
			                  	<bean:write name="receiving" property="vendorBill.taxDate" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.termOfPayment.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="receiving" property="purchaseOrder">
													<logic:present name="receiving" property="purchaseOrder.termOfPayment">
			                  	<bean:write name="receiving" property="purchaseOrder.termOfPayment.name" scope="request"/>
													</logic:present>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="receiving" property="createBy">
			                  	<bean:write name="receiving" property="createBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="receiving" property="formatedCreateOn" scope="request"/></td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="receiving" property="changeBy">
			                  	<bean:write name="receiving" property="changeBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="receiving" property="formatedChangeOn" scope="request"/></td>
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
			                  <td class=titleField><bean:message key="page.returQuantity.title"/></td>
												<td class=titleField><bean:message key="page.warehouse.title"/></td>
												<td class=titleField><bean:message key="page.discount.title"/></td>
												<td class=titleField><bean:message key="page.tax.title"/></td>
			                  <td class=titleField><bean:message key="page.price.title"/>x<bean:message key="page.quantity.title"/></td>
			                  <td class=titleField><bean:message key="page.description.title"/></td>
			                </tr>
			<%
			int i = 0;
			try {
			i = Integer.parseInt(request.getParameter("start"));
			}catch(Exception ex){
			i = 0;
			}
			%>
											<logic:present name="receiving" property="receivingDetails">
			                <logic:iterate id="receivingDetail" name="receiving" property="receivingDetails" type="com.mpe.financial.model.ReceivingDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="receivingDetail" property="id.item">
													<bean:write name="receivingDetail" property="id.item.code" scope="page"/>&nbsp;
													<bean:write name="receivingDetail" property="id.item.name" scope="page"/>
													</logic:present>
												</td>
												<td align="right">
													<i>(<bean:write name="receivingDetail" property="formatedExchangeRate" scope="page"/>)</i>&nbsp;
													<bean:write name="receivingDetail" property="formatedPrice" scope="page"/>
												</td>
												<td><bean:write name="receivingDetail" property="quantity" scope="page"/>&nbsp;
													<logic:present name="receivingDetail" property="itemUnit">
													<bean:write name="receivingDetail" property="itemUnit.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="receivingDetail" property="returQuantity" scope="page"/>&nbsp;
													<logic:present name="receivingDetail" property="itemUnit">
													<bean:write name="receivingDetail" property="itemUnit.name" scope="page"/>
													</logic:present>
												</td>
												<td>
													<logic:present name="receivingDetail" property="warehouse">
													<bean:write name="receivingDetail" property="warehouse.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="receivingDetail" property="formatedDiscountAmount" scope="page"/>/<bean:write name="receivingDetail" property="discountProcent" scope="page"/>%</td>
												<td>
													<logic:present name="receivingDetail" property="tax">
													<bean:write name="receivingDetail" property="tax.name" scope="page"/>
													</logic:present>&nbsp;<i>(<bean:write name="receivingDetail" property="taxAmount"/>%)</i>
												</td>
												<td align="right"><bean:write name="receivingDetail" property="formatedPriceQuantity" scope="page"/></td>
												<td><bean:write name="receivingDetail" property="description" scope="page"/></td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                	<!--
			                		<logic:present name="receiving" property="purchaseOrder">
													<logic:present name="receiving" property="purchaseOrder.currency">
													<bean:write name="receiving" property="purchaseOrder.currency.symbol" scope="request"/>&nbsp;
													</logic:present>
													</logic:present>
													-->
													<bean:write name="receiving" property="formatedReceivingDetailAmount" scope="request"/>
													</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.discount.title"/>&nbsp;<logic:present name="receiving"><logic:present name="receiving" property="purchaseOrder">(<bean:write name="receiving" property="purchaseOrder.discountProcent"/>%)</logic:present></logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="receiving" property="purchaseOrder">
													<logic:present name="receiving" property="purchaseOrder.currency">
													<bean:write name="receiving" property="purchaseOrder.currency.symbol" scope="request"/>&nbsp;
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="receiving" property="formatedAmountDiscount" scope="request"/>
													</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="receiving" property="purchaseOrder">
													<logic:present name="receiving" property="purchaseOrder.currency">
													<bean:write name="receiving" property="purchaseOrder.currency.symbol" scope="request"/>&nbsp;
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="receiving" property="formatedAmountAfterDiscount" scope="request"/>
													</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.tax.title"/>&nbsp;<logic:present name="receiving"><logic:present name="receiving" property="purchaseOrder">(<bean:write name="receiving" property="purchaseOrder.taxAmount"/>%)</logic:present></logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="receiving" property="purchaseOrder">
													<logic:present name="receiving" property="purchaseOrder.currency">
													<bean:write name="receiving" property="purchaseOrder.currency.symbol" scope="request"/>&nbsp;
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="receiving" property="formatedAmountTax" scope="request"/>
													</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="receiving" property="purchaseOrder">
													<logic:present name="receiving" property="purchaseOrder.currency">
													<bean:write name="receiving" property="purchaseOrder.currency.symbol" scope="request"/>&nbsp;
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="receiving" property="formatedAmountAfterTaxAndDiscount" scope="request"/>
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
    				<td>
    					<table width="100%">
    						<tr valign="top"><td>&nbsp;</td></tr>
    					</table>
    				</td>
    			</tr>
    			<tr>
    				<td align="center">
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/receiving/form.do"/>?receivingId=<bean:write name="receiving" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/receiving/delete.do"/>?receivingId=<bean:write name="receiving" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/receiving/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








