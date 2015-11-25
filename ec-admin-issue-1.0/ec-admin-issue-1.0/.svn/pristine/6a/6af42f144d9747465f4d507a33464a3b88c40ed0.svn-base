$().ready(function() {
	var dates = $( "#bookStartDate, #bookEndDate" ).datepicker({
			onSelect: function( selectedDate ) {
				var option = this.id == "bookStartDate" ? "minDate" : "maxDate",
					instance = $( this ).data( "datepicker" ),
					date = $.datepicker.parseDate(
						instance.settings.dateFormat ||
						$.datepicker._defaults.dateFormat,
						selectedDate, instance.settings );
				dates.not( this ).datepicker( "option", option, date );
			}
		});
	$("#saleStatus").live("change",function(){
 		if($("#saleStatus").val()==13002){
				var proId=$("#id").val();
  				$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					dataType : 'json',
					data : {
		 				"proId" : proId,
						"format" : 'json'
					},
					url : "/product/canbeonshelf",
					error : function() {// 请求失败处理函数
						alert('请求失败');
					},
					success : function(data) { // 请求成功后回调函数。
 						if(!data.result){
 							$("#saleStatus").val($("#oldSaleStatus").val());
 							alert("不符合上架销售的条件，上架失败！")
 						}
	 				}
 				});
 			};
            //选择下架时, 使用下拉列表
            showRemark();
		});
    function showRemark(){
        //选择下架时, 使用下拉列表
        if($("#saleStatus").val()==13003){
            $("#remark_checkbox_div").show();
            $("#remark").hide();
        } else {
            $("#remark_checkbox_div").hide();
            $("#remark").show();
        }
        $("#remark").html("");
    }
    //进入界面时就调用一次
    showRemark();
    $("#remark_checkbox").live("change", function(){
        var val =  $("#remark_checkbox").val();
        if(val != 0){
            $("#remark").html(val);
        } else {
            $("#remark").html("");
        }
    });
	
	$("input[bind='datepicker']").datepicker("option","minDate",new Date());
	$("#sbm").live("click",function(){
		if(checkBooking()==0){
			$("#proEditForm").submit();
		}
	})
	
})


$().ready(function() {
			$("#stockQuantity").blur(function(){
				$("#activeQuantity").val($(this).val()-$("#saleQuantity").text());
			});
			$("#activeQuantity").blur(function(){
				$("#stockQuantity").val(eval($(this).val())+eval($("#saleQuantity").text()));
			});
			if ($("#storageType").val() == $("#noEditType").val()) {
				$("#activeQuantity").attr("readonly", true);
				$("#showmsg").css("display", "block");
			}
			$("#storageType").live("change", function() {
						if ($(this).val() === $("#noEditType").val()) {
							$("#activeQuantity").val($("#oldQuantity").val());
							$("#activeQuantity").attr("readonly", true);
							$("#showmsg").css("display", "block");
						} else {
							$("#activeQuantity").attr("readonly", false);
							$("#showmsg").css("display", "none");
						}
					})

			// 表单验证
			$("#proEditForm").validate({
						rules : {
							sellName : {
								required : true
							},
							listPrice : {
								required : true,
								isMoney : true
							},
							activeQuantity : {
								required : true,
								orderIdNumber : true
							},
							stockQuantity : {
								orderIdNumber : true
							}
						},

						messages : {
							sellName : {
								required : "销售名称必须填写"
							},
							listPrice : {
								required : "定价必须填写",
								isMoney : "定价格式错误"
							},
							activeQuantity : {
								required : "可用数量必须填写",
								orderIdNumber : "数量格式错误"
							},
							stockQuantity : {
								orderIdNumber : "数量格式错误"
							}

						}
					});
			
			
    $("#proEditForm").submit(function(){
        var status = $("#saleStatus").val();
        var remark = $("#remark").val();

        if(status == '13003' && (null == remark || "" == remark)){
            alert("商品停用, 必须选择备注信息!");
            return false
        }
        return true;
    });

		});

		
	function checkBooking(){
 		var count=0;
 		var sd = $("#bookStartDate").val();
		var ed = $("#bookEndDate").val();
		if(sd==""||ed==""){
			$("#dateError").html("日期不能为空");
			count++;
		}else if(!comptime(sd,ed)){
			$("#dateError").html("");
			$("#dateError").html("日期先后顺序错误");
			count++;
		}else{
			$("#dateError").html("");
		}		
		return count;
	}

function comptime(beginTime, endTime) {
    var beginTimes = beginTime.substring(0, 10).split('-');
    var endTimes = endTime.substring(0, 10).split('-');
    beginTime = beginTimes[1] + '/' + beginTimes[2] + '/' + beginTimes[0] + '/ ' + beginTime.substring(10, 19);
    endTime = endTimes[1] + '/' + endTimes[2] + '/' + endTimes[0] + '/ ' + endTime.substring(10, 19);
    var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
    if (a < 0) {
        return false;
    } else {
     return true;
    }
}  
