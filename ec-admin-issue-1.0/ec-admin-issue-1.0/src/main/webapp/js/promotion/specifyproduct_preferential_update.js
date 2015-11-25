//验证表单
function validatespecifyProductPreferential() {
	
	var manner = $("input:radio[name=orderPricemanner][checked]").val();
	var generalPriceSpends = $("input[name=generalPriceSpends]").val();
	var generalPriceSpares = $("input[name=generalPriceSpares]").val();
	var gradsPriceSpends = $("input[name=gradsPriceSpends]").val();
	var gradsPriceSpares = $("input[name=gradsPriceSpares]").val();
	// 普通优惠
	if((manner==0&&(generalPriceSpends==""||generalPriceSpares==""))){
       alert('请填写促销规则');
       return false;
	}
	// 梯度优惠
	if((manner==1&&(gradsPriceSpends==""||gradsPriceSpares==""))){
       alert('请填写促销规则');
       return false; 
	}
	
	if ($("#singlePrice").find(".price").length < 1) {
		alert("请添加商品");
		return false;
	}
	return true;
}

$(function() {
	//弹出式初始化
	$("#addProductDiv").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width : 350
	});
	//点击添加商品的时候弹出层
	$("#single_product_addProduct")
			.click(
					function() {
						var content = '<form target=iframe method=post id=addProductForm action=/product/addProduct ><select style=vertical-align:top name=type><option value=productId>商品编号</option><option value=isbn>ISBN号</option><option value=outerId>自编码</option></select><textarea name=productInfo id="productInfo"></textarea>'
								+ '<input  style=vertical-align:top type=submit value=添加商品></input></form>';
						$("#addProductDiv").html("").append(content).dialog(
								'open');
					});

});

//点击导入excel时候弹出层
$("#importExcel")
		.click(
				function() {
					var content = '<form  enctype=multipart/form-data target=iframe method=post id=importExcelForm action=/product/addProductByExcel onsubmit="return check_file();" ><input id=singleExcel name=file type=file  /><button type=submit >导入Excel</button></form>';
					$("#addProductDiv").html("").append(content).dialog('open');
				});
//关闭层
$("#addProductForm").live("submit", function() {
	$("#addProductDiv").dialog("close").html();
});

//检测excel是否选择
function check_file() {
	if ($("#singleExcel").val() == "") {
		return false;
	}
	return isPattern($("#singleExcel").val(), [ 'xls', 'xlsx' ], '请上传excel文件');
	return true;
}

//关闭层
$("#importExcelForm").live("submit", function() {
	$("#addProductDiv").dialog("close").html();
});

//全选功能
$("#quanxuan").click(function() {
	if ($("#quanxuan").attr("checked") == "checked") {
		$("input[name=chk]").attr("checked", true);
	} else {
		$("input[name=chk]").attr("checked", false);
	}
});

//反选功能
$("#fanxuan").click(function() {
	if ($("#fanxuan").attr("checked") == "checked") {
		$("input[name=chk]").each(function() {
			if ($(this).attr("checked") == "checked") {
				$(this).attr("checked", false);
			} else {
				$(this).attr("checked", true);
			}
		});
	} else {
		$("input[name=chk]").each(function() {
			if ($(this).attr("checked") == "checked") {
				$(this).attr("checked", false);
			} else {
				$(this).attr("checked", true);
			}
		});
	}
});


//删除
$(".del").die().live("click", function(event) {
	event.preventDefault();
	removeUsedProducts($(this).attr("id"));
	$(this).parent().parent().remove();
	hasData();
});

//如果表格中没有数据显示暂无数据，如果有数据则不显示
function hasData() {
	if ($(".selectData").length == 0) {
		$("#productlist tbody tr:last").after(
				"<tr id=nodata><td colspan=10>暂无数据</td></tr>");
	}
}

function removeUsedProducts(id) {
	for (x in usedProducts) {
		if (usedProducts[x] == id) {
			usedProducts = usedProducts.splice(x - 1, 1);
			return;
		}
	}
}

var usedProducts = new Array();
//向商品列表添加商品
function addProduct(json) {
	for (x in usedProducts) {
		if (usedProducts[x] == json.productId) {
			return;
		}
	}
	$("#productlist").show();
	var v = "<tr class=selectData>"
			+ "<td>" + fetchIndex() + "</td>"
			+ "<td><input type=checkbox name=chk /></td><td><input type=hidden name=productSaleIds value="
			+ json.productId
			+ " />"
			+ json.productId
			+ "</td><td>"
			+ json.productName
			+ "</td><td>"
			+ json.productType
			+ "</td><td>"
			+ json.shopName
			+ "</td><td>"
			+ json.storageType
			+ "</td><td><label class=price>"
			+ json.price
			+ "</label></td><td><label class=dbsaleprice >"
			+ json.dbSalePrice.toFixed(1)
			+ "</label></td>"
			+ "<td><a id="
			+ json.productId + " href=# class=del>删除</a></td></tr>";
	usedProducts.unshift(json.productId);
	$("#productlist tbody tr:last").after(v);
	$("#nodata").remove();
}

var index = -1;
function fetchIndex(){
	if(index == -1){
		index = $("#index").val();
	}
	return ++index;
}

function specifyProductPerferentialUpdate(){
	if (!mainvalidate()) {
	   return false;
    }
	if (!validatespecifyProductPreferential()) {
	   return false;
	}
	return true;
}
