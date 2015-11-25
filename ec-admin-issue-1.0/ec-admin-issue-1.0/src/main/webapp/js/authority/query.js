   $(document).ready(function() { 
	   $("#getValue").click(
				function(){
					var val=$('#keyword').val();
					var url="";
					if(val=="请输入用户账户"){
						url="/authority/queryByCondition";
					}else{
						url="/authority/queryByCondition?userName="+val;
					}
				 	var form = document.forms[0];
					form.action = url;
					form.method="post";
					form.submit(); 
		});
		
		//模仿googlesugest
		$("#keyword").autocomplete("/authority/queryByName?format=json", {
			dataType : 'json', // 必须填写，不然默认的是text 
			autoFill : true,   // 自动填充 可选 
			max:100,
			extraParams : {
				name: function (){
					return   $('#keyword').val(); 
				},
				format: function (){
					return   'json'; 
				} 
			},//支持像后台传递参数，key为你后台接收参数的名称 
			parse : function(data) {       // 处理返回的json串，以供后续的使用 
				var rows = [];         // 处理后 返回的一个 数组 
				for ( var i = 0; i < data.employeesList.length; i++) { // 如果你返回的是一个 类似{'my':[{'name':'value1'},{'name':'value2'}]} 
					rows[rows.length] = {
						data : data.employeesList[i], //返回的参数,供后续的函数调用 
						value : data.employeesList[i], //鼠标经过时 在 输入框显示的值 
						result : data.employeesList[i],   //选中后在 输入框显示的值 
					};
				} 
				return rows; 
			},
			formatItem : function(item) {
				return item; 
			}
		}); 
	});

function clearnText(){
	var val=$('#keyword').val();
	if(val=="请输入用户账户"){
		$('#keyword').val(""); 
	}
	
}

function addText(){
	var val=$('#keyword').val();
	if(val==""){
		$('#keyword').val("请输入用户账户"); 
	}
}
