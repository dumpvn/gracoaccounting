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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.customerPaymentTitle.title"/></TD></TR>
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
    			<html:form action="/customerPayment/detail">
					<html:hidden property="customerPaymentId"/>								        			
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
			                  	<logic:present name="customerPayment" property="location">
			                  	<bean:write name="customerPayment" property="location.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="customerPayment" property="number" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.paymentDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="customerPayment" property="paymentDate" scope="request"/>&nbsp;(dd/MM/yyyy)</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.method.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="customerPayment" property="method" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.customer.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerPayment" property="customer">
			                  	<bean:write name="customerPayment" property="customer.company" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.customerAlias.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerPayment" property="customerAlias">
			                  	<bean:write name="customerPayment" property="customerAlias.company" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.bankAccount.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerPayment" property="bankAccount">
			                  	<bean:write name="customerPayment" property="bankAccount.number" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.exceedAccount.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerPayment" property="exceedAccount">
			                  	<bean:write name="customerPayment" property="exceedAccount.numberName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
              			</table>
              		</td>
              		<td width="10%">&nbsp;</td>
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.currency.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerPayment" property="currency">
			                  	<bean:write name="customerPayment" property="currency.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.reference.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="customerPayment" property="reference" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="customerPayment" property="description" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerPayment" property="createBy">
			                  	<bean:write name="customerPayment" property="createBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="customerPayment" property="formatedCreateOn" scope="request"/></td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="customerPayment" property="changeBy">
			                  	<bean:write name="customerPayment" property="changeBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="customerPayment" property="formatedChangeOn" scope="request"/></td>
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
			                  <td class=titleField><bean:message key="page.invoice.title"/></td>
			                  <td class=titleField><bean:message key="page.amount.title"/></td>
			                  <td class=titleField><bean:message key="page.paymentAmount.title"/></td>
			                </tr>
			<%
			int i = 0;
			try {
			i = Integer.parseInt(request.getParameter("start"));
			}catch(Exception ex){
			i = 0;
			}
			%>
											<logic:present name="customerPayment" property="customerPaymentDetails">
			                <logic:iterate id="customerPaymentDetail" name="customerPayment" property="customerPaymentDetails" type="com.mpe.financial.model.CustomerPaymentDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="customerPaymentDetail" property="invoice">
													<bean:write name="customerPaymentDetail" property="invoice.number" scope="page"/>
													</logic:present>
													<logic:present name="customerPaymentDetail" property="invoiceSimple">
													<bean:write name="customerPaymentDetail" property="invoiceSimple.number" scope="page"/>
													</logic:present>
													<logic:present name="customerPaymentDetail" property="invoicePolos">
													<bean:write name="customerPaymentDetail" property="invoicePolos.number" scope="page"/>
													</logic:present>
												</td>
												<td>
													<logic:present name="customerPaymentDetail" property="invoice">
													<logic:present name="customerPaymentDetail" property="invoice.currency">
													<i>(<bean:write name="customerPaymentDetail" property="invoice.formatedExchangeRate" scope="page"/>)</i>&nbsp;
													<bean:write name="customerPaymentDetail" property="invoice.currency.symbol" scope="page"/>
													</logic:present>
													</logic:present>
													<logic:present name="customerPaymentDetail" property="invoicePolos">
													<logic:present name="customerPaymentDetail" property="invoicePolos.currency">
													<i>(<bean:write name="customerPaymentDetail" property="invoicePolos.formatedExchangeRate" scope="page"/>)</i>&nbsp;
													<bean:write name="customerPaymentDetail" property="invoicePolos.currency.symbol" scope="page"/>
													</logic:present>
													</logic:present>
													<logic:present name="customerPaymentDetail" property="invoiceSimple">
													<logic:present name="customerPaymentDetail" property="invoiceSimple.currency">
													<i>(<bean:write name="customerPaymentDetail" property="invoiceSimple.formatedExchangeRate" scope="page"/>)</i>&nbsp;
													<bean:write name="customerPaymentDetail" property="invoiceSimple.currency.symbol" scope="page"/>
													</logic:present>
													</logic:present>
													<bean:write name="customerPaymentDetail" property="formatedInvoiceAmount" scope="page"/>
												</td>
												<td align="right">
													<i>(<bean:write name="customerPaymentDetail" property="formatedInvoiceExchangeRate" scope="page"/>)</i>&nbsp;
													<logic:present name="customerPayment" property="currency">
													<bean:write name="customerPayment" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="customerPaymentDetail" property="formatedPaymentAmount" scope="page"/>
													
												</td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="3"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<logic:present name="customerPayment" property="currency">
													<bean:write name="customerPayment" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="customerPayment" property="formatedCustomerPaymentDetailAmount"/></b></td>
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
			                  <td class=titleField><bean:message key="page.customerRetur.title"/></td>
			                  <td class=titleField><bean:message key="page.amount.title"/></td>
			                </tr>
			<%
			int j = 0;
			%>
											<logic:present name="customerPayment" property="customerReturs">
			                <logic:iterate id="customerRetur" name="customerPayment" property="customerReturs" type="com.mpe.financial.model.CustomerRetur">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++j%>.</td>
												<td>
													<bean:write name="customerRetur" property="number" scope="page"/>
												</td>
												<td align="right">
													<logic:present name="customerRetur" property="deliveryOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.salesOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.salesOrder.currency.symbol" scope="page"/>&nbsp;
													</logic:present>
													</logic:present>
													<logic:notPresent name="customerRetur" property="deliveryOrder.salesOrder">
													<logic:present name="customerRetur" property="deliveryOrder.currency">
													<bean:write name="customerRetur" property="deliveryOrder.currency.symbol" scope="page"/>&nbsp;
													</logic:present>
													</logic:notPresent>
													</logic:present>
													<bean:write name="customerRetur" property="formatedAmountAfterTaxAndDiscount" scope="page"/>
												</td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="2"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<logic:present name="customerPayment" property="currency">
													<bean:write name="customerPayment" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="customerPayment" property="formatedCustomerReturAmount"/></b></td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="2"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<logic:present name="customerPayment" property="currency">
													<bean:write name="customerPayment" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="customerPayment" property="formatedPaymentAmount"/></b></td>
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
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/customerPayment/form.do"/>?customerPaymentId=<bean:write name="customerPayment" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/customerPayment/delete.do"/>?customerPaymentId=<bean:write name="customerPayment" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/customerPayment/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








