$(document).ready(function() {
	// 弹出式初始化渠道树
		$("#channelDiv").dialog({
			autoOpen : false,
			bgiframe : true,
			modal : true,
			width : 350
		});
		
		$("#add_channel").click(function(){
			$("#channelDiv").dialog("open");
		});
		
		$("#time_select").attr("selected","selected");
		
		 initArea(23);
			$(".price").change(function() {
				if ($(".price").val() == "1") {
					$(".price1").attr("name", "startListPrice");
					$(".price2").attr("name", "endListPrice")
				} else if ($(".price").val() == "2") {
					$(".price1").attr("name", "startSalePrice");
					$(".price2").attr("name", "endSalePrice")
				}
			})
			$(".userName").change(function() {
				if ($(".userName").val() == "1") {
					$("#name").attr("name", "registerName");
				} else if ($(".userName").val() == "2") {
					$("#name").attr("name", "consignee");
				}
			})
			$(".code").change(function() {
				if ($(".code").val() == "1") {
					$("#productCode").attr("name", "product");
				} else if ($(".code").val() == "2") {
					$("#productCode").attr("name", "owncode");
				} else if ($(".code").val() == "3") {
                    $("#productCode").attr("name", "barcode");
                }
			})
			$(".phoneCode").change(function() {
				if ($(".phoneCode").val() == "1") {
					$("#phoneNumber").attr("name", "phone");
				} else if ($(".phoneCode").val() == "2") {
					$("#phoneNumber").attr("name", "mobile");
				}			
			})
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
        	}
        	$(trClass +" .starttime").attr("value",date.getFullYear()+"-"+(date.getMonth()+1)+"-"+(date.getDate()));  
    		var date1 =new Date();
    		$(trClass+" .endtime").attr("value",date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+(date1.getDate()));        	
        }
        function clearRadio(object){
        	var trClass = "."+$(object).parent().parent().attr("class");	
        	$(trClass+" .day").attr("checked",false);
        	$(trClass+" .week").attr("checked",false);
        	$(trClass+" .month").attr("checked",false);
        }
		function change(time) {
			var timeClass = "." + $(time).attr("class");
			var timeTrClass = "." + $(time).parent().parent().attr("class");
			var startTime = timeTrClass +" .starttime";
			var endTime = timeTrClass +" .endtime";
			if ($(timeClass).val() == "1") {
				$(startTime).attr("name", "startCreateTime");
				$(endTime).attr("name", "endCreateTime");
			} else if ($(timeClass).val() == "2") {
				$(startTime).attr("name", "startLastProcessTime");
				$(endTime).attr("name", "endLastProcessTime");
			} else if ($(timeClass).val() == "3") {
				$(startTime).attr("name", "startPayTime");
				$(endTime).attr("name", "endPayTime");
			} else if ($(timeClass).val() == "4") {
				$(startTime).attr("name", "startDeliveryTime");
				$(endTime).attr("name", "endDeliveryTime");
			}
		}
		function addDate(obj) {
			var StringArray = [ "创建时间", "更新时间", "支付时间", "发货时间" ];
			var startTimeArray = [ "startCreateTime", "startLastProcessTime",
					"startPayTime", "startDeliveryTime" ];
			var endTimeArray = [ "endCreateTime", "endLastProcessTime",
					"endPayTime", "endDeliveryTime" ];
			var timeTrClass = "." + $(obj).parent().parent().attr("class");
			var timeClass = "." + $(timeTrClass + " select").attr("class");
			if ($(timeClass + " option").length <= 1)
				return false;
			$(".addbutton").remove();
			$(".ts").css("display","");
			$(timeClass).attr("disabled", "disabled");
			var firstNum = 0;
			var firstValue = 1;
			var count = 6 - $(timeClass + " option").length;
			var selected = $(timeClass).val();
			var selectTr = "";
			selectTr = selectTr	
					+ "<tr class ='timeTr"+count+"'><td class='hight-30' align='right'><select name='time"
					+ count + "' class ='time" + count
					+ "' onchange='change(this);' style='width:70px'>";
			$(timeClass)
					.each(
							function() {
								$(this).children("option").each(
												function() {
													var current = $(this).val();
													if (selected != $(this)
															.val()) {
														selectTr = selectTr
																+ "<option value='"+current+"'>"
																+ StringArray[current - 1]
																+ "</option>";
														if (firstNum < 1) {
															firstVaule = current - 1;
															firstNum++;
														}

													}
												});
							});
			selectTr = selectTr
					+"</td><td colspan='4' align='left' style='padding: 10px;'>"
					+"<input type='radio' name='Date"+count+"' onclick = 'clickRadio(this,1)'/>最近一天 "
					+"<input type='radio' name='Date"+count+"' onclick = 'clickRadio(this,2)'/>最近一周 "
					+"<input type='radio' name='Date"+count+"' onclick = 'clickRadio(this,3)'/>最近一月<input class ='starttime' name='"+startTimeArray[firstVaule]+"' id='"+startTimeArray[firstVaule]+"' style='width:100px'>~"
					+"<input class ='endtime' name='"+endTimeArray[firstVaule]+"' id='"+endTimeArray[firstVaule]+"' style='width:100px'>"
					+"<input class='addbutton' type='button' value='添    加' onclick='addDate(this);'><b class='ts' style='display:none'>不需要该条件可不填</b>";
			selectTr = selectTr + "</td></tr>";    
			$(selectTr).insertAfter($(timeTrClass));
			$("#" + startTimeArray[firstVaule]).datepicker({
				regional : "zh-CN"
			});
			$("#" + endTimeArray[firstVaule]).datepicker({
				regional : "zh-CN"
			});
		}
		
		$(function() {
			$( "#accordion" ).accordion({
				collapsible: true
			}).accordion( "activate" , 1) ;
		});