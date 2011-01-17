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
  
  <tr valign="top"><td>
    
<!-- content -->    
      <TABLE height=50 cellSpacing=1 cellPadding=1 border=0 width="100%" valign="top"><BR><BR>
        <TR>
          <TD align=middle width="80%">
            <TABLE cellSpacing=0 cellPadding=0 width="92%" border=0>
              <TR>
                <TD width=9 height=25><html:img page="/images/left_title.gif"/></TD>
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25>STOCK TABLE</TD></TR>
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
    			<html:form action="/itemReport/penjualanGlobal">
					<html:hidden property="start"/>
					<html:hidden property="count"/>
					<html:hidden property="orderBy"/>
					<html:hidden property="ascDesc"/>
					<html:hidden property="caller"/>
					<html:hidden property="subaction"/>
    			<tr>
    				<td>
    					<table width="100%">
    						<tr> 
                  <td><bean:message key="page.location.title"/></td><td>:</td>
                  <td>
                  	<html:select property="locationId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="locationLst" property="id" labelProperty="name" />
                  	</html:select>
                  </td>
                </tr>
                <tr> 
                  <td><bean:message key="page.type.title"/></td><td>:</td>
                  <td>
                  	<html:select property="itemTypeId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="itemTypeLst" property="id" labelProperty="name" />
                  	</html:select>
                  </td>
                </tr>
    						<tr>
    							<td><bean:message key="page.date.title"/></td><td>:</td><td>
    								<html:text property="fromDate" size="12"/>&nbsp;-&nbsp;<html:text property="toDate" size="12"/>&nbsp;<html:submit styleClass="button"><bean:message key="page.search.link"/></html:submit></td>
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
                  <td width="5%" class=titleField>&nbsp;</td>
                  <td class=titleField><bean:message key="page.merk.title"/></td>
                  <td class=titleField><bean:message key="page.item.title"/></td>
                  <td class=titleField><bean:message key="page.price.title"/></td>
                  <td class=titleField>Stock Awal</td>
                  <td class=titleField>Masuk</td>
                  <td class=titleField>Tukar Masuk</td>
                  <td class=titleField>Retur</td>
                  <td class=titleField>Tukar Keluar</td>
                  <td class=titleField>Jual</td>
                  <td class=titleField>Stock Akhir</td>
                </tr>
<%
int i = 0;
try {
i = Integer.parseInt(request.getParameter("start"));
}catch(Exception ex){
i = 0;
}
%>
                <logic:iterate id="penjualanGlobal" name="penjualanGlobalLst" type="com.mpe.financial.model.ItemPosReportPenjualanGlobal">
								<% 
									sequence++;
									String bgcolor = "FFFFFF";
									if(sequence%2 == 0){
										bgcolor = "F5F9EE";
									}
								 %>
								<tr bgcolor="<% out.print(bgcolor); %>">
									<td align="right"><%=++i%>.</td>
									<td><bean:write name="penjualanGlobal" property="merk" scope="page"/></td>
									<td><bean:write name="penjualanGlobal" property="name" scope="page"/></td>
									<td align="right"><bean:write name="penjualanGlobal" property="formatedPrice" scope="page"/></td>
									<td align="right"><bean:write name="penjualanGlobal" property="stockAwal" scope="page"/></td>
									<td align="right"><bean:write name="penjualanGlobal" property="masuk" scope="page"/></td>
									<td align="right"><bean:write name="penjualanGlobal" property="tukarMasuk" scope="page"/></td>
									<td align="right"><bean:write name="penjualanGlobal" property="retur" scope="page"/></td>
									<td align="right"><bean:write name="penjualanGlobal" property="tukarKeluar" scope="page"/></td>
									<td align="right"><bean:write name="penjualanGlobal" property="jual" scope="page"/></td>
									<td align="right"></td>
                </tr>
                </logic:iterate>
              </table>
            </td>
          </tr>
          <tr>
    				<td>&nbsp;</td>
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
    					<table width="10%">
    						<tr>
    							<td align="center">
    								<input class="Button" type="button" name="create" onclick="window.open('<html:rewrite page="/itemReport/penjualanGlobalPdf.do"/>?itemTypeId=<bean:write name="itemForm" property="itemTypeId"/>&locationId=<bean:write name="itemForm" property="locationId"/>&fromDate=<bean:write name="itemForm" property="fromDate"/>&toDate=<bean:write name="itemForm" property="toDate"/>','gg','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=yes,width=800,height=600');" value="<bean:message key="page.print.link"/>">
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



