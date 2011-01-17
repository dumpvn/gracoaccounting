<%@ page contentType="text/html;charset=iso-8859-1" language="java" %>
<% response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>	
	
	<TR height="20">
    <TD width=145 bgColor=#FFFFFF height=20>&nbsp;</TD>
    <TD class=brief bgColor=#A9ACAF vAlign=bottom width=465 height=20 align="middle"><logic:present name="user"><logic:present name="user" property="organization"><bean:write name="user" property="organization.name"/> @ 2006-2011</logic:present></logic:present></TD>
 </TR>
 
 