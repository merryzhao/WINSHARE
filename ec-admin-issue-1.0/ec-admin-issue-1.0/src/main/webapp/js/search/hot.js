$(function(){
	var items = {};
	var changes = {};
	init();
	
	
	function each(handle){
		var trs = $("#hotList tr");
		$.each(trs, function(i, tr){
			var obj = $(tr);
			var id = obj.data("id");
			var keyword = obj.find("[name=keyword]").val();
			var href = obj.find("[name=href]").val();
			var item = {"index": id, "keyword": keyword, "href": href};
			handle(id, item);
		});
	}
	//初始化
	function init(){
		each(function(id, item){
			items[id] = item;
		});
	}
	
	//检查变更
	function checkChange(){
		each(function(id, item){
			var i = items[id];
			if(item.keyword == i.keyword && item.href == i.href){
				return;
			}
			changes[id] = item;
		});
	}
	
	$("[bind=updateHot]").click(function(){
		$(".msg").html("开始进行更新!");
		checkChange();
		$.each(changes, function(i, item){
			update(item);
		});
	});
	
	function update(item){
		$.ajax({
			"url":"/dic/hot?format=json"
			,"type":"post"
			,"data":item
			,"success":function(e){
				$(".msg").html("更新成功!");
			}
			,"error":function(){
				$(".msg").html("更新失败!");
			}
		});
	}
	function del(){}
});