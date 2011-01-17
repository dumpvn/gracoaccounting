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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.salesOrderTitle.title"/></TD></TR>
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
    			<html:form action="/salesOrderProject/detail">
					<html:hidden property="salesOrderId"/>								        			
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
			                  	<logic:present name="salesOrder" property="location">
			                  	<bean:write name="salesOrder" property="location.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="salesOrder" property="number" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.orderDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="salesOrder" property="formatedOrderDate" scope="request"/>&nbsp;(dd/MM/yyyy)</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.customer.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="salesOrder" property="customer">
			                  	<bean:write name="salesOrder" property="customer.company" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.customerAlias.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="salesOrder" property="customerAlias">
			                  	<bean:write name="salesOrder" property="customerAlias.company" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.termOfPayment.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
													<logic:present name="salesOrder" property="termOfPayment">
													<bean:write name="salesOrder" property="termOfPayment.name" scope="request"/>
													</logic:present>
												</td>
			                </tr>
											<tr> 
			                  <td width="30%" align="right"><bean:message key="page.department.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="salesOrder" property="department">
			                  	<bean:write name="salesOrder" property="department.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <!--
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.project.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="salesOrder" property="project">
			                  	<bean:write name="salesOrder" property="project.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                -->
											<tr> 
			                  <td width="30%" align="right"><bean:message key="page.currency.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="salesOrder" property="currency">
			                  	<bean:write name="salesOrder" property="currency.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
											<tr> 
			                  <td width="30%" align="right"><bean:message key="page.tax.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="salesOrder" property="tax">
			                  	<bean:write name="salesOrder" property="tax.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.taxAmount.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="salesOrder" property="taxAmount" scope="request"/>%</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.discount.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="salesOrder" property="discountProcent" scope="request"/>%</td>
			                </tr>
              			</table>
              		</td>
              		<td width="10%">&nbsp;</td>
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.salesman.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="salesOrder" property="salesman">
			                  	<bean:write name="salesOrder" property="salesman.fullName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.purchaseOrder.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="salesOrder" property="purchaseOrder" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.deliveryDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="salesOrder" property="deliveryDate" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="salesOrder" property="createBy">
			                  	<bean:write name="salesOrder" property="createBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="salesOrder" property="formatedCreateOn" scope="request"/></td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="salesOrder" property="changeBy">
			                  	<bean:write name="salesOrder" property="changeBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="salesOrder" property="formatedChangeOn" scope="request"/></td>
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
			                  <td class=titleField><bean:message key="page.receiveQuantity.title"/></td>
			                  <td class=titleField><bean:message key="page.returQuantity.title"/></td>
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
											<logic:present name="salesOrder" property="salesOrderDetails">
			                <logic:iterate id="salesOrderDetail" name="salesOrder" property="salesOrderDetails" type="com.mpe.financial.model.SalesOrderDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="salesOrderDetail" property="id.item">
													<bean:write name="salesOrderDetail" property="id.item.name" scope="page"/>
													</logic:present>
												</td>
												<td>
													<i>(<bean:write name="salesOrderDetail" property="formatedExchangeRate" scope="page"/>)</i>&nbsp;
													<bean:write name="salesOrderDetail" property="formatedPrice" scope="page"/>
													<!--
													<logic:present name="salesOrderDetail" property="currency">
													<bean:write name="salesOrderDetail" property="currency.name" scope="page"/>
													</logic:present>
													-->
												</td>
												<td><bean:write name="salesOrderDetail" property="quantity" scope="page"/>&nbsp;
													<logic:present name="salesOrderDetail" property="itemUnit">
													<bean:write name="salesOrderDetail" property="itemUnit.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="salesOrderDetail" property="deliveryQuantity" scope="page"/>&nbsp;
													<logic:present name="salesOrderDetail" property="itemUnit">
													<bean:write name="salesOrderDetail" property="itemUnit.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="salesOrderDetail" property="returQuantity" scope="page"/>&nbsp;
													<logic:present name="salesOrderDetail" property="itemUnit">
													<bean:write name="salesOrderDetail" property="itemUnit.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="salesOrderDetail" property="discountAmount" scope="page"/>/<bean:write name="salesOrderDetail" property="discountProcent" scope="page"/>%</td>
												<td>
													<logic:present name="salesOrderDetail" property="tax">
													<bean:write name="salesOrderDetail" property="tax.name" scope="page"/>
													</logic:present>&nbsp;<i>(<bean:write name="salesOrderDetail" property="taxAmount" scope="page"/>%)</i>
												</td>
												<td align="right">
													<!--
													<logic:present name="salesOrder" property="currency">
													<bean:write name="salesOrder" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
													<bean:write name="salesOrderDetail" property="formatedPriceQuantity" scope="page"/>
												</td>
												<td><bean:write name="salesOrderDetail" property="description" scope="page"/></td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="salesOrder" property="currency">
													<bean:write name="salesOrder" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="salesOrder" property="formatedSalesOrderDetailAmount" scope="request"/>
												</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.discount.title"/>&nbsp;<logic:present name="salesOrder">(<bean:write name="salesOrder" property="discountProcent"/>%)</logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="salesOrder" property="currency">
													<bean:write name="salesOrder" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="salesOrder" property="formatedAmountDiscount" scope="request"/>
												</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="salesOrder" property="currency">
													<bean:write name="salesOrder" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="salesOrder" property="formatedAmountAfterDiscount" scope="request"/>
												</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.tax.title"/>&nbsp;<logic:present name="salesOrder">(<bean:write name="salesOrder" property="taxAmount"/>%)</logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="salesOrder" property="currency">
													<bean:write name="salesOrder" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
													<bean:write name="salesOrder" property="formatedAmountTax" scope="request"/>
			                	</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="salesOrder" property="currency">
													<bean:write name="salesOrder" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="salesOrder" property="formatedAmountAfterTaxAndDiscount" scope="request"/>
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
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/salesOrderProject/form.do"/>?salesOrderId=<bean:write name="salesOrder" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/salesOrderProject/delete.do"/>?salesOrderId=<bean:write name="salesOrder" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/salesOrderProject/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








