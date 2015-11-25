$(function() {
	
	// 从服务器抓去商品信息, codingValue为查询条如ID, handle回调函数, 用于填充数据, error错误信息回调
	function fetchProduct(codingType, codingValue, handle, error) {
		if ($.trim(codingValue).length == 0) {
			alert("请输入" + $("#codingType").find("option:selected").text());
			return;
		}
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : "/bundle/productinfolist",
			dataType : 'json',
			data : {
				"codingType" : codingType,
				"codingValue" : codingValue,
				"format" : 'json'
			},
			error : function() {// 请求失败处理函数
			},
			success : function(data) { // 请求成功后回调函数。
				error("")
				if (data.error.length != 0) {
					error(data.error);
				}
				handle(data.productSales);
			}
		});
	};
	
	var showMaster = function(item){
		var html = "";
		html += "<tr>";
		html += "<td>" + item.id +"</td>";
		html += "<td>"+ item.product.name + "</td>";
		html += "<td>"+ item.product.sort.name + "</td>";
		html += "<td>"+ item.shop.name + "</td>";
		html += "<td>"+ item.storageType.name + "</td>";
		html += "<td>"+ item.product.listPrice + "</td>";
		html += "<td>"+ item.salePrice + "</td>";
		html += "<td><a href='javascript:;' bind='masterDel' data-id='"+item.id+"'>删除</a></td>";
		html += "</tr>";
		return html;
	}
	var showProduct = function(item){
		var html = "";
		html += "<tr>";
		html += "<td>" + item.id +"</td>";
		html += "<td>"+ item.product.name + "</td>";
		html += "<td>"+ item.product.sort.name + "</td>";
		html += "<td>"+ item.shop.name + "</td>";
		html += "<td>"+ item.storageType.name + "</td>";
		html += "<td>"+ item.product.listPrice + "</td>";
		html += "<td>"+ item.salePrice + "</td>";
		html += "<td><input type='text' class='saveMoney' data-id='"+item.id+"' value='0'></td>";
		html += "<td><a href='javascript:;' bind='productDel' data-id='"+item.id+"' data-salePrice='"+item.salePrice+"'>删除</a></td>";
		html += "</tr>";
		return html;
	}
	
	/**
	 * 添加主商品
	 */
	$("a[bind=addMaster]").bind("click", function() {
		// 根据输入的信息取到商品
		fetchProduct($("#masterType").val(), $("#masterValue").val(), function(items) {
			var html = "";
			$.each(items, function(i, item){
				html += showMaster(item);
			});
			$("#masterBody").html(html);
		}, function(error){$("#msg").html(error);});
	});
	/**
	 * 添加子商品
	 */
	$("a[bind=addProduct]").bind("click", function() {
		// 根据输入的信息取到商品
		fetchProduct($("#productType").val(), $("#productValue").val(), function(items) {
			var html = ""; 
			$.each(items, function(i, item){
				html += showProduct(item);
			});
			$("#productBody").append(html);
		}, function(error){$("#productmsg").html(error);});
	});
	
	/**
	 * 删除数据
	 */
	$("[bind=masterDel]").live("click",function(){
		$(this).parent().parent().remove();
	});
	/**
	 * 删除数据
	 */
	$("[bind=productDel]").live("click",function(){
		$(this).parent().parent().remove();
	});
	/**
	 * ajax直接删除数据
	 */
	$("[bind=bundleProductDel]").live("click",function(){
		if(confirm("你确定要删除吗?")){
			var self = $(this);
			var id = self.data("id");
			$.ajax({
				url:'/bundle/bundleitem/'+id+'?format=json',
				type:'delete',
				dataType : 'json',
				success:function(e){
					self.parent().parent().remove();
					alert(e.msg);
				}
			});
		}
	});
	
	/**
	 * 提交
	 */
	$("input[bind=savebundle]").click(function(){
		var form = $("#bundleform");
		var html = "";
		$("[bind=masterDel]").each(function(){
			html += "<input type='hidden' name='masters' value='"+$(this).data("id")+"'>";
		});
		var cansubmit = true;
		$(".saveMoney").each(function(){
			var val = $(this).val(); 
			var sale = $(this).data("salePrice");
			if(/^\d+(\.\d+)?$/.test(val) == false){
				alert("优惠价必需是数字并且大于零!");
				cansubmit = false;
				return false;
			}
			if(val > sale){
				alert("优惠价不能大于实洋!");
				cansubmit = false;
				return false;
			}
			html += "<input type='hidden' name='products' value='"+ 
			$(this).data("id") + ":" + val +"'>";
		});
		if(cansubmit){
			form.html(html);
			form.submit();
		}
	});

});