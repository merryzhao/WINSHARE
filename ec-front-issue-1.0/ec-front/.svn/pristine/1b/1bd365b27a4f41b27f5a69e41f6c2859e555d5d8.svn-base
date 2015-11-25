seajs.use(["jQuery", "jQueryUI", "cart", "feedback","inputTip"], function($, ui, cart, feedback,inputTip){
    var $ = $.sub();
    ui($);
	inputTip($);
	
    $("input[bind='datepicker']").datepicker({
        regional: "zh-CN"
    });
    
    var ctn = $(".drop-down-able"), isMenuVisibility = false, menu = ctn.find("ul");
    menu.hover(function(){
        $(this).show();
        isMenuVisibility = true;
    }, function(){
        $(this).hide();
        isMenuVisibility = false;
        //$("#top-bar-lable").removeClass("top-bar-lable2");
        reverseImg(false);
    });
    ctn.find("label").hover(function(){
        if (!isMenuVisibility) {
            menu.show();
            isMenuVisibility = true;
            //$("#top-bar-lable").addClass("top-bar-lable2");
            reverseImg(true);
        }
    }, function(){
        if (!isMenuVisibility) {
            menu.hide();
            isMenuVissibility = false;
            $("#top-bar-lable").removeClass("top-bar-lable2");
            reverseImg(false);
        }
    });
    
    $("#batchPayOrder").click(function(e){
        batchPayOrder();
		e.preventDefault();
    });
    $("#batchSelectAll").click(function(){
        if ($(this).attr("checked")) {
            $("input[@name='batchOrder'][type='checkbox']").not("[disabled]").attr("checked", true);
        }
        else {
            $("input[@name='batchOrder'][type='checkbox']").not("[disabled]").attr("checked", false);
        }
    });
	
	$("#orderIdOrConsignee").inputTip("订单编号、收货人");
	$("select[name=processStatus]").change(function(){
		$("#find_btn").click();
	});
	$("select[name=payment]").change(function(){
		$("#find_btn").click();
	});
	$("#createTime").change(function(){
		$("#find_btn").click();
	});
	$("select[name=invoiceType]").change(function(){
		$("#find_btn").click();
	});
	
	//查询按钮
	$("#find_btn").click(function(){
		var re = /^[0-9]*$/g;
		var ooc = $("#orderIdOrConsignee").val();
		if(ooc == "订单编号、收货人"){
			$("input[type='hidden'][name='orderId']").val("");
			$("input[type='hidden'][name='consignee']").val("");
		}else if(re.test(ooc)){
			$("input[type='hidden'][name='orderId']").val(ooc);
			$("input[type='hidden'][name='consignee']").val("");
		} else {
			$("input[type='hidden'][name='orderId']").val("");
			$("input[type='hidden'][name='consignee']").val(ooc);
		}
		
		var ct = $("#createTime").val();
		switch(ct){
			case "1":
				$("input[type='hidden'][name='startCreateTime']").val(getDate(30));
			break;
			case "3":
				$("input[type='hidden'][name='startCreateTime']").val(getDate(90));
			break;
			case "6":
				$("input[type='hidden'][name='startCreateTime']").val(getDate(180));
			break;
			case "12":
				$("input[type='hidden'][name='startCreateTime']").val(getDate(365));
			break;
			case "0":
				$("input[type='hidden'][name='startCreateTime']").val("1970-01-01");
			break;
		}
		
		$("#orderForm").submit();
	});
	
	//绑定查询条件
	bindConditions();
	function bindConditions(){
		var time = $("input[type='hidden'][name='startCreateTime']").attr("time");
		if(time.length <= 0){
			return;
		}
		var ab =  new Date(parseInt(time));
		var space = new Date() - new Date(parseInt(time));
		if(space <= getMS(30 + 1)){
			$("#createTime").val(1);
		} else if(space <= getMS(90 + 1)){
			$("#createTime").val(3);
		} else if(space <= getMS(180 + 1)){
			$("#createTime").val(6);
		} else if(space <= getMS(365 + 1)){
			$("#createTime").val(12);
		} else {
			$("#createTime").val(0);
		}
		
		//设置订单/收件人
		if($("input[type='hidden'][name='orderId']").val() != ""){
			$("#orderIdOrConsignee").val(
			 	$("input[type='hidden'][name='orderId']").val()
			);
		} else if($("input[type='hidden'][name='consignee']").val() != ""){
			$("#orderIdOrConsignee").val(
			 	$("input[type='hidden'][name='consignee']").val()
			);
		}
	}
	
	//计算时间
	function getDate(days){
		var now = new Date();
		if(days > 0){
			now = new Date(now - getMS(days));
		}
		var yy = now.getFullYear();
		var mm = (now.getMonth() + 1).toString();
		var dd = now.getDate().toString();
		if(mm.length == 1)
			mm = "0" + mm;
		if(dd.length == 1)
			dd = '0' + dd;
		return yy + "-" + mm + "-" + dd;
	}
	
	function getMS(day){
		return day * 24 * 60 * 60 * 1000;
	}
    
    function selectBtachOrders(){
        var orderlist = "";
        var str = "";
        $("input[@name='batchOrder'][type='checkbox'][checked]").each(function(){
        	if(!!$(this).val()){
        		orderlist += str + $(this).val();
                str = ",";
        	}
        });
        if (orderlist != "") {
            return orderlist;
        }
        else {
            alert("未选择订单!");
        }
    }
    
    
    function batchPayOrder(){
        var orderList = selectBtachOrders();
        if (orderList) {
            $('input[name="payOrder"]').val(orderList);
            var form = $("form[name='batchPayOrder']"), 
            	param = {payOrder: $('input[name="payOrder"]').val()};
            form.submit();
        }
    }
    
    function selectBank(bank, code){
        $("input[@name='order'][type='hidden']").each(function(index, order){
            if ($(order).attr("name") == 'order') {
                $("form[name='batchPayOrder']").append("<input type='hidden' name='id' value='" + $(order).val() + "'>");
            }
        });
        $("form[name='batchPayOrder']").append("<input type='hidden' name='bank' value='" + bank + "'>");
        $("form[name='batchPayOrder']").append("<input type='hidden' name='code' value='" + code + "'>");
        var form = $("form[name='batchPayOrder']");
        form.submit();
    }
    
    var action={
		receiveOrder: function(el){
			var id = $(el).data("id");
			feedback.show({id:id, success:function(){
				$(el).after("<a class='link2' href='/customer/bought/"+id+"'>写评论</a>");
				$(el).remove();
			}});
	    },
		cancelOrder:function(el){
			if (!confirm("确认取消订单?")) {
	            return;
	        }
			var id=$(el).data("id");
	        var el = $("table").find("tr[attr='" + id + "']");
	        var controlEL = el.find("td[attr='control']");
	        controlEL.html("<img src='../images/loading.gif'/>");
	        $.get("/customer/order/" + id + "/cancel", {
	            format: "json"
	        }, function(data){
	            if (data.result == "1") {
	                refreshOrderRow(id, "取消成功!");
	            }
	            else {
	                controlEL.html(data.message);
	            }
	        });
		}
	};
    
    function refreshOrderRow(id, str){
        var el = $("table").find("tr[attr='" + id + "']");
        $.get("/customer/order/" + id + "", {
            format: "json"
        }, function(data){
            el.find("td").each(function(index, tdEl){
                if (index == 5) {
                    $(this).html(data.order.processStatus.name);
                }
                else 
                    if (index == 8) {
                        $(this).html("<font color='#CE9393'>" + str + "</font>");
                    }
            });
        });
    }
    
    function reverseImg(direction){
        var i = 0;
        var timer = setInterval(function(){
            if (direction) {
                $('#top-bar-lable').rotateLeft(6);
            }
            else {
                $('#top-bar-lable').rotateRight(6);
            }
            i++;
            if (i >= 30) {
                window.clearInterval(timer);
            }
        }, 1);
    }
	$("a[bind]").click(function(){
		action[$(this).attr("bind")](this);
	});
});
