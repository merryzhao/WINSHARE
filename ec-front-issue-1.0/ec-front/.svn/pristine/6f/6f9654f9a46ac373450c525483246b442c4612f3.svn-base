seajs.use(["jQuery"], function($){
   $.fn.w_picSwap=function(d){var d=$.extend({},$.fn.w_picSwap.defaults,d);return this.each(function(){var a=$(this);if(a.length==0){return false}var b=0,$tmpel,old=0,now=0,sib=Array();var c=a.children(d.child);$div=$('<div>').css({'position':'relative','height':a.css('height')});c.each(function(i){if(d.tnum>1){if(i==0||i%d.tnum==0){$tmpel=$('<div class="picSwap">');if(d.width){$tmpel.width(d.width);};sib.push($tmpel)}$tmpel.append($(this))}else{sib.push($(this))}});for(var i=0;i<sib.length;i++){i>0&&sib[i].css({'display':'none'});sib[i].css({'position':'absolute'});$div.append(sib[i])};$div.appendTo(a);if(d.cbtn){$.each(d.cbtn,function(i,n){$('#'+n).click(function(){end();d.way=i;showImg();begin()})})}function showImg(){if(d.way){old=now;now=(now==(sib.length-1))?0:now+1}else{old=now;now=(now==0)?sib.length-1:now-1}d.funeffect.call($div,sib[old],sib[now],d.way)};function begin(){if(d.delay>0){b=setInterval(showImg,d.delay)}};function end(){if(b){clearInterval(b)}};$div.hover(function(){end()},function(){begin()});begin()})};$.fn.w_picSwap.defaults={delay:2000,way:1,cbtn:0,tnum:1,funeffect:0,child:''};
 //默认设置
if(typeof(scroll_num)=='undefined'){var scroll_num = 5;}
if(typeof(favorate_display)=='undefined'){var favorate_display = true; /*打开收藏*/}
if(typeof(recommend_display)=='undefined'){var recommend_display = true; /*打开收藏*/}

 
 /*new arrive*/
		var scroll_width_5 = $('.item_box').width();
		var new_li_num = Math.ceil($('.item_box dl').size()/4);	
	
		$(function(){
			$('.item_box').w_picSwap({cbtn:['new_pre','new_next'],child:'dl',delay:0,tnum:4,funeffect:w_picSwapeffect_new_arrive,width:scroll_width_5});
		});
		function w_picSwapeffect_new_arrive(o,n,w){		
			w=w==0?-1:1;
			o!=undefined && o.stop().animate({left:-scroll_width_5*w},300);
			n.stop().show().css({left:scroll_width_5*w}).animate({left:0},300,function(){
				aferall_new();
				
			});	
			
		}			
		function aferall_new(num){
			//max length : daily_maddness_li_num
			if(num==1){
				$("#page_new").text("第1页 共"+new_li_num+"页");
				$("#new_pre2").show(); $("#new_next").show();$("#new_pre").hide();$("#new_next2").hide();
			}
			$(".item_box .picSwap").each(function(){
				if(parseInt($(this).css("left"))===0 && $(this).css("display")!="none"){					
					var this_num = $(".item_box .picSwap").index(this)+1;
					$("#page_new").text("第"+this_num+"页 共"+new_li_num+"页");
					if(this_num===1){							
						 $("#new_pre2").show();$("#new_next").show();$("#new_pre").hide();$("#new_next2").hide();
					}else if(new_li_num==this_num) {
						 $("#new_pre").show();$("#new_next2").show();$("#new_pre2").hide();$("#new_next").hide();
					}else{
						 $("#new_pre").show();$("#new_next").show();$("#new_pre2").hide();$("#new_next2").hide();
					}						
				}				
			});
		}
		aferall_new(1);
		
		/*每日秒杀滚动*/		
		var scroll_width_4 = $('.discount_goods').width();
		$('#daily_maddness li').width(180);	
		var daily_maddness_li_num = $('#daily_maddness li').size();		
		$(function(){
			$('#daily_maddness').w_picSwap({cbtn:['daily_pre','daily_next'],child:'li',delay:0,tnum:1,funeffect:w_picSwapeffect_daily_maddness});
		});
		function w_picSwapeffect_daily_maddness(o,n,w){		
			w=w==0?-1:1;
			o!=undefined && o.stop().animate({left:-scroll_width_4*w},300);
			n.stop().show().css({left:scroll_width_4*w}).animate({left:0},300,function(){
				
				aferall_daily();
			});	
			//window.setTimeout(aferall_daily,301);		
		}			
		function aferall_daily(num){
			//max length : daily_maddness_li_num
			if(num==1){
				if(daily_maddness_li_num>0){
					$("#page_daily").text("第1页 共"+daily_maddness_li_num+"页");
					$("#daily_pre2").show(); $("#daily_next").show();$("#daily_pre").hide();$("#daily_next2").hide();
			
				}else{
					$("#page_daily").text("第0页 共"+daily_maddness_li_num+"页");
					$("#daily_pre2").show(); $("#daily_next").hide();$("#daily_pre").hide();$("#daily_next2").show();
			
				}
				
			}
			$("#daily_maddness li").each(function(){		
				if(parseInt($(this).css("left"))===0 && $(this).css("display")!="none"){					
					var this_num = $("#daily_maddness li").index(this)+1;
					$("#page_daily").text("第"+this_num+"页 共"+daily_maddness_li_num+"页");
					if(this_num===1){							
						 $("#daily_pre2").show();$("#daily_next").show();$("#daily_pre").hide();$("#daily_next2").hide();
					}else if(daily_maddness_li_num==this_num) {
						 $("#daily_pre").show();$("#daily_next2").show();$("#daily_pre2").hide();$("#daily_next").hide();
					}else{
						 $("#daily_pre").show();$("#daily_next").show();$("#daily_pre2").hide();$("#daily_next2").hide();
					}						
				}				
			});
		}
		aferall_daily(1);
		
		
		/*热门套装书滚动*/
		var scroll_width_3 = $('.recom_goods_box').width();	
		var hot_li_num =  Math.ceil($('#hot_book_scroll li').size()/scroll_num);
		$("#hot_page").text("第1页 (共"+hot_li_num+"页)");
		$(function(){
			$('#hot_book_scroll').w_picSwap({cbtn:['hot_left_roll','hot_right_roll'],child:'li',delay:0,tnum:scroll_num,funeffect:w_picSwapeffect_hot_book});
		});
		function w_picSwapeffect_hot_book(o,n,w){
			w=w==0?-1:1;
			o!=undefined && o.stop().animate({left:-scroll_width_3*w},300);
			n.stop().show().css({left:scroll_width_3*w}).animate({left:0},300,function(){
				
				aferall_hot();
				
			});
			//window.setTimeout(aferall_hot,301);			
		};
		function aferall_hot(){
			$("#hot_book_scroll .picSwap").each(function(){
				if(parseInt($(this).css("left"))===0 && $(this).css("display")!="none"){					
					var this_num = $("#hot_book_scroll .picSwap").index(this)+1;
					$("#hot_page").text("第"+this_num+"页 (共"+new_li_num+"页)");	
				}				
			});			
		};
		
		/*特价书滚动*/
		var special_li_num =  Math.ceil($('#special_book_scroll li').size()/scroll_num);
		$("#specail_page").text("第1页 (共"+special_li_num+"页)");
		$(function(){
			$('#special_book_scroll').w_picSwap({cbtn:['special_left_roll','special_right_roll'],child:'li',delay:0,tnum:scroll_num,funeffect:w_picSwapeffect_special_book});
		});
		function w_picSwapeffect_special_book(o,n,w){
			w=w==0?-1:1;
			o!=undefined && o.stop().animate({left:-scroll_width_3*w},300);
			n.stop().show().css({left:scroll_width_3*w}).animate({left:0},300,function(){
				aferall_specail();				
			});
			//window.setTimeout(aferall_specail,301);			
		};
		function aferall_specail(){
			$("#special_book_scroll .picSwap").each(function(){
				if(parseInt($(this).css("left"))===0 && $(this).css("display")!="none"){					
					var this_num = $("#special_book_scroll .picSwap").index(this)+1;
					$("#specail_page").text("第"+this_num+"页 (共"+special_li_num+"页)");	
				}				
			});			
		};
		
		/*booklist 滚动*/
		var scroll_width_6 = $('.best_box').width();	
		$(function(){
			$('.best_box').w_picSwap({cbtn:['left_roll','right_roll'],child:'dl',delay:0,tnum:3,funeffect:w_picSwapeffect_booklist});
		});
		function w_picSwapeffect_booklist(o,n,w){
			w=w==0?-1:1;
			o!=undefined && o.stop().animate({left:-scroll_width_6*w},300);
			n.stop().show().css({left:scroll_width_6*w}).animate({left:0},300);
			images.trigger("scroll");
			images.trigger("imgload");
		};
		
		/*详细页精品推荐*/
		var scroll_width_2 = $('.purchase_box').width();	
		$(function(){
			$('#recom_goods_scroll_ul_thing_item').w_picSwap({cbtn:['left_roll','right_roll'],child:'li',delay:0,tnum:scroll_num,funeffect:w_picSwapeffect_jptj_item});
		});
		function w_picSwapeffect_jptj_item(o,n,w){
			w=w==0?-1:1;
			o!=undefined && o.stop().animate({left:-scroll_width_2*w},300);
			n.stop().show().css({left:scroll_width_2*w}).animate({left:0},300);
		};
   
})
