seajs.use(["jQuery","notify","cart","favorite","ui","login"],function($,notify,cart,favorite,ui,login){
	
	var el = $(".sg-selectbox-sort");
	var btn = $(".active-middle");
	var elLeft = $(".left_box");
	var queryString = window.location.search.substring(1);
	
	var wwwBase = "http://www.winxuan.com";
	function getUrl(queryString,param,value){
		var url = queryString.split("&");
		var reUrl = "";
		if(url != ''){
			for (var i=0; i<url.length; i++) {
				var p = url[i].split("=");
				if(p[0] != param && p[0] !== "page"){
					reUrl = reUrl == "" ? reUrl : reUrl + "&";
					reUrl = reUrl + p[0] + "=" + p[1]; 
				}
			};
		}
		if(value){
			reUrl = (reUrl == "" ? reUrl : reUrl + "&");
			reUrl = reUrl + param + "=" + value;
		}
		return reUrl;
	};
	//获得参数
	function GetQueryString(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r!=null) return $.trim(r[2]); return null;
	}
	//强制保留两位小数
    function changeTwoDecimal_f(x){
        var f_x = parseFloat(x);
        if (isNaN(f_x)) {
            alert('function:changeTwoDecimal->parameter error');
            return false;
        }
        var f_x = Math.round(x * 100) / 100;
        var s_x = f_x.toString();
        var pos_decimal = s_x.indexOf('.');
        if (pos_decimal < 0) {
            pos_decimal = s_x.length;
            s_x += '.';
        }
        while (s_x.length <= pos_decimal + 2) {
            s_x += '0';
        }
        return s_x;
    }
	
	var view = {
		init:function(){
			
			seajs.use(["jQuery","toolkit"],function($,ToolKit){new ToolKit();});
			//只显示有货
			if(!!GetQueryString("hasstock")){
				$("a[name='onlyShowHasStock']").addClass('current');
			}
			if(!!GetQueryString("onlyEBook")){
				$("a[name='onlyShowHasEBook']").addClass('choose');
			}
			$("a[name='onlyShowHasStock']").click(function(){
				$(this).toggleClass('current');
				if($(this).hasClass('current')){
					var url = getUrl(queryString, "hasstock", "true");
				}else{
					var url = getUrl(queryString, "hasstock", "");
				}
				window.location = window.location.pathname + "?" + url;
				return false;
			});
			
			//只显示电子书
			$("a[name='onlyShowHasEBook']").click(function(){
				$(this).toggleClass('choose');
				if($(this).hasClass('choose')){
					var url = getUrl(queryString, "onlyEBook", "true");
					url = getUrl(url,"type", "");
				}
				else {
					var url = getUrl(queryString, "onlyEBook", "");
				}
				window.location = window.location.pathname + "?" + url;
				return false;
			});
			
			
			//删除通知
			btn.find("a[bind='remove']").live("click",function(){
				var type = btn.find("a[bind='removeBatch']").attr("name");
				if(type == "arrival"){
					type = 461003;
				}
				else if(type == "priceReduce"){
					type = 461004;
				}
				else {
					alert("错误");
					return;
				}
				var pid = $(this).closest("tr").attr("productsaleid");
				var param = [pid];
				notify.remove(param,this,type);
			});
			
			//添加到收藏夹
			btn.find("a[bind='addFavorite']").live("click",function(){
				var pid = $(this).attr("data-id");
				favorite.add(pid,$(this));
			});
			$(favorite).bind(favorite.ADD_EVENT,function(event,data,el){
				var html=favorite.template;
					html=html.replace("{result}",favorite.message[data.status]);
					html=html.replace("{productSaleId}",$(el).attr("productSaleId"));
				var tags=[];
					$.each(data.recommends,function(){
						tags.push("<a href='javascript:;'>"+this+"</a>");
					});
					html=html.replace("{tags}",tags.join(","));
				favorite.show(html,el);
			});
			$(favorite).bind(favorite.LOGIN_EVENT,function(event,id,el){
				favorite.hide();
				login.callbackUrl="http://search.winxuan.com/wxdoor.jsp?callback=miniLoginWindowCallBack";
				window.miniLoginWindowCallBack=function(status,msg){
					login.callback(status,msg);
				};
				login.show(el);
			});
			
			//排序
			//高亮 || 选中
			var p_sortEL = el.find(".p_sort");
			if(!p_sortEL || p_sortEL.length == 0){
				p_sortEL = el.find(".sort-item");
			}
			var order = GetQueryString("sort");
            
            if (!order) {
                p_sortEL.find("a[bind='orderDefault']").attr("class", "current");
            }else if (order == 7) {
                    p_sortEL.find("[bind='orderSell']").attr("class", "current");
            }else if (order == 8) {
                    p_sortEL.find("[bind='orderRank']").attr("class", "current");
            }else if (order == 1) {
                    p_sortEL.find("[bind='orderPriceDesc']").attr("class", "current");
            }else if (order == 3) {
                    p_sortEL.find("[bind='orderDiscount']").attr("class", "current");
            }else if (order == 6) {
                    p_sortEL.find("[bind='orderTime']").attr("class", "current");
            }else if (order == 2) {
                    p_sortEL.find("[bind='orderPriceAsc']").attr("class", "current");
            }
			
            el.find("a[bind='orderDefault']").live("click", function(){
				var param = "sort";
				var value = "";
				var url = getUrl(queryString, param, value);
				window.location = window.location.pathname + "?" + url;
				return false;//ie6 下不跳转
			});
			el.find("a[bind='orderSell']").live("click", function(){
				var param = "sort";
				var value = "7";
				var url = getUrl(queryString, param, value);
				window.location = window.location.pathname + "?" + url;
				return false;//ie6 下不跳转
			});
			el.find("a[bind='orderRank']").live("click", function(){
				var param = "sort";
				var value = "8";
				var url = getUrl(queryString, param, value);
				window.location = window.location.pathname + "?" + url;
				return false;//ie6 下不跳转
			});
			el.find("a[bind='orderPriceDesc']").live("click", function(){
				var param = "sort";
				var value = "2";
				var url = getUrl(queryString, param, value);
				window.location = window.location.pathname + "?" + url;
				return false;//ie6 下不跳转
			});
			el.find("a[bind='orderPriceAsc']").live("click", function(){
				var param = "sort";
				var value = "1";
				var url = getUrl(queryString, param, value);
				window.location = window.location.pathname + "?" + url;
				return false;//ie6 下不跳转
			});
			el.find("a[bind='orderDiscount']").live("click", function(){
				var param = "sort";
				var value = "3";
				var url = getUrl(queryString, param, value);
				window.location = window.location.pathname + "?" + url;
				return false;//ie6 下不跳转
			});
			el.find("a[bind='orderTime']").live("click", function(){
				var param = "sort";
				var value = "6";
				var url = getUrl(queryString, param, value);
				window.location = window.location.pathname + "?" + url;
				return false;//ie6 下不跳转
			});
			
			//下拉排序
			el.find("select[bind='orderOther']").live("change", function(){
				var param = "sort";
				var value = $(this).val();
				var url = getUrl(queryString, param, value);
				window.location = window.location.pathname + "?" + url;
				return false;//ie6 下不跳转
			});
			
			//分类高亮
			var categoryHrefEl = elLeft.find("a[name='categoryHref']");
			var regCat = new RegExp("http://.*com/(\\d+).*");
			var searchCats = window.location.href.match(regCat);
			if(searchCats && searchCats[1]){
				var searchCat = searchCats[1];
				for(var i =0; i < categoryHrefEl.length; i++){
					var categoryEle = $(categoryHrefEl[i]);
					if(categoryEle.attr("href").match(regCat)[1] == searchCat){
//						categoryEle.addClass("black");
						categoryEle.parent().addClass("have_bg");
						break;
					}
				}
			}
			
			
			//畅销榜
			var bestSellerEl = el.find(".bestsellers");
			if(bestSellerEl && bestSellerEl.length > 0){
				var bestBox = bestSellerEl.find(".best_box");
				var category;
				var type;
				var reg = new RegExp(".*\.winxuan\.com/(\\d+).*");
				var r = window.location.href.match(reg);
				if (r != null) {
					category = $.trim(r[1]);
					var onlyEBook = GetQueryString("onlyEBook");
					if(onlyEBook){
						type = 6004;
					}
				}
				else{
					bestBox.html("<br/><br/><br/><div align='center'>无</div><br/>");
					return;
				}
				
				bestBox.html("<br/><br/><br/><div align='center'>正在加载 ......</div><br/>");
				window.bestSellers=function(data){
					if(!data || data.length == 0 ||  !data.list || data.list.length == 0){
						bestBox.html("<br/><br/><br/><div align='center'>无</div><br/>");
						return;
					}
					var list = data.list;
					var html = "<div class='bestseller_fix'>";
					for(var i = 0; i < list.length; i++){
						var author = list[i].author ? (list[i].author) : "";
						var effectPrice = changeTwoDecimal_f(list[i].effectivePrice);
						var listPrice = changeTwoDecimal_f(list[i].listPrice);
						html += '<dl class="best_item"><dt><a target="_blank" title="'+ list[i].effectiveName +'" href="'+ list[i].url +'"><img alt="'+ list[i].effectiveName +'" src="'+ list[i].imageUrl +'"></a></dt><dd><strong><a target="_blank" title="'+ list[i].effectiveName +'" class="link1" href="'+ list[i].url +'">'+ list[i].effectiveName +'</a></strong></dd><dd>'+ author +'</dd><dd><b class="red fb">￥'+ effectPrice +'</b> <del>￥'+ listPrice +'</del></dd></dl>';
					}
					html+="</div>";
					bestBox.html(html);
					seajs.use(['jQuery','roller'],function($,Roller){
				    	var context=$("div.right_box");
				    	Roller({
				    		context:context,
				    		page:4,
				    		selector:{
				    			container:"div.bestsellers",
				    			next:"a.right_arrow",
				    			prev:"a.left_arrow",
				    			box:"div.bestseller_fix",
				    			items:"dl.best_item"
				    		}
				    	});
	 			   });
				};
				$.getScript(wwwBase + "/product/bestSellers?callback=bestSellers&format=jsonp&category="+category + "$type=" + type);
			}
		},
		
		
		remove:function(data){
			if(data.status != 1){
				alert("删除失败");
			}
			else{
				btn.find("tr input[name='checkbox']").removeAttr("checked");
				window.location.reload();
			}
		}
	};
	view.init();
});