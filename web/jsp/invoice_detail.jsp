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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.invoiceTitle.title"/></TD></TR>
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
    			<html:form action="/invoice/detail">
					<html:hidden property="invoiceId"/>								        			
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
			                  <td width="30%" align="right"><bean:message key="page.invoiceDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="formatedInvoiceDate"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.invoiceDue.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="formatedInvoiceDue"/></td>
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
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.deliveryOrder.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="invoice" property="deliveryOrder">
			                  	<bean:write name="invoice" property="deliveryOrder.number"/>
			                  	</logic:present>
			                  </td>
			                </tr>
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
			                  	&nbsp;<i>(<bean:write name="invoice" property="formatedExchangeRate" scope="request"/>)</i>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.discount.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="discountProcent"/>&nbsp;%</td>
			                </tr>
			                <!--
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.discount.title"/>#1</td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="discount1"/>&nbsp;%</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.discount.title"/>#2</td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="discount2"/>&nbsp;%</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.discount.title"/>#3</td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="discount3"/>&nbsp;%</td>
			                </tr>
			                -->
              			</table>
              		</td>
              		<td width="10%">&nbsp;</td>
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.project.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="invoice" property="project">
													<bean:write name="invoice" property="project.name"/>
													</logic:present>
			                  </td>
			                </tr>
											<tr> 
			                  <td width="30%" align="right"><bean:message key="page.department.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="invoice" property="department">
													<bean:write name="invoice" property="department.name"/>
													</logic:present>
			                  </td>
			                </tr>
											<tr> 
			                  <td width="30%" align="right"><bean:message key="page.taxSerialNumber.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="npwpNumber"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.taxDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="npwpDate"/></td>
			                </tr>
			                <!--
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.reference.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="reference"/></td>
			                </tr>
			                -->
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="description"/></td>
			                </tr>
											<tr> 
			                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="invoice" property="createBy">
			                  	<bean:write name="invoice" property="createBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="formatedCreateOn" scope="request"/></td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="invoice" property="changeBy">
			                  	<bean:write name="invoice" property="changeBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="invoice" property="formatedChangeOn" scope="request"/></td>
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
											<logic:present name="invoice" property="invoiceDetails">
			                <logic:iterate id="invoiceDetail" name="invoice" property="invoiceDetails" type="com.mpe.financial.model.InvoiceDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="invoiceDetail" property="id.item">
													<bean:write name="invoiceDetail" property="id.item.name" scope="page"/>
													</logic:present>
												</td>
												<td>
													<i>(<bean:write name="invoiceDetail" property="formatedExchangeRate" scope="page"/>)</i>&nbsp;
													<bean:write name="invoiceDetail" property="formatedPrice" scope="page"/>
													<!--
													<logic:present name="invoiceDetail" property="currency">
													<bean:write name="invoiceDetail" property="currency.name" scope="page"/>
													</logic:present>
													-->
												</td>
												<td><bean:write name="invoiceDetail" property="quantity" scope="page"/>&nbsp;
													<logic:present name="invoiceDetail" property="itemUnit">
													<bean:write name="invoiceDetail" property="itemUnit.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="invoiceDetail" property="discountAmount" scope="page"/>/<bean:write name="invoiceDetail" property="discountProcent" scope="page"/>%</td>
												<td>
													<logic:present name="invoiceDetail" property="tax">
													<bean:write name="invoiceDetail" property="tax.name" scope="page"/>
													</logic:present>&nbsp;<i>(<bean:write name="invoiceDetail" property="taxAmount"/>%)</i>
												</td>
			                  <td align="right">
			                  	<!--
			                  	<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                  	<bean:write name="invoiceDetail" property="formatedPriceQuantity" scope="page"/>
												</td>
												<td><bean:write name="invoiceDetail" property="description" scope="page"/></td>
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
			                		<bean:write name="invoice" property="formatedInvoiceDetailAmount"/></b>
			                	</td>
												<td>&nbsp;</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.discount.title"/>&nbsp;<logic:present name="invoice">(<bean:write name="invoice" property="discountProcent"/>%)</logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedInvoiceDiscountAmount"/></b>
			                	</td>
												<td>&nbsp;</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedInvoiceAfterDiscount"/></b>
			                	</td>
												<td>&nbsp;</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.tax.title"/>&nbsp;<logic:present name="invoice">(<bean:write name="invoice" property="taxAmount"/>%)</logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedInvoiceTaxAmount"/></b>
			                	</td>
												<td>&nbsp;</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="6"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedInvoiceAfterDiscountAndTax"/></b>
			                	</td>
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
											<logic:present name="invoice" property="invoicePrepayments">
			                <logic:iterate id="invoicePrepayment" name="invoice" property="invoicePrepayments" type="com.mpe.financial.model.InvoicePrepayment">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++j%>.</td>
												<td>
													<logic:present name="invoicePrepayment" property="id.customerPrepayment">
													<bean:write name="invoicePrepayment" property="id.customerPrepayment.number" scope="page"/>
													</logic:present>
												</td>
												<td>
													<logic:present name="invoicePrepayment" property="id.customerPrepayment">
													<bean:write name="invoicePrepayment" property="id.customerPrepayment.formatedPrepaymentDate" scope="page"/>
													</logic:present>
												</td>
												<td>
													<logic:present name="invoicePrepayment" property="id.customerPrepayment">
													<bean:write name="invoicePrepayment" property="id.customerPrepayment.salesOrder.number" scope="page"/>
													</logic:present>
												</td>
												<td align="right">
													<logic:present name="invoicePrepayment" property="id.customerPrepayment">
													<!--
													<bean:write name="invoicePrepayment" property="id.customerPrepayment.currency.symbol" scope="page"/>&nbsp;
													-->
													<bean:write name="invoicePrepayment" property="id.customerPrepayment.formatedAmount" scope="page"/>
													</logic:present>
												</td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="4"><b><bean:message key="page.prepaymentAmount.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedInvoicePrepaymentAmount"/></b>
			                	</td>
			                </tr>
			                <tr  bgcolor="#FFFFFF">
			                	<td colspan="4"><b><bean:message key="page.amount.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="invoice" property="currency">
													<bean:write name="invoice" property="currency.symbol"/>&nbsp;
													</logic:present>
													-->
			                		<bean:write name="invoice" property="formatedInvoiceAfterDiscountAndTaxAndPrepayment"/></b>
			                	</td>
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
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/invoice/form.do"/>?invoiceId=<bean:write name="invoice" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/invoice/delete.do"/>?invoiceId=<bean:write name="invoice" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/invoice/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>&nbsp;&nbsp;
    					<input class="Button" type="button" name="create" onclick="window.open('<html:rewrite page="/invoice/fakturPajak.do"/>?invoiceId=<bean:write name="invoice" property="id"/>','gg','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width=800,height=600');" value="Faktur Pajak">
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








