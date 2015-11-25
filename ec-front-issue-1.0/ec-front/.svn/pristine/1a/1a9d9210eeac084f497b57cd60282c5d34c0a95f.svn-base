seajs.use(["jQuery","notify","cart","favorite","ui","login"],function($,notify,cart,favorite,ui,login){
	
	var el = $(".right_box");
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
		visitContent:function(no , sellName , href , image , price , isLast){
			var lastClass =  !isLast ? "" : 'class="last_g';
			return '<li ' + lastClass + '"><a target="_blank" href="' + href + '" title=""><img class="fl" src="'+ image +'"></a><h3>'+ no +'.<a target="_blank" href="'+ href +'" title="'+ sellName +'">'+ sellName +'</a></h3><b class="red fb">￥'+ price +'</b></li>';
		},
		init: function(){
			//历史浏览
			var visitEl = elLeft.find(".viewed_goods");
			if(visitEl){
				visitEl.html("<br/><div align='center'></div><br/>");
				$.getJSON(wwwBase+"/product/visit/list?&callback=?",{format:"jsonp"},function(data){
					var list = data.visitedList;
					var html = "";
					for(var i = 0; i < list.length; i++){
						html += view.visitContent(i+1,list[i].sellName,list[i].url,list[i].imageUrl,changeTwoDecimal_f(list[i].salePrice),i + 1==list.length);
					}
					if(!html){
						html = "<br/><div align='center'>您没有浏览任何商品</div><br/>";
					}
					visitEl.html(html);
				});
			}
		}
	};
	view.init();
});