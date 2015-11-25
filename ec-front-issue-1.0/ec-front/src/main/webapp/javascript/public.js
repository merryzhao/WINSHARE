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
