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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.bankReconcileTitle.title"/></TD></TR>
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
    			<html:form action="/bankReconcile/save">
					<html:hidden property="bankReconcileId"/>
					<html:hidden property="createBy"/>
					<html:hidden property="changeBy"/>
					<html:hidden property="createOn"/>
					<html:hidden property="changeOn"/>
					<html:hidden property="subaction"/>
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.reconcileDate.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:text property="reconcileDate" size="12"/>&nbsp;(dd/MM/yyyy)</td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.lastReconcileDate.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:text property="lastReconcileDate" size="12" readonly="true"/>&nbsp;(dd/MM/yyyy)</td>
                </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.bankAccount.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:select property="bankAccountId" onchange="this.form.subaction.value='refresh';this.form.submit();">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="bankAccountLst" property="id" labelProperty="numberName" />
                  	</html:select>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right">Difference Account</td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:select property="differenceAccountId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="chartOfAccountLst" property="id" labelProperty="numberName" />
                  	</html:select>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.beginningBalance.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:text property="beginningBalance" size="30"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.endingBalance.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:text property="endingBalance" size="30"/></td>
                </tr>
                
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.serviceCharge.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:text property="serviceCharge" size="15"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.serviceChargeAccount.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:select property="serviceChargeAccountId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="chartOfAccountLst" property="id" labelProperty="numberName" />
                  	</html:select>
                  </td>
                </tr>
                <tr> 
                  <td width="30%" align="right">Interest Earned</td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:text property="interestCharge" size="15"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.interestEarnedAccount.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%">
                  	<html:select property="interestEarnedAccountId">
											<html:option value=""><bean:message key="page.selectBellow.title"/></html:option>
											<html:options collection="chartOfAccountLst" property="id" labelProperty="numberName" />
                  	</html:select>
                  </td>
                </tr>
								<tr> 
                  <td width="30%" align="right"><bean:message key="page.description.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%"><html:textarea property="description" cols="40" rows="3"/></td>
                </tr>
                <tr> 
                  <td colspan="3">&nbsp;</td>
                </tr>
                
                <tr> 
                  <td width="30%" align="right"><b>BANK TRANSACTION</b></td>
                  <td width="5%"></td>
                  <td width="65%"></td>
                </tr>
								<tr>
                	<td colspan="3" align="center">
                		<table border="0" cellspacing=1 cellpadding=0 bgcolor="#dddddd" width="80%">
			                <tr align="center"> 
			                  <td width="5%" class=titleField>&nbsp;</td>
			                  <td class=titleField><bean:message key="page.transactionDate.title"/></td>
			                  <td class=titleField><bean:message key="page.description.title"/></td>
			                  <td class=titleField><bean:message key="page.reference.title"/></td>
			                  <td class=titleField><bean:message key="page.amount.title"/></td>
			                </tr>
			                <%int j = 0;%>
			                <logic:equal name="bankReconcileForm" property="bankReconcileId" value="0" scope="request">
                			<logic:present name="bankTransactionLst">
                			<logic:iterate id="bankTransaction" name="bankTransactionLst" type="com.mpe.financial.model.BankTransaction">
                			<tr bgcolor="#FFFFFF"> 
			                 	<td width="8%" align="center"><input type="checkbox" name="bankTransactionIdList[<%=j%>]" value="<bean:write name="bankTransaction" property="id" scope="page"/>" /></td>
			                	<td align="center">
				                		<bean:write name="bankTransaction" property="transactionDate" scope="page"/>
				                	</a>
			                	</td>       
			                  <td align="center"><bean:write name="bankTransaction" property="note" scope="page"/></td>
			                  <td align="center"><bean:write name="bankTransaction" property="reference" scope="page"/></td>
			                  <td align="center"><bean:write name="bankTransaction" property="formatedAmount" scope="page"/>&nbsp;
			                  	<logic:present name="bankTransaction" property="currency">
			                  	<bean:write name="bankTransaction" property="currency.name" scope="page"/>
			                  	</logic:present>
			                  </td>
			                </tr>
			                <%j++;%>
                			</logic:iterate>
                			</logic:present>
                			</logic:equal>
                			

                			
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
							<html:submit styleClass="Button" onclick="this.form.subaction.value=''"><bean:message key="page.submit.link"/></html:submit>
							<html:cancel styleClass="Button" onclick="this.form.subaction.value='';bCancel=true;"><bean:message key="page.cancel.link"/></html:cancel>
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