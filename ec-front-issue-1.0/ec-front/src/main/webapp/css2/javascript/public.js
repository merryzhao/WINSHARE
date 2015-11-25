// JavaScript Document
//默认设置
if(typeof(scroll_num)=='undefined'){var scroll_num = 5;}
if(typeof(favorate_display)=='undefined'){var favorate_display = true; /*打开收藏*/}
if(typeof(recommend_display)=='undefined'){var recommend_display = true; /*打开收藏*/}

if(typeof(jQuery)!='undefined'){
(function($){			
	$(function(){
		/*精品推荐滚动*/		
		var scroll_width = $('.recom_goods_box').width();
		$(function(){
			$('#recom_goods_scroll_ul').w_picSwap({cbtn:['arrow_pre','arrow_next'],child:'li',delay:0,tnum:scroll_num,funeffect:w_picSwapeffect_jptj});
		});
		function w_picSwapeffect_jptj(o,n,w){
			w=w==0?-1:1;
			o!=undefined && o.stop().animate({left:-scroll_width*w},300);
			n.stop().show().css({left:scroll_width*w}).animate({left:0},500);
		}
		/**/
		/*详细页精品推荐*/
		var scroll_width_2 = $('.purchase_box').width();	
		$(function(){
			$('#recom_goods_scroll_ul_thing_item').w_picSwap({cbtn:['left_roll','right_roll'],child:'li',delay:0,tnum:scroll_num,funeffect:w_picSwapeffect_jptj_item});
		});
		function w_picSwapeffect_jptj_item(o,n,w){
			w=w==0?-1:1;
			o!=undefined && o.stop().animate({left:-scroll_width_2*w},300);
			n.stop().show().css({left:scroll_width_2*w}).animate({left:0},300);
		}
		/**bookshop页**/
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
			n.stop().show().css({left:scroll_width_3*w}).animate({left:0},300);
			window.setTimeout(aferall_hot,301);			
		}
		function aferall_hot(){
			$("#hot_book_scroll .picSwap").each(function(){
				if(parseInt($(this).css("left"))===0 && $(this).css("display")!="none"){					
					var this_num = $("#hot_book_scroll .picSwap").index(this)+1;
					$("#hot_page").text("第"+this_num+"页 (共"+new_li_num+"页)");	
				}				
			});			
		}
		/*特价书滚动*/
		var special_li_num =  Math.ceil($('#special_book_scroll li').size()/scroll_num);
		$("#specail_page").text("第1页 (共"+special_li_num+"页)");
		$(function(){
			$('#special_book_scroll').w_picSwap({cbtn:['special_left_roll','special_right_roll'],child:'li',delay:0,tnum:scroll_num,funeffect:w_picSwapeffect_special_book});
		});
		function w_picSwapeffect_special_book(o,n,w){
			w=w==0?-1:1;
			o!=undefined && o.stop().animate({left:-scroll_width_3*w},300);
			n.stop().show().css({left:scroll_width_3*w}).animate({left:0},300);
			window.setTimeout(aferall_specail,301);			
		}
		function aferall_specail(){
			$("#special_book_scroll .picSwap").each(function(){
				if(parseInt($(this).css("left"))===0 && $(this).css("display")!="none"){					
					var this_num = $("#special_book_scroll .picSwap").index(this)+1;
					$("#specail_page").text("第"+this_num+"页 (共"+special_li_num+"页)");	
				}				
			});			
		}		
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
			n.stop().show().css({left:scroll_width_4*w}).animate({left:0},300);	
			window.setTimeout(aferall_daily,301);		
		}			
		function aferall_daily(num){
			//max length : daily_maddness_li_num
			if(num==1){
				$("#page_daily").text("第1页 共"+daily_maddness_li_num+"页");
				$("#daily_pre2").show(); $("#daily_next").show();$("#daily_pre").hide();$("#daily_next2").hide();
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
		
		/*new arrive*/
		var scroll_width_5 = $('.item_box').width();
		var new_li_num = Math.ceil($('.item_box dl').size()/4);	
	
		$(function(){
			$('.item_box').w_picSwap({cbtn:['new_pre','new_next'],child:'dl',delay:0,tnum:4,funeffect:w_picSwapeffect_new_arrive,width:scroll_width_5});
		});
		function w_picSwapeffect_new_arrive(o,n,w){		
			w=w==0?-1:1;
			o!=undefined && o.stop().animate({left:-scroll_width_5*w},300);
			n.stop().show().css({left:scroll_width_5*w}).animate({left:0},300);	
			window.setTimeout(aferall_new,301);		
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
		
		/*booklist 滚动*/
		var scroll_width_6 = $('.best_box').width();	
		$(function(){
			$('.best_box').w_picSwap({cbtn:['left_roll','right_roll'],child:'dl',delay:0,tnum:3,funeffect:w_picSwapeffect_booklist});
		});
		function w_picSwapeffect_booklist(o,n,w){
			w=w==0?-1:1;
			o!=undefined && o.stop().animate({left:-scroll_width_6*w},300);
			n.stop().show().css({left:scroll_width_6*w}).animate({left:0},300);
		}	
		
		/*shop_ads*/
		//判断出广告的个数
		var ads_num = $(".shop_ads li").size();
		var ads_now_num =1;
		var ads_hover =0;
		var __tmp_ads;
		//显示几个广告相应的控制器
		$('.rotation').empty(); 
		for(var rot=0; rot<ads_num; rot++){
			if(rot===0){
				$('.rotation').append("<li class='current_ad'></li>"); 
				$('.shop_ads li').eq(rot).show();
			}else{
				$('.rotation').append("<li></li>"); 
				$('.shop_ads li').eq(rot).hide();
			}
		}
		//点击显示控制器的class 和 广告
		$(".rotation li").click(function(){
			  ads_now_num = $(".rotation li").index(this);
			  ads_hover=1;
			  rotation_interval();			  			 
		});
		__tmp_ads = setTimeout(rotation_interval,3000);
		$(".shopads_box").hover(function(){
			ads_hover=1;	
			clearTimeout(__tmp_ads);	 
		},function(){
			ads_hover=0;
			 __tmp_ads = setTimeout(rotation_interval,3000);
		});
		
		function rotation_interval(){	
			 clearTimeout(__tmp_ads);	
			 if(ads_now_num>=ads_num)	ads_now_num=0;	 
			 $(".rotation li").removeClass('current_ad');
			 $(".rotation li").eq(ads_now_num).addClass('current_ad');
			 $('.shop_ads li').fadeOut('fast');
			 $('.shop_ads li').eq(ads_now_num).fadeIn('slow');
			 ads_now_num++;
			 if(!ads_hover){
				 __tmp_ads = setTimeout(rotation_interval,3000);
			 }else{
				 clearTimeout(__tmp_ads);
			 }
		}
		
		/*收藏商品的折叠与显示*/
		$("#favorate").queue(function(){
				if(favorate_display){					
					$("#favorate .open_up").show();	$("#favorate .pack_up").hide();
					$("#favorate .order_coll").removeClass("unexpanded"); 
					$("#favorate").data('xs','show');
				}else{
					$("#favorate .open_up").hide();	$("#favorate .pack_up").show();
					$("#favorate .order_coll").addClass("unexpanded");
					$("#favorate").data('xs','hide');
				}
				$("#favorate .fr").click(function(){
						if($("#favorate").data('xs')=='show'){
							$("#favorate .open_up").hide();	$("#favorate .pack_up").show();
							$("#favorate .order_coll").addClass("unexpanded"); 
							$("#favorate").data('xs','hide');
						}else{
							$("#favorate .open_up").show();	$("#favorate .pack_up").hide();
							$("#favorate .order_coll").removeClass("unexpanded");
							$("#favorate").data('xs','show');
						}
				});	
		});	
		
		/*推荐商品的折叠与显示*/
		$("#recommend").queue(function(){			
				if(recommend_display){					
					$("#recommend .open_up").show();$("#recommend .pack_up").hide();$("#recommend .narrow").show();	$("#recommend .expansion").hide();					
					$("#recommend").removeClass("unexp_rec");				
					$("#recommend").data('xs','show');
				}else{
					$("#recommend .open_up").hide();$("#recommend .pack_up").show();$("#recommend .narrow").hide();	$("#recommend .expansion").show();$("#recommend").addClass("unexp_rec");
					$("#recommend").data('xs','hide');
				}
				$("#recommend .fr , #recommend .narrow ,#recommend .expansion").click(function(){
						if($("#recommend").data('xs')=='show'){
							$("#recommend .open_up").hide();$("#recommend .pack_up").show();$("#recommend .narrow").hide();	$("#recommend .expansion").show();
							$("#recommend").addClass("unexp_rec");
							$("#recommend").data('xs','hide');
						}else{
							$("#recommend .open_up").show();$("#recommend .pack_up").hide();$("#recommend .narrow").show();	$("#recommend .expansion").hide();	
							$("#recommend").removeClass("unexp_rec");
							$("#recommend").data('xs','show');
						}
				});	
		});		
				
	});	
})(jQuery);
}
