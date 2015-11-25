seajs.use(["jQuery","table","roller","toolkit","bookingslider","pageturn"],function($,table,roller,ToolKit,BookSlider,PageTurn) {
	var tab_more_presell=table(
		{
			context:$(".mod-more-presell .J-tab"),
			label:"div.tab .J-tab-trigger",
			className:"current",
			content:".J-tab-cont .list"
	});
	var tab_hot_shelves=table(
	{
			context:$(".mod-hot-shelves .J-tab"),
			label:"div.tab .J-tab-trigger",
			className:"current",
			content:".J-tab-cont"
	});
	var tab_heavy_recommend=table(
	{
			context:$(".mod-heavy-recommend .J-tab"),
			label:"div.tab .J-tab-trigger",
			className:"current",
			content:".J-tab-cont"
	});
	new ToolKit();
	var newhotList={
		newhostEl:$("#newhotlist"),
		newhostTmpl:'<li><div class="cell cell-small-1"><div class="img"><a href="{producturl}" target="_blank"><img src="{imgurl}" alt=""></a></div><div class="name"><a href="{producturl}" target="_blank" title="">{productname}</a></div><div class="price"><span class="price-n">￥{saleprice}</span><span class="price-o">￥{listprice}</span></div></div></li>',
		init:function(){
			newhotList.load();
		},
		load:function(){
			var pagesize=5;
			$.ajax({
					url: "http://search.winxuan.com/top/booking?format=jsonp&pageSize="+pagesize+"&callback=?",
					async: false,
					success: function(data){
						newhotList.fillData(data);
					},
					error: function(xhr, status){
						newhotList.newhostEl.html("正在加载......");
					},
					dataType: "jsonp"
				});
		},
		fillData:function(data){			
			if(data&&data.hotsalelist){
				$.each(data.hotsalelist,function(k,result){
				var html=newhotList.newhostTmpl;
				html=html.replace(/\{producturl\}/g,result.url);
                html=html.replace(/\{imgurl\}/g,result.coverPath);
                html=html.replace(/\{productname\}/g,result.name);
                html=html.replace(/\{saleprice\}/g,result.saleprice);
                html=html.replace(/\{listprice\}/g,result.listprice);
				var el=$(html);
				el.appendTo(newhotList.newhostEl);
				
				});
			}else{
				newhotList.newhostEl.html("暂无数据");
			}

		}
	};
	newhotList.init();
	var slider=new BookSlider({
        element : '.mod-presell-slider',
        effect : 'fade',
        interval : 3000
    });
	var context=$(".mod-week-arrival .J-slider");
  	PageTurn({
  	context:context,
  	auto:true,
  	  		selector:{
    			container:"div.J-slider-cont",
    			items:"ul.list li",
    			movecontent:".cell",
    			arrow:".attach a",
    			next:"a.J-slider-next",
    			prev:"a.J-slider-prev"
    		}
  });
});