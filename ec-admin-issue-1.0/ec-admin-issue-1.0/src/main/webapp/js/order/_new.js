 //初始 
var returnedDeliveryTypeId = -1;
var returnedPaymentId = -1;

	$().ready(function() {
			$("#create").validate({
				rules : {
					customerName : {
						required : true
					},
					consignee : {
						required : true
					},
					phone : {
						isTelephone : true
					},
					mobile : {
 						isCellphone :true
					},
					email : {
						required:true,
						email : true
					},
					zipcode : {
						isZipCode: true,
						required: true
					},
					address : {
						required:true
					},
					deliveryTypeId : {
						selectRequired:true
					},
					deliveryfee : {
						min:0,
						required : true,
						number:true 
					},
					paymentType : {
						selectRequired:true
					}
				},
				messages : {
					customerName : {
						required : ""
					},
					consignee : {
						required : ""
					},
					phone : {
						required : ""
					},
					mobile : {
						required:"",
						isCellphone :"格式"
					},
					email : {
						required:"",
						email : ""
					},
					zipcode : {
						isZipCode: "格式",
						required: ""
					},
					address : {
						required:""
					},
					deliveryTypeId : {
						selectRequired:""
					},
					deliveryfee : {
						min:"",
						required : "",
						number:"" 
					},
					paymentType : {
						selectRequired:""
					}
				}
			});
			$(".dc").html("<select name='dc' id='dc' onchange='chooseOrderSaleType()'><option value='-1'>自动分配</option><option value='110003'>8A17</option><option value='110004'>D818</option><option value='110005'>8A19</option><option value='110007'>D819</option></select>");
		});
	
	function checkCustomer(name) {
		if(name!="请输入正确的用户名"){
			var checkCustomerUrl = '/customer/checkCustomer';
		$.ajax({
			async : false,
			cache : false,
			type : 'GET',
			url : checkCustomerUrl,
			data : {'name':name, 'format':'json'},
			error : function() {
				
			},
			success : function(data) { 
				if(data.result==1){
					if(data.address != null){
					
					$("#tjdd").attr({"disabled":false});
					$("input[name=consignee]").val(data.address.consignee);
					$("input[name=phone]").val(data.address.phone);
					$("input[name=mobile]").val(data.address.mobile);
					$("input[name=email]").val(data.address.email);
					$("input[name=zipcode]").val(data.address.zipCode);
					$("input[name=address]").val(data.address.address);
					$("select[name=deliverytime]").val(data.address.deliveryOption.id);
					if($.browser.version != "6.0"){
					if(data.address.country.id!=""){
						$("select[name=country]").val(data.address.country.id);
						$("select[name=country]").change();
						if(data.address.province.id!=""){
							$("select[name=province]").val(data.address.province.id);
							$("select[name=province]").change();
							if(data.address.city.id!=""){
								$("select[name=city]").val(data.address.city.id);
								$("select[name=city]").change();
								if(data.address.district.id){ 
									$("select[name=district]").val(data.address.district.id);
									$("select[name=district]").change();
									if(data.address.town.id){
										// $("select[name=deliveryTypeId]").val(data.address.deliveryType.id);
										// $("select[name=paymentType]").val(data.address.payment.id);
										returnedDeliveryTypeId = data.address.deliveryType.id;
										returnedPaymentId = data.address.payment.id;
										$("select[name=town]").val(data.address.town.id);
										$("select[name=town]").change();
									}
								}
							}
						}
					}
					}
					}
					else if (data.address == null && name == "文轩网孔夫子店"){
						$("#tjdd").attr({"disabled":false});
						$("input[name=consignee]").val("");
						$("input[name=phone]").val("");
						$("input[name=mobile]").val("");
						$("select[name=deliverytime]").val("");
						$("input[name=email]").val("xhbs@info.com");
						$("input[name=zipcode]").val("000000");
						if (totalSaleMoney < 38){
							$("input[name=deliveryfee]").val("5.00");
						}
						if (totalSaleMoney >= 38){
							$("input[name=deliveryfee]").val("0.00");
						}
						$("input[name=address]").val(" ");
						$("#channelChecked").html("<input type=hidden name=channel value='115'/> (综合渠道—孔夫子)");
						if($.browser.version != "6.0"){
							$("select[name=country]").val("");
							$("select[name=country]").change();
							$("select[name=province]").val("");
							$("select[name=province]").change();
							$("select[name=city]").val("");
							$("select[name=city]").change();
							$("select[name=district]").val("");
							$("select[name=district]").change();
							$("select[name=town]").val("");
							$("select[name=town]").change();
						}
						initArea(23,175,1507,1510,34160);
					}
				}
				else{
					$("#customerName").val("请输入正确的用户名");
					$("#tjdd").attr({"disabled":true});
					$("input[name=consignee]").val("");
					$("input[name=phone]").val("");
					$("input[name=mobile]").val("");
					$("input[name=email]").val("");
					$("input[name=zipcode]").val("");
					$("input[name=address]").val("");
					$("select[name=deliverytime]").val("");
					initArea(23,-1,-1,-1,-1);
				}
			}
		});
		}
	}
	 $(document).ready(function() {
		 	// 弹出式初始化渠道树
		 	var name = $('#customerName').val();
			$("#channelDiv").dialog({
				autoOpen : false,
				bgiframe : true,
				modal : true,
				width : 350
			});
			
			$("#add_channel").click(function(){
				$("#channelDiv").dialog("open");
			});
			
		   initArea(23,175,-1,-1,-1);
		   $('#channelTitle').hide();
		   $('#outerOrderIdTitle').hide();
		   $('#outerOrderIdContent').hide();
		   if($('#isInvoice').val()){
		   $('#invoiceTitleTShow').hide();
		   $('#invoiceTitleShow').hide();
		   $('#companyNameTitle').hide();
		   $('#companyName').hide();
		   }
		   $('#isInvoice').change(function(){
			   // 如果开发票
			   if($('#isInvoice').val()){
				   $('#invoiceTitleTShow').show();
				   $('#invoiceTitleShow').show();
				   if($('#invoiceTitle').val()==3461){
					   $('#companyNameTitle').show();
					   $('#companyName').show();
					   }
			   }else{
				   $('#invoiceTitleTShow').hide();
				   $('#invoiceTitleShow').hide();
				   $('#companyNameTitle').hide();
				   $('#companyName').hide();
			   }
		   });
		   if($('#invoiceTitle').val()!=3461){
		   $('#companyNameTitle').hide();
		   $('#companyName').hide();
		   }
		   $('#invoiceTitle').change(function(){
			   // 如果是公司
			   if($('#invoiceTitle').val()==3461){
				   $('#companyNameTitle').show();
				   $('#companyName').show();
			   }else{
				   $('#companyNameTitle').hide();
				   $('#companyName').hide();
			   }
		   });
		   $('#country').change(function(){
			   araeChange();
		   });
		   $('#province').change(function(){
			   araeChange();
		   });
		   $('#city').change(function(){
			   araeChange();
		   });
		   $('#district').change(function(){
			   araeChange();
		   });
		   $('#town').change(function(){
			   araeChange();
		   });
		   $('#deliveryTypeId').change(function(){
			   deliveryTypeChange();
		   });
		   $('#address').blur(function(){
			   araeChange();
		   });
		   $('#deliveryTypeId').click(function(){
			  var lengt = $('#deliveryTypeId option').length;
			  if(lengt==1){
				var bool = $('#town').val()==-1;
				if(bool){
					alert("请确定区域值！");
				}
			  }
		   });
		   $('#paymentType').click(function(){
			   var lengt = $('#paymentType option').length;
			   if(lengt==1){
					var bool = $('#town').val()==-1||$('#deliveryTypeId').val()==-1;
					if(bool){
							alert("请确定区域或配送方式的值！");
					}
			   }
		   });
		   $("#customerName").blur(function(){
			   if($(this).val()!=null){
				   checkCustomer($(this).val());
			   }
		   });
		   // 外部ID失焦
		   $("#outerOrderId").blur(function(){
		   		var outerOrderId = $.trim($(this).val());
				if (outerOrderId != null && outerOrderId != ""){
					$.ajax({
						url : "/order/outer/" + outerOrderId +".json",
						async : false,
						cache : false,
						type : "GET",
						dataType : "JSON",
						error : function(){
							alert("请求出错");
						},
						success : function(data){
							if (data.result != 1){
								alert("外部订单号:" + outerOrderId + ",有重复!");
							}
						}
					})
				}
		   });
	 })
	 function checkExistsOuterOrderId(id){
	 	var exists = false;
			
	 	return exists;
	 }
	 
	 // 区域变更时
	 function araeChange(){
		 var town =  $('#town').val();
		 if(town!=-1){
			 ajaxget('/order/area',town,'',1);
			 return true;
		 }else{
			 // var province = $('#province').val();
			 // var city = $('#city').val();
			 // var district = $('#district').val();
			 // if(town == -1){
			 // buildTown(23, province, city, district, town);
			 // }
			 var html = '<option value="-1" selected="selected">请选择</option>'
			 $('#deliveryTypeId').html(html);
			 $('#paymentType').html(html);
			 return false;
		 }
	 }
    // 配送方式变更时
	 function deliveryTypeChange(){
		 var town =  $('#town').val();
		 var deliveryType = $('#deliveryTypeId').val();
		 if(town!=-1&&deliveryType!=-1){
			 ajaxget('/order/area',town,deliveryType,2);
			 return true;
		 }else{
			 var html = '<option value="-1" selected="selected">请选择</option>'
		     $('#paymentType').html(html);
			 return false;
		 }
		 
	 }
    // 设置配送方式和支付类型
	 function setDeliveryType(data){
		if($("#address").val()){
			beginSetDeliveryType(data);
		}
	 }
	 
	 function beginSetDeliveryType(data){
		var id = returnedDeliveryTypeId;
		var name = $('#customerName').val();
		if(name == "文轩网孔夫子店"){
			id = 3;
		}
		var html = "";
		var bool = true;
		var obj = data.deliveryType;
		for(var i=0;i<obj.length;i++){
			if(id==obj[i].deliveryType.id){
			html+='<option value="'+obj[i].deliveryType.id+'" selected="selected">'+obj[i].deliveryType.name+'</option>';
			bool = false;
			}else{
			html+='<option value="'+obj[i].deliveryType.id+'">'+obj[i].deliveryType.name+'</option>';	
			}
		}
		
		if(bool){
			html = '<option value="-1" selected="selected">请选择</option>'+html;
		}
		
		$('#deliveryTypeId').html(html);
		deliveryTypeChange();
	 }
	 
	 function setPaymentTerrace(data){
		
		 var id = returnedPaymentId;
		 var html = "";
         var bool = true;
		 var obj = data.payment;
		 var name = $('#customerName').val();
		 if (name == "文轩网孔夫子店"){
			 id = 100;
		 }
		 for(var i=0;i<obj.length;i++){
				if(id==obj[i].id){
					html+='<option value="'+obj[i].id+'" selected="selected">'+obj[i].name+'</option>';
					// bool = false;加载默认支付方式时也需要显示账期支付与渠道支付
				}
				else{
					html+='<option value="'+obj[i].id+'">'+obj[i].name+'</option>';	
				}
		 }
		 html = '<option value="-1" selected="selected">请选择</option>' + html;
		 if($('#deliveryTypeId').val() == 4){
			 bool = false;
		 }
		 if(bool){
			 if(name == "文轩网孔夫子店"){
				 html = '<option value="100" selected="selected">渠道支付</option>';
//				 html = html +'<option value="81">账期支付 </option>';
			 }
			 else{
				 html = html +'<option value="81">账期支付 </option><option value="100">渠道支付 </option>';
			 }
		 }
		 $('#paymentType').html(html);
	 }
    // ajax请求
	 function ajaxget(url,id,deliveryTypeId,type) {
   
		 var name = $('#customerName').val();
		 $.ajax({
   		async : false,
   		cache : false,
   		type : 'GET',
   		dataType : 'json',
   		data : {
   			"areaId" : $("#town").val(),
   			"free" : $("#total_money").text(),
   			"id" : id,
   			"deliveryTypeId" : deliveryTypeId,
   			"address" : $("input[name=address]").val(),
   			"format" : 'json'
   		},
   		url : "/order/area",
   		error : function() {// 请求失败处理函数
   			alert('请求失败');
   		},
   		success : function(data) { // 请求成功后回调函数。
    		if(type==1){
       			setDeliveryType(data);	
   			}else if(type==2){
   				setPaymentTerrace(data);
   			}
    		if(name!="文轩网孔夫子店"){
       			if(data.free!=null&&data.free.length!=0){
       				$("#deliveryfee").val(data.free);
       			}
    		}
   		}
   	});
   }
	
	
		$(function() {
			$("#accordion").accordion({
				collapsible : true,
				 autoHeight: false
			});
			setTotal();
			$("#total").hide();
		});
		
		$("input[name='purchaseQuantity']").live('blur',function(){
			if(check_stock()){
				if(parseInt($(this).val()) > parseInt($(".canSaleQuantity").text())){
					alert("购买量不能大于可用量!");
					$(this).val(this.getAttribute("value"));
					$(this).focus();
				}else if(isNaN($(this).val())){
					alert("购买量不能是0!");
					$(this).focus();
				}
			}else{
				var parent = $(this).parent().parent();
				var saleprice = parent.find(".saleprice").val();
				var xiaoji = $(this).val()*saleprice;
				parent.find(".allsaleprice").html(xiaoji.toFixed(2));
				setTotal();
			}
		});

		$("a[class='cldelete']").live('click',
				function() {
					var v = confirm("确定删除?");
					if(v==true){
						var thisvalue = $(this).parent().parent()
						.children(".index").html();
				$("#product").find("td[class='index']").each(function() {
					if ($(this).html() > thisvalue) {
						var temp = $(this).html() - 1;
						$(this).html(temp);
					}
				});
				$(this).parent().parent().remove();
				setTotal();
				var temp = "#error"+$(this).parent().parent().find(".produtSale").val();
				$(temp).remove();
					}
					if($(".product_content").length==0){
						$("#total").hide();
					}
				})
	
		$("#discount").live('blur',function(){
			if(!isNaN($(this).val())){
				var parent = $(this).parent().parent();
				var price = parent.find(".price").html();
				var saleprice = $(this).val()*price;
				parent.find(".saleprice").val(saleprice.toFixed(1)+'0');
				var xiaoji = parent.find(".num").val()*parent.find(".saleprice").val();
				parent.find(".allsaleprice").html(xiaoji.toFixed(2));
				setTotal();
			} ;
		});
		
		$("input[name='salePrice']").live('blur',function(){
			if(!isNaN($(this).val())){
				var parent = $(this).parent().parent();
				var price = parent.find(".price").html();
				var discount = $(this).val()/price;
				parent.find("#discount").val(discount.toFixed(2));
				var xiaoji = parent.find(".num").val()*parent.find(".saleprice").val();
				parent.find(".allsaleprice").html(xiaoji.toFixed(2));
				setTotal();
			};
		});
		function clear_product(){
			$("#product").find("tr[class='product_content']").each(
					function() {
						$(this).remove();
					});
			$("#total_number").html("0");
			$("#total_money").html("0");
			$("#total_sale_money").html("0");
			$("#information").html("").css({"border":"solid 0px red"});
			$("#total").hide();
		};
		
		$("#clear").live('click',function(){
			if (confirm("确认清空商品?")) {
				clear_product();
			}
		});
		// 设置总数量，总码洋，总实洋
		function setTotal(){
			var totalNumber = 0;
			$("input[name='purchaseQuantity']").each(function() {
				totalNumber += parseInt($(this).val());
			});
			$("#total_number").html(totalNumber);
			var totalMoney = 0;
			$("span[class='price']").each(
					function() {
						var number = $(this).parent().parent().find(
								"input[name='purchaseQuantity']").val();
						var tempMoney = (number * $(this).html());
						totalMoney += parseFloat(tempMoney);
					});
			$("#total_money").html(parseFloat(totalMoney.toFixed(2)));
			totalSaleMoney = 0;
			$("span[class='allsaleprice']").each(function() {
				totalSaleMoney += parseFloat($(this).html());
			});
			$("#total_sale_money").html(totalSaleMoney.toFixed(2));
		}
		
		function checkFile(){
			if($("#productFile").val()==""){
				return false;
			}
			return isPattern($("#productFile").val(),['xls','xlsx'],'请上传excel文件');
			return true;
		}
		/**
		 * 验证文件格式 regex:需要验证的文件名称如demo.doc suffix:后缀名doc message:提示信息
		 */
		function isPattern(regex,suffix,message){
			for(var i=0;i<suffix.length;i++){
				var r = regex.substring(regex.length-suffix[i].length);
				if(r==suffix[i]){  
			        return true;  
			    }
			}
			alert(message);
		    return false;  
		}  

		// 向订单商品添加商品
		function addProduct(data){
			var position = $("#product tr").length-2;
			var flag = true;
			var exist = false;
			var error = "";
			$("#product input[name='productSale']").each(function(index){
				if($(this).val()==data.productSaleId){
					flag = false;
					exist = true;
					if($("#information").find("#error"+data.productSaleId).html()==null){
						error = "<span id=error"+data.productSaleId+" > EC商品编号为"+data.productSaleId+"已添加! </span>";
					}
				}
			});
			if(flag && data.statusId != 13002 && data.supplyType != 7003){
				flag=false;
				if($("#information").find("#error"+data.productSaleId).html()==null){
					error = "<span id=error"+data.productSaleId+" > EC商品编号为"+data.productSaleId+"未上架! </span>";
				}
			}
			// 如果是快拨商品，则根据8A19判断是否可下单
			if(flag){
				var hasStock = false;
				if(data.stockQuantity != ""){
					var dcArr = data.stockQuantity.split("|");
					$.each(dcArr, function(key, value){
						if(value.split("-")[1] > 0){
							hasStock = true;
						}
					});
				}
				if(!hasStock || data.canSaleQuantity < 1){
					flag=false;
					if($("#information").find("#error"+data.productSaleId).html()==null){
						error = "<span style='margin-right:20px;' id=error"+data.productSaleId+" > EC商品编号为"+data.productSaleId+"可用量小于1! </span>";
					}
				}
			}
			
			if(!exist && (!check_stock() || flag)){
				// data.stockQuantity为商品可用量
				if(check_stock() && data.purchaseQuantity>data.canSaleQuantity){
					data.purchaseQuantity = data.canSaleQuantity;
				}
				var dcArr = data.stockQuantity.split("|");
				var stockQuantity = "<select>";
				$.each(dcArr, function(key, value){
					var name = value.split("-")[0];
					var id = value.split("-")[1];
					stockQuantity += "<option value='"+ id +"'>" + value + "</option>";
				});
				stockQuantity += "</select>";
				if(data.supplyType==7003){
					data.statusName="";
				}
			 	var str = "<tr class=product_content><td class=index>"+(1+position)+"</td><td>"+data.productSaleId+"<input class=produtSale type=hidden name=productSale value="+data.productSaleId+" /></td>"
				+"<td>"+data.productOuterId+"</td><td>"+data.productName+"</td><td>"+data.shopName+"</td><td>"+data.statusName+"</td><td><span class=canSaleQuantity >"+data.canSaleQuantity+"</span></td><td>"+stockQuantity+"</td><td><span class=price>"+data.listPrice+"</span><input type=hidden name=listPrice value="+data.listPrice+" /></td>"
				+"<td><input type=text size=2 id=discount name=discount value="+data.discount+" /></td><td><input type=text class=saleprice value="+(data.salePrice.toFixed(1)+'0')
				+" name=salePrice size=2 /></td><td><input class=num type=text name=purchaseQuantity value="+data.purchaseQuantity+" size=2 /></td><td><span class=allsaleprice>"
				+((data.purchaseQuantity*data.salePrice).toFixed(1)+'0')+"</span>元</td><td><a href=javascript:void(0) class=cldelete>删除</a></td></tr>"; 
				$("#product tr:eq("+position+")").after(str);
				setTotal();
			}else if(error!=''){
				$("#information").html($("#information").html()+"<br/>"+error);
			}
			if($(".product_content").length>0){
				$("#total").show();
			}
		}
		// 商品搜索时如果不填写收缩条件，则不搜索
		function checkAPF(){
			
 			var id =$("input[name='search_productSaleId']").val();
			var outerid=$("input[name='search_outerId']").val();
			var ISBN = $("input[name='search_ISBN']").val();
			var name = $("input[name='search_productName']").val();
			if(id==""&&outerid==""&&ISBN==""&&name==""){
				return false;
			}
			return true;
		}
	    function check_stock_change(){
	    	var reset = window.confirm("该操作会清空,已添加的订单商品");
	    	if (reset){
	    		clear_product();
	    	}
			return reset;
		};
		function check_stock(){
			return checkedVal($("#cbxCheckStock"));
		}
		
		function checkedVal(item){
			return $(item).attr("checked") == "checked";
		}
		
		function check_channel(){
			var check_val = $("#transferSelect").val();
			var dc_val = $("#dc").val();
			var dccustomer_val = $("#dccustomer").val();
			var productSale_val = $("input[name=productSale]").val();
			$("#orderCheckStock").val(check_stock());
			$("#transferResultId").val(check_val);
			$("#orderDc").val(dc_val);
			$("#orderDcCustomer").val(dccustomer_val);
			if(productSale_val == null || productSale_val == undefined){
				alert("请至少添加一个商品");
				return false;
			}
			if(check_val=='35004' && (dc_val == "" || dccustomer_val == "")) {
				alert("下传SAP，请完善物流信息");
				return false;
			}
			if($.trim($("#phone").val())==""&&$.trim($("#mobile").val())==""){
				alert("电话和手机号码必须填写一个");
				return false;
			}
			if($("#channelChecked").html()==""){
				alert("请添加渠道");
				return false;
			}
			return true;
		}
		
		function noProduct(){
			$("#information").html("").append("该商品不存在");
		}
		
		function chooseOrderSaleType(){
			var dc = $("#dc").val();
			if(dc == 110005){
				$("#orderSaleType").html("<input type='hidden' value='7003' name='orderSaleType'>");
				$("#search_supplytype").html("<input type='hidden' value='7003' name='search_supplyType'>");
			}
			if(confirm("该操作会清空,已添加的订单商品")){
				clear_product();
			}
		}
		
		function chooseDc(){
			if($("#transferSelect").val() == 35003){
				$(".dc").html("<select name='dc' id='dc' onchange='chooseOrderSaleType()'><option value='-1'>自动分配</option><option value='110006'>百货</option></select>");
			}else if($("#transferSelect").val() == 35004){
				$(".dc").html("<select name='dc' id='dc' onchange='chooseOrderSaleType()'><option value='-1'>自动分配</option><option value='110001'>D803</option><option value='110002'>D801</option></select>");
			}else if($("#transferSelect").val() == 35001){
				$(".dc").html("<select name='dc' id='dc' onchange='chooseOrderSaleType()'><option value='-1'>自动分配</option><option value='110003'>8A17</option><option value='110004'>D818</option><option value='110005'>8A19</option><option value='110007'>D819</option></select>");
			}else{
				alert("您选择的下传方式无对应的DC选项");
			}
		}