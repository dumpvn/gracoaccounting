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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.paidCreditCardTitle.title"/></TD></TR>
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
    			<html:form action="/paidCreditCard/detail">
					<html:hidden property="paidCreditCardId"/>								        			
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.code.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="paidCreditCard" property="code" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.name.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="paidCreditCard" property="name" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.address.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="paidCreditCard" property="address" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.city.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="paidCreditCard" property="city" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.postalCode.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="paidCreditCard" property="postalCode" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.province.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="paidCreditCard" property="province" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.telephone.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="paidCreditCard" property="telephone" scope="request"/></td>
                </tr>
                 <tr> 
                  <td width="30%" align="right"><bean:message key="page.mobile.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="paidCreditCard" property="mobile" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.isActive.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="paidCreditCard" property="active" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="paidCreditCard" property="createBy">
                  	<bean:write name="paidCreditCard" property="createBy.userName" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="paidCreditCard" property="formatedCreateOn" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="paidCreditCard" property="changeBy">
                  	<bean:write name="paidCreditCard" property="changeBy.userName" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="paidCreditCard" property="formatedChangeOn" scope="request"/></td>
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
							<input class="Button" type="button" name="edit" onclick="window.paidCreditCard.href=('<html:rewrite page="/paidCreditCard/form.do"/>?paidCreditCardId=<bean:write name="paidCreditCard" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.paidCreditCard.href=('<html:rewrite page="/paidCreditCard/delete.do"/>?paidCreditCardId=<bean:write name="paidCreditCard" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.paidCreditCard.href=('<html:rewrite page="/paidCreditCard/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








