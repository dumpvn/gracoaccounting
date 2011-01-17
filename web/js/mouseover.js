
	<!--
	function fwLoadMenus() {
	  if (window.fw_menu_0) return;
	  window.fw_menu_0 = new Menu("root",110,16,"Verdana, Arial, Helvetica, sans-serif",8,"#ffffff","#ffffff","#76B5E3","#4B87B3");
	  fw_menu_0.addMenuItem("Law & Regulation","location='lawregulation_dwi.jsp'");
	  fw_menu_0.addMenuItem("Glossary","location='glossary_dwi.jsp'");
	   fw_menu_0.fontWeight="bold";
	   fw_menu_0.textAlign="center";
	   fw_menu_0.hideOnMouseOut=true;
		   
	     	   
	  if (window.fw_menu_1) return;
	  window.fw_menu_1 = new Menu("root",165,10,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_1.addMenuItem("&nbsp;Oven","");
	  fw_menu_1.addMenuItem("<img src='/media/bt1r.gif' border=0>","window.top.location='product_oven.html'");
	   fw_menu_1.fontWeight="bold";
	   fw_menu_1.textAlign="center";
	   fw_menu_1.hideOnMouseOut=true;	   
	     	   
	  if (window.fw_menu_2) return;
	  window.fw_menu_2 = new Menu("root",165,32,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_2.addMenuItem("<img src='/media/bt2r.gif' border=0>","window.top.location='product_cooker.html'");
	  fw_menu_2.addMenuItem("Cooker","");
	   fw_menu_2.fontWeight="bold";
	   fw_menu_2.hideOnMouseOut=true;	   
	     	   
	  if (window.fw_menu_3) return;
	  window.fw_menu_3 = new Menu("root",165,10,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_3.addMenuItem("&nbsp;&nbsp;Hobs","");
	  fw_menu_3.addMenuItem("<img src='/media/bt3r.gif' border=0>","window.top.location='product_hobs.html'");
	   fw_menu_3.fontWeight="bold";
	   fw_menu_3.hideOnMouseOut=true;	   
	     	   
	  if (window.fw_menu_4) return;
	  window.fw_menu_4 = new Menu("root",165,32,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_4.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;<img src='/media/bt4r.gif' border=0>","window.top.location='product_cookerHood.html'");
	  fw_menu_4.addMenuItem("Cooker hood","");
	   fw_menu_4.fontWeight="bold";
	   fw_menu_4.hideOnMouseOut=true;	   
	     	   
	  if (window.fw_menu_5) return;
	  window.fw_menu_5 = new Menu("root",165,10,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_5.addMenuItem("Microwave","");
	  fw_menu_5.addMenuItem("&nbsp;&nbsp;&nbsp;<img src='/media/bt5r.gif' border=0>","window.top.location='product_microwave.html'");
	   fw_menu_5.fontWeight="bold";
	   fw_menu_5.hideOnMouseOut=true;	   
	     	   
	  if (window.fw_menu_6) return;
	  window.fw_menu_6 = new Menu("root",165,32,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_6.addMenuItem("&nbsp;&nbsp;&nbsp;<img src='/media/bt6r.gif' border=0>","window.top.location='product_dishwasher.html'");
	  fw_menu_6.addMenuItem("Dishwasher","");
	   fw_menu_6.fontWeight="bold";
	   fw_menu_6.hideOnMouseOut=true;	   	     	   
	   
	  if (window.fw_menu_7) return;
	  window.fw_menu_7 = new Menu("root",165,11,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_7.addMenuItem("Refrigerator","");
	  fw_menu_7.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;<img src='/media/bt7r.gif' border=0>","window.top.location='product_refrigerator.html'");
	   fw_menu_7.fontWeight="bold";
	   fw_menu_7.hideOnMouseOut=true;	   
 	   
	   
	  if (window.fw_menu_8) return;
	  window.fw_menu_8 = new Menu("root",165,32,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_8.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/media/bt8r.gif' border=0>","window.top.location='product_washingMachine.html'");
	  fw_menu_8.addMenuItem("Washing machine","");
	   fw_menu_8.fontWeight="bold";
	   fw_menu_8.hideOnMouseOut=true;	   
 	   
	   
	  if (window.fw_menu_9) return;
	  window.fw_menu_9 = new Menu("root",165,12,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_9.addMenuItem("&nbsp;Dryer","");
	  fw_menu_9.addMenuItem("<img src='/media/bt9r.gif' border=0>","window.top.location='product_dryer.html'");
	   fw_menu_9.fontWeight="bold";
	   fw_menu_9.hideOnMouseOut=true;	   
 	   
	   
	  if (window.fw_menu_10) return;
	  window.fw_menu_10 = new Menu("root",165,32,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_10.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/media/bt10r.gif' border=0>","window.top.location='product_vacumCleaner.html'");
	  fw_menu_10.addMenuItem("Vacuum cleaner","");
	   fw_menu_10.fontWeight="bold";
	   fw_menu_10.hideOnMouseOut=true;	   
 	   
	   
	  if (window.fw_menu_11) return;
	  window.fw_menu_11 = new Menu("root",165,12,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_11.addMenuItem("Small appliances","");
	  fw_menu_11.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/media/bt11r.gif' border=0>","window.top.location='product_smallAppliances.html'");
	   fw_menu_11.fontWeight="bold";
	   fw_menu_11.hideOnMouseOut=true;	   
 	   
	   
	  if (window.fw_menu_12) return;
	  window.fw_menu_12 = new Menu("root",165,32,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_12.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/media/bt12r.gif' border=0>","window.top.location='product_waterHeater.html'");
	  fw_menu_12.addMenuItem("Water heater","");
	   fw_menu_12.fontWeight="bold";
	   fw_menu_12.hideOnMouseOut=true;	 
	     
	  if (window.fw_menu_13) return;
	  window.fw_menu_13 = new Menu("root",165,32,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_13.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/media/line/about-us.gif' border=0>","");
	   fw_menu_13.fontWeight="bold";
	   fw_menu_13.hideOnMouseOut=true;	 
	     
	  if (window.fw_menu_14) return;
	  window.fw_menu_14 = new Menu("root",165,32,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_14.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/media/line/products.gif' border=0>","");
	   fw_menu_14.fontWeight="bold";
	   fw_menu_14.hideOnMouseOut=true;	 
	     
	  if (window.fw_menu_15) return;
	  window.fw_menu_15 = new Menu("root",165,32,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_15.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/media/line/locate-dealer.gif' border=0>","");
	   fw_menu_15.fontWeight="bold";
	   fw_menu_15.hideOnMouseOut=true;	 
	     
	  if (window.fw_menu_16) return;
	  window.fw_menu_16 = new Menu("root",165,32,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_16.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/media/line/after-sales.gif' border=0>","");
	   fw_menu_16.fontWeight="bold";
	   fw_menu_16.hideOnMouseOut=true;	 
	     
	  if (window.fw_menu_17) return;
	  window.fw_menu_17 = new Menu("root",165,32,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_17.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/media/line/press.gif' border=0>","");
	   fw_menu_17.fontWeight="bold";
	   fw_menu_17.hideOnMouseOut=true;	 
	     
	  if (window.fw_menu_18) return;
	  window.fw_menu_18 = new Menu("root",165,32,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_18.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/media/line/customer.gif' border=0>","");
	   fw_menu_18.fontWeight="bold";
	   fw_menu_18.hideOnMouseOut=true;	 
	     
	  if (window.fw_menu_19) return;
	  window.fw_menu_19 = new Menu("root",165,32,"Verdana, Arial, Helvetica, sans-serif",9,"#990033","#990033");
	  fw_menu_19.addMenuItem("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='/media/line/business.gif' border=0>","");
	   fw_menu_19.fontWeight="bold";
	   fw_menu_19.hideOnMouseOut=true;	 
	   
	   
	   
	   
	  fw_menu_0.writeMenus();
	} // fwLoadMenus()
	
	//-->
	
		/* Scroller */
	<!-- 
		function verifyCompatibleBrowser(){ 
	    this.ver=navigator.appVersion 
	    this.dom=document.getElementById?1:0 
	    this.ie5=(this.ver.indexOf("MSIE 5")>-1 && this.dom)?1:0; 
	    this.ie4=(document.all && !this.dom)?1:0; 
	    this.ns5=(this.dom && parseInt(this.ver) >= 5) ?1:0; 
	 
	    this.ns4=(document.layers && !this.dom)?1:0; 
	    this.bw=(this.ie5 || this.ie4 || this.ns4 || this.ns5) 
	    return this 
		} 
		bw=new verifyCompatibleBrowser() 
		 
		 
		var speed=50 
		 
		var loop, timer 
		 
		function ConstructObject(obj,nest){ 
	    nest=(!nest) ? '':'document.'+nest+'.' 
	    this.el=bw.dom?document.getElementById(obj):bw.ie4?document.all[obj]:bw.ns4?eval(nest+'document.'+obj):0; 
	    this.css=bw.dom?document.getElementById(obj).style:bw.ie4?document.all[obj].style:bw.ns4?eval(nest+'document.'+obj):0; 
	    this.scrollHeight=bw.ns4?this.css.document.height:this.el.offsetHeight 
	    this.clipHeight=bw.ns4?this.css.clip.height:this.el.offsetHeight 
	    this.up=MoveAreaUp;this.down=MoveAreaDown; 
	    this.MoveArea=MoveArea; this.x; this.y; 
	    this.obj = obj + "Object" 
	    eval(this.obj + "=this") 
	    return this 
		} 
		function MoveArea(x,y){ 
	    this.x=x;this.y=y 
	    this.css.left=this.x 
	    this.css.top=this.y 
		} 
		 
		function MoveAreaDown(move){ 
			if(this.y>-this.scrollHeight+objContainer.clipHeight){ 
		    this.MoveArea(0,this.y-move) 
		    if(loop) setTimeout(this.obj+".down("+move+")",speed) 
			} 
		} 
		function MoveAreaUp(move){ 
			if(this.y<0){ 
		    this.MoveArea(0,this.y-move) 
		    if(loop) setTimeout(this.obj+".up("+move+")",speed) 
			} 
		} 
		 
		function PerformScroll(speed){ 
			if(initialised){ 
				loop=true; 
				if(speed>0) objScroller.down(speed) 
				else objScroller.up(speed) 
			} 
		} 
		 
		function CeaseScroll(){ 
		    loop=false 
		    if(timer) clearTimeout(timer) 
		} 
		var initialised; 
		function InitialiseScrollableArea(){ 
	    objContainer=new ConstructObject('divContainer') 
	    objScroller=new ConstructObject('divContent','divContainer') 
	    objScroller.MoveArea(0,0) 
	    objContainer.css.visibility='visible' 
	    initialised=true; 
		} 
		// end absolutely positioned scrollable area object scripts 
	//--> 
	/* End Scroller */