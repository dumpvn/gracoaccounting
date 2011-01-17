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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.firstBalanceTitle.title"/></TD></TR>
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
    			<html:form action="/firstBalance/save">
					<html:hidden property="generalLedgerId"/>
					<html:hidden property="createBy"/>
					<html:hidden property="changeBy"/>
					<html:hidden property="createOn"/>
					<html:hidden property="changeOn"/>
					<html:hidden property="orderBy"/>
					<html:hidden property="ascDesc"/>
          <tr bgcolor="#FFFFFF"> 
            <td align="center"> 
              <table border="0" cellspacing=1 cellpadding=2 bgcolor="#cccccc" width="100%">
                <tr align="center"> 
                  <td width="5%" class=titleField>&nbsp;</td>
                  <td class=titleField><bean:message key="page.chartOfAccount.title"/></td>
                  <td class=titleField><bean:message key="page.date.title"/></td>
                  <td class=titleField><bean:message key="page.isDebit.title"/></td>
                  <td class=titleField><bean:message key="page.debit.title"/></td>
                  <td class=titleField><bean:message key="page.credit.title"/></td>
                  <td width="10%" class=titleField>&nbsp;</td>
                </tr>
<%
int i = 0;
try {
i = Integer.parseInt(request.getParameter("start"));
}catch(Exception ex){
i = 0;
}
%>
								<logic:present name="firstBalanceLst">
                <logic:iterate id="firstBalance" name="firstBalanceLst" type="com.mpe.financial.model.GeneralLedger">
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
										<logic:present name="firstBalance" property="chartOfAccount">
                  	<bean:write name="firstBalance" property="chartOfAccount.numberName" scope="page"/>
                  	</logic:present>
									</td>
									<td><bean:write name="firstBalance" property="ledgerDate" scope="page"/></td>
									<td><bean:write name="firstBalance" property="debit" scope="page"/></td>
									<td align="right">
										<!-- debit -->
										<logic:greaterThan name="firstBalance" property="amount" value="0">
										<logic:equal name="firstBalance" property="debit" value="true">
                  	<bean:write name="firstBalance" property="formatedAmount" scope="page"/>
                  	</logic:equal>
                  	</logic:greaterThan>
                  	<logic:lessThan name="firstBalance" property="amount" value="0">
                  	<logic:notEqual name="firstBalance" property="debit" value="true">
                  	<bean:write name="firstBalance" property="formatedAmount" scope="page"/>
                  	</logic:notEqual>
                  	</logic:lessThan>
									</td>
									<td align="right">
										<!-- credit -->
										<logic:greaterThan name="firstBalance" property="amount" value="0">
                  	<logic:notEqual name="firstBalance" property="debit" value="true">
                  	<bean:write name="firstBalance" property="formatedAmount" scope="page"/>
                  	</logic:notEqual>
                  	</logic:greaterThan>
                  	<logic:lessThan name="firstBalance" property="amount" value="0">
										<logic:equal name="firstBalance" property="debit" value="true">
                  	<bean:write name="firstBalance" property="formatedAmount" scope="page"/>
                  	</logic:equal>
                  	</logic:lessThan>
									</td>
                  <td align="center">
										<html:link page="/firstBalance/form.do" paramId="generalLedgerId" paramName="firstBalance" paramProperty="id"><html:img page="/images/edit.gif" border="0" alt="Edit"/></html:link>&nbsp;
										<html:link page="/firstBalance/delete.do" paramId="generalLedgerId" paramName="firstBalance" paramProperty="id"><html:img page="/images/trash.gif" border="0" alt="Delete"/></html:link>
                  </td>
                </tr>
                </logic:iterate>
                </logic:present>
                <tr bgcolor="F5F9EE">
                	<td colspan="4">&nbsp;</td>
									<bean:define id="c" name="creditAmountTotal"/>
            			<logic:notEqual name="debitAmountTotal" value="<%=(String)c%>">
									<td align="right"><b><font color="red"><bean:write name="debitAmountTotal" scope="request"/></font></b></td>
									<td align="right"><b><font color="red"><bean:write name="creditAmountTotal" scope="request"/></font></b></td>
									</logic:notEqual>
									<logic:equal name="debitAmountTotal" value="<%=(String)c%>">
									<td align="right"><b><bean:write name="debitAmountTotal" scope="request"/></b></td>
									<td align="right"><b><bean:write name="creditAmountTotal" scope="request"/></b></td>
									</logic:equal>
									<td></td>
								</tr>
              </table>
            </td>
          </tr>
          <tr>
          	<td>&nbsp;</td>
          </tr>
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <tr> 
                  <td colspan="3">You can add new first balance each of chart of account bellow :</td>
                </tr>
                <logic:present name="chartOfAccountLst">
                <tr> 
                  <td width="30%"><bean:message key="page.chartOfAccount.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:select property="chartOfAccountId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="chartOfAccountLst" property="id" labelProperty="numberName" />
                  	</html:select>
                  </td>
                </tr>
                </logic:present>
                <tr> 
                  <td width="30%"><bean:message key="page.amount.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:text property="amount" size="20"/>&nbsp;
                  	<html:submit styleClass="Button"><bean:message key="page.submit.link"/></html:submit>
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

function selectAll(col1){
	col1 = eval('document.forms[0].'+col1);
  for(i=0; i<col1.options.length; i++ ){
		col1.options[i].selected = true;
	}
}

function up(col1){
	col1 = eval('document.forms[0].'+col1);
  index = col1.selectedIndex;
  //alert(index);
  if (index == -1) {
  	alert ('You haven\'t selected any options!');
  } else {
	  if(index <= 0) {
	  	alert("Can't move to up!");
	  } else {
		  toMoveX = col1.options[index-1];
		  toMoveY = col1.options[index];
		  optX = new Option(toMoveX.text,toMoveX.value,false,false);
		  optY = new Option(toMoveY.text,toMoveY.value,false,false);
		  col1.options[index] = optX;
		  col1.options[index-1] = optY;
		  col1.selectedIndex = index-1;
		}
	}
}

function down(col1){
	col1 = eval('document.forms[0].'+col1);
  index = col1.selectedIndex;
  if (index == -1) {
  	alert ('You haven\'t selected any options!');
  } else {
	  if(index+1 >=  col1.options.length) {
	  	alert("Can't move to down!");
	 	} else {
		  toMoveX = col1.options[index];
		  toMoveY = col1.options[index+1];
		  optX = new Option(toMoveX.text,toMoveX.value,false,false);
		  optY = new Option(toMoveY.text,toMoveY.value,false,false);
		  col1.options[index] = optY;
		  col1.options[index+1] = optX;
		  col1.selectedIndex = index+1;
		}
	}
}

function copyToList(from,to) {
  fromList = eval('document.forms[0].' + from);
  toList = eval('document.forms[0].' + to);
  if (toList.options.length > 0 && toList.options[0].value == 'temp') {
    toList.options.length = 0;
  }
  var sel = false;
  for (i=0;i<fromList.options.length;i++) {
    var current = fromList.options[i];
    if (current.selected) {
      sel = true;
      if (current.value == 'temp') {
        alert ('You cannot move this text!');
        return;
      }
      txt = current.text;
      val = current.value;
      toList.options[toList.length] = new Option(txt,val);
      fromList.options[i] = null;
      i--;
    }
  }
  if (!sel) alert ('You haven\'t selected any options!');
}

function page_submit() {
	document.forms[0].submit();
};
</script>