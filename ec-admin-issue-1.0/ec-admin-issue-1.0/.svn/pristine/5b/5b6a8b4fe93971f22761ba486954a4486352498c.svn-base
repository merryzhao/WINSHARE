function validateSingleProduct(){
				var flag = true;
				$("#singlePriceEdit").find(".saleprice").each(function(){
					if($(this).val()==""){
						$(this).focus();
						alert("请填写实洋")
						flag=false;
						return false;
					}
					if(isNaN($(this).val())){
						alert("实洋必须为数字");
						flag=false;
						return false;
					}
				});
				$("#singlePriceEdit").find(".discount").each(function(){
					if($(this).val()==""){
						alert("请填写折扣")
						flag=false;
						return false;
					}
						var   newPar=/^\d+(\.\d+)?$/
						if(!newPar.test($(this).val())){
							alert("折扣为正整数");
							flag=false;
							return false;
						}
				});
				if($("#singlePriceEdit").find(".discount").length<1){
					 alert("请添加商品");
					 return false;
				}
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
								$("#addProductDiv").html("").append(content)
										.dialog('open');
							});

		})
		//点击导入excel时候弹出层
		$("#importExcel")
				.click(
						function() {
							var content = '<form  enctype=multipart/form-data target=iframe method=post id=importExcelForm action=/product/addProductByExcel onsubmit="return check_file();" ><input id=singleExcel name=file type=file  /><button type=submit >导入Excel</button></form>';
							$("#addProductDiv").html("").append(content)
									.dialog('open');
						});
		//关闭层
		$("#addProductForm").live("submit", function() {
			$("#addProductDiv").dialog("close").html();
		});
		//检测excel是否选择
		function check_file(){
			if($("#singleExcel").val()==""){
				return false;
			}
			return isPattern($("#singleExcel").val(),['xls','xlsx'],'请上传excel文件');
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
		//批量修改实洋，折扣，限购数量
		$("#change").click(
				function() {
					if ($("#SetSalePrice").val() >0) {
						$("input[name=chk]:checked").each(
								function() {
									var saleprice = $(this).parent().parent()
											.find(".saleprice");
									saleprice.val($("#SetSalePrice").val());
									var discount = $(this).parent().parent()
											.find(".discount");
									var price = $(this).parent().parent().find(
											".price");
									discount.val((saleprice.val()
											/ price.html() * 100).toFixed(0));
								});
					}
					if ($("#SetDiscount").val()>0) {
						$("input[name=chk]:checked").each(
								function() {
									var discount = $(this).parent().parent()
											.find(".discount");
									discount.val($("#SetDiscount").val());
									var saleprice = $(this).parent().parent()
											.find(".saleprice");
									var price = $(this).parent().parent().find(
											".price");
									saleprice.val((price.html() * (discount
											.val() / 100)).toFixed(1));
								});
					}
					if ($("#SetNum").val()!='' && $("#SetNum").val()>=0) {
						$("input[name=chk]:checked").each(
								function() {
									var num = $(this).parent().parent().find(".num");
									num.val($("#SetNum").val());
								});
					}
					if ($("#SetNums").val()!='' && $("#SetNums").val()>=0) {
						$("input[name=chk]:checked").each(
								function() {
									var nums = $(this).parent().parent().find(".nums");
									nums.val($("#SetNums").val());
								});
					}
				});
		//实洋和折扣只能填写一个
		$("#SetSalePrice").click(function() {
			$("#SetDiscount").val("");
		});
		$("#SetDiscount").click(function() {
			$("#SetSalePrice").val("");
		});
		//批量重置
		$("#batchreset").click(
				function(event) {
					event.preventDefault();
					$("input[name=chk]:checked")
							.each(
									function() {
										var dbsaleprice = $(this).parent().parent().find(".dbsaleprice");
										var saleprice = $(this).parent().parent().find(".saleprice");
										var discount = $(this).parent().parent().find(".discount");
										var price = $(this).parent().parent().find(".price");
										var num = $(this).parent().parent().find(".num");
										var nums = $(this).parent().parent().find(".nums");
										saleprice.val(dbsaleprice.html());
										discount.val((saleprice.val()/ price.html() * 100).toFixed(0));
										num.val(0);
										nums.val(0);
									});
				});
		//单个重置
		$(".reset").live("click", function(event) {
			event.preventDefault();
			var dbsaleprice = $(this).parent().parent().find(".dbsaleprice");
			var saleprice = $(this).parent().parent().find(".saleprice");
			var discount = $(this).parent().parent().find(".discount");
			var price = $(this).parent().parent().find(".price");
			var num = $(this).parent().parent().find(".num");
			var nums = $(this).parent().parent().find(".nums");
			saleprice.val(dbsaleprice.html());
			discount.val((saleprice.val()/ price.html() * 100).toFixed(0));
			num.val(0);
			nums.val(0);
		});
		//删除
		$(".del").die().live("click", function(event) {
			event.preventDefault();
			$(this).parent().parent().remove();
			hasData();
		});
		//向商品列表添加商品
		var usedProducts = new Array();
		function addProduct(json) {
			for(x in usedProducts){
				if(usedProducts[x] == json.productId){
					return;
				}
			}
			$("#productlist").show();
			var disc = (json.saleprice / json.price * 100).toFixed(0);
			var v = "<tr class=selectData >" 
					+ "<td>" + fetchIndex()
					+ "</td><td><input type=checkbox name=chk /></td><td><input type=hidden name=productSaleIds value="+json.productId+" />"
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
					+ "</label></td><td><input size=4 name=salePrice class=saleprice  type=text onblur=setDiscount(this) value="+json.saleprice+" />(<label class=dbsaleprice >"
					+ json.dbSalePrice.toFixed(1)
					+ "</label>)</td><td><input class=discount type=text  onblur=setSalePrice(this) size=3 value="+disc
					+" /></td><td><input name=num class=num type=text  size=3 value='0'/> </td>"+
					"<td><input name=nums class=nums type=text  size=3 value='0'/> </td><td><a href=# class=reset>重置</a> <a href=# class=del>删除</a></td></tr>";
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
		function hasData(){
			if($(".selectData").length==0){
				$("#productlist tbody tr:last").after("<tr id=nodata><td colspan=10>暂无数据</td></tr>");
			}
		}

		function setDiscount(saleprice){
			if(saleprice.value>0){
				var price = $(saleprice).parent().parent().find(".price");
				var discount = $(saleprice).parent().parent().find(".discount");
				discount.val((saleprice.value / price.html() * 100).toFixed(0));
			}else{
				saleprice.value="";
			}
		}
		function setSalePrice(discount){
			if(discount.value>0){
				var price = $(discount).parent().parent().find(".price");
				var saleprice = $(discount).parent().parent().find(".saleprice");
				saleprice.val((price.html()*discount.value/100).toFixed(1));
			}else{
				discount.value="";
			}
		}