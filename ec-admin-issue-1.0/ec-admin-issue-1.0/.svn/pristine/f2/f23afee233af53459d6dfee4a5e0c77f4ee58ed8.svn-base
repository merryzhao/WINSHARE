$(function(){
	var temp = {};
	var tempps = {};
	var orders = {};
	var checked = {};
	var productDiv;
	var orderDiv;
	
	$("#productSelect").hide();
	$("#addProduct").click(function(){
		$("#productSelect").dialog({width:800, title:"添加商品"});
		clickRadio(this,4);
	});
	
	$("#orderPanel").hide();
	$("#addOrder").click(function(){
		$("#orderPanel").dialog({width:800, title:"添加订单"});
		$("#ordersList").val("");
	});
	
	$(".starttime").datepicker({regional : "zh-CN"});
	$(".endtime").datepicker({regional : "zh-CN"});
	
	//-------------------------控制选择-------------------------
	$("#showOrderItemsTable tr").live("click",function(){
		var self = $(this);
		var txt = self.find(".available");
		if(self.data("check")){
			self.data("check", false);
			txt.val(0);
			self.removeClass("selected");
			delete checked[txt.data("id")];
		} else{
			//选中
			self.addClass("selected");
			self.data("check", true);
			var available = txt.data("available");
			txt.val(available);
			
			var id = txt.data("id");
			checked[id] = parseInt(available);
		}
		showCount();
	});
	$(".available").live("click", function(event){
		event.stopPropagation();
	});
	$(".available").live("focusout", function(){
		var self = $(this);
		var available = self.data("available");
		if(self.val() > available || self.val() < 0){
			alert("不在当前订单的可退量范围内, 请重新输入!");
			self.focus();
			self.val(available);
			return;
		}
		checked[self.data("id")] = parseInt(self.val());
		showCount();
	});
	
	function showCount(){
		var count = 0;
		for(var i in checked){
			count += checked[i];
		}
		$("#showCount").html("当前退换数:<b style='color:red;font-size:24px;'>" + count + "</b>");
	}
	
	/**
	 * 退换货新建，根据退换货类型，显示或隐藏部分内容
	 */
	$("#orders").hide();
	$('#type').change(function() {
			orders = {};
				var typeid = $('#type').val()
				if (typeid == 24003 || typeid == 24004) {
					$("#orders").show();
					$(".fhyf").hide();
					$('#refundcompensating').show();
					$('#refundcompensating_title').show();
					$('#refundcouponvalue').show();
					$('#refundcouponvalue_title').show();
					if(!productDiv){
						productDiv = $('#product').detach();
					}
					if(orderDiv){
						$("#newreturn").after(orderDiv);
						orderDiv = null;
					}
				} else {
					$('#product').show();
					if($('#type').val() == 24002){
						$('.fhyf').hide();
					}else{
						$(".fhyf").show();
					}
					$('#refundcompensating').hide();
					$('#refundcompensating_title').hide();
					$('#refundcouponvalue').hide();
					$('#refundcouponvalue_title').hide();
					
					if(!orderDiv){
						orderDiv = $("#orders").detach();
					}
					if(productDiv){
						$("#newreturn").after(productDiv);
						productDiv = null;
					}
					
					
				}

			});
	
	
	//-------------------------END控制选择-------------------------
	//-------------------------插入-------------------------
	$("#insertbtn").click(function(){
		for(var i in checked){
			if(!orders[i]){
				orders[i] = {};
			}
			if(!orders[i].order){
				orders[i].order = temp[i];
			}
			if(!orders[i].product){
				orders[i].product = [];
			}
			if(!orders[i].product[tempps.id]){
				orders[i].product[tempps.id] = {};
			}
			orders[i].product[tempps.id].product = tempps;
			orders[i].product[tempps.id].count = checked[i];
			orders[i].product[tempps.id].available = $("#order" + i).data("available");
			orders[i].product[tempps.id].itemid = $("#order" + i).data("itemid");
		}
		showInsert();
		clean();
	});
	
	$(".removeOrder").live("click",function(){
		if(confirm("确定要删除吗?删除后只能重新添加哦~~~")){
			delOrderItem($(this).data("id"));
		}
	});
	$("$.productItem").live("focusout", function(){
		var self = $(this);
		var available = self.data("available");
		if(self.val() > available || self.val() < 0){
			alert("不在当前订单的可退量范围内, 请重新输入!");
			self.focus();
			self.val(self.data("val"));
			return;
		}
		orders[self.data("id")].product[self.data("pid")].count = self.val();
		showInsert()
	});
	function showInsert(){
		var html = "";
		var count = 0;
		for(var i in orders){
			html += "<tr>";
			var order = orders[i].order;
			for(var j in orders[i].product){
				html += wraptd(i);
				html += wraptd(order.customer.name);
				html += wraptd(order.consignee.consignee);
//				html += wraptd(order.shop.shopName);
				var ps = orders[i].product[j];
				html += wraptd(j);
				html += wraptd(ps.product.product.name);
				html += wraptd(ps.product.product.listPrice);
				html += wraptd(ps.available);
				html += wraptd("<input type='text' value='"+ps.count+"' data-val='"+ps.count+"' class='productItem' style='width:30px;' data-id='"+i+"' data-pid='"+j+"' data-available='"+ps.available+"'>");
				html += wraptd("<a href='javascript:;' class='removeOrder' data-id='"+i+"'>删除</a>");
				count += parseInt(ps.count);
			}
			html += "</tr>";
		}
		$("#producttable").html(html);
		$("#productCount").html("("+count+")");
	}
	
	function delOrderItem(id){
		var self = $("a[data-id="+id+"]");
		delete orders[id];
		var tr = self.parents("tr");
		tr.remove();
	}
	function clean(){
		temp = {};
		tempps = {};
		checked = {};
		$("#showOrderItemsTable").html("");
		$("#showProdcutItem").html("");
		$("#showCount").html("");
	}
	//-------------------------END插入-------------------------
	//-------------------------插入订单-------------------------
	$("#insertOrder").click(function(){
		var val = $("#ordersList").val();
		var items = val.split(/[\s,]+(?!$)/);
		var html = "";
		for(var i in items){
			var order = items[i];
			html += "<tr>";
			html += wraptd(order); 
			html += wraptd("<a href='javascript:;' class='removeOrderItem' data-id='"+order+"'>删除</a>"); 
			html += "</tr>";
			if(!orders[order]){
				orders[order] = {};
			}
			orders[order].order = {"id":order};
		}
		$("#orderData").html(html);
		
	});
	
	$(".removeOrderItem").live("click",function(){
		delOrderItem($(this).data("id"));
	});
	
	//-------------------------END插入订单-------------------------
	//-------------------------提交-------------------------
	$("#collectPanel").hide();
	$("#checkForm").click(function(){
		$("#collectPanel").dialog({width:800});
		var products = {};
		for(var i in orders){
			for(var j in orders[i].product){
				if(!products[j]){
					products[j] = {};
				}
				var p = orders[i].product[j];
				if(!products[j].count){
					products[j].count = 0;
				}
				products[j].count = parseInt(products[j].count) + parseInt(p.count); 
				
				var html = "";
				html += wraptd(p.product.id);
				html += wraptd(p.product.product.name);
				html += wraptd(p.product.product.listPrice);
				products[j].name = p.product.product.name;
				products[j].html = html;
			}
			html += wraptd("");
		}
		var html = "";
		for(var i in products){
			var p = products[i]
			html += "<tr>";
//			html += p.name + ":" + p.available;
			html += p.html;
			html += wraptd("<span style='font-size: 18;color:red'>" + p.count + "</span>");
			html += "</tr>";
		}
		$("#collect").html(html);
		$("#collectMsg").html("");
	});
	
	$("#submitBtn").click(function(){
		$("#collectMsg").append("<div>正在进行处理, 请不要关闭浏览器~~~</div>");
//		var originalorder = $("[name='originalorder']").val();
		var item = {};
		item['format'] = "json";
		item['refundcompensating'] = $("#refundcompensating").val();
		item['type'] = $("#type").val();
		item['responsible'] = $("#responsible").val();
		item['holder'] = $("#holder").val();
		item['reason'] = $("#reason").val();
		item['pickup'] = $("#pickup").val();
		item['refundcouponvalue'] = $("#refundcouponvalue").val();
		item['remark'] = $("#remark").val();
		item["refunddeliveryfee"] = $("#refunddeliveryfee").val();
		item["originalreturned"] = $("#originalreturned").val();
		for(var i in orders){
			var order = orders[i].order;
			var dt = $.extend({}, item);
			dt.originalorder = i;
			var par = "";
			for(var j in orders[i].product){
				var product = orders[i].product[j];
				var count = product.count;
				if(count <= 0){
					$("#collectMsg").append("<div>订单"+i+"中的["+product.product.product.name+"("+product.product.id+")]退换数量小于等于0, 将不做保存!</div>");
					continue;
				}
				var oi = product.itemid;
				par += "&item=" + oi;
				par += "&itemcount=" + count;
				
			}
			
			$.ajax({
				async:false,
				url:"/returnorder/new",
				type:"post",
				data:packUrl(dt) + par,
				dataType: "json",
				success: function(msg){
					console.log(msg);
					if(msg.success){
						var id;
						if(msg.returnOrder){
							id = msg.returnOrder.id;
						}
						$("#collectMsg").append("<div>订单[" + i + "->" + id + "]操作成功! 将会从列表中删除!</div>");
						delOrderItem(i);
					} else{
						$("#collectMsg").append("<div><b>订单["+i+"]操作失败!"+msg.errorMessage+"</b></div>");
						console.log("请求成功，处理失败...");
					}
				}, 
				error: function(msg){
					$("#collectMsg").append("<div><b>订单["+i+"]操作失败!"+msg.errorMessage+"</b></div>");
					console.log("请求失败...");
				}
			});
		}
		$("#collectMsg").append("<div>提交完成!</div>");
	});
	
	function packUrl(item){
		var html = "";
		for(var i in item){
			if(html == ""){
				html += i + "=" + item[i];
			}
			html += "&" + i + "=" + item[i];
		}
		return html;
	}
	
	//-------------------------END提交-------------------------
	
	//-------------------------查询-------------------------
	var findData;
	$("#findProductBtn").click(function(){
		var data = {"format":"json"};
		if($("#findname").val() && $("#findname").val() != ""){
			data["name"] = $("#findname").val();
		}
		if($("#findconsignee").val() && $("#findconsignee").val() != ""){
			data["consignee"] = $("#findconsignee").val();
		}
		if($(".starttime").val() && $(".starttime").val() != ""){
			data["startDeliveryTime"] = $(".starttime").val(); 
		}
		if($(".endtime").val() && $(".endtime").val() != ""){
			data["endDeliveryTime"] = $(".endtime").val(); 
		}
		
		data["transferResult"] = $("input[type=radio][name=transferResult]:checked").val();
		
		data[$("#findtype").val()] = $("#findtypevalue").val();
		
		
		findData = $.extend(true, {}, data);
		find(data);
	});
	
	
	
	/**
	 * 查询
	 */
	function find(data){
		//清除之前选择的数据
		clean();
		var url = "findorder";
		$("#showOrderItemsTable").html("");
		$("#showProdcutItem").html("");
		$("#showCount").html("正在进行查询, 请稍等...");
		$.ajax({
			'url':url, 
			type:"post",
			dataType:"json",
			'data':data,
			success:function(msg){
				$("#showCount").html("查询成功!");
				console.log(msg);
				showProduct(msg);
				showOrders(msg);
				showPage(msg.page);
			}, 
			error:function(msg){
				$("#showCount").html("查询失败!");
			}
		});
	}
	$(".pagechange").live("click", function(e){
		var page = $(this).data("page"); 
		var size = $(this).data("size"); 
		var data = $.extend(true, {}, findData);
		data.page = page;
		data.size = size;
		find(data);
	});
	function showPage(page){
		if(!page || page == null || page == undefined){
			return;
		}
		var html = "";
		var pre = page.currentPage <= 1 ? 1 : (page.currentPage -1);
		var next = page.currentPage >= page.pageCount ? page.currentPage : (page.currentPage + 1);
		html += "("+page.currentPage+"/"+page.pageCount+")&nbsp;";
		html += "<a href='javascript:;' class='pagechange' data-page='"+pre+"' data-size='"+page.pageSize+"'>上一页</a>&nbsp;";
		html += "<a href='javascript:;' class='pagechange' data-page='"+next+"' data-size='"+page.pageSize+"'>下一页</a>";
		html += "";
		$("#showPage").html(html);
	}
	
	function showProduct(msg){
		tempps = msg.ps;
		if(!tempps){
			$("#showCount").html("查询失败!");
			return;
		}
		var html = "";
		html += "<span>商品编号:"+tempps.id+"</span>";
		html += "<span>商品名称:"+tempps.product.name+"</span>";
		html += "<span>商品码洋:"+tempps.product.listPrice+"</span>";
		$("#showProdcutItem").html(html);
	}
	function showOrders(msg){
		var table = "";
		for(var i in msg.orders){
			var canshow = true;
			var order = msg.orders[i];
			temp[order.id] = order;
			var html = "<tr>";
			for(var j in order.itemList){
				var item = order.itemList[j];
				if(item.productSale.id == msg.ps.id){
					if(item.deliveryQuantity - item.returnQuantity <= 0){
						canshow = false;
					}
					html += wraptd(order.id);
					html += wraptd(order.customer.name);
					html += wraptd(order.consignee.consignee);
					html += wraptd(order.shop.shopName);
					html += wraptd(item.deliveryQuantity);
					html += wraptd(item.returnQuantity);
					html += wraptd(item.deliveryQuantity - item.returnQuantity);
					html += wraptd("<input type='text' value='0' class='available' id='order"+order.id+"' style='width:30px;' data-id='"+order.id+"' data-itemid='"+item.id+"' data-available='"+(item.deliveryQuantity - item.returnQuantity)+"'>");
					html += wraptd(order.transferResult.id == 35003? '未下传' :  '已下传');
					html += wraptd(order.distributionCenter == null ? '--' : order.distributionCenter.dcOriginal.name);//发货DC
					break;
				}
			}
			html += "</tr>";
			if(canshow){
				table += html;
			}
		}
		$("#showOrderItemsTable").html(table);
	}
	
	function wraptd(val){
		return "<td>" + val + "</td>";
	}
	//-------------------------END查询-------------------------
});

function clickRadio(radio,time){
	var trClass = "."+$(radio).parent().parent().attr("class");
	var date = new Date();
	if(time == 1){
		date.setDate(date.getDate()-1);    		
	}else if(time == 2){
		date.setDate(date.getDate()-7);      	
	}else if(time == 3){
		date.setMonth(date.getMonth()-1);      	
	}else if(time == 4){
		date.setMonth(date.getMonth()-3);      	
	}
	$(" .starttime").attr("value",date.getFullYear()+"-"+(date.getMonth()+1)+"-"+(date.getDate()));  
	var date1 =new Date();
	$(" .endtime").attr("value",date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+(date1.getDate()));        	
}


