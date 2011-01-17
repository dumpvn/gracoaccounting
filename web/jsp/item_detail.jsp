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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.itemTitle.title"/></TD></TR>
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
    			<html:form action="/item/detail">
					<html:hidden property="itemId"/>								        			
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.parent.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="inventory" property="parent">
                  	<bean:write name="inventory" property="parent.name" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.itemCategory.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="inventory" property="itemCategory">
                  	<bean:write name="inventory" property="itemCategory.name" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.type.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="type" scope="request"/></td>
                </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.code.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="code" scope="request"/></td>
                </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.plu.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="plu" scope="request"/></td>
                </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.barcode.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="barcode" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.name.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="name" scope="request"/></td>
                </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.isActive.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="active" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="description" scope="request"/></td>
                </tr>
                <tr> 
                  <td colspan="3">&nbsp;</td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.itemPrice.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">&nbsp;</td>
                </tr>
                <tr> 
                  <td colspan="3" align="center">
										<table border="0" cellspacing=1 cellpadding=0 bgcolor="#dddddd" width="100%">
											<tr align="center"> 
			                  <td width="5%" class=titleField>&nbsp;</td>
			                  <td class=titleField><bean:message key="page.itemPriceCategory.title"/></td>
			                  <td class=titleField><bean:message key="page.price.title"/></td>
			                  <td class=titleField><bean:message key="page.currency.title"/></td>
			                  <td class=titleField><bean:message key="page.isDefault.title"/></td>
			                  <td class=titleField><bean:message key="page.incomeAccount.title"/></td>
												<td class=titleField><bean:message key="page.salesTax.title"/></td>
			                </tr>
			                <% int z=0; %>
			                <logic:iterate id="itemPrice" name="inventory" property="itemPrices" type="com.mpe.financial.model.ItemPrice" scope="request">
			                <tr bgcolor="#FFFFFF" align=center> 
			                  <td><%= ++z%></td>
			                  <td>
			                  	<logic:present name="itemPrice" property="id.itemPriceCategory">
			                  	<bean:write name="itemPrice" property="id.itemPriceCategory.name" scope="page"/>
			                  	</logic:present>
			                  </td>
			                  <td><bean:write name="itemPrice" property="formatedPrice" scope="page"/></td>
			                  <td><logic:present name="itemPrice" property="currency"><bean:write name="itemPrice" property="currency.name" scope="page"/></logic:present></td>
			                  <td><bean:write name="itemPrice" property="default" scope="page"/></td>
			                  <td>
			                  	<logic:present name="itemPrice" property="chartOfAccount">
			                  	<bean:write name="itemPrice" property="chartOfAccount.numberName" scope="page"/>
			                  	</logic:present>
			                  </td>
												<td>
			                  	<logic:present name="itemPrice" property="salesTax">
			                  	<bean:write name="itemPrice" property="salesTax.name" scope="page"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                </logic:iterate>
										</table>
									</td>
                </tr>
                <tr>
                	<td colspan="3">&nbsp;</td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.inventoryAccount.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="inventory" property="inventoryAccount">
                  	<bean:write name="inventory" property="inventoryAccount.numberName" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.minimumStock.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="minimumStock" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.maximumStock.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="maximumStock" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.itemUnit.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="inventory" property="itemUnit">
                  	<bean:write name="inventory" property="itemUnit.name" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr>
                	<td colspan="3">&nbsp;</td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.costPriceAccount.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="inventory" property="costPriceAccount">
                  	<bean:write name="inventory" property="costPriceAccount.numberName" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.costPrice.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="costPrice" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.currency.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="inventory" property="currency">
                  	<bean:write name="inventory" property="currency.name" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.purchaseTax.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="inventory" property="purchaseTax">
                  	<bean:write name="inventory" property="purchaseTax.name" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.hasVendor.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">&nbsp;</td>
                </tr>
                <tr> 
                  <td colspan="3" align="center">
										<table border="0" cellspacing=1 cellpadding=0 bgcolor="#dddddd" width="100%">
											<tr align="center"> 
			                  <td width="5%" class=titleField>&nbsp;</td>
			                  <td class=titleField><bean:message key="page.vendor.title"/></td>
			                  <td class=titleField><bean:message key="page.costPrice.title"/></td>
			                  <td class=titleField><bean:message key="page.currency.title"/></td>
												<td class=titleField><bean:message key="page.purchaseTax.title"/></td>
			                </tr>
			                <% int k=0; %>
			                <logic:iterate id="itemVendor" name="inventory" property="itemVendors" type="com.mpe.financial.model.ItemVendor" scope="request">
			                <tr bgcolor="#FFFFFF" align=center> 
			                  <td><%= ++k%></td>
			                  <td>
			                  	<logic:present name="itemVendor" property="id.vendor">
			                  	<bean:write name="itemVendor" property="id.vendor.company" scope="page"/>
			                  	</logic:present>
			                  </td>
			                  <td><bean:write name="itemVendor" property="formatedCostPrice" scope="page"/></td>
			                  <td>
			                  	<logic:present name="itemVendor" property="currency">
			                  	<bean:write name="itemVendor" property="currency.name" scope="page"/>
			                  	</logic:present>
			                  </td>
												<td>
			                  	<logic:present name="itemVendor" property="purchaseTax">
			                  	<bean:write name="itemVendor" property="purchaseTax.name" scope="page"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                </logic:iterate>
										</table>
									</td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><logic:present name="inventory" property="createBy">
                  	<bean:write name="inventory" property="createBy.userName" scope="request"/>
                  	</logic:present></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="formatedCreateOn" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><logic:present name="inventory" property="changeBy">
                  	<bean:write name="inventory" property="changeBy.userName" scope="request"/>
                  	</logic:present></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="inventory" property="formatedChangeOn" scope="request"/></td>
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
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/item/form.do"/>?itemId=<bean:write name="inventory" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/item/delete.do"/>?itemId=<bean:write name="inventory" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/item/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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









