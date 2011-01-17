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
					<logic:messagesNotPresent>
    			<tr valign="top"><td>&nbsp;</td></tr>
    			<html:form action="/journal/list">
					<html:hidden property="start"/>
					<html:hidden property="count"/>
					<html:hidden property="orderBy"/>
					<html:hidden property="ascDesc"/>
					<html:hidden property="subaction"/>
    			<tr>
    				<td>
    					<table width="100%">
    						<tr>
    							<td><bean:message key="page.journalType.title"/></td><td>:</td><td>
    								<html:select property="journalTypeId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="journalTypeLst" property="id" labelProperty="name" />
                  	</html:select>
    							</td>
    						</tr>
    						<tr>
    							<td><bean:message key="page.project.title"/></td><td>:</td><td>
    								<html:select property="projectId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="projectLst" property="id" labelProperty="name" />
                  	</html:select>
    							</td>
    						</tr>
    						<tr>
    							<td><bean:message key="page.department.title"/></td><td>:</td><td>
    								<html:select property="departmentId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="departmentLst" property="id" labelProperty="name" />
                  	</html:select>
    							</td>
    						</tr>
    						<tr>
    							<td><bean:message key="page.isPosted.title"/></td><td>:</td><td>
    								<html:radio property="isPosted" value="Y"/>&nbsp;<bean:message key="page.yes.title"/>&nbsp;
    								<html:radio property="isPosted" value="N"/>&nbsp;<bean:message key="page.no.title"/>&nbsp;
    								<html:radio property="isPosted" value="A"/>&nbsp;<bean:message key="page.all.title"/>&nbsp;
    							</td>
    						</tr>
    						<tr>
    							<td><bean:message key="page.number.title"/></td><td>:</td><td><html:text property="number" size="20"/>&nbsp;</td>
    						</tr>
    						<tr>
    							<td><bean:message key="page.journalDate.title"/></td><td>:</td><td><html:text property="journalDate" size="12"/>&nbsp;<html:submit styleClass="Button"><bean:message key="page.search.link"/></html:submit></td>
    						</tr>
    					</table>
    				</td>
    			</tr>
    			<tr>
    				<td>
    					<table width="100%">
    						<tr>
    							<td align="left"><font face="Verdana,Arial,Helvetica,sans-serif" size="1"><bean:write name="PAGER" scope="request" filter="false"/></font></td>
    							<td align="center"><font face="Verdana,Arial,Helvetica,sans-serif" size="1">Goto page : <html:text property="gotoPage" size="4" styleClass="Data" onchange="gotoPager();"/>&nbsp;</font></td>
    							<td align="right"><font face="Verdana,Arial,Helvetica,sans-serif" size="1"><bean:write name="PAGERITEM" scope="request" filter="false"/></font></td>
    						</tr>
    					</table>
    				</td>
    			</tr>
          <tr bgcolor="#FFFFFF"> 
            <td align="center"> 
              <table border="0" cellspacing=1 cellpadding=2 bgcolor="#cccccc" width="100%">
                <tr align="center"> 
                  <td width="5%" class=titleField>&nbsp;</td>
                  <td width="5%" class=titleField align="center"><input type=checkbox name="x" value="" onclick="selectAll();"></td>
                  <td class=titleField><a href=javascript:sort('Number')><bean:message key="page.number.title"/></a>&nbsp;<logic:match name="journalForm" property="orderBy" value="Number"><logic:match name="journalForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="journalForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField><a href=javascript:sort('JournalDate')><bean:message key="page.journalDate.title"/></a>&nbsp;<logic:match name="journalForm" property="orderBy" value="JournalDate"><logic:match name="journalForm" property="ascDesc" value="desc"><html:img page="/images/sort_terang.gif" border="0"/></logic:match><logic:match name="journalForm" property="ascDesc" value="asc"><html:img page="/images/sort2_terang.gif" border="0"/></logic:match></logic:match></td>
                  <td class=titleField><bean:message key="page.amount.title"/></td>
                  <td width="10%" class=titleField>&nbsp;</td>
                </tr>
<%
int i = 0, j=0;;
try {
i = Integer.parseInt(request.getParameter("start"));
}catch(Exception ex){
i = 0;
}
%>
                <logic:iterate id="journal" name="JOURNAL" type="com.mpe.financial.model.Journal">
								<% 
									sequence++;
									String bgcolor = "FFFFFF";
									if(sequence%2 == 0){
										bgcolor = "F5F9EE";
									}
								 %>
								<tr bgcolor="<% out.print(bgcolor); %>">
                	<td align="right"><%=++i%>.</td>
                	<td>
                		<logic:match name="journal" property="posted" value="false">
										<input type="hidden" name="journalIdLst[<%=j%>]" value="<bean:write name="journal" property="id"/>"/><input type="checkbox" name="isPostedLst[<%=j%>]" value="Y"/>
										</logic:match>
										<logic:match name="journal" property="posted" value="true">
										<input type="hidden" name="journalIdLst[<%=j%>]" value="<bean:write name="journal" property="id"/>"/><input type="checkbox" name="journalIdLst[<%=j%>]" value="Y" checked disabled/>
										</logic:match>
                	</td>
									<td><bean:write name="journal" property="number" scope="page"/></td>
									<td><bean:write name="journal" property="formatedJournalDate" scope="page"/></td>
									<td>
										<bean:write name="journal" property="formatedAmount" scope="page"/>
										<logic:present name="journal" property="currency">
                  	<bean:write name="journal" property="currency.name" scope="page"/>
                  	</logic:present>
									</td>
                  <td align="center">
										<html:link page="/journal/detail.do" paramId="journalId" paramName="journal" paramProperty="id"><html:img page="/images/detail.gif" border="0" alt="Detail"/></html:link>&nbsp;
										<logic:match name="journal" property="posted" value="false">
										<html:link page="/journal/form.do" paramId="journalId" paramName="journal" paramProperty="id"><html:img page="/images/edit.gif" border="0" alt="Edit"/></html:link>&nbsp;
                  	</logic:match>
                  	<a href="#" onclick="window.open('<html:rewrite page="/journal/pdf.do"/>?journalId=<bean:write name="journal" property="id"/>','gg','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width=800,height=600');"><html:img page="/images/print.gif" border="0" alt="Print"/></a>
                  </td>
                </tr>
                <%j++;%>
                </logic:iterate>
              </table>
            </td>
          </tr>
          <tr>
    				<td>
    					<table width="100%">
    						<tr>
    							<td align="left"><font face="Verdana,Arial,Helvetica,sans-serif" size="1"><bean:write name="PAGER" scope="request" filter="false"/></font></td>
    							<td align="center"><font face="Verdana,Arial,Helvetica,sans-serif" size="1">Goto page : <html:text property="gotoPage2" size="4" styleClass="Data" onchange="gotoPager2();"/>&nbsp;</font></td>
    							<td align="right"><font face="Verdana,Arial,Helvetica,sans-serif" size="1"><bean:write name="PAGERITEM" scope="request" filter="false"/></font></td>
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
    					<table width="50%">
    						<tr>
    							<td align="center">
    								<input class="Button" type="button" name="create" onclick="window.location.href=('<html:rewrite page="/journal/form.do"/>');" value="<bean:message key="page.new.link"/>">&nbsp;
    								<input class="Button" type="submit" name="posting" onclick="this.form.subaction.value='POSTINGFORM';this.form.submit();" value="<bean:message key="page.posting.link"/>">
    								<input class="Button" type="button" name="create" onclick="window.open('<html:rewrite page="/journal/reportPdf.do"/>?fromJournalDate=<bean:write name="journalForm" property="fromJournalDate"/>&toJournalDate=<bean:write name="journalForm" property="toJournalDate"/>','gg','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width=800,height=600');" value="PDF">
    							</td>
    						</tr>
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


<bean:size id="m" name="JOURNAL"/>

<script language="javascript">

var checkflag = "false";
var tot = <%=((Integer)m).intValue()%>;
function selectAll() {
	if (checkflag == "false") {
		<%for (int x=0; x<(((Integer)m).intValue()); x++) {%>
		for(var i=0; i < document.forms[0].length; i++) {
			if (document.forms[0].elements[i].name =="isPostedLst[<%=x%>]") {
				document.forms[0].elements[i].checked = true;
			}
		}
		<%}%>
		checkflag = "true";
	} else {
		<%for (int x=0; x<(((Integer)m).intValue()); x++) {%>	
		for(var i=0; i < document.forms[0].length; i++) {
			if (document.forms[0].elements[i].name =="isPostedLst[<%=x%>]"){
				document.forms[0].elements[i].checked = false;
			}
		}
		<%}%>
		checkflag = "false";
	}
}

</script>	
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



