
seajs.use(["jQuery","visit"],function($,visit){
	 var el = $("#also_viewed"),
	 	
 		 html = "",
		 clearHtml = "<div class='nocontent'>无浏览历史</div>",
 		 productSaleId = $(".productSaleId").val(),
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
							html += '<dt><a class="historyList" target="_blank" href="'+this.url+'" title="'+this.sellName+'"><img alt="'+ this.sellName +'" src="'+ this.imageUrl +'"></a><a target="_blank" title="'+ this.sellName +'" class="link4" href="'+ this.url +'">'+view.ebookIco(this)+'</div>'+ view.subString(this.sellName) +'</a><br>文轩价：<span class="fb red">￥'+ this.salePrice.toFixed(2) +'</span></dt>';     
						}else{
							 html = html + "<dd><a target='_blank' class='link4' title='" + this.sellName + "' href='" + this.url + "'>"+view.ebookIco(this)+"</div>" + this.sellName + "</a></dd>";
						}
	                  });
				  }else{
					html = clearHtml;
				   }
						
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