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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.viewTitle.title"/></TD></TR>
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
    			<html:form action="/view/save">
					<html:hidden property="viewId"/>									        			
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.name.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"><html:text property="name" size="40" styleClass="Data"/></td>
                </tr>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.parent.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm">
                  	<html:select property="parentId" styleClass="Data">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="viewLst" property="id" labelProperty="viewName" />
                  	</html:select>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.path.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"><html:text property="path" size="40" styleClass="Data"/></td>
                </tr>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.isView.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"><html:checkbox property="isView" value="Y" styleClass="Data"/>&nbsp;<bean:message key="page.yes.title"/></td>
                </tr>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.description.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"><html:textarea property="description" styleClass="Data"/></td>
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
            
<!-- end content-->            
      </TD>
    </TR>
  <!-- footer start -->
  <tiles:insert template='/common/footer.jsp'/>
  <!-- footer end -->
 </TABLE>
</BODY>
</html:html>