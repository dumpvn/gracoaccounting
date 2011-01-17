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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.customerReturTitle.title"/></TD></TR>
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
    			<html:form action="/customerRetur/detail">
					<html:hidden property="customerReturId"/>								        			
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
			                  	<logic:present name="customerRetur" property="location">
			                  	<bean:write name="customerRetur" property="location.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="customerRetur" property="number" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.returDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="customerRetur" property="formatedReturDate" scope="request"/>&nbsp;(dd/MM/yyyy)</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.customer.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerRetur" property="customer">
			                  	<bean:write name="customerRetur" property="customer.company" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.customerAlias.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerRetur" property="customerAlias">
			                  	<bean:write name="customerRetur" property="customerAlias.company" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.deliveryOrder.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerRetur" property="deliveryOrder">
			                  	<bean:write name="customerRetur" property="deliveryOrder.number" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.reference.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="customerRetur" property="reference" scope="request"/></td>
			                </tr>
              			</table>
              		</td>
              		<td width="10%">&nbsp;</td>
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.project.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerRetur" property="project">
													<bean:write name="customerRetur" property="project.name"/>
													</logic:present>
			                  </td>
			                </tr>
											<tr> 
			                  <td width="30%" align="right"><bean:message key="page.department.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerRetur" property="department">
													<bean:write name="customerRetur" property="department.name"/>
													</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.note.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="customerRetur" property="note" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerRetur" property="createBy">
			                  	<bean:write name="customerRetur" property="createBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="customerRetur" property="formatedCreateOn" scope="request"/></td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerRetur" property="changeBy">
			                  	<bean:write name="customerRetur" property="changeBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="customerRetur" property="formatedChangeOn" scope="request"/></td>
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
											<logic:present name="customerRetur" property="customerReturDetails">
			                <logic:iterate id="customerReturDetail" name="customerRetur" property="customerReturDetails" type="com.mpe.financial.model.CustomerReturDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="customerReturDetail" property="id.item">
													<bean:write name="customerReturDetail" property="id.item.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="customerReturDetail" property="formatedPrice" scope="page"/>
													<!--
													<logic:present name="customerReturDetail" property="currency">
													<bean:write name="customerReturDetail" property="currency.name" scope="page"/>
													</logic:present>
													-->
												</td>
												<td><bean:write name="customerReturDetail" property="quantity" scope="page"/>&nbsp;
													<logic:present name="customerReturDetail" property="itemUnit">
													<bean:write name="customerReturDetail" property="itemUnit.name" scope="page"/>
													</logic:present>
												</td>
												<td>
													<logic:present name="customerReturDetail" property="warehouse">
													<bean:write name="customerReturDetail" property="warehouse.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="customerReturDetail" property="formatedDiscountAmount" scope="page"/>/<bean:write name="customerReturDetail" property="discountProcent" scope="page"/>%</td>
												<td>
													<logic:present name="customerReturDetail" property="tax">
													<bean:write name="customerReturDetail" property="tax.name" scope="page"/>
													</logic:present>&nbsp;<i>(<bean:write name="customerReturDetail" property="taxAmount"/>%)</i>
												</td>
												<td align="right">
													<!--
													<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
													<bean:write name="customerReturDetail" property="formatedPriceQuantity" scope="page"/>
												</td>
												<td><bean:write name="customerReturDetail" property="description" scope="page"/></td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="customerRetur" property="formatedCustomerReturDetailAmount" scope="request"/>
													</b></td>
													<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.discount.title"/>&nbsp;<logic:present name="customerRetur" property="deliveryOrder"><logic:present name="customerRetur" property="deliveryOrder.salesOrder">(<bean:write name="customerRetur" property="deliveryOrder.salesOrder.discountProcent"/>%)</logic:present></logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="customerRetur" property="formatedAmountDiscount" scope="request"/>
													</b></td>
													<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="customerRetur" property="formatedAmountAfterDiscount" scope="request"/>
													</b></td>
													<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.tax.title"/>&nbsp;<logic:present name="customerRetur" property="deliveryOrder"><logic:present name="customerRetur" property="deliveryOrder.salesOrder">(<bean:write name="customerRetur" property="deliveryOrder.salesOrder.taxAmount"/>%)</logic:present></logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="customerRetur" property="formatedAmountTax" scope="request"/>
													</b></td>
													<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="customerRetur" property="formatedAmountAfterTaxAndDiscount" scope="request"/>
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
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/customerRetur/form.do"/>?customerReturId=<bean:write name="customerRetur" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/customerRetur/delete.do"/>?customerReturId=<bean:write name="customerRetur" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/customerRetur/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








