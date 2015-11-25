	$(function() {
		$("#codeouter").accordion({
			active:false
		});
	});
	function visible(targetid) {
		target = document.getElementById(targetid);
		target.style.display = "block";
	}
	function invisible(targetid) {
		target = document.getElementById(targetid);
		target.style.display = "none";
	}	
    function ajaxget(url,date,id) {
    	$("#requestInfo").html('正在请求,请稍后......');
    	$("#requestInfo").dialog("open");
        $.ajax({
            type: 'get', //可选get
            url: url, //这里是接收数据的程序
            data: date, //传给PHP的数据，多个参数用&连接
            dataType: 'html', //服务器返回的数据类型 可选XML ,Json jsonp script html text等
            success: function(msg) {    	
                //这里是ajax提交成功后，程序返回的数据处理函数。msg是返回的数据，数据类型在dataType参数里定义！
                	$('#'+id).html(msg);
               $("#requestInfo").dialog("close");
            },
            error: function() {
            	$("#requestInfo").html('请求失败');
            }
        })
    }
    function ajaxpost(url,form,id) {
    	var flag=validate(form);
    	if(flag){
    	$("#requestInfo").html('正在请求,请稍后......');
    	$("#requestInfo").dialog("open");
        $.ajax({
            type: 'post', //可选get
            url: url, //这里是接收数据的程序
            data: getFormVal(form), //传给PHP的数据，多个参数用&连接
            dataType: 'html', //服务器返回的数据类型 可选XML ,Json jsonp script html text等
            
            success: function(msg) {
                //这里是ajax提交成功后，程序返回的数据处理函数。msg是返回的数据，数据类型在dataType参数里定义！
                	$('#'+id).html(msg);
                $("#requestInfo").dialog("close");
            },
            error: function() {
				$("#requestInfo").html('请求失败');
            }
        })
        }
    }
    function getFormVal(form){
    	var datevaule = "";
    	var thisform=document.getElementById(form);
    	var inputs=thisform.getElementsByTagName("input");
    	for(i=0;i<inputs.length;i++){//inputs.length
    	   if(i+1==inputs.length){
    		  datevaule += inputs[i].name+"="+inputs[i].value
    	   }else{
    		  datevaule +=inputs[i].name+"="+inputs[i].value+"&"
       	   }
    	}
    	return datevaule;
    }
    
   $("#requestInfo").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width : 250,
		height : 80
	});
   
   function validate(form){
	  var f = "#"+form;
	  if($(f+" input[name='name']").val()==""){
		  alert("字段名称为必填!");
		  return false;
	  };
	  return true;
   }
