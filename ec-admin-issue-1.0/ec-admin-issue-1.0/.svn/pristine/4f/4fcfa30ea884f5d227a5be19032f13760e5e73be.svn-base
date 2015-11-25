// 初始运行
$(document).ready(function() {
	//查询结果全选A
	$("input[name='selectA']").click(function() {
				if ($("input[name='selectA']").attr("checked")) {
					$("input[name='ids']")
							.attr("checked", "checked");
				} else {
					$("input[name='ids']").removeAttr("checked");
				}
			});
	//查询结果全选B
	$("input[name='selectB']").click(function() {
				if ($("input[name='selectB']").attr("checked")) {
					$("input[name='ids']")
							.attr("checked", "checked");
				} else {
					$("input[name='ids']").removeAttr("checked");
				}
			});
	
})
//渠道销售审批通过或回退
function channelSaleAudit(id,audit,page,count) {
	//确认通过
	var operate = "";
	if(audit==1){
    	operate="通过审核"
	}else{
	    operate="回退"
	}
	if (!confirm("确定要"+operate+"吗?")) {
		return;
	}
	//弹出等待框
	$("#requestInfo").html('正在请求....');
	$("#requestInfo").dialog("open");
	//提交路径
	var url = "/seller/channelSalesAudit";
	//提交数据
	var date = ""
	if(id!=0){//如果是但数据
		date = "ids="+id;
	}else{//如果是批量数据
		date = $('#ChannelSaleForm').serialize();
	}
	//加上json
    date = date+"&format=json&audit="+audit;
	$.ajax({
				type : 'POST',
				url : url,
				data : date,
				dataType : 'json',
				success : function(msg) {
					$("#requestInfo").dialog("close");
					if(id==0){
						alert('成功'+operate+msg.succeed+'条记录，失败'+msg.fail+'条记录！');
						if(msg.succeed!=0){
						//刷新页面
						pagination(page,count);
						}
					}else{
						if (msg.succeed!=0) {
						 //刷新页面
						 pagination(page,count);
						}else{
							alert(operate+'失败');
						}
					}
				},
				error : function() {
					$("#requestInfo").dialog("close");
					alert('请求失败');
				}
			});
}
//导出excel
function channelSalesExcel() {
	if (!confirm("确定导出搜索结果吗?")) {
		return;
	}
	var form = $('#ChannelSalesQueryForm');
	form.attr("action", "/excel/salesAudit");
	form.append("<input id='formatExcel' type='hidden' name='format' value='xls'> ");
	$("#requestInfo").html('正在导出EXCEL....');
	$("#requestInfo").dialog("open");
    form.submit();
}
//查询
 function query(){
      var form = $('#ChannelSalesQueryForm');
      form.attr("action", "/seller/salesAudit");
      var format = $('#formatExcel');
      format.remove();
      form.submit();
 }

//弹出对话框
$("#requestInfo").dialog({
			autoOpen : false,
			bgiframe : false,
			modal : true,
			width : 250,
			height : 80
		});