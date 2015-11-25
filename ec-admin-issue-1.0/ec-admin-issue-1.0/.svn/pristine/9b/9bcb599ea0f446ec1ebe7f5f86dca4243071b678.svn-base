//验证
			function validateCategoryPrice(){
				    var  category_priceedit=$("#categoryPriceEdit");
					if(category_priceedit.find(".sellers").text()==""){
						 alert("请选择卖家");
						 return false;
					 }
					if(category_priceedit.find(".discount_method").length<1){
						 alert("请选择分类");
						 return false;
					}
					var flag = true;
					category_priceedit.find(".discount").each(function(){
						var   newPar=/^\d+(\.\d+)?$/
						if(!newPar.test($(this).val())){
							alert("折扣为正整数");
							flag=false;
							return false;
						}
						if($(this).val()==""){
							alert("请填写折扣")
							flag=false;
							return false;
						}
					
					});
					return flag;
			}
		$(function() {
			var dates = $( "#promotionStartDate, #promotionEndDate" ).datepicker({
			onSelect: function( selectedDate ) {
				var option = this.id == "promotionStartDate" ? "minDate" : "maxDate",
					instance = $( this ).data( "datepicker" ),
					date = $.datepicker.parseDate(
						instance.settings.dateFormat ||
						$.datepicker._defaults.dateFormat,
						selectedDate, instance.settings );
				dates.not( this ).datepicker( "option", option, date );
			}
		});
			
 			$('#promotionStartTime').timepicker({});          
			$('#promotionEndTime').timepicker({});
			//弹出式初始化
			$("#categoryDiv").dialog({
				autoOpen : false,
				bgiframe : false,
				modal : true,
				width : 350
			});
			$("#show_tree").click(function() {
				$("#categoryDiv").dialog("open");
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
			//修改分类列表中的折扣方式跟折扣
			$("#change_discount").click(function() {
				$("input[name=chk]:checked").each(function() {
					var row = $(this).parent().parent();
					var discount_method = row.find(".discount_method");
					var discount = row.find(".discount");
					discount_method.val($("#discount_methods").val());
					discount.val($("#discount").val());
				});
			});
		})
		
		var first = true;
		//打开树
		
		//获得点击后的tree并且在页面上显示
		var productArray = new Array();
		function insertNodes() {
			var nodes = zTree.getChangeCheckedNodes();
			insert(nodes);
		}

		//将选中的节点添加到表格中
		function insert(nodes) {
			for ( var i = 0; i < nodes.length; i++) {
				var string = nodes[i].name;
				var id = nodes[i].id;
				if(!iscontantProduct(productArray,id)){
					productArray.push(id);
					while (nodes[i].level != 0) {
						string = nodes[i].parentNode.name + "-" + string;
						nodes[i] = nodes[i].parentNode;
					}
					$("#categorylist tbody tr:last")
							.after(
									"<tr class='selectData' ><td><input type=checkbox name=chk /><input type=hidden name=categorys value="+id+" /></td><td>"
											+ string
											+ "</td><td><select name=categoryDisTypes class=discount_method ><option value=26001>统一折扣</option><option value=26002>只下调折扣</option><option value=26003>只上调折扣</option></select></td><td><input name=categoryDiscounts type=text class=discount size=5 /></td><td><a href=# class=del>删除</a></td></tr>");
				}
			}
			$("#categoryDiv").dialog("close");
			if($("#categorylist tbody tr").length!=2){
				$("#categorylist").show();
			}
			$("#nodata").remove();
		}
		
		//删除表格行
		$(".del").die().live("click", function() {
			var row = $(this).parent().parent();
			deleteProduct(productArray,row.find("input[name=categorys]").val())
			row.remove();
			hasData();
		});
		//判断数组中是否包含某元素 
		function iscontantProduct(array,e){
			for(var i=0;i<array.length;i++){
				if(array[i]==e){
					return true;
				}
			}
			return false;
		}
		//删除数组中的元素 
		function deleteProduct(array,e){
			for(var i=0;i<array.length;i++){
				if(array[i]==e){
					productArray=null;
					productArray=array.slice(0,i).concat(array.slice(i+1,array.length));
					return ;
				}
			}
		 }
		//如果表格中没有数据显示暂无数据，如果有数据则不显示
		function hasData(){
			if($(".selectData").length==0){
				$("#categorylist tbody tr:last").after("<tr id=nodata><td colspan=5>暂无数据</td></tr>");
			}
		}