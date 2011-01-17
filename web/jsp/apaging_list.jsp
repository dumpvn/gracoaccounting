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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.apAgingTitle.title"/></TD></TR>
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
					<logic:messagesNotPresent>
    			<tr><td>&nbsp;</td></tr>
    			<html:form action="/apAging/list">
					<html:hidden property="start"/>
					<html:hidden property="count"/>
					<html:hidden property="orderBy"/>
					<html:hidden property="ascDesc"/>
    			<tr>
    				<td>
    					<table width="100%">    						
    						<tr>
    							<td><bean:message key="page.vendor.title"/></td><td>:</td><td>
    								<html:select property="vendorId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="vendorsLst" property="id" labelProperty="company" />
                  	</html:select>
                  </td>
    						</tr>
    						<tr>
    							<td><bean:message key="page.currency.title"/></td><td>:</td><td>
    								<html:select property="currencyId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="currencyLst" property="id" labelProperty="name" />
                  	</html:select>
    							&nbsp;<html:submit styleClass="Button"><bean:message key="page.search.link"/></html:submit></td>
    						</tr>
    					</table>
    				</td>
    			</tr>
    			<tr>
    				<td>&nbsp;</td>
    			</tr>
          <tr bgcolor="#FFFFFF"> 
            <td align="center"> 
              <table border="0" cellspacing=1 cellpadding=2 bgcolor="#cccccc" width="100%">
                <tr align="center"> 
                  <td width="3%" class=titleField>&nbsp;</td>
                  <td class=titleField><a href=javascript:sort('Company')><bean:message key="page.company.title"/></a>&nbsp;<logic:match name="generalLedgerForm" property="orderBy" value="Company"><logic:match name="generalLedgerForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="generalLedgerForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField><bean:message key="page.aging0.title"/></td>
                  <td class=titleField><bean:message key="page.aging030.title"/></td>
                  <td class=titleField><bean:message key="page.aging3060.title"/></td>
                  <td class=titleField><bean:message key="page.aging6090.title"/></td>
                  <td class=titleField><bean:message key="page.aging90.title"/></td>
                </tr>
<%
int i = 0;
try {
i = Integer.parseInt(request.getParameter("start"));
}catch(Exception ex){
i = 0;
}
%>
								<logic:present name="APAGING">
                <logic:iterate id="apAging" name="APAGING" type="com.mpe.financial.model.ApAgingReport">
								<% 
									sequence++;
									String bgcolor = "FFFFFF";
									if(sequence%2 == 0){
										bgcolor = "F5F9EE";
									}
								 %>
								<tr bgcolor="<% out.print(bgcolor); %>">
                	<td align="right"><%=++i%>.</td>
                	<td><bean:write name="apAging" property="company" scope="page"/></td>
									<td align="right"><a href="<html:rewrite page="/apAging/detail.do"/>?aging=0&vendorId=<bean:write name="apAging" property="vendorId" scope="page"/>&currencyId=<bean:write name="generalLedgerForm" property="currencyId" scope="request"/>"><bean:write name="apAging" property="formatedAging0" scope="page"/></a></td>
									<td align="right"><a href="<html:rewrite page="/apAging/detail.do"/>?aging=030&vendorId=<bean:write name="apAging" property="vendorId" scope="page"/>&currencyId=<bean:write name="generalLedgerForm" property="currencyId" scope="request"/>"><bean:write name="apAging" property="formatedAging030" scope="page"/></a></td>
									<td align="right"><a href="<html:rewrite page="/apAging/detail.do"/>?aging=3060&vendorId=<bean:write name="apAging" property="vendorId" scope="page"/>&currencyId=<bean:write name="generalLedgerForm" property="currencyId" scope="request"/>"><bean:write name="apAging" property="formatedAging3060" scope="page"/></a></td>
									<td align="right"><a href="<html:rewrite page="/apAging/detail.do"/>?aging=6090&vendorId=<bean:write name="apAging" property="vendorId" scope="page"/>&currencyId=<bean:write name="generalLedgerForm" property="currencyId" scope="request"/>"><bean:write name="apAging" property="formatedAging6090" scope="page"/></a></td>
									<td align="right"><a href="<html:rewrite page="/apAging/detail.do"/>?aging=90&vendorId=<bean:write name="apAging" property="vendorId" scope="page"/>&currencyId=<bean:write name="generalLedgerForm" property="currencyId" scope="request"/>"><bean:write name="apAging" property="formatedAging90" scope="page"/></a></td>
                </tr>
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
    				<td>
    					<table width="100%">
    						<tr><td align="center">
    							<input class="Button" type="button" name="create" onclick="window.open('<html:rewrite page="/apAging/report.do"/>?currencyId=<bean:write name="generalLedgerForm" property="currencyId"/>&vendorId=<bean:write name="generalLedgerForm" property="vendorId"/>','gg','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width=800,height=600');" value="PDF">&nbsp;
    							<input class="Button" type="button" name="create" onclick="window.location.href=('<html:rewrite page="/apAging/posisiHutang.do"/>?currencyId=<bean:write name="generalLedgerForm" property="currencyId"/>');" value="Posisi Hutang">
    						</td></tr>
    					</table>
    				</td>
    			</tr>
          </html:form>
          </logic:messagesNotPresent>
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



