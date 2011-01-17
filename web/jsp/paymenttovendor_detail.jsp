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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.paymentToVendorTitle.title"/></TD></TR>
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
    			<html:form action="/paymentToVendor/detail">
					<html:hidden property="paymentToVendorId"/>								        			
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
			                  	<logic:present name="paymentToVendor" property="location">
			                  	<bean:write name="paymentToVendor" property="location.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="paymentToVendor" property="number" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.paymentDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="paymentToVendor" property="formatedPaymentDate" scope="request"/>&nbsp;(dd/MM/yyyy)</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.project.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="paymentToVendor" property="project">
			                  	<bean:write name="paymentToVendor" property="project.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.vendor.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="paymentToVendor" property="vendor">
			                  	<bean:write name="paymentToVendor" property="vendor.company" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.bankAccount.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="paymentToVendor" property="bankAccount">
			                  	<bean:write name="paymentToVendor" property="bankAccount.bankNumberName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.exceedAccount.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="paymentToVendor" property="exceedAccount">
			                  	<bean:write name="paymentToVendor" property="exceedAccount.numberName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.currency.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="paymentToVendor" property="currency">
			                  	<bean:write name="paymentToVendor" property="currency.name" scope="request"/>
			                  	</logic:present>
			                  	&nbsp;<i>(<bean:write name="paymentToVendor" property="formatedExchangeRate" scope="request"/>)</i>
			                  </td>
			                </tr>
              			</table>
              		</td>
              		<td width="10%">&nbsp;</td>
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.method.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="paymentToVendor" property="method" scope="request"/></td>
			                </tr>
			                <!--
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.reference.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="paymentToVendor" property="reference" scope="request"/></td>
			                </tr>
			                -->
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="paymentToVendor" property="description" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="paymentToVendor" property="createBy">
			                  	<bean:write name="paymentToVendor" property="createBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="paymentToVendor" property="formatedCreateOn" scope="request"/></td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="paymentToVendor" property="changeBy">
			                  	<bean:write name="paymentToVendor" property="changeBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="paymentToVendor" property="formatedChangeOn" scope="request"/></td>
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
			                  <td class=titleField><bean:message key="page.vendorBill.title"/></td>
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
											<logic:present name="paymentToVendor" property="paymentToVendorDetails">
			                <logic:iterate id="paymentToVendorDetail" name="paymentToVendor" property="paymentToVendorDetails" type="com.mpe.financial.model.PaymentToVendorDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="paymentToVendorDetail" property="id.vendorBill">
													<bean:write name="paymentToVendorDetail" property="id.vendorBill.number" scope="page"/>
													</logic:present>
												</td>
												<td>
													<i>(<bean:write name="paymentToVendorDetail" property="id.vendorBill.formatedExchangeRate" scope="page"/>)</i>&nbsp;
													<logic:present name="paymentToVendorDetail" property="id.vendorBill">
													<logic:present name="paymentToVendorDetail" property="id.vendorBill.currency">
													<bean:write name="paymentToVendorDetail" property="id.vendorBill.currency.symbol" scope="page"/>&nbsp;
													</logic:present>
													</logic:present>
													<bean:write name="paymentToVendorDetail" property="formatedVendorBillAmount" scope="page"/>
												</td>
												<td align="right">
													<i>(<bean:write name="paymentToVendorDetail" property="formatedVendorBillExchangeRate" scope="page"/>)</i>&nbsp;
													<logic:present name="paymentToVendor" property="currency">
													<bean:write name="paymentToVendor" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="paymentToVendorDetail" property="formatedPaymentAmount" scope="page"/>
												</td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="3"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<logic:present name="paymentToVendor" property="currency">
													<bean:write name="paymentToVendor" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="paymentToVendor" property="formatedPaymentToVendorDetailAmount"/></b></td>
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
			                  <td class=titleField><bean:message key="page.returToVendor.title"/></td>
			                  <td class=titleField><bean:message key="page.amount.title"/></td>
			                </tr>
			<%
			int j = 0;
			%>
											<logic:present name="paymentToVendor" property="returToVendors">
			                <logic:iterate id="returToVendor" name="paymentToVendor" property="returToVendors" type="com.mpe.financial.model.ReturToVendor">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++j%>.</td>
												<td>
													<bean:write name="returToVendor" property="number" scope="page"/>
												</td>
												<td align="right">
													<logic:present name="returToVendor" property="receiving">
													<logic:present name="returToVendor" property="receiving.purchaseOrder">
													<logic:present name="returToVendor" property="receiving.purchaseOrder.currency">
													<bean:write name="returToVendor" property="receiving.purchaseOrder.currency.symbol" scope="page"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													<bean:write name="returToVendor" property="formatedAmountAfterTaxAndDiscount" scope="page"/>
												</td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="2"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<logic:present name="paymentToVendor" property="currency">
													<bean:write name="paymentToVendor" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="paymentToVendor" property="formatedReturToVendorAmount"/></b></td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="2"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<logic:present name="paymentToVendor" property="currency">
													<bean:write name="paymentToVendor" property="currency.symbol"/>&nbsp;
													</logic:present>
													<bean:write name="paymentToVendor" property="formatedPaymentAmount"/></b></td>
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
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/paymentToVendor/form.do"/>?paymentToVendorId=<bean:write name="paymentToVendor" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/paymentToVendor/delete.do"/>?paymentToVendorId=<bean:write name="paymentToVendor" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/paymentToVendor/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








