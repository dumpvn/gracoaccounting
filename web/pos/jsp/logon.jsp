<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html>
<head>
<title><bean:message key="page.index.title"/></title>
<link href="<html:rewrite page="/js/general.css"/>" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<BODY class=BgForm>
<TABLE class=TableBg height="100%" cellSpacing=40 cellPadding=1 width="100%" border=2>
  <TR>
    <TD align=middle height=242>
      <TABLE class=Cell1 height="100%" cellSpacing=1 cellPadding=1 width="100%"   border=0>
	<tr>
	<td align="center">
	<html:form action="/logon" focus="userName">
	<table width="50%"  border="0" cellspacing="1" cellpadding="1" class="Cell1">
	<html:errors/>
      <tr>
        <td width="33%"><b><bean:message key="page.userName.title"/></b> </td>
        <td width="8%"><b>:</b></td>
        <td width="59%">
          <html:text property="userName" size="20" styleClass="Form"/></td>
      </tr>
      <tr>
        <td width="33%"><b><bean:message key="page.userPass.title"/></b></td>
        <td width="8%"><b>:</b></td>
        <td width="59%"><html:password property="userPass" size="20" styleClass="Form"/></td>
      </tr>
      <logic:present name="locationLst">
      <tr> 
        <td width="33%"><b><bean:message key="page.location.title"/></b></td>
        <td width="8%"><b>:</b></td>
        <td width="59%">
        	<html:select property="locationId" styleClass="Form">
						<html:options collection="locationLst" property="id" labelProperty="name" />
        	</html:select>
        </td>
      </tr>
      </logic:present>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td colspan="3" align="center">
          <html:submit/>
        </td>
        </tr>
    </table>
    </html:form>
    </td>
  </tr></table></td>
  </tr>
</table>
</body>
</html:html>
