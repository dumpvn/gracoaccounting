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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.deliveryOrderTitle.title"/></TD></TR>
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
    			<html:form action="/deliveryOrder/detail">
					<html:hidden property="deliveryOrderId"/>								        			
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
			                  	<logic:present name="deliveryOrder" property="location">
			                  	<bean:write name="deliveryOrder" property="location.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="deliveryOrder" property="number" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.deliveryDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="deliveryOrder" property="formatedDeliveryDate" scope="request"/>&nbsp;(dd/MM/yyyy)</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.customer.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="deliveryOrder" property="customer">
			                  	<bean:write name="deliveryOrder" property="customer.company" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.customerAlias.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="deliveryOrder" property="customerAlias">
			                  	<bean:write name="deliveryOrder" property="customerAlias.company" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.salesOrder.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="deliveryOrder" property="salesOrder">
			                  	<bean:write name="deliveryOrder" property="salesOrder.number" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.project.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="deliveryOrder" property="project">
			                  	<bean:write name="deliveryOrder" property="project.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
											<tr> 
			                  <td width="30%" align="right"><bean:message key="page.department.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="deliveryOrder" property="department">
			                  	<bean:write name="deliveryOrder" property="department.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
              			</table>
              		</td>
              		<td width="10%">&nbsp;</td>
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.policeNumber.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="deliveryOrder" property="policeNumber" scope="request"/></td>
			                </tr>
			                 <tr> 
			                  <td width="30%" align="right"><bean:message key="page.vehicle.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="deliveryOrder" property="vehicle" scope="request"/></td>
			                </tr>
											<tr> 
			                  <td width="30%" align="right"><bean:message key="page.expedition.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="deliveryOrder" property="expedition" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="deliveryOrder" property="createBy">
			                  	<bean:write name="deliveryOrder" property="createBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="deliveryOrder" property="formatedCreateOn" scope="request"/></td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="deliveryOrder" property="changeBy">
			                  	<bean:write name="deliveryOrder" property="changeBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="deliveryOrder" property="formatedChangeOn" scope="request"/></td>
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
											<logic:present name="deliveryOrder" property="deliveryOrderDetails">
			                <logic:iterate id="deliveryOrderDetail" name="deliveryOrder" property="deliveryOrderDetails" type="com.mpe.financial.model.DeliveryOrderDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="deliveryOrderDetail" property="id.item">
													<bean:write name="deliveryOrderDetail" property="id.item.name" scope="page"/>
													</logic:present>
												</td>
												<td>
													<i>(<bean:write name="deliveryOrderDetail" property="formatedExchangeRate" scope="page"/>)</i>&nbsp;
													<bean:write name="deliveryOrderDetail" property="formatedPrice" scope="page"/>
													<!--
													<logic:present name="deliveryOrderDetail" property="currency">
													<bean:write name="deliveryOrderDetail" property="currency.name" scope="page"/>
													</logic:present>
													-->
												</td>
												<td><bean:write name="deliveryOrderDetail" property="quantity" scope="page"/>&nbsp;
													<logic:present name="deliveryOrderDetail" property="itemUnit">
													<bean:write name="deliveryOrderDetail" property="itemUnit.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="deliveryOrderDetail" property="returQuantity" scope="page"/>&nbsp;
													<logic:present name="deliveryOrderDetail" property="itemUnit">
													<bean:write name="deliveryOrderDetail" property="itemUnit.name" scope="page"/>
													</logic:present>
												</td>
												<td>
													<logic:present name="deliveryOrderDetail" property="warehouse">
													<bean:write name="deliveryOrderDetail" property="warehouse.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="deliveryOrderDetail" property="formatedDiscountAmount" scope="page"/>/<bean:write name="deliveryOrderDetail" property="discountProcent" scope="page"/>%</td>
												<td>
													<logic:present name="deliveryOrderDetail" property="tax">
													<bean:write name="deliveryOrderDetail" property="tax.name" scope="page"/>
													</logic:present>&nbsp;<i>(<bean:write name="deliveryOrderDetail" property="taxAmount"/>%)</i>
												</td>
												<td align="right">
													<!--
													<logic:present name="deliveryOrder" property="salesOrder">
													<logic:present name="deliveryOrder" property="salesOrder.currency">
													<bean:write name="deliveryOrder" property="salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													-->
													<bean:write name="deliveryOrderDetail" property="formatedPriceQuantity" scope="page"/>
													
												</td>
												<td><bean:write name="deliveryOrderDetail" property="description" scope="page"/></td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="deliveryOrder" property="salesOrder">
													<logic:present name="deliveryOrder" property="salesOrder.currency">
													<bean:write name="deliveryOrder" property="salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="deliveryOrder" property="formatedDeliveryOrderDetailAmount" scope="request"/>
												</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.discount.title"/>&nbsp;<logic:present name="deliveryOrder" property="salesOrder">(<bean:write name="deliveryOrder" property="salesOrder.discountProcent"/>%)</logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="deliveryOrder" property="salesOrder">
													<logic:present name="deliveryOrder" property="salesOrder.currency">
													<bean:write name="deliveryOrder" property="salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="deliveryOrder" property="formatedAmountDiscount" scope="request"/>
												</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="deliveryOrder" property="salesOrder">
													<logic:present name="deliveryOrder" property="salesOrder.currency">
													<bean:write name="deliveryOrder" property="salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="deliveryOrder" property="formatedAmountAfterDiscount" scope="request"/>
												</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.tax.title"/>&nbsp;<logic:present name="deliveryOrder" property="salesOrder">(<bean:write name="deliveryOrder" property="salesOrder.taxAmount"/>%)</logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="deliveryOrder" property="salesOrder">
													<logic:present name="deliveryOrder" property="salesOrder.currency">
													<bean:write name="deliveryOrder" property="salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													-->
													<bean:write name="deliveryOrder" property="formatedAmountTax" scope="request"/>
			                	</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="deliveryOrder" property="salesOrder">
													<logic:present name="deliveryOrder" property="salesOrder.currency">
													<bean:write name="deliveryOrder" property="salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="deliveryOrder" property="formatedAmountAfterTaxAndDiscount" scope="request"/>
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
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/deliveryOrder/form.do"/>?deliveryOrderId=<bean:write name="deliveryOrder" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/deliveryOrder/delete.do"/>?deliveryOrderId=<bean:write name="deliveryOrder" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/deliveryOrder/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








