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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.userTitle.title"/></TD></TR>
            </TABLE>
          </TD>
        </TR>
        <TR>
          <TD align=middle width="80%" height=10>&nbsp;</TD></TR>
        <TR>
          <TD align=middle width="80%">
			
			<!-- CONTENT START -->
	      <table width="97%" border="0" cellspacing="2" cellpadding="0">
    			<tr valign="top"><td>&nbsp;</td></tr>
    			<tr valign="top"><td><html:errors/></td></tr>
    			<html:form action="/user/detail">
					<html:hidden property="userId"/>									        			
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.organization.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm">
                  	<logic:present name="user" property="organization" scope="request">
                  	<bean:write name="user" property="organization.name" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.userName.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"><bean:write name="user" property="userName" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.userPass.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm">**********</td>
                </tr>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.isActive.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"><bean:write name="user" property="active" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.userType.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"><bean:write name="user" property="userType" scope="request"/></td>
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
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/user/form.do"/>?userId=<bean:write name="user" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/user/delete.do"/>?userId=<bean:write name="user" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/user/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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


