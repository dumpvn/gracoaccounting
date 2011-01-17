<%@ page contentType="text/html;charset=iso-8859-1" language="java" %>
<% response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>

	<TR valign=top>
    <TD width=150 rowSpan=2 bgColor=#A9ACAF ><html:img height="40" page="/images/logo_2.gif" width="145"/></TD>
    <TD width=90% height=40>
		<table width="100%" border="0"cellspacing="0"cellpadding="0" height="40">
			<tr>
				<td bgcolor="#999999" width="520">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="2" color="white">Welcome, <logic:present name="user"><b><bean:write name="user" property="userName"/>.</b></logic:present><logic:notPresent name="user"><b>guest.</b></logic:notPresent></font>&nbsp;&nbsp;&nbsp;</td>
				<td bgcolor="#000000" width="20%">&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
  
  <logic:present scope="session" name="user">
  <tiles:insert template='/common/menu.jsp'/>
  </logic:present>
  
	