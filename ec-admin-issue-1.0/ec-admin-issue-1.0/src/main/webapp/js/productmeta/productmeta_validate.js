$().ready(function(){
   //表单验证
 	$("#productMetaForm").validate({
		rules:{
			    name:{ required:true
		        	} , 
		        	length:{
		       		    required:true,
		                number:true,
		                range:[1,999]
		       	   } 
 	    },
 	    
	messages:{
		      name:{
	    		required:"属性名不能为空"
	    		},
	    		length:{
 	    	required:"属性长度不能为空",
 	    	number:"属性长度格式错误",
 	    	range:"属性长度范围(1-999)"
 	    } 
 
 	}
	});	


});

