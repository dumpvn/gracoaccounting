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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.journalTitle.title"/></TD></TR>
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
    			<html:form action="/journal/detail">
					<html:hidden property="journalId"/>								        			
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>

		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.journalType.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="journal" property="journalType">
                  	<bean:write name="journal" property="journalType.name" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.vendor.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="journal" property="vendor">
                  	<bean:write name="journal" property="vendor.company" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.customer.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="journal" property="customer">
                  	<bean:write name="journal" property="customer.company" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.project.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="journal" property="project">
                  	<bean:write name="journal" property="project.name" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="journal" property="number" scope="request"/></td>
               </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.journalDate.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="journal" property="formatedJournalDate" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.currency.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="journal" property="currency">
                  	<bean:write name="journal" property="currency.name" scope="request"/>
                  	</logic:present>
                  	&nbsp;<i>(<bean:write name="journal" property="formatedExchangeRate" scope="request"/>)</i>
                  </td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.amount.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<bean:write name="journal" property="formatedAmount" scope="request"/>
                  </td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.reference.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="journal" property="reference" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.isPosted.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="journal" property="posted" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="journal" property="description" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><logic:present name="journal" property="createBy">
										<bean:write name="journal" property="createBy.userName" scope="request"/>
										</logic:present></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.createOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="journal" property="formatedCreateOn" scope="request"/></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeBy.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><logic:present name="journal" property="changeBy">
										<bean:write name="journal" property="changeBy.userName" scope="request"/>
										</logic:present></td>
                </tr>
		           <tr> 
                  <td width="30%" align="right"><bean:message key="page.changeOn.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="journal" property="formatedChangeOn" scope="request"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
    				<td>
    					<table width="100%">
    						<tr bgcolor="#FFFFFF"> 
			            <td align="center"> 
			              <table border="0" cellspacing=1 cellpadding=0 bgcolor="#cccccc" width="100%">
			                <tr align="center"> 
			                  <td width="5%" class=titleField>&nbsp;</td>
			                  <td class=titleField><bean:message key="page.chartOfAccount.title"/></td>
			                  <td class=titleField><bean:message key="page.debit.title"/></td>
			                  <td class=titleField><bean:message key="page.credit.title"/></td>
			                  <td class=titleField><bean:message key="page.department.title"/></td>
			                  <td class=titleField><bean:message key="page.description.title"/></td>
			                </tr>
			<%
			int i = 0;
			try {
			i = Integer.parseInt(request.getParameter("start"));
			}catch(Exception ex){
			i = 0;
			}
			%>
											<logic:present name="journal" property="journalDetails">
			                <logic:iterate id="journalDetail" name="journal" property="journalDetails" type="com.mpe.financial.model.JournalDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="journalDetail" property="id.chartOfAccount">
													<bean:write name="journalDetail" property="id.chartOfAccount.numberName" scope="page"/>
													</logic:present>
												</td>
												<td align="right">
													<!-- debit -->
													<logic:greaterEqual name="journalDetail" property="amount" value="0">
													<logic:present name="journalDetail" property="id.chartOfAccount">
													<logic:equal name="journalDetail" property="id.chartOfAccount.debit" value="true">
			                  	<bean:write name="journalDetail" property="formatedAmount" scope="page"/>
			                  		<logic:present name="journal" property="currency">
				                  	&nbsp;<bean:write name="journal" property="currency.name" scope="request"/>
				                  	</logic:present>
			                  	</logic:equal>
			                  	</logic:present>
			                  	</logic:greaterEqual>
			                  	<logic:lessThan name="journalDetail" property="amount" value="0">
			                  	<logic:present name="journalDetail" property="id.chartOfAccount">
			                  	<logic:notEqual name="journalDetail" property="id.chartOfAccount.debit" value="true">
			                  	<bean:write name="journalDetail" property="formatedAmount" scope="page"/>
			                  		<logic:present name="journal" property="currency">
				                  	&nbsp;<bean:write name="journal" property="currency.name" scope="request"/>
				                  	</logic:present>
			                  	</logic:notEqual>
			                  	</logic:present>
			                  	</logic:lessThan>
												</td>
												<td align="right">
													<!-- credit -->
													<logic:greaterEqual name="journalDetail" property="amount" value="0">
			                  	<logic:present name="journalDetail" property="id.chartOfAccount">
			                  	<logic:notEqual name="journalDetail" property="id.chartOfAccount.debit" value="true">
			                  	<bean:write name="journalDetail" property="formatedAmount" scope="page"/>
				                  	<logic:present name="journal" property="currency">
				                  	&nbsp;<bean:write name="journal" property="currency.name" scope="request"/>
				                  	</logic:present>
			                  	</logic:notEqual>
			                  	</logic:present>
			                  	</logic:greaterEqual>
			                  	<logic:lessThan name="journalDetail" property="amount" value="0">
			                  	<logic:present name="journalDetail" property="id.chartOfAccount">
													<logic:equal name="journalDetail" property="id.chartOfAccount.debit" value="true">
			                  	<bean:write name="journalDetail" property="formatedAmount" scope="page"/>
			                  		<logic:present name="journal" property="currency">
				                  	&nbsp;<bean:write name="journal" property="currency.name" scope="request"/>
				                  	</logic:present>
			                  	</logic:equal>
			                  	</logic:present>
			                  	</logic:lessThan>
												</td>
												<td>
													<logic:present name="journalDetail" property="department">
													<bean:write name="journalDetail" property="department.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="journalDetail" property="description" scope="page"/></td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td>&nbsp;</td>
			                	<td>&nbsp;</td>
			                	<bean:define id="c" name="creditAmount"/>
			            			<logic:notEqual name="debitAmount" value="<%=(String)c%>">
												<td align="right"><b><font color="red"><bean:write name="debitAmount" scope="request"/>
													<logic:present name="journal" property="currency">
			                  	&nbsp;<bean:write name="journal" property="currency.name" scope="request"/>
			                  	</logic:present>
												</font></b></td>
												<td align="right"><b><font color="red"><bean:write name="creditAmount" scope="request"/>
													<logic:present name="journal" property="currency">
			                  	&nbsp;<bean:write name="journal" property="currency.name" scope="request"/>
			                  	</logic:present>
												</font></b></td>
												</logic:notEqual>
												<logic:equal name="debitAmount" value="<%=(String)c%>">
												<td align="right"><b><bean:write name="debitAmount" scope="request"/>
													<logic:present name="journal" property="currency">
			                  	&nbsp;<bean:write name="journal" property="currency.name" scope="request"/>
			                  	</logic:present>
												</b></td>
												<td align="right"><b><bean:write name="creditAmount" scope="request"/>
													<logic:present name="journal" property="currency">
			                  	&nbsp;<bean:write name="journal" property="currency.name" scope="request"/>
			                  	</logic:present>
												</b></td>
												</logic:equal>
			                	<td>&nbsp;</td>
			                	<td>&nbsp;</td>
			                </tr>
			                			                
			              </table>
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
							<logic:match name="journal" property="posted" value="false">
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/journal/form.do"/>?journalId=<bean:write name="journal" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/journal/delete.do"/>?journalId=<bean:write name="journal" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							</logic:match>
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/journal/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








