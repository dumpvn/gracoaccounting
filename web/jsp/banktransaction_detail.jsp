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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.bankTransactionTitle.title"/></TD></TR>
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
    			<html:form action="/bankTransaction/detail">
					<html:hidden property="bankTransactionId"/>								        			
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
              	<tr> 
                  <td width="30%" align="right"><bean:message key="page.number.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="bankTransaction" property="number" scope="request"/></td>
                </tr>
		           	<tr> 
                  <td width="30%" align="right"><bean:message key="page.transactionDate.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="bankTransaction" property="transactionDate" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.bankAccount.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="bankTransaction" property="fromBankAccount">
                  	<bean:write name="bankTransaction" property="fromBankAccount.number" scope="request"/>
                  	</logic:present>
                  	<logic:present name="bankTransaction" property="toBankAccount">
                  	<bean:write name="bankTransaction" property="toBankAccount.number" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.project.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="bankTransaction" property="project">
                  	<bean:write name="bankTransaction" property="project.name" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.vendor.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="bankTransaction" property="vendor">
                  	<bean:write name="bankTransaction" property="vendor.company" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.customer.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="bankTransaction" property="customer">
                  	<bean:write name="bankTransaction" property="customer.company" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.reference.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="bankTransaction" property="reference" scope="request"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.amount.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="bankTransaction" property="formatedAmount" scope="request"/>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.currency.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<logic:present name="bankTransaction" property="currency">
                  	<bean:write name="bankTransaction" property="currency.name" scope="request"/>
                  	</logic:present>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.note.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><bean:write name="bankTransaction" property="note" scope="request"/></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
          	<td>&nbsp;</td>
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
			                </tr>
<%int i=0;%>			                
											<logic:present name="bankTransaction" property="journal">
			                <logic:iterate id="journalDetail" name="bankTransaction" property="journal.journalDetails" type="com.mpe.financial.model.JournalDetail">
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
			                  	</logic:equal>
			                  	</logic:present>
			                  	</logic:greaterEqual>
			                  	<logic:lessThan name="journalDetail" property="amount" value="0">
			                  	<logic:present name="journalDetail" property="id.chartOfAccount">
			                  	<logic:notEqual name="journalDetail" property="id.chartOfAccount.debit" value="true">
			                  	<bean:write name="journalDetail" property="formatedAmount" scope="page"/>	
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
			                  	</logic:notEqual>
			                  	</logic:present>
			                  	</logic:greaterEqual>
			                  	<logic:lessThan name="journalDetail" property="amount" value="0">
			                  	<logic:present name="journalDetail" property="id.chartOfAccount">
													<logic:equal name="journalDetail" property="id.chartOfAccount.debit" value="true">
			                  	<bean:write name="journalDetail" property="formatedAmount" scope="page"/>
			                  	</logic:equal>
			                  	</logic:present>
			                  	</logic:lessThan>
												</td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                
			                
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
							<input class="Button" type="button" name="edit" onclick="window.location.href=('<html:rewrite page="/bankTransaction/form.do"/>?bankTransactionId=<bean:write name="bankTransaction" property="id"/>');" value="<bean:message key="page.edit.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="delete" onclick="window.location.href=('<html:rewrite page="/bankTransaction/delete.do"/>?bankTransactionId=<bean:write name="bankTransaction" property="id"/>');" value="<bean:message key="page.delete.link"/>"/>&nbsp;&nbsp;
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/bankTransaction/list.do"/>?start=<bean:write name="start" scope="session"/>&count=<bean:write name="count" scope="session"/>');" value="<bean:message key="page.back.link"/>"/>
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








