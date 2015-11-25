$().ready(function(){
 	 if($("#type").find("option:selected").text()=="枚举"){
		 $("#enumadd").html("<a id='itemsadd' onclick='gotoproductMetaEnum("+$("#id").val()+");' href='javascript:void(0)'>添加枚举项>></a>");
	 }
     $("#type").bind("change",function(){
     	 if($("#type").find("option:selected").text()=="枚举"){
    		 $("#enumadd").html("<a id='itemsadd' onclick='gotoproductMetaEnum("+$("#id").val()+");' href='javascript:void(0)'>添加枚举项>></a>");
    	 }
     	 else{
     		 $("#enumadd").html("");
     	 }  
 })
});

 