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
                <TD class=title width="100%" background=<html:rewrite page="/images/bg_title.gif"/> height=25><bean:message key="page.roleTitle.title"/></TD></TR>
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
    			<html:form action="/role/save">
					<html:hidden property="roleId"/>									        			
          <tr> 
            <td align="center"> 
              <table width="100%" border="0" cellspacing="1" cellpadding="0" class=abuabu>
                <logic:equal name="roleForm" property="roleId" value="0">
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.name.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"><html:text property="name" size="40" styleClass="Data"/></td>
                </tr>
                </logic:equal>
                <logic:greaterThan name="roleForm" property="roleId" value="0">
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.name.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"><html:text property="name" readonly="true" size="40" styleClass="Data"/></td>
                </tr>
                </logic:greaterThan>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.description.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"><html:textarea property="description" cols="40" rows="3" styleClass="Data"/></td>
                </tr>
                <tr> 
                  <td width="30%" class="DataForm"><bean:message key="page.roleSelected.title"/></td>
                  <td width="5%" class="DataForm">:</td>
                  <td width="65%" class="DataForm"></td>
                </tr>
                <tr>
                	<td colspan="3" width="90%">
                		<table width="90%">
                			<tr>
                				<logic:present name="viewLst">
                				<td width="40%" class="DataForm">
                					<html:select property="viewLstId" size="20" multiple="true" style="width:250px;" styleClass="Data">
														<html:options collection="viewLst" property="id" labelProperty="parentViewName" />
													</html:select>
                				</td>
                				</logic:present>
                				<td width="10%" class="DataForm">
                					<table width="100%">
									          <tr>
									      			<td class="DataForm">
									      				<A HREF="javascript:copyToList('viewLstId','viewSltdLstId')"><html:img page="/images/right.gif" border="0"/></A>
									      			</td>
									      		</tr>
									      		<tr>
									      			<td class="DataForm"><A HREF="javascript:copyToList('viewSltdLstId','viewLstId')"><html:img page="/images/left.gif" border="0"/></A></td>
									      		</tr>
									    		</table>
                				</td>
                				<logic:present name="viewSltdLst">
                				<td width="40%" class="DataForm">
                					<html:select property="viewSltdLstId" size="20" multiple="true" style="width:250px;" styleClass="Data">
														<html:options collection="viewSltdLst" property="id" labelProperty="parentViewName" />
													</html:select>
                				</td>
                				</logic:present>
                				<td width="10%">
                					<table width="100%">
									          <tr>
									      			<td class="DataForm"><a href="javascript:up('viewSltdLstId')"><html:img page="/images/up.gif" border="0"/></a></td>
									      		</tr>
									      		<tr>
									      			<td class="DataForm"><a href="javascript:down('viewSltdLstId')"><html:img page="/images/down.gif" border="0"/></a></td>
									      		</tr>
									    		</table>	
                				</td>
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
							<html:submit styleClass="Button" onclick="javascript:selectAll('viewSltdLstId');"><bean:message key="page.submit.link"/></html:submit>
							<html:cancel styleClass="Button"><bean:message key="page.cancel.link"/></html:cancel>
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