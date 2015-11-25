seajs.use(["jQuery","notify","cart","favorite","ui","login"],function($,notify,cart,favorite,ui,login){
	var el = $(".right_box");
	var elLeft = $(".sg-selectbox-row");
	var sort = $('.sg-selectbox-sort');
	var queryString = window.location.search.substring(1);
	var context=$(".list-group");
	context.delegate(".group-ul>li","mouseover",function(){
		context.find(".group-ul>li.current").removeClass("current");
		$(this).addClass("current");
	});
	context.delegate(".group-ul>li","mouseout",function(){
		context.find(".group-ul>li.current").removeClass("current");
	});
	var wwwBase = "http://www.winxuan.com";
	var onlyEBook = "";
	function getUrl(queryString,param,value){
		var url = queryString.split("&");
		var reUrl = "";
		if(url != ''){
			for (var i=0; i<url.length; i++) {
				var p = url[i].split("=");
				if(p[0] != param && p[0] !== "page"){
					reUrl = reUrl == "" ? reUrl : reUrl + "&";
					reUrl = reUrl + p[0] + "=" + p[1]; 
					if(p[0]== "onlyEBook"){
						onlyEBook = p[1];
					}
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
		visitContent:function(sellName , href){
			return '<dd><a target="_blank" href="'+ href +'" title="'+ sellName +'" class="link4">'+ sellName +'</a></dd>';
		},
		init: function(){
			var orderPrice1 = "?" + getUrl(getUrl(queryString, "minprice", 0), "maxprice", 9.99);
			var orderPrice2 = "?" + getUrl(getUrl(queryString, "minprice", 10), "maxprice", 29.99);
			var orderPrice3 = "?" + getUrl(getUrl(queryString, "minprice", 30), "maxprice", 49.99);
			var orderPrice6 = "?" + getUrl(getUrl(queryString, "minprice", 50), "maxprice", 99.99);
			var orderPrice4 = "?" + getUrl(getUrl(queryString, "minprice", 100), "maxprice", 0);
			var orderPrice5 = "?" + getUrl(getUrl(queryString, "minprice", 0), "maxprice", 0);
			elLeft.find("a[bind='orderPrice1']").attr("href", orderPrice1);
			elLeft.find("a[bind='orderPrice2']").attr("href", orderPrice2);
			elLeft.find("a[bind='orderPrice3']").attr("href", orderPrice3);
			elLeft.find("a[bind='orderPrice6']").attr("href", orderPrice6);
			elLeft.find("a[bind='orderPrice4']").attr("href", orderPrice4);
			elLeft.find("a[bind='orderPrice5']").attr("href", orderPrice5);
			//高亮
			var minPrice = GetQueryString("minprice");
			var maxPrice = GetQueryString("maxprice");
			var priceEl = "orderPrice";
			if (maxPrice == 9.99) {
				priceEl += 1;
			}else if (minPrice == 10 && maxPrice == 29.99) {
					priceEl += 2;
				}else if (minPrice == 30 && maxPrice == 49.99) {
						priceEl += 3;
					}else if (minPrice == 100) {
							priceEl += 4;
						}else if (minPrice == 50 && maxPrice == 99.99) {
								priceEl += 6;
							}else if (!minPrice && !maxPrice) {
										priceEl += 5;
								}
			
			priceEl = elLeft.find("a[bind='" + priceEl + "']");
			priceEl.addClass("current");
			
			var orderDiscount0 = "?" + getUrl(getUrl(queryString, "mindiscount", 0), "maxdiscount", 0.3);
			var orderDiscount1 = "?" + getUrl(getUrl(queryString, "mindiscount", 0.71), "maxdiscount", 0);
			var orderDiscount2 = "?" + getUrl(getUrl(queryString, "mindiscount", 0.5), "maxdiscount", 0.7);
			var orderDiscount3 = "?" + getUrl(getUrl(queryString, "mindiscount", 0.3), "maxdiscount", 0.5);
			var orderDiscount4 = "?" + getUrl(getUrl(queryString, "mindiscount", 0), "maxdiscount", 0);
			elLeft.find("a[bind='orderDiscount0']").attr("href", orderDiscount0);
			elLeft.find("a[bind='orderDiscount1']").attr("href", orderDiscount1);
			elLeft.find("a[bind='orderDiscount2']").attr("href", orderDiscount2);
			elLeft.find("a[bind='orderDiscount3']").attr("href", orderDiscount3);
			elLeft.find("a[bind='orderDiscount4']").attr("href", orderDiscount4);
			//高亮
			var minDiscount = GetQueryString("mindiscount");
			var maxDiscount = GetQueryString("maxdiscount");
			var discountEl = "orderDiscount";
			if (minDiscount == 0.71) {
				discountEl += 1;
			}else if (minDiscount == 0.5 && maxDiscount == 0.7) {
					discountEl += 2;
				}else if (minDiscount == 0.3 && maxDiscount == 0.5) {
						discountEl += 3;
					}else if(minDiscount == null && maxDiscount == 0.3){
							discountEl += 0;
						}else if (!minDiscount && !maxDiscount) {
								discountEl += 4;
						}
			discountEl = elLeft.find("a[bind='" + discountEl + "']");
			discountEl.addClass("current");
			
			var orderRank1 = "?" + getUrl(queryString, "minrank", 5);
			var orderRank2 = "?" + getUrl(queryString, "minrank", 4);
			var orderRank3 = "?" + getUrl(queryString, "minrank", 3);
			var orderRank4 = "?" + getUrl(queryString, "minrank", 2);
			var orderRank5 = "?" + getUrl(queryString, "minrank", 0);
			elLeft.find("a[bind='orderRank1']").attr("href", orderRank1);
			elLeft.find("a[bind='orderRank2']").attr("href", orderRank2);
			elLeft.find("a[bind='orderRank3']").attr("href", orderRank3);
			elLeft.find("a[bind='orderRank4']").attr("href", orderRank4);
			elLeft.find("a[bind='orderRank5']").attr("href", orderRank5);
			//高亮
			var minRank = GetQueryString("minrank");
			var rankEl = "orderRank";
			if (minRank == 5) {
				rankEl += 1;
			}
			else 
				if (minRank == 4) {
					rankEl += 2;
				}
				else 
					if (minRank == 3) {
						rankEl += 3;
					}
					else 
						if (minRank == 2) {
							rankEl += 4;
						}
						else 
							if (!minRank) {
								rankEl += 5;
							}
			rankEl = elLeft.find("a[bind='" + rankEl + "']");
			rankEl.addClass("current");
			//历史浏览
			if(onlyEBook== "undefined" || onlyEBook == "" || onlyEBook == "false"){
				var visitEl = elLeft.find(".also_viewed");
				if(visitEl && visitEl.length > 0){
					visitEl.html("<br/><div align='center'></div><br/>");
					$.getJSON(wwwBase+"/product/visit/list?&callback=?",{format:"jsonp"},function(data){
						var list = data.visitedList;
						var html = "";
						for(var i = 0; i < list.length; i++){
							if(i == 0){
								html = '<dt><a target="_blank" href="'+ list[i].url +'"><img alt="'+ list[i].sellName +'" src="'+ list[i].imageUrl +'" /></a><a target="_blank" title="'+ list[i].sellName +'" class="link4" href="'+ list[i].url +'">'+ list[i].sellName +'</a><br>文轩价：<span class="fb red">￥'+ changeTwoDecimal_f(list[i].salePrice) +'</span></dt>';
							}
							else{
								html += view.visitContent(list[i].sellName,list[i].url);
							}
						}
						if(!html){
							html = "<br/><div align='center'>您没有浏览任何商品</div><br/>";
						}
						visitEl.html(html);
					});
				}
			}
			sort.find('dd>a[bind=listStyle]').click(function(){
				if(!$(this).is('.current')){
					$.cookie('style',$(this).attr('param'),'/');
					sort.find('dd>a[bind=listStyle]').removeClass('current');
					$(this).toggleClass('current');
					$('#grid,#list').toggle('slow');
				}
			});
		}
	};
	view.init();
});