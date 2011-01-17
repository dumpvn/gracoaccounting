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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.budgetTitle.title"/></TD></TR>
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
    			<html:form action="/budget/save">
					<html:hidden property="budgetId"/>
					<html:hidden property="createBy"/>
					<html:hidden property="changeBy"/>
					<html:hidden property="createOn"/>
					<html:hidden property="changeOn"/>
					<html:hidden property="subaction"/>
					<html:hidden property="revisionDate"/>
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.year.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:text property="year" size="30"/></td>
                </tr>
                <logic:present name="departmentLst">
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.department.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:select property="departmentId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="departmentLst" property="id" labelProperty="name" />
                  	</html:select>
                  </td>
                </tr>
                </logic:present>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:textarea property="description" cols="40" rows="4"/></td>
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
			                  <td width="5%" class=titleField rowspan="3">&nbsp;</td>
			                  <td class=titleField rowspan="3"><bean:message key="page.chartOfAccount.title"/></td>
			                  <td class=titleField rowspan="3"><bean:message key="page.revision.title"/></td>
			                  <td class=titleField colspan="6"><bean:message key="page.month.title"/></td>
			                  <td width="10%" class=titleField rowspan="3">&nbsp;</td>
			                </tr>
			                <tr align="center">
			                	<td class=titleField><bean:message key="page.january.title"/></td>
			                	<td class=titleField><bean:message key="page.february.title"/></td>
			                	<td class=titleField><bean:message key="page.march.title"/></td>
			                	<td class=titleField><bean:message key="page.april.title"/></td>
			                	<td class=titleField><bean:message key="page.may.title"/></td>
			                	<td class=titleField><bean:message key="page.june.title"/></td>
			                </tr>
			                <tr align="center">
			                	<td class=titleField><bean:message key="page.july.title"/></td>
			                	<td class=titleField><bean:message key="page.august.title"/></td>
			                	<td class=titleField><bean:message key="page.september.title"/></td>
			                	<td class=titleField><bean:message key="page.october.title"/></td>
			                	<td class=titleField><bean:message key="page.november.title"/></td>
			                	<td class=titleField><bean:message key="page.december.title"/></td>
			                </tr>
			<%
			int i = 0;
			try {
			i = Integer.parseInt(request.getParameter("start"));
			}catch(Exception ex){
			i = 0;
			}
			%>
											<logic:present name="budgetDetailLst">
			                <logic:iterate id="budgetDetail" name="budgetDetailLst" type="com.mpe.financial.model.BudgetDetail">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td rowspan="2"><%=++i%>.</td>
												<td rowspan="2">
													<logic:present name="budgetDetail" property="id.chartOfAccount">
													<bean:write name="budgetDetail" property="id.chartOfAccount.numberName" scope="page"/>
													</logic:present>
												</td>
												<td rowspan="2"><bean:write name="budgetDetail" property="id.revisionNumber" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount1" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount2" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount3" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount4" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount5" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount6" scope="page"/></td>
			                  <td align="center" rowspan="2">
													<a href="<html:rewrite page="/budget/form.do"/>?budgetId=<bean:write name="budgetForm" property="budgetId"/>&chartOfAccountId=<bean:write name="budgetDetail" property="id.chartOfAccount.id"/>&revisionNumber=<bean:write name="budgetDetail" property="id.revisionNumber"/>"><html:img page="/images/edit.gif" border="0" alt="Edit"/></a>&nbsp;
			                  	<a href="<html:rewrite page="/budget/form.do"/>?budgetId=<bean:write name="budgetForm" property="budgetId"/>&chartOfAccountId=<bean:write name="budgetDetail" property="id.chartOfAccount.id"/>&revisionNumber=<bean:write name="budgetDetail" property="id.revisionNumber"/>&subaction=REMOVEBUDGETDETAIL&caller=/budget/form.do"><html:img page="/images/trash.gif" border="0" alt="Remove"/></a>
			                  </td>
			                </tr>
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><bean:write name="budgetDetail" property="formatedAmount7" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount8" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount9" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount10" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount11" scope="page"/></td>
												<td><bean:write name="budgetDetail" property="formatedAmount12" scope="page"/></td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                
			                <tr>
			                	<td rowspan="2">&nbsp;</td>
			                	<td rowspan="2">
			                		<logic:present name="chartOfAccountLst">
			                		<html:select property="chartOfAccountId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="chartOfAccountLst" property="id" labelProperty="numberName" />
			                  	</html:select>
			                  	</logic:present>
			                	</td>
			                	<td rowspan="2"><html:text property="revisionNumber" size="2"/></td>
			                	<td><html:text property="amount1" size="10"/></td>
			                	<td><html:text property="amount2" size="10"/></td>
			                	<td><html:text property="amount3" size="10"/></td>
			                	<td><html:text property="amount4" size="10"/></td>
			                	<td><html:text property="amount5" size="10"/></td>
			                	<td><html:text property="amount6" size="10"/></td>
			                	<td rowspan="2"><html:submit styleClass="button" onclick="this.form.subaction.value='ADDBUDGETDETAIL'">ADD</html:submit></td>
			                </tr>
			                <tr>
			                	<td><html:text property="amount7" size="10"/></td>
			                	<td><html:text property="amount8" size="10"/></td>
			                	<td><html:text property="amount9" size="10"/></td>
			                	<td><html:text property="amount10" size="10"/></td>
			                	<td><html:text property="amount11" size="10"/></td>
			                	<td><html:text property="amount12" size="10"/></td>
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
							<html:submit styleClass="button" onclick="this.form.subaction.value='SAVEALL'"><bean:message key="page.submit.link"/></html:submit>
							<html:cancel styleClass="button" onclick="bCancel=true;"><bean:message key="page.cancel.link"/></html:cancel>
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