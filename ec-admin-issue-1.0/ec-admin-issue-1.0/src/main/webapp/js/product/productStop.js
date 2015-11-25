$().ready(function(){
	   $("#actionFlag").val("");
   $("#cx").bind("click",function(){
 	   $("#productform").submit();
   })
    $("#sbm").bind("click",function(){
 	   $("#excelForm").submit();
   })
   
   $(".selectAll").bind("click",function(){
	   if($(this).attr("checked")){
		   $(".selectAll").attr("checked",true);
		   $("#datatable").find("input[class='stop'][canstop='can']").each(function(){
			   $(this).attr("checked",true);
		   })
	   }else{
		   $(".selectAll").attr("checked",false);
		   $("#datatable").find("input[class='stop']").each(function(){
			   $(this).attr("checked",false);
		   }) 
	   }
   })
   
   $("#stopButton").bind("click",function(){
       $("#actionFlag").val("stop");
	   if($("#datatable").find(":checked:checked[class='stop']").length==0){
		   alert("请选择数据");
		   return;
	   }
       var remark = $("#remark").val();
       if(remark == ""){
           alert("必须选择备注!");
           return;
       }
	   if(!confirm("确认停用所选商品？")){
		   return;
	   }
	   var ids="";
 	   $("#datatable").find(":checked:checked[class='stop']").each(function(){
		   ids=ids+$(this).val()+",";
	   })
	   $("#ids").val(ids);
	  	$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : 'json',
			data : {
				"actionFlag" : 'stop',
                "remark": remark,
				"ids" : ids,
				"format" : 'json'
			},
			url : "/product/findproduct",
			error : function() {// 请求失败处理函数
				alert('请求失败');
			},
			success : function(data) { // 请求成功后回调函数。
				alert("批量停用成功");
			 	   $("#datatable").find(":checked:checked[class='stop']").each(function(){
					   $(this).parent().parent().find("span[class='status']").text(data.saleStatus);
					   $(this).attr("checked",false);
					   $(this).attr("canstop","cannot");
					   $(this).attr("disabled","disabled");
				   })
			}
	  	});
    })
   
   $(".delectpro").live("click",function(){
 	   if(!confirm("确认删除该条记录？")){
			return;
		}
	    var obj=$(this).parent().parent();
	    obj.remove();
  //	 	var proId= obj.find("input[class='stop']").val();
// 	  	$.ajax({
//				async : false,
//				cache : false,
//				type : 'POST',
//				dataType : 'json',
//				data : {
//	 				"proId" : proId,
//					"format" : 'json'
//				},
//				url : "/product/deleteproduct",
//				error : function() {// 请求失败处理函数
//					alert('请求失败');
//				},
//				success : function(data) { // 请求成功后回调函数。
//					alert("删除成功");
//					obj.find("span[class='status']").text(data.message);
// 				}
//		});
   })
      $("#tjcx").click();
});

function checkfile(){
	if($("#fileName").val()==""){
		return false;
	}
	return true;
}
 