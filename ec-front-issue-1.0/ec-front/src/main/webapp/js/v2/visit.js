
seajs.use(["jQuery","visit"],function($,visit){
	 var el = $("#also_viewed"),
	     search = $("#search_visit_history"),
	     search_html="",
 		 html = "",
		 clearHtml = "<div class='nocontent'>无浏览历史</div>",
 		 productSaleId = !$(".productSaleId").val()?-1:$(".productSaleId").val(),
          view = {
              init: function(){
                  el.html("<div class='loading nocontent'><p>正在更新浏览历史，请稍候...</p></div>");
                  el.show();
                  visit.add(productSaleId, el);
                  $(visit).bind(visit.CLEAR_EVENT, function(e){
                      view.clear();
                  });
                  $(visit).bind(visit.LOAD_EVENT, function(e, data){
                      view.load(data);  
                  });
                  $(visit).bind(visit.CLEAR_EVENT, function(e){
                      view.clear();
                  });
                  $(visit).bind(visit.ERROR_EVENT,function(e){
                    view.clear();
                  });
                  $("a[bind='clean']").live("click",function(){         
                      visit.clear();
                  });   
              },
              load: function(data){
			  	if(data && data.visitedList && data.visitedList.length != 0){
	                  $.each(data.visitedList, function(i){
					  	if(i==0){
						    html +="<li><div class='list-accordion' style='display:none;'>"+ this.sellName +"</div><div class='cell cell-style-1'><div class='img'><a href="+this.url+" target='_blank'><img src="+this.imageUrl+" alt="+ this.sellName +"></a></div><div class='name'><a href="+this.url+" target='_blank'>"+this.sellName+"</a></div><div class='price-n'>文轩价：<b>￥"+ this.salePrice.toFixed(2) +"</b></div></div></li>";
					  	}else{
							 html = html + "<li><div class='list-accordion'><a href='" + this.url + "' target='_blank' >* " + this.sellName + "</a></div></li>"
						
					  	}
					  	if(data.visitedList.length > 0 && i < 6){
					  		//search_list浏览记录
					  		search_html += '<li>';
					  		search_html += '<div class="cell cell-m-book-top-p">';
					  		search_html += '<div class="img"><a href="'+this.url+'" target="_blank" title=""><img src="'+this.product.imageUrlFor200px+'" alt="'+this.sellName+'"></a></div>';
					  		search_html += '<div class="name"><a href="'+this.url+'" target="_blank" title="'+this.sellName+'">'+this.sellName+'</a></div>';
					  		search_html += '<div class="price"><span class="price-n">￥'+this.salePrice.toFixed(2)+'</span></div>';
					  		if(this.storageType.id=='6004'){
					  			search_html += '<div class="tag-ebook">电子书</div>';
					  		}
					  		search_html += '</div>';
					  		search_html += '</li>';
					  	}else if(search_html==''){
					  		search.parent('div').parent('div').hide();
					  	}});
				  }else{
					html = clearHtml;
					search.parent('div').parent('div').hide();
				  }
			  	  search.html(search_html);
				  el.html(html);
                  el.show();
              },
              clear: function(){
                  el.html(clearHtml);
              },
              subString:function(str){
                    return str.length>=17 ? str.substring(0,17)+"..." : str;
              },
              ebookIco:function(product){
                  var str = '<div class="ebook_mini_ico"></div>';
                 if(product.storageType.id==6004){
                     return str; 
                 }
                 return "";
              }
              
          };
    view.init();    
});