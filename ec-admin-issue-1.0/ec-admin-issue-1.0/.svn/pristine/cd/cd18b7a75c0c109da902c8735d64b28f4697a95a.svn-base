$(function(){
	$("#paymentEditForm").submit(function(){
		 return confirm("是否确认提交？");
	});
	$("button[name='addPayment']").click(function(){
		window.location=$("#path").val()+"/payment/new";
	});
});

function changeAvailable(id,index){
	var paymentUrl = '/payment/'+id+'/changeAvailable?format=json';
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : paymentUrl,
		error : function() {//请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { //请求成功后回调函数。  
			if(data.result==1){
				if($("#availablespan"+index).html() == "禁用"){
					$("#availablespan"+index).html("启用");
				}else{
					$("#availablespan"+index).html("禁用");
				}
				if($("#availablestr"+index).html() == "有效"){
					$("#availablestr"+index).html("无效");
				}else{
					$("#availablestr"+index).html("有效");
				}
			}else{
				alert('更新失败');
			}
		}
	});
}

var secs =5;
var URL ; 
function Load(url){ 
URL =url; 
for(var i=secs;i>=0;i--) 
{ 
window.setTimeout('doUpdate(' + i + ')', (secs-i) * 1000); 
} 
} 
function doUpdate(num) 
{ 
document.getElementById('ShowDiv').innerHTML = '将在'+num+'秒后系统自动跳转到管理支付方式页面' ; 
if(num == 0) { window.location=URL; } 
} 
$().ready(function() {
	$("#paymentEditForm").validate({
		rules : {
			name:{
				required : true
			},
			payFee : {
				min:0.01,
				number:true 
			},
			payFeeMin : {
				min:0.01,
				number:true 
			},
			refundFee : {
				min:0.01,
				number:true 
			},
			refundFeeMin : {
				min:0.01,
				number:true 
			}
		},
		messages : {
			name:{
				required : "订单号必填"
			},
			payFee : {
				min:"收款手续费费率不能负数和零",
				number:"收款手续费费率必须为数字"
			},
			payFeeMin : {
				min:"收款手续费最小收取金额不能负数和零",
				number:"收款手续费最小收取金额必须为数字"
			},
			refundFee : {
				min:"退款手续费费率不能为负数和零",
				number:"退款手续费费率必须为数字"
			},
			refundFeeMin : {
				min:"退款手续费最小收取金额不能为负数和零",
				number:"退款手续费最小收取金额必须为数字"
			}
		}
	});
});

