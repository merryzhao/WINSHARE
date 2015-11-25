$(document).ready(function() {
	$(".list-table").tablesorter({
		headers:{
			0:{sorter:false},2:{sorter:false},
			4:{sorter:false},7:{sorter:false}
		},
		callback : {
			callbackFun : function() {
				$(".td_click").click(
					Examine.tdClickEvent
				);
			}
		}
	});
	Examine.initEvent();
});
(
	function() {
		window.Examine = {};
		
		Examine.saveExamine={
				add:false,
				modity:false
		};
		//初始化事件
		Examine.initEvent = function() {
			//初始化审核时间
			$("#createStartTime").datepicker({changeYear:true});
			$("#createEndTime").datepicker({changeYear:true});
			//初始化下拉框
			$("#subMcCategory").val($.trim($("#mcCategory").val()));
			
			
			//实现复选框全选
			$("#select_checkbox").click(function(){
				if($(this).attr("checked")){
					$(".checkbox_list").attr("checked",true);
				}else{
					$(".checkbox_list").attr("checked",false);
				}
			
			});
			//反选
			$("#invert_select_checkbox").click(function(){
				var checkboxList = $(".checkbox_list");
				var length = checkboxList.length;
				if($(this).attr("checked")){
					for ( var i = 0; i < length; i++) {
						var checkbox = $(".checkbox_list").eq(i);
						var checkState = checkbox.attr("checked");
						if (checkState) {
							checkbox.attr("checked",false);
						} else {
							checkbox.attr("checked",true);
						}
					}
				}else{
					for ( var i = 0; i < length; i++) {
						var checkbox = $(".checkbox_list").eq(i);
						var checkState = checkbox.attr("checked");
						if (!checkState) {
							checkbox.attr("checked",true);
						} else {
							checkbox.attr("checked",false);
						}
					}
				}
			
			});
			
			
			//审核
			$("#examineItem").click(function() {
				var trCheckBoxs = $(".tr_list").find("input:checked");
				var length = trCheckBoxs.length;
				if(length < 1) {
					alert("请勾选需要审核的商品补货条目！");
					return false;
				}
				var ids = "";
				for ( var i = 0; i < length; i++) {
					var id = trCheckBoxs.eq(i).attr("id").slice("checkbox_".length);
					if(i != length-1) {
						ids += id +"|";
					} else {
						ids += id;
					}
				}
				if(!window.confirm("您确定要审核吗？")){
					return false;
				} 
				$("#messsage").text("系统正在处理，请耐心等待！……");
			
				$.ajax({
					url : "/replenishment/updateCheckStatus",
					type : "post",
					async : true,
					data :{
						ids : $.trim(ids),
						"queryForm.outerId" : $.trim($("#outerId").val()),
						"queryForm.productName" : $.trim($("#productName").val()),
						"queryForm.subMcCategory" : $.trim($("#mcCategory").val()),
						"queryForm.grades" : $.trim($("#grades").val()),
						"queryForm.basicPriceMin" : $.trim($("#basicPriceMin").val()),
						"queryForm.basicPriceMax" : $.trim($("#basicPriceMax").val()),
						"queryForm.availableQuantityMin" : $.trim($("#availableQuantityMin").val()),
						"queryForm.availableQuantityMax" : $.trim($("#availableQuantityMax").val()),
						"queryForm.repQuantityMin" : $.trim($("#repQuantityMin").val()),
						"queryForm.repQuantityMax" : $.trim($("#repQuantityMax").val()),
						"queryForm.dc" : $.trim($("#dc").val()),
						"queryForm.productVendor" : $.trim($("#productVendor").val()),
						"queryForm.createStartTime" : $.trim($("#createStartTime").val()),
						"queryForm.createEndTime" : $.trim($("#createEndTime").val()),
						"pagination.pageSize" : $.trim($("#pageSize").val()),
						"pagination.currentPage" : $.trim($("#currentPage").val())
					},
					dataType : "html",
					success : function(msg) {
						alert(msg);
						$("#check_msg").val(msg);
						$("#queryItemForm").submit();
					},
					error : function (XMLHttpRequest, textStatus, errorThrown) {
					    this;
					}
				});
				
			});
			
			//删除
			$("#deleteItem").click(function() {
				
				var length = $(".tr_list").find("input:checked").length;
				if(length < 1) {
					alert("请勾选需要删除的商品补货条目！");
					return false;
				}
				var ids = "";
				for ( var i = 0; i < length; i++) {
					var id = $(".tr_list").find("input:checked").eq(i).attr("id").slice(9);
					if(i != length-1) {
						ids += id +"|";
					} else {
						ids += id;
					}
				}
				
				if (!ids) {
					$("#message").text("请重新勾选需要删除的限制补货的EC编码！")
					return false;
				}
				
				if(!window.confirm("您确定要删除吗？")){
					return false;
				} 
				$("#messsage").text("系统正在处理，请耐心等待！……");
				$.ajax({
					url : "/replenishment/deleteProductItem",
					type : "post",
					async : true,
					data :{
						ids : $.trim(ids)
					},
					dataType : "html",
					success : function() {
						$("#queryItemForm").submit();
						$("#messsage").text("删除成功！");
					},
					error : function (XMLHttpRequest, textStatus, errorThrown) {
					    this;
					}
				});
			});
			
			//查询表单提交验证
			$("#queryItemForm").submit( function () {
				//查询输入验证
				if(!Examine.validateForm()){
					return false;
				}
				
			});
			
			//补货数量异步修改
			$(".td_click").click(
				Examine.tdClickEvent
			);
				
			
			//导出补货审核的数据
			$("#exportItem").click(function(){
				if(!window.confirm("确定要导出补货审核的所有信息吗？")){
					return false;
				}
				var data = {
						"outerId" : $.trim($("#outerId").val()),
						"productName" : $.trim($("#productName").val()),
						"subMcCategory" : $.trim($("#mcCategory").val()),
						"grades" : encodeURIComponent($.trim($("#grades").val())),
						"basicPriceMin" : $.trim($("#basicPriceMin").val()),
						"basicPriceMax" : $.trim($("#basicPriceMax").val()),
						"availableQuantityMin" : $.trim($("#availableQuantityMin").val()),
						"availableQuantityMax" : $.trim($("#availableQuantityMax").val()),
						"repQuantityMin" : $.trim($("#repQuantityMin").val()),
						"repQuantityMax" : $.trim($("#repQuantityMax").val()),
						"dc" : $.trim($("#dc").val()),
						"productVendor" : $.trim($("#productVendor").val()),
						"createStartTime" : $.trim($("#createStartTime").val()),
						"createEndTime" : $.trim($("#createEndTime").val()),
						"pageSize" : $.trim($("#pageSize").val()),
						"currentPage" : $.trim($("#currentPage").val())	
				};
			
				var condition = "";
				for ( var key in data) {
					condition = condition + key + "=" + data[key] + "&";
				}
				condition = condition.slice(0, condition.length-1);
				window.open("/replenishment/exportProductItem?"+condition);
			});
			
			
		};
		//表单提交验证
		Examine.validateForm = function() {
			//验证码洋区间 
			if(!($.trim($("#basicPriceMin").val())=="")){
				if(!(/^[0-9]+(.[0-9]{2})?$/).test($("#basicPriceMin").val())) {
					alert("码洋开始区间只能输入保留两位小数的数字！");
					$("#basicPriceMin").focus();
					return false;
				}
			}
			if(!($.trim($("#basicPriceMax").val())=="")){
				if(!(/^[0-9]+(.[0-9]{2})?$/).test($("#basicPriceMax").val())) {
					alert("码洋结束区间只能输入保留两位小数的数字！");
					$("#basicPriceMax").focus();
					return false;
				}
			}
			//验证库存数量
			if(!($.trim($("#availableQuantityMin").val())=="")){
				if(!(/^(-|\+)?\d+$/).test($("#availableQuantityMin").val())) {
					alert("可用量开始区间只能输入整数！");
					$("#availableQuantityMin").focus();
					return false;
				}
			}
			if(!($.trim($("#availableQuantityMax").val())=="")){
				if(!(/^(-|\+)?\d+$/).test($("#availableQuantityMax").val())) {
					alert("可用量结束区间只能输入整数！");
					$("#availableQuantityMax").focus();
					return false;
				}
			}
			//验证补货数量
			if(!($.trim($("#repQuantityMin").val())=="")){
				if(!(/^(-|\+)?\d+$/).test($("#repQuantityMin").val())) {
					alert("补货数量开始区间只能输入整数！");
					$("#repQuantityMin").focus();
					return false;
				}
			}
			if(!($.trim($("#repQuantityMax").val())=="")){
				if(!(/^(-|\+)?\d+$/).test($("#repQuantityMax").val())) {
					alert("补货数量结束区间只能输入整数！");
					$("#repQuantityMax").focus();
					return false;
				}
			}
			return true;
		};
		
		//表格的单击事件
		Examine.tdClickEvent = function() {
			var td = $(this);
			var id = td.attr("id").slice(3);
			var text = $.trim(td.find("a").text());
			td.html("");
			var input = $("<input type='text' style='width: 65px;' />");
			td.append(input);
			td.unbind("click");
			input.focus();
			input.attr("value",text);
			td.find("input").focusout(function() {
				var newText = $.trim(td.find("input").val());
				if(!(newText === "")){
					if(!(/^\d+$/).test(newText)) {
						alert("补货数量只能输入整数！");
						td.find("input").focus();
						return false;	
					}if(newText == 0) {
						alert("补货数量不能输入0！");
						td.find("input").focus();
						return false;
					}
				}
				td.html("<a href='javascript:void(0);'>"+newText+"</a>");
				$.ajax({
					url : "/replenishment/updateProductItem",
					type : "post",
					async : false,
					data :{
							id : $.trim(id),
							replenishmentQuantity : $.trim(newText)
						},
					dataType : "html",
					success : function() {
						$("#messsage").text("修改补货数量成功！");
						td.bind("click",Examine.tdClickEvent);
					}
				});
			});
		};
		
	}	
)();