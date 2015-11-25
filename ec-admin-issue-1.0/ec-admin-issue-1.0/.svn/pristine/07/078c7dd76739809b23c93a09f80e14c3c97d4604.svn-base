$(document).ready(function() {
	Supply.initEvent();
});
(
	function() {
		window.Supply = {};
		
		Supply.saveSupply={
				add:false,
				modity:false
		};
		//初始化事件
		Supply.initEvent = function() {
			//初始化添加按钮
			$("#addModel").click(function(){
				Supply.saveSupply.add = true;
				Supply.saveSupply.modity = false;
				$("#grade").val("");
				$("#model").val("480001");
				
				$( "#add_models" ).dialog({
					height:115,
					width:500,
					title:"模型新增界面",
					modal: true
				});
				
			});
			
			//初始化修改按钮
			$("#updateModel").click(function(){
				var length = $("input:checked").length;
				if(length < 1) {
					alert("请勾选需要修改的模型！");
					return false;
				} 
				var id = $("input:checked").attr("id").slice(9);
				var grade = $("#tr_list"+id).find("td").eq(1).text();
				var weightsText = $("#tr_list"+id).find("td").eq(2).text();
				var leng = weightsText.length;
				var model = parseInt(weightsText.slice(0, leng-1))/100;
				
				Supply.saveSupply.add = false;
				Supply.saveSupply.modity = true;
				
				$("#grade").val(grade);
				$("#model.id").val(model)
				$("#id").val(id);
				$( "#add_models" ).dialog({
					height:115,
					width:500,
					title:"模型修改界面",
					modal: true
				});
				
			});
			
			//初始化删除按钮
			$("#deleteModel").click(function() {
				var length = $("input:checked").length;
				if(length < 1) {
					alert("请勾选需要删除的模型！");
					return false;
				} 
				var id = $("input:checked").attr("id").slice(9);
				$.ajax({
					url : "/replenishment/deleteModel",
					type : "POST",
					async : false,
					data :{id :id},
					dataType : "html",
					success : function() {
						window.location.href="/replenishment/showMrModelChoose";
					},
					error : function (XMLHttpRequest, textStatus, errorThrown) {
					    this;
					}
				});
			});
			
			
			$("#form").submit( function () {
				//新增保存
				if(Supply.saveSupply.add){
					if(!Supply.validateForm()){
						return false;
					}
					Supply.closeDialog();
				}
				if(Supply.saveSupply.modity){
					if(!Supply.validateForm()){
						return false;
					}
					Supply.closeDialog();
				}
			} );
			
			//处理复选框只能选择一个
			$(".checkbox_list").click(function() {
				if($(this).attr("checked")) {
					var id = $(this).attr("id");
					var checkboxList = $(".checkbox_list");
					var length = checkboxList.length;
					for ( var i = 0; i < length; i++) {
						if (id != checkboxList.eq(i).attr("id")  && checkboxList.eq(i).attr("checked")) {
							checkboxList.eq(i).attr("checked", false);
						}
					}
				}
			});
		};
		//表单提交验证
		Supply.validateForm = function() {
			if($("#grade").val().replace(/^(\s+)|(\s+)$/g, "")==""){
				alert("销售级别不能为空！");
				$("#grade").focus();
				return false;
			} else {
				if(!(/^[a-zA-Z]{1,1}[+-]{0,1}$/).test($("#grade").val())) {
					alert("销售级别只能是字母或者'+','-'号！");
					$("#grade").focus();
					return false;
				}
			}
			return true;
		};
		Supply.closeDialog = function() {
			$( "#add_models").dialog("close"); 
		};
		
	}	
)();