seajs.use(["jQuery","config"],function($,config){
    $('.ebook_menu dd').mousemove(function(){
	$(this).find('.ebook_menu_content').show();
	$(this).addClass('over');
	});
	$('.ebook_menu dd').mouseleave(function(){
	$(this).find('.ebook_menu_content').hide();
	$(this).removeClass('over');
	});
//autotab
	var o=0;
	var timeInterval=15000;
	var $cont=$(".tab_content .tab_item");
	var $title=$(".tab_btn .tab");
		
	$cont.hide();
	$($cont[0]).show();
	function auto(){
		o<$cont.length-1?o++:o=0;
		$cont.eq(o).fadeIn("slow").siblings().hide(); 
		$title.eq(o).addClass("current").siblings().removeClass("current");   
	}
	set = window.setInterval(auto,timeInterval);
	$title.mouseover(function(){
			window.clearInterval(set);
			o=$(this).index();
			$cont.eq(o).fadeIn("slow").siblings().hide(); 
			$title.eq(o).addClass("current").siblings().removeClass("current"); 
			set = window.setInterval(auto,timeInterval);  
		});   
var ids ="";

	var itemTemplate={
			randomProduct: '<li><dl class="fix"><dt><a href="{bookUrl}"><img src="{imgUrl}" alt=""/></a></dt>'+
			'<dd class="book_title"><a href="{bookUrl}" title="{bookName}">{bookName}</a></dd>'+
			'<dd class="book_author">{bookAuthor}</dd><dd class="book_price">￥{bookPrice}</dd>'+
			'<dd class="book_intro">{bookRecommendInfo}<a href="{bookUrl}" target="_blank">详情&gt;&gt;</a></dd></dl></li>'
	}

$(".link").click(function(){
	  $.ajax({
			type: "GET",
			dataType: "json",
			url: "/fragment/20100?format=json&random=1&t="+new Date().getTime(),
			success: function(data){				
				$(".ebook_book_list2 li").remove();
				var contentList = data.contentList;
				for(i in contentList){
					var newChildHtml = itemTemplate.randomProduct;
					newChildHtml = newChildHtml.replace(/\{bookUrl\}/g, contentList[i].url);
					newChildHtml = newChildHtml.replace(/\{imgUrl\}/g, contentList[i].imageUrl);
					newChildHtml = newChildHtml.replace(/\{bookName\}/g, contentList[i].name);
					newChildHtml = newChildHtml.replace(/\{bookPrice\}/g, contentList[i].effectivePrice.toFixed(2));
					
					var author = (contentList[i].author == null) ? "" : contentList[i].author;
					newChildHtml = newChildHtml.replace(/\{bookAuthor\}/g, author);
					
					var info = "";
					if(contentList[i].productEditorRecommendInfo != null && contentList[i].productEditorRecommendInfo.content != null){
						info = contentList[i].productEditorRecommendInfo.content;
					}
					if(info.length > 26){
						info = info.substring(0,26) + "...";
					}
					newChildHtml = newChildHtml.replace(/\{bookRecommendInfo\}/g, info);
					$(".ebook_book_list2").append(newChildHtml);
				}
			}
	  });
});
});