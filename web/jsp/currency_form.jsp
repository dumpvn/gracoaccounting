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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.currencyTitle.title"/></TD></TR>
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
    			<html:form action="/currency/save">
					<html:hidden property="currencyId"/>
					<html:hidden property="createBy"/>
					<html:hidden property="changeBy"/>
					<html:hidden property="createOn"/>
					<html:hidden property="changeOn"/>
					<html:hidden property="subaction"/>
					<html:hidden property="currencyExchangeId"/>	
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <logic:equal name="currencyForm" property="currencyId" value="0">
                <tr> 
                  <td width="30%"><bean:message key="page.name.title"/></td>
                  <td width="5%">:</td>
                  <td width="65%"><html:text property="name" size="30"/></td>
                </tr>
                </logic:equal>
                <logic:greaterThan name="currencyForm" property="currencyId" value="0">
                <tr> 
                  <td width="30%"><bean:message key="page.name.title"/></td>
                  <td width="5%">:</td>
                  <td width="65%"><html:text property="name" readonly="true" size="30"/></td>
                </tr>
                </logic:greaterThan>
                <tr> 
                  <td width="30%"><bean:message key="page.symbol.title"/></td>
                  <td width="5%">:</td>
                  <td width="65%"><html:text property="symbol" size="4"/></td>
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
			                  <td class=titleField><bean:message key="page.toCurrency.title"/></td>
			                  <td class=titleField><bean:message key="page.exchangeRate.title"/></td>
			                  <td class=titleField><bean:message key="page.currencyExchangeRateType.title"/></td>
			                  <td class=titleField><bean:message key="page.chartOfAccount.title"/></td>
			                  <td class=titleField><bean:message key="page.isPosted.title"/></td>
			                  <td class=titleField><bean:message key="page.validFrom.title"/></td>
			                  <td class=titleField><bean:message key="page.validTo.title"/></td>
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
											<logic:present name="currencyExchangeFromLst">
			                <logic:iterate id="currencyExchange" name="currencyExchangeFromLst" type="com.mpe.financial.model.CurrencyExchange">
			                <tr bgcolor="#FFFFFF" align=center>
			                	<td><%=++i%>.</td>
												<td>
													<logic:present name="currencyExchange" property="id.toCurrency">
													<bean:write name="currencyExchange" property="id.toCurrency.name" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="currencyExchange" property="formatedExchangeRate" scope="page"/></td>
												<td>
													<logic:present name="currencyExchange" property="id.currencyExchangeRateType">
													<bean:write name="currencyExchange" property="id.currencyExchangeRateType.name" scope="page"/>
													</logic:present>
												</td>
												<td>
													<logic:present name="currencyExchange" property="chartOfAccount">
													<bean:write name="currencyExchange" property="chartOfAccount.numberName" scope="page"/>
													</logic:present>
												</td>
												<td><bean:write name="currencyExchange" property="posted" scope="page"/></td>
												<td><bean:write name="currencyExchange" property="id.formatedValidFrom" scope="page"/></td>
												<td><bean:write name="currencyExchange" property="id.formatedValidTo" scope="page"/></td>
												
			                  <td align="center">
													<a href="<html:rewrite page="/currency/form.do"/>?currencyId=<bean:write name="currencyForm" property="currencyId"/>&toCurrencyId=<bean:write name="currencyExchange" property="id.toCurrency.id"/>&currencyExchangeRateTypeId=<bean:write name="currencyExchange" property="id.currencyExchangeRateType.id"/>&validFrom=<bean:write name="currencyExchange" property="id.formatedValidFrom"/>&&validTo=<bean:write name="currencyExchange" property="id.formatedValidTo"/>"><html:img page="/images/edit.gif" border="0" alt="Edit"/></a>&nbsp;
			                  	<a href="<html:rewrite page="/currency/form.do"/>?currencyId=<bean:write name="currencyForm" property="currencyId"/>&toCurrencyId=<bean:write name="currencyExchange" property="id.toCurrency.id"/>&currencyExchangeRateTypeId=<bean:write name="currencyExchange" property="id.currencyExchangeRateType.id"/>&validFrom=<bean:write name="currencyExchange" property="id.formatedValidFrom"/>&&validTo=<bean:write name="currencyExchange" property="id.formatedValidTo"/>&subaction=REMOVECURRENCY&caller=/currency/form.do"><html:img page="/images/trash.gif" border="0" alt="Remove"/></a>
			                  </td>
			                </tr>
			                </logic:iterate>
			                </logic:present>
			                <tr>
			                	<td>&nbsp;</td>
			                	<td>
			                		<logic:present name="toCurrencyLst">
			                		<html:select property="toCurrencyId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="toCurrencyLst" property="id" labelProperty="name" />
			                  	</html:select>
			                  	</logic:present>
			                	</td>
			                	<td><html:text property="exchangeRate" size="10"/></td>
			                	<td>
			                		<logic:present name="currencyExchangeRateTypeLst">
			                		<html:select property="currencyExchangeRateTypeId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="currencyExchangeRateTypeLst" property="id" labelProperty="name" />
			                  	</html:select>
			                  	</logic:present>
			                	</td>
			                	<td>
			                		<logic:present name="chartOfAccountLst">
			                		<html:select property="chartOfAccountId">
														<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
														<html:options collection="chartOfAccountLst" property="id" labelProperty="numberName" />
			                  	</html:select>
			                  	</logic:present>
			                	</td>
			                	<td><html:checkbox property="isPosted" value="Y"/></td>
			                	<td><html:text property="validFrom" size="10"/></td>
			                	<td><html:text property="validTo" size="10"/></td>
			                	<td><html:submit styleClass="button" onclick="this.form.subaction.value='ADDCURRENCY'">ADD</html:submit></td>
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