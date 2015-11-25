$().ready(function(){
   //表单验证
 	$("#presentBatchForm").validate({
		rules:{
			      value:{ 
			    	    required:true,
		                number:true,
		                range:[0,999999]
		        	} , 
		        	num:{
		       		    required:true,
		                number:true,
		                range:[0,999999]
		       	   } ,
		       	orderBaseAmount:{
		       		    required:true,
		                number:true,
		                range:[0,999999]
		       	   } ,
		       	productType:{
		       		    required:true
		       	   } ,
		       	presentStartDateString:{
		       		    required:true
		       	   } ,
		       	maxQuantity:{
		       		    required:true,
 		                number:true,
		                range:[0,999999]
		       	   } ,
		       	batchTitle:{
		       		    required:true
		       	   },
		       	presentEndDateString:{
		       		isDate:true
		       	},
		       	presentEffectiveDay:{
		            number:true,
		            range:[1,999999]	
		       	}
		       	
		       
 	    },
 	    
	messages:{
	      value:{ 
	    	    required:"不能为空",
              number:"格式错误",
              range:"范围错误"
      	} , 
      	num:{
     		    required:"不能为空",
              number:"格式错误",
              range:"范围错误"
     	   } ,
     	orderBaseAmount:{
     		    required:"不能为空",
              number:"格式错误",
              range:"范围错误"
     	   } ,
     	productType:{
      		    required:"请选择针对商品类别"
     	   } ,
     	presentStartDateString:{
     		    required:"不能为空"
     	   } ,
     	maxQuantity:{
     		   required:"不能为空",
               number:"格式错误",
               range:"量范围错误"
     	   } ,
     	batchTitle:{
     		    required:"不能为空"
     	   },
     	  presentEndDateString:{
	       		isDate:"格式错误"
	       	},
	      presentEffectiveDay:{
	            number:"格式错误",
	            range:"范围错误"	
	       	}
 	}
	});	
});



