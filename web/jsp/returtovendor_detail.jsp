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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.returToVendorTitle.title"/></TD></TR>
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
    			<html:form action="/returToVendor/detail">
					<html:hidden property="returToVendorId"/>								        			
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
			                  	<logic:present name="returToVendor" property="location">
			                  	<bean:write name="returToVendor" property="location.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="returToVendor" property="number" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.returDate.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="returToVendor" property="returDate" scope="request"/>&nbsp;(dd/MM/yyyy)</td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.vendor.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="returToVendor" property="vendor">
			                  	<bean:write name="returToVendor" property="vendor.company" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.receiving.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="returToVendor" property="receiving">
			                  	<bean:write name="returToVendor" property="receiving.number" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
											<tr> 
			                  <td width="30%" align="right"><bean:message key="page.department.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="returToVendor" property="department">
			                  	<bean:write name="returToVendor" property="department.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.project.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="returToVendor" property="project">
			                  	<bean:write name="returToVendor" property="project.name" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.reference.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="returToVendor" property="reference" scope="request"/></td>
			                </tr>
              			</table>
              		</td>
              		<td width="10%">&nbsp;</td>
              		<td width="45%" valign="top">
              			<table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              				<tr> 
			                  <td width="30%" align="right"><bean:message key="page.note.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="returToVendor" property="note" scope="request"/></td>
			                </tr>
			                <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="returToVendor" property="createBy">
			                  	<bean:write name="returToVendor" property="createBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="returToVendor" property="formatedCreateOn" scope="request"/></td>
			                </tr>
					           <tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%">
			                  	<logic:present name="returToVendor" property="changeBy">
			                  	<bean:write name="returToVendor" property="changeBy.userName" scope="request"/>
			                  	</logic:present>
			                  </td>
			                </tr>
					           	<tr> 
			                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
			                  <td width="5%" align="center">:</td>
			                  <td width="65%"><bean:write name="returToVendor" property="formatedChangeOn" scope="request"/></td>
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
											<logic:present name="returToVendor" property="returToVendorDetails">
			                <logic:iterate id="returToVendorDetail" name="returToVendor" property="returToVendorDetails" type="com.mpe.financial.model.ReturToVendorDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="returToVendorDetail" property="id.item">
													<bean:write name="returToVendorDetail" property="id.item.code" scope="page"/>&nbsp;
													<bean:write name="returToVendorDetail" property="id.item.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="returToVendorDetail" property="formatedPrice" scope="page"/>
													<!--
													<logic:present name="returToVendorDetail" property="currency">
													<bean:write name="returToVendorDetail" property="currency.name" scope="page"/>
													</logic:present>
													-->
												</td>
												<td><bean:write name="returToVendorDetail" property="quantity" scope="page"/>&nbsp;
													<logic:present name="returToVendorDetail" property="itemUnit">
													<bean:write name="returToVendorDetail" property="itemUnit.name" scope="page"/>
													</logic:present>
												</td>
												<td>
													<logic:present name="returToVendorDetail" property="warehouse">
													<bean:write name="returToVendorDetail" property="warehouse.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="returToVendorDetail" property="formatedDiscountAmount" scope="page"/>/<bean:write name="returToVendorDetail" property="discountProcent" scope="page"/>%</td>
												<td>
													<logic:present name="returToVendorDetail" property="tax">
													<bean:write name="returToVendorDetail" property="tax.name" scope="page"/>
													</logic:present>&nbsp;<i>(<bean:write name="returToVendorDetail" property="taxAmount" scope="page"/>%)</i>
												</td>
												<td align="right">
													<!--
													<logic:present name="returToVendor" property="receiving">
													<logic:present name="returToVendor" property="receiving.purchaseOrder">
													<logic:present name="returToVendor" property="receiving.purchaseOrder.currency">
													<bean:write name="returToVendor" property="receiving.purchaseOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
													<bean:write name="returToVendorDetail" property="formatedPriceQuantity" scope="page"/>
												</td>
												<td><bean:write name="returToVendorDetail" property="description" scope="page"/></td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="returToVendor" property="receiving">
													<logic:present name="returToVendor" property="receiving.purchaseOrder">
													<logic:present name="returToVendor" property="receiving.purchaseOrder.currency">
													<bean:write name="returToVendor" property="receiving.purchaseOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="returToVendor" property="formatedReturToVendorDetailAmount" scope="request"/>
													</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.discount.title"/>&nbsp;<logic:present name="returToVendor" property="receiving"><logic:present name="returToVendor" property="receiving.purchaseOrder">(<bean:write name="returToVendor" property="receiving.purchaseOrder.discountProcent"/>%)</logic:present></logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="returToVendor" property="receiving">
													<logic:present name="returToVendor" property="receiving.purchaseOrder">
													<logic:present name="returToVendor" property="receiving.purchaseOrder.currency">
													<bean:write name="returToVendor" property="receiving.purchaseOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="returToVendor" property="formatedAmountDiscount" scope="request"/>
													</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.subtotal.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="returToVendor" property="receiving">
													<logic:present name="returToVendor" property="receiving.purchaseOrder">
													<logic:present name="returToVendor" property="receiving.purchaseOrder.currency">
													<bean:write name="returToVendor" property="receiving.purchaseOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="returToVendor" property="formatedAmountAfterDiscount" scope="request"/>
													</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.tax.title"/>&nbsp;<logic:present name="returToVendor" property="receiving"><logic:present name="returToVendor" property="receiving.purchaseOrder">(<bean:write name="returToVendor" property="receiving.purchaseOrder.taxAmount"/>%)</logic:present></logic:present></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="returToVendor" property="receiving">
													<logic:present name="returToVendor" property="receiving.purchaseOrder">
													<logic:present name="returToVendor" property="receiving.purchaseOrder.currency">
													<bean:write name="returToVendor" property="receiving.purchaseOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="returToVendor" property="formatedAmountTax" scope="request"/>
													</b></td>
												<td>&nbsp;</td>
			                </tr>
			                <tr bgcolor="#FFFFFF">
			                	<td colspan="7"><b><bean:message key="page.total.title"/></b></td>
			                	<td align="right"><b>
			                		<!--
			                		<logic:present name="returToVendor" property="receiving">
													<logic:present name="returToVendor" property="receiving.purchaseOrder">
													<logic:present name="returToVendor" property="receiving.purchaseOrder.currency">
													<bean:write name="returToVendor" property="receiving.purchaseOrder.currency.symbol"/>&nbsp;
													</logic:present>
													</logic:present>
													</logic:present>
													-->
			                		<bean:write name="returToVendor" property="formatedAmountAfterTaxAndDiscount" scope="request"/>
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
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/returToVendor/form.do"/>?returToVendorId=<bean:write name="returToVendor" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/returToVendor/delete.do"/>?returToVendorId=<bean:write name="returToVendor" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/returToVendor/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








