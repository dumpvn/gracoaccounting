 var acePopupWindow=null;
function acePopup(mypage,myname,w,h,pos,infocus){
if(pos=='random'){
LeftPosition=(screen.width)?Math.floor(Math.random()*(screen.width-w)):100;
TopPosition=(screen.height)?Math.floor(Math.random()*((screen.height-h)-75)):100;}
if(pos=='center'){
LeftPosition=(screen.width)?(screen.width-w)/2:100;
TopPosition=(screen.height)?(screen.height-h)/2:100;}
else if((pos!='center' && pos!='random') || pos==null){LeftPosition=0;TopPosition=0;}settings='width='+ w + ',height='+ h + ',top=' + TopPosition + ',left=' + LeftPosition + ',location=no,directories=no,menubar=no,toolbar=no,status=no,scrollbars=no,resizable=no,dependent=no';
acePopupWindow=window.open('',myname,settings);
if(infocus=='front'){acePopupWindow.focus();acePopupWindow.location='help.html';}
}

 var indonesiaWindow=null;
function indonesia(mypage,myname,w,h,pos,infocus){
if(pos=='random'){
LeftPosition=(screen.width)?Math.floor(Math.random()*(screen.width-w)):100;
TopPosition=(screen.height)?Math.floor(Math.random()*((screen.height-h)-75)):100;}
if(pos=='center'){
LeftPosition=(screen.width)?(screen.width-w)/2:100;
TopPosition=(screen.height)?(screen.height-h)/2:100;}
else if((pos!='center' && pos!='random') || pos==null){LeftPosition=0;TopPosition=0;}settings='width='+ w + ',height='+ h + ',top=' + TopPosition + ',left=' + LeftPosition + ',location=no,directories=no,menubar=no,toolbar=no,status=no,scrollbars=no,resizable=no,dependent=no';
indonesiaWindow=window.open('',myname,settings);
if(infocus=='front'){indonesiaWindow.focus();indonesiaWindow.location='indonesian.html';}
}

 var sitemapWindow=null;
function sitemap(mypage,myname,w,h,pos,infocus){
if(pos=='random'){
LeftPosition=(screen.width)?Math.floor(Math.random()*(screen.width-w)):100;
TopPosition=(screen.height)?Math.floor(Math.random()*((screen.height-h)-75)):100;}
if(pos=='center'){
LeftPosition=(screen.width)?(screen.width-w)/2:100;
TopPosition=(screen.height)?(screen.height-h)/2:100;}
else if((pos!='center' && pos!='random') || pos==null){LeftPosition=0;TopPosition=0;}settings='width='+ w + ',height='+ h + ',top=' + TopPosition + ',left=' + LeftPosition + ',location=no,directories=no,menubar=no,toolbar=no,status=no,scrollbars=no,resizable=no,dependent=no';
sitemapWindow=window.open('',myname,settings);
if(infocus=='front'){sitemapWindow.focus();sitemapWindow.location='sitemap.html';}
}



function test(){
	alert("dodol");	
}