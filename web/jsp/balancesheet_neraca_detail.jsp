<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%
	int sequence = 0;
%>
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
  
  <tr valign="top"><td valign="top">
    
<!-- content -->    
      <TABLE height=50 cellSpacing=1 cellPadding=1 border=0 width="100%" valign="top"><BR><BR>
        <TR>
          <TD align=middle width="80%">
            <TABLE cellSpacing=0 cellPadding=0 width="92%" border=0>
              <TR>
                <TD width=9 height=25><html:img page="/images/left_title.gif"/></TD>
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.balanceSheetTitle.title"/></TD></TR>
            </TABLE>
          </TD>
        </TR>
        <TR>
          <TD align=middle width="80%" height=10>&nbsp;</TD></TR>
        <TR>
          <TD align=middle width="80%">
			
			<!-- CONTENT START -->
	      <table width="97%" border="0" cellspacing="2" cellpadding="0">
	      	<tr><td><html:errors/></td></tr>
					
    			<tr><td>&nbsp;</td></tr>
    			<html:form action="/balanceSheet/neracaDetail">
					<html:hidden property="start"/>
					<html:hidden property="count"/>
					<html:hidden property="orderBy"/>
					<html:hidden property="ascDesc"/>
    			<tr>
    				<td>
    					<table width="100%">
    						<tr>
    							<td><bean:message key="page.date.title"/></td><td>:</td><td><html:text property="fromDate" size="12"/>&nbsp;(dd/MM/yyyy)&nbsp;-&nbsp;<html:text property="toDate" size="12"/>&nbsp;(dd/MM/yyyy)&nbsp;
    								<html:submit styleClass="Button"><bean:message key="page.search.link"/></html:submit>
    							</td>
    						</tr>
    					</table>
    				</td>
    			</tr>
    			<tr>
    				<td>&nbsp;</td>
    			</tr>

			    						<tr bgcolor="#FFFFFF" valign="top"> 
						            <td align="center" width="100%" valign="top"> 
						              <table border="0" cellspacing=1 cellpadding=2 bgcolor="#cccccc" width="100%">
						                <tr align="center"> 
						                  <td width="5%" class=titleField>&nbsp;</td>
						                  <td class=titleField><bean:message key="page.chartGroup.title"/></td>
						                  <td class=titleField><bean:message key="page.amount.title"/></td>
						                </tr>
						                <logic:present name="chartGroup">
						                <tr bgcolor="FFFFFF">
															<td colspan="2"><b><bean:write name="chartGroup" property="name" scope="request"/></b></td>
															<td align="right"><b><bean:write name="chartGroup" property="formatedAmount" scope="request"/></b></td>
						                </tr>
						                </logic:present>
<%
int i = 0;
try {
i = Integer.parseInt(request.getParameter("start"));
}catch(Exception ex){
i = 0;
}
%>
														<logic:present name="PARENT">
						                <logic:iterate id="asset" name="PARENT" type="com.mpe.financial.model.ProfitLossReport">
														<% 
															sequence++;
															String bgcolor = "FFFFFF";
															if(sequence%2 == 0){
																bgcolor = "F5F9EE";
															}
														 %>
														<tr bgcolor="<% out.print(bgcolor); %>">
															<td colspan="2"><b><bean:write name="asset" property="name" scope="page"/></b></td>
															<td align="right"><b><bean:write name="asset" property="formatedAmount" scope="page"/></b></td>
						                </tr>
						                <%int k=0;%>
						                <logic:iterate id="assetParent" name="asset" property="list" type="com.mpe.financial.model.ProfitLossReport">
						                <% 
															sequence++;
															bgcolor = "FFFFFF";
															if(sequence%2 == 0){
																bgcolor = "F5F9EE";
															}
														 %>
						                <tr bgcolor="<% out.print(bgcolor); %>">
						                	<td align="right"><%=++k%>.</td>
															<td><bean:write name="assetParent" property="name" scope="page"/></td>
															<td align="right"><bean:write name="assetParent" property="formatedAmount" scope="page"/></td>
						                </tr>
						                </logic:iterate>
						                
						                </logic:iterate>
						                </logic:present>
						                
						                
						              </table>
						            </td>
						          </tr>


          <tr>
    				<td>
    					<table width="100%">
    						<tr><td>&nbsp;</td></tr>
    					</table>
    				</td>
    			</tr>
    			<tr>
    				<td align="center">
							<input class="Button" type="button" name="back" onclick="window.open('<html:rewrite page="/balanceSheet/neracaDetailPdf.do"/>?fromDate=<bean:write name="generalLedgerForm" property="fromDate"/>&toDate=<bean:write name="generalLedgerForm" property="toDate"/>&chartGroupId=<bean:write name="generalLedgerForm" property="chartGroupId"/>','gg','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width=800,height=600');" value="<bean:message key="page.print.link"/>"/>
							<input class="Button" type="button" name="back" onclick="window.location.href=('<html:rewrite page="/balanceSheet/neraca.do"/>?fromDate=<bean:write name="generalLedgerForm" property="fromDate"/>&toDate=<bean:write name="generalLedgerForm" property="toDate"/>');" value="<bean:message key="page.back.link"/>"/>
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

<script language="javascript">
function page(start,count) {
	document.forms[0].gotoPage.value = '';
	document.forms[0].start.value = start;
	document.forms[0].count.value = count;
	document.forms[0].submit();
};

function gotoPager() {
	if (document.forms[0].gotoPage.value >= 1) {
		if (document.forms[0].count.value == '' || document.forms[0].count.value == 0) document.forms[0].count.value = 10; 
		var a = (document.forms[0].gotoPage.value - 1) * document.forms[0].count.value;
		document.forms[0].start.value = a;
		document.forms[0].gotoPage2.value = document.forms[0].gotoPage.value;
		document.forms[0].submit();
	}
};

function gotoPager2() {
	if (document.forms[0].gotoPage2.value >= 1) {
		if (document.forms[0].count.value == '' || document.forms[0].count.value == 0) document.forms[0].count.value = 10; 
		var a = (document.forms[0].gotoPage2.value - 1) * document.forms[0].count.value;
		document.forms[0].start.value = a;
		document.forms[0].gotoPage.value = document.forms[0].gotoPage2.value;
		document.forms[0].submit();
	}
};

function sort(orderBy) {
	document.forms[0].orderBy.value = orderBy;
	if (document.forms[0].ascDesc.value=='') {
		document.forms[0].ascDesc.value='desc';
	} else if (document.forms[0].ascDesc.value=='desc') {
		document.forms[0].ascDesc.value='asc';
	} else {
		document.forms[0].ascDesc.value='desc';
	}
	document.forms[0].submit();
};

</script>



