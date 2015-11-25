var keyindex=2;   //防止key重复
var key='';//
var keyName=rndNum(4);
var disflag=true;//true为普通，false为梯度 
var havedata=false;//赠送信息是否完整 
var idList=new Array();
var tableth='<tr>'+
    '<th>编号</th>'+
    '<th>标题</th>'+
    '<th>面额</th>'+
    '<th>商品类型</th>'+
    '<th>订单基准金额</th>'+
    '<th>赠送数量</th>'+
    '<th>操作</th>'+
    '</tr>'; //表头 用以初始化表格 
$(document).ready(
 		function() {
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
 			$("#present_send").dialog({
 				autoOpen : false,
 				bgiframe : false,
 				modal : true,
 				width:600,
 				height:350
 			});	
 			$("#register_present_send").dialog({
 				autoOpen : false,
 				bgiframe : false,
 				modal : true,
 				width:600,
 				height:350
 			});	
 			//取消按钮事件
			$("#cancel").live('click',function(){
				$("#present_send").dialog("close");
				});	
			$("#regcancel").live('click',function(){
				$("#register_present_send").dialog("close");
				});	
 			
			$("#showtd").live('click',function(){
 				 $("#tidu").css('display','block');
 				 $("#putong").css('display','none');
 				disflag=false;
  				});	
			
			$("#showpt").live('click',function(){
				 $("#tidu").css('display','none');
 				 $("#putong").css('display','block');
 				disflag=true;
  				});		
 			
 			//礼券添加按钮事件 
			$("#preadd").live('click',function(){
  						var url = "/presentbatch/findbatchlist?format=json"; 
						var paramType = $("#paramType").val();
						var paramValue = $("#paramValue").val();
						var nomey = $("#nomey").val();
						$.ajax({
							async : false,
							cache : false,
							type : 'POST',
 							data : {
								"paramType" : paramType,
								"paramValue" : paramValue,
								"nomey" : nomey
							},
							url : url,
							error : function() {// 请求失败处理函数
								alert('请求失败');
							},
							success : function(data) { // 请求成功后回调函数。
 								if (data.message != 0) {
                                   var list=data.presentBatchs;
                                    for(var i=0;i<list.length;i++){
                                    	if(!iscontant(idList,list[i].id)){
                                    			idList.push(list[i].id);
                                	 	 	 $("#presentitems").append(
                                			  '<tr>'+
                       					  	  '<td class='+"'idclass'"+'>'
                       					  	  +"<input name='presentchoice' value='"+list[i].id+"' type='checkbox'  />"
                       					  	  +list[i].id+
                       					  	  '</td>'+
                    					  	  '<td>'+list[i].batchTitle+'</td>'+
                    					  	  '<td>'+list[i].value+'</td>'+
                    					  	  '<td>'+list[i].productType+'</td>'+
                    					  	  '<td>'+list[i].orderBaseAmount+'</td>'+
                    					  	  '<td>'+"<input type='text' class='nomey' size='10' value=''>"+'</td>'+
                    					  	  '<td>'+"<a href='javascript:void(0);' class='deleteItems'>删除</a>"+'</td>'+
                    					  	  '</tr>');
                                    		}
                                    }
								}
							}
 						});
				});	
			//礼券添加按钮事件 
			$("#regpreadd").live('click',function(){
  						var url = "/presentbatch/findbatchlist?format=json"; 
						var paramType = $("#paramType").val();
						var paramValue = $("#paramValue").val();
						var nomey = $("#nomey").val();
						$.ajax({
							async : false,
							cache : false,
							type : 'POST',
 							data : {
								"paramType" : paramType,
								"paramValue" : paramValue,
								"nomey" : nomey
							},
							url : url,
							error : function() {// 请求失败处理函数
								alert('请求失败');
							},
							success : function(data) { // 请求成功后回调函数。
 								if (data.message != 0) {
                                   var list=data.presentBatchs;
                                    for(var i=0;i<list.length;i++){
                                    	if(!iscontant(idList,list[i].id)){
                                    			idList.push(list[i].id);
                                	 	 	 $("#regpresentitems").append(
                                			  '<tr>'+
                       					  	  '<td class='+"'idclass'"+'>'
                       					  	  +"<input name='presentchoice' value='"+list[i].id+"' type='checkbox'  />"
                       					  	  +list[i].id+
                       					  	  '</td>'+
                    					  	  '<td>'+list[i].batchTitle+'</td>'+
                    					  	  '<td>'+list[i].value+'</td>'+
                    					  	  '<td>'+list[i].productType+'</td>'+
                    					  	  '<td>'+list[i].orderBaseAmount+'</td>'+
                    					  	  '<td>'+"<input type='text' class='nomey' size='10' value=''>"+'</td>'+
                    					  	  '<td>'+"<a href='javascript:void(0);' class='deleteItems'>删除</a>"+'</td>'+
                    					  	  '</tr>');
                                    		}
                                    }
								}
							}
 						});
				});
 			
 			//添加梯度优惠项
			$("#addtidu").live('click',function(){
				$("#tidu").append("<p>订单满<input type='text' size='10' name='sendprice'>元送  <a href='javascript:void(0);' id='addpresentitems"+keyindex+"' class='mark' onclick='showdiv(this)'>礼券 </a>  <a href='javascript:void(0);' class='addpresentitems"+keyindex+"' onclick='removeadditems(this)'>删除>> </a></p>");
				keyindex++;
				});	
 			$(".deleteItems").live('click',function(){
  				deleteElement(idList,$(this).parent().parent().find("td[class='idclass']").text());
 				$(this).parent().parent().remove();
 			});

		});

 
		
//移除卖家
function sellerdelete(o){
	  var $obj= $(o);
  	  $("#seller").append("<option value='"+$obj.parent().find("input[class='select-value']").val()+"'>"+$obj.parent().find("span[class='seller-text']").text()+"</option>");
      $obj.parent().remove(); 
}

// 显示礼券添加DIV 并初始化表格
function showdiv(o){
	var $obj= $(o); 
	var price=$obj.parent().find("input[name='sendprice']").val();
	var isHave=false;
	if(!/^[0-9]*(\.[0-9]{1,2})?$/.test(price)||$.trim(price)==""){
		 alert("请输入正确的订单金额");
		return false;
	}
    $("#tidu").find("input[name='sendprice']").each(function(){
     	if($(this).val()==price&&$(this).parent().find("a[class='mark']").attr("id")!=$obj.parent().find("a[class='mark']").attr("id")){
    		alert("已有相关的礼券赠送信息");
     		isHave=true;
    		return false;
    	}
    });
     if(isHave){
       return false;
    }
  	idList=null;
    idList=new Array();
    key=$obj.attr("id");
     if($("#"+key+"p").length>0){
     }else{
     	$("#tidudata").append("<p class='tidudatap' id='"+key+"p"+"'></p>");
    }
   	$("#presentitems").html(tableth+($('body').data(key+keyName)==null?"":$('body').data(key+keyName)));
	 $("#presentitems").find(":checkbox:checked").each(function(){
      	if(!iscontant(idList,$(this).val())){
 			idList.push($(this).val());
      	}
 	 });
   	$("#present_send").dialog("open");
}

//显示礼券添加DIV 并初始化表格
function showregdiv(o){
	var $obj= $(o); 
  	idList=null;
    idList=new Array();
    key=$obj.attr("id");
   	$("#regpresentitems").html(tableth+($('body').data(key+keyName)==null?"":$('body').data(key+keyName)));
	 $("#regpresentitems").find(":checkbox:checked").each(function(){
      	if(!iscontant(idList,$(this).val())){
 			idList.push($(this).val());
      	}
 	 });
   	$("#register_present_send").dialog("open");
}

//移除梯度优惠添加项 并清空相应cookie
function removeadditems(o){
	 var $obj= $(o);
	// $.cookie($obj.attr("class")+keyName,null);
 	 $("#"+$obj.attr("class")+"p").remove();
	 $obj.parent().remove(); 
}

//判断数组中是否包含某元素 
function iscontant(array,e){
	for(var i=0;i<array.length;i++){
		if(array[i]==e){
			return true;
		}
	}
	return false;
}

//删除数组中的元素 
function deleteElement(array,e){
	for(var i=0;i<array.length;i++){
		if(array[i]==e){
			idList=null;
			idList=array.slice(0,i).concat(array.slice(i+1,array.length));
			return ;
		}
	}
 }
 
function storedata(){
	var textdata="";
	havedata=false;
	var count="";
	var flag=true;
	if(disflag){
		textdata="{\""+$("#"+key).parent().find("input[name='sendprice']").val()+"\":[";
		$("#presentitems").find(":checkbox:checked").each(function(){
 			count=$(this).parent().parent().find("input[class='nomey']").val();
 			if(!/^[0-9]{1,9}$/.test(count)){
				flag=false;
				return flag;
			}
			havedata=true;
			textdata=textdata+"{\"id\":\""+$(this).val()+"\",\"count\":\""+$(this).parent().parent().find("input[class='nomey']").val()+"\"},";
  		 });
		textdata=textdata+"]}";
		$("#putongdata").text(textdata);
	}
	else{
		if($("#presentitems").find(":checkbox:checked").length>0){
  		textdata="\""+$("#"+key).parent().find("input[name='sendprice']").val()+"\":[";
 		$("#presentitems").find(":checkbox:checked").each(function(){
			count=$(this).parent().parent().find("input[class='nomey']").val();
			if(!/^[0-9]{1,9}$/.test(count)){
				flag=false;
				return flag;
			}
			havedata=true;
			textdata=textdata+"{\"id\":\""+$(this).val()+"\",\"count\":\""+$(this).parent().parent().find("input[class='nomey']").val()+"\"},";
  		 });
		  textdata=textdata+"],";
		} 
		$("#"+key+"p").text(textdata);
 	}
  	return flag;
}

function sbmdata(){
	var sbmtext="";
	if(disflag){
		sbmtext=$("#putongdata").text();
	}else{
		sbmtext="{"
			$("#tidudata").find("p[class='tidudatap']").each(function(){
				sbmtext=sbmtext+$(this).text();
				});
		sbmtext=sbmtext+"}";
	}
   	$("#promotionData1").val(sbmtext);

 }
 
 function valijsondata(){
		 if(!havedata){
			 return false;
		 }else{
			 return true;
		 }
 }
 
	//卖家添加按钮事件
function addSeller(){
	 if($("#seller").find("option:selected").text()==""){
		return;
	  }
		$("#seller-div").append("<p class='sellerp'><input type='hidden' class='select-value' value="+$("#seller").val()+">"+"<span class='seller-text'>"+$("#seller").find("option:selected").text()+"</span>"+"  <a href='javascript:void(0);' onclick='sellerdelete(this)'>删除</a>"+"<p>");
		$("#seller").find("option:selected").remove();
	} 
 
 function sbmdiv(){
 		var trtext="";
		 $("#presentitems").find(":checkbox:checked").each(function(){
  			 trtext=trtext+'<tr>'+$(this).parent().parent().html().replace("type=\"checkbox\"","type=\"checkbox\" checked=\"checked\"").replace("value=\"\"","").replace("size=\"10\"","size=\"10\" value=\""+$(this).parent().parent().find("input[class='nomey']").val()+"\"")+'</tr>';
 		 });
			 if(!storedata()){
				alert("请输入正确的赠送数量");
				return;
			 }
			 $('body').data(key+keyName,trtext);
			 $("#present_send").dialog("close");
 }
 function orderPresentValidate(){
	 var pflag=false;
	 sbmdata();
  	 if($("#seller_list").find("input[name='sellers']").length<=0){
		 alert("请选择卖家");
		 return false;
	 }
 	 if($("input[name='sendTime']:checked").length==0){
		 alert("请选择礼券发放时间");
		 return false;	 
   	 }
  	 if($("input[name='smanner']:checked").length==0){
		 alert("请选择促销方式");
		 return false;	 
   	 }
	 if(disflag){
		 if(!valijsondata()){
				alert("请把礼券赠送信息输入完整");
				return false;
			}
		 var pri=$("#putong").find("input[name='sendprice']").val();
	    	 if(!/^[0-9]*(\.[0-9]{1,2})?$/.test(pri)||$.trim(pri)==""){
	    		 alert("请输入正确的订单应满金额");
	    		 return false;
	    	 }
	 }else{
  			 if(!valijsondata()){
					alert("请把礼券赠送信息输入完整");
					return false;
				}
			 $("#tidu").find("input[name='sendprice']").each(function(){
				if(!/^[0-9]*(\.[0-9]{1,2})?$/.test($(this).val())||$.trim($(this).val())==""){
					 pflag=true;
 				} 
			 });
 			 if(pflag){
				 alert("请输入正确的订单应满金额");
				 return false;
			 }
 	 }
 
    return true;
  }
 
 function storepresenddata(){
		var textdata="";
		havedata=false;
		var count="";
		var flag=true;
		$("#regpresentitems").find(":checkbox:checked").each(function(){
			count=$(this).parent().parent().find("input[class='nomey']").val();
			if(!/^[0-9]{1,9}$/.test(count)){
				flag=false;
				return flag;
			}
			havedata=true;
			if(textdata == ""){
				textdata="{\"present\":[{\"id\":\""+$(this).val()+"\",\"count\":\""+$(this).parent().parent().find("input[class='nomey']").val()+"\"}"
			}else{
				textdata=textdata+",{\"id\":\""+$(this).val()+"\",\"count\":\""+$(this).parent().parent().find("input[class='nomey']").val()+"\"}";
			}
		 });
		textdata=textdata+"]}";
		$("#presentdata").text(textdata);
		return flag;
	}
 
 function sbmregdiv(){
		var trtext="";
		 $("#regpresentitems").find(":checkbox:checked").each(function(){
			 trtext=trtext+'<tr>'+$(this).parent().parent().html().replace("type=\"checkbox\"","type=\"checkbox\" checked=\"checked\"").replace("value=\"\"","").replace("size=\"10\"","size=\"10\" value=\""+$(this).parent().parent().find("input[class='nomey']").val()+"\"")+'</tr>';
		 });
			 if(!storepresenddata()){
				alert("请输入正确的赠送数量");
				return;
			 }
			 $('body').data(key+keyName,trtext);
			 $("#register_present_send").dialog("close");
}
 function registerPresentValidate(){
	 sbmregdata();
  	 if($("#seller_list").find("input[name='sellers']").length<=0){
		 alert("请选择卖家");
		 return false;
	 }
  	 if(!valijsondata()){
  		 alert("请把礼券赠送信息输入完整");
		return false;
	}
    return true;
  }
 
 function sbmregdata(){
		var sbmtext="";
		sbmtext=$("#presentdata").text();
	   	$("#promotionData1").val(sbmtext);
	 }
 function rndNum(n){
	 var rnd="";
	 for(var i=0;i<n;i++)
	 rnd+=Math.floor(Math.random()*10);
	 return rnd;
}
