$(function() {
	
	$("#successMessage").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		buttons: {
			"确定": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
	$("#failMessage").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		buttons: {
			"确定": function() {
				$( this ).dialog( "close" );
			}
		}
	});

	$("#successMessage").bind("dialogclose", function() {
		window.location.href = "/presentcard/password";
	});
	
	$("#failMessage").bind("dialogclose", function() {
		window.location.href = "/presentcard/password";
	});

});

$("#sbm").bind("click",function(){
 	if(submitCheck()){
		$("#presentCardModifyForm").submit();
	}
})

function submitCheck() {
	if ($("#repeatedPassword").val() != $("#password").val()) {
		$("#repeatMessage").html("输入密码不一致");
		return false;
	}else{
		return true;
	}
}

jQuery(function($) {
	$("#id").blur(function() {
		var id = $("#id").val();
		if (id != '') {
			var url = '/presentcard/get?id=' + id + '&format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'GET',
				url : url,
				error : function() {

				},
				success : function(data) {
					if (data.result == 1) {
						
						$("#error").html('');
						$("#presentCardId").html(data.presentCard.id);
						var name = data.presentCard.status.name;
						$("#status").html(name);
						$("#type").html(data.presentCard.type.name);
						$("#createdate").html(data.presentCard.createDate);
						$("#denomination").html(data.presentCard.denomination);
						$("#balance").html(data.presentCard.balance);
						var date = data.presentCard.endDate;
						if(date!='' && date != null){
							var dateday=date.split(" ");
							$("#enddate").html(dateday[0]);
						}else{
							$("#enddate").html('');
						}

						$("#canceldiv").attr("style", "display: block");
					} else {
                        $("#error").html('礼品卡不存在');
                        $("#canceldiv").attr("style", "display: none");
					}
				}
			});
		}
	});

});

$().ready(function(){
 
	   // 表单验证
	 	$("#presentCardModifyForm").validate({
			rules:{
			    password:{
			       	required:true
			        } 
	 	    },	 	    
		messages:{
	      	password:{
	      		required:"密码不能为空"
	      	}
	 	}
		});	
	});

$(document).ready(function(){
	var result = $("#result").val();
	if(result==1){
		$("#successMessage").dialog('open');
	}else if(result == 2){
		$("#failMessage").dialog('open');
	}	
	});

