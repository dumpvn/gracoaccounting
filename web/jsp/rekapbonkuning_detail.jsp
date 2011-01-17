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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.rekapBonKuningTitle.title"/></TD></TR>
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
    			<html:form action="/rekapBonKuning/detail">
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
			                  	<logic:present name="invoice" property="location">
			                  	<bean:write name="invoice" property="location.name"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="number"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.deliveryDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="deliveryDate"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.fromBonKuningDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="fromBonKuningDate"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.toBonKuningDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="toBonKuningDate"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.isDetail.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="detail"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.customer.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="invoice" property="customer">
			                  	<bean:write name="invoice" property="customer.company"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.customerAlias.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="invoice" property="customerAlias">
			                  	<bean:write name="invoice" property="customerAlias.company"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                
			               
              			</table>
              		</td>
              		<td width="10%">&nbsp;</td>
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.tax.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="invoice" property="tax">
			                  	<bean:write name="invoice" property="tax.name"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.currency.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="invoice" property="currency">
			                  	<bean:write name="invoice" property="currency.name"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.discount.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="discount1"/>&nbsp;%</td>
			                </tr>
			                
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="description"/></td>
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
          
          <logic:notEqual name="invoice" property="detail" value="true">
         	<tr>
    				<td>
    					<table width="100%">
    						<tr bgcolor="#FFFFFF"> 
			            <td align="center"> 
			              <table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
			                <tr align="center"> 
			                  <td width="5%" class=titleField>&nbsp;</td>
			                  <td class=titleField><bean:message key="page.date.title"/></td>
			                  <td class=titleField><bean:message key="page.bonKuning.title"/></td>
			                  <td class=titleField><bean:message key="page.kuantum.title"/></td>
			                  <td class=titleField><bean:message key="page.bruto.title"/></td>
			                  <td class=titleField><bean:message key="page.discount.title"/></td>
			                  <td class=titleField><bean:message key="page.netto.title"/></td>
			                </tr>
			<%
			int i = 0;
			try {
			i = Integer.parseInt(request.getParameter("start"));
			}catch(Exception ex){
			i = 0;
			}
			%>
											
											<logic:present name="invoice" property="deliveryOrders">
			                <logic:iterate id="deliveryOrder" name="invoice" property="deliveryOrders" type="com.mpe.financial.model.DeliveryOrder">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<bean:write name="deliveryOrder" property="formatedDeliveryDate" scope="page"/>&nbsp;
												</td>
												<td><bean:write name="deliveryOrder" property="number" scope="page"/></td>
												<td><bean:write name="deliveryOrder" property="deliveryOrderDetailQuantity" scope="page"/></td>
												<td align="right"><bean:write name="deliveryOrder" property="formatedDeliveryOrderDetailBrutoAmount" scope="page"/></td>
												<td align="right"><bean:write name="deliveryOrder" property="formatedDeliveryOrderDetailDiscountAmount" scope="page"/></td>
												<td align="right"><bean:write name="deliveryOrder" property="formatedAmountAfterTaxAndDiscount" scope="page"/>
												</td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedDeliveryOrderDetailAmount"/></b>
			                	</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.discount.title"/>&nbsp;<logic:present name="invoice">(<bean:write name="invoice" property="discount1"/>%)</logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedAmountDiscount"/></b>
			                	</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedAmountAfterDiscount"/></b>
			                	</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.tax.title"/>&nbsp;<logic:present name="invoice">(<bean:write name="invoice" property="taxAmount"/>%)</logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedAmountTax"/></b>
			                	</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedAmountAfterTaxAndDiscount"/></b>
			                	</td>
			                </tr>
			              </table>
			            </td>
			          </tr>
    					</table>
    				</td>
    			</tr>
         	</logic:notEqual>
         	
         	<logic:equal name="invoice" property="detail" value="true">
          <tr>
    				<td>
    					<table width="100%">
    						<tr bgcolor="#FFFFFF"> 
			            <td align="center"> 
			              <table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
			                <tr align="center"> 
			                  <td width="5%" class=titleField>&nbsp;</td>
			                  <td class=titleField><bean:message key="page.date.title"/></td>
			                  <td class=titleField><bean:message key="page.bonKuning.title"/></td>
			                  <td class=titleField><bean:message key="page.item.title"/></td>
			                  <td class=titleField><bean:message key="page.price.title"/></td>
			                  <td class=titleField><bean:message key="page.quantity.title"/></td>
			                  <td class=titleField><bean:message key="page.bruto.title"/></td>
			                  <td class=titleField><bean:message key="page.discount.title"/></td>
			                  <td class=titleField><bean:message key="page.netto.title"/></td>
			                </tr>
			<%
			int i = 0;
			try {
			i = Integer.parseInt(request.getParameter("start"));
			}catch(Exception ex){
			i = 0;
			}
			%>
											
			                <logic:present name="invoice" property="deliveryOrders">
			                <logic:iterate id="deliveryOrder" name="invoice" property="deliveryOrders" type="com.mpe.financial.model.DeliveryOrder">
			                <logic:iterate id="deliveryOrderDetail" name="deliveryOrder" property="deliveryOrderDetails" type="com.mpe.financial.model.DeliveryOrderDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td><bean:write name="deliveryOrderDetail" property="id.deliveryOrder.formatedDeliveryDate" scope="page"/></td>
												<td><bean:write name="deliveryOrderDetail" property="id.deliveryOrder.number" scope="page"/></td>
												<td><bean:write name="deliveryOrderDetail" property="id.item.name" scope="page"/></td>
												<td align="right"><bean:write name="deliveryOrderDetail" property="formatedPrice" scope="page"/></td>
												<td><bean:write name="deliveryOrderDetail" property="quantity" scope="page"/></td>
												<td align="right"><bean:write name="deliveryOrderDetail" property="formatedPriceQuantityBruto" scope="page"/></td>
												<td align="right"><bean:write name="deliveryOrderDetail" property="formatedPriceQuantityDiscount" scope="page"/></td>
												<td align="right"><bean:write name="deliveryOrderDetail" property="formatedPriceQuantity" scope="page"/></td>
			                </tr>
			                </logic:iterate>
			                </logic:iterate>
			                </logic:present>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedDeliveryOrderDetailAmount"/></b>
			                	</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.discount.title"/>&nbsp;<logic:present name="invoice">(<bean:write name="invoice" property="discount1"/>%)</logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedAmountDiscount"/></b>
			                	</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedAmountAfterDiscount"/></b>
			                	</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.tax.title"/>&nbsp;<logic:present name="invoice">(<bean:write name="invoice" property="taxAmount"/>%)</logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedAmountTax"/></b>
			                	</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="8"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedAmountAfterTaxAndDiscount"/></b>
			                	</td>
			                </tr>
			              </table>
			            </td>
			          </tr>
    					</table>
    				</td>
    			</tr>
    			</logic:equal>
          
          
          

          <tr>
    				<td>
    					<table width="100%">
    						<tr valign="top"><td>&nbsp;</td></tr>
    					</table>
    				</td>
    			</tr>
    			<tr>
    				<td align="center">
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/rekapBonKuning/form.do"/>?deliveryOrderId=<bean:write name="invoice" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/rekapBonKuning/delete.do"/>?deliveryOrderId=<bean:write name="invoice" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/rekapBonKuning/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








