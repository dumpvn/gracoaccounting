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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.customerPrepaymentTitle.title"/></TD></TR>
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
    			<html:form action="/customerPrepayment/detail">
					<html:hidden property="customerPrepaymentId"/>								        			
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.location.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="customerPrepayment" property="location">
                  	<bean:write name="customerPrepayment" property="location.name" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customerPrepayment" property="number" scope="request"/></td>
                </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.prepaymentDate.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customerPrepayment" property="prepaymentDate" scope="request"/>&nbsp;(dd/MM/yyyy)</td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.bankAccount.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="customerPrepayment" property="bankAccount">
                  	<bean:write name="customerPrepayment" property="bankAccount.number" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.reference.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customerPrepayment" property="reference" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.amount.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customerPrepayment" property="formatedAmount" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.currency.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="customerPrepayment" property="currency">
                  	<bean:write name="customerPrepayment" property="currency.name" scope="request"/>
                  	</logic:present>
                  	&nbsp;<i>(<bean:write name="customerPrepayment" property="formatedExchangeRate" scope="request"/>)</i>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.customer.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="customerPrepayment" property="customer">
                  	<bean:write name="customerPrepayment" property="customer.company" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.customerAlias.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="customerPrepayment" property="customerAlias">
                  	<bean:write name="customerPrepayment" property="customerAlias.company" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.salesOrder.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="customerPrepayment" property="salesOrder">
                  	<bean:write name="customerPrepayment" property="salesOrder.number" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customerPrepayment" property="description" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.isPosted.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customerPrepayment" property="posted" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><logic:present name="customerPrepayment" property="createBy">
										<bean:write name="customerPrepayment" property="createBy.userName" scope="request"/>
										</logic:present></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customerPrepayment" property="formatedCreateOn" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><logic:present name="customerPrepayment" property="changeBy">
										<bean:write name="customerPrepayment" property="changeBy.userName" scope="request"/>
										</logic:present></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="customerPrepayment" property="formatedChangeOn" scope="request"/></td>
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
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/customerPrepayment/form.do"/>?customerPrepaymentId=<bean:write name="customerPrepayment" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/customerPrepayment/delete.do"/>?customerPrepaymentId=<bean:write name="customerPrepayment" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/customerPrepayment/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








