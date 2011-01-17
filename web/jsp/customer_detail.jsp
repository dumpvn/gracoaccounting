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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.customerTitle.title"/></TD></TR>
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
    			<html:form action="/customer/detail">
					<html:hidden property="customerId"/>								        			
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <tr> 
                <td width="30%" align="right"><bean:message key="page.customerCategory.title"/></td>
                <td width="5%" align="center">:</td>
                <td width="65%">
                	<logic:present name="customer" property="customersCategory">
            			<bean:write name="customer" property="customersCategory.name"  scope="request"/>
            			</logic:present>
            	  </td>
              </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.company.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="company" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.code.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="code" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.npwp.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="npwp" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.address.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="address" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.city.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="city" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.areaCode.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="areaCode" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.postalCode.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="postalCode" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.province.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="province" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.country.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="country" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.telephone.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="telephone" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.fax.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="fax" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.email.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="email" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.isActive.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="active" scope="request"/></td>
                </tr>
              <tr> 
                <td width="30%" align="right"><bean:message key="page.accountReceivable.title"/></td>
                <td width="5%" align="center">:</td>
                <td width="65%">
                	<logic:present name="customer" property="chartOfAccount">
            			<bean:write name="customer" property="chartOfAccount.numberName"  scope="request"/>
            			</logic:present>
            	  </td>
              </tr>
              <tr> 
                <td width="30%" align="right"><bean:message key="page.itemPriceCategory.title"/></td>
                <td width="5%" align="center">:</td>
                <td width="65%">
                	<logic:present name="customer" property="itemPriceCategory">
            			<bean:write name="customer" property="itemPriceCategory.name"  scope="request"/>
            			</logic:present>
            	  </td>
              </tr>
              <tr> 
                <td width="30%" align="right"><bean:message key="page.customerAlias.title"/></td>
                <td width="5%" align="center">:</td>
                <td width="65%">
                	<logic:present name="customer" property="customerAlias">
            			<bean:write name="customer" property="customerAlias.company"  scope="request"/>
            			</logic:present>
            	  </td>
              </tr>
              <tr> 
                <td width="30%" align="right"><bean:message key="page.isStore.title"/></td>
                <td width="5%" align="center">:</td>
                <td width="65%"><bean:write name="customer" property="store" scope="request"/></td>
              </tr>
              <tr> 
                <td width="30%" align="right"><bean:message key="page.creditLimit.title"/></td>
                <td width="5%" align="center">:</td>
                <td width="65%"><bean:write name="customer" property="formatedCreditLimit" scope="request"/></td>
              </tr>
							<tr> 
                <td width="30%" align="right"><bean:message key="page.isBlockOverCreditLimit.title"/></td>
                <td width="5%" align="center">:</td>
                <td width="65%"><bean:write name="customer" property="blockOverCreditLimit" scope="request"/></td>
              </tr>
              <tr> 
                <td width="30%" align="right"><bean:message key="page.description.title"/></td>
                <td width="5%" align="center">:</td>
                <td width="65%"><bean:write name="customer" property="description" scope="request"/></td>
              </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="customer" property="createBy">
                  	<bean:write name="customer" property="createBy.userName" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="formatedCreateOn" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="customer" property="changeBy">
                  	<bean:write name="customer" property="changeBy.userName" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customer" property="formatedChangeOn" scope="request"/></td>
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
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/customer/form.do"/>?customerId=<bean:write name="customer" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/customer/delete.do"/>?customerId=<bean:write name="customer" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/customer/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








