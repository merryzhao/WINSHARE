var productSaleList = new Array(); // 商品列表
var productMetas = new Array(); //商品扩展信息列表
var textidlist = new Array();
var thStr = "<tr>" + // 表头
"<th>序号</th>" + "<th>商品编码</th>" + "<th>商品名称</th>" + "<th>商品自编码</th>"
		+ "<th>商品码洋</th>" + "<th>商品实洋</th>" + "<th>库存</th>" + "<th>操作</th>"
		+ "</tr>";
$(document)
		.ready(
				function() {
					$("#categoryDiv").dialog({
						autoOpen : false,
						bgiframe : false,
						modal : true,
						width : 350
					});
					$("#chooseCategory").click(function() {
						$("#categoryDiv").dialog("open");
					});
					/**
					 * 添加商品
					 */
					$("#addProduct")
							.bind(
									"click",
									function() {
										var codingValue = $("#codingValue")
												.val();
										if ($.trim(codingValue).length == 0) {
											alert("请输入"
													+ $("#codingType").find(
															"option:selected")
															.text());
											return;
										}
										$
												.ajax({
													async : false,
													cache : false,
													type : 'POST',
													url : "/product/productinfolist",
													dataType : 'json',
													data : {
														"codingType" : $(
																"#codingType")
																.val(),
														"codingValue" : codingValue,
														"format" : 'json'
													},
													error : function() {// 请求失败处理函数
													},
													success : function(data) { // 请求成功后回调函数。
														textidlist.length=0;
														$("#msg").html("");
														if (data.error.length != 0) {
															$("#msg").html(
																	data.error);
														}
														// 如果商品列表没有商品，则把新增的全部商品放入列表
														if (productSaleList.length == 0
																|| productSaleList == null) {
															productSaleList = data.productSales;
														} else {// 把商品列表中没有的商品放入列表
															for ( var i = 0; i < data.productSales.length; i++) {
																if (!iscontant(
																		productSaleList,
																		data.productSales[i].id)) {
																	productSaleList
																			.push(data.productSales[i]);
																}
															}
														}
														productMetas = data.ProductMetas;
														updateTable();// 更新商品信息table
													}
												});
									})

					/**
					 * 提交
					 */
					$("#sbm")
							.bind(
									"click",
									function() {
										var editorArray = KindEditor.instances;
										for(var i = 0 ; i < editorArray.length;i++){
											editorArray[i].sync();
										}
										if(View.inputDc.val()<0){
											alert("请选择库位信息.")
											return;
										}
										
										if (productSaleList.length < 2) {
											alert("套装住至少包括2种商品");
											return;
										}
										var validataFlag = false;
										var errorName;
										$("#ex_table")
												.find("input[exclass='mun']")
												.each(
														function() {
															if (!/^[0-9]{1,9}$/
																	.test($(
																			this)
																			.val())) {
																validataFlag = true;
																errorName = $(
																		this)
																		.attr(
																				"exname");
															}
														})
										if (validataFlag) {
											alert(errorName + "必须为正整数");
											return false;
										}
										if ($.trim($("#name").val()).length == 0) {
											alert("套装书名称不能为空");
											return false;
										}
										if ($.trim($("#barcode").val()).length == 0) {
											alert("ISBN不能为空");
											return false;
										}
										if ($.trim($("#manuFacturer").val()).length == 0) {
											alert("出版社不能为空");
											return false;
										}
										if ($.trim($("#author").val()).length == 0) {
											alert("作者不能为空");
											return false;
										}
										if ($("#newCategory").val() == -1) {
											alert("销售分类不能为空");
											return false;
										}
										creatJsonData();
										getIds();
										$("#complexForm").submit();
									})

					/**
					 * 删除商品
					 */
					$(".delrow").live("click", function() {
						if (!confirm("确认删除此商品?")) {
							return false;
						}
						var psid;
						psid = $(this).attr("psid");
						deleteProductSaleElement(productSaleList, psid); // 把删除的商品从列表中移除
						textidlist.length=0;
						updateTable();
					})

					/**
					 * 设为主商品
					 */
					$(".setMain").live("click", function() {
						if (!confirm("确认设置此商品为主商品?")) {
							return false;
						}
						var psid = $(this).attr("psid");
						changeElement(psid); // 把要设置为主商品的商品移到列表开头
						updateTable();
					})

				})

// 判断数组中是否包含某元素
function iscontant(array, e) {
	for ( var i = 0; i < array.length; i++) {
		if (array[i].id == e) {
			return true;
		}
	}
	return false;
}

function iscontantMeta(array, e) {
	for ( var i = 0; i < array.length; i++) {
		if (array[i].productMeta.id == e) {
			return true;
		}
	}
	return false;
}

// 删除productSale数组中的元素
function deleteProductSaleElement(array, e) {
	for ( var i = 0; i < array.length; i++) {
		if (array[i].id == e) {
			productSaleList = null;
			productSaleList = array.slice(0, i).concat(
					array.slice(i + 1, array.length));
			return;
		}
	}
}

// 设置为套装书的主商品
function setMainProduct(ps) {
	var allMetaList = fetchMeta(ps);
	var trIndex = 0;
	// 基本属性
	$("#name").val(ps.product.name);
	$("#listPrice").text(getTotal("listPrice"));
	$("#salePrice").text(getTotal("salePrice"));
	$("#barcode").val(ps.product.barcode);
	$("#manuFacturer").val(ps.product.manufacturer);
	$("#author").val(ps.product.author);

	if (ps && ps.product && ps.product.categories) {
		var ct = ps.product.categories;
		for ( var i in ct) {
			makeCategoryItem(ct[i]);
		}
	}
	$("#mcCategory").text(ps.product.mcCategory);
	if (ps.product.productionDate != null) {
		$("#productionDate").val(
				ps.product.productionDate.replace(
						/[0-9][0-9]:[0-9][0-9]:[0-9][0-9]/, ''));
	}
	$("#promValue").val(ps.promValue);
	getComplexQuantity();
	$("#ex_table").empty();
	$("#exdec_table").empty();

	// 图片信息
	var conI;
	$("#detailimg").attr("src", ps.product.smallImageUrl);

	// 扩展属性
	var exList = ps.product.extendList;
	for ( var i = 0; i < exList.length; i++) {
		if (i % 3 == 0) {
			trIndex++;
			$("#ex_table").append("<tr id='tr" + trIndex + "'></tr>")
		}
		// 如果是枚举
		if (exList[i].productMeta.type.id == 12004) {
			$("#tr" + trIndex).append(
					"<td align='right'>" + exList[i].name + "：" + "</td>"
							+ "<td align='left'><select exname='"
							+ exList[i].name + "' class='ex' id='"
							+ exList[i].productMeta.id + "'></select></td>");
			for ( var j = 0; j < exList[i].productMeta.enumList.length; j++) {
				if (exList[i].value == exList[i].productMeta.enumList[j].value) {
					$("#" + exList[i].productMeta.id).append(
							"<option selected='selected' value='"
									+ exList[i].productMeta.enumList[j].value
									+ "'>"
									+ exList[i].productMeta.enumList[j].value
									+ "</option>");
				} else {
					$("#" + exList[i].productMeta.id).append(
							"<option value='"
									+ exList[i].productMeta.enumList[j].value
									+ "'>"
									+ exList[i].productMeta.enumList[j].value
									+ "</option>");
				}
			}
		} else if (exList[i].productMeta.type.id == 12003) { // 如果是日期
			$("#tr" + trIndex)
					.append(
							"<td align='right'>"
									+ exList[i].name
									+ "："
									+ "</td>"
									+ "<td align='left'><input  exname='"
									+ exList[i].name
									+ "' binddata='datepicker' readonly='readonly' type='text' value='"
									+ exList[i].value
											.replace(
													/^[0-9][0-9]:[0-9][0-9]:[0-9][0-9]$/,
													'') + "' class='ex' id='"
									+ exList[i].productMeta.id + "' /></td>")
		} else if (exList[i].productMeta.type.id == 12001) {// 如果是数字
			$("#tr" + trIndex).append(
					"<td align='right'>" + exList[i].name + "：" + "</td>"
							+ "<td align='left'><input exclass='mun' exname='"
							+ exList[i].name + "' type='text' value='"
							+ exList[i].value + "' class='ex' id='"
							+ exList[i].productMeta.id + "' /></td>")
		} else {
			$("#tr" + trIndex).append(
					"<td align='right'>" + exList[i].name + "：" + "</td>"
							+ "<td align='left'><input exname='"
							+ exList[i].name + "' type='text' value='"
							+ exList[i].value + "' class='ex' id='"
							+ exList[i].productMeta.id + "' /></td>")
		}
		conI = i;
	}
	for ( var i = 0; i < allMetaList.length && allMetaList[i] != null; i++) {
		if (iscontantMeta(exList, allMetaList[i].id)
				|| allMetaList[i].type.id == 12005) {
			continue;
		}
		if (conI % 3 == 0) {
			trIndex++;
			$("#ex_table").append("<tr id='tr" + trIndex + "'></tr>")
		}
		conI++;
		// 如果是枚举
		if (allMetaList[i].type.id == 12004) {
			$("#tr" + trIndex).append(
					"<td align='right'>" + allMetaList[i].name + "：" + "</td>"
							+ "<td align='left'><select exname='"
							+ allMetaList[i].name + "' class='ex' id='"
							+ allMetaList[i].id + "'></select></td>");
			for ( var j = 0; j < allMetaList[i].enumList.length; j++) {
				$("#" + allMetaList[i].id).append(
						"<option value='" + allMetaList[i].enumList[j].value
								+ "'>" + allMetaList[i].enumList[j].value
								+ "</option>");
			}
		} else if (allMetaList[i].type.id == 12003) { // 如果是日期
			$("#tr" + trIndex)
					.append(
							"<td align='right'>"
									+ allMetaList[i].name
									+ "："
									+ "</td>"
									+ "<td align='left'><input  exname='"
									+ allMetaList[i].name
									+ "' binddata='datepicker' readonly='readonly' type='text' class='ex' id='"
									+ allMetaList[i].id + "' /></td>")
		} else if (allMetaList[i].type.id == 12001) {// 如果是数字
			$("#tr" + trIndex).append(
					"<td align='right'>" + allMetaList[i].name + "：" + "</td>"
							+ "<td align='left'><input exclass='mun' exname='"
							+ allMetaList[i].name
							+ "' type='text' class='ex' id='"
							+ allMetaList[i].id + "' /></td>")
		} else if (allMetaList[i].type.id != 12003) {
			$("#tr" + trIndex).append(
					"<td align='right'>" + allMetaList[i].name + "：" + "</td>"
							+ "<td align='left'><input exname='"
							+ allMetaList[i].name
							+ "' type='text' class='ex' id='"
							+ allMetaList[i].id + "' /></td>")
		}
	}
	// 扩展描述
	var descriptionList = productMetas;
	for ( var i = 0; i < descriptionList.length && descriptionList[i] != null; i++) {
		$("#exdec_table")
				.append(
						"<tr>"
								+ "<td align='right'>"
								+ descriptionList[i].name
								+ "："
								+ "</td>"
								+ "<td align='left'><textarea style='width:650px;height:80px;' exname='"
								+ descriptionList[i].name + "'  class='exdec' id='"
								+ descriptionList[i].id + "'></textarea></td>"
								+ "</tr>");
		textidlist.push(descriptionList[i].id);
		
	}

	$("input[binddata='datepicker']").datepicker({
		regional : "zh-CN",
		changeYear : true
	});	
		for(var i = 0 ; i < textidlist.length;i++){
			KindEditor.create("textarea[id='"+textidlist[i]+"']",{
				uploadJson : '/editor/file?mainId='+ps.id,
				themeType : 'simple',
				newlineTag:'br',
				items:[
				        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'cut', 'copy', 'paste',
				        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
				        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
				        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
				        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
				        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
				        'table', 'hr','map','pagebreak',
				        'link', 'unlink',
				]
			});
		}
}

function getTotal(type) {
	if (type == "listPrice") {
		var totalListPrice = 0;
		for ( var i = 0; i < productSaleList.length; i++) {
			totalListPrice = totalListPrice
					+ productSaleList[i].product.listPrice;
		}
		return totalListPrice.toFixed(2);
	} else if (type == "salePrice") {
		var totalSalePrice = 0;
		for ( var i = 0; i < productSaleList.length; i++) {
			totalSalePrice = totalSalePrice + productSaleList[i].salePrice;
		}
		return totalSalePrice.toFixed(2);
	}
	return 0;
}

/**
 * 设置套装书库存 套装库存为子商品中可用量最小的那个商品的可用量-5，小于0时为0
 */
function getComplexQuantity() {
	var data = Array();
	data.push("format=json");
	for(var i=0;i<productSaleList.length;i++){
		data.push("id="+productSaleList[i].id)
	}
	//View.detectionStock();
}


/**
 * 交换数组元素
 */
function changeElement(psid) {
	for ( var i = 0; i < productSaleList.length; i++) {
		if (productSaleList[i].id == psid) {
			var hold = productSaleList[i];
			productSaleList[i] = productSaleList[0];
			productSaleList[0] = hold;
			return;
		}
	}
}

function renderStock(productSale) {
	var productStocks = productSale.productSaleStockVos
	var template = '{desc}({descname}):{stock}本<br/>';
		
	var result = template;
	var stockInfo = '';
	for ( var i = 0; i < productStocks.length; i++) {
		result = result.replace(/{desc}/g, productStocks[i].dcdetail.description);
		result = result.replace(/{descname}/g, productStocks[i].dcdetail.name);
		if(productSale.supplyType.id==13102){
			result = result.replace(/{stock}/g, productStocks[i].virtual);
		}else{
			result = result.replace(/{stock}/g, productStocks[i].availableQuantity);	
		}
			
		stockInfo += result;
		result = template;
	}
	return stockInfo;
}

function bookingDesc(productSale){
	var result = productSale.product.name;
	if(productSale.supplyType.id==13102){
		return result+"(预售)";
	}
	return result;
}

/**
 * 当删除或重新设置主商品的时候刷新列表
 */
function updateTable() {
	$("#dataTable").empty();
	$("#dataTable").append(thStr);
	if (productSaleList.length == 0) {
		return false;
	}
	$("#dataTable").append(
			"<tr>" + "<td>1<br/>(主商品)</td>" + "<td><a href='/product/"
					+ productSaleList[0].id + "/detail'>"
					+ productSaleList[0].id + "</a></td>" + "<td>"
					+ bookingDesc(productSaleList[0]) + "</td>" + "<td>"
					+ productSaleList[0].outerId + "</td>" + "<td>"
					+ productSaleList[0].product.listPrice + "</td>" + "<td>"
					+ productSaleList[0].salePrice + "</td>" + "<td>"
					+ renderStock(productSaleList[0])
					+ "</td>" + "<td><a class='delrow' psid='"
					+ productSaleList[0].id
					+ "' href='javascript:void(0);'>[删除]</a>"
					+ "<input type='hidden' name='psid' value='"
					+ productSaleList[0].id + "'></td>" + "</tr>");
	for ( var i = 1; i < productSaleList.length; i++) {
		$("#dataTable").append(
				"<tr>" + "<td>" + (i + 1) + "</td>" + "<td><a href='/product/"
						+ productSaleList[i].id + "/detail'>"
						+ productSaleList[i].id + "</a></td>" + "<td>"
						+ bookingDesc(productSaleList[i])+ "</td>" + "<td>"
						+ productSaleList[i].outerId + "</td>" + "<td>"
						+ productSaleList[i].product.listPrice + "</td>"
						+ "<td>" + productSaleList[i].salePrice + "</td>"
						+ "<td>"
						+ renderStock(productSaleList[i])
						+ "</td>" + "<td><a class='setMain' psid='"
						+ productSaleList[i].id
						+ "' href='javascript:void(0);'>[设为主商品]</a>"
						+ "<br/><a class='delrow' psid='"
						+ productSaleList[i].id
						+ "' href='javascript:void(0);'>[删除]</a>"
						+ "<input type='hidden' name='psid' value='"
						+ productSaleList[i].id + "'></td>" + "</tr>");
	}
	setMainProduct(productSaleList[0]);
}

function getIds() {
	var ids = "";
	for ( var i = 0; i < productSaleList.length; i++) {
		ids = ids + productSaleList[i].id + ",";
	}
	$("#mainId").val(productSaleList[0].id);
	$("#ids").val(ids);

}
$().ready(function() {
	$("input[bind=datepicker]").datepicker('option', 'changeYear', true);
});
