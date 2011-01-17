<%@ page contentType="text/html;charset=iso-8859-1" language="java" %>
<% response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>

<logic:messagesNotPresent>
<logic:notPresent scope="session" name="user">
	<logic:redirect page="/logon/form.do"/>
</logic:notPresent>

<logic:present scope="session" name="user">
	<logic:redirect page="/home.do"/>
</logic:present>
</logic:messagesNotPresent>

<logic:messagesPresent>
<html:errors/>
</logic:messagesPresent>
