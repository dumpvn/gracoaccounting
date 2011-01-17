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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.memberTitle.title"/></TD></TR>
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
    			<html:form action="/member/detail">
					<html:hidden property="memberId"/>								        			
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              	<tr> 
                  <td width="30%" align="right"><bean:message key="page.memberDiscount.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><logic:present name="member" property="memberDiscount"><bean:write name="member" property="memberDiscount.name" scope="request"/></logic:present></td>
                </tr>
		           	<tr> 
                  <td width="30%" align="right"><bean:message key="page.memberNumber.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="memberNumber" scope="request"/></td>
                </tr>
		           	<tr> 
                  <td width="30%" align="right"><bean:message key="page.memberDate.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="memberDate" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.fullName.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="fullName" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.nickName.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="nickName" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.address.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="address" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.city.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="city" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.postalCode.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="postalCode" scope="request"/></td>
                </tr>
                 <tr> 
                  <td width="30%" align="right"><bean:message key="page.province.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="province" scope="request"/></td>
                </tr>
                 <tr> 
                  <td width="30%" align="right"><bean:message key="page.telephone.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="telephone" scope="request"/></td>
                </tr>
                 <tr> 
                  <td width="30%" align="right"><bean:message key="page.fax.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="fax" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.email.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="email" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.mobile.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="mobile" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.isActive.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="active" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><logic:present name="member" property="createBy">
										<bean:write name="member" property="createBy.userName" scope="request"/>
										</logic:present></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="formatedCreateOn" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><logic:present name="member" property="changeBy">
										<bean:write name="member" property="changeBy.userName" scope="request"/>
										</logic:present></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="member" property="formatedChangeOn" scope="request"/></td>
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
    				<!--
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/member/form.do"/>?memberId=<bean:write name="member" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/member/delete.do"/>?memberId=<bean:write name="member" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							-->
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/member/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








