$().ready(function(){
   // 表单验证
  	$("#grid_form").validate({

 
		rules:{
			    name:{ required:true
		        	} , 
		        pageSize:{
		       		    required:true,
		                number:true,
		                range:[0,50]
		       	   },
		       	exportSize:{
		     	   required:true,
		      	   number:true,
		      	   range:[0,999999999]
		      	},
		      	mainSql:{
		        		 required:true
		        	} 
 	    },
 	    
	messages:{
		    name:{
	    		required:"报表名称不能为空"
	    		},
	    	pageSize:{
 	    	required:"每页显示数量不能为空",
 	    	number:"输入错误",
 	    	range:"范围错误，显示数量为0-50"
 	    },
 	   exportSize:{
	     	   required:"每次导出数量不能为空",
	      	   number:"输入错误",
	      	   range:"范围错误"
	      	},
	      	mainSql:{
       		 required:"主要SQL不能为空"
       	} 
 	}
	});	


});
 

function Column_formValidate(){
  	if($("#Column_name").val().length==0){
  		alert("列名不能为空");
 		return false;
	}
  	if($("#Column_value").val().length==0){
  		alert("值不能为空");
 		return false;
	}
  	if($("#Column_order").attr("checked")){
   		if($("#Column_ascSql").val().length==0){
   	  		alert("升序sql不能为空");
  	 		return false;
  		}
  		if($("#Column_descSql").val().length==0){
  	  		alert("降序sql不能为空");
  	 		return false;
  		}
  	}
    return true;
}

function Condition_form(){
  	if($("#Condition_name").val().length==0){
  		alert("字段名不能为空");
 		return false;
	}
  	if($("#Condition_parameterName").val().length==0){
  		alert("参数不能为空");
 		return false;
	} 
  	if($("#Condition_type").val().length==0){
  		alert("请选择类型");
 		return false;
	}
  	if($("#Condition_control").val().length==0){
  		alert("请选择控件");
 		return false;
	}
    return true;
}
 
 

