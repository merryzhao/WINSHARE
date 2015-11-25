
seajs.use(["jQuery","visit"],function($,visit){
	 var el = $("ol.viewed_goods"),
	 	
 		 html = "",
		 clearHtml = "<div class=''>无浏览历史</div>",
 		 productSaleId = $(".productSaleId").val(),
          view = {
              init: function(){
                  el.html("<div class='loading'><p>正在更新浏览历史，请稍候...</p></div>");
                  el.show();
                  if(productSaleId){
                	  visit.add(productSaleId, el);
                  }else{
                	  visit.addall(el);
                  }
                  $(visit).bind(visit.CLEAR_EVENT, function(e){
                      view.clear();
                  });
                  $(visit).bind(visit.LOAD_EVENT, function(e, data){
                      view.load(data);	
                  });
				  $(visit).bind(visit.CLEAR_EVENT, function(e){
                      view.clear();
                  });
				  $("a[bind='clean']").live("click",function(){		  	
					  visit.clear();
				  }); 	
              },
              load: function(data){
            	  if(!data || !data.visitedList){
            		  return;
            	  }
                  $.each(data.visitedList, function(i){
                    //  html = html + "<li><img src='"+this.imageUrl+"'><a class='link4' title='" + this.sellName + "' href='" + this.url + "'>" + this.sellName + "</a></li>";
                      if(this.subheading == null){ this.subheading = ""};
	                  html +=  "<li><a href='"+this.url+"' title="+this.sellName+"><img class=\"fl\" src="+this.imageUrl+" alt=\"\"></a><h3>"+(i+1)+".<a href="+this.url+" title="+this.sellName+">"+this.sellName+"</a><b class=\"orange\">"+this.subheading+"</b></h3><b class=\"red fb\">￥"+this.salePrice.toFixed(2)+"</b></li>";
     
				  });
				    html += "<a  bind='clean' href='javascript:;'>清空浏览历史</a>";
                  el.html(html);
                  el.show();
              },
              clear: function(){
                  el.html(clearHtml);
              }
              
          };
	view.init();	
});