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
    			<tr valign="top"><td><html:errors/></td></tr>
    			<tr valign="top"><td>&nbsp;</td></tr>
    			<html:form action="/user/save">
					<html:hidden property="userId"/>
					<logic:equal name="userForm" property="userId" value="0">
					<html:hidden property="isActive"/>
					</logic:equal>
					<logic:greaterThan name="userForm" property="userId" value="0">
					<html:hidden property="userPass"/>
					</logic:greaterThan>
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <tr valign="top"> 
                  <td width="30%" class="DataForm"><bean:message key="page.organization.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm">
                  	<html:select property="organizationId">
											<html:options collection="organizationLst" property="id" labelProperty="name" />
                  	</html:select>
                  </td>
                </tr>
                <tr valign="top"> 
                  <td width="30%" class="DataForm"><bean:message key="page.role.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm">
                  	<html:select property="roleId" multiple="true" style="width:240px; height:120px;" styleClass="Data">
											<html:options collection="roleLst" property="id" labelProperty="roleName" />
                  	</html:select>
                  </td>
                </tr>
                <logic:equal name="userForm" property="userId" value="0">
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.userName.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"><html:text property="userName" size="40" styleClass="Data"/></td>
                </tr>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.userPass.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%"><html:password property="userPass" size="40" styleClass="Data"/></td>
                </tr>
                </logic:equal>
                <logic:greaterThan name="userForm" property="userId" value="0">
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.userName.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"><html:text property="userName" size="40" readonly="true" styleClass="Data"/></td>
                </tr>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.userPass.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%"><html:password property="confirmUserPass" size="40" styleClass="Data"/></td>
                </tr>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.isActive.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"><html:checkbox property="isActive" value="Y" styleClass="Data"/>&nbsp;<bean:message key="page.yes.title"/></td>
                </tr>
                </logic:greaterThan>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.userType.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm">
                  	<html:select property="userType" styleClass="Data">
											<html:option value="administrator"><bean:message key="page.administrator.title"/></html:option>
                  	</html:select>
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
							<html:submit styleClass="Button"/>
							<html:cancel styleClass="Button"/>
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


