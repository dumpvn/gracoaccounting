<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
<HEAD><TITLE><bean:message key="page.index.title"/></TITLE>
<link href="<html:rewrite page="/js/general.css"/>" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<TABLE class=TableBg height=600 cellSpacing=40 cellPadding=1 width="100%" border=0>
  <TR valign="top">
    <TD valign="top">
      <TABLE bgcolor="#ECE9D8" height=100 cellSpacing=0 cellPadding=1 width="100%" border="0">
		  		<tr>
			    <td> 
			
			<!-- CONTENT START -->
	      <table width="100%" border="0" cellspacing=20" cellpadding="0">
	       <tr><td><html:errors/></td></tr>
    			<tr><td>&nbsp;</td></tr>
    			
    			<body class="BgForm">
    			<html:form action="/posOrder/memberSave">
    			<html:hidden property="posOrderId"/>
					<html:hidden property="memberId"/>
					<html:hidden property="memberIsActive"/>									        			
          <tr>
          	<td>
          		<table width="100%">
					 
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.memberDate.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%" align="left"><html:text property="memberDate" size="12"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.memberNumber.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%" align="left"><html:text property="memberNumber" size="30" readonly="true"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.fullName.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%" align="left"><html:text property="memberFullName" size="30"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.nickName.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%" align="left"><html:text property="memberNickName" size="30"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.mobile.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%" align="left"><html:text property="memberMobile" size="30"/></td>
                </tr>
                
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.address.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%" align="left"><html:text property="memberAddress" size="30"/></td>
                </tr>
                
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.city.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%" align="left"><html:text property="memberCity" size="30"/></td>
                </tr>
                
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.province.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%" align="left"><html:text property="memberProvince" size="30"/></td>
                </tr>
                
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.postalCode.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%" align="left"><html:text property="memberPostalCode" size="30"/></td>
                </tr>
                
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.telephone.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%" align="left"><html:text property="memberTelephone" size="30"/></td>
                </tr>
                <tr> 
                  <td width="30%" align="right"><bean:message key="page.email.title"/></td>
                  <td width="5%" align="center">:</td>
                  <td width="65%" align="left"><html:text property="memberEmail" size="30"/></td>
                </tr>
                
                </table>
            </td>
          </tr>
             		 
    		 <tr>
    			 <td>&nbsp;</td>
    		 </tr>
    		 <tr>
    		  	 <td align="center">
					 <html:submit><bean:message key="page.submit.link"/></html:submit>
				 	 <html:cancel><bean:message key="page.cancel.link"/></html:cancel>
    			 </td>
    		 </tr>
    		 
    		
 			</TBODY>
    	</table>
    </td>
  </tr>
</TBODY>
</table>
</html:form>
</body>
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

var cal1 = new calendar2(document.forms['memberForm'].elements['memberDate']);
cal1.year_scroll = true;
cal1.time_comp = false;


var cal2 = new calendar2(document.forms['memberForm'].elements['dateOfBirth']);
cal2.year_scroll = true;
cal2.time_comp = false;


function checkForm(form) {
  if (validateOrganization(form.organization)) {
        return true
  }
  return false
}

function validateOrganization(field) {
  var input = field.value
  if (isEmpty(input)) {
    alert("Be sure to enter a organization name")
    select(field)
    return false
  } 
  return true
}

function isEmpty(inputStr) {
  if (inputStr == "" || inputStr == null) {
    return true
  }
  return false
}

</script>
