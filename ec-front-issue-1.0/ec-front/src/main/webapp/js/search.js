seajs.use(["jQuery"],function($){
var url_search = "http://search.winxuan.com";
var url_css = "http://search.winxuan.com/css";
var url_js = "http://search.winxuan.com/js";


//search首页分类选择
$("#selectType li").click(function(){
	$("#selectType li").attr("class","iecss3");
	$(this).attr("class","tab_on iecss3");
	
	if($(this).html() == '图书'){
		$("#search_type").val(1);
	}else if($(this).html() == '音像'){
		$("#search_type").val(7786);
	}else if($(this).html() == '百货'){
		$("#search_type").val(7787);
	}
});
//大小图切换
$("#change_style .bigpic").click(function(){
	var display = GetQueryString("display");
	var url = window.location.href;
	if(!display){
		url = url + "&display=pic";
	}else if(display == 'list'){
		url = url.replace("display=list", "display=pic");
	}
	else if(display == 'pic'){
		url = url.replace("display=pic", "display=list");
	}
	else{
		alert('参数错误');
		return;
	}
	window.location = url;
});



//搜索历史

//绑定事件
function bindCleanSearchHistory(){
	$("#clear_search_history").click(function(){
		if(!confirm('您要删除搜索历史记录么?')){
			return;
		}
		clearSearchHistory();
	});
}
addAndGetSearchHistory(GetQueryString("keyword"), getSearchCount($(".com_pages .fl").html()));
function addAndGetSearchHistory(keyword, hits){
	var query = window.location.search;
	//去掉
	query = query.replace("&type=","");
	var paramsCount = query.split("&").length;
	var onlyGet = false;
	var responseTime = $("body").attr("responseTime");
	if(!responseTime){
		return;
	}
	if(!keyword){
		onlyGet = true;
	}
	if(!hits || hits <= 0){
		hits = 0;
	}
	if(paramsCount != 1 && paramsCount != 2){
		onlyGet = true;
	}
	if(paramsCount == 2){
		var code = GetQueryString("code");
		if(code != 1 && code != 7786 && code != 7787){
			onlyGet = true;
		}
	}
	$.ajax({
		   type: "POST",
		   url: "/searchHistory.json",
		   data: "keyword=" + keyword + "&hits=" + hits + "&responseTime=" + responseTime + "&onlyGet=" + onlyGet,
		   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		   success: function(data){
		   		$.each(data['searchHistory'], function(i,n){
		   			n['enkeyword'] = encodeURIComponent(n['keyword']);
	   				$("#search_history").append("<li><a class=\"link1\" href=\"/search?keyword="+n['enkeyword']+"\" >" + n['keyword'] + "</a>&nbsp;大约"+ n['hits'] +"条结果</li>");
		   		});
				$("#search_history").append("<li><a class=\"fr light_gray\" href=\"javascript:void(0);\" id=\"clear_search_history\">清空搜索历史</a></li>");
		   		bindCleanSearchHistory();
		   }
	}); 
}

function clearSearchHistory(){
	$.ajax({
		   type: "DELETE",
		   url: "/searchHistory.json",
		   success : (function(){
			   $("#search_history").html("<li><a class=\"fr light_gray\" href=\"javascript:void(0);\" id=\"clear_search_history\">清空搜索历史</a></li>");
		   })
	});
	
}

$("dl.store_list a[bind='searchHistoryOp']").click(function(){
	var page = GetQueryString("page") ? GetQueryString("page") : 1;
	var docnum = parseInt($(this).closest("dl").attr("index")) + (page - 1) * 30;
	var keyword = GetQueryString("keyword");
	var productSaleId = $(this).closest("dl").attr("productsaleid");
	if(docnum <= 0 || !keyword){
		return;
	}
	$.ajax({
		   type: "POST",
		   url: "/searchHistory/op.json",
		   data: "docnum=" + docnum + "&keyword=" + keyword + "&productSaleId=" + productSaleId,
		   contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		   success : (function(){
		   })
	}); 
});


//获得参数
function GetQueryString(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r!=null) return $.trim(r[2]); return null;
}
//获得记录条数
function getSearchCount(string){
	if(!string){
		return null;
	}
	var reg = new RegExp(".*共<.*>(\\d+)<.*>条.*");
	var r = string.match(reg);
	if (r!=null) return $.trim(r[1]); return null;
}

});